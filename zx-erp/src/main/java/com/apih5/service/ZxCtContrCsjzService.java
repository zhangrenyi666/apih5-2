package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrCsjz;

public interface ZxCtContrCsjzService {

    public ResponseEntity getZxCtContrCsjzListByCondition(ZxCtContrCsjz zxCtContrCsjz);

    public ResponseEntity getZxCtContrCsjzDetail(ZxCtContrCsjz zxCtContrCsjz);

    public ResponseEntity saveZxCtContrCsjz(ZxCtContrCsjz zxCtContrCsjz);

    public ResponseEntity updateZxCtContrCsjz(ZxCtContrCsjz zxCtContrCsjz);

    public ResponseEntity batchDeleteUpdateZxCtContrCsjz(List<ZxCtContrCsjz> zxCtContrCsjzList);

	public ResponseEntity getZxCtContrCsjzItemList(ZxCtContrCsjz zxCtContrCsjz);

	public ResponseEntity getZxCtContrCsjzListApih5FlowStatus2ForDq(ZxCtContrCsjz zxCtContrCsjz);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
