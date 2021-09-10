package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgSaCoWorkLinkWork;

public interface ZxGcsgSaCoWorkLinkWorkMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgSaCoWorkLinkWork record);

	int insertSelective(ZxGcsgSaCoWorkLinkWork record);

	ZxGcsgSaCoWorkLinkWork selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgSaCoWorkLinkWork record);

	int updateByPrimaryKey(ZxGcsgSaCoWorkLinkWork record);

	List<ZxGcsgSaCoWorkLinkWork> selectByZxGcsgSaCoWorkLinkWorkList(ZxGcsgSaCoWorkLinkWork record);

	int batchDeleteUpdateZxGcsgSaCoWorkLinkWork(List<ZxGcsgSaCoWorkLinkWork> recordList, ZxGcsgSaCoWorkLinkWork record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	List<ZxGcsgSaCoWorkLinkWork> getZxGcsgSaCoWorkLinkWorkLeftTree(ZxGcsgSaCoWorkLinkWork record);

	int deleteZxGcsgSaCoWorkLinkWorkByCondition(ZxGcsgSaCoWorkLinkWork record);

}
