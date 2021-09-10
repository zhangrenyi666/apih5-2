package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzComplianceDetail;

public interface ZjTzComplianceDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzComplianceDetail record);

    int insertSelective(ZjTzComplianceDetail record);

    ZjTzComplianceDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzComplianceDetail record);

    int updateByPrimaryKey(ZjTzComplianceDetail record);

    List<ZjTzComplianceDetail> selectByZjTzComplianceDetailList(ZjTzComplianceDetail record);

    int batchDeleteUpdateZjTzComplianceDetail(List<ZjTzComplianceDetail> recordList, ZjTzComplianceDetail record);

	List<ZjTzComplianceDetail> uReportZjTzComplianceDetailList(ZjTzComplianceDetail zjTzComplianceDetail);

}

