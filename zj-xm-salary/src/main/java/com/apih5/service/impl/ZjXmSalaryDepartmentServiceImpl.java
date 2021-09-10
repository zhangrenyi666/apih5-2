package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmSalaryDepartmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryDepartment;
import com.apih5.service.ZjXmSalaryDepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryDepartmentService")
public class ZjXmSalaryDepartmentServiceImpl implements ZjXmSalaryDepartmentService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmSalaryDepartmentMapper zjXmSalaryDepartmentMapper;

	@Override
	public ResponseEntity getZjXmSalaryDepartmentListByCondition(ZjXmSalaryDepartment zjXmSalaryDepartment) {
		if (zjXmSalaryDepartment == null) {
			zjXmSalaryDepartment = new ZjXmSalaryDepartment();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryDepartment.getPage(), zjXmSalaryDepartment.getLimit());
		// 获取数据
		List<ZjXmSalaryDepartment> zjXmSalaryDepartmentList = zjXmSalaryDepartmentMapper
				.selectByZjXmSalaryDepartmentList(zjXmSalaryDepartment);
		// 得到分页信息
		PageInfo<ZjXmSalaryDepartment> p = new PageInfo<>(zjXmSalaryDepartmentList);

		return repEntity.okList(zjXmSalaryDepartmentList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryDepartmentDetails(ZjXmSalaryDepartment zjXmSalaryDepartment) {
		if (zjXmSalaryDepartment == null) {
			zjXmSalaryDepartment = new ZjXmSalaryDepartment();
		}
		// 获取数据
		ZjXmSalaryDepartment dbZjXmSalaryDepartment = zjXmSalaryDepartmentMapper
				.selectByPrimaryKey(zjXmSalaryDepartment.getDepartmentId());
		// 数据存在
		if (dbZjXmSalaryDepartment != null) {
			return repEntity.ok(dbZjXmSalaryDepartment);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryDepartment(ZjXmSalaryDepartment zjXmSalaryDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryDepartment.setDepartmentId(UuidUtil.generate());
		zjXmSalaryDepartment.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryDepartmentMapper.insert(zjXmSalaryDepartment);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryDepartment);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryDepartment(ZjXmSalaryDepartment zjXmSalaryDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryDepartment dbzjXmSalaryDepartment = zjXmSalaryDepartmentMapper
				.selectByPrimaryKey(zjXmSalaryDepartment.getDepartmentId());
		if (dbzjXmSalaryDepartment != null && StrUtil.isNotEmpty(dbzjXmSalaryDepartment.getDepartmentId())) {
			// 所属项目/部门名称
			dbzjXmSalaryDepartment.setDepartmentName(zjXmSalaryDepartment.getDepartmentName());
			// 项目类型
			dbzjXmSalaryDepartment.setProjectType(zjXmSalaryDepartment.getProjectType());
			// 营业额（万元）
			dbzjXmSalaryDepartment.setTurnover(zjXmSalaryDepartment.getTurnover());
			// 折算总合同金额（万元）
			dbzjXmSalaryDepartment.setContractAmount(zjXmSalaryDepartment.getContractAmount());
			// 折算平均年产值（万元）
			dbzjXmSalaryDepartment.setOutputValue(zjXmSalaryDepartment.getOutputValue());
			// 工程造价（万元）
			dbzjXmSalaryDepartment.setEngineeringCost(zjXmSalaryDepartment.getEngineeringCost());
			// 年度计量金额（万元）
			dbzjXmSalaryDepartment.setMeasurementAmount(zjXmSalaryDepartment.getMeasurementAmount());
			// 合同工期（月）
			dbzjXmSalaryDepartment.setContractPeriod(zjXmSalaryDepartment.getContractPeriod());
			// 实际开工日期
			dbzjXmSalaryDepartment.setActualStartDate(zjXmSalaryDepartment.getActualStartDate());
			// 实际竣工日期
			dbzjXmSalaryDepartment.setActualEndDate(zjXmSalaryDepartment.getActualEndDate());
			// 所属地区code
			dbzjXmSalaryDepartment.setRegionCode(zjXmSalaryDepartment.getRegionCode());
			// 所属地区name
			dbzjXmSalaryDepartment.setRegionName(zjXmSalaryDepartment.getRegionName());
			// 项目类型（项目人员配备标准）
			dbzjXmSalaryDepartment.setProjectStaffType(zjXmSalaryDepartment.getProjectStaffType());
			// 项目阶段（项目人员配备标准）
			dbzjXmSalaryDepartment.setProjectPhase(zjXmSalaryDepartment.getProjectPhase());
			// 部门类型
			dbzjXmSalaryDepartment.setDeptType(zjXmSalaryDepartment.getDeptType());
			// 人数单位(个值/区间)
			dbzjXmSalaryDepartment.setNumberUnit(zjXmSalaryDepartment.getNumberUnit());
			// 人数(个值)
			dbzjXmSalaryDepartment.setSingleValue(zjXmSalaryDepartment.getSingleValue());
			// 人数(左区间)
			dbzjXmSalaryDepartment.setLeftValue(zjXmSalaryDepartment.getLeftValue());
			// 人数(右区间)
			dbzjXmSalaryDepartment.setRightValue(zjXmSalaryDepartment.getRightValue());
			// 所属公司
			dbzjXmSalaryDepartment.setCompany(zjXmSalaryDepartment.getCompany());
			// 所属总承包
			dbzjXmSalaryDepartment.setGeneralContracting(zjXmSalaryDepartment.getGeneralContracting());
			// 所属项目公司
			dbzjXmSalaryDepartment.setProjectCompany(zjXmSalaryDepartment.getProjectCompany());
			// pid
			dbzjXmSalaryDepartment.setDepartmentParentId(zjXmSalaryDepartment.getDepartmentParentId());
			// pidAll
			dbzjXmSalaryDepartment.setDepartmentPath(zjXmSalaryDepartment.getDepartmentPath());
			// pnameAll
			dbzjXmSalaryDepartment.setDepartmentPathName(zjXmSalaryDepartment.getDepartmentPathName());
			// deptFlag
			dbzjXmSalaryDepartment.setDepartmentFlag(zjXmSalaryDepartment.getDepartmentFlag());
			// deptFlagName
			dbzjXmSalaryDepartment.setDepartmentFlagName(zjXmSalaryDepartment.getDepartmentFlagName());
			// companyId
			dbzjXmSalaryDepartment.setCompanyId(zjXmSalaryDepartment.getCompanyId());
			// companyName
			dbzjXmSalaryDepartment.setCompanyName(zjXmSalaryDepartment.getCompanyName());
			// projectId
			dbzjXmSalaryDepartment.setProjectId(zjXmSalaryDepartment.getProjectId());
			// projectName
			dbzjXmSalaryDepartment.setProjectName(zjXmSalaryDepartment.getProjectName());
			// otherId
			dbzjXmSalaryDepartment.setOtherId(zjXmSalaryDepartment.getOtherId());
			// 备注
			dbzjXmSalaryDepartment.setRemarks(zjXmSalaryDepartment.getRemarks());
			// 排序
			dbzjXmSalaryDepartment.setSort(zjXmSalaryDepartment.getSort());
			// 共通
			dbzjXmSalaryDepartment.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryDepartmentMapper.updateByPrimaryKey(dbzjXmSalaryDepartment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryDepartment);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryDepartment(List<ZjXmSalaryDepartment> zjXmSalaryDepartmentList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryDepartmentList != null && zjXmSalaryDepartmentList.size() > 0) {
			ZjXmSalaryDepartment zjXmSalaryDepartment = new ZjXmSalaryDepartment();
			zjXmSalaryDepartment.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryDepartmentMapper.batchDeleteUpdateZjXmSalaryDepartment(zjXmSalaryDepartmentList,
					zjXmSalaryDepartment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryDepartmentList);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryDepartmentPrj(ZjXmSalaryDepartment zjXmSalaryDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryDepartment dbzjXmSalaryDepartment = zjXmSalaryDepartmentMapper
				.selectByPrimaryKey(zjXmSalaryDepartment.getDepartmentId());
		if (dbzjXmSalaryDepartment != null && StrUtil.isNotEmpty(dbzjXmSalaryDepartment.getDepartmentId())) {
			// 所属项目/部门名称
			dbzjXmSalaryDepartment.setDepartmentName(zjXmSalaryDepartment.getDepartmentName());
			// 项目类型
			dbzjXmSalaryDepartment.setProjectType(zjXmSalaryDepartment.getProjectType());
			// 营业额（万元）
			dbzjXmSalaryDepartment.setTurnover(zjXmSalaryDepartment.getTurnover());
			// 折算总合同金额（万元）
			dbzjXmSalaryDepartment.setContractAmount(zjXmSalaryDepartment.getContractAmount());
			// 折算平均年产值（万元）
			dbzjXmSalaryDepartment.setOutputValue(zjXmSalaryDepartment.getOutputValue());
			// 工程造价（万元）
			dbzjXmSalaryDepartment.setEngineeringCost(zjXmSalaryDepartment.getEngineeringCost());
			// 年度计量金额（万元）
			dbzjXmSalaryDepartment.setMeasurementAmount(zjXmSalaryDepartment.getMeasurementAmount());
			// 合同工期（月）
			dbzjXmSalaryDepartment.setContractPeriod(zjXmSalaryDepartment.getContractPeriod());
			// 实际开工日期
			dbzjXmSalaryDepartment.setActualStartDate(zjXmSalaryDepartment.getActualStartDate());
			// 实际竣工日期
			dbzjXmSalaryDepartment.setActualEndDate(zjXmSalaryDepartment.getActualEndDate());
			// 所属地区code
			dbzjXmSalaryDepartment.setRegionCode(zjXmSalaryDepartment.getRegionCode());
			// 所属地区name
			dbzjXmSalaryDepartment.setRegionName(zjXmSalaryDepartment.getRegionName());
			// 项目类型（项目人员配备标准）
			dbzjXmSalaryDepartment.setProjectStaffType(zjXmSalaryDepartment.getProjectStaffType());
			// 项目阶段（项目人员配备标准）
			dbzjXmSalaryDepartment.setProjectPhase(zjXmSalaryDepartment.getProjectPhase());
			// pid
			dbzjXmSalaryDepartment.setDepartmentParentId(zjXmSalaryDepartment.getDepartmentParentId());
			// pidAll
			dbzjXmSalaryDepartment.setDepartmentPath(zjXmSalaryDepartment.getDepartmentPath());
			// pnameAll
			dbzjXmSalaryDepartment.setDepartmentPathName(zjXmSalaryDepartment.getDepartmentPathName());
			// deptFlag
			dbzjXmSalaryDepartment.setDepartmentFlag(zjXmSalaryDepartment.getDepartmentFlag());
			// deptFlagName
			dbzjXmSalaryDepartment.setDepartmentFlagName(zjXmSalaryDepartment.getDepartmentFlagName());
			// companyId
			dbzjXmSalaryDepartment.setCompanyId(zjXmSalaryDepartment.getCompanyId());
			// companyName
			dbzjXmSalaryDepartment.setCompanyName(zjXmSalaryDepartment.getCompanyName());
			// projectId
			dbzjXmSalaryDepartment.setProjectId(zjXmSalaryDepartment.getProjectId());
			// projectName
			dbzjXmSalaryDepartment.setProjectName(zjXmSalaryDepartment.getProjectName());
			// otherId
			dbzjXmSalaryDepartment.setOtherId(zjXmSalaryDepartment.getOtherId());
			// 备注
			dbzjXmSalaryDepartment.setRemarks(zjXmSalaryDepartment.getRemarks());
			// 排序
			dbzjXmSalaryDepartment.setSort(zjXmSalaryDepartment.getSort());
			// 共通
			dbzjXmSalaryDepartment.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryDepartmentMapper.updateByPrimaryKey(dbzjXmSalaryDepartment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryDepartment);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryDepartmentDept(ZjXmSalaryDepartment zjXmSalaryDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryDepartment dbzjXmSalaryDepartment = zjXmSalaryDepartmentMapper
				.selectByPrimaryKey(zjXmSalaryDepartment.getDepartmentId());
		if (dbzjXmSalaryDepartment != null && StrUtil.isNotEmpty(dbzjXmSalaryDepartment.getDepartmentId())) {
			// 所属项目/部门名称
			dbzjXmSalaryDepartment.setDepartmentName(zjXmSalaryDepartment.getDepartmentName());
			// 部门类型
			dbzjXmSalaryDepartment.setDeptType(zjXmSalaryDepartment.getDeptType());
			// 人数单位(个值/区间)
			dbzjXmSalaryDepartment.setNumberUnit(zjXmSalaryDepartment.getNumberUnit());
			// 人数(个值)
			dbzjXmSalaryDepartment.setSingleValue(zjXmSalaryDepartment.getSingleValue());
			// 人数(左区间)
			dbzjXmSalaryDepartment.setLeftValue(zjXmSalaryDepartment.getLeftValue());
			// 人数(右区间)
			dbzjXmSalaryDepartment.setRightValue(zjXmSalaryDepartment.getRightValue());
			// pid
			dbzjXmSalaryDepartment.setDepartmentParentId(zjXmSalaryDepartment.getDepartmentParentId());
			// pidAll
			dbzjXmSalaryDepartment.setDepartmentPath(zjXmSalaryDepartment.getDepartmentPath());
			// pnameAll
			dbzjXmSalaryDepartment.setDepartmentPathName(zjXmSalaryDepartment.getDepartmentPathName());
			// deptFlag
			dbzjXmSalaryDepartment.setDepartmentFlag(zjXmSalaryDepartment.getDepartmentFlag());
			// deptFlagName
			dbzjXmSalaryDepartment.setDepartmentFlagName(zjXmSalaryDepartment.getDepartmentFlagName());
			// companyId
			dbzjXmSalaryDepartment.setCompanyId(zjXmSalaryDepartment.getCompanyId());
			// companyName
			dbzjXmSalaryDepartment.setCompanyName(zjXmSalaryDepartment.getCompanyName());
			// projectId
			dbzjXmSalaryDepartment.setProjectId(zjXmSalaryDepartment.getProjectId());
			// projectName
			dbzjXmSalaryDepartment.setProjectName(zjXmSalaryDepartment.getProjectName());
			// otherId
			dbzjXmSalaryDepartment.setOtherId(zjXmSalaryDepartment.getOtherId());
			// 备注
			dbzjXmSalaryDepartment.setRemarks(zjXmSalaryDepartment.getRemarks());
			// 排序
			dbzjXmSalaryDepartment.setSort(zjXmSalaryDepartment.getSort());
			// 共通
			dbzjXmSalaryDepartment.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryDepartmentMapper.updateByPrimaryKey(dbzjXmSalaryDepartment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryDepartment);
		}
	}
}
