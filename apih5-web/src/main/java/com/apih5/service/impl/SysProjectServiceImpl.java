package com.apih5.service.impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.BaseCode;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TreeNodeEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseProjectConfJobMapper;
import com.apih5.mybatis.dao.BaseProjectConfMapper;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysFilesMapper;
import com.apih5.mybatis.dao.SysFlowUserMapper;
import com.apih5.mybatis.dao.SysProjectDeptConfMapper;
import com.apih5.mybatis.dao.SysProjectMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.mybatis.pojo.BaseProjectConf;
import com.apih5.mybatis.pojo.BaseProjectConfJob;
import com.apih5.mybatis.pojo.SysFlowUser;
import com.apih5.mybatis.pojo.SysProject;
import com.apih5.mybatis.pojo.SysProjectDeptConf;
import com.apih5.service.SysProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("sysProjectService")
public class SysProjectServiceImpl implements SysProjectService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysProjectMapper sysProjectMapper;
    
    @Autowired(required = true)
    private SysDepartmentMapper sysDepartmentMapper;
    
    @Autowired(required = true)
    private SysFilesMapper sysFilesMapper;
    
    @Autowired(required = true)
    private SysUserMapper sysUserMapper;
    
    @Autowired(required = true)
    private SysFlowUserMapper sysFlowUserMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private BaseProjectConfMapper baseProjectConfMapper;
    
    @Autowired(required = true)
    private BaseProjectConfJobMapper baseProjectConfJobMapper;
    
    @Autowired(required = true)
    private SysProjectDeptConfMapper sysProjectDeptConfMapper;
    
    @Override
    public ResponseEntity getSysProjectListByCondition(SysProject sysProject) {
        if (sysProject == null) {
            sysProject = new SysProject();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("admin", userId)) {
            sysProject.setDepartmentId("");
            sysProject.setCompanyId("");
        } else if(StrUtil.equals("2", ext1)) {
            // 公司只看见自己的
            sysProject.setCompanyId(sysProject.getDepartmentId());
            sysProject.setDepartmentId("");
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            // 项目通过右上角数据
            sysProject.setDepartmentId(sysProject.getDepartmentId());
        }

        // 分页查询
        PageHelper.startPage(sysProject.getPage(),sysProject.getLimit());
        
        // 获取数据
        sysProject.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_PRO);
        List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProject);
        if(sysProjectList != null && sysProjectList.size()>0) {
            for(SysProject dbSysProject:sysProjectList) {
                // 所属公司设置
                if(StrUtil.isNotEmpty(dbSysProject.getCompanyId()) 
                        && StrUtil.isNotEmpty(dbSysProject.getCompanyConf())) {
                    JSONArray companyJSONArray = new JSONArray(dbSysProject.getCompanyConf());
                    dbSysProject.setCompanyJSONArray(companyJSONArray);
                }
                // 托管
                if(StrUtil.isNotEmpty(dbSysProject.getDelegateId()) 
                        && StrUtil.isNotEmpty(dbSysProject.getDelegateConf())) {
                    JSONArray delegateJSONArray = new JSONArray(dbSysProject.getDelegateConf());
                    dbSysProject.setDelegateJSONArray(delegateJSONArray);
                }
                // 项目配置
                if(StrUtil.isNotEmpty(dbSysProject.getConfUp())) {
                    dbSysProject.setConfUpJSONArray(new JSONArray(dbSysProject.getConfUp()));
                }
                if(StrUtil.isNotEmpty(dbSysProject.getConfDown())) {
                    dbSysProject.setConfDownJSONArray(new JSONArray(dbSysProject.getConfDown()));
                }
                
//                // 所有附件
//                SysFiles sysFilesSelect = new SysFiles();
//                sysFilesSelect.setOtherId(dbSysProject.getDepartmentId());
//                List<SysFiles> sysFileList = sysFilesMapper.selectBySysFilesList(sysFilesSelect);
//                dbSysProject.setSysFileList(sysFileList);
            }
        }
        
        // 得到分页信息
        PageInfo<SysProject> p = new PageInfo<>(sysProjectList);

        return repEntity.okList(sysProjectList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysProjectDetail(SysProject sysProject) {
        if (sysProject == null) {
            sysProject = new SysProject();
        }
        // 获取数据
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
        // 数据存在
        if (dbSysProject != null) {
            return repEntity.ok(dbSysProject);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveSysProject(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysProject.setDepartmentId(UuidUtil.generate());
        sysProject.setCreateUserInfo(userKey, realName);
        int flag = sysProjectMapper.insert(sysProject);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", sysProject);
        }
    }

    @Override
    public ResponseEntity updateSysProject(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
        if (dbSysProject != null && StrUtil.isNotEmpty(dbSysProject.getDepartmentId())) {
         // 项目简称
            dbSysProject.setDepartmentName(sysProject.getDepartmentName());
            // 项目全程
            dbSysProject.setDepartmentFullName(sysProject.getDepartmentFullName());
            // 项目父ID
            dbSysProject.setDepartmentParentId(sysProject.getDepartmentParentId());
            // 项目路径
            dbSysProject.setDepartmentPath(sysProject.getDepartmentPath());
            // 项目路径名称
            dbSysProject.setDepartmentPathName(sysProject.getDepartmentPathName());
            // 项目标识（机构类型）
            dbSysProject.setDepartmentFlag(sysProject.getDepartmentFlag());
            // 项目标识（机构类型）
            dbSysProject.setDepartmentFlagName(sysProject.getDepartmentFlagName());
            // 所属公司
            dbSysProject.setCompanyId(sysProject.getCompanyId());
            // 公司项目表
            dbSysProject.setCompanyConf(sysProject.getCompanyConf());
            // 所属公司
            dbSysProject.setCompanyName(sysProject.getCompanyName());
            // 所属项目
            dbSysProject.setProjectId(sysProject.getProjectId());
            // 所属项目
            dbSysProject.setProjectName(sysProject.getProjectName());
            // 其他id（如微信等）
            dbSysProject.setOtherId(sysProject.getOtherId());
            // 项目表文件up
            dbSysProject.setConfUp(sysProject.getConfUp());
            // 项目表文件down
            dbSysProject.setConfDown(sysProject.getConfDown());
            // 是否显示下一层
            dbSysProject.setConfDownShowFlag(sysProject.getConfDownShowFlag());
            // 项目拼音
            dbSysProject.setProjectPinyin(sysProject.getProjectPinyin());
            // 承建项目类型
            dbSysProject.setContractorType(sysProject.getContractorType());
            // 是否局直属
            dbSysProject.setJuFlag(sysProject.getJuFlag());
            // 局直属项目名称
            dbSysProject.setJuName(sysProject.getJuName());
            // 项目施工单位
            dbSysProject.setLocationInDeprFormula(sysProject.getLocationInDeprFormula());
            // 承建单位简称
            dbSysProject.setContractor(sysProject.getContractor());
            // 中标资质
            dbSysProject.setBiddingQualification(sysProject.getBiddingQualification());
            // 所属事业部
            dbSysProject.setBizDep(sysProject.getBizDep());
            // 所属区域
            dbSysProject.setSubArea(sysProject.getSubArea());
            // 项目所在地
            dbSysProject.setProjectLocation(sysProject.getProjectLocation());
            // 项目所在省份简称
            dbSysProject.setProvinceAbbreviation(sysProject.getProvinceAbbreviation());
            // 市级
            dbSysProject.setCityName(sysProject.getCityName());
            // 标段号
            dbSysProject.setLotNo(sysProject.getLotNo());
            // 工程类别
            dbSysProject.setProjType(sysProject.getProjType());
            // 项目性质
            dbSysProject.setProjectProperty(sysProject.getProjectProperty());
            // 项目特征
            dbSysProject.setProDescribe(sysProject.getProDescribe());
            // 主要工程项目及工程数量
            dbSysProject.setDetail(sysProject.getDetail());
            // 开工日期
            dbSysProject.setActualStartDate(sysProject.getActualStartDate());
            // 主体完工日期
            dbSysProject.setMainEndDate(sysProject.getMainEndDate());
            // 交工日期
            dbSysProject.setDeliveryDate(sysProject.getDeliveryDate());
            // 实际竣工日期
            dbSysProject.setActualEndDate(sysProject.getActualEndDate());
            // 归档日期
            dbSysProject.setDocDate(sysProject.getDocDate());
            // 项目表up数组
            dbSysProject.setConfUpJSONArray(sysProject.getConfUpJSONArray());
            // 项目表down数组
            dbSysProject.setConfDownJSONArray(sysProject.getConfDownJSONArray());
            // 所属公司
            dbSysProject.setCompanyJSONArray(sysProject.getCompanyJSONArray());
            // userKey
            dbSysProject.setUserKey(sysProject.getUserKey());
            // 项目经理姓名
            dbSysProject.setProjectChiefName(sysProject.getProjectChiefName());
            // 项目承包模式
            dbSysProject.setContractingType(sysProject.getContractingType());
            // 项目经理电话
            dbSysProject.setProjectChiefTel(sysProject.getProjectChiefTel());
            // 备注
            dbSysProject.setRemarks(sysProject.getRemarks());
            // 排序
            dbSysProject.setSort(sysProject.getSort());
            // 共通
            dbSysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.updateByPrimaryKey(dbSysProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysProject);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysProject(List<SysProject> sysProjectList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysProjectList != null && sysProjectList.size() > 0) {
           SysProject sysProject = new SysProject();
           sysProject.setModifyUserInfo(userKey, realName);
           flag = sysProjectMapper.batchDeleteUpdateSysProject(sysProjectList, sysProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",sysProjectList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 新增时关联部门表
     * @param sysProject
     * @return
     */
    @Override
    public ResponseEntity saveSysProjectByRelation(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysProject.setDepartmentId(UuidUtil.generate());

        // 所属公司
        JSONArray companyJSONArray = sysProject.getCompanyJSONArray();
        if(companyJSONArray != null && companyJSONArray.size()>0) {
            JSONObject jsonObject = companyJSONArray.getJSONObject(0);
            jsonObject.remove("showData");
            jsonObject.remove("_key");
            jsonObject.remove("title");
            jsonObject.remove("children");
            jsonObject.remove("loading");
            sysProject.setCompanyConf(companyJSONArray.toString());
            // 获取公司ID
            SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(jsonObject.getStr("value"));
            sysProject.setCompanyId(sysDepartment.getCompanyId());
            sysProject.setCompanyName(sysDepartment.getCompanyName());
            // path
            String path = sysDepartment.getDepartmentPath() + "," + sysProject.getDepartmentId();
            String pathName = sysDepartment.getDepartmentPathName() + "," + sysProject.getDepartmentName();
            sysProject.setDepartmentPath(path);
            sysProject.setDepartmentPathName(pathName);
            // 当前上级pid
            sysProject.setDepartmentParentId(jsonObject.getStr("value"));
        }
        
        // 托管
        JSONArray delegateJSONArray = sysProject.getDelegateJSONArray();
        if(delegateJSONArray != null && delegateJSONArray.size()>0) {
            JSONObject jsonObject = delegateJSONArray.getJSONObject(0);
            jsonObject.remove("showData");
            jsonObject.remove("_key");
            jsonObject.remove("title");
            jsonObject.remove("children");
            jsonObject.remove("loading");
            sysProject.setDelegateId(jsonObject.getStr("value"));
            sysProject.setDelegateName(jsonObject.getStr("label"));
            sysProject.setDelegateConf(delegateJSONArray.toString());
        }

        // 解析前端传来的confUp，更新部门表或项目表的confDown字段
        JSONArray confUpJSONArray = sysProject.getConfUpJSONArray();
        if(confUpJSONArray != null && confUpJSONArray.size()>0) {
            JSONObject jsonObject = confUpJSONArray.getJSONObject(0);
            jsonObject.remove("showData");
            jsonObject.remove("_key");
            jsonObject.remove("title");
            jsonObject.remove("children");
            jsonObject.remove("loading");
            sysProject.setHeadquartersId(jsonObject.getStr("value"));
            sysProject.setHeadquartersName(jsonObject.getStr("label"));
            sysProject.setHeadquartersConf(delegateJSONArray.toString());
            // 总部的时候看看是否有托管
            SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(jsonObject.getStr("value"));
            if(dbSysProject != null) {
                sysProject.setDelegateId(dbSysProject.getDepartmentId());
                sysProject.setDelegateName(dbSysProject.getDepartmentName());
            }
        }

        sysProject.setConfUp(confUpJSONArray.toString());
        confUpJSONArray = confUpJSONArray == null ? new JSONArray() : confUpJSONArray;
        confUpJSONArray.addAll(companyJSONArray);
        
        // 快速去重
        List<TreeNodeEntity> confUpList = JSONUtil.toList(confUpJSONArray, TreeNodeEntity.class);
        Set<TreeNodeEntity> set = new TreeSet<>(
        Comparator.comparing(TreeNodeEntity::getValue));
        set.addAll(confUpList);
        confUpList.clear();
        confUpList.addAll(set);
        confUpJSONArray = JSONUtil.parseArray(confUpList);

        if(confUpJSONArray != null && confUpJSONArray.size()>0) {
            for (Iterator<Object> iterator = confUpJSONArray.iterator(); iterator.hasNext();) {
                JSONObject jsonObject = (JSONObject)iterator.next();
                String departmentId = jsonObject.getStr("value");
//                String tableFlag = jsonObject.getStr("tableFlag");
                jsonObject.remove("showData");
                jsonObject.remove("_key");
                jsonObject.remove("title");
                jsonObject.remove("children");
                jsonObject.remove("loading");
                // 保存confDown内容到department表或project表
                updateConfDown(sysProject.getDepartmentId(), departmentId);
            }
        }
        sysProject.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_PRO);
        sysProject.setDepartmentFlagName("项目");
        sysProject.setProjectId(sysProject.getDepartmentId());
        sysProject.setProjectName(sysProject.getDepartmentName());
        
        
        // 工程类型时，决定了当前部门内容
        BaseCode baseCode = new BaseCode();
        baseCode.setItemId("xiangMuBuMen");
        ResponseEntity baseResponseEntity = baseCodeService.getBaseCodeTree(baseCode);
        if(baseResponseEntity.isSuccess()) {
            JSONArray jsonArray = new JSONArray(baseResponseEntity.getData());
            if(jsonArray != null && jsonArray.size()>0) {
                for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
                    JSONObject jsonObject = (JSONObject)iterator.next();
                    String itemId = jsonObject.getStr("itemId");
                    // 工程类型相同时
                    if(StrUtil.equals(sysProject.getProjType(), itemId)) {
                        JSONArray deptJSONArray = jsonObject.getJSONArray("children");
                        // 部门信息
                        if(deptJSONArray != null && deptJSONArray.size()>0) {
                            for (Iterator<Object> deptIterator = deptJSONArray.iterator(); deptIterator.hasNext();) {
                                JSONObject deptJsonObject = (JSONObject)deptIterator.next();
                                // 工程类型、项目级别、部门内容决定当前的数量
                                BaseProjectConf baseProjectConf = new BaseProjectConf();
                                baseProjectConf.setProjType(sysProject.getProjType());
                                baseProjectConf.setProLevel(sysProject.getProLevel());
                                baseProjectConf.setDepartmentName(deptJsonObject.getStr("itemName"));
                                List<BaseProjectConf> baseProjectConfList = baseProjectConfMapper.selectByBaseProjectConfList(baseProjectConf);
                                SysProject sysProjectDept = new SysProject();
                                if(baseProjectConfList != null && baseProjectConfList.size()>0) {
                                    sysProjectDept.setDepartmentNum(baseProjectConfList.get(0).getDepartmentNum());
                                }
                                sysProjectDept.setDepartmentId(IdUtil.getSnowflake(1, 1).nextIdStr());
                                sysProjectDept.setDepartmentName(deptJsonObject.getStr("itemName"));
                                sysProjectDept.setDepartmentParentId(sysProject.getDepartmentId());
                                sysProjectDept.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_DEPT);
                                sysProjectDept.setDepartmentFlagName("部门");
                                sysProjectDept.setDepartmentPath(sysProject.getDepartmentPath() + "," + sysProjectDept.getDepartmentId());
                                sysProjectDept.setDepartmentPathName(sysProject.getDepartmentPathName() + "," + sysProjectDept.getDepartmentName());
                                sysProjectDept.setCreateUserInfo(userKey, realName);
                                sysProjectMapper.insert(sysProjectDept);
                                
                                // 查询岗位配置表并且插入数据到项目配置表中
                                BaseProjectConfJob baseProjectConfJobSelect = new BaseProjectConfJob();
                                baseProjectConfJobSelect.setBaseProjectConfId(baseProjectConfList.get(0).getBaseProjectConfId());
                                List<BaseProjectConfJob> baseProjectConfJobList = baseProjectConfJobMapper.selectByBaseProjectConfJobList(baseProjectConfJobSelect);
                                if(baseProjectConfJobList != null && baseProjectConfJobList.size()>0) {
                                    for(BaseProjectConfJob dbBaseProjectConfJob:baseProjectConfJobList) {
                                        SysProjectDeptConf sysProjectDeptConf = new SysProjectDeptConf();
                                        BeanUtil.copyProperties(dbBaseProjectConfJob, sysProjectDeptConf, true);
                                        sysProjectDeptConf.setSysProjectDeptConfId(IdUtil.getSnowflake(1, 1).nextIdStr());
                                        sysProjectDeptConf.setCreateUserInfo(userKey, realName);
                                        sysProjectDeptConfMapper.insert(sysProjectDeptConf);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        
        sysProject.setCreateUserInfo(userKey, realName);
        int flag = sysProjectMapper.insert(sysProject);
        
//        List<SysFiles> sysFileList = sysProject.getSysFileList();
//        if(sysFileList != null && sysFileList.size()>0) {
//            for(SysFiles sysFiles:sysFileList) {
//                sysFiles.setUid(UuidUtil.generate());
//                sysFiles.setOtherId(sysProject.getDepartmentId());
//                sysFiles.setCreateUserInfo(userKey, realName);
//                sysFilesMapper.insert(sysFiles);
//            }
//        }
        
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", sysProject);
        }
    }
    
    @Override
    public ResponseEntity updateSysProjectByRelation(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
        if (dbSysProject != null && StrUtil.isNotEmpty(dbSysProject.getDepartmentId())) {
            // 项目简称
            dbSysProject.setDepartmentName(sysProject.getDepartmentName());
            // 项目全程
            dbSysProject.setDepartmentFullName(sysProject.getDepartmentFullName());
//            // 项目父ID
//            dbSysProject.setDepartmentParentId(sysProject.getDepartmentParentId());
//            // 项目路径
//            dbSysProject.setDepartmentPath(sysProject.getDepartmentPath());
//            // 项目路径名称
//            dbSysProject.setDepartmentPathName(sysProject.getDepartmentPathName());
//            // 项目标识（机构类型）
//            dbSysProject.setDepartmentFlag(sysProject.getDepartmentFlag());
//            // 项目标识（机构类型）
//            dbSysProject.setDepartmentFlagName(sysProject.getDepartmentFlagName());
            // 所属公司
            dbSysProject.setCompanyId(sysProject.getCompanyId());
            // 公司项目表
            dbSysProject.setCompanyConf(sysProject.getCompanyConf());
            // 所属公司
            dbSysProject.setCompanyName(sysProject.getCompanyName());
//            // 所属项目
//            dbSysProject.setProjectId(sysProject.getProjectId());
            // 所属项目
            dbSysProject.setProjectName(sysProject.getDepartmentName());
//            // 其他id（如微信等）
//            dbSysProject.setOtherId(sysProject.getOtherId());
            // 项目表文件up
            dbSysProject.setConfUp(sysProject.getConfUp());
            // 项目表文件down
            dbSysProject.setConfDown(sysProject.getConfDown());
            // 是否显示下一层
            dbSysProject.setConfDownShowFlag(sysProject.getConfDownShowFlag());
            // 项目拼音
            dbSysProject.setProjectPinyin(sysProject.getProjectPinyin());
            // 承建项目类型
            dbSysProject.setContractorType(sysProject.getContractorType());
            // 是否局直属
            dbSysProject.setJuFlag(sysProject.getJuFlag());
            // 局直属项目名称
            dbSysProject.setJuName(sysProject.getJuName());
            // 项目施工单位
            dbSysProject.setLocationInDeprFormula(sysProject.getLocationInDeprFormula());
            // 承建单位简称
            dbSysProject.setContractor(sysProject.getContractor());
            // 中标资质
            dbSysProject.setBiddingQualification(sysProject.getBiddingQualification());
            // 所属事业部
            dbSysProject.setBizDep(sysProject.getBizDep());
            // 所属区域
            dbSysProject.setSubArea(sysProject.getSubArea());
            // 项目所在地
            dbSysProject.setProjectLocation(sysProject.getProjectLocation());
            // 项目所在省份简称
            dbSysProject.setProvinceAbbreviation(sysProject.getProvinceAbbreviation());
            // 市级
            dbSysProject.setCityName(sysProject.getCityName());
            // 标段号
            dbSysProject.setLotNo(sysProject.getLotNo());
            // 工程类别
            dbSysProject.setProjType(sysProject.getProjType());
            // 项目性质
            dbSysProject.setProjectProperty(sysProject.getProjectProperty());
            // 项目特征
            dbSysProject.setProDescribe(sysProject.getProDescribe());
            // 主要工程项目及工程数量
            dbSysProject.setDetail(sysProject.getDetail());
            // 开工日期
            dbSysProject.setActualStartDate(sysProject.getActualStartDate());
            // 主体完工日期
            dbSysProject.setMainEndDate(sysProject.getMainEndDate());
            // 交工日期
            dbSysProject.setDeliveryDate(sysProject.getDeliveryDate());
            // 实际竣工日期
            dbSysProject.setActualEndDate(sysProject.getActualEndDate());
            // 归档日期
            dbSysProject.setDocDate(sysProject.getDocDate());
            // 项目表up数组
            dbSysProject.setConfUpJSONArray(sysProject.getConfUpJSONArray());
            // 项目表down数组
            dbSysProject.setConfDownJSONArray(sysProject.getConfDownJSONArray());
            // 所属公司
            dbSysProject.setCompanyJSONArray(sysProject.getCompanyJSONArray());
            // userKey
            dbSysProject.setUserKey(sysProject.getUserKey());
            // 项目经理姓名
            dbSysProject.setProjectChiefName(sysProject.getProjectChiefName());
            // 项目承包模式
            dbSysProject.setContractingType(sysProject.getContractingType());
            // 项目经理电话
            dbSysProject.setProjectChiefTel(sysProject.getProjectChiefTel());
            
            
            // 所属公司
            JSONArray companyJSONArray = sysProject.getCompanyJSONArray();
            if(companyJSONArray != null && companyJSONArray.size()>0) {
                JSONObject jsonObject = companyJSONArray.getJSONObject(0);
                jsonObject.remove("showData");
                jsonObject.remove("_key");
                jsonObject.remove("title");
                jsonObject.remove("children");
                jsonObject.remove("loading");
                dbSysProject.setCompanyConf(companyJSONArray.toString());
                // 获取公司ID
                SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(jsonObject.getStr("value"));
                dbSysProject.setCompanyId(sysDepartment.getCompanyId());
                dbSysProject.setCompanyName(sysDepartment.getCompanyName());
            }
            
            // 托管
            JSONArray delegateJSONArray = sysProject.getDelegateJSONArray();
            if(delegateJSONArray != null && delegateJSONArray.size()>0) {
                JSONObject jsonObject = delegateJSONArray.getJSONObject(0);
                jsonObject.remove("showData");
                jsonObject.remove("_key");
                jsonObject.remove("title");
                jsonObject.remove("children");
                jsonObject.remove("loading");
                dbSysProject.setDelegateId(jsonObject.getStr("value"));
                dbSysProject.setDelegateName(jsonObject.getStr("label"));
                dbSysProject.setDelegateConf(delegateJSONArray.toString());
            }
            
            // 解析前端传来的confUp，更新部门表或项目表的confDown字段
            JSONArray confUpJSONArray = sysProject.getConfUpJSONArray();
            if(confUpJSONArray != null && confUpJSONArray.size()>0) {
                JSONObject jsonObject = delegateJSONArray.getJSONObject(0);
                jsonObject.remove("showData");
                jsonObject.remove("_key");
                jsonObject.remove("title");
                jsonObject.remove("children");
                jsonObject.remove("loading");
                dbSysProject.setHeadquartersId(jsonObject.getStr("value"));
                dbSysProject.setHeadquartersName(jsonObject.getStr("label"));
                dbSysProject.setHeadquartersConf(delegateJSONArray.toString());
                
                dbSysProject.setConfUp(confUpJSONArray.toString());
                confUpJSONArray = confUpJSONArray == null ? new JSONArray() : confUpJSONArray;
                confUpJSONArray.addAll(companyJSONArray);
            }
            
            // 快速去重
            List<TreeNodeEntity> confUpList = JSONUtil.toList(confUpJSONArray, TreeNodeEntity.class);
            Set<TreeNodeEntity> set = new TreeSet<>(
            Comparator.comparing(TreeNodeEntity::getValue));
            set.addAll(confUpList);
            confUpList.clear();
            confUpList.addAll(set);
            confUpJSONArray = JSONUtil.parseArray(confUpList);
            
            // 1、根据当前项目id查找所有相关项目id，全部删除掉
//            updateRemoveConfDown(dbSysProject.getDepartmentId());
            
            // 2、解析前端传来的confUp，更新部门表或项目表的confDown字段
            if(confUpJSONArray != null && confUpJSONArray.size()>0) {
                for (Iterator<Object> iterator = confUpJSONArray.iterator(); iterator.hasNext();) {
                    JSONObject jsonObject = (JSONObject)iterator.next();
                    String departmentId = jsonObject.getStr("value");
                    jsonObject.remove("showData");
                    jsonObject.remove("_key");
                    jsonObject.remove("title");
                    // 保存confDown内容到department表或project表
                    updateConfDown(dbSysProject.getDepartmentId(), departmentId);
                }
            }
            dbSysProject.setSchedule(null);
            // 备注
            dbSysProject.setRemarks(sysProject.getRemarks());
            // 排序
            dbSysProject.setSort(sysProject.getSort());
            // 共通
            dbSysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.updateByPrimaryKey(dbSysProject);
            
//            // 删除所有附件
//            SysFiles sysFilesSelect = new SysFiles();
//            sysFilesSelect.setOtherId(dbSysProject.getDepartmentId());
//            List<SysFiles> sysFileList = sysFilesMapper.selectBySysFilesList(sysFilesSelect);
//            if(sysFileList != null && sysFileList.size()>0) {
//                sysFilesMapper.batchDeleteUpdateSysFiles(sysFileList);
//            }
//            // 新增所有附件
//            sysFileList = sysProject.getSysFileList();
//            if(sysFileList != null && sysFileList.size()>0) {
//                for(SysFiles sysFiles:sysFileList) {
//                    sysFiles.setUid(UuidUtil.generate());
//                    sysFiles.setOtherId(sysProject.getDepartmentId());
//                    sysFiles.setCreateUserInfo(userKey, realName);
//                    sysFilesMapper.insert(sysFiles);
//                }
//            }
            
            // 更新业主台账表
            try {
                String url = Apih5Properties.getWebUrl() + "updateZxCtContractFinishInfo";
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("orgID", dbSysProject.getDepartmentId());
                // 项目开工日期
                jsonObject.set("actualStartDate", dbSysProject.getActualStartDate());
                // 项目竣工日期
                jsonObject.set("actualEndDate", dbSysProject.getActualEndDate());
                // 主体完工日期
                jsonObject.set("mainEndDate", dbSysProject.getMainEndDate());
                // 归档日期
                jsonObject.set("docDate", dbSysProject.getDocDate());
                String result = HttpUtil.sendPostToken(url, jsonObject.toString(), token);
            }catch (Exception e) {
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysProject);
        }
    }

    /**
     * 主体完工的相关更新
     */
    @Override
    public ResponseEntity updateSysProjectByMainFinish(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(sysProject == null || StrUtil.isEmpty(sysProject.getDepartmentId())) {
            return repEntity.layerMessage("no", "项目ID不能为空！");
        }
        int flag = 0;
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
        if (dbSysProject != null && StrUtil.isNotEmpty(dbSysProject.getDepartmentId())) {
            // 主体完工日期
            dbSysProject.setMainEndDate(sysProject.getMainEndDate());
            // 共通
            dbSysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.updateByPrimaryKey(dbSysProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysProject);
        }
    }
    
    /**
     * 业主台账的相关更新
     */
    @Override
    public ResponseEntity updateSysProjectByContract(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(sysProject == null || StrUtil.isEmpty(sysProject.getDepartmentId())) {
            return repEntity.layerMessage("no", "项目ID不能为空！");
        }
        int flag = 0;
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
        if (dbSysProject != null && StrUtil.isNotEmpty(dbSysProject.getDepartmentId())) {
            // 项目开工日期
            dbSysProject.setActualStartDate(sysProject.getActualStartDate());
            // 项目竣工日期
            dbSysProject.setActualEndDate(sysProject.getActualEndDate());
            // 共通
            dbSysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.updateByPrimaryKey(dbSysProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysProject);
        }
    }
    
    @Override
    public ResponseEntity batchDeleteUpdateSysProjectByRelation(List<SysProject> sysProjectList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysProjectList != null && sysProjectList.size() > 0) {
            for(SysProject dbSysProject:sysProjectList) {
                // 调用接口，如果失败则回滚
                String url = Apih5Properties.getWebUrl() + "getZxCtContractListByOrgId";
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("orgID", dbSysProject.getDepartmentId());
                String result = HttpUtil.sendPostToken(url, jsonObject.toString(), token);
                JSONObject resultJSONObject = new JSONObject(result);
                if(resultJSONObject.getBool("success")) {
                    JSONArray jsonArray = resultJSONObject.getJSONArray("data");
                    if(jsonArray != null && jsonArray.size()>0) {
                        return repEntity.layerMessage("no", dbSysProject.getDepartmentName() + "已建立业主合同，无法删除!");
                    }
                } else {
                    return repEntity.layerMessage("no", dbSysProject.getDepartmentName() + "已建立业主合同，无法删除!");
                }

                // 1、根据当前项目id查找所有相关项目id，全部删除掉
                updateRemoveConfDown(dbSysProject.getDepartmentId());
            }
            SysProject sysProject = new SysProject();
            sysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.batchDeleteUpdateSysProject(sysProjectList, sysProject);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",sysProjectList);
        }
    }

    // ----查询相关-----------
    /**
     * 查询项目数据权限-项目获取
     * 
     * @param sysDepartment
     * @return 【项目权限列表】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysPermissionListByProject(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("admin", userId)) {
//            JSONArray jsonArray = new JSONArray();
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.set("departmentId", "9999999999");
//            jsonObject.set("departmentName", "集团");
//            jsonObject.set("companyId", "9999999999");
//            jsonObject.set("companyName", "集团");
//            jsonArray.add(jsonObject);
//            return repEntity.ok(jsonArray);
            
            JSONArray jsonArray = new JSONArray();
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("9999999999");
            sysDepartment.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_TOP);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            for (SysDepartment dbSysDepartment : sysDepartmentList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("departmentId", dbSysDepartment.getCompanyId());
                jsonObject.set("departmentName", dbSysDepartment.getCompanyName());
                jsonObject.set("companyId", dbSysDepartment.getCompanyId());
                jsonObject.set("companyName", dbSysDepartment.getCompanyName());
                jsonArray.add(jsonObject);
            }
            return repEntity.ok(jsonArray);
        } else if(StrUtil.equals("2", ext1)) {
            JSONArray jsonArray = new JSONArray();
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setUserKey(userKey);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartment);
            for (SysDepartment dbSysDepartment : sysDepartmentList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("departmentId", dbSysDepartment.getCompanyId());
                jsonObject.set("departmentName", dbSysDepartment.getCompanyName());
                jsonObject.set("companyId", dbSysDepartment.getCompanyId());
                jsonObject.set("companyName", dbSysDepartment.getCompanyName());
                jsonArray.add(jsonObject);
            }
            return repEntity.ok(jsonArray);
        } 
//        else if(StrUtil.equals("3", ext1)
//                || StrUtil.equals("4", ext1)) {
//            
//        }
        SysProject sysProjectSelect = new SysProject();
        sysProjectSelect.setUserKey(userKey);
        List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
        if(sysProjectList == null || sysProjectList.size()==0) {
            SysProject noSysProject = new SysProject();
            noSysProject.setDepartmentId("00000");
            noSysProject.setDepartmentName("无项目");
            sysProjectList.add(noSysProject);
        }
        return repEntity.ok(sysProjectList);
    }

    /**
     * 查询项目数据权限-项目获取
     * 
     * @param sysDepartment
     * @return 【项目权限列表】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysProjectBySelect(SysProject sysProject) {
        if (sysProject == null) {
            sysProject = new SysProject();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("admin", userId)) {
            sysProject.setDepartmentId("");
            sysProject.setCompanyId("");
        } else if(StrUtil.equals("2", ext1)) {
            // 公司只看见自己的
            sysProject.setCompanyId(sysProject.getDepartmentId());
            sysProject.setDepartmentId("");
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            // 项目通过右上角数据
            sysProject.setDepartmentId(sysProject.getDepartmentId());
        }
        // 分页查询
        PageHelper.startPage(sysProject.getPage(),sysProject.getLimit());
        // 获取数据
        List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProject);
//        if(sysProjectList != null && sysProjectList.size()>0) {
//            for(SysProject dbSysProject:sysProjectList) {
//                // 所属公司设置
//                if(StrUtil.isNotEmpty(dbSysProject.getCompanyId())) {
//                    JSONArray companyJSONArray = new JSONArray(dbSysProject.getCompanyConf());
//                    dbSysProject.setCompanyJSONArray(companyJSONArray);
//                }
//                // 项目配置
//                if(StrUtil.isNotEmpty(dbSysProject.getConfUp())) {
//                    dbSysProject.setConfUpJSONArray(new JSONArray(dbSysProject.getConfUp()));
//                }
//                if(StrUtil.isNotEmpty(dbSysProject.getConfDown())) {
//                    dbSysProject.setConfDownJSONArray(new JSONArray(dbSysProject.getConfDown()));
//                }
//            }
//        }
        // 得到分页信息
        PageInfo<SysProject> p = new PageInfo<>(sysProjectList);

        return repEntity.okList(sysProjectList, p.getTotal());
    }

    /**
     * 查询公司下拉
     * 
     * @param sysDepartment
     * @return 【公司下拉】
     */
    @Override
    public ResponseEntity getSysCompanyBySelect(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        
        // DepartmentId是空的时候，全部查询
        if(StrUtil.isEmpty(sysProject.getDepartmentId())) {
            ext1 = "1"; 
        }
        
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("admin", userId)) {
            JSONArray jsonArray = new JSONArray();
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("9999999999");
            sysDepartment.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_TOP);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            // 添加公司
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("9999999999");
            sysDepartment.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_COMPANY);
            List<SysDepartment> sysDepartmentListCompany = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            sysDepartmentList.addAll(sysDepartmentListCompany);
            for (SysDepartment dbSysDepartment : sysDepartmentList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("companyId", dbSysDepartment.getCompanyId());
                jsonObject.set("companyName", dbSysDepartment.getCompanyName());
                jsonObject.set("departmentFlag", dbSysDepartment.getDepartmentFlag());
                jsonArray.add(jsonObject);
            }
            return repEntity.ok(jsonArray);
        } else if(StrUtil.equals("2", ext1)) {
            JSONArray jsonArray = new JSONArray();
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setUserKey(userKey);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartment);
            for (SysDepartment dbSysDepartment : sysDepartmentList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("companyId", dbSysDepartment.getCompanyId());
                jsonObject.set("companyName", dbSysDepartment.getCompanyName());
                jsonObject.set("departmentFlag", dbSysDepartment.getDepartmentFlag());
                jsonArray.add(jsonObject);
            }
            return repEntity.ok(jsonArray);
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            sysProject.setUserKey(userKey);
            // 当前departmentId如果是companyId则，请求当前公司的所有项目
            SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysProject.getDepartmentId());
            if(StrUtil.equals(SysConst.SYS_DEPARTMENT_TYPE_COMPANY, dbSysDepartment.getDepartmentFlag())) {
                sysProject.setCompanyId(sysProject.getDepartmentId());
                sysProject.setDepartmentId("");
            }
        }
        List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectCompanyListByUser(sysProject);
        JSONArray jsonArray = new JSONArray();
        for (SysProject dbSysProject : sysProjectList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("companyId", dbSysProject.getCompanyId());
            jsonObject.set("companyName", dbSysProject.getCompanyName());
            jsonObject.set("departmentFlag", dbSysProject.getDepartmentFlag());
            jsonArray.add(jsonObject);
        }
        return repEntity.ok(jsonArray);
    }

    /**
     * 查询项目数据权限-项目获取
     * 
     * @param sysDepartment
     * @return 【项目权限列表】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysCompanyProject(SysProject sysProject) {
        if (sysProject == null) {
            sysProject = new SysProject();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("admin", userId)) {
            sysProject.setProjectId("");
            sysProject.setCompanyId("");
        } else if(StrUtil.equals("2", ext1)) {
            // 公司只看见自己的
            sysProject.setCompanyId(sysProject.getDepartmentId());
            sysProject.setProjectId("");
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            // 项目通过右上角数据
            sysProject.setProjectId(sysProject.getDepartmentId());
            // 当前departmentId如果是companyId则，请求当前公司的所有项目,
            // 设备异动里面的公司内调拨用到这个场景
            SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysProject.getDepartmentId());
            if(dbSysDepartment != null
                    && StrUtil.equals(SysConst.SYS_DEPARTMENT_TYPE_COMPANY, dbSysDepartment.getDepartmentFlag())) {
                sysProject.setCompanyId(sysProject.getDepartmentId());
                sysProject.setProjectId("");
            }
        }
        
        // 分页查询
        PageHelper.startPage(sysProject.getPage(),sysProject.getLimit());
        // 获取数据
        List<SysProject> sysProjectList = sysProjectMapper.getSysCompanyProject(sysProject);
        // 得到分页信息
        PageInfo<SysProject> p = new PageInfo<>(sysProjectList);

        return repEntity.okList(sysProjectList, p.getTotal());
    }

    /**
     * 查询项目数据权限-项目获取
     * 
     * @param sysDepartment
     * @return 【项目权限列表】，并形成树形结构
     */
    @Override
    public ResponseEntity changeSysProject(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String ext1 = TokenUtils.getExt1(request);
        return repEntity.ok("sys.data.sava", "");
    }

//    /**
//     * 项目类型查询
//     * 
//     * @param sysDepartment
//     * @return 直接返回【承建项目类型】
//     */
//    @Override
//    public String getSysProjectByFlowType(SysProject sysProject) {
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String token = TokenUtils.getToken(request);
//        String flag = request.getParameter("flag");
//        if(sysProject == null || StrUtil.isEmpty(sysProject.getProjectId())) {
//            return "";
//        }
//        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
//        if(dbSysProject == null) {
//            return "";
//        }
//        
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.set("zb", true);
//        jsonObject.set("tg", true);
//        
//        // 总部
//        if(StrUtil.equals("zb", flag)
//                && StrUtil.isNotEmpty(dbSysProject.getHeadquartersId())) {
//            jsonObject.set("zb", false);
//        } 
//        // 托管
//        if(StrUtil.equals("tg", flag)
//                && StrUtil.isNotEmpty(dbSysProject.getDelegateId())) {
//            jsonObject.set("tg", false);
//        } 
//        
//        return jsonObject.toString();
//    }

    /**
      * 获取项目树
      * 
      * @param sysDepartment
      * @return 【项目】，并形成树形结构
      */
     @Override
     public ResponseEntity getSysProjectTree(SysDepartment sysDepartment) {
         HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userKey = TokenUtils.getUserKey(request);
         if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
//             return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
         }
         // 页面刷新，第一次进来时
         if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
             SysProject sysProjectSelect = new SysProject();
             sysProjectSelect.setUserKey(userKey);
             List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
             // 获取的数据时当前关联的部门，数据公司数据在companyId中，所以吧当前departmentId更换成companyId
             if(sysProjectList != null && sysProjectList.size()>0) {
                 JSONArray jsonArray = new JSONArray();
                 for(SysProject dbSysProject:sysProjectList){
                     JSONObject jsonObject = new JSONObject();
                     jsonObject.set("value", dbSysProject.getDepartmentId());
                     jsonObject.set("valuePid", dbSysProject.getDepartmentParentId());
                     jsonObject.set("label", dbSysProject.getDepartmentName());
                     jsonObject.set("title", dbSysProject.getDepartmentName());
                     jsonObject.set("type", "1");
                     jsonArray.add(jsonObject);
                 }     
                 return repEntity.ok(jsonArray);
             }
             
         }
         
         // 如果是项目身份时，必须传departmentParentId（右上角传过来）
         if(StrUtil.isEmpty(sysDepartment.getDepartmentId())) {
             return repEntity.layerMessage("no", "请选择项目！");
         }
         
         SysProject sysProjectSelect = new SysProject();

         // 根据主键查询当前 
         SysProject dbSysProjectPrimaryKey = sysProjectMapper.selectByPrimaryKey(sysDepartment.getProjectId());
         if(dbSysProjectPrimaryKey == null) {
             sysProjectSelect.setDepartmentParentId("0");
         } else {
             sysProjectSelect.setDepartmentParentId(dbSysProjectPrimaryKey.getDepartmentParentId());
         }
         JSONArray jsonArray = new JSONArray();
         List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProjectSelect);
         if(sysProjectList != null && sysProjectList.size()>0) {
             for(SysProject dbSysProject:sysProjectList){
                 JSONObject jsonObject = new JSONObject();
                 jsonObject.set("value", dbSysProject.getDepartmentId());
                 jsonObject.set("valuePid", dbSysProject.getDepartmentParentId());
                 jsonObject.set("label", dbSysProject.getDepartmentName());
                 jsonObject.set("title", dbSysProject.getDepartmentName());
                 jsonObject.set("type", "1");
                 jsonArray.add(jsonObject);
             }        
         }

         return repEntity.ok(jsonArray);
     }
     
     /**
      * 获取项目树
      * 
      * @param sysDepartment
      * @return 【项目】，并形成树形结构
      */
     @Override
     public ResponseEntity getSysProjectUserTree(SysDepartment sysDepartment) {
         HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userKey = TokenUtils.getUserKey(request);
         if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
//             return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
         }
         // 页面刷新，第一次进来时
         if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
             SysProject sysProjectSelect = new SysProject();
             sysProjectSelect.setUserKey(userKey);
             List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
             // 获取的数据时当前关联的部门，数据公司数据在companyId中，所以吧当前departmentId更换成companyId
             if(sysProjectList != null && sysProjectList.size()>0) {
                 JSONArray jsonArray = new JSONArray();
                 for(SysProject dbSysProject:sysProjectList){
                     JSONObject jsonObject = new JSONObject();
                     jsonObject.set("value", dbSysProject.getDepartmentId());
                     jsonObject.set("valuePid", dbSysProject.getDepartmentParentId());
                     jsonObject.set("label", dbSysProject.getDepartmentName());
                     jsonObject.set("title", dbSysProject.getDepartmentName());
                     jsonObject.set("type", "1");
                     jsonArray.add(jsonObject);
                 }     
                 return repEntity.ok(jsonArray);
             }
             
         }
         
         // 如果是项目身份时，必须传departmentParentId（右上角传过来）
         if(StrUtil.isEmpty(sysDepartment.getDepartmentId())) {
             return repEntity.layerMessage("no", "请选择项目！");
         }
         
         SysProject sysProjectSelect = new SysProject();

         // 根据主键查询当前 
         SysProject dbSysProjectPrimaryKey = sysProjectMapper.selectByPrimaryKey(sysDepartment.getProjectId());
         if(dbSysProjectPrimaryKey == null) {
             sysProjectSelect.setDepartmentParentId("0");
         } else {
             sysProjectSelect.setDepartmentParentId(dbSysProjectPrimaryKey.getDepartmentParentId());
         }
         JSONArray jsonArray = new JSONArray();
         List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProjectSelect);
         if(sysProjectList != null && sysProjectList.size()>0) {
             for(SysProject dbSysProject:sysProjectList){
                 JSONObject jsonObject = new JSONObject();
                 jsonObject.set("value", dbSysProject.getDepartmentId());
                 jsonObject.set("valuePid", dbSysProject.getDepartmentParentId());
                 jsonObject.set("label", dbSysProject.getDepartmentName());
                 jsonObject.set("title", dbSysProject.getDepartmentName());
                 jsonObject.set("type", "1");
                 jsonArray.add(jsonObject);
             }        
         }
         
         // 项目人员查询
         // 当前departmentId如果存在于project表则获取当前项目的用户数据
         SysProject sysProject = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
         if(sysProject != null) {
             SysUser sysUserSelect = new SysUser();
             sysUserSelect.setDepartmentId(sysDepartment.getDepartmentParentId());
             List<SysUser> sysUserProjectList = sysUserMapper.selectBySysUserListByDepartment(sysUserSelect);
             if(sysUserProjectList != null && sysUserProjectList.size()>0) {
                 for(SysUser dbSysUser:sysUserProjectList){
                     JSONObject jsonObject = new JSONObject();
                     jsonObject.set("value", dbSysUser.getUserKey());
                     jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                     jsonObject.set("label", dbSysUser.getRealName());
                     jsonObject.set("title", dbSysUser.getRealName());
                     jsonObject.set("type", "2");
                     jsonArray.add(jsonObject);
                 }        
             }
         }
         
         return repEntity.ok(jsonArray);
     }

     /**
      * 获取项目树-流程
      * 
      * @param sysDepartment
      * @return 【项目-流程】，并形成树形结构
      */
     @Override
     public ResponseEntity getSysProjectUserFlowTree(SysDepartment sysDepartment) {
         HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userKey = TokenUtils.getUserKey(request);
         if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
//             return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
         }
         // 页面刷新，第一次进来时
         if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
             SysProject sysProjectSelect = new SysProject();
             sysProjectSelect.setUserKey(userKey);
             List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
             // 获取的数据时当前关联的部门，数据公司数据在companyId中，所以吧当前departmentId更换成companyId
             if(sysProjectList != null && sysProjectList.size()>0) {
                 JSONArray jsonArray = new JSONArray();
                 for(SysProject dbSysProject:sysProjectList){
                     JSONObject jsonObject = new JSONObject();
                     jsonObject.set("value", dbSysProject.getDepartmentId());
                     jsonObject.set("valuePid", dbSysProject.getDepartmentParentId());
                     jsonObject.set("label", dbSysProject.getDepartmentName());
                     jsonObject.set("title", dbSysProject.getDepartmentName());
                     jsonObject.set("type", "1");
                     jsonArray.add(jsonObject);
                 }     
                 return repEntity.ok(jsonArray);
             }
             
         }
         
         // 如果是项目身份时，必须传departmentParentId（右上角传过来）
         if(StrUtil.isEmpty(sysDepartment.getDepartmentId())) {
             return repEntity.layerMessage("no", "请选择项目！");
         }
         
         SysProject sysProjectSelect = new SysProject();

         // 根据主键查询当前 
         SysProject dbSysProjectPrimaryKey = sysProjectMapper.selectByPrimaryKey(sysDepartment.getProjectId());
         if(dbSysProjectPrimaryKey == null) {
             sysProjectSelect.setDepartmentParentId("0");
         } else {
             sysProjectSelect.setDepartmentParentId(dbSysProjectPrimaryKey.getDepartmentParentId());
         }
         JSONArray jsonArray = new JSONArray();
         List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProjectSelect);
         if(sysProjectList != null && sysProjectList.size()>0) {
             for(SysProject dbSysProject:sysProjectList){
                 JSONObject jsonObject = new JSONObject();
                 jsonObject.set("value", dbSysProject.getDepartmentId());
                 jsonObject.set("valuePid", dbSysProject.getDepartmentParentId());
                 jsonObject.set("label", dbSysProject.getDepartmentName());
                 jsonObject.set("title", dbSysProject.getDepartmentName());
                 jsonObject.set("type", "1");
                 jsonArray.add(jsonObject);
             }        
         }
         
         // 项目人员查询
         // 当前departmentId如果存在于project表则获取当前项目的用户数据
         SysProject sysProject = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
         if(sysProject != null) {
             SysUser sysUserSelect = new SysUser();
             sysUserSelect.setDepartmentId(sysDepartment.getDepartmentParentId());
             List<SysUser> sysUserProjectList = sysUserMapper.selectBySysUserListByDepartment(sysUserSelect);
             if(sysUserProjectList != null && sysUserProjectList.size()>0) {
                 for(SysUser dbSysUser:sysUserProjectList){
                     JSONObject jsonObject = new JSONObject();
                     jsonObject.set("value", dbSysUser.getUserKey());
                     jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                     jsonObject.set("label", dbSysUser.getRealName());
                     jsonObject.set("title", dbSysUser.getRealName());
                     jsonObject.set("type", "2");
                     
                     SysFlowUser sysFlowUser =  new SysFlowUser();
                     sysFlowUser.setValue(dbSysUser.getUserKey());
                     sysFlowUser.setFlowRoleId(sysDepartment.getFlowRoleId());
                     sysFlowUser.setTopId(sysDepartment.getTopId());
                     List<SysFlowUser> sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUser);
                     if(sysFlowUserList != null && sysFlowUserList.size()>0) {
                         jsonObject.set("checked", true);
                     }                     
                     
                     jsonArray.add(jsonObject);
                 }        
             }
         }
         
         return repEntity.ok(jsonArray);
     }
     
     // ----↓↓↓私有方法↓↓↓-----
     /**
      * 保存confDown内容到department表或project表
      * 
      * @param currentDepartmentId 当前【新增项目ID】保存到配置down中
      * @param departmentId 页面所选择配置的downList里面的某个departmentId
      */
     private void updateConfDown(String currentDepartmentId, String departmentId) {
         // 部门表时，查找当前id的项目表或部门表数据
         SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(departmentId);
         // 部门的ConfDown
         if(dbSysDepartment != null) {
             // 获取confDown配置
             String confDown = dbSysDepartment.getConfDown();
             // 把当前新项目id插入到配置的里面
             JSONArray confDownJSONArray = new JSONArray();
             if(confDown != null && StrUtil.isNotEmpty(confDown)) {
                 confDownJSONArray = new JSONArray(confDown);
             }
             confDownJSONArray.add(currentDepartmentId);
             dbSysDepartment.setConfDown(confDownJSONArray.toString());
             // 更新数据
             sysDepartmentMapper.updateByPrimaryKey(dbSysDepartment);
         }
         
         // 项目表时，查找当前id的项目表或部门表数据
         SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(departmentId);
         // 项目的ConfDown
         if(dbSysProject != null) {
             // 获取confDown配置
             String confDown = dbSysProject.getConfDown();
             // 把当前新项目id插入到配置的里面
             JSONArray confDownJSONArray = new JSONArray();
             if(confDown != null && StrUtil.isNotEmpty(confDown)) {
                 confDownJSONArray = new JSONArray(confDown);
             }
             confDownJSONArray.add(currentDepartmentId);
             dbSysProject.setConfDown(confDownJSONArray.toString());
             // 更新数据
             sysProjectMapper.updateByPrimaryKey(dbSysProject);
         }
     }
     
     /**
      * 根据当前更新值（departmentId）删除department表或project表相关联数据
      * 
      * @param currentDepartmentId 当前【新增项目ID】保存到配置down中
      * @param departmentId 页面所选择配置的downList里面的某个departmentId
      */
     private void updateRemoveConfDown(String currentDepartmentId) {
         // 1.1 部门表
         SysDepartment sysDepartmentSelect = new SysDepartment();
         sysDepartmentSelect.setConfDown(currentDepartmentId);
         List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
         if(sysDepartmentList != null && sysDepartmentList.size()>0) {
             for(SysDepartment dbSysDepartment:sysDepartmentList) {
                 JSONArray confDownJSONArray = new JSONArray(dbSysDepartment.getConfDown());
                 confDownJSONArray.remove(currentDepartmentId);
                 dbSysDepartment.setConfDown(confDownJSONArray.toString());
                 sysDepartmentMapper.updateByPrimaryKey(dbSysDepartment);
             }
         }
         // 1.2 项目表
         SysProject sysProjectSelect = new SysProject();
         sysProjectSelect.setConfDown(currentDepartmentId);
         List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProjectSelect);
         if(sysProjectList != null && sysProjectList.size()>0) {
             for(SysProject dbSysProject:sysProjectList) {
                 JSONArray confDownJSONArray = new JSONArray(dbSysProject.getConfDown());
                 confDownJSONArray.remove(currentDepartmentId);
                 dbSysProject.setConfDown(confDownJSONArray.toString());
                 sysProjectMapper.updateByPrimaryKey(dbSysProject);
             }
         }
     }
     // ----↑↑↑私有方法↑↑↑-----
}
