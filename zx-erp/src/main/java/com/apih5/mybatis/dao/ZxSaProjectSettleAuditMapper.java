package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxSaProjectSettleAudit;

public interface ZxSaProjectSettleAuditMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectSettleAudit record);

    int insertSelective(ZxSaProjectSettleAudit record);

    ZxSaProjectSettleAudit selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectSettleAudit record);

    int updateByPrimaryKey(ZxSaProjectSettleAudit record);

    List<ZxSaProjectSettleAudit> selectByZxSaProjectSettleAuditList(ZxSaProjectSettleAudit record);

    int batchDeleteUpdateZxSaProjectSettleAudit(List<ZxSaProjectSettleAudit> recordList, ZxSaProjectSettleAudit record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxSaProjectSettleAudit> getZxSaProjectSettleAuditProjectList(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

	List<ZxSaProjectSettleAudit> getZxSaProjectSettleAuditContractNoList(ZxSaProjectSettleAudit zxSaProjectSettleAudit);

	List<ZxSaProjectSettleAudit> getZxSaProjectSettleAuditTotalList(ZxSaProjectSettleAudit checkProjectSettleAudit);

	int updatePayInfoByPrimaryKey(ZxSaProjectSettleAudit dbZxSaProjectSettleAudit);

}
