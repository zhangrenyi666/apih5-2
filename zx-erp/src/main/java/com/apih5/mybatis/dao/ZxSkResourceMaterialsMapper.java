package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;

public interface ZxSkResourceMaterialsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkResourceMaterials record);

    int insertSelective(ZxSkResourceMaterials record);

    ZxSkResourceMaterials selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkResourceMaterials record);

    int updateByPrimaryKey(ZxSkResourceMaterials record);

    List<ZxSkResourceMaterials> selectByZxSkResourceMaterialsList(ZxSkResourceMaterials record);

    int batchDeleteUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> recordList, ZxSkResourceMaterials record);

    int batchStartUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> zxSkResourceMaterialsList, ZxSkResourceMaterials resourceMaterials);

    int batchStopUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> zxSkResourceMaterialsList, ZxSkResourceMaterials resourceMaterials);

    List<ZxSkResourceMaterials> getZxSkResourceMaterialsListNameJoinNotRevolve(ZxSkResourceMaterials zxSkResourceMaterials);

    List<ZxSkResourceMaterials> getZxSkResourceMaterialsListNotRevolving(ZxSkResourceMaterials zxSkResourceMaterials);

}

