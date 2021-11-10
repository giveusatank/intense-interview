package com.jd.scala.demo

//导入包下全部类
import com.jd.scala.bean.JDER

import java.util._
import java.util.{HashMap => javaHashMap}

//导入包下部分类
import java.util.{LinkedHashMap,LinkedList}

/**
 * 构造器、Scala包的引入、类型判断与转换、权限修饰符
 */
object SyllabusDemo3 {
  def main(args: Array[String]): Unit = {

    val number:Any = 123
    println(number.isInstanceOf[Int])
    val num:Int = number.asInstanceOf[Int]
    println(num)

    //导入类型并起别名
    val jhm = new javaHashMap[String,String]()
    jhm.put("name","AAA")

    //Scala的构造器分为：主构造器 、辅助构造器（最终一定要调用到主构造器）
    //主构造器属性定义：val 、缺省 、var
    //辅助构造器定义：def this() = {...}
    //类体中所有方法定义以外的部分都算在"主构造方法内"
    val jder = new JDER("AAA","AAA31",1001,2222.22)
    println(jder)

    //Scala权限修饰符：public、缺省、protected、private、private[this]
    //private修饰 就是private final ，private[this]修饰也是private final 但是不会生成 private同名方法，
    //即方法内只能通过this.属性访问到

  }
}
