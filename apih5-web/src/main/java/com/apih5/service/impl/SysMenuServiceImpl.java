package com.apih5.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysMenuMapper;
import com.apih5.mybatis.dao.SysRoleMenuMapper;
import com.apih5.mybatis.dao.SysRoleUserMapper;
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.SysRoleMenu;
import com.apih5.mybatis.pojo.SysRoleUser;
import com.apih5.service.SysMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private SysMenuMapper sysMenuMapper;

	@Autowired(required = true)
	private SysDepartmentService sysDepartmentService;

	@Autowired(required = true)
	private SysRoleUserMapper sysRoleUserMapper;

	@Autowired(required = true)
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	public ResponseEntity getSysMenuListByCondition(SysMenu sysMenu) {
		if (sysMenu == null) {
			sysMenu = new SysMenu();
		}
		// 分页查询
		PageHelper.startPage(sysMenu.getPage(), sysMenu.getLimit());
		// 获取数据
		List<SysMenu> sysMenuList = sysMenuMapper.selectBySysMenuList(sysMenu);
		// 得到分页信息
		PageInfo<SysMenu> p = new PageInfo<>(sysMenuList);

		return repEntity.okList(sysMenuList, p.getTotal());
	}

	@Override
	public ResponseEntity saveSysMenu(SysMenu sysMenu) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		sysMenu.setMenuId(UuidUtil.generate());
		// 查询上一个节点ID
		SysMenu sysMenuDb = sysMenuMapper.selectByPrimaryKey(sysMenu.getMenuParentId());
		if (sysMenuDb != null) {
			sysMenu.setMenuPath(sysMenuDb.getMenuPath() + "," + sysMenu.getMenuId());
			sysMenu.setMenuPathName(sysMenuDb.getMenuPathName() + "," + sysMenu.getMenuName());
		} else {
			sysMenu.setMenuPath(sysMenu.getMenuId());
			sysMenu.setMenuPathName(sysMenu.getMenuName());
		}
		if(sysMenu.getSort() == null || sysMenu.getSort() == 0) {
		    SysMenu sysMenuSort = new SysMenu();
		    sysMenuSort.setMenuParentId(sysMenu.getMenuParentId());
		    sysMenuSort = sysMenuMapper.selectMaxSortBySysMenu(sysMenuSort);
		    if(sysMenuSort != null && sysMenu.getSort() != null) {
		        sysMenu.setSort(sysMenuSort.getSort() + 1);
		    } else {
		        sysMenu.setSort(1);
		    }
		}
		sysMenu.setCreateUserInfo(userKey, realName);
		int flag = sysMenuMapper.insert(sysMenu);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", sysMenu);
		}
	}

	@Override
	public ResponseEntity updateSysMenu(SysMenu sysMenu) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		SysMenu dbsysMenu = sysMenuMapper.selectByPrimaryKey(sysMenu.getMenuId());
		if (dbsysMenu != null && StrUtil.isNotEmpty(dbsysMenu.getMenuId())) {
			dbsysMenu.setMenuName(sysMenu.getMenuName());
			// 部门全路径
			String pathName = dbsysMenu.getMenuPathName().substring(0, dbsysMenu.getMenuPathName().lastIndexOf(","));
			pathName += "," + sysMenu.getMenuName();
			dbsysMenu.setMenuPathName(pathName);
			// 共通
			dbsysMenu.setCreateUserInfo(userKey, realName);
			flag = sysMenuMapper.updateByPrimaryKey(dbsysMenu);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", sysMenu);
		}
	}

	@Override
	public ResponseEntity updateSysMenuDetails(SysMenu sysMenu) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		SysMenu dbsysMenu = sysMenuMapper.selectByPrimaryKey(sysMenu.getMenuId());
		if (dbsysMenu != null && StrUtil.isNotEmpty(dbsysMenu.getMenuId())) {
			dbsysMenu.setMenuUrl(sysMenu.getMenuUrl());
			// 共通
			dbsysMenu.setCreateUserInfo(userKey, realName);
			flag = sysMenuMapper.updateByPrimaryKey(dbsysMenu);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", sysMenu);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateSysMenu(List<SysMenu> sysMenuList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		SysMenu sysMenuSelect = new SysMenu();
		sysMenuSelect.setMenuParentId(sysMenuList.get(0).getMenuId());
		List<SysMenu> dbSysMenuList = sysMenuMapper.selectBySysMenuList(sysMenuSelect);
		if(dbSysMenuList != null && dbSysMenuList.size()>0) {
			return repEntity.layerMessage("no", "该菜单拥有子菜单，清先删除子菜单");
		}
		int flag = 0;
		if (sysMenuList != null && sysMenuList.size() > 0) {
			SysMenu sysMenu = new SysMenu();
			sysMenu.setCreateUserInfo(userKey, realName);
			flag = sysMenuMapper.batchDeleteUpdateSysMenu(sysMenuList, sysMenu);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", sysMenuList);
		}
	}

	/**
	 * 获取菜单节点，并形成树形结构
	 * 
	 * @return 树形结构
	 */
	@Override
	public ResponseEntity getSysMenuAllTree(SysMenu sysMenu) {
		if (sysMenu == null) {
			sysMenu = new SysMenu();
		}
		// 获取全部的部门
		List<SysMenu> sysMenuList = sysMenuMapper.selectBySysMenuList(null);
		// 获取菜单
		JSONObject jsonObject = ConvertUtil.listToTreeMenu(new JSONArray(sysMenuList), "menuId", "menuParentId", "menuName");
		// 根对象放置数组
		JSONObject returnJSONObject =jsonObject.getJSONObject("menuTop");
		returnJSONObject.set("children", jsonObject.getJSONArray("menuTree"));
		return repEntity.ok(returnJSONObject);
	}
	
	@Override
	public JSONObject getSysMenuAllTreeByRole(String userKey) {
		if(StrUtil.equals("admin", userKey)) {
			// 获取全部的部门
		    SysMenu sysMenu = new SysMenu();
		    // 只获取菜单，不获取按钮
		    sysMenu.setMenuFlag("1");
			List<SysMenu> sysMenuList = sysMenuMapper.selectBySysMenuList(sysMenu);
			JSONArray jsonArray = new JSONArray(sysMenuList);
			return ConvertUtil.listToTreeMenu(jsonArray, "menuId", "menuParentId", "menuName");
		}
		List<String> sysMenuIdList = Lists.newArrayList();

		// 角色ID获取
		List<String> sysRoleIdList = Lists.newArrayList();
// 应该可以改善，token里面获取		
// 查找所有的部门ID
List<SysDepartment> sysDepartmentList = sysDepartmentService.getSysDepartmentByUserkey(userKey);
for (SysDepartment sysDepartment : sysDepartmentList) {
	// 循环该相关部门的所有层级部门
	String[] departmentPath = sysDepartment.getDepartmentPath().split(",");
	for(int i=0; i<departmentPath.length; i++) {
		SysRoleUser sysRoleUserSelect = new SysRoleUser();
		sysRoleUserSelect.setValue(departmentPath[i]);
		List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUserSelect);
		for (SysRoleUser sysRoleUser : sysRoleUserList) {
			sysRoleIdList.add(sysRoleUser.getRoleId());
		}
	}
}

		// 通过userKey角色ID
		SysRoleUser sysRoleUserSelect = new SysRoleUser();
		sysRoleUserSelect.setValue(userKey);
		List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUserSelect);
		for (SysRoleUser sysRoleUser : sysRoleUserList) {
			sysRoleIdList.add(sysRoleUser.getRoleId());
		}
		// 去重
		sysRoleIdList = CollUtil.distinct(sysRoleIdList);

		if (sysRoleIdList == null || sysRoleIdList.size() == 0) {
			return null;
		}

		// 通过角色ID获取所有选中菜单ID数据
		SysRoleMenu sysRoleMenuSelect = new SysRoleMenu();
	    sysRoleMenuSelect.setMenuFlag("1");
	    Map<String, Object> map = Maps.newHashMap(); 
	    map.put("roleIdList", sysRoleIdList);
	    map.put("sysRoleMenu", sysRoleMenuSelect);
		List<String> sysRoleMenuIdList = sysRoleMenuMapper.selectBySysRoleMenuIdList(map);
		if(sysRoleMenuIdList == null || sysRoleMenuIdList.size()==0) {
		    return null;
		}

		// 菜单遍历，重新添加
		sysRoleMenuIdList = CollUtil.distinct(sysRoleMenuIdList);
		for (String menuId : sysRoleMenuIdList) {
			SysMenu sysMenuDb = sysMenuMapper.selectByPrimaryKey(menuId);
			if(sysMenuDb != null) {
				sysMenuIdList.addAll(StrUtil.split(sysMenuDb.getMenuPath(), (char) ','));
			}
		}
		sysMenuIdList = CollUtil.distinct(sysMenuIdList);

		// 最后获取所有path的数据
		List<SysMenu> sysMenuList = Lists.newArrayList();
		for (String menuId : sysMenuIdList) {
			sysMenuList.add(sysMenuMapper.selectByPrimaryKey(menuId));
		}

		// 排序
		Collections.sort(sysMenuList, new Comparator<SysMenu>() {
			@Override
			public int compare(SysMenu o1, SysMenu o2) {
				return o1.getSort() - o2.getSort();
			}
		});
		
		JSONArray jsonArray = new JSONArray(sysMenuList);
		return ConvertUtil.listToTreeMenu(jsonArray, "menuId", "menuParentId", "menuName");
	}

	/**
	 * 按钮获取，通过前端过来的menuParentId和当前授权信息返回按钮list
	 * 
	 */
	@Override
	public ResponseEntity getSysMenuBtn(SysMenu sysMenu) {
	    if(sysMenu == null || StrUtil.isEmpty(sysMenu.getMenuParentId())) {
	        return repEntity.ok(Lists.newArrayList());
	    }
	    HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
	    if(StrUtil.equals("admin", userId)) {
	        List<SysMenu> sysMenuList = sysMenuMapper.selectBySysMenuList(sysMenu);
	        JSONArray jsonArray = new JSONArray();
	        for(SysMenu dbSysMenu:sysMenuList) {
	            JSONObject jsonObject = new JSONObject(dbSysMenu.getMenuUrl());
	            if(!StrUtil.equals("show", jsonObject.getStr("name"))) {
	                jsonArray.add(jsonObject);
	            }
	        }
	        return repEntity.ok(jsonArray);
	    }
	    
	    // 角色ID获取
	    List<String> sysRoleIdList = Lists.newArrayList();
	    
        // 获取登陆者的所有部门Id和userKey
        List<String> permissionList = TokenUtils.getPermissionIds(request);
        for (int i=0; i<permissionList.size(); i++) {
            String departmentId = permissionList.get(i);
            SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(departmentId);
            if(sysDepartment != null) {
                String[] sysDepartments = sysDepartment.getDepartmentPath().split(",");
                for(String sysDepartmentId:sysDepartments) {
//                    sysRoleIdList.add(sysDepartmentId);
                    SysRoleUser sysRoleUserSelect = new SysRoleUser();
                    sysRoleUserSelect.setValue(sysDepartmentId);
                    List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUserSelect);
                    for (SysRoleUser sysRoleUser : sysRoleUserList) {
                        sysRoleIdList.add(sysRoleUser.getRoleId());
                    }
                }
            } else {
                SysRoleUser sysRoleUserSelect = new SysRoleUser();
                sysRoleUserSelect.setValue(departmentId);
                List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUserSelect);
                for (SysRoleUser sysRoleUser : sysRoleUserList) {
                  sysRoleIdList.add(sysRoleUser.getRoleId());
                }
            }
        }
        
	    // 去重
	    sysRoleIdList = CollUtil.distinct(sysRoleIdList);
	    if (sysRoleIdList == null || sysRoleIdList.size() == 0) {
	        return repEntity.ok(Lists.newArrayList());
	    }
	    
	    
//	       // 角色ID获取
//        List<String> sysRoleIdList = Lists.newArrayList();
//// 应该可以改善，token里面获取     
//// 查找所有的部门ID
//List<SysDepartment> sysDepartmentList = sysDepartmentService.getSysDepartmentByUserkey(userKey);
//for (SysDepartment sysDepartment : sysDepartmentList) {
//    // 循环该相关部门的所有层级部门
//    String[] departmentPath = sysDepartment.getDepartmentPath().split(",");
//    for(int i=0; i<departmentPath.length; i++) {
//        SysRoleUser sysRoleUserSelect = new SysRoleUser();
//        sysRoleUserSelect.setValue(departmentPath[i]);
//        List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUserSelect);
//        for (SysRoleUser sysRoleUser : sysRoleUserList) {
//            sysRoleIdList.add(sysRoleUser.getRoleId());
//        }
//    }
//}
	    
	    
	    // 通过角色ID获取所有选中菜单ID数据
	    SysRoleMenu sysRoleMenuSelect = new SysRoleMenu();
	    sysRoleMenuSelect.setMenuFlag("2");
	    sysRoleMenuSelect.setMenuParentId(sysMenu.getMenuParentId());
	    Map<String, Object> map = Maps.newHashMap(); 
	    map.put("roleIdList", sysRoleIdList);
	    // 父级菜单ID
	    map.put("sysRoleMenu", sysRoleMenuSelect);
	    List<String> sysRoleMenuIdList = sysRoleMenuMapper.selectBySysRoleMenuIdList(map);
	    // 菜单遍历，重新添加
	    sysRoleMenuIdList = CollUtil.distinct(sysRoleMenuIdList);
	    
	    // 最后获取所有path的数据
	    List<SysMenu> sysMenuList = Lists.newArrayList();
	    for (String menuId : sysRoleMenuIdList) {
	        sysMenuList.add(sysMenuMapper.selectByPrimaryKey(menuId));
	    }
	    
	    // 排序
	    Collections.sort(sysMenuList, new Comparator<SysMenu>() {
	        @Override
	        public int compare(SysMenu o1, SysMenu o2) {
	            return o1.getSort() - o2.getSort();
	        }
	    });
	    
	    JSONArray jsonArray = new JSONArray();
        for(SysMenu dbSysMenu:sysMenuList) {
            JSONObject jsonObject = new JSONObject(dbSysMenu.getMenuUrl());
            if(!StrUtil.equals("show", jsonObject.getStr("name"))) {
                jsonArray.add(jsonObject);
            }
        }
        return repEntity.ok(jsonArray);
	}

	@Override
	public ResponseEntity getSysMenuByCondition(SysMenu sysMenu) {
		if (sysMenu == null) {
			sysMenu = new SysMenu();
		}
		// 获取数据
		SysMenu sysMenuDb = sysMenuMapper.selectByPrimaryKey(sysMenu.getMenuId());
		if (sysMenuDb == null) {
			return repEntity.ok(new SysMenu());
		} else {
			return repEntity.ok(sysMenuDb);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity moveUpdateSysMenu(SysMenu sysMenu) throws Exception {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 移动后菜单的新父节点id(一定存在)
		String parentMenuId = sysMenu.getMenuParent();
		// 要移动的菜单id(一定存在)
		String toMoveMenuId = sysMenu.getMenuToMove();
		// 移动后菜单的同级下面的节点id(可能存在)
		String afterMenuId = sysMenu.getMenuAfter();
		// 移动后菜单的同级上面的节点id(可能存在)
		String beforeMenuId = sysMenu.getMenuBefore();

		// 父节点对象(一定存在)
		SysMenu sysMenuParent = sysMenuMapper.selectByPrimaryKey(parentMenuId);
		// 要移动节点对象(一定存在)
		SysMenu sysMenuToMove = sysMenuMapper.selectByPrimaryKey(toMoveMenuId);
		// 上一个节点对象(可能存在)
		SysMenu sysMenuBefore = sysMenuMapper.selectByPrimaryKey(beforeMenuId);
		if (sysMenuParent == null || sysMenuToMove == null) {
			return repEntity.layerMessage("no", "传入参数有误!");
		}
		// 暂存要移动节点原属性
		String oldMenuParentId = sysMenuToMove.getMenuParentId();
		String oldMenuPath = sysMenuToMove.getMenuPath();
		String oldMenuPathName = sysMenuToMove.getMenuPathName();
		// 获取新父节点下原有的所有子节点集合
		SysMenu menu = new SysMenu();
		menu.setMenuParentId(parentMenuId);
		LinkedList<SysMenu> sysMenuList = new LinkedList<SysMenu>(sysMenuMapper.selectBySysMenuList(menu));
		// 如果要移动的节点本来就在当前父节点集合下则先将其移除
		if (sysMenuList.size() > 0 && StrUtil.equals(parentMenuId, oldMenuParentId)) {
			for (int i = 0; i < sysMenuList.size(); i++) {
				// 从数据集合中先移出当前移动节点
				if (StrUtil.equals(toMoveMenuId, sysMenuList.get(i).getMenuId())) {
					sysMenuList.remove(i);
					break;
				}
			}
		} else {
			// 如果要移动的节点不在当前父节点集合下则重新构建移动后节点数据
			sysMenuToMove.setMenuPath(sysMenuParent.getMenuPath() + "," + sysMenuToMove.getMenuId());
			sysMenuToMove.setMenuPathName(sysMenuParent.getMenuPathName() + "," + sysMenuToMove.getMenuName());
			sysMenuToMove.setMenuParentId(parentMenuId);
			// 如果要移动的节点不在当前父节点集合下则需要处理要移动节点的下级节点数据
			SysMenu sysMenuCondition = new SysMenu();
			sysMenuCondition.setMenuPath(sysMenuToMove.getMenuId());
			int count = sysMenuMapper.countSysMenuListByCondition(sysMenuCondition);
			if (count > 1) {
				sysMenuCondition.setOldMenuPath(oldMenuPath);
				sysMenuCondition.setOldMenuPathName(oldMenuPathName);
				sysMenuCondition.setMenuPath(sysMenuToMove.getMenuPath());
				sysMenuCondition.setMenuPathName(sysMenuToMove.getMenuPathName());
				flag = sysMenuMapper.batchUpdateMenuPathAndMenuPathName(sysMenuCondition);
				if (flag < 2) {
					throw new Apih5Exception("处理要移动节点的子节点数据失败!");
				}
			}
		}
		sysMenuToMove.setModifyUserInfo(userKey, realName);
		// 处理要移动节点和移动后节点的同级兄弟节点的sort(分为以下四种情况)
		// befor、after同时存在时、说明移动到了中间位置
		if (StrUtil.isNotEmpty(beforeMenuId) && StrUtil.isNotEmpty(afterMenuId)) {
			for (int i = 0; i < sysMenuList.size(); i++) {
				sysMenuList.get(i).setSort(i);
				if (StrUtil.equals(sysMenuList.get(i).getMenuId(), beforeMenuId)) {
					// 将要移动的节点加入集合
					sysMenuList.add(i + 1, sysMenuToMove);
				}
			}
			// 将节点放在中间每次移动都将批量修改集合
			flag = sysMenuMapper.batchUpdateSysMenu(sysMenuList);
		}
		// 只有befor存在时、说明移动到了最后面，移动后的节点后面没有内容
		else if (StrUtil.isNotEmpty(beforeMenuId) && StrUtil.isEmpty(afterMenuId)) {
			sysMenuToMove.setSort(sysMenuBefore.getSort() + 1);
			flag = sysMenuMapper.updateByPrimaryKey(sysMenuToMove);
		}
		// 只有after存在时、说明移动到了最前面面，移动后的节点前面没有内容
		else if (StrUtil.isEmpty(beforeMenuId) && StrUtil.isNotEmpty(afterMenuId)) {
			// 将要移动的节点加入集合
			sysMenuList.add(0, sysMenuToMove);
			for (int i = 0; i < sysMenuList.size(); i++) {
				sysMenuList.get(i).setSort(i);
			}
			// 将节点移到最前面每次都将批量修改集合
			flag = sysMenuMapper.batchUpdateSysMenu(sysMenuList);
		}
		// befor、after都不存在时，说明前后不存在节点（即当前父节点下只有一个节点）
		else if (StrUtil.isEmpty(beforeMenuId) && StrUtil.isEmpty(afterMenuId)) {
			if (sysMenuList.size() > 0) {
				// 将插入的放在最后面
				sysMenuToMove.setSort(sysMenuList.get(sysMenuList.size() - 1).getSort() + 1);
			} else {
				// 随意设置一个排序即可
				sysMenuToMove.setSort(1);
			}
			flag = sysMenuMapper.updateByPrimaryKey(sysMenuToMove);
		}
		// 失败
		if (flag == 0) {
			throw new Apih5Exception("菜单移动失败!");
		} else {
			return repEntity.ok("sys.data.update", sysMenu);
		}
	}

	@Override
	public ResponseEntity tempMenuPath() {
		// 获取全部的部门
		List<SysMenu> sysDepartmentList = sysMenuMapper.selectBySysMenuList(null);

		for (int i = 0; i < sysDepartmentList.size(); i++) {
			SysMenu dbSysDepartment = sysDepartmentList.get(i);
			if ("0".equals(dbSysDepartment.getMenuParentId())) {
				continue;
			}
			String[] str = new String[10];
			String[] strName = new String[10];
			for (int j = 0; j < 10; j++) {
				if (j == 0) {
					str[j] = dbSysDepartment.getMenuParentId();
					SysMenu sysDepartment2 = sysMenuMapper.selectByPrimaryKey(dbSysDepartment.getMenuParentId());
					if (sysDepartment2 != null) {
						strName[j] = sysDepartment2.getMenuName();
					} else {
						strName[j] = "";
					}
				} else {
					String str2 = str[j - 1];
					if (StrUtil.isEmpty(str2) || StrUtil.equals("0", str2)) {
						break;
					}
					SysMenu sysDepartment2 = sysMenuMapper.selectByPrimaryKey(str[j - 1]);
					if (sysDepartment2 != null) {
						if (StrUtil.isEmpty(sysDepartment2.getMenuParentId())
								|| StrUtil.equals("0", sysDepartment2.getMenuParentId())) {
							break;
						}
						str[j] = sysDepartment2.getMenuParentId();
						if (StrUtil.isNotEmpty(sysDepartment2.getMenuParentId())
								&& !StrUtil.equals("0", sysDepartment2.getMenuParentId())) {
							sysDepartment2 = sysMenuMapper
									.selectByPrimaryKey(sysDepartment2.getMenuParentId());
							if (sysDepartment2 != null) {
								strName[j] = sysDepartment2.getMenuName();
							} else {
								strName[j] = "";
							}
						}
					}
				}
			}
			String strPath = "";
			String strPathName = "";
			for (int j = 9; j >= 0; j--) {
				String str1 = str[j];
				String strName2 = strName[j];
				if (StrUtil.isNotEmpty(str1)) {
					if ("".equals(strPath)) {
						strPath = str1;
						strPathName = strName2;
					} else {
						strPath = strPath + "," + str1;
						strPathName = strPathName + "," + strName2;
					}
				}
			}

			dbSysDepartment.setMenuPath(strPath + "," + dbSysDepartment.getMenuId());
			dbSysDepartment.setMenuPathName(strPathName + "," + dbSysDepartment.getMenuName());
			int t = sysMenuMapper.updateByPrimaryKey(dbSysDepartment);
			if (t == 0) {
				System.out.println(
						"----------" + dbSysDepartment.getMenuId() + dbSysDepartment.getMenuName());
			}
		}
		return repEntity.ok("");
	}
}
