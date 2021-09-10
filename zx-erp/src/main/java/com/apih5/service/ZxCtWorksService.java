package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtWorks;

public interface ZxCtWorksService {

	public ResponseEntity getZxCtWorksListByCondition(ZxCtWorks zxCtWorks);

	public ResponseEntity getZxCtWorksDetail(ZxCtWorks zxCtWorks);

	public ResponseEntity saveZxCtWorks(ZxCtWorks zxCtWorks);

	public ResponseEntity updateZxCtWorks(ZxCtWorks zxCtWorks);

	public ResponseEntity batchDeleteUpdateZxCtWorks(List<ZxCtWorks> zxCtWorksList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
	public ResponseEntity getZxCtWorksWorkNameTree(ZxCtWorks zxCtWorks);

	public ResponseEntity getZxCtWorksTreeList(ZxCtWorks zxCtWorks);

	public ResponseEntity getZxCtWorksBalanceList(ZxCtWorks zxCtWorks);

	public ResponseEntity updateZxCtWorksBalanceList(List<ZxCtWorks> zxCtWorksList);

	public ResponseEntity getZxCtWorksBalanceEditList(ZxCtWorks zxCtWorks);

	public ResponseEntity updateZxCtWorksList(ZxCtWorks zxCtWorks);

	public ResponseEntity importZxCtWorks(ZxCtWorks zxCtWorks);

	public ResponseEntity addZxCtWorksChange(ZxCtWorks zxCtWorks);

	public ResponseEntity delZxCtWorksByOrgIDWorkBookID(ZxCtWorks zxCtWorks);

	public ResponseEntity saveZxCtWorksList(ZxCtWorks zxCtWorks);

	public ResponseEntity getZxCtWorksRightTree(ZxCtWorks zxCtWorks);

}
