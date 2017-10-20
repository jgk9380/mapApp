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