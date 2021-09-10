package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzThousandCheck;

public interface ZjTzThousandCheckService {

    public ResponseEntity getZjTzThousandCheckListByCondition(ZjTzThousandCheck zjTzThousandCheck);

    public ResponseEntity getZjTzThousandCheckDetails(ZjTzThousandCheck zjTzThousandCheck);

    public ResponseEntity saveZjTzThousandCheck(ZjTzThousandCheck zjTzThousandCheck);

    public ResponseEntity updateZjTzThousandCheck(ZjTzThousandCheck zjTzThousandCheck);

    public ResponseEntity batchDeleteUpdateZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList);

	public ResponseEntity batchReleaseZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList);

	public ResponseEntity batchRecallZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList);

	public ResponseEntity batchExportZjTzThousandCheckFile(List<ZjTzThousandCheck> zjTzThousandCheckList);

	public ResponseEntity saveZjTzThousandCheckAllDeduct(ZjTzThousandCheck zjTzThousandCheck);

}

