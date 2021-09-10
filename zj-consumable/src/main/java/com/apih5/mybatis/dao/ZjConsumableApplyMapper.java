package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjConsumableApply;

public interface ZjConsumableApplyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjConsumableApply record);

    int insertSelective(ZjConsumableApply record);

    ZjConsumableApply selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjConsumableApply record);

    int updateByPrimaryKey(ZjConsumableApply record);

    List<ZjConsumableApply> selectByZjConsumableApplyList(ZjConsumableApply record);

    int batchDeleteUpdateZjConsumableApply(List<ZjConsumableApply> recordList, ZjConsumableApply record);

}

