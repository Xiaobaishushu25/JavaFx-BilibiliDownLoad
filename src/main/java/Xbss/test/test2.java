package Xbss.test;

import Xbss.Utils.Merge;
import Xbss.Utils.ReadText;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-25-12:37
 * @descirbe :测试融合
 */
public class test2 {
    public static void main(String[] args) throws IOException {
        String ffmpeg="F:\\Environment\\FFmpeg\\ffmpeg-4.4.1-essentials_build\\ffmpeg-4.4.1-essentials_build\\bin\\ffmpeg.exe";
        String file="G:\\自己的下载\\Bilibili下载\\下载\\【A-SOUL向晚】国王排名ED《Oz.》【翻唱】.mp3";
        String out="ffmpeg -i G:\\自己的下载\\Bilibili下载\\下载\\【A-SOUL向晚】国王排名ED《Oz.》【翻唱】.mp3 -ss 00:00:00 -t 192 G:\\自己的下载\\Bilibili下载\\下载\\test.mp3";
        Process process = Runtime.getRuntime().exec(out);
    }
}
