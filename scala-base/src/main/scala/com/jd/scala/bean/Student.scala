package com.jd.scala.bean

class Student(val income:Double,
              val outcome:Double)

object Student{
  def apply(income:Double,outcome:Double) = new Student(income,outcome)

  def unapply(stu:Student) = {
    if(stu.income > 30)
      Some((Math.sqrt(stu.income), Math.sqrt(stu.outcome)))
    else None
  }
}