package engine

import model.criterion.Criterion

case class GenerationParameters(groupMaxSizes: Seq[Int], constraints: Seq[Criterion]) {

  def maxGroupNb: Int = groupMaxSizes.length

}
