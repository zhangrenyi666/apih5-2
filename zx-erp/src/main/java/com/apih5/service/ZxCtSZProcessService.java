package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtSZProcess;

public interface ZxCtSZProcessService {

	public ResponseEntity getZxCtSZProcessListByCondition(ZxCtSZProcess zxCtSZProcess);

	public ResponseEntity getZxCtSZProcessDetails(ZxCtSZProcess zxCtSZProcess);

	public ResponseEntity saveZxCtSZProcess(ZxCtSZProcess zxCtSZProcess);

	public ResponseEntity updateZxCtSZProcess(ZxCtSZProcess zxCtSZProcess);

	public ResponseEntity batchDeleteUpdateZxCtSZProcess(List<ZxCtSZProcess> zxCtSZProcessList);

	public ResponseEntity getZxCtSZProcessTree(ZxCtSZProcess zxCtSZProcess);

	public ResponseEntity getZxCtSZProcessItemList(ZxCtSZProcess zxCtSZProcess);

	public ResponseEntity gcsgGetZxCtSZProcessList(ZxCtSZProcess zxCtSZProcess);

	public ResponseEntity gcsgGetZxCtSZProcessSelect(ZxCtSZProcess zxCtSZProcess);

}
