package com.bfd.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	
	public static String long2String(Long time){
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(time);
		return sdf.format(date);
	}
	
	public static String long2Str(Long time){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		return sdf.format(date);
	}

	public static String str2day(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
		
	public static Long str2long(String time){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取间隔日期
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static String getIntervalDays(String startTime,String endTime){
		
        if(StringUtils.isNotBlank(startTime)){
        	if(StringUtils.isEmpty(endTime) || StringUtils.equals(startTime, endTime)){
        		return startTime;
        	}
        }else{
          	if(StringUtils.isEmpty(endTime)){
        		return null;
        	}
        	return endTime;
        }
		
		String daytime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try{
	        Date start = sdf.parse(startTime.replace("-", ""));
	        Date end = sdf.parse(endTime.replace("-", ""));
	        List<Date> lists = dateSplit(start, end);
	        if (!lists.isEmpty()) {
	            for (Date date : lists) {
	                if(StringUtils.isEmpty(daytime)){
	                	daytime = sdf.format(date)+"|";
	                }else{
	                	daytime = daytime + sdf.format(date) +"|";
	                }
	            }
	        }
        }catch (Exception e) {
        	e.printStackTrace();
		}
        if(daytime.length()>0){
        	daytime = daytime.substring(0,daytime.length()-1);
        }
        return daytime;
	}
	
	private static List<Date> dateSplit(Date startDate, Date endDate)
	        throws Exception {
		
	    if (!startDate.before(endDate)){
	    	return null;
	    }
	    
	    List<Date> dateList = new ArrayList<Date>();
	    Long spi = endDate.getTime() - startDate.getTime();
	    Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

	    dateList.add(endDate);
	    for (int i = 1; i <= step; i++) {
	        dateList.add(new Date(dateList.get(i - 1).getTime()
	                - (24 * 60 * 60 * 1000)));// 比上一天减一
	    }
	    return dateList;
	}
	
	public static void main(String[] args) {
		
		try {
			System.out.println(getIntervalDays("20161207", "20161209"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(long2Str((long) (1502803006)));
	
	}
}
