package com.apih5.service.impl;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtValuationRulesMapper;
import com.apih5.mybatis.dao.ZxCtValuationSZRulesMapper;
import com.apih5.mybatis.pojo.ZxCtValuationRules;
import com.apih5.mybatis.pojo.ZxCtValuationSZRules;
import com.apih5.service.ZxCtValuationSZRulesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxCtValuationSZRulesService")
public class ZxCtValuationSZRulesServiceImpl implements ZxCtValuationSZRulesService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxCtValuationSZRulesMapper zxCtValuationSZRulesMapper;
	@Autowired(required = true)
	private ZxCtValuationRulesMapper zxCtValuationRulesMapper;

	@Override
	public ResponseEntity getZxCtValuationSZRulesListByCondition(ZxCtValuationSZRules zxCtValuationSZRules) {
		if (zxCtValuationSZRules == null) {
			zxCtValuationSZRules = new ZxCtValuationSZRules();
		}
		// 分页查询
		PageHelper.startPage(zxCtValuationSZRules.getPage(), zxCtValuationSZRules.getLimit());
		// 获取数据
		List<ZxCtValuationSZRules> zxCtValuationSZRulesList = zxCtValuationSZRulesMapper
				.selectByZxCtValuationSZRulesList(zxCtValuationSZRules);
		// 得到分页信息
		PageInfo<ZxCtValuationSZRules> p = new PageInfo<>(zxCtValuationSZRulesList);

		return repEntity.okList(zxCtValuationSZRulesList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtValuationSZRulesDetails(ZxCtValuationSZRules zxCtValuationSZRules) {
		if (zxCtValuationSZRules == null) {
			zxCtValuationSZRules = new ZxCtValuationSZRules();
		}
		// 获取数据
		ZxCtValuationSZRules dbZxCtValuationSZRules = zxCtValuationSZRulesMapper
				.selectByPrimaryKey(zxCtValuationSZRules.getId());
		// 数据存在
		if (dbZxCtValuationSZRules != null) {
			return repEntity.ok(dbZxCtValuationSZRules);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxCtValuationSZRules(ZxCtValuationSZRules zxCtValuationSZRules) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxCtValuationSZRules.setId(UuidUtil.generate());
		zxCtValuationSZRules.setCreateUserInfo(userKey, realName);
		int flag = zxCtValuationSZRulesMapper.insert(zxCtValuationSZRules);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCtValuationSZRules);
		}
	}

	@Override
	public ResponseEntity updateZxCtValuationSZRules(ZxCtValuationSZRules zxCtValuationSZRules) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtValuationSZRules dbzxCtValuationSZRules = zxCtValuationSZRulesMapper
				.selectByPrimaryKey(zxCtValuationSZRules.getId());
		if (dbzxCtValuationSZRules != null && StrUtil.isNotEmpty(dbzxCtValuationSZRules.getId())) {
			// 编码
			dbzxCtValuationSZRules.setOrderNum(zxCtValuationSZRules.getOrderNum());
			// 计价规则名称
			dbzxCtValuationSZRules.setRuleName(zxCtValuationSZRules.getRuleName());
			// 备注
			dbzxCtValuationSZRules.setRemark(zxCtValuationSZRules.getRemark());
			// 编制时间
			dbzxCtValuationSZRules.setEditTime(zxCtValuationSZRules.getEditTime());
			// 规则库类型
			dbzxCtValuationSZRules.setBaseType(zxCtValuationSZRules.getBaseType());
			// 共通
			dbzxCtValuationSZRules.setModifyUserInfo(userKey, realName);
			flag = zxCtValuationSZRulesMapper.updateByPrimaryKey(dbzxCtValuationSZRules);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxCtValuationSZRules);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCtValuationSZRules(List<ZxCtValuationSZRules> zxCtValuationSZRulesList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtValuationSZRulesList != null && zxCtValuationSZRulesList.size() > 0) {
			ZxCtValuationSZRules zxCtValuationSZRules = new ZxCtValuationSZRules();
			zxCtValuationSZRules.setModifyUserInfo(userKey, realName);
			flag = zxCtValuationSZRulesMapper.batchDeleteUpdateZxCtValuationSZRules(zxCtValuationSZRulesList,
					zxCtValuationSZRules);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxCtValuationSZRulesList);
		}
	}

	@Override
	public ResponseEntity gcsgGetZxCtValuationSZRulesList(ZxCtValuationSZRules zxCtValuationSZRules) {
		if (zxCtValuationSZRules == null) {
			zxCtValuationSZRules = new ZxCtValuationSZRules();
		}
		// 分页查询
		PageHelper.startPage(zxCtValuationSZRules.getPage(), zxCtValuationSZRules.getLimit());
		// 获取数据
		List<ZxCtValuationSZRules> szRulesList = Lists.newArrayList();
		if (StrUtil.equals("contr", zxCtValuationSZRules.getBaseType())) {
			List<ZxCtValuationRules> rulesList = zxCtValuationRulesMapper
					.selectByZxCtValuationRulesList(new ZxCtValuationRules());
			if (rulesList.size() > 0) {
				Iterator<ZxCtValuationRules> it = rulesList.iterator();
				while (it.hasNext()) {
					ZxCtValuationRules dbRules = it.next();
					ZxCtValuationSZRules szRules = new ZxCtValuationSZRules();
					BeanUtil.copyProperties(dbRules, szRules);
					szRulesList.add(szRules);
				}
			}
		} else {
			szRulesList = zxCtValuationSZRulesMapper.selectByZxCtValuationSZRulesList(zxCtValuationSZRules);
		}
		// 得到分页信息
		PageInfo<ZxCtValuationSZRules> p = new PageInfo<>(szRulesList);
		return repEntity.okList(szRulesList, p.getTotal());
	}

}
