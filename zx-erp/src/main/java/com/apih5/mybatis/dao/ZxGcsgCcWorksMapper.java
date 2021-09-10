package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZxGcsgCcWorks;

public interface ZxGcsgCcWorksMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCcWorks record);

	int insertSelective(ZxGcsgCcWorks record);

	ZxGcsgCcWorks selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCcWorks record);

	int updateByPrimaryKey(ZxGcsgCcWorks record);

	List<ZxGcsgCcWorks> selectByZxGcsgCcWorksList(ZxGcsgCcWorks record);

	int batchDeleteUpdateZxGcsgCcWorks(List<ZxGcsgCcWorks> recordList, ZxGcsgCcWorks record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZxGcsgCcWorks(List<ZxGcsgCcWorks> recordList);

	int batchUpdateZxGcsgCcWorks(List<ZxGcsgCcWorks> recordList);

	ZxGcsgCcWorks getZxGcsgCcWorksMAXTreeNode(ZxGcsgCcWorks record);

	int deleteZxGcsgCcWorksByCondition(ZxGcsgCcWorks record);

	int updateZxGcsgCcWorksByLeaf(ZxGcsgCcWorks record);

	int updateZxGcsgCcWorksByNotLeaf(ZxGcsgCcWorks record);

	ZxGcsgCcWorks getZxGcsgCcWorksContractCost(ZxGcsgCcWorks record);

	List<ZxGcsgCcWorks> getZxGcsgCcWorksListByParentID(ZxGcsgCcWorks record);

	ZxGcsgCcWorks getZxGcsgCcWorksContractAmt(ZxGcsgCcWorks record);

	List<Map<String, Object>> getZxGcsgCcWorksListProcess(ZxGcsgCcWorks record);

	ZxGcsgCcWorks getZxGcsgCcWorksByCondition(ZxGcsgCcWorks record);

	ZxGcsgCcWorks getZxGcsgCcWorksMAXTreeNodeByCondition(ZxGcsgCcWorks record);
}
