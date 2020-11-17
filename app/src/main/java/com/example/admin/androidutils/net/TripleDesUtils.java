package com.example.admin.androidutils.net;



import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class TripleDesUtils {
	
	private static final String ALGORITHM = "DESede";
	
	private static final String CHAR_ENCODE = "UTF-8";

	/**
	 * 获取随机密钥，采用UUID实现
	 * @return
	 */
	public static String getSecretKey(){
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 24);
	}
	
	/**
	 * 加密
	 * @param data
	 * @return
	 */
	public static String encrypt(String data, String secretKey){
		
		SecretKey deskey = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] output = cipher.doFinal(data.getBytes(CHAR_ENCODE));
//			return Base64.encodeBase64String(output);
			return Base64.encodeToString(output, Base64.DEFAULT);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 * @param data
	 * @param secretKey
	 * @return
	 */
	public static String decrypt(String data, String secretKey){
		
		SecretKey deskey = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
		
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, deskey);
//			byte[] output = cipher.doFinal(Base64.decodeBase64(data));
			byte[] output=cipher.doFinal(Base64.decode(data,Base64.DEFAULT));
			return new String(output, CHAR_ENCODE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
