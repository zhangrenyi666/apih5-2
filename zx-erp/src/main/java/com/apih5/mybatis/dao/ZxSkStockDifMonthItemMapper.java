package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkStockDifMonthItem;
import org.apache.ibatis.annotations.Param;

public interface ZxSkStockDifMonthItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkStockDifMonthItem record);

    int insertSelective(ZxSkStockDifMonthItem record);

    ZxSkStockDifMonthItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkStockDifMonthItem record);

    int updateByPrimaryKey(ZxSkStockDifMonthItem record);

    List<ZxSkStockDifMonthItem> selectByZxSkStockDifMonthItemList(ZxSkStockDifMonthItem record);

    int batchDeleteUpdateZxSkStockDifMonthItem(List<ZxSkStockDifMonthItem> recordList, ZxSkStockDifMonthItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxSkStockDifMonthItem>  getStockDifMonthForm();

    List<ZxSkStockDifMonthItem> getStockDifJiDuForm ();
    
    List<ZxSkStockDifMonthItem> getStockDifMonthMaterialCategory ();
    
    List<ZxSkStockDifMonthItem> getReceivingDynamic (ZxSkStockDifMonthItem record);
    
    List<ZxSkStockDifMonthItem> getResMoveMonthMP ();
}
