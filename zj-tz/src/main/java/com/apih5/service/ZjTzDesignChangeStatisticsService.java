package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzDesignChangeStatistics;

public interface ZjTzDesignChangeStatisticsService {

    public ResponseEntity getZjTzDesignChangeStatisticsListByCondition(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics);

    public ResponseEntity getZjTzDesignChangeStatisticsDetails(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics);

    public ResponseEntity saveZjTzDesignChangeStatistics(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics);

    public ResponseEntity updateZjTzDesignChangeStatistics(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics);

    public ResponseEntity batchDeleteUpdateZjTzDesignChangeStatistics(List<ZjTzDesignChangeStatistics> zjTzDesignChangeStatisticsList);

	public List<ZjTzDesignChangeStatistics> reportZjTzDesignChangeStatisticsList(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics);

}

