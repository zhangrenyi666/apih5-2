package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqIecsCBS;

public interface ZxEqIecsCBSMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqIecsCBS record);

    int insertSelective(ZxEqIecsCBS record);

    ZxEqIecsCBS selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqIecsCBS record);

    int updateByPrimaryKey(ZxEqIecsCBS record);

    List<ZxEqIecsCBS> selectByZxEqIecsCBSList(ZxEqIecsCBS record);

    int batchDeleteUpdateZxEqIecsCBS(List<ZxEqIecsCBS> recordList, ZxEqIecsCBS record);

    List<ZxEqIecsCBS> selectByZxEqIecsCBSListOrgId(ZxEqIecsCBS zxEqIecsCBS);

    List<ZxEqIecsCBS> selectByZxEqIecsCBSPickingList(ZxEqIecsCBS zxEqIecsCBS);
}

