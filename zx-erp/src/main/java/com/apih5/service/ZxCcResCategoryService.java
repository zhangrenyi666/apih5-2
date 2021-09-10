package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCcResCategory;

public interface ZxCcResCategoryService {

    public ResponseEntity getZxCcResCategoryListByCondition(ZxCcResCategory zxCcResCategory);

    public ResponseEntity getZxCcResCategoryDetail(ZxCcResCategory zxCcResCategory);

    public ResponseEntity saveZxCcResCategory(ZxCcResCategory zxCcResCategory);

    public ResponseEntity updateZxCcResCategory(ZxCcResCategory zxCcResCategory);

    public ResponseEntity batchDeleteUpdateZxCcResCategory(List<ZxCcResCategory> zxCcResCategoryList);

	public ResponseEntity getZxCcResCategoryTree(ZxCcResCategory zxCcResCategory);

	public ResponseEntity getZxCcResCategoryItemList(ZxCcResCategory zxCcResCategory);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
