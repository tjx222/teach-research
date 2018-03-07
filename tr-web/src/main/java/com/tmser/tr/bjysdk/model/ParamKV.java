package com.tmser.tr.bjysdk.model;

/**
 * Created by yfwang on 2017/11/7.
 */
public class ParamKV implements Comparable<Object> {

  private String key;
  private String value;

  public ParamKV(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return super.toString();
  }

  @Override
  public int compareTo(Object o) {
    ParamKV obj = (ParamKV) o;
    return this.key.compareTo(obj.key);
  }
}
