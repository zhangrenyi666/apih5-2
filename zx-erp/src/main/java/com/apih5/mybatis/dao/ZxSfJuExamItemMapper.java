package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.ZxSfExamItem;
import com.apih5.mybatis.pojo.ZxSfJuExamItem;

public interface ZxSfJuExamItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfJuExamItem record);

    int insertSelective(ZxSfJuExamItem record);

    ZxSfJuExamItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfJuExamItem record);

    int updateByPrimaryKey(ZxSfJuExamItem record);

    List<ZxSfJuExamItem> selectByZxSfJuExamItemList(ZxSfJuExamItem record);

    int batchDeleteUpdateZxSfJuExamItem(List<ZxSfJuExamItem> recordList, ZxSfJuExamItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    int insertInit(ZxSfJuExamItem record);
}
