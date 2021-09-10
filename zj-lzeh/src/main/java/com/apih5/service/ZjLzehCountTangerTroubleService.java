package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehCountTangerTrouble;

public interface ZjLzehCountTangerTroubleService {

    public ResponseEntity getZjLzehCountTangerTroubleListByCondition(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble);

    public ResponseEntity getZjLzehCountTangerTroubleDetail(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble);

    public ResponseEntity saveZjLzehCountTangerTrouble(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble);

    public ResponseEntity updateZjLzehCountTangerTrouble(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble);

    public ResponseEntity batchDeleteUpdateZjLzehCountTangerTrouble(List<ZjLzehCountTangerTrouble> zjLzehCountTangerTroubleList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity selectTroubleCountInfo(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble);

    public ResponseEntity selectDangerCountInfo(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble);
}
