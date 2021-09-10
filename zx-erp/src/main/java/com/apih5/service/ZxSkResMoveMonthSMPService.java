package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResMoveMonthSMP;

public interface ZxSkResMoveMonthSMPService {

    public ResponseEntity getZxSkResMoveMonthSMPListByCondition(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP);

    public ResponseEntity getZxSkResMoveMonthSMPDetail(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP);

    public ResponseEntity saveZxSkResMoveMonthSMP(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP);

    public ResponseEntity updateZxSkResMoveMonthSMP(ZxSkResMoveMonthSMP zxSkResMoveMonthSMP);

    public ResponseEntity batchDeleteUpdateZxSkResMoveMonthSMP(List<ZxSkResMoveMonthSMP> zxSkResMoveMonthSMPList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkResMoveMonthSMP> ureportZxSkResMoveMonthSMP (ZxSkResMoveMonthSMP zxSkResMoveMonthSMP);
    
    public ResponseEntity ureportZxSkResMoveMonthSMPIdle (ZxSkResMoveMonthSMP zxSkResMoveMonthSMP);
}
