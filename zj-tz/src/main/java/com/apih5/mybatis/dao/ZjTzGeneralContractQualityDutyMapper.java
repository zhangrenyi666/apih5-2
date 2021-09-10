package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzGeneralContractQualityDuty;

public interface ZjTzGeneralContractQualityDutyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzGeneralContractQualityDuty record);

    int insertSelective(ZjTzGeneralContractQualityDuty record);

    ZjTzGeneralContractQualityDuty selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzGeneralContractQualityDuty record);

    int updateByPrimaryKey(ZjTzGeneralContractQualityDuty record);

    List<ZjTzGeneralContractQualityDuty> selectByZjTzGeneralContractQualityDutyList(ZjTzGeneralContractQualityDuty record);

    int batchDeleteUpdateZjTzGeneralContractQualityDuty(List<ZjTzGeneralContractQualityDuty> recordList, ZjTzGeneralContractQualityDuty record);

}

