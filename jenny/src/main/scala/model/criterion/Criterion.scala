package model.criterion

import model.Person

trait Criterion { self =>

  def cost(group: Seq[Person]): Double

  def withWeight(w: Double): Criterion =
    (group: Seq[Person]) => self.cost(group) * w

  // for now constraints are just criteria with very high weight
  def toConstraint: Criterion = self.withWeight(Criterion.constraintWeight)

}

object Criterion {

  val constraintWeight = 1000.0

}
