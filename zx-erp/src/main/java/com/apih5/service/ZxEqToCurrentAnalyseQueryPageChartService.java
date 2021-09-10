package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqToCurrentAnalyseQueryPageChart;

public interface ZxEqToCurrentAnalyseQueryPageChartService {

    public ResponseEntity getZxEqToCurrentAnalyseQueryPageChartListByCondition(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart);

    public ResponseEntity getZxEqToCurrentAnalyseQueryPageChartDetail(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart);

    public ResponseEntity saveZxEqToCurrentAnalyseQueryPageChart(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart);

    public ResponseEntity updateZxEqToCurrentAnalyseQueryPageChart(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart);

    public ResponseEntity batchDeleteUpdateZxEqToCurrentAnalyseQueryPageChart(List<ZxEqToCurrentAnalyseQueryPageChart> zxEqToCurrentAnalyseQueryPageChartList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity ureportZxEqToCurrentAnalyseQueryPageChartIdle(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart);
    
    public ResponseEntity ureportZxEqToCurrentAnalyseQueryPageIdle(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart);
}
