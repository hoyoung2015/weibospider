package net.hoyoung.weibospider.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hoyoung on 2015/11/30.
 */
public class ContentFilter {
    private static Pattern contentPat = Pattern.compile("[\\u4e00-\\u9fa5\\uFE30-\\uFFA0\\w]+");
    public static String filter(String text){
        if(text!=null){
            Matcher m = contentPat.matcher(text);
            StringBuffer sb = new StringBuffer();
            while (m.find()){
                sb.append(m.group());
            }
            /*if(m.find()){
                return m.group(0);
            }*/
            return sb.toString();
        }
        return "";
    }
}
