package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTaskCensus;

public interface ZjLzehTaskCensusService {

    public ResponseEntity getZjLzehTaskCensusListByCondition(ZjLzehTaskCensus zjLzehTaskCensus);

    public ResponseEntity getZjLzehTaskCensusDetail(ZjLzehTaskCensus zjLzehTaskCensus);

    public ResponseEntity saveZjLzehTaskCensus(ZjLzehTaskCensus zjLzehTaskCensus);

    public ResponseEntity updateZjLzehTaskCensus(ZjLzehTaskCensus zjLzehTaskCensus);

    public ResponseEntity batchDeleteUpdateZjLzehTaskCensus(List<ZjLzehTaskCensus> zjLzehTaskCensusList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
