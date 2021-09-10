package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPolicyLocal;

public interface ZjTzPolicyLocalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPolicyLocal record);

    int insertSelective(ZjTzPolicyLocal record);

    ZjTzPolicyLocal selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPolicyLocal record);

    int updateByPrimaryKey(ZjTzPolicyLocal record);

    List<ZjTzPolicyLocal> selectByZjTzPolicyLocalList(ZjTzPolicyLocal record);

    int batchDeleteUpdateZjTzPolicyLocal(List<ZjTzPolicyLocal> recordList, ZjTzPolicyLocal record);

	int batchDeleteReleaseZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList, ZjTzPolicyLocal zjTzRules);

	int batchDeleteRecallZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList, ZjTzPolicyLocal zjTzRules);

	int batchReportZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList, ZjTzPolicyLocal zjTzRules);
	
	List<ZjTzPolicyLocal> selectZjTzHomeMacroPolicyLocal(ZjTzPolicyLocal record);

	int updateZjTzPolicyLocalProjectShortName(ZjTzPolicyLocal local);
}

