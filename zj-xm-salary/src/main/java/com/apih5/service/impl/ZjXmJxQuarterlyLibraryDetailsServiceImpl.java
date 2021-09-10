package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyLibraryDetailsMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyProjectDetailsMapper;
import com.apih5.mybatis.dao.ZjXmSalaryDepartmentMapper;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyLibraryDetails;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyProjectDetails;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartment;
import com.apih5.service.ZjXmJxQuarterlyLibraryDetailsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("zjXmJxQuarterlyLibraryDetailsService")
public class ZjXmJxQuarterlyLibraryDetailsServiceImpl implements ZjXmJxQuarterlyLibraryDetailsService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxQuarterlyLibraryDetailsMapper zjXmJxQuarterlyLibraryDetailsMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyProjectDetailsMapper zjXmJxQuarterlyProjectDetailsMapper;
	@Autowired(required = true)
	private ZjXmSalaryDepartmentMapper zjXmSalaryDepartmentMapper;

	@Override
	public ResponseEntity getZjXmJxQuarterlyLibraryDetailsListByCondition(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		if (zjXmJxQuarterlyLibraryDetails == null) {
			zjXmJxQuarterlyLibraryDetails = new ZjXmJxQuarterlyLibraryDetails();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxQuarterlyLibraryDetails.getPage(), zjXmJxQuarterlyLibraryDetails.getLimit());
		// 获取数据
		List<ZjXmJxQuarterlyLibraryDetails> zjXmJxQuarterlyLibraryDetailsList = zjXmJxQuarterlyLibraryDetailsMapper
				.selectByZjXmJxQuarterlyLibraryDetailsList(zjXmJxQuarterlyLibraryDetails);
		// 得到分页信息
		PageInfo<ZjXmJxQuarterlyLibraryDetails> p = new PageInfo<>(zjXmJxQuarterlyLibraryDetailsList);

		return repEntity.okList(zjXmJxQuarterlyLibraryDetailsList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyLibraryDetailsDetail(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		if (zjXmJxQuarterlyLibraryDetails == null) {
			zjXmJxQuarterlyLibraryDetails = new ZjXmJxQuarterlyLibraryDetails();
		}
		// 获取数据
		ZjXmJxQuarterlyLibraryDetails dbZjXmJxQuarterlyLibraryDetails = zjXmJxQuarterlyLibraryDetailsMapper
				.selectByPrimaryKey(zjXmJxQuarterlyLibraryDetails.getDetailsId());
		// 数据存在
		if (dbZjXmJxQuarterlyLibraryDetails != null) {
			return repEntity.ok(dbZjXmJxQuarterlyLibraryDetails);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxQuarterlyLibraryDetails(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxQuarterlyLibraryDetails.setDetailsId(UuidUtil.generate());
		zjXmJxQuarterlyLibraryDetails.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxQuarterlyLibraryDetailsMapper.insert(zjXmJxQuarterlyLibraryDetails);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyLibraryDetails);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxQuarterlyLibraryDetails(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxQuarterlyLibraryDetails dbZjXmJxQuarterlyLibraryDetails = zjXmJxQuarterlyLibraryDetailsMapper
				.selectByPrimaryKey(zjXmJxQuarterlyLibraryDetails.getDetailsId());
		if (dbZjXmJxQuarterlyLibraryDetails != null
				&& StrUtil.isNotEmpty(dbZjXmJxQuarterlyLibraryDetails.getDetailsId())) {
			// 季度考核id
			dbZjXmJxQuarterlyLibraryDetails.setAssessmentId(zjXmJxQuarterlyLibraryDetails.getAssessmentId());
			// 部门ID
			dbZjXmJxQuarterlyLibraryDetails.setDeptId(zjXmJxQuarterlyLibraryDetails.getDeptId());
			// 部门名称
			dbZjXmJxQuarterlyLibraryDetails.setDeptName(zjXmJxQuarterlyLibraryDetails.getDeptName());
			// 项目状态id
			dbZjXmJxQuarterlyLibraryDetails.setProjectStatus(zjXmJxQuarterlyLibraryDetails.getProjectStatus());
			// 项目状态名称
			dbZjXmJxQuarterlyLibraryDetails.setProjectStatusName(zjXmJxQuarterlyLibraryDetails.getProjectStatusName());
			// 项目类型id
			dbZjXmJxQuarterlyLibraryDetails.setProjectType(zjXmJxQuarterlyLibraryDetails.getProjectType());
			// 项目类型名称
			dbZjXmJxQuarterlyLibraryDetails.setProjectTypeName(zjXmJxQuarterlyLibraryDetails.getProjectTypeName());
			// 考核标题
			dbZjXmJxQuarterlyLibraryDetails.setLibraryTitle(zjXmJxQuarterlyLibraryDetails.getLibraryTitle());
			// 考核内容
			dbZjXmJxQuarterlyLibraryDetails.setLibraryContent(zjXmJxQuarterlyLibraryDetails.getLibraryContent());
			// 考核责任人key
			dbZjXmJxQuarterlyLibraryDetails.setPersonLiableKey(zjXmJxQuarterlyLibraryDetails.getPersonLiableKey());
			// 考核责任人姓名
			dbZjXmJxQuarterlyLibraryDetails.setPersonLiableName(zjXmJxQuarterlyLibraryDetails.getPersonLiableName());
			// 是否是固定分数
			dbZjXmJxQuarterlyLibraryDetails.setIsFixedScore(zjXmJxQuarterlyLibraryDetails.getIsFixedScore());
			// 分数
			dbZjXmJxQuarterlyLibraryDetails.setScore(zjXmJxQuarterlyLibraryDetails.getScore());
			// 加/减分下限
			dbZjXmJxQuarterlyLibraryDetails.setLowerLimitScore(zjXmJxQuarterlyLibraryDetails.getLowerLimitScore());
			// 加/减分上限
			dbZjXmJxQuarterlyLibraryDetails.setUpperLimitScore(zjXmJxQuarterlyLibraryDetails.getUpperLimitScore());
			// 是否是收尾项目
			dbZjXmJxQuarterlyLibraryDetails.setIsClosed(zjXmJxQuarterlyLibraryDetails.getIsClosed());
			// 确认状态
			dbZjXmJxQuarterlyLibraryDetails.setConfirmStatus(zjXmJxQuarterlyLibraryDetails.getConfirmStatus());
			// 备注
			dbZjXmJxQuarterlyLibraryDetails.setRemarks(zjXmJxQuarterlyLibraryDetails.getRemarks());
			// 排序
			dbZjXmJxQuarterlyLibraryDetails.setSort(zjXmJxQuarterlyLibraryDetails.getSort());
			// 共通
			dbZjXmJxQuarterlyLibraryDetails.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyLibraryDetailsMapper.updateByPrimaryKey(dbZjXmJxQuarterlyLibraryDetails);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyLibraryDetails);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyLibraryDetails(
			List<ZjXmJxQuarterlyLibraryDetails> zjXmJxQuarterlyLibraryDetailsList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyLibraryDetailsList != null && zjXmJxQuarterlyLibraryDetailsList.size() > 0) {
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails = new ZjXmJxQuarterlyLibraryDetails();
			zjXmJxQuarterlyLibraryDetails.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyLibraryDetailsMapper.batchDeleteUpdateZjXmJxQuarterlyLibraryDetails(
					zjXmJxQuarterlyLibraryDetailsList, zjXmJxQuarterlyLibraryDetails);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyLibraryDetailsList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity batchConfirmZjXmJxQuarterlyLibraryDetails(
			List<ZjXmJxQuarterlyLibraryDetails> zjXmJxQuarterlyLibraryDetailsList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyLibraryDetailsList != null && zjXmJxQuarterlyLibraryDetailsList.size() > 0) {
			flag = zjXmJxQuarterlyLibraryDetailsMapper
					.batchConfirmZjXmJxQuarterlyLibraryDetails(zjXmJxQuarterlyLibraryDetailsList);
			if (flag != 0) {
				// 根据传参id集合获取数据库完整数据
				ZjXmJxQuarterlyLibraryDetails search = new ZjXmJxQuarterlyLibraryDetails();
				search.setLibraryDetailsList(zjXmJxQuarterlyLibraryDetailsList);
				List<ZjXmJxQuarterlyLibraryDetails> dbList = zjXmJxQuarterlyLibraryDetailsMapper
						.getZjXmJxQuarterlyLibraryDetailsList(search);
				if (dbList.size() > 0) {
					List<ZjXmJxQuarterlyProjectDetails> insertList = Lists.newArrayList();
					for (ZjXmJxQuarterlyLibraryDetails dbData : dbList) {
						// 根据项目状态和项目类型获取项目集合
						ZjXmSalaryDepartment zjXmSalaryDepartment = new ZjXmSalaryDepartment();
						if (!StrUtil.equals("0", dbData.getProjectStatus())) {
							zjXmSalaryDepartment.setActualStatus(dbData.getProjectStatus());
						}
						if (!StrUtil.equals("0", dbData.getProjectType())) {
							zjXmSalaryDepartment.setActualType(dbData.getProjectType());
						}
						List<ZjXmSalaryDepartment> projectList = zjXmSalaryDepartmentMapper
								.selectByZjXmSalaryDepartmentList(zjXmSalaryDepartment);
						// 遍利项目集合构建数据
						for (ZjXmSalaryDepartment dbProject : projectList) {
							ZjXmJxQuarterlyProjectDetails insertData = new ZjXmJxQuarterlyProjectDetails();
							BeanUtil.copyProperties(dbData, insertData);
							insertData.setDetailsId(UuidUtil.generate());
							insertData.setProjectId(dbProject.getDepartmentId());
							insertData.setProjectName(dbProject.getDepartmentName());
							insertData.setManagerKey(dbProject.getManagerKey());
							insertData.setManagerName(dbProject.getManagerName());
							insertData.setActualStatus(dbProject.getActualStatus());
							insertData.setActualStatusName(dbProject.getActualStatusName());
							insertData.setActualType(dbProject.getActualType());
							insertData.setActualTypeName(dbProject.getActualTypeName());
							// insertData.setActualScore(actualScore);
							insertData.setConfirmStatus("0");
							insertData.setCreateUserInfo(userKey, realName);
							insertList.add(insertData);
						}
					}
					flag = zjXmJxQuarterlyProjectDetailsMapper.batchInsertZjXmJxQuarterlyProjectDetails(insertList);
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.layerMessage("no", "确认失败，请重试。");
		} else {
			return repEntity.ok("确认成功。");
		}
	}

	@Override
	public ResponseEntity checkZjXmJxQuarterlyLibraryDetailsConfirmStatus(
			ZjXmJxQuarterlyLibraryDetails zjXmJxQuarterlyLibraryDetails) {
		JSONObject obj = JSONUtil.createObj();
		int count = zjXmJxQuarterlyLibraryDetailsMapper
				.checkZjXmJxQuarterlyLibraryDetailsConfirmStatus(zjXmJxQuarterlyLibraryDetails);
		if (count > 0) {
			obj.set("buttonFlag", "show");
		} else {
			obj.set("buttonFlag", "hide");
		}
		// 失败
		return repEntity.ok(obj);
	}
}
