package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehFile;

public interface ZjLzehFileService {

    public ResponseEntity getZjLzehFileListByCondition(ZjLzehFile zjLzehFile);

    public ResponseEntity getZjLzehFileDetails(ZjLzehFile zjLzehFile);

    public ResponseEntity saveZjLzehFile(ZjLzehFile zjLzehFile);

    public ResponseEntity updateZjLzehFile(ZjLzehFile zjLzehFile);

    public ResponseEntity batchDeleteUpdateZjLzehFile(List<ZjLzehFile> zjLzehFileList);

}

