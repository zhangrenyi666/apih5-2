DROP TABLE IF EXISTS zj_pxhy_historical_records;
CREATE TABLE  zj_pxhy_historical_records (
	historical_records_id	char(32) DEFAULT '' COMMENT '主键ID',
	plan_id	varchar(32) DEFAULT '' COMMENT '日程表关联键',
	modify_content_before	varchar(500) DEFAULT '' COMMENT '修改前内容',
	modify_content	varchar(500) DEFAULT '' COMMENT '修改内容',
	del_flag	char(1) DEFAULT '' COMMENT '删除标识',
	create_time	datetime DEFAULT NULL COMMENT '创建时间',
	create_user	char(32) DEFAULT '' COMMENT '创建者',
	create_user_name	varchar(100) DEFAULT '' COMMENT '创建者姓名',
	modify_time	datetime DEFAULT NULL COMMENT '更新时间',
	modify_user	char(32) DEFAULT '' COMMENT '更新者',
	modify_user_name	varchar(100) DEFAULT '' COMMENT '更新者姓名',
PRIMARY KEY ( historical_records_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行为记录表';
