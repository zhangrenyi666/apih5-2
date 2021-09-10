package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaFsPaySettlementDetail;

public interface ZxSaFsPaySettlementDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaFsPaySettlementDetail record);

    int insertSelective(ZxSaFsPaySettlementDetail record);

    ZxSaFsPaySettlementDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaFsPaySettlementDetail record);

    int updateByPrimaryKey(ZxSaFsPaySettlementDetail record);

    List<ZxSaFsPaySettlementDetail> selectByZxSaFsPaySettlementDetailList(ZxSaFsPaySettlementDetail record);

    int batchDeleteUpdateZxSaFsPaySettlementDetail(List<ZxSaFsPaySettlementDetail> recordList, ZxSaFsPaySettlementDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
