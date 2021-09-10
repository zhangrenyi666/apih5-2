
--增加字段长度
ALTER TABLE `zx_gcsg_ct_contr_apply` MODIFY COLUMN `ct_contr_apply_id` CHAR(64) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_ct_contr_apply` MODIFY COLUMN `project_id` CHAR(64) DEFAULT NULL COMMENT '项目ID';

ALTER TABLE `zx_gcsg_ct_contr_apply` MODIFY COLUMN `company_id` CHAR(64) DEFAULT NULL COMMENT '公司ID';

ALTER TABLE `zx_gcsg_ct_contr_apply` MODIFY COLUMN `work_id` CHAR(64) DEFAULT NULL COMMENT '流程ID';

ALTER TABLE `zx_gcsg_ct_contr_apply` MODIFY COLUMN `content` VARCHAR(800) DEFAULT NULL COMMENT '合同内容';

ALTER TABLE `zx_gcsg_ct_contr_apply` MODIFY COLUMN `appInsHistID` VARCHAR(300) DEFAULT NULL COMMENT '局历史流程ID';

ALTER TABLE `zx_gcsg_ct_contr_apply_works` MODIFY COLUMN `ct_contr_apply_works_id` CHAR(64) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_ct_contr_process_guajie` MODIFY COLUMN `ct_contr_process_guajie_id` CHAR(100) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_ct_price_sys` MODIFY COLUMN `ct_price_sys_id` CHAR(100) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_ct_price_sys_item` MODIFY COLUMN `ct_price_sys_item_id` CHAR(100) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `ct_contract_id` CHAR(64) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `code` VARCHAR(40) DEFAULT NULL COMMENT 'name';

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `project_id` CHAR(64) DEFAULT NULL COMMENT '项目ID';

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `company_id` CHAR(64) DEFAULT NULL COMMENT '公司ID';

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `codeP1` VARCHAR(60) DEFAULT NULL COMMENT '业主合同功能码';

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `zjxmhtb_mc` VARCHAR(200) DEFAULT NULL COMMENT '财务合同名称';  
                                                 
ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `pp1` VARCHAR(200) DEFAULT NULL COMMENT '乙方(手机)';  

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `ZJGCXM_XMMC` VARCHAR(200) DEFAULT NULL COMMENT '项目名称';

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `content` VARCHAR(1000) DEFAULT NULL COMMENT '合同内容';  

ALTER TABLE `zx_gcsg_ct_contract` MODIFY COLUMN `accountUnitCode` VARCHAR(200) DEFAULT NULL COMMENT '核算单位编号'; 

ALTER TABLE `zx_gcsg_cc_works` MODIFY COLUMN `cc_works_id` CHAR(64) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_sa_co_work_link_work` MODIFY COLUMN `sa_co_work_link_work_id` CHAR(64) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_ct_co_contract_amt_rate` MODIFY COLUMN `ct_co_contract_amt_rate_id` CHAR(64) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_cc_co_alter` MODIFY COLUMN `cc_co_alter_id` CHAR(64) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_cc_co_alter_work` MODIFY COLUMN `cc_co_alter_work_id` CHAR(64) NOT NULL COMMENT '主键ID';

ALTER TABLE `zx_gcsg_cc_co_alter_work` MODIFY COLUMN `ruleName` VARCHAR(500) DEFAULT NULL COMMENT '计价规则名称'; 



























