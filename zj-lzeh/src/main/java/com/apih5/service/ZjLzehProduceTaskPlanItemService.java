package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlanItem;

public interface ZjLzehProduceTaskPlanItemService {

    public ResponseEntity getZjLzehProduceTaskPlanItemListByCondition(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem);

    public ResponseEntity getZjLzehProduceTaskPlanItemDetail(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem);

    public ResponseEntity saveZjLzehProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem);

    public ResponseEntity updateZjLzehProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem);

    public ResponseEntity batchDeleteUpdateZjLzehProduceTaskPlanItem(List<ZjLzehProduceTaskPlanItem> zjLzehProduceTaskPlanItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 导入生产目标任务计划明细
     * @author suncg
     * @param zjLzehProduceTaskPlanItem
     * */
    public ResponseEntity importProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem);
    
    public ResponseEntity exportProduceTaskPlanItem(ZjLzehProduceTaskPlanItem zjLzehProduceTaskPlanItem, HttpServletResponse response);
}
