package mp.scopa.grafa.ruleSystem.core

import scala.collection.mutable
/**
  * sql 运行器，生成sql ，优化sql，运行sql
  * Created by mininglamp on 2017/3/16.
  */
class SQLExecutor {
  var sqlOption= mutable.HashMap[String,List[(String,String)]]// tableName,List((where,""))

  /**
    * 加载sql 初始化信息
    *
    */
  def init(): Unit ={

  }


  /**
    * 根据sql option生成sql 语句
    * 根据 外部输入生成sql
    */
  def genericSQL(): Unit ={

  }


  /**
    * 注册udf ，udaf，udtf
    */
  def registerUDF(): Unit ={

  }


  /**
    * sql 优化器，暂时对 广播改为join ，
    *                对 热点结点，进行split成连个stage
    */
  def sqlOptimizer(): Unit ={

  }


  /**
    * 拆成连个阶段执行sql
    */
  def split2Stage(): Unit ={

  }
}
