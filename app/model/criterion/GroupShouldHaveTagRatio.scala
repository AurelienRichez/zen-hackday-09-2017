package model.criterion
import model.Person

class GroupShouldHaveTagRatio(ratioMap: Map[String, Double]) extends Criterion {
  import GroupShouldHaveTagRatio._

  private val ratios = normalizedMap(ratioMap)

  override def cost(group: Seq[Person]) = {
    group.flatMap(_.tags.intersect(ratioMap.keySet)) match {
      case Nil  => 0.0
      case tags => costNonEmpty(tags)
    }
  }

  private def costNonEmpty(tags: Seq[String]): Double = {
    val total = tags.length
    val effectiveRatioMap =
      ratioMap.keys.map(key => key -> tags.count(_ == key).toDouble / total).toMap
    mapDiff(effectiveRatioMap)
  }

  private def mapDiff(effectiveRatio: Map[String, Double]): Double =
    ratios
      .mapValues { case (tag, idealRatio) => math.abs(effectiveRatio(tag) - idealRatio) }
      .values
      .sum

}

object GroupShouldHaveTagRatio {

  private def normalizedMap(map: Map[String, Double]) = {
    val sum = map.values.sum
    map.mapValues(_ / sum).view.force
  }
}
