package mp.scopa.grafa.ruleSystem.Module

/**
  *  规则对象接口
  * Created by mininglamp on 2017/3/16.
  */
case class Rule (ruleId:String, tableName:String, rypte:String ,desc :List[(String,String)], score:Double,
                 timeLength:String)
