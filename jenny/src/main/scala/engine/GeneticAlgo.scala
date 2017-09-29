package engine

import model.Person

import scala.util.Random

case class GeneticAlgo(params: GenerationParameters) {

  def generations()(implicit random: Random = new Random()): Iterator[Generation] =
    Iterator.iterate(init()) { previousGeneration =>
      ???
    }

  private def init()(implicit random: Random): Generation = ???

}

case class Generation(individuals: Seq[Seq[Option[Person]]], best: Seq[Option[Person]])
