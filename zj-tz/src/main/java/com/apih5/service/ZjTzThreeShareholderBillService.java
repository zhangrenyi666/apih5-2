package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThreeShareholderBill;

public interface ZjTzThreeShareholderBillService {

    public ResponseEntity getZjTzThreeShareholderBillListByCondition(ZjTzThreeShareholderBill zjTzThreeShareholderBill);

    public ResponseEntity getZjTzThreeShareholderBillDetails(ZjTzThreeShareholderBill zjTzThreeShareholderBill);

    public ResponseEntity saveZjTzThreeShareholderBill(ZjTzThreeShareholderBill zjTzThreeShareholderBill);

    public ResponseEntity updateZjTzThreeShareholderBill(ZjTzThreeShareholderBill zjTzThreeShareholderBill);

    public ResponseEntity batchDeleteUpdateZjTzThreeShareholderBill(List<ZjTzThreeShareholderBill> zjTzThreeShareholderBillList);

	public List<ZjTzThreeShareholderBill> ureportZjTzThreeBillListFinish(ZjTzThreeShareholderBill zjTzThreeShareholderBill);

	public ResponseEntity getZjTzThreeShareholderBillListForReport(ZjTzThreeShareholderBill zjTzThreeShareholderBill);

}

