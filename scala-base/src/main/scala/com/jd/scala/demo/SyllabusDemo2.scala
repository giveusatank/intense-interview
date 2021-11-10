package com.jd.scala.demo

import com.jd.scala.bean.{Employee, Person, Student}

/**
 * lazy关键字、异常处理、模式匹配
 */
object SyllabusDemo2 {

  def main(args: Array[String]): Unit = {

    //添加lazy关键字的变量只会在真正使用时才会创建
    lazy val person = new Person(12313)
    println(person)

    //Scala不区分编译时、运行时异常
    try {
      println(1 / 0)
    } catch {
      case ex: RuntimeException => {
        ex.printStackTrace()
      }
      case ex: ArithmeticException => {
        ex.printStackTrace()
      }
    }

    //Scala模式匹配：匹配类型、匹配AnyVal、匹配样例类
    //模式匹配一般与case class集合使用，成功匹配的情况就是 样例类的unapply方法返回Some而不是None
    val m1 = 123
    val m2 = "666"
    m2 match {
      case k: String => {
        println(s"String\t${k}")
      }
      case j: Any => {
        println(s"Any\t${j}")
      }
    }

    val emp1 = Employee("AAA", 1111.11, "male")
    emp1 match {
      case Employee(x, y, z) => {
        println(x)
        println(y)
        println(z)
      }
    }

    val stu: Student = Student(81.0, 49.0)
    stu match {
      case Student(x, y) => {
        println(x)
        println(y)
      }
    }

  }
}
