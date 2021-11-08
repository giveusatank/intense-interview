package com.jd.scala

object SyllabusDemo1 {
  def main(args: Array[String]): Unit = {
    val l1 = List(1,2,3,4)

    val arr = Array(1,2,3,4,5)

    implicit val ord = new Ordering[Int] {
      override def compare(x: Int, y: Int): Int = -(x-y)
    }

    val arr2 = arr.toList.sorted.take(2)
    println(arr2)

    val arr1: Array[(String, Int)] = Array(("1",1),("1",2),("1",3),("1",4))
    val rr = arr1.foldLeft(0)( (cur:Int,nxt:(String,Int)) => cur + nxt._2 )
    println(rr)
    println("2021-11-11 11:11:11".substring(0,10))
  }
}
