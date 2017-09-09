package com.chengjs.cjsssmsweb.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * MybatisHelper:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/9
 */
public class MybatisHelper {

  /**
   * 根据mybatis配置获取SqlSession
   * @return
   * @throws IOException
   */
  public static SqlSession session() throws IOException {
    String resource = "database/mybatis-config-javaapi.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream);
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession;
  }

}
