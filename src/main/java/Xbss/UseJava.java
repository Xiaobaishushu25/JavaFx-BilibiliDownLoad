package Xbss;

import Xbss.Utils.BytesToFile;
import Xbss.Utils.Merge;
import Xbss.Utils.ReadText;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-24-18:18
 * @descirbe
 */
public class UseJava {
    public String[] DownLoad(String url,int flag) throws XpathSyntaxErrorException, IOException {
        String pro_path = System.getProperty("user.dir");
        List<String> setting = ReadText.readLine(pro_path+"\\setting.txt");
        String save_filepath = setting.get(0);
        String UserAgent= setting.get(1);
        String[] tip=new String[4];
        tip[3]=pro_path;
        HashMap<String, String> mainHeaders = new HashMap<>();
        mainHeaders.put("User-Agent",UserAgent);
        mainHeaders.put("Referer",url);
        HttpResponse<String> response = Unirest.get(url).headers(mainHeaders).asString();
        String body = response.getBody();
        List list = GetNodes(body, "//title/text()");
        String title = list.get(0).toString();
        String[] split = title.split("_");
        title=split[0];
        //对文件名中的违规符号进行处理
        String[] ban={"\\","/",":","*","?","'","<",">","|"};
        for (String r : ban) {
            if (title.contains(r)){
                title = title.replace(r, "");
            }
        }
        String[] words = title.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for(String word:words){
            builder.append(word);
        }
        title=builder.toString();
        tip[0]=title;
        String main_url="";
        for (Object node : GetNodes(body, "//script/text()")) {
            if (node.toString().contains("playinfo")){
                main_url=node.toString();
                break;
            }
        }
//        System.out.println(main_url);
        String video_url="";
        video_url = StringUtils.substringBetween(main_url, "\"video\":[{\"id\":80,\"baseUrl\":\"", "\",\"base_url");
//        System.out.println(video_url);
        String audio_url="";
        audio_url = StringUtils.substringBetween(main_url, "\"audio\":[{\"id\":30280,\"baseUrl\":\"", "\",\"base_url");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36 Edg/101.0.1210.53");
        headers.put("Referer",url);
        tip[1]="Start DownLoad...";
        byte[] audio_bytes = Unirest.get(audio_url).headers(headers).asBytes().getBody();
//        BytesToFile.fileToBytes(audio_bytes,save_filepath,title+".mp3");
        BytesToFile.fileToBytes(audio_bytes,save_filepath,title);
        String command="ffmpeg -i "+save_filepath+title+" "+save_filepath+title+".mp3";
        System.out.println("转换为mp3："+command);
        Process exec = Runtime.getRuntime().exec(command);
        System.out.println("mp3已就绪");
//        File file1 = new File(save_filepath + title);
//        if (file1.exists())
//            file1.delete();
        String ffmpeg=pro_path+"\\ffmpeg-4.4.1-essentials_build\\ffmpeg-4.4.1-essentials_build\\bin\\ffmpeg.exe";
        if(flag == 1){
            tip[2]="Ok";
        }else if (flag==3){//删除mp4
            byte[] video_bytes = Unirest.get(video_url).headers(headers).asBytes().getBody();
            BytesToFile.fileToBytes(video_bytes,save_filepath,title+".mp4");
            Merge.MergeMpx(ffmpeg,save_filepath+title);
            File file = new File(save_filepath + title + ".mp4");
            if (file.exists()){
                System.out.println("进来删除mp4");
                file.delete();
            }
            tip[2]="Ok";
        }else {//删除mp3和mp4
            byte[] video_bytes = Unirest.get(video_url).headers(headers).asBytes().getBody();
            BytesToFile.fileToBytes(video_bytes,save_filepath,title+".mp4");
            Merge.MergeMpx(ffmpeg,save_filepath+title);
            File mp3_file = new File(save_filepath + title + ".mp3");
            if (mp3_file.exists()){
                mp3_file.delete();
                System.out.println("进来删除mp3");
            }
            File mp4_file = new File(save_filepath + title + ".mp4");
            if (mp4_file.exists()){
//                mp4_file.delete();
                System.out.println("进来删除mp4");
            }
            tip[2]="Ok";
        }
        return tip;
    }
    /**
     * @Author Xiaobaishushu
     * @Description ：在网页中查找某个结点（html的标签）
     * @Date 2022/5/26 18:57
     * @Param [html, xpath]
     * @return java.util.List
     **/
    public static List GetNodes(String html,String xpath) throws XpathSyntaxErrorException {
        //将传进来的string转换为document对象
        Document doc = Jsoup.parse(html);
        //document对象转为xml对象
        JXDocument document = new JXDocument(doc);
        //用xpath解析
        List<JXNode> nodes = document.selN(xpath);
        return nodes;
    }
}
