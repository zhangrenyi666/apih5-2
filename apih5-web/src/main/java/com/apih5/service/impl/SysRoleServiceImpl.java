package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TreeNodeEntity;
import com.apih5.framework.entity.TreeNodeMerger;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysRoleMapper;
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.SysRole;
import com.apih5.service.SysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysRoleMapper sysRoleMapper;

    @Override
    public ResponseEntity getSysRoleListByCondition(SysRole sysRole) {
        if (sysRole == null) {
            sysRole = new SysRole();
        }
        // 分页查询
        PageHelper.startPage(sysRole.getPage(),sysRole.getLimit());
        // 获取数据
        List<SysRole> sysRoleList = sysRoleMapper.selectBySysRoleList(sysRole);
        // 得到分页信息
        PageInfo<SysRole> p = new PageInfo<>(sysRoleList);

        return repEntity.okList(sysRoleList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysRole(SysRole sysRole) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysRole.setRoleId(UuidUtil.generate());
        sysRole.setCreateUserInfo(userKey, realName);
        int flag = sysRoleMapper.insert(sysRole);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysRole);
        }
    }

    @Override
    public ResponseEntity updateSysRole(SysRole sysRole) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysRole dbsysRole = sysRoleMapper.selectByPrimaryKey(sysRole.getRoleId());
        if (dbsysRole != null && StrUtil.isNotEmpty(dbsysRole.getRoleId())) {
        	dbsysRole.setRoleName(sysRole.getRoleName());
        	// 共通
        	dbsysRole.setModifyUserInfo(userKey, realName);
        	flag = sysRoleMapper.updateByPrimaryKey(dbsysRole);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysRole);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysRole(List<SysRole> sysRoleList) {
        int flag = 0;
        if (sysRoleList != null && sysRoleList.size() > 0) {
           flag = sysRoleMapper.batchDeleteUpdateSysRole(sysRoleList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysRoleList);
        }
   }
    
    /**
	 * 获取菜单节点，并形成树形结构
	 * 
	 * @return 树形结构
	 */
	@Override
	public ResponseEntity getSysRoleAllTree(SysRole sysRole) {
		if(sysRole == null){
			sysRole = new SysRole();
		}
		
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String ext1 = TokenUtils.getExt1(request);
        
        // ext1=1,集团
        // ext1=2,公司
        // ext1=3,项目
        // ext1=4,项目
        
        SysRole sysRoleSelect = new SysRole();
        if(StrUtil.equals("1", ext1)) {
            
        } else if(StrUtil.equals("2", ext1)) {
            sysRoleSelect.setDepartmentId(sysRole.getDepartmentId());
        } else if(StrUtil.equals("3", ext1)
                || StrUtil.equals("4", ext1)) {
            sysRoleSelect.setDepartmentId(sysRole.getDepartmentId());
        }
		
		// 获取全部的部门
		List<SysRole> sysRoleList = sysRoleMapper.selectBySysRoleList(sysRoleSelect);

		
		TreeNodeEntity[] treeNodes = new TreeNodeEntity[sysRoleList.size()+1];
		// 添加根节点
		treeNodes[0] = new TreeNodeEntity("0", "1", "0","权限设置", null);
		
		int i = 1;
		// 合并部门&人员信息 【特殊条件，类似分公司的时候departmentId, dbSysDepartment.getDepartmentId()】
		for (SysRole sysRoleDb : sysRoleList) {
			treeNodes[i] = new TreeNodeEntity("1", sysRoleDb.getRoleId(), "1", sysRoleDb.getRoleName(), null);
			i++;
		}
		
		TreeNodeEntity treeNode = TreeNodeMerger.merge(treeNodes);
		return repEntity.ok(treeNode);
		
		
//		JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysRoleList), "roleId", "1", "roleName", "roleName");
//		return this.repEntity.ok(jsonArray);
	}
	
}
