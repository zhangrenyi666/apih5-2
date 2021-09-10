package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThreeShareholderBill;

public interface ZjTzThreeShareholderBillMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThreeShareholderBill record);

    int insertSelective(ZjTzThreeShareholderBill record);

    ZjTzThreeShareholderBill selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThreeShareholderBill record);

    int updateByPrimaryKey(ZjTzThreeShareholderBill record);

    List<ZjTzThreeShareholderBill> selectByZjTzThreeShareholderBillList(ZjTzThreeShareholderBill record);

    int batchDeleteUpdateZjTzThreeShareholderBill(List<ZjTzThreeShareholderBill> recordList, ZjTzThreeShareholderBill record);

	List<ZjTzThreeShareholderBill> ureportZjTzThreeBillListFinish(ZjTzThreeShareholderBill zjTzThreeShareholderBill);

	List<ZjTzThreeShareholderBill> ureportZjTzThreeShareholderBillList(
			ZjTzThreeShareholderBill zjTzThreeShareholderBill);

}

