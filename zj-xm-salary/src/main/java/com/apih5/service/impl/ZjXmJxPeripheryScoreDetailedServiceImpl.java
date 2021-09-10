package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxAssessmentUserScoreMapper;
import com.apih5.mybatis.dao.ZjXmJxPeripheryScoreDetailedMapper;
import com.apih5.mybatis.dao.ZjXmJxPeripheryScoreRuleMapper;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreDetailed;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreRule;
import com.apih5.service.ZjXmJxPeripheryScoreDetailedService;
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

@Service("zjXmJxPeripheryScoreDetailedService")
public class ZjXmJxPeripheryScoreDetailedServiceImpl implements ZjXmJxPeripheryScoreDetailedService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxPeripheryScoreDetailedMapper zjXmJxPeripheryScoreDetailedMapper;
	@Autowired(required = true)
	private ZjXmJxAssessmentUserScoreMapper zjXmJxAssessmentUserScoreMapper;
	@Autowired(required = true)
	private ZjXmJxPeripheryScoreRuleMapper zjXmJxPeripheryScoreRuleMapper;

	@Override
	public ResponseEntity getZjXmJxPeripheryScoreDetailedListByCondition(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		if (zjXmJxPeripheryScoreDetailed == null) {
			zjXmJxPeripheryScoreDetailed = new ZjXmJxPeripheryScoreDetailed();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxPeripheryScoreDetailed.getPage(), zjXmJxPeripheryScoreDetailed.getLimit());
		// 获取数据
		List<ZjXmJxPeripheryScoreDetailed> zjXmJxPeripheryScoreDetailedList = zjXmJxPeripheryScoreDetailedMapper
				.selectByZjXmJxPeripheryScoreDetailedList(zjXmJxPeripheryScoreDetailed);
		// 得到分页信息
		PageInfo<ZjXmJxPeripheryScoreDetailed> p = new PageInfo<>(zjXmJxPeripheryScoreDetailedList);

		return repEntity.okList(zjXmJxPeripheryScoreDetailedList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxPeripheryScoreDetailedDetails(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		if (zjXmJxPeripheryScoreDetailed == null) {
			zjXmJxPeripheryScoreDetailed = new ZjXmJxPeripheryScoreDetailed();
		}
		// 获取数据
		ZjXmJxPeripheryScoreDetailed dbZjXmJxPeripheryScoreDetailed = zjXmJxPeripheryScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxPeripheryScoreDetailed.getDetailedId());
		// 数据存在
		if (dbZjXmJxPeripheryScoreDetailed != null) {
			return repEntity.ok(dbZjXmJxPeripheryScoreDetailed);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxPeripheryScoreDetailed(ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxPeripheryScoreDetailed.setDetailedId(UuidUtil.generate());
		zjXmJxPeripheryScoreDetailed.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxPeripheryScoreDetailedMapper.insert(zjXmJxPeripheryScoreDetailed);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxPeripheryScoreDetailed);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxPeripheryScoreDetailed(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxPeripheryScoreDetailed dbzjXmJxPeripheryScoreDetailed = zjXmJxPeripheryScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxPeripheryScoreDetailed.getDetailedId());
		if (dbzjXmJxPeripheryScoreDetailed != null
				&& StrUtil.isNotEmpty(dbzjXmJxPeripheryScoreDetailed.getDetailedId())) {
			// 月度考核得分表主键
			dbzjXmJxPeripheryScoreDetailed.setScoreId(zjXmJxPeripheryScoreDetailed.getScoreId());
			// 月度考核表主键
			dbzjXmJxPeripheryScoreDetailed.setAssessmentId(zjXmJxPeripheryScoreDetailed.getAssessmentId());
			// 被审核人员主键
			dbzjXmJxPeripheryScoreDetailed.setAuditeeKey(zjXmJxPeripheryScoreDetailed.getAuditeeKey());
			// 被审核人员姓名
			dbzjXmJxPeripheryScoreDetailed.setAuditeeName(zjXmJxPeripheryScoreDetailed.getAuditeeName());
			// 被审核者部门主键
			dbzjXmJxPeripheryScoreDetailed.setAuditeeDeptId(zjXmJxPeripheryScoreDetailed.getAuditeeDeptId());
			// 被审核者部门名称
			dbzjXmJxPeripheryScoreDetailed.setAuditeeDeptName(zjXmJxPeripheryScoreDetailed.getAuditeeDeptName());
			// 被审核者项目id
			dbzjXmJxPeripheryScoreDetailed.setAuditeeProId(zjXmJxPeripheryScoreDetailed.getAuditeeProId());
			// 被审核者项目名称
			dbzjXmJxPeripheryScoreDetailed.setAuditeeProName(zjXmJxPeripheryScoreDetailed.getAuditeeProName());
			// 审核者主键
			dbzjXmJxPeripheryScoreDetailed.setReviewerKey(zjXmJxPeripheryScoreDetailed.getReviewerKey());
			// 审核者姓名
			dbzjXmJxPeripheryScoreDetailed.setReviewerName(zjXmJxPeripheryScoreDetailed.getReviewerName());
			// 人事专员主键
			dbzjXmJxPeripheryScoreDetailed.setHrUserKey(zjXmJxPeripheryScoreDetailed.getHrUserKey());
			// 人事专员姓名
			dbzjXmJxPeripheryScoreDetailed.setHrName(zjXmJxPeripheryScoreDetailed.getHrName());
			// 权重
			dbzjXmJxPeripheryScoreDetailed.setWeightValue(zjXmJxPeripheryScoreDetailed.getWeightValue());
			// 分数
			dbzjXmJxPeripheryScoreDetailed.setScore(zjXmJxPeripheryScoreDetailed.getScore());
			// 审核者身份类型
			dbzjXmJxPeripheryScoreDetailed.setReviewerType(zjXmJxPeripheryScoreDetailed.getReviewerType());
			// 审核状态
			dbzjXmJxPeripheryScoreDetailed.setAuditStatus(zjXmJxPeripheryScoreDetailed.getAuditStatus());
			// 备注
			dbzjXmJxPeripheryScoreDetailed.setRemarks(zjXmJxPeripheryScoreDetailed.getRemarks());
			// 排序
			dbzjXmJxPeripheryScoreDetailed.setSort(zjXmJxPeripheryScoreDetailed.getSort());
			// 共通
			dbzjXmJxPeripheryScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxPeripheryScoreDetailedMapper.updateByPrimaryKey(dbzjXmJxPeripheryScoreDetailed);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxPeripheryScoreDetailed);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxPeripheryScoreDetailed(
			List<ZjXmJxPeripheryScoreDetailed> zjXmJxPeripheryScoreDetailedList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxPeripheryScoreDetailedList != null && zjXmJxPeripheryScoreDetailedList.size() > 0) {
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed = new ZjXmJxPeripheryScoreDetailed();
			zjXmJxPeripheryScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxPeripheryScoreDetailedMapper.batchDeleteUpdateZjXmJxPeripheryScoreDetailed(
					zjXmJxPeripheryScoreDetailedList, zjXmJxPeripheryScoreDetailed);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxPeripheryScoreDetailedList);
		}
	}

	@Override
	public ResponseEntity getZjXmJxPeripheryScoreDetailedListByReviewer(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		if (zjXmJxPeripheryScoreDetailed == null) {
			zjXmJxPeripheryScoreDetailed = new ZjXmJxPeripheryScoreDetailed();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxPeripheryScoreDetailed.setReviewerKey(userKey);
		// 根据分数获取数据
		List<ZjXmJxPeripheryScoreDetailed> auditeeList = zjXmJxPeripheryScoreDetailedMapper
				.getZjXmJxPeripheryScoreDetailedList(zjXmJxPeripheryScoreDetailed);
		JSONArray deputyArray = JSONUtil.createArray();
		JSONArray leaderArray = JSONUtil.createArray();
		JSONArray employeeArray = JSONUtil.createArray();
		// 随机标识
		boolean shuffleFlag = true;
		if (auditeeList.size() > 0) {
			for (ZjXmJxPeripheryScoreDetailed auditee : auditeeList) {
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
		zjXmJxPeripheryScoreDetailed.setDeputyArray(deputyArray);
		zjXmJxPeripheryScoreDetailed.setLeaderArray(leaderArray);
		zjXmJxPeripheryScoreDetailed.setEmployeeArray(employeeArray);
		// check根据打分者和月度考核判断是否所有人已打完分，
		ZjXmJxPeripheryScoreDetailed check = new ZjXmJxPeripheryScoreDetailed();
		check.setAssessmentId(zjXmJxPeripheryScoreDetailed.getAssessmentId());
		check.setReviewerKey(userKey);
		int count1 = zjXmJxPeripheryScoreDetailedMapper.countZjXmJxPeripheryScoreDetailedList(check);
		if (count1 == 0) {
			zjXmJxPeripheryScoreDetailed.setButtonFlag("0");
		} else {
			zjXmJxPeripheryScoreDetailed.setButtonFlag("1");
		}
		return repEntity.ok(zjXmJxPeripheryScoreDetailed);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity tempOrSubmitZjXmJxPeripheryScoreDetailedList(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) throws Exception {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		JSONArray userArray = JSONUtil.createArray();
		if (zjXmJxPeripheryScoreDetailed != null && zjXmJxPeripheryScoreDetailed.getDeputyArray() != null
				&& zjXmJxPeripheryScoreDetailed.getDeputyArray().size() > 0) {
			// check三个数组每个数组不能出现相同的分数
			if (checkRepeatScore(zjXmJxPeripheryScoreDetailed.getDeputyArray())) {
				return repEntity.layerMessage("no", "项目副职中存在相同分值。");
			}
			userArray.addAll(zjXmJxPeripheryScoreDetailed.getDeputyArray());
		}
		if (zjXmJxPeripheryScoreDetailed != null && zjXmJxPeripheryScoreDetailed.getLeaderArray() != null
				&& zjXmJxPeripheryScoreDetailed.getLeaderArray().size() > 0) {
			if (checkRepeatScore(zjXmJxPeripheryScoreDetailed.getLeaderArray())) {
				return repEntity.layerMessage("no", "部门负责人中存在相同分值。");
			}
			userArray.addAll(zjXmJxPeripheryScoreDetailed.getLeaderArray());
		}
		if (zjXmJxPeripheryScoreDetailed != null && zjXmJxPeripheryScoreDetailed.getEmployeeArray() != null
				&& zjXmJxPeripheryScoreDetailed.getEmployeeArray().size() > 0) {
			if (checkRepeatScore(zjXmJxPeripheryScoreDetailed.getEmployeeArray())) {
				return repEntity.layerMessage("no", "普通员工中存在相同分值。");
			}
			userArray.addAll(zjXmJxPeripheryScoreDetailed.getEmployeeArray());
		}
		if (StrUtil.isNotEmpty(zjXmJxPeripheryScoreDetailed.getAssessmentId()) && userArray.size() > 0) {
			// check根据打分者和月度考核判断是否所有人已打完分，
			ZjXmJxPeripheryScoreDetailed check = new ZjXmJxPeripheryScoreDetailed();
			check.setAssessmentId(zjXmJxPeripheryScoreDetailed.getAssessmentId());
			check.setReviewerKey(userKey);
			int count1 = zjXmJxPeripheryScoreDetailedMapper.countZjXmJxPeripheryScoreDetailedList(check);
			if (count1 == 0) {
				return repEntity.layerMessage("no", "该评分者已打分,请不要重复提交");
			}
			// 暂存
			if (StrUtil.equals("3", zjXmJxPeripheryScoreDetailed.getAuditStatus())) {
				List<ZjXmJxPeripheryScoreDetailed> updateList = Lists.newArrayList();
				for (int i = 0; i < userArray.size(); i++) {
					ZjXmJxPeripheryScoreDetailed updateData = new ZjXmJxPeripheryScoreDetailed();
					updateData.setAuditeeKey(userArray.getJSONObject(i).getStr("value"));
					updateData.setReviewerKey(userKey);
					updateData.setAssessmentId(zjXmJxPeripheryScoreDetailed.getAssessmentId());
					updateData.setScore(userArray.getJSONObject(i).getBigDecimal("score"));
					// 暂存
					updateData.setAuditStatus("3");
					updateList.add(updateData);
				}
				flag = zjXmJxPeripheryScoreDetailedMapper.batchUpdateZjXmJxPeripheryScoreDetailedByReviewer(updateList);
			}
			// 提交
			else if (StrUtil.equals("4", zjXmJxPeripheryScoreDetailed.getAuditStatus())) {
				List<ZjXmJxPeripheryScoreDetailed> updateList = Lists.newArrayList();
				for (int i = 0; i < userArray.size(); i++) {
					ZjXmJxPeripheryScoreDetailed updateData = new ZjXmJxPeripheryScoreDetailed();
					updateData.setAuditeeKey(userArray.getJSONObject(i).getStr("value"));
					updateData.setReviewerKey(userKey);
					updateData.setAssessmentId(zjXmJxPeripheryScoreDetailed.getAssessmentId());
					updateData.setScore(userArray.getJSONObject(i).getBigDecimal("score"));
					// 已审核
					updateData.setAuditStatus("4");
					updateList.add(updateData);
				}
				flag = zjXmJxPeripheryScoreDetailedMapper.batchUpdateZjXmJxPeripheryScoreDetailedByReviewer(updateList);
				// 循环判断被打分者是否已被打完分,被打完分需要核算该人员周边考核总分
				for (ZjXmJxPeripheryScoreDetailed auditee : updateList) {
					// 根据auditeeKey和assessmentId和auditStatus判断是否被打完分
					ZjXmJxPeripheryScoreDetailed check2 = new ZjXmJxPeripheryScoreDetailed();
					check2.setAssessmentId(auditee.getAssessmentId());
					check2.setAuditeeKey(auditee.getAuditeeKey());
					int count2 = zjXmJxPeripheryScoreDetailedMapper.countZjXmJxPeripheryScoreDetailedList(check2);
					// 说明该被考核者已被打完分
					if (count2 == 0) {
						// 核算该被打分者总分
						// 根据auditeeKey和assessmentId获取集合
						ZjXmJxPeripheryScoreDetailed scoreDetailed = new ZjXmJxPeripheryScoreDetailed();
						scoreDetailed.setAuditeeKey(auditee.getAuditeeKey());
						scoreDetailed.setAssessmentId(auditee.getAssessmentId());
						List<ZjXmJxPeripheryScoreDetailed> list = zjXmJxPeripheryScoreDetailedMapper
								.getZjXmJxPeripheryScoreDetailedList(scoreDetailed);
						// 将集合按最大可能性(分数都是从高到低)
						// 分为副职打分集合、部门负责人打分集合、普通员工打分集合
						List<ZjXmJxPeripheryScoreDetailed> lastList = Lists.newArrayList();
						if (list.size() > 0) {
							List<ZjXmJxPeripheryScoreDetailed> deputyList = Lists.newArrayList();
							List<ZjXmJxPeripheryScoreDetailed> leaderList = Lists.newArrayList();
							List<ZjXmJxPeripheryScoreDetailed> employeeList = Lists.newArrayList();
							for (ZjXmJxPeripheryScoreDetailed score : list) {
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
						// 整理总分
						BigDecimal totalScore = BigDecimal.ZERO;
						Map<String, List<ZjXmJxPeripheryScoreDetailed>> map = new HashMap<String, List<ZjXmJxPeripheryScoreDetailed>>();
						if (lastList.size() > 0) {
							for (ZjXmJxPeripheryScoreDetailed score : lastList) {
								if (map.containsKey(score.getScoringType())) {
									map.get(score.getScoringType()).add(score);
								} else {
									List<ZjXmJxPeripheryScoreDetailed> tempList = Lists.newArrayList();
									tempList.add(score);
									map.put(score.getScoringType(), tempList);
								}
							}
							// 遍历map核算总分(相同权重的分值求平均分*权重，最后相加)
							for (Map.Entry<String, List<ZjXmJxPeripheryScoreDetailed>> entry : map.entrySet()) {
								BigDecimal score1 = BigDecimal.ZERO;
								for (ZjXmJxPeripheryScoreDetailed score2 : entry.getValue()) {
									score1 = CalcUtils.calcAdd(score1, score2.getScore());
								}
								BigDecimal average = CalcUtils.calcDivide(score1,
										new BigDecimal(entry.getValue().size()), 6);
								BigDecimal product = CalcUtils.calcMultiply(average,
										new BigDecimal(entry.getValue().get(0).getWeightValue()));
								totalScore = CalcUtils.calcAdd(totalScore,
										CalcUtils.calcDivide(product, new BigDecimal(100), 6));
							}
							totalScore = totalScore.setScale(2, RoundingMode.HALF_UP);
						}
						// 找到该被打分者得分表修改总分及审核状态字段
						ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
						userScore.setAssessmentId(zjXmJxPeripheryScoreDetailed.getAssessmentId());
						userScore.setAuditeeKey(auditee.getAuditeeKey());
						userScore.setAssessmentType("1");
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
			return repEntity.ok("sys.data.update", zjXmJxPeripheryScoreDetailed);
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
	public ResponseEntity getZjXmJxPeripheryScoreDetailedPeripheryExcel(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		if (zjXmJxPeripheryScoreDetailed == null) {
			zjXmJxPeripheryScoreDetailed = new ZjXmJxPeripheryScoreDetailed();
		}
		// 根据项目id/月份/月度考核id
		// check该检索条件下是否能查到打分者，无打分者不能导出excel(否则系统异常sql报错)
		// 获取列标题内容
		List<ZjXmJxPeripheryScoreDetailed> columnTitleList = zjXmJxPeripheryScoreDetailedMapper
				.getZjXmJxPeripheryScoreDetailedListByReviewer(zjXmJxPeripheryScoreDetailed);
		if (columnTitleList.size() < 1) {
			return repEntity.okList(Lists.newArrayList(), 0);
		}
		// 否则动态获取统计数据
		zjXmJxPeripheryScoreDetailed.setColumnTitleList(columnTitleList);
		// 获取数据
		List<Map<String, Object>> list = zjXmJxPeripheryScoreDetailedMapper
				.getZjXmJxPeripheryScoreDetailedPeripheryExcel(zjXmJxPeripheryScoreDetailed);
		return repEntity.okList(list, list.size());
	}

	@Override
	public void exportZjXmJxPeripheryScoreDetailedPeripheryExcel(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed, HttpServletResponse response) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxPeripheryScoreDetailed == null) {
			zjXmJxPeripheryScoreDetailed = new ZjXmJxPeripheryScoreDetailed();
		}
		// 根据项目id/月份/月度考核id
		// check该检索条件下是否能查到打分者，无打分者不能导出excel(否则系统异常sql报错)
		// 获取列标题内容
		List<ZjXmJxPeripheryScoreDetailed> columnTitleList = zjXmJxPeripheryScoreDetailedMapper
				.getZjXmJxPeripheryScoreDetailedListByReviewer(zjXmJxPeripheryScoreDetailed);
		// 返回汇总数据
		List<List<?>> rowsList = Lists.newArrayList();
		// 表头
		List<String> row1 = CollUtil.newArrayList();
		row1.add("姓名");
		row1.add("身份证号码");
		row1.add("打分类别");
		if (columnTitleList.size() > 0) {
			// 否则动态获取统计数据
			zjXmJxPeripheryScoreDetailed.setColumnTitleList(columnTitleList);
			// 获取数据
			List<Map<String, Object>> dataList = zjXmJxPeripheryScoreDetailedMapper
					.getZjXmJxPeripheryScoreDetailedPeripheryExcel(zjXmJxPeripheryScoreDetailed);
			if (columnTitleList.size() > 0) {
				for (ZjXmJxPeripheryScoreDetailed columnTitle : columnTitleList) {
					row1.add(columnTitle.getReviewerName());
				}
			}
			// 装载数据
			// rowsList.add(CollUtil.newArrayList(row1));
			rowsList.add(row1);
			if (dataList != null && dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					List<String> contentList = Lists.newArrayList();
					contentList.add(dataList.get(i).get("auditeeName").toString());
					contentList.add(dataList.get(i).get("idNumber").toString());
					contentList.add(dataList.get(i).get("scoringType").toString());
					for (int j = 0; j < columnTitleList.size(); j++) {
						contentList.add(dataList.get(i).get(columnTitleList.get(j).getReviewerName()).toString());
					}
					// 装载完放入rowsList
					// rowsList.add(CollUtil.newArrayList(contentList));
					rowsList.add(contentList);
				}
			}
		}
		// 数据装载（与上面的表头对应）
		// List<List<?>> rows = CollUtil.newArrayList(rowsList);
		ExcelWriter writer = ExcelUtil.getWriter(true);
		// BigExcelWriter writer= ExcelUtil.getBigWriter(true);
		// 通过工具类创建writer，创建xlsx格式
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
					"attachment; filename=\"" + new String("周边业绩得分明细表".getBytes("utf-8"), "iso-8859-1") + "\"");
			out = response.getOutputStream();
			// 设置标题
			writer.merge(row1.size() - 1, "周边业绩得分明细表");
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
	public ResponseEntity getZjXmJxPeripheryScoreDetailedPeripheryExcelColumn(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		if (zjXmJxPeripheryScoreDetailed == null) {
			zjXmJxPeripheryScoreDetailed = new ZjXmJxPeripheryScoreDetailed();
		}
		// 根据项目id/月份/月度考核id
		// check该检索条件下是否能查到打分者，无打分者不能导出excel(否则系统异常sql报错)
		// 获取列标题内容
		List<ZjXmJxPeripheryScoreDetailed> columnTitleList = zjXmJxPeripheryScoreDetailedMapper
				.getZjXmJxPeripheryScoreDetailedListByReviewer(zjXmJxPeripheryScoreDetailed);
		// 返回汇总数据
		JSONArray array = JSONUtil.createArray();
		JSONObject obj1 = JSONUtil.createObj();
		obj1.set("姓名", "auditeeName");
		array.add(obj1);
		JSONObject obj2 = JSONUtil.createObj();
		obj2.set("身份证号码", "idNumber");
		array.add(obj2);
		JSONObject obj3 = JSONUtil.createObj();
		obj3.set("打分类别", "scoringType");
		array.add(obj3);
		// 表头
		if (columnTitleList.size() > 0) {
			for (ZjXmJxPeripheryScoreDetailed columnTitle : columnTitleList) {
				JSONObject obj4 = JSONUtil.createObj();
				obj4.set(columnTitle.getReviewerName(), columnTitle.getReviewerName());
				array.add(obj4);
			}
		}
		return repEntity.okList(array, array.size());
	}

	@Override
	public ResponseEntity recalculateZjXmJxPeripheryScoreDetailed(
			ZjXmJxPeripheryScoreDetailed zjXmJxPeripheryScoreDetailed) {
		int flag = 0;
		if (StrUtil.isNotEmpty(zjXmJxPeripheryScoreDetailed.getAssessmentId())) {
			// 根据考核id获取该考核下所有参与被打分的人员列表
			List<ZjXmJxPeripheryScoreDetailed> updateList = zjXmJxPeripheryScoreDetailedMapper
					.getZjXmJxPeripheryScoreDetailedAllAuditee(zjXmJxPeripheryScoreDetailed);
			// 循环判断被打分者是否已被打完分,被打完分需要重新核算该人员周边考核总分
			for (ZjXmJxPeripheryScoreDetailed auditee : updateList) {
				// 根据auditeeKey和assessmentId和auditStatus判断是否被打完分
				ZjXmJxPeripheryScoreDetailed check2 = new ZjXmJxPeripheryScoreDetailed();
				check2.setAssessmentId(auditee.getAssessmentId());
				check2.setAuditeeKey(auditee.getAuditeeKey());
				int count2 = zjXmJxPeripheryScoreDetailedMapper.countZjXmJxPeripheryScoreDetailedList(check2);
				// 说明该被考核者已被打完分
				if (count2 == 0) {
					// 核算该被打分者总分
					// 根据auditeeKey和assessmentId获取集合
					ZjXmJxPeripheryScoreDetailed scoreDetailed = new ZjXmJxPeripheryScoreDetailed();
					scoreDetailed.setAuditeeKey(auditee.getAuditeeKey());
					scoreDetailed.setAssessmentId(auditee.getAssessmentId());
					List<ZjXmJxPeripheryScoreDetailed> list = zjXmJxPeripheryScoreDetailedMapper
							.getZjXmJxPeripheryScoreDetailedList(scoreDetailed);
					// 将集合按最大可能性(分数都是从高到低)
					// 分为副职打分集合、部门负责人打分集合、普通员工打分集合
					List<ZjXmJxPeripheryScoreDetailed> lastList = Lists.newArrayList();
					if (list.size() > 0) {
						List<ZjXmJxPeripheryScoreDetailed> deputyList = Lists.newArrayList();
						List<ZjXmJxPeripheryScoreDetailed> leaderList = Lists.newArrayList();
						List<ZjXmJxPeripheryScoreDetailed> employeeList = Lists.newArrayList();
						for (ZjXmJxPeripheryScoreDetailed score : list) {
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
					// 整理总分
					BigDecimal totalScore = BigDecimal.ZERO;
					Map<String, List<ZjXmJxPeripheryScoreDetailed>> map = new HashMap<String, List<ZjXmJxPeripheryScoreDetailed>>();
					if (lastList.size() > 0) {
						for (ZjXmJxPeripheryScoreDetailed score : lastList) {
							if (map.containsKey(score.getScoringType())) {
								map.get(score.getScoringType()).add(score);
							} else {
								List<ZjXmJxPeripheryScoreDetailed> tempList = Lists.newArrayList();
								tempList.add(score);
								map.put(score.getScoringType(), tempList);
							}
						}
						// 遍历map核算总分(相同权重的分值求平均分*权重，最后相加)
						for (Map.Entry<String, List<ZjXmJxPeripheryScoreDetailed>> entry : map.entrySet()) {
							BigDecimal score1 = BigDecimal.ZERO;
							for (ZjXmJxPeripheryScoreDetailed score2 : entry.getValue()) {
								score1 = CalcUtils.calcAdd(score1, score2.getScore());
							}
							BigDecimal average = CalcUtils.calcDivide(score1, new BigDecimal(entry.getValue().size()),
									6);
							BigDecimal product = CalcUtils.calcMultiply(average,
									new BigDecimal(entry.getValue().get(0).getWeightValue()));
							totalScore = CalcUtils.calcAdd(totalScore,
									CalcUtils.calcDivide(product, new BigDecimal(100), 6));
						}
						totalScore = totalScore.setScale(2, RoundingMode.HALF_UP);
					}
					// 找到该被打分者得分表修改总分及审核状态字段
					ZjXmJxAssessmentUserScore userScore = new ZjXmJxAssessmentUserScore();
					userScore.setAssessmentId(zjXmJxPeripheryScoreDetailed.getAssessmentId());
					userScore.setAuditeeKey(auditee.getAuditeeKey());
					userScore.setAssessmentType("1");
					List<ZjXmJxAssessmentUserScore> userScorelist = zjXmJxAssessmentUserScoreMapper
							.selectByZjXmJxAssessmentUserScoreList(userScore);
					if (userScorelist.size() == 1) {
						userScorelist.get(0).setScore(totalScore);
						userScorelist.get(0).setAuditStatus("4");
						// userScorelist.get(0).setAuditTime(DateUtil.date());
						// userScorelist.get(0).setModifyUserInfo(userKey, realName);
						flag = zjXmJxAssessmentUserScoreMapper.updateByPrimaryKey(userScorelist.get(0));
					}
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxPeripheryScoreDetailed);
		}
	}

}
