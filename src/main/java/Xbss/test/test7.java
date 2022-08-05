package Xbss.test;

import Xbss.Utils.GetNodes;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import kong.unirest.Unirest;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-07-31-22:26
 * @describe
 */
public class test7 {
    public static void main(String[] args) throws XpathSyntaxErrorException {
        String body = Unirest.get("https://www.1biqug.com/searchbook.php?keyword=长夜").asString().getBody();
        for (Object getNode : GetNodes.GetNodes(body, "//div[@id='main']/div/ul/li")) {
//        for (Object getNode : GetNodes.GetNodes(body, "//div/div/ul/li/*[1]")) {
            System.out.println(getNode);
        }

    }
}
