package com.apih5.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlanItem;

public interface ZjLzehManageTaskPlanItemService {

    public ResponseEntity getZjLzehManageTaskPlanItemListByCondition(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem);

    public ResponseEntity getZjLzehManageTaskPlanItemDetail(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem);

    public ResponseEntity saveZjLzehManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem);

    public ResponseEntity updateZjLzehManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem);

    public ResponseEntity batchDeleteUpdateZjLzehManageTaskPlanItem(List<ZjLzehManageTaskPlanItem> zjLzehManageTaskPlanItemList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 导入经营目标任务计划明细
     *
     * @param zjLzehManageTaskPlanItem
     * @author suncg
     */
    public ResponseEntity importManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem);
    
    public ResponseEntity exportManageTaskPlanItem(ZjLzehManageTaskPlanItem zjLzehManageTaskPlanItem, HttpServletResponse response);
}
