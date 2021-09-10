package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzDesignChangeStatistics;

public interface ZjTzDesignChangeStatisticsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzDesignChangeStatistics record);

    int insertSelective(ZjTzDesignChangeStatistics record);

    ZjTzDesignChangeStatistics selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzDesignChangeStatistics record);

    int updateByPrimaryKey(ZjTzDesignChangeStatistics record);

    List<ZjTzDesignChangeStatistics> selectByZjTzDesignChangeStatisticsList(ZjTzDesignChangeStatistics record);

    int batchDeleteUpdateZjTzDesignChangeStatistics(List<ZjTzDesignChangeStatistics> recordList, ZjTzDesignChangeStatistics record);

}

