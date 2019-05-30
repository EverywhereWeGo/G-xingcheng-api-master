package com.bfd.api.utils;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

	private static Map<String, Logger> logMap = new HashMap<String, Logger>();

	private LogUtil() {
	}

	static {
		logMap.put(LogType.Run.toString(), LoggerFactory.getLogger(LogType.Run.toString()));
		logMap.put(LogType.Ope.toString(), LoggerFactory.getLogger(LogType.Ope.toString()));
		logMap.put(LogType.Beh.toString(), LoggerFactory.getLogger(LogType.Beh.toString()));
		logMap.put(LogType.Ser.toString(), LoggerFactory.getLogger(LogType.Ser.toString()));
	}

	public static Logger getLogger(LogType logtype) {
		return logMap.get(logtype.toString());
	}
	
	

}
