package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqIecsCBS;

public interface ZxEqIecsCBSService {

    public ResponseEntity getZxEqIecsCBSListByCondition(ZxEqIecsCBS zxEqIecsCBS);

    public ResponseEntity getZxEqIecsCBSDetails(ZxEqIecsCBS zxEqIecsCBS);

    public ResponseEntity saveZxEqIecsCBS(ZxEqIecsCBS zxEqIecsCBS);

    public ResponseEntity updateZxEqIecsCBS(ZxEqIecsCBS zxEqIecsCBS);

    public ResponseEntity batchDeleteUpdateZxEqIecsCBS(List<ZxEqIecsCBS> zxEqIecsCBSList);

    public ResponseEntity getZxEqIecsCBSOrgId(ZxEqIecsCBS zxEqIecsCBS);

    public ResponseEntity getZxEqIecsCBSTree(ZxEqIecsCBS zxEqIecsCBS);

    public ResponseEntity getZxEqIecsCBSPickingList(ZxEqIecsCBS zxEqIecsCBS);

}

