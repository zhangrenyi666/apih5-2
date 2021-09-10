package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverIn;

public interface ZxSkTurnoverInService {

    public ResponseEntity getZxSkTurnoverInListByCondition(ZxSkTurnoverIn zxSkTurnoverIn);

    public ResponseEntity getZxSkTurnoverInDetail(ZxSkTurnoverIn zxSkTurnoverIn);

    public ResponseEntity saveZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn);

    public ResponseEntity updateZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn);

    public ResponseEntity batchDeleteUpdateZxSkTurnoverIn(List<ZxSkTurnoverIn> zxSkTurnoverInList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity checkZxSkTurnoverIn(ZxSkTurnoverIn zxSkTurnoverIn);

    public ResponseEntity getZxSkTurnoverInResourceList(ZxSkTurnoverIn zxSkTurnoverIn);

    public ResponseEntity getZxSkTurnoverInNo(ZxSkTurnoverIn zxSkTurnoverIn);

    public List<ZxSkTurnoverIn> ureportZxSkTurnoverInList(ZxSkTurnoverIn zxSkTurnoverIn);

    public ResponseEntity getUreportZxSkTurnoverInList(ZxSkTurnoverIn zxSkTurnoverIn);
}
