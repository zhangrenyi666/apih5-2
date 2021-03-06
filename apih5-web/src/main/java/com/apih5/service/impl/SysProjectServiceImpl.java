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
        // ??????????????????
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("admin", userId)) {
            sysProject.setDepartmentId("");
            sysProject.setCompanyId("");
        } else if(StrUtil.equals("2", ext1)) {
            // ????????????????????????
            sysProject.setCompanyId(sysProject.getDepartmentId());
            sysProject.setDepartmentId("");
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            // ???????????????????????????
            sysProject.setDepartmentId(sysProject.getDepartmentId());
        }

        // ????????????
        PageHelper.startPage(sysProject.getPage(),sysProject.getLimit());
        
        // ????????????
        sysProject.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_PRO);
        List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProject);
        if(sysProjectList != null && sysProjectList.size()>0) {
            for(SysProject dbSysProject:sysProjectList) {
                // ??????????????????
                if(StrUtil.isNotEmpty(dbSysProject.getCompanyId()) 
                        && StrUtil.isNotEmpty(dbSysProject.getCompanyConf())) {
                    JSONArray companyJSONArray = new JSONArray(dbSysProject.getCompanyConf());
                    dbSysProject.setCompanyJSONArray(companyJSONArray);
                }
                // ??????
                if(StrUtil.isNotEmpty(dbSysProject.getDelegateId()) 
                        && StrUtil.isNotEmpty(dbSysProject.getDelegateConf())) {
                    JSONArray delegateJSONArray = new JSONArray(dbSysProject.getDelegateConf());
                    dbSysProject.setDelegateJSONArray(delegateJSONArray);
                }
                // ????????????
                if(StrUtil.isNotEmpty(dbSysProject.getConfUp())) {
                    dbSysProject.setConfUpJSONArray(new JSONArray(dbSysProject.getConfUp()));
                }
                if(StrUtil.isNotEmpty(dbSysProject.getConfDown())) {
                    dbSysProject.setConfDownJSONArray(new JSONArray(dbSysProject.getConfDown()));
                }
                
//                // ????????????
//                SysFiles sysFilesSelect = new SysFiles();
//                sysFilesSelect.setOtherId(dbSysProject.getDepartmentId());
//                List<SysFiles> sysFileList = sysFilesMapper.selectBySysFilesList(sysFilesSelect);
//                dbSysProject.setSysFileList(sysFileList);
            }
        }
        
        // ??????????????????
        PageInfo<SysProject> p = new PageInfo<>(sysProjectList);

        return repEntity.okList(sysProjectList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysProjectDetail(SysProject sysProject) {
        if (sysProject == null) {
            sysProject = new SysProject();
        }
        // ????????????
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
        // ????????????
        if (dbSysProject != null) {
            return repEntity.ok(dbSysProject);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
         // ????????????
            dbSysProject.setDepartmentName(sysProject.getDepartmentName());
            // ????????????
            dbSysProject.setDepartmentFullName(sysProject.getDepartmentFullName());
            // ?????????ID
            dbSysProject.setDepartmentParentId(sysProject.getDepartmentParentId());
            // ????????????
            dbSysProject.setDepartmentPath(sysProject.getDepartmentPath());
            // ??????????????????
            dbSysProject.setDepartmentPathName(sysProject.getDepartmentPathName());
            // ??????????????????????????????
            dbSysProject.setDepartmentFlag(sysProject.getDepartmentFlag());
            // ??????????????????????????????
            dbSysProject.setDepartmentFlagName(sysProject.getDepartmentFlagName());
            // ????????????
            dbSysProject.setCompanyId(sysProject.getCompanyId());
            // ???????????????
            dbSysProject.setCompanyConf(sysProject.getCompanyConf());
            // ????????????
            dbSysProject.setCompanyName(sysProject.getCompanyName());
            // ????????????
            dbSysProject.setProjectId(sysProject.getProjectId());
            // ????????????
            dbSysProject.setProjectName(sysProject.getProjectName());
            // ??????id??????????????????
            dbSysProject.setOtherId(sysProject.getOtherId());
            // ???????????????up
            dbSysProject.setConfUp(sysProject.getConfUp());
            // ???????????????down
            dbSysProject.setConfDown(sysProject.getConfDown());
            // ?????????????????????
            dbSysProject.setConfDownShowFlag(sysProject.getConfDownShowFlag());
            // ????????????
            dbSysProject.setProjectPinyin(sysProject.getProjectPinyin());
            // ??????????????????
            dbSysProject.setContractorType(sysProject.getContractorType());
            // ???????????????
            dbSysProject.setJuFlag(sysProject.getJuFlag());
            // ?????????????????????
            dbSysProject.setJuName(sysProject.getJuName());
            // ??????????????????
            dbSysProject.setLocationInDeprFormula(sysProject.getLocationInDeprFormula());
            // ??????????????????
            dbSysProject.setContractor(sysProject.getContractor());
            // ????????????
            dbSysProject.setBiddingQualification(sysProject.getBiddingQualification());
            // ???????????????
            dbSysProject.setBizDep(sysProject.getBizDep());
            // ????????????
            dbSysProject.setSubArea(sysProject.getSubArea());
            // ???????????????
            dbSysProject.setProjectLocation(sysProject.getProjectLocation());
            // ????????????????????????
            dbSysProject.setProvinceAbbreviation(sysProject.getProvinceAbbreviation());
            // ??????
            dbSysProject.setCityName(sysProject.getCityName());
            // ?????????
            dbSysProject.setLotNo(sysProject.getLotNo());
            // ????????????
            dbSysProject.setProjType(sysProject.getProjType());
            // ????????????
            dbSysProject.setProjectProperty(sysProject.getProjectProperty());
            // ????????????
            dbSysProject.setProDescribe(sysProject.getProDescribe());
            // ?????????????????????????????????
            dbSysProject.setDetail(sysProject.getDetail());
            // ????????????
            dbSysProject.setActualStartDate(sysProject.getActualStartDate());
            // ??????????????????
            dbSysProject.setMainEndDate(sysProject.getMainEndDate());
            // ????????????
            dbSysProject.setDeliveryDate(sysProject.getDeliveryDate());
            // ??????????????????
            dbSysProject.setActualEndDate(sysProject.getActualEndDate());
            // ????????????
            dbSysProject.setDocDate(sysProject.getDocDate());
            // ?????????up??????
            dbSysProject.setConfUpJSONArray(sysProject.getConfUpJSONArray());
            // ?????????down??????
            dbSysProject.setConfDownJSONArray(sysProject.getConfDownJSONArray());
            // ????????????
            dbSysProject.setCompanyJSONArray(sysProject.getCompanyJSONArray());
            // userKey
            dbSysProject.setUserKey(sysProject.getUserKey());
            // ??????????????????
            dbSysProject.setProjectChiefName(sysProject.getProjectChiefName());
            // ??????????????????
            dbSysProject.setContractingType(sysProject.getContractingType());
            // ??????????????????
            dbSysProject.setProjectChiefTel(sysProject.getProjectChiefTel());
            // ??????
            dbSysProject.setRemarks(sysProject.getRemarks());
            // ??????
            dbSysProject.setSort(sysProject.getSort());
            // ??????
            dbSysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.updateByPrimaryKey(dbSysProject);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",sysProjectList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    /**
     * ????????????????????????
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

        // ????????????
        JSONArray companyJSONArray = sysProject.getCompanyJSONArray();
        if(companyJSONArray != null && companyJSONArray.size()>0) {
            JSONObject jsonObject = companyJSONArray.getJSONObject(0);
            jsonObject.remove("showData");
            jsonObject.remove("_key");
            jsonObject.remove("title");
            jsonObject.remove("children");
            jsonObject.remove("loading");
            sysProject.setCompanyConf(companyJSONArray.toString());
            // ????????????ID
            SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(jsonObject.getStr("value"));
            sysProject.setCompanyId(sysDepartment.getCompanyId());
            sysProject.setCompanyName(sysDepartment.getCompanyName());
            // path
            String path = sysDepartment.getDepartmentPath() + "," + sysProject.getDepartmentId();
            String pathName = sysDepartment.getDepartmentPathName() + "," + sysProject.getDepartmentName();
            sysProject.setDepartmentPath(path);
            sysProject.setDepartmentPathName(pathName);
            // ????????????pid
            sysProject.setDepartmentParentId(jsonObject.getStr("value"));
        }
        
        // ??????
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

        // ?????????????????????confUp?????????????????????????????????confDown??????
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
            // ????????????????????????????????????
            SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(jsonObject.getStr("value"));
            if(dbSysProject != null) {
                sysProject.setDelegateId(dbSysProject.getDepartmentId());
                sysProject.setDelegateName(dbSysProject.getDepartmentName());
            }
        }

        sysProject.setConfUp(confUpJSONArray.toString());
        confUpJSONArray = confUpJSONArray == null ? new JSONArray() : confUpJSONArray;
        confUpJSONArray.addAll(companyJSONArray);
        
        // ????????????
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
                // ??????confDown?????????department??????project???
                updateConfDown(sysProject.getDepartmentId(), departmentId);
            }
        }
        sysProject.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_PRO);
        sysProject.setDepartmentFlagName("??????");
        sysProject.setProjectId(sysProject.getDepartmentId());
        sysProject.setProjectName(sysProject.getDepartmentName());
        
        
        // ?????????????????????????????????????????????
        BaseCode baseCode = new BaseCode();
        baseCode.setItemId("xiangMuBuMen");
        ResponseEntity baseResponseEntity = baseCodeService.getBaseCodeTree(baseCode);
        if(baseResponseEntity.isSuccess()) {
            JSONArray jsonArray = new JSONArray(baseResponseEntity.getData());
            if(jsonArray != null && jsonArray.size()>0) {
                for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
                    JSONObject jsonObject = (JSONObject)iterator.next();
                    String itemId = jsonObject.getStr("itemId");
                    // ?????????????????????
                    if(StrUtil.equals(sysProject.getProjType(), itemId)) {
                        JSONArray deptJSONArray = jsonObject.getJSONArray("children");
                        // ????????????
                        if(deptJSONArray != null && deptJSONArray.size()>0) {
                            for (Iterator<Object> deptIterator = deptJSONArray.iterator(); deptIterator.hasNext();) {
                                JSONObject deptJsonObject = (JSONObject)deptIterator.next();
                                // ???????????????????????????????????????????????????????????????
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
                                sysProjectDept.setDepartmentFlagName("??????");
                                sysProjectDept.setDepartmentPath(sysProject.getDepartmentPath() + "," + sysProjectDept.getDepartmentId());
                                sysProjectDept.setDepartmentPathName(sysProject.getDepartmentPathName() + "," + sysProjectDept.getDepartmentName());
                                sysProjectDept.setCreateUserInfo(userKey, realName);
                                sysProjectMapper.insert(sysProjectDept);
                                
                                // ????????????????????????????????????????????????????????????
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
            // ????????????
            dbSysProject.setDepartmentName(sysProject.getDepartmentName());
            // ????????????
            dbSysProject.setDepartmentFullName(sysProject.getDepartmentFullName());
//            // ?????????ID
//            dbSysProject.setDepartmentParentId(sysProject.getDepartmentParentId());
//            // ????????????
//            dbSysProject.setDepartmentPath(sysProject.getDepartmentPath());
//            // ??????????????????
//            dbSysProject.setDepartmentPathName(sysProject.getDepartmentPathName());
//            // ??????????????????????????????
//            dbSysProject.setDepartmentFlag(sysProject.getDepartmentFlag());
//            // ??????????????????????????????
//            dbSysProject.setDepartmentFlagName(sysProject.getDepartmentFlagName());
            // ????????????
            dbSysProject.setCompanyId(sysProject.getCompanyId());
            // ???????????????
            dbSysProject.setCompanyConf(sysProject.getCompanyConf());
            // ????????????
            dbSysProject.setCompanyName(sysProject.getCompanyName());
//            // ????????????
//            dbSysProject.setProjectId(sysProject.getProjectId());
            // ????????????
            dbSysProject.setProjectName(sysProject.getDepartmentName());
//            // ??????id??????????????????
//            dbSysProject.setOtherId(sysProject.getOtherId());
            // ???????????????up
            dbSysProject.setConfUp(sysProject.getConfUp());
            // ???????????????down
            dbSysProject.setConfDown(sysProject.getConfDown());
            // ?????????????????????
            dbSysProject.setConfDownShowFlag(sysProject.getConfDownShowFlag());
            // ????????????
            dbSysProject.setProjectPinyin(sysProject.getProjectPinyin());
            // ??????????????????
            dbSysProject.setContractorType(sysProject.getContractorType());
            // ???????????????
            dbSysProject.setJuFlag(sysProject.getJuFlag());
            // ?????????????????????
            dbSysProject.setJuName(sysProject.getJuName());
            // ??????????????????
            dbSysProject.setLocationInDeprFormula(sysProject.getLocationInDeprFormula());
            // ??????????????????
            dbSysProject.setContractor(sysProject.getContractor());
            // ????????????
            dbSysProject.setBiddingQualification(sysProject.getBiddingQualification());
            // ???????????????
            dbSysProject.setBizDep(sysProject.getBizDep());
            // ????????????
            dbSysProject.setSubArea(sysProject.getSubArea());
            // ???????????????
            dbSysProject.setProjectLocation(sysProject.getProjectLocation());
            // ????????????????????????
            dbSysProject.setProvinceAbbreviation(sysProject.getProvinceAbbreviation());
            // ??????
            dbSysProject.setCityName(sysProject.getCityName());
            // ?????????
            dbSysProject.setLotNo(sysProject.getLotNo());
            // ????????????
            dbSysProject.setProjType(sysProject.getProjType());
            // ????????????
            dbSysProject.setProjectProperty(sysProject.getProjectProperty());
            // ????????????
            dbSysProject.setProDescribe(sysProject.getProDescribe());
            // ?????????????????????????????????
            dbSysProject.setDetail(sysProject.getDetail());
            // ????????????
            dbSysProject.setActualStartDate(sysProject.getActualStartDate());
            // ??????????????????
            dbSysProject.setMainEndDate(sysProject.getMainEndDate());
            // ????????????
            dbSysProject.setDeliveryDate(sysProject.getDeliveryDate());
            // ??????????????????
            dbSysProject.setActualEndDate(sysProject.getActualEndDate());
            // ????????????
            dbSysProject.setDocDate(sysProject.getDocDate());
            // ?????????up??????
            dbSysProject.setConfUpJSONArray(sysProject.getConfUpJSONArray());
            // ?????????down??????
            dbSysProject.setConfDownJSONArray(sysProject.getConfDownJSONArray());
            // ????????????
            dbSysProject.setCompanyJSONArray(sysProject.getCompanyJSONArray());
            // userKey
            dbSysProject.setUserKey(sysProject.getUserKey());
            // ??????????????????
            dbSysProject.setProjectChiefName(sysProject.getProjectChiefName());
            // ??????????????????
            dbSysProject.setContractingType(sysProject.getContractingType());
            // ??????????????????
            dbSysProject.setProjectChiefTel(sysProject.getProjectChiefTel());
            
            
            // ????????????
            JSONArray companyJSONArray = sysProject.getCompanyJSONArray();
            if(companyJSONArray != null && companyJSONArray.size()>0) {
                JSONObject jsonObject = companyJSONArray.getJSONObject(0);
                jsonObject.remove("showData");
                jsonObject.remove("_key");
                jsonObject.remove("title");
                jsonObject.remove("children");
                jsonObject.remove("loading");
                dbSysProject.setCompanyConf(companyJSONArray.toString());
                // ????????????ID
                SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(jsonObject.getStr("value"));
                dbSysProject.setCompanyId(sysDepartment.getCompanyId());
                dbSysProject.setCompanyName(sysDepartment.getCompanyName());
            }
            
            // ??????
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
            
            // ?????????????????????confUp?????????????????????????????????confDown??????
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
            
            // ????????????
            List<TreeNodeEntity> confUpList = JSONUtil.toList(confUpJSONArray, TreeNodeEntity.class);
            Set<TreeNodeEntity> set = new TreeSet<>(
            Comparator.comparing(TreeNodeEntity::getValue));
            set.addAll(confUpList);
            confUpList.clear();
            confUpList.addAll(set);
            confUpJSONArray = JSONUtil.parseArray(confUpList);
            
            // 1?????????????????????id????????????????????????id??????????????????
//            updateRemoveConfDown(dbSysProject.getDepartmentId());
            
            // 2????????????????????????confUp?????????????????????????????????confDown??????
            if(confUpJSONArray != null && confUpJSONArray.size()>0) {
                for (Iterator<Object> iterator = confUpJSONArray.iterator(); iterator.hasNext();) {
                    JSONObject jsonObject = (JSONObject)iterator.next();
                    String departmentId = jsonObject.getStr("value");
                    jsonObject.remove("showData");
                    jsonObject.remove("_key");
                    jsonObject.remove("title");
                    // ??????confDown?????????department??????project???
                    updateConfDown(dbSysProject.getDepartmentId(), departmentId);
                }
            }
            dbSysProject.setSchedule(null);
            // ??????
            dbSysProject.setRemarks(sysProject.getRemarks());
            // ??????
            dbSysProject.setSort(sysProject.getSort());
            // ??????
            dbSysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.updateByPrimaryKey(dbSysProject);
            
//            // ??????????????????
//            SysFiles sysFilesSelect = new SysFiles();
//            sysFilesSelect.setOtherId(dbSysProject.getDepartmentId());
//            List<SysFiles> sysFileList = sysFilesMapper.selectBySysFilesList(sysFilesSelect);
//            if(sysFileList != null && sysFileList.size()>0) {
//                sysFilesMapper.batchDeleteUpdateSysFiles(sysFileList);
//            }
//            // ??????????????????
//            sysFileList = sysProject.getSysFileList();
//            if(sysFileList != null && sysFileList.size()>0) {
//                for(SysFiles sysFiles:sysFileList) {
//                    sysFiles.setUid(UuidUtil.generate());
//                    sysFiles.setOtherId(sysProject.getDepartmentId());
//                    sysFiles.setCreateUserInfo(userKey, realName);
//                    sysFilesMapper.insert(sysFiles);
//                }
//            }
            
            // ?????????????????????
            try {
                String url = Apih5Properties.getWebUrl() + "updateZxCtContractFinishInfo";
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("orgID", dbSysProject.getDepartmentId());
                // ??????????????????
                jsonObject.set("actualStartDate", dbSysProject.getActualStartDate());
                // ??????????????????
                jsonObject.set("actualEndDate", dbSysProject.getActualEndDate());
                // ??????????????????
                jsonObject.set("mainEndDate", dbSysProject.getMainEndDate());
                // ????????????
                jsonObject.set("docDate", dbSysProject.getDocDate());
                String result = HttpUtil.sendPostToken(url, jsonObject.toString(), token);
            }catch (Exception e) {
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysProject);
        }
    }

    /**
     * ???????????????????????????
     */
    @Override
    public ResponseEntity updateSysProjectByMainFinish(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(sysProject == null || StrUtil.isEmpty(sysProject.getDepartmentId())) {
            return repEntity.layerMessage("no", "??????ID???????????????");
        }
        int flag = 0;
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
        if (dbSysProject != null && StrUtil.isNotEmpty(dbSysProject.getDepartmentId())) {
            // ??????????????????
            dbSysProject.setMainEndDate(sysProject.getMainEndDate());
            // ??????
            dbSysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.updateByPrimaryKey(dbSysProject);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",sysProject);
        }
    }
    
    /**
     * ???????????????????????????
     */
    @Override
    public ResponseEntity updateSysProjectByContract(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(sysProject == null || StrUtil.isEmpty(sysProject.getDepartmentId())) {
            return repEntity.layerMessage("no", "??????ID???????????????");
        }
        int flag = 0;
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysProject.getDepartmentId());
        if (dbSysProject != null && StrUtil.isNotEmpty(dbSysProject.getDepartmentId())) {
            // ??????????????????
            dbSysProject.setActualStartDate(sysProject.getActualStartDate());
            // ??????????????????
            dbSysProject.setActualEndDate(sysProject.getActualEndDate());
            // ??????
            dbSysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.updateByPrimaryKey(dbSysProject);
        }
        // ??????
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
                // ????????????????????????????????????
                String url = Apih5Properties.getWebUrl() + "getZxCtContractListByOrgId";
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("orgID", dbSysProject.getDepartmentId());
                String result = HttpUtil.sendPostToken(url, jsonObject.toString(), token);
                JSONObject resultJSONObject = new JSONObject(result);
                if(resultJSONObject.getBool("success")) {
                    JSONArray jsonArray = resultJSONObject.getJSONArray("data");
                    if(jsonArray != null && jsonArray.size()>0) {
                        return repEntity.layerMessage("no", dbSysProject.getDepartmentName() + "????????????????????????????????????!");
                    }
                } else {
                    return repEntity.layerMessage("no", dbSysProject.getDepartmentName() + "????????????????????????????????????!");
                }

                // 1?????????????????????id????????????????????????id??????????????????
                updateRemoveConfDown(dbSysProject.getDepartmentId());
            }
            SysProject sysProject = new SysProject();
            sysProject.setModifyUserInfo(userKey, realName);
            flag = sysProjectMapper.batchDeleteUpdateSysProject(sysProjectList, sysProject);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",sysProjectList);
        }
    }

    // ----????????????-----------
    /**
     * ????????????????????????-????????????
     * 
     * @param sysDepartment
     * @return ????????????????????????????????????????????????
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
//            jsonObject.set("departmentName", "??????");
//            jsonObject.set("companyId", "9999999999");
//            jsonObject.set("companyName", "??????");
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
            noSysProject.setDepartmentName("?????????");
            sysProjectList.add(noSysProject);
        }
        return repEntity.ok(sysProjectList);
    }

    /**
     * ????????????????????????-????????????
     * 
     * @param sysDepartment
     * @return ????????????????????????????????????????????????
     */
    @Override
    public ResponseEntity getSysProjectBySelect(SysProject sysProject) {
        if (sysProject == null) {
            sysProject = new SysProject();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("admin", userId)) {
            sysProject.setDepartmentId("");
            sysProject.setCompanyId("");
        } else if(StrUtil.equals("2", ext1)) {
            // ????????????????????????
            sysProject.setCompanyId(sysProject.getDepartmentId());
            sysProject.setDepartmentId("");
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            // ???????????????????????????
            sysProject.setDepartmentId(sysProject.getDepartmentId());
        }
        // ????????????
        PageHelper.startPage(sysProject.getPage(),sysProject.getLimit());
        // ????????????
        List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProject);
//        if(sysProjectList != null && sysProjectList.size()>0) {
//            for(SysProject dbSysProject:sysProjectList) {
//                // ??????????????????
//                if(StrUtil.isNotEmpty(dbSysProject.getCompanyId())) {
//                    JSONArray companyJSONArray = new JSONArray(dbSysProject.getCompanyConf());
//                    dbSysProject.setCompanyJSONArray(companyJSONArray);
//                }
//                // ????????????
//                if(StrUtil.isNotEmpty(dbSysProject.getConfUp())) {
//                    dbSysProject.setConfUpJSONArray(new JSONArray(dbSysProject.getConfUp()));
//                }
//                if(StrUtil.isNotEmpty(dbSysProject.getConfDown())) {
//                    dbSysProject.setConfDownJSONArray(new JSONArray(dbSysProject.getConfDown()));
//                }
//            }
//        }
        // ??????????????????
        PageInfo<SysProject> p = new PageInfo<>(sysProjectList);

        return repEntity.okList(sysProjectList, p.getTotal());
    }

    /**
     * ??????????????????
     * 
     * @param sysDepartment
     * @return ??????????????????
     */
    @Override
    public ResponseEntity getSysCompanyBySelect(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        
        // DepartmentId??????????????????????????????
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
            // ????????????
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
            // ??????departmentId?????????companyId???????????????????????????????????????
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
     * ????????????????????????-????????????
     * 
     * @param sysDepartment
     * @return ????????????????????????????????????????????????
     */
    @Override
    public ResponseEntity getSysCompanyProject(SysProject sysProject) {
        if (sysProject == null) {
            sysProject = new SysProject();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("admin", userId)) {
            sysProject.setProjectId("");
            sysProject.setCompanyId("");
        } else if(StrUtil.equals("2", ext1)) {
            // ????????????????????????
            sysProject.setCompanyId(sysProject.getDepartmentId());
            sysProject.setProjectId("");
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            // ???????????????????????????
            sysProject.setProjectId(sysProject.getDepartmentId());
            // ??????departmentId?????????companyId???????????????????????????????????????,
            // ??????????????????????????????????????????????????????
            SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysProject.getDepartmentId());
            if(dbSysDepartment != null
                    && StrUtil.equals(SysConst.SYS_DEPARTMENT_TYPE_COMPANY, dbSysDepartment.getDepartmentFlag())) {
                sysProject.setCompanyId(sysProject.getDepartmentId());
                sysProject.setProjectId("");
            }
        }
        
        // ????????????
        PageHelper.startPage(sysProject.getPage(),sysProject.getLimit());
        // ????????????
        List<SysProject> sysProjectList = sysProjectMapper.getSysCompanyProject(sysProject);
        // ??????????????????
        PageInfo<SysProject> p = new PageInfo<>(sysProjectList);

        return repEntity.okList(sysProjectList, p.getTotal());
    }

    /**
     * ????????????????????????-????????????
     * 
     * @param sysDepartment
     * @return ????????????????????????????????????????????????
     */
    @Override
    public ResponseEntity changeSysProject(SysProject sysProject) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String ext1 = TokenUtils.getExt1(request);
        return repEntity.ok("sys.data.sava", "");
    }

//    /**
//     * ??????????????????
//     * 
//     * @param sysDepartment
//     * @return ????????????????????????????????????
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
//        // ??????
//        if(StrUtil.equals("zb", flag)
//                && StrUtil.isNotEmpty(dbSysProject.getHeadquartersId())) {
//            jsonObject.set("zb", false);
//        } 
//        // ??????
//        if(StrUtil.equals("tg", flag)
//                && StrUtil.isNotEmpty(dbSysProject.getDelegateId())) {
//            jsonObject.set("tg", false);
//        } 
//        
//        return jsonObject.toString();
//    }

    /**
      * ???????????????
      * 
      * @param sysDepartment
      * @return ????????????????????????????????????
      */
     @Override
     public ResponseEntity getSysProjectTree(SysDepartment sysDepartment) {
         HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userKey = TokenUtils.getUserKey(request);
         if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
//             return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
         }
         // ?????????????????????????????????
         if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
             SysProject sysProjectSelect = new SysProject();
             sysProjectSelect.setUserKey(userKey);
             List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
             // ???????????????????????????????????????????????????????????????companyId?????????????????????departmentId?????????companyId
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
         
         // ????????????????????????????????????departmentParentId????????????????????????
         if(StrUtil.isEmpty(sysDepartment.getDepartmentId())) {
             return repEntity.layerMessage("no", "??????????????????");
         }
         
         SysProject sysProjectSelect = new SysProject();

         // ???????????????????????? 
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
      * ???????????????
      * 
      * @param sysDepartment
      * @return ????????????????????????????????????
      */
     @Override
     public ResponseEntity getSysProjectUserTree(SysDepartment sysDepartment) {
         HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userKey = TokenUtils.getUserKey(request);
         if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
//             return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
         }
         // ?????????????????????????????????
         if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
             SysProject sysProjectSelect = new SysProject();
             sysProjectSelect.setUserKey(userKey);
             List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
             // ???????????????????????????????????????????????????????????????companyId?????????????????????departmentId?????????companyId
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
         
         // ????????????????????????????????????departmentParentId????????????????????????
         if(StrUtil.isEmpty(sysDepartment.getDepartmentId())) {
             return repEntity.layerMessage("no", "??????????????????");
         }
         
         SysProject sysProjectSelect = new SysProject();

         // ???????????????????????? 
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
         
         // ??????????????????
         // ??????departmentId???????????????project???????????????????????????????????????
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
      * ???????????????-??????
      * 
      * @param sysDepartment
      * @return ?????????-?????????????????????????????????
      */
     @Override
     public ResponseEntity getSysProjectUserFlowTree(SysDepartment sysDepartment) {
         HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userKey = TokenUtils.getUserKey(request);
         if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
//             return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
         }
         // ?????????????????????????????????
         if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
             SysProject sysProjectSelect = new SysProject();
             sysProjectSelect.setUserKey(userKey);
             List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
             // ???????????????????????????????????????????????????????????????companyId?????????????????????departmentId?????????companyId
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
         
         // ????????????????????????????????????departmentParentId????????????????????????
         if(StrUtil.isEmpty(sysDepartment.getDepartmentId())) {
             return repEntity.layerMessage("no", "??????????????????");
         }
         
         SysProject sysProjectSelect = new SysProject();

         // ???????????????????????? 
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
         
         // ??????????????????
         // ??????departmentId???????????????project???????????????????????????????????????
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
     
     // ----??????????????????????????????-----
     /**
      * ??????confDown?????????department??????project???
      * 
      * @param currentDepartmentId ?????????????????????ID??????????????????down???
      * @param departmentId ????????????????????????downList???????????????departmentId
      */
     private void updateConfDown(String currentDepartmentId, String departmentId) {
         // ???????????????????????????id??????????????????????????????
         SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(departmentId);
         // ?????????ConfDown
         if(dbSysDepartment != null) {
             // ??????confDown??????
             String confDown = dbSysDepartment.getConfDown();
             // ??????????????????id????????????????????????
             JSONArray confDownJSONArray = new JSONArray();
             if(confDown != null && StrUtil.isNotEmpty(confDown)) {
                 confDownJSONArray = new JSONArray(confDown);
             }
             confDownJSONArray.add(currentDepartmentId);
             dbSysDepartment.setConfDown(confDownJSONArray.toString());
             // ????????????
             sysDepartmentMapper.updateByPrimaryKey(dbSysDepartment);
         }
         
         // ???????????????????????????id??????????????????????????????
         SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(departmentId);
         // ?????????ConfDown
         if(dbSysProject != null) {
             // ??????confDown??????
             String confDown = dbSysProject.getConfDown();
             // ??????????????????id????????????????????????
             JSONArray confDownJSONArray = new JSONArray();
             if(confDown != null && StrUtil.isNotEmpty(confDown)) {
                 confDownJSONArray = new JSONArray(confDown);
             }
             confDownJSONArray.add(currentDepartmentId);
             dbSysProject.setConfDown(confDownJSONArray.toString());
             // ????????????
             sysProjectMapper.updateByPrimaryKey(dbSysProject);
         }
     }
     
     /**
      * ????????????????????????departmentId?????????department??????project??????????????????
      * 
      * @param currentDepartmentId ?????????????????????ID??????????????????down???
      * @param departmentId ????????????????????????downList???????????????departmentId
      */
     private void updateRemoveConfDown(String currentDepartmentId) {
         // 1.1 ?????????
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
         // 1.2 ?????????
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
     // ----??????????????????????????????-----
}
