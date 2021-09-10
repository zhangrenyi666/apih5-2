package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseFlowStartSetting;

public interface BaseFlowStartSettingMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseFlowStartSetting record);

    int insertSelective(BaseFlowStartSetting record);

    BaseFlowStartSetting selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseFlowStartSetting record);

    int updateByPrimaryKey(BaseFlowStartSetting record);

    List<BaseFlowStartSetting> selectByBaseFlowStartSettingList(BaseFlowStartSetting record);

    int batchDeleteUpdateBaseFlowStartSetting(List<BaseFlowStartSetting> recordList);

}

