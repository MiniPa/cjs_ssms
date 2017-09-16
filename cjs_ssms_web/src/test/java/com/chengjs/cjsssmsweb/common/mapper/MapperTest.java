package com.chengjs.cjsssmsweb.common.mapper;

import com.chengjs.cjsssmsweb.common.util.codec.MD5Util;
import com.chengjs.cjsssmsweb.common.util.math.MathUtil;
import com.chengjs.cjsssmsweb.mybatis.MybatisHelper;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.UUserMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;

import java.io.IOException;
import java.util.Date;

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
      session = MybatisHelper.getSqlSession();
      UUserMapper mapper = session.getMapper(UUserMapper.class);
      UUser user = new UUser();
//      user.setId(UUIDUtil.uuid());
      user.setUsername("mapperTestUser" + NUM);
      user.setPassword(MD5Util.md5("1"));
      user.setDescription("MapperTest创建的User");
//      user.setDiscard("1");
      user.setCreatetime(new Date());
      user.setModifytime(new Date());

      //新增一条数据
      Assert.assertEquals(1, mapper.insert(user));
//      mapper.insert(user);
      /*Mapper null属性不使用数据库默认值 用selective方法即可,为null,不参与sql,数据库使用默认值*/
//      mapper.insertSelective(user);
      System.out.println("回调 user id :" + user.getId());

/*      //查询数据 等号查询
      List<UUser> users = mapper.select(user);

      //ID回写,不为空
      System.out.printf(user.getId());
      Assert.assertNotNull(user.getId());

      //通过主键删除新增的数据
      Assert.assertEquals("1", mapper.deleteByPrimaryKey(user));*/

//      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
    }

  }
}
