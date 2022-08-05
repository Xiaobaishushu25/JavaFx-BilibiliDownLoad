package Xbss

import Xbss.Utils.*
import javafx.concurrent.Service
import javafx.concurrent.Task
import kong.unirest.Unirest
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.regex.Pattern

/**
 * @author  Xbss
 * @create 2022-07-21-22:18
 * @version  1.0
 * @descirbe :一个文件写了两个类....
 */
/**
 * TODO :返回一个list，依次是标题、音频地址、视频地址、资源url
 *
 * @constructor
 * TODO
 *
 * @param url
 */
class ParseService(url:String):Service<MutableList<String>>(){
    var url=url
    override fun createTask(): Task<MutableList<String>> {
        val task = object : Task<MutableList<String>>() {
            override fun call() :MutableList<String> {
                val parseMsg = mutableListOf<String>()
//                val saveFilepath = ReadText.readLine(System.getProperty("user.dir") + "\\setting.txt")[0]
                this.updateMessage("正在解析资源")
                val body = Unirest.get(url).asString().body
                val title = GetNodes.GetNodes(body, "//title/text()")[0].toString().run {
                    var title = this.split("_")[0]
                    val ban = arrayOf("\\"," ","/", ":", "*", "?", "'", "<", ">", "|")
                    for (r in ban) {
                        if (title.contains(r))
                            title = title.replace(r, "")
                    }
                    java.lang.StringBuilder().apply {
                        for (word in title.split("\\s+"))
                            this.append(word)
                    }.toString()
                }
                parseMsg.add(title)
                this.updateTitle(title)
                var mainUrl=""
                for (node in GetNodes.GetNodes(body,"//script/text()")){
                    if (node.toString().contains("playinfo")){
                        mainUrl=node.toString()
                        break
                    }
                }
                var audioUrl=""
                Pattern.compile("(?<=\"audio\":\\[\\{\"id\":.{1,8},\"baseUrl\":\").*?(?=\",\"base_url)").matcher(mainUrl).apply {
                    while (this.find())
                        audioUrl=this.group()
                }
                parseMsg.add(audioUrl)
                var videoUrl=""
                Pattern.compile("(?<=\"video\":\\[\\{\"id\":.{1,3},\"baseUrl\":\").*?(?=\",\"base_url)").matcher(mainUrl).apply {
                    while (this.find())
                        videoUrl=this.group()
                }
                parseMsg.add(videoUrl)
                parseMsg.add(url)
                this.updateMessage("资源解析完成")
                return parseMsg
            }
        }
        return task
    }
}
//class DownLoadService(url:String,flag:Int):Service<List<String>>() {
//class DownLoadService(val parseMsg:MutableList<String>,flag:Int):Service<List<String>>() {
class DownLoadService(val parseMsg:MutableList<String>,flag:Int):Service<Int>() {
    var flag=flag
    lateinit var saveFilepath:String
    val observable=HigherFunctions(0)
//    override fun createTask(): Task<List<String>> {
    override fun createTask(): Task<Int> {
//        val task = object : Task<List<String>>() {
        val task = object : Task<Int>() {
//            override fun call() : List<String> {
            override fun call() : Int {
//                val fileDelete = mutableListOf<String>()
                var fileToDelete:String?=null
                saveFilepath = ReadText.readLine(System.getProperty("user.dir") + "\\setting.txt")[0]
                val title = parseMsg[0]
                val audioUrl=parseMsg[1]
                val videoUrl=parseMsg[2]
                this.updateMessage("正在下载音频资源")
                val headers = hashMapOf<String, String>(Pair("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36 Edg/101.0.1210.53"),
                    Pair("Referer",parseMsg[3])
                )
                val errorCode = mutableMapOf<Int, String>(Pair(1, "下载成功"), Pair(2, "音频转换失败"), Pair(3, "视频转换失败"),Pair(4,"正在清理"))
                observable.changeAction={_,newValue->
                    println("自定义的方法打印新值$newValue")
                    this.updateMessage(errorCode[newValue])
                    fileToDelete?.let {
                        File(it).apply {
                            if (this.exists())
                                this.delete()
                        }
                    }
//                    if (newValue==1){
//                        this.updateMessage("下载成功")
//                        File("$saveFilepath$title").apply {
//                            if (this.exists())
//                                this.delete()
//                        }
//                    }
                }
                Unirest.get(audioUrl).headers(headers).asBytes().body.apply {
                    BytesToFile.fileToBytes(this, saveFilepath, title)
                    var command= "ffmpeg -i $saveFilepath$title  $saveFilepath$title.mp3"
                    println(command)
                    val exec = Runtime.getRuntime().exec(command)
//                    BufferedReader(InputStreamReader(exec.errorStream)).apply {
//                        var str:String?
//                        str=this.readLine()
//                        while (str!=null){
//                            println(str)
//                            str=this.readLine()
//                        }
//                    }
                    BufferedReader(InputStreamReader(exec.errorStream)).apply {
                        if (this.readLines().takeLast(1)[0].contains("audio")){
                            fileToDelete="$saveFilepath$title"
                            observable.set(1)
                        }else{
                            observable.set(2)
                        }
                    }
                }
                var msg:String
                if (flag==1){
//                    this.updateMessage("下载成功")
//                    fileDelete.add("$saveFilepath$title")
                }else{
                    this.updateMessage("正在下载视频资源")
                    Unirest.get(videoUrl).headers(headers).asBytes().body.apply {
                        BytesToFile.fileToBytes(this, saveFilepath, "$title.mp4")
                        msg = Merge.MergeMpx("ffmpeg", saveFilepath + title)
//                        if (msg.contains("audio"))
//                            observable.set(1)
//                        else
//                            observable.set(3)
                    }
                    if (msg.contains("audio")){
                        fileToDelete="$saveFilepath$title.mp4"
                        if (flag==3){
                            observable.set(1)
                        }else{
                            observable.set(4)
                            fileToDelete="$saveFilepath$title.mp3"
                            observable.set(1)
                        }
                    }
                    else
                        observable.set(3)
//                    this.updateMessage("下载成功")
//                    if (flag==3){
////                        fileDelete.add("$saveFilepath$title")
////                        fileDelete.add("$saveFilepath$title.mp4")
//                    }else{
//
//                        println("全部删除")
//                        fileDelete.add("$saveFilepath$title")
//                        fileDelete.add("$saveFilepath$title.mp4")
//                        fileDelete.add("$saveFilepath$title.mp3")
//                    }
                }
                return 0
            }
        }
        return task
    }
}

