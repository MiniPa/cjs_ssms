package com.chengjs.cjsssmsweb.dao.master;

import com.chengjs.cjsssmsweb.pojo.WebRole;

public interface WebRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web_role
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String roleid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web_role
     *
     * @mbggenerated
     */
    int insert(WebRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web_role
     *
     * @mbggenerated
     */
    int insertSelective(WebRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web_role
     *
     * @mbggenerated
     */
    WebRole selectByPrimaryKey(String roleid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WebRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web_role
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WebRole record);
}