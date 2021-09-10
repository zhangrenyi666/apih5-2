package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgSaCoWorkLinkWork;

public interface ZxGcsgSaCoWorkLinkWorkService {

	public ResponseEntity getZxGcsgSaCoWorkLinkWorkListByCondition(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork);

	public ResponseEntity getZxGcsgSaCoWorkLinkWorkDetail(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork);

	public ResponseEntity saveZxGcsgSaCoWorkLinkWork(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork);

	public ResponseEntity updateZxGcsgSaCoWorkLinkWork(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork);

	public ResponseEntity batchDeleteUpdateZxGcsgSaCoWorkLinkWork(
			List<ZxGcsgSaCoWorkLinkWork> zxGcsgSaCoWorkLinkWorkList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity getZxGcsgSaCoWorkLinkWorkLeftTree(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork);

	public ResponseEntity clickAddOrDeleteZxGcsgSaCoWorkLinkWork(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork);

	public ResponseEntity updateZxGcsgSaCoWorkLinkWorkIsMain(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork);

}
