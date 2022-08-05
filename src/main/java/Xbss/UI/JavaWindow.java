package Xbss.UI;

import Xbss.UseJava;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-23-21:32
 * @descirbe
 */
public class JavaWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane ac=new AnchorPane();

        TextField text = new TextField();
        text.setLayoutX(400);
        text.setLayoutY(100);
        text.setPrefHeight(30);
        text.setPrefWidth(300);
        text.setFont(Font.font(15));
        text.setPromptText("请输入B站视频链接");


        TextArea area = new TextArea();
        area.setLayoutX(20);
        area.setLayoutY(50);
        area.setFont(Font.font(15));

        CheckBox checkBox1 = new CheckBox("mp3");
        CheckBox checkBox2 = new CheckBox("mp4");
        checkBox1.setIndeterminate(false);
        checkBox2.setIndeterminate(false);

        Button button = new Button("下载");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean mp3 = checkBox1.isSelected();
                boolean mp4 = checkBox2.isSelected();
                int i=0;
                if(mp3){
                     i=1;//只要mp3
                    if(mp4){
                        i=3;//全要
                    }
                }else {
                    i=2;//只要mp4
                }
                String s = text.getText();
                area.appendText(s);
                UseJava down = new UseJava();
                String[] strings = new String[4];
                try {
                    try {
                        strings = down.DownLoad(s,i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (XpathSyntaxErrorException e) {
                    e.printStackTrace();
                }
                for (String tip : strings) {
                    area.appendText("\n"+tip);
                }
                area.appendText("\n");
            }
        });
        Button clean = new Button("清空");
        clean.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                text.clear();
            }
        });

        HBox box = new HBox();
        HBox box2 = new HBox();
        box.getChildren().addAll(text,checkBox1,checkBox2,button,clean);
        box.setPadding(new Insets(10));
        box.setSpacing(10.0);
        box.setAlignment(Pos.BOTTOM_CENTER);

        ac.getChildren().addAll(box,area);

        Scene scene = new Scene(ac);

        primaryStage.setTitle("Bilibili下载器");
        primaryStage.setScene(scene);
        primaryStage.setHeight(320);
        primaryStage.setWidth(700);
//        primaryStage.getIcons().add(new Image("file:C:/Users/20557/Desktop/新建文件夹/简历.ico.jpg"));//加icon的话要用file：+绝对路径
        primaryStage.getIcons().add(new Image("file:E:\\Workspace\\Java_Workspace\\IDEAUltimate\\DownLoadBilibi\\src\\main\\resources\\ava.jpg"));//加icon的话要用file：+绝对路径
        primaryStage.show();

    }
}
