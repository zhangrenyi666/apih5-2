package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqYyglJbb;

public interface ZxEqYyglJbbService {

    public ResponseEntity getZxEqYyglJbbListByCondition(ZxEqYyglJbb zxEqYyglJbb);

    public ResponseEntity getZxEqYyglJbbDetail(ZxEqYyglJbb zxEqYyglJbb);

    public ResponseEntity saveZxEqYyglJbb(ZxEqYyglJbb zxEqYyglJbb);

    public ResponseEntity updateZxEqYyglJbb(ZxEqYyglJbb zxEqYyglJbb);

    public ResponseEntity batchDeleteUpdateZxEqYyglJbb(List<ZxEqYyglJbb> zxEqYyglJbbList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ZxEqYyglJbb ureportZxEqYyglJbb(ZxEqYyglJbb zxEqYyglJbb);
    
    public ResponseEntity ureportZxEqYyglJbbIdle(ZxEqYyglJbb zxEqYyglJbb);
}
