package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxSystemAutoScoreDetailed;

public interface ZjXmJxSystemAutoScoreDetailedService {

    public ResponseEntity getZjXmJxSystemAutoScoreDetailedListByCondition(ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed);

    public ResponseEntity getZjXmJxSystemAutoScoreDetailedDetails(ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed);

    public ResponseEntity saveZjXmJxSystemAutoScoreDetailed(ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed);

    public ResponseEntity updateZjXmJxSystemAutoScoreDetailed(ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed);

    public ResponseEntity batchDeleteUpdateZjXmJxSystemAutoScoreDetailed(List<ZjXmJxSystemAutoScoreDetailed> zjXmJxSystemAutoScoreDetailedList);

}

