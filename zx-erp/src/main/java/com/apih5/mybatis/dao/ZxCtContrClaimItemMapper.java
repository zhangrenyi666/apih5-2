package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrClaimItem;

public interface ZxCtContrClaimItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrClaimItem record);

    int insertSelective(ZxCtContrClaimItem record);

    ZxCtContrClaimItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrClaimItem record);

    int updateByPrimaryKey(ZxCtContrClaimItem record);

    List<ZxCtContrClaimItem> selectByZxCtContrClaimItemList(ZxCtContrClaimItem record);

    int batchDeleteUpdateZxCtContrClaimItem(List<ZxCtContrClaimItem> recordList, ZxCtContrClaimItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int delAllZxCtContrClaimItem(ZxCtContrClaimItem zxCtContrClaimItem);
}
