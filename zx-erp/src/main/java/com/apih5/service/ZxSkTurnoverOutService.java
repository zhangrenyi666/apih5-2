package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverOut;

public interface ZxSkTurnoverOutService {

    public ResponseEntity getZxSkTurnoverOutListByCondition(ZxSkTurnoverOut zxSkTurnoverOut);

    public ResponseEntity getZxSkTurnoverOutDetail(ZxSkTurnoverOut zxSkTurnoverOut);

    public ResponseEntity saveZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut);

    public ResponseEntity updateZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut);

    public ResponseEntity batchDeleteUpdateZxSkTurnoverOut(List<ZxSkTurnoverOut> zxSkTurnoverOutList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSkTurnoverOutNo(ZxSkTurnoverOut zxSkTurnoverOut);

    public ResponseEntity getZxSkTurnoverOutResourceList(ZxSkTurnoverOut zxSkTurnoverOut);

    public ResponseEntity checkZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut);

    public ResponseEntity returnZxSkTurnoverOut(ZxSkTurnoverOut zxSkTurnoverOut);

}
