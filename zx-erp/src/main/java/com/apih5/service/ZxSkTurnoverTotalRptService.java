package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverTotalRpt;

public interface ZxSkTurnoverTotalRptService {

    public ResponseEntity getZxSkTurnoverTotalRptListByCondition(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt);

    public ResponseEntity getZxSkTurnoverTotalRptDetail(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt);

    public ResponseEntity saveZxSkTurnoverTotalRpt(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt);

    public ResponseEntity updateZxSkTurnoverTotalRpt(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt);

    public ResponseEntity batchDeleteUpdateZxSkTurnoverTotalRpt(List<ZxSkTurnoverTotalRpt> zxSkTurnoverTotalRptList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkTurnoverTotalRpt> ureportZxSkTurnoverTotalRpt(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt);
    
    public ResponseEntity ureportZxSkTurnoverTotalRptIdle(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt);
}
