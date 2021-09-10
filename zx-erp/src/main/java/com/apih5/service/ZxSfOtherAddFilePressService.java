package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfOtherAddFilePress;

public interface ZxSfOtherAddFilePressService {

    public ResponseEntity getZxSfOtherAddFilePressListByCondition(ZxSfOtherAddFilePress zxSfOtherAddFilePress);

    public ResponseEntity getZxSfOtherAddFilePressDetail(ZxSfOtherAddFilePress zxSfOtherAddFilePress);

    public ResponseEntity saveZxSfOtherAddFilePress(ZxSfOtherAddFilePress zxSfOtherAddFilePress);

    public ResponseEntity updateZxSfOtherAddFilePress(ZxSfOtherAddFilePress zxSfOtherAddFilePress);

    public ResponseEntity batchDeleteUpdateZxSfOtherAddFilePress(List<ZxSfOtherAddFilePress> zxSfOtherAddFilePressList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
