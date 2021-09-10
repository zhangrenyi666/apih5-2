package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPppTermBase;

public interface ZjTzPppTermBaseService {

    public ResponseEntity getZjTzPppTermBaseListByCondition(ZjTzPppTermBase zjTzPppTermBase);

    public ResponseEntity getZjTzPppTermBaseDetails(ZjTzPppTermBase zjTzPppTermBase);

    public ResponseEntity saveZjTzPppTermBase(ZjTzPppTermBase zjTzPppTermBase);

    public ResponseEntity updateZjTzPppTermBase(ZjTzPppTermBase zjTzPppTermBase);

    public ResponseEntity batchDeleteUpdateZjTzPppTermBase(List<ZjTzPppTermBase> zjTzPppTermBaseList);

	public ResponseEntity batchImportZjTzPppTermBase(ZjTzPppTermBase zjTzPppTermBase);

}

