package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEvaItem;

public interface ZxCrHalfYearCreditEvaItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrHalfYearCreditEvaItem record);

    int insertSelective(ZxCrHalfYearCreditEvaItem record);

    ZxCrHalfYearCreditEvaItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrHalfYearCreditEvaItem record);

    int updateByPrimaryKey(ZxCrHalfYearCreditEvaItem record);

    List<ZxCrHalfYearCreditEvaItem> selectByZxCrHalfYearCreditEvaItemList(ZxCrHalfYearCreditEvaItem record);

    int batchDeleteUpdateZxCrHalfYearCreditEvaItem(List<ZxCrHalfYearCreditEvaItem> recordList, ZxCrHalfYearCreditEvaItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCrHalfYearCreditEvaItem> selectInit(ZxCrHalfYearCreditEvaItem record);
    
    int insertInit(ZxCrHalfYearCreditEvaItem record);
    
    List<ZxCrHalfYearCreditEvaItem> selectInitAll(ZxCrHalfYearCreditEvaItem record);
    
    int batchDeleteUpdateAll();

    int deleteByMasterId(ZxCrHalfYearCreditEvaItem record);
}
