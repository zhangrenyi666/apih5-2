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
		// ????????????
		PageHelper.startPage(zjXmJxAnnualAssessmentSummary.getPage(), zjXmJxAnnualAssessmentSummary.getLimit());
		// ????????????
		List<ZjXmJxAnnualAssessmentSummary> zjXmJxAnnualAssessmentSummaryList = zjXmJxAnnualAssessmentSummaryMapper
				.selectByZjXmJxAnnualAssessmentSummaryList(zjXmJxAnnualAssessmentSummary);
		// ??????????????????
		PageInfo<ZjXmJxAnnualAssessmentSummary> p = new PageInfo<>(zjXmJxAnnualAssessmentSummaryList);

		return repEntity.okList(zjXmJxAnnualAssessmentSummaryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxAnnualAssessmentSummaryDetails(
			ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary) {
		if (zjXmJxAnnualAssessmentSummary == null) {
			zjXmJxAnnualAssessmentSummary = new ZjXmJxAnnualAssessmentSummary();
		}
		// ????????????
		ZjXmJxAnnualAssessmentSummary dbZjXmJxAnnualAssessmentSummary = zjXmJxAnnualAssessmentSummaryMapper
				.selectByPrimaryKey(zjXmJxAnnualAssessmentSummary.getSummaryId());
		// ????????????
		if (dbZjXmJxAnnualAssessmentSummary != null) {
			return repEntity.ok(dbZjXmJxAnnualAssessmentSummary);
		} else {
			return repEntity.layerMessage("no", "????????????");
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
			// ??????id
			dbzjXmJxAnnualAssessmentSummary.setProjectId(zjXmJxAnnualAssessmentSummary.getProjectId());
			// ????????????
			dbzjXmJxAnnualAssessmentSummary.setProjectName(zjXmJxAnnualAssessmentSummary.getProjectName());
			// ??????id
			dbzjXmJxAnnualAssessmentSummary.setDeptId(zjXmJxAnnualAssessmentSummary.getDeptId());
			// ????????????
			dbzjXmJxAnnualAssessmentSummary.setDeptName(zjXmJxAnnualAssessmentSummary.getDeptName());
			// userKey
			dbzjXmJxAnnualAssessmentSummary.setUserKey(zjXmJxAnnualAssessmentSummary.getUserKey());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setRealName(zjXmJxAnnualAssessmentSummary.getRealName());
			// ???????????????
			dbzjXmJxAnnualAssessmentSummary.setIdNumber(zjXmJxAnnualAssessmentSummary.getIdNumber());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setPosition(zjXmJxAnnualAssessmentSummary.getPosition());
			// ????????????(??????)
			dbzjXmJxAnnualAssessmentSummary.setRankPositionType(zjXmJxAnnualAssessmentSummary.getRankPositionType());
			// ????????????(????????????)
			dbzjXmJxAnnualAssessmentSummary.setLastPositionType(zjXmJxAnnualAssessmentSummary.getLastPositionType());
			// ????????????
			dbzjXmJxAnnualAssessmentSummary.setUserType(zjXmJxAnnualAssessmentSummary.getUserType());
			// ????????????
			dbzjXmJxAnnualAssessmentSummary.setYear(zjXmJxAnnualAssessmentSummary.getYear());
			// 1?????????
			dbzjXmJxAnnualAssessmentSummary.setJanuaryScore(zjXmJxAnnualAssessmentSummary.getJanuaryScore());
			// 2?????????
			dbzjXmJxAnnualAssessmentSummary.setFebruaryScore(zjXmJxAnnualAssessmentSummary.getFebruaryScore());
			// 3?????????
			dbzjXmJxAnnualAssessmentSummary.setMarchScore(zjXmJxAnnualAssessmentSummary.getMarchScore());
			// 4?????????
			dbzjXmJxAnnualAssessmentSummary.setAprilScore(zjXmJxAnnualAssessmentSummary.getAprilScore());
			// 5?????????
			dbzjXmJxAnnualAssessmentSummary.setMayScore(zjXmJxAnnualAssessmentSummary.getMayScore());
			// 6?????????
			dbzjXmJxAnnualAssessmentSummary.setJuneScore(zjXmJxAnnualAssessmentSummary.getJuneScore());
			// 7?????????
			dbzjXmJxAnnualAssessmentSummary.setJulyScore(zjXmJxAnnualAssessmentSummary.getJulyScore());
			// 8?????????
			dbzjXmJxAnnualAssessmentSummary.setAugustScore(zjXmJxAnnualAssessmentSummary.getAugustScore());
			// 9?????????
			dbzjXmJxAnnualAssessmentSummary.setSeptemberScore(zjXmJxAnnualAssessmentSummary.getSeptemberScore());
			// 10?????????
			dbzjXmJxAnnualAssessmentSummary.setOctoberScore(zjXmJxAnnualAssessmentSummary.getOctoberScore());
			// 11?????????
			dbzjXmJxAnnualAssessmentSummary.setNovemberScore(zjXmJxAnnualAssessmentSummary.getNovemberScore());
			// 12?????????
			dbzjXmJxAnnualAssessmentSummary.setDecemberScore(zjXmJxAnnualAssessmentSummary.getDecemberScore());
			// ???????????????
			dbzjXmJxAnnualAssessmentSummary.setAverageScore(zjXmJxAnnualAssessmentSummary.getAverageScore());
			// ???????????????????????????
			dbzjXmJxAnnualAssessmentSummary.setLastPersonMonth(zjXmJxAnnualAssessmentSummary.getLastPersonMonth());
			// ????????????
			dbzjXmJxAnnualAssessmentSummary.setGroupSort(zjXmJxAnnualAssessmentSummary.getGroupSort());
			// ???????????????
			dbzjXmJxAnnualAssessmentSummary.setSummaryType(zjXmJxAnnualAssessmentSummary.getSummaryType());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setOpinionField1(zjXmJxAnnualAssessmentSummary.getOpinionField1());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setOpinionField2(zjXmJxAnnualAssessmentSummary.getOpinionField2());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setOpinionField3(zjXmJxAnnualAssessmentSummary.getOpinionField3());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setOpinionField4(zjXmJxAnnualAssessmentSummary.getOpinionField4());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setOpinionField5(zjXmJxAnnualAssessmentSummary.getOpinionField5());
			// ??????id
			dbzjXmJxAnnualAssessmentSummary.setApih5FlowId(zjXmJxAnnualAssessmentSummary.getApih5FlowId());
			// work_id
			dbzjXmJxAnnualAssessmentSummary.setWorkId(zjXmJxAnnualAssessmentSummary.getWorkId());
			// ??????????????????
			dbzjXmJxAnnualAssessmentSummary.setApih5FlowStatus(zjXmJxAnnualAssessmentSummary.getApih5FlowStatus());
			// ????????????
			dbzjXmJxAnnualAssessmentSummary
					.setApih5FlowNodeStatus(zjXmJxAnnualAssessmentSummary.getApih5FlowNodeStatus());
			// ?????????????????????
			dbzjXmJxAnnualAssessmentSummary.setAnnualFlowId(zjXmJxAnnualAssessmentSummary.getAnnualFlowId());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setRemarks(zjXmJxAnnualAssessmentSummary.getRemarks());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setSort(zjXmJxAnnualAssessmentSummary.getSort());
			// ??????
			dbzjXmJxAnnualAssessmentSummary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxAnnualAssessmentSummaryMapper.updateByPrimaryKey(dbzjXmJxAnnualAssessmentSummary);
		}
		// ??????
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
		// ??????
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
		// ??????????????????????????????(?????????????????????????????????)
		if (zjXmJxAnnualAssessmentSummary == null || zjXmJxAnnualAssessmentSummary.getYear() == null) {
			zjXmJxAnnualAssessmentSummary = new ZjXmJxAnnualAssessmentSummary();
			zjXmJxAnnualAssessmentSummary.setYear(DateUtil.date());
		}
		// ??????????????????????????????????????????????????????,?????????????????????????????????????????????
		ZjXmJxMonthlyAssessmentSummary search = new ZjXmJxMonthlyAssessmentSummary();
		search.setYearMonth(zjXmJxAnnualAssessmentSummary.getYear());
		List<ZjXmJxMonthlyAssessmentSummary> projectList = zjXmJxMonthlyAssessmentSummaryMapper
				.getZjXmJxMonthlyAssessmentSummaryProjectByYear(search);
		if (projectList.size() > 0) {
			List<ZjXmJxAnnualSummaryFlow> insertFlowList = Lists.newArrayList();
			List<ZjXmJxAnnualAssessmentSummary> insertList = Lists.newArrayList();
			for (ZjXmJxMonthlyAssessmentSummary project : projectList) {
				// ?????????????????????????????????????????????????????????
				ZjXmJxAnnualSummaryFlow insertFlow = new ZjXmJxAnnualSummaryFlow();
				insertFlow.setAnnualFlowId(UuidUtil.generate());
				insertFlow.setYear(zjXmJxAnnualAssessmentSummary.getYear());
				insertFlow.setProjectId(project.getProjectId());
				insertFlow.setProjectName(project.getProjectName());
				insertFlow.setDeptId("?????????");
				insertFlow.setDeptName("?????????");
				insertFlow.setFlowType("1");
				insertFlow.setCreateUserInfo("????????????", "????????????");
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
						// ????????????
						insertData.setSummaryType("1");
						insertData.setAnnualFlowId(insertFlow.getAnnualFlowId());
						insertData.setCreateUserInfo("????????????", "????????????");
						insertList.add(insertData);
					}
				}
			}
			if (insertFlowList.size() > 0) {
				// ??????????????????????????????
				ZjXmJxAnnualSummaryFlow delete = new ZjXmJxAnnualSummaryFlow();
				delete.setYear(zjXmJxAnnualAssessmentSummary.getYear());
				delete.setFlowType("1");
				zjXmJxAnnualSummaryFlowMapper.deleteZjXmJxAnnualSummaryFlowByCondition(delete);
				int flag = zjXmJxAnnualSummaryFlowMapper.batchInsertZjXmJxAnnualSummaryFlow(insertFlowList);
			}
			if (insertList.size() > 0) {
				// ??????????????????????????????
				ZjXmJxAnnualAssessmentSummary delete = new ZjXmJxAnnualAssessmentSummary();
				delete.setYear(zjXmJxAnnualAssessmentSummary.getYear());
				delete.setSummaryType("1");
				zjXmJxAnnualAssessmentSummaryMapper.deleteZjXmJxAnnualAssessmentSummaryByCondition(delete);
				int flag = zjXmJxAnnualAssessmentSummaryMapper.batchInsertZjXmJxAnnualAssessmentSummary(insertList);
			}
		}
		return repEntity.ok("????????????????????????!");
	}

	@Override
	public void exportRankZjXmJxAnnualAssessmentSummary(ZjXmJxAnnualAssessmentSummary zjXmJxAnnualAssessmentSummary,
			HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxAnnualAssessmentSummary == null) {
			zjXmJxAnnualAssessmentSummary = new ZjXmJxAnnualAssessmentSummary();
		}
		// ???????????? ??????id ?????? ??????
		List<ZjXmJxAnnualAssessmentSummary> dataList = zjXmJxAnnualAssessmentSummaryMapper
				.selectByZjXmJxAnnualAssessmentSummaryList(zjXmJxAnnualAssessmentSummary);
		// ??????
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("??????", "??????", "???????????????", "????????????", "??????", "??????", "????????????", "????????????", "1?????????", "2?????????",
				"3?????????", "4?????????", "5?????????", "6?????????", "7?????????", "8?????????", "9?????????", "10?????????", "11?????????", "12?????????", "???????????????", "???????????????????????????",
				"??????");
		rowsList.add(row);
		// ??????????????????????????????????????????
		if (dataList != null && dataList.size() > 0) {
			for (ZjXmJxAnnualAssessmentSummary dbData : dataList) {
				// ??????????????????????????????
				String positionName = "";
				if (StrUtil.isNotEmpty(dbData.getPosition())) {
					positionName = baseCodeService.getBaseCodeItemName("jobType", dbData.getPosition());
				}
				String rankPositionTypeName = "";
				if (StrUtil.equals("1", dbData.getRankPositionType())) {
					rankPositionTypeName = "????????????";
				} else if (StrUtil.equals("2", dbData.getRankPositionType())) {
					rankPositionTypeName = "???????????????";
				} else if (StrUtil.equals("3", dbData.getRankPositionType())) {
					rankPositionTypeName = "???????????????";
				} else if (StrUtil.equals("4", dbData.getRankPositionType())) {
					rankPositionTypeName = "??????????????????????????????";
				} else if (StrUtil.equals("5", dbData.getRankPositionType())) {
					rankPositionTypeName = "????????????";
				}
				String userTypeName = "";
				if (StrUtil.equals("1", dbData.getUserType())) {
					userTypeName = "??????";
				} else if (StrUtil.equals("2", dbData.getUserType())) {
					userTypeName = "?????????";
				} else if (StrUtil.equals("3", dbData.getUserType())) {
					userTypeName = "?????????";
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
		// ?????????????????????writer?????????xlsx??????
		ExcelWriter writer = ExcelUtil.getWriter(true);
		// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
		// response???HttpServletResponse??????
		// ??????response????????????
		// response.reset();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
		// test.xls??????????????????????????????????????????????????????????????????????????????
		// response.setHeader("Content-Disposition", "attachment;filename=test.xlsx");
		// out???OutputStream??????????????????????????????
		ServletOutputStream out = null;
		try {
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + new String(
							(DateUtil.year(zjXmJxAnnualAssessmentSummary.getYear()) + "????????????????????????????????????").getBytes("utf-8"),
							"iso-8859-1") + "\"");
			out = response.getOutputStream();
			// ????????????
			writer.merge(row.size() - 1, DateUtil.year(zjXmJxAnnualAssessmentSummary.getYear()) + "???????????????????????????????????????");
			// ???????????????????????????????????????????????????????????????
			writer.write(rowsList, true);
			writer.flush(out, true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// ??????writer???????????????
			if (writer != null) {
				writer.close();
			}
			// ????????????????????????Servlet???
			if (out != null) {
				IoUtil.close(out);
			}
		}
	}
}