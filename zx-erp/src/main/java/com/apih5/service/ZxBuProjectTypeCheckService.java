package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuProjectTypeCheck;

public interface ZxBuProjectTypeCheckService {

    public ResponseEntity getZxBuProjectTypeCheckListByCondition(ZxBuProjectTypeCheck zxBuProjectTypeCheck);

    public ResponseEntity getZxBuProjectTypeCheckDetail(ZxBuProjectTypeCheck zxBuProjectTypeCheck);

    public ResponseEntity saveZxBuProjectTypeCheck(ZxBuProjectTypeCheck zxBuProjectTypeCheck);

    public ResponseEntity updateZxBuProjectTypeCheck(ZxBuProjectTypeCheck zxBuProjectTypeCheck);

    public ResponseEntity batchDeleteUpdateZxBuProjectTypeCheck(List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    ResponseEntity getZxBuProjectTypeCheckTree(ZxBuProjectTypeCheck zxBuProjectTypeCheck);

    ResponseEntity getZxBuProjectTypeCheckTreeList(ZxBuProjectTypeCheck zxBuProjectTypeCheck);

    ResponseEntity getZxBuProjectTypeCheckProjectType(ZxBuProjectTypeCheck zxBuProjectTypeCheck);

}
