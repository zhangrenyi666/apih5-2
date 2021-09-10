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
		// 分页查询
		PageHelper.startPage(zxGcsgCcCoAlterWork.getPage(), zxGcsgCcCoAlterWork.getLimit());
		// 获取数据
		List<ZxGcsgCcCoAlterWork> zxGcsgCcCoAlterWorkList = zxGcsgCcCoAlterWorkMapper
				.selectByZxGcsgCcCoAlterWorkList(zxGcsgCcCoAlterWork);
		// 得到分页信息
		PageInfo<ZxGcsgCcCoAlterWork> p = new PageInfo<>(zxGcsgCcCoAlterWorkList);

		return repEntity.okList(zxGcsgCcCoAlterWorkList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCcCoAlterWorkDetail(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		if (zxGcsgCcCoAlterWork == null) {
			zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
		}
		// 获取数据
		ZxGcsgCcCoAlterWork dbZxGcsgCcCoAlterWork = zxGcsgCcCoAlterWorkMapper
				.selectByPrimaryKey(zxGcsgCcCoAlterWork.getCcCoAlterWorkId());
		// 数据存在
		if (dbZxGcsgCcCoAlterWork != null) {
			return repEntity.ok(dbZxGcsgCcCoAlterWork);
		} else {
			return repEntity.layerMessage("no", "无数据！");
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
			// 变更ID(alterID)
			dbZxGcsgCcCoAlterWork.setCcCoAlterId(zxGcsgCcCoAlterWork.getCcCoAlterId());
			// 变更类型
			dbZxGcsgCcCoAlterWork.setAlterType(zxGcsgCcCoAlterWork.getAlterType());
			// 管理单元ID
			dbZxGcsgCcCoAlterWork.setMuID(zxGcsgCcCoAlterWork.getMuID());
			// 清单ID(workID)
			dbZxGcsgCcCoAlterWork.setCcWorksId(zxGcsgCcCoAlterWork.getCcWorksId());
			// 原合同数量
			dbZxGcsgCcCoAlterWork.setOriginQty(zxGcsgCcCoAlterWork.getOriginQty());
			// 原含税合同单价
			dbZxGcsgCcCoAlterWork.setOriginPrice(zxGcsgCcCoAlterWork.getOriginPrice());
			// 申报数量
			dbZxGcsgCcCoAlterWork.setApplyQty(zxGcsgCcCoAlterWork.getApplyQty());
			// 申报单价
			dbZxGcsgCcCoAlterWork.setApplyPrice(zxGcsgCcCoAlterWork.getApplyPrice());
			// 申报新增数量
			dbZxGcsgCcCoAlterWork.setApplyAddQty(zxGcsgCcCoAlterWork.getApplyAddQty());
			// 批复数量
			dbZxGcsgCcCoAlterWork.setReplyQty(zxGcsgCcCoAlterWork.getReplyQty());
			// 批复单价
			dbZxGcsgCcCoAlterWork.setReplyPrice(zxGcsgCcCoAlterWork.getReplyPrice());
			// 批复新增数量
			dbZxGcsgCcCoAlterWork.setReplyAddQty(zxGcsgCcCoAlterWork.getReplyAddQty());
			// 废弃字段
			dbZxGcsgCcCoAlterWork.setCombProp(zxGcsgCcCoAlterWork.getCombProp());
			// 归属主合同清单编号
			dbZxGcsgCcCoAlterWork.setPp1(zxGcsgCcCoAlterWork.getPp1());
			// 清单名称
			dbZxGcsgCcCoAlterWork.setPp2(zxGcsgCcCoAlterWork.getPp2());
			// 单位
			dbZxGcsgCcCoAlterWork.setPp3(zxGcsgCcCoAlterWork.getPp3());
			// 清单编号
			dbZxGcsgCcCoAlterWork.setPp4(zxGcsgCcCoAlterWork.getPp4());
			// pp5
			dbZxGcsgCcCoAlterWork.setPp5(zxGcsgCcCoAlterWork.getPp5());
			// 清单父节点ID
			dbZxGcsgCcCoAlterWork.setPp6(zxGcsgCcCoAlterWork.getPp6());
			// pp7
			dbZxGcsgCcCoAlterWork.setPp7(zxGcsgCcCoAlterWork.getPp7());
			// pp8
			dbZxGcsgCcCoAlterWork.setPp8(zxGcsgCcCoAlterWork.getPp8());
			// pp9
			dbZxGcsgCcCoAlterWork.setPp9(zxGcsgCcCoAlterWork.getPp9());
			// 挂接
			dbZxGcsgCcCoAlterWork.setPp10(zxGcsgCcCoAlterWork.getPp10());
			// 归属主合同清单编号(pp1)
			dbZxGcsgCcCoAlterWork.setCcWorksParentNo(zxGcsgCcCoAlterWork.getCcWorksParentNo());
			// 清单名称(pp2)
			dbZxGcsgCcCoAlterWork.setCcWorksName(zxGcsgCcCoAlterWork.getCcWorksName());
			// 单位(pp3)
			dbZxGcsgCcCoAlterWork.setCcWorksUnit(zxGcsgCcCoAlterWork.getCcWorksUnit());
			// 清单编号(pp4)
			dbZxGcsgCcCoAlterWork.setCcWorksNo(zxGcsgCcCoAlterWork.getCcWorksNo());
			// 归属主合同清单ID(pp6)
			dbZxGcsgCcCoAlterWork.setCcWorksParentId(zxGcsgCcCoAlterWork.getCcWorksParentId());
			// 挂接(pp10)
			dbZxGcsgCcCoAlterWork.setMount(zxGcsgCcCoAlterWork.getMount());
			// 最后编辑时间
			dbZxGcsgCcCoAlterWork.setEditTime(zxGcsgCcCoAlterWork.getEditTime());
			// 变更增减数量
			dbZxGcsgCcCoAlterWork.setChangeQty(zxGcsgCcCoAlterWork.getChangeQty());
			// 增减单价
			dbZxGcsgCcCoAlterWork.setChangePrice(zxGcsgCcCoAlterWork.getChangePrice());
			// 原含税合同金额
			dbZxGcsgCcCoAlterWork.setContractPrice(zxGcsgCcCoAlterWork.getContractPrice());
			// 变更后数量
			dbZxGcsgCcCoAlterWork.setAfterChangeQty(zxGcsgCcCoAlterWork.getAfterChangeQty());
			// 变更后单价
			dbZxGcsgCcCoAlterWork.setAfterChangePrice(zxGcsgCcCoAlterWork.getAfterChangePrice());
			// 是否叶子节点
			dbZxGcsgCcCoAlterWork.setIsLeaf(zxGcsgCcCoAlterWork.getIsLeaf());
			// 要求修改
			dbZxGcsgCcCoAlterWork.setRequestEdit(zxGcsgCcCoAlterWork.getRequestEdit());
			// 修改人
			dbZxGcsgCcCoAlterWork.setEditUserID(zxGcsgCcCoAlterWork.getEditUserID());
			// 修改人
			dbZxGcsgCcCoAlterWork.setEditUserName(zxGcsgCcCoAlterWork.getEditUserName());
			// 修改日期
			dbZxGcsgCcCoAlterWork.setEditDate(zxGcsgCcCoAlterWork.getEditDate());
			// 原不含税合同单价
			dbZxGcsgCcCoAlterWork.setOriginPriceNoTax(zxGcsgCcCoAlterWork.getOriginPriceNoTax());
			// 原不含税合同金额
			dbZxGcsgCcCoAlterWork.setContractCostNoTax(zxGcsgCcCoAlterWork.getContractCostNoTax());
			// 税率(%)
			dbZxGcsgCcCoAlterWork.setTaxRate(zxGcsgCcCoAlterWork.getTaxRate());
			// 变更后含税金额
			dbZxGcsgCcCoAlterWork.setAfterAmt(zxGcsgCcCoAlterWork.getAfterAmt());
			// 变更后不含税金额
			dbZxGcsgCcCoAlterWork.setAfterAmtNoTax(zxGcsgCcCoAlterWork.getAfterAmtNoTax());
			// 变更后税额
			dbZxGcsgCcCoAlterWork.setAfterAmtTax(zxGcsgCcCoAlterWork.getAfterAmtTax());
			// 计价规则ID
			dbZxGcsgCcCoAlterWork.setRuleID(zxGcsgCcCoAlterWork.getRuleID());
			// 计价规则名称
			dbZxGcsgCcCoAlterWork.setRuleName(zxGcsgCcCoAlterWork.getRuleName());
			// 已挂接工序数
			dbZxGcsgCcCoAlterWork.setGjNum(zxGcsgCcCoAlterWork.getGjNum());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField1(zxGcsgCcCoAlterWork.getOpinionField1());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField2(zxGcsgCcCoAlterWork.getOpinionField2());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField3(zxGcsgCcCoAlterWork.getOpinionField3());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField4(zxGcsgCcCoAlterWork.getOpinionField4());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField5(zxGcsgCcCoAlterWork.getOpinionField5());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField6(zxGcsgCcCoAlterWork.getOpinionField6());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField7(zxGcsgCcCoAlterWork.getOpinionField7());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField8(zxGcsgCcCoAlterWork.getOpinionField8());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField9(zxGcsgCcCoAlterWork.getOpinionField9());
			// 备注
			dbZxGcsgCcCoAlterWork.setOpinionField10(zxGcsgCcCoAlterWork.getOpinionField10());
			// 流程id
			dbZxGcsgCcCoAlterWork.setApih5FlowId(zxGcsgCcCoAlterWork.getApih5FlowId());
			// 工序审核状态
			dbZxGcsgCcCoAlterWork.setApih5FlowStatus(zxGcsgCcCoAlterWork.getApih5FlowStatus());
			// 流程状态
			dbZxGcsgCcCoAlterWork.setApih5FlowNodeStatus(zxGcsgCcCoAlterWork.getApih5FlowNodeStatus());
			// work_id
			dbZxGcsgCcCoAlterWork.setWorkId(zxGcsgCcCoAlterWork.getWorkId());
			// 备注
			dbZxGcsgCcCoAlterWork.setRemarks(zxGcsgCcCoAlterWork.getRemarks());
			// 排序
			dbZxGcsgCcCoAlterWork.setSort(zxGcsgCcCoAlterWork.getSort());
			// 共通
			dbZxGcsgCcCoAlterWork.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcCoAlterWorkMapper.updateByPrimaryKey(dbZxGcsgCcCoAlterWork);
		}
		// 失败
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
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCcCoAlterWorkList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity getZxGcsgCcCoAlterWorkListAmount(ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork) {
		if (zxGcsgCcCoAlterWork == null) {
			zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
		}
		List<ZxGcsgCcCoAlterWork> zxGcsgCcCoAlterWorkList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCcCoAlterWork.getCtContrApplyId())) {
			zxGcsgCcCoAlterWorkList = zxGcsgCcCoAlterWorkMapper.getZxGcsgCcCoAlterWorkByCondition(zxGcsgCcCoAlterWork);
			// 金额已经持久化,不必计算
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
		// 选中一条清单进行挂接工序
		// 获取需要挂接的清单
		ZxGcsgCcCoAlterWork dbZxGcsgCcWorks = zxGcsgCcCoAlterWorkMapper
				.selectByPrimaryKey(zxGcsgCcCoAlterWork.getCcCoAlterWorkId());
		List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
		// 获取选中的公路、房建、铁路、轨道、市政标准工序库(这里需要前端传入)
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
		// 根据补充协议清单id、工序库类型删除关系表
		if (StrUtil.isNotEmpty(dbZxGcsgCcWorks.getCcCoAlterWorkId())
				&& StrUtil.isNotEmpty(zxGcsgCcCoAlterWork.getInputWorkType())) {
			ZxGcsgCtContrProcessGuajie deleteCondition = new ZxGcsgCtContrProcessGuajie();
			deleteCondition.setApplyAlterWorksId(dbZxGcsgCcWorks.getCcCoAlterWorkId());
			deleteCondition.setBaseType(zxGcsgCcCoAlterWork.getInputWorkType());
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(deleteCondition);
		}
		// 然后新增
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
		// 获取需要挂接计价规则的清单
		ZxGcsgCcCoAlterWork dbZxGcsgCcCoAlterWork = zxGcsgCcCoAlterWorkMapper
				.selectByPrimaryKey(zxGcsgCcCoAlterWork.getCcCoAlterWorkId());
		// 根据选中的工序库类型获取计价规则(这里需要前端传入)
		String ruleName = "";
		if (StrUtil.equals("contr", zxGcsgCcCoAlterWork.getInputWorkType())) {
			// 调取分包计价规则库
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
		// 获取该补充协议的清单工序挂接台账
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if (zxGcsgCcCoAlterWork != null && StrUtil.isNotEmpty(zxGcsgCcCoAlterWork.getCtContrApplyId())) {
			mapList = zxGcsgCcCoAlterWorkMapper.getZxGcsgCcCoAlterWorkListProcess(zxGcsgCcCoAlterWork);
		}
		return repEntity.okList(mapList, mapList.size());
	}

}
