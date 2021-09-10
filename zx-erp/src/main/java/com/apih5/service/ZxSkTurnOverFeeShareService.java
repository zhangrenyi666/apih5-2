package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShare;

public interface ZxSkTurnOverFeeShareService {

    public ResponseEntity getZxSkTurnOverFeeShareListByCondition(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    public ResponseEntity getZxSkTurnOverFeeShareDetail(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    public ResponseEntity saveZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    public ResponseEntity updateZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    public ResponseEntity batchDeleteUpdateZxSkTurnOverFeeShare(List<ZxSkTurnOverFeeShare> zxSkTurnOverFeeShareList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkTurnOverFeeShareNo(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    public ResponseEntity getZxSkTurnOverFeeShareResourceList(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    public ResponseEntity checkZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);

    public ResponseEntity counterCheckZxSkTurnOverFeeShare(ZxSkTurnOverFeeShare zxSkTurnOverFeeShare);


}
