package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzGeneralContractSecurityDuty;

public interface ZjTzGeneralContractSecurityDutyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzGeneralContractSecurityDuty record);

    int insertSelective(ZjTzGeneralContractSecurityDuty record);

    ZjTzGeneralContractSecurityDuty selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzGeneralContractSecurityDuty record);

    int updateByPrimaryKey(ZjTzGeneralContractSecurityDuty record);

    List<ZjTzGeneralContractSecurityDuty> selectByZjTzGeneralContractSecurityDutyList(ZjTzGeneralContractSecurityDuty record);

    int batchDeleteUpdateZjTzGeneralContractSecurityDuty(List<ZjTzGeneralContractSecurityDuty> recordList, ZjTzGeneralContractSecurityDuty record);

}

