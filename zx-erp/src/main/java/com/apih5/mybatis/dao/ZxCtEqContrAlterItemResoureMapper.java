package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtEqContrAlterItemResoure;

public interface ZxCtEqContrAlterItemResoureMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtEqContrAlterItemResoure record);

    int insertSelective(ZxCtEqContrAlterItemResoure record);

    ZxCtEqContrAlterItemResoure selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtEqContrAlterItemResoure record);

    int updateByPrimaryKey(ZxCtEqContrAlterItemResoure record);

    List<ZxCtEqContrAlterItemResoure> selectByZxCtEqContrAlterItemResoureList(ZxCtEqContrAlterItemResoure record);

    int batchDeleteUpdateZxCtEqContrAlterItemResoure(List<ZxCtEqContrAlterItemResoure> recordList, ZxCtEqContrAlterItemResoure record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
