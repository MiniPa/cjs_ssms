package com.chengjs.cjsssmsweb.common.pagehelper;

import com.chengjs.cjsssmsweb.mybatis.MybatisHelper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * GridMiniHelper: Miniui grid 查询帮助工具类
 * author: Chengjs, version:1.0.0, 2017-09-30
 */
public class GridMiniHelper {

  /*mapper*/
  public static Mapper getSample(Class<UUser> aClass) {
    SqlSession sqlSession = MybatisHelper.getSqlSession(); /*2.sqlSession*/
    return (Mapper) sqlSession.getMapper(aClass);
  }

  /*criteria*/
  public static Example.Criteria getCriteria(Class<UUser> aClass, Example example) {
    Example.Criteria criteria = example.createCriteria();
    return criteria;
  }



}
