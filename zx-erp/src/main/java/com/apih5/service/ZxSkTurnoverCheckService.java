package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverCheck;

public interface ZxSkTurnoverCheckService {

    public ResponseEntity getZxSkTurnoverCheckListByCondition(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public ResponseEntity getZxSkTurnoverCheckDetail(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public ResponseEntity saveZxSkTurnoverCheck(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public ResponseEntity updateZxSkTurnoverCheck(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public ResponseEntity batchDeleteUpdateZxSkTurnoverCheck(List<ZxSkTurnoverCheck> zxSkTurnoverCheckList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkTurnoverCheckNo(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public ResponseEntity getZxSkTurnoverCheckReceive(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public ResponseEntity checkZxSkTurnoverCheck(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public List<ZxSkTurnoverCheck> ureportZxSkTurnoverCheckList(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public ResponseEntity getUreportZxSkTurnoverCheckList(ZxSkTurnoverCheck zxSkTurnoverCheck);

    public ResponseEntity getZxSkTurnoverCheckSupplierList(ZxSkTurnoverCheck zxSkTurnoverCheck);

}
