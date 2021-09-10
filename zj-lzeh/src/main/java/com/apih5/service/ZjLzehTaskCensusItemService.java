package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTaskCensusItem;

public interface ZjLzehTaskCensusItemService {

    public ResponseEntity getZjLzehTaskCensusItemListByCondition(ZjLzehTaskCensusItem zjLzehTaskCensusItem);

    public ResponseEntity getZjLzehTaskCensusItemDetail(ZjLzehTaskCensusItem zjLzehTaskCensusItem);

    public ResponseEntity saveZjLzehTaskCensusItem(ZjLzehTaskCensusItem zjLzehTaskCensusItem);

    public ResponseEntity updateZjLzehTaskCensusItem(ZjLzehTaskCensusItem zjLzehTaskCensusItem);

    public ResponseEntity batchDeleteUpdateZjLzehTaskCensusItem(List<ZjLzehTaskCensusItem> zjLzehTaskCensusItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZjLzehTaskCensusItemChartList(ZjLzehTaskCensusItem zjLzehTaskCensusItem);

    public ResponseEntity getTaskCensusItemChartByCenMonth(ZjLzehTaskCensusItem zjLzehTaskCensusItem);
}
