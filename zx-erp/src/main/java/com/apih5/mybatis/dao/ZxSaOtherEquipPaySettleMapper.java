package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettle;

public interface ZxSaOtherEquipPaySettleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaOtherEquipPaySettle record);

    int insertSelective(ZxSaOtherEquipPaySettle record);

    ZxSaOtherEquipPaySettle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaOtherEquipPaySettle record);

    int updateByPrimaryKey(ZxSaOtherEquipPaySettle record);

    List<ZxSaOtherEquipPaySettle> selectByZxSaOtherEquipPaySettleList(ZxSaOtherEquipPaySettle record);

    int batchDeleteUpdateZxSaOtherEquipPaySettle(List<ZxSaOtherEquipPaySettle> recordList, ZxSaOtherEquipPaySettle record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    ZxSaOtherEquipPaySettle selectTotalAmt(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle);
}
