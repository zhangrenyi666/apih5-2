package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApply;

public interface ZxGcsgCtContrApplyMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCtContrApply record);

	int insertSelective(ZxGcsgCtContrApply record);

	ZxGcsgCtContrApply selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCtContrApply record);

	int updateByPrimaryKey(ZxGcsgCtContrApply record);

	List<ZxGcsgCtContrApply> selectByZxGcsgCtContrApplyList(ZxGcsgCtContrApply record);

	int batchDeleteUpdateZxGcsgCtContrApply(List<ZxGcsgCtContrApply> recordList, ZxGcsgCtContrApply record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	List<ZxGcsgCtContrApply> psGetZxGcsgCtContrApplyList(ZxGcsgCtContrApply record);

	List<ZxGcsgCtContrApply> bxGetZxGcsgCtContrApplyList(ZxGcsgCtContrApply record);

	ZxGcsgCtContrApply psGetZxGcsgCtContrApplyContractNo(ZxGcsgCtContrApply record);

	ZxGcsgCtContrApply bxGetZxGcsgCtContrApplyContractNo(ZxGcsgCtContrApply record);

	ZxGcsgCtContrApply getZxGcsgCtContrApplyByWorkId(String key);

	int checkZxGcsgCtContrApplyRepetitiveNo(ZxGcsgCtContrApply record);

	int bxCheckZxGcsgCtContrApplyPreCondition(ZxGcsgCtContrApply record);

}
