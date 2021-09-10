package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThreeSupervisorBill;

public interface ZjTzThreeSupervisorBillService {

    public ResponseEntity getZjTzThreeSupervisorBillListByCondition(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill);

    public ResponseEntity getZjTzThreeSupervisorBillDetails(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill);

    public ResponseEntity saveZjTzThreeSupervisorBill(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill);

    public ResponseEntity updateZjTzThreeSupervisorBill(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill);

    public ResponseEntity batchDeleteUpdateZjTzThreeSupervisorBill(List<ZjTzThreeSupervisorBill> zjTzThreeSupervisorBillList);

}

