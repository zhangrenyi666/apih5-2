/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : wdmall

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-07-12 13:53:43
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `tz_accessory`
-- ----------------------------
DROP TABLE IF EXISTS `tz_accessory`;
CREATE TABLE `tz_accessory` (
  `accessory_id` char(32) NOT NULL DEFAULT '' COMMENT '附件ID',
  `else_id` char(32) DEFAULT '' COMMENT '其他内容ID',
  `type` char(1) DEFAULT '' COMMENT '类型（设计图、土地征拆）',
  `person_id` char(32) DEFAULT '' COMMENT '填报者ID',
  `acc_path` varchar(500) DEFAULT '' COMMENT '附件路径',
  `acc_name` varchar(200) DEFAULT '' COMMENT '附件名字',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`accessory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

-- ----------------------------
-- Records of tz_accessory
-- ----------------------------
INSERT INTO `tz_accessory` VALUES ('1', '3', '', '', '', '', '0', null, '', null, '');
INSERT INTO `tz_accessory` VALUES ('2', '111', '', '', '', 'fs', '0', null, '', null, '');
INSERT INTO `tz_accessory` VALUES ('3', '222', '', '', '', 'da', '0', null, '', null, '');
INSERT INTO `tz_accessory` VALUES ('4', '333', '', '', '', 'ss', '0', null, '', null, '');
INSERT INTO `tz_accessory` VALUES ('5', '444', '', '', '', 'sd', '0', null, '', null, '');
INSERT INTO `tz_accessory` VALUES ('6', '555', '', '', '', 'ssssss', '0', null, '', null, '');
INSERT INTO `tz_accessory` VALUES ('7', '1', '', '', '', 'sf', '0', null, '', null, '');
INSERT INTO `tz_accessory` VALUES ('8', '2', '', '', '', 'sdf', '0', null, '', null, '');

-- ----------------------------
-- Table structure for `tz_assessment`
-- ----------------------------
DROP TABLE IF EXISTS `tz_assessment`;
CREATE TABLE `tz_assessment` (
  `assessment_id` char(32) NOT NULL DEFAULT '' COMMENT '评估、许可证办理ID',
  `company_id` char(32) DEFAULT '' COMMENT '投资公司ID',
  `person_id` char(32) DEFAULT '' COMMENT '填报者ID',
  `progress_desc` text(0) COMMENT '进展描述',
  `fill_date` datetime DEFAULT NULL COMMENT '填报日期',
  `changes` char(1) DEFAULT '' COMMENT '是否有变化',
  `change_desc` text(0) COMMENT '变化描述',
  `cdate` datetime DEFAULT NULL COMMENT '操作日期',
  `check_state` char(1) DEFAULT '' COMMENT '审核',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`assessment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评估、许可证办理表';

-- ----------------------------
-- Records of tz_assessment
-- ----------------------------
INSERT INTO `tz_assessment` VALUES ('1', '1', '', '', null, '1', '', null, '', '0', null, '', null, '');

-- ----------------------------
-- Table structure for `tz_company_person`
-- ----------------------------
DROP TABLE IF EXISTS `tz_company_person`;
CREATE TABLE `tz_company_person` (
  `company_person_id` char(32) NOT NULL DEFAULT '' COMMENT '投资公司与填报者关系id',
  `company_id` char(32) DEFAULT '' COMMENT '投资公司ID',
  `oa_user_id` varchar(100) DEFAULT '' COMMENT 'oaUserId',
  `oa_user_Name` varchar(100) DEFAULT '' COMMENT 'oaUserName',
  `oa_org_id` varchar(100) DEFAULT '' COMMENT 'oaOrgId',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`company_person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资公司与填报者关系表';

-- ----------------------------
-- Records of tz_company_person
-- ----------------------------
INSERT INTO `tz_company_person` VALUES ('111', '1', '11', 'dddddddd', '11', '0', null, '', null, '');
INSERT INTO `tz_company_person` VALUES ('1BK32PAVP003337420AC000050F13858', '1BK32JG6H000337420AC0000D82E4904', '', 'ccc', '', '0', '2017-07-03 09:59:37', 'test', null, '');
INSERT INTO `tz_company_person` VALUES ('1BK32PAVS004337420AC0000D7945AE2', '1BK32JG6H000337420AC0000D82E4904', '', 'ddd', '', '0', '2017-07-03 09:59:37', 'test', null, '');
INSERT INTO `tz_company_person` VALUES ('222', '1', '', 'sdfsd', '11', '0', null, '', null, '');
INSERT INTO `tz_company_person` VALUES ('333', '333', '11', '', '', '', null, '', null, '');

-- ----------------------------
-- Table structure for `tz_design_chart`
-- ----------------------------
DROP TABLE IF EXISTS `tz_design_chart`;
CREATE TABLE `tz_design_chart` (
  `design_chart_id` char(32) NOT NULL DEFAULT '' COMMENT '设计图ID',
  `company_id` char(32) DEFAULT '' COMMENT '投资公司ID',
  `person_id` char(32) DEFAULT '' COMMENT '填报者ID',
  `progress_desc` text(0) COMMENT '进展描述',
  `fill_date` datetime DEFAULT NULL COMMENT '填报日期',
  `changes` char(1) DEFAULT '' COMMENT '是否有变化',
  `change_desc` text(0) COMMENT '变化描述',
  `cdate` datetime DEFAULT NULL COMMENT '操作日期',
  `check_state` char(1) DEFAULT '' COMMENT '审核',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`design_chart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设计图表';

-- ----------------------------
-- Records of tz_design_chart
-- ----------------------------
INSERT INTO `tz_design_chart` VALUES ('1', '1', '', '', null, '1', '', null, '', '0', null, '', null, '');

-- ----------------------------
-- Table structure for `tz_fails_person`
-- ----------------------------
DROP TABLE IF EXISTS `tz_fails_person`;
CREATE TABLE `tz_fails_person` (
  `person_id` char(32) NOT NULL DEFAULT '' COMMENT '投资日报填报者ID',
  `name` varchar(100) DEFAULT '' COMMENT '姓名',
  `oaUserId` varchar(100) DEFAULT '' COMMENT 'oaUserId',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资日报填报者表';

-- ----------------------------
-- Records of tz_fails_person
-- ----------------------------

-- ----------------------------
-- Table structure for `tz_graphic_progress`
-- ----------------------------
DROP TABLE IF EXISTS `tz_graphic_progress`;
CREATE TABLE `tz_graphic_progress` (
  `progress_id` char(32) NOT NULL DEFAULT '' COMMENT '工程形象进度统计表ID',
  `company_id` char(32) DEFAULT '' COMMENT '投资公司ID',
  `person_id` char(32) DEFAULT '' COMMENT '填报者ID',
  `progress_desc` text(0) COMMENT '进展描述',
  `fill_date` datetime DEFAULT NULL COMMENT '填报日期',
  `changes` char(1) DEFAULT '' COMMENT '是否有变化',
  `change_desc` text(0) COMMENT '变化描述',
  `cdate` datetime DEFAULT NULL COMMENT '操作日期',
  `check_state` char(1) DEFAULT '' COMMENT '审核',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`progress_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工程形象进度统计表';

-- ----------------------------
-- Records of tz_graphic_progress
-- ----------------------------
INSERT INTO `tz_graphic_progress` VALUES ('1', '1', '', '', null, '1', '1', '2017-07-10 13:42:03', '1', '0', null, '', null, '');

-- ----------------------------
-- Table structure for `tz_invest_company`
-- ----------------------------
DROP TABLE IF EXISTS `tz_invest_company`;
CREATE TABLE `tz_invest_company` (
  `company_id` char(32) NOT NULL DEFAULT '' COMMENT '投资公司ID',
  `project_name` varchar(200) DEFAULT '' COMMENT '项目名称',
  `project_state` char(1) DEFAULT '' COMMENT '项目状态',
  `holding_than` varchar(200) DEFAULT '' COMMENT '控股比',
  `contract_amount` decimal(10,2) DEFAULT NULL COMMENT '合同金额',
  `report_state` char(1) DEFAULT '' COMMENT '上报状态',
  `newest_date` datetime DEFAULT NULL COMMENT '日报最新日期',
  `changes` char(1) DEFAULT '' COMMENT '是否有变化',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资公司基础表';

-- ----------------------------
-- Records of tz_invest_company
-- ----------------------------
INSERT INTO `tz_invest_company` VALUES ('1', '你好么', '1', 'sdf', null, '', null, '', '0', null, '', null, '');
INSERT INTO `tz_invest_company` VALUES ('11', '我很好', '1', '1', '111.00', '', null, '1', '0', null, '', null, '');
INSERT INTO `tz_invest_company` VALUES ('1BK32JG6H000337420AC0000D82E4904', 'sfdlkjsl', '1', '1', '1.00', '1', null, '1', '0', '2017-07-03 09:56:25', 'test', '2017-07-03 09:59:37', 'test');
INSERT INTO `tz_invest_company` VALUES ('222', 'sfs', '1', 'sd', null, '', null, '', '0', null, '', null, '');
INSERT INTO `tz_invest_company` VALUES ('232', 'sdfsdf', '', '', null, '', null, '', '0', null, '', null, '');
INSERT INTO `tz_invest_company` VALUES ('2321', 'ksksi', '', '', null, '', null, '', '0', null, '', null, '');
INSERT INTO `tz_invest_company` VALUES ('3', '3fffddd', '3', '3', '3.00', '3', null, '1', '0', null, '', null, '');
INSERT INTO `tz_invest_company` VALUES ('333', 'uijs', '1', '66', '66.00', '', null, '', '0', null, '', null, '');
INSERT INTO `tz_invest_company` VALUES ('4', '1', '2', '2', '3.00', '', null, '0', '0', null, '', null, '');
INSERT INTO `tz_invest_company` VALUES ('555', 'jsh', '', '', null, '', null, '', '0', null, '', null, '');

-- ----------------------------
-- Table structure for `tz_land`
-- ----------------------------
DROP TABLE IF EXISTS `tz_land`;
CREATE TABLE `tz_land` (
  `land_id` char(32) NOT NULL DEFAULT '' COMMENT '土地征拆ID',
  `company_id` char(32) DEFAULT '' COMMENT '投资公司ID',
  `person_id` char(32) DEFAULT '' COMMENT '填报者ID',
  `progress_desc` text(0) COMMENT '进展描述',
  `fill_date` datetime DEFAULT NULL COMMENT '填报日期',
  `changes` char(1) DEFAULT '' COMMENT '是否有变化',
  `change_desc` text(0) COMMENT '变化描述',
  `cdate` datetime DEFAULT NULL COMMENT '操作日期',
  `check_state` char(1) DEFAULT '' COMMENT '审核',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`land_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='土地征拆表';

-- ----------------------------
-- Records of tz_land
-- ----------------------------
INSERT INTO `tz_land` VALUES ('1', '1', '1', '', null, '1', '', '2017-07-03 11:58:31', '1', '0', null, '', null, '');
INSERT INTO `tz_land` VALUES ('2', '1', '', '', null, '0', '', '2017-07-25 11:58:35', '1', '0', null, '', null, '');
INSERT INTO `tz_land` VALUES ('3', '2', '11', '', null, '1', '', '2017-07-11 11:58:39', '1', '0', null, '', null, '');

-- ----------------------------
-- Table structure for `tz_message_member`
-- ----------------------------
DROP TABLE IF EXISTS tz_message_member;
CREATE TABLE  tz_message_member (
	msg_member_id	char(32) DEFAULT '' COMMENT '消息人员id',
	member_type	varchar(100) DEFAULT '' COMMENT '人员类型',
	oa_user_id	varchar(100) DEFAULT '' COMMENT 'oaUserId',
	oa_org_id	varchar(100) DEFAULT '' COMMENT 'oaOrgId',
	oa_user_Name	varchar(100) DEFAULT '' COMMENT 'oaUserName',
	del_flag	char(1) DEFAULT '' COMMENT '削除FLG',
	create_time	datetime DEFAULT NULL COMMENT '创建时间',
	create_user	char(15) DEFAULT '' COMMENT '创建者',
	modify_time	datetime DEFAULT NULL COMMENT '更新时间',
	modify_user	char(15) DEFAULT '' COMMENT '更新者',
PRIMARY KEY ( msg_member_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息人员表';

-- ----------------------------
-- Records of tz_message_member
-- ----------------------------

-- ----------------------------
-- Table structure for `tz_project_funds`
-- ----------------------------
DROP TABLE IF EXISTS `tz_project_funds`;
CREATE TABLE `tz_project_funds` (
  `project_funds_id` char(32) NOT NULL DEFAULT '' COMMENT '项目资金ID',
  `company_id` char(32) DEFAULT '' COMMENT '投资公司ID',
  `person_id` char(32) DEFAULT '' COMMENT '填报者ID',
  `progress_desc` text(0) COMMENT '进展描述',
  `fill_date` datetime DEFAULT NULL COMMENT '填报日期',
  `changes` char(1) DEFAULT '' COMMENT '是否有变化',
  `change_desc` text(0) COMMENT '变化描述',
  `cdate` datetime DEFAULT NULL COMMENT '操作日期',
  `check_state` char(1) DEFAULT '' COMMENT '审核',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`project_funds_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目资金表';

-- ----------------------------
-- Records of tz_project_funds
-- ----------------------------
INSERT INTO `tz_project_funds` VALUES ('1', '1', '', '', null, '1', '', '2017-07-16 13:17:43', '1', '0', null, '', null, '');

-- ----------------------------
-- Table structure for `tz_send_message_record`
-- ----------------------------
DROP TABLE IF EXISTS `tz_send_message_record`;
CREATE TABLE `tz_send_message_record` (
  `msg_id` char(32) NOT NULL DEFAULT '' COMMENT '发消息id',
  `parameter1` varchar(100) DEFAULT '' COMMENT '参数1',
  `parameter2` varchar(100) DEFAULT '' COMMENT '参数2',
  `parameter3` varchar(100) DEFAULT '' COMMENT '参数3',
  `parameter4` varchar(100) DEFAULT '' COMMENT '参数4',
  `parameter5` varchar(100) DEFAULT '' COMMENT '参数5',
  `parameter6` varchar(100) DEFAULT '' COMMENT '参数6',
  `parameter7` varchar(100) DEFAULT '' COMMENT '参数7',
  `parameter8` varchar(100) DEFAULT '' COMMENT '参数8',
  `parameter9` varchar(100) DEFAULT '' COMMENT '参数9',
  `parameter10` varchar(100) DEFAULT '' COMMENT '参数10',
  `send_oa_user_id` varchar(100) DEFAULT '' COMMENT '发送者id',
  `send_oa_org_id` varchar(100) DEFAULT '' COMMENT '发送者orgId',
  `send_oa_user_name` varchar(100) DEFAULT '' COMMENT '发送者姓名',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发消息记录表';

-- ----------------------------
-- Records of tz_send_message_record
-- ----------------------------

-- ----------------------------
-- Table structure for `tz_log_record`
-- ----------------------------
DROP TABLE IF EXISTS `tz_log_record`;
CREATE TABLE `tz_log_record` (
  `record_id` char(32) NOT NULL DEFAULT '' COMMENT '日志id',
  `title` varchar(500) DEFAULT '' COMMENT '标题',
  `sender_id` varchar(200) DEFAULT '' COMMENT '发报者id',
  `sender_name` varchar(200) DEFAULT '' COMMENT '发报者姓名',
  `reader_id` varchar(200) DEFAULT '' COMMENT '阅览者id',
  `reader_name` varchar(200) DEFAULT '' COMMENT '阅览者姓名',
  `record_date` datetime DEFAULT NULL COMMENT '记录日期',
  `level` char(1) DEFAULT '' COMMENT '人员级别',
  `del_flag` char(1) DEFAULT '' COMMENT '削除FLAG',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(15) DEFAULT '' COMMENT '创建者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(15) DEFAULT '' COMMENT '更新者',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志管理表';

-- ----------------------------
-- Records of tz_log_record
-- ----------------------------
