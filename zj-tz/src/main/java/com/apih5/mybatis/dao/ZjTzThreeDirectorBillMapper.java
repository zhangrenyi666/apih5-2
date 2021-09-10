package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThreeDirectorBill;

public interface ZjTzThreeDirectorBillMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThreeDirectorBill record);

    int insertSelective(ZjTzThreeDirectorBill record);

    ZjTzThreeDirectorBill selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThreeDirectorBill record);

    int updateByPrimaryKey(ZjTzThreeDirectorBill record);

    List<ZjTzThreeDirectorBill> selectByZjTzThreeDirectorBillList(ZjTzThreeDirectorBill record);

    int batchDeleteUpdateZjTzThreeDirectorBill(List<ZjTzThreeDirectorBill> recordList, ZjTzThreeDirectorBill record);

	List<ZjTzThreeDirectorBill> ureportZjTzThreeDirectorBillList(ZjTzThreeDirectorBill zjTzThreeDirectorBill);

}

