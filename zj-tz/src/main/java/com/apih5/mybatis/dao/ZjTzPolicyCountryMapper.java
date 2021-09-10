package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzPolicyCountry;

public interface ZjTzPolicyCountryMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzPolicyCountry record);

    int insertSelective(ZjTzPolicyCountry record);

    ZjTzPolicyCountry selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzPolicyCountry record);

    int updateByPrimaryKey(ZjTzPolicyCountry record);

    List<ZjTzPolicyCountry> selectByZjTzPolicyCountryList(ZjTzPolicyCountry record);

    int batchDeleteUpdateZjTzPolicyCountry(List<ZjTzPolicyCountry> recordList, ZjTzPolicyCountry record);

	int batchDeleteReleaseZjTzPolicyCountry(List<ZjTzPolicyCountry> zjTzPolicyCountryList, ZjTzPolicyCountry zjTzRules);

	int batchDeleteRecallZjTzPolicyCountry(List<ZjTzPolicyCountry> zjTzPolicyCountryList, ZjTzPolicyCountry zjTzRules);
	
	List<ZjTzPolicyCountry> selectZjTzHomeMacroPolicyCountry(ZjTzPolicyCountry record);
}

