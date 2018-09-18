package com.dognessnetwork.ops.utils;

import java.math.BigDecimal;  
import java.text.DecimalFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;
  
  
/** 
 * @author DingJiaCheng 
 * */  
public class DateUtils {  
      
    
      
    /** 
     * 获取晚上9点半的时间戳 
     *  
     * @return 
     */  
    public static int getTimes(int day, int hour, int minute) {  
        Calendar cal = Calendar.getInstance();  
        cal.add(Calendar.DATE, day);  
        cal.set(Calendar.HOUR_OF_DAY, hour);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MINUTE, minute);  
        cal.set(Calendar.MILLISECOND, 0);  
        return (int) (cal.getTimeInMillis() / 1000);  
    }  
      
    /** 
     * 获取当前时间往上的整点时间 
     *  
     * @return 
     */  
    public static int getIntegralTime() {  
        Calendar cal = Calendar.getInstance();  
        cal.add(Calendar.HOUR_OF_DAY, 1);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return (int) (cal.getTimeInMillis() / 1000);  
    }  
      
    public static int getIntegralTimeEnd() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.HOUR_OF_DAY, 24);  
        cal.set(Calendar.SECOND, 0);  
        cal.set(Calendar.MINUTE, 0);  
        cal.set(Calendar.MILLISECOND, 0);  
        return (int) (cal.getTimeInMillis() / 1000);  
    }  
      
      
      
      
      
      
      
      
  
      
      
    /** 
     * 时间戳转日期 
     * @param ms 
     * @return 
     */  
    public static Date transForDate3(Integer ms){  
        if(ms==null){  
            ms=0;  
        }  
        long msl=(long)ms*1000;  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");  
        Date temp=null;  
        if(ms!=null){  
            try {  
                String str=sdf.format(msl);  
                temp=sdf.parse(str);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        return temp;  
    }  
    /** 
     * 时间戳转日期 
     * @param ms 
     * @return 
     */  
    public static Date transForDate(Long ms){  
        if(ms==null){  
            ms=(long)0;  
        }  
        long msl=(long)ms;  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date temp=null;  
        if(ms!=null){  
            try {  
                String str=sdf.format(msl);  
                temp=sdf.parse(str);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }  
        return temp;  
    }  
      
      
    public static String transForDate1(Long ms){  
        String str = "";  
        if(ms!=null){  
            long msl=(long)ms*1000;  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
              
            if(ms!=null){  
                try {  
                    str=sdf.format(msl);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }         
        return str;  
    }  
    public static String transForDate2(Long ms){  
        String str = "";  
        if(ms!=null){  
            long msl=(long)ms*1000;  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
              
            if(ms!=null){  
                try {  
                    str=sdf.format(msl);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }         
        return str;  
    }  
      
    public static String transForDate4(Integer ms){  
        String str = "";  
        if(ms!=null){  
            long msl=(long)ms*1000;  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");  
              
            if(ms!=null){  
                try {  
                    str=sdf.format(msl);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }         
        return str;  
    }  
      
      
    public static String transForDate5(Integer ms){  
        String str = "";  
        if(ms!=null){  
            long msl=(long)ms*1000;  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
              
            if(ms!=null){  
                try {  
                    str=sdf.format(msl);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }         
        return str;  
    }  
      
    public static String transForDateInChinese(Integer ms){  
        String str = "";  
        if(ms!=null){  
            long msl=(long)ms*1000;  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");  
              
            if(ms!=null){  
                try {  
                    str=sdf.format(msl);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }         
        return str;  
    }  
      
    /** 
     * 日期转时间戳 
     * @param date 
     * @return 
     */  
    public static Long transForMilliSecond(Date date){  
        if(date==null) return null;  
        return (date.getTime()/1000);  
    }  
      
    /** 
     * 获取当前时间戳 
     * @return 
     */  
    public static Long currentTimeStamp(){  
        return (System.currentTimeMillis()/1000);  
    }  
      
    /** 
     * 日期字符串转时间戳 
     * @param dateStr 
     * @return 
     */  
    public static Long transForMilliSecond(String dateStr){  
        Date date = DateUtils.formatDate(dateStr);  
        return date == null ? null : DateUtils.transForMilliSecond(date);  
    }  
    /** 
     * 日期字符串转时间戳 
     * @param dateStr 
     * @return 
     */  
    public static Long transForMilliSecond(String dateStr,String format){  
        Date date = DateUtils.formatDate(dateStr,format);  
        return date == null ? null : DateUtils.transForMilliSecond(date);  
    }  
    /** 
     * 日期字符串转时间戳 
     * @param dateStr 
     * @param 格式 如"yyyy-mm-dd" 
     * @return 
     */  
    public static Long transForMilliSecondByTim(String dateStr,String tim){  
        SimpleDateFormat sdf=new SimpleDateFormat(tim);  
        Date date =null;  
        try {  
            date = sdf.parse(dateStr);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return date == null ? null : DateUtils.transForMilliSecond(date);  
    }  
    /** 
     * 字符串转日期，格式为："yyyy-MM-dd HH:mm:ss" 
     * @param dateStr 
     * @return 
     */  
    public static Date formatDate(String dateStr){  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date result=null;  
        try {  
            result = sdf.parse(dateStr);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    /** 
     * 字符串转日期，格式为："yyyy-MM-dd HH:mm:ss" 
     * @param dateStr 
     * @return 
     */  
    public static Date formatDate(String dateStr,String format){  
        SimpleDateFormat sdf=new SimpleDateFormat(format);  
        Date result=null;  
        try {  
            result = sdf.parse(dateStr);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    /** 
     * 日期转字符串 
     * @param date 
     * @return 
     */  
    public static String formatDate(Date date){  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String result=null;  
        result = sdf.format(date);  
        return result;  
    }  
    /** 
     * 日期转字符串 
     * @param date 
     * @return 
     */  
    public static String formatDate(Date date,String format){  
        SimpleDateFormat sdf=new SimpleDateFormat(format);  
        String result=null;  
        result = sdf.format(date);  
        return result;  
    }  
    /** 
     * 时间戳格式化输出（httl模版用） 
     *  
     * @param ms        时间戳 
     * @param format    格式化 
     * @return 
     */  
    public static String transForDate(Integer ms, String format){  
        String str = "";  
        if(ms!=null){  
            long msl=(long)ms*1000;  
            SimpleDateFormat sdf=new SimpleDateFormat(format);  
            if(!ms.equals(0)){  
                try {  
                    str=sdf.format(msl);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return str;  
    }     
      
    /** 
     * 取BigDecimal类型数的整数或小数部分（httl模版用） 
     *  
     * @param b 值 
     * @param mode  模式 0取整 1去小数部分 
     * @return 
     */  
    public static String splitBigDecimal(BigDecimal b, int mode) {  
        DecimalFormat df = new DecimalFormat("0.00");  
        String s = df.format(b);  
        if(mode==0){  
            return s.split("\\.")[0];  
        }else {  
            return "."+s.split("\\.")[1];  
        }  
    }  
      
    /** 
     * 计算两个日期之间差的天数（httl模版用） 
     *  
     * @param ts1   时间戳1 
     * @param ts2   时间戳2 
     * @return 
     */  
    public static int caculate2Days(Long ts1, Long ts2) {  
        Date firstDate = DateUtils.transForDate(ts1);  
        Date secondDate = DateUtils.transForDate(ts2);  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(firstDate);  
        int dayNum1 = calendar.get(Calendar.DAY_OF_YEAR);  
        calendar.setTime(secondDate);  
        int dayNum2 = calendar.get(Calendar.DAY_OF_YEAR);  
        return Math.abs(dayNum1 - dayNum2);  
    }  
      
    /** 
     * 给手机加密中间四位加星号 
     *  
     * @param mobile 
     * @return 
     */  
//    public String mobileSerect(String mobile){  
//        if(!StringUtils.isBlank(mobile)){  
//            int between = mobile.length()/2;  
//            mobile = mobile.substring(0, between-2)+"****"+mobile.substring(between+2, mobile.length());  
//        }  
//        return mobile;  
//    }  
      
    /** 
     * 给邮箱加密加星号 
     *  
     * @param email 
     * @return 
     */  
//    public String emailSerect(String email) {  
//        if(!StringUtils.isBlank(email)){  
//            int length = email.lastIndexOf("@");  
//            email = email.substring(0, 2)+"****"+email.substring(length-2, email.length());  
//        }  
//        return email;  
//    }  
      
    /** 
     * BigDecimal类型数据相加 
     *  
     * @param BigDecimal source 
     * @param BigDecimal target 
     * @return 
     */  
    public BigDecimal sumBigDicimal(BigDecimal source, BigDecimal target) {  
        source = source.add(target);  
        return source;  
    }  
      
    /** 
     * BigDecimal类型数据相加 
     *  
     * @param BigDecimal source 
     * @param BigDecimal target 
     * @return 
     */  
    public BigDecimal sumBigDicimalAndDouble(BigDecimal source, Double target) {  
        BigDecimal new_target = new BigDecimal(target);  
        source = source.add(new_target);  
        return source;  
    }  
      
    /** 
     * BigDecimal类型数据相减 
     *  
     * @param BigDecimal source 
     * @param BigDecimal target 
     * @return 
     */  
    public BigDecimal subBigDicimal(BigDecimal source, BigDecimal target) {  
        source = source.subtract(target);  
        return source;  
    }  
      
    /** 
     * 获取传入时间和当前时间的时间差 
     * @return 
     */  
    public static Long getTimediff(Long timeStamp){  
        Date d1 = DateUtils.transForDate(timeStamp);  
        Date today = new Date();  
        if(d1.getTime()<today.getTime()){  
            return null;  
        }  
        return (d1.getTime()-today.getTime())/1000;  
    }  
  
    /** 
     * 获取某周的第一天日期 
     * @param week 0 当周 1 上一周 -1 下一周 
     * @return 
     */  
    public static String weekFirstDay(int week){  
        Calendar c1 = Calendar.getInstance();  
        int dow = c1.get(Calendar.DAY_OF_WEEK);  
        c1.add(Calendar.DATE, -dow-7*(week-1)-5 );  
        String d1 = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());  
        return d1+" 00:00:00";  
    }  
      
    /** 
     * 当前时间加一年 
     */  
    public static String addYear(Long startTime){  
        Date firstDate = DateUtils.transForDate(startTime);  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(firstDate);  
        calendar.add(Calendar.YEAR,1);  
        String d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());  
        return d1;  
    }  
      
    /** 
     * 获取某周的最后一天日期 
     * @param week 
     * @return 
     */  
    public static String weekLastDay(int week){  
        Calendar c1 = Calendar.getInstance();  
        int dow = c1.get(Calendar.DAY_OF_WEEK);  
        c1.add(Calendar.DATE, -dow-7*(week-1)+1);  
        String d1 = new SimpleDateFormat("yyyy-MM-dd").format(c1.getTime());  
        return d1+" 23:59:59";  
    }     
      
    /** 
     * 和当前时间比对 
     * @return 
     */  
    public static boolean greaterThanNow(Long timeStamp){  
        Date d1 = DateUtils.transForDate(timeStamp);  
        Date today = new Date();  
        if(d1.getTime()>=today.getTime()){  
            return true;  
        }  
        return false;  
    }  
  
      
      
    /** 
     * HH:mm:ss格式时间转换为1970-01-01日的时间戳（也就是只有时间没有日期的情况要求使用时间戳表示时间） 
     * @author DingJiaCheng 
     * */  
    public static Long transFromTime(String time){  
        return transForMilliSecond("1970-01-01 "+time,"yyyy-mm-dd HH:mm:ss");  
    }  
      
    /** 
     * 时间戳转换为HH：mm：ss格式时间(日期去除) 
     * @author DingJiaCheng 
     * */  
    public static String transToTime(Long time){  
        String s = new String(transForDate1(time));  
        String ss[] = s.split(" ");  
        return ss[1];  
    }  
      
    public static int transToChuo(String dateString){  
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");  
        int res = 0;  
        try {  
            Date date=simpleDateFormat .parse(dateString);  
            res = (int) date.getTime();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return res;  
    }  
    /**
	 * 获取指定日期所在月份开始的时间戳
	 * @param date 指定日期
	 * @return
	 */
	public static Long getMonthBegin(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		//设置为1号,当前日期既为本月第一天  
		c.set(Calendar.DAY_OF_MONTH, 1);
		//将小时至0  
		c.set(Calendar.HOUR_OF_DAY, 0);  
		//将分钟至0  
		c.set(Calendar.MINUTE, 0);  
		//将秒至0  
		c.set(Calendar.SECOND,0);  
		//将毫秒至0  
		c.set(Calendar.MILLISECOND, 0);  
		// 获取本月第一天的时间戳  
		return c.getTimeInMillis();  
	}
	/**
	 * 获取指定日期所在月份结束的时间戳
	 * @param date 指定日期
	 * @return
	 */
	public static Long getMonthEnd(Date date) {
		Calendar c = Calendar.getInstance();  
		c.setTime(date);
		
		//设置为当月最后一天
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
		//将小时至23
		c.set(Calendar.HOUR_OF_DAY, 23);  
		//将分钟至59
		c.set(Calendar.MINUTE, 59);  
		//将秒至59
		c.set(Calendar.SECOND,59);  
		//将毫秒至999
		c.set(Calendar.MILLISECOND, 999);  
		// 获取本月最后一天的时间戳  
		return c.getTimeInMillis();
	}
	
	
    public static void main(String[] args) {  
          
        //System.out.println(getIntegralTimeEnd());  
        System.out.println(transForMilliSecond("2017-03-25 23:59:59"));  
//       
//        long current = System.currentTimeMillis();
//        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
//        long twelve=zero-24*60*60*1000-1;
//        System.out.println(zero); 
//        System.out.println(twelve);//今天零点零分零秒
        //System.out.println(transForDate1(transForMilliS3cond("1970-01-01 00:00:00","yyyy-mm-dd HH:mm:ss")));  
        //System.out.println(currentTimeStamp());  
        //System.out.println(transForDate(currentTimeStamp()));  
        //System.out.println(new Date().getTime());  
        //System.out.println(DateUtils.getDate());  
//        System.out.println(transFromTime("00:00:01"));  
//        System.out.println(transToTime(transFromTime("15:01:13")));  
    }  
}