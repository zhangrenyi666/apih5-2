package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseAccount;

public interface BaseAccountMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseAccount record);

    int insertSelective(BaseAccount record);

    BaseAccount selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseAccount record);

    int updateByPrimaryKey(BaseAccount record);

    List<BaseAccount> selectByBaseAccountList(BaseAccount record);

    int batchDeleteUpdateBaseAccount(List<BaseAccount> recordList);

    List<BaseAccount> selectByBaseAccountListByLike(String accountId);
}

