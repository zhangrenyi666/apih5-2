/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : apih5

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-05-14 20:19:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `zj_dd_supervision`
-- ----------------------------
DROP TABLE IF EXISTS `zj_dd_supervision`;
CREATE TABLE `zj_dd_supervision` (
  `supervision_id` char(32) NOT NULL DEFAULT '' COMMENT '督导id',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` varchar(200) DEFAULT NULL COMMENT '内容',
  `remarks` text COMMENT '备注',
  `state` char(1) DEFAULT '' COMMENT '状态 0已发布；1未发布；2发布失败',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`supervision_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督导表';

-- ----------------------------
-- Records of zj_dd_supervision
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_dd_supervision_company`
-- ----------------------------
DROP TABLE IF EXISTS `zj_dd_supervision_company`;
CREATE TABLE `zj_dd_supervision_company` (
  `supervision_company_id` char(32) NOT NULL DEFAULT '' COMMENT '督导公司id',
  `supervision_id` char(32) DEFAULT NULL COMMENT '督导id',
  `company_id` varchar(50) DEFAULT NULL COMMENT '公司id',
  `company_name` varchar(500) DEFAULT NULL COMMENT '公司名',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`supervision_company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督导公司表';

-- ----------------------------
-- Records of zj_dd_supervision_company
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_dd_supervision_file`
-- ----------------------------
DROP TABLE IF EXISTS `zj_dd_supervision_file`;
CREATE TABLE `zj_dd_supervision_file` (
  `file_id` char(32) NOT NULL DEFAULT '' COMMENT '附件表id',
  `supervision_company_id` varchar(32) DEFAULT NULL COMMENT '督导公司id',
  `file_name` varchar(256) DEFAULT NULL COMMENT '附件名',
  `file_url` varchar(100) DEFAULT NULL COMMENT '附件路径',
  `url` varchar(100) DEFAULT NULL COMMENT 'url',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督导附件表';

-- ----------------------------
-- Records of zj_dd_supervision_file
-- ----------------------------

-- ----------------------------
-- Table structure for `zj_dd_supervision_oa_member`
-- ----------------------------
DROP TABLE IF EXISTS `zj_dd_supervision_oa_member`;
CREATE TABLE `zj_dd_supervision_oa_member` (
  `dd_oa_id` char(32) NOT NULL DEFAULT '' COMMENT '督导签字者id（主键）',
  `file_id` char(32) DEFAULT NULL COMMENT '督导附件id（其他关联表id）',
  `other_type` char(2) DEFAULT NULL COMMENT '其他类型',
  `oa_department_member_flag` char(32) DEFAULT NULL COMMENT '组织人员区分标识',
  `object_user_key` varchar(100) DEFAULT NULL COMMENT 'OA用户id',
  `object_name` varchar(100) DEFAULT NULL COMMENT 'OA用户名',
  `object_department_id` varchar(100) DEFAULT NULL COMMENT 'oaOrgId',
  `file_name` varchar(256) DEFAULT '' COMMENT '签字内容（附件标题）',
  `state` char(1) DEFAULT '' COMMENT '签字状态（0：未签字  1:已签字）',
  `sign_time` datetime DEFAULT NULL COMMENT '签字时间',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除Flag',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`dd_oa_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='督导签字者表';

-- ----------------------------
-- Records of zj_dd_supervision_oa_member
-- ----------------------------
