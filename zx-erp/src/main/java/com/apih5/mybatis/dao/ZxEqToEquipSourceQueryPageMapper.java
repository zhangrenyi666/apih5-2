package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqToEquipSourceQueryPage;

public interface ZxEqToEquipSourceQueryPageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqToEquipSourceQueryPage record);

    int insertSelective(ZxEqToEquipSourceQueryPage record);

    ZxEqToEquipSourceQueryPage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqToEquipSourceQueryPage record);

    int updateByPrimaryKey(ZxEqToEquipSourceQueryPage record);

    List<ZxEqToEquipSourceQueryPage> selectByZxEqToEquipSourceQueryPageList(ZxEqToEquipSourceQueryPage record);

    int batchDeleteUpdateZxEqToEquipSourceQueryPage(List<ZxEqToEquipSourceQueryPage> recordList, ZxEqToEquipSourceQueryPage record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxEqToEquipSourceQueryPage> selectZxEqToEquipSourceQueryPage(ZxEqToEquipSourceQueryPage record);
}
