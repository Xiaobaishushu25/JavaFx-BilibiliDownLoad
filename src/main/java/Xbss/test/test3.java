package Xbss.test;

import Xbss.Utils.ReadText;

/**
 * @author Xbss
 * @version 1.0
 * @create 2022-05-25-21:48
 * @descirbe
 */
public class test3 {
    public static void main(String[] args) {
        String s="《Moment》\\ MSI-玖 | 集火.mp3";
        char ban[]={'\\','/',':','*','?','"','<','>','|'};
        String[] ban2={"\\","/",":","*","?","'","<",">","|"};
        for (String r : ban2) {
          if (s.contains(r)){
              System.out.println("****");
               s = s.replace(r, " ");
          }
        }
        String[] words = s.split("\\s+");
        StringBuilder builder = new StringBuilder();
        for(String word:words){
            builder.append(word);
        }
        System.out.println(builder);
    }
}
