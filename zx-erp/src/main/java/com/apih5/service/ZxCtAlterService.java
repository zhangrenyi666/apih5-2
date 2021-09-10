package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtAlter;

public interface ZxCtAlterService {

    public ResponseEntity getZxCtAlterListByCondition(ZxCtAlter zxCtAlter);

    public ResponseEntity getZxCtAlterDetail(ZxCtAlter zxCtAlter);

    public ResponseEntity saveZxCtAlter(ZxCtAlter zxCtAlter);

    public ResponseEntity updateZxCtAlter(ZxCtAlter zxCtAlter);

    public ResponseEntity batchDeleteUpdateZxCtAlter(List<ZxCtAlter> zxCtAlterList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity zxCtAlterAudit(ZxCtAlter zxCtAlter);

	public ResponseEntity zxCtAlterReporting(ZxCtAlter zxCtAlter);
}
