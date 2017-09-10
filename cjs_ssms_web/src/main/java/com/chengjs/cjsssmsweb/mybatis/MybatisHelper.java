package com.chengjs.cjsssmsweb.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.SqlServerMapper;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;

/**
 * MybatisHelper:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/9
 */
public class MybatisHelper {

  private static SqlSessionFactory sqlSessionFactory;

  static {
    try {
      /*SqlSessionFactory*/
      Reader reader = Resources.getResourceAsReader("database/mybatis-config-javaapi.xml");
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      reader.close();

      /*创建数据库*/
      SqlSession session = null;
      try {
        session = sqlSessionFactory.openSession();
        /*创建一个MapperHelper*/
        MapperHelper mapperHelper = new MapperHelper();

        /*=================特殊配置=================*/
        Config config = new Config();

        // 设置UUID生成策略
        // 配置UUID生成策略需要使用OGNL表达式
        // 默认值32位长度:@java.util.UUID@randomUUID().toString().replace("-", "")
        config.setUUID("");

        // 主键自增回写方法,默认值MYSQL,详细说明请看文档
        // config.setIDENTITY("MYSQL");
        config.setIDENTITY("select uuid()");

        // 支持方法上的注解
        // 3.3.1版本增加
        config.setEnableMethodAnnotation(true);
        config.setNotEmpty(true);

        //校验Example中的类型是否一致
        config.setCheckExampleEntityClass(true);

        //启用简单类型
        config.setUseSimpleType(true);

        // 序列的获取规则,使用{num}格式化参数，默认值为{0}.nextval，针对Oracle
        // 可选参数一共3个，对应0,1,2,分别为SequenceName，ColumnName, PropertyName
        //config.setSeqFormat("NEXT VALUE FOR {0}");

        // 设置全局的catalog,默认为空，如果设置了值，操作表时的sql会是catalog.tablename
        //config.setCatalog("");

        // 设置全局的schema,默认为空，如果设置了值，操作表时的sql会是schema.tablename
        // 如果同时设置了catalog,优先使用catalog.tablename
        //config.setSchema("");

        // 主键自增回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)
        config.setOrder("BEFORE");

        //设置配置
        mapperHelper.setConfig(config);
        /*=================特殊配置=================*/

        /*注册通用Mapper接口 - 可以自动注册继承的接口*/
        mapperHelper.registerMapper(Mapper.class);
        mapperHelper.registerMapper(MySqlMapper.class);

        /*配置完成后，执行下面的操作*/
        mapperHelper.processConfiguration(session.getConfiguration());

      } catch (Exception e) {
        throw e;
      } finally {
        if (session != null) {
          session.close();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static SqlSession getSqlSession() {
    return sqlSessionFactory.openSession();
  }

  /**
   * 根据mybatis配置获取SqlSession,这个方法在测试Mapper时候不是很靠谱,需检测
   *
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
