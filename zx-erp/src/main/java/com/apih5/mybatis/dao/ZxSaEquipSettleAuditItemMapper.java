package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAuditItem;

public interface ZxSaEquipSettleAuditItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaEquipSettleAuditItem record);

    int insertSelective(ZxSaEquipSettleAuditItem record);

    ZxSaEquipSettleAuditItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaEquipSettleAuditItem record);

    int updateByPrimaryKey(ZxSaEquipSettleAuditItem record);

    List<ZxSaEquipSettleAuditItem> selectByZxSaEquipSettleAuditItemList(ZxSaEquipSettleAuditItem record);

    int batchDeleteUpdateZxSaEquipSettleAuditItem(List<ZxSaEquipSettleAuditItem> recordList, ZxSaEquipSettleAuditItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
