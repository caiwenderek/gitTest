package mp.scopa.grafa.ruleSystem.ProcessTracker

import org.apache.log4j.Logger

/**
  * Created by mininglamp on 2017/3/16.
  */
class RSProcessTracker  extends TProcessTracker {

  override var logger: Logger = Logger.getLogger(classOf[RSProcessTracker])

  /**
    * 应用于进入 类，对象 ，第一次调用情况
    *
    * @param level
    */
  override def before(c: Class, level: Byte): Unit = {

  }

  /**
    * 方法调用时使用日志
    *
    * @param c
    * @param method
    * @param level
    */
  override def makeLog(c: Class, method: String, level: Byte): Unit = {

  }

  /**
    * 最后执行的输出的日志
    *
    * @param c
    * @param method
    * @param level
    */
  override def end(c: Class, method: String, level: Byte): Unit = {

  }
}
