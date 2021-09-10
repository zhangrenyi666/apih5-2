package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzAnnualAssess;

public interface ZjTzAnnualAssessService {

    public ResponseEntity getZjTzAnnualAssessListByCondition(ZjTzAnnualAssess zjTzAnnualAssess);

    public ResponseEntity getZjTzAnnualAssessDetails(ZjTzAnnualAssess zjTzAnnualAssess);

    public ResponseEntity saveZjTzAnnualAssess(ZjTzAnnualAssess zjTzAnnualAssess);

    public ResponseEntity updateZjTzAnnualAssess(ZjTzAnnualAssess zjTzAnnualAssess);

    public ResponseEntity batchDeleteUpdateZjTzAnnualAssess(List<ZjTzAnnualAssess> zjTzAnnualAssessList);

	public ResponseEntity batchReleaseZjTzAnnualAssess(List<ZjTzAnnualAssess> zjTzAnnualAssessList);

	public ResponseEntity batchRecallZjTzAnnualAssess(List<ZjTzAnnualAssess> zjTzAnnualAssessList);

	public ResponseEntity batchExportZjTzAnnualAssessFile(List<ZjTzAnnualAssess> zjTzAnnualAssessList);

}

