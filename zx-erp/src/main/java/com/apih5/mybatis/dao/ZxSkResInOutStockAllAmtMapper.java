package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkResInOutStockAllAmt;

public interface ZxSkResInOutStockAllAmtMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkResInOutStockAllAmt record);

    int insertSelective(ZxSkResInOutStockAllAmt record);

    ZxSkResInOutStockAllAmt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkResInOutStockAllAmt record);

    int updateByPrimaryKey(ZxSkResInOutStockAllAmt record);

    List<ZxSkResInOutStockAllAmt> selectByZxSkResInOutStockAllAmtList(ZxSkResInOutStockAllAmt record);

    int batchDeleteUpdateZxSkResInOutStockAllAmt(List<ZxSkResInOutStockAllAmt> recordList, ZxSkResInOutStockAllAmt record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkResInOutStockAllAmt> selectZxSkResInOutStockAllAmt(ZxSkResInOutStockAllAmt record);
}
