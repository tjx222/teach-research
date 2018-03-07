package com.tmser.tr.bjysdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by yfwang on 2017/11/8.
 */
@Component("video_settings")
public class BJYProperties {

  @Value("${vedio.siteId}")
  private String siteId;

  @Value("${vedio.key}")
  private String key;

  @Value("${vedio.switch:off}")
  private String vedioSwitch;

  @Value("${jy.host:}")
  private String jyptHost;

  /**
   * Getter method for property <tt>jyptHost</tt>.
   * 
   * @return property value of jyptHost
   */
  public String getJyptHost() {
    return jyptHost;
  }

  /**
   * Setter method for property <tt>jyptHost</tt>.
   * 
   * @param jyptHost
   *          value to be assigned to property jyptHost
   */
  public void setJyptHost(String jyptHost) {
    this.jyptHost = jyptHost;
  }

  /**
   * <p>
   * </p>
   * Getter method for property <tt>siteId</tt>.
   *
   * @return siteId String
   */
  public String getSiteId() {
    return siteId;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>siteId</tt>.
   *
   * @param siteId
   *          String value to be assigned to property siteId
   */
  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  /**
   * <p>
   * </p>
   * Getter method for property <tt>key</tt>.
   *
   * @return key String
   */
  public String getKey() {
    return key;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>key</tt>.
   *
   * @param key
   *          String value to be assigned to property key
   */
  public void setKey(String key) {
    this.key = key;
  }

  /**
   * <p>
   * </p>
   * Getter method for property <tt>vedioSwitch</tt>.
   *
   * @return vedioSwitch String
   */
  public String getVedioSwitch() {
    return vedioSwitch;
  }

  /**
   * <p>
   * </p>
   * Setter method for property <tt>vedioSwitch</tt>.
   *
   * @param vedioSwitch
   *          String value to be assigned to property vedioSwitch
   */
  public void setVedioSwitch(String vedioSwitch) {
    this.vedioSwitch = vedioSwitch;
  }

}
