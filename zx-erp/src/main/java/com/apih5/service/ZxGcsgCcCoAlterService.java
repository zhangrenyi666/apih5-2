package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlter;

public interface ZxGcsgCcCoAlterService {

	public ResponseEntity getZxGcsgCcCoAlterListByCondition(ZxGcsgCcCoAlter zxGcsgCcCoAlter);

	public ResponseEntity getZxGcsgCcCoAlterDetail(ZxGcsgCcCoAlter zxGcsgCcCoAlter);

	public ResponseEntity saveZxGcsgCcCoAlter(ZxGcsgCcCoAlter zxGcsgCcCoAlter);

	public ResponseEntity updateZxGcsgCcCoAlter(ZxGcsgCcCoAlter zxGcsgCcCoAlter);

	public ResponseEntity batchDeleteUpdateZxGcsgCcCoAlter(List<ZxGcsgCcCoAlter> zxGcsgCcCoAlterList);

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	public ResponseEntity getZxGcsgCcCoAlterDetailsWorkList(ZxGcsgCcCoAlter zxGcsgCcCoAlter);

	public ResponseEntity saveZxGcsgCcCoAlterDetailsWorkList(ZxGcsgCcCoAlter zxGcsgCcCoAlter);

	public ResponseEntity saveZxGcsgCcCoAlterDetailsWorkList20210121(ZxGcsgCcCoAlter zxGcsgCcCoAlter);

}
