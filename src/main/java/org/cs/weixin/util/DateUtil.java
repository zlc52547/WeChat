package org.cs.weixin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zlc on 2018/12/25.
 * Descriptor: TODO 日期工具类
 */
public class DateUtil {


    public static void main(String[] agrs){
        String a = simpleDate("-");
        System.out.println(a);
    }

    /**
      * @Description: 获取当前日期(1970年01月01日)
      * @Param: format 日期格式 默认为年月日,可填‘-’或 ‘/’
      * @return: 1900年01月01日
      * @Author: Zhaolc
      * @Date: 2018/12/25 15:17
      */
    public static String newDate(String format){
        Date date=new Date();
        Long time=date.getTime();
        Date d=new Date(time);
        SimpleDateFormat sdf=new SimpleDateFormat();
        if (format.equals("-")){
            sdf=new SimpleDateFormat("yyyy-MM-dd");
        }else if (format.equals("/")){
            sdf=new SimpleDateFormat("yyyy/MM/dd");
        }else {
            sdf=new SimpleDateFormat("yyyy年MM月dd日");
        }
        return sdf.format(d);
    }

    /**
      * @Description: 获取当前日期时分秒
      * @Param: format 日期格式 默认为年月日,可填‘-’或 ‘/’
      * @return: 1900年01月01日
      * @Author: Zhaolc
      * @Date: 2018/12/25 15:45
      */
    public static String simpleDate(String format){
        Date date=new Date();
        Long time=date.getTime();
        Date d=new Date(time);
        SimpleDateFormat sdf=new SimpleDateFormat();
        if (format.equals("-")){
            sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        }else if (format.equals("/")){
            sdf=new SimpleDateFormat("yyyy/MM/dd HH/mm/ss");
        }else {
            sdf=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        }
        return sdf.format(d);
    }
}
