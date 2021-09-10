package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqToEquipCategoryQuantityQueryPage;

public interface ZxEqToEquipCategoryQuantityQueryPageService {

    public ResponseEntity getZxEqToEquipCategoryQuantityQueryPageListByCondition(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage);

    public ResponseEntity getZxEqToEquipCategoryQuantityQueryPageDetail(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage);

    public ResponseEntity saveZxEqToEquipCategoryQuantityQueryPage(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage);

    public ResponseEntity updateZxEqToEquipCategoryQuantityQueryPage(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage);

    public ResponseEntity batchDeleteUpdateZxEqToEquipCategoryQuantityQueryPage(List<ZxEqToEquipCategoryQuantityQueryPage> zxEqToEquipCategoryQuantityQueryPageList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageIdle(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage);
    
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageChartIdle(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage);
    
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageRentOutIdle(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage);
    
    public ResponseEntity ureportZxEqToEquipCategoryQuantityQueryPageCooperationUnitIdle(ZxEqToEquipCategoryQuantityQueryPage zxEqToEquipCategoryQuantityQueryPage);
}
