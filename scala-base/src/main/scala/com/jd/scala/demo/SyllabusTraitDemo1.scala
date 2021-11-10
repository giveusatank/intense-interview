package com.jd.scala.demo

trait Fly {
  abstract var name:String
  abstract def action()
  def shout(content:String) = println(content)
}

trait Run {
  abstract var name:String
  abstract def action()
  def shout(content:String) = println(content)
}

abstract class Bird ( val content:String ) {
  abstract var name:String
  abstract def action()
  def shout(content:String) = println(this.content)
}

abstract class Cat {
  abstract var name:String
  abstract def action()
  def shout(content:String) = println(content)
}

class Animal extends Fly  with Run {
  override def action(): Unit = println(this.name)
  abstract var name: String = "4444"
}

class WhiteCat extends Cat {
  override def action(): Unit = println(this.name)
  abstract var name: String = "666"
}
