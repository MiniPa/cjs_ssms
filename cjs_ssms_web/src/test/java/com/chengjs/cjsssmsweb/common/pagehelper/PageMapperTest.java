package com.chengjs.cjsssmsweb.common.pagehelper;

import com.chengjs.cjsssmsweb.mybatis.MybatisHelper;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.CountryMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.Country;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * PageMapperTest:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/10
 */
public class PageMapperTest {

  @Test
  public void test(){

    SqlSession sqlSession = MybatisHelper.getSqlSession();
    CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);

    Example example = new Example(Country.class);

    example.createCriteria().andGreaterThan("id",100);
    PageHelper.startPage(2,10);

    /*SELECT Id,countryname,countrycode FROM country WHERE ( Id > ? )*/
    List<Country> countries = countryMapper.selectByExample(example);
    PageInfo<Country> pageInfo = new PageInfo<Country>(countries);
    System.out.println(pageInfo.getTotal());

    countries = countryMapper.selectByExample(example);
    pageInfo = new PageInfo<Country>(countries);
    System.out.println(pageInfo.getTotal());

  }

}
