package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysFlowUser;

public interface SysFlowUserMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysFlowUser record);

    int insertSelective(SysFlowUser record);

    SysFlowUser selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysFlowUser record);

    int updateByPrimaryKey(SysFlowUser record);

    List<SysFlowUser> selectBySysFlowUserList(SysFlowUser record);

    int batchDeleteUpdateSysFlowUser(List<SysFlowUser> recordList, SysFlowUser record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    int deleteUpdateSysFlowUser(SysFlowUser record);
}
