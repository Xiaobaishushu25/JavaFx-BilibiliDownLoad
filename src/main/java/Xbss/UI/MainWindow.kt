package Xbss.UI

import Xbss.DownLoadService
import Xbss.ParseService
import Xbss.Utils.ReadText
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import java.awt.Desktop
import java.io.File

/**
 * @author  Xbss
 * @create 2022-07-21-23:10
 * @version  1.0
 * @descirbe
 */
class MainWindow:Application() {
    override fun start(stage: Stage) {
        lateinit var parseService:ParseService
        lateinit var service:DownLoadService
        val loader = FXMLLoader()
        loader.location=javaClass.getResource("/fxml/MainWindow.fxml")
        val root = loader.load<Parent>()
        val map = loader.namespace
        val urltext = (map["urltext"] as TextField ).apply { styleClass.addAll("cf-text-field") }
        val titlelabel = (map["titlelabel"] as TextField).apply{ styleClass.addAll("cf-info-label") }
        val msg = (map["msg"] as Label).apply {
            styleClass.addAll("cf-warn-label")
            style="-fx-text-fill:#FFA500;"+"-fx-font-size:15px"
        }
        val mp3Check = (map["mp3"] as CheckBox).apply { styleClass.addAll("cf-check-box") }
        val mp4Check = (map["mp4"] as CheckBox).apply{  styleClass.addAll("cf-check-box") }
        val parse = (map["parse"] as Button).apply {
            styleClass.addAll("cf-info-but","round")
            style="-fx-font-size:12px"
            setOnAction {
                parseService = ParseService(urltext.text)
                parseService.start()
                parseService.titleProperty().addListener { _,_,newValue-> titlelabel.text=newValue }
                parseService.messageProperty().addListener { _,_,newValue-> msg.text=newValue }
            }
        }
        val down = (map["down"] as Button).apply {
            styleClass.addAll("cf-success-but")
            isDisable=true
            setOnAction {
                val mp3: Boolean = mp3Check.isSelected
                val mp4: Boolean = mp4Check.isSelected
                var i = 0
                if (mp3) {
                    i = 1 //只要mp3
                    if (mp4) {
                        i = 3 //全要
                    }
                } else {
                    i = 2 //只要mp4
                }
//                service = DownLoadService(urltext.text, i)
                parseService.value[0]=titlelabel.text
                service = DownLoadService(parseService.value, i)
                service.start()
                service.titleProperty().addListener { _,_,newValue-> titlelabel.text=newValue }
                service.messageProperty().addListener { _,_,newValue-> msg.text=newValue }
            }
        }
        mp3Check.selectedProperty().addListener { _,_,newValue->
            if (newValue)
                down.isDisable=false
            else{
                if (!mp4Check.isSelected)
                    down.isDisable=true
            }
        }
        mp4Check.selectedProperty().addListener { _,_,newValue->
            if (newValue)
                down.isDisable=false
            else{
                if (!mp3Check.isSelected)
                    down.isDisable=true
            }
        }
        val clear = (map["clear"] as Button).apply {
            styleClass.addAll("cf-danger-but")
            setOnAction {
                urltext.clear()
                titlelabel.text=""
                msg.text=""
                mp3Check.isSelected=false
                mp4Check.isSelected=false
            }
        }
        val open = (map["open"] as Label).apply {
            styleClass.addAll("Xbss-label-url")
            setOnMouseClicked {
                Desktop.getDesktop().open( File(ReadText.readLine(System.getProperty("user.dir") + "\\setting.txt")[0].dropLast(1)));
            }
        }
        stage.apply {
            icons.addAll(Image("img/bilibili.png"))
            this.scene= Scene(root).apply { stylesheets.addAll("css/core.css","css/color.css","css/Xbss.css") }
            title="哔哩哔哩下载V3.0"
            show()
        }
    }
}
fun main() {
    Application.launch(MainWindow::class.java)
}