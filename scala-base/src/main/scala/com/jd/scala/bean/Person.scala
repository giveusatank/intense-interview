package com.jd.scala.bean

class Person(val name:String,
             address:String,
             var desc:String ) {
    println("Person")

  def this( price:Int ) = {
    this(price.toString, "BEIJIGN" , "world~")
  }

  def this( flag : Boolean) = {
    this(666)
  }

  override def toString: String = {
    s"${this.name}\t${address}"
  }


}
