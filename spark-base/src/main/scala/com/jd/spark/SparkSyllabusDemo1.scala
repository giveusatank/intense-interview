package com.jd.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkSyllabusDemo1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test")
    val sc = new SparkContext(conf)

    //city  userid  price
    val sourceRDD: RDD[(String, String, Double)] =
      sc.textFile("/tmp/qz_test/spark1.txt")
        .map(x => {
          val arr = x.split(",")
          (s"${arr(0)}~${arr(1)}" , arr(2).toDouble)
        })
        .groupBy(_._1)
        .map( x => {
          ( x._1.split("~")(0),
            x._1.split("~")(1),
            x._2.foldLeft(0.0)( (cur,nxt) => cur+nxt._2)
          )
        })

    val top3RDD: RDD[(String, String, Double)] = sourceRDD
      .groupBy(_._1)
      .flatMap(x => {
        implicit val ord = new Ordering[(String,String,Double)] {
          override def compare(x: (String, String, Double),
                               y: (String, String, Double)): Int = {
            x._3.toInt - y._3.toInt
          }
        }
        x._2.toList.sorted.take(3)
      })

    println("~~~~~~~~~~~~~~~~~~~~~~~~")
    top3RDD.collect().foreach(println(_))
    println("~~~~~~~~~~~~~~~~~~~~~~~~")

    sc.stop()
  }
}
