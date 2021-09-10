package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPartManage;

public interface ZjTzPartManageService {

    public ResponseEntity getZjTzPartManageListByCondition(ZjTzPartManage zjTzPartManage);

    public ResponseEntity getZjTzPartManageDetails(ZjTzPartManage zjTzPartManage);

    public ResponseEntity saveZjTzPartManage(ZjTzPartManage zjTzPartManage);

    public ResponseEntity updateZjTzPartManage(ZjTzPartManage zjTzPartManage);

    public ResponseEntity batchDeleteUpdateZjTzPartManage(List<ZjTzPartManage> zjTzPartManageList);

	public ResponseEntity insertZjTzPartManage(ZjTzPartManage zjTzPartManage);

	public ResponseEntity batchLockUpdateZjTzPartManage(List<ZjTzPartManage> zjTzPartManageList);

	public ResponseEntity batchClearUpdateZjTzPartManage(List<ZjTzPartManage> zjTzPartManageList);

}

