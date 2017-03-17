package mp.scopa.grafa.ruleSystem

import mp.scopa.grafa.ruleSystem.schedule._


/**
  * Created by yxshao on 9/12/16.
  */
object GrafaRunner {

  val usage =
    """
         Usage: [--appname <stats | rulesystem | loadertest> --isInc <false|true>]
    		"""

  type optionMap = Map[Symbol, Any]

  def main (args: Array[String]) {
    val options = parseArgs(Map(), args.toList)

    if (!options.get('appname).isDefined) {
      println("No APPNAME is defined!!!")
      sys.exit(1)
    }

    var app: AbstractSchedule = null

    val appname = options.getOrElse('appname, "default").toString
    val isInc = options.getOrElse('isInc, "false").toString.toBoolean
    appname match {
      case "rulesystem" => app = new RSSchedule(appname, isInc, options.getOrElse('tasktype, "not defined").toString)
      case "stats" => //app = new DistCompApp(appname, isInc)
      case "loadertest" => // app = new DataIncLoaderApp(appname, isInc)
      case "gstats" => //app = new StatisticApp(appname, isInc)
      case _ => {
        println("unsupported app: " + appname + "\n" + usage)
        System.exit(-1)
      }
    }
    app.init()
    if (app.run()) {
      println(s"app ${appname} executes successed.")
      app.success()
    }
    else
      app.fail()
  }

  def parseArgs(map:optionMap, args:List[String]): optionMap = {
    args match {
      case Nil => map
      case "--appname"::value::tail => parseArgs(map ++ Map('appname -> value), tail)
      case "--isInc"::value::tail => parseArgs(map ++ Map('isInc -> value), tail)
      case "--tasktype"::value::tail => parseArgs(map ++ Map('tasktype -> value), tail)
      case option::tail => println("unknown option: "+option+"\n"+usage)
        sys.exit(1)
    }
  }



}
