package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqMoveUseOrg;

public interface ZxEqMoveUseOrgMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqMoveUseOrg record);

    int insertSelective(ZxEqMoveUseOrg record);

    ZxEqMoveUseOrg selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqMoveUseOrg record);

    int updateByPrimaryKey(ZxEqMoveUseOrg record);

    List<ZxEqMoveUseOrg> selectByZxEqMoveUseOrgList(ZxEqMoveUseOrg record);

    int batchDeleteUpdateZxEqMoveUseOrg(List<ZxEqMoveUseOrg> recordList, ZxEqMoveUseOrg record);

}

