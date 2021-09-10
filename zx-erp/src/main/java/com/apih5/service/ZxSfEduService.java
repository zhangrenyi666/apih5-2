package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfEdu;

public interface ZxSfEduService {

    public ResponseEntity getZxSfEduListByCondition(ZxSfEdu zxSfEdu);

    public ResponseEntity getZxSfEduDetail(ZxSfEdu zxSfEdu);

    public ResponseEntity saveZxSfEdu(ZxSfEdu zxSfEdu);

    public ResponseEntity updateZxSfEdu(ZxSfEdu zxSfEdu);

    public ResponseEntity batchDeleteUpdateZxSfEdu(List<ZxSfEdu> zxSfEduList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public ResponseEntity getZxSfEduComList();

    public ResponseEntity getZxSfEduCom(ZxSfEdu zxSfEdu);

    public ResponseEntity getZxSfEduOrgList(ZxSfEdu zxSfEdu);

    public ResponseEntity getZxSfEduGuiDangList(ZxSfEdu zxSfEdu);

    public ResponseEntity getZxSfEduJiaoGongList(ZxSfEdu zxSfEdu);

    public ResponseEntity getZxSfEduWanGongList(ZxSfEdu zxSfEdu);

    public ResponseEntity getZxSfEduKaiGongList(ZxSfEdu zxSfEdu);

    public ResponseEntity getZxSfEduJuInfo(ZxSfEdu zxSfEdu);
}
