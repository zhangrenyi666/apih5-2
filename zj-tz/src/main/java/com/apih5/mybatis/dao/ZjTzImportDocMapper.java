package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzImportDoc;

public interface ZjTzImportDocMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzImportDoc record);

    int insertSelective(ZjTzImportDoc record);

    ZjTzImportDoc selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzImportDoc record);

    int updateByPrimaryKey(ZjTzImportDoc record);

    List<ZjTzImportDoc> selectByZjTzImportDocList(ZjTzImportDoc record);

    int batchDeleteUpdateZjTzImportDoc(List<ZjTzImportDoc> recordList, ZjTzImportDoc record);

	int batchDeleteReleaseZjTzImportDoc(List<ZjTzImportDoc> zjTzImportDocList, ZjTzImportDoc zjTzRules);

	int batchDeleteRecallZjTzImportDoc(List<ZjTzImportDoc> zjTzImportDocList, ZjTzImportDoc zjTzRules);
	
	List<ZjTzImportDoc> selectZjTzHomeImportDoc(ZjTzImportDoc record);

}

