package Xbss.Utils;

import com.sun.istack.internal.Nullable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-06-12-0:02
 * @descirbe
 */
public class GetImageView {
    /**
     * @Author Xiaobaishushu
     * @Description :abOrrel（绝对路径还是相对路径），默认不传（为相对路径），传数字表示绝对路径
     * @Date 2022/6/12 0:20
     * @Param [abOrrel, filepath, height, width]
     * @return javafx.scene.image.ImageView
     **/
    public static ImageView getImageView(@Nullable Integer abOrrel,  String filepath, @Nullable Double width,@Nullable Double height){
        ImageView imageView = new ImageView();
        if (abOrrel!=null){
            File file = new File(filepath);
            imageView = new ImageView(new Image(file.toURI().toString()));
        }
        else {
            String pro_path = System.getProperty("user.dir");
            File file = new File(pro_path+"/"+filepath);
            imageView = new ImageView(new Image(file.toURI().toString())) ;
        }
        if (height!=null)
            imageView.setFitHeight(height);
        if (width!=null)
            imageView.setFitWidth(width);
        return imageView;
    }
    public static ImageView getImageView(String filepath, Double width,Double height){
        ImageView imageView = new ImageView(new Image(filepath));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }
}
