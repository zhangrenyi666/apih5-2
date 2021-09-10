package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzRunFile;

public interface ZjTzRunFileService {

    public ResponseEntity getZjTzRunFileListByCondition(ZjTzRunFile zjTzRunFile);

    public ResponseEntity getZjTzRunFileDetails(ZjTzRunFile zjTzRunFile);

    public ResponseEntity saveZjTzRunFile(ZjTzRunFile zjTzRunFile);

    public ResponseEntity updateZjTzRunFile(ZjTzRunFile zjTzRunFile);

    public ResponseEntity batchDeleteUpdateZjTzRunFile(List<ZjTzRunFile> zjTzRunFileList);

	public ResponseEntity batchExportZjTzRunFile(List<ZjTzRunFile> zjTzRunFileList);

}

