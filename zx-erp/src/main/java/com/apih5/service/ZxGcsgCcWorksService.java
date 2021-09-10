package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCcWorks;
import cn.hutool.json.JSONArray;

public interface ZxGcsgCcWorksService {

	public ResponseEntity getZxGcsgCcWorksListByCondition(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity getZxGcsgCcWorksDetail(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity saveZxGcsgCcWorks(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity updateZxGcsgCcWorks(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity batchDeleteUpdateZxGcsgCcWorks(List<ZxGcsgCcWorks> zxGcsgCcWorksList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity importZxGcsgCcWorks(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity getZxGcsgCcWorksTree(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity getZxGcsgCcWorksListAmount(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity autoHookZxGcsgCcWorksProcess(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity manualHookZxGcsgCcWorksProcess(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity manualHookZxGcsgCcWorksRule(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity getZxGcsgCcWorksListProcess(ZxGcsgCcWorks zxGcsgCcWorks);

	public ResponseEntity getZxGcsgCcWorksSelect(ZxGcsgCcWorks zxGcsgCcWorks);

}
