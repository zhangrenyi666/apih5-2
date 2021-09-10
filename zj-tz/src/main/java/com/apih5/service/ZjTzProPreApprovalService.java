package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProPreApproval;

public interface ZjTzProPreApprovalService {

    public ResponseEntity getZjTzProPreApprovalListByCondition(ZjTzProPreApproval zjTzProPreApproval);

    public ResponseEntity getZjTzProPreApprovalDetails(ZjTzProPreApproval zjTzProPreApproval);

    public ResponseEntity saveZjTzProPreApproval(ZjTzProPreApproval zjTzProPreApproval);

    public ResponseEntity updateZjTzProPreApproval(ZjTzProPreApproval zjTzProPreApproval);

    public ResponseEntity batchDeleteUpdateZjTzProPreApproval(List<ZjTzProPreApproval> zjTzProPreApprovalList);

	public ResponseEntity batchReleaseZjTzProPreApproval(List<ZjTzProPreApproval> zjTzProPreApprovalList);

	public ResponseEntity batchRecallZjTzProPreApproval(List<ZjTzProPreApproval> zjTzProPreApprovalList);

	public ResponseEntity batchExportZjTzProPreApprovalFile(List<ZjTzProPreApproval> zjTzProPreApprovalList);

}

