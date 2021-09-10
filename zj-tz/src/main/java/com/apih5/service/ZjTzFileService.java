package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzFile;

public interface ZjTzFileService {

    public ResponseEntity getZjTzFileListByCondition(ZjTzFile zjTzFile);

    public ResponseEntity getZjTzFileDetails(ZjTzFile zjTzFile);

    public ResponseEntity saveZjTzFile(ZjTzFile zjTzFile);

    public ResponseEntity updateZjTzFile(ZjTzFile zjTzFile);

    public ResponseEntity batchDeleteUpdateZjTzFile(List<ZjTzFile> zjTzFileList);

}

