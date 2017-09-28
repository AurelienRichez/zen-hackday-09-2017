# Play framework template

Template d'application utilisant le framework Play en version 2.5 et l'injection de dépendances à la compilation

Contient :

 - Dépendances basiques (ws, tests)
 - Options de compilation pour garder le code sûr
 - Scalariform pour un style de code cohérent
 - Des métriques basiques avec des dashboards Grafana pré-configurés
 - Des outils de logs
 - Un exemple d'implémentation de sécurité
 - Des outils pour aider l'intégration avec une db PostgreSQL


## Setup: jenkins - bitbucket/github - nestincloud

Des scripts sont diponibles dans `scripts/jenkins` pour créer facilement des jobs jenkins.

Sur bitbucket/github, il faut ajouter la clé ssh publique de Jenkins pour qu'il puisse accéder en lecture au répertoire.

*Optionel*: une application Nestincloud peut être créé pour faciliter le déploiement des nouvelles versions de l'application.

## Layout du répertoire

```
.
├── app                  # contient les sources de l'application
│   ├── api                   # classes utilitaire liéées à l'API
│   ├── common                # classes communes pouvant être ré-utilisées entre projets
│   ├── controllers           # controlleurs de l'application qui gère le cycle requête/réponse HTTP
│   ├── filters               # filtres HTTP de l'application
│   ├── models                # modèles utilisés dans l'application. Représentent une donnée pour l'application
│   ├── persistence           # classes liées à la persistence des données (sql, mongo ...)
│   └── services              # service pour fournir une couche d'abstraction entre la db et les controlleurs. Gère également la logique des appels
├── conf                 # contient la configuration de l'application: `application.conf`, `logback.xml` ...
│   └── evolutions            # scripts sql à exécuter sur la db
├── docs                 # Documentation de l'application
├── grafana              # json des dashboards grafana
├── project              # configuration des plugins sbt du projet
├── scripts              # scripts utilitaires du projet
├── target               # répertoire utilisé par sbt pour la compilation
└── test                 # contient les tests de l'application
    ├── integration            # tests d'intégration: dépendent d'autres services ex. db, webservice ...
    └── unit                   # tests unitaires: indépendants, doivent être rapides à lancer pour une validation immédiate
```

## Installation

L'application a besoin des dépendances suivantes:

- postgresql
- *optionel* influxdb
- *optionel* grafana

Ces dépendances peuvent soit être installées manuellement (avec `brew`, `apt-get` ...) ou alors démarrées avec `docker-compose`

L'installation d'une instance de postgresql est nécessaire pour le développement de l'application. Cette étape peut être passée dans le cas où on veut seulement lancer l'application avec `docker-compose`.

### Docker-compose

- Installer `docker` et `docker-compose` sur votre machine [Mac](https://docs.docker.com/docker-for-mac/install/) [Windows](https://docs.docker.com/docker-for-windows/install/) [Linux](https://docs.docker.com/engine/installation/linux/debian/) - [docker-compose](https://docs.docker.com/compose/install/)
- Démarrer les dépendances: `docker-compose up`
- Bootstrap influxdb et grafana (cf. Métriques Setup)

Pour arrêter les dépendances: `docker-compose down`


### Installation Manuelle

#### Postgresql

*Installation en mode globale sur la machine*

Vous avez besoin de démarrer Postgres avec une db initialisée. Pour cela, installez PostgreSQL sur votre machine (avec `brew` si vous êtes sur mac). Si vous n'avez pas de db initialisée (remplacez `/data/db/path/somewhere` avec un chemin sur votre machine)

```shell
initdb -D /data/db/path/somewhere
```

Démarrez le daemon postgres

```shell
pg_ctl start -D /data/db/path/somewhere
```

Ou avec brew `brew`

```shell
brew services start postgresql
```

Pour utiliser la configuration par défaut, créer un utilisateur `saretec` avec un mot de passe `saretec` et un nom de db `saretec-devfactory`.

```shell
createuser --echo --pwprompt saretec
createdb --owner=saretec saretec-devfactory
```

*Avec un container Docker:*

Pour installer et démarrer une instance de PostgreSQL dans un container:

```shell
docker run -p '5432:5432' -e 'POSTGRES_USER=saretec' -e 'POSTGRES_PASSWORD=saretec' -e 'POSTGRES_DB=saretec-devfactory' postgres:9
```

La connection peut être testée en utilisant le binaire `psql`:

```shell
/path/to/bin/psql -h localhost -U saretec -W saretec saretec-devfactory
```

Dans la commande docker, l'argument `-p 5432:5432` lie le le port de postgresql à l'intérieur du container avec le port local 5432.
Pour démarrer plusieurs DBs localement, il est recommandé de remplacer cet argument avec `-P`, de checker le port local qui a été automatiquement assigné et de l'utiliser dans la configuration de l'application.


> Si d'autres login ou mot de passe sont utilisés, alors il est nécessaire de changer les valeurs url/username/password de l'objets `db.default` dans votre fichier de configuration local `local.conf`

#### Influxdb - Grafana

cf documentation des projets:

- [Influxdb](https://docs.influxdata.com/influxdb/v1.2/introduction/installation/)
- [Grafana](http://docs.grafana.org/installation/)

## Lancer l'application

S'il n'est pas déjà lancé, démarrer le daemon postgres (à la main, avec brew ou avec docker)

Démarrer le serveur:

```shell
sbt ~run
```

## Créer une release

```
git checkout master && git pull # Checkouter la branche principale du projet et récupérer les derniers changements
sbt release
```

Cette dernière commande va vous demander de définir la version pour la release courante et pour le prochain cycle de développement.
Vous pouvez démarrer le process avec les valeurs par défaut avec `sbt release with-defaults`, cela va bumper le numéro mineur de la version.

Vous pouvez aussi définir les versions sur la ligne de commande avec: `sbt release release-version a.b.c next-version x.y.z-SNAPSHOT`

## Logs

Les logs sont essentiels dans une application. Ils permettent aux développeurs et aux admin systèmes de monitorer et de débuguer une application.
Ainsi les logs doivent être efficaces et utilisés prudemment.

Pour écrire des logs, vous avez tout d'abord besoin d'une instance de l'objet `Logger`. Pour cela vous pouvez utiliser le trait `common.Logging`. Cela vous fournira une instance de `Logger` correctement nommé (basé sur le nom de class où il est utilisé)

Exemple:

```scala
class MyAlgorithm extends common.Logging {
  // here I have an instance of `play.api.Logger`
  // this is equivalent to `val logger = play.api.Logger("MyAlgorithm")`

  logger.info("...")
}
```

Vous avez à votre disposition différents niveaux de logs: `trace`, `debug`, `info`, `warn` et `error`.

Une application devrait utiliser différents niveaux de logs pour donner des niveaux différents d'informations. Une règle simple et d'utiliser `trace` et `debug` pour les informations utiles pendant le développement et qui pourrait aider à débuguer certaines parties complexes d'une application.
`info` est utilisé pour donner des informations sur l'exécution normale du programme.
`warn` est utilisé pour des informations d'événements non-fatals qui ne stoppent pas le programme.
`error` devrait être utilisé pour logguer toutes les exceptions et tout ce qui arrête l'exécution du programme.

Vous **ne devez pas** logguer des informations confidentielles comme des mots de passe utilisateurs, des informations personnelles, des clés secrétes ...

Exemple:

```scala
import utils.Logging

class MyService extends Logging {
  def createUser(userData: UserData): Future[Option[User]] = {
    // don't log all userData, it may contain sensitive information
    logger.debug(s"creating user with data ${userData.name}")
    userStorage.create(userData)
      .recoverWith {
        case DuplicatedElement =>
          logger.warn(s"User ${userData.name} already exists")
          Future.successful(None)
        case exception =>
          logger.error(s"Error while creating user ${userData.name}", exception)
          Future.failed(exception)
      }
  }
}
```

Les logs sont écrits dans `stdout` et dans `logs/application.conf`.
Play utilise [logback](https://logback.qos.ch). Les loggers sont configurés dans le fichier `conf/logback.xml`. Plus d'information sur les options de configuration est disponible sur leur site web.

A lire [aussi](https://www.clever-cloud.com/blog/engineering/2016/05/23/let-your-logs-help-you/)

## Métriques

L'applications utilise [Kamon](http://kamon.io) pour collecter et reporter les métriques. L'application est configurée pour envoyer les métriques à un serveur [InfluxDB](https://docs.influxdata.com/influxdb) server.

### Setup

Installer une instance de InfluxDB sur votre machine, soit manuellement, avec `brew` ou avec `docker`.


Ensuite, il faut créer une database sur InfluxDB pour recevoir nos métriques:

```sh
curl -i -XPOST http://localhost:8086/query --data-urlencode "q=CREATE DATABASE saretec"
```

(Remplacer localhost et le port les informations de votre instance d'InfluxDB)

### Visualisation

Vous pouvez visualiser les métriques en utilisant [Grafana](http://grafana.org) ou [Cronograf](https://docs.influxdata.com/chronograf). Ici nous allons utiliser Grafana. Des dashboards pré-configurés sont disponibles dans le dossier `grafana/`.

Dans Grafana, vous aurez tout d'abord besoin de configurer InfluxDB en tant que datasource. Cliquer sur le bouton "Data Sources", "Add data source". Selectionner `Type = "InfluxDB"`, configurer les options http et sélectionner la base de données que vous venez de créer, `saretec` ici.

Pour importer un dashboard, sélectionner le bouton "Import", "Upload .json File". Ensuite entrez un nom pour votre dashboard, sélectionnez la datasource InfluxDB que vous venez de configurer. Pour le champ `application name`, entrer la valeur qui est présente dans `conf/application.conf`, pour la clé `kamon.influxdb.application-name`.


## Sécurité

Lors de la création d'une méthode dans un controlleur, un développeur doit penser aux contrôles d'accès à utiliser sur cette méthode. Pour chaque resource d'une application, les controles peuvent se faire sur plusieurs niveaux:

- L'identité de l'utilisateur (authentication): vérifier si l'utilisateur est connu et valide
- Les permissions utilisateurs (authorization): si l'utilisateur a les droits suffisants pour accéder à la resource voulue.
- Autres: rate limit, détection de fraude ...

Dans le fichier `app/controllers/authentication/Auth.scala` vous avez un exemple de contrôle sur une clé d'API. Si une clé d'API est présente, alors on vérifie sa validité avec notre service `ApiKeyService`. Si aucune clé n'est présente, une réponse `Unauthorized` est renvoyée.

Une fois nos polices de sécurité définies, on peut facilement les utiliser dans les controlleurs pour protéger nos méthodes, cf. `app/controllers/PetsController.scala`.

## Conventions de code

### Scala format

L'application utilise [Scalariform](https://github.com/scala-ide/scalariform) pour avoir un code formatté de manière consistante. Le code est formatté après chaque étape `compile`

### Imports

Les imports en Scala doivent être conservés clairs et organisés. Dans ce projet, la convention suivante est suivie:

- Les imports sont séparés en groupes:
    1. imports des langages de base: `scala` and `java` namespaces
    2. imports de librairies: `play`, `anorm`, `kamon` ...
    3. imports de l'application: `controllers`, `models`, `common` ...
- Dans chaque groupe les imports doivent être ordonnés par ordre alphabétique

Exemple:

```scala
import java.util.UUID
import scala.concurrent.Future

import enumeratum.{PlayJsonEnum, Enum, EnumEntry}
import play.api.libs.json.{Json, JsObject}

import persistence.utils.AnormEnum
```
