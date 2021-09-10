package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtContract;

public interface ZxCtContractMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtContract record);

    int insertSelective(ZxCtContract record);

    ZxCtContract selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtContract record);

    int updateByPrimaryKey(ZxCtContract record);

    List<ZxCtContract> selectByZxCtContractList(ZxCtContract record);

    int batchDeleteUpdateZxCtContract(List<ZxCtContract> recordList, ZxCtContract record);

	int updateContractCostByOrgID(ZxCtContract updateZxCtContract);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
	List<ZxCtContract> getContractAuditStatus(List<ZxCtContract> zxCtContractList);

	List<ZxCtContract> getZxCtContractListByStatus(ZxCtContract zxCtContract);

	int syncZxCtContractIsSettle(ZxCtContract zxCtContract);
}
