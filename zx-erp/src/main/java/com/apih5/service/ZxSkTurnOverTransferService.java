package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnOverTransfer;

public interface ZxSkTurnOverTransferService {

    public ResponseEntity getZxSkTurnOverTransferListByCondition(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    public ResponseEntity getZxSkTurnOverTransferDetail(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    public ResponseEntity saveZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    public ResponseEntity updateZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    public ResponseEntity batchDeleteUpdateZxSkTurnOverTransfer(List<ZxSkTurnOverTransfer> zxSkTurnOverTransferList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkTurnOverTransferNo(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    public ResponseEntity getZxSkTurnOverTransferResourceList(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    public ResponseEntity checkZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

    public ResponseEntity counterCheckZxSkTurnOverTransfer(ZxSkTurnOverTransfer zxSkTurnOverTransfer);

}
