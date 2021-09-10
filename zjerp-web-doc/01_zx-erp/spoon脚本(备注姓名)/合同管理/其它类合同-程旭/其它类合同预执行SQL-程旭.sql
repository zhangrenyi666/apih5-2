
--增加字段长度

ALTER TABLE `zx_ct_other` MODIFY COLUMN `work_id` CHAR(64) DEFAULT NULL COMMENT '流程ID';

ALTER TABLE `zx_ct_other` MODIFY COLUMN `content` VARCHAR(1000) DEFAULT NULL COMMENT '合同内容';

ALTER TABLE `zx_ct_other` MODIFY COLUMN `csTimeLimit` VARCHAR(200) DEFAULT NULL COMMENT '合同工期';

ALTER TABLE `zx_ct_other_manage` MODIFY COLUMN `content` VARCHAR(1000) DEFAULT NULL COMMENT '合同内容'; 

ALTER TABLE `zx_ct_other_manage` MODIFY COLUMN `csTimeLimit` VARCHAR(200) DEFAULT NULL COMMENT '合同工期(天)';

ALTER TABLE `zx_ct_other_manage_amtRate` MODIFY COLUMN `backCondition` VARCHAR(200) DEFAULT NULL COMMENT '返还条件';

ALTER TABLE `zx_ct_other_supply_agreement` MODIFY COLUMN `work_id` CHAR(64) DEFAULT NULL COMMENT '流程ID';

ALTER TABLE `zx_ct_other_supply_agreement` MODIFY COLUMN `content` VARCHAR(1000) DEFAULT NULL COMMENT '合同内容'; 




























