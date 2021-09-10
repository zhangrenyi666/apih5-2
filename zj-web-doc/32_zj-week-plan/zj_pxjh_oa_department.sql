DROP TABLE IF EXISTS zj_pxjh_oa_department;
CREATE TABLE  zj_pxjh_oa_department (
	zc_oa_id	char(32) DEFAULT '' COMMENT '部门人员关系表id',
	other_id	char(32) DEFAULT '' COMMENT '其他关联表id',
	other_type	char(2) DEFAULT '' COMMENT '其他类型',
	oa_department_member_flag	char(32) DEFAULT '' COMMENT '组织人员区分标识',
	oa_user_id	varchar(100) DEFAULT '' COMMENT 'oaUserId',
	oa_user_Name	varchar(100) DEFAULT '' COMMENT 'oaUserName',
	oa_org_id	varchar(100) DEFAULT '' COMMENT 'oaOrgId',
	order_flag	char(3) DEFAULT '' COMMENT '排序flag',
	del_flag	char(1) DEFAULT '' COMMENT '削除FLG',
	create_time	datetime DEFAULT NULL COMMENT '创建时间',
	create_user	char(32) DEFAULT '' COMMENT '创建者',
	create_user_name	varchar(100) DEFAULT '' COMMENT '创建者姓名',
	modify_time	datetime DEFAULT NULL COMMENT '更新时间',
	modify_user	char(32) DEFAULT '' COMMENT '更新者',
	modify_user_name	varchar(100) DEFAULT '' COMMENT '更新者姓名',
PRIMARY KEY ( zc_oa_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='OA部门表';
