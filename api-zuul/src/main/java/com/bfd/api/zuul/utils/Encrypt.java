package com.bfd.api.zuul.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * 加密解密类
 */
public class Encrypt {
	private static final String KEY = "bfd_api";
	private static StandardPBEStringEncryptor encryptor;
	
	static{
		encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(KEY);
	}
	
	/**
	 * 加密
	 * @param text
	 * 明文
	 * @return 密文
	 */
	public static String encrypt(String text) {
		return encryptor.encrypt(text);
	}

	/**
	 * 解密
	 * @param ciphertext
	 * 密文
	 * @return 明文
	 */
	public static String decrypt(String ciphertext) {
		return encryptor.decrypt(ciphertext);
	}

	public static void main(String[] args) {
		String ciphertext1 = encrypt("jupiter123");
		System.out.println(ciphertext1);
		
		String ciphertext2 = encrypt("xhs123456");
		System.out.println(ciphertext2);

		String text1 = decrypt(ciphertext1);
		System.out.println(text1);
	}
}