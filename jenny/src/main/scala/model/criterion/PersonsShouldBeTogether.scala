package model.criterion

import model.Person

import scala.collection.immutable.Set

class PersonsShouldBeTogether(persons: Set[Person]) extends Criterion {

  override def cost(group: Seq[Person]): Double = {
    val persons = matchingPersons(group)
    if (persons.isEmpty || persons.size == persons.size) 0.0
    else 1.0
  }
  private def matchingPersons(group: Seq[Person]) = group.toSet.intersect(persons)

}
