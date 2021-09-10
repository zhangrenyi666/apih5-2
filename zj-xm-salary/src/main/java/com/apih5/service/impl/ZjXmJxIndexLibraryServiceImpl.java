package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxIndexLibraryMapper;
import com.apih5.mybatis.pojo.ZjXmJxIndexLibrary;
import com.apih5.service.ZjXmJxIndexLibraryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxIndexLibraryService")
public class ZjXmJxIndexLibraryServiceImpl implements ZjXmJxIndexLibraryService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxIndexLibraryMapper zjXmJxIndexLibraryMapper;
	@Autowired(required = true)
	private SysDepartmentService sysDepartmentService;

	@Override
	public ResponseEntity getZjXmJxIndexLibraryListByCondition(ZjXmJxIndexLibrary zjXmJxIndexLibrary) {
		if (zjXmJxIndexLibrary == null) {
			zjXmJxIndexLibrary = new ZjXmJxIndexLibrary();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxIndexLibrary.getPage(), zjXmJxIndexLibrary.getLimit());
		// 获取数据
		List<ZjXmJxIndexLibrary> zjXmJxIndexLibraryList = zjXmJxIndexLibraryMapper
				.selectByZjXmJxIndexLibraryList(zjXmJxIndexLibrary);
		// 得到分页信息
		PageInfo<ZjXmJxIndexLibrary> p = new PageInfo<>(zjXmJxIndexLibraryList);

		return repEntity.okList(zjXmJxIndexLibraryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxIndexLibraryDetails(ZjXmJxIndexLibrary zjXmJxIndexLibrary) {
		if (zjXmJxIndexLibrary == null) {
			zjXmJxIndexLibrary = new ZjXmJxIndexLibrary();
		}
		// 获取数据
		ZjXmJxIndexLibrary dbZjXmJxIndexLibrary = zjXmJxIndexLibraryMapper
				.selectByPrimaryKey(zjXmJxIndexLibrary.getLibraryId());
		// 数据存在
		if (dbZjXmJxIndexLibrary != null) {
			return repEntity.ok(dbZjXmJxIndexLibrary);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxIndexLibrary(ZjXmJxIndexLibrary zjXmJxIndexLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		zjXmJxIndexLibrary.setLibraryId(UuidUtil.generate());
		zjXmJxIndexLibrary.setCreateUserInfo(userKey, realName);
		// check同一个部门下的权重和不能超过100
		if (StrUtil.isEmpty(zjXmJxIndexLibrary.getDeptId())) {
//			ZjXmJxIndexLibrary check = new ZjXmJxIndexLibrary();
//			check.setDeptId(zjXmJxIndexLibrary.getDeptId());
//			int sum = zjXmJxIndexLibraryMapper.sumZjXmJxIndexLibraryByCondition(check);
//			if (zjXmJxIndexLibrary.getWeightValue().intValue() + sum > 100) {
//				return repEntity.layerMessage("no", "同一部门下权重和不能超过100。");
//			}
			return repEntity.layerMessage("no", "部门信息不能为空。");
		}
		// 通过前端传过来的deptId获取项目id和项目name
		SysDepartment dbDeptInfo = sysDepartmentService.getSysDepartmentByPrimaryKey(zjXmJxIndexLibrary.getDeptId());
		if (dbDeptInfo == null || StrUtil.isEmpty(dbDeptInfo.getProjectId())
				|| StrUtil.isEmpty(dbDeptInfo.getProjectName())) {
			return repEntity.layerMessage("no", "未获取到该部门的项目信息。");
		} else {
			zjXmJxIndexLibrary.setProjectId(dbDeptInfo.getProjectId());
			zjXmJxIndexLibrary.setProjectName(dbDeptInfo.getProjectName());
		}
		// 如果新增者权限ext=1则禁止普通人员删改
		if (StrUtil.equals("1", ext1)) {
			zjXmJxIndexLibrary.setForbidFlag("1");
		}
		int flag = zjXmJxIndexLibraryMapper.insert(zjXmJxIndexLibrary);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxIndexLibrary);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxIndexLibrary(ZjXmJxIndexLibrary zjXmJxIndexLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		int flag = 0;
		ZjXmJxIndexLibrary dbzjXmJxIndexLibrary = zjXmJxIndexLibraryMapper
				.selectByPrimaryKey(zjXmJxIndexLibrary.getLibraryId());
		// check部门信息不能为空
		if (StrUtil.isEmpty(zjXmJxIndexLibrary.getDeptId())) {
			return repEntity.layerMessage("no", "部门信息不能为空。");
		}
		if (dbzjXmJxIndexLibrary != null && StrUtil.isNotEmpty(dbzjXmJxIndexLibrary.getLibraryId())) {
			// check如果新增者权限ext=1则禁止普通人员删改
			if (StrUtil.equals("1", dbzjXmJxIndexLibrary.getForbidFlag()) && !StrUtil.equals("1", ext1)) {
				return repEntity.layerMessage("no", "该指标您无权限修改。");
			}
			// 部门ID
			// dbzjXmJxIndexLibrary.setDeptId(zjXmJxIndexLibrary.getDeptId());
			// 部门名称
			// dbzjXmJxIndexLibrary.setDeptName(zjXmJxIndexLibrary.getDeptName());
			// 项目id
			// dbzjXmJxIndexLibrary.setProjectId(zjXmJxIndexLibrary.getProjectId());
			// 项目名称
			// dbzjXmJxIndexLibrary.setProjectName(zjXmJxIndexLibrary.getProjectName());
			// 成本责任指标
			dbzjXmJxIndexLibrary.setCostDutyIndex(zjXmJxIndexLibrary.getCostDutyIndex());
			// 目标值
			dbzjXmJxIndexLibrary.setTargetValue(zjXmJxIndexLibrary.getTargetValue());
			// 评价计分标准
			dbzjXmJxIndexLibrary.setScoringStandard(zjXmJxIndexLibrary.getScoringStandard());
			// 是否自动评分
			dbzjXmJxIndexLibrary.setIsAutomaticScoring(zjXmJxIndexLibrary.getIsAutomaticScoring());
			// 来源
			dbzjXmJxIndexLibrary.setDataSources(zjXmJxIndexLibrary.getDataSources());
			// 是否必选
			// dbzjXmJxIndexLibrary.setIsMandatory(zjXmJxIndexLibrary.getIsMandatory());
			// 权重
			// dbzjXmJxIndexLibrary.setWeightValue(zjXmJxIndexLibrary.getWeightValue());
			// 备注
			dbzjXmJxIndexLibrary.setRemarks(zjXmJxIndexLibrary.getRemarks());
			// 共通
			dbzjXmJxIndexLibrary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxIndexLibraryMapper.updateByPrimaryKey(dbzjXmJxIndexLibrary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxIndexLibrary);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxIndexLibrary(List<ZjXmJxIndexLibrary> zjXmJxIndexLibraryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String ext1 = TokenUtils.getExt1(request);
		// check如果ext1 != 1登陆则判断是否有禁止删改的指标
		if (!StrUtil.equals("1", ext1)) {
			// 查询db是否有forbidFlag = 1的指标，有则禁止删除
			int count = zjXmJxIndexLibraryMapper.checkZjXmJxIndexLibraryByCondition(zjXmJxIndexLibraryList);
			if (count > 0) {
				return repEntity.layerMessage("no", "所选内容您无权限删除,请重新选择。");
			}
		}
		int flag = 0;
		if (zjXmJxIndexLibraryList != null && zjXmJxIndexLibraryList.size() > 0) {
			ZjXmJxIndexLibrary zjXmJxIndexLibrary = new ZjXmJxIndexLibrary();
			zjXmJxIndexLibrary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxIndexLibraryMapper.batchDeleteUpdateZjXmJxIndexLibrary(zjXmJxIndexLibraryList,
					zjXmJxIndexLibrary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxIndexLibraryList);
		}
	}
}
