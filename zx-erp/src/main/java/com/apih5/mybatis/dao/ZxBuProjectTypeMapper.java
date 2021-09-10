package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuProjectType;

public interface ZxBuProjectTypeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuProjectType record);

    int insertSelective(ZxBuProjectType record);

    ZxBuProjectType selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuProjectType record);

    int updateByPrimaryKey(ZxBuProjectType record);

    List<ZxBuProjectType> selectByZxBuProjectTypeList(ZxBuProjectType record);

    int batchDeleteUpdateZxBuProjectType(List<ZxBuProjectType> recordList, ZxBuProjectType record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int checkZxBuProjectType(ZxBuProjectType dbzxBuProjectType);
}
