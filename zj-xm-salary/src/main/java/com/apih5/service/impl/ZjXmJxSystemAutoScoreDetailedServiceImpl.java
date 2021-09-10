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
import com.apih5.mybatis.dao.ZjXmJxSystemAutoScoreDetailedMapper;
import com.apih5.mybatis.pojo.ZjXmJxSystemAutoScoreDetailed;
import com.apih5.service.ZjXmJxSystemAutoScoreDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxSystemAutoScoreDetailedService")
public class ZjXmJxSystemAutoScoreDetailedServiceImpl implements ZjXmJxSystemAutoScoreDetailedService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmJxSystemAutoScoreDetailedMapper zjXmJxSystemAutoScoreDetailedMapper;

	@Override
	public ResponseEntity getZjXmJxSystemAutoScoreDetailedListByCondition(
			ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed) {
		if (zjXmJxSystemAutoScoreDetailed == null) {
			zjXmJxSystemAutoScoreDetailed = new ZjXmJxSystemAutoScoreDetailed();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxSystemAutoScoreDetailed.getPage(), zjXmJxSystemAutoScoreDetailed.getLimit());
		// 获取数据
		List<ZjXmJxSystemAutoScoreDetailed> zjXmJxSystemAutoScoreDetailedList = zjXmJxSystemAutoScoreDetailedMapper
				.selectByZjXmJxSystemAutoScoreDetailedList(zjXmJxSystemAutoScoreDetailed);
		// 得到分页信息
		PageInfo<ZjXmJxSystemAutoScoreDetailed> p = new PageInfo<>(zjXmJxSystemAutoScoreDetailedList);

		return repEntity.okList(zjXmJxSystemAutoScoreDetailedList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxSystemAutoScoreDetailedDetails(
			ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed) {
		if (zjXmJxSystemAutoScoreDetailed == null) {
			zjXmJxSystemAutoScoreDetailed = new ZjXmJxSystemAutoScoreDetailed();
		}
		// 获取数据
		ZjXmJxSystemAutoScoreDetailed dbZjXmJxSystemAutoScoreDetailed = zjXmJxSystemAutoScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxSystemAutoScoreDetailed.getDetailedId());
		// 数据存在
		if (dbZjXmJxSystemAutoScoreDetailed != null) {
			return repEntity.ok(dbZjXmJxSystemAutoScoreDetailed);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxSystemAutoScoreDetailed(
			ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxSystemAutoScoreDetailed.setDetailedId(UuidUtil.generate());
		zjXmJxSystemAutoScoreDetailed.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxSystemAutoScoreDetailedMapper.insert(zjXmJxSystemAutoScoreDetailed);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxSystemAutoScoreDetailed);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxSystemAutoScoreDetailed(
			ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxSystemAutoScoreDetailed dbzjXmJxSystemAutoScoreDetailed = zjXmJxSystemAutoScoreDetailedMapper
				.selectByPrimaryKey(zjXmJxSystemAutoScoreDetailed.getDetailedId());
		if (dbzjXmJxSystemAutoScoreDetailed != null
				&& StrUtil.isNotEmpty(dbzjXmJxSystemAutoScoreDetailed.getDetailedId())) {
			// 月度考核得分表主键
			dbzjXmJxSystemAutoScoreDetailed.setScoreId(zjXmJxSystemAutoScoreDetailed.getScoreId());
			// 月度考核表主键
			dbzjXmJxSystemAutoScoreDetailed.setAssessmentId(zjXmJxSystemAutoScoreDetailed.getAssessmentId());
			// 被审核人员主键
			dbzjXmJxSystemAutoScoreDetailed.setAuditeeKey(zjXmJxSystemAutoScoreDetailed.getAuditeeKey());
			// 被审核人员姓名
			dbzjXmJxSystemAutoScoreDetailed.setAuditeeName(zjXmJxSystemAutoScoreDetailed.getAuditeeName());
			// 被审核人员类型
			dbzjXmJxSystemAutoScoreDetailed.setAuditeeType(zjXmJxSystemAutoScoreDetailed.getAuditeeType());
			// 被审核者部门主键
			dbzjXmJxSystemAutoScoreDetailed.setAuditeeDeptId(zjXmJxSystemAutoScoreDetailed.getAuditeeDeptId());
			// 被审核者部门名称
			dbzjXmJxSystemAutoScoreDetailed.setAuditeeDeptName(zjXmJxSystemAutoScoreDetailed.getAuditeeDeptName());
			// 被审核者项目id
			dbzjXmJxSystemAutoScoreDetailed.setAuditeeProId(zjXmJxSystemAutoScoreDetailed.getAuditeeProId());
			// 被审核者项目名称
			dbzjXmJxSystemAutoScoreDetailed.setAuditeeProName(zjXmJxSystemAutoScoreDetailed.getAuditeeProName());
			// 审核者主键
			dbzjXmJxSystemAutoScoreDetailed.setReviewerKey(zjXmJxSystemAutoScoreDetailed.getReviewerKey());
			// 审核者姓名
			dbzjXmJxSystemAutoScoreDetailed.setReviewerName(zjXmJxSystemAutoScoreDetailed.getReviewerName());
			// 人事专员主键
			dbzjXmJxSystemAutoScoreDetailed.setHrUserKey(zjXmJxSystemAutoScoreDetailed.getHrUserKey());
			// 人事专员姓名
			dbzjXmJxSystemAutoScoreDetailed.setHrName(zjXmJxSystemAutoScoreDetailed.getHrName());
			// 分数
			dbzjXmJxSystemAutoScoreDetailed.setScore(zjXmJxSystemAutoScoreDetailed.getScore());
			// 审核者身份类型
			dbzjXmJxSystemAutoScoreDetailed.setReviewerType(zjXmJxSystemAutoScoreDetailed.getReviewerType());
			// 审核状态
			dbzjXmJxSystemAutoScoreDetailed.setAuditStatus(zjXmJxSystemAutoScoreDetailed.getAuditStatus());
			// 扣分原因及来源
			dbzjXmJxSystemAutoScoreDetailed.setReasonSources(zjXmJxSystemAutoScoreDetailed.getReasonSources());
			// 扣分类型
			dbzjXmJxSystemAutoScoreDetailed.setDeductionType(zjXmJxSystemAutoScoreDetailed.getDeductionType());
			// 备注
			dbzjXmJxSystemAutoScoreDetailed.setRemarks(zjXmJxSystemAutoScoreDetailed.getRemarks());
			// 排序
			dbzjXmJxSystemAutoScoreDetailed.setSort(zjXmJxSystemAutoScoreDetailed.getSort());
			// 共通
			dbzjXmJxSystemAutoScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxSystemAutoScoreDetailedMapper.updateByPrimaryKey(dbzjXmJxSystemAutoScoreDetailed);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxSystemAutoScoreDetailed);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxSystemAutoScoreDetailed(
			List<ZjXmJxSystemAutoScoreDetailed> zjXmJxSystemAutoScoreDetailedList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxSystemAutoScoreDetailedList != null && zjXmJxSystemAutoScoreDetailedList.size() > 0) {
			ZjXmJxSystemAutoScoreDetailed zjXmJxSystemAutoScoreDetailed = new ZjXmJxSystemAutoScoreDetailed();
			zjXmJxSystemAutoScoreDetailed.setModifyUserInfo(userKey, realName);
			flag = zjXmJxSystemAutoScoreDetailedMapper.batchDeleteUpdateZjXmJxSystemAutoScoreDetailed(
					zjXmJxSystemAutoScoreDetailedList, zjXmJxSystemAutoScoreDetailed);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxSystemAutoScoreDetailedList);
		}
	}
}
