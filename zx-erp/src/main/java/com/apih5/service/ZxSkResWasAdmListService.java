package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResWasAdmList;

public interface ZxSkResWasAdmListService {

    public ResponseEntity getZxSkResWasAdmListListByCondition(ZxSkResWasAdmList zxSkResWasAdmList);

    public ResponseEntity getZxSkResWasAdmListDetail(ZxSkResWasAdmList zxSkResWasAdmList);

    public ResponseEntity saveZxSkResWasAdmList(ZxSkResWasAdmList zxSkResWasAdmList);

    public ResponseEntity updateZxSkResWasAdmList(ZxSkResWasAdmList zxSkResWasAdmList);

    public ResponseEntity batchDeleteUpdateZxSkResWasAdmList(List<ZxSkResWasAdmList> zxSkResWasAdmListList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
