package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnoverTotalRpt;

public interface ZxSkTurnoverTotalRptMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnoverTotalRpt record);

    int insertSelective(ZxSkTurnoverTotalRpt record);

    ZxSkTurnoverTotalRpt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnoverTotalRpt record);

    int updateByPrimaryKey(ZxSkTurnoverTotalRpt record);

    List<ZxSkTurnoverTotalRpt> selectByZxSkTurnoverTotalRptList(ZxSkTurnoverTotalRpt record);

    int batchDeleteUpdateZxSkTurnoverTotalRpt(List<ZxSkTurnoverTotalRpt> recordList, ZxSkTurnoverTotalRpt record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkTurnoverTotalRpt> selectZxSkTurnoverTotalRpt(ZxSkTurnoverTotalRpt record);
}
