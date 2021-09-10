package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfOtherAddFile;

public interface ZxSfOtherAddFileService {

    public ResponseEntity getZxSfOtherAddFileListByCondition(ZxSfOtherAddFile zxSfOtherAddFile);

    public ResponseEntity getZxSfOtherAddFileDetail(ZxSfOtherAddFile zxSfOtherAddFile);

    public ResponseEntity saveZxSfOtherAddFile(ZxSfOtherAddFile zxSfOtherAddFile);

    public ResponseEntity updateZxSfOtherAddFile(ZxSfOtherAddFile zxSfOtherAddFile);

    public ResponseEntity batchDeleteUpdateZxSfOtherAddFile(List<ZxSfOtherAddFile> zxSfOtherAddFileList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
