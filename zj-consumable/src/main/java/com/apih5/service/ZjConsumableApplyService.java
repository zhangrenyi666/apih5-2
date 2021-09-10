package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjConsumableApply;

public interface ZjConsumableApplyService {

    public ResponseEntity getZjConsumableApplyListByCondition(ZjConsumableApply zjConsumableApply);

    public ResponseEntity getZjConsumableApplyDetails(ZjConsumableApply zjConsumableApply);

    public ResponseEntity saveZjConsumableApply(ZjConsumableApply zjConsumableApply);

    public ResponseEntity updateZjConsumableApply(ZjConsumableApply zjConsumableApply);

    public ResponseEntity batchDeleteUpdateZjConsumableApply(List<ZjConsumableApply> zjConsumableApplyList);

	public ResponseEntity approveZjConsumableApply(ZjConsumableApply zjConsumableApply);

	public ResponseEntity useZjConsumableApply(ZjConsumableApply zjConsumableApply);

}

