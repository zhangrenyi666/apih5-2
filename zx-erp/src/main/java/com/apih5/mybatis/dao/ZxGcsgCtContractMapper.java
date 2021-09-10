package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCtContract;

public interface ZxGcsgCtContractMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCtContract record);

	int insertSelective(ZxGcsgCtContract record);

	ZxGcsgCtContract selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCtContract record);

	int updateByPrimaryKey(ZxGcsgCtContract record);

	List<ZxGcsgCtContract> selectByZxGcsgCtContractList(ZxGcsgCtContract record);

	int batchDeleteUpdateZxGcsgCtContract(List<ZxGcsgCtContract> recordList, ZxGcsgCtContract record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	List<ZxGcsgCtContract> getZxGcsgCtContractListByIdList(List<ZxGcsgCtContract> recordList);

	int updateSettleTypeByPrimaryKey(ZxGcsgCtContract zxGcsgCtContract);

}
