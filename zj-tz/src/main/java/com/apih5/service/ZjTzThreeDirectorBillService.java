package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThreeDirectorBill;

public interface ZjTzThreeDirectorBillService {

    public ResponseEntity getZjTzThreeDirectorBillListByCondition(ZjTzThreeDirectorBill zjTzThreeDirectorBill);

    public ResponseEntity getZjTzThreeDirectorBillDetails(ZjTzThreeDirectorBill zjTzThreeDirectorBill);

    public ResponseEntity saveZjTzThreeDirectorBill(ZjTzThreeDirectorBill zjTzThreeDirectorBill);

    public ResponseEntity updateZjTzThreeDirectorBill(ZjTzThreeDirectorBill zjTzThreeDirectorBill);

    public ResponseEntity batchDeleteUpdateZjTzThreeDirectorBill(List<ZjTzThreeDirectorBill> zjTzThreeDirectorBillList);

}

