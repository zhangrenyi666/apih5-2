package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkColInven;

public interface ZxSkColInvenService {

    public ResponseEntity getZxSkColInvenListByCondition(ZxSkColInven zxSkColInven);

    public ResponseEntity getZxSkColInvenDetail(ZxSkColInven zxSkColInven);

    public ResponseEntity saveZxSkColInven(ZxSkColInven zxSkColInven);

    public ResponseEntity updateZxSkColInven(ZxSkColInven zxSkColInven);

    public ResponseEntity batchDeleteUpdateZxSkColInven(List<ZxSkColInven> zxSkColInvenList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkColInvenResourceList(ZxSkColInven zxSkColInven);

    public ResponseEntity getZxSkColInvenInOrgList(ZxSkColInven zxSkColInven);
}
