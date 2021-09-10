package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZxGcsgCcCoAlterWork;

public interface ZxGcsgCcCoAlterWorkMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCcCoAlterWork record);

	int insertSelective(ZxGcsgCcCoAlterWork record);

	ZxGcsgCcCoAlterWork selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCcCoAlterWork record);

	int updateByPrimaryKey(ZxGcsgCcCoAlterWork record);

	List<ZxGcsgCcCoAlterWork> selectByZxGcsgCcCoAlterWorkList(ZxGcsgCcCoAlterWork record);

	int batchDeleteUpdateZxGcsgCcCoAlterWork(List<ZxGcsgCcCoAlterWork> recordList, ZxGcsgCcCoAlterWork record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZxGcsgCcCoAlterWork(List<ZxGcsgCcCoAlterWork> recordList);

	int deleteZxGcsgCcCoAlterWorkByCondition(ZxGcsgCcCoAlterWork record);

	List<ZxGcsgCcCoAlterWork> getZxGcsgCcCoAlterWorkByCondition(ZxGcsgCcCoAlterWork record);

	List<Map<String, Object>> getZxGcsgCcCoAlterWorkListProcess(ZxGcsgCcCoAlterWork record);

	List<ZxGcsgCcCoAlterWork> getZxGcsgCcCoAlterWorkListByMainTable(ZxGcsgCcCoAlterWork record);
}
