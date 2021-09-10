package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTeamScoreItem;

public interface ZjLzehTeamScoreItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTeamScoreItem record);

    int insertSelective(ZjLzehTeamScoreItem record);

    ZjLzehTeamScoreItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTeamScoreItem record);

    int updateByPrimaryKey(ZjLzehTeamScoreItem record);

    List<ZjLzehTeamScoreItem> selectByZjLzehTeamScoreItemList(ZjLzehTeamScoreItem record);

    int batchDeleteUpdateZjLzehTeamScoreItem(List<ZjLzehTeamScoreItem> recordList, ZjLzehTeamScoreItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZjLzehTeamScoreItem> getScoreInfo(ZjLzehTeamScoreItem record);

    List<ZjLzehTeamScoreItem> getAvgScoreInfo(ZjLzehTeamScoreItem record);

    List<ZjLzehTeamScoreItem> getInfoListByTeamScoreId(ZjLzehTeamScoreItem record);

    List<ZjLzehTeamScoreItem> getInfoListByMonth(ZjLzehTeamScoreItem record);

    List<ZjLzehTeamScoreItem> getChartByMonth(ZjLzehTeamScoreItem record);

}
