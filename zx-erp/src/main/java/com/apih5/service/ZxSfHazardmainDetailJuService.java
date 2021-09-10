package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfHazardmainDetailJu;

public interface ZxSfHazardmainDetailJuService {

    public ResponseEntity getZxSfHazardmainDetailJuListByCondition(ZxSfHazardmainDetailJu zxSfHazardmainDetailJu);

    public ResponseEntity getZxSfHazardmainDetailJuDetail(ZxSfHazardmainDetailJu zxSfHazardmainDetailJu);

    public ResponseEntity saveZxSfHazardmainDetailJu(ZxSfHazardmainDetailJu zxSfHazardmainDetailJu);

    public ResponseEntity updateZxSfHazardmainDetailJu(ZxSfHazardmainDetailJu zxSfHazardmainDetailJu);

    public ResponseEntity batchDeleteUpdateZxSfHazardmainDetailJu(List<ZxSfHazardmainDetailJu> zxSfHazardmainDetailJuList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
