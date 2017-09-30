package com.chengjs.cjsssmsweb.mybatis.pojo.master;

import java.util.Date;
import javax.persistence.*;

@Table(name = "redis_content")
public class RedisContent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT replace(t.uuid,\"-\",\"\") FROM (SELECT uuid() uuid FROM dual) t")
    private String id;

    @Column(name = "content")
    private String content;

    @Column(name = "createtime")
    private Date createtime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}