package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfOtherAddFileReport;

public interface ZxSfOtherAddFileReportMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfOtherAddFileReport record);

    int insertSelective(ZxSfOtherAddFileReport record);

    ZxSfOtherAddFileReport selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfOtherAddFileReport record);

    int updateByPrimaryKey(ZxSfOtherAddFileReport record);

    List<ZxSfOtherAddFileReport> selectByZxSfOtherAddFileReportList(ZxSfOtherAddFileReport record);

    int batchDeleteUpdateZxSfOtherAddFileReport(List<ZxSfOtherAddFileReport> recordList, ZxSfOtherAddFileReport record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
