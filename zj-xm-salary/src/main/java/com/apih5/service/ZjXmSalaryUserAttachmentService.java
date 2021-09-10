package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;

public interface ZjXmSalaryUserAttachmentService {

    public ResponseEntity getZjXmSalaryUserAttachmentListByCondition(ZjXmSalaryUserAttachment zjXmSalaryUserAttachment);

    public ResponseEntity getZjXmSalaryUserAttachmentDetails(ZjXmSalaryUserAttachment zjXmSalaryUserAttachment);

    public ResponseEntity saveZjXmSalaryUserAttachment(ZjXmSalaryUserAttachment zjXmSalaryUserAttachment);

    public ResponseEntity updateZjXmSalaryUserAttachment(ZjXmSalaryUserAttachment zjXmSalaryUserAttachment);

    public ResponseEntity batchDeleteUpdateZjXmSalaryUserAttachment(List<ZjXmSalaryUserAttachment> zjXmSalaryUserAttachmentList);

}

