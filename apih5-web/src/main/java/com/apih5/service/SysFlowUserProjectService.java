package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysFlowUserProject;

public interface SysFlowUserProjectService {

    public ResponseEntity getSysFlowUserProjectListByCondition(SysFlowUserProject sysFlowUserProject);

    public ResponseEntity getSysFlowUserProjectDetail(SysFlowUserProject sysFlowUserProject);

    public ResponseEntity saveSysFlowUserProject(SysFlowUserProject sysFlowUserProject);

    public ResponseEntity updateSysFlowUserProject(SysFlowUserProject sysFlowUserProject);

    public ResponseEntity batchDeleteUpdateSysFlowUserProject(List<SysFlowUserProject> sysFlowUserProjectList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    /**
     * 项目类型查询
     * 
     * @param sysFlowUserProject
     * @return
     */
    public String getSysFlowUserProjectType(SysFlowUserProject sysFlowUserProject);
    
    public ResponseEntity addSysFlowUserProjectByFlow(SysFlowUserProject sysFlowUserProject);
}
