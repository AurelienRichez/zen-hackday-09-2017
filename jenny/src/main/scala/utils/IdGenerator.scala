package utils

import scala.util.Random

object IdGenerator {

  def genId()(implicit rand: Random) = rand.alphanumeric.take(10).mkString

}
