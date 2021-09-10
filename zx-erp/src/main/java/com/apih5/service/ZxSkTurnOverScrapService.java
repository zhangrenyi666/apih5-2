package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnOverScrap;

public interface ZxSkTurnOverScrapService {

    public ResponseEntity getZxSkTurnOverScrapListByCondition(ZxSkTurnOverScrap zxSkTurnOverScrap);

    public ResponseEntity getZxSkTurnOverScrapDetail(ZxSkTurnOverScrap zxSkTurnOverScrap);

    public ResponseEntity saveZxSkTurnOverScrap(ZxSkTurnOverScrap zxSkTurnOverScrap);

    public ResponseEntity updateZxSkTurnOverScrap(ZxSkTurnOverScrap zxSkTurnOverScrap);

    public ResponseEntity batchDeleteUpdateZxSkTurnOverScrap(List<ZxSkTurnOverScrap> zxSkTurnOverScrapList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZxSkTurnOverScrapNo(ZxSkTurnOverScrap zxSkTurnOverScrap);

    public ResponseEntity getZxSkTurnOverScrapResourceList(ZxSkTurnOverScrap zxSkTurnOverScrap);


}
