package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuProjectTypeCheck;

public interface ZxBuProjectTypeCheckMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuProjectTypeCheck record);

    int insertSelective(ZxBuProjectTypeCheck record);

    ZxBuProjectTypeCheck selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuProjectTypeCheck record);

    int updateByPrimaryKey(ZxBuProjectTypeCheck record);

    List<ZxBuProjectTypeCheck> selectByZxBuProjectTypeCheckList(ZxBuProjectTypeCheck record);

    int batchDeleteUpdateZxBuProjectTypeCheck(List<ZxBuProjectTypeCheck> recordList, ZxBuProjectTypeCheck record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxBuProjectTypeCheck> getZxBuProjectTypeCheckProjectType(ZxBuProjectTypeCheck zxBuProjectTypeCheck);

    int updateZxBuProjectTypeCheck(ZxBuProjectTypeCheck updateZxBuProjectTypeCheck);


}
