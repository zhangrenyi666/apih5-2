package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqIecrCustomerNew;

public interface ZxEqIecrCustomerNewService {

    public ResponseEntity getZxEqIecrCustomerNewListByCondition(ZxEqIecrCustomerNew zxEqIecrCustomerNew);

    public ResponseEntity getZxEqIecrCustomerNewDetails(ZxEqIecrCustomerNew zxEqIecrCustomerNew);

    public ResponseEntity saveZxEqIecrCustomerNew(ZxEqIecrCustomerNew zxEqIecrCustomerNew);

    public ResponseEntity updateZxEqIecrCustomerNew(ZxEqIecrCustomerNew zxEqIecrCustomerNew);

    public ResponseEntity batchDeleteUpdateZxEqIecrCustomerNew(List<ZxEqIecrCustomerNew> zxEqIecrCustomerNewList);

}

