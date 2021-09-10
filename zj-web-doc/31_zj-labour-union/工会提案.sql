/*
Navicat MySQL Data Transfer

Source Server         : MySQL8.0.15
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : apih5-zxlw

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-12-20 16:32:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `zj_labour_union_flow_application`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labour_union_flow_application`;
CREATE TABLE `zj_labour_union_flow_application` (
  `application_id` char(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `application_name` varchar(200) DEFAULT NULL COMMENT '立案审批申请名称',
  `application_content` text COMMENT '申请内容',
  `sort` int(5) DEFAULT NULL COMMENT '排序',
  `opinion_field1` text COMMENT '备注',
  `opinion_field2` text COMMENT '备注',
  `opinion_field3` text COMMENT '备注',
  `opinion_field4` text COMMENT '备注',
  `opinion_field5` text COMMENT '备注',
  `opinion_field6` text COMMENT '备注',
  `opinion_field7` text COMMENT '备注',
  `opinion_field8` text COMMENT '备注',
  `opinion_field9` text COMMENT '备注',
  `opinion_field10` text COMMENT '备注',
  `apih5_flow_id` char(32) DEFAULT NULL COMMENT '流程id',
  `work_id` char(32) DEFAULT NULL COMMENT 'work_id',
  `apih5_flow_status` char(2) DEFAULT NULL COMMENT '工序审核状态',
  `apih5_flow_node_status` char(2) DEFAULT NULL COMMENT '流程状态',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`application_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='立案流程申请表';

-- ----------------------------
-- Records of zj_labour_union_flow_application
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labour_union_implement_dept`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labour_union_implement_dept`;
CREATE TABLE `zj_labour_union_implement_dept` (
  `dept_id` char(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `value` char(64) DEFAULT NULL COMMENT '主键ID',
  `label` varchar(200) DEFAULT NULL COMMENT '节点内容（名称）',
  `title` varchar(200) DEFAULT NULL COMMENT '保留',
  `type` char(3) DEFAULT NULL COMMENT '节点类型',
  `value_pid` varchar(200) DEFAULT NULL COMMENT '父节点ID',
  `personnel_type` char(2) DEFAULT NULL COMMENT '人员类型',
  `proposal_id` char(32) DEFAULT NULL COMMENT '提案表主键',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='落实部门表';

-- ----------------------------
-- Records of zj_labour_union_implement_dept
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labour_union_process_record`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labour_union_process_record`;
CREATE TABLE `zj_labour_union_process_record` (
  `record_id` char(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `operate_desc` text COMMENT '操作描述',
  `operate_date` datetime DEFAULT NULL COMMENT '操作时间',
  `operator_key` varchar(100) DEFAULT NULL COMMENT '操作人',
  `operator_name` varchar(100) DEFAULT NULL COMMENT '操作人姓名',
  `operator_level` varchar(100) DEFAULT NULL COMMENT '操作人级别',
  `receiver_key` varchar(100) DEFAULT NULL COMMENT '接收单位/人',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '接收单位/人名称',
  `opinion` text COMMENT '意见',
  `status` char(5) DEFAULT NULL COMMENT '提案状态',
  `status_name` varchar(100) DEFAULT NULL COMMENT '提案状态名称',
  `sort` int(5) DEFAULT NULL COMMENT '排序',
  `proposal_id` char(32) DEFAULT NULL COMMENT '提案表主键',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提案过程流转记录表';

-- ----------------------------
-- Records of zj_labour_union_process_record
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labour_union_proposal`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labour_union_proposal`;
CREATE TABLE `zj_labour_union_proposal` (
  `proposal_id` char(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `proposal_name` varchar(300) DEFAULT NULL COMMENT '提案名称',
  `proposal_reason` varchar(500) DEFAULT NULL COMMENT '案由',
  `proposal_date` datetime DEFAULT NULL COMMENT '提案日期',
  `proposer` varchar(20) DEFAULT NULL COMMENT '提案人姓名',
  `session` char(5) DEFAULT NULL COMMENT '届次',
  `session_name` varchar(200) DEFAULT NULL COMMENT '届次名称',
  `belong_type` char(5) DEFAULT NULL COMMENT '所属类别',
  `belong_type_name` varchar(100) DEFAULT NULL COMMENT '所属类别名称',
  `belong_group` char(5) DEFAULT NULL COMMENT '所属团组',
  `belong_group_name` varchar(200) DEFAULT NULL COMMENT '所属团组名称',
  `belong_company` varchar(300) DEFAULT NULL COMMENT '所属公司',
  `belong_company_name` varchar(300) DEFAULT NULL COMMENT '所属公司名称',
  `rectify_suggest` varchar(1000) DEFAULT NULL COMMENT '整改建议',
  `reply_content` varchar(300) DEFAULT NULL COMMENT '最终回复',
  `feedback_status` char(5) DEFAULT NULL COMMENT '反馈状态',
  `feedback_status_name` varchar(100) DEFAULT NULL COMMENT '反馈状态名称',
  `merge_status` char(5) DEFAULT NULL COMMENT '合并状态',
  `merge_opinion1` text COMMENT '公司合并意见',
  `merge_opinion2` text COMMENT '集团合并意见',
  `return_opinion` text COMMENT '退回意见',
  `chairman_agree` char(2) DEFAULT NULL COMMENT '工会主席是否同意',
  `chairman_opinion` text COMMENT '工会主席审批意见',
  `report_status` char(5) DEFAULT NULL COMMENT '上报状态',
  `report_content` text COMMENT '上报工会主席内容',
  `register_number` varchar(50) DEFAULT NULL COMMENT '立案编号',
  `register_time` datetime DEFAULT NULL COMMENT '立案起始时间',
  `limit_time` datetime DEFAULT NULL COMMENT '完成时限',
  `register_status` char(2) DEFAULT NULL COMMENT '立案状态',
  `register_opinion` text COMMENT '立案意见',
  `main_measures` text COMMENT '主要措施',
  `main_nodes` text COMMENT '主要节点和目标',
  `can_display1` char(2) DEFAULT NULL COMMENT '1季度反馈是否显示',
  `can_edit1` char(2) DEFAULT NULL COMMENT '1季度是否可编辑',
  `feedback1` text COMMENT '1季度落实情况反馈',
  `can_display2` char(2) DEFAULT NULL COMMENT '2季度反馈是否显示',
  `can_edit2` char(2) DEFAULT NULL COMMENT '2季度是否可编辑',
  `feedback2` text COMMENT '2季度落实情况反馈',
  `can_display3` char(2) DEFAULT NULL COMMENT '3季度反馈是否显示',
  `can_edit3` char(2) DEFAULT NULL COMMENT '3季度是否可编辑',
  `feedback3` text COMMENT '3季度落实情况反馈',
  `can_display4` char(2) DEFAULT NULL COMMENT '4季度反馈是否显示',
  `can_edit4` char(2) DEFAULT NULL COMMENT '4季度是否可编辑',
  `feedback4` text COMMENT '4季度落实情况反馈',
  `implement_status` char(2) DEFAULT NULL COMMENT '季度落实状态',
  `application_id` char(32) DEFAULT NULL COMMENT '立案流程申请表id',
  `sort` int(5) DEFAULT NULL COMMENT '排序',
  `opinion_field1` text COMMENT '备注',
  `opinion_field2` text COMMENT '备注',
  `opinion_field3` text COMMENT '备注',
  `opinion_field4` text COMMENT '备注',
  `opinion_field5` text COMMENT '备注',
  `opinion_field6` text COMMENT '备注',
  `opinion_field7` text COMMENT '备注',
  `opinion_field8` text COMMENT '备注',
  `opinion_field9` text COMMENT '备注',
  `opinion_field10` text COMMENT '备注',
  `apih5_flow_id` char(32) DEFAULT NULL COMMENT '流程id',
  `work_id` char(32) DEFAULT NULL COMMENT 'work_id',
  `apih5_flow_status` char(2) DEFAULT NULL COMMENT '工序审核状态',
  `apih5_flow_node_status` char(2) DEFAULT NULL COMMENT '流程状态',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`proposal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提案表';

-- ----------------------------
-- Records of zj_labour_union_proposal
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labour_union_proposal_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labour_union_proposal_attachment`;
CREATE TABLE `zj_labour_union_proposal_attachment` (
  `uid` char(32) NOT NULL DEFAULT '' COMMENT 'uid',
  `other_id` char(32) DEFAULT NULL COMMENT '其他表主键',
  `name` varchar(300) DEFAULT NULL COMMENT '文件名称',
  `size` varchar(100) DEFAULT NULL COMMENT '大小',
  `type` varchar(100) DEFAULT NULL COMMENT '文件格式',
  `url` varchar(500) DEFAULT NULL COMMENT 'WEB文件地址',
  `thumb_url` varchar(500) DEFAULT NULL COMMENT 'WEB缩略图文件地址',
  `mobile_url` varchar(500) DEFAULT NULL COMMENT '手机文件地址',
  `mobile_thumb_url` varchar(500) DEFAULT NULL COMMENT '手机缩略图文件地址',
  `relative_url` varchar(500) DEFAULT NULL COMMENT '相对路径文件地址',
  `relative_thumb_url` varchar(500) DEFAULT NULL COMMENT '相对路径缩略图文件地址',
  `classify` char(2) DEFAULT NULL COMMENT '附件分类',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提案附件表';

-- ----------------------------
-- Records of zj_labour_union_proposal_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labour_union_seconder`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labour_union_seconder`;
CREATE TABLE `zj_labour_union_seconder` (
  `seconder_id` char(32) NOT NULL DEFAULT '' COMMENT '主键ID',
  `value` char(64) DEFAULT NULL COMMENT '主键ID',
  `label` varchar(200) DEFAULT NULL COMMENT '节点内容（名称）',
  `title` varchar(200) DEFAULT NULL COMMENT '保留',
  `type` char(3) DEFAULT NULL COMMENT '节点类型',
  `value_pid` varchar(200) DEFAULT NULL COMMENT '父节点ID',
  `proposal_id` char(32) DEFAULT NULL COMMENT '提案表主键',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`seconder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附议人表';

-- ----------------------------
-- Records of zj_labour_union_seconder
-- ----------------------------
