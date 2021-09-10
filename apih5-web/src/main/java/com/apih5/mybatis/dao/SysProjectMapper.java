package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysProject;

public interface SysProjectMapper {
    int deleteByPrimaryKey(String key);

    int insert(SysProject record);

    int insertSelective(SysProject record);

    SysProject selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SysProject record);

    int updateByPrimaryKey(SysProject record);

    List<SysProject> selectBySysProjectList(SysProject record);

    int batchDeleteUpdateSysProject(List<SysProject> recordList, SysProject record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<SysProject> selectBySysProjectListByUserKey(SysProject record);

    List<SysProject> selectBySysProjectCompanyListByUser(SysProject record);
    
    List<SysProject> getSysCompanyProject(SysProject record);
}
