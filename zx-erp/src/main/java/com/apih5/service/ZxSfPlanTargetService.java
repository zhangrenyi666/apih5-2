package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfPlanTarget;

public interface ZxSfPlanTargetService {

    public ResponseEntity getZxSfPlanTargetListByCondition(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity getZxSfPlanTargetDetail(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity saveZxSfPlanTarget(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity updateZxSfPlanTarget(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity batchDeleteUpdateZxSfPlanTarget(List<ZxSfPlanTarget> zxSfPlanTargetList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    ResponseEntity getJuInfo();

    public ResponseEntity getComList();

    public ResponseEntity getComInfo(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity getOrgList(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity getGuiDangList(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity getJiaoGongList(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity getWanGongList(ZxSfPlanTarget zxSfPlanTarget);

    public ResponseEntity getKaiGongList(ZxSfPlanTarget zxSfPlanTarget);

}
