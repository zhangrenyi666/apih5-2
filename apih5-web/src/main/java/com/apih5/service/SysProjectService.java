package com.apih5.service;

import java.util.List;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysProject;

public interface SysProjectService {

    public ResponseEntity getSysProjectListByCondition(SysProject sysProject);

    public ResponseEntity getSysProjectDetail(SysProject sysProject);

    public ResponseEntity saveSysProject(SysProject sysProject);

    public ResponseEntity updateSysProject(SysProject sysProject);

    public ResponseEntity batchDeleteUpdateSysProject(List<SysProject> sysProjectList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 新增时关联部门表
     * 
     * @param sysProject
     * @return
     */
    public ResponseEntity saveSysProjectByRelation(SysProject sysProject);

    /**
     * 修改时关联部门表
     * 
     * @param sysProject
     * @return
     */
    public ResponseEntity updateSysProjectByRelation(SysProject sysProject);
    
    /**
     * 主体完工的相关更新
     * 
     * @param sysProject
     * @return
     */
    public ResponseEntity updateSysProjectByMainFinish(SysProject sysProject);
    
    /**
     * 业主台账的相关更新
     * 
     * @param sysProject
     * @return
     */
    public ResponseEntity updateSysProjectByContract(SysProject sysProject);
    
    /**
     * 删除时关联部门表
     * @param sysProject
     * @return
     */
    public ResponseEntity batchDeleteUpdateSysProjectByRelation(List<SysProject> sysProjectList);
    // ----查询相关-----------
    /**
     * 新增时关联部门表
     * 
     * @param sysProject
     * @return
     */
    public ResponseEntity getSysPermissionListByProject(SysProject sysProject);

    /**
     * 新增时关联部门表
     * 
     * @param sysProject
     * @return
     */
    public ResponseEntity getSysProjectBySelect(SysProject sysProject);
    
    /**
     * 公司下拉
     * 
     * @param sysProject
     * @return
     */
    public ResponseEntity getSysCompanyBySelect(SysProject sysProject);
    
    /**
     * 同时获取公司及项目下拉数据
     * 
     * @param sysProject
     * @return
     */
    public ResponseEntity getSysCompanyProject(SysProject sysProject);

    /**
     * 新增时关联部门表
     * @param sysProject
     * @return
     */
    public ResponseEntity changeSysProject(SysProject sysProject);
    
//    /**
//     * 项目类型查询
//     * 
//     * @param sysProject
//     * @return
//     */
//    public String getSysProjectByFlowType(SysProject sysProject);

    /**
     * 获取项目树
     * 
     * @param sysDepartment
     * @return 【项目】，并形成树形结构
     */
    public ResponseEntity getSysProjectTree(SysDepartment sysDepartment);
    
    /**
     * 获取项目+人员树
     * 
     * @param sysDepartment
     * @return 【项目+人员】，并形成树形结构
     */
    public ResponseEntity getSysProjectUserTree(SysDepartment sysDepartment);
    
    /**
     * 获取项目+人员树
     * 
     * @param sysDepartment
     * @return 【项目+人员】，并形成树形结构
     */
    public ResponseEntity getSysProjectUserFlowTree(SysDepartment sysDepartment);
    
}
