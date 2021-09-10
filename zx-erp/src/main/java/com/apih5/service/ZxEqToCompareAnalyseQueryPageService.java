package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqToCompareAnalyseQueryPage;

public interface ZxEqToCompareAnalyseQueryPageService {

    public ResponseEntity getZxEqToCompareAnalyseQueryPageListByCondition(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage);

    public ResponseEntity getZxEqToCompareAnalyseQueryPageDetail(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage);

    public ResponseEntity saveZxEqToCompareAnalyseQueryPage(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage);

    public ResponseEntity updateZxEqToCompareAnalyseQueryPage(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage);

    public ResponseEntity batchDeleteUpdateZxEqToCompareAnalyseQueryPage(List<ZxEqToCompareAnalyseQueryPage> zxEqToCompareAnalyseQueryPageList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity ureportZxEqEquipIntegratedQueryIdle(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage);
    
    public ResponseEntity ureportZxEqEquipIntegratedQueryPeriodIdle(ZxEqToCompareAnalyseQueryPage zxEqToCompareAnalyseQueryPage);
}
