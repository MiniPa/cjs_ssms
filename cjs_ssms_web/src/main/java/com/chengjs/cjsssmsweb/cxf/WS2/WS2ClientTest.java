package com.chengjs.cjsssmsweb.cxf.WS2;

import com.chengjs.cjsssmsweb.cxf.WSSample;
import com.chengjs.cjsssmsweb.enums.EnvEnum;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * WS2ClientTest:客户端编程的方式调用Webservice服务
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/2
 */
public class WS2ClientTest {

  /**
   * 请求参数
   */
  private static final String PARAM = "chengjs";

  /**
   * 参数指代接口命名空间,可在wsdl请求返回信息中找到
   */
  private static final String SERVICE_NAMESPACE = "http://cxf.cjsssmsweb.chengjs.com/";

  /**
   * 服务名称
   */
  private static final String SERVICE_NAME = "WSSampleService";



  public static void main(String[] args) throws Exception {
    URL wsdlUrl = new URL(EnvEnum.IP9000.getVal()  + "wSSample?wsdl");
    Service s = Service.create(wsdlUrl, new QName(SERVICE_NAMESPACE,"WSSampleService"));
    WSSample hs = s.getPort(new QName(SERVICE_NAMESPACE,"WSSamplePort"), WSSample.class);
    String ret = hs.say("chengjs");
    System.out.println(ret);
  }


}
