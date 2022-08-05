package Xbss.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-25-11:40
 * @descirbe
 */
public  class ReadText {
    public static List<String> readLine(String fileName){
        ArrayList<String> setting = new ArrayList<>();
        String lineTxt = null;
        try {
            File myFile = new File(fileName);//通过字符串创建File类型对象，指向该字符串路径下的文件
            if (myFile.isFile() && myFile.exists()) { //判断文件是否存在
                InputStreamReader Reader = new InputStreamReader(new FileInputStream(myFile), "UTF-8");
                //考虑到编码格式，new FileInputStream(myFile)文件字节输入流，以字节为单位对文件中的数据进行读取
                //new InputStreamReader(FileInputStream a, "编码类型")
                //将文件字节输入流转换为文件字符输入流并给定编码格式
                BufferedReader bufferedReader = new BufferedReader(Reader);
                //BufferedReader从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
                //通过BuffereReader包装实现高效读取
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //buffereReader.readLine()按行读取写成字符串
                    setting.add(lineTxt);
//                    System.out.println(lineTxt);
                }
                Reader.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return setting;
    }
}
