package com.tmser.tr.uc.controller.ws.wx.utils;


public class Platform
{
  private static IEncrypt encrypt = new AesEncrypt();

  public static String encrypt(String info)
    throws Exception
  {
    return encrypt.encrypt(info);
  }

  public static String decrypt(String info) throws Exception
  {
    return encrypt.decrypt(info);
  }
  public static void main(String[] args) throws Exception {
//	System.out.println(encrypt("50197_123456789123456789"));
	System.out.println(decrypt("6738ADC1638A2EACE3E7B55A000323E3CE43112A9E3FD24473030EC86959F5198B5689AC7E1EBEE1501FD016B3E47D2D"));
//	System.out.println(Encodes.md5("76c87f8473084804b098e47c708cda46zhulin2016-01-181231q2w3eQWE\\qw"));
}
  //71B6A222C6518B5CC652C895FF10D5F80E330BFC1898A34E9759D230D26E36BFF25EB3215DA4FE8F37559AD814F85392&appid=a8b43dc1-ab1e-4b4a-b241-d0eedd996c17&appkey=43433f2c-d635-43a8-b7f1-1a9ec6239697

}
