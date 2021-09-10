package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfHazardRoom;

public interface ZxSfHazardRoomService {

    public ResponseEntity getZxSfHazardRoomListByCondition(ZxSfHazardRoom zxSfHazardRoom);

    public ResponseEntity getZxSfHazardRoomDetail(ZxSfHazardRoom zxSfHazardRoom);

    public ResponseEntity saveZxSfHazardRoom(ZxSfHazardRoom zxSfHazardRoom);

    public ResponseEntity updateZxSfHazardRoom(ZxSfHazardRoom zxSfHazardRoom);

    public ResponseEntity batchDeleteUpdateZxSfHazardRoom(List<ZxSfHazardRoom> zxSfHazardRoomList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
