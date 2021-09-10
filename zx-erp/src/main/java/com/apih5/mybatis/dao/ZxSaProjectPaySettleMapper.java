package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettle;

public interface ZxSaProjectPaySettleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectPaySettle record);

    int insertSelective(ZxSaProjectPaySettle record);

    ZxSaProjectPaySettle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectPaySettle record);

    int updateByPrimaryKey(ZxSaProjectPaySettle record);

    List<ZxSaProjectPaySettle> selectByZxSaProjectPaySettleList(ZxSaProjectPaySettle record);

    int batchDeleteUpdateZxSaProjectPaySettle(List<ZxSaProjectPaySettle> recordList, ZxSaProjectPaySettle record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZxSaProjectPaySettle getUpZxSaProjectPaySettle(ZxSaProjectPaySettle upPaySettle);
}
