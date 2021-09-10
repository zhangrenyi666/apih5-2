package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfHazardRoomAtt;

public interface ZxSfHazardRoomAttService {

    public ResponseEntity getZxSfHazardRoomAttListByCondition(ZxSfHazardRoomAtt zxSfHazardRoomAtt);

    public ResponseEntity getZxSfHazardRoomAttDetail(ZxSfHazardRoomAtt zxSfHazardRoomAtt);

    public ResponseEntity saveZxSfHazardRoomAtt(ZxSfHazardRoomAtt zxSfHazardRoomAtt);

    public ResponseEntity updateZxSfHazardRoomAtt(ZxSfHazardRoomAtt zxSfHazardRoomAtt);

    public ResponseEntity batchDeleteUpdateZxSfHazardRoomAtt(List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSfHazardRoomAtt> getZxSfHazardRoomListByCondition(ZxSfHazardRoomAtt zxSfHazardRoomAtt);
    
    public ResponseEntity getZxSfHazardRoomAttProArea(ZxSfHazardRoomAtt zxSfHazardRoomAtt);
    
    public ResponseEntity getZxSfHazardRoomAttDoing(ZxSfHazardRoomAtt zxSfHazardRoomAtt);
    
    public ResponseEntity getZxSfHazardRoomAttRiskFactors(ZxSfHazardRoomAtt zxSfHazardRoomAtt);

    public ResponseEntity getUreportFormList(ZxSfHazardRoomAtt zxSfHazardRoomAtt);

    public List<ZxSfHazardRoomAtt> UreportForm(ZxSfHazardRoomAtt zxSfHazardRoomAtt);


    /**
     * 展示危险源台账（公司）报表查询
     * @author suncg
     * @param zxSfHazardRoomAtt
     * */

    public ResponseEntity getUreportFormComList(ZxSfHazardRoomAtt zxSfHazardRoomAtt);


    /**
     * 导出危险源台账（公司）报表查询
     * @author suncg
     * @param zxSfHazardRoomAtt
     * */

    public List<ZxSfHazardRoomAtt> UreportFormCom(ZxSfHazardRoomAtt zxSfHazardRoomAtt);


}
