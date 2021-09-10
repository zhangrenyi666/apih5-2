package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverStatTotalRpt;

public interface ZxSkTurnoverStatTotalRptService {

    public ResponseEntity getZxSkTurnoverStatTotalRptListByCondition(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt);

    public ResponseEntity getZxSkTurnoverStatTotalRptDetail(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt);

    public ResponseEntity saveZxSkTurnoverStatTotalRpt(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt);

    public ResponseEntity updateZxSkTurnoverStatTotalRpt(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt);

    public ResponseEntity batchDeleteUpdateZxSkTurnoverStatTotalRpt(List<ZxSkTurnoverStatTotalRpt> zxSkTurnoverStatTotalRptList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkTurnoverStatTotalRpt> ureportZxSkTurnoverStatTotalRpt(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt);
    
    public ResponseEntity ureportZxSkTurnoverStatTotalRptIdle(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt);
}
