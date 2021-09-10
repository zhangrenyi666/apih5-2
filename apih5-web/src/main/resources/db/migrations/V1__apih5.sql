/*
Navicat MySQL Data Transfer

Source Server         : a
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : a

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-06-11 10:31:26
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `base_account`
-- ----------------------------
DROP TABLE IF EXISTS `base_account`;
CREATE TABLE `base_account` (
  `account_id` char(32) NOT NULL DEFAULT '' COMMENT 'App账号ID',
  `account_name` varchar(100) DEFAULT NULL COMMENT '名称',
  `account_corp_id` varchar(50) DEFAULT NULL COMMENT '企业账号ID',
  `account_app_type` char(1) DEFAULT NULL COMMENT '应用类型',
  `agent_id` varchar(50) DEFAULT NULL COMMENT '应用id',
  `corp_id` varchar(100) DEFAULT NULL COMMENT '企业ID(服务号的AppId)',
  `secret` varchar(100) DEFAULT NULL COMMENT '秘钥',
  `token` char(32) DEFAULT NULL COMMENT 'token',
  `access_token` varchar(2000) DEFAULT NULL COMMENT 'ACCESSTOKEN',
  `scope` varchar(100) DEFAULT NULL COMMENT '授权方式',
  `other_account_id` char(32) DEFAULT NULL COMMENT '其他App账号ID',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础账号表';

-- ----------------------------
-- Records of base_account
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_company`
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company` (
  `company_id` char(64) NOT NULL DEFAULT '' COMMENT '公司ID',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `company_url` varchar(200) DEFAULT NULL COMMENT '公司访问地址',
  `company_remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司表';

-- ----------------------------
-- Records of sys_company
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_department`
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `department_id` char(64) NOT NULL DEFAULT '' COMMENT '部门ID',
  `department_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `department_parent_id` varchar(64) DEFAULT NULL COMMENT '部门父ID',
  `department_path` varchar(1000) DEFAULT NULL COMMENT '全路径',
  `department_path_name` varchar(1000) DEFAULT NULL COMMENT '全路径名称',
  `department_flag` char(1) DEFAULT NULL COMMENT '部门标识',
  `other_id` varchar(100) DEFAULT NULL COMMENT '其他id（如微信等）',
  `department_remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `sort` int(5) unsigned DEFAULT '0' COMMENT '排序',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_department
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_files_demo`
-- ----------------------------
DROP TABLE IF EXISTS `sys_files_demo`;
CREATE TABLE `sys_files_demo` (
  `uid` char(32) NOT NULL DEFAULT '' COMMENT 'uid',
  `other_id` char(32) DEFAULT NULL COMMENT '其他表主键',
  `name` varchar(300) DEFAULT NULL COMMENT 'name',
  `size` varchar(10) DEFAULT NULL COMMENT 'size',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `url` varchar(500) DEFAULT NULL COMMENT 'url',
  `thumbUrl` varchar(500) DEFAULT NULL COMMENT '缩略图地址',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件表';

-- ----------------------------
-- Records of sys_files_demo
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_lock`
-- ----------------------------
DROP TABLE IF EXISTS `sys_lock`;
CREATE TABLE `sys_lock` (
  `system_cron` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '系统是否正在运行爬取任务（0：否，1：是）',
  `business_cron` char(1) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户是否正在运行爬取任务（0：否，1：是）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_lock
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '登录账户',
  `execute_time` datetime DEFAULT NULL COMMENT '执行时间',
  `type_id` int(11) DEFAULT NULL COMMENT '类型（1：登录系统，2：登出系统）',
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'ip地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` char(32) NOT NULL DEFAULT '' COMMENT '菜单ID',
  `menu_name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `menu_url` varchar(1000) DEFAULT NULL COMMENT '菜单URL',
  `menu_parent_id` char(32) DEFAULT NULL COMMENT '父菜单ID',
  `sort` int(5) DEFAULT NULL COMMENT '排序字段',
  `menu_path` varchar(1000) DEFAULT NULL COMMENT '树形层级编码',
  `menu_path_name` varchar(1000) DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` char(32) NOT NULL DEFAULT '' COMMENT '角色ID',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_menu_id` char(32) NOT NULL DEFAULT '' COMMENT '角色菜单ID',
  `role_id` char(32) DEFAULT NULL COMMENT '角色ID',
  `type` char(1) DEFAULT NULL COMMENT '节点类型（0：根节点；1：部门；2：人员）',
  `value` varchar(100) DEFAULT NULL COMMENT 'value（部门或人员主键ID）',
  `title` varchar(100) DEFAULT NULL COMMENT 'title（同value暂保留）',
  `label` varchar(100) DEFAULT NULL COMMENT '节点内容（名称）',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`role_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `role_user_id` char(32) NOT NULL DEFAULT '' COMMENT '角色用户ID',
  `role_id` char(32) DEFAULT NULL COMMENT '角色ID',
  `type` char(1) DEFAULT NULL COMMENT '节点类型（0：根节点；1：部门；2：人员）',
  `value` varchar(100) DEFAULT NULL COMMENT 'value（部门或人员主键ID）',
  `title` varchar(100) DEFAULT NULL COMMENT 'title（同value暂保留）',
  `label` varchar(100) DEFAULT NULL COMMENT '节点内容（名称）',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`role_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色用户关系表';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_select_department_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_select_department_user`;
CREATE TABLE `sys_select_department_user` (
  `select_id` char(32) NOT NULL DEFAULT '' COMMENT '选择id',
  `other_id` char(32) DEFAULT NULL COMMENT '其他关联表id',
  `other_type` char(1) DEFAULT NULL COMMENT '其他关联表类型区分',
  `department_user_flag` char(1) DEFAULT NULL COMMENT '组织人员区分标识（部门1；人',
  `project_id` varchar(20) DEFAULT NULL COMMENT '项目标识',
  `object_user_key` varchar(100) DEFAULT NULL COMMENT '对象userKey',
  `object_department_id` varchar(100) DEFAULT NULL COMMENT '对象department',
  `object_name` varchar(100) DEFAULT NULL COMMENT '对象name',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`select_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选择部门用户关系表';

-- ----------------------------
-- Records of sys_select_department_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `sys_sequence`;
CREATE TABLE `sys_sequence` (
  `seq_name` varchar(50) NOT NULL DEFAULT '' COMMENT '番号名称',
  `current_val` int(11) DEFAULT NULL COMMENT '当前番号',
  `increment_val` int(11) DEFAULT NULL COMMENT '下一个番号',
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统番号表';

-- ----------------------------
-- Records of sys_sequence
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_key` char(32) NOT NULL DEFAULT '' COMMENT '用户key',
  `user_id` varchar(100) DEFAULT NULL COMMENT '用户id',
  `real_name` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '真实姓名',
  `user_pwd` varchar(100) DEFAULT NULL COMMENT '用户密码',
  `mobile` varchar(15) DEFAULT NULL COMMENT '电话',
  `identity_card` varchar(30) DEFAULT NULL COMMENT '身份证',
  `gender` char(1) DEFAULT NULL COMMENT '性别',
  `image_url` varchar(500) DEFAULT NULL COMMENT '头像',
  `openid` varchar(100) DEFAULT NULL COMMENT 'openid',
  `account_corp_id` varchar(50) DEFAULT NULL COMMENT '企业账号ID',
  `account_app_type` char(1) DEFAULT NULL COMMENT '企业应用类型',
  `ext1` varchar(100) DEFAULT NULL COMMENT '扩展1',
  `ext2` varchar(100) DEFAULT NULL COMMENT '扩展2',
  `ext3` varchar(100) DEFAULT NULL COMMENT '扩展3',
  `ext4` varchar(100) DEFAULT NULL COMMENT '扩展4',
  `ext5` varchar(100) DEFAULT NULL COMMENT '扩展5',
  `ext6` varchar(100) DEFAULT NULL COMMENT '扩展6',
  `ext7` varchar(100) DEFAULT NULL COMMENT '扩展7',
  `ext8` varchar(100) DEFAULT NULL COMMENT '扩展8',
  `ext9` varchar(100) DEFAULT NULL COMMENT '扩展9',
  `ext10` varchar(100) DEFAULT NULL COMMENT '扩展10',
  `ext_version` char(2) DEFAULT NULL COMMENT '扩展类型',
  `ext` text COMMENT '扩展',
  `user_type` char(1) DEFAULT NULL COMMENT '用户类型',
  `user_status` char(1) DEFAULT NULL COMMENT '用户状态FLG(1:有效;2:无效)',
  `expiration_date` datetime DEFAULT NULL COMMENT '有效期',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`user_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_company`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_company`;
CREATE TABLE `sys_user_company` (
  `user_company_id` char(32) NOT NULL DEFAULT '' COMMENT '用户部门关系ID',
  `user_key` char(32) DEFAULT NULL COMMENT '用户Key',
  `company_id` char(64) DEFAULT NULL COMMENT '公司ID',
  `company_select_flag` char(1) DEFAULT NULL COMMENT '公司选中标识（保留字段）',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`user_company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户公司关系表';

-- ----------------------------
-- Records of sys_user_company
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user_department`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department` (
  `user_department_id` char(32) NOT NULL DEFAULT '' COMMENT '用户部门关系ID',
  `user_key` char(32) DEFAULT NULL COMMENT '用户Key',
  `department_id` char(64) DEFAULT NULL COMMENT '部门ID',
  `del_flag` char(1) DEFAULT NULL COMMENT '删除flg',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` char(32) DEFAULT NULL COMMENT '创建者',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建者姓名',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `modify_user` char(32) DEFAULT NULL COMMENT '更新者',
  `modify_user_name` varchar(100) DEFAULT NULL COMMENT '更新者姓名',
  PRIMARY KEY (`user_department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户部门关系表';

-- 在MySQL中创建函数时出现这种错误的解决方法：
-- set global log_bin_trust_function_creators=TRUE;
-- ----------------------------
-- Function structure for `sys_currval`
-- ----------------------------
DROP FUNCTION IF EXISTS `sys_currval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `sys_currval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin     
    declare value integer;       
    set value = 0;       
    select current_val into value  from sys_sequence where seq_name = v_seq_name; 
   return value; 
end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `sys_nextval`
-- ----------------------------
DROP FUNCTION IF EXISTS `sys_nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `sys_nextval`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin
    update sys_sequence set current_val = current_val + increment_val  where seq_name = v_seq_name;
    return sys_currval(v_seq_name);
end
;;
DELIMITER ;
