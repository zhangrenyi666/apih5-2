package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyIndexLibrary;

public interface ZjXmJxQuarterlyIndexLibraryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjXmJxQuarterlyIndexLibrary record);

    int insertSelective(ZjXmJxQuarterlyIndexLibrary record);

    ZjXmJxQuarterlyIndexLibrary selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjXmJxQuarterlyIndexLibrary record);

    int updateByPrimaryKey(ZjXmJxQuarterlyIndexLibrary record);

    List<ZjXmJxQuarterlyIndexLibrary> selectByZjXmJxQuarterlyIndexLibraryList(ZjXmJxQuarterlyIndexLibrary record);

    int batchDeleteUpdateZjXmJxQuarterlyIndexLibrary(List<ZjXmJxQuarterlyIndexLibrary> recordList, ZjXmJxQuarterlyIndexLibrary record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
