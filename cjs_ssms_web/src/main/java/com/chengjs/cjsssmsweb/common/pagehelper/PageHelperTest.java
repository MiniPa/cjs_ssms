package com.chengjs.cjsssmsweb.common.pagehelper;

import com.chengjs.cjsssmsweb.mybatis.MybatisHelper;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.CountryMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.Country;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.List;

/**
 * PageHelperTest:
 *
 * https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/en/HowToUse.md
 *
 * Criteria: http://www.cnblogs.com/kangping/p/6001519.html
 *
 *
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/9
 */
public class PageHelperTest {
  private static final Logger log = LoggerFactory.getLogger(PageHelperTest.class);

  public static void main(String[] args) throws IOException {

    SqlSession sqlSession = MybatisHelper.getSqlSession();
    CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);

    /*rowBounds*/
    RowBounds rowBounds = new RowBounds(2, 5);
    /*Example*/
    Example example = new Example(Country.class);
    Example.Criteria criteria = example.createCriteria();

//    criteria.andCountrycodeBetween("0", "ZZZZZZZZZZ");
//    criteria.andIdBetween(0, 20);



    List<Country> countries = countryMapper.selectByExampleAndRowBounds(example, rowBounds);
    PageInfo<Country> pageInfo = new PageInfo<>(countries);

    System.out.println("PageHelperTest.main() pageInfo :" + pageInfo);

  }

      /*3.Example*/
//    Example example = new Example(pojo_clz);
    /*3.params附着条件, Criteria之间是AND关系, oredCriteria中的Criteria是OR关系*/
//    Example.Criteria criteria1 = example.createCriteria();
//    criteria.andCountrycodeBetween("0", "ZZZZZZZZZZ");
//    criteria.andIdBetween(0, 20);


//    Object obj = mapper_clz.newInstance();
//    Method m = obj.getClass().getDeclaredMethod(strs[1], String.class);
//    String  result = (String) m.invoke(obj, "aaaaa");
//    Method m = mapper.getClass().getMethod(method, new Class[]{Map.class});
//    Object result = m.invoke(mapper, params);

}
