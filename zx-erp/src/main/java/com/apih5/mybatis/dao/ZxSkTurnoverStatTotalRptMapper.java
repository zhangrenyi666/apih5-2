package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnoverStatTotalRpt;

public interface ZxSkTurnoverStatTotalRptMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnoverStatTotalRpt record);

    int insertSelective(ZxSkTurnoverStatTotalRpt record);

    ZxSkTurnoverStatTotalRpt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnoverStatTotalRpt record);

    int updateByPrimaryKey(ZxSkTurnoverStatTotalRpt record);

    List<ZxSkTurnoverStatTotalRpt> selectByZxSkTurnoverStatTotalRptList(ZxSkTurnoverStatTotalRpt record);

    int batchDeleteUpdateZxSkTurnoverStatTotalRpt(List<ZxSkTurnoverStatTotalRpt> recordList, ZxSkTurnoverStatTotalRpt record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkTurnoverStatTotalRpt> selectZxSkTurnoverStatTotalRpt(ZxSkTurnoverStatTotalRpt record);
}
