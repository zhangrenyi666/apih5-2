/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : apih5-sjz

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-07-10 12:03:53
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `zj_labor_emergency_drill`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labor_emergency_drill`;
CREATE TABLE `zj_labor_emergency_drill` (
  `emergency_drill_id` char(32) NOT NULL DEFAULT '' COMMENT '应急演练主键id',
  `crop_name` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `project_name` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `drill_name` varchar(200) DEFAULT NULL COMMENT '演练名称',
  `drill_time` datetime DEFAULT NULL COMMENT '时间',
  `drill_content` text COMMENT '内容',
  `drill_kind` varchar(200) DEFAULT NULL COMMENT '种类',
  `drill_number` int(100) DEFAULT NULL COMMENT '次数',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`emergency_drill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应急演练表';

-- ----------------------------
-- Records of zj_labor_emergency_drill
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labor_file`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labor_file`;
CREATE TABLE `zj_labor_file` (
  `labor_file_id` char(32) NOT NULL DEFAULT '' COMMENT '附件表主键id',
  `file_related_id` varchar(32) DEFAULT NULL COMMENT '附件关联id',
  `file_name` varchar(256) DEFAULT NULL COMMENT '附件名称',
  `file_url` varchar(100) DEFAULT NULL COMMENT '附件路径',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flag',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`labor_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全需求附件表';

-- ----------------------------
-- Records of zj_labor_file
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labor_native_place`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labor_native_place`;
CREATE TABLE `zj_labor_native_place` (
  `native_place_id` char(32) NOT NULL DEFAULT '' COMMENT '籍贯主键id',
  `native_place_number` varchar(20) DEFAULT NULL COMMENT '籍贯编号',
  `native_place_name` varchar(200) DEFAULT NULL COMMENT '籍贯名称',
  `high_native_place_id` varchar(200) DEFAULT NULL COMMENT '上级籍贯id',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`native_place_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='籍贯表';

-- ----------------------------
-- Records of zj_labor_native_place
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labor_per_info_base`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labor_per_info_base`;
CREATE TABLE `zj_labor_per_info_base` (
  `per_info_base_id` char(32) NOT NULL DEFAULT '' COMMENT '人员信息主键id',
  `per_type` char(1) DEFAULT NULL COMMENT '人员类型',
  `name` varchar(300) DEFAULT NULL COMMENT '姓名',
  `project_name` varchar(500) DEFAULT NULL COMMENT '项目名称',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `dep_name` varchar(500) DEFAULT NULL COMMENT '部门',
  `age` int(100) DEFAULT NULL COMMENT '年龄',
  `native_place` varchar(500) DEFAULT NULL COMMENT '籍贯',
  `education` char(1) DEFAULT NULL COMMENT '学历',
  `duty` varchar(100) DEFAULT NULL COMMENT '职务',
  `professional_title` char(1) DEFAULT NULL COMMENT '职称',
  `working_year` int(100) DEFAULT NULL COMMENT '工作年限',
  `safety_work_flag` char(1) DEFAULT NULL COMMENT '是否从事专职安全工作',
  `safety_work_year` int(100) DEFAULT NULL COMMENT '从事安全工作累计年数',
  `id_card` char(18) DEFAULT NULL COMMENT '身份证号',
  `crop_name` varchar(500) DEFAULT NULL COMMENT '公司名称',
  `per_photo_file_id` varchar(500) DEFAULT NULL COMMENT '人员照片附件id',
  `id_card_photo_file_id` varchar(500) DEFAULT NULL COMMENT '身份证扫描件附件id',
  `note_number` varchar(500) DEFAULT NULL COMMENT '注安师证号',
  `note_start_time` datetime DEFAULT NULL COMMENT '发证日期',
  `note_end_time` datetime DEFAULT NULL COMMENT '到期日期',
  `transport_number` varchar(500) DEFAULT NULL COMMENT '交通部安全证书编号',
  `transport_start_time` datetime DEFAULT NULL COMMENT '发证日期',
  `transport_end_time` datetime DEFAULT NULL COMMENT '到期日期',
  `transport_file_id` varchar(500) DEFAULT NULL COMMENT '交通部安全证书附件id',
  `construction_number` varchar(500) DEFAULT NULL COMMENT '建设部安全证书编号',
  `construction_start_time` datetime DEFAULT NULL COMMENT '发证日期',
  `construction_end_time` datetime DEFAULT NULL COMMENT '到期日期',
  `construction_file_id` varchar(500) DEFAULT NULL COMMENT '建设部安全证书附件id',
  `special_operation_number` varchar(500) DEFAULT NULL COMMENT '特种作业证件',
  `special_operation_start_time` datetime DEFAULT NULL COMMENT '发证日期',
  `special_operation_end_time` datetime DEFAULT NULL COMMENT '到期日期',
  `special_operation_file_id` varchar(500) DEFAULT NULL COMMENT '特种作业证件附件id',
  `rewards_and_punish` text COMMENT '安全奖惩情况',
  `rewards_and_punish_file_id` varchar(500) DEFAULT NULL COMMENT '安全奖惩情况附件id',
  `type_of_work` varchar(100) DEFAULT NULL COMMENT '工种',
  `violate_warn` text COMMENT '违章警示',
  `violate_warn_file_id` varchar(500) DEFAULT NULL COMMENT '违章警示附件id',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`per_info_base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员信息基础表';

-- ----------------------------
-- Records of zj_labor_per_info_base
-- ----------------------------
INSERT INTO `zj_labor_per_info_base` VALUES ('1CI1599SU0096701A8C0000091F1482D', '1', '管理人员姓名111', '项目名称1', '1', '1', '11', '* 籍贯：', '4', '职务：', '1', '11', '1', '11', '1111', '公司名称：', '1CI15HJFM00K6701A8C00000310E9A27', '1CI15HJFM00L6701A8C000000720A224', '', '2018-07-10 00:00:00', '2018-07-10 00:00:00', '', '2018-07-10 00:00:00', '2018-07-10 00:00:00', '1CI15HJFM00M6701A8C0000027356541', '', '2018-07-10 00:00:00', '2018-07-10 00:00:00', '1CI15HJFM00N6701A8C000003A768436', '', '2018-07-10 00:00:00', '2018-07-10 00:00:00', '1CI15HJFM00O6701A8C000003B03ABCF', '', '1CI15HJFM00P6701A8C00000B34C4DAE', '', '', '1CI15HJFM00Q6701A8C000008B85EBAB', '0', '2018-07-10 11:55:43', '3ff8e85805174c6ba059cef3d9b92d46', '管理员', '2018-07-10 12:00:15', '3ff8e85805174c6ba059cef3d9b92d46', '管理员');
INSERT INTO `zj_labor_per_info_base` VALUES ('1CI15A9KV00A6701A8C000001012D136', '2', '施工人员姓名111', '项目名称：', '1', '', '12', '* 籍贯：', '', '', '', null, '', null, '222', '公司名称：', '', '', '', null, null, '', null, null, '', '', null, null, '', '', '2018-07-10 00:00:00', '2018-07-10 00:00:00', '', '', '', '2', '', '', '0', '2018-07-10 11:56:16', '3ff8e85805174c6ba059cef3d9b92d46', '管理员', '2018-07-10 11:56:16', '3ff8e85805174c6ba059cef3d9b92d46', '管理员');
INSERT INTO `zj_labor_per_info_base` VALUES ('1CI15B78E00B6701A8C00000A609BFD1', '3', '非本单位人员姓名111', '项目名称', '1', '', '11', '* 籍贯：', '', '', '', null, '', null, '1111', '* 公司名称：', '', '', '', null, null, '', null, null, '', '', null, null, '', '', '2018-07-10 00:00:00', '2018-07-10 00:00:00', '', '', '', '2', '', '', '0', '2018-07-10 11:56:46', '3ff8e85805174c6ba059cef3d9b92d46', '管理员', '2018-07-10 11:56:46', '3ff8e85805174c6ba059cef3d9b92d46', '管理员');

-- ----------------------------
-- Table structure for `zj_labor_safe_edu_case`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labor_safe_edu_case`;
CREATE TABLE `zj_labor_safe_edu_case` (
  `safe_edu_case_id` char(32) NOT NULL DEFAULT '' COMMENT '安全教育情况主键id',
  `corp_name` varchar(500) DEFAULT NULL COMMENT '公司名称',
  `project_name` varchar(500) DEFAULT NULL COMMENT '项目名称',
  `edu_type` varchar(50) DEFAULT NULL COMMENT '教育类型',
  `train_name` varchar(500) DEFAULT NULL COMMENT '培训名称',
  `date` datetime DEFAULT NULL COMMENT '日期',
  `teacher` varchar(500) DEFAULT NULL COMMENT '授课人',
  `participant` varchar(500) DEFAULT NULL COMMENT '参加人员',
  `train_content` text COMMENT '培训内容',
  `pass_exam_flag` char(1) DEFAULT NULL COMMENT '是否通过考核',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`safe_edu_case_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全教育情况表';

-- ----------------------------
-- Records of zj_labor_safe_edu_case
-- ----------------------------
INSERT INTO `zj_labor_safe_edu_case` VALUES ('1CI15G54J00C6701A8C0000066656AB7', '', '项目名称：', '1', '* 培训名称：', '2018-07-10 00:00:00', '授课人：', '* 参加人员：', '* 培训内容：', '', '0', '2018-07-10 11:59:28', '3ff8e85805174c6ba059cef3d9b92d46', '管理员', '2018-07-10 11:59:28', '3ff8e85805174c6ba059cef3d9b92d46', '管理员');

-- ----------------------------
-- Table structure for `zj_labor_safe_technical_disclosure`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labor_safe_technical_disclosure`;
CREATE TABLE `zj_labor_safe_technical_disclosure` (
  `technical_disclosure_id` char(32) NOT NULL DEFAULT '' COMMENT '安全技术交底及风险告知主键id',
  `crop_name` varchar(500) DEFAULT NULL COMMENT '公司名称',
  `project_name` varchar(500) DEFAULT NULL COMMENT '项目名称',
  `distribution_name` varchar(500) DEFAULT NULL COMMENT '分布分项名称',
  `disclosure_time` datetime DEFAULT NULL COMMENT '交底时间',
  `disclosure_content` text COMMENT '交底内容',
  `disclosure_personal` varchar(100) DEFAULT NULL COMMENT '交底人员',
  `taskmaster` varchar(500) DEFAULT NULL COMMENT '接受任务人',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`technical_disclosure_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全技术交底及风险告知表';

-- ----------------------------
-- Records of zj_labor_safe_technical_disclosure
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_labor_select_data`
-- ----------------------------
DROP TABLE IF EXISTS `zj_labor_select_data`;
CREATE TABLE `zj_labor_select_data` (
  `select_data_id` char(32) NOT NULL DEFAULT '' COMMENT '下拉数据主键id',
  `select_number` varchar(20) DEFAULT NULL COMMENT '下拉编号',
  `select_classifty` varchar(200) DEFAULT NULL COMMENT '下拉分类',
  `select_classifty_content` varchar(200) DEFAULT NULL COMMENT '下拉分类内容',
  `select_data_content` varchar(200) DEFAULT NULL COMMENT '下拉数据内容',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`select_data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全下拉数据表';

-- ----------------------------
-- Records of zj_labor_select_data
-- ----------------------------
INSERT INTO `zj_labor_select_data` VALUES ('1CH4OR1080006D01A8C00000C7C93F58', '1', 'age', '年龄', '1', '0', '2018-06-29 11:19:29', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:19:29', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
INSERT INTO `zj_labor_select_data` VALUES ('1CH4OR92P0016D01A8C0000025983D3F', '2', 'age', '年龄', '2', '0', '2018-06-29 11:19:37', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:19:37', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
INSERT INTO `zj_labor_select_data` VALUES ('1CH4ORCHN0026D01A8C00000C656EF14', '3', 'age', '年龄', '3', '0', '2018-06-29 11:19:40', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:19:40', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
INSERT INTO `zj_labor_select_data` VALUES ('1CH4ORG180036D01A8C00000982C69CF', '4', 'age', '年龄', '4', '0', '2018-06-29 11:19:44', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:19:44', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
INSERT INTO `zj_labor_select_data` VALUES ('1CH4ORKQ00046D01A8C00000483AECC8', '5', 'age', '年龄', '5', '0', '2018-06-29 11:19:49', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:19:57', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
INSERT INTO `zj_labor_select_data` VALUES ('1CH4PNVF80006D01A8C000001A4D7411', '1', 'workingYear', '工作年限', '11', '0', '2018-06-29 11:35:17', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:35:17', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
INSERT INTO `zj_labor_select_data` VALUES ('1CH4PO3710016D01A8C0000026E0605E', '2', 'workingYear', '工作年限', '22', '0', '2018-06-29 11:35:21', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:35:21', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
INSERT INTO `zj_labor_select_data` VALUES ('1CH4Q872K0006D01A8C0000014C4CDF0', '1', 'safetyWorkYear', '从事安全工作累计年数', '111', '0', '2018-06-29 11:44:09', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:44:09', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
INSERT INTO `zj_labor_select_data` VALUES ('1CH4Q8AT20016D01A8C000006D129D8E', '2', 'safetyWorkYear', '从事安全工作累计年数', '222', '0', '2018-06-29 11:44:13', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员', '2018-06-29 11:44:13', '1AQK0PJ0V12CD31FA8C000006BE3047C', '系统管理员');
