package com.chengjs.cjsssmsweb.cxf;

import com.chengjs.cjsssmsweb.cxf.WSSample;
import com.chengjs.cjsssmsweb.cxf.impl.WSSampleImpl;
import com.chengjs.cjsssmsweb.enums.TestEnv;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class WSServerTest {

  /**
   * 接口测试 wsdl: http://localhost:9000/wSSample?wsdl
   * @param args
   */
  public static void main(String[] args) {
    System.out.println("web service 启动中。。。");
    WSSampleImpl implementor = new WSSampleImpl();
    String address = "http://192.168.245.221:9000/wSSample";
    if (TestEnv.onTTrue) {
      address = "http://localhost:9000/wSSample";
    }
    JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
    factoryBean.setAddress(address); // 设置地址
    factoryBean.setServiceClass(WSSample.class); // 接口类
    factoryBean.setServiceBean(implementor); // 设置实现类

    factoryBean.create(); // 创建webservice接口
    System.out.println("web service 启动成功。。");
  }
}
