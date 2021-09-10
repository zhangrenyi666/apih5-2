package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTeamScore;

public interface ZjLzehTeamScoreMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTeamScore record);

    int insertSelective(ZjLzehTeamScore record);

    ZjLzehTeamScore selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTeamScore record);

    int updateByPrimaryKey(ZjLzehTeamScore record);

    List<ZjLzehTeamScore> selectByZjLzehTeamScoreList(ZjLzehTeamScore record);

    int batchDeleteUpdateZjLzehTeamScore(List<ZjLzehTeamScore> recordList, ZjLzehTeamScore record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
