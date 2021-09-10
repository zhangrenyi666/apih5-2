package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfCheckItem;

public interface ZxSfCheckItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfCheckItem record);

    int insertSelective(ZxSfCheckItem record);

    ZxSfCheckItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfCheckItem record);

    int updateByPrimaryKey(ZxSfCheckItem record);

    List<ZxSfCheckItem> selectByZxSfCheckItemList(ZxSfCheckItem record);

    int batchDeleteUpdateZxSfCheckItem(List<ZxSfCheckItem> recordList, ZxSfCheckItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
