package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjConsumableApplyBook;

public interface ZjConsumableApplyBookMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjConsumableApplyBook record);

    int insertSelective(ZjConsumableApplyBook record);

    ZjConsumableApplyBook selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjConsumableApplyBook record);

    int updateByPrimaryKey(ZjConsumableApplyBook record);

    List<ZjConsumableApplyBook> selectByZjConsumableApplyBookList(ZjConsumableApplyBook record);

    int batchDeleteUpdateZjConsumableApplyBook(List<ZjConsumableApplyBook> recordList, ZjConsumableApplyBook record);

}

