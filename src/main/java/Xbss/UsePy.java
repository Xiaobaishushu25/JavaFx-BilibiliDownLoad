package Xbss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-23-21:30
 * @descirbe
 */
public class UsePy {
    public static void main(String[] args) {
        System.out.println("请输入B站视频链接:");
        Scanner input = new Scanner(System.in);
        String url=input.nextLine();
        try {
            String compiler="E:\\ANACONDA\\Main\\python.exe";
            String python="E:\\Workspace\\PyCharm\\GetBiliBili\\Final.py";
            String commond=compiler+" "+python+" "+url;
            Process proc = Runtime.getRuntime().exec(commond);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String[] test(String url,int i){
        try {
            String compiler="E:\\ANACONDA\\Main\\python.exe";
            String python="E:\\Workspace\\PyCharm\\GetBiliBili\\Final.py";
            String commond=compiler+" "+python+" "+url+" "+i;
            Process proc = Runtime.getRuntime().exec(commond);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            String[] out=new String[4];
            int m=0;
            while ((line = in.readLine()) != null) {
                out[m++]=line;
//                System.out.println(line);
            }
            for (String o :
                    out) {
                System.out.println(o);
            }
            in.close();
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return out;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
