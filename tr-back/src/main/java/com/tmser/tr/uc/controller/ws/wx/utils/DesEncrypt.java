package com.tmser.tr.uc.controller.ws.wx.utils;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DesEncrypt
  implements IEncrypt
{
  private String winShareKey = "zA%^>6@)";

  public String decrypt(String info)
    throws Exception
  {
    return decrypt(info, this.winShareKey);
  }

  public String encrypt(String info) throws Exception
  {
    return toHexString(encrypt(info, this.winShareKey)).toUpperCase();
  }

  private String decrypt(String message, String key) throws Exception
  {
    byte[] bytesrc = convertHexString(message);
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

    cipher.init(2, secretKey, iv);

    byte[] retByte = cipher.doFinal(bytesrc);
    return new String(retByte);
  }

  private byte[] encrypt(String message, String key) throws Exception {
    Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
    DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
    IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
    cipher.init(1, secretKey, iv);

    return cipher.doFinal(message.getBytes("UTF-8"));
  }

  private byte[] convertHexString(String ss) {
    byte[] digest = new byte[ss.length() / 2];
    for (int i = 0; i < digest.length; ++i)
    {
      String byteString = ss.substring(2 * i, 2 * i + 2);
      int byteValue = Integer.parseInt(byteString, 16);
      digest[i] = (byte)byteValue;
    }
    return digest;
  }

  private String toHexString(byte[] b) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < b.length; ++i) {
      String plainText = Integer.toHexString(0xFF & b[i]);
      if (plainText.length() < 2)
        plainText = "0" + plainText;
      hexString.append(plainText);
    }
    return hexString.toString();
  }
}