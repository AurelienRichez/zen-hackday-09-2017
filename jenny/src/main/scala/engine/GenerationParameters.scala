package engine

import model.Person
import model.criterion.Criterion

case class GenerationParameters(groupMaxSizes: List[Int], constraints: Seq[Criterion]) {

  def maxGroupNb: Int = groupMaxSizes.length

  def totalCost(group: Seq[Person]): Double = constraints.map(_.cost(group)).sum

}
