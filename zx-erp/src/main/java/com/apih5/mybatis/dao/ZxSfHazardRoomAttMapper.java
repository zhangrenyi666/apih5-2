package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfHazardRoomAtt;

public interface ZxSfHazardRoomAttMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfHazardRoomAtt record);

    int insertSelective(ZxSfHazardRoomAtt record);

    ZxSfHazardRoomAtt selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfHazardRoomAtt record);

    int updateByPrimaryKey(ZxSfHazardRoomAtt record);

    List<ZxSfHazardRoomAtt> selectByZxSfHazardRoomAttList(ZxSfHazardRoomAtt record);

    int batchDeleteUpdateZxSfHazardRoomAtt(List<ZxSfHazardRoomAtt> recordList, ZxSfHazardRoomAtt record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSfHazardRoomAtt> selectByZxSfHazardRoomProArea(ZxSfHazardRoomAtt record);
    
    List<ZxSfHazardRoomAtt> selectByZxSfHazardRoomProDoing(ZxSfHazardRoomAtt record);

    List<ZxSfHazardRoomAtt> exportForm(ZxSfHazardRoomAtt record);

    List<ZxSfHazardRoomAtt> exportFormCom(ZxSfHazardRoomAtt record);
}
