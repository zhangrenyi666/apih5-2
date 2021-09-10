package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDesignFlow;

public interface ZjTzDesignFlowMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDesignFlow record);

    int insertSelective(ZjTzDesignFlow record);

    ZjTzDesignFlow selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDesignFlow record);

    int updateByPrimaryKey(ZjTzDesignFlow record);

    List<ZjTzDesignFlow> selectByZjTzDesignFlowList(ZjTzDesignFlow record);

    int batchDeleteUpdateZjTzDesignFlow(List<ZjTzDesignFlow> recordList, ZjTzDesignFlow record);

	int batchRecallZjTzDesignFlow(List<ZjTzDesignFlow> zjTzDesignFlowList, ZjTzDesignFlow zjTzRules);

	int updateZjTzDesignFlowProjectShortName(ZjTzDesignFlow designFlow);

}

