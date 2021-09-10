package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtContrJzItem;

public interface ZxCtContrJzItemService {

    public ResponseEntity getZxCtContrJzItemListByCondition(ZxCtContrJzItem zxCtContrJzItem);

    public ResponseEntity getZxCtContrJzItemDetail(ZxCtContrJzItem zxCtContrJzItem);

    public ResponseEntity saveZxCtContrJzItem(ZxCtContrJzItem zxCtContrJzItem);

    public ResponseEntity updateZxCtContrJzItem(ZxCtContrJzItem zxCtContrJzItem);

    public ResponseEntity batchDeleteUpdateZxCtContrJzItem(List<ZxCtContrJzItem> zxCtContrJzItemList);

	public ResponseEntity addZxCtContrJzItemForCs(ZxCtContrJzItem zxCtContrJzItem);

	public ResponseEntity addZxCtContrJzItemForDq(ZxCtContrJzItem zxCtContrJzItem);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
