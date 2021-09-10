package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

public interface ZxSkTurnoverCheckMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnoverCheck record);

    int insertSelective(ZxSkTurnoverCheck record);

    ZxSkTurnoverCheck selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnoverCheck record);

    int updateByPrimaryKey(ZxSkTurnoverCheck record);

    List<ZxSkTurnoverCheck> selectByZxSkTurnoverCheckList(ZxSkTurnoverCheck record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int batchDeleteUpdateZxSkTurnoverCheck(List<ZxSkTurnoverCheck> recordList, ZxSkTurnoverCheck record);

    List<ZxSkTurnoverIn> getZxSkTurnoverCheckReceive(ZxSkTurnoverCheck zxSkTurnoverCheck);

//    @MapKey("batchNo")
    List<ZxSkTurnoverCheckItem> getOldRemainAmt(@Param("zxSkTurnoverCheck") ZxSkTurnoverCheck zxSkTurnoverCheck, @Param("zxSkTurnoverInItemList") List<ZxSkTurnoverCheckItem> zxSkTurnoverInItemList);

    int updateStockRemainAmt(@Param("oldRemainAmt") List<ZxSkTurnoverCheckItem> oldRemainAmt);

    List<ZxSkTurnoverCheck> getZxSkTurnoverCheckListForReport(ZxSkTurnoverCheck zxSkTurnoverCheck);

    int getZxSkTurnoverCheckCount(String date);

    int checkZxSkTurnoverCheck(ZxSkTurnoverCheck zxSkTurnoverCheck);

    List<ZxCrCustomerNew> getZxSkTurnoverCheckSupplierList(ZxSkTurnoverCheck zxSkTurnoverCheck);

}
