package com.jd.spark

import org.apache.hadoop.util.StringUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkSyllabusDemo2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("SparkSyllabusDemo2")
    val sc = new SparkContext(conf)

    // dt userId  2021-11-11 11:11:11,u1001
    val dt_userId_pv_rdd = sc
      .textFile("/tmp/qz_test/spark2.txt")
      .map(line => {
        val arr = line.split(",")
        (s"${arr(0).substring(0,10)}~${arr(1)}",1)
      })
      .groupBy(_._1)
      .map(item => {
        val arr = item._1.split("~")
        //dt userid pv
        (arr(0),arr(1),item._2.size)
      })

    val top3RDD: RDD[(String, String)] = dt_userId_pv_rdd
      .groupBy(_._1)
      .flatMap( items => {
        implicit val ord = new Ordering[(String,String,Int)] {
          override def compare(x: (String, String, Int),
                               y: (String, String, Int)): Int = {
            y._3 - x._3
          }
        }
        items._2.toList.sorted.take(3)
      })
      .map(res => (res._1 , res._2))

    println("~~~~~~~~~~~~~~~~~~~~~~~")
    top3RDD.collect().foreach(println(_))
    println("~~~~~~~~~~~~~~~~~~~~~~~")

    sc.stop()
  }
}
