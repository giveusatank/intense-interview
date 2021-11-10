package com.jd.scala.bean

class JDER (private val erp:String,
            private[this] val name:String,
            val number:Int,
            val salary:Double) {

  def this(erp:String,number:Int,salary:Double) = {
    this(erp,"AAA",1001,2222.22)
  }

  def this(erp:String) = {
    this(erp,1001,2222.22)
  }

  override def toString: String = {
    s"${erp}\t${name}\t${number}\t${salary}"
  }
}
