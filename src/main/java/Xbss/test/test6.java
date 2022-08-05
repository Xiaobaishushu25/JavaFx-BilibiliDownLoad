package Xbss.test;

import java.io.File;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-07-22-12:31
 * @descirbe
 */
public class test6 {
    public static void main(String[] args) {
        File file = new File("G:\\自己的下载\\Bilibili下载\\下载\\【A-SOUL乃琳】《红颜如霜》【直播剪辑】");
        if (file.exists()){
            System.out.println(1111);
            file.delete();
        }
    }
}
