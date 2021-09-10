package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxDisciplineAssessmentDetailed;

public interface ZjXmCqjxDisciplineAssessmentDetailedService {

    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetailedListByCondition(ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed);

    public ResponseEntity getZjXmCqjxDisciplineAssessmentDetailedDetails(ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed);

    public ResponseEntity saveZjXmCqjxDisciplineAssessmentDetailed(ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed);

    public ResponseEntity updateZjXmCqjxDisciplineAssessmentDetailed(ZjXmCqjxDisciplineAssessmentDetailed zjXmCqjxDisciplineAssessmentDetailed);

    public ResponseEntity batchDeleteUpdateZjXmCqjxDisciplineAssessmentDetailed(List<ZjXmCqjxDisciplineAssessmentDetailed> zjXmCqjxDisciplineAssessmentDetailedList);
    
    public ResponseEntity batchUpdateZjXmCqjxDisciplineAssessmentDetailed(List<ZjXmCqjxDisciplineAssessmentDetailed> zjXmCqjxDisciplineAssessmentDetailedList);

}
