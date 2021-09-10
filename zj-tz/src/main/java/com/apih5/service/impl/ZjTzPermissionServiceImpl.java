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
        // 分页查询
        PageHelper.startPage(zjTzPermission.getPage(),zjTzPermission.getLimit());
        // 获取数据
        List<ZjTzPermission> zjTzPermissionList = zjTzPermissionMapper.selectByZjTzPermissionList(zjTzPermission);
        if(zjTzPermissionList != null && zjTzPermissionList.size()>0) {
            for(ZjTzPermission dbZjTzPermission:zjTzPermissionList) {
                ZjTzPermissionUser zjTzPermissionUser = new ZjTzPermissionUser();
                zjTzPermissionUser.setPermissionId(dbZjTzPermission.getPermissionId());
                List<ZjTzPermissionUser> zjTzPermissionUserList = zjTzPermissionUserMapper.selectByZjTzPermissionUserList(zjTzPermissionUser);
                dbZjTzPermission.setZjTzPermissionUserList(zjTzPermissionUserList);
            }
        }
        // 得到分页信息
        PageInfo<ZjTzPermission> p = new PageInfo<>(zjTzPermissionList);

        return repEntity.okList(zjTzPermissionList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPermissionDetails(ZjTzPermission zjTzPermission) {
        if (zjTzPermission == null) {
            zjTzPermission = new ZjTzPermission();
        }
        // 获取数据
        ZjTzPermission dbZjTzPermission = zjTzPermissionMapper.selectByPrimaryKey(zjTzPermission.getPermissionId());
        // 数据存在
        if (dbZjTzPermission != null) {
            return repEntity.ok(dbZjTzPermission);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
                // 权限ID
                zjTzPermissionUser.setPermissionId(zjTzPermission.getPermissionId());
                zjTzPermissionUser.setCreateUserInfo(userKey, realName);
                flag = zjTzPermissionUserMapper.insert(zjTzPermissionUser);

                // 授权给项目时，同时把公司权限也给了，但是要隐藏
                if(StrUtil.equals("1", zjTzPermissionUser.getType())) {
                    SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(zjTzPermissionUser.getValue());
                    if(StrUtil.equals("4", sysDepartment.getDepartmentFlag())) {
                        ZjTzPermissionUser zjTzPermissionUserSelect = new ZjTzPermissionUser();
                        zjTzPermissionUserSelect.setPermissionId(zjTzPermission.getPermissionId());
                        zjTzPermissionUserSelect.setValue(sysDepartment.getCompanyId());
                        List<ZjTzPermissionUser> dbZjTzPermissionUserList = zjTzPermissionUserMapper.selectByZjTzPermissionUserList(zjTzPermissionUserSelect);
                        if(dbZjTzPermissionUserList == null || dbZjTzPermissionUserList.size() == 0) {
                            zjTzPermissionUser.setPermissionUserId(UuidUtil.generate());
                            // 权限ID
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
           // 权限名称
           dbzjTzPermission.setPermissionName(zjTzPermission.getPermissionName());
           // 项目ID
           dbzjTzPermission.setProjectId(zjTzPermission.getProjectId());
           // 项目名称
           dbzjTzPermission.setProjectName(zjTzPermission.getProjectName());
           // 项目简称
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
           // 备注
           dbzjTzPermission.setRemark(zjTzPermission.getRemark());
           // 共通
           dbzjTzPermission.setModifyUserInfo(userKey, realName);
           flag = zjTzPermissionMapper.updateByPrimaryKey(dbzjTzPermission);
       
           
        // 权限删除再添加
           ZjTzPermissionUser zjTzPermissionUserDelete = new ZjTzPermissionUser();
           zjTzPermissionUserDelete.setPermissionId(dbzjTzPermission.getPermissionId());
           List<ZjTzPermissionUser> dbZjTzPermissionUserList = zjTzPermissionUserMapper.selectByZjTzPermissionUserList(zjTzPermissionUserDelete);
           if(dbZjTzPermissionUserList != null && dbZjTzPermissionUserList.size()>0) {
        	   zjTzPermissionUserDelete.setModifyUserInfo(userKey, realName);
        	   zjTzPermissionUserMapper.batchDeleteUpdateZjTzPermissionUser(dbZjTzPermissionUserList,zjTzPermissionUserDelete);
           }
           
           // 权限对象
           List<ZjTzPermissionUser> zjTzPermissionUserList = zjTzPermission.getZjTzPermissionUserList();
           for(ZjTzPermissionUser zjTzPermissionUser:zjTzPermissionUserList) {
           	zjTzPermissionUser.setPermissionUserId(UuidUtil.generate());
           	// 权限ID
           	zjTzPermissionUser.setPermissionId(zjTzPermission.getPermissionId());
               zjTzPermissionUser.setCreateUserInfo(userKey, realName);
               flag = zjTzPermissionUserMapper.insert(zjTzPermissionUser);
           }
           
           
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPermissionList);
        }
    }
    
    // --扩展
    /**
     * 查询项目数据权限-项目获取
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
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        
        // 设置选中项目
        ZjTzPermissionSelect zjTzPermissionSelectFlag = new ZjTzPermissionSelect();
        ZjTzPermissionSelect zjTzPermissionSelectSelect = new ZjTzPermissionSelect();
        zjTzPermissionSelectSelect.setUserKey(userKey);
        List<ZjTzPermissionSelect> zjTzPermissionSelectList = zjTzPermissionSelectMapper.selectByZjTzPermissionSelectList(zjTzPermissionSelectSelect);
        if(zjTzPermissionSelectList != null && zjTzPermissionSelectList.size()>0) {
            zjTzPermissionSelectFlag = zjTzPermissionSelectList.get(0);
        }

        List<ZjTzPermission> zjTzPermissionList = Lists.newArrayList();
        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 组合成sql的in条件
            String sqlIn = "";
            // 获取登陆者的所有部门Id和userKey
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
            // 查询条件
            ZjTzPermission zjTzPermissionSelect = new ZjTzPermission();
            zjTzPermissionSelect.setValue(sqlIn);
            // sys的ext1：1=局；2托管公司；3、直属项目；4托管项目
            if(StrUtil.equals("4", ext1)) {
                // 托管公司的人，不能查看直属项目
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
                    // 项目ID
                    zjTzPermissionProject.setProjectId(zjTzProManagemen.getProjectId());
                    // 项目名称
                    zjTzPermissionProject.setProjectName(zjTzProManagemen.getProjectName());
                    // 项目简称
                    zjTzPermissionProject.setProjectShortName(zjTzProManagemen.getProjectShortName());
                    // 公司id、名称
                    zjTzPermissionProject.setCompanyId(zjTzProManagemen.getCompanyId());
                    zjTzPermissionProject.setCompanyName(zjTzProManagemen.getCompanyName());
                    // 项目类型
                    zjTzPermissionProject.setProProcessId(zjTzProManagemen.getProProcessId());
                    // 项目分析主体
                    zjTzPermissionProject.setAnalySubject(zjTzProManagemen.getAnalySubject());
                    // 规模控制主体
                    zjTzPermissionProject.setSizeControlSubject(zjTzProManagemen.getSizeControlSubject());
                    // 局用户默认全部
//                    if(StrUtil.equals(zjTzProManagemen.getProjectId(), zjTzPermissionSelectFlag.getProjectId())) {
//                        zjTzPermissionProject.setSelectFlag("1");
//                    }
                    zjTzPermissionList.add(zjTzPermissionProject);
                }
            }
        }
        
        boolean defaultProFlag = false;
        // 得到数据为空 && 非项目简称检索
        if((zjTzPermissionList == null || zjTzPermissionList.size()==0)
                && StrUtil.isEmpty(zjTzPermission.getProjectShortName())) {
            zjTzPermissionList = Lists.newArrayList();
            ZjTzPermission zjTzPermissionProject = new ZjTzPermission();
            // 项目ID
            zjTzPermissionProject.setProjectId("9999999999");
            // 项目名称
            zjTzPermissionProject.setProjectName("默认无数据项目");
            // 项目简称
            zjTzPermissionProject.setProjectShortName("默认无数据项目");
            zjTzPermissionProject.setSelectFlag("1");
            zjTzPermissionList.add(zjTzPermissionProject);
            defaultProFlag = true;
        }
        
        // 根据LevelId，快速去重
        Set<ZjTzPermission> set = new TreeSet<>(
        Comparator.comparing(ZjTzPermission::getProjectId));
        set.addAll(zjTzPermissionList);
        zjTzPermissionList.clear();
        zjTzPermissionList.addAll(set);
        
        // 默认给一个全部
        if(!defaultProFlag && !StrUtil.equals("0", zjTzPermission.getAllFlag())) {
            List<ZjTzPermission> zjTzPermissionListAll = Lists.newArrayList();
            ZjTzPermission zjTzPermissionAll = new ZjTzPermission();
            zjTzPermissionAll.setProjectId("all");
            zjTzPermissionAll.setProjectName("所有项目");
            // 局用户默认全部
            if(StrUtil.equals("1", ext1)) {
                zjTzPermissionAll.setSelectFlag("1");
            }
            zjTzPermissionAll.setProjectShortName("所有项目");
            zjTzPermissionListAll.add(zjTzPermissionAll);
            zjTzPermissionList.addAll(0, zjTzPermissionListAll);
        }
        
        return repEntity.ok(zjTzPermissionList);
    }
    
    @Override
    public String getZjtzProjectSql() {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        List<String> permissionList = TokenUtils.getPermissionIds(request);

        // 组合成sql的in条件
        String projectSqlIn = "";
        
        // 组合成sql的in条件
        String sqlIn = "";
        // 获取登陆者的所有部门Id和userKey
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

        // 项目查询条件
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
     * 首页
     */
    
    @Override
    public String getZjtzProjectSqlForHome() {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);

        // 组合成sql的in条件
        String projectSqlIn = "";
        
        // 组合成sql的in条件
        String sqlIn = "";
        // 获取登陆者的所有部门Id和userKey
        List<SysDepartment> deptList = sysDepartmentService.getSysDepartmentByUserkey(userKey);
        if(deptList != null && deptList.size() >0) {
        	for (SysDepartment sysDepartment : deptList) {
        		 sqlIn += "'" + sysDepartment.getDepartmentId() + "',";
			}
        }else {
        	sqlIn += "'" + userKey + "',";
        }
        
        sqlIn = sqlIn.substring(0, sqlIn.length() - 1);

        // 项目查询条件
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
     * 根据用户获取当前所属公司或项目ID
     * 
     * @return departmentId
     */
    @Override
    public String getZjtzSysDepartmentIdType(ZjTzPermission zjTzPermission) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        List<String> permissionList = TokenUtils.getPermissionIds(request);
        // 投资事业部或分公司时
        if(StrUtil.equals("1", ext1)
                || StrUtil.equals("2", ext1)) {
            // 分公司
            return companyId;
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            // 项目（直属或托管）
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentFlag("4");//项目
            String sysDepartmentId = permissionList.get(permissionList.size()-1);
            sysDepartment.setDepartmentPath(sysDepartmentId);
            List<SysDepartment> sysDepartmentList = sysDepartmentService.selectBySysDepartmentList(sysDepartment);
            return sysDepartmentList.get(0).getDepartmentId();
        }
        return "";
    }
}
