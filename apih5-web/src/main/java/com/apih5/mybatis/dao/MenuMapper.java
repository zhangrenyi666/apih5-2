package com.apih5.mybatis.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.TreeNode;

/**
 * 菜单mapper
 */
@Repository
public interface MenuMapper {

    /**
     * 根据用户ID得到菜单列表
     * @return
     */
    List<SysMenu> getMenuList(SysMenu sysMenu);

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