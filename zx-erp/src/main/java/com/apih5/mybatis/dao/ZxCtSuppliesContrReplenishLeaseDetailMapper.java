package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseDetail;

public interface ZxCtSuppliesContrReplenishLeaseDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrReplenishLeaseDetail record);

    int insertSelective(ZxCtSuppliesContrReplenishLeaseDetail record);

    ZxCtSuppliesContrReplenishLeaseDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrReplenishLeaseDetail record);

    int updateByPrimaryKey(ZxCtSuppliesContrReplenishLeaseDetail record);

    List<ZxCtSuppliesContrReplenishLeaseDetail> selectByZxCtSuppliesContrReplenishLeaseDetailList(ZxCtSuppliesContrReplenishLeaseDetail record);

    int batchDeleteUpdateZxCtSuppliesContrReplenishLeaseDetail(List<ZxCtSuppliesContrReplenishLeaseDetail> recordList, ZxCtSuppliesContrReplenishLeaseDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
