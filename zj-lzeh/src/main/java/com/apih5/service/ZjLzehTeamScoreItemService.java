package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTeamScoreItem;

public interface ZjLzehTeamScoreItemService {

    public ResponseEntity getZjLzehTeamScoreItemListByCondition(ZjLzehTeamScoreItem zjLzehTeamScoreItem);

    public ResponseEntity getZjLzehTeamScoreItemDetail(ZjLzehTeamScoreItem zjLzehTeamScoreItem);

    public ResponseEntity saveZjLzehTeamScoreItem(ZjLzehTeamScoreItem zjLzehTeamScoreItem);

    public ResponseEntity updateZjLzehTeamScoreItem(ZjLzehTeamScoreItem zjLzehTeamScoreItem);

    public ResponseEntity batchDeleteUpdateZjLzehTeamScoreItem(List<ZjLzehTeamScoreItem> zjLzehTeamScoreItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getChartList(ZjLzehTeamScoreItem zjLzehTeamScoreItem);

    public ResponseEntity getZjLzehTeamScoreItemByTeamScoreId(ZjLzehTeamScoreItem zjLzehTeamScoreItem);

    public ResponseEntity getChartByMonth(ZjLzehTeamScoreItem zjLzehTeamScoreItem);
}
