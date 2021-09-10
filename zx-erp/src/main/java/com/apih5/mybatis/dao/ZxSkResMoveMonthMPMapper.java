package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSkResMoveMonthMP;

public interface ZxSkResMoveMonthMPMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSkResMoveMonthMP record);

    int insertSelective(ZxSkResMoveMonthMP record);

    ZxSkResMoveMonthMP selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSkResMoveMonthMP record);

    int updateByPrimaryKey(ZxSkResMoveMonthMP record);

    List<ZxSkResMoveMonthMP> selectByZxSkResMoveMonthMPList(ZxSkResMoveMonthMP record);

    int batchDeleteUpdateZxSkResMoveMonthMP(List<ZxSkResMoveMonthMP> recordList, ZxSkResMoveMonthMP record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
