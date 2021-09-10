package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnitStandard;

public interface ZjTzDesignAdvistoryUnitStandardMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDesignAdvistoryUnitStandard record);

    int insertSelective(ZjTzDesignAdvistoryUnitStandard record);

    ZjTzDesignAdvistoryUnitStandard selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDesignAdvistoryUnitStandard record);

    int updateByPrimaryKey(ZjTzDesignAdvistoryUnitStandard record);

    List<ZjTzDesignAdvistoryUnitStandard> selectByZjTzDesignAdvistoryUnitStandardList(ZjTzDesignAdvistoryUnitStandard record);

    int batchDeleteUpdateZjTzDesignAdvistoryUnitStandard(List<ZjTzDesignAdvistoryUnitStandard> recordList, ZjTzDesignAdvistoryUnitStandard record);

}

