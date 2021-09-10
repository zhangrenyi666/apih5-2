package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxWeightManagementMapper;
import com.apih5.mybatis.pojo.ZjXmJxWeightManagement;
import com.apih5.service.ZjXmJxWeightManagementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxWeightManagementService")
public class ZjXmJxWeightManagementServiceImpl implements ZjXmJxWeightManagementService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmJxWeightManagementMapper zjXmJxWeightManagementMapper;

	@Override
	public ResponseEntity getZjXmJxWeightManagementListByCondition(ZjXmJxWeightManagement zjXmJxWeightManagement) {
		if (zjXmJxWeightManagement == null) {
			zjXmJxWeightManagement = new ZjXmJxWeightManagement();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxWeightManagement.getPage(), zjXmJxWeightManagement.getLimit());
		// 获取数据
		List<ZjXmJxWeightManagement> zjXmJxWeightManagementList = zjXmJxWeightManagementMapper
				.selectByZjXmJxWeightManagementList(zjXmJxWeightManagement);
		// 得到分页信息
		PageInfo<ZjXmJxWeightManagement> p = new PageInfo<>(zjXmJxWeightManagementList);

		return repEntity.okList(zjXmJxWeightManagementList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxWeightManagementDetails(ZjXmJxWeightManagement zjXmJxWeightManagement) {
		if (zjXmJxWeightManagement == null) {
			zjXmJxWeightManagement = new ZjXmJxWeightManagement();
		}
		// 获取数据
		ZjXmJxWeightManagement dbZjXmJxWeightManagement = zjXmJxWeightManagementMapper
				.selectByPrimaryKey(zjXmJxWeightManagement.getManagementId());
		// 数据存在
		if (dbZjXmJxWeightManagement != null) {
			return repEntity.ok(dbZjXmJxWeightManagement);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxWeightManagement(ZjXmJxWeightManagement zjXmJxWeightManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// check每个年月只能新增一个权重管理
		if (zjXmJxWeightManagement.getYearMonth() != null) {
			int count = zjXmJxWeightManagementMapper.countZjXmJxWeightManagementByYearMonth(zjXmJxWeightManagement);
			if (count > 0) {
				return repEntity.layerMessage("no", "当前年月已设置权重,不要重复添加。");
			}
		}
		// 各项权重之和为100
		BigDecimal sum1 = CalcUtils.calcAdd(new BigDecimal(zjXmJxWeightManagement.getTaskWeight()),
				new BigDecimal(zjXmJxWeightManagement.getPeripheryWeight()));
		BigDecimal sum2 = CalcUtils.calcAdd(sum1, new BigDecimal(zjXmJxWeightManagement.getPrincipalWeight()));
		if (sum2.compareTo(new BigDecimal(100)) != 0) {
			return repEntity.layerMessage("no", "各项权重之和应为100。");
		}
		zjXmJxWeightManagement.setManagementId(UuidUtil.generate());
		zjXmJxWeightManagement.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxWeightManagementMapper.insert(zjXmJxWeightManagement);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxWeightManagement);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxWeightManagement(ZjXmJxWeightManagement zjXmJxWeightManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// check每个年月只能新增一个权重管理（年月不应该修改）
		// 各项权重之和为100
		BigDecimal sum1 = CalcUtils.calcAdd(new BigDecimal(zjXmJxWeightManagement.getTaskWeight()),
				new BigDecimal(zjXmJxWeightManagement.getPeripheryWeight()));
		BigDecimal sum2 = CalcUtils.calcAdd(sum1, new BigDecimal(zjXmJxWeightManagement.getPrincipalWeight()));
		if (sum2.compareTo(new BigDecimal(100)) != 0) {
			return repEntity.layerMessage("no", "各项权重之和应为100。");
		}
		ZjXmJxWeightManagement dbzjXmJxWeightManagement = zjXmJxWeightManagementMapper
				.selectByPrimaryKey(zjXmJxWeightManagement.getManagementId());
		if (dbzjXmJxWeightManagement != null && StrUtil.isNotEmpty(dbzjXmJxWeightManagement.getManagementId())) {
			// 任务考核权重
			dbzjXmJxWeightManagement.setTaskWeight(zjXmJxWeightManagement.getTaskWeight());
			// 周边业绩考核权重
			dbzjXmJxWeightManagement.setPeripheryWeight(zjXmJxWeightManagement.getPeripheryWeight());
			// 项目正职考核权重
			dbzjXmJxWeightManagement.setPrincipalWeight(zjXmJxWeightManagement.getPrincipalWeight());
			// 综合评价考核权重
			dbzjXmJxWeightManagement.setEvaluateWeight(zjXmJxWeightManagement.getEvaluateWeight());
			// 年月
			dbzjXmJxWeightManagement.setYearMonth(zjXmJxWeightManagement.getYearMonth());
			// 备注
			dbzjXmJxWeightManagement.setRemarks(zjXmJxWeightManagement.getRemarks());
			// 排序
			dbzjXmJxWeightManagement.setSort(zjXmJxWeightManagement.getSort());
			// 共通
			dbzjXmJxWeightManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmJxWeightManagementMapper.updateByPrimaryKey(dbzjXmJxWeightManagement);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxWeightManagement);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxWeightManagement(
			List<ZjXmJxWeightManagement> zjXmJxWeightManagementList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxWeightManagementList != null && zjXmJxWeightManagementList.size() > 0) {
			ZjXmJxWeightManagement zjXmJxWeightManagement = new ZjXmJxWeightManagement();
			zjXmJxWeightManagement.setModifyUserInfo(userKey, realName);
			flag = zjXmJxWeightManagementMapper.batchDeleteUpdateZjXmJxWeightManagement(zjXmJxWeightManagementList,
					zjXmJxWeightManagement);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxWeightManagementList);
		}
	}
}
