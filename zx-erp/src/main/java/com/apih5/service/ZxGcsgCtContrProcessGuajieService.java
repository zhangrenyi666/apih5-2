package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxGcsgCtContrProcessGuajie;

public interface ZxGcsgCtContrProcessGuajieService {

    public ResponseEntity getZxGcsgCtContrProcessGuajieListByCondition(ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie);

    public ResponseEntity getZxGcsgCtContrProcessGuajieDetail(ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie);

    public ResponseEntity saveZxGcsgCtContrProcessGuajie(ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie);

    public ResponseEntity updateZxGcsgCtContrProcessGuajie(ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie);

    public ResponseEntity batchDeleteUpdateZxGcsgCtContrProcessGuajie(List<ZxGcsgCtContrProcessGuajie> zxGcsgCtContrProcessGuajieList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
