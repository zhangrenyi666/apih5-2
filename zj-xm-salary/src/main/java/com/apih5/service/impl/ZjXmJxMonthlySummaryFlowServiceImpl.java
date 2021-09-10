package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxMonthlySummaryFlowMapper;
import com.apih5.mybatis.pojo.ZjXmJxMonthlySummaryFlow;
import com.apih5.service.ZjXmJxMonthlySummaryFlowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxMonthlySummaryFlowService")
public class ZjXmJxMonthlySummaryFlowServiceImpl implements ZjXmJxMonthlySummaryFlowService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmJxMonthlySummaryFlowMapper zjXmJxMonthlySummaryFlowMapper;

	@Override
	public ResponseEntity getZjXmJxMonthlySummaryFlowListByCondition(
			ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow) {
		if (zjXmJxMonthlySummaryFlow == null) {
			zjXmJxMonthlySummaryFlow = new ZjXmJxMonthlySummaryFlow();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxMonthlySummaryFlow.getPage(), zjXmJxMonthlySummaryFlow.getLimit());
		// 获取数据
		List<ZjXmJxMonthlySummaryFlow> zjXmJxMonthlySummaryFlowList = zjXmJxMonthlySummaryFlowMapper
				.selectByZjXmJxMonthlySummaryFlowList(zjXmJxMonthlySummaryFlow);
		// 得到分页信息
		PageInfo<ZjXmJxMonthlySummaryFlow> p = new PageInfo<>(zjXmJxMonthlySummaryFlowList);

		return repEntity.okList(zjXmJxMonthlySummaryFlowList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxMonthlySummaryFlowDetails(ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow) {
		if (zjXmJxMonthlySummaryFlow == null) {
			zjXmJxMonthlySummaryFlow = new ZjXmJxMonthlySummaryFlow();
		}
		// 获取数据
		ZjXmJxMonthlySummaryFlow dbZjXmJxMonthlySummaryFlow = zjXmJxMonthlySummaryFlowMapper
				.selectByPrimaryKey(zjXmJxMonthlySummaryFlow.getMonthlyFlowId());
		// 数据存在
		if (dbZjXmJxMonthlySummaryFlow != null) {
			return repEntity.ok(dbZjXmJxMonthlySummaryFlow);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxMonthlySummaryFlow(ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxMonthlySummaryFlow.setMonthlyFlowId(UuidUtil.generate());
		zjXmJxMonthlySummaryFlow.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxMonthlySummaryFlowMapper.insert(zjXmJxMonthlySummaryFlow);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxMonthlySummaryFlow);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxMonthlySummaryFlow(ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxMonthlySummaryFlow dbzjXmJxMonthlySummaryFlow = zjXmJxMonthlySummaryFlowMapper
				.selectByPrimaryKey(zjXmJxMonthlySummaryFlow.getMonthlyFlowId());
		if (dbzjXmJxMonthlySummaryFlow != null && StrUtil.isNotEmpty(dbzjXmJxMonthlySummaryFlow.getMonthlyFlowId())) {
			// 项目月度考核主键
			dbzjXmJxMonthlySummaryFlow.setAssessmentId(zjXmJxMonthlySummaryFlow.getAssessmentId());
			// 考核月份
			dbzjXmJxMonthlySummaryFlow.setYearMonth(zjXmJxMonthlySummaryFlow.getYearMonth());
			// 项目id
			dbzjXmJxMonthlySummaryFlow.setProjectId(zjXmJxMonthlySummaryFlow.getProjectId());
			// 项目名称
			dbzjXmJxMonthlySummaryFlow.setProjectName(zjXmJxMonthlySummaryFlow.getProjectName());
			// 部门id
			dbzjXmJxMonthlySummaryFlow.setDeptId(zjXmJxMonthlySummaryFlow.getDeptId());
			// 部门名称
			dbzjXmJxMonthlySummaryFlow.setDeptName(zjXmJxMonthlySummaryFlow.getDeptName());
			// 汇总审核表类型
			dbzjXmJxMonthlySummaryFlow.setFlowType(zjXmJxMonthlySummaryFlow.getFlowType());
			// 备注
			dbzjXmJxMonthlySummaryFlow.setOpinionField1(zjXmJxMonthlySummaryFlow.getOpinionField1());
			// 备注
			dbzjXmJxMonthlySummaryFlow.setOpinionField2(zjXmJxMonthlySummaryFlow.getOpinionField2());
			// 备注
			dbzjXmJxMonthlySummaryFlow.setOpinionField3(zjXmJxMonthlySummaryFlow.getOpinionField3());
			// 备注
			dbzjXmJxMonthlySummaryFlow.setOpinionField4(zjXmJxMonthlySummaryFlow.getOpinionField4());
			// 备注
			dbzjXmJxMonthlySummaryFlow.setOpinionField5(zjXmJxMonthlySummaryFlow.getOpinionField5());
			// 流程id
			dbzjXmJxMonthlySummaryFlow.setApih5FlowId(zjXmJxMonthlySummaryFlow.getApih5FlowId());
			// work_id
			dbzjXmJxMonthlySummaryFlow.setWorkId(zjXmJxMonthlySummaryFlow.getWorkId());
			// 工序审核状态
			dbzjXmJxMonthlySummaryFlow.setApih5FlowStatus(zjXmJxMonthlySummaryFlow.getApih5FlowStatus());
			// 流程状态
			dbzjXmJxMonthlySummaryFlow.setApih5FlowNodeStatus(zjXmJxMonthlySummaryFlow.getApih5FlowNodeStatus());
			// 备注
			dbzjXmJxMonthlySummaryFlow.setRemarks(zjXmJxMonthlySummaryFlow.getRemarks());
			// 排序
			dbzjXmJxMonthlySummaryFlow.setSort(zjXmJxMonthlySummaryFlow.getSort());
			// 共通
			dbzjXmJxMonthlySummaryFlow.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlySummaryFlowMapper.updateByPrimaryKey(dbzjXmJxMonthlySummaryFlow);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxMonthlySummaryFlow);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxMonthlySummaryFlow(
			List<ZjXmJxMonthlySummaryFlow> zjXmJxMonthlySummaryFlowList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxMonthlySummaryFlowList != null && zjXmJxMonthlySummaryFlowList.size() > 0) {
			ZjXmJxMonthlySummaryFlow zjXmJxMonthlySummaryFlow = new ZjXmJxMonthlySummaryFlow();
			zjXmJxMonthlySummaryFlow.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlySummaryFlowMapper
					.batchDeleteUpdateZjXmJxMonthlySummaryFlow(zjXmJxMonthlySummaryFlowList, zjXmJxMonthlySummaryFlow);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxMonthlySummaryFlowList);
		}
	}
}