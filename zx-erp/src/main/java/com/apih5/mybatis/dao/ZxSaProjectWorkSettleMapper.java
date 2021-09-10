package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettle;

public interface ZxSaProjectWorkSettleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaProjectWorkSettle record);

    int insertSelective(ZxSaProjectWorkSettle record);

    ZxSaProjectWorkSettle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaProjectWorkSettle record);

    int updateByPrimaryKey(ZxSaProjectWorkSettle record);

    List<ZxSaProjectWorkSettle> selectByZxSaProjectWorkSettleList(ZxSaProjectWorkSettle record);

    int batchDeleteUpdateZxSaProjectWorkSettle(List<ZxSaProjectWorkSettle> recordList, ZxSaProjectWorkSettle record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZxSaProjectWorkSettle selectPastContractListTotalAmt(ZxSaProjectWorkSettle selectTotalAmt);
}
