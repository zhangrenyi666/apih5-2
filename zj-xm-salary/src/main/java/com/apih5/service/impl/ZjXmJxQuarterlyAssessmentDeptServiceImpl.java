package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyAssessmentDeptMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyWeightManagementMapper;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessmentDept;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightManagement;
import com.apih5.service.ZjXmJxQuarterlyAssessmentDeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("zjXmJxQuarterlyAssessmentDeptService")
public class ZjXmJxQuarterlyAssessmentDeptServiceImpl implements ZjXmJxQuarterlyAssessmentDeptService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxQuarterlyAssessmentDeptMapper zjXmJxQuarterlyAssessmentDeptMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyWeightManagementMapper zjXmJxQuarterlyWeightManagementMapper;

	@Override
	public ResponseEntity getZjXmJxQuarterlyAssessmentDeptListByCondition(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		if (zjXmJxQuarterlyAssessmentDept == null) {
			zjXmJxQuarterlyAssessmentDept = new ZjXmJxQuarterlyAssessmentDept();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxQuarterlyAssessmentDept.getPage(), zjXmJxQuarterlyAssessmentDept.getLimit());
		// 获取数据
		List<ZjXmJxQuarterlyAssessmentDept> zjXmJxQuarterlyAssessmentDeptList = zjXmJxQuarterlyAssessmentDeptMapper
				.selectByZjXmJxQuarterlyAssessmentDeptList(zjXmJxQuarterlyAssessmentDept);
		if (zjXmJxQuarterlyAssessmentDeptList.size() > 0) {
			for (ZjXmJxQuarterlyAssessmentDept dbData : zjXmJxQuarterlyAssessmentDeptList) {
				JSONArray arr = JSONUtil.createArray();
				JSONObject obj = JSONUtil.createObj();
				obj.set("value", dbData.getDepartmentId());
				obj.set("label", dbData.getDepartmentName());
				obj.set("title", dbData.getDepartmentName());
				arr.add(obj);
				dbData.setSysDeptArray(arr);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmJxQuarterlyAssessmentDept> p = new PageInfo<>(zjXmJxQuarterlyAssessmentDeptList);

		return repEntity.okList(zjXmJxQuarterlyAssessmentDeptList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyAssessmentDeptDetail(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		if (zjXmJxQuarterlyAssessmentDept == null) {
			zjXmJxQuarterlyAssessmentDept = new ZjXmJxQuarterlyAssessmentDept();
		}
		// 获取数据
		ZjXmJxQuarterlyAssessmentDept dbZjXmJxQuarterlyAssessmentDept = zjXmJxQuarterlyAssessmentDeptMapper
				.selectByPrimaryKey(zjXmJxQuarterlyAssessmentDept.getDeptId());
		// 数据存在
		if (dbZjXmJxQuarterlyAssessmentDept != null) {
			return repEntity.ok(dbZjXmJxQuarterlyAssessmentDept);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxQuarterlyAssessmentDept(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxQuarterlyAssessmentDept.setDeptId(UuidUtil.generate());
		zjXmJxQuarterlyAssessmentDept.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxQuarterlyAssessmentDeptMapper.insert(zjXmJxQuarterlyAssessmentDept);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyAssessmentDept);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxQuarterlyAssessmentDept(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxQuarterlyAssessmentDept dbZjXmJxQuarterlyAssessmentDept = zjXmJxQuarterlyAssessmentDeptMapper
				.selectByPrimaryKey(zjXmJxQuarterlyAssessmentDept.getDeptId());
		if (dbZjXmJxQuarterlyAssessmentDept != null
				&& StrUtil.isNotEmpty(dbZjXmJxQuarterlyAssessmentDept.getDeptId())) {
			// 部门名称
			dbZjXmJxQuarterlyAssessmentDept.setDeptName(zjXmJxQuarterlyAssessmentDept.getDeptName());
			// 系统部门表id
			dbZjXmJxQuarterlyAssessmentDept.setDepartmentId(zjXmJxQuarterlyAssessmentDept.getDepartmentId());
			// 系统部门表名称
			dbZjXmJxQuarterlyAssessmentDept.setDepartmentName(zjXmJxQuarterlyAssessmentDept.getDepartmentName());
			// 是否是收尾项目
			dbZjXmJxQuarterlyAssessmentDept.setIsClosed(zjXmJxQuarterlyAssessmentDept.getIsClosed());
			// 项目状态id
			dbZjXmJxQuarterlyAssessmentDept.setProjectStatus(zjXmJxQuarterlyAssessmentDept.getProjectStatus());
			// 项目状态名称
			dbZjXmJxQuarterlyAssessmentDept.setProjectStatusName(zjXmJxQuarterlyAssessmentDept.getProjectStatusName());
			// 项目类型id
			dbZjXmJxQuarterlyAssessmentDept.setProjectType(zjXmJxQuarterlyAssessmentDept.getProjectType());
			// 项目类型名称
			dbZjXmJxQuarterlyAssessmentDept.setProjectTypeName(zjXmJxQuarterlyAssessmentDept.getProjectTypeName());
			// 上限分数
			dbZjXmJxQuarterlyAssessmentDept.setUpperLimitScore(zjXmJxQuarterlyAssessmentDept.getUpperLimitScore());
			// 备注
			dbZjXmJxQuarterlyAssessmentDept.setRemarks(zjXmJxQuarterlyAssessmentDept.getRemarks());
			// 排序
			dbZjXmJxQuarterlyAssessmentDept.setSort(zjXmJxQuarterlyAssessmentDept.getSort());
			// 共通
			dbZjXmJxQuarterlyAssessmentDept.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyAssessmentDeptMapper.updateByPrimaryKey(dbZjXmJxQuarterlyAssessmentDept);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyAssessmentDept);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessmentDept(
			List<ZjXmJxQuarterlyAssessmentDept> zjXmJxQuarterlyAssessmentDeptList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyAssessmentDeptList != null && zjXmJxQuarterlyAssessmentDeptList.size() > 0) {
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept = new ZjXmJxQuarterlyAssessmentDept();
			zjXmJxQuarterlyAssessmentDept.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyAssessmentDeptMapper.batchDeleteUpdateZjXmJxQuarterlyAssessmentDept(
					zjXmJxQuarterlyAssessmentDeptList, zjXmJxQuarterlyAssessmentDept);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyAssessmentDeptList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity addZjXmJxQuarterlyAssessmentDeptToWeight(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// 非收尾→0:无类型区分 1:路桥类型、2:房建类型、3:轨道类型
		if (StrUtil.equals("0", zjXmJxQuarterlyAssessmentDept.getProjectType())) {
			zjXmJxQuarterlyAssessmentDept.setProjectTypeName("全部类型");
		} else if (StrUtil.equals("1", zjXmJxQuarterlyAssessmentDept.getProjectType())) {
			zjXmJxQuarterlyAssessmentDept.setProjectTypeName("路桥类型");
		} else if (StrUtil.equals("2", zjXmJxQuarterlyAssessmentDept.getProjectType())) {
			zjXmJxQuarterlyAssessmentDept.setProjectTypeName("房建类型");
		} else if (StrUtil.equals("3", zjXmJxQuarterlyAssessmentDept.getProjectType())) {
			zjXmJxQuarterlyAssessmentDept.setProjectTypeName("轨道类型");
		}
		// 非收尾→0:无状态区分 1:在建、2:主体完工
		if (StrUtil.equals("0", zjXmJxQuarterlyAssessmentDept.getProjectStatus())) {
			zjXmJxQuarterlyAssessmentDept.setProjectStatusName("全部状态");
		} else if (StrUtil.equals("1", zjXmJxQuarterlyAssessmentDept.getProjectStatus())) {
			zjXmJxQuarterlyAssessmentDept.setProjectStatusName("在建");
		} else if (StrUtil.equals("2", zjXmJxQuarterlyAssessmentDept.getProjectStatus())) {
			zjXmJxQuarterlyAssessmentDept.setProjectStatusName("主体完工");
		}
		zjXmJxQuarterlyAssessmentDept.setDeptId(UuidUtil.generate());
		zjXmJxQuarterlyAssessmentDept.setCreateUserInfo(userKey, realName);
		if (zjXmJxQuarterlyAssessmentDept.getSysDeptArray() != null
				&& zjXmJxQuarterlyAssessmentDept.getSysDeptArray().size() > 0) {
			zjXmJxQuarterlyAssessmentDept
					.setDepartmentId(zjXmJxQuarterlyAssessmentDept.getSysDeptArray().getJSONObject(0).getStr("value"));
			zjXmJxQuarterlyAssessmentDept.setDepartmentName(
					zjXmJxQuarterlyAssessmentDept.getSysDeptArray().getJSONObject(0).getStr("label"));
		}
		int flag = zjXmJxQuarterlyAssessmentDeptMapper.insert(zjXmJxQuarterlyAssessmentDept);
		// 新增部门,根据情况生成一条权重数据
		// check(同一状态[收尾、非收尾]同一类型只能存在一条权重)
		if (StrUtil.isNotEmpty(zjXmJxQuarterlyAssessmentDept.getIsClosed())
				&& StrUtil.isNotEmpty(zjXmJxQuarterlyAssessmentDept.getProjectType())) {
			ZjXmJxQuarterlyWeightManagement check = new ZjXmJxQuarterlyWeightManagement();
			check.setIsClosed(zjXmJxQuarterlyAssessmentDept.getIsClosed());
			check.setProjectType(zjXmJxQuarterlyAssessmentDept.getProjectType());
			int count = zjXmJxQuarterlyWeightManagementMapper.countZjXmJxQuarterlyWeightManagementByCondition(check);
			if (count < 1) {
				ZjXmJxQuarterlyWeightManagement insertData = new ZjXmJxQuarterlyWeightManagement();
				insertData.setManagementId(UuidUtil.generate());
				insertData.setProjectType(zjXmJxQuarterlyAssessmentDept.getProjectType());
				insertData.setProjectTypeName(zjXmJxQuarterlyAssessmentDept.getProjectTypeName());
				insertData.setIsClosed(zjXmJxQuarterlyAssessmentDept.getIsClosed());
				insertData.setCreateUserInfo(userKey, realName);
				flag = zjXmJxQuarterlyWeightManagementMapper.insert(insertData);
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyAssessmentDept);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxQuarterlyAssessmentDeptToWeight(
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxQuarterlyAssessmentDept dbZjXmJxQuarterlyAssessmentDept = zjXmJxQuarterlyAssessmentDeptMapper
				.selectByPrimaryKey(zjXmJxQuarterlyAssessmentDept.getDeptId());
		if (dbZjXmJxQuarterlyAssessmentDept != null
				&& StrUtil.isNotEmpty(dbZjXmJxQuarterlyAssessmentDept.getDeptId())) {
			String oldProjectType = dbZjXmJxQuarterlyAssessmentDept.getProjectType();
			String newProjectType = zjXmJxQuarterlyAssessmentDept.getProjectType();
			// 非收尾→0:无类型区分 1:路桥类型、2:房建类型、3:轨道类型
			if (StrUtil.equals("0", zjXmJxQuarterlyAssessmentDept.getProjectType())) {
				zjXmJxQuarterlyAssessmentDept.setProjectTypeName("全部类型");
			} else if (StrUtil.equals("1", zjXmJxQuarterlyAssessmentDept.getProjectType())) {
				zjXmJxQuarterlyAssessmentDept.setProjectTypeName("路桥类型");
			} else if (StrUtil.equals("2", zjXmJxQuarterlyAssessmentDept.getProjectType())) {
				zjXmJxQuarterlyAssessmentDept.setProjectTypeName("房建类型");
			} else if (StrUtil.equals("3", zjXmJxQuarterlyAssessmentDept.getProjectType())) {
				zjXmJxQuarterlyAssessmentDept.setProjectTypeName("轨道类型");
			}
			// 非收尾→0:无状态区分 1:在建、2:主体完工
			if (StrUtil.equals("0", zjXmJxQuarterlyAssessmentDept.getProjectStatus())) {
				zjXmJxQuarterlyAssessmentDept.setProjectStatusName("全部状态");
			} else if (StrUtil.equals("1", zjXmJxQuarterlyAssessmentDept.getProjectStatus())) {
				zjXmJxQuarterlyAssessmentDept.setProjectStatusName("在建");
			} else if (StrUtil.equals("2", zjXmJxQuarterlyAssessmentDept.getProjectStatus())) {
				zjXmJxQuarterlyAssessmentDept.setProjectStatusName("主体完工");
			}
			// 部门名称
			dbZjXmJxQuarterlyAssessmentDept.setDeptName(zjXmJxQuarterlyAssessmentDept.getDeptName());
			if (zjXmJxQuarterlyAssessmentDept.getSysDeptArray() != null
					&& zjXmJxQuarterlyAssessmentDept.getSysDeptArray().size() > 0) {
				// 系统部门表id
				dbZjXmJxQuarterlyAssessmentDept.setDepartmentId(
						zjXmJxQuarterlyAssessmentDept.getSysDeptArray().getJSONObject(0).getStr("value"));
				// 系统部门表名称
				dbZjXmJxQuarterlyAssessmentDept.setDepartmentName(
						zjXmJxQuarterlyAssessmentDept.getSysDeptArray().getJSONObject(0).getStr("label"));
			}
			// 是否是收尾项目
			dbZjXmJxQuarterlyAssessmentDept.setIsClosed(zjXmJxQuarterlyAssessmentDept.getIsClosed());
			// 项目状态id
			dbZjXmJxQuarterlyAssessmentDept.setProjectStatus(zjXmJxQuarterlyAssessmentDept.getProjectStatus());
			// 项目状态名称
			dbZjXmJxQuarterlyAssessmentDept.setProjectStatusName(zjXmJxQuarterlyAssessmentDept.getProjectStatusName());
			// 项目类型id
			dbZjXmJxQuarterlyAssessmentDept.setProjectType(zjXmJxQuarterlyAssessmentDept.getProjectType());
			// 项目类型名称
			dbZjXmJxQuarterlyAssessmentDept.setProjectTypeName(zjXmJxQuarterlyAssessmentDept.getProjectTypeName());
			// 上限分数
			dbZjXmJxQuarterlyAssessmentDept.setUpperLimitScore(zjXmJxQuarterlyAssessmentDept.getUpperLimitScore());
			// 备注
			dbZjXmJxQuarterlyAssessmentDept.setRemarks(zjXmJxQuarterlyAssessmentDept.getRemarks());
			// 共通
			dbZjXmJxQuarterlyAssessmentDept.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyAssessmentDeptMapper.updateByPrimaryKey(dbZjXmJxQuarterlyAssessmentDept);
			// 如果项目类型发生变化,则修改权重表数据
			if (!StrUtil.equals(oldProjectType, newProjectType)) {
				// 一、判断是否删除原类型的权重数据
				ZjXmJxQuarterlyAssessmentDept check1 = new ZjXmJxQuarterlyAssessmentDept();
				check1.setIsClosed(dbZjXmJxQuarterlyAssessmentDept.getIsClosed());
				check1.setProjectType(oldProjectType);
				int count1 = zjXmJxQuarterlyAssessmentDeptMapper.countZjXmJxQuarterlyAssessmentDeptByCondition(check1);
				// 如果部门表已经没有这种类型，则清空权重数据
				if (count1 < 1) {
					ZjXmJxQuarterlyWeightManagement delete = new ZjXmJxQuarterlyWeightManagement();
					delete.setIsClosed(dbZjXmJxQuarterlyAssessmentDept.getIsClosed());
					delete.setProjectType(oldProjectType);
					zjXmJxQuarterlyWeightManagementMapper.deleteZjXmJxQuarterlyWeightManagementByCondition(delete);
				}
				// 二、判断是否需要新增新的权重数据
				// check(同一状态[收尾、非收尾]同一类型只能存在一条权重)
				ZjXmJxQuarterlyWeightManagement check2 = new ZjXmJxQuarterlyWeightManagement();
				check2.setIsClosed(dbZjXmJxQuarterlyAssessmentDept.getIsClosed());
				check2.setProjectType(newProjectType);
				int count2 = zjXmJxQuarterlyWeightManagementMapper
						.countZjXmJxQuarterlyWeightManagementByCondition(check2);
				// 如果权重表还没有这种类型的权重,则新增一条
				if (count2 < 1) {
					ZjXmJxQuarterlyWeightManagement insertData = new ZjXmJxQuarterlyWeightManagement();
					insertData.setManagementId(UuidUtil.generate());
					insertData.setProjectType(zjXmJxQuarterlyAssessmentDept.getProjectType());
					insertData.setProjectTypeName(zjXmJxQuarterlyAssessmentDept.getProjectTypeName());
					insertData.setIsClosed(zjXmJxQuarterlyAssessmentDept.getIsClosed());
					insertData.setCreateUserInfo(userKey, realName);
					flag = zjXmJxQuarterlyWeightManagementMapper.insert(insertData);
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyAssessmentDept);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessmentDeptToWeight(
			List<ZjXmJxQuarterlyAssessmentDept> zjXmJxQuarterlyAssessmentDeptList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyAssessmentDeptList != null && zjXmJxQuarterlyAssessmentDeptList.size() > 0) {
			ZjXmJxQuarterlyAssessmentDept zjXmJxQuarterlyAssessmentDept = new ZjXmJxQuarterlyAssessmentDept();
			zjXmJxQuarterlyAssessmentDept.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyAssessmentDeptMapper.batchDeleteUpdateZjXmJxQuarterlyAssessmentDept(
					zjXmJxQuarterlyAssessmentDeptList, zjXmJxQuarterlyAssessmentDept);
			// 循环判断是否需要删除该类型的权重
			for (ZjXmJxQuarterlyAssessmentDept deleteDept : zjXmJxQuarterlyAssessmentDeptList) {
				if (StrUtil.isNotEmpty(deleteDept.getIsClosed()) && StrUtil.isNotEmpty(deleteDept.getProjectType())) {
					// 一、判断是否删除原类型的权重数据
					ZjXmJxQuarterlyAssessmentDept check1 = new ZjXmJxQuarterlyAssessmentDept();
					check1.setIsClosed(deleteDept.getIsClosed());
					check1.setProjectType(deleteDept.getProjectType());
					int count1 = zjXmJxQuarterlyAssessmentDeptMapper
							.countZjXmJxQuarterlyAssessmentDeptByCondition(check1);
					// 如果部门表已经没有这种类型，则清空权重数据
					if (count1 < 1) {
						ZjXmJxQuarterlyWeightManagement delete = new ZjXmJxQuarterlyWeightManagement();
						delete.setIsClosed(deleteDept.getIsClosed());
						delete.setProjectType(deleteDept.getProjectType());
						zjXmJxQuarterlyWeightManagementMapper.deleteZjXmJxQuarterlyWeightManagementByCondition(delete);
					}
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyAssessmentDeptList);
		}
	}

}
