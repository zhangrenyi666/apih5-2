package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxQuarterlyWeightDetailsMapper;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyWeightDetails;
import com.apih5.service.ZjXmJxQuarterlyWeightDetailsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("zjXmJxQuarterlyWeightDetailsService")
public class ZjXmJxQuarterlyWeightDetailsServiceImpl implements ZjXmJxQuarterlyWeightDetailsService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZjXmJxQuarterlyWeightDetailsMapper zjXmJxQuarterlyWeightDetailsMapper;

	@Override
	public ResponseEntity getZjXmJxQuarterlyWeightDetailsListByCondition(
			ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		if (zjXmJxQuarterlyWeightDetails == null) {
			zjXmJxQuarterlyWeightDetails = new ZjXmJxQuarterlyWeightDetails();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxQuarterlyWeightDetails.getPage(), zjXmJxQuarterlyWeightDetails.getLimit());
		// 获取数据
		List<ZjXmJxQuarterlyWeightDetails> zjXmJxQuarterlyWeightDetailsList = zjXmJxQuarterlyWeightDetailsMapper
				.selectByZjXmJxQuarterlyWeightDetailsList(zjXmJxQuarterlyWeightDetails);
		// 得到分页信息
		PageInfo<ZjXmJxQuarterlyWeightDetails> p = new PageInfo<>(zjXmJxQuarterlyWeightDetailsList);

		return repEntity.okList(zjXmJxQuarterlyWeightDetailsList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyWeightDetailsDetail(
			ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		if (zjXmJxQuarterlyWeightDetails == null) {
			zjXmJxQuarterlyWeightDetails = new ZjXmJxQuarterlyWeightDetails();
		}
		// 获取数据
		ZjXmJxQuarterlyWeightDetails dbZjXmJxQuarterlyWeightDetails = zjXmJxQuarterlyWeightDetailsMapper
				.selectByPrimaryKey(zjXmJxQuarterlyWeightDetails.getDetailsId());
		// 数据存在
		if (dbZjXmJxQuarterlyWeightDetails != null) {
			return repEntity.ok(dbZjXmJxQuarterlyWeightDetails);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxQuarterlyWeightDetails(ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxQuarterlyWeightDetails.setDetailsId(UuidUtil.generate());
		zjXmJxQuarterlyWeightDetails.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxQuarterlyWeightDetailsMapper.insert(zjXmJxQuarterlyWeightDetails);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyWeightDetails);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxQuarterlyWeightDetails(
			ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxQuarterlyWeightDetails dbZjXmJxQuarterlyWeightDetails = zjXmJxQuarterlyWeightDetailsMapper
				.selectByPrimaryKey(zjXmJxQuarterlyWeightDetails.getDetailsId());
		if (dbZjXmJxQuarterlyWeightDetails != null
				&& StrUtil.isNotEmpty(dbZjXmJxQuarterlyWeightDetails.getDetailsId())) {
			// 季度考核id
			dbZjXmJxQuarterlyWeightDetails.setAssessmentId(zjXmJxQuarterlyWeightDetails.getAssessmentId());
			// 项目类型id
			dbZjXmJxQuarterlyWeightDetails.setProjectType(zjXmJxQuarterlyWeightDetails.getProjectType());
			// 项目类型名称
			dbZjXmJxQuarterlyWeightDetails.setProjectTypeName(zjXmJxQuarterlyWeightDetails.getProjectTypeName());
			// 路桥事业部权重
			dbZjXmJxQuarterlyWeightDetails.setRoadBridgeWeight(zjXmJxQuarterlyWeightDetails.getRoadBridgeWeight());
			// 城市房建事业部权重
			dbZjXmJxQuarterlyWeightDetails.setHousingWeight(zjXmJxQuarterlyWeightDetails.getHousingWeight());
			// 铁路轨道事业部权重
			dbZjXmJxQuarterlyWeightDetails.setTrackWeight(zjXmJxQuarterlyWeightDetails.getTrackWeight());
			// 技术质量部权重
			dbZjXmJxQuarterlyWeightDetails.setTechnicalWeight(zjXmJxQuarterlyWeightDetails.getTechnicalWeight());
			// 安全监督部权重
			dbZjXmJxQuarterlyWeightDetails.setSafetyWeight(zjXmJxQuarterlyWeightDetails.getSafetyWeight());
			// 经营考核部权重
			dbZjXmJxQuarterlyWeightDetails.setBusinessWeight(zjXmJxQuarterlyWeightDetails.getBusinessWeight());
			// 财务部权重
			dbZjXmJxQuarterlyWeightDetails.setFinanceWeight(zjXmJxQuarterlyWeightDetails.getFinanceWeight());
			// 物资设备部权重
			dbZjXmJxQuarterlyWeightDetails.setMaterialsWeight(zjXmJxQuarterlyWeightDetails.getMaterialsWeight());
			// 人力资源部权重
			dbZjXmJxQuarterlyWeightDetails
					.setHumanResourcesWeight(zjXmJxQuarterlyWeightDetails.getHumanResourcesWeight());
			// 法律部权重
			dbZjXmJxQuarterlyWeightDetails.setLegalWeight(zjXmJxQuarterlyWeightDetails.getLegalWeight());
			// 办公室权重
			dbZjXmJxQuarterlyWeightDetails.setOfficeWeight(zjXmJxQuarterlyWeightDetails.getOfficeWeight());
			// 经营考核部供应链管理部权重
			dbZjXmJxQuarterlyWeightDetails.setSupplyChainWeight(zjXmJxQuarterlyWeightDetails.getSupplyChainWeight());
			// 经营考核部收尾中心权重
			dbZjXmJxQuarterlyWeightDetails
					.setClosingCenterWeight(zjXmJxQuarterlyWeightDetails.getClosingCenterWeight());
			// 是否是收尾项目
			dbZjXmJxQuarterlyWeightDetails.setIsClosed(zjXmJxQuarterlyWeightDetails.getIsClosed());
			// 确认状态
			dbZjXmJxQuarterlyWeightDetails.setConfirmStatus(zjXmJxQuarterlyWeightDetails.getConfirmStatus());
			// 备注
			dbZjXmJxQuarterlyWeightDetails.setRemarks(zjXmJxQuarterlyWeightDetails.getRemarks());
			// 排序
			dbZjXmJxQuarterlyWeightDetails.setSort(zjXmJxQuarterlyWeightDetails.getSort());
			// 共通
			dbZjXmJxQuarterlyWeightDetails.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyWeightDetailsMapper.updateByPrimaryKey(dbZjXmJxQuarterlyWeightDetails);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyWeightDetails);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyWeightDetails(
			List<ZjXmJxQuarterlyWeightDetails> zjXmJxQuarterlyWeightDetailsList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyWeightDetailsList != null && zjXmJxQuarterlyWeightDetailsList.size() > 0) {
			ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails = new ZjXmJxQuarterlyWeightDetails();
			zjXmJxQuarterlyWeightDetails.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyWeightDetailsMapper.batchDeleteUpdateZjXmJxQuarterlyWeightDetails(
					zjXmJxQuarterlyWeightDetailsList, zjXmJxQuarterlyWeightDetails);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyWeightDetailsList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity batchConfirmZjXmJxQuarterlyWeightDetails(
			List<ZjXmJxQuarterlyWeightDetails> zjXmJxQuarterlyWeightDetailsList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyWeightDetailsList != null && zjXmJxQuarterlyWeightDetailsList.size() > 0) {
			flag = zjXmJxQuarterlyWeightDetailsMapper
					.batchConfirmZjXmJxQuarterlyWeightDetails(zjXmJxQuarterlyWeightDetailsList);
		}
		// 失败
		if (flag == 0) {
			return repEntity.layerMessage("no", "确认失败，请重试。");
		} else {
			return repEntity.ok("权重确认成功。");
		}
	}

	@Override
	public ResponseEntity checkZjXmJxQuarterlyWeightDetailsConfirmStatus(
			ZjXmJxQuarterlyWeightDetails zjXmJxQuarterlyWeightDetails) {
		JSONObject obj = JSONUtil.createObj();
		int count = zjXmJxQuarterlyWeightDetailsMapper
				.checkZjXmJxQuarterlyWeightDetailsConfirmStatus(zjXmJxQuarterlyWeightDetails);
		if (count > 0) {
			obj.set("buttonFlag", "show");
		} else {
			obj.set("buttonFlag", "hide");
		}
		// 失败
		return repEntity.ok(obj);
	}

}
