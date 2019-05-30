package com.bfd.api.utils;
public class MD5UtilAdapter {
	/**
	 * 获取字符串md5前两位XX,给字符串加前缀 "XX:"
	 */
	public static String addPrefix(String str){
		String prefix="";
		try {
			prefix= MD5Util.encode32Bit(str).substring(0, 2);
		} catch (Exception e) {
			throw new RuntimeException("截取字符串MD5前两位失败");
		}
		return prefix+":"+str;
	}
	
	public static void main(String[] args) {
		System.out.println(addPrefix("global:superid:c4d51bad-63a4-4f4b-88f8-41b6853df484"));
	}

}
