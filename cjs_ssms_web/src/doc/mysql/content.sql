create table redis_content
(
  contentid varchar(36) not null
    primary key,
  content varchar(255) null,
  createtime timestamp default CURRENT_TIMESTAMP not null
)
;

create table socket_content
(
  contentid varchar(36) not null
    primary key,
  contentSender varchar(50) null,
  content varchar(1000) null,
  createtime timestamp default CURRENT_TIMESTAMP not null,
  constraint socket_content_contentid_uindex
  unique (contentid)
)
;

comment on table socket_content is 'socket记录'
;

comment on column socket_content.contentSender is '发送者'
;

comment on column socket_content.content is '聊天内容'
;

