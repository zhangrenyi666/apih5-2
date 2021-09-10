package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehSafetyInspectionStatistics;

public interface ZjLzehSafetyInspectionStatisticsService {

    public ResponseEntity getZjLzehSafetyInspectionStatisticsListByCondition(ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics);

    public ResponseEntity getZjLzehSafetyInspectionStatisticsDetails(ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics);

    public ResponseEntity saveZjLzehSafetyInspectionStatistics(ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics);

    public ResponseEntity updateZjLzehSafetyInspectionStatistics(ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics);

    public ResponseEntity batchDeleteUpdateZjLzehSafetyInspectionStatistics(List<ZjLzehSafetyInspectionStatistics> zjLzehSafetyInspectionStatisticsList);

}

