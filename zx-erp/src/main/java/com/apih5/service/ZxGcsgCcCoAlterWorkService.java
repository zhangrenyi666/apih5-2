package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlterWork;

public interface ZxGcsgCcCoAlterWorkService {

	public ResponseEntity getZxGcsgCcCoAlterWorkListByCondition(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork);

	public ResponseEntity getZxGcsgCcCoAlterWorkDetail(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork);

	public ResponseEntity saveZxGcsgCcCoAlterWork(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork);

	public ResponseEntity updateZxGcsgCcCoAlterWork(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork);

	public ResponseEntity batchDeleteUpdateZxGcsgCcCoAlterWork(List<ZxGcsgCcCoAlterWork> zxGcsgCcCoAlterWorkList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity getZxGcsgCcCoAlterWorkListAmount(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork);

	public ResponseEntity manualHookZxGcsgCcCoAlterWorkProcess(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork);

	public ResponseEntity manualHookZxGcsgCcCoAlterWorkRule(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork);

	public ResponseEntity getZxGcsgCcCoAlterWorkListProcess(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork);

}
