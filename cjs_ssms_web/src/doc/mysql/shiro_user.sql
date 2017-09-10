-- mysql 用户权限 user role permission 表结构创建

create table u_con_role_permission
(
  createtime timestamp default CURRENT_TIMESTAMP not null,
  modifytime timestamp default CURRENT_TIMESTAMP not null,
  discard char default '1' not null,
  conrpid varchar(36) not null
    primary key,
  roleid varchar(36) not null,
  perid varchar(36) not null,
  constraint u_con_role_permission_con_rp_id_uindex
  unique (conrpid)
)
;

comment on table u_con_role_permission is '系统_关联表_角色_权限'
;

comment on column u_con_role_permission.discard is '‘1’-有效 '0'-无效'
;

create table u_con_user_role
(
  createtime timestamp default CURRENT_TIMESTAMP not null,
  modifytime timestamp default CURRENT_TIMESTAMP not null,
  discard char default '1' not null,
  conurid varchar(36) not null
    primary key,
  userid varchar(36) not null,
  roleid varchar(36) not null,
  constraint u_con_user_role_con_ur_id_uindex
  unique (conurid)
)
;

comment on table u_con_user_role is '系统_关联表_用户角色'
;

comment on column u_con_user_role.discard is '‘1’-有效 '0'-无效'
;

create table u_permission
(
  description varchar(255) null,
  createtime timestamp default CURRENT_TIMESTAMP not null,
  modifytime timestamp default CURRENT_TIMESTAMP not null,
  discard char default '1' null,
  perid varchar(36) not null
    primary key,
  pername varchar(255) not null,
  constraint u_permission_per_id_uindex
  unique (perid)
)
;

comment on table u_permission is '系统_许可权限'
;

comment on column u_permission.discard is ''1'-有效 '0'-无效'
;

create table u_role
(
  description varchar(255) null,
  createtime timestamp default CURRENT_TIMESTAMP not null,
  modifytime timestamp default CURRENT_TIMESTAMP not null,
  discard char default '1' null,
  roleid varchar(36) not null
    primary key,
  rolename varchar(255) null,
  constraint u_role_role_id_uindex
  unique (roleid)
)
;

comment on table u_role is '系统_角色'
;

comment on column u_role.discard is ''1'-有效 '0'-无效'
;

create table u_user
(
  password varchar(36) not null,
  description varchar(255) null,
  createtime datetime default CURRENT_TIMESTAMP not null,
  modifytime datetime default CURRENT_TIMESTAMP not null,
  discard char default '1' null,
  userid varchar(36) not null
    primary key,
  username varchar(255) not null,
  constraint u_user_user_name_uindex
  unique (username)
)
;

comment on table u_user is '系统_用户'
;

comment on column u_user.discard is ''1'-有效的 '0'-无效的废弃的'
;

comment on column u_user.userid is '用户id'
;

