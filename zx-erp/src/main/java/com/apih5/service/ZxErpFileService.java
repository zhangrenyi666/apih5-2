package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxErpFile;

public interface ZxErpFileService {

    public ResponseEntity getZxErpFileListByCondition(ZxErpFile zxErpFile);

    public ResponseEntity getZxErpFileDetails(ZxErpFile zxErpFile);

    public ResponseEntity saveZxErpFile(ZxErpFile zxErpFile);

    public ResponseEntity updateZxErpFile(ZxErpFile zxErpFile);

    public ResponseEntity batchDeleteUpdateZxErpFile(List<ZxErpFile> zxErpFileList);

	public ResponseEntity deleteAllZxErpFile(ZxErpFile zxErpFile);

}

