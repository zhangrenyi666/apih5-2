package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxBuYgjLiveStandard;

public interface ZxBuYgjLiveStandardMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxBuYgjLiveStandard record);

    int insertSelective(ZxBuYgjLiveStandard record);

    ZxBuYgjLiveStandard selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxBuYgjLiveStandard record);

    int updateByPrimaryKey(ZxBuYgjLiveStandard record);

    List<ZxBuYgjLiveStandard> selectByZxBuYgjLiveStandardList(ZxBuYgjLiveStandard record);

    int batchDeleteUpdateZxBuYgjLiveStandard(List<ZxBuYgjLiveStandard> recordList, ZxBuYgjLiveStandard record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxBuYgjLiveStandard> selectByZxBuYgjLiveStandardListData(ZxBuYgjLiveStandard record);

    int updateZxBuYgjLiveStandard(ZxBuYgjLiveStandard updateZxBuYgjLiveStandard);


}
