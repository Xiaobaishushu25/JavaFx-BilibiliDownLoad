package Xbss.test;

import Xbss.Utils.GetNodes;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import javafx.concurrent.Task;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-07-19-22:22
 * @descirbe
 */
public class test5 {
    public static void main(String[] args) {
        Task<Number> task = new Task<Number>() {
            @Override
            protected Number call() throws Exception {
                return null;
            }
        };
        HttpResponse<String> response = Unirest.get("https://zhuanlan.zhihu.com/p/449383989").asString();
        try {
            for (Object getNode : GetNodes.GetNodes(response.getBody(), "//*[@id='root']/div/main/div/article/header/h1/text()")) {
                System.out.println(getNode.toString());
            }
        } catch (XpathSyntaxErrorException e) {
            throw new RuntimeException(e);
        }
    }
}

