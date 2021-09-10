package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRiskAnalysis;

public interface ZjTzRiskAnalysisService {

    public ResponseEntity getZjTzRiskAnalysisListByCondition(ZjTzRiskAnalysis zjTzRiskAnalysis);

    public ResponseEntity getZjTzRiskAnalysisDetails(ZjTzRiskAnalysis zjTzRiskAnalysis);

    public ResponseEntity saveZjTzRiskAnalysis(ZjTzRiskAnalysis zjTzRiskAnalysis);

    public ResponseEntity updateZjTzRiskAnalysis(ZjTzRiskAnalysis zjTzRiskAnalysis);

    public ResponseEntity batchDeleteUpdateZjTzRiskAnalysis(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList);

	public ResponseEntity batchReleaseZjTzRiskAnalysis(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList);

	public ResponseEntity batchRecallZjTzRiskAnalysis(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList);

	public ResponseEntity batchExportZjTzRiskAnalysisFile(List<ZjTzRiskAnalysis> zjTzRiskAnalysisList);

}

