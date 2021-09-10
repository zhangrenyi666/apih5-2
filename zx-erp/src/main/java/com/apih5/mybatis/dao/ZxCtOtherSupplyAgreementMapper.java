package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtOtherSupplyAgreement;

public interface ZxCtOtherSupplyAgreementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtOtherSupplyAgreement record);

    int insertSelective(ZxCtOtherSupplyAgreement record);

    ZxCtOtherSupplyAgreement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtOtherSupplyAgreement record);

    int updateByPrimaryKey(ZxCtOtherSupplyAgreement record);

    List<ZxCtOtherSupplyAgreement> selectByZxCtOtherSupplyAgreementList(ZxCtOtherSupplyAgreement record);

    int batchDeleteUpdateZxCtOtherSupplyAgreement(List<ZxCtOtherSupplyAgreement> recordList, ZxCtOtherSupplyAgreement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxCtOtherSupplyAgreement> selectByContractNo(String contractNo);

}
