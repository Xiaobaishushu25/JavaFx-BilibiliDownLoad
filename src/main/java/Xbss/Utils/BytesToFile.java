package Xbss.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-25-12:58
 * @descirbe ：用于将二进制bytes数据转化为文件
 */
public class BytesToFile {
    /**
     * @Author Xiaobaishushu
     * @Description :bytes是数据流，filepath是文件存储路径，fileName是文件存储名
     * @Date 2022/5/26 18:56
     * @Param [bytes, filePath, fileName]
     * @return void
     **/
    public static void fileToBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(filePath + fileName);
            System.out.println(file);
            if (!file.getParentFile().exists()){
                //文件夹不存在 生成
                System.out.println(" 文件夹不存在 生成");
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
