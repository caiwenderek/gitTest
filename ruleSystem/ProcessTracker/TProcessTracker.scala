package mp.scopa.grafa.ruleSystem.ProcessTracker

import org.apache.log4j.{Logger,Level}


/**
  * Created by mininglamp on 2017/3/16.
  */
trait TProcessTracker {
  var logger:Logger =_

  /**
    * 应用于进入 类，对象 ，第一次调用情况
    * @param level
    */
  def before(c:Class ,level :Byte): Unit


  /**
    * 方法调用时使用日志
    * @param c
    * @param method
    * @param level
    */
  def makeLog(c:Class ,method :String, level :Byte)


  /**
    * 最后执行的输出的日志
    * @param c
    * @param method
    * @param level
    */
  def end(c:Class ,method :String,level:Byte): Unit

}
