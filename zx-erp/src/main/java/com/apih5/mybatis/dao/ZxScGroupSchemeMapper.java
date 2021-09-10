package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxScGroupScheme;

public interface ZxScGroupSchemeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxScGroupScheme record);

    int insertSelective(ZxScGroupScheme record);

    ZxScGroupScheme selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxScGroupScheme record);

    int updateByPrimaryKey(ZxScGroupScheme record);

    List<ZxScGroupScheme> selectByZxScGroupSchemeList(ZxScGroupScheme record);

    int batchDeleteUpdateZxScGroupScheme(List<ZxScGroupScheme> recordList, ZxScGroupScheme record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    String getLastIssueSchemeNoByProjectId(String projectId);
}
