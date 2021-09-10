package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransItemReceiving;
import com.apih5.mybatis.pojo.ZxSkStockTransferReceiving;

public interface ZxSkStockTransferReceivingService {

    public ResponseEntity getZxSkStockTransferReceivingListByCondition(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    public ResponseEntity getZxSkStockTransferReceivingDetails(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    public ResponseEntity saveZxSkStockTransferReceiving(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    public ResponseEntity updateZxSkStockTransferReceiving(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    public ResponseEntity batchDeleteUpdateZxSkStockTransferReceiving(List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList);

    public ResponseEntity checkZxSkStockTransferReceiving(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    public ResponseEntity getZxSkStockTransferReceivingNo(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    public List<ZxSkStockTransItemReceiving> getZxSkStockTransferReceivingByContractNoItemList(List<String> zxSkStockTransferReceivingList);

    public List<ZxSkStockTransferReceiving> getZxSkStockTransferReceivingByContractNoList(ZxSkStockTransferReceiving zxSkStockTransferReceiving);

    public List<ZxSkStockTransItemReceiving> getZxSkStockTransferReceivingByContractNoItemListNew(List<ZxSkStockTransferReceiving> zxSkStockTransferReceivingList);

    public void updateSuppliesShopResState(List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingsList);

}

