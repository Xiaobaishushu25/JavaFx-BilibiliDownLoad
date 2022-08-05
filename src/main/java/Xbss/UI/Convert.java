package Xbss.UI;

import Xbss.Utils.GetImageView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-07-07-22:42
 * @descirbe
 */
public class Convert extends Application {
    public static String path="";
    public static String newPath="";
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextField field = new TextField();
        field.setPrefWidth(200);
        field.getStyleClass().add("cf-text-field");
        FileChooser chooser = new FileChooser();
        chooser.setTitle("mp3选择");
//        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3|img","*.jpg","*.mp3"));
        Button b = new Button("选择文件");
        b.getStyleClass().add("cf-primary-but");
//        b.setGraphic(new ImageView(new Image("img/转换-2.png")));
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = chooser.showOpenDialog(primaryStage);
                if (file!=null){
                    path=file.getAbsolutePath();
                    field.setText(path);
                    System.out.println(file.getAbsolutePath());
                }
            }
        });
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(250);
        textArea.setStyle("-fx-font-size: 16;"+"-fx-border-color: black");
        textArea.getStyleClass().addAll("cf-text-area");
        Button conv = new Button("转换");
//        conv.setGraphic(GetImageView.getImageView(null,"/src/main/resources/img/转换-2.png",26.0,26.0));
        conv.setGraphic(GetImageView.getImageView("img/转换-2.png",26.0,26.0));
        conv.getStyleClass().add("cf-info-but");
        conv.setOnAction(event -> {
            newPath=field.getText();
            File file = new File(newPath);
            if (file.exists()){
                textArea.appendText("文件已存在\n");
                return;
            }
            String command="ffmpeg -i "+path+" "+newPath;
            System.out.println(command);
            try {
                Process exec = Runtime.getRuntime().exec(command);
                BufferedReader br= new BufferedReader(new InputStreamReader(exec.getErrorStream()));
                String line=null;
                while ((line = br.readLine()) != null) {
                    textArea.appendText(line+"\n");
                    System.out.println(line);
                }
                try {
                    exec.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (file.exists()){
                    textArea.appendText("转换完成\n");
                    return;
                }else{
                    textArea.appendText("转换失败\n");
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        VBox box = new VBox(b,field,conv,textArea);
        box.setSpacing(20);
        box.setPadding(new Insets(50,50,50,50));
        box.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
        });
        box.setOnDragDropped(event -> {
            for (File file : event.getDragboard().getFiles()) {
                path = file.getAbsolutePath();
                field.setText(path);
            }
        });
        Scene scene = new Scene(box);
        //加载css资源的两种方式 一个是类加载器 resource：file:/F:/WorkSpace/Java/IdeaUltimate/DownLoadBilibi/target/classes/css/core.css
        URL resource = this.getClass().getClassLoader().getResource("css/core.css");
        scene.getStylesheets().add(resource.toExternalForm());
        //一个是直接添加
        scene.getStylesheets().add("css/color.css");
        File file = new File("css/color.css");
        System.out.println(file.toURI());
        System.out.println(file.exists());
        primaryStage.setScene(scene);
        primaryStage.setWidth(980);
        primaryStage.setHeight(580);
        primaryStage.setTitle("格式转换");
        primaryStage.getIcons().add(new Image("img/ava.jpg"));
        primaryStage.show();
    }
}
