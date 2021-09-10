package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaOtherEquipResSettle;

public interface ZxSaOtherEquipResSettleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaOtherEquipResSettle record);

    int insertSelective(ZxSaOtherEquipResSettle record);

    ZxSaOtherEquipResSettle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaOtherEquipResSettle record);

    int updateByPrimaryKey(ZxSaOtherEquipResSettle record);

    List<ZxSaOtherEquipResSettle> selectByZxSaOtherEquipResSettleList(ZxSaOtherEquipResSettle record);

    int batchDeleteUpdateZxSaOtherEquipResSettle(List<ZxSaOtherEquipResSettle> recordList, ZxSaOtherEquipResSettle record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZxSaOtherEquipResSettle selectTotalAmt(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle);

}
