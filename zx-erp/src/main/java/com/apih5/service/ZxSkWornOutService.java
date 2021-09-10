package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkWornOut;

public interface ZxSkWornOutService {

    public ResponseEntity getZxSkWornOutListByCondition(ZxSkWornOut zxSkWornOut);

    public ResponseEntity getZxSkWornOutDetail(ZxSkWornOut zxSkWornOut);

    public ResponseEntity saveZxSkWornOut(ZxSkWornOut zxSkWornOut);

    public ResponseEntity updateZxSkWornOut(ZxSkWornOut zxSkWornOut);

    public ResponseEntity batchDeleteUpdateZxSkWornOut(List<ZxSkWornOut> zxSkWornOutList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkWornOutNo(ZxSkWornOut zxSkWornOut);
}
