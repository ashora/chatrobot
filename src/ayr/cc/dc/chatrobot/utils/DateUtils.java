package ayr.cc.dc.chatrobot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
   public static String getDateString(Date date){
	   SimpleDateFormat format = new SimpleDateFormat("yyy年MM月dd日 HH:mm:ss");
	   return format.format(date);
   }
}
