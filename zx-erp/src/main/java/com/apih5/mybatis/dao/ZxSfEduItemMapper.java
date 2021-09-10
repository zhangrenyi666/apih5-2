package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfEduItem;

public interface ZxSfEduItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfEduItem record);

    int insertSelective(ZxSfEduItem record);

    ZxSfEduItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfEduItem record);

    int updateByPrimaryKey(ZxSfEduItem record);

    List<ZxSfEduItem> selectByZxSfEduItemList(ZxSfEduItem record);

    int batchDeleteUpdateZxSfEduItem(List<ZxSfEduItem> recordList, ZxSfEduItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSfEduItem> uReportForm(ZxSfEduItem record);
}
