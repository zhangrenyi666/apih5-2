package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehCountTangerTrouble;

public interface ZjLzehCountTangerTroubleMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehCountTangerTrouble record);

    int insertSelective(ZjLzehCountTangerTrouble record);

    ZjLzehCountTangerTrouble selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehCountTangerTrouble record);

    int updateByPrimaryKey(ZjLzehCountTangerTrouble record);

    List<ZjLzehCountTangerTrouble> selectByZjLzehCountTangerTroubleList(ZjLzehCountTangerTrouble record);

    int batchDeleteUpdateZjLzehCountTangerTrouble(List<ZjLzehCountTangerTrouble> recordList, ZjLzehCountTangerTrouble record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZjLzehCountTangerTrouble> selectTroubleCountInfo(ZjLzehCountTangerTrouble record);

    List<ZjLzehCountTangerTrouble> selectDangerCountInfo(ZjLzehCountTangerTrouble record);

}
