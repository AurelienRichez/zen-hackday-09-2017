package engine

import model.Person

import scala.util.Random

case class GeneticAlgo(params: GenerationParameters, random: Random = new Random()) {

  def generations: Iterator[Generation] = Iterator.iterate(init()) { previousGeneration =>
    ???
  }

  private def init(): Generation = ???

}

case class Generation(individuals: Seq[Seq[Option[Person]]], best: Seq[Option[Person]])
