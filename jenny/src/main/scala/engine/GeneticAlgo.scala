package engine

import model.Person
import utils.IdGenerator

import scala.util.Random

case class GeneticAlgo(
  params: GenerationParameters,
  people: Seq[Person],
  populationSize: Int = GeneticAlgo.defaultPopulationSize,
  tournamentSize: Int = GeneticAlgo.defaultTournamentSize
) {
  require(params.groupMaxSizes.sum >= people.size)

  private def cost(genes: Seq[Seat]): Double = genesToGroups(genes).map(params.totalCost).sum

  def generations()(implicit random: Random = new Random()): Iterator[Generation] =
    Iterator.iterate(init())(nextGeneration)

  private def nextGeneration(
    currentGeneration: Generation
  )(implicit rand: Random): Generation = {
    val population = for (i <- 0 to populationSize) yield {
      val breeder1 = findBest(rand.shuffle(currentGeneration.population).take(tournamentSize))
      val breeder2 = findBest(rand.shuffle(currentGeneration.population).take(tournamentSize))
      breed(breeder1, breeder2)
    }
    val best = findBest(currentGeneration.best +: population)
    Generation(population, best)
  }

  private def breed(breeder1: Seq[Seat], breeder2: Seq[Seat])(implicit rand: Random): Seq[Seat] = {
    val crossOverPoint = rand.nextInt(breeder1.size)
    val firstHalf = breeder1.take(crossOverPoint)
    firstHalf ++ breeder2.diff(firstHalf)
  }

  private def init()(implicit random: Random): Generation = {
    val size = params.groupMaxSizes.sum
    val base = people.map(OccupiedSeat.apply) ++ emptySeats().take(size - people.size)
    val population = Stream.continually(random.shuffle(base)).take(populationSize)
    Generation(population, findBest(population))
  }

  private def emptySeats()(implicit random: Random) =
    Stream.continually(EmptySeat(IdGenerator.genId()))

  private def findBest(population: Seq[Seq[Seat]]) = {
    population.minBy(cost)
  }

  private def genesToGroups(seats: Seq[Seat]): Seq[Seq[Person]] = {
    def foo(seats: Seq[Seat], maxSizes: List[Int], acc: List[Seq[Person]]): Seq[Seq[Person]] =
      maxSizes match {
        case size :: tail =>
          val (group, remaining) = seats.splitAt(size)
          foo(remaining, tail, group.flatMap(_.toOpt) +: acc)
        case Nil => acc
      }

    foo(seats, params.groupMaxSizes, Nil)
  }
}

object GeneticAlgo {

  val defaultPopulationSize = 2000

  val defaultTournamentSize = 5
}

case class Generation(population: Seq[Seq[Seat]], best: Seq[Seat])

sealed trait Seat extends Product with Serializable {

  def toOpt: Option[Person] = this match {
    case EmptySeat(_)                 => None
    case OccupiedSeat(person: Person) => Some(person)
  }

}

// We need an id to be able to differentiate, which is not possible with an option
case class EmptySeat(id: String) extends Seat

case class OccupiedSeat(person: Person) extends Seat
