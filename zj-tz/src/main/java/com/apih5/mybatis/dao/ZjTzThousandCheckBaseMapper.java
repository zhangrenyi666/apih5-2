package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThousandCheckBase;

public interface ZjTzThousandCheckBaseMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThousandCheckBase record);

    int insertSelective(ZjTzThousandCheckBase record);

    ZjTzThousandCheckBase selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThousandCheckBase record);

    int updateByPrimaryKey(ZjTzThousandCheckBase record);

    List<ZjTzThousandCheckBase> selectByZjTzThousandCheckBaseList(ZjTzThousandCheckBase record);

    int batchDeleteUpdateZjTzThousandCheckBase(List<ZjTzThousandCheckBase> recordList, ZjTzThousandCheckBase record);

	List<ZjTzThousandCheckBase> getZjTzThousandCheckBaseItemList(ZjTzThousandCheckBase base);

}

