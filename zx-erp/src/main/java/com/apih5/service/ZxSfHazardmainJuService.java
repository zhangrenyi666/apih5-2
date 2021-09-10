package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfHazardmainJu;

public interface ZxSfHazardmainJuService {

    public ResponseEntity getZxSfHazardmainJuListByCondition(ZxSfHazardmainJu zxSfHazardmainJu);

    public ResponseEntity getZxSfHazardmainJuDetail(ZxSfHazardmainJu zxSfHazardmainJu);

    public ResponseEntity saveZxSfHazardmainJu(ZxSfHazardmainJu zxSfHazardmainJu);

    public ResponseEntity updateZxSfHazardmainJu(ZxSfHazardmainJu zxSfHazardmainJu);

    public ResponseEntity batchDeleteUpdateZxSfHazardmainJu(List<ZxSfHazardmainJu> zxSfHazardmainJuList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
