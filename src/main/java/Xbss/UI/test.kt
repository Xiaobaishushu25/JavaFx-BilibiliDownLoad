package Xbss.UI

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author  Xbss
 * @create 2022-08-05-11:37
 * @version  1.0
 * @describe
 */
fun main() {
    var command="ffmpeg -i G:\\A自己的下载\\Bilibili下载\\下载\\【翻唱】姗姗（直播剪辑）  G:\\A自己的下载\\Bilibili下载\\下载\\【翻唱】姗姗（直播剪辑）.mp3"
    val exec = Runtime.getRuntime().exec(command)
    BufferedReader(InputStreamReader(exec.errorStream)).apply {
        var msg:String?
//        while ((this.readLine().also { msg = it })!=null){
//        while ((msg=this.readLine())!=null){
//
//        }
    }
}