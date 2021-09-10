package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaEquipResSettle;

public interface ZxSaEquipResSettleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaEquipResSettle record);

    int insertSelective(ZxSaEquipResSettle record);

    ZxSaEquipResSettle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaEquipResSettle record);

    int updateByPrimaryKey(ZxSaEquipResSettle record);

    List<ZxSaEquipResSettle> selectByZxSaEquipResSettleList(ZxSaEquipResSettle record);

    int batchDeleteUpdateZxSaEquipResSettle(List<ZxSaEquipResSettle> recordList, ZxSaEquipResSettle record);

	List<ZxSaEquipResSettle> getZxSaEquipResSettleListLessAutoNum(ZxSaEquipResSettle equipResSettle);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
