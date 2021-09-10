package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTempTaskCommunicate;

public interface ZjLzehTempTaskCommunicateMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTempTaskCommunicate record);

    int insertSelective(ZjLzehTempTaskCommunicate record);

    ZjLzehTempTaskCommunicate selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTempTaskCommunicate record);

    int updateByPrimaryKey(ZjLzehTempTaskCommunicate record);

    List<ZjLzehTempTaskCommunicate> selectByZjLzehTempTaskCommunicateList(ZjLzehTempTaskCommunicate record);

    int batchDeleteUpdateZjLzehTempTaskCommunicate(List<ZjLzehTempTaskCommunicate> recordList, ZjLzehTempTaskCommunicate record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
