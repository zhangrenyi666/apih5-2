package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyAssessmentMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyIndexLibraryMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyLibraryDetailsMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyWeightDetailsMapper;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyWeightManagementMapper;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyAssessment;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyIndexLibrary;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyLibraryDetails;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightDetails;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightManagement;
import com.apih5.service.ZjXmJxQuarterlyAssessmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxQuarterlyAssessmentService")
public class ZjXmJxQuarterlyAssessmentServiceImpl implements ZjXmJxQuarterlyAssessmentService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxQuarterlyAssessmentMapper zjXmJxQuarterlyAssessmentMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyIndexLibraryMapper zjXmJxQuarterlyIndexLibraryMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyLibraryDetailsMapper zjXmJxQuarterlyLibraryDetailsMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyWeightManagementMapper zjXmJxQuarterlyWeightManagementMapper;
	@Autowired(required = true)
	private ZjXmJxQuarterlyWeightDetailsMapper zjXmJxQuarterlyWeightDetailsMapper;

	@Override
	public ResponseEntity getZjXmJxQuarterlyAssessmentListByCondition(
			ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		if (zjXmJxQuarterlyAssessment == null) {
			zjXmJxQuarterlyAssessment = new ZjXmJxQuarterlyAssessment();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxQuarterlyAssessment.getPage(), zjXmJxQuarterlyAssessment.getLimit());
		// 获取数据
		List<ZjXmJxQuarterlyAssessment> zjXmJxQuarterlyAssessmentList = zjXmJxQuarterlyAssessmentMapper
				.selectByZjXmJxQuarterlyAssessmentList(zjXmJxQuarterlyAssessment);
		// 得到分页信息
		PageInfo<ZjXmJxQuarterlyAssessment> p = new PageInfo<>(zjXmJxQuarterlyAssessmentList);

		return repEntity.okList(zjXmJxQuarterlyAssessmentList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyAssessmentDetail(ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		if (zjXmJxQuarterlyAssessment == null) {
			zjXmJxQuarterlyAssessment = new ZjXmJxQuarterlyAssessment();
		}
		// 获取数据
		ZjXmJxQuarterlyAssessment dbZjXmJxQuarterlyAssessment = zjXmJxQuarterlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxQuarterlyAssessment.getAssessmentId());
		// 数据存在
		if (dbZjXmJxQuarterlyAssessment != null) {
			return repEntity.ok(dbZjXmJxQuarterlyAssessment);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxQuarterlyAssessment(ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxQuarterlyAssessment.setAssessmentId(UuidUtil.generate());
		zjXmJxQuarterlyAssessment.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxQuarterlyAssessmentMapper.insert(zjXmJxQuarterlyAssessment);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyAssessment);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxQuarterlyAssessment(ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxQuarterlyAssessment dbZjXmJxQuarterlyAssessment = zjXmJxQuarterlyAssessmentMapper
				.selectByPrimaryKey(zjXmJxQuarterlyAssessment.getAssessmentId());
		if (dbZjXmJxQuarterlyAssessment != null && StrUtil.isNotEmpty(dbZjXmJxQuarterlyAssessment.getAssessmentId())) {
			// 年月
			dbZjXmJxQuarterlyAssessment.setYearMonth(zjXmJxQuarterlyAssessment.getYearMonth());
			// 第几季度
			dbZjXmJxQuarterlyAssessment.setQuarter(zjXmJxQuarterlyAssessment.getQuarter());
			// 季度考核标题
			dbZjXmJxQuarterlyAssessment.setAssessmentTitle(zjXmJxQuarterlyAssessment.getAssessmentTitle());
			// 通知内容
			dbZjXmJxQuarterlyAssessment.setNoticeContent(zjXmJxQuarterlyAssessment.getNoticeContent());
			// 通知状态
			dbZjXmJxQuarterlyAssessment.setNoticeStatus(zjXmJxQuarterlyAssessment.getNoticeStatus());
			// 考核状态
			dbZjXmJxQuarterlyAssessment.setAssessmentStatus(zjXmJxQuarterlyAssessment.getAssessmentStatus());
			// 备注
			dbZjXmJxQuarterlyAssessment.setRemarks(zjXmJxQuarterlyAssessment.getRemarks());
			// 排序
			dbZjXmJxQuarterlyAssessment.setSort(zjXmJxQuarterlyAssessment.getSort());
			// 共通
			dbZjXmJxQuarterlyAssessment.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyAssessmentMapper.updateByPrimaryKey(dbZjXmJxQuarterlyAssessment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyAssessment);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyAssessment(
			List<ZjXmJxQuarterlyAssessment> zjXmJxQuarterlyAssessmentList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyAssessmentList != null && zjXmJxQuarterlyAssessmentList.size() > 0) {
			ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment = new ZjXmJxQuarterlyAssessment();
			zjXmJxQuarterlyAssessment.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyAssessmentMapper.batchDeleteUpdateZjXmJxQuarterlyAssessment(
					zjXmJxQuarterlyAssessmentList, zjXmJxQuarterlyAssessment);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyAssessmentList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity cascadeAddZjXmJxQuarterlyAssessment(ZjXmJxQuarterlyAssessment zjXmJxQuarterlyAssessment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxQuarterlyAssessment.setAssessmentId(UuidUtil.generate());
		zjXmJxQuarterlyAssessment.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxQuarterlyAssessmentMapper.insert(zjXmJxQuarterlyAssessment);
		if (flag != 0) {
			// 新增该季度考核指标库详情表
			List<ZjXmJxQuarterlyIndexLibrary> basicLibraryList = zjXmJxQuarterlyIndexLibraryMapper
					.selectByZjXmJxQuarterlyIndexLibraryList(new ZjXmJxQuarterlyIndexLibrary());
			if (basicLibraryList.size() > 0) {
				List<ZjXmJxQuarterlyLibraryDetails> insertList = Lists.newArrayList();
				for (ZjXmJxQuarterlyIndexLibrary basicLibrary : basicLibraryList) {
					ZjXmJxQuarterlyLibraryDetails insertLibrary = new ZjXmJxQuarterlyLibraryDetails();
					BeanUtil.copyProperties(basicLibrary, insertLibrary);
					insertLibrary.setDetailsId(UuidUtil.generate());
					insertLibrary.setAssessmentId(zjXmJxQuarterlyAssessment.getAssessmentId());
					insertLibrary.setConfirmStatus("0");
					insertLibrary.setCreateUserInfo(userKey, realName);
					insertList.add(insertLibrary);
				}
				flag = zjXmJxQuarterlyLibraryDetailsMapper.batchInsertZjXmJxQuarterlyLibraryDetails(insertList);
			}
			// 新增该季度考核权重详情表
			List<ZjXmJxQuarterlyWeightManagement> basicWeightList = zjXmJxQuarterlyWeightManagementMapper
					.selectByZjXmJxQuarterlyWeightManagementList(new ZjXmJxQuarterlyWeightManagement());
			if (basicWeightList.size() > 0) {
				List<ZjXmJxQuarterlyWeightDetails> insertList = Lists.newArrayList();
				for (ZjXmJxQuarterlyWeightManagement basicWeight : basicWeightList) {
					ZjXmJxQuarterlyWeightDetails insertWeight = new ZjXmJxQuarterlyWeightDetails();
					BeanUtil.copyProperties(basicWeight, insertWeight);
					insertWeight.setDetailsId(UuidUtil.generate());
					insertWeight.setAssessmentId(zjXmJxQuarterlyAssessment.getAssessmentId());
					insertWeight.setConfirmStatus("0");
					insertList.add(insertWeight);
				}
				flag = zjXmJxQuarterlyWeightDetailsMapper.batchInsertZjXmJxQuarterlyWeightDetails(insertList);
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyAssessment);
		}
	}
}
