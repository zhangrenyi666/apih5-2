package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfExam;

public interface ZxSfExamMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfExam record);

    int insertSelective(ZxSfExam record);

    ZxSfExam selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfExam record);

    int updateByPrimaryKey(ZxSfExam record);

    List<ZxSfExam> selectByZxSfExamList(ZxSfExam record);

    int batchDeleteUpdateZxSfExam(List<ZxSfExam> recordList, ZxSfExam record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
