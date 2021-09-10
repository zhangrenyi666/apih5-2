package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAudit;

public interface ZxSaEquipSettleAuditMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaEquipSettleAudit record);

    int insertSelective(ZxSaEquipSettleAudit record);

    ZxSaEquipSettleAudit selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaEquipSettleAudit record);

    int updateByPrimaryKey(ZxSaEquipSettleAudit record);

    List<ZxSaEquipSettleAudit> selectByZxSaEquipSettleAuditList(ZxSaEquipSettleAudit record);

    int batchDeleteUpdateZxSaEquipSettleAudit(List<ZxSaEquipSettleAudit> recordList, ZxSaEquipSettleAudit record);

	List<ZxSaEquipSettleAudit> getZxSaEquipSettleAuditTotalList(ZxSaEquipSettleAudit checkProjectSettleAudit);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
