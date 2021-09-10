/*
Navicat MySQL Data Transfer

Source Server         : MySQL5.6.24
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : apih5-deh

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2019-01-03 14:57:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `zj_zp_user_detail`
-- ----------------------------
DROP TABLE IF EXISTS `zj_zp_user_detail`;
CREATE TABLE `zj_zp_user_detail` (
  `detail_id` char(32) NOT NULL DEFAULT '' COMMENT '详情表id',
  `self_user_id` varchar(100) DEFAULT NULL COMMENT '自评人id',
  `self_detail` decimal(10,2) DEFAULT NULL COMMENT '自评分明细',
  `self_comment` text COMMENT '自评说明',
  `review_comment` text COMMENT '复评说明',
  `review_user_id` varchar(100) DEFAULT NULL COMMENT '复评人id',
  `review_detail` decimal(10,2) DEFAULT NULL COMMENT '复评分明细',
  `evaluation_id` char(32) DEFAULT NULL COMMENT '评分表id',
  `org_id` varchar(200) DEFAULT NULL COMMENT '部门id',
  `draft_flag` char(2) DEFAULT NULL COMMENT '临时数据flag',
  `auditor_id` char(32) DEFAULT NULL COMMENT '审核人id',
  `opinion` text COMMENT '审核人意见',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员评分详情表';

-- ----------------------------
-- Records of zj_zp_user_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_zp_evaluation_auditor`
-- ----------------------------
DROP TABLE IF EXISTS `zj_zp_evaluation_auditor`;
CREATE TABLE `zj_zp_evaluation_auditor` (
  `auditor_id` char(32) NOT NULL DEFAULT '' COMMENT 'id',
  `auditor_name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `auditor_status` char(2) DEFAULT NULL COMMENT '审核人状态',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`auditor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自评审核人表';

-- ----------------------------
-- Records of zj_zp_evaluation_auditor
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_zp_evaluation`
-- ----------------------------
DROP TABLE IF EXISTS `zj_zp_evaluation`;
CREATE TABLE `zj_zp_evaluation` (
  `evaluation_id` char(32) NOT NULL DEFAULT '' COMMENT '评分表id',
  `content` text COMMENT '自评项',
  `standard` text COMMENT '自评标准',
  `standard_detail` decimal(10,2) DEFAULT NULL COMMENT '标准分明细',
  `module_type` char(2) DEFAULT NULL COMMENT '工作模块分类',
  `evaluation_type` char(2) DEFAULT NULL COMMENT '评分表分类',
  `assessment_id` char(32) DEFAULT NULL COMMENT '考核活动表id',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`evaluation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自评系统评分表';

-- ----------------------------
-- Records of zj_zp_evaluation
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_zp_annual_assessment`
-- ----------------------------
DROP TABLE IF EXISTS `zj_zp_annual_assessment`;
CREATE TABLE `zj_zp_annual_assessment` (
  `assessment_id` char(32) NOT NULL DEFAULT '' COMMENT '考核活动表id',
  `title` varchar(200) DEFAULT NULL COMMENT '考核标题',
  `require` text COMMENT '考核要求',
  `start_time` datetime DEFAULT NULL COMMENT '考核发起时间',
  `end_time` datetime DEFAULT NULL COMMENT '考核截止时间',
  `status` char(2) DEFAULT NULL COMMENT '考核活动状态',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`assessment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='年度评分考核表';

-- ----------------------------
-- Records of zj_zp_annual_assessment
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_zp_accessory`
-- ----------------------------
DROP TABLE IF EXISTS `zj_zp_accessory`;
CREATE TABLE `zj_zp_accessory` (
  `accessory_id` char(32) NOT NULL DEFAULT '' COMMENT '附件id',
  `accessory_name` varchar(200) DEFAULT NULL COMMENT '附件名称',
  `accessory_type` char(2) DEFAULT NULL COMMENT '附件分类',
  `accessory_address` varchar(300) DEFAULT NULL COMMENT '附件地址',
  `detail_id` char(32) DEFAULT NULL COMMENT '评分详情表id',
  `del_flag` char(2) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`accessory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员评分详情附件表';

-- ----------------------------
-- Records of zj_zp_accessory
-- ----------------------------
