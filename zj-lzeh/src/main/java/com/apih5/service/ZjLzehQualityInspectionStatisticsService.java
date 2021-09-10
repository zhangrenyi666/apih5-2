package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehQualityInspectionStatistics;

public interface ZjLzehQualityInspectionStatisticsService {

    public ResponseEntity getZjLzehQualityInspectionStatisticsListByCondition(ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics);

    public ResponseEntity getZjLzehQualityInspectionStatisticsDetails(ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics);

    public ResponseEntity saveZjLzehQualityInspectionStatistics(ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics);

    public ResponseEntity updateZjLzehQualityInspectionStatistics(ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics);

    public ResponseEntity batchDeleteUpdateZjLzehQualityInspectionStatistics(List<ZjLzehQualityInspectionStatistics> zjLzehQualityInspectionStatisticsList);

}

