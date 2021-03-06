package com.apih5.service.impl;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtValuationRulesMapper;
import com.apih5.mybatis.dao.ZxCtValuationSZRulesMapper;
import com.apih5.mybatis.dao.ZxGcsgCcCoAlterWorkMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrProcessGuajieMapper;
import com.apih5.mybatis.pojo.ZxCtValuationRules;
import com.apih5.mybatis.pojo.ZxCtValuationSZRules;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlterWork;
import com.apih5.mybatis.pojo.ZxGcsgCtContrProcessGuajie;
import com.apih5.service.ZxGcsgCcCoAlterWorkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxGcsgCcCoAlterWorkService")
public class ZxGcsgCcCoAlterWorkServiceImpl implements ZxGcsgCcCoAlterWorkService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxGcsgCcCoAlterWorkMapper zxGcsgCcCoAlterWorkMapper;
	@Autowired(required = true)
	private ZxGcsgCtContrProcessGuajieMapper zxGcsgCtContrProcessGuajieMapper;
	@Autowired(required = true)
	private ZxCtValuationRulesMapper zxCtValuationRulesMapper;
	@Autowired(required = true)
	private ZxCtValuationSZRulesMapper zxCtValuationSZRulesMapper;

	@Override
	public ResponseEntity getZxGcsgCcCoAlterWorkListByCondition(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		if (zxGcsgCcCoAlterWork == null) {
			zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
		}
		// ????????????
		PageHelper.startPage(zxGcsgCcCoAlterWork.getPage(), zxGcsgCcCoAlterWork.getLimit());
		// ????????????
		List<ZxGcsgCcCoAlterWork> zxGcsgCcCoAlterWorkList = zxGcsgCcCoAlterWorkMapper
				.selectByZxGcsgCcCoAlterWorkList(zxGcsgCcCoAlterWork);
		// ??????????????????
		PageInfo<ZxGcsgCcCoAlterWork> p = new PageInfo<>(zxGcsgCcCoAlterWorkList);

		return repEntity.okList(zxGcsgCcCoAlterWorkList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCcCoAlterWorkDetail(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		if (zxGcsgCcCoAlterWork == null) {
			zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
		}
		// ????????????
		ZxGcsgCcCoAlterWork dbZxGcsgCcCoAlterWork = zxGcsgCcCoAlterWorkMapper
				.selectByPrimaryKey(zxGcsgCcCoAlterWork.getCcCoAlterWorkId());
		// ????????????
		if (dbZxGcsgCcCoAlterWork != null) {
			return repEntity.ok(dbZxGcsgCcCoAlterWork);
		} else {
			return repEntity.layerMessage("no", "????????????");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgCcCoAlterWork(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxGcsgCcCoAlterWork.setCcCoAlterWorkId(UuidUtil.generate());
		zxGcsgCcCoAlterWork.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgCcCoAlterWorkMapper.insert(zxGcsgCcCoAlterWork);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCcCoAlterWork);
		}
	}

	@Override
	public ResponseEntity updateZxGcsgCcCoAlterWork(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCcCoAlterWork dbZxGcsgCcCoAlterWork = zxGcsgCcCoAlterWorkMapper
				.selectByPrimaryKey(zxGcsgCcCoAlterWork.getCcCoAlterWorkId());
		if (dbZxGcsgCcCoAlterWork != null && StrUtil.isNotEmpty(dbZxGcsgCcCoAlterWork.getCcCoAlterWorkId())) {
			// ??????ID(alterID)
			dbZxGcsgCcCoAlterWork.setCcCoAlterId(zxGcsgCcCoAlterWork.getCcCoAlterId());
			// ????????????
			dbZxGcsgCcCoAlterWork.setAlterType(zxGcsgCcCoAlterWork.getAlterType());
			// ????????????ID
			dbZxGcsgCcCoAlterWork.setMuID(zxGcsgCcCoAlterWork.getMuID());
			// ??????ID(workID)
			dbZxGcsgCcCoAlterWork.setCcWorksId(zxGcsgCcCoAlterWork.getCcWorksId());
			// ???????????????
			dbZxGcsgCcCoAlterWork.setOriginQty(zxGcsgCcCoAlterWork.getOriginQty());
			// ?????????????????????
			dbZxGcsgCcCoAlterWork.setOriginPrice(zxGcsgCcCoAlterWork.getOriginPrice());
			// ????????????
			dbZxGcsgCcCoAlterWork.setApplyQty(zxGcsgCcCoAlterWork.getApplyQty());
			// ????????????
			dbZxGcsgCcCoAlterWork.setApplyPrice(zxGcsgCcCoAlterWork.getApplyPrice());
			// ??????????????????
			dbZxGcsgCcCoAlterWork.setApplyAddQty(zxGcsgCcCoAlterWork.getApplyAddQty());
			// ????????????
			dbZxGcsgCcCoAlterWork.setReplyQty(zxGcsgCcCoAlterWork.getReplyQty());
			// ????????????
			dbZxGcsgCcCoAlterWork.setReplyPrice(zxGcsgCcCoAlterWork.getReplyPrice());
			// ??????????????????
			dbZxGcsgCcCoAlterWork.setReplyAddQty(zxGcsgCcCoAlterWork.getReplyAddQty());
			// ????????????
			dbZxGcsgCcCoAlterWork.setCombProp(zxGcsgCcCoAlterWork.getCombProp());
			// ???????????????????????????
			dbZxGcsgCcCoAlterWork.setPp1(zxGcsgCcCoAlterWork.getPp1());
			// ????????????
			dbZxGcsgCcCoAlterWork.setPp2(zxGcsgCcCoAlterWork.getPp2());
			// ??????
			dbZxGcsgCcCoAlterWork.setPp3(zxGcsgCcCoAlterWork.getPp3());
			// ????????????
			dbZxGcsgCcCoAlterWork.setPp4(zxGcsgCcCoAlterWork.getPp4());
			// pp5
			dbZxGcsgCcCoAlterWork.setPp5(zxGcsgCcCoAlterWork.getPp5());
			// ???????????????ID
			dbZxGcsgCcCoAlterWork.setPp6(zxGcsgCcCoAlterWork.getPp6());
			// pp7
			dbZxGcsgCcCoAlterWork.setPp7(zxGcsgCcCoAlterWork.getPp7());
			// pp8
			dbZxGcsgCcCoAlterWork.setPp8(zxGcsgCcCoAlterWork.getPp8());
			// pp9
			dbZxGcsgCcCoAlterWork.setPp9(zxGcsgCcCoAlterWork.getPp9());
			// ??????
			dbZxGcsgCcCoAlterWork.setPp10(zxGcsgCcCoAlterWork.getPp10());
			// ???????????????????????????(pp1)
			dbZxGcsgCcCoAlterWork.setCcWorksParentNo(zxGcsgCcCoAlterWork.getCcWorksParentNo());
			// ????????????(pp2)
			dbZxGcsgCcCoAlterWork.setCcWorksName(zxGcsgCcCoAlterWork.getCcWorksName());
			// ??????(pp3)
			dbZxGcsgCcCoAlterWork.setCcWorksUnit(zxGcsgCcCoAlterWork.getCcWorksUnit());
			// ????????????(pp4)
			dbZxGcsgCcCoAlterWork.setCcWorksNo(zxGcsgCcCoAlterWork.getCcWorksNo());
			// ?????????????????????ID(pp6)
			dbZxGcsgCcCoAlterWork.setCcWorksParentId(zxGcsgCcCoAlterWork.getCcWorksParentId());
			// ??????(pp10)
			dbZxGcsgCcCoAlterWork.setMount(zxGcsgCcCoAlterWork.getMount());
			// ??????????????????
			dbZxGcsgCcCoAlterWork.setEditTime(zxGcsgCcCoAlterWork.getEditTime());
			// ??????????????????
			dbZxGcsgCcCoAlterWork.setChangeQty(zxGcsgCcCoAlterWork.getChangeQty());
			// ????????????
			dbZxGcsgCcCoAlterWork.setChangePrice(zxGcsgCcCoAlterWork.getChangePrice());
			// ?????????????????????
			dbZxGcsgCcCoAlterWork.setContractPrice(zxGcsgCcCoAlterWork.getContractPrice());
			// ???????????????
			dbZxGcsgCcCoAlterWork.setAfterChangeQty(zxGcsgCcCoAlterWork.getAfterChangeQty());
			// ???????????????
			dbZxGcsgCcCoAlterWork.setAfterChangePrice(zxGcsgCcCoAlterWork.getAfterChangePrice());
			// ??????????????????
			dbZxGcsgCcCoAlterWork.setIsLeaf(zxGcsgCcCoAlterWork.getIsLeaf());
			// ????????????
			dbZxGcsgCcCoAlterWork.setRequestEdit(zxGcsgCcCoAlterWork.getRequestEdit());
			// ?????????
			dbZxGcsgCcCoAlterWork.setEditUserID(zxGcsgCcCoAlterWork.getEditUserID());
			// ?????????
			dbZxGcsgCcCoAlterWork.setEditUserName(zxGcsgCcCoAlterWork.getEditUserName());
			// ????????????
			dbZxGcsgCcCoAlterWork.setEditDate(zxGcsgCcCoAlterWork.getEditDate());
			// ????????????????????????
			dbZxGcsgCcCoAlterWork.setOriginPriceNoTax(zxGcsgCcCoAlterWork.getOriginPriceNoTax());
			// ????????????????????????
			dbZxGcsgCcCoAlterWork.setContractCostNoTax(zxGcsgCcCoAlterWork.getContractCostNoTax());
			// ??????(%)
			dbZxGcsgCcCoAlterWork.setTaxRate(zxGcsgCcCoAlterWork.getTaxRate());
			// ?????????????????????
			dbZxGcsgCcCoAlterWork.setAfterAmt(zxGcsgCcCoAlterWork.getAfterAmt());
			// ????????????????????????
			dbZxGcsgCcCoAlterWork.setAfterAmtNoTax(zxGcsgCcCoAlterWork.getAfterAmtNoTax());
			// ???????????????
			dbZxGcsgCcCoAlterWork.setAfterAmtTax(zxGcsgCcCoAlterWork.getAfterAmtTax());
			// ????????????ID
			dbZxGcsgCcCoAlterWork.setRuleID(zxGcsgCcCoAlterWork.getRuleID());
			// ??????????????????
			dbZxGcsgCcCoAlterWork.setRuleName(zxGcsgCcCoAlterWork.getRuleName());
			// ??????????????????
			dbZxGcsgCcCoAlterWork.setGjNum(zxGcsgCcCoAlterWork.getGjNum());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField1(zxGcsgCcCoAlterWork.getOpinionField1());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField2(zxGcsgCcCoAlterWork.getOpinionField2());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField3(zxGcsgCcCoAlterWork.getOpinionField3());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField4(zxGcsgCcCoAlterWork.getOpinionField4());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField5(zxGcsgCcCoAlterWork.getOpinionField5());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField6(zxGcsgCcCoAlterWork.getOpinionField6());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField7(zxGcsgCcCoAlterWork.getOpinionField7());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField8(zxGcsgCcCoAlterWork.getOpinionField8());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField9(zxGcsgCcCoAlterWork.getOpinionField9());
			// ??????
			dbZxGcsgCcCoAlterWork.setOpinionField10(zxGcsgCcCoAlterWork.getOpinionField10());
			// ??????id
			dbZxGcsgCcCoAlterWork.setApih5FlowId(zxGcsgCcCoAlterWork.getApih5FlowId());
			// ??????????????????
			dbZxGcsgCcCoAlterWork.setApih5FlowStatus(zxGcsgCcCoAlterWork.getApih5FlowStatus());
			// ????????????
			dbZxGcsgCcCoAlterWork.setApih5FlowNodeStatus(zxGcsgCcCoAlterWork.getApih5FlowNodeStatus());
			// work_id
			dbZxGcsgCcCoAlterWork.setWorkId(zxGcsgCcCoAlterWork.getWorkId());
			// ??????
			dbZxGcsgCcCoAlterWork.setRemarks(zxGcsgCcCoAlterWork.getRemarks());
			// ??????
			dbZxGcsgCcCoAlterWork.setSort(zxGcsgCcCoAlterWork.getSort());
			// ??????
			dbZxGcsgCcCoAlterWork.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcCoAlterWorkMapper.updateByPrimaryKey(dbZxGcsgCcCoAlterWork);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCcCoAlterWork);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxGcsgCcCoAlterWork(List<ZxGcsgCcCoAlterWork> zxGcsgCcCoAlterWorkList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxGcsgCcCoAlterWorkList != null && zxGcsgCcCoAlterWorkList.size() > 0) {
			ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
			zxGcsgCcCoAlterWork.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcCoAlterWorkMapper.batchDeleteUpdateZxGcsgCcCoAlterWork(zxGcsgCcCoAlterWorkList,
					zxGcsgCcCoAlterWork);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCcCoAlterWorkList);
		}
	}

	// ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

	@Override
	public ResponseEntity getZxGcsgCcCoAlterWorkListAmount(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		if (zxGcsgCcCoAlterWork == null) {
			zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
		}
		List<ZxGcsgCcCoAlterWork> zxGcsgCcCoAlterWorkList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCcCoAlterWork.getCtContrApplyId())) {
			zxGcsgCcCoAlterWorkList = zxGcsgCcCoAlterWorkMapper.getZxGcsgCcCoAlterWorkByCondition(zxGcsgCcCoAlterWork);
			// ?????????????????????,????????????
		}
		return repEntity.okList(zxGcsgCcCoAlterWorkList, zxGcsgCcCoAlterWorkList.size());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity manualHookZxGcsgCcCoAlterWorkProcess(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// ????????????????????????????????????
		// ???????????????????????????
		ZxGcsgCcCoAlterWork dbZxGcsgCcWorks = zxGcsgCcCoAlterWorkMapper
				.selectByPrimaryKey(zxGcsgCcCoAlterWork.getCcCoAlterWorkId());
		List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
		// ????????????????????????????????????????????????????????????????????????(????????????????????????)
		if (zxGcsgCcCoAlterWork != null && zxGcsgCcCoAlterWork.getProcessArray() != null
				&& zxGcsgCcCoAlterWork.getProcessArray().size() > 0) {
			JSONArray jsonArr = zxGcsgCcCoAlterWork.getProcessArray();
			for (int i = 0; i < jsonArr.size(); i++) {
				ZxGcsgCtContrProcessGuajie insertData = new ZxGcsgCtContrProcessGuajie();
				insertData.setCtContrProcessGuajieId(UuidUtil.generate());
				insertData.setCcWorksId("");
				insertData.setApplyAlterWorksId(dbZxGcsgCcWorks.getCcCoAlterWorkId());
				insertData.setCtContrApplyId(dbZxGcsgCcWorks.getCtContrApplyId());
				insertData.setCtContractId("");
				insertData.setProcessID(jsonArr.getJSONObject(i).getStr("id"));
				insertData.setProcessNo(jsonArr.getJSONObject(i).getStr("processNo"));
				insertData.setProcessName(jsonArr.getJSONObject(i).getStr("processName"));
				insertData.setProcessUnit(jsonArr.getJSONObject(i).getStr("processUnit"));
				insertData.setBaseType(zxGcsgCcCoAlterWork.getInputWorkType());
				if (jsonArr.getJSONObject(i).getStr("isLeaf") != null
						&& NumberUtil.isNumber(jsonArr.getJSONObject(i).getStr("isLeaf"))) {
					insertData.setIsLeaf(Integer.parseInt(jsonArr.getJSONObject(i).getStr("isLeaf")));
				}
				insertData.setCreateUserInfo(userKey, realName);
				insertList.add(insertData);
			}
		}
		// ????????????????????????id?????????????????????????????????
		if (StrUtil.isNotEmpty(dbZxGcsgCcWorks.getCcCoAlterWorkId())
				&& StrUtil.isNotEmpty(zxGcsgCcCoAlterWork.getInputWorkType())) {
			ZxGcsgCtContrProcessGuajie deleteCondition = new ZxGcsgCtContrProcessGuajie();
			deleteCondition.setApplyAlterWorksId(dbZxGcsgCcWorks.getCcCoAlterWorkId());
			deleteCondition.setBaseType(zxGcsgCcCoAlterWork.getInputWorkType());
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(deleteCondition);
		}
		// ????????????
		if (insertList.size() > 0) {
			flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
		}
//		if (flag == 0) {
//			return repEntity.errorSave();
//		} else {
		return repEntity.ok("sys.data.sava", zxGcsgCcCoAlterWork);
//		}
	}

	@Override
	public ResponseEntity manualHookZxGcsgCcCoAlterWorkRule(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// ???????????????????????????????????????
		ZxGcsgCcCoAlterWork dbZxGcsgCcCoAlterWork = zxGcsgCcCoAlterWorkMapper
				.selectByPrimaryKey(zxGcsgCcCoAlterWork.getCcCoAlterWorkId());
		// ????????????????????????????????????????????????(????????????????????????)
		String ruleName = "";
		if (StrUtil.equals("contr", zxGcsgCcCoAlterWork.getInputWorkType())) {
			// ???????????????????????????
			ZxCtValuationRules dbZxCtValuationRules = zxCtValuationRulesMapper
					.selectByPrimaryKey(zxGcsgCcCoAlterWork.getRuleID());
			ruleName = dbZxCtValuationRules != null ? dbZxCtValuationRules.getRuleName() : "";
		} else {
			ZxCtValuationSZRules dbZxCtValuationSZRules = zxCtValuationSZRulesMapper
					.selectByPrimaryKey(zxGcsgCcCoAlterWork.getRuleID());
			ruleName = dbZxCtValuationSZRules != null ? dbZxCtValuationSZRules.getRuleName() : "";
		}
		if (dbZxGcsgCcCoAlterWork != null) {
			dbZxGcsgCcCoAlterWork.setRuleID(zxGcsgCcCoAlterWork.getRuleID());
			dbZxGcsgCcCoAlterWork.setRuleName(ruleName);
			dbZxGcsgCcCoAlterWork.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcCoAlterWorkMapper.updateByPrimaryKey(dbZxGcsgCcCoAlterWork);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCcCoAlterWork);
		}
	}

	@Override
	public ResponseEntity getZxGcsgCcCoAlterWorkListProcess(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		// ????????????????????????????????????????????????
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if (zxGcsgCcCoAlterWork != null && StrUtil.isNotEmpty(zxGcsgCcCoAlterWork.getCtContrApplyId())) {
			mapList = zxGcsgCcCoAlterWorkMapper.getZxGcsgCcCoAlterWorkListProcess(zxGcsgCcCoAlterWork);
		}
		return repEntity.okList(mapList, mapList.size());
	}

}
