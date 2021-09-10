package com.apih5.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.apih5.mybatis.pojo.ZxGcsgCtContrApplyWorks;

public interface ZxGcsgCtContrApplyWorksMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCtContrApplyWorks record);

	int insertSelective(ZxGcsgCtContrApplyWorks record);

	ZxGcsgCtContrApplyWorks selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCtContrApplyWorks record);

	int updateByPrimaryKey(ZxGcsgCtContrApplyWorks record);

	List<ZxGcsgCtContrApplyWorks> selectByZxGcsgCtContrApplyWorksList(ZxGcsgCtContrApplyWorks record);

	int batchDeleteUpdateZxGcsgCtContrApplyWorks(List<ZxGcsgCtContrApplyWorks> recordList,
			ZxGcsgCtContrApplyWorks record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	ZxGcsgCtContrApplyWorks getZxGcsgCtContrApplyWorksMAXTreeNode(ZxGcsgCtContrApplyWorks record);

	int deleteZxGcsgCtContrApplyWorksByCondition(ZxGcsgCtContrApplyWorks record);

	int deleteZxGcsgCtContrApplyWorksByParentID(ZxGcsgCtContrApplyWorks record);

	int batchInsertZxGcsgCtContrApplyWorks(List<ZxGcsgCtContrApplyWorks> recordList);

	int updateZxGcsgCtContrApplyWorksByLeaf(ZxGcsgCtContrApplyWorks record);

	int updateZxGcsgCtContrApplyWorksByNotLeaf(ZxGcsgCtContrApplyWorks record);

	ZxGcsgCtContrApplyWorks getZxGcsgCtContrApplyWorksContractCost(ZxGcsgCtContrApplyWorks record);

	ZxGcsgCtContrApplyWorks getZxGcsgCtContrApplyWorksContractAmt(ZxGcsgCtContrApplyWorks record);

	int batchUpdateZxGcsgCtContrApplyWorks(List<ZxGcsgCtContrApplyWorks> recordList);

	int batchDeleteZxGcsgCtContrApplyWorks(List<ZxGcsgCtContrApplyWorks> recordList);

	List<ZxGcsgCtContrApplyWorks> getZxGcsgCtContrApplyWorksListByParentID(ZxGcsgCtContrApplyWorks record);

	List<ZxGcsgCtContrApplyWorks> getZxGcsgCtContrApplyWorksListByCondition(ZxGcsgCtContrApplyWorks record);

	List<Map<String, Object>> getZxGcsgCtContrApplyWorksListProcess(ZxGcsgCtContrApplyWorks record);

	ZxGcsgCtContrApplyWorks getZxGcsgCtContrApplyWorksByCondition(ZxGcsgCtContrApplyWorks record);
}