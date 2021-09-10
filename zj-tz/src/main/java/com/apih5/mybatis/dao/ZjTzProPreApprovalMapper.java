package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProPreApproval;

public interface ZjTzProPreApprovalMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProPreApproval record);

    int insertSelective(ZjTzProPreApproval record);

    ZjTzProPreApproval selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProPreApproval record);

    int updateByPrimaryKey(ZjTzProPreApproval record);

    List<ZjTzProPreApproval> selectByZjTzProPreApprovalList(ZjTzProPreApproval record);

    int batchDeleteUpdateZjTzProPreApproval(List<ZjTzProPreApproval> recordList, ZjTzProPreApproval record);

	int batchReleaseZjTzProPreApproval(List<ZjTzProPreApproval> zjTzProPreApprovalList, ZjTzProPreApproval zjTzRules);

	int batchRecallZjTzProPreApproval(List<ZjTzProPreApproval> zjTzProPreApprovalList, ZjTzProPreApproval zjTzRules);

}

