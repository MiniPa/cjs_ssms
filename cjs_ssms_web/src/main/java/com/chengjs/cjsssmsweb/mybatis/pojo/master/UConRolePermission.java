package com.chengjs.cjsssmsweb.mybatis.pojo.master;

import java.util.Date;
import javax.persistence.*;

@Table(name = "u_con_role_permission")
public class UConRolePermission {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT replace(t.uuid,\"-\",\"\") FROM (SELECT uuid() uuid FROM dual) t")
    private String id;

    @Column(name = "roleid")
    private String roleid;

    @Column(name = "perid")
    private String perid;

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
     * @return perid
     */
    public String getPerid() {
        return perid;
    }

    /**
     * @param perid
     */
    public void setPerid(String perid) {
        this.perid = perid == null ? null : perid.trim();
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