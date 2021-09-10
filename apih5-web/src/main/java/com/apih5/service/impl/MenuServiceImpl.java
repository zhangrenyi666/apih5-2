package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.mybatis.dao.MenuMapper;
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.TreeNode;
import com.apih5.service.MenuService;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResponseEntity getUserMenuList() {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	SysMenu sysMenu = new SysMenu();
    	sysMenu.setUserKey(userKey);
    	// 得到用户对应的菜单信息
        List<SysMenu> menuList = menuMapper.getMenuList(sysMenu);
    	List<List<SysMenu>> sList = Lists.newArrayList();
    	menuList.forEach(t -> {
        	// 如果是一个根节点
    		if(StrUtil.equals(t.getMenuParentId(), "0")) {
            	List<SysMenu> smList = Lists.newArrayList();
            	smList.add(t);
            	sList.add(smList);
    		}
    		// 如果是上一个根节点的子节点
    		else {
    			sList.get(sList.size() - 1).add(t);
    		}
    	});
        return repEntity.okList(sList, sList.size());
    }
    
//    @Override
//    public List<SysMenu> getMenuList() {
//    	SysMenu sysMenu = new SysMenu();
//        return menuMapper.getMenuList(sysMenu);
//    }
    
    @Override
    public List<Integer> getMenuIdList(int userId) {
        return menuMapper.getMenuIdList(userId);
    }
    
    @Override
    public List<TreeNode> getMenuTreeList() {
        return menuMapper.getMenuTreeList();
    }

    @Override
    public void updateUrl(String prefix) {
        menuMapper.updateUrl(prefix);
    }
}
