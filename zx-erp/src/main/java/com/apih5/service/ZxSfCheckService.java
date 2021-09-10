package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfCheck;

public interface ZxSfCheckService {

    public ResponseEntity getZxSfCheckListByCondition(ZxSfCheck zxSfCheck);

    public ResponseEntity getZxSfCheckDetail(ZxSfCheck zxSfCheck);

    public ResponseEntity saveZxSfCheck(ZxSfCheck zxSfCheck);

    public ResponseEntity updateZxSfCheck(ZxSfCheck zxSfCheck);

    public ResponseEntity batchDeleteUpdateZxSfCheck(List<ZxSfCheck> zxSfCheckList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public ResponseEntity updateZxSfCheckIsReported(ZxSfCheck zxSfCheck);
    
    public ResponseEntity updateZxSfCheckCheckAgainStatus(ZxSfCheck zxSfCheck);
    
    public ResponseEntity getZxSfCheckListAll(ZxSfCheck zxSfCheck);
    
    public ResponseEntity updateZxSfCheckIsReportedCompany(ZxSfCheck zxSfCheck);
    
    public ResponseEntity updateZxSfCheckCheckAgainStatusCompany(ZxSfCheck zxSfCheck);
    
    public ResponseEntity getZxSfCheckListAllCompany(ZxSfCheck zxSfCheck);
    
    public ResponseEntity updateZxSfCheckIsSendCompany(ZxSfCheck zxSfCheck);
    
    public ResponseEntity updateZxSfCheckAgainStatusBureau(ZxSfCheck zxSfCheck);
    
    public ResponseEntity getZxSfCheckListAllBureau(ZxSfCheck zxSfCheck);
    
    public ResponseEntity updateZxSfCheckIsSendBureau(ZxSfCheck zxSfCheck);


    /**
     * 查询公司
     * @suncg
     * @param zxSfCheck
     * */
    public ResponseEntity getCompany(ZxSfCheck zxSfCheck);
    /**
     * 查询项目（根据状态分组）
     * @suncg
     * @param zxSfCheck
     * */
    public ResponseEntity getCheckList(ZxSfCheck zxSfCheck);

    /**
     * 查询归档项目列表
     * @suncg
     * @param zxSfCheck
     * */
    public ResponseEntity getGuiDangList(ZxSfCheck zxSfCheck);

    /**
     * 查询交工项目列表
     * @suncg
     * @param zxSfCheck
     * */

    public ResponseEntity getJiaoGongList(ZxSfCheck zxSfCheck);

    /**
     * 查询完工项目列表
     * @suncg
     * @param zxSfCheck
     * */
    public ResponseEntity getWanGongList(ZxSfCheck zxSfCheck);

    /**
     * 查询开工项目列表
     * @suncg
     * @param zxSfCheck
     * */
    public ResponseEntity getKaiGongList(ZxSfCheck zxSfCheck);

    /**
     * 查询局下属公司列表
     * @suncg
     * */
    public ResponseEntity getComList(ZxSfCheck zxSfCheck);


    public List<ZxSfCheck> getZxSfCheckListForm(ZxSfCheck zxSfCheck);

    public ResponseEntity getFormZxSfCheckList(ZxSfCheck zxSfCheck);

    public ResponseEntity getFormJuInfo(ZxSfCheck zxSfCheck);


}


