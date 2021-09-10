package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfJuAccessRoom;

public interface ZxSfJuAccessRoomMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfJuAccessRoom record);

    int insertSelective(ZxSfJuAccessRoom record);

    ZxSfJuAccessRoom selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfJuAccessRoom record);

    int updateByPrimaryKey(ZxSfJuAccessRoom record);

    List<ZxSfJuAccessRoom> selectByZxSfJuAccessRoomList(ZxSfJuAccessRoom record);

    int batchDeleteUpdateZxSfJuAccessRoom(List<ZxSfJuAccessRoom> recordList, ZxSfJuAccessRoom record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
