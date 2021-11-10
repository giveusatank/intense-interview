package com.jd.scala.demo

/**
 * Scala类图：                  Any
 * -AnyVal                  -AnyRef
 * --Int                     --Scala类
 * --Float                   --Java类/接口
 * --Double
 * --Unit                      ---Null
 *
 * ----Nothing
 *
 * outline: Scala类图 、for循环 、函数
 */
object SyllabusDemo1 {
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      print(s"${i}\t")
    }
    println()
    for (j <- 1 to 10 if j > 5) {
      print(s"${j}\t")
    }
    println()
    for (k <- 1 to 10; l = k * 2) {
      print(s"${l}\t")
    }
    println()
    val f = for (m <- 1 to 10) yield m + 1
    println(f)

    //匿名函数
    val func1: (String, Double) => Unit =
      (name: String, price: Double) => {
        println(s"姓名:${name}\t价格:${price}")
      }

    //偏函数
    val parFunc: (Any => String) = {
      case input: Int => {
        "Int"
      }
      case input: String => {
        "String"
      }
      case input: Any => {
        "Not Int OR String"
      }
    }

    judgeInputDataType(parFunc, "123123")
  }

  def normalFunc(name: String, price: Double) = println(s"${name}\t${price}")

  def highLevelFunc(name: String, price: Double, func: (String, Double) => Unit) = {
    func(name, price)
  }

  def judgeInputDataType(f: (Any => String), input: Any) = {
    println(f(input))
  }

}
