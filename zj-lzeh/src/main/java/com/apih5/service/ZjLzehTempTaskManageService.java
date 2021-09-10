package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehTempTaskManage;

public interface ZjLzehTempTaskManageService {

    public ResponseEntity getZjLzehTempTaskManageListByCondition(ZjLzehTempTaskManage zjLzehTempTaskManage);

    public ResponseEntity getZjLzehTempTaskManageDetail(ZjLzehTempTaskManage zjLzehTempTaskManage);

    public ResponseEntity saveZjLzehTempTaskManage(ZjLzehTempTaskManage zjLzehTempTaskManage);

    public ResponseEntity updateZjLzehTempTaskManage(ZjLzehTempTaskManage zjLzehTempTaskManage);

    public ResponseEntity batchDeleteUpdateZjLzehTempTaskManage(List<ZjLzehTempTaskManage> zjLzehTempTaskManageList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getZjLzehTempTaskManageTreeByCondition(ZjLzehTempTaskManage zjLzehTempTaskManage);

    /**
     * 通过人员查询临时任务
     * @author suncg
     * @param zjLzehTempTaskManage
     * */
    public ResponseEntity getZjLzehTempTaskManageListByPerson(ZjLzehTempTaskManage zjLzehTempTaskManage);

    /**
     * 根节点分页查询树列表
     * @author suncg
     * @param zjLzehTempTaskManage
     * */
    public ResponseEntity getZjLzehTempTaskManageTreeList(ZjLzehTempTaskManage zjLzehTempTaskManage);


    /**
     * 跳转查询
     * @author suncg
     * @param zjLzehTempTaskManage
     * */
    public ResponseEntity selectZjLzehTempTaskManageListByPersonMonth(ZjLzehTempTaskManage zjLzehTempTaskManage);
}
