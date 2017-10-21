--消息列表，每次消息交互是先保存消息，后台处理消息。
drop table wx_event
select * from  wx_event
create table wx_event(
    id INTEGER,
    --app_id references wx_app,
    --event_type,
    content VARCHAR2(500),
    occure_date DATE DEFAULT  sysdate,
    disp_date DATE,
    disp_result varchar2(400),
    flag INTEGER
)

create table wx_agent(
    openId primary key ,--
    cert_id,--身份证号码
    cert_name,--身份证名称
    --tele,
    --subsidy_tele,
    address,--地址
    bank_name,--银行名称
    bank_account,--银行账号
    bank_account_name,--银行账号名称
    licence_id,--营业执照编号
    store_name --店面名称
    licence_pict_id,
    store_pict_id,
    developer_manager_id,--发展人
    maintainer_id,--维系人。
    servcer_id    --服务人员，配送人员。
}


create table wx_picture(--图片库
  id number primary key,
  remark varchar2(200),
  pict blob
)