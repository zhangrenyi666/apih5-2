package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.CheckSfCheck;

public interface CheckSfCheckService {

    public ResponseEntity getCheckSfCheckListByCondition(CheckSfCheck checkSfCheck);

    public ResponseEntity getCheckSfCheckDetail(CheckSfCheck checkSfCheck);

    public ResponseEntity saveCheckSfCheck(CheckSfCheck checkSfCheck);

    public ResponseEntity updateCheckSfCheck(CheckSfCheck checkSfCheck);

    public ResponseEntity batchDeleteUpdateCheckSfCheck(List<CheckSfCheck> checkSfCheckList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 查询公司
     * @suncg
     * @param checkSfCheck
     * */
    public ResponseEntity getCompany(CheckSfCheck checkSfCheck);
    /**
     * 查询项目（根据状态分组）
     * @suncg
     * @param checkSfCheck
     * */
    public ResponseEntity getCheckList(CheckSfCheck checkSfCheck);

    public ResponseEntity getGuiDangList(CheckSfCheck checkSfCheck);

    public ResponseEntity getJiaoGongList(CheckSfCheck checkSfCheck);

    public ResponseEntity getWanGongList(CheckSfCheck checkSfCheck);

    public ResponseEntity getKaiGongList(CheckSfCheck checkSfCheck);

    public ResponseEntity getComList(CheckSfCheck checkSfCheck);



}
