package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThreeSupervisorBill;

public interface ZjTzThreeSupervisorBillMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThreeSupervisorBill record);

    int insertSelective(ZjTzThreeSupervisorBill record);

    ZjTzThreeSupervisorBill selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThreeSupervisorBill record);

    int updateByPrimaryKey(ZjTzThreeSupervisorBill record);

    List<ZjTzThreeSupervisorBill> selectByZjTzThreeSupervisorBillList(ZjTzThreeSupervisorBill record);

    int batchDeleteUpdateZjTzThreeSupervisorBill(List<ZjTzThreeSupervisorBill> recordList, ZjTzThreeSupervisorBill record);

	List<ZjTzThreeSupervisorBill> ureportZjTzThreeSupervisorBillList(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill);

}

