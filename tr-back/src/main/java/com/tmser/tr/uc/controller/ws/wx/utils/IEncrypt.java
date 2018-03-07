package com.tmser.tr.uc.controller.ws.wx.utils;


public abstract interface IEncrypt
{
  public abstract String decrypt(String paramString)
    throws Exception;

  public abstract String encrypt(String paramString)
    throws Exception;
}