package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqIecmOrg;

public interface ZxEqIecmOrgMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqIecmOrg record);

    int insertSelective(ZxEqIecmOrg record);

    ZxEqIecmOrg selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqIecmOrg record);

    int updateByPrimaryKey(ZxEqIecmOrg record);

    List<ZxEqIecmOrg> selectByZxEqIecmOrgList(ZxEqIecmOrg record);

    int batchDeleteUpdateZxEqIecmOrg(List<ZxEqIecmOrg> recordList, ZxEqIecmOrg record);

}

