package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfExamItem;

public interface ZxSfExamItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfExamItem record);

    int insertSelective(ZxSfExamItem record);

    ZxSfExamItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfExamItem record);

    int updateByPrimaryKey(ZxSfExamItem record);

    List<ZxSfExamItem> selectByZxSfExamItemList(ZxSfExamItem record);

    int batchDeleteUpdateZxSfExamItem(List<ZxSfExamItem> recordList, ZxSfExamItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    int insertInit(ZxSfExamItem record);
}
