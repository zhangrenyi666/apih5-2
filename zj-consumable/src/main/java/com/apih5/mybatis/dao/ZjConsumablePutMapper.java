package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjConsumablePut;

public interface ZjConsumablePutMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjConsumablePut record);

    int insertSelective(ZjConsumablePut record);

    ZjConsumablePut selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjConsumablePut record);

    int updateByPrimaryKey(ZjConsumablePut record);

    List<ZjConsumablePut> selectByZjConsumablePutList(ZjConsumablePut record);

    int batchDeleteUpdateZjConsumablePut(List<ZjConsumablePut> recordList, ZjConsumablePut record);

}

