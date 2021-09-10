package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzProRebuyInfo;

public interface ZjTzProRebuyInfoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzProRebuyInfo record);

    int insertSelective(ZjTzProRebuyInfo record);

    ZjTzProRebuyInfo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzProRebuyInfo record);

    int updateByPrimaryKey(ZjTzProRebuyInfo record);

    List<ZjTzProRebuyInfo> selectByZjTzProRebuyInfoList(ZjTzProRebuyInfo record);

    int batchDeleteUpdateZjTzProRebuyInfo(List<ZjTzProRebuyInfo> recordList, ZjTzProRebuyInfo record);

}

