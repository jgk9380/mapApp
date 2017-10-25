select * from wx_app

create table WX_APP   
(
  app_name         VARCHAR2(20) ,
  app_id           VARCHAR2(100) primary key,
  app_secret       VARCHAR2(100),
  app_token        VARCHAR2(100),
  user_name        VARCHAR2(100),
  token            VARCHAR2(400),
  token_date       DATE,
  toke_expire      INTEGER,
  remark           VARCHAR2(200),
  js_ticket        VARCHAR2(400),
  js_ticket_date   DATE,
  js_ticket_expire INTEGER,
  welcome_title    VARCHAR2(400),
  welcome_pict_url VARCHAR2(400),
  welcome_url      VARCHAR2(400),
  welcome_desc     VARCHAR2(400)
)


select * from  wx_interface_msg
create table wx_interface_msg(
    id INTEGER,
    --app_id references wx_app,
    --event_type,
    msg_type varchar2(30) ,--not null,
    event_type varchar2(30),
    from_user_open_id varchar2(60),-- not null,
    content VARCHAR2(500),
    occure_date DATE DEFAULT  sysdate,
    disp_date DATE,
    disp_result varchar2(400),
    flag INTEGER
)

select * from wx_user

create table WX_USER
(
  id               INTEGER primary key,
  app_id           VARCHAR2(100) not null,
  open_id          VARCHAR2(60) not null,
  nickname         VARCHAR2(100),
  sex              VARCHAR2(6),
  country          VARCHAR2(40),
  province         VARCHAR2(40),
  city             VARCHAR2(40),
  language         VARCHAR2(40),
  headimgurl       VARCHAR2(300),
  user_group       VARCHAR2(60),
  subscribe_status NUMBER,--订购状态
  subscribe_date   DATE,--订购日期
  tele             VARCHAR2(11),
  long_name        VARCHAR2(40),--真实姓名
  mail_addr        VARCHAR2(200),--邮寄地址
  promotion_code   VARCHAR2(20),
  referee_id       references wx_user,--推荐人
  user_type        VARCHAR2(40),--用户类型
  update_date      DATE,--上次更新时间
  last_login_date  DATE,--上次交互时间   更新？？？
)

--文章仓库
  --1，记录点赞
  --2，记录转发及阅读人
  --3,评论及回复。
 alter table   wx_article rename to wx_article_bak
  
create table wx_article (
  id integer primary key ,
  title varchar2(100),--html格式输入
  clob varchar2(4000),--html格式输入
  type integer default 3, --1新闻,2知识，3、其他。
  --提供三类模板，其他直接嵌套content。模板底部点赞及评论。
  creater varchar2(20), -- references emp,
  create_date date,--创建日期。
  praise_count integer,--点赞次数。
  read_count integer,  --阅读数。
  expires_date date --过期时间，默认3天有效器。
)

alter table WX_ARTICLE_READ_HISTORY rename  to WX_ARTICLE_READ_HISTORY_bak

create table WX_ARTICLE_share_HISTORY   --文章转发历史。因为转发可能没有被阅读，所以有保留必要。
(
  id         INTEGER   primary key,
  article_id references wx_article,
  sharer_open_id     INTEGER,      --转发人 可能没有关注。
  share_type varchar(20),--朋友圈，朋友
  share_date  DATE default sysdate  --记录最后一次阅读时间。
)

create table WX_ARTICLE_READ_HISTORY   --文章阅读历史。
(
  id         INTEGER   primary key,
  article_id references wx_article,
  reader_open_id    INTEGER,       --阅读人 openId ,可能没有关注。
  sharer_open_id     INTEGER,      --转发人 可能没有关注。
  praise integer default 0,        --点赞只能一次。
  read_count integer default 1,    --记录同一个文章，同一个阅读次数。主要用于sharer为空的情况。
  read_date  DATE default sysdate  --记录最后一次阅读时间。
)




create table wx_article_discuss(   --文章评论历史。
  id         INTEGER   primary key,
  article_id references wx_article,
  discusser_open_id varchar2(100) not null,
  discuss_content varchar2(2000) not null,
  parent_id references wx_article_discuss ,--为空表示为评论，不为空为回复。
  discuss_date date default sysdate --
)



create table wx_agent(
    id  integer  primary key ,
    wx_user_id integer unique references wx_user,--对应的微信用户ID
    cert_id varchar2(18),--身份证号码
    cert_name varchar2(12),--身份证名称
    --tele,--wx_user中保存，赠送话费的号码。
    --subsidy_tele,
    address varchar2(200),--地址
    bank_name varchar2(40),--银行名称
    bank_account varchar2(60),--银行账号
    bank_account_name varchar2(20),--银行账号户主名称
    licence_id varchar2(100),--营业执照编号
    store_name varchar2(100),--店面名称
    licence_pict_id references wx_picture,
    store_pict_id references wx_picture,
    developer_manager_id  varchar2(20) ,--references emp,--发展人
    maintainer_id  varchar2(20) ,--references emp,--微信人
    servcer_id  varchar2(20) --references emp,  --服务人员，配送人员。    
)

create table wx_order_tele( --用户下单时联系电话,用于统计订单由谁发展的。
    id integer primary key,
    tele varchar2(11) not null,--一个号码在同一天同一个业务出现一次
    sharer_wx_user_id references wx_user,--扫码来源
    open_id varchar2(40),--扫码的用户
    type integer ,--业务类型 1， 大王卡，2，冰洁林 ，3其他。
    create_date  date default sysdate--
)

create table wx_picture(   --图片库
  id number primary key,
  remark varchar2(200),
  pict blob 
)

--二维码扫描流程。
--1、无wx_user_id,注册agent_id信息。
--2、已注册的二维码，跳出业务介绍页面（关注），引导订购。
--3、

create table wx_perm_qrcode(
    --永久二维码
    id number primary key,
    ticket varchar2(400),
    scene_id integer unique,	--场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
    url varchar2(200),
    created_date date default sysdate,
    owner_id  varchar2(20),-- references  emp;
    wx_user_id references wx_user,
    type integer default 1   --用途 1,2,3
)
 

create table wx_temp_qrcode(
    id number primary key,
    scene_id integer unique,	--场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
    --scene_str,统一用scene_id
    ticket varchar2(200),
    url varchar2(100),
    created_date date default sysdate,
    user_id references wx_user not null,--wxUserId
    type integer,--用途
    expire_seconds integer	--该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
)

select * from wx_manual_message

create table  wx_manual_message (
--ready 微信人工回复信息
    id number primary key,
    wx_user_id references wx_user,
    content varchar2(400),
    received_date date default sysdate,
    reply_content varchar2(400),
    reply_date date,
    replyer varchar2(50) ,--references emp
    reply_flag number(1) default 0
)


select * from wx_manual_message

create table wx_b2i_order(--订单信息，人工导入，
    id integer primary key,
    tele varchar2(11),--dd
    create_date date default sysdate,
    cert_id varchar2(20),
    cert_name varchar2(12),
    address varchar2(100),  --派送地址
    mail_type integer,  --派送方式，1，上门，2，现场，3线下。
    mailer varchar2(20),    -- references emp 派送人。
    --channel_id varchar2(20),--是否放到对应人员中，发送相关链接是指定channel_id
    assigner varchar2(20)    -- references emp 派单人或发展人、邮寄回访人
)


create table wx_b2i_maintain_task(
--维系信息，人工导入,用于维系任务推送。微信或钉钉推送。
    tele varchar2(11),
    name varchar2(10),
    second_tele varchar2(11),
    stop_date date, --停机时间
    create_date date  default sysdate,
    maintainer varchar(10),-- references emp,--维系人
    fee number,       --缴费金额
    fee_date date ,  --缴费数量
    push_flag integer  --是否推送成功 0,1
)


--留言箱，每小时发送一次 最近动作（wx_user last_login_date）24小时内的用户。

drop table wx_mail

create table wx_mail(
  id integer primary key,
  content varchar2(600),
  wx_user_id references wx_user,
  flag integer , --0,待发送，-1，发送失败，1，发送成功。
  send_count integer default 0,--超过3次不再发送。
  fail_reason varchar2(40)--失败原因 
)
  





-- --每日微信推送报表
--   1、渠道经理
--   2、代理商，
--   3、2i代理商。
--   3、网格，
--   4、业务单元，
--   5、客户群。
--   6、其他人员。






