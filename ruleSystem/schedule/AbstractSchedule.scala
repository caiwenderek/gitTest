package mp.scopa.grafa.ruleSystem.schedule

import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import scala.collection.JavaConverters._


/**
  * Created by mininglamp on 2017/3/16.
  */
abstract  class AbstractSchedule (appName: String, isInc: Boolean) {

  @transient private[app] var sc: SparkContext = _
  @transient private[app] var sqlc: SQLContext = _
  private[app] val appConf = ConfigFactory.load(s"${appName}.conf")

  /**
    * 读取配置文件，次配置文件为与job相关的参数，优化参数等, 读取业务文件的路径
    *
    */
  def init(): Unit = {
    val conf = new SparkConf().setAppName(appName)
    sc = new SparkContext(conf)
    sqlc = new SQLContext(sc)
  }

  /**
    *  选取执行阶段，运行
    * @return
    */
  def run(): Boolean


  /**
    * 当前任务运行状态
    */
  def success(): Unit = {
    //update the checkpoint
//    NewConaDB.success(sc, sqlc, appName, isInc)
  }

  /**
    *
    */
  def fail(): Unit = {
//    NewConaDB.fail(sc, sqlc, appName, isInc)
  }
}
