package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzVideoHistory;

public interface ZjTzVideoHistoryService {

    public ResponseEntity getZjTzVideoHistoryListByCondition(ZjTzVideoHistory zjTzVideoHistory);

    public ResponseEntity getZjTzVideoHistoryDetails(ZjTzVideoHistory zjTzVideoHistory);

    public ResponseEntity saveZjTzVideoHistory(ZjTzVideoHistory zjTzVideoHistory);

    public ResponseEntity updateZjTzVideoHistory(ZjTzVideoHistory zjTzVideoHistory);

    public ResponseEntity batchDeleteUpdateZjTzVideoHistory(List<ZjTzVideoHistory> zjTzVideoHistoryList);

}

