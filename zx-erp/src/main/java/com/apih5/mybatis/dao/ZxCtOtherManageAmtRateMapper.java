package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtOtherManageAmtRate;

public interface ZxCtOtherManageAmtRateMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtOtherManageAmtRate record);

    int insertSelective(ZxCtOtherManageAmtRate record);

    ZxCtOtherManageAmtRate selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtOtherManageAmtRate record);

    int updateByPrimaryKey(ZxCtOtherManageAmtRate record);

    List<ZxCtOtherManageAmtRate> selectByZxCtOtherManageAmtRateList(ZxCtOtherManageAmtRate record);

    int batchDeleteUpdateZxCtOtherManageAmtRate(List<ZxCtOtherManageAmtRate> recordList, ZxCtOtherManageAmtRate record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
