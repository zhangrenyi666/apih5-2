package com.apih5.service;

import java.util.List;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.TreeNode;;

/**
 * 菜单Service
 */
public interface MenuService {

    /**
     * 根据用户ID得到菜单列表
     * @return
     */
	ResponseEntity getUserMenuList();

    /**
     * 根据用户ID得到菜单列表
     * @return
     */
//    List<SysMenu> getMenuList();
    
    /**
     * 根据用户ID得到菜单ID列表
     * @return
     */
    List<Integer> getMenuIdList(int userId);
    
    /**
     * 获得菜单列表树
     * @return
     */
    List<TreeNode> getMenuTreeList();

    /**
     * 更新网站监控和API文档的url
     * @param prefix
     */
    void updateUrl(String prefix);
}
