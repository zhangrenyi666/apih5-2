package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResourceOut;

public interface ZxSkResourceOutService {

    public ResponseEntity getZxSkResourceOutListByCondition(ZxSkResourceOut zxSkResourceOut);

    public ResponseEntity getZxSkResourceOutDetail(ZxSkResourceOut zxSkResourceOut);

    public ResponseEntity saveZxSkResourceOut(ZxSkResourceOut zxSkResourceOut);

    public ResponseEntity updateZxSkResourceOut(ZxSkResourceOut zxSkResourceOut);

    public ResponseEntity batchDeleteUpdateZxSkResourceOut(List<ZxSkResourceOut> zxSkResourceOutList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSkResourceOut> ureportZxSkResourceOut(ZxSkResourceOut zxSkResourceOut);
    
    public ResponseEntity ureportZxSkResourceOutIdle(ZxSkResourceOut zxSkResourceOut);
}
