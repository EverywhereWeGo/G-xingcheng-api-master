package com.bfd.api.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public final class CalendarUtil
{
  private static final Logger log = LoggerFactory.getLogger(CalendarUtil.class);
  public static final String CREDIT_CARD_DATE_FORMAT = "MM/yyyy";
  public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
  public static final String SHORT_DATE_FORMAT_YYYY_MM = "yyyy-MM";
  public static final String SHORT_DATE_DOT_FORMAT = "yyyy.MM.dd";
  public static final String SHORT_DATE_FORMAT_NO_DASH = "yyyyMMdd";
  public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String SIMPLE_DATE_FORMAT_NO_DASH = "yyyyMMddHHmmss";
  public static final String LOG_DATE_FORMAT = "yyyyMMdd_HH00";
  public static final String ZONE_DATE_FORMAT = "EEE yyyy-MM-dd HH:mm:ss zzz";
  public static final String DATE_FORMAT = "yyyy/MM/dd EEE";
  public static final String TIME_FORMAT = "HH:mm";
  
  public static int daysBetween(Calendar startTime, Calendar endTime)
  {
    if (startTime == null) {
      throw new IllegalArgumentException("startTime is null");
    }
    if (endTime == null) {
      throw new IllegalArgumentException("endTime is null");
    }
    if (startTime.compareTo(endTime) > 0) {
      throw new IllegalArgumentException("endTime is before the startTime");
    }
    return (int)((endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 86400000L);
  }
  
  public static Calendar startOfDayTomorrow()
  {
    Calendar calendar = Calendar.getInstance();
    truncateDay(calendar);
    calendar.add(5, 1);
    return calendar;
  }
  
  public static Calendar startOfDayYesterday()
  {
    Calendar yesterday = Calendar.getInstance();
    truncateDay(yesterday);
    yesterday.add(5, -1);
    return yesterday;
  }
  
  public static Calendar truncateDay(Calendar calendar)
  {
    if (calendar == null) {
      throw new IllegalArgumentException("input is null");
    }
    calendar.set(9, 0);
    calendar.set(10, 0);
    calendar.set(12, 0);
    calendar.set(13, 0);
    calendar.set(14, 0);
    return calendar;
  }
  
  public static String format(Calendar calendar)
  {
    String formatted = "";
    if (calendar != null) {
      formatted = new SimpleDateFormat().format(calendar.getTime());
    }
    return formatted;
  }
  
  public static String format(Time time)
  {
    String formatted = "";
    if (time != null) {
      formatted = new SimpleDateFormat("HH:mm").format(Long.valueOf(time.getTime()));
    }
    return formatted;
  }
  
  public static String getDateString(Calendar calendar, String format)
  {
    if (calendar == null) {
      return null;
    }
    return getDateString(calendar.getTime(), format);
  }
  
  public static String getDefaultDateString(Date date)
  {
    if (date == null) {
      return "";
    }
    return getDateString(date, "yyyy-MM-dd HH:mm:ss");
  }
  
  public static String getShortDateString(Date date)
  {
    if (date == null) {
      return "";
    }
    return getDateString(date, "yyyy-MM-dd");
  }
  
  public static String getDateString(Date date, String format)
  {
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }
  
  public static String getDateString(Date date, String format, Locale locale)
  {
    if (date == null) {
      return null;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
    return sdf.format(date);
  }
  
  public static Date parseDate(String dateString, String pattern)
  {
    Date date = null;
    try
    {
      DateFormat format = new SimpleDateFormat(pattern);
      date = format.parse(dateString);
    }
    catch (ParseException ex)
    {
      log.error("Invalid date string: " + dateString, ex);
      throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
    }
    return date;
  }
  
  public static Date parseDefaultDate(String dateString)
  {
    if (!StringUtils.hasText(dateString)) {
      return null;
    }
    Date date = null;
    try
    {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      date = format.parse(dateString);
    }
    catch (ParseException ex)
    {
      log.error("Invalid date string: " + dateString, ex);
      throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
    }
    return date;
  }
  
  public static Calendar parseCalendarShort(String dateString)
  {
    if (!StringUtils.hasText(dateString)) {
      return null;
    }
    return parseCalendar(dateString, "yyyy-MM-dd");
  }
  
  public static Calendar parseCalendar(String dateString)
  {
    if (!StringUtils.hasText(dateString)) {
      return null;
    }
    return parseCalendar(dateString, "yyyy-MM-dd HH:mm:ss");
  }
  
  public static String parseIntegerToDate(Integer time)
  {
    if (time == null) {
      return "00:00";
    }
    StringBuilder sb = new StringBuilder("");
    int xs = time.intValue() / 3600;
    if (xs > 0)
    {
      if (xs < 10) {
        sb.append("0");
      }
      sb.append(xs);
      sb.append(":");
    }
    int fen = (time.intValue() - 3600 * xs) / 60;
    if (fen < 10) {
      sb.append("0");
    }
    sb.append(fen);
    sb.append(":");
    int second = time.intValue() - xs * 3600 - fen * 60;
    if (second < 10) {
      sb.append("0");
    }
    sb.append(second);
    return sb.toString();
  }
  
  public static Calendar parseCalendar(String dateString, String pattern)
  {
    Date date = parseDate(dateString, pattern);
    
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c;
  }
  
  public static Date parseShortDate(String dateString)
  {
    if (!StringUtils.hasText(dateString)) {
      return null;
    }
    Date date = null;
    try
    {
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      date = format.parse(dateString);
    }
    catch (ParseException ex)
    {
      log.error("Invalid date string: " + dateString, ex);
      throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
    }
    return date;
  }
  
  public static Calendar backOneDay(Calendar date)
  {
    Calendar cal = (Calendar)date.clone();
    cal.add(5, -1);
    return cal;
  }
  
  public static int daysForCurrentMonth()
  {
    Calendar c = Calendar.getInstance();
    int days = c.getActualMaximum(5);
    
    return days;
  }
  
  public static Calendar fromDate(Date date)
  {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    
    return c;
  }
  
  public static Calendar epoch()
  {
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(0L);
    return c;
  }
  
  public static long getSimpleDateTimeMillis(long timeMillis)
  {
    Date date = new Date(timeMillis);
    String dateStr = getDateString(date, "yyyy-MM-dd");
    Date transformDate = parseDate(dateStr, "yyyy-MM-dd");
    return transformDate.getTime();
  }
  
  public static Calendar getDateFromDate(Date from, long days)
  {
    long froml = from.getTime();
    
    long interval = days * 24L * 60L * 60L * 1000L;
    long millis = froml + interval;
    Calendar now = Calendar.getInstance();
    now.setTimeInMillis(millis);
    return now;
  }
  
  public static String getDateFromDate(Date from, long days, String format)
  {
    Calendar c = getDateFromDate(from, days);
    return getDateString(c, format);
  }
  
  public static String getDateFromDate(String from, long days, String format)
  {
    Date d = parseDate(from, format);
    Calendar c = getDateFromDate(d, days);
    return getDateString(c, format);
  }
  
  public static Calendar getDayStart()
  {
    Calendar cal = Calendar.getInstance();
    cal = truncateDay(cal);
    return cal;
  }
  
  public static Calendar getWeekDayStart()
  {
    Calendar cal = Calendar.getInstance();
    int day_of_week = cal.get(7) - 2;
    cal.add(5, -day_of_week);
    cal = truncateDay(cal);
    return cal;
  }
  
  public static Calendar getMonthDayStart()
  {
    Calendar cal = Calendar.getInstance();
    int day_of_month = cal.get(5) - 1;
    cal.add(5, -day_of_month);
    cal = truncateDay(cal);
    return cal;
  }
  
  public static Date getDateByAfterDays(Calendar cale, int days)
  {
    cale.set(5, cale.get(5) + days);
    return cale.getTime();
  }
  
  public static Date getDateByBeforeDays(Calendar cale, int days)
  {
    cale.set(5, cale.get(5) - days);
    return cale.getTime();
  }
  
  public static void main(String[] args) {}
}
