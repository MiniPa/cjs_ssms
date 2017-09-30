package com.chengjs.cjsssmsweb.mybatis.mapper.common;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ISelectDao: 通用select查询方法
 *
 * 此处借助了通用Mapper,这里的Mapper<UUser>可以是任意合适的的对象.
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/12
 */
public interface ISelectDao extends Mapper<UUser>{

  /**
   * mybatis返回单一对象或对象列表的问题：
   *
   * 1.返回数据类型由dao中的接口和*map.xml文件共同决定。
   *  另外，不论是返回单一对象还是对象列表，*map.xml中的配置都是一样的，都是resultMap="*Map"*或resultType=“* .* .*”类型.
   * 2.每一次mybatis从数据库中select数据之后，都会检查数据条数和dao中定义的返回值是否匹配。
   * 3.若返回一条数据，dao中定义的返回值是一个对象或对象的List列表，则可以正常匹配，将查询的数据按照dao中定义的返回值存放。
   * 4.若返回多条数据，dao中定义的返回值是一个对象，则无法将多条数据映射为一个对象，此时mybatis报错。
   * */
  List<Map<String,String>> users(Map<String,String> params);

  List<Map<String,String>> roles(Map<String,String> params);

}




















