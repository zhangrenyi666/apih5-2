package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtEqCoContractAmtRate;

public interface ZxCtEqCoContractAmtRateMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtEqCoContractAmtRate record);

    int insertSelective(ZxCtEqCoContractAmtRate record);

    ZxCtEqCoContractAmtRate selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtEqCoContractAmtRate record);

    int updateByPrimaryKey(ZxCtEqCoContractAmtRate record);

    List<ZxCtEqCoContractAmtRate> selectByZxCtEqCoContractAmtRateList(ZxCtEqCoContractAmtRate record);

    int batchDeleteUpdateZxCtEqCoContractAmtRate(List<ZxCtEqCoContractAmtRate> recordList, ZxCtEqCoContractAmtRate record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
