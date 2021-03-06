package com.bfd.api.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.NameValuePair;

public class StringUtil {
	public static boolean isEmpty(String str){
		if (str==null||str.equals("")) {
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean isNotEmpty(String str){
		if (str==null||str.equals("")) {
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 找出在str字符串中，toMatch字符串出现的次数
	 */
	public static int countMatch (String str,String toMatch) {
		int count=0;
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, str.length()).indexOf(toMatch)==0) {
				count++;
			}
		}
		return count;
	}
	
	// 二进制转字符串
	public static String byte2hex(byte[] b) 
	{
	   StringBuffer sb = new StringBuffer();
	   String tmp = "";
	   for (int i = 0; i < b.length; i++) {
	    tmp = Integer.toHexString(b[i] & 0XFF);
	    if (tmp.length() == 1){
	    	sb.append("0" + tmp);
	    }else{
	    	sb.append(tmp);
	    }
	    
	   }
	   return sb.toString();
	}
	
	// 字符串转二进制
	public static byte[] hex2byte(String str) { 
	  if (str == null){
	   return null;
	  }
	  
	  str = str.trim();
	  int len = str.length();
	  
	  if (len == 0 || len % 2 == 1){
	   return null;
	  }
	  
	  byte[] b = new byte[len / 2];
	  try {
		   for (int i = 0; i < str.length(); i += 2) {
		    	b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
		   }
		   return b;
	  } catch (Exception e) {
	   return null;
	  }
	}
	
	//object convert to binary
	public static String objectToBinaryString(Object obj) throws IOException
	{
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			NameValuePair onep = new NameValuePair();
			oos.writeObject(obj);
			byte[] abc = baos.toByteArray();
			return Base64.encodeBase64String(abc);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException("one value on error.");
		}
		finally {
			if (null != oos)
				oos.close();
		}
	}
	
	public static Object binaryStringToObject(String str) throws IOException
	{
		ObjectInputStream ois = null;
		try {
			byte[] bin = Base64.decodeBase64(str);
			ByteArrayInputStream bais = new ByteArrayInputStream(bin);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException("one value on error.");
		}
		finally {
			if (null != ois)
				ois.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		List<String> lst = new ArrayList<String>();
		for (int i = 0; i < 1000; i++)
			lst.add(String.valueOf(i));
		String st = StringUtil.objectToBinaryString(lst);
		List aaa = (List)StringUtil.binaryStringToObject(st);
		System.out.println(aaa);
		
		String str2 = StringUtil.objectToBinaryString("123abcdfg");
		String str3 = (String)StringUtil.binaryStringToObject(str2);
		System.out.println(str3);
		
	}
	
}
