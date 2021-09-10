package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtEqContrApply;

public interface ZxCtEqContrApplyMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtEqContrApply record);

    int insertSelective(ZxCtEqContrApply record);

    ZxCtEqContrApply selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtEqContrApply record);

    int updateByPrimaryKey(ZxCtEqContrApply record);

    List<ZxCtEqContrApply> selectByZxCtEqContrApplyList(ZxCtEqContrApply record);

    int batchDeleteUpdateZxCtEqContrApply(List<ZxCtEqContrApply> recordList, ZxCtEqContrApply record);

	List<ZxCtEqContrApply> selectByZxCtEqContrApplyListAll(ZxCtEqContrApply record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
