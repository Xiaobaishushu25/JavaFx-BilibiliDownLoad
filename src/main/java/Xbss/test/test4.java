package Xbss.test;

import java.io.File;
import java.io.IOException;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-07-07-23:15
 * @descirbe
 */
public class test4 {
    public static void main(String[] args) throws IOException {
        File file = new File("G:\\待处理文件");
        for (File listFile : file.listFiles()) {
            if (listFile.isFile()){
                String path = "G:\\待处理文件\\add\\"+ listFile.getName();
                String absolutePath = listFile.getAbsolutePath();
//                String name = listFile.getName();
//                System.out.println(listFile.getName());
//                System.out.println(listFile.getAbsolutePath());
                String command="ffmpeg -i "+absolutePath+" "+path;
                System.out.println(command);
                Process exec = Runtime.getRuntime().exec(command);
            }
        }

    }
}
