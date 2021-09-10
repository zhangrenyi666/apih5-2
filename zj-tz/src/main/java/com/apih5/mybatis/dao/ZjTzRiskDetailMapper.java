package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzRiskDetail;

public interface ZjTzRiskDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzRiskDetail record);

    int insertSelective(ZjTzRiskDetail record);

    ZjTzRiskDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzRiskDetail record);

    int updateByPrimaryKey(ZjTzRiskDetail record);

    List<ZjTzRiskDetail> selectByZjTzRiskDetailList(ZjTzRiskDetail record);

    int batchDeleteUpdateZjTzRiskDetail(List<ZjTzRiskDetail> recordList, ZjTzRiskDetail record);

	int batchLockUpdateZjTzRiskDetail(List<ZjTzRiskDetail> zjTzRiskDetailList, ZjTzRiskDetail zjTzRiskDetail);

	int batchClearUpdateZjTzRiskDetail(List<ZjTzRiskDetail> zjTzRiskDetailList, ZjTzRiskDetail zjTzRiskDetail);

}

