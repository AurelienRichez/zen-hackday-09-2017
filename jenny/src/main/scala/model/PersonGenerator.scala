package model

import utils.IdGenerator

import scala.util.Random

class PersonGenerator(rand: Random) {
  import PersonGenerator._

  def next =
    Person(IdGenerator.genId()(rand),
           names(rand.nextInt(names.size - 1)),
           surnames(rand.nextInt(surnames.size - 1)),
           nextSeq(tags, 4).toSet)

  def nextSeq[A](source: Vector[A], total: Int): Seq[A] =
    (for (i <- 1 to total) yield source(rand.nextInt(source.size - 1)))

}

object PersonGenerator {

  def newPersonGenerator = new PersonGenerator(new Random())

  val names = Vector(
    "Martin",
    "Bernard",
    "Thomas",
    "Petit",
    "Robert",
    "Richard",
    "Durand",
    "Dubois",
    "Moreau",
    "Laurent",
    "Simon",
    "Michel",
    "Lefebvre",
    "Leroy",
    "Roux",
    "David",
    "Bertrand",
    "Morel",
    "Fournier",
    "Girard",
    "Bonnet",
    "Dupont",
    "Lambert",
    "Fontaine",
    "Rousseau",
    "Vincent",
    "Muller",
    "Lefèvre",
    "Faure",
    "André",
    "Mercier",
    "Blanc",
    "Guérin",
    "Boyer",
    "Garnier",
    "Chevalier",
    "François",
    "Legrand",
    "Gauthier",
    "Garcia",
    "Perrin",
    "Robin",
    "Clément",
    "Morin",
    "Nicolas",
    "Henry",
    "Roussel",
    "Mathieu",
    "Gautier",
    "Masson",
    "Marchand",
    "Duval",
    "Denis",
    "Dumont",
    "Marie",
    "Lemaire",
    "Noël",
    "Meyer",
    "Dufour",
    "Meunier",
    "Brun",
    "Blanchard",
    "Giraud",
    "Joly",
    "Rivière",
    "Lucas",
    "Brunet",
    "Gaillard",
    "Barbier",
    "Arnaud",
    "Martinez",
    "Gérard",
    "Roche",
    "Renard",
    "Schmitt",
    "Roy",
    "Leroux",
    "Colin",
    "Vidal",
    "Caron",
    "Picard",
    "Roger",
    "Fabre",
    "Aubert",
    "Lemoine",
    "Renaud",
    "Dumas",
    "Lacroix",
    "Olivier",
    "Philippe",
    "Bourgeois",
    "Pierre",
    "Benoît",
    "Rey",
    "Leclerc",
    "Payet",
    "Rolland",
    "Leclercq",
    "Guillaume",
    "Lecomte",
    "Lopez",
    "Jean",
    "Dupuy",
    "Guillot",
    "Hubert",
    "Berger",
    "Carpentier",
    "Sanchez",
    "Dupuis",
    "Moulin",
    "Louis",
    "Deschamps",
    "Huet",
    "Vasseur",
    "Perez",
    "Boucher",
    "Fleury",
    "Royer",
    "Klein",
    "Jacquet",
    "Adam",
    "Paris",
    "Poirier",
    "Marty",
    "Aubry",
    "Guyot",
    "Carré",
    "Charles",
    "Renault",
    "Charpentier",
    "Ménard",
    "Maillard",
    "Baron",
    "Bertin",
    "Bailly",
    "Hervé",
    "Schneider",
    "Fernandez",
    "Le",
    "Collet",
    "Léger",
    "Bouvier",
    "Julien",
    "Prévost",
    "Millet",
    "Perrot",
    "Daniel",
    "Le",
    "Cousin",
    "Germain",
    "Breton",
    "Besson",
    "Langlois",
    "Rémy",
    "Le",
    "Pelletier",
    "Lévêque",
    "Perrier",
    "Leblanc",
    "Barré",
    "Lebrun",
    "Marchal",
    "Weber",
    "Mallet",
    "Hamon",
    "Boulanger",
    "Jacob",
    "Monnier",
    "Michaud",
    "Rodriguez",
    "Guichard",
    "Gillet",
    "Étienne",
    "Grondin",
    "Poulain",
    "Tessier",
    "Chevallier",
    "Collin",
    "Chauvin",
    "Da",
    "Bouchet",
    "Gay",
    "Lemaître",
    "Bénard",
    "Maréchal",
    "Humbert",
    "Reynaud",
    "Antoine",
    "Hoarau",
    "Perret",
    "Barthélemy",
    "Cordier",
    "Pichon",
    "Lejeune",
    "Gilbert",
    "Lamy",
    "Delaunay",
    "Pasquier",
    "Carlier",
    "Laporte"
  )

  val surnames = Vector(
    "Jean",
    "Marie",
    "Philippe",
    "Nathalie",
    "Michel",
    "Isabelle",
    "Alain",
    "Sylvie",
    "Patrick",
    "Catherine",
    "Nicolas",
    "Martine",
    "Christophe",
    "Christine",
    "Pierre",
    "Françoise",
    "Christian",
    "Valérie",
    "Éric",
    "Sandrine",
    "Frédéric",
    "Stéphanie",
    "Laurent",
    "Véronique",
    "Stéphane",
    "Sophie",
    "David",
    "Céline",
    "Pascal",
    "Chantal",
    "Daniel",
    "Patricia",
    "Alexandre",
    "Anne",
    "Julien",
    "Brigitte",
    "Thierry",
    "Julie",
    "Olivier",
    "Monique",
    "Bernard",
    "Aurélie",
    "Thomas",
    "Nicole",
    "Sébastien",
    "Laurence",
    "Gérard",
    "Annie",
    "Didier",
    "Émilie",
    "Dominique",
    "Dominique",
    "Vincent",
    "Virginie",
    "François",
    "Corinne",
    "Bruno",
    "Élodie",
    "Guillaume",
    "Christelle",
    "Jérôme",
    "Camille",
    "Jacques",
    "Caroline",
    "Marc",
    "Léa",
    "Maxime",
    "Sarah",
    "Romain",
    "Florence",
    "Claude",
    "Laetitia",
    "Antoine",
    "Audrey",
    "Franck",
    "Hélène",
    "Jean-Pierre",
    "Laura",
    "Anthony",
    "Manon",
    "Kévin",
    "Michèle",
    "Gilles",
    "Cécile",
    "Cédric",
    "Christiane",
    "Serge",
    "Béatrice",
    "André",
    "Claire",
    "Mathieu",
    "Nadine",
    "Benjamin",
    "Delphine",
    "Patrice",
    "Pauline",
    "Fabrice",
    "Karine",
    "Joël",
    "Mélanie",
    "Jérémy",
    "Marion",
    "Clément",
    "Chloe",
    "Arnaud",
    "Jacqueline",
    "Denis",
    "Elisabeth",
    "Paul",
    "Evelyne",
    "Lucas",
    "Marine",
    "Hervé",
    "Claudine",
    "Jean-Claude",
    "Anais",
    "Sylvain",
    "Lucie",
    "Yves",
    "Danielle",
    "Ludovic",
    "Carole",
    "Guy",
    "Fabienne",
    "Florian",
    "Mathilde",
    "Damien",
    "Sandra",
    "Alexis",
    "Pascale",
    "Mickaël",
    "Annick",
    "Quentin",
    "Charlotte",
    "Emmanuel",
    "Emma",
    "Louis",
    "Severine",
    "Benoît",
    "Sabrina",
    "Jean-Luc",
    "Amandine",
    "Fabien",
    "Myriam",
    "Francis",
    "Jocelyne",
    "Hugo",
    "Alexandra",
    "Jonathan",
    "Angelique",
    "Loïc",
    "Josiane",
    "Xavier",
    "Joelle",
    "Théo",
    "Agnes",
    "Adrien",
    "Mireille",
    "Raphaël",
    "Vanessa",
    "Jean-François",
    "Justine",
    "Grégory",
    "Sonia",
    "Robert",
    "Bernadette",
    "Michaël",
    "Emmanuelle",
    "Valentin",
    "Oceane",
    "Cyril",
    "Amelie",
    "Jean-Marc",
    "Clara",
    "René",
    "Maryse",
    "Lionel",
    "Anne-marie",
    "Yannick",
    "Fanny",
    "Enzo",
    "Magali",
    "Yannis",
    "Marie-christine",
    "Jean-Michel",
    "Morgane",
    "Baptiste",
    "Ines",
    "Matthieu",
    "Nadia",
    "Rémi",
    "Muriel",
    "Georges",
    "Jessica",
    "Aurélien",
    "Laure",
    "Nathan",
    "Genevieve",
    "Jean-Paul",
    "Estelle"
  )

  val tags = Vector(
    "red",
    "blue",
    "orange",
    "pink",
    "gray",
    "purple",
    "navy",
    "brown",
    "white",
    "black"
  )

}
