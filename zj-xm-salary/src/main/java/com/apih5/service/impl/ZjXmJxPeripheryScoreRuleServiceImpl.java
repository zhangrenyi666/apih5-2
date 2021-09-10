package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxPeripheryScoreRuleMapper;
import com.apih5.mybatis.pojo.ZjXmJxPeripheryScoreRule;
import com.apih5.service.ZjXmJxPeripheryScoreRuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxPeripheryScoreRuleService")
public class ZjXmJxPeripheryScoreRuleServiceImpl implements ZjXmJxPeripheryScoreRuleService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxPeripheryScoreRuleMapper zjXmJxPeripheryScoreRuleMapper;

	@Override
	public ResponseEntity getZjXmJxPeripheryScoreRuleListByCondition(
			ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		if (zjXmJxPeripheryScoreRule == null) {
			zjXmJxPeripheryScoreRule = new ZjXmJxPeripheryScoreRule();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxPeripheryScoreRule.getPage(), zjXmJxPeripheryScoreRule.getLimit());
		// 获取数据
		List<ZjXmJxPeripheryScoreRule> zjXmJxPeripheryScoreRuleList = zjXmJxPeripheryScoreRuleMapper
				.selectByZjXmJxPeripheryScoreRuleList(zjXmJxPeripheryScoreRule);
		// 得到分页信息
		PageInfo<ZjXmJxPeripheryScoreRule> p = new PageInfo<>(zjXmJxPeripheryScoreRuleList);

		return repEntity.okList(zjXmJxPeripheryScoreRuleList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxPeripheryScoreRuleDetails(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		if (zjXmJxPeripheryScoreRule == null) {
			zjXmJxPeripheryScoreRule = new ZjXmJxPeripheryScoreRule();
		}
		// 获取数据
		ZjXmJxPeripheryScoreRule dbZjXmJxPeripheryScoreRule = zjXmJxPeripheryScoreRuleMapper
				.selectByPrimaryKey(zjXmJxPeripheryScoreRule.getRuleId());
		// 数据存在
		if (dbZjXmJxPeripheryScoreRule != null) {
			return repEntity.ok(dbZjXmJxPeripheryScoreRule);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxPeripheryScoreRule(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxPeripheryScoreRule.setRuleId(UuidUtil.generate());
		zjXmJxPeripheryScoreRule.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxPeripheryScoreRuleMapper.insert(zjXmJxPeripheryScoreRule);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxPeripheryScoreRule);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxPeripheryScoreRule(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxPeripheryScoreRule dbzjXmJxPeripheryScoreRule = zjXmJxPeripheryScoreRuleMapper
				.selectByPrimaryKey(zjXmJxPeripheryScoreRule.getRuleId());
		if (dbzjXmJxPeripheryScoreRule != null && StrUtil.isNotEmpty(dbzjXmJxPeripheryScoreRule.getRuleId())) {
			// 副职基数
			dbzjXmJxPeripheryScoreRule.setDeputyDivisor(zjXmJxPeripheryScoreRule.getDeputyDivisor());
			// 部门负责人基数
			dbzjXmJxPeripheryScoreRule.setLeaderDivisor(zjXmJxPeripheryScoreRule.getLeaderDivisor());
			// 普通员工基数
			dbzjXmJxPeripheryScoreRule.setEmployeeDivisor(zjXmJxPeripheryScoreRule.getEmployeeDivisor());
			// 年月
			dbzjXmJxPeripheryScoreRule.setYearMonth(zjXmJxPeripheryScoreRule.getYearMonth());
			// 是否失效
			dbzjXmJxPeripheryScoreRule.setIsInvalid(zjXmJxPeripheryScoreRule.getIsInvalid());
			// 备注
			dbzjXmJxPeripheryScoreRule.setRemarks(zjXmJxPeripheryScoreRule.getRemarks());
			// 排序
			dbzjXmJxPeripheryScoreRule.setSort(zjXmJxPeripheryScoreRule.getSort());
			// 共通
			dbzjXmJxPeripheryScoreRule.setModifyUserInfo(userKey, realName);
			flag = zjXmJxPeripheryScoreRuleMapper.updateByPrimaryKey(dbzjXmJxPeripheryScoreRule);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxPeripheryScoreRule);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxPeripheryScoreRule(
			List<ZjXmJxPeripheryScoreRule> zjXmJxPeripheryScoreRuleList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxPeripheryScoreRuleList != null && zjXmJxPeripheryScoreRuleList.size() > 0) {
			ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule = new ZjXmJxPeripheryScoreRule();
			zjXmJxPeripheryScoreRule.setModifyUserInfo(userKey, realName);
			flag = zjXmJxPeripheryScoreRuleMapper
					.batchDeleteUpdateZjXmJxPeripheryScoreRule(zjXmJxPeripheryScoreRuleList, zjXmJxPeripheryScoreRule);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxPeripheryScoreRuleList);
		}
	}

	@Override
	public ResponseEntity getValidZjXmJxPeripheryScoreRule(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		if (zjXmJxPeripheryScoreRule == null) {
			zjXmJxPeripheryScoreRule = new ZjXmJxPeripheryScoreRule();
		}
		zjXmJxPeripheryScoreRule.setIsInvalid("0");
		// 获取数据
		List<ZjXmJxPeripheryScoreRule> dbZjXmJxPeripheryScoreRuleList = zjXmJxPeripheryScoreRuleMapper
				.selectByZjXmJxPeripheryScoreRuleList(zjXmJxPeripheryScoreRule);
		// 数据存在
		if (dbZjXmJxPeripheryScoreRuleList != null && dbZjXmJxPeripheryScoreRuleList.size() == 1) {
			return repEntity.ok(dbZjXmJxPeripheryScoreRuleList.get(0));
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity setUpZjXmJxPeripheryScoreRule(ZjXmJxPeripheryScoreRule zjXmJxPeripheryScoreRule) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 1、获取数据库中唯一一条生效的规则(最多有一条)
		ZjXmJxPeripheryScoreRule getCondition = new ZjXmJxPeripheryScoreRule();
		getCondition.setIsInvalid("0");
		List<ZjXmJxPeripheryScoreRule> validDataList = zjXmJxPeripheryScoreRuleMapper
				.selectByZjXmJxPeripheryScoreRuleList(getCondition);
		// 无生效的则直接新增,有生效的判断是否发生修改,没发生修改则不保存,
		// 发生修改,则将以前生效的置为失效,然后将发生修改的新增为生效即可
		zjXmJxPeripheryScoreRule.setRuleId(UuidUtil.generate());
		zjXmJxPeripheryScoreRule.setIsInvalid("0");
		zjXmJxPeripheryScoreRule.setCreateUserInfo(userKey, realName);
		if (validDataList.size() == 0) {
			flag = zjXmJxPeripheryScoreRuleMapper.insert(zjXmJxPeripheryScoreRule);
		} else if (validDataList.size() == 1) {
			ZjXmJxPeripheryScoreRule dbData = validDataList.get(0);
			if (dbData.getDeputyDivisor() != zjXmJxPeripheryScoreRule.getDeputyDivisor()
					|| dbData.getLeaderDivisor() != zjXmJxPeripheryScoreRule.getLeaderDivisor()
					|| dbData.getEmployeeDivisor() != zjXmJxPeripheryScoreRule.getEmployeeDivisor()) {
				dbData.setIsInvalid("1");
				dbData.setModifyUserInfo(userKey, realName);
				flag = zjXmJxPeripheryScoreRuleMapper.updateByPrimaryKey(dbData);
				flag = zjXmJxPeripheryScoreRuleMapper.insert(zjXmJxPeripheryScoreRule);
			} else {
				// 数值没发生变化则不保存
				flag = 1;
			}
		} else {
			// return repEntity.layerMessage("no", "存在多个生效的规则,请联系管理员。");
			// 将多个全都生效的全都置为失效，然后新增
			flag = zjXmJxPeripheryScoreRuleMapper.updateZjXmJxPeripheryScoreRuleByIdList(validDataList);
			flag = zjXmJxPeripheryScoreRuleMapper.insert(zjXmJxPeripheryScoreRule);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxPeripheryScoreRule);
		}
	}
}
