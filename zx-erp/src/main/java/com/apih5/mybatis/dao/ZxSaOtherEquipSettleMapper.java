package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettleItem;
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettle;

public interface ZxSaOtherEquipSettleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaOtherEquipSettle record);

    int insertSelective(ZxSaOtherEquipSettle record);

    ZxSaOtherEquipSettle selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaOtherEquipSettle record);

    int updateByPrimaryKey(ZxSaOtherEquipSettle record);

    List<ZxSaOtherEquipSettle> selectByZxSaOtherEquipSettleList(ZxSaOtherEquipSettle record);

    int batchDeleteUpdateZxSaOtherEquipSettle(List<ZxSaOtherEquipSettle> recordList, ZxSaOtherEquipSettle record);

    List<ZxSaOtherEquipSettle> checkPeriod(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    List<ZxSaOtherEquipSettle> checkPeriodStatus(ZxSaOtherEquipSettle dbZxSaOtherEquipSettle);


    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSaOtherEquipSettle> getZxSaOtherEquipSettleListForReport(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    ZxSaOtherEquipSettle getTheLastZxSaOtherEquipSettle(String contractNo);

    List<ZxSaOtherEquipSettle> getZxSaOtherEquipSettleListInfo(ZxSaOtherEquipSettle zxSaOtherEquipSettle);

    List<ZxSaOtherEquipPaySettleItem> getZxSaOtherEquipPaySettleItemList(ZxSaOtherEquipSettle zxSaOtherEquipSettle);
}
