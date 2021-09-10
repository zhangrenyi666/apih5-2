package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkResMoveMonthSMP;

public interface ZxSkResMoveMonthSMPMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkResMoveMonthSMP record);

    int insertSelective(ZxSkResMoveMonthSMP record);

    ZxSkResMoveMonthSMP selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkResMoveMonthSMP record);

    int updateByPrimaryKey(ZxSkResMoveMonthSMP record);

    List<ZxSkResMoveMonthSMP> selectByZxSkResMoveMonthSMPList(ZxSkResMoveMonthSMP record);

    int batchDeleteUpdateZxSkResMoveMonthSMP(List<ZxSkResMoveMonthSMP> recordList, ZxSkResMoveMonthSMP record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSkResMoveMonthSMP> selectZxSkResMoveMonthSMP(ZxSkResMoveMonthSMP record);
}
