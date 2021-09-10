package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtOtherSupplyAgreementWorks;

public interface ZxCtOtherSupplyAgreementWorksMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtOtherSupplyAgreementWorks record);

    int insertSelective(ZxCtOtherSupplyAgreementWorks record);

    ZxCtOtherSupplyAgreementWorks selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtOtherSupplyAgreementWorks record);

    int updateByPrimaryKey(ZxCtOtherSupplyAgreementWorks record);

    List<ZxCtOtherSupplyAgreementWorks> selectByZxCtOtherSupplyAgreementWorksList(ZxCtOtherSupplyAgreementWorks record);

    int batchDeleteUpdateZxCtOtherSupplyAgreementWorks(List<ZxCtOtherSupplyAgreementWorks> recordList, ZxCtOtherSupplyAgreementWorks record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
