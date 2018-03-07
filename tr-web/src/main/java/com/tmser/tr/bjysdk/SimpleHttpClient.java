package com.tmser.tr.bjysdk;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.tmser.tr.bjysdk.exception.VideoException;

/**
 * Created by yfwang on 2017/11/15.
 */
public class SimpleHttpClient {

  public static String sendMessage(String url, Map<String, String> map) throws VideoException {
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(url);
    List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    if (map != null) {
      for (String key : map.keySet()) {
        nvps.add(new BasicNameValuePair(key, map.get(key)));
      }
    }
    httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
    httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
    httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
    return executeMessage(client, httpPost);
  }

  public static String sendFileMessage(String url, Map<String, String> map, File file, String fileName)
      throws VideoException {
    CloseableHttpClient client = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost(url);
    FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY, fileName
        + file.getName().substring(file.getName().lastIndexOf(".")));
    MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
    reqEntity.setCharset(Charset.forName("UTF-8"));
    reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    reqEntity.addPart("attachment", fileBody);
    for (String key : map.keySet()) {
      StringBody paramBody = new StringBody(map.get(key), ContentType.TEXT_PLAIN);
      reqEntity.addPart(key, paramBody);
    }
    httpPost.setEntity(reqEntity.build());
    return executeMessage(client, httpPost);
  }

  public static String executeMessage(CloseableHttpClient client, HttpPost httpPost) {
    String body = "";
    CloseableHttpResponse response = null;
    try {
      response = client.execute(httpPost);
    } catch (IOException e) {
      throw new VideoException("sendMessage Error", e);
    }
    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
      HttpEntity entity = response.getEntity();
      if (entity == null) {
        throw new VideoException("response HttpEntity is null");
      }
      try {
        body = EntityUtils.toString(entity, Charset.forName("UTF-8"));
        EntityUtils.consume(entity);
      } catch (Exception e) {
        throw new VideoException("entity parse error", e);
      }
      try {
        response.close();
      } catch (IOException e) {
        throw new VideoException("response close Error", e);
      }
    }
    return body;
  }

}
