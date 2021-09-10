package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkTurnOverStock;

public interface ZxSkTurnOverStockMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnOverStock record);

    int insertSelective(ZxSkTurnOverStock record);

    ZxSkTurnOverStock selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnOverStock record);

    int updateByPrimaryKey(ZxSkTurnOverStock record);

    List<ZxSkTurnOverStock> selectByZxSkTurnOverStockList(ZxSkTurnOverStock record);

    int batchDeleteUpdateZxSkTurnOverStock(List<ZxSkTurnOverStock> recordList, ZxSkTurnOverStock record);

    ZxSkTurnOverStock selectReturnZxSkStockList(ZxSkTurnOverStock zxSkTurnOverStock1);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int updateZxSkTurnOverStockOriginalAmtAndRemainAmt(ZxSkTurnOverStock zxSkTurnOverStock1);

    int updateZxSkTurnOverStockScrap(ZxSkTurnOverStock zxSkTurnOverStock1);

}
