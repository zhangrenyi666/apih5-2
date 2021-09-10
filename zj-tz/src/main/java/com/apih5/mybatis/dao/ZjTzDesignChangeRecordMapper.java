package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDesignChangeRecord;

public interface ZjTzDesignChangeRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDesignChangeRecord record);

    int insertSelective(ZjTzDesignChangeRecord record);

    ZjTzDesignChangeRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDesignChangeRecord record);

    int updateByPrimaryKey(ZjTzDesignChangeRecord record);

    List<ZjTzDesignChangeRecord> selectByZjTzDesignChangeRecordList(ZjTzDesignChangeRecord record);

    int batchDeleteUpdateZjTzDesignChangeRecord(List<ZjTzDesignChangeRecord> recordList, ZjTzDesignChangeRecord record);

	int batchRecallZjTzDesignChangeRecord(List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList,ZjTzDesignChangeRecord zjTzRules);

}

