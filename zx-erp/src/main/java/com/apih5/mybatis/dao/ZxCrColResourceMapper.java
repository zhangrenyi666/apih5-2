package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrColResource;

public interface ZxCrColResourceMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrColResource record);

    int insertSelective(ZxCrColResource record);

    ZxCrColResource selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrColResource record);

    int updateByPrimaryKey(ZxCrColResource record);

    List<ZxCrColResource> selectByZxCrColResourceList(ZxCrColResource record);

    int batchDeleteUpdateZxCrColResource(List<ZxCrColResource> recordList, ZxCrColResource record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
