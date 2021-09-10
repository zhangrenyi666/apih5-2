package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxAssessmentUserScoreMapper;
import com.apih5.mybatis.dao.ZjXmJxPeripheryScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxPeripheryScoreRuleMapper;
import com.apih5.mybatis.dao.ZjXmJxPrincipalScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxSystemAutoScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxTaskScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxWeightManagementMapper;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreRule;
import com.apih5.mybatis.pojo.ZjXmJxPrincipalScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxSystemAutoScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxWeightManagement;
import com.apih5.service.ZjXmJxAssessmentUserScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

@Service("zjXmJxAssessmentUserScoreService")
public class ZjXmJxAssessmentUserScoreServiceImpl implements ZjXmJxAssessmentUserScoreService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreMapper zjXmJxAssessmentUserScoreMapper;
	@Autowired(required = true)
	private ZjXmJxWeightManagementMapper zjXmJxWeightManagementMapper;
	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private ZjXmJxTaskScoreDetailedMapper zjXmJxTaskScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxSystemAutoScoreDetailedMapper zjXmJxSystemAutoScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxPeripheryScoreDetailedMapper zjXmJxPeripheryScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxPrincipalScoreDetailedMapper zjXmJxPrincipalScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxPeripheryScoreRuleMapper zjXmJxPeripheryScoreRuleMapper;

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreListByCondition(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxAssessmentUserScore.getPage(), zjXmJxAssessmentUserScore.getLimit());
		// 获取数据
		List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList = zjXmJxAssessmentUserScoreMapper
				.selectByZjXmJxAssessmentUserScoreList(zjXmJxAssessmentUserScore);
		// 得到分页信息
		PageInfo<ZjXmJxAssessmentUserScore> p = new PageInfo<>(zjXmJxAssessmentUserScoreList);

		return repEntity.okList(zjXmJxAssessmentUserScoreList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreDetails(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		// 获取数据
		ZjXmJxAssessmentUserScore dbZjXmJxAssessmentUserScore = zjXmJxAssessmentUserScoreMapper
				.selectByPrimaryKey(zjXmJxAssessmentUserScore.getScoreId());
		// 数据存在
		if (dbZjXmJxAssessmentUserScore != null) {
			return repEntity.ok(dbZjXmJxAssessmentUserScore);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxAssessmentUserScore(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxAssessmentUserScore.setScoreId(UuidUtil.generate());
		zjXmJxAssessmentUserScore.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxAssessmentUserScoreMapper.insert(zjXmJxAssessmentUserScore);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxAssessmentUserScore);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxAssessmentUserScore(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxAssessmentUserScore dbzjXmJxAssessmentUserScore = zjXmJxAssessmentUserScoreMapper
				.selectByPrimaryKey(zjXmJxAssessmentUserScore.getScoreId());
		if (dbzjXmJxAssessmentUserScore != null && StrUtil.isNotEmpty(dbzjXmJxAssessmentUserScore.getScoreId())) {
			// 项目月度考核主键
			dbzjXmJxAssessmentUserScore.setAssessmentId(zjXmJxAssessmentUserScore.getAssessmentId());
			// 被审核人员主键
			dbzjXmJxAssessmentUserScore.setAuditeeKey(zjXmJxAssessmentUserScore.getAuditeeKey());
			// 被审核人员姓名
			dbzjXmJxAssessmentUserScore.setAuditeeName(zjXmJxAssessmentUserScore.getAuditeeName());
			// 被审核者部门主键
			dbzjXmJxAssessmentUserScore.setAuditeeDeptId(zjXmJxAssessmentUserScore.getAuditeeDeptId());
			// 被审核者部门名称
			dbzjXmJxAssessmentUserScore.setAuditeeDeptName(zjXmJxAssessmentUserScore.getAuditeeDeptName());
			// 被审核者项目id
			dbzjXmJxAssessmentUserScore.setAuditeeProId(zjXmJxAssessmentUserScore.getAuditeeProId());
			// 被审核者项目名称
			dbzjXmJxAssessmentUserScore.setAuditeeProName(zjXmJxAssessmentUserScore.getAuditeeProName());
			// 考核类型
			dbzjXmJxAssessmentUserScore.setAssessmentType(zjXmJxAssessmentUserScore.getAssessmentType());
			// 确认状态
			dbzjXmJxAssessmentUserScore.setConfirmStatus(zjXmJxAssessmentUserScore.getConfirmStatus());
			// 被通知时间
			dbzjXmJxAssessmentUserScore.setNotifiedTime(zjXmJxAssessmentUserScore.getNotifiedTime());
			// 得分
			dbzjXmJxAssessmentUserScore.setScore(zjXmJxAssessmentUserScore.getScore());
			// 审核者主键
			dbzjXmJxAssessmentUserScore.setReviewerKey(zjXmJxAssessmentUserScore.getReviewerKey());
			// 审核者姓名
			dbzjXmJxAssessmentUserScore.setReviewerName(zjXmJxAssessmentUserScore.getReviewerName());
			// 审核时间
			dbzjXmJxAssessmentUserScore.setAuditTime(zjXmJxAssessmentUserScore.getAuditTime());
			// 审核状态
			dbzjXmJxAssessmentUserScore.setAuditStatus(zjXmJxAssessmentUserScore.getAuditStatus());
			// 备注
			dbzjXmJxAssessmentUserScore.setRemarks(zjXmJxAssessmentUserScore.getRemarks());
			// 排序
			dbzjXmJxAssessmentUserScore.setSort(zjXmJxAssessmentUserScore.getSort());
			// 共通
			dbzjXmJxAssessmentUserScore.setModifyUserInfo(userKey, realName);
			flag = zjXmJxAssessmentUserScoreMapper.updateByPrimaryKey(dbzjXmJxAssessmentUserScore);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxAssessmentUserScore);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxAssessmentUserScore(
			List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxAssessmentUserScoreList != null && zjXmJxAssessmentUserScoreList.size() > 0) {
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
			zjXmJxAssessmentUserScore.setModifyUserInfo(userKey, realName);
			flag = zjXmJxAssessmentUserScoreMapper.batchDeleteUpdateZjXmJxAssessmentUserScore(
					zjXmJxAssessmentUserScoreList, zjXmJxAssessmentUserScore);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxAssessmentUserScoreList);
		}
	}

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreListByTaskAuditee(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		zjXmJxAssessmentUserScore.setAuditeeKey(userKey);
		// 任务考核类型
		zjXmJxAssessmentUserScore.setAssessmentType("0");
		// 分页查询
		PageHelper.startPage(zjXmJxAssessmentUserScore.getPage(), zjXmJxAssessmentUserScore.getLimit());
		// 获取数据
		List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreListByUser(zjXmJxAssessmentUserScore);
		// 得到分页信息
		PageInfo<ZjXmJxAssessmentUserScore> p = new PageInfo<>(zjXmJxAssessmentUserScoreList);

		return repEntity.okList(zjXmJxAssessmentUserScoreList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreListByReviewer(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		zjXmJxAssessmentUserScore.setReviewerKey(userKey);
		// 分页查询
		PageHelper.startPage(zjXmJxAssessmentUserScore.getPage(), zjXmJxAssessmentUserScore.getLimit());
		// 获取数据
		List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreListByReviewer(zjXmJxAssessmentUserScore);
		// 得到分页信息
		PageInfo<ZjXmJxAssessmentUserScore> p = new PageInfo<>(zjXmJxAssessmentUserScoreList);
		return repEntity.okList(zjXmJxAssessmentUserScoreList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreListByTaskReviewer(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		zjXmJxAssessmentUserScore.setReviewerKey(userKey);
		// 任务考核类型
		zjXmJxAssessmentUserScore.setAssessmentType("0");
		// 分页查询
		PageHelper.startPage(zjXmJxAssessmentUserScore.getPage(), zjXmJxAssessmentUserScore.getLimit());
		// 获取数据
		List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreListByUser(zjXmJxAssessmentUserScore);
		// 得到分页信息
		PageInfo<ZjXmJxAssessmentUserScore> p = new PageInfo<>(zjXmJxAssessmentUserScoreList);

		return repEntity.okList(zjXmJxAssessmentUserScoreList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreListByPrincipalReviewer(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		zjXmJxAssessmentUserScore.setReviewerKey(userKey);
		// 正职考核类型
		zjXmJxAssessmentUserScore.setAssessmentType("2");
		PageHelper.startPage(zjXmJxAssessmentUserScore.getPage(), zjXmJxAssessmentUserScore.getLimit());
		// 获取数据
		List<ZjXmJxAssessmentUserScore> auditeeList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreListByUser(zjXmJxAssessmentUserScore);
		JSONArray userArray = JSONUtil.createArray();
		if (auditeeList.size() > 0) {
			for (ZjXmJxAssessmentUserScore auditee : auditeeList) {
				JSONObject jsonObj = JSONUtil.createObj();
				jsonObj.put("value", auditee.getAuditeeKey());
				jsonObj.put("label", auditee.getAuditeeName());
				jsonObj.put("score", auditee.getScore());
				jsonObj.put("principalScore", auditee.getPrincipalScore());
				jsonObj.put("secretaryScore", auditee.getSecretaryScore());
				jsonObj.put("valuePid", auditee.getAuditeeDeptId());
				userArray.add(jsonObj);
			}
		}
		return repEntity.ok(userArray);
	}

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreListByCompositeReviewer(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		zjXmJxAssessmentUserScore.setReviewerKey(userKey);
		// 综合评分类型
		zjXmJxAssessmentUserScore.setAssessmentType("3");
		// 分页查询
		PageHelper.startPage(zjXmJxAssessmentUserScore.getPage(), zjXmJxAssessmentUserScore.getLimit());
		// 获取数据
		List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreListByUser(zjXmJxAssessmentUserScore);
		if (zjXmJxAssessmentUserScoreList.size() > 0) {
			for (ZjXmJxAssessmentUserScore dbUserScore : zjXmJxAssessmentUserScoreList) {
				JSONArray userArray = JSONUtil.createArray();
				JSONObject jsonObj = JSONUtil.createObj();
				jsonObj.set("value", dbUserScore.getAuditeeKey());
				jsonObj.set("label", dbUserScore.getAuditeeName());
				jsonObj.set("valuePid", dbUserScore.getAuditeeDeptId());
				userArray.add(jsonObj);
				dbUserScore.setUserArray(userArray);
//				JSONArray scoreArray = JSONUtil.createArray();
//				scoreArray.add(dbUserScore.getScore());
//				dbUserScore.setScoreArray(scoreArray);
				JSONArray deductReasonArray = JSONUtil.createArray();
				deductReasonArray.add(dbUserScore.getDeductReason());
				dbUserScore.setDeductReasonArray(deductReasonArray);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmJxAssessmentUserScore> p = new PageInfo<>(zjXmJxAssessmentUserScoreList);
		return repEntity.okList(zjXmJxAssessmentUserScoreList, p.getTotal());
	}

	@Override
	public ResponseEntity batchSubmitZjXmJxAssessmentUserScoreBySecretary(
			List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxAssessmentUserScoreList != null && zjXmJxAssessmentUserScoreList.size() > 0) {
			// check当前登录者(书记)是否已经提交过扣分,确认提交过的不能再次提交
			ZjXmJxAssessmentUserScore check = new ZjXmJxAssessmentUserScore();
			check.setReviewerKey(userKey);// 打分者只有书记所以可不加此参数
			check.setAssessmentId(zjXmJxAssessmentUserScoreList.get(0).getAssessmentId());
			int count = zjXmJxAssessmentUserScoreMapper.countZjXmJxAssessmentUserScoreList(check);
			if (count > 0) {
				return repEntity.layerMessage("no", "您已提交过综合评分,请勿重复提交。");
			}
			// check被提交者不能重复Map
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore : zjXmJxAssessmentUserScoreList) {
				// check被考核者key和得分不能为空
//				if (zjXmJxAssessmentUserScore == null || zjXmJxAssessmentUserScore.getUserArray() == null
//						|| zjXmJxAssessmentUserScore.getScoreArray() == null) {
				if (zjXmJxAssessmentUserScore == null || zjXmJxAssessmentUserScore.getUserArray() == null
						|| zjXmJxAssessmentUserScore.getScore() == null) {
					return repEntity.layerMessage("no", "被考核者姓名或得分不能为空。");
				}
				String auditeeKey = zjXmJxAssessmentUserScore.getUserArray().getJSONObject(0).getStr("value");
				String auditeeName = zjXmJxAssessmentUserScore.getUserArray().getJSONObject(0).getStr("label");
//				BigDecimal score = zjXmJxAssessmentUserScore.getScoreArray().getBigDecimal(0);
				BigDecimal score = zjXmJxAssessmentUserScore.getScore();
				// check提交人员不能重复
				if (map.containsKey(auditeeKey)) {
					return repEntity.layerMessage("no", "被考核者不能重复,请检查后再试。");
				} else {
					map.put(auditeeKey, 1);
				}
				zjXmJxAssessmentUserScore.setScoreId(UuidUtil.generate());
				// zjXmJxAssessmentUserScore.setAssessmentId();
				zjXmJxAssessmentUserScore.setAuditeeKey(auditeeKey);
				zjXmJxAssessmentUserScore.setAuditeeName(auditeeName);
				SysUser dbUser = userService.getSysUserByUserKey(auditeeKey);
				zjXmJxAssessmentUserScore.setAuditeeIdNumber(dbUser.getIdentityCard());
				zjXmJxAssessmentUserScore.setAuditeePosition(dbUser.getJobType());
				zjXmJxAssessmentUserScore.setAuditeeRankType(dbUser.getExt5());
				zjXmJxAssessmentUserScore.setAuditeeLastType(dbUser.getExt6());
				zjXmJxAssessmentUserScore.setAuditeeUserType(dbUser.getExt9());
				zjXmJxAssessmentUserScore.setAuditeeType(dbUser != null ? dbUser.getExt2() : "");
				// zjXmJxAssessmentUserScore.setAuditeeDeptId();
				// zjXmJxAssessmentUserScore.setAuditeeDeptName();
				// zjXmJxAssessmentUserScore.setAuditeeProId();
				// zjXmJxAssessmentUserScore.setAuditeeProName();
				zjXmJxAssessmentUserScore.setAssessmentType("3");
				zjXmJxAssessmentUserScore.setConfirmStatus("0");
				// 调用接口获取经理或者书记得userKey及name
				zjXmJxAssessmentUserScore.setReviewerKey(userKey);
				zjXmJxAssessmentUserScore.setReviewerName(realName);
				zjXmJxAssessmentUserScore.setScore(score);
				// 0:未发起 1:被考核人暂存 2:审核中 3:审核者暂存 4:已审核 5:已驳回
				// zjXmJxAssessmentUserScore.setAuditStatus(zjXmJxAssessmentUserScore.getAuditStatus());
				zjXmJxAssessmentUserScore.setAuditTime(DateUtil.date());
				if (zjXmJxAssessmentUserScore.getDeductReasonArray() != null
						&& zjXmJxAssessmentUserScore.getDeductReasonArray().size() > 0) {
					zjXmJxAssessmentUserScore
							.setDeductReason(zjXmJxAssessmentUserScore.getDeductReasonArray().getStr(0));
				}
				zjXmJxAssessmentUserScore.setCreateUserInfo(userKey, realName);
			}
			// 先删除再新增
			ZjXmJxAssessmentUserScore delete = new ZjXmJxAssessmentUserScore();
			delete.setReviewerKey(userKey);// 打分者只有书记所以可不加此参数
			delete.setAssessmentId(zjXmJxAssessmentUserScoreList.get(0).getAssessmentId());
			delete.setAssessmentType("3");
			zjXmJxAssessmentUserScoreMapper.deleteZjXmJxAssessmentUserScoreByCondition(delete);
			flag = zjXmJxAssessmentUserScoreMapper.batchInsertZjXmJxAssessmentUserScore(zjXmJxAssessmentUserScoreList);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxAssessmentUserScoreList);
		}
	}

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreListByAuditee(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		zjXmJxAssessmentUserScore.setAuditeeKey(userKey);
		// 分页查询
		PageHelper.startPage(zjXmJxAssessmentUserScore.getPage(), zjXmJxAssessmentUserScore.getLimit());
		// 获取数据
		List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreListByAuditee(zjXmJxAssessmentUserScore);
		if (zjXmJxAssessmentUserScoreList.size() > 0) {
			for (ZjXmJxAssessmentUserScore userScore : zjXmJxAssessmentUserScoreList) {
				userScore.setCompositeTotalScore(
						CalcUtils.calcSubtract(BigDecimal.ZERO, userScore.getCompositeTotalScore()));
				userScore.setSystemTotalScore(CalcUtils.calcSubtract(BigDecimal.ZERO, userScore.getSystemTotalScore()));
				// 获取当前年月的总权重
				ZjXmJxWeightManagement zjXmJxWeightManagement = new ZjXmJxWeightManagement();
				zjXmJxWeightManagement.setYearMonth(userScore.getYearMonth());
				List<ZjXmJxWeightManagement> list = zjXmJxWeightManagementMapper
						.selectByZjXmJxWeightManagementList(zjXmJxWeightManagement);
				int taskWeight = new Integer(50);
				int peripheryWeight = new Integer(20);
				int principalWeight = new Integer(30);
				if (list.size() == 1) {
					taskWeight = list.get(0).getTaskWeight();
					peripheryWeight = list.get(0).getPeripheryWeight();
					principalWeight = list.get(0).getPrincipalWeight();
				}
				BigDecimal product1 = CalcUtils.calcMultiply(userScore.getTaskTotalScore(), new BigDecimal(taskWeight));
				BigDecimal product2 = CalcUtils.calcMultiply(userScore.getPeripheryTotalScore(),
						new BigDecimal(peripheryWeight));
				BigDecimal product3 = CalcUtils.calcMultiply(userScore.getPrincipalTotalScore(),
						new BigDecimal(principalWeight));
				BigDecimal sum = CalcUtils.calcAdd(CalcUtils.calcAdd(product1, product2), product3);
				BigDecimal totalScore = CalcUtils.calcAdd(CalcUtils
						.calcAdd(CalcUtils.calcDivide(sum, new BigDecimal(100), 2), userScore.getCompositeTotalScore()),
						userScore.getSystemTotalScore());
				userScore.setTotalScore(totalScore);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmJxAssessmentUserScore> p = new PageInfo<>(zjXmJxAssessmentUserScoreList);
		return repEntity.okList(zjXmJxAssessmentUserScoreList, p.getTotal());
	}

	@Override
	public ResponseEntity timingToConfirmZjXmJxAssessmentUserScore(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore)
			throws Exception {
		// 直接更改通知超过3天(72小时)的任务考核的确认状态为2:自动确认
		ZjXmJxAssessmentUserScore job = new ZjXmJxAssessmentUserScore();
		if (zjXmJxAssessmentUserScore.getStartValue() != null) {
			job.setStartValue(zjXmJxAssessmentUserScore.getStartValue());
		} else {
			job.setStartValue(4320);
		}
		int flag = zjXmJxAssessmentUserScoreMapper.updateZjXmJxAssessmentUserScoreForConfirmStatus(job);
		return repEntity.ok("定时任务执行成功!");
	}

	@Override
	public ResponseEntity timingToSendMessageZjXmJxAssessmentUserScore(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) throws Exception {
		// 发送通知后3天~6天催打分者打分(72~144小时)
		// 根据时间段(72~144小时)获取所有需要发送消息的人员信息
		// 分三个类型进行发送消息(同一月度考核的任务考核、周边考核、正职评分)
		ZjXmJxAssessmentUserScore job = new ZjXmJxAssessmentUserScore();
		job.setAssessmentType("0");
		if (zjXmJxAssessmentUserScore.getStartValue() != null) {
			job.setStartValue(zjXmJxAssessmentUserScore.getStartValue());
		} else {
			job.setStartValue(4320);
		}
		if (zjXmJxAssessmentUserScore.getEndValue() != null) {
			job.setEndValue(zjXmJxAssessmentUserScore.getEndValue());
		} else {
			job.setEndValue(7200);
		}
		List<ZjXmJxAssessmentUserScore> renwuList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreReviewerByTime(job);
		// 1、发送任务考核消息(每个月度考核每个打分者只发一条消息)
		if (renwuList.size() > 0) {
			// java8特性根据多个字段快速去重
			List<ZjXmJxAssessmentUserScore> distinctClass = renwuList.stream()
					.collect(Collectors.collectingAndThen(
							Collectors.toCollection(() -> new TreeSet<>(
									Comparator.comparing(o -> o.getAssessmentId() + ";" + o.getReviewerKey()))),
							ArrayList::new));
			for (ZjXmJxAssessmentUserScore message : distinctClass) {
				// 您未确认或完成7月份【任务考核】内容，请尽快处理！
				System.out.println(message.getReviewerKey() + "您未确认或完成"
						+ DateUtil.format(message.getYearMonth(), "yyyy年MM月") + "【任务考核】内容，请尽快处理！");
			}
		}
		// 2、发送周边考核消息(每个月度考核每个打分者只发送一条消息)
		ZjXmJxPeripheryScoreDetailed condition = new ZjXmJxPeripheryScoreDetailed();
		condition.setStartValue(10);
		condition.setEndValue(20);
		List<ZjXmJxPeripheryScoreDetailed> zhoubianList = zjXmJxPeripheryScoreDetailedMapper
				.getZjXmJxPeripheryScoreDetailedReviewerByTime(condition);
		if (zhoubianList.size() > 0) {
			// java8特性根据多个字段快速去重
			List<ZjXmJxPeripheryScoreDetailed> distinctClass = zhoubianList.stream()
					.collect(Collectors.collectingAndThen(
							Collectors.toCollection(() -> new TreeSet<>(
									Comparator.comparing(o -> o.getAssessmentId() + ";" + o.getReviewerKey()))),
							ArrayList::new));
			for (ZjXmJxPeripheryScoreDetailed message : distinctClass) {
				// 您未完成7月份【周边业绩考核】对他人评分，请尽快处理！
				System.out.println(message.getReviewerKey() + "您未完成"
						+ DateUtil.format(message.getYearMonth(), "yyyy年MM月") + "【周边业绩考核】对他人评分，请尽快处理！");
			}
		}
		// 3、发送正职评分消息(每个月度考核每个打分者只发送一条消息)
		ZjXmJxPrincipalScoreDetailed condition2 = new ZjXmJxPrincipalScoreDetailed();
		condition2.setStartValue(10);
		condition2.setEndValue(20);
		List<ZjXmJxPrincipalScoreDetailed> zhengzhiList = zjXmJxPrincipalScoreDetailedMapper
				.getZjXmJxPrincipalScoreDetailedReviewerByTime(condition2);
		if (zhengzhiList.size() > 0) {
			// java8特性根据多个字段快速去重
			List<ZjXmJxPrincipalScoreDetailed> distinctClass = zhengzhiList.stream()
					.collect(Collectors.collectingAndThen(
							Collectors.toCollection(() -> new TreeSet<>(
									Comparator.comparing(o -> o.getAssessmentId() + ";" + o.getReviewerKey()))),
							ArrayList::new));
			for (ZjXmJxPrincipalScoreDetailed message : distinctClass) {
				// 您未完成7月份【正职考核】对他人评分，请尽快处理！
				System.out.println(message.getReviewerKey() + "您未完成"
						+ DateUtil.format(message.getYearMonth(), "yyyy年MM月") + "【正职考核】对他人评分，请尽快处理！");
			}
		}
//			// 遍历集合获取到打分者key的集合
//			List<String> reveiwerKeyList = Lists.newArrayList();
//			for (ZjXmJxAssessmentUserScore dbReveiwer : reviewerList) {
//				if (StrUtil.isNotEmpty(dbReveiwer.getReviewerKey())) {
//					reveiwerKeyList.addAll(Arrays.asList(dbReveiwer.getReviewerKey().split(",")));
//				}
//			}
//			// 去重
//			HashSet<String> hashSet = new HashSet<String>(reveiwerKeyList);
//			reveiwerKeyList.clear();
//			reveiwerKeyList.addAll(hashSet);
		return repEntity.ok("定时任务执行成功!");
	}

	@Override
	public ResponseEntity timingToDealWithTaskZjXmJxAssessmentUserScore(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) throws Exception {
		int flag = 0;
		// 获取超过(144小时)打分者还未进行打分的任务考核
		ZjXmJxAssessmentUserScore job = new ZjXmJxAssessmentUserScore();
		job.setAssessmentType("0");
		if (zjXmJxAssessmentUserScore.getStartValue() != null) {
			job.setStartValue(zjXmJxAssessmentUserScore.getStartValue());
		} else {
			job.setStartValue(8640);
		}
		List<ZjXmJxAssessmentUserScore> dbList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreReviewerByTime2(job);
		if (dbList.size() > 0) {
			// 批量修改修改被考核人得分表中得分为0分、审核状态4、审核时间字段当前时间
			flag = zjXmJxAssessmentUserScoreMapper.batchUpdateZjXmJxAssessmentUserScoreByList(dbList);
			// 根据scoreId批量将所有任务考核明细表打0分领导意见为空
			flag = zjXmJxTaskScoreDetailedMapper.batchUpdateZjXmJxTaskScoreDetailedZero(dbList);
			// 根据 月度考核id和打分者去重
			// (不同的月度考核相同的打分者及是同一个被打分者只扣一个10分)
			// (不同的月度考核相同的打分者及不同的被打分者扣多个10分)
			// java8特性根据多个字段快速去重
			// List<ZjXmJxAssessmentUserScore> distinctList = dbList.stream()
			// .collect(Collectors.collectingAndThen(
			// Collectors.toCollection(() -> new TreeSet<>(
			// Comparator.comparing(o -> o.getAssessmentId() + ";" + o.getReviewerKey()))),
			// ArrayList::new));
			// 批量新增系统自动扣分明细表数据并修改系统扣分总分
			List<ZjXmJxSystemAutoScoreDetailed> systemScoreList = Lists.newArrayList();
			for (ZjXmJxAssessmentUserScore shuji : dbList) {
				// 根据月度考核id和打分者id找到打分者的系统扣分表数据并将扣分值加(-10)
				ZjXmJxAssessmentUserScore update = new ZjXmJxAssessmentUserScore();
				update.setAuditeeKey(shuji.getReviewerKey());
				update.setAssessmentId(shuji.getAssessmentId());
				// 获取打分者被扣分值如果已经达到-100，则不再进行扣分
				update.setAssessmentType("4");
				List<ZjXmJxAssessmentUserScore> scoreList = zjXmJxAssessmentUserScoreMapper
						.selectByZjXmJxAssessmentUserScoreList(update);
				if (scoreList.size() == 1 && scoreList.get(0).getScore().compareTo(new BigDecimal(-100)) > 0) {
					flag = zjXmJxAssessmentUserScoreMapper.updateSystemScoreZjXmJxAssessmentUserScore(update);
					// 整理系统扣分明细表数据
					ZjXmJxSystemAutoScoreDetailed insertData = new ZjXmJxSystemAutoScoreDetailed();
					insertData.setDetailedId(UuidUtil.generate());
					// 获取打分者的系统扣分表数据
					ZjXmJxAssessmentUserScore systemScore = new ZjXmJxAssessmentUserScore();
					systemScore.setAssessmentId(shuji.getAssessmentId());
					systemScore.setAuditeeKey(shuji.getReviewerKey());
					systemScore.setAssessmentType("4");
					List<ZjXmJxAssessmentUserScore> list = zjXmJxAssessmentUserScoreMapper
							.getZjXmJxAssessmentUserScoreListByUser(systemScore);
					insertData.setScoreId(list != null && list.size() == 1 ? list.get(0).getScoreId() : "");
					insertData.setAssessmentId(shuji.getAssessmentId());
					insertData.setAuditeeKey(shuji.getReviewerKey());
					insertData.setAuditeeName(shuji.getReviewerName());
					SysUser dbUser = userService.getSysUserByUserKey(shuji.getReviewerKey());
					insertData.setAuditeeType(dbUser != null ? dbUser.getExt2() : "");
					insertData.setAuditeeDeptId(shuji.getAuditeeDeptId());
					insertData.setAuditeeDeptName(shuji.getAuditeeDeptName());
					insertData.setAuditeeProId(shuji.getAuditeeProId());
					insertData.setAuditeeProName(shuji.getAuditeeProName());
					insertData.setReviewerKey("系统自动扣分");
					insertData.setReviewerName("系统自动扣分");
					insertData.setHrUserKey("系统");
					insertData.setHrName("系统");
					insertData.setScore(new BigDecimal(-10));
					insertData.setReviewerType("系统自动扣分");
					// 0:未发起 1:被考核人暂存 2:审核中 3:审核者暂存 4:已审核 5:已驳回
					insertData.setAuditStatus("4");
					insertData.setReasonSources("任务考核超时未评分" + shuji.getAuditeeName());
					insertData.setDeductionType("任务考核扣分");
					insertData.setCreateUserInfo("打分者超时未打分", "打分者超时未打分");
					systemScoreList.add(insertData);
				}
			}
			flag = zjXmJxSystemAutoScoreDetailedMapper.batchInsertZjXmJxSystemAutoScoreDetailed(systemScoreList);
		}
		return repEntity.ok("定时任务执行成功!");
	}

	@Override
	public ResponseEntity timingToDealWithPeripheryZjXmJxAssessmentUserScore(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) throws Exception {
		int flag = 0;
		// 获取超过(144小时)打分者还未完成打分的周边考核自动算分(没评完也算分)
		ZjXmJxAssessmentUserScore job = new ZjXmJxAssessmentUserScore();
		job.setAssessmentType("1");
		if (zjXmJxAssessmentUserScore.getStartValue() != null) {
			job.setStartValue(zjXmJxAssessmentUserScore.getStartValue());
		} else {
			job.setStartValue(4320);
		}

		List<ZjXmJxAssessmentUserScore> dbList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreReviewerByTime2(job);
		if (dbList.size() > 0) {
			// 循环逐条自动计算总分
			for (ZjXmJxAssessmentUserScore dbScore : dbList) {
				// 核算该被打分者总分
				// 根据scoreId(或者auditeeKey和assessmentId)获取集合
				ZjXmJxPeripheryScoreDetailed scoreDetailed = new ZjXmJxPeripheryScoreDetailed();
				scoreDetailed.setScoreId(dbScore.getScoreId());
				// 不过滤掉未填分数的数据,否则超时后打分者还可以打分
				List<ZjXmJxPeripheryScoreDetailed> list = zjXmJxPeripheryScoreDetailedMapper
						.getZjXmJxPeripheryScoreDetailedListBySystem(scoreDetailed);
				// 将集合分为有分数集合和无分数集合
				List<ZjXmJxPeripheryScoreDetailed> hasScorelist = Lists.newArrayList();
				List<ZjXmJxPeripheryScoreDetailed> noneScorelist = Lists.newArrayList();
				if (list.size() > 0) {
					for (ZjXmJxPeripheryScoreDetailed dbData : list) {
						if (dbData.getScore() != null) {
							hasScorelist.add(dbData);
						} else {
							noneScorelist.add(dbData);
						}
					}
				}
				// 将无分数的批量处理成已审核
				if (noneScorelist.size() > 0) {
					for (ZjXmJxPeripheryScoreDetailed dbData : noneScorelist) {
						// 已审核
						dbData.setAuditStatus("4");
					}
					flag = zjXmJxPeripheryScoreDetailedMapper
							.batchUpdateZjXmJxPeripheryScoreDetailedByReviewer(noneScorelist);
				}
				// 将集合按最大可能性(分数都是从高到低)
				// 分为副职打分集合、部门负责人打分集合、普通员工打分集合
				List<ZjXmJxPeripheryScoreDetailed> lastList = Lists.newArrayList();
				if (hasScorelist.size() > 0) {
					List<ZjXmJxPeripheryScoreDetailed> deputyList = Lists.newArrayList();
					List<ZjXmJxPeripheryScoreDetailed> leaderList = Lists.newArrayList();
					List<ZjXmJxPeripheryScoreDetailed> employeeList = Lists.newArrayList();
					for (ZjXmJxPeripheryScoreDetailed score : hasScorelist) {
						if (StrUtil.equals("0", score.getReviewerType())) {
							deputyList.add(score);
						} else if (StrUtil.equals("1", score.getReviewerType())) {
							leaderList.add(score);
						} else if (StrUtil.equals("2", score.getReviewerType())) {
							employeeList.add(score);
						}
					}
					// 然后获取周边考核生效中的规则,进行去掉最高分和最低分
					ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule = new ZjXmJxPeripheryScoreRule();
					zjXmJxPeripheryScoreRule.setIsInvalid("0");
					List<ZjXmJxPeripheryScoreRule> ruleList = zjXmJxPeripheryScoreRuleMapper
							.selectByZjXmJxPeripheryScoreRuleList(zjXmJxPeripheryScoreRule);
					// 三个集合分别进行去除XX个最高分和最低分
					if (ruleList.size() > 0) {
						deputyList = removeHighestlowest(deputyList, ruleList.get(0).getDeputyDivisor());
						leaderList = removeHighestlowest(leaderList, ruleList.get(0).getLeaderDivisor());
						employeeList = removeHighestlowest(employeeList, ruleList.get(0).getEmployeeDivisor());
					}
					lastList.addAll(deputyList);
					lastList.addAll(leaderList);
					lastList.addAll(employeeList);
				}
				// 然后再整理总分
				BigDecimal totalScore = BigDecimal.ZERO;
				Map<Integer, List<ZjXmJxPeripheryScoreDetailed>> map = new HashMap<Integer, List<ZjXmJxPeripheryScoreDetailed>>();
				if (lastList.size() > 0) {
					for (ZjXmJxPeripheryScoreDetailed score : lastList) {
						if (map.containsKey(score.getWeightValue())) {
							map.get(score.getWeightValue()).add(score);
						} else {
							List<ZjXmJxPeripheryScoreDetailed> tempList = Lists.newArrayList();
							tempList.add(score);
							map.put(score.getWeightValue(), tempList);
						}
					}
					// 遍历map核算总分(相同权重的分值求平均分*权重，最后相加)
					for (Map.Entry<Integer, List<ZjXmJxPeripheryScoreDetailed>> entry : map.entrySet()) {
						BigDecimal score1 = BigDecimal.ZERO;
						for (ZjXmJxPeripheryScoreDetailed score2 : entry.getValue()) {
							score1 = CalcUtils.calcAdd(score1, score2.getScore());
						}
						BigDecimal average = CalcUtils.calcDivide(score1, new BigDecimal(entry.getValue().size()), 6);
						BigDecimal product = CalcUtils.calcMultiply(average, new BigDecimal(entry.getKey()));
						totalScore = CalcUtils.calcAdd(totalScore,
								CalcUtils.calcDivide(product, new BigDecimal(100), 6));
					}
					totalScore = totalScore.setScale(1, RoundingMode.HALF_UP);
				}
				// 找到该被打分者得分表修改总分及审核状态字段
				dbScore.setScore(totalScore);
				dbScore.setAuditStatus("4");
				dbScore.setAuditTime(DateUtil.date());
				dbScore.setModifyUserInfo("系统自动打分", "系统自动打分");
				flag = zjXmJxAssessmentUserScoreMapper.updateByPrimaryKey(dbScore);
			}
		}
		return repEntity.ok("定时任务执行成功!");
	}

	@Override
	public ResponseEntity timingToDealWithPrincipalZjXmJxAssessmentUserScore(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) throws Exception {
		int flag = 0;
		// 获取超过(72小时)打分者还未完成打分的正职评分自动算分(有一个打分算分，一个都没有算零分)
		ZjXmJxAssessmentUserScore job = new ZjXmJxAssessmentUserScore();
		job.setAssessmentType("2");
		if (zjXmJxAssessmentUserScore.getStartValue() != null) {
			job.setStartValue(zjXmJxAssessmentUserScore.getStartValue());
		} else {
			job.setStartValue(4320);
		}

		List<ZjXmJxAssessmentUserScore> dbList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreReviewerByTime2(job);
		if (dbList.size() > 0) {
			// 循环逐条自动计算总分
			for (ZjXmJxAssessmentUserScore dbScore : dbList) {
				// 核算该被打分者总分
				// 根据scoreId(或者auditeeKey和assessmentId)获取集合
				ZjXmJxPrincipalScoreDetailed scoreDetailed = new ZjXmJxPrincipalScoreDetailed();
				scoreDetailed.setScoreId(dbScore.getScoreId());
				// 不过滤掉没填分数的数据,否则打分人超时还能打分
				List<ZjXmJxPrincipalScoreDetailed> list = zjXmJxPrincipalScoreDetailedMapper
						.getZjXmJxPrincipalScoreDetailedListBySystem(scoreDetailed);
				// 整理总分
				BigDecimal totalScore = BigDecimal.ZERO;
				if (list.size() > 0) {
					int divideValue = 0;
					for (ZjXmJxPrincipalScoreDetailed score : list) {
						if (score.getScore() != null) {
							totalScore = CalcUtils.calcAdd(totalScore, score.getScore());
							divideValue++;
						} else {
							// 已审核
							score.setAuditStatus("4");
							zjXmJxPrincipalScoreDetailedMapper.updateByPrimaryKey(score);
						}
					}
					if (divideValue > 0) {
						totalScore = CalcUtils.calcDivide(totalScore, new BigDecimal(divideValue), 1);
					}
				}
				// 找到该被打分者得分表修改总分及审核状态字段
				dbScore.setScore(totalScore);
				dbScore.setAuditStatus("4");
				dbScore.setAuditTime(DateUtil.date());
				dbScore.setModifyUserInfo("系统自动评分", "系统自动评分");
				flag = zjXmJxAssessmentUserScoreMapper.updateByPrimaryKey(dbScore);
			}
		}
		return repEntity.ok("定时任务执行成功!");
	}

	private List removeHighestlowest(List list, Integer divisor) {
		if (list.size() > 2 && divisor > 0) {
			int number = list.size() / divisor;
			// BigDecimal number = new BigDecimal(list.size()).divide(new
			// BigDecimal(divisor), 0, RoundingMode.DOWN);
			if (number > 0) {
				while (number > 0 && list.size() > 2) {
					list.remove(0);
					list.remove(list.size() - 1);
					number--;
				}
			}
		}
		return list;
	}

	@Override
	public ResponseEntity getZjXmJxAssessmentUserScoreCompositeExcel(
			ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore) {
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		// 0:任务考核 1:周边考核 2:项目正职评分 3:综合评分 4:系统自动扣分
		zjXmJxAssessmentUserScore.setAssessmentType("3");
		// 分页查询
		PageHelper.startPage(zjXmJxAssessmentUserScore.getPage(), zjXmJxAssessmentUserScore.getLimit());
		// 获取数据
		List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreListByUser(zjXmJxAssessmentUserScore);
		// 得到分页信息
		PageInfo<ZjXmJxAssessmentUserScore> p = new PageInfo<>(zjXmJxAssessmentUserScoreList);

		return repEntity.okList(zjXmJxAssessmentUserScoreList, p.getTotal());
	}

	@Override
	public void exportZjXmJxAssessmentUserScoreCompositeExcel(ZjXmJxAssessmentUserScore zjXmJxAssessmentUserScore,
			HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxAssessmentUserScore == null) {
			zjXmJxAssessmentUserScore = new ZjXmJxAssessmentUserScore();
		}
		// 获取数据 项目id 月份 月度考核id
		// 0:任务考核 1:周边考核 2:项目正职评分 3:综合评分 4:系统自动扣分
		zjXmJxAssessmentUserScore.setAssessmentType("3");
		// 获取数据
		List<ZjXmJxAssessmentUserScore> dataList = zjXmJxAssessmentUserScoreMapper
				.getZjXmJxAssessmentUserScoreListByUser(zjXmJxAssessmentUserScore);
		// 表头
		List<List<?>> rowsList = Lists.newArrayList();
		List<?> row = CollUtil.newArrayList("姓名", "身份证号码", "扣分分值");
		rowsList.add(row);
		// 数据装载（与上面的表头对应）
		if (dataList != null && dataList.size() > 0) {
			for (ZjXmJxAssessmentUserScore dbData : dataList) {
				rowsList.add(
						CollUtil.newArrayList(dbData.getAuditeeName(), dbData.getAuditeeIdNumber(), dbData.getScore()));
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
					"attachment; filename=\"" + new String("综合评价扣分明细表".getBytes("utf-8"), "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			writer.merge(row.size() - 1, "综合评价扣分明细表");
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

	@Override
	public ResponseEntity cancelZjXmJxTaskScoreDetailedByLeader(
			List<ZjXmJxAssessmentUserScore> zjXmJxAssessmentUserScoreList) {
		// 第一个表、zj_xm_jx_task_score_detailed  
		// 表score分数清空，appeal_status申诉状态置0；
		// 第二个表 zj_xm_jx_assessment_user_score表当assessment_type=0时，
		// 的数据audit_status审核状态置2，分数score清空
		int flag = 0;
		// check如果不是审核完成的数据不能撤销分数
		if (zjXmJxAssessmentUserScoreList != null && zjXmJxAssessmentUserScoreList.size() > 0) {
			for (ZjXmJxAssessmentUserScore dbUserScore : zjXmJxAssessmentUserScoreList) {
				if (!StrUtil.equals("4", dbUserScore.getAuditStatus())) {
					return repEntity.layerMessage("no", "尚未审核完成的评分不能撤销,请重新选择。");
				}
			}
			// 进行撤销操作
			// 批量清空得分表中分数和审核状态
			flag = zjXmJxAssessmentUserScoreMapper
					.batchClearZjXmJxAssessmentUserScoreByLeader(zjXmJxAssessmentUserScoreList);
			// 批量清空得分明细中分数和领导意见字段
			flag = zjXmJxTaskScoreDetailedMapper
					.batchClearZjXmJxTaskScoreDetailedByLeader(zjXmJxAssessmentUserScoreList);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxAssessmentUserScoreList);
		}
	}
}
