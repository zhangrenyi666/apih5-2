package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxAssessmentUserScoreMapper;
import com.apih5.mybatis.dao.ZjXmJxPrincipalScoreDetailedMapper;
import com.apih5.mybatis.pojo.ZjXmJxAnnualAssessmentSummary;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxPrincipalScoreDetailed;
import com.apih5.service.ZjXmJxPrincipalScoreDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjXmJxPrincipalScoreDetailedService")
public class ZjXmJxPrincipalScoreDetailedServiceImpl implements ZjXmJxPrincipalScoreDetailedService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxPrincipalScoreDetailedMapper zjXmJxPrincipalScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreMapper zjXmJxAssessmentUserScoreMapper;

	@Override
	public ResponseEntity getZjXmJxPrincipalScoreDetailedListByCondition(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		if (zjXmJxPrincipalScoreDetailed == null) {
			zjXmJxPrincipalScoreDetailed = new ZjXmJxPrincipalScoreDetailed();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxPrincipalScoreDetailed.getPage(), zjXmJxPrincipalScoreDetailed.getLimit());
		// 获取数据
		List<ZjXmJxPrincipalScoreDetailed> zjXmJxPrincipalScoreDetailedList = zjXmJxPrincipalScoreDetailedMapper
				.selectByZjXmJxPrincipalScoreDetailedList(zjXmJxPrincipalScoreDetailed);
		// 得到分页信息
		PageInfo<ZjXmJxPrincipalScoreDetailed> p = new PageInfo<>(zjXmJxPrincipalScoreDetailedList);

		return repEntity.okList(zjXmJxPrincipalScoreDetailedList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxPrincipalScoreDetailedDetails(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		if (zjXmJxPrincipalScoreDetailed == null) {
			zjXmJxPrincipalScoreDetailed = new ZjXmJxPrincipalScoreDetailed();
		}
		// 获取数据
		ZjXmJxPrincipalScoreDetailed dbZjXmJxPrincipalScoreDetailed = zjXmJxPrincipalScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxPrincipalScoreDetailed.getDetailedId());
		// 数据存在
		if (dbZjXmJxPrincipalScoreDetailed != null) {
			return repEntity.ok(dbZjXmJxPrincipalScoreDetailed);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxPrincipalScoreDetailed(ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxPrincipalScoreDetailed.setDetailedId(UuidUtil.generate());
		zjXmJxPrincipalScoreDetailed.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxPrincipalScoreDetailedMapper.insert(zjXmJxPrincipalScoreDetailed);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxPrincipalScoreDetailed);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxPrincipalScoreDetailed(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxPrincipalScoreDetailed dbzjXmJxPrincipalScoreDetailed = zjXmJxPrincipalScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxPrincipalScoreDetailed.getDetailedId());
		if (dbzjXmJxPrincipalScoreDetailed != null
				&& StrUtil.isNotEmpty(dbzjXmJxPrincipalScoreDetailed.getDetailedId())) {
			// 月度考核得分表主键
			dbzjXmJxPrincipalScoreDetailed.setScoreId(zjXmJxPrincipalScoreDetailed.getScoreId());
			// 月度考核表主键
			dbzjXmJxPrincipalScoreDetailed.setAssessmentId(zjXmJxPrincipalScoreDetailed.getAssessmentId());
			// 被审核人员主键
			dbzjXmJxPrincipalScoreDetailed.setAuditeeKey(zjXmJxPrincipalScoreDetailed.getAuditeeKey());
			// 被审核人员姓名
			dbzjXmJxPrincipalScoreDetailed.setAuditeeName(zjXmJxPrincipalScoreDetailed.getAuditeeName());
			// 被审核者部门主键
			dbzjXmJxPrincipalScoreDetailed.setAuditeeDeptId(zjXmJxPrincipalScoreDetailed.getAuditeeDeptId());
			// 被审核者部门名称
			dbzjXmJxPrincipalScoreDetailed.setAuditeeDeptName(zjXmJxPrincipalScoreDetailed.getAuditeeDeptName());
			// 被审核者项目id
			dbzjXmJxPrincipalScoreDetailed.setAuditeeProId(zjXmJxPrincipalScoreDetailed.getAuditeeProId());
			// 被审核者项目名称
			dbzjXmJxPrincipalScoreDetailed.setAuditeeProName(zjXmJxPrincipalScoreDetailed.getAuditeeProName());
			// 审核者主键
			dbzjXmJxPrincipalScoreDetailed.setReviewerKey(zjXmJxPrincipalScoreDetailed.getReviewerKey());
			// 审核者姓名
			dbzjXmJxPrincipalScoreDetailed.setReviewerName(zjXmJxPrincipalScoreDetailed.getReviewerName());
			// 人事专员主键
			dbzjXmJxPrincipalScoreDetailed.setHrUserKey(zjXmJxPrincipalScoreDetailed.getHrUserKey());
			// 人事专员姓名
			dbzjXmJxPrincipalScoreDetailed.setHrName(zjXmJxPrincipalScoreDetailed.getHrName());
			// 分数
			dbzjXmJxPrincipalScoreDetailed.setScore(zjXmJxPrincipalScoreDetailed.getScore());
			// 判分者职位
			dbzjXmJxPrincipalScoreDetailed.setReviewerPosition(zjXmJxPrincipalScoreDetailed.getReviewerPosition());
			// 审核状态
			dbzjXmJxPrincipalScoreDetailed.setAuditStatus(zjXmJxPrincipalScoreDetailed.getAuditStatus());
			// 备注
			dbzjXmJxPrincipalScoreDetailed.setRemarks(zjXmJxPrincipalScoreDetailed.getRemarks());
			// 排序
			dbzjXmJxPrincipalScoreDetailed.setSort(zjXmJxPrincipalScoreDetailed.getSort());
			// 共通
			dbzjXmJxPrincipalScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxPrincipalScoreDetailedMapper.updateByPrimaryKey(dbzjXmJxPrincipalScoreDetailed);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxPrincipalScoreDetailed);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxPrincipalScoreDetailed(
			List<ZjXmJxPrincipalScoreDetailed> zjXmJxPrincipalScoreDetailedList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxPrincipalScoreDetailedList != null && zjXmJxPrincipalScoreDetailedList.size() > 0) {
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed = new ZjXmJxPrincipalScoreDetailed();
			zjXmJxPrincipalScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxPrincipalScoreDetailedMapper.batchDeleteUpdateZjXmJxPrincipalScoreDetailed(
					zjXmJxPrincipalScoreDetailedList, zjXmJxPrincipalScoreDetailed);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxPrincipalScoreDetailedList);
		}
	}

	@Override
	public ResponseEntity getZjXmJxPrincipalScoreDetailedListByReviewer(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		if (zjXmJxPrincipalScoreDetailed == null) {
			zjXmJxPrincipalScoreDetailed = new ZjXmJxPrincipalScoreDetailed();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxPrincipalScoreDetailed.setReviewerKey(userKey);
		// 根据分数获取数据
		List<ZjXmJxPrincipalScoreDetailed> auditeeList = zjXmJxPrincipalScoreDetailedMapper
				.getZjXmJxPrincipalScoreDetailedList(zjXmJxPrincipalScoreDetailed);
		JSONArray deputyArray = JSONUtil.createArray();
		JSONArray leaderArray = JSONUtil.createArray();
		JSONArray employeeArray = JSONUtil.createArray();
		// 随机标识
		boolean shuffleFlag = true;
		if (auditeeList.size() > 0) {
			for (ZjXmJxPrincipalScoreDetailed auditee : auditeeList) {
				if (!StrUtil.equals("0", auditee.getAuditStatus())) {
					shuffleFlag = false;
				}
				JSONObject jsonObj = JSONUtil.createObj();
				jsonObj.set("value", auditee.getAuditeeKey());
				jsonObj.set("label", auditee.getAuditeeName());
				jsonObj.set("score", auditee.getScore());
				jsonObj.set("valuePid", auditee.getAuditeeDeptId());
				if (StrUtil.equals("0", auditee.getAuditeeType())) {
					deputyArray.add(jsonObj);
				} else if (StrUtil.equals("1", auditee.getAuditeeType())) {
					leaderArray.add(jsonObj);
				} else if (StrUtil.equals("2", auditee.getAuditeeType())) {
					employeeArray.add(jsonObj);
				}
			}
		}
		// 随机
		if (shuffleFlag) {
			Collections.shuffle(deputyArray);
			Collections.shuffle(leaderArray);
			Collections.shuffle(employeeArray);
		}
		zjXmJxPrincipalScoreDetailed.setDeputyArray(deputyArray);
		zjXmJxPrincipalScoreDetailed.setLeaderArray(leaderArray);
		zjXmJxPrincipalScoreDetailed.setEmployeeArray(employeeArray);
		// check根据打分者和月度考核判断是否所有人已打完分，
		ZjXmJxPrincipalScoreDetailed check = new ZjXmJxPrincipalScoreDetailed();
		check.setAssessmentId(zjXmJxPrincipalScoreDetailed.getAssessmentId());
		check.setReviewerKey(userKey);
		int count1 = zjXmJxPrincipalScoreDetailedMapper.countZjXmJxPrincipalScoreDetailedList(check);
		if (count1 == 0) {
			zjXmJxPrincipalScoreDetailed.setButtonFlag("0");
		} else {
			zjXmJxPrincipalScoreDetailed.setButtonFlag("1");
		}
		return repEntity.ok(zjXmJxPrincipalScoreDetailed);
	}

	@Override
	public ResponseEntity tempOrSubmitZjXmJxPrincipalScoreDetailedList(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		JSONArray userArray = JSONUtil.createArray();
		if (zjXmJxPrincipalScoreDetailed != null) {
			if (zjXmJxPrincipalScoreDetailed.getDeputyArray() != null
					&& zjXmJxPrincipalScoreDetailed.getDeputyArray().size() > 0) {
				// check三个数组每个数组不能出现相同的分数
				if (checkRepeatScore(zjXmJxPrincipalScoreDetailed.getDeputyArray())) {
					return repEntity.layerMessage("no", "项目副职中存在相同分值。");
				}
				userArray.addAll(zjXmJxPrincipalScoreDetailed.getDeputyArray());
			}
			if (zjXmJxPrincipalScoreDetailed.getLeaderArray() != null
					&& zjXmJxPrincipalScoreDetailed.getLeaderArray().size() > 0) {
				// check三个数组每个数组不能出现相同的分数
				if (checkRepeatScore(zjXmJxPrincipalScoreDetailed.getLeaderArray())) {
					return repEntity.layerMessage("no", "部门负责人中存在相同分值。");
				}
				userArray.addAll(zjXmJxPrincipalScoreDetailed.getLeaderArray());
			}
			if (zjXmJxPrincipalScoreDetailed.getEmployeeArray() != null
					&& zjXmJxPrincipalScoreDetailed.getEmployeeArray().size() > 0) {
				// check三个数组每个数组不能出现相同的分数
				if (checkRepeatScore(zjXmJxPrincipalScoreDetailed.getEmployeeArray())) {
					return repEntity.layerMessage("no", "普通员工中存在相同分值。");
				}
				userArray.addAll(zjXmJxPrincipalScoreDetailed.getEmployeeArray());
			}
		}

		if (StrUtil.isNotEmpty(zjXmJxPrincipalScoreDetailed.getAssessmentId()) && userArray.size() > 0) {
			// check根据打分者和月度考核判断是否所有人已打完分，
			ZjXmJxPrincipalScoreDetailed check = new ZjXmJxPrincipalScoreDetailed();
			check.setAssessmentId(zjXmJxPrincipalScoreDetailed.getAssessmentId());
			check.setReviewerKey(userKey);
			int count1 = zjXmJxPrincipalScoreDetailedMapper.countZjXmJxPrincipalScoreDetailedList(check);
			if (count1 == 0) {
				return repEntity.layerMessage("no", "该评分者已打分,请不要重复提交");
			}
			// 暂存
			if (StrUtil.equals("3", zjXmJxPrincipalScoreDetailed.getAuditStatus())) {
				List<ZjXmJxPrincipalScoreDetailed> updateList = Lists.newArrayList();
				for (int i = 0; i < userArray.size(); i++) {
					ZjXmJxPrincipalScoreDetailed updateData = new ZjXmJxPrincipalScoreDetailed();
					updateData.setAuditeeKey(userArray.getJSONObject(i).getStr("value"));
					updateData.setReviewerKey(userKey);
					updateData.setAssessmentId(zjXmJxPrincipalScoreDetailed.getAssessmentId());
					updateData.setScore(userArray.getJSONObject(i).getBigDecimal("score"));
					// 暂存
					updateData.setAuditStatus("3");
					updateList.add(updateData);
				}
				flag = zjXmJxPrincipalScoreDetailedMapper.batchUpdateZjXmJxPrincipalScoreDetailedByReviewer(updateList);
			}
			// 提交
			else if (StrUtil.equals("4", zjXmJxPrincipalScoreDetailed.getAuditStatus())) {
				List<ZjXmJxPrincipalScoreDetailed> updateList = Lists.newArrayList();
				for (int i = 0; i < userArray.size(); i++) {
					ZjXmJxPrincipalScoreDetailed updateData = new ZjXmJxPrincipalScoreDetailed();
					updateData.setAuditeeKey(userArray.getJSONObject(i).getStr("value"));
					updateData.setReviewerKey(userKey);
					updateData.setAssessmentId(zjXmJxPrincipalScoreDetailed.getAssessmentId());
					updateData.setScore(userArray.getJSONObject(i).getBigDecimal("score"));
					// 已审核
					updateData.setAuditStatus("4");
					updateList.add(updateData);
				}
				flag = zjXmJxPrincipalScoreDetailedMapper.batchUpdateZjXmJxPrincipalScoreDetailedByReviewer(updateList);
				// 循环判断被打分者是否已被打完分,被打完分需要核算该人员正职考核总分
				for (ZjXmJxPrincipalScoreDetailed auditee : updateList) {
					// 根据auditeeKey和assessmentId和auditStatus判断是否被打完分
					ZjXmJxPrincipalScoreDetailed check2 = new ZjXmJxPrincipalScoreDetailed();
					check2.setAssessmentId(auditee.getAssessmentId());
					check2.setAuditeeKey(auditee.getAuditeeKey());
					int count2 = zjXmJxPrincipalScoreDetailedMapper.countZjXmJxPrincipalScoreDetailedList(check2);
					// 说明该被考核者已被打完分
					if (count2 == 0) {
						// 核算该被打分者总分
						// 根据auditeeKey和assessmentId获取集合
						ZjXmJxPrincipalScoreDetailed scoreDetailed = new ZjXmJxPrincipalScoreDetailed();
						scoreDetailed.setAuditeeKey(auditee.getAuditeeKey());
						scoreDetailed.setAssessmentId(auditee.getAssessmentId());
						List<ZjXmJxPrincipalScoreDetailed> list = zjXmJxPrincipalScoreDetailedMapper
								.getZjXmJxPrincipalScoreDetailedList(scoreDetailed);
						// 整理总分
						BigDecimal totalScore = BigDecimal.ZERO;
						if (list.size() > 0) {
							for (ZjXmJxPrincipalScoreDetailed score : list) {
								totalScore = CalcUtils.calcAdd(totalScore, score.getScore());
							}
							totalScore = CalcUtils.calcDivide(totalScore, new BigDecimal(list.size()), 2);
						}
						// 找到该被打分者得分表修改总分及审核状态字段
						ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
						userScore.setAssessmentId(zjXmJxPrincipalScoreDetailed.getAssessmentId());
						userScore.setAuditeeKey(auditee.getAuditeeKey());
						userScore.setAssessmentType("2");
						List<ZjXmJxAssessmentUserScore> userScorelist = zjXmJxAssessmentUserScoreMapper
								.selectByZjXmJxAssessmentUserScoreList(userScore);
						if (userScorelist.size() == 1) {
							userScorelist.get(0).setScore(totalScore);
							userScorelist.get(0).setAuditStatus("4");
							userScorelist.get(0).setAuditTime(DateUtil.date());
							userScorelist.get(0).setModifyUserInfo(userKey, realName);
							flag = zjXmJxAssessmentUserScoreMapper.updateByPrimaryKey(userScorelist.get(0));
						}
					}
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxPrincipalScoreDetailed);
		}
	}

	private Boolean checkRepeatScore(JSONArray array) {
		Boolean bool = false;
		Map<BigDecimal, String> map = new HashMap<BigDecimal, String>();
		if (array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				if (map.containsKey(array.getJSONObject(i).getBigDecimal("score"))) {
					bool = true;
					break;
				} else {
					map.put(array.getJSONObject(i).getBigDecimal("score"), "记录重复分数");
				}
			}
		}
		return bool;
	}

	@Override
	public ResponseEntity getZjXmJxPrincipalScoreDetailedPrincipalExcel(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed) {
		if (zjXmJxPrincipalScoreDetailed == null) {
			zjXmJxPrincipalScoreDetailed = new ZjXmJxPrincipalScoreDetailed();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxPrincipalScoreDetailed.getPage(), zjXmJxPrincipalScoreDetailed.getLimit());
		// 获取数据
		List<ZjXmJxPrincipalScoreDetailed> zjXmJxPrincipalScoreDetailedList = zjXmJxPrincipalScoreDetailedMapper
				.getZjXmJxPrincipalScoreDetailedPrincipalExcel(zjXmJxPrincipalScoreDetailed);
		// 得到分页信息
		PageInfo<ZjXmJxPrincipalScoreDetailed> p = new PageInfo<>(zjXmJxPrincipalScoreDetailedList);

		return repEntity.okList(zjXmJxPrincipalScoreDetailedList, p.getTotal());
	}

	@Override
	public void exportZjXmJxPrincipalScoreDetailedPrincipalExcel(
			ZjXmJxPrincipalScoreDetailed zjXmJxPrincipalScoreDetailed, HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxPrincipalScoreDetailed == null) {
			zjXmJxPrincipalScoreDetailed = new ZjXmJxPrincipalScoreDetailed();
		}
		// 获取数据 项目id 月份 月度考核id
		List<ZjXmJxPrincipalScoreDetailed> dataList = zjXmJxPrincipalScoreDetailedMapper
				.getZjXmJxPrincipalScoreDetailedPrincipalExcel(zjXmJxPrincipalScoreDetailed);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("姓名", "身份证号码", "正职(书记)得分", "正职(经理)得分");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZjXmJxPrincipalScoreDetailed dbData : dataList) {
				rowsList.add(CollUtil.newArrayList(dbData.getAuditeeName(), dbData.getIdNumber(),
						dbData.getSecretaryScore(), dbData.getManagerScore()));
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
					"attachment; filename=\"" + new String("正职得分明细表".getBytes("utf-8"), "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			writer.merge(row.size() - 1, "正职得分明细表");
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
