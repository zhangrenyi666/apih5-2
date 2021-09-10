package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfAccessRoom;

public interface ZxSfAccessRoomService {

    public ResponseEntity getZxSfAccessRoomListByCondition(ZxSfAccessRoom zxSfAccessRoom);

    public ResponseEntity getZxSfAccessRoomDetail(ZxSfAccessRoom zxSfAccessRoom);

    public ResponseEntity saveZxSfAccessRoom(ZxSfAccessRoom zxSfAccessRoom);

    public ResponseEntity updateZxSfAccessRoom(ZxSfAccessRoom zxSfAccessRoom);

    public ResponseEntity batchDeleteUpdateZxSfAccessRoom(List<ZxSfAccessRoom> zxSfAccessRoomList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    public List<ZxSfAccessRoom> getZxSfAccessRoomInit();
}
