package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkResCategoryMaterials;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;

public interface ZxSkResCategoryMaterialsMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkResCategoryMaterials record);

    int insertSelective(ZxSkResCategoryMaterials record);

    ZxSkResCategoryMaterials selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkResCategoryMaterials record);

    int updateByPrimaryKey(ZxSkResCategoryMaterials record);

    List<ZxSkResCategoryMaterials> selectByZxSkResCategoryMaterialsList(ZxSkResCategoryMaterials record);

    int batchDeleteUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> recordList, ZxSkResCategoryMaterials record);

    int batchStartUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList, ZxSkResCategoryMaterials resCategoryMaterials);

    int batchStopUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList, ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    List<ZxSkResourceMaterials> selectZxSkResourceMaterialsListNameJoinResource(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    List<ZxSkResourceMaterials> getZxSkResCategoryMaterialsAllResource(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    List<ZxSkResCategoryMaterials> getZxSkResCategoryMaterialsListResourceNotRevolve(ZxSkResCategoryMaterials zxSkResCategoryMaterials);
}

