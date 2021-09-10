package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPolicyLocal;

public interface ZjTzPolicyLocalService {

    public ResponseEntity getZjTzPolicyLocalListByCondition(ZjTzPolicyLocal zjTzPolicyLocal);

    public ResponseEntity getZjTzPolicyLocalDetails(ZjTzPolicyLocal zjTzPolicyLocal);

    public ResponseEntity saveZjTzPolicyLocal(ZjTzPolicyLocal zjTzPolicyLocal);

    public ResponseEntity updateZjTzPolicyLocal(ZjTzPolicyLocal zjTzPolicyLocal);

    public ResponseEntity batchDeleteUpdateZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList);

	public ResponseEntity updateZjTzPolicyLocalPush(ZjTzPolicyLocal zjTzPolicyLocal);

	public ResponseEntity updateZjTzPolicyLocalHomeShow(ZjTzPolicyLocal zjTzPolicyLocal);

	public ResponseEntity batchDeleteReleaseZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList);

	public ResponseEntity batchDeleteRecallZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList);

	public ResponseEntity batchExportZjTzPolicyLocalFile(List<ZjTzPolicyLocal> zjTzPolicyLocalList);

	public ResponseEntity batchReportZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList);

	public ResponseEntity batchReturnZjTzPolicyLocal(List<ZjTzPolicyLocal> zjTzPolicyLocalList);

}

