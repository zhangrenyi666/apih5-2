package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzOtherData;

public interface ZjTzOtherDataService {

    public ResponseEntity getZjTzOtherDataListByCondition(ZjTzOtherData zjTzOtherData);

    public ResponseEntity getZjTzOtherDataDetails(ZjTzOtherData zjTzOtherData);

    public ResponseEntity saveZjTzOtherData(ZjTzOtherData zjTzOtherData);

    public ResponseEntity updateZjTzOtherData(ZjTzOtherData zjTzOtherData);

    public ResponseEntity batchDeleteUpdateZjTzOtherData(List<ZjTzOtherData> zjTzOtherDataList);

	public ResponseEntity toHomeShowZjTzOtherData(ZjTzOtherData zjTzOtherData);

	public ResponseEntity batchDeleteReleaseZjTzOtherData(List<ZjTzOtherData> zjTzOtherDataList);

	public ResponseEntity batchDeleteRecallZjTzOtherData(List<ZjTzOtherData> zjTzOtherDataList);

	public ResponseEntity batchExportZjTzOtherDataFile(List<ZjTzOtherData> zjTzOtherDataList);

	public ResponseEntity getZjTzOtherDataHome(ZjTzOtherData zjTzOtherData);

}

