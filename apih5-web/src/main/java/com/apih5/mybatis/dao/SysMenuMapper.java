package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.SysMenu;

public interface SysMenuMapper {
	int deleteByPrimaryKey(String key);

	int insert(SysMenu record);

	int insertSelective(SysMenu record);

	SysMenu selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(SysMenu record);

	int updateByPrimaryKey(SysMenu record);

	List<SysMenu> selectBySysMenuList(SysMenu record);

	int batchDeleteUpdateSysMenu(List<SysMenu> recordList, SysMenu record);

	int batchUpdateSysMenu(List<SysMenu> recordList);

	int batchUpdateMenuPathAndMenuPathName(SysMenu record);

	int countSysMenuListByCondition(SysMenu record);

	SysMenu selectMaxSortBySysMenu(SysMenu record);
}
