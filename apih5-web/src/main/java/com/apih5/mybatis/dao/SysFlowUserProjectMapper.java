package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysFlowUserProject;

public interface SysFlowUserProjectMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysFlowUserProject record);

    int insertSelective(SysFlowUserProject record);

    SysFlowUserProject selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysFlowUserProject record);

    int updateByPrimaryKey(SysFlowUserProject record);

    List<SysFlowUserProject> selectBySysFlowUserProjectList(SysFlowUserProject record);

    int batchDeleteUpdateSysFlowUserProject(List<SysFlowUserProject> recordList, SysFlowUserProject record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
