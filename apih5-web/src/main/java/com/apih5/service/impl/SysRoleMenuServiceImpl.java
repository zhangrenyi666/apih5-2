package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysMenuMapper;
import com.apih5.mybatis.dao.SysRoleMenuMapper;
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.SysRoleMenu;
import com.apih5.service.SysRoleMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysRoleMenuMapper sysRoleMenuMapper;
    
    @Autowired(required = true)
    private SysMenuMapper sysMenuMapper;

    @Override
    public ResponseEntity getSysRoleMenuListByCondition(SysRoleMenu sysRoleMenu) {
        if (sysRoleMenu == null) {
            sysRoleMenu = new SysRoleMenu();
        }
        // 分页查询
        PageHelper.startPage(sysRoleMenu.getPage(),sysRoleMenu.getLimit());
        // 获取数据
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectBySysRoleMenuList(sysRoleMenu);
        // 得到分页信息
        PageInfo<SysRoleMenu> p = new PageInfo<>(sysRoleMenuList);

        return repEntity.okList(sysRoleMenuList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysRoleMenu(SysRoleMenu sysRoleMenu) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysRoleMenu.setRoleMenuId(UuidUtil.generate());
        sysRoleMenu.setCreateUserInfo(userKey, realName);
        int flag = sysRoleMenuMapper.insert(sysRoleMenu);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysRoleMenu);
        }
    }

    @Override
    public ResponseEntity updateSysRoleMenu(SysRoleMenu sysRoleMenu) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        
        String roleId = sysRoleMenu.getRoleId();
        
        // 获取数据
        SysRoleMenu sysRoleMenuSelect = new SysRoleMenu();
        sysRoleMenuSelect.setRoleId(roleId);
        List<SysRoleMenu> sysRoleMenuDbList = sysRoleMenuMapper.selectBySysRoleMenuList(sysRoleMenuSelect);
        if(sysRoleMenuDbList != null && sysRoleMenuDbList.size() > 0) {
        	flag = sysRoleMenuMapper.batchDeleteUpdateSysRoleMenu(sysRoleMenuDbList);
        }
        
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenu.getSysRoleMenuList();
        for(SysRoleMenu sysRoleMenuPage:sysRoleMenuList) {
        	sysRoleMenuPage.setRoleMenuId(UuidUtil.generate());
        	sysRoleMenuPage.setRoleId(roleId);
        	SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(sysRoleMenuPage.getValue());
        	// 按钮=2 并且父节点
        	
        	// 如果是按钮时，需要添加父节点数据到权限关联表中，否则菜单看不见（因为获取时，
        	if(StrUtil.equals("2", sysMenu.getMenuFlag())) {
        	    SysRoleMenu sysRoleMenuPid = new SysRoleMenu();
        	    BeanUtil.copyProperties(sysRoleMenuPage, sysRoleMenuPid);
        	    sysRoleMenuPid.setRoleMenuId(UuidUtil.generate());
        	    sysRoleMenuPid.setRoleId(roleId);
        	    sysRoleMenuPid.setMenuFlag("1");
        	    sysRoleMenuPid.setMenuParentId("");
        	    sysRoleMenuPid.setValue(sysMenu.getMenuParentId());
        	    sysRoleMenuPid.setCreateUserInfo(userKey, realName);
        	    
        	    // 根据角色id和当前菜单id判断，如果存在则不重复插入
                SysRoleMenu sysRoleMenuSelectRepeat = new SysRoleMenu();
                sysRoleMenuSelectRepeat.setRoleId(roleId);
                // 菜单ID
                sysRoleMenuSelectRepeat.setValue(sysRoleMenuPid.getValue());
                List<SysRoleMenu> sysRoleMenuDbListRepeat = sysRoleMenuMapper.selectBySysRoleMenuList(sysRoleMenuSelectRepeat);
                if(sysRoleMenuDbListRepeat == null || sysRoleMenuDbListRepeat.size()==0) {
                    flag = sysRoleMenuMapper.insert(sysRoleMenuPid);
                }
        	}
    	    sysRoleMenuPage.setMenuFlag(sysMenu.getMenuFlag());
    	    sysRoleMenuPage.setMenuParentId(sysMenu.getMenuParentId());
    	    sysRoleMenuPage.setCreateUserInfo(userKey, realName);
    	    flag = sysRoleMenuMapper.insert(sysRoleMenuPage);
        }
        
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update", sysRoleMenu);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysRoleMenu(List<SysRoleMenu> sysRoleMenuList) {
        int flag = 0;
        if (sysRoleMenuList != null && sysRoleMenuList.size() > 0) {
//           flag = sysRoleMenuMapper.batchDeleteUpdateSysRoleMenu(sysRoleMenuList);
        	for(SysRoleMenu sysRoleMenu:sysRoleMenuList) {
        		flag = sysRoleMenuMapper.batchDeleteUpdateSysRoleMenu(sysRoleMenuMapper.selectBySysRoleMenuList(sysRoleMenu));
        	}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysRoleMenuList);
        }
    }
    
    // ---扩展
    @Override
    public ResponseEntity getSysRoleMenuListByRole(SysRoleMenu sysRoleMenu) {
        if (sysRoleMenu == null) {
            sysRoleMenu = new SysRoleMenu();
        }
        List<SysRoleMenu> newSysRoleMenuList = Lists.newArrayList();
        // 获取数据（只获取菜单，按钮不获取）
        sysRoleMenu.setMenuFlag("1");
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectBySysRoleMenuList(sysRoleMenu);
        for(SysRoleMenu dbSysRoleMenu:sysRoleMenuList) {
            // 通过menuId查询menu表的子按钮
            SysMenu sysMenuSelect = new SysMenu();
            sysMenuSelect.setMenuFlag("2"); // 按钮
            sysMenuSelect.setMenuParentId(dbSysRoleMenu.getValue()); // menuId
            List<SysMenu> sysMenuList = sysMenuMapper.selectBySysMenuList(sysMenuSelect);
            
            // 授权菜单数据
            SysRoleMenu sysRoleMenuBtn = new SysRoleMenu(); 
            sysRoleMenuBtn.setMenuFlag("2");
            sysRoleMenuBtn.setMenuParentId(dbSysRoleMenu.getValue());
            sysRoleMenuBtn.setRoleId(dbSysRoleMenu.getRoleId());
            List<SysRoleMenu> sysRoleMenuListBtn = sysRoleMenuMapper.selectBySysRoleMenuList(sysRoleMenuBtn);
            // 如果数量和当前授权表的数量一致，说明按钮全部授权，则只返回父菜单即可
            if(sysMenuList != null && sysRoleMenuListBtn != null
                    && sysMenuList.size() == sysRoleMenuListBtn.size()) {
                newSysRoleMenuList.add(dbSysRoleMenu);
            } else {
                // 否则，不返回父菜单，返回具体按钮明细（即当前授权表的信息）
                newSysRoleMenuList.addAll(sysRoleMenuListBtn);
            }
            
        }
        return repEntity.ok(newSysRoleMenuList);
    }
}
