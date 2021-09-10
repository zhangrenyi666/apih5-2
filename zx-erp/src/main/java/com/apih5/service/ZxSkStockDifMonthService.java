package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockDifMonth;

public interface ZxSkStockDifMonthService {

    public ResponseEntity getZxSkStockDifMonthListByCondition(ZxSkStockDifMonth zxSkStockDifMonth);

    public ResponseEntity getZxSkStockDifMonthDetail(ZxSkStockDifMonth zxSkStockDifMonth);

    public ResponseEntity saveZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth);

    public ResponseEntity updateZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth);

    public ResponseEntity batchDeleteUpdateZxSkStockDifMonth(List<ZxSkStockDifMonth> zxSkStockDifMonthList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity checkZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth);

    public ResponseEntity counterCheckZxSkStockDifMonth(ZxSkStockDifMonth zxSkStockDifMonth);

}
