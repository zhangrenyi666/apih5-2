package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseFlowCode;

public interface BaseFlowCodeMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseFlowCode record);

    int insertSelective(BaseFlowCode record);

    BaseFlowCode selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseFlowCode record);

    int updateByPrimaryKey(BaseFlowCode record);

    List<BaseFlowCode> selectByBaseFlowCodeList(BaseFlowCode record);

    int batchDeleteUpdateBaseFlowCode(List<BaseFlowCode> recordList);
    
    List<BaseFlowCode> selectByFlowId(String key);
    
    int updateByFlowId(BaseFlowCode record);
    
    List<BaseFlowCode> selectGroupByFlowIdList(BaseFlowCode record);
    

}

