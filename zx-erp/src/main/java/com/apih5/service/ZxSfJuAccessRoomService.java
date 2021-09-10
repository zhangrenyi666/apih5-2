package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfJuAccessRoom;

public interface ZxSfJuAccessRoomService {

    public ResponseEntity getZxSfJuAccessRoomListByCondition(ZxSfJuAccessRoom zxSfJuAccessRoom);

    public ResponseEntity getZxSfJuAccessRoomDetail(ZxSfJuAccessRoom zxSfJuAccessRoom);

    public ResponseEntity saveZxSfJuAccessRoom(ZxSfJuAccessRoom zxSfJuAccessRoom);

    public ResponseEntity updateZxSfJuAccessRoom(ZxSfJuAccessRoom zxSfJuAccessRoom);

    public ResponseEntity batchDeleteUpdateZxSfJuAccessRoom(List<ZxSfJuAccessRoom> zxSfJuAccessRoomList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
