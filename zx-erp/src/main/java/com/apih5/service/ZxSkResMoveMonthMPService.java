package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResMoveMonthMP;

public interface ZxSkResMoveMonthMPService {

    public ResponseEntity getZxSkResMoveMonthMPListByCondition(ZxSkResMoveMonthMP zxSkResMoveMonthMP);

    public ResponseEntity getZxSkResMoveMonthMPDetail(ZxSkResMoveMonthMP zxSkResMoveMonthMP);

    public ResponseEntity saveZxSkResMoveMonthMP(ZxSkResMoveMonthMP zxSkResMoveMonthMP);

    public ResponseEntity updateZxSkResMoveMonthMP(ZxSkResMoveMonthMP zxSkResMoveMonthMP);

    public ResponseEntity batchDeleteUpdateZxSkResMoveMonthMP(List<ZxSkResMoveMonthMP> zxSkResMoveMonthMPList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
