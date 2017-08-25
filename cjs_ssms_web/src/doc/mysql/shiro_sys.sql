-- mysql 用户权限 user role permission 表结构创建

CREATE TABLE sys_con_role_permission
(
  con_rp_id  VARCHAR(36)                         NOT NULL
    PRIMARY KEY,
  role_id    VARCHAR(36)                         NOT NULL,
  per_id     VARCHAR(36)                         NOT NULL,
  createtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifytime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  discard    CHAR DEFAULT '1'                    NOT NULL,
  CONSTRAINT sys_con_role_permission_con_rp_id_uindex
  UNIQUE (con_rp_id)
);

COMMENT ON TABLE sys_con_role_permission IS '系统_关联表_角色_权限'
;

COMMENT ON COLUMN sys_con_role_permission.discard IS '‘1’-有效 '0'-无效'
;

CREATE TABLE sys_con_user_role
(
  con_ur_id  VARCHAR(36)                         NOT NULL
    PRIMARY KEY,
  user_id    VARCHAR(36)                         NOT NULL,
  role_id    VARCHAR(36)                         NOT NULL,
  createtime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifytime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  discard    CHAR DEFAULT '1'                    NOT NULL,
  CONSTRAINT sys_con_user_role_con_ur_id_uindex
  UNIQUE (con_ur_id)
);

COMMENT ON TABLE sys_con_user_role IS '系统_关联表_用户角色'
;

COMMENT ON COLUMN sys_con_user_role.discard IS '‘1’-有效 '0'-无效'
;

CREATE TABLE sys_permission
(
  per_id      VARCHAR(36)                         NOT NULL
    PRIMARY KEY,
  per_name    VARCHAR(255)                        NOT NULL,
  description VARCHAR(255)                        NULL,
  createtime  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifytime  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  discard     CHAR DEFAULT '1'                    NULL,
  CONSTRAINT sys_permission_per_id_uindex
  UNIQUE (per_id)
);

COMMENT ON TABLE sys_permission IS '系统_许可权限'
;

COMMENT ON COLUMN sys_permission.discard IS ''1'-有效 '0'-无效'
;

CREATE TABLE sys_role
(
  role_id     VARCHAR(36)                         NOT NULL
    PRIMARY KEY,
  role_name   VARCHAR(255)                        NULL,
  description VARCHAR(255)                        NULL,
  modifytime  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  discard     CHAR DEFAULT '1'                    NULL,
  createtime  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  CONSTRAINT sys_role_role_id_uindex
  UNIQUE (role_id)
);

COMMENT ON TABLE sys_role IS '系统_角色'
;

COMMENT ON COLUMN sys_role.discard IS ''1'-有效 '0'-无效'
;

CREATE TABLE sys_user
(
  createtime  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  modifytime  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
  discard     CHAR DEFAULT '1'                   NULL,
  user_id     VARCHAR(36)                        NOT NULL
    PRIMARY KEY,
  user_name   VARCHAR(255)                       NOT NULL,
  password    VARCHAR(36)                        NOT NULL,
  description VARCHAR(255)                       NULL
);

COMMENT ON TABLE sys_user IS '系统_用户'
;

COMMENT ON COLUMN sys_user.discard IS ''1'-有效的 '0'-无效的废弃的'
;

COMMENT ON COLUMN sys_user.user_id IS '用户id'
;

