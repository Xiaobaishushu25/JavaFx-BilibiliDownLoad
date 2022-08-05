package Xbss.Utils

/**
 * @author  Xbss
 * @create 2022-08-04-19:48
 * @version  1.0
 * @describe
 */
class HigherFunctions(var value: Int) {
    var oldValue:Int =0
    var changeAction:(oldValue:Int,newValue: Int)->Unit={oldValue,newValue-> println("旧值是$oldValue  新值是$newValue") }
    fun set(value: Int){
        oldValue=this.value
        this.value=value
        changeAction(oldValue,value)
    }
}
fun main() {
    val mySimpleBean =HigherFunctions(0)
    mySimpleBean.changeAction={ _,newValue->
        println("自定义的方法打印新值$newValue")
    }
    mySimpleBean.set(5)
    mySimpleBean.set(7)
    mySimpleBean.set(2)
    mySimpleBean.set(1)
}