package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfHazardRoom;

public interface ZxSfHazardRoomMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfHazardRoom record);

    int insertSelective(ZxSfHazardRoom record);

    ZxSfHazardRoom selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfHazardRoom record);

    int updateByPrimaryKey(ZxSfHazardRoom record);

    List<ZxSfHazardRoom> selectByZxSfHazardRoomList(ZxSfHazardRoom record);

    int batchDeleteUpdateZxSfHazardRoom(List<ZxSfHazardRoom> recordList, ZxSfHazardRoom record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
