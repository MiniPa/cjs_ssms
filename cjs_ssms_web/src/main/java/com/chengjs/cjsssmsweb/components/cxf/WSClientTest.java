package com.chengjs.cjsssmsweb.components.cxf;

import com.chengjs.cjsssmsweb.enums.EnvEnum;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * WS2ClientTest: WebService后端UrlConnection调用
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/2
 */
public class WSClientTest {

  /**
   * 方法名
   */
  private static final String METHOD = "say";

  /**
   * 请求参数
   */
  private static final String PARAM = "chengjs";

  /**
   * 参数指代接口命名空间,可在wsdl请求返回信息中找到
   */
  private static final String SERVICE_NAMESPACE = "http://cxf.cjsssmsweb.chengjs.com/";

  /**
   * 调用接口：http://blog.csdn.net/u011165335/article/details/51345224
   * http://www.cnblogs.com/siqi/archive/2013/12/15/3475222.html
   * @param args
   */
  public static void main(String[] args) throws IOException {
    //服务的地址
    URL wsUrl = new URL(EnvEnum.IP9000.getVal()+"wSSample");

    HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();

    conn.setDoInput(true);
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

    OutputStream os = conn.getOutputStream();

    //请求体
    String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
        "xmlns:q0=\"" + SERVICE_NAMESPACE + "\" " +
        "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
        "<soapenv:Body> <q0:" + METHOD + "><arg0>" + PARAM + "</arg0>  </q0:" + METHOD + "> </soapenv:Body> </soapenv:Envelope>";

    os.write(soap.getBytes());

    InputStream is = conn.getInputStream();

    byte[] b = new byte[1024];
    int len = 0;
    String s = "";
    while((len = is.read(b)) != -1){
      String ss = new String(b,0,len,"UTF-8");
      s += ss;
    }
    System.out.println(s);
    /*
    * 返回请求值
    * <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Body>
    *   <ns2:sayResponse xmlns:ns2="http://cxf.cjsssmsweb.chengjs.com/">
    *     <return>Hellochengjs</return>
    *     </ns2:sayResponse>
    * </soap:Body></soap:Envelope>
    * */

    is.close();
    os.close();
    conn.disconnect();


  }
}
