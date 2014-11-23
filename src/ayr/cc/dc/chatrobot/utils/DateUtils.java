package ayr.cc.dc.chatrobot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
   public static String getDateString(Date date){
//	   SimpleDateFormat format = new SimpleDateFormat("yyy年mm月dd日  hh:mm:ss");
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   return format.format(date);
   }
}
