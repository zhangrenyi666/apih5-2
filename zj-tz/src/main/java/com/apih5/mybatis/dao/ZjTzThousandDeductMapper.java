package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThousandDeduct;

public interface ZjTzThousandDeductMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThousandDeduct record);

    int insertSelective(ZjTzThousandDeduct record);

    ZjTzThousandDeduct selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThousandDeduct record);

    int updateByPrimaryKey(ZjTzThousandDeduct record);

    List<ZjTzThousandDeduct> selectByZjTzThousandDeductList(ZjTzThousandDeduct record);

    int batchDeleteUpdateZjTzThousandDeduct(List<ZjTzThousandDeduct> recordList, ZjTzThousandDeduct record);

}

