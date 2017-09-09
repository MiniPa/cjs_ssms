package com.chengjs.cjsssmsweb.common.pagehelper;

import com.chengjs.cjsssmsweb.mybatis.MybatisHelper;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.CountryMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * PageHelperTest:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/9
 */
public class PageHelperTest {
  private static final Logger log = LoggerFactory.getLogger(PageHelperTest.class);

  public static void main(String[] args) throws IOException {
    SqlSession sqlSession = MybatisHelper.session();
    CountryMapper countryMapper = (CountryMapper)sqlSession.getMapper(CountryMapper.class);

  }

}
