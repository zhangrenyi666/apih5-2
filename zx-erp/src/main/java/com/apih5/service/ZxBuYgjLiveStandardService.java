package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxBuYgjLiveStandard;

public interface ZxBuYgjLiveStandardService {

    public ResponseEntity getZxBuYgjLiveStandardListByCondition(ZxBuYgjLiveStandard zxBuYgjLiveStandard);

    public ResponseEntity getZxBuYgjLiveStandardDetail(ZxBuYgjLiveStandard zxBuYgjLiveStandard);

    public ResponseEntity saveZxBuYgjLiveStandard(ZxBuYgjLiveStandard zxBuYgjLiveStandard);

    public ResponseEntity updateZxBuYgjLiveStandard(ZxBuYgjLiveStandard zxBuYgjLiveStandard);

    public ResponseEntity batchDeleteUpdateZxBuYgjLiveStandard(List<ZxBuYgjLiveStandard> zxBuYgjLiveStandardList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxBuYgjLiveStandardTree(ZxBuYgjLiveStandard zxBuYgjLiveStandard);

    public ResponseEntity getZxBuYgjLiveStandardTreeList(ZxBuYgjLiveStandard zxBuYgjLiveStandard);
}
