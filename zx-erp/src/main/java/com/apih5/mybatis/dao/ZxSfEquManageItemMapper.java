package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfEquManageItem;

public interface ZxSfEquManageItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfEquManageItem record);

    int insertSelective(ZxSfEquManageItem record);

    ZxSfEquManageItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfEquManageItem record);

    int updateByPrimaryKey(ZxSfEquManageItem record);

    List<ZxSfEquManageItem> selectByZxSfEquManageItemList(ZxSfEquManageItem record);

    int batchDeleteUpdateZxSfEquManageItem(List<ZxSfEquManageItem> recordList, ZxSfEquManageItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSfEquManageItem> ureportForm(ZxSfEquManageItem record);
}
