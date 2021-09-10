package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfSourceOfDanger;

public interface ZxSfSourceOfDangerService {

    public ResponseEntity getZxSfSourceOfDangerListByCondition(ZxSfSourceOfDanger zxSfSourceOfDanger);

    public ResponseEntity getZxSfSourceOfDangerDetail(ZxSfSourceOfDanger zxSfSourceOfDanger);

    public ResponseEntity saveZxSfSourceOfDanger(ZxSfSourceOfDanger zxSfSourceOfDanger);

    public ResponseEntity updateZxSfSourceOfDanger(ZxSfSourceOfDanger zxSfSourceOfDanger);

    public ResponseEntity batchDeleteUpdateZxSfSourceOfDanger(List<ZxSfSourceOfDanger> zxSfSourceOfDangerList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
