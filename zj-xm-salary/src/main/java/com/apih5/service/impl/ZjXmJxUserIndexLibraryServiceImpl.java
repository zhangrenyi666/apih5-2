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
import com.apih5.mybatis.dao.ZjXmJxIndexLibraryMapper;
import com.apih5.mybatis.dao.ZjXmJxUserIndexLibraryMapper;
import com.apih5.mybatis.pojo.ZjXmJxIndexLibrary;
import com.apih5.mybatis.pojo.ZjXmJxUserIndexLibrary;
import com.apih5.service.ZjXmJxUserIndexLibraryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxUserIndexLibraryService")
public class ZjXmJxUserIndexLibraryServiceImpl implements ZjXmJxUserIndexLibraryService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxUserIndexLibraryMapper zjXmJxUserIndexLibraryMapper;
	@Autowired(required = true)
	private ZjXmJxIndexLibraryMapper zjXmJxIndexLibraryMapper;

	@Override
	public ResponseEntity getZjXmJxUserIndexLibraryListByCondition(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		if (zjXmJxUserIndexLibrary == null) {
			zjXmJxUserIndexLibrary = new ZjXmJxUserIndexLibrary();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxUserIndexLibrary.getPage(), zjXmJxUserIndexLibrary.getLimit());
		// 获取数据
		List<ZjXmJxUserIndexLibrary> zjXmJxUserIndexLibraryList = zjXmJxUserIndexLibraryMapper
				.selectByZjXmJxUserIndexLibraryList(zjXmJxUserIndexLibrary);
		// 得到分页信息
		PageInfo<ZjXmJxUserIndexLibrary> p = new PageInfo<>(zjXmJxUserIndexLibraryList);

		return repEntity.okList(zjXmJxUserIndexLibraryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxUserIndexLibraryDetails(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		if (zjXmJxUserIndexLibrary == null) {
			zjXmJxUserIndexLibrary = new ZjXmJxUserIndexLibrary();
		}
		// 获取数据
		ZjXmJxUserIndexLibrary dbZjXmJxUserIndexLibrary = zjXmJxUserIndexLibraryMapper
				.selectByPrimaryKey(zjXmJxUserIndexLibrary.getLibraryId());
		// 数据存在
		if (dbZjXmJxUserIndexLibrary != null) {
			return repEntity.ok(dbZjXmJxUserIndexLibrary);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxUserIndexLibrary(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxUserIndexLibrary.setLibraryId(UuidUtil.generate());
		zjXmJxUserIndexLibrary.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxUserIndexLibraryMapper.insert(zjXmJxUserIndexLibrary);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxUserIndexLibrary);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxUserIndexLibrary(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxUserIndexLibrary dbzjXmJxUserIndexLibrary = zjXmJxUserIndexLibraryMapper
				.selectByPrimaryKey(zjXmJxUserIndexLibrary.getLibraryId());
		if (dbzjXmJxUserIndexLibrary != null && StrUtil.isNotEmpty(dbzjXmJxUserIndexLibrary.getLibraryId())) {
			// 人员主键
			dbzjXmJxUserIndexLibrary.setUserKey(zjXmJxUserIndexLibrary.getUserKey());
			// 人员姓名
			dbzjXmJxUserIndexLibrary.setRealName(zjXmJxUserIndexLibrary.getRealName());
			// 部门ID
			dbzjXmJxUserIndexLibrary.setDeptId(zjXmJxUserIndexLibrary.getDeptId());
			// 部门名称
			dbzjXmJxUserIndexLibrary.setDeptName(zjXmJxUserIndexLibrary.getDeptName());
			// 项目id
			dbzjXmJxUserIndexLibrary.setProjectId(zjXmJxUserIndexLibrary.getProjectId());
			// 项目名称
			dbzjXmJxUserIndexLibrary.setProjectName(zjXmJxUserIndexLibrary.getProjectName());
			// 成本责任指标
			dbzjXmJxUserIndexLibrary.setCostDutyIndex(zjXmJxUserIndexLibrary.getCostDutyIndex());
			// 目标值
			dbzjXmJxUserIndexLibrary.setTargetValue(zjXmJxUserIndexLibrary.getTargetValue());
			// 评价计分标准
			dbzjXmJxUserIndexLibrary.setScoringStandard(zjXmJxUserIndexLibrary.getScoringStandard());
			// 来源
			dbzjXmJxUserIndexLibrary.setDataSources(zjXmJxUserIndexLibrary.getDataSources());
			// 是否必选
			dbzjXmJxUserIndexLibrary.setIsMandatory(zjXmJxUserIndexLibrary.getIsMandatory());
			// 权重
			dbzjXmJxUserIndexLibrary.setWeightValue(zjXmJxUserIndexLibrary.getWeightValue());
			// 是否自动评分
			dbzjXmJxUserIndexLibrary.setIsAutomaticScoring(zjXmJxUserIndexLibrary.getIsAutomaticScoring());
			// 备注
			dbzjXmJxUserIndexLibrary.setRemarks(zjXmJxUserIndexLibrary.getRemarks());
			// 排序
			dbzjXmJxUserIndexLibrary.setSort(zjXmJxUserIndexLibrary.getSort());
			// 共通
			dbzjXmJxUserIndexLibrary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxUserIndexLibraryMapper.updateByPrimaryKey(dbzjXmJxUserIndexLibrary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxUserIndexLibrary);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxUserIndexLibrary(
			List<ZjXmJxUserIndexLibrary> zjXmJxUserIndexLibraryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxUserIndexLibraryList != null && zjXmJxUserIndexLibraryList.size() > 0) {
			ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary = new ZjXmJxUserIndexLibrary();
			zjXmJxUserIndexLibrary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxUserIndexLibraryMapper.batchDeleteUpdateZjXmJxUserIndexLibrary(zjXmJxUserIndexLibraryList,
					zjXmJxUserIndexLibrary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxUserIndexLibraryList);
		}
	}

	@Override
	public ResponseEntity oneClickPullZjXmJxUserIndexLibrary(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxUserIndexLibrary != null && StrUtil.isNotEmpty(zjXmJxUserIndexLibrary.getDeptId())
				&& StrUtil.isNotEmpty(zjXmJxUserIndexLibrary.getUserKey())
				&& StrUtil.isNotEmpty(zjXmJxUserIndexLibrary.getRealName())) {
			// 根据部门获取基础指标库集合
			ZjXmJxIndexLibrary zjXmJxIndexLibrary = new ZjXmJxIndexLibrary();
			zjXmJxIndexLibrary.setDeptId(zjXmJxUserIndexLibrary.getDeptId());
			List<ZjXmJxIndexLibrary> basicList = zjXmJxIndexLibraryMapper
					.selectByZjXmJxIndexLibraryList(zjXmJxIndexLibrary);
			List<ZjXmJxUserIndexLibrary> insertList = Lists.newArrayList();
			if (basicList.size() > 0) {
				for (ZjXmJxIndexLibrary basicLibrary : basicList) {
					ZjXmJxUserIndexLibrary insertData = new ZjXmJxUserIndexLibrary();
					insertData.setLibraryId(UuidUtil.generate());
					insertData.setUserKey(zjXmJxUserIndexLibrary.getUserKey());
					insertData.setRealName(zjXmJxUserIndexLibrary.getRealName());
					insertData.setDeptId(basicLibrary.getDeptId());
					insertData.setDeptName(basicLibrary.getDeptName());
					insertData.setProjectId(basicLibrary.getProjectId());
					insertData.setProjectName(basicLibrary.getProjectName());
					insertData.setCostDutyIndex(basicLibrary.getCostDutyIndex());
					insertData.setTargetValue(basicLibrary.getTargetValue());
					insertData.setScoringStandard(basicLibrary.getScoringStandard());
					insertData.setDataSources(basicLibrary.getDataSources());
					insertData.setIsMandatory(basicLibrary.getIsMandatory());
					insertData.setWeightValue(basicLibrary.getWeightValue());
					insertData.setIsAutomaticScoring(basicLibrary.getIsAutomaticScoring());
					insertData.setRemarks(basicLibrary.getRemarks());
					insertData.setCreateUserInfo(userKey, realName);
					insertList.add(insertData);
				}
				// 先删除该人员的指标库
				ZjXmJxUserIndexLibrary delete = new ZjXmJxUserIndexLibrary();
				delete.setUserKey(zjXmJxUserIndexLibrary.getUserKey());
				zjXmJxUserIndexLibraryMapper.deleteZjXmJxUserIndexLibraryByCondition(delete);
				// 批量新增
				flag = zjXmJxUserIndexLibraryMapper.batchInsertZjXmJxUserIndexLibrary(insertList);
			}
		} else {
			return repEntity.layerMessage("no", "未获取到部门及人员信息。");
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxUserIndexLibrary);
		}
	}

	@Override
	public ResponseEntity batchSubmitZjXmJxUserIndexLibrary(ZjXmJxUserIndexLibrary zjXmJxUserIndexLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 判断是清空还是保存0删除 1保存
		if (zjXmJxUserIndexLibrary != null && StrUtil.equals("1", zjXmJxUserIndexLibrary.getSaveFlag())
				&& zjXmJxUserIndexLibrary.getUserLibraryList() != null
				&& zjXmJxUserIndexLibrary.getUserLibraryList().size() > 0) {
			BigDecimal sum = BigDecimal.ZERO;
			for (ZjXmJxUserIndexLibrary userIndexLibrary : zjXmJxUserIndexLibrary.getUserLibraryList()) {
				if (userIndexLibrary.getWeightValue() == null) {
					return repEntity.layerMessage("no", "权重值不能为空。");
				}
				userIndexLibrary.setLibraryId(UuidUtil.generate());
				userIndexLibrary.setCreateUserInfo(userKey, realName);
				sum = CalcUtils.calcAdd(sum, new BigDecimal(userIndexLibrary.getWeightValue()));
			}
			// check权重和必须为100
			if (sum.compareTo(new BigDecimal(100)) != 0) {
				return repEntity.layerMessage("no", "权重之和必须为100。");
			}
			// 先删除该部门的该人员的指标库
			ZjXmJxUserIndexLibrary delete = new ZjXmJxUserIndexLibrary();
			delete.setUserKey(zjXmJxUserIndexLibrary.getUserLibraryList().get(0).getUserKey());
			delete.setDeptId(zjXmJxUserIndexLibrary.getUserLibraryList().get(0).getDeptId());
			zjXmJxUserIndexLibraryMapper.deleteZjXmJxUserIndexLibraryByCondition(delete);
			// 批量新增
			flag = zjXmJxUserIndexLibraryMapper
					.batchInsertZjXmJxUserIndexLibrary(zjXmJxUserIndexLibrary.getUserLibraryList());
		} else if (zjXmJxUserIndexLibrary != null && StrUtil.equals("0", zjXmJxUserIndexLibrary.getSaveFlag())
				&& StrUtil.isNotEmpty(zjXmJxUserIndexLibrary.getUserKey())
				&& StrUtil.isNotEmpty(zjXmJxUserIndexLibrary.getDeptId())) {
			// 做清空操作，删除该部门的该人员的指标库
			ZjXmJxUserIndexLibrary delete = new ZjXmJxUserIndexLibrary();
			delete.setUserKey(zjXmJxUserIndexLibrary.getUserKey());
			delete.setDeptId(zjXmJxUserIndexLibrary.getDeptId());
			flag = zjXmJxUserIndexLibraryMapper.deleteZjXmJxUserIndexLibraryByCondition(delete);
		} else {
			return repEntity.layerMessage("no", "保存参数不正确。");
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxUserIndexLibrary.getUserLibraryList());
		}
	}
}
