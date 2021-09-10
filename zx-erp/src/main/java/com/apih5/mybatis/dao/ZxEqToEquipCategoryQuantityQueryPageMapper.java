package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqToEquipCategoryQuantityQueryPage;

public interface ZxEqToEquipCategoryQuantityQueryPageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqToEquipCategoryQuantityQueryPage record);

    int insertSelective(ZxEqToEquipCategoryQuantityQueryPage record);

    ZxEqToEquipCategoryQuantityQueryPage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqToEquipCategoryQuantityQueryPage record);

    int updateByPrimaryKey(ZxEqToEquipCategoryQuantityQueryPage record);

    List<ZxEqToEquipCategoryQuantityQueryPage> selectByZxEqToEquipCategoryQuantityQueryPageList(ZxEqToEquipCategoryQuantityQueryPage record);

    int batchDeleteUpdateZxEqToEquipCategoryQuantityQueryPage(List<ZxEqToEquipCategoryQuantityQueryPage> recordList, ZxEqToEquipCategoryQuantityQueryPage record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqToEquipCategoryQuantityQueryPage> selectZxEqToEquipCategoryQuantityQueryPage(ZxEqToEquipCategoryQuantityQueryPage record);
    
    List<ZxEqToEquipCategoryQuantityQueryPage> selectZxEqToEquipCategoryQuantityQueryPageChart(ZxEqToEquipCategoryQuantityQueryPage record);
    
    List<ZxEqToEquipCategoryQuantityQueryPage> selectZxEqToEquipCategoryQuantityQueryPageRentOut(ZxEqToEquipCategoryQuantityQueryPage record);
    
    List<ZxEqToEquipCategoryQuantityQueryPage> selectZxEqToEquipCategoryQuantityQueryPageCooperationUnit(ZxEqToEquipCategoryQuantityQueryPage record);
}
