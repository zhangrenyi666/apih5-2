package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehVideo;

public interface ZjLzehVideoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehVideo record);

    int insertSelective(ZjLzehVideo record);

    ZjLzehVideo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehVideo record);

    int updateByPrimaryKey(ZjLzehVideo record);

    List<ZjLzehVideo> selectByZjLzehVideoList(ZjLzehVideo record);

    int batchDeleteUpdateZjLzehVideo(List<ZjLzehVideo> recordList, ZjLzehVideo record);

    ZjLzehVideo selectByZjLzehVideo(ZjLzehVideo record);

    void updateZjLzehVideoValue0();

    int updateZjLzehVideoValue1(ZjLzehVideo record);


}

