package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrClaimItem;

public interface ZxCtContrClaimItemService {

    public ResponseEntity getZxCtContrClaimItemListByCondition(ZxCtContrClaimItem zxCtContrClaimItem);

    public ResponseEntity getZxCtContrClaimItemDetail(ZxCtContrClaimItem zxCtContrClaimItem);

    public ResponseEntity saveZxCtContrClaimItem(ZxCtContrClaimItem zxCtContrClaimItem);

    public ResponseEntity updateZxCtContrClaimItem(ZxCtContrClaimItem zxCtContrClaimItem);

    public ResponseEntity batchDeleteUpdateZxCtContrClaimItem(List<ZxCtContrClaimItem> zxCtContrClaimItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity delAllZxCtContrClaimItem(ZxCtContrClaimItem zxCtContrClaimItem);
}
