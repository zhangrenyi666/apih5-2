package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnit;

public interface ZjTzDesignAdvistoryUnitMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDesignAdvistoryUnit record);

    int insertSelective(ZjTzDesignAdvistoryUnit record);

    ZjTzDesignAdvistoryUnit selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDesignAdvistoryUnit record);

    int updateByPrimaryKey(ZjTzDesignAdvistoryUnit record);

    List<ZjTzDesignAdvistoryUnit> selectByZjTzDesignAdvistoryUnitList(ZjTzDesignAdvistoryUnit record);

    int batchDeleteUpdateZjTzDesignAdvistoryUnit(List<ZjTzDesignAdvistoryUnit> recordList, ZjTzDesignAdvistoryUnit record);

	int updateZjTzDesignAdvistoryUnitProjectShortName(ZjTzDesignAdvistoryUnit designAdvistoryUnit);

}

