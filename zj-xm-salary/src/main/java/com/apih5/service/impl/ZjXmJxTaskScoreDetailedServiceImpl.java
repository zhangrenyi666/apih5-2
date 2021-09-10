package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
import com.apih5.mybatis.dao.ZjXmJxTaskScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxTaskScoreDetailedRecordMapper;
import com.apih5.mybatis.pojo.ZjXmJxAnnualAssessmentSummary;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailedRecord;
import com.apih5.service.ZjXmJxTaskScoreDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjXmJxTaskScoreDetailedService")
public class ZjXmJxTaskScoreDetailedServiceImpl implements ZjXmJxTaskScoreDetailedService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxTaskScoreDetailedMapper zjXmJxTaskScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreMapper zjXmJxAssessmentUserScoreMapper;
	@Autowired(required = true)
	private ZjXmJxTaskScoreDetailedRecordMapper zjXmJxTaskScoreDetailedRecordMapper;

	@Override
	public ResponseEntity getZjXmJxTaskScoreDetailedListByCondition(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		if (zjXmJxTaskScoreDetailed == null) {
			zjXmJxTaskScoreDetailed = new ZjXmJxTaskScoreDetailed();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxTaskScoreDetailed.getPage(), zjXmJxTaskScoreDetailed.getLimit());
		// 获取数据
		List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList = zjXmJxTaskScoreDetailedMapper
				.selectByZjXmJxTaskScoreDetailedList(zjXmJxTaskScoreDetailed);
		// 得到分页信息
		PageInfo<ZjXmJxTaskScoreDetailed> p = new PageInfo<>(zjXmJxTaskScoreDetailedList);

		return repEntity.okList(zjXmJxTaskScoreDetailedList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxTaskScoreDetailedDetails(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		if (zjXmJxTaskScoreDetailed == null) {
			zjXmJxTaskScoreDetailed = new ZjXmJxTaskScoreDetailed();
		}
		// 获取数据
		ZjXmJxTaskScoreDetailed dbZjXmJxTaskScoreDetailed = zjXmJxTaskScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxTaskScoreDetailed.getDetailedId());
		// 数据存在
		if (dbZjXmJxTaskScoreDetailed != null) {
			return repEntity.ok(dbZjXmJxTaskScoreDetailed);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxTaskScoreDetailed(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxTaskScoreDetailed.setDetailedId(UuidUtil.generate());
		zjXmJxTaskScoreDetailed.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxTaskScoreDetailedMapper.insert(zjXmJxTaskScoreDetailed);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxTaskScoreDetailed);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxTaskScoreDetailed(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxTaskScoreDetailed dbzjXmJxTaskScoreDetailed = zjXmJxTaskScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxTaskScoreDetailed.getDetailedId());
		if (dbzjXmJxTaskScoreDetailed != null && StrUtil.isNotEmpty(dbzjXmJxTaskScoreDetailed.getDetailedId())) {
			// 月度考核得分表主键
			dbzjXmJxTaskScoreDetailed.setScoreId(zjXmJxTaskScoreDetailed.getScoreId());
			// 指标库主键
			dbzjXmJxTaskScoreDetailed.setLibraryId(zjXmJxTaskScoreDetailed.getLibraryId());
			// 月度考核表主键
			dbzjXmJxTaskScoreDetailed.setAssessmentId(zjXmJxTaskScoreDetailed.getAssessmentId());
			// 被审核人员主键
			dbzjXmJxTaskScoreDetailed.setAuditeeKey(zjXmJxTaskScoreDetailed.getAuditeeKey());
			// 被审核人员姓名
			dbzjXmJxTaskScoreDetailed.setAuditeeName(zjXmJxTaskScoreDetailed.getAuditeeName());
			// 被审核者部门主键
			dbzjXmJxTaskScoreDetailed.setAuditeeDeptId(zjXmJxTaskScoreDetailed.getAuditeeDeptId());
			// 被审核者部门名称
			dbzjXmJxTaskScoreDetailed.setAuditeeDeptName(zjXmJxTaskScoreDetailed.getAuditeeDeptName());
			// 被审核者项目id
			dbzjXmJxTaskScoreDetailed.setAuditeeProId(zjXmJxTaskScoreDetailed.getAuditeeProId());
			// 被审核者项目名称
			dbzjXmJxTaskScoreDetailed.setAuditeeProName(zjXmJxTaskScoreDetailed.getAuditeeProName());
			// 审核者主键
			dbzjXmJxTaskScoreDetailed.setReviewerKey(zjXmJxTaskScoreDetailed.getReviewerKey());
			// 审核者姓名
			dbzjXmJxTaskScoreDetailed.setReviewerName(zjXmJxTaskScoreDetailed.getReviewerName());
			// 人事专员主键
			dbzjXmJxTaskScoreDetailed.setHrUserKey(zjXmJxTaskScoreDetailed.getHrUserKey());
			// 人事专员姓名
			dbzjXmJxTaskScoreDetailed.setHrName(zjXmJxTaskScoreDetailed.getHrName());
			// 成本责任指标
			dbzjXmJxTaskScoreDetailed.setCostDutyIndex(zjXmJxTaskScoreDetailed.getCostDutyIndex());
			// 目标值
			dbzjXmJxTaskScoreDetailed.setTargetValue(zjXmJxTaskScoreDetailed.getTargetValue());
			// 评价计分标准
			dbzjXmJxTaskScoreDetailed.setScoringStandard(zjXmJxTaskScoreDetailed.getScoringStandard());
			// 权重
			dbzjXmJxTaskScoreDetailed.setWeightValue(zjXmJxTaskScoreDetailed.getWeightValue());
			// 分数
			dbzjXmJxTaskScoreDetailed.setScore(zjXmJxTaskScoreDetailed.getScore());
			// 申诉状态
			dbzjXmJxTaskScoreDetailed.setAppealStatus(zjXmJxTaskScoreDetailed.getAppealStatus());
			// 申诉意见
			dbzjXmJxTaskScoreDetailed.setAppealOpinion(zjXmJxTaskScoreDetailed.getAppealOpinion());
			// 人事专员意见
			dbzjXmJxTaskScoreDetailed.setHrOpinion(zjXmJxTaskScoreDetailed.getHrOpinion());
			// 完成情况填写
			dbzjXmJxTaskScoreDetailed.setCompletion(zjXmJxTaskScoreDetailed.getCompletion());
			// 上级领导意见
			dbzjXmJxTaskScoreDetailed.setSuperiorOpinion(zjXmJxTaskScoreDetailed.getSuperiorOpinion());
			// 来源
			dbzjXmJxTaskScoreDetailed.setDataSources(zjXmJxTaskScoreDetailed.getDataSources());
			// 是否必选
			dbzjXmJxTaskScoreDetailed.setIsMandatory(zjXmJxTaskScoreDetailed.getIsMandatory());
			// 是否自动评分
			dbzjXmJxTaskScoreDetailed.setIsAutomaticScoring(zjXmJxTaskScoreDetailed.getIsAutomaticScoring());
			// 备注
			dbzjXmJxTaskScoreDetailed.setRemarks(zjXmJxTaskScoreDetailed.getRemarks());
			// 排序
			dbzjXmJxTaskScoreDetailed.setSort(zjXmJxTaskScoreDetailed.getSort());
			// 共通
			dbzjXmJxTaskScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxTaskScoreDetailedMapper.updateByPrimaryKey(dbzjXmJxTaskScoreDetailed);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxTaskScoreDetailed);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxTaskScoreDetailed(
			List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxTaskScoreDetailedList != null && zjXmJxTaskScoreDetailedList.size() > 0) {
			ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed = new ZjXmJxTaskScoreDetailed();
			zjXmJxTaskScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxTaskScoreDetailedMapper.batchDeleteUpdateZjXmJxTaskScoreDetailed(zjXmJxTaskScoreDetailedList,
					zjXmJxTaskScoreDetailed);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxTaskScoreDetailedList);
		}
	}

	@Override
	public ResponseEntity getZjXmJxTaskScoreDetailedListByAuditee(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		if (zjXmJxTaskScoreDetailed == null) {
			zjXmJxTaskScoreDetailed = new ZjXmJxTaskScoreDetailed();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxTaskScoreDetailed.getPage(), zjXmJxTaskScoreDetailed.getLimit());
		// 获取数据
		List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList = zjXmJxTaskScoreDetailedMapper
				.selectByZjXmJxTaskScoreDetailedList(zjXmJxTaskScoreDetailed);
		// 得到分页信息
		PageInfo<ZjXmJxTaskScoreDetailed> p = new PageInfo<>(zjXmJxTaskScoreDetailedList);

		return repEntity.okList(zjXmJxTaskScoreDetailedList, p.getTotal());
	}

	@Override
	public ResponseEntity batchAppealZjXmJxTaskScoreDetailed(
			List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxTaskScoreDetailedList != null && zjXmJxTaskScoreDetailedList.size() > 0) {
			flag = zjXmJxTaskScoreDetailedMapper
					.batchUpdateZjXmJxTaskScoreDetailedOpinionByAuditee(zjXmJxTaskScoreDetailedList);
			// 将申诉记录同步到记录表
			if (flag != 0) {
				List<ZjXmJxTaskScoreDetailedRecord> scoreDetailedRecordList = Lists.newArrayList();
				for (ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed : zjXmJxTaskScoreDetailedList) {
					ZjXmJxTaskScoreDetailedRecord record = new ZjXmJxTaskScoreDetailedRecord();
					record.setRecordId(UuidUtil.generate());
					record.setDetailedId(zjXmJxTaskScoreDetailed.getDetailedId());
					record.setAuditeeKey(zjXmJxTaskScoreDetailed.getAuditeeKey());
					record.setAuditeeName(zjXmJxTaskScoreDetailed.getAuditeeName());
					record.setAuditeeDeptId(zjXmJxTaskScoreDetailed.getAuditeeDeptId());
					record.setAuditeeDeptName(zjXmJxTaskScoreDetailed.getAuditeeDeptName());
					record.setAuditeeProId(zjXmJxTaskScoreDetailed.getAuditeeProId());
					record.setAuditeeProName(zjXmJxTaskScoreDetailed.getAuditeeProName());
					record.setHrUserKey(zjXmJxTaskScoreDetailed.getHrUserKey());
					record.setHrName(zjXmJxTaskScoreDetailed.getHrName());
					record.setAppealStatus("1");
					record.setAppealOpinion(zjXmJxTaskScoreDetailed.getAppealOpinion());
					record.setCreateUserInfo(userKey, realName);
					scoreDetailedRecordList.add(record);
				}
				flag = zjXmJxTaskScoreDetailedRecordMapper
						.batchInsertZjXmJxTaskScoreDetailedRecord(scoreDetailedRecordList);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxTaskScoreDetailedList);
		}
	}

	@Override
	public ResponseEntity tempOrConfirmZjXmJxTaskScoreDetailedByAuditee(
			ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 修改被考核人得分表中审核人字段以及确认状态，审核状态字段
		ZjXmJxAssessmentUserScore dbUserScore = zjXmJxAssessmentUserScoreMapper
				.selectByPrimaryKey(zjXmJxTaskScoreDetailed.getScoreId());
		// check已经审核中的不能再提交审核
		if (dbUserScore != null && (StrUtil.equals("2", dbUserScore.getAuditStatus())
				|| StrUtil.equals("3", dbUserScore.getAuditStatus())
				|| StrUtil.equals("4", dbUserScore.getAuditStatus()))) {
			return repEntity.layerMessage("no", "审核中的月度考核不可操作。");
		}
		// 暂存
		if (StrUtil.equals("1", zjXmJxTaskScoreDetailed.getAuditStatus()) && dbUserScore != null) {
			dbUserScore.setAuditStatus("1");
			flag = zjXmJxAssessmentUserScoreMapper.updateByPrimaryKey(dbUserScore);
			// 批量修改得分明细中完成情况字段
			if (flag != 0 && zjXmJxTaskScoreDetailed.getScoreDetailedList() != null
					&& zjXmJxTaskScoreDetailed.getScoreDetailedList().size() > 0) {
				flag = zjXmJxTaskScoreDetailedMapper
						.batchUpdateZjXmJxTaskScoreDetailedCompletion(zjXmJxTaskScoreDetailed.getScoreDetailedList());
			}
		}
		// 确认(提交审核)
		else if (StrUtil.equals("2", zjXmJxTaskScoreDetailed.getAuditStatus()) && dbUserScore != null) {
			dbUserScore.setConfirmStatus("1");
			dbUserScore.setAuditStatus("2");
			flag = zjXmJxAssessmentUserScoreMapper.updateByPrimaryKey(dbUserScore);
			// 批量修改得分明细中完成情况字段
			if (flag != 0 && zjXmJxTaskScoreDetailed.getScoreDetailedList() != null
					&& zjXmJxTaskScoreDetailed.getScoreDetailedList().size() > 0) {
				flag = zjXmJxTaskScoreDetailedMapper
						.batchUpdateZjXmJxTaskScoreDetailedCompletion(zjXmJxTaskScoreDetailed.getScoreDetailedList());
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxTaskScoreDetailed);
		}
	}

	@Override
	public ResponseEntity getZjXmJxTaskScoreDetailedListByHR(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		if (zjXmJxTaskScoreDetailed == null) {
			zjXmJxTaskScoreDetailed = new ZjXmJxTaskScoreDetailed();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxTaskScoreDetailed.getPage(), zjXmJxTaskScoreDetailed.getLimit());
		// 获取数据
		List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList = zjXmJxTaskScoreDetailedMapper
				.getZjXmJxTaskScoreDetailedListByHR(zjXmJxTaskScoreDetailed);
		// 得到分页信息
		PageInfo<ZjXmJxTaskScoreDetailed> p = new PageInfo<>(zjXmJxTaskScoreDetailedList);

		return repEntity.okList(zjXmJxTaskScoreDetailedList, p.getTotal());
	}

	@Override
	public ResponseEntity rejectOrConfirmZjXmJxTaskScoreDetailedByHR(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxTaskScoreDetailed dbzjXmJxTaskScoreDetailed = zjXmJxTaskScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxTaskScoreDetailed.getDetailedId());
		if (dbzjXmJxTaskScoreDetailed != null) {
			dbzjXmJxTaskScoreDetailed.setScore(zjXmJxTaskScoreDetailed.getScore());
			dbzjXmJxTaskScoreDetailed.setHrOpinion(zjXmJxTaskScoreDetailed.getHrOpinion());
			dbzjXmJxTaskScoreDetailed.setHrUserKey(userKey);
			dbzjXmJxTaskScoreDetailed.setHrName(realName);
			// 审核驳回或者通过
			dbzjXmJxTaskScoreDetailed.setAppealStatus(zjXmJxTaskScoreDetailed.getAppealStatus());
			// 共通
			dbzjXmJxTaskScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxTaskScoreDetailedMapper.updateByPrimaryKey(dbzjXmJxTaskScoreDetailed);
			if (flag != 0) {
				// 将数据插入记录表
				ZjXmJxTaskScoreDetailedRecord record = new ZjXmJxTaskScoreDetailedRecord();
				record.setRecordId(UuidUtil.generate());
				record.setDetailedId(dbzjXmJxTaskScoreDetailed.getDetailedId());
				record.setAuditeeKey(dbzjXmJxTaskScoreDetailed.getAuditeeKey());
				record.setAuditeeName(dbzjXmJxTaskScoreDetailed.getAuditeeName());
				record.setAuditeeDeptId(dbzjXmJxTaskScoreDetailed.getAuditeeDeptId());
				record.setAuditeeDeptName(dbzjXmJxTaskScoreDetailed.getAuditeeDeptName());
				record.setAuditeeProId(dbzjXmJxTaskScoreDetailed.getAuditeeProId());
				record.setAuditeeProName(dbzjXmJxTaskScoreDetailed.getAuditeeProName());
				record.setHrUserKey(userKey);
				record.setHrName(realName);
				record.setAppealStatus(zjXmJxTaskScoreDetailed.getAppealStatus());
				record.setAppealOpinion(dbzjXmJxTaskScoreDetailed.getAppealOpinion());
				record.setHrOpinion(zjXmJxTaskScoreDetailed.getHrOpinion());
				record.setCreateUserInfo(userKey, realName);
				flag = zjXmJxTaskScoreDetailedRecordMapper.insert(record);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxTaskScoreDetailed);
		}
	}

	@Override
	public ResponseEntity appealZjXmJxTaskScoreDetailedByAuditee(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxTaskScoreDetailed dbzjXmJxTaskScoreDetailed = zjXmJxTaskScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxTaskScoreDetailed.getDetailedId());
		if (dbzjXmJxTaskScoreDetailed != null) {
			if (StrUtil.equals("1", dbzjXmJxTaskScoreDetailed.getAppealStatus())) {
				return repEntity.layerMessage("no", "该条明细正在申诉中,请勿重复申诉。");
			}
			// 申诉中
			dbzjXmJxTaskScoreDetailed.setAppealStatus("1");
			dbzjXmJxTaskScoreDetailed.setAppealOpinion(zjXmJxTaskScoreDetailed.getAppealOpinion());
			dbzjXmJxTaskScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxTaskScoreDetailedMapper.updateByPrimaryKey(dbzjXmJxTaskScoreDetailed);
			// 将申诉记录同步到记录表
			if (flag != 0) {
				ZjXmJxTaskScoreDetailedRecord record = new ZjXmJxTaskScoreDetailedRecord();
				record.setRecordId(UuidUtil.generate());
				record.setDetailedId(dbzjXmJxTaskScoreDetailed.getDetailedId());
				record.setAuditeeKey(dbzjXmJxTaskScoreDetailed.getAuditeeKey());
				record.setAuditeeName(dbzjXmJxTaskScoreDetailed.getAuditeeName());
				record.setAuditeeDeptId(dbzjXmJxTaskScoreDetailed.getAuditeeDeptId());
				record.setAuditeeDeptName(dbzjXmJxTaskScoreDetailed.getAuditeeDeptName());
				record.setAuditeeProId(dbzjXmJxTaskScoreDetailed.getAuditeeProId());
				record.setAuditeeProName(dbzjXmJxTaskScoreDetailed.getAuditeeProName());
				record.setHrUserKey(dbzjXmJxTaskScoreDetailed.getHrUserKey());
				record.setHrName(dbzjXmJxTaskScoreDetailed.getHrName());
				record.setAppealStatus("1");
				record.setAppealOpinion(zjXmJxTaskScoreDetailed.getAppealOpinion());
				// record.setHrOpinion("");
				record.setCreateUserInfo(userKey, realName);
				flag = zjXmJxTaskScoreDetailedRecordMapper.insert(record);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("申诉提交成功。");
		}
	}

	@Override
	public ResponseEntity rejectOrSubmitZjXmJxTaskScoreDetailedByLeader(
			ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 修改被考核人得分表中得分、审核状态、审核时间字段
		ZjXmJxAssessmentUserScore dbUserScore = zjXmJxAssessmentUserScoreMapper
				.selectByPrimaryKey(zjXmJxTaskScoreDetailed.getScoreId());
		// check已经驳回的不能重复驳回，已经提交的不能再操作
		if (dbUserScore != null && StrUtil.equals("4", dbUserScore.getAuditStatus())) {
			return repEntity.layerMessage("no", "已评分的任务考核不能再次提交。");
		}
		if (dbUserScore != null && StrUtil.equals("5", dbUserScore.getAuditStatus())
				&& StrUtil.equals("5", zjXmJxTaskScoreDetailed.getAuditStatus())) {
			return repEntity.layerMessage("no", "该任务考核不能重复驳回。");
		}
		// 批量修改得分明细中分数和领导意见字段
		if (dbUserScore != null && zjXmJxTaskScoreDetailed.getScoreDetailedList() != null
				&& zjXmJxTaskScoreDetailed.getScoreDetailedList().size() > 0) {
			flag = zjXmJxTaskScoreDetailedMapper
					.batchUpdateZjXmJxTaskScoreDetailedByLeader(zjXmJxTaskScoreDetailed.getScoreDetailedList());
			// 如果是提交则算出总分
			if (StrUtil.equals("4", zjXmJxTaskScoreDetailed.getAuditStatus())) {
				// 算出总分，修改得分表
				BigDecimal sum = BigDecimal.ZERO;
				for (ZjXmJxTaskScoreDetailed score : zjXmJxTaskScoreDetailed.getScoreDetailedList()) {
					BigDecimal product = CalcUtils.calcMultiply(score.getScore(),
							new BigDecimal(score.getWeightValue()));
					sum = CalcUtils.calcAdd(sum, product);
				}
				BigDecimal totalScore = CalcUtils.calcDivide(sum, new BigDecimal(100), 2);
				dbUserScore.setScore(totalScore);
				dbUserScore.setAuditStatus("4");
				dbUserScore.setAuditTime(DateUtil.date());
			}
			// 如果是驳回则不算总分
			else if (StrUtil.equals("5", zjXmJxTaskScoreDetailed.getAuditStatus())) {
				dbUserScore.setAuditStatus("5");
			}
			dbUserScore.setModifyUserInfo(userKey, realName);
			flag = zjXmJxAssessmentUserScoreMapper.updateByPrimaryKey(dbUserScore);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxTaskScoreDetailed);
		}
	}

	@Override
	public ResponseEntity getZjXmJxTaskScoreDetailedTaskExcel(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed) {
		if (zjXmJxTaskScoreDetailed == null) {
			zjXmJxTaskScoreDetailed = new ZjXmJxTaskScoreDetailed();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxTaskScoreDetailed.getPage(), zjXmJxTaskScoreDetailed.getLimit());
		// 根据项目id、月份、考核id获取数据
		List<ZjXmJxTaskScoreDetailed> zjXmJxTaskScoreDetailedList = zjXmJxTaskScoreDetailedMapper
				.getZjXmJxTaskScoreDetailedTaskExcel(zjXmJxTaskScoreDetailed);
		// 得到分页信息
		PageInfo<ZjXmJxTaskScoreDetailed> p = new PageInfo<>(zjXmJxTaskScoreDetailedList);
		return repEntity.okList(zjXmJxTaskScoreDetailedList, p.getTotal());
	}

	@Override
	public void exportZjXmJxTaskScoreDetailedTaskExcel(ZjXmJxTaskScoreDetailed zjXmJxTaskScoreDetailed,
			HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxTaskScoreDetailed == null) {
			zjXmJxTaskScoreDetailed = new ZjXmJxTaskScoreDetailed();
		}
		// 获取数据 项目id 月份 月度考核id
		List<ZjXmJxTaskScoreDetailed> dataList = zjXmJxTaskScoreDetailedMapper
				.getZjXmJxTaskScoreDetailedTaskExcel(zjXmJxTaskScoreDetailed);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("姓名", "身份证号码", "指标名称", "权重", "得分", "任务业绩总得分");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		// List<List<?>> rows = CollUtil.newArrayList(rowsList);
		// 通过工具类创建writer，创建xlsx格式
		ExcelWriter writer = ExcelUtil.getWriter(true);
		if (dataList != null && dataList.size() > 0) {
			String name = "";
			int firstRow = 2;
			int lastRow = 0;
			for (ZjXmJxTaskScoreDetailed dbData : dataList) {
				lastRow++;
				if (!StrUtil.equals(name, dbData.getAuditeeName())) {
					if (StrUtil.isNotEmpty(name) && firstRow != lastRow) {
						// 2:7
						writer.merge(firstRow, lastRow, 5, 5, dbData.getTaskScore(), false);
						// writer.merge(firstRow, lastRow, firstColumn, lastColumn, content,
						// isSetHeaderStyle)
						firstRow = lastRow + 1;
					}
					name = dbData.getAuditeeName();
				}
				// 工程类型以及班组类型
				rowsList.add(CollUtil.newArrayList(dbData.getAuditeeName(), dbData.getIdNumber(),
						dbData.getCostDutyIndex(), dbData.getWeightValue(), dbData.getScore(), dbData.getTaskScore()));
			}
			// 合并最后一项
			writer.merge(firstRow, dataList.size() + 1, 5, 5, dataList.get(dataList.size() - 1).getTaskScore(), false);
		}
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
					"attachment; filename=\"" + new String("任务业绩得分明细表".getBytes("utf-8"), "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			writer.merge(row.size() - 1, "任务业绩得分明细表");
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
