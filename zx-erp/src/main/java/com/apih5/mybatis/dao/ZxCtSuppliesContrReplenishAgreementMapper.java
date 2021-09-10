package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishAgreement;

public interface ZxCtSuppliesContrReplenishAgreementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrReplenishAgreement record);

    int insertSelective(ZxCtSuppliesContrReplenishAgreement record);

    ZxCtSuppliesContrReplenishAgreement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrReplenishAgreement record);

    int updateByPrimaryKey(ZxCtSuppliesContrReplenishAgreement record);

    List<ZxCtSuppliesContrReplenishAgreement> selectByZxCtSuppliesContrReplenishAgreementList(ZxCtSuppliesContrReplenishAgreement record);

    int batchDeleteUpdateZxCtSuppliesContrReplenishAgreement(List<ZxCtSuppliesContrReplenishAgreement> recordList, ZxCtSuppliesContrReplenishAgreement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    String getZxCtReplenishAgreementNoByCode(ZxCtSuppliesContrReplenishAgreement record);
    
    ZxCtSuppliesContrReplenishAgreement getDetailByWorkId(String key);
}
