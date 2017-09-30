package com.chengjs.cjsssmsweb.mybatis.pojo.master;

import java.util.Date;
import javax.persistence.*;

@Table(name = "u_con_user_role")
public class UConUserRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT replace(t.uuid,\"-\",\"\") FROM (SELECT uuid() uuid FROM dual) t")
    private String id;

    @Column(name = "userid")
    private String userid;

    @Column(name = "roleid")
    private String roleid;

    @Column(name = "createtime")
    private Date createtime;

    @Column(name = "modifytime")
    private Date modifytime;

    @Column(name = "discard")
    private String discard;

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
     * @return userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    /**
     * @return roleid
     */
    public String getRoleid() {
        return roleid;
    }

    /**
     * @param roleid
     */
    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
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

    /**
     * @return modifytime
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return discard
     */
    public String getDiscard() {
        return discard;
    }

    /**
     * @param discard
     */
    public void setDiscard(String discard) {
        this.discard = discard == null ? null : discard.trim();
    }
}