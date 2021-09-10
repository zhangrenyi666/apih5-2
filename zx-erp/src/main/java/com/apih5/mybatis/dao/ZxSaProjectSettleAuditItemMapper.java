package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAuditItem;

public interface ZxSaProjectSettleAuditItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectSettleAuditItem record);

    int insertSelective(ZxSaProjectSettleAuditItem record);

    ZxSaProjectSettleAuditItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectSettleAuditItem record);

    int updateByPrimaryKey(ZxSaProjectSettleAuditItem record);

    List<ZxSaProjectSettleAuditItem> selectByZxSaProjectSettleAuditItemList(ZxSaProjectSettleAuditItem record);

    int batchDeleteUpdateZxSaProjectSettleAuditItem(List<ZxSaProjectSettleAuditItem> recordList, ZxSaProjectSettleAuditItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int deleteAllZxSaProjectSettleAuditItem(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

	List<ZxSaProjectSettleAuditItem> getZxSaProjectSettleAuditItemBeforeTotalList(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem);

	ZxSaProjectSettleAuditItem selectTotalAmtUpAmt(ZxSaProjectSettleAuditItem projectSettleAuditItem);
}
