package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;

public interface ZxSkResourceMaterialsService {

    public ResponseEntity getZxSkResourceMaterialsListByCondition(ZxSkResourceMaterials zxSkResourceMaterials);

    public ResponseEntity getZxSkResourceMaterialsDetails(ZxSkResourceMaterials zxSkResourceMaterials);

    public ResponseEntity saveZxSkResourceMaterials(ZxSkResourceMaterials zxSkResourceMaterials);

    public ResponseEntity updateZxSkResourceMaterials(ZxSkResourceMaterials zxSkResourceMaterials);

    public ResponseEntity batchDeleteUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> zxSkResourceMaterialsList);

    public ResponseEntity batchStartUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> zxSkResourceMaterialsList);

    public ResponseEntity batchStopUpdateZxSkResourceMaterials(List<ZxSkResourceMaterials> zxSkResourceMaterialsList);

    public ResponseEntity getZxSkResourceMaterialsListNameJoin(ZxSkResourceMaterials zxSkResourceMaterials);

    public ResponseEntity getZxSkResourceMaterialsListNameJoinNotRevolve(ZxSkResourceMaterials zxSkResourceMaterials);

    public ResponseEntity getZxSkResourceMaterialsListNotRevolving(ZxSkResourceMaterials zxSkResourceMaterials);

}

