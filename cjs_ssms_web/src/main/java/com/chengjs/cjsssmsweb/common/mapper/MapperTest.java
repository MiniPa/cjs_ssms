package com.chengjs.cjsssmsweb.common.mapper;

import com.chengjs.cjsssmsweb.mybatis.MybatisHelper;
import com.chengjs.cjsssmsweb.common.util.codec.MD5Util;
import com.chengjs.cjsssmsweb.common.util.math.MathUtil;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.WebUserMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.WebUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;

import java.io.IOException;

/**
 * MapperTest:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/9
 */
public class MapperTest {

  private static final String NUM = MathUtil.genRandNum(4);

  public static void main(String[] args) throws IOException {

    SqlSession session = null;
    try {
      session = MybatisHelper.session();
      WebUserMapper mapper = session.getMapper(WebUserMapper.class);
      WebUser webUser = new WebUser();
      webUser.setUsername("mapperTestUser" + NUM);
      webUser.setPassword(MD5Util.md5("1"));

      //新增一条数据
      Assert.assertEquals(1, mapper.insert(webUser));
      //ID回写,不为空
      Assert.assertNotNull(webUser.getUserid());
      //通过主键删除新增的数据
      Assert.assertEquals("mapperTestUser" + NUM, mapper.deleteByPrimaryKey(webUser));
    } finally {
      session.close();
    }

  }
}
