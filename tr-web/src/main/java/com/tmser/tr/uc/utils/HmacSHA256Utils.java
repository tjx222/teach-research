package com.tmser.tr.uc.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.shiro.codec.Hex;

import com.tmser.tr.utils.Encodes;

/**
 * 
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: HmacSHA256Utils.java, v 1.0 2016年4月12日 下午6:34:23 3020mt Exp $
 */
public abstract class HmacSHA256Utils {
	
	/**
	 * 加密
	 * @param key 密匙
	 * @param content
	 * @return
	 */
	public static String digest(String key, String content) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = key.getBytes("utf-8");
            byte[] dataBytes = content.getBytes("utf-8");

            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);

            byte[] doFinal = mac.doFinal(dataBytes);
            char[] hexB = Hex.encode(doFinal);
            return new String(hexB);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	public static void main(String[] args) {
		System.out.println(digest("4QrcOUm6Wau+VuBX8g+IPg==", "xxty"));
		String s = "{\"username\":\"xxty\",\"token\":\"9802af7299edfddbbb17eb51583af0b4073c7b30bcedbece0c37dfb40a8cc7ce\",spaceid:11}";
		System.out.println(Encodes.encodeBase64(s.getBytes()));
	}
}
