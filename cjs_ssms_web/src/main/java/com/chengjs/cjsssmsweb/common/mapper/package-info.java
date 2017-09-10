/**
 * package-info: Mapper 实践经验和细节 https://github.com/abel533/Mapper
 *
 * 1.回写的UUID http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/10.Mapper-UUID.md
 * Oracle: <property name="ORDER" value="BEFORE"/> 配置如此先取到值否则报错
 *  @Id
 *  @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select uuid()")
 *  private String id;
 *
 *  @Id
 *  @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "select SEQ_ID.nextval from dual")
 *   private Integer id;
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/10
 */
package com.chengjs.cjsssmsweb.common.mapper;