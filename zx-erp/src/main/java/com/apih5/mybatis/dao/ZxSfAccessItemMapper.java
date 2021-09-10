package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfAccessItem;

public interface ZxSfAccessItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfAccessItem record);

    int insertSelective(ZxSfAccessItem record);

    ZxSfAccessItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfAccessItem record);

    int updateByPrimaryKey(ZxSfAccessItem record);

    List<ZxSfAccessItem> selectByZxSfAccessItemList(ZxSfAccessItem record);

    int batchDeleteUpdateZxSfAccessItem(List<ZxSfAccessItem> recordList, ZxSfAccessItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
