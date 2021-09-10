package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmSalaryPositionLevelSalaryMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryPositionLevelSalary;
import com.apih5.service.ZjXmSalaryPositionLevelSalaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("zjXmSalaryPositionLevelSalaryService")
public class ZjXmSalaryPositionLevelSalaryServiceImpl implements ZjXmSalaryPositionLevelSalaryService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryPositionLevelSalaryMapper zjXmSalaryPositionLevelSalaryMapper;

	@Override
	public ResponseEntity getZjXmSalaryPositionLevelSalaryListByCondition(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		if (zjXmSalaryPositionLevelSalary == null) {
			zjXmSalaryPositionLevelSalary = new ZjXmSalaryPositionLevelSalary();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryPositionLevelSalary.getPage(), zjXmSalaryPositionLevelSalary.getLimit());
		// 获取数据
		List<ZjXmSalaryPositionLevelSalary> zjXmSalaryPositionLevelSalaryList = zjXmSalaryPositionLevelSalaryMapper
				.selectByZjXmSalaryPositionLevelSalaryList(zjXmSalaryPositionLevelSalary);
		// 得到分页信息
		PageInfo<ZjXmSalaryPositionLevelSalary> p = new PageInfo<>(zjXmSalaryPositionLevelSalaryList);

		return repEntity.okList(zjXmSalaryPositionLevelSalaryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryPositionLevelSalaryDetails(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		if (zjXmSalaryPositionLevelSalary == null) {
			zjXmSalaryPositionLevelSalary = new ZjXmSalaryPositionLevelSalary();
		}
		// 获取数据
		ZjXmSalaryPositionLevelSalary dbZjXmSalaryPositionLevelSalary = zjXmSalaryPositionLevelSalaryMapper
				.selectByPrimaryKey(zjXmSalaryPositionLevelSalary.getLevelSalaryId());
		// 数据存在
		if (dbZjXmSalaryPositionLevelSalary != null) {
			return repEntity.ok(dbZjXmSalaryPositionLevelSalary);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryPositionLevelSalary(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// 新增岗级(check)
		if (StrUtil.equals("0", zjXmSalaryPositionLevelSalary.getPid())) {
			// 项目类型岗位类型联合唯一check
			ZjXmSalaryPositionLevelSalary check = new ZjXmSalaryPositionLevelSalary();
			check.setProjectType(zjXmSalaryPositionLevelSalary.getProjectType());
			check.setPositionType(zjXmSalaryPositionLevelSalary.getPositionType());
			int count = zjXmSalaryPositionLevelSalaryMapper.checkZjXmSalarPositionLevelSalary(check);
			if (count > 0) {
				return repEntity.ok("同一项目类型下不能存在同一岗位类型数据。");
			}
		}
		// 新增岗薪(check)(带着岗级字段positionLevel)
		else {
			// 同一个岗级下，档位唯一
			ZjXmSalaryPositionLevelSalary check = new ZjXmSalaryPositionLevelSalary();
			check.setPositionGrade(zjXmSalaryPositionLevelSalary.getPositionGrade());
			check.setPid(zjXmSalaryPositionLevelSalary.getPid());
			int count = zjXmSalaryPositionLevelSalaryMapper.checkZjXmSalarPositionLevelSalary(check);
			if (count > 0) {
				return repEntity.ok("同一岗级下不能新增重复的档位。");
			}
		}
		zjXmSalaryPositionLevelSalary.setLevelSalaryId(UuidUtil.generate());
		zjXmSalaryPositionLevelSalary.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryPositionLevelSalaryMapper.insert(zjXmSalaryPositionLevelSalary);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryPositionLevelSalary);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryPositionLevelSalary(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryPositionLevelSalary dbzjXmSalaryPositionLevelSalary = zjXmSalaryPositionLevelSalaryMapper
				.selectByPrimaryKey(zjXmSalaryPositionLevelSalary.getLevelSalaryId());
		if (dbzjXmSalaryPositionLevelSalary != null) {
			// 修改岗级(check)
			if (StrUtil.equals("0", zjXmSalaryPositionLevelSalary.getPid())) {
				// 项目类型岗位类型联合唯一check
				ZjXmSalaryPositionLevelSalary check = new ZjXmSalaryPositionLevelSalary();
				check.setProjectType(zjXmSalaryPositionLevelSalary.getProjectType());
				check.setPositionType(zjXmSalaryPositionLevelSalary.getPositionType());
				int count = zjXmSalaryPositionLevelSalaryMapper.checkZjXmSalarPositionLevelSalary(check);
				if (count > 0) {
					return repEntity.ok("同一项目类型下不能存在同一岗位类型数据。");
				}
				// 项目类型
				dbzjXmSalaryPositionLevelSalary.setProjectType(zjXmSalaryPositionLevelSalary.getProjectType());
				// 岗位类型
				dbzjXmSalaryPositionLevelSalary.setPositionType(zjXmSalaryPositionLevelSalary.getPositionType());
				// 岗位级别
				dbzjXmSalaryPositionLevelSalary.setPositionLevel(zjXmSalaryPositionLevelSalary.getPositionLevel());
			}
			// 修改岗薪(check)(带着岗级字段positionLevel)
			else {
				// 同一个岗级下，档位唯一
				ZjXmSalaryPositionLevelSalary check = new ZjXmSalaryPositionLevelSalary();
				check.setPositionGrade(zjXmSalaryPositionLevelSalary.getPositionGrade());
				check.setPid(zjXmSalaryPositionLevelSalary.getPid());
				int count = zjXmSalaryPositionLevelSalaryMapper.checkZjXmSalarPositionLevelSalary(check);
				if (count > 0) {
					return repEntity.ok("同一岗级下不能新增重复的档位。");
				}
				// 岗位级别
				dbzjXmSalaryPositionLevelSalary.setPositionLevel(zjXmSalaryPositionLevelSalary.getPositionLevel());
				// 档位
				dbzjXmSalaryPositionLevelSalary.setPositionGrade(zjXmSalaryPositionLevelSalary.getPositionGrade());
				// 岗薪
				dbzjXmSalaryPositionLevelSalary.setPositionSalary(zjXmSalaryPositionLevelSalary.getPositionSalary());
			}
			// 共通
			dbzjXmSalaryPositionLevelSalary.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryPositionLevelSalaryMapper.updateByPrimaryKey(dbzjXmSalaryPositionLevelSalary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryPositionLevelSalary);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryPositionLevelSalary(
			List<ZjXmSalaryPositionLevelSalary> zjXmSalaryPositionLevelSalaryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryPositionLevelSalaryList != null && zjXmSalaryPositionLevelSalaryList.size() > 0) {
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary = new ZjXmSalaryPositionLevelSalary();
			zjXmSalaryPositionLevelSalary.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryPositionLevelSalaryMapper.batchDeleteUpdateZjXmSalaryPositionLevelSalary(
					zjXmSalaryPositionLevelSalaryList, zjXmSalaryPositionLevelSalary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryPositionLevelSalaryList);
		}
	}

	@Override
	public ResponseEntity getZjXmSalaryPositionLevelSalarySelect(
			ZjXmSalaryPositionLevelSalary zjXmSalaryPositionLevelSalary) {
		List<ZjXmSalaryPositionLevelSalary> zjXmSalaryPositionLevelSalaryList = zjXmSalaryPositionLevelSalaryMapper
				.selectByZjXmSalaryPositionLevelSalaryList(zjXmSalaryPositionLevelSalary);
//		JSONArray jsonArray2 = ConvertUtil.listToTree(new JSONArray(zjXmSalaryPositionLevelSalaryList), "levelId",
//				"pid", "positionLevel", "positionLevel");
		// 处理成二层树结构
		JSONArray jsonArray = JSONUtil.createArray();
		if (zjXmSalaryPositionLevelSalaryList.size() > 0) {
			List<ZjXmSalaryPositionLevelSalary> levelList = Lists.newArrayList();
			List<ZjXmSalaryPositionLevelSalary> salaryList = Lists.newArrayList();
			// 整理两级list
			for (ZjXmSalaryPositionLevelSalary positionLevelSalary : zjXmSalaryPositionLevelSalaryList) {
				if (StrUtil.equals("0", positionLevelSalary.getPid())) {
					levelList.add(positionLevelSalary);
				} else {
					salaryList.add(positionLevelSalary);
				}
			}
			for (ZjXmSalaryPositionLevelSalary positionLevel : levelList) {
				if (StrUtil.equals("0", positionLevel.getPid())) {
					JSONObject jsonObj = JSONUtil.createObj();
					jsonObj.put("value", positionLevel.getLevelSalaryId());
					jsonObj.put("valuePid", "0");
					jsonObj.put("type", "1");
					jsonObj.put("label", positionLevel.getPositionLevel());
					jsonObj.put("title", positionLevel.getPositionLevel());
					jsonObj.put("showData", JSONUtil.createArray());
					jsonArray.add(jsonObj);
					for (int i = 0; i < salaryList.size(); i++) {
						if (StrUtil.equals(positionLevel.getLevelSalaryId(), salaryList.get(i).getPid())) {
							JSONArray jsonArray2 = jsonObj.getJSONArray("showData");
							JSONObject jsonObj2 = JSONUtil.createObj();
							jsonObj2.put("value", salaryList.get(i).getLevelSalaryId());
							jsonObj2.put("valuePid", salaryList.get(i).getPid());
							jsonObj2.put("type", "1");
							jsonObj2.put("label", salaryList.get(i).getPositionGrade());
							jsonObj2.put("title", salaryList.get(i).getPositionGrade());
							jsonObj2.put("showData", JSONUtil.createArray());
							jsonArray2.add(jsonObj2);
							salaryList.remove(i);
							i--;
						}
					}
				}
			}
		}
		return repEntity.ok(jsonArray);
	}

//	/**
//	 * 使用递归方法建树 (将查询出来的list处理成树结构)
//	 * 
//	 * @param treeNodes
//	 * @return
//	 */
//	private List<ZxQrcodeOrganizationLevel> buildTreeByRecursive(List<ZxQrcodeOrganizationLevel> treeNodeList) {
//		List<ZxQrcodeOrganizationLevel> trees = Lists.newArrayList();
//		for (ZxQrcodeOrganizationLevel treeNode : treeNodeList) {
//			if ("0".equals(treeNode.getParentId())) {
//				trees.add(toFindChildren(treeNode, treeNodeList));
//			}
//		}
//		return trees;
//	}
//
//	/**
//	 * 递归查找子节点
//	 * 
//	 * @param treeNodes
//	 * @return
//	 */
//	private ZxQrcodeOrganizationLevel toFindChildren(ZxQrcodeOrganizationLevel treeNode,
//			List<ZxQrcodeOrganizationLevel> treeNodeList) {
//		for (ZxQrcodeOrganizationLevel it : treeNodeList) {
//			// 如果childrenList不想是null则将这段提出来,否则写在下面的判断里
//			if (treeNode.getChildrenList() == null) {
//				treeNode.setChildrenList(Lists.newArrayList());
//			}
//			if (treeNode.getLevelId().equals(it.getParentId())) {
//				// if (treeNode.getChildrenList() == null) {
//				// treeNode.setChildrenList(Lists.newArrayList());
//				// }
//				treeNode.getChildrenList().add(toFindChildren(it, treeNodeList));
//			}
//		}
//		return treeNode;
//	}
}
