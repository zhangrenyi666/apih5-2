package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTaskCensusItem;

public interface ZjLzehTaskCensusItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTaskCensusItem record);

    int insertSelective(ZjLzehTaskCensusItem record);

    ZjLzehTaskCensusItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTaskCensusItem record);

    int updateByPrimaryKey(ZjLzehTaskCensusItem record);

    List<ZjLzehTaskCensusItem> selectByZjLzehTaskCensusItemList(ZjLzehTaskCensusItem record);

    int batchDeleteUpdateZjLzehTaskCensusItem(List<ZjLzehTaskCensusItem> recordList, ZjLzehTaskCensusItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZjLzehTaskCensusItem> selectTaskCensusItemByMonth (ZjLzehTaskCensusItem record);

    /**
     * 查询临时任务任务明细图表
     * @author suncg
     * @param record
     * */
    List<ZjLzehTaskCensusItem> selectChartInfoByID(ZjLzehTaskCensusItem record);

    List<ZjLzehTaskCensusItem> selectChartByCenMonth(ZjLzehTaskCensusItem record);

    List<ZjLzehTaskCensusItem> selectAvgCRateByCenMonth(ZjLzehTaskCensusItem record);

}
