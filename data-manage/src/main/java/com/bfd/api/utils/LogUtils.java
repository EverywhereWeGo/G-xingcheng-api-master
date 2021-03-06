package com.bfd.api.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * log4j日志Util
 */
public class LogUtils {
	private static Logger logger=null;
	private static final String clazzName=LogUtils.class.getName();
	
	static{
		//获取该类的调用者(调用者类名)
		StackTraceElement[] stackTraceElements=new Throwable().getStackTrace();
		String callerClassName=stackTraceElements[1].getClassName();
		//用调用者类名初始化logger,保证日志输出的类是调用者类名
		logger=Logger.getLogger(callerClassName);
	}
	
	private LogUtils(){}
	
	//---------------------error---------------------------------
	public static void error(String msg){
		//必须用这种方法调用才能获取调用者的正确代码行号
		logger.log(clazzName, Level.ERROR, msg, null);
	}
	
	public static void error(Throwable e){
		if(e instanceof InvocationTargetException){
			e=((InvocationTargetException) e).getTargetException();
		}
		error("Exception: "+e.toString());
		for(int i=0;i<e.getStackTrace().length;i++){
			error(e.getStackTrace()[i].toString());
		}
	}
	
	public static void error(String msg,Throwable e){
		error(msg);
		if(e instanceof InvocationTargetException){
			e=((InvocationTargetException) e).getTargetException();
		}
		error("Exception: "+e.toString());
		for(int i=0;i<e.getStackTrace().length;i++){
			error(e.getStackTrace()[i].toString());
		}
	}
	
	//---------------------warn----------------------------------
	public static void warn(String msg){
		//必须用这种方法调用才能获取调用者的正确代码行号
		logger.log(clazzName, Level.WARN, msg, null);
	}
	
	//---------------------info----------------------------------
	public static void info(String msg){
		//必须用这种方法调用才能获取调用者的正确代码行号
		logger.log(clazzName, Level.INFO, msg, null);
	}
	
	//---------------------info----------------------------------
		public static void debug(String msg){
			//必须用这种方法调用才能获取调用者的正确代码行号
			logger.log(clazzName, Level.DEBUG, msg, null);
		}
	
}
