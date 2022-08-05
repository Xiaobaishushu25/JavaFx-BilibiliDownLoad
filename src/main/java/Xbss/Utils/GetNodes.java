package Xbss.Utils;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-07-19-22:23
 * @descirbe
 */
public class GetNodes {
    /**
     * @Author Xiaobaishushu
     * @Description ：在网页中查找某个结点（html的标签）
     * @Date 2022/5/26 18:57
     * @Param [html, xpath]
     * @return java.util.List
     **/
    public static List GetNodes(String html, String xpath) throws XpathSyntaxErrorException {
        //将传进来的string转换为document对象
        Document doc = Jsoup.parse(html);
        //document对象转为xml对象
        JXDocument document = new JXDocument(doc);
        //用xpath解析
//        List<JXNode> nodes = document.selN(xpath);
        List<Object> nodes = document.sel(xpath);
        return nodes;
    }
}
