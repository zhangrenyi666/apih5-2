package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettle;

public interface ZxSaEquipPaySettleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaEquipPaySettle record);

    int insertSelective(ZxSaEquipPaySettle record);

    ZxSaEquipPaySettle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaEquipPaySettle record);

    int updateByPrimaryKey(ZxSaEquipPaySettle record);

    List<ZxSaEquipPaySettle> selectByZxSaEquipPaySettleList(ZxSaEquipPaySettle record);

    /**
     * 设备租赁结算单: 本期与往期支付项
     */
    List<ZxSaEquipPaySettle> selectByZxSaEquipPaySettlePastList(ZxSaEquipPaySettle record);

    int batchDeleteUpdateZxSaEquipPaySettle(List<ZxSaEquipPaySettle> recordList, ZxSaEquipPaySettle record);

	List<ZxSaEquipPaySettle> getZxSaEquipPaySettleListLessAutoNum(ZxSaEquipPaySettle payLess);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
