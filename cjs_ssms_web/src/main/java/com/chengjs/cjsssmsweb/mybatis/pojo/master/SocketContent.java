package com.chengjs.cjsssmsweb.mybatis.pojo.master;

import java.util.Date;
import javax.persistence.*;

@Table(name = "socket_content")
public class SocketContent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT replace(t.uuid,\"-\",\"\") FROM (SELECT uuid() uuid FROM dual) t")
    private String id;

    /**
     * 发送者
     */
    @Column(name = "contentsender")
    private String contentsender;

    /**
     * 聊天内容
     */
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
     * 获取发送者
     *
     * @return contentsender - 发送者
     */
    public String getContentsender() {
        return contentsender;
    }

    /**
     * 设置发送者
     *
     * @param contentsender 发送者
     */
    public void setContentsender(String contentsender) {
        this.contentsender = contentsender == null ? null : contentsender.trim();
    }

    /**
     * 获取聊天内容
     *
     * @return content - 聊天内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置聊天内容
     *
     * @param content 聊天内容
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