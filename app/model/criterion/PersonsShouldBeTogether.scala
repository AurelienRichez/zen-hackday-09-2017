package model.criterion

import model.Person

class PersonsShouldBeTogether(persons: Set[Person]) extends Criterion {

  override def cost(group: Seq[Person]): Double =
    matchingPersons(group) match {
      case Set.empty => 0.0
      case ps if ps.size == persons.size => 0.0
      case _ => 1.0
    }

  private def matchingPersons(group: Seq[Person]) = group.toSet.intersect(persons)

}
