package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqYyglJbb;

public interface ZxEqYyglJbbMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqYyglJbb record);

    int insertSelective(ZxEqYyglJbb record);

    ZxEqYyglJbb selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqYyglJbb record);

    int updateByPrimaryKey(ZxEqYyglJbb record);

    List<ZxEqYyglJbb> selectByZxEqYyglJbbList(ZxEqYyglJbb record);

    int batchDeleteUpdateZxEqYyglJbb(List<ZxEqYyglJbb> recordList, ZxEqYyglJbb record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    ZxEqYyglJbb selectZxEqYyglJbb(ZxEqYyglJbb record);
}
