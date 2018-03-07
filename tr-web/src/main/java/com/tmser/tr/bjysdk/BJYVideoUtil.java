package com.tmser.tr.bjysdk;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tmser.tr.bjysdk.exception.VideoException;
import com.tmser.tr.bjysdk.model.ParamKV;

/**
 * Created by yfwang on 2017/11/7.
 */
public class BJYVideoUtil {

  public static Map<String, String> hander(List<ParamKV> parames, String partnerKey) throws VideoException {
    Collections.sort(parames);
    StringBuilder temp = new StringBuilder();

    Map<String, String> paramMap = new HashMap<String, String>();

    for (ParamKV paramKV : parames) {
      temp.append((temp.toString().equals("") ? "" : "&") + paramKV.getKey() + "=" + paramKV.getValue());
      paramMap.put(paramKV.getKey(), paramKV.getValue());
    }
    temp.append("&partner_key=" + partnerKey);
    try {
      String sign = md5(temp.toString());
      paramMap.put("sign", sign);
      return paramMap;
    } catch (Exception e) {
      throw new VideoException("sign get failed,temp=" + temp);
    }
  }

  public static String toString(Map<String, String> params) {
    if (params == null || params.size() == 0) {
      return "";
    }
    StringBuilder temp = new StringBuilder();
    for (String key : params.keySet()) {
      temp.append((temp.toString().equals("") ? "" : "&") + key + "=" + params.get(key));
    }
    return temp.toString();
  }

  public static String md5(String param) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(param.getBytes("UTF-8"));
    byte[] bytes = md5.digest();
    StringBuffer sb = new StringBuffer();
    for (byte b : bytes) {
      String s = Integer.toHexString(0xff & b);
      if (s.length() == 1) {
        sb.append("0" + s);
      } else {
        sb.append(s);
      }
    }
    String sign = sb.toString();
    return sign;
  }
}
