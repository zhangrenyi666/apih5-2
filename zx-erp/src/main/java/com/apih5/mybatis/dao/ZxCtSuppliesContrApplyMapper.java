package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApply;

public interface ZxCtSuppliesContrApplyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtSuppliesContrApply record);

    int insertSelective(ZxCtSuppliesContrApply record);

    ZxCtSuppliesContrApply selectByPrimaryKey(String key);
    
    ZxCtSuppliesContrApply getDetailByWorkId(String key);

    int updateByPrimaryKeySelective(ZxCtSuppliesContrApply record);

    int updateByPrimaryKey(ZxCtSuppliesContrApply record);
    
    List<ZxCtSuppliesContrApply> selectByZxCtSuppliesContrApplyList(ZxCtSuppliesContrApply record);

    int batchDeleteUpdateZxCtSuppliesContrApply(List<ZxCtSuppliesContrApply> recordList, ZxCtSuppliesContrApply record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    String getZxCtSuppliesContrNoByCode(ZxCtSuppliesContrApply record);
}
