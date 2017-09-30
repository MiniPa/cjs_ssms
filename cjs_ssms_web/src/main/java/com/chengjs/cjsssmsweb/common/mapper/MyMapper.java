package com.chengjs.cjsssmsweb.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * MyMapper: 其他接口继承 Mapper即可, 见com.chengjs.cjsssmsweb.mybatis.mapper.master下Mapper-generate生成的接口
 *
 * http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/3.Use.md
 *
 * 1.泛型T必须被指定为具体的类型
 *  1)驼峰 UserInfo => user_info
 *  2)@Table(name = "tableName")
 *  3)@Column(name = "fieldName")
 *  4)@Transient 字段不作为表字段使用
 *  5)@id
 *  6)实体类可以继承使用
 *  7)实体类不建议使用基本类型 如int
 *  8)@NameStype 配置pojo和表名映射
 *    normal,camelhump,uppercase,lowercase
 *
 * 2.主键策略(insert): 序列(支持Oracle)、UUID(任意数据库,字段长度32)、主键自增(类似Mysql,Hsqldb)
 *  序列和UUID可以配置多个，主键自增只能配置一个
 *
 *  @GeneratedValue(generator = "JDBC")
 *  @GeneratedValue(strategy = GenerationType.IDENTITY)
 *  @GeneratedValue(generator = "UUID")
 *
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 21:12
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {



}
