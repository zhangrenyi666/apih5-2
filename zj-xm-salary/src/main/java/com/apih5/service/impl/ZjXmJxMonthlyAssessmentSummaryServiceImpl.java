package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxAssessmentUserScoreMapper;
import com.apih5.mybatis.dao.ZjXmJxMonthlyAssessmentSummaryMapper;
import com.apih5.mybatis.dao.ZjXmJxMonthlySummaryFlowMapper;
import com.apih5.mybatis.dao.ZjXmJxWeightManagementMapper;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessmentSummary;
import com.apih5.mybatis.pojo.ZjXmJxMonthlySummaryFlow;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxWeightManagement;
import com.apih5.service.ZjXmJxMonthlyAssessmentSummaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjXmJxMonthlyAssessmentSummaryService")
public class ZjXmJxMonthlyAssessmentSummaryServiceImpl implements ZjXmJxMonthlyAssessmentSummaryService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxMonthlyAssessmentSummaryMapper zjXmJxMonthlyAssessmentSummaryMapper;
	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreMapper zjXmJxAssessmentUserScoreMapper;
	@Autowired(required = true)
	private ZjXmJxWeightManagementMapper zjXmJxWeightManagementMapper;
	@Autowired(required = true)
	private ZjXmJxMonthlySummaryFlowMapper zjXmJxMonthlySummaryFlowMapper;
	@Autowired(required = true)
	private BaseCodeService baseCodeService;

	@Override
	public ResponseEntity getZjXmJxMonthlyAssessmentSummaryListByCondition(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		if (zjXmJxMonthlyAssessmentSummary == null) {
			zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxMonthlyAssessmentSummary.getPage(), zjXmJxMonthlyAssessmentSummary.getLimit());
		// 获取数据
		List<ZjXmJxMonthlyAssessmentSummary> zjXmJxMonthlyAssessmentSummaryList = zjXmJxMonthlyAssessmentSummaryMapper
				.selectByZjXmJxMonthlyAssessmentSummaryList(zjXmJxMonthlyAssessmentSummary);
		// 得到分页信息
		PageInfo<ZjXmJxMonthlyAssessmentSummary> p = new PageInfo<>(zjXmJxMonthlyAssessmentSummaryList);

		return repEntity.okList(zjXmJxMonthlyAssessmentSummaryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxMonthlyAssessmentSummaryDetails(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		if (zjXmJxMonthlyAssessmentSummary == null) {
			zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
		}
		// 获取数据
		ZjXmJxMonthlyAssessmentSummary dbZjXmJxMonthlyAssessmentSummary = zjXmJxMonthlyAssessmentSummaryMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessmentSummary.getSummaryId());
		// 数据存在
		if (dbZjXmJxMonthlyAssessmentSummary != null) {
			return repEntity.ok(dbZjXmJxMonthlyAssessmentSummary);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxMonthlyAssessmentSummary(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxMonthlyAssessmentSummary.setSummaryId(UuidUtil.generate());
		zjXmJxMonthlyAssessmentSummary.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxMonthlyAssessmentSummaryMapper.insert(zjXmJxMonthlyAssessmentSummary);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxMonthlyAssessmentSummary);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxMonthlyAssessmentSummary(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxMonthlyAssessmentSummary dbzjXmJxMonthlyAssessmentSummary = zjXmJxMonthlyAssessmentSummaryMapper
				.selectByPrimaryKey(zjXmJxMonthlyAssessmentSummary.getSummaryId());
		if (dbzjXmJxMonthlyAssessmentSummary != null
				&& StrUtil.isNotEmpty(dbzjXmJxMonthlyAssessmentSummary.getSummaryId())) {
			// 项目id
			dbzjXmJxMonthlyAssessmentSummary.setProjectId(zjXmJxMonthlyAssessmentSummary.getProjectId());
			// 项目名称
			dbzjXmJxMonthlyAssessmentSummary.setProjectName(zjXmJxMonthlyAssessmentSummary.getProjectName());
			// 部门id
			dbzjXmJxMonthlyAssessmentSummary.setDeptId(zjXmJxMonthlyAssessmentSummary.getDeptId());
			// 部门
			dbzjXmJxMonthlyAssessmentSummary.setDeptName(zjXmJxMonthlyAssessmentSummary.getDeptName());
			// userKey
			dbzjXmJxMonthlyAssessmentSummary.setUserKey(zjXmJxMonthlyAssessmentSummary.getUserKey());
			// 姓名
			dbzjXmJxMonthlyAssessmentSummary.setRealName(zjXmJxMonthlyAssessmentSummary.getRealName());
			// 身份证号码
			dbzjXmJxMonthlyAssessmentSummary.setIdNumber(zjXmJxMonthlyAssessmentSummary.getIdNumber());
			// 岗位
			dbzjXmJxMonthlyAssessmentSummary.setPosition(zjXmJxMonthlyAssessmentSummary.getPosition());
			// 岗位类别(排名)
			dbzjXmJxMonthlyAssessmentSummary.setRankPositionType(zjXmJxMonthlyAssessmentSummary.getRankPositionType());
			// 岗位类别(末位人员)
			dbzjXmJxMonthlyAssessmentSummary.setLastPositionType(zjXmJxMonthlyAssessmentSummary.getLastPositionType());
			// 人员类别
			dbzjXmJxMonthlyAssessmentSummary.setUserType(zjXmJxMonthlyAssessmentSummary.getUserType());
			// 项目月度考核主键
			dbzjXmJxMonthlyAssessmentSummary.setAssessmentId(zjXmJxMonthlyAssessmentSummary.getAssessmentId());
			// 考核月份
			dbzjXmJxMonthlyAssessmentSummary.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
			// 任务业绩考核
			dbzjXmJxMonthlyAssessmentSummary.setTaskScore(zjXmJxMonthlyAssessmentSummary.getTaskScore());
			// 周边业绩考核
			dbzjXmJxMonthlyAssessmentSummary.setPeripheryScore(zjXmJxMonthlyAssessmentSummary.getPeripheryScore());
			// 项目正职评价
			dbzjXmJxMonthlyAssessmentSummary.setPrincipalScore(zjXmJxMonthlyAssessmentSummary.getPrincipalScore());
			// 综合管理评价扣分
			dbzjXmJxMonthlyAssessmentSummary.setCompositeScore(zjXmJxMonthlyAssessmentSummary.getCompositeScore());
			// 系统自动扣分
			dbzjXmJxMonthlyAssessmentSummary.setSystemScore(zjXmJxMonthlyAssessmentSummary.getSystemScore());
			// 月度考核得分
			dbzjXmJxMonthlyAssessmentSummary.setTotalScore(zjXmJxMonthlyAssessmentSummary.getTotalScore());
			// 是否为本月末位人员
			dbzjXmJxMonthlyAssessmentSummary
					.setMonthlyLastPerson(zjXmJxMonthlyAssessmentSummary.getMonthlyLastPerson());
			// 组内排序
			dbzjXmJxMonthlyAssessmentSummary.setGroupSort(zjXmJxMonthlyAssessmentSummary.getGroupSort());
			// 汇总表类型
			dbzjXmJxMonthlyAssessmentSummary.setSummaryType(zjXmJxMonthlyAssessmentSummary.getSummaryType());
			// 备注
			dbzjXmJxMonthlyAssessmentSummary.setOpinionField1(zjXmJxMonthlyAssessmentSummary.getOpinionField1());
			// 备注
			dbzjXmJxMonthlyAssessmentSummary.setOpinionField2(zjXmJxMonthlyAssessmentSummary.getOpinionField2());
			// 备注
			dbzjXmJxMonthlyAssessmentSummary.setOpinionField3(zjXmJxMonthlyAssessmentSummary.getOpinionField3());
			// 备注
			dbzjXmJxMonthlyAssessmentSummary.setOpinionField4(zjXmJxMonthlyAssessmentSummary.getOpinionField4());
			// 备注
			dbzjXmJxMonthlyAssessmentSummary.setOpinionField5(zjXmJxMonthlyAssessmentSummary.getOpinionField5());
			// 流程id
			dbzjXmJxMonthlyAssessmentSummary.setApih5FlowId(zjXmJxMonthlyAssessmentSummary.getApih5FlowId());
			// work_id
			dbzjXmJxMonthlyAssessmentSummary.setWorkId(zjXmJxMonthlyAssessmentSummary.getWorkId());
			// 工序审核状态
			dbzjXmJxMonthlyAssessmentSummary.setApih5FlowStatus(zjXmJxMonthlyAssessmentSummary.getApih5FlowStatus());
			// 流程状态
			dbzjXmJxMonthlyAssessmentSummary
					.setApih5FlowNodeStatus(zjXmJxMonthlyAssessmentSummary.getApih5FlowNodeStatus());
			// 备注
			dbzjXmJxMonthlyAssessmentSummary.setRemarks(zjXmJxMonthlyAssessmentSummary.getRemarks());
			// 排序
			dbzjXmJxMonthlyAssessmentSummary.setSort(zjXmJxMonthlyAssessmentSummary.getSort());
			// 共通
			dbzjXmJxMonthlyAssessmentSummary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlyAssessmentSummaryMapper.updateByPrimaryKey(dbzjXmJxMonthlyAssessmentSummary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxMonthlyAssessmentSummary);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxMonthlyAssessmentSummary(
			List<ZjXmJxMonthlyAssessmentSummary> zjXmJxMonthlyAssessmentSummaryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxMonthlyAssessmentSummaryList != null && zjXmJxMonthlyAssessmentSummaryList.size() > 0) {
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
			zjXmJxMonthlyAssessmentSummary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxMonthlyAssessmentSummaryMapper.batchDeleteUpdateZjXmJxMonthlyAssessmentSummary(
					zjXmJxMonthlyAssessmentSummaryList, zjXmJxMonthlyAssessmentSummary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxMonthlyAssessmentSummaryList);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity timingToCountZjXmJxMonthlyAssessmentSummary(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) throws Exception {
		// 一、获取基础数据
		// 核算上个月各个项目人员当前月份的总分(行转列)
		if (zjXmJxMonthlyAssessmentSummary == null || zjXmJxMonthlyAssessmentSummary.getYearMonth() == null) {
			zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
			zjXmJxMonthlyAssessmentSummary.setYearMonth(DateUtil.lastMonth());
		}
		ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		zjXmJxAssessmentUserScore.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
		List<Map<String, Object>> list = zjXmJxAssessmentUserScoreMapper
				.countZjXmJxMonthlyAssessmentSummaryByCondition(zjXmJxAssessmentUserScore);
		if (list.size() > 0) {
			List<ZjXmJxMonthlyAssessmentSummary> insertList = Lists.newArrayList();
			for (Map<String, Object> userMap : list) {
				ZjXmJxMonthlyAssessmentSummary insertData = new ZjXmJxMonthlyAssessmentSummary();
				insertData.setSummaryId(UuidUtil.generate());
				insertData.setProjectId(String.valueOf(userMap.get("projectId")));
				insertData.setProjectName(String.valueOf(userMap.get("projectName")));
				insertData.setDeptId(String.valueOf(userMap.get("deptId")));
				insertData.setDeptName(String.valueOf(userMap.get("deptName")));
				insertData.setUserKey(String.valueOf(userMap.get("userKey")));
				insertData.setRealName(String.valueOf(userMap.get("realName")));
				insertData.setIdNumber(String.valueOf(userMap.get("idNumber")));
				insertData.setPosition(String.valueOf(userMap.get("position")));
				insertData.setRankPositionType(String.valueOf(userMap.get("rankPositionType")));
				insertData.setLastPositionType(String.valueOf(userMap.get("lastPositionType")));
				insertData.setUserType(String.valueOf(userMap.get("userType")));
				insertData.setAssessmentId(String.valueOf(userMap.get("assessmentId")));
				insertData.setYearMonth(DateUtil.parseDateTime(String.valueOf(userMap.get("yearMonth"))));
				insertData.setTaskScore(new BigDecimal(String.valueOf(userMap.get("taskScore"))));
				insertData.setPeripheryScore(new BigDecimal(String.valueOf(userMap.get("peripheryScore"))));
				insertData.setPrincipalScore(new BigDecimal(String.valueOf(userMap.get("principalScore"))));
				insertData.setCompositeScore(new BigDecimal(String.valueOf(userMap.get("compositeScore"))));
				insertData.setSystemScore(new BigDecimal(String.valueOf(userMap.get("systemScore"))));
				insertData.setTotalScore(BigDecimal.ZERO);
				insertData.setMonthlyLastPerson("");
				insertData.setGroupSort(0);
				// 基础数据
				insertData.setSummaryType("0");
				insertData.setRemarks("");
				insertData.setSort(0);
				insertData.setCreateUserInfo("定时任务", "定时任务");
				// 核算总分
				insertData.setCompositeScore(CalcUtils.calcSubtract(BigDecimal.ZERO, insertData.getCompositeScore()));
				insertData.setSystemScore(CalcUtils.calcSubtract(BigDecimal.ZERO, insertData.getSystemScore()));
				// 获取当前年月的总权重
				ZjXmJxWeightManagement zjXmJxWeightManagement = new ZjXmJxWeightManagement();
				zjXmJxWeightManagement.setYearMonth(insertData.getYearMonth());
				List<ZjXmJxWeightManagement> weightlist = zjXmJxWeightManagementMapper
						.selectByZjXmJxWeightManagementList(zjXmJxWeightManagement);
				int taskWeight = new Integer(50);
				int peripheryWeight = new Integer(20);
				int principalWeight = new Integer(30);
				if (weightlist.size() == 1) {
					taskWeight = weightlist.get(0).getTaskWeight();
					peripheryWeight = weightlist.get(0).getPeripheryWeight();
					principalWeight = weightlist.get(0).getPrincipalWeight();
				}
				BigDecimal product1 = CalcUtils.calcMultiply(insertData.getTaskScore(), new BigDecimal(taskWeight));
				BigDecimal product2 = CalcUtils.calcMultiply(insertData.getPeripheryScore(),
						new BigDecimal(peripheryWeight));
				BigDecimal product3 = CalcUtils.calcMultiply(insertData.getPrincipalScore(),
						new BigDecimal(principalWeight));
				BigDecimal sum = CalcUtils.calcAdd(CalcUtils.calcAdd(product1, product2), product3);
				BigDecimal totalScore = CalcUtils.calcAdd(CalcUtils
						.calcAdd(CalcUtils.calcDivide(sum, new BigDecimal(100), 2), insertData.getCompositeScore()),
						insertData.getSystemScore());
				// 总分
				insertData.setTotalScore(totalScore);
				insertList.add(insertData);
			}
			if (insertList.size() > 0) {
				// 根据月份先删后增
				ZjXmJxMonthlyAssessmentSummary delete = new ZjXmJxMonthlyAssessmentSummary();
				delete.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				delete.setSummaryType("0");
				zjXmJxMonthlyAssessmentSummaryMapper.deleteZjXmJxMonthlyAssessmentSummaryByCondition(delete);
				int flag = zjXmJxMonthlyAssessmentSummaryMapper.batchInsertZjXmJxMonthlyAssessmentSummary(insertList);
			}
		}
		// 根据本月基础数据获取,本月项目集合
		ZjXmJxMonthlyAssessmentSummary search = new ZjXmJxMonthlyAssessmentSummary();
		search.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
		List<ZjXmJxMonthlyAssessmentSummary> projectList = zjXmJxMonthlyAssessmentSummaryMapper
				.getZjXmJxMonthlyAssessmentSummaryProjectByMonth(search);
		if (projectList.size() > 0) {
			// 二、根据基础数据处理排名数据
			// 根据基础数据获取所有项目的集合,然后根据月份和每个项目进行排名
			List<ZjXmJxMonthlySummaryFlow> insertFlowList = Lists.newArrayList();
			List<ZjXmJxMonthlyAssessmentSummary> insertList = Lists.newArrayList();
			for (ZjXmJxMonthlyAssessmentSummary project : projectList) {
				// 根据每个月份每个项目新增一条审核表数据
				ZjXmJxMonthlySummaryFlow insertFlow = new ZjXmJxMonthlySummaryFlow();
				insertFlow.setMonthlyFlowId(UuidUtil.generate());
				insertFlow.setAssessmentId(project.getAssessmentId());
				insertFlow.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				insertFlow.setProjectId(project.getProjectId());
				insertFlow.setProjectName(project.getProjectName());
				insertFlow.setDeptId("不确定");
				insertFlow.setDeptName("不确定");
				insertFlow.setFlowType("1");
				insertFlow.setCreateUserInfo("定时任务", "定时任务");
				insertFlowList.add(insertFlow);
				ZjXmJxMonthlyAssessmentSummary search2 = new ZjXmJxMonthlyAssessmentSummary();
				search2.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				search2.setProjectId(project.getProjectId());
				List<ZjXmJxMonthlyAssessmentSummary> rankList = zjXmJxMonthlyAssessmentSummaryMapper
						.getZjXmJxMonthlyAssessmentSummaryListByRank(search2);
				if (rankList.size() > 0) {
					for (ZjXmJxMonthlyAssessmentSummary insertData : rankList) {
						insertData.setSummaryId(UuidUtil.generate());
						insertData.setMonthlyLastPerson("");
						// 排名数据
						insertData.setSummaryType("1");
						insertData.setMonthlyFlowId(insertFlow.getMonthlyFlowId());
						insertData.setCreateUserInfo("定时任务", "定时任务");
						insertList.add(insertData);
					}
				}
			}
			if (insertFlowList.size() > 0) {
				// 根据月份项目先删后增
				ZjXmJxMonthlySummaryFlow delete = new ZjXmJxMonthlySummaryFlow();
				delete.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				delete.setFlowType("1");
				zjXmJxMonthlySummaryFlowMapper.deleteZjXmJxMonthlySummaryFlowByCondition(delete);
				int flag = zjXmJxMonthlySummaryFlowMapper.batchInsertZjXmJxMonthlySummaryFlow(insertFlowList);
			}
			if (insertList.size() > 0) {
				// 根据月份项目先删后增
				ZjXmJxMonthlyAssessmentSummary delete = new ZjXmJxMonthlyAssessmentSummary();
				delete.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				delete.setSummaryType("1");
				zjXmJxMonthlyAssessmentSummaryMapper.deleteZjXmJxMonthlyAssessmentSummaryByCondition(delete);
				int flag = zjXmJxMonthlyAssessmentSummaryMapper.batchInsertZjXmJxMonthlyAssessmentSummary(insertList);
			}
			// 三、根据基础数据处理末位人员数据
			List<ZjXmJxMonthlySummaryFlow> insertFlowList2 = Lists.newArrayList();
			List<ZjXmJxMonthlyAssessmentSummary> insertList2 = Lists.newArrayList();
			for (ZjXmJxMonthlyAssessmentSummary project : projectList) {
				// 根据每个月份每个项目新增一条审核表数据
				ZjXmJxMonthlySummaryFlow insertFlow = new ZjXmJxMonthlySummaryFlow();
				insertFlow.setMonthlyFlowId(UuidUtil.generate());
				insertFlow.setAssessmentId(project.getAssessmentId());
				insertFlow.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				insertFlow.setProjectId(project.getProjectId());
				insertFlow.setProjectName(project.getProjectName());
				insertFlow.setDeptId("不确定");
				insertFlow.setDeptName("不确定");
				insertFlow.setFlowType("2");
				insertFlow.setCreateUserInfo("定时任务", "定时任务");
				insertFlowList2.add(insertFlow);
				ZjXmJxMonthlyAssessmentSummary search2 = new ZjXmJxMonthlyAssessmentSummary();
				search2.setProjectId(project.getProjectId());
				search2.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				List<ZjXmJxMonthlyAssessmentSummary> lastList = zjXmJxMonthlyAssessmentSummaryMapper
						.getZjXmJxMonthlyAssessmentSummaryListByLast(search2);
				// // ++++++++++++++++++++++++++find末尾人start++++++++++++++++++++++++
				// 根据局聘公司聘和项目聘转换成两个map
				Map<String, List<ZjXmJxMonthlyAssessmentSummary>> jpgspMap = new HashMap<String, List<ZjXmJxMonthlyAssessmentSummary>>();
				List<ZjXmJxMonthlyAssessmentSummary> xmpList = Lists.newArrayList();
				// 末位人员集合
				List<ZjXmJxMonthlyAssessmentSummary> lastPersonList = Lists.newArrayList();
				if (lastList.size() > 0) {
					for (ZjXmJxMonthlyAssessmentSummary dbData : lastList) {
						// 根据业务表格处理末位人员1:局聘 2:公司聘 3:项目聘
						if (StrUtil.equals("1", dbData.getUserType()) || StrUtil.equals("2", dbData.getUserType())) {
							if (jpgspMap.containsKey(dbData.getLastPositionType())) {
								jpgspMap.get(dbData.getLastPositionType()).add(dbData);
							} else {
								List<ZjXmJxMonthlyAssessmentSummary> tempList = Lists.newArrayList();
								tempList.add(dbData);
								jpgspMap.put(dbData.getLastPositionType(), tempList);
							}
						} else if (StrUtil.equals("3", dbData.getUserType())) {
							xmpList.add(dbData);
						}
					}
					// 遍历局聘公司聘map获取末位人员
					if (jpgspMap.size() > 0) {
						for (Map.Entry<String, List<ZjXmJxMonthlyAssessmentSummary>> jpgspEntry : jpgspMap.entrySet()) {
							// 1:局聘及公司聘项目副职
							if (StrUtil.equals("1", jpgspEntry.getKey())) {
								// 配置>3 取1名末位人
								if (jpgspEntry.getValue().size() > 3) {
									lastPersonList.add(jpgspEntry.getValue().get(jpgspEntry.getValue().size() - 1));
								}
							} // 2: 局聘及公司聘副总师及部门负责人
							else if (StrUtil.equals("2", jpgspEntry.getKey())) {
								// 配置>3 取1名末位人
								if (jpgspEntry.getValue().size() > 3) {
									lastPersonList.add(jpgspEntry.getValue().get(jpgspEntry.getValue().size() - 1));
								}
							} // 3: 局聘及公司聘副部长及其他人员
							else if (StrUtil.equals("3", jpgspEntry.getKey())) {
								// 6 < 配置< 10 取1名末位人
								if (jpgspEntry.getValue().size() > 6 && jpgspEntry.getValue().size() <= 10) {
									lastPersonList.add(jpgspEntry.getValue().get(jpgspEntry.getValue().size() - 1));
								}
								// 配置 > 10 取2名末位人
								else if (jpgspEntry.getValue().size() > 10) {
									lastPersonList.add(jpgspEntry.getValue().get(jpgspEntry.getValue().size() - 1));
									lastPersonList.add(jpgspEntry.getValue().get(jpgspEntry.getValue().size() - 2));
								}
							}
						}
					}
					// 遍历项目聘公司聘map获取末位人员
					// 4: 所有项目聘人员
					if (xmpList.size() > 0) {
						// 6 < 配置< 10 取1名末位人
						if (xmpList.size() > 6 && xmpList.size() <= 10) {
							lastPersonList.add(xmpList.get(xmpList.size() - 1));
						}
						// 配置 > 10 取2名末位人
						else if (xmpList.size() > 10) {
							lastPersonList.add(xmpList.get(xmpList.size() - 1));
							lastPersonList.add(xmpList.get(xmpList.size() - 2));
						}
					}
					// ++++++++++++++++++++++++++find末尾人end++++++++++++++++++++++++
					for (ZjXmJxMonthlyAssessmentSummary insertData : lastList) {
						insertData.setSummaryId(UuidUtil.generate());
						insertData.setSummaryType("2");
						// 根据userKey匹配末位人员
						insertData.setMonthlyLastPerson("0");
						for (ZjXmJxMonthlyAssessmentSummary lastPerson : lastPersonList) {
							if (StrUtil.equals(insertData.getUserKey(), lastPerson.getUserKey())) {
								insertData.setMonthlyLastPerson("1");
								break;
							}
						}
						// 排名数据
						insertData.setMonthlyFlowId(insertFlow.getMonthlyFlowId());
						insertData.setCreateUserInfo("定时任务", "定时任务");
						insertList2.add(insertData);
					}
				}
			}
			if (insertFlowList2.size() > 0) {
				// 根据月份项目先删后增
				ZjXmJxMonthlySummaryFlow delete = new ZjXmJxMonthlySummaryFlow();
				delete.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				delete.setFlowType("2");
				zjXmJxMonthlySummaryFlowMapper.deleteZjXmJxMonthlySummaryFlowByCondition(delete);
				int flag = zjXmJxMonthlySummaryFlowMapper.batchInsertZjXmJxMonthlySummaryFlow(insertFlowList2);
			}
			if (insertList2.size() > 0) {
				// 根据月份先删后增
				ZjXmJxMonthlyAssessmentSummary delete = new ZjXmJxMonthlyAssessmentSummary();
				delete.setYearMonth(zjXmJxMonthlyAssessmentSummary.getYearMonth());
				delete.setSummaryType("2");
				zjXmJxMonthlyAssessmentSummaryMapper.deleteZjXmJxMonthlyAssessmentSummaryByCondition(delete);
				int flag = zjXmJxMonthlyAssessmentSummaryMapper.batchInsertZjXmJxMonthlyAssessmentSummary(insertList2);

			}
		}
		return repEntity.ok("定时任务执行成功!");
	}

	@Override
	public ResponseEntity exportRankZjXmJxMonthlyAssessmentSummary2(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxMonthlyAssessmentSummary == null) {
			zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
		}
		// 获取数据 项目id 月份 类型
		List<ZjXmJxMonthlyAssessmentSummary> dataList = zjXmJxMonthlyAssessmentSummaryMapper
				.selectByZjXmJxMonthlyAssessmentSummaryList(zjXmJxMonthlyAssessmentSummary);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("序号", "项目名称", "姓名", "组内排序", "身份证号码", "部门", "岗位", "岗位类别", "人员类别", "考核月份",
				"任务业绩考核", "周边业绩考核", "项目正职评价", "综合管理评价扣分", "月度考核得分", "备注");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZjXmJxMonthlyAssessmentSummary dbData : dataList) {
				// 工程类型以及班组类型
				String positionName = "";
				if (StrUtil.isNotEmpty(dbData.getPosition())) {
					positionName = baseCodeService.getBaseCodeItemName("jobType", dbData.getPosition());
				}
				String rankPositionTypeName = "";
				if (StrUtil.equals("1", dbData.getRankPositionType())) {
					rankPositionTypeName = "项目副职";
				} else if (StrUtil.equals("2", dbData.getRankPositionType())) {
					rankPositionTypeName = "项目副总师";
				} else if (StrUtil.equals("3", dbData.getRankPositionType())) {
					rankPositionTypeName = "部门负责人";
				} else if (StrUtil.equals("4", dbData.getRankPositionType())) {
					rankPositionTypeName = "部门副部长及工区主任";
				} else if (StrUtil.equals("5", dbData.getRankPositionType())) {
					rankPositionTypeName = "普通职工";
				}
				String userTypeName = "";
				if (StrUtil.equals("1", dbData.getUserType())) {
					userTypeName = "局聘";
				} else if (StrUtil.equals("2", dbData.getUserType())) {
					userTypeName = "公司聘";
				} else if (StrUtil.equals("3", dbData.getUserType())) {
					userTypeName = "项目聘";
				}
				String yearMonth = DateUtil.format(dbData.getYearMonth(), "yyyy-MM");
				rowsList.add(CollUtil.newArrayList(dbData.getSort(), dbData.getProjectName(), dbData.getRealName(),
						dbData.getGroupSort(), dbData.getIdNumber(), dbData.getDeptName(), positionName,
						rankPositionTypeName, userTypeName, yearMonth, dbData.getTaskScore(),
						dbData.getPeripheryScore(), dbData.getPrincipalScore(), dbData.getCompositeScore(),
						dbData.getTotalScore(), dbData.getRemarks()));
			}
			// 报表名称
			String dateStr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
			String fileName = dateStr + "月度考核汇总排名.xlsx";
			// List<List<?>> rows = CollUtil.newArrayList(rowsList);
			BigExcelWriter writer = null;
			try {
				String path = HttpUtil.getUploadPath("temp") + fileName;
				try {
					FileUtil.del(path);
				} catch (Exception e) {
				}
				writer = ExcelUtil.getBigWriter(path);
				// 一次性写出内容，使用默认样式
				writer.write(rowsList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭writer，释放内存
				if (writer != null) {
					writer.close();
				}
			}
			String url = HttpUtil.getUploadPathWeb(request, "temp") + fileName;
			return repEntity.ok(url);
		} else {
			return repEntity.ok("无数据。");
		}
	}

	@Override
	public ResponseEntity exportLastZjXmJxMonthlyAssessmentSummary2(
			ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxMonthlyAssessmentSummary == null) {
			zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
		}
		// 获取数据 项目id 月份 类型
		List<ZjXmJxMonthlyAssessmentSummary> dataList = zjXmJxMonthlyAssessmentSummaryMapper
				.selectByZjXmJxMonthlyAssessmentSummaryList(zjXmJxMonthlyAssessmentSummary);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("序号", "项目名称", "姓名", "组内排序", "身份证号码", "部门", "岗位", "岗位类别", "人员类别", "考核月份",
				"任务业绩考核", "周边业绩考核", "项目正职评价", "综合管理评价扣分", "月度考核得分", "是否为本月末位人员", "备注");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZjXmJxMonthlyAssessmentSummary dbData : dataList) {
				// 工程类型以及班组类型
				String positionName = "";
				if (StrUtil.isNotEmpty(dbData.getPosition())) {
					positionName = baseCodeService.getBaseCodeItemName("jobType", dbData.getPosition());
				}
				String lastPositionTypeName = "";
				if (StrUtil.equals("1", dbData.getLastPositionType())) {
					lastPositionTypeName = "局聘及公司聘项目副职";
				} else if (StrUtil.equals("2", dbData.getLastPositionType())) {
					lastPositionTypeName = "局聘及公司聘副总师及部门负责人";
				} else if (StrUtil.equals("3", dbData.getLastPositionType())) {
					lastPositionTypeName = "局聘及公司聘副部长及其他人员";
				} else if (StrUtil.equals("4", dbData.getLastPositionType())) {
					lastPositionTypeName = "所有项目聘人员";
				}
				String userTypeName = "";
				if (StrUtil.equals("1", dbData.getUserType())) {
					userTypeName = "局聘";
				} else if (StrUtil.equals("2", dbData.getUserType())) {
					userTypeName = "公司聘";
				} else if (StrUtil.equals("3", dbData.getUserType())) {
					userTypeName = "项目聘";
				}
				String yearMonth = DateUtil.format(dbData.getYearMonth(), "yyyy-MM");
				String monthlyLastPerson = StrUtil.equals("1", dbData.getMonthlyLastPerson()) ? "是" : "否";
				rowsList.add(CollUtil.newArrayList(dbData.getSort(), dbData.getProjectName(), dbData.getRealName(),
						dbData.getGroupSort(), dbData.getIdNumber(), dbData.getDeptName(), positionName,
						lastPositionTypeName, userTypeName, yearMonth, dbData.getTaskScore(),
						dbData.getPeripheryScore(), dbData.getPrincipalScore(), dbData.getCompositeScore(),
						dbData.getTotalScore(), monthlyLastPerson, dbData.getRemarks()));
			}
			// 报表名称
			String dateStr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
			String fileName = dateStr + "月度考核汇总末位人员.xlsx";
			// List<List<?>> rows = CollUtil.newArrayList(rowsList);
			BigExcelWriter writer = null;
			try {
				String path = HttpUtil.getUploadPath("temp") + fileName;
				try {
					FileUtil.del(path);
				} catch (Exception e) {
				}
				writer = ExcelUtil.getBigWriter(path);
				// 一次性写出内容，使用默认样式
				writer.write(rowsList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭writer，释放内存
				if (writer != null) {
					writer.close();
				}
			}
			String url = HttpUtil.getUploadPathWeb(request, "temp") + fileName;
			return repEntity.ok(url);
		} else {
			return repEntity.ok("无数据。");
		}
	}

	@Override
	public void exportLastZjXmJxMonthlyAssessmentSummary(ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary,
			HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxMonthlyAssessmentSummary == null) {
			zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
		}
		// 获取数据 项目id 月份 类型
		List<ZjXmJxMonthlyAssessmentSummary> dataList = zjXmJxMonthlyAssessmentSummaryMapper
				.selectByZjXmJxMonthlyAssessmentSummaryList(zjXmJxMonthlyAssessmentSummary);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("序号", "项目名称", "姓名", "组内排序", "身份证号码", "部门", "岗位", "岗位类别", "人员类别", "考核月份",
				"任务业绩考核", "周边业绩考核", "项目正职评价", "综合管理评价扣分", "月度考核得分", "是否为本月末位人员", "备注");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZjXmJxMonthlyAssessmentSummary dbData : dataList) {
				// 工程类型以及班组类型
				String positionName = "";
				if (StrUtil.isNotEmpty(dbData.getPosition())) {
					positionName = baseCodeService.getBaseCodeItemName("jobType", dbData.getPosition());
				}
				String lastPositionTypeName = "";
				if (StrUtil.equals("1", dbData.getLastPositionType())) {
					lastPositionTypeName = "局聘及公司聘项目副职";
				} else if (StrUtil.equals("2", dbData.getLastPositionType())) {
					lastPositionTypeName = "局聘及公司聘副总师及部门负责人";
				} else if (StrUtil.equals("3", dbData.getLastPositionType())) {
					lastPositionTypeName = "局聘及公司聘副部长及其他人员";
				} else if (StrUtil.equals("4", dbData.getLastPositionType())) {
					lastPositionTypeName = "所有项目聘人员";
				}
				String userTypeName = "";
				if (StrUtil.equals("1", dbData.getUserType())) {
					userTypeName = "局聘";
				} else if (StrUtil.equals("2", dbData.getUserType())) {
					userTypeName = "公司聘";
				} else if (StrUtil.equals("3", dbData.getUserType())) {
					userTypeName = "项目聘";
				}
				String yearMonth = DateUtil.format(dbData.getYearMonth(), "yyyy-MM");
				String monthlyLastPerson = StrUtil.equals("1", dbData.getMonthlyLastPerson()) ? "是" : "否";
				rowsList.add(CollUtil.newArrayList(dbData.getSort(), dbData.getProjectName(), dbData.getRealName(),
						dbData.getGroupSort(), dbData.getIdNumber(), dbData.getDeptName(), positionName,
						lastPositionTypeName, userTypeName, yearMonth, dbData.getTaskScore(),
						dbData.getPeripheryScore(), dbData.getPrincipalScore(), dbData.getCompositeScore(),
						dbData.getTotalScore(), monthlyLastPerson, dbData.getRemarks()));
			}
			// List<List<?>> rows = CollUtil.newArrayList(rowsList);
			// 通过工具类创建writer，创建xlsx格式
			ExcelWriter writer = ExcelUtil.getWriter(true);
			// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
			// response为HttpServletResponse对象
			// 设置response下载弹窗
			// response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			// test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
			// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
			// out为OutputStream，需要写出到的目标流
			ServletOutputStream out = null;
			try {
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ new String("项目员工月度考核汇总末位人员".getBytes("utf-8"), "iso-8859-1") + "\"");
				out = response.getOutputStream();
				// 设置标题
				writer.merge(row.size() - 1, "项目员工月度考核汇总末位人员表");
				// 一次性写出内容，使用默认样式，强制输出标题
				writer.write(rowsList, true);
				writer.flush(out, true);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭writer，释放内存
				if (writer != null) {
					writer.close();
				}
				// 此处记得关闭输出Servlet流
				if (out != null) {
					IoUtil.close(out);
				}
			}
		}
	}

	@Override
	public void exportRankZjXmJxMonthlyAssessmentSummary(ZjXmJxMonthlyAssessmentSummary zjXmJxMonthlyAssessmentSummary,
			HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxMonthlyAssessmentSummary == null) {
			zjXmJxMonthlyAssessmentSummary = new ZjXmJxMonthlyAssessmentSummary();
		}
		// 获取数据 项目id 月份 类型
		List<ZjXmJxMonthlyAssessmentSummary> dataList = zjXmJxMonthlyAssessmentSummaryMapper
				.selectByZjXmJxMonthlyAssessmentSummaryList(zjXmJxMonthlyAssessmentSummary);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("序号", "项目名称", "姓名", "组内排序", "身份证号码", "部门", "岗位", "岗位类别", "人员类别", "考核月份",
				"任务业绩考核", "周边业绩考核", "项目正职评价", "综合管理评价扣分", "月度考核得分", "备注");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZjXmJxMonthlyAssessmentSummary dbData : dataList) {
				// 工程类型以及班组类型
				String positionName = "";
				if (StrUtil.isNotEmpty(dbData.getPosition())) {
					positionName = baseCodeService.getBaseCodeItemName("jobType", dbData.getPosition());
				}
				String rankPositionTypeName = "";
				if (StrUtil.equals("1", dbData.getRankPositionType())) {
					rankPositionTypeName = "项目副职";
				} else if (StrUtil.equals("2", dbData.getRankPositionType())) {
					rankPositionTypeName = "项目副总师";
				} else if (StrUtil.equals("3", dbData.getRankPositionType())) {
					rankPositionTypeName = "部门负责人";
				} else if (StrUtil.equals("4", dbData.getRankPositionType())) {
					rankPositionTypeName = "部门副部长及工区主任";
				} else if (StrUtil.equals("5", dbData.getRankPositionType())) {
					rankPositionTypeName = "普通职工";
				}
				String userTypeName = "";
				if (StrUtil.equals("1", dbData.getUserType())) {
					userTypeName = "局聘";
				} else if (StrUtil.equals("2", dbData.getUserType())) {
					userTypeName = "公司聘";
				} else if (StrUtil.equals("3", dbData.getUserType())) {
					userTypeName = "项目聘";
				}
				String yearMonth = DateUtil.format(dbData.getYearMonth(), "yyyy-MM");
				rowsList.add(CollUtil.newArrayList(dbData.getSort(), dbData.getProjectName(), dbData.getRealName(),
						dbData.getGroupSort(), dbData.getIdNumber(), dbData.getDeptName(), positionName,
						rankPositionTypeName, userTypeName, yearMonth, dbData.getTaskScore(),
						dbData.getPeripheryScore(), dbData.getPrincipalScore(), dbData.getCompositeScore(),
						dbData.getTotalScore(), dbData.getRemarks()));
			}
			// List<List<?>> rows = CollUtil.newArrayList(rowsList);
			// 通过工具类创建writer，创建xlsx格式
			ExcelWriter writer = ExcelUtil.getWriter(true);
			// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
			// response为HttpServletResponse对象
			// 设置response下载弹窗
			// response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			// test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
			// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
			// out为OutputStream，需要写出到的目标流
			ServletOutputStream out = null;
			try {
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + new String("项目员工月度考核汇总排名".getBytes("utf-8"), "iso-8859-1") + "\"");
				out = response.getOutputStream();
				// 设置标题
				writer.merge(row.size() - 1, "项目员工月度考核汇总排名表");
				// 一次性写出内容，使用默认样式，强制输出标题
				writer.write(rowsList, true);
				writer.flush(out, true);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭writer，释放内存
				if (writer != null) {
					writer.close();
				}
				// 此处记得关闭输出Servlet流
				if (out != null) {
					IoUtil.close(out);
				}
			}
		}
	}
}