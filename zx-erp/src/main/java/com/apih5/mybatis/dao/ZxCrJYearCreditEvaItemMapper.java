package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrJYearCreditEvaItem;

public interface ZxCrJYearCreditEvaItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrJYearCreditEvaItem record);

    int insertSelective(ZxCrJYearCreditEvaItem record);

    ZxCrJYearCreditEvaItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrJYearCreditEvaItem record);

    int updateByPrimaryKey(ZxCrJYearCreditEvaItem record);

    List<ZxCrJYearCreditEvaItem> selectByZxCrJYearCreditEvaItemList(ZxCrJYearCreditEvaItem record);

    int batchDeleteUpdateZxCrJYearCreditEvaItem(List<ZxCrJYearCreditEvaItem> recordList, ZxCrJYearCreditEvaItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCrJYearCreditEvaItem> selectInit(ZxCrJYearCreditEvaItem record);
    
    int insertInit(ZxCrJYearCreditEvaItem record);
}
