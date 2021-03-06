package com.apih5.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzPermissionMapper;
import com.apih5.mybatis.dao.ZjTzPermissionSelectMapper;
import com.apih5.mybatis.dao.ZjTzPermissionUserMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzPermission;
import com.apih5.mybatis.pojo.ZjTzPermissionSelect;
import com.apih5.mybatis.pojo.ZjTzPermissionUser;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPermissionService")
public class ZjTzPermissionServiceImpl implements ZjTzPermissionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPermissionMapper zjTzPermissionMapper;
    
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;

    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;

    @Autowired(required = true)
    private ZjTzPermissionSelectMapper zjTzPermissionSelectMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionUserMapper zjTzPermissionUserMapper;
    
    @Override
    public ResponseEntity getZjTzPermissionListByCondition(ZjTzPermission zjTzPermission) {
        if (zjTzPermission == null) {
            zjTzPermission = new ZjTzPermission();
        }
        // ????????????
        PageHelper.startPage(zjTzPermission.getPage(),zjTzPermission.getLimit());
        // ????????????
        List<ZjTzPermission> zjTzPermissionList = zjTzPermissionMapper.selectByZjTzPermissionList(zjTzPermission);
        if(zjTzPermissionList != null && zjTzPermissionList.size()>0) {
            for(ZjTzPermission dbZjTzPermission:zjTzPermissionList) {
                ZjTzPermissionUser zjTzPermissionUser = new ZjTzPermissionUser();
                zjTzPermissionUser.setPermissionId(dbZjTzPermission.getPermissionId());
                List<ZjTzPermissionUser> zjTzPermissionUserList = zjTzPermissionUserMapper.selectByZjTzPermissionUserList(zjTzPermissionUser);
                dbZjTzPermission.setZjTzPermissionUserList(zjTzPermissionUserList);
            }
        }
        // ??????????????????
        PageInfo<ZjTzPermission> p = new PageInfo<>(zjTzPermissionList);

        return repEntity.okList(zjTzPermissionList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPermissionDetails(ZjTzPermission zjTzPermission) {
        if (zjTzPermission == null) {
            zjTzPermission = new ZjTzPermission();
        }
        // ????????????
        ZjTzPermission dbZjTzPermission = zjTzPermissionMapper.selectByPrimaryKey(zjTzPermission.getPermissionId());
        // ????????????
        if (dbZjTzPermission != null) {
            return repEntity.ok(dbZjTzPermission);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity saveZjTzPermission(ZjTzPermission zjTzPermission) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPermission.setPermissionId(UuidUtil.generate());
        zjTzPermission.setCreateUserInfo(userKey, realName);
        int flag = zjTzPermissionMapper.insert(zjTzPermission);
        
        List<ZjTzPermissionUser> zjTzPermissionUserList = zjTzPermission.getZjTzPermissionUserList();
        if(zjTzPermissionUserList != null && zjTzPermissionUserList.size()>0) {
            for(ZjTzPermissionUser zjTzPermissionUser:zjTzPermissionUserList) {
                zjTzPermissionUser.setPermissionUserId(UuidUtil.generate());
                // ??????ID
                zjTzPermissionUser.setPermissionId(zjTzPermission.getPermissionId());
                zjTzPermissionUser.setCreateUserInfo(userKey, realName);
                flag = zjTzPermissionUserMapper.insert(zjTzPermissionUser);

                // ?????????????????????????????????????????????????????????????????????
                if(StrUtil.equals("1", zjTzPermissionUser.getType())) {
                    SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(zjTzPermissionUser.getValue());
                    if(StrUtil.equals("4", sysDepartment.getDepartmentFlag())) {
                        ZjTzPermissionUser zjTzPermissionUserSelect = new ZjTzPermissionUser();
                        zjTzPermissionUserSelect.setPermissionId(zjTzPermission.getPermissionId());
                        zjTzPermissionUserSelect.setValue(sysDepartment.getCompanyId());
                        List<ZjTzPermissionUser> dbZjTzPermissionUserList = zjTzPermissionUserMapper.selectByZjTzPermissionUserList(zjTzPermissionUserSelect);
                        if(dbZjTzPermissionUserList == null || dbZjTzPermissionUserList.size() == 0) {
                            zjTzPermissionUser.setPermissionUserId(UuidUtil.generate());
                            // ??????ID
                            zjTzPermissionUser.setPermissionId(zjTzPermission.getPermissionId());
                            zjTzPermissionUser.setType("3");
                            zjTzPermissionUser.setValue(sysDepartment.getCompanyId());
                            zjTzPermissionUser.setLabel(sysDepartment.getCompanyName());
                            zjTzPermissionUser.setCreateUserInfo(userKey, realName);
                            flag = zjTzPermissionUserMapper.insert(zjTzPermissionUser);
                        }
                    }
                }
            }
        }
        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPermission);
        }
    }

    @Override
    public ResponseEntity updateZjTzPermission(ZjTzPermission zjTzPermission) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPermission dbzjTzPermission = zjTzPermissionMapper.selectByPrimaryKey(zjTzPermission.getPermissionId());
        if (dbzjTzPermission != null && StrUtil.isNotEmpty(dbzjTzPermission.getPermissionId())) {
           // ????????????
           dbzjTzPermission.setPermissionName(zjTzPermission.getPermissionName());
           // ??????ID
           dbzjTzPermission.setProjectId(zjTzPermission.getProjectId());
           // ????????????
           dbzjTzPermission.setProjectName(zjTzPermission.getProjectName());
           // ????????????
           dbzjTzPermission.setProjectShortName(zjTzPermission.getProjectShortName());
           // ext1
           dbzjTzPermission.setExt1(zjTzPermission.getExt1());
           // ext2
           dbzjTzPermission.setExt2(zjTzPermission.getExt2());
           // ext3
           dbzjTzPermission.setExt3(zjTzPermission.getExt3());
           // ext4
           dbzjTzPermission.setExt4(zjTzPermission.getExt4());
           // ext5
           dbzjTzPermission.setExt5(zjTzPermission.getExt5());
           // ext6
           dbzjTzPermission.setExt6(zjTzPermission.getExt6());
           // ??????
           dbzjTzPermission.setRemark(zjTzPermission.getRemark());
           // ??????
           dbzjTzPermission.setModifyUserInfo(userKey, realName);
           flag = zjTzPermissionMapper.updateByPrimaryKey(dbzjTzPermission);
       
           
        // ?????????????????????
           ZjTzPermissionUser zjTzPermissionUserDelete = new ZjTzPermissionUser();
           zjTzPermissionUserDelete.setPermissionId(dbzjTzPermission.getPermissionId());
           List<ZjTzPermissionUser> dbZjTzPermissionUserList = zjTzPermissionUserMapper.selectByZjTzPermissionUserList(zjTzPermissionUserDelete);
           if(dbZjTzPermissionUserList != null && dbZjTzPermissionUserList.size()>0) {
        	   zjTzPermissionUserDelete.setModifyUserInfo(userKey, realName);
        	   zjTzPermissionUserMapper.batchDeleteUpdateZjTzPermissionUser(dbZjTzPermissionUserList,zjTzPermissionUserDelete);
           }
           
           // ????????????
           List<ZjTzPermissionUser> zjTzPermissionUserList = zjTzPermission.getZjTzPermissionUserList();
           for(ZjTzPermissionUser zjTzPermissionUser:zjTzPermissionUserList) {
           	zjTzPermissionUser.setPermissionUserId(UuidUtil.generate());
           	// ??????ID
           	zjTzPermissionUser.setPermissionId(zjTzPermission.getPermissionId());
               zjTzPermissionUser.setCreateUserInfo(userKey, realName);
               flag = zjTzPermissionUserMapper.insert(zjTzPermissionUser);
           }
           
           
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPermission);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPermission(List<ZjTzPermission> zjTzPermissionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPermissionList != null && zjTzPermissionList.size() > 0) {
           ZjTzPermission zjTzPermission = new ZjTzPermission();
           zjTzPermission.setModifyUserInfo(userKey, realName);
           flag = zjTzPermissionMapper.batchDeleteUpdateZjTzPermission(zjTzPermissionList, zjTzPermission);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPermissionList);
        }
    }
    
    // --??????
    /**
     * ????????????????????????-????????????
     */
    @Override
    public ResponseEntity getZjTzPermissionListByProject(ZjTzPermission zjTzPermission) {
        if (zjTzPermission == null) {
            zjTzPermission = new ZjTzPermission();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        // ?????????1????????????
        String ext1 = TokenUtils.getExt1(request);
        
        // ??????????????????
        ZjTzPermissionSelect zjTzPermissionSelectFlag = new ZjTzPermissionSelect();
        ZjTzPermissionSelect zjTzPermissionSelectSelect = new ZjTzPermissionSelect();
        zjTzPermissionSelectSelect.setUserKey(userKey);
        List<ZjTzPermissionSelect> zjTzPermissionSelectList = zjTzPermissionSelectMapper.selectByZjTzPermissionSelectList(zjTzPermissionSelectSelect);
        if(zjTzPermissionSelectList != null && zjTzPermissionSelectList.size()>0) {
            zjTzPermissionSelectFlag = zjTzPermissionSelectList.get(0);
        }

        List<ZjTzPermission> zjTzPermissionList = Lists.newArrayList();
        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ?????????sql???in??????
            String sqlIn = "";
            // ??????????????????????????????Id???userKey
            List<String> permissionList = TokenUtils.getPermissionIds(request);
            for (int i=0; i<permissionList.size(); i++) {
                String departmentId = permissionList.get(i);
                SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(departmentId);
                if(sysDepartment != null) {
                    String[] sysDepartments = sysDepartment.getDepartmentPath().split(",");
                    for(String sysDepartmentId:sysDepartments) {
                        sqlIn += "'" + sysDepartmentId + "',";
                    }
                } else {
                    sqlIn += "'" + departmentId + "',";
                }
            }
            sqlIn = sqlIn.substring(0, sqlIn.length() - 1);
            // ????????????
            ZjTzPermission zjTzPermissionSelect = new ZjTzPermission();
            zjTzPermissionSelect.setValue(sqlIn);
            // sys???ext1???1=??????2???????????????3??????????????????4????????????
            if(StrUtil.equals("4", ext1)) {
                // ?????????????????????????????????????????????
                zjTzPermissionSelect.setType("t2.type != '3'");
            }
            zjTzPermissionSelect.setProjectShortName(zjTzPermission.getProjectShortName());
            zjTzPermissionList = zjTzPermissionMapper.selectZjTzPermissionListByProject(zjTzPermissionSelect);
            if(zjTzPermissionList != null && zjTzPermissionList.size()>0) {
                for(ZjTzPermission dbZjTzPermission:zjTzPermissionList) {
                    if(StrUtil.equals(dbZjTzPermission.getProjectId(), zjTzPermissionSelectFlag.getProjectId())) {
                        ZjTzProManage zjTzProManage = zjTzProManageMapper.selectByPrimaryKey(dbZjTzPermission.getProjectId());
                        dbZjTzPermission.setProProcessId(zjTzProManage.getProProcessId());
                        dbZjTzPermission.setSelectFlag("1");
                    }
                }
            } 
        } else {
            ZjTzProManage zjTzProManagementSelect = new ZjTzProManage();
            zjTzProManagementSelect.setProjectShortName(zjTzPermission.getProjectShortName());
            List<ZjTzProManage> zjTzProManagemenList = zjTzProManageMapper.selectByZjTzProManageList(zjTzProManagementSelect);
            if(zjTzProManagemenList != null && zjTzProManagemenList.size()>0) {
                for(ZjTzProManage zjTzProManagemen:zjTzProManagemenList) {
                    ZjTzPermission zjTzPermissionProject = new ZjTzPermission();
                    // ??????ID
                    zjTzPermissionProject.setProjectId(zjTzProManagemen.getProjectId());
                    // ????????????
                    zjTzPermissionProject.setProjectName(zjTzProManagemen.getProjectName());
                    // ????????????
                    zjTzPermissionProject.setProjectShortName(zjTzProManagemen.getProjectShortName());
                    // ??????id?????????
                    zjTzPermissionProject.setCompanyId(zjTzProManagemen.getCompanyId());
                    zjTzPermissionProject.setCompanyName(zjTzProManagemen.getCompanyName());
                    // ????????????
                    zjTzPermissionProject.setProProcessId(zjTzProManagemen.getProProcessId());
                    // ??????????????????
                    zjTzPermissionProject.setAnalySubject(zjTzProManagemen.getAnalySubject());
                    // ??????????????????
                    zjTzPermissionProject.setSizeControlSubject(zjTzProManagemen.getSizeControlSubject());
                    // ?????????????????????
//                    if(StrUtil.equals(zjTzProManagemen.getProjectId(), zjTzPermissionSelectFlag.getProjectId())) {
//                        zjTzPermissionProject.setSelectFlag("1");
//                    }
                    zjTzPermissionList.add(zjTzPermissionProject);
                }
            }
        }
        
        boolean defaultProFlag = false;
        // ?????????????????? && ?????????????????????
        if((zjTzPermissionList == null || zjTzPermissionList.size()==0)
                && StrUtil.isEmpty(zjTzPermission.getProjectShortName())) {
            zjTzPermissionList = Lists.newArrayList();
            ZjTzPermission zjTzPermissionProject = new ZjTzPermission();
            // ??????ID
            zjTzPermissionProject.setProjectId("9999999999");
            // ????????????
            zjTzPermissionProject.setProjectName("?????????????????????");
            // ????????????
            zjTzPermissionProject.setProjectShortName("?????????????????????");
            zjTzPermissionProject.setSelectFlag("1");
            zjTzPermissionList.add(zjTzPermissionProject);
            defaultProFlag = true;
        }
        
        // ??????LevelId???????????????
        Set<ZjTzPermission> set = new TreeSet<>(
        Comparator.comparing(ZjTzPermission::getProjectId));
        set.addAll(zjTzPermissionList);
        zjTzPermissionList.clear();
        zjTzPermissionList.addAll(set);
        
        // ?????????????????????
        if(!defaultProFlag && !StrUtil.equals("0", zjTzPermission.getAllFlag())) {
            List<ZjTzPermission> zjTzPermissionListAll = Lists.newArrayList();
            ZjTzPermission zjTzPermissionAll = new ZjTzPermission();
            zjTzPermissionAll.setProjectId("all");
            zjTzPermissionAll.setProjectName("????????????");
            // ?????????????????????
            if(StrUtil.equals("1", ext1)) {
                zjTzPermissionAll.setSelectFlag("1");
            }
            zjTzPermissionAll.setProjectShortName("????????????");
            zjTzPermissionListAll.add(zjTzPermissionAll);
            zjTzPermissionList.addAll(0, zjTzPermissionListAll);
        }
        
        return repEntity.ok(zjTzPermissionList);
    }
    
    @Override
    public String getZjtzProjectSql() {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // ?????????1????????????
        String ext1 = TokenUtils.getExt1(request);
        List<String> permissionList = TokenUtils.getPermissionIds(request);

        // ?????????sql???in??????
        String projectSqlIn = "";
        
        // ?????????sql???in??????
        String sqlIn = "";
        // ??????????????????????????????Id???userKey
        for (int i=0; i<permissionList.size(); i++) {
            String departmentId = permissionList.get(i);
            SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(departmentId);
            if(sysDepartment != null) {
                String[] sysDepartments = sysDepartment.getDepartmentPath().split(",");
                for(String sysDepartmentId:sysDepartments) {
                    sqlIn += "'" + sysDepartmentId + "',";
                }
            } else {
                sqlIn += "'" + departmentId + "',";
            }
        }
        if (sqlIn.length() > 0) {
			
        	sqlIn = sqlIn.substring(0, sqlIn.length() - 1);
		}

        // ??????????????????
        ZjTzPermission zjTzPermissionSelect = new ZjTzPermission();
        zjTzPermissionSelect.setValue(sqlIn);
        if(StrUtil.equals("4", ext1)) {
            zjTzPermissionSelect.setType("t2.type != '3'");
        }
        List<ZjTzPermission> zjTzPermissionList = zjTzPermissionMapper.selectZjTzPermissionListByProject(zjTzPermissionSelect);
        if(zjTzPermissionList != null && zjTzPermissionList.size()>0) {
            for(ZjTzPermission dbZjTzPermission:zjTzPermissionList) {
                projectSqlIn += "'" + dbZjTzPermission.getProjectId() + "',";
            }
        } else {
            projectSqlIn += "'all',";
        }
        projectSqlIn = projectSqlIn.substring(0, projectSqlIn.length() - 1);
        return projectSqlIn;
    }
    
    /**
     * 
     * ??????
     */
    
    @Override
    public String getZjtzProjectSqlForHome() {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        // ?????????1????????????
        String ext1 = TokenUtils.getExt1(request);

        // ?????????sql???in??????
        String projectSqlIn = "";
        
        // ?????????sql???in??????
        String sqlIn = "";
        // ??????????????????????????????Id???userKey
        List<SysDepartment> deptList = sysDepartmentService.getSysDepartmentByUserkey(userKey);
        if(deptList != null && deptList.size() >0) {
        	for (SysDepartment sysDepartment : deptList) {
        		 sqlIn += "'" + sysDepartment.getDepartmentId() + "',";
			}
        }else {
        	sqlIn += "'" + userKey + "',";
        }
        
        sqlIn = sqlIn.substring(0, sqlIn.length() - 1);

        // ??????????????????
        ZjTzPermission zjTzPermissionSelect = new ZjTzPermission();
        zjTzPermissionSelect.setValue(sqlIn);
        if(StrUtil.equals("4", ext1)) {
            zjTzPermissionSelect.setType("t2.type != '3'");
        }
        List<ZjTzPermission> zjTzPermissionList = zjTzPermissionMapper.selectZjTzPermissionListByProject(zjTzPermissionSelect);
        if(zjTzPermissionList != null && zjTzPermissionList.size()>0) {
            for(ZjTzPermission dbZjTzPermission:zjTzPermissionList) {
                projectSqlIn += "'" + dbZjTzPermission.getProjectId() + "',";
            }
        } else {
            projectSqlIn += "'all',";
        }
        projectSqlIn = projectSqlIn.substring(0, projectSqlIn.length() - 1);
        return projectSqlIn;
    }
    
    
    
    /**
     * ?????????????????????????????????????????????ID
     * 
     * @return departmentId
     */
    @Override
    public String getZjtzSysDepartmentIdType(ZjTzPermission zjTzPermission) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        List<String> permissionList = TokenUtils.getPermissionIds(request);
        // ??????????????????????????????
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("2", ext1)) {
            // ?????????
            return companyId;
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            // ???????????????????????????
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentFlag("4");//??????
            String sysDepartmentId = permissionList.get(permissionList.size()-1);
            sysDepartment.setDepartmentPath(sysDepartmentId);
            List<SysDepartment> sysDepartmentList = sysDepartmentService.selectBySysDepartmentList(sysDepartment);
            return sysDepartmentList.get(0).getDepartmentId();
        }
        return "";
    }
}
