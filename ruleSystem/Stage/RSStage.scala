package mp.scopa.grafa.ruleSystem.Stage

import mp.scopa.grafa.ruleSystem.Module.Rule
import org.apache.commons.configuration.Configuration

/**
  * Created by mininglamp on 2017/3/16.
  * 生成小的执行阶段，提供schedule 进行组合
  */
class RSStage extends AbstractStage {
  /**
    * 读取json 业务配置文件，eg: conna ，hart 配置文件， rule规则文件（表）
    *
    */
  override def loadJsonBizParam(conf: Configuration): Unit = ???

  /**
    * 合并最近一个月的数据。一次跑完，当月之前的窗口数据已经合并完成，只需合并当前月
    */
  override def mergeRecentOneMonth(): Unit = ???

  /**
    * 以rule 为核心，执行处理，该方法是sql形成的外围的执行逻辑
    *
    */
  override def runDependRule(): Unit = ???

  /**
    * 以每个输入源的表为核心进行处理，后续与runDependRule比较效率
    *
    */
  override def runDependTable(): Unit = ???

  /**
    * 加载cona 和harts的文件或者表，并做初始的过滤，提取所需字段，并注册表。
    */
  override def loadConaAndHartsData(): Unit = ???

  /**
    * 中间执行核心部分
    *
    */
  override def execute(): Unit = ???

  /**
    * 最后的清理操作,eg 清除cache文件等
    *
    */
  override def clean(): Unit = ???

  /**
    * 加载外部的id ，并计算id的数量，当达到一定阈值时调用 optimizer 进行优化
    * 修改sql 变成 join 操作，当出现了数据倾斜时，对sql 再进行拆分，变成连个stage
    *
    * @param conf
    * @param path
    * @return
    */
override def loadExtractId(conf: Configuration, path: String): (Seq[String]) => Set[String] = super.loadExtractId(conf, path)

  /**
    * 校验输入数据是否存在，以及输出数据，并更新rule 中查询的范围
    *
    * @param conf
    * @param rulesMap
    * @return
    */
  override def checkPath(conf: Configuration, rulesMap: Map[String, Rule]): Map[String, Rule.type] = super.checkPath(conf, rulesMap)
}
