package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCmGlobalCode;

public interface ZxCmGlobalCodeService {

    public ResponseEntity getZxCmGlobalCodeListByCondition(ZxCmGlobalCode zxCmGlobalCode);

    public ResponseEntity getZxCmGlobalCodeDetail(ZxCmGlobalCode zxCmGlobalCode);

    public ResponseEntity saveZxCmGlobalCode(ZxCmGlobalCode zxCmGlobalCode);

    public ResponseEntity updateZxCmGlobalCode(ZxCmGlobalCode zxCmGlobalCode);

    public ResponseEntity batchDeleteUpdateZxCmGlobalCode(List<ZxCmGlobalCode> zxCmGlobalCodeList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
