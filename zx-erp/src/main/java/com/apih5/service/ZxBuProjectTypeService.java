package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuProjectType;

public interface ZxBuProjectTypeService {

    public ResponseEntity getZxBuProjectTypeListByCondition(ZxBuProjectType zxBuProjectType);

    public ResponseEntity getZxBuProjectTypeDetail(ZxBuProjectType zxBuProjectType);

    public ResponseEntity saveZxBuProjectType(ZxBuProjectType zxBuProjectType);

    public ResponseEntity updateZxBuProjectType(ZxBuProjectType zxBuProjectType);

    public ResponseEntity batchDeleteUpdateZxBuProjectType(List<ZxBuProjectType> zxBuProjectTypeList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity checkZxBuProjectType(ZxBuProjectType zxBuProjectType);

    public ResponseEntity getZxBuProjectTypeCheckOver(ZxBuProjectType zxBuProjectType);

    public ResponseEntity getZxBuProjectTypeProjectList(ZxBuProjectType zxBuProjectType);

}
