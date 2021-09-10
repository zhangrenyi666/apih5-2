package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqToEquipCategoryQueryPage;

public interface ZxEqToEquipCategoryQueryPageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqToEquipCategoryQueryPage record);

    int insertSelective(ZxEqToEquipCategoryQueryPage record);

    ZxEqToEquipCategoryQueryPage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqToEquipCategoryQueryPage record);

    int updateByPrimaryKey(ZxEqToEquipCategoryQueryPage record);

    List<ZxEqToEquipCategoryQueryPage> selectByZxEqToEquipCategoryQueryPageList(ZxEqToEquipCategoryQueryPage record);

    int batchDeleteUpdateZxEqToEquipCategoryQueryPage(List<ZxEqToEquipCategoryQueryPage> recordList, ZxEqToEquipCategoryQueryPage record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqToEquipCategoryQueryPage> selectZxEqToEquipCategoryQueryPage(ZxEqToEquipCategoryQueryPage record);
    
    List<ZxEqToEquipCategoryQueryPage> selectZxEqToEquipCategoryQueryPageCount(ZxEqToEquipCategoryQueryPage record);
    
    List<ZxEqToEquipCategoryQueryPage> selectZxEqToEquipCategoryQueryPageOrginalValue(ZxEqToEquipCategoryQueryPage record);
    
    List<ZxEqToEquipCategoryQueryPage> selectZxEqToEquipCategoryQueryPageleftValue(ZxEqToEquipCategoryQueryPage record);
    
}
