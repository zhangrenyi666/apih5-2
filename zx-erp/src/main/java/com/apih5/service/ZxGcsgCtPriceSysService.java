package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSys;

public interface ZxGcsgCtPriceSysService {

	public ResponseEntity getZxGcsgCtPriceSysListByCondition(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

	public ResponseEntity getZxGcsgCtPriceSysDetail(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

	public ResponseEntity saveZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

	public ResponseEntity updateZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

	public ResponseEntity batchDeleteUpdateZxGcsgCtPriceSys(List<ZxGcsgCtPriceSys> zxGcsgCtPriceSysList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity psSyncHookDataZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

	public ResponseEntity psglbxGetZxGcsgCtPriceSysDetails(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

	public ResponseEntity psglbxUpdateZxGcsgCtPriceSysAndItem(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

	public ResponseEntity glSyncHookDataZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

	public ResponseEntity bxSyncHookDataZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys);

}
