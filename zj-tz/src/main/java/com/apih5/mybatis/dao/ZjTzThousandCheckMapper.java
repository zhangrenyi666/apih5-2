package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThousandCheck;

public interface ZjTzThousandCheckMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThousandCheck record);

    int insertSelective(ZjTzThousandCheck record);

    ZjTzThousandCheck selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThousandCheck record);

    int updateByPrimaryKey(ZjTzThousandCheck record);

    List<ZjTzThousandCheck> selectByZjTzThousandCheckList(ZjTzThousandCheck record);

    int batchDeleteUpdateZjTzThousandCheck(List<ZjTzThousandCheck> recordList, ZjTzThousandCheck record);

	int batchReleaseZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList, ZjTzThousandCheck zjTzRules);

	int batchRecallZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList, ZjTzThousandCheck zjTzRules);

}

