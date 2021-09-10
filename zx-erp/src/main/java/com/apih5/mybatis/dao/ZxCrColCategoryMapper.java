package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrColCategory;
import com.apih5.mybatis.pojo.ZxCrColResource;

public interface ZxCrColCategoryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrColCategory record);

    int insertSelective(ZxCrColCategory record);

    ZxCrColCategory selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrColCategory record);

    int updateByPrimaryKey(ZxCrColCategory record);

    List<ZxCrColCategory> selectByZxCrColCategoryList(ZxCrColCategory record);

    int batchDeleteUpdateZxCrColCategory(List<ZxCrColCategory> recordList, ZxCrColCategory record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCrColCategory> selectByZxCrColCategoryDear(ZxCrColCategory record);
    
}
