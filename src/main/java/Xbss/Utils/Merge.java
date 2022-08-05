package Xbss.Utils;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-25-12:24
 * @descirbe :java调用cmd运行ffmpeg合成视频和音频
 */
public class Merge {
    /**
     * @Author Xiaobaishushu
     * @Description ：ffmpeg要传入绝对路径，精确到ffmpeg.exe
     * @Date 2022/5/26 19:02
     * @Param [ffmpeg, filepath]
     * @return void
     **/
    public static String MergeMpx(String ffmpeg,String filepath) throws IOException {
        String file1=filepath+".mp3";
        String file2=filepath+".mp4";
        ArrayList<String> command = new ArrayList<>();
        command.add(ffmpeg);
        command.add("-i");
        command.add(file2);
        command.add("-i");
        command.add(file1);
        command.add("-vcodec copy");
        command.add("-acodec copy");
        command.add(filepath+"合成.mp4");
        StringBuilder builder2 = new StringBuilder();
        for (String c : command) {
            builder2.append(c+" ");
        }
        System.out.println(builder2.toString());
        //java调用cmd
        Process process = Runtime.getRuntime().exec(builder2.toString());
        BufferedReader br= new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line=null;
        String msg=null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            msg=line;
        }
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
