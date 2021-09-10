package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContractContrastRpt;

public interface ZxCtContractContrastRptService {

    public ResponseEntity getZxCtContractContrastRptListByCondition(ZxCtContractContrastRpt zxCtContractContrastRpt);

    public ResponseEntity getZxCtContractContrastRptDetail(ZxCtContractContrastRpt zxCtContractContrastRpt);

    public ResponseEntity saveZxCtContractContrastRpt(ZxCtContractContrastRpt zxCtContractContrastRpt);

    public ResponseEntity updateZxCtContractContrastRpt(ZxCtContractContrastRpt zxCtContractContrastRpt);

    public ResponseEntity batchDeleteUpdateZxCtContractContrastRpt(List<ZxCtContractContrastRpt> zxCtContractContrastRptList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxCtContractContrastRpt> ureportZxCtContractContrastRpt(ZxCtContractContrastRpt zxCtContractContrastRpt);
    
    public ResponseEntity ureportZxCtContractContrastRptIdle(ZxCtContractContrastRpt zxCtContractContrastRpt);
}
