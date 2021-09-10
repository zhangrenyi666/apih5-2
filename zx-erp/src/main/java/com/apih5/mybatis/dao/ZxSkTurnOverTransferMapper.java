package com.apih5.mybatis.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZxSkTurnOverTransfer;
import com.apih5.mybatis.pojo.ZxSkTurnOverTransferItem;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

public interface ZxSkTurnOverTransferMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkTurnOverTransfer record);

    int insertSelective(ZxSkTurnOverTransfer record);

    ZxSkTurnOverTransfer selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkTurnOverTransfer record);

    int updateByPrimaryKey(ZxSkTurnOverTransfer record);

    List<ZxSkTurnOverTransfer> selectByZxSkTurnOverTransferList(ZxSkTurnOverTransfer record);

    int batchDeleteUpdateZxSkTurnOverTransfer(List<ZxSkTurnOverTransfer> recordList, ZxSkTurnOverTransfer record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSkTurnOverTransferItem> getZxSkTurnOverTransferResourceList(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    int checkZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    @MapKey("batchNo")
    Map<String, ZxSkTurnOverTransferItem> selectStockPrice(@Param("zxSkTurnOverTransferItemsList") List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemsList);

    int getZxSkTurnOverTransferCount(@Param("date") String date,@Param("orgID")String orgID);

}
