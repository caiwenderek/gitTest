package mp.scopa.grafa.ruleSystem.schedule

import java.io.{File, FileWriter}

import com.typesafe.config.ConfigFactory
import mp.scopa.grafa.model.rulesys.RuleSystem

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.io.Source

/**
	*
	* Created by yxshao on 9/12/16.
	*/
class RSSchedule(appname: String, isInc: Boolean, tasktype: String) extends AbstractSchedule(appname, isInc) {

  var runCount: Int = 0
	var pathPrefix: String = _
  var confname: String = _



  override def init(): Unit = {
    super.init()
    DBLoader.init(sc,sqlc)//
    /*confname = appConf.getString("grafa.app.rulesystem.confname")
    val cacheDir = sc.hadoopConfiguration.get("grafa.conadb.progress.cachedir", ".")
	  pathPrefix = cacheDir+"/"+confname+"_"+tasktype

    if(!isFailedRetry()) {
      val writer = new FileWriter(pathPrefix + ".checkpoint", false)
      writer.write("running\n")
      writer.close()

      //clean progress checkpoint and start a new run.
      println("Begin a new run!!")
      val f = new File(pathPrefix+ ".finish")
      if(f.exists()) f.delete()
    }
    else {
      println("Begin a failedAndRetry run!!")
    }

    //read the running number
    try {
      val lines = Source.fromFile(pathPrefix + ".prog", "utf-8").getLines()
      this.runCount = lines.next.toString.toInt
    }catch{
      case e:Exception => println("[WARNING]"+ pathPrefix  + ".prog does not exist.")
    }*/
  }

	override def success(): Unit = {
		super.success()

		val f = new File( pathPrefix +".checkpoint")
		if(f.exists()) f.delete()

		val writer2 = new FileWriter(pathPrefix + ".prog", false)
		val cnt = this.runCount + 1
		writer2.write(cnt + "\n")
		writer2.close()
	}

	/**
		* support running
		*   1, computeRule
		*   2, mergeRule
		*   3, computeFeature
		*   4, trainModel
		*   5, computeScore
		*   6, uploadScore
		*/
	def run(): Boolean = {

    val rulesys = new RuleSystem(sc,sqlc)

		tasktype match {
			case "computeRule" => rulesys.run(Array("run",pathPrefix,confname))
      case "mergeRule" => rulesys.run(Array("merge",pathPrefix,confname))
      case "computeFeature" => rulesys.run(Array("evaluate",pathPrefix,confname))
      case "trainModel" => rulesys.run(Array("train",pathPrefix,confname))
      case "computeScore" => rulesys.run(Array("tag",pathPrefix,confname))
      case "uploadScore" => rulesys.run(Array("update",pathPrefix,confname))
			case _ => {
				println("unsupported app: " + appname + "\n")
				return false
			}
		}
		isSuccess()
	}

	def isSuccess(): Boolean = {
		val finishedList = mutable.HashSet[String]()
    val config = ConfigFactory.load(confname+".conf")

 		tasktype match {
      case "compuuteFeature" => {
        try {
          for (line <- Source.fromFile(pathPrefix+ ".finish", "utf-8").getLines()) {
            finishedList.add(line)
          }
        }catch{
          case e:Exception => println("[WARNING]"+pathPrefix+ ".finish does not exist.")
        }

        for (line <- config.getStringList("grafa.rulesystem.evaluate.ruleset").asScala) {
          if (!finishedList.exists(x => x.equals(line))) {
            return false
          }
        }
      }
      case "computeRule" => {
        try {
          for (line <- Source.fromFile(pathPrefix+ ".finish", "utf-8").getLines()) {
            finishedList.add(line)
          }
        }catch{
          case e:Exception => println("[WARNING]"+pathPrefix+ ".finish does not exist.")
        }

        for (line <- config.getStringList("grafa.rulesystem.run.datasource").asScala) {
          if (!finishedList.exists(x => x.equals(line))) {
            return false
          }
        }
      }
      case _ => {
        try {
          for (line <- Source.fromFile(pathPrefix + ".finish", "utf-8").getLines()) {
            finishedList.add(line)
          }
        }catch{
          case e:Exception => println("[WARNING]"+ pathPrefix + ".finish does not exist.")
        }

        if (!finishedList.exists(x => x.equals(tasktype))) {
            return false
        }
      }
		}

		true
	}

  def isFailedRetry(): Boolean = {
    val f = new File(pathPrefix +".checkpoint")
    f.exists()
  }



  /**
    * 预处理清洗阶段，生成逻辑上的 table。
    *  此时已经找到了符合rule下的记录，并对窗口内的数据进行聚合。并根据时间窗口生成每个窗口内符合要求的记录数，最近一条记录
    * eg              rule1          ,rule2 ...
    *      id (count1,lastRecord1)   (count1,lastRecord1)  ...
    */
  def preProcess()={

  }


  /**
    * 根据机器学习，生成规则的score ，后续完善
    */
  def ml2score()={

  }

  /**
    * 将数据进行打分，
    */
  def mergeProcess()={

  }
}
