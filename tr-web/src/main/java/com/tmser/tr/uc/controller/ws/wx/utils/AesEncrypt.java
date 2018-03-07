package com.tmser.tr.uc.controller.ws.wx.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesEncrypt implements IEncrypt
{
  private final char[] bcdLookup=
  {'0','1','2','3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  private String AES_KEY="D02E25026109A8B1";

  public String decrypt(String info)
    throws Exception
  {
    byte[] keyb = this.AES_KEY.getBytes("utf-8");
    SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    byte[] miwenBytes = hex2bytes(info);
    cipher.init(2, sKeySpec);
    byte[] bjiemihou = cipher.doFinal(miwenBytes);
    return new String(bjiemihou);
  }

  public String encrypt(String info)
    throws Exception
  {
    byte[] keyb = this.AES_KEY.getBytes("utf-8");
    SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(1, sKeySpec);
    byte[] miwen = cipher.doFinal(info.getBytes());
    return bytes2Hex(miwen);
  }

  private final String bytes2Hex(byte[] bytes)
  {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < bytes.length; ++i) {
      buf.append(this.bcdLookup[(bytes[i] >>> 4 & 0xF)]);
      buf.append(this.bcdLookup[(bytes[i] & 0xF)]);
    }
    return buf.toString();
  }

  private final byte[] hex2bytes(String s)
  {
    byte[] bytes = new byte[s.length() / 2];
    for (int i = 0; i < bytes.length; ++i)
      bytes[i] = (byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 
        16);

    return bytes;
  }
}
