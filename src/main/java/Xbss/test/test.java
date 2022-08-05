package Xbss.test;

import Xbss.Utils.ReadText;

import java.util.List;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-25-11:43
 * @descirbe
 */

public class test {
    public static void main(String[] args) {
//        ReadText txt = new ReadText();
//        List s = txt.readLine("G:\\Bilibili下载\\setting.txt");
        List s = ReadText.readLine(System.getProperty("user.dir")+"\\settings.txt");
        System.out.println(s.get(0));
        System.out.println(System.getProperty("user.dir"));
//        System.out.println(s.get(1));
    }
}
