package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfProjectEmpMain;

public interface ZxSfProjectEmpMainService {

    public ResponseEntity getZxSfProjectEmpMainListByCondition(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZxSfProjectEmpMainDetail(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity saveZxSfProjectEmpMain(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity updateZxSfProjectEmpMain(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity batchDeleteUpdateZxSfProjectEmpMain(List<ZxSfProjectEmpMain> zxSfProjectEmpMainList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 学历图表接口
     * @suncg
     *
     * */
    public ResponseEntity getZxSfProEmpMainJuInfo();

    public ResponseEntity getZxSfProEmpMainComInfo(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZxSfProEmpMainOrgList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZxSfProEmpMainGuiDangList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZxSfProEmpMainJiaoGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZxSfProEmpMainWanGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZxSfProEmpMainKaiGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);


    /**
     * 职称图表接口
     * @suncg
     *
     * */
    public ResponseEntity getZhiChengComList();

    public ResponseEntity getZhiChengJuInfo(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZhiChengComInfo(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZxSfProEmpZhiChengOrgList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZhiChengGuiDangList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZhiChengJiaoGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZhiChengWanGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZhiChengKaiGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);


    public ResponseEntity getWorkAgeComList();

    public ResponseEntity getWorkAgeComInfo(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getWorkAgeJuInfo(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getZxSfProEmpWorkAgeOrgList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getWorkAgeGuiDangList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getWorkAgeJiaoGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getWorkAgeWanGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getWorkAgeKaiGongList(ZxSfProjectEmpMain zxSfProjectEmpMain);

    public ResponseEntity getJuInfo(ZxSfProjectEmpMain zxSfProjectEmpMain);


}
