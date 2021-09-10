package com.apih5.service.impl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxAnnualAssessmentSummaryMapper;
import com.apih5.mybatis.dao.ZjXmJxAnnualSummaryFlowMapper;
import com.apih5.mybatis.dao.ZjXmJxMonthlyAssessmentSummaryMapper;
import com.apih5.mybatis.pojo.ZjXmJxAnnualAssessmentSummary;
import com.apih5.mybatis.pojo.ZjXmJxAnnualSummaryFlow;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessmentSummary;
import com.apih5.service.ZjXmJxAnnualAssessmentSummaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjXmJxAnnualAssessmentSummaryService")
public class ZjXmJxAnnualAssessmentSummaryServiceImpl implements ZjXmJxAnnualAssessmentSummaryService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxAnnualAssessmentSummaryMapper zjXmJxAnnualAssessmentSummaryMapper;
	@Autowired(required = true)
	private ZjXmJxMonthlyAssessmentSummaryMapper zjXmJxMonthlyAssessmentSummaryMapper;
	@Autowired(required = true)
	private ZjXmJxAnnualSummaryFlowMapper zjXmJxAnnualSummaryFlowMapper;
	@Autowired(required = true)
	private BaseCodeService baseCodeService;

	@Override
	public ResponseEntity getZjXmJxAnnualAssessmentSummaryListByCondition(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		if (zjXmJxAnnualAssessmentSummary == null) {
			zjXmJxAnnualAssessmentSummary = new ZjXmJxAnnualAssessmentSummary();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxAnnualAssessmentSummary.getPage(), zjXmJxAnnualAssessmentSummary.getLimit());
		// 获取数据
		List<ZjXmJxAnnualAssessmentSummary> zjXmJxAnnualAssessmentSummaryList = zjXmJxAnnualAssessmentSummaryMapper
				.selectByZjXmJxAnnualAssessmentSummaryList(zjXmJxAnnualAssessmentSummary);
		// 得到分页信息
		PageInfo<ZjXmJxAnnualAssessmentSummary> p = new PageInfo<>(zjXmJxAnnualAssessmentSummaryList);

		return repEntity.okList(zjXmJxAnnualAssessmentSummaryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxAnnualAssessmentSummaryDetails(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		if (zjXmJxAnnualAssessmentSummary == null) {
			zjXmJxAnnualAssessmentSummary = new ZjXmJxAnnualAssessmentSummary();
		}
		// 获取数据
		ZjXmJxAnnualAssessmentSummary dbZjXmJxAnnualAssessmentSummary = zjXmJxAnnualAssessmentSummaryMapper
				.selectByPrimaryKey(zjXmJxAnnualAssessmentSummary.getSummaryId());
		// 数据存在
		if (dbZjXmJxAnnualAssessmentSummary != null) {
			return repEntity.ok(dbZjXmJxAnnualAssessmentSummary);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxAnnualAssessmentSummary(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxAnnualAssessmentSummary.setSummaryId(UuidUtil.generate());
		zjXmJxAnnualAssessmentSummary.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxAnnualAssessmentSummaryMapper.insert(zjXmJxAnnualAssessmentSummary);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxAnnualAssessmentSummary);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxAnnualAssessmentSummary(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxAnnualAssessmentSummary dbzjXmJxAnnualAssessmentSummary = zjXmJxAnnualAssessmentSummaryMapper
				.selectByPrimaryKey(zjXmJxAnnualAssessmentSummary.getSummaryId());
		if (dbzjXmJxAnnualAssessmentSummary != null
				&& StrUtil.isNotEmpty(dbzjXmJxAnnualAssessmentSummary.getSummaryId())) {
			// 项目id
			dbzjXmJxAnnualAssessmentSummary.setProjectId(zjXmJxAnnualAssessmentSummary.getProjectId());
			// 项目名称
			dbzjXmJxAnnualAssessmentSummary.setProjectName(zjXmJxAnnualAssessmentSummary.getProjectName());
			// 部门id
			dbzjXmJxAnnualAssessmentSummary.setDeptId(zjXmJxAnnualAssessmentSummary.getDeptId());
			// 部门名称
			dbzjXmJxAnnualAssessmentSummary.setDeptName(zjXmJxAnnualAssessmentSummary.getDeptName());
			// userKey
			dbzjXmJxAnnualAssessmentSummary.setUserKey(zjXmJxAnnualAssessmentSummary.getUserKey());
			// 姓名
			dbzjXmJxAnnualAssessmentSummary.setRealName(zjXmJxAnnualAssessmentSummary.getRealName());
			// 身份证号码
			dbzjXmJxAnnualAssessmentSummary.setIdNumber(zjXmJxAnnualAssessmentSummary.getIdNumber());
			// 岗位
			dbzjXmJxAnnualAssessmentSummary.setPosition(zjXmJxAnnualAssessmentSummary.getPosition());
			// 岗位类别(排名)
			dbzjXmJxAnnualAssessmentSummary.setRankPositionType(zjXmJxAnnualAssessmentSummary.getRankPositionType());
			// 岗位类别(末位人员)
			dbzjXmJxAnnualAssessmentSummary.setLastPositionType(zjXmJxAnnualAssessmentSummary.getLastPositionType());
			// 人员类别
			dbzjXmJxAnnualAssessmentSummary.setUserType(zjXmJxAnnualAssessmentSummary.getUserType());
			// 考核年份
			dbzjXmJxAnnualAssessmentSummary.setYear(zjXmJxAnnualAssessmentSummary.getYear());
			// 1月得分
			dbzjXmJxAnnualAssessmentSummary.setJanuaryScore(zjXmJxAnnualAssessmentSummary.getJanuaryScore());
			// 2月得分
			dbzjXmJxAnnualAssessmentSummary.setFebruaryScore(zjXmJxAnnualAssessmentSummary.getFebruaryScore());
			// 3月得分
			dbzjXmJxAnnualAssessmentSummary.setMarchScore(zjXmJxAnnualAssessmentSummary.getMarchScore());
			// 4月得分
			dbzjXmJxAnnualAssessmentSummary.setAprilScore(zjXmJxAnnualAssessmentSummary.getAprilScore());
			// 5月得分
			dbzjXmJxAnnualAssessmentSummary.setMayScore(zjXmJxAnnualAssessmentSummary.getMayScore());
			// 6月得分
			dbzjXmJxAnnualAssessmentSummary.setJuneScore(zjXmJxAnnualAssessmentSummary.getJuneScore());
			// 7月得分
			dbzjXmJxAnnualAssessmentSummary.setJulyScore(zjXmJxAnnualAssessmentSummary.getJulyScore());
			// 8月得分
			dbzjXmJxAnnualAssessmentSummary.setAugustScore(zjXmJxAnnualAssessmentSummary.getAugustScore());
			// 9月得分
			dbzjXmJxAnnualAssessmentSummary.setSeptemberScore(zjXmJxAnnualAssessmentSummary.getSeptemberScore());
			// 10月得分
			dbzjXmJxAnnualAssessmentSummary.setOctoberScore(zjXmJxAnnualAssessmentSummary.getOctoberScore());
			// 11月得分
			dbzjXmJxAnnualAssessmentSummary.setNovemberScore(zjXmJxAnnualAssessmentSummary.getNovemberScore());
			// 12月得分
			dbzjXmJxAnnualAssessmentSummary.setDecemberScore(zjXmJxAnnualAssessmentSummary.getDecemberScore());
			// 年度平均分
			dbzjXmJxAnnualAssessmentSummary.setAverageScore(zjXmJxAnnualAssessmentSummary.getAverageScore());
			// 列为末位人员的月份
			dbzjXmJxAnnualAssessmentSummary.setLastPersonMonth(zjXmJxAnnualAssessmentSummary.getLastPersonMonth());
			// 组内排序
			dbzjXmJxAnnualAssessmentSummary.setGroupSort(zjXmJxAnnualAssessmentSummary.getGroupSort());
			// 汇总表类型
			dbzjXmJxAnnualAssessmentSummary.setSummaryType(zjXmJxAnnualAssessmentSummary.getSummaryType());
			// 备注
			dbzjXmJxAnnualAssessmentSummary.setOpinionField1(zjXmJxAnnualAssessmentSummary.getOpinionField1());
			// 备注
			dbzjXmJxAnnualAssessmentSummary.setOpinionField2(zjXmJxAnnualAssessmentSummary.getOpinionField2());
			// 备注
			dbzjXmJxAnnualAssessmentSummary.setOpinionField3(zjXmJxAnnualAssessmentSummary.getOpinionField3());
			// 备注
			dbzjXmJxAnnualAssessmentSummary.setOpinionField4(zjXmJxAnnualAssessmentSummary.getOpinionField4());
			// 备注
			dbzjXmJxAnnualAssessmentSummary.setOpinionField5(zjXmJxAnnualAssessmentSummary.getOpinionField5());
			// 流程id
			dbzjXmJxAnnualAssessmentSummary.setApih5FlowId(zjXmJxAnnualAssessmentSummary.getApih5FlowId());
			// work_id
			dbzjXmJxAnnualAssessmentSummary.setWorkId(zjXmJxAnnualAssessmentSummary.getWorkId());
			// 工序审核状态
			dbzjXmJxAnnualAssessmentSummary.setApih5FlowStatus(zjXmJxAnnualAssessmentSummary.getApih5FlowStatus());
			// 流程状态
			dbzjXmJxAnnualAssessmentSummary
					.setApih5FlowNodeStatus(zjXmJxAnnualAssessmentSummary.getApih5FlowNodeStatus());
			// 年度审核表主键
			dbzjXmJxAnnualAssessmentSummary.setAnnualFlowId(zjXmJxAnnualAssessmentSummary.getAnnualFlowId());
			// 备注
			dbzjXmJxAnnualAssessmentSummary.setRemarks(zjXmJxAnnualAssessmentSummary.getRemarks());
			// 排序
			dbzjXmJxAnnualAssessmentSummary.setSort(zjXmJxAnnualAssessmentSummary.getSort());
			// 共通
			dbzjXmJxAnnualAssessmentSummary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxAnnualAssessmentSummaryMapper.updateByPrimaryKey(dbzjXmJxAnnualAssessmentSummary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxAnnualAssessmentSummary);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxAnnualAssessmentSummary(
			List<ZjXmJxAnnualAssessmentSummary> zjXmJxAnnualAssessmentSummaryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxAnnualAssessmentSummaryList != null && zjXmJxAnnualAssessmentSummaryList.size() > 0) {
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary = new ZjXmJxAnnualAssessmentSummary();
			zjXmJxAnnualAssessmentSummary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxAnnualAssessmentSummaryMapper.batchDeleteUpdateZjXmJxAnnualAssessmentSummary(
					zjXmJxAnnualAssessmentSummaryList, zjXmJxAnnualAssessmentSummary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxAnnualAssessmentSummaryList);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity timingToCountZjXmJxAnnualAssessmentSummary(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) throws Exception {
		// 根据月度汇总获取数据(每年每个项目一个统计表)
		if (zjXmJxAnnualAssessmentSummary == null || zjXmJxAnnualAssessmentSummary.getYear() == null) {
			zjXmJxAnnualAssessmentSummary = new ZjXmJxAnnualAssessmentSummary();
			zjXmJxAnnualAssessmentSummary.setYear(DateUtil.date());
		}
		// 根据月度汇总表获取查询年份的项目集合,然后根据年份和每个项目进行排名
		ZjXmJxMonthlyAssessmentSummary search = new ZjXmJxMonthlyAssessmentSummary();
		search.setYearMonth(zjXmJxAnnualAssessmentSummary.getYear());
		List<ZjXmJxMonthlyAssessmentSummary> projectList = zjXmJxMonthlyAssessmentSummaryMapper
				.getZjXmJxMonthlyAssessmentSummaryProjectByYear(search);
		if (projectList.size() > 0) {
			List<ZjXmJxAnnualSummaryFlow> insertFlowList = Lists.newArrayList();
			List<ZjXmJxAnnualAssessmentSummary> insertList = Lists.newArrayList();
			for (ZjXmJxMonthlyAssessmentSummary project : projectList) {
				// 根据每个月份每个项目新增一条审核表数据
				ZjXmJxAnnualSummaryFlow insertFlow = new ZjXmJxAnnualSummaryFlow();
				insertFlow.setAnnualFlowId(UuidUtil.generate());
				insertFlow.setYear(zjXmJxAnnualAssessmentSummary.getYear());
				insertFlow.setProjectId(project.getProjectId());
				insertFlow.setProjectName(project.getProjectName());
				insertFlow.setDeptId("不确定");
				insertFlow.setDeptName("不确定");
				insertFlow.setFlowType("1");
				insertFlow.setCreateUserInfo("定时任务", "定时任务");
				insertFlowList.add(insertFlow);
				ZjXmJxAnnualAssessmentSummary search2 = new ZjXmJxAnnualAssessmentSummary();
				search2.setYear(zjXmJxAnnualAssessmentSummary.getYear());
				search2.setProjectId(project.getProjectId());
				List<ZjXmJxAnnualAssessmentSummary> rankList = zjXmJxAnnualAssessmentSummaryMapper
						.getZjXmJxAnnualAssessmentSummaryListByMonthLast(search2);
				if (rankList.size() > 0) {
					for (ZjXmJxAnnualAssessmentSummary insertData : rankList) {
						insertData.setSummaryId(UuidUtil.generate());
						insertData.setYear(zjXmJxAnnualAssessmentSummary.getYear());
						// 排名数据
						insertData.setSummaryType("1");
						insertData.setAnnualFlowId(insertFlow.getAnnualFlowId());
						insertData.setCreateUserInfo("定时任务", "定时任务");
						insertList.add(insertData);
					}
				}
			}
			if (insertFlowList.size() > 0) {
				// 根据年份项目先删后增
				ZjXmJxAnnualSummaryFlow delete = new ZjXmJxAnnualSummaryFlow();
				delete.setYear(zjXmJxAnnualAssessmentSummary.getYear());
				delete.setFlowType("1");
				zjXmJxAnnualSummaryFlowMapper.deleteZjXmJxAnnualSummaryFlowByCondition(delete);
				int flag = zjXmJxAnnualSummaryFlowMapper.batchInsertZjXmJxAnnualSummaryFlow(insertFlowList);
			}
			if (insertList.size() > 0) {
				// 根据年份项目先删后增
				ZjXmJxAnnualAssessmentSummary delete = new ZjXmJxAnnualAssessmentSummary();
				delete.setYear(zjXmJxAnnualAssessmentSummary.getYear());
				delete.setSummaryType("1");
				zjXmJxAnnualAssessmentSummaryMapper.deleteZjXmJxAnnualAssessmentSummaryByCondition(delete);
				int flag = zjXmJxAnnualAssessmentSummaryMapper.batchInsertZjXmJxAnnualAssessmentSummary(insertList);
			}
		}
		return repEntity.ok("定时任务执行成功!");
	}

	@Override
	public void exportRankZjXmJxAnnualAssessmentSummary(ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary,
			HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxAnnualAssessmentSummary == null) {
			zjXmJxAnnualAssessmentSummary = new ZjXmJxAnnualAssessmentSummary();
		}
		// 获取数据 项目id 月份 类型
		List<ZjXmJxAnnualAssessmentSummary> dataList = zjXmJxAnnualAssessmentSummaryMapper
				.selectByZjXmJxAnnualAssessmentSummaryList(zjXmJxAnnualAssessmentSummary);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("序号", "姓名", "身份证号码", "组内排序", "部门", "岗位", "岗位类别", "人员类别", "1月得分", "2月得分",
				"3月得分", "4月得分", "5月得分", "6月得分", "7月得分", "8月得分", "9月得分", "10月得分", "11月得分", "12月得分", "年度平均分", "列为末位人员的月份",
				"备注");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZjXmJxAnnualAssessmentSummary dbData : dataList) {
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
				rowsList.add(CollUtil.newArrayList(dbData.getSort(), dbData.getRealName(), dbData.getIdNumber(),
						dbData.getGroupSort(), dbData.getDeptName(), positionName, rankPositionTypeName, userTypeName,
						dbData.getJanuaryScore(), dbData.getFebruaryScore(), dbData.getMarchScore(),
						dbData.getAprilScore(), dbData.getMayScore(), dbData.getJuneScore(), dbData.getJulyScore(),
						dbData.getAugustScore(), dbData.getSeptemberScore(), dbData.getOctoberScore(),
						dbData.getNovemberScore(), dbData.getDecemberScore(), dbData.getAverageScore(),
						dbData.getLastPersonMonth(), dbData.getRemarks()));
			}
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
					"attachment; filename=\"" + new String(
							(DateUtil.year(zjXmJxAnnualAssessmentSummary.getYear()) + "年度项目员工考核评分汇总").getBytes("utf-8"),
							"iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			writer.merge(row.size() - 1, DateUtil.year(zjXmJxAnnualAssessmentSummary.getYear()) + "年度项目员工考核评分汇总表");
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