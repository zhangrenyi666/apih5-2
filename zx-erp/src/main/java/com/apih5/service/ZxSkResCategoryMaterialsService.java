package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResCategoryMaterials;

public interface ZxSkResCategoryMaterialsService {

    public ResponseEntity getZxSkResCategoryMaterialsListByCondition(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity getZxSkResCategoryMaterialsDetails(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity saveZxSkResCategoryMaterials(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity updateZxSkResCategoryMaterials(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity batchDeleteUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList);

    public ResponseEntity getZxSkResCategoryMaterialsTree(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity batchStartUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList);

    public ResponseEntity batchStopUpdateZxSkResCategoryMaterials(List<ZxSkResCategoryMaterials> zxSkResCategoryMaterialsList);

    public ResponseEntity getZxSkResCategoryMaterialsListResource(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity getZxSkResourceMaterialsListNameJoinResource(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity getZxSkResCategoryMaterialsAll(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity getZxSkResCategoryMaterialsAllResource(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

    public ResponseEntity getZxSkResCategoryMaterialsListResourceNotRevolve(ZxSkResCategoryMaterials zxSkResCategoryMaterials);

}

