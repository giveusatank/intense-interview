package com.jd.scala.demo
import scala.collection.immutable

/**
 * 空、
 */
object SyllabusDemo4 {
  def main(args: Array[String]): Unit = {

    //Scala中的“空”：Nil、None、Nothing、Null、null、Unit、()
    val l1: immutable.Seq[Nothing] = Nil
    val op1: Option[(Int, Int, Int)] = Some((1,2,3))
    val op2 = None

    val kong:Unit = ()
    val null_1 : Null = null
    val anyref_1 : AnyRef = "123"
    //val nothing_1 : Nothing


  }
}
