package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApplyWorks;

public interface ZxGcsgCtContrApplyWorksService {

	public ResponseEntity getZxGcsgCtContrApplyWorksListByCondition(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity getZxGcsgCtContrApplyWorksDetail(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity saveZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity updateZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity batchDeleteUpdateZxGcsgCtContrApplyWorks(
			List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity importZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity getZxGcsgCtContrApplyWorksTree(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity getZxGcsgCtContrApplyWorksListAmount(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity getZxGcsgCtContrApplyWorksEditSubList(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity getZxGcsgCtContrApplyWorksEditAllList(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity batchEditSubListZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity batchEditAllListZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity autoHookZxGcsgCtContrApplyWorksProcess(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity manualHookZxGcsgCtContrApplyWorksProcess(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity manualHookZxGcsgCtContrApplyWorksRule(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);

	public ResponseEntity getZxGcsgCtContrApplyWorksListProcess(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks);
}
