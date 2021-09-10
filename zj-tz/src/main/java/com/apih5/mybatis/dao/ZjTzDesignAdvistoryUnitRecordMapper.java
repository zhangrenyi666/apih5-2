package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnitRecord;

public interface ZjTzDesignAdvistoryUnitRecordMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDesignAdvistoryUnitRecord record);

    int insertSelective(ZjTzDesignAdvistoryUnitRecord record);

    ZjTzDesignAdvistoryUnitRecord selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDesignAdvistoryUnitRecord record);

    int updateByPrimaryKey(ZjTzDesignAdvistoryUnitRecord record);

    List<ZjTzDesignAdvistoryUnitRecord> selectByZjTzDesignAdvistoryUnitRecordList(ZjTzDesignAdvistoryUnitRecord record);

    int batchDeleteUpdateZjTzDesignAdvistoryUnitRecord(List<ZjTzDesignAdvistoryUnitRecord> recordList, ZjTzDesignAdvistoryUnitRecord record);

	int batchRecallZjTzDesignAdvistoryUnitRecord(List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList,ZjTzDesignAdvistoryUnitRecord zjTzRules);

}

