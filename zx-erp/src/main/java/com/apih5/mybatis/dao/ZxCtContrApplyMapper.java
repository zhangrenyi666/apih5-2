package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContrApply;
import com.apih5.mybatis.pojo.ZxSkStockTransItemSalesReturn;
import org.apache.ibatis.annotations.Mapper;

public interface ZxCtContrApplyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContrApply record);

    int insertSelective(ZxCtContrApply record);

    ZxCtContrApply selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContrApply record);

    int updateByPrimaryKey(ZxCtContrApply record);

    List<ZxCtContrApply> selectByZxCtContrApplyList(ZxCtContrApply record);

    int batchDeleteUpdateZxCtContrApply(List<ZxCtContrApply> recordList, ZxCtContrApply record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxCtContrApply> selectByZxCtContrApplyFirstNameList(ZxCtContrApply record);
}
