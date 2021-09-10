package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfAccessRoom;

public interface ZxSfAccessRoomMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfAccessRoom record);

    int insertSelective(ZxSfAccessRoom record);

    ZxSfAccessRoom selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfAccessRoom record);

    int updateByPrimaryKey(ZxSfAccessRoom record);

    List<ZxSfAccessRoom> selectByZxSfAccessRoomList(ZxSfAccessRoom record);

    int batchDeleteUpdateZxSfAccessRoom(List<ZxSfAccessRoom> recordList, ZxSfAccessRoom record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSfAccessRoom> selectZxSfAccessRoomInit();
}
