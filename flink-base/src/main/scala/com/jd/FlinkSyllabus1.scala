package com.jd

import org.apache.flink.streaming.api._
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment, createTypeInformation}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.table.api.{EnvironmentSettings, Slide, Table, Tumble}
import org.apache.flink.table.api.scala._
import org.apache.flink.types.Row


object FlinkSyllabus1 {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val bsSettings = EnvironmentSettings.newInstance()
      .useBlinkPlanner()
      .inStreamingMode()
      .build()

    val streamTableEnv = StreamTableEnvironment.create(env, bsSettings)
    val dataStream: DataStream[DataItem2222] =
      env.socketTextStream("localhost", 9999)
        .filter(x => x.contains(","))
        .map(x => {
          DataItem2222(
            x.split(",")(0),
            x.split(",")(1),
            x.split(",")(2).toLong)
        })
        .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[DataItem2222](Time.seconds(1)) {
          override def extractTimestamp(t: DataItem2222): Long = t.ts.toLong * 1000L
        })

    /*val table: Table = streamTableEnv.fromDataStream(dataStream)
    streamTableEnv
      .createTemporaryView(
        "tempTable" ,
        table ,
        'name ,
        'city ,
        'ts.rowtime as 'ttt )

    val query_sql =
      """
        |SELECT city,
        |       COUNT(name)
        |from tempTable
        |group by city , tumble(ttt, interval '10' second )
      """
      */

    val dataTable: Table = streamTableEnv
      .fromDataStream(dataStream, 'name, 'city, 'timestamp.rowtime)




    val dataTable2: Table = streamTableEnv
      .fromDataStream(dataStream, 'name, 'city as 'city2, 'timestamp.rowtime)

    val resultTable1: Table = dataTable
      .window(Slide over 10.seconds every 5.seconds on 'timestamp as 'tw)
      .groupBy('city, 'tw)
      .select('city, 'name.count as 'nc1)

    val resultTable2: Table = dataTable2
      .window(Slide over 10.seconds every 5.seconds on 'timestamp as 'tw)
      .groupBy('city2, 'tw)
      .select('city2, 'name.count as 'nc2)

    val resultTable3 = resultTable1.
      join(resultTable2, 'city === 'city2)
      .select('city, 'city2, 'nc1, 'nc2)

    streamTableEnv
      .createTemporaryView(
        "mainTables" ,
        dataStream ,
        'name ,
        'city ,
        'ts.rowtime)


    streamTableEnv
      .createTemporaryView(
        "otherTables" ,
        dataStream ,
        'name ,
        'city ,
        'ts.rowtime)


    val q_sql =
      """
        |SELECT aaa.city,
        |       aaa.cnt1,
        |       bbb.cnt2
        |
        |from (
        |SELECT
        |     city,
        |     COUNT(name) as cnt1
        |FROM mainTables
        |GROUP BY TUMBLE(ts, INTERVAL '10' SECOND),
        |         city
        |) aaa
        |
        |left join
        |
        |(
        |SELECT
        |     city,
        |     COUNT(name) as cnt2
        |FROM otherTables
        |GROUP BY TUMBLE(ts, INTERVAL '10' SECOND),
        |         city
        |) bbb
        |
        |on aaa.city = bbb.city
        |""".stripMargin

    val tableSql: Table = streamTableEnv.sqlQuery(q_sql)

    val q2_sql =
      """
        |
        |
        |
        |""".stripMargin


    //val tableSql = streamTableEnv.sqlQuery(query_sql)
    val tableres = tableSql.toRetractStream[Row]
    tableres.filter(_._1).print()

    env.execute("flinksql")
  }
}
