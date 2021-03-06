package com.apih5.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrProcessMapper;
import com.apih5.mybatis.dao.ZxCtSZProcessMapper;
import com.apih5.mybatis.dao.ZxCtValuationRulesMapper;
import com.apih5.mybatis.dao.ZxCtValuationSZRulesMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrApplyMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrApplyWorksMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrProcessGuajieMapper;
import com.apih5.mybatis.dao.ZxGcsgCtPriceSysMapper;
import com.apih5.mybatis.pojo.ZxCtContrProcess;
import com.apih5.mybatis.pojo.ZxCtSZProcess;
import com.apih5.mybatis.pojo.ZxCtValuationRules;
import com.apih5.mybatis.pojo.ZxCtValuationSZRules;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApply;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApplyWorks;
import com.apih5.mybatis.pojo.ZxGcsgCtContrProcessGuajie;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSys;
import com.apih5.service.ZxGcsgCtContrApplyWorksService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zxGcsgCtContrApplyWorksService")
public class ZxGcsgCtContrApplyWorksServiceImpl implements ZxGcsgCtContrApplyWorksService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxGcsgCtContrApplyWorksMapper zxGcsgCtContrApplyWorksMapper;
	@Autowired(required = true)
	private ZxGcsgCtContrApplyMapper zxGcsgCtContrApplyMapper;
	@Autowired(required = true)
	private ZxCtContrProcessMapper zxCtContrProcessMapper;
	@Autowired(required = true)
	private ZxCtSZProcessMapper zxCtSZProcessMapper;
	@Autowired(required = true)
	private ZxGcsgCtContrProcessGuajieMapper zxGcsgCtContrProcessGuajieMapper;
	@Autowired(required = true)
	private ZxCtValuationRulesMapper zxCtValuationRulesMapper;
	@Autowired(required = true)
	private ZxCtValuationSZRulesMapper zxCtValuationSZRulesMapper;
	@Autowired(required = true)
	private ZxGcsgCtPriceSysMapper zxGcsgCtPriceSysMapper;

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksListByCondition(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		if (zxGcsgCtContrApplyWorks == null) {
			zxGcsgCtContrApplyWorks = new ZxGcsgCtContrApplyWorks();
		}
		// ????????????
		PageHelper.startPage(zxGcsgCtContrApplyWorks.getPage(), zxGcsgCtContrApplyWorks.getLimit());
		// ????????????
		List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList = zxGcsgCtContrApplyWorksMapper
				.selectByZxGcsgCtContrApplyWorksList(zxGcsgCtContrApplyWorks);
		// ??????????????????
		PageInfo<ZxGcsgCtContrApplyWorks> p = new PageInfo<>(zxGcsgCtContrApplyWorksList);

		return repEntity.okList(zxGcsgCtContrApplyWorksList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksDetail(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		if (zxGcsgCtContrApplyWorks == null) {
			zxGcsgCtContrApplyWorks = new ZxGcsgCtContrApplyWorks();
		}
		// ????????????
		ZxGcsgCtContrApplyWorks dbZxGcsgCtContrApplyWorks = zxGcsgCtContrApplyWorksMapper
				.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getCtContrApplyWorksId());
		// ????????????
		if (dbZxGcsgCtContrApplyWorks != null) {
			return repEntity.ok(dbZxGcsgCtContrApplyWorks);
		} else {
			return repEntity.layerMessage("no", "????????????");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxGcsgCtContrApplyWorks.setCtContrApplyWorksId(UuidUtil.generate());
		zxGcsgCtContrApplyWorks.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgCtContrApplyWorksMapper.insert(zxGcsgCtContrApplyWorks);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContrApplyWorks);
		}
	}

	@Override
	public ResponseEntity updateZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtContrApplyWorks dbZxGcsgCtContrApplyWorks = zxGcsgCtContrApplyWorksMapper
				.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getCtContrApplyWorksId());
		if (dbZxGcsgCtContrApplyWorks != null
				&& StrUtil.isNotEmpty(dbZxGcsgCtContrApplyWorks.getCtContrApplyWorksId())) {
			// ?????????ID
			dbZxGcsgCtContrApplyWorks.setParentID(zxGcsgCtContrApplyWorks.getParentID());
			// ???????????????
			dbZxGcsgCtContrApplyWorks.setTreeNode(zxGcsgCtContrApplyWorks.getTreeNode());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setOrgID(zxGcsgCtContrApplyWorks.getOrgID());
			// ????????????ID(contrApplyID)
			dbZxGcsgCtContrApplyWorks.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setWorkType(zxGcsgCtContrApplyWorks.getWorkType());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setWorkNo(zxGcsgCtContrApplyWorks.getWorkNo());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setWorkName(zxGcsgCtContrApplyWorks.getWorkName());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setUnit(zxGcsgCtContrApplyWorks.getUnit());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setContractPrice(zxGcsgCtContrApplyWorks.getContractPrice());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setContractQty(zxGcsgCtContrApplyWorks.getContractQty());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setContractAmt(zxGcsgCtContrApplyWorks.getContractAmt());
			// ?????????
			dbZxGcsgCtContrApplyWorks.setQuantity(zxGcsgCtContrApplyWorks.getQuantity());
			// ????????????(??????????????????)
			dbZxGcsgCtContrApplyWorks.setPrice(zxGcsgCtContrApplyWorks.getPrice());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setDeleted(zxGcsgCtContrApplyWorks.getDeleted());
			// ??????????????????
			dbZxGcsgCtContrApplyWorks.setIsLeaf(zxGcsgCtContrApplyWorks.getIsLeaf());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setExsitStatus(zxGcsgCtContrApplyWorks.getExsitStatus());
			// ?????????????????????
			dbZxGcsgCtContrApplyWorks.setIsAssignable(zxGcsgCtContrApplyWorks.getIsAssignable());
			// updateFlag
			dbZxGcsgCtContrApplyWorks.setUpdateFlag(zxGcsgCtContrApplyWorks.getUpdateFlag());
			// combProp
			dbZxGcsgCtContrApplyWorks.setCombProp(zxGcsgCtContrApplyWorks.getCombProp());
			// pp1
			dbZxGcsgCtContrApplyWorks.setPp1(zxGcsgCtContrApplyWorks.getPp1());
			// pp2
			dbZxGcsgCtContrApplyWorks.setPp2(zxGcsgCtContrApplyWorks.getPp2());
			// pp3
			dbZxGcsgCtContrApplyWorks.setPp3(zxGcsgCtContrApplyWorks.getPp3());
			// pp4
			dbZxGcsgCtContrApplyWorks.setPp4(zxGcsgCtContrApplyWorks.getPp4());
			// pp5
			dbZxGcsgCtContrApplyWorks.setPp5(zxGcsgCtContrApplyWorks.getPp5());
			// pp6
			dbZxGcsgCtContrApplyWorks.setPp6(zxGcsgCtContrApplyWorks.getPp6());
			// pp7
			dbZxGcsgCtContrApplyWorks.setPp7(zxGcsgCtContrApplyWorks.getPp7());
			// pp8
			dbZxGcsgCtContrApplyWorks.setPp8(zxGcsgCtContrApplyWorks.getPp8());
			// pp9
			dbZxGcsgCtContrApplyWorks.setPp9(zxGcsgCtContrApplyWorks.getPp9());
			// pp10
			dbZxGcsgCtContrApplyWorks.setPp10(zxGcsgCtContrApplyWorks.getPp10());
			// ??????????????????
			dbZxGcsgCtContrApplyWorks.setEditTime(zxGcsgCtContrApplyWorks.getEditTime());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setCheckQty(zxGcsgCtContrApplyWorks.getCheckQty());
			// ?????????????????????
			dbZxGcsgCtContrApplyWorks.setExpectChangeQty(zxGcsgCtContrApplyWorks.getExpectChangeQty());
			// ?????????????????????
			dbZxGcsgCtContrApplyWorks.setExpectChangePrice(zxGcsgCtContrApplyWorks.getExpectChangePrice());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setInputWorkType(zxGcsgCtContrApplyWorks.getInputWorkType());
			// ???????????????????????????????????????
			dbZxGcsgCtContrApplyWorks.setIsReCountAmt(zxGcsgCtContrApplyWorks.getIsReCountAmt());
			// old contract qty
			dbZxGcsgCtContrApplyWorks.setQty(zxGcsgCtContrApplyWorks.getQty());
			// ???????????????
			dbZxGcsgCtContrApplyWorks.setIsGroup(zxGcsgCtContrApplyWorks.getIsGroup());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setRequestEdit(zxGcsgCtContrApplyWorks.getRequestEdit());
			// ?????????
			dbZxGcsgCtContrApplyWorks.setEditUserID(zxGcsgCtContrApplyWorks.getEditUserID());
			// ?????????
			dbZxGcsgCtContrApplyWorks.setEditUserName(zxGcsgCtContrApplyWorks.getEditUserName());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setEditDate(zxGcsgCtContrApplyWorks.getEditDate());
			// ??????????????????????????????
			dbZxGcsgCtContrApplyWorks.setContractPriceNoTax(zxGcsgCtContrApplyWorks.getContractPriceNoTax());
			// ??????????????????????????????
			dbZxGcsgCtContrApplyWorks.setPriceNoTax(zxGcsgCtContrApplyWorks.getPriceNoTax());
			// ??????
			dbZxGcsgCtContrApplyWorks.setTaxRate(zxGcsgCtContrApplyWorks.getTaxRate());
			// ?????????????????????
			dbZxGcsgCtContrApplyWorks.setAmtNoTax(zxGcsgCtContrApplyWorks.getAmtNoTax());
			// ????????????ID
			dbZxGcsgCtContrApplyWorks.setRuleID(zxGcsgCtContrApplyWorks.getRuleID());
			// ??????????????????
			dbZxGcsgCtContrApplyWorks.setRuleName(zxGcsgCtContrApplyWorks.getRuleName());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField1(zxGcsgCtContrApplyWorks.getOpinionField1());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField2(zxGcsgCtContrApplyWorks.getOpinionField2());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField3(zxGcsgCtContrApplyWorks.getOpinionField3());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField4(zxGcsgCtContrApplyWorks.getOpinionField4());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField5(zxGcsgCtContrApplyWorks.getOpinionField5());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField6(zxGcsgCtContrApplyWorks.getOpinionField6());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField7(zxGcsgCtContrApplyWorks.getOpinionField7());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField8(zxGcsgCtContrApplyWorks.getOpinionField8());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField9(zxGcsgCtContrApplyWorks.getOpinionField9());
			// ??????
			dbZxGcsgCtContrApplyWorks.setOpinionField10(zxGcsgCtContrApplyWorks.getOpinionField10());
			// ??????id
			dbZxGcsgCtContrApplyWorks.setApih5FlowId(zxGcsgCtContrApplyWorks.getApih5FlowId());
			// work_id
			dbZxGcsgCtContrApplyWorks.setWorkId(zxGcsgCtContrApplyWorks.getWorkId());
			// ??????????????????
			dbZxGcsgCtContrApplyWorks.setApih5FlowStatus(zxGcsgCtContrApplyWorks.getApih5FlowStatus());
			// ????????????
			dbZxGcsgCtContrApplyWorks.setApih5FlowNodeStatus(zxGcsgCtContrApplyWorks.getApih5FlowNodeStatus());
			// ??????
			dbZxGcsgCtContrApplyWorks.setRemarks(zxGcsgCtContrApplyWorks.getRemarks());
			// ??????
			dbZxGcsgCtContrApplyWorks.setSort(zxGcsgCtContrApplyWorks.getSort());
			// ??????
			dbZxGcsgCtContrApplyWorks.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContrApplyWorksMapper.updateByPrimaryKey(dbZxGcsgCtContrApplyWorks);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContrApplyWorks);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxGcsgCtContrApplyWorks(
			List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxGcsgCtContrApplyWorksList != null && zxGcsgCtContrApplyWorksList.size() > 0) {
			ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks = new ZxGcsgCtContrApplyWorks();
			zxGcsgCtContrApplyWorks.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContrApplyWorksMapper.batchDeleteUpdateZxGcsgCtContrApplyWorks(zxGcsgCtContrApplyWorksList,
					zxGcsgCtContrApplyWorks);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCtContrApplyWorksList);
		}
	}

	// ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity importZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		// http://192.168.1.133:8889/iecmDataImportAction.iems?p=importWorksExcelFile
		// &workBookID=34632505-175b0374bb9-bf9153e05ed5f2346f3f6269fbe6b2fc
		// &isMasterContr=false&isFromContrApply=true&inputWorkType=1&delType=0
		int sqlFlag = 0;
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if (zxGcsgCtContrApplyWorks == null || zxGcsgCtContrApplyWorks.getAttachmentList() == null
				|| zxGcsgCtContrApplyWorks.getAttachmentList().size() == 0) {
			return repEntity.layerMessage("no", "????????????????????????");
		}
		// ????????????1:?????? 2:?????? 3:?????? 4:??????
		String inputWorkType = zxGcsgCtContrApplyWorks.getInputWorkType();
		if (StrUtil.isNotEmpty(inputWorkType)
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getAttachmentList().get(0).getName())) {
			String fileName = zxGcsgCtContrApplyWorks.getAttachmentList().get(0).getName();
			if (StrUtil.equals("1", inputWorkType) && fileName.indexOf("??????") < 0) {
				return repEntity.layerMessage("no", "?????????????????????????????????????????????");
			} else if (StrUtil.equals("2", inputWorkType) && fileName.indexOf("??????") < 0) {
				return repEntity.layerMessage("no", "?????????????????????????????????????????????");
			} else if (StrUtil.equals("3", inputWorkType) && fileName.indexOf("??????") < 0) {
				return repEntity.layerMessage("no", "?????????????????????????????????????????????");
			} else if (StrUtil.equals("4", inputWorkType) && fileName.indexOf("??????") < 0) {
				return repEntity.layerMessage("no", "?????????????????????????????????????????????");
			}
		}
		// delType=0 ?????????????????????????????????????????? delType=1 ??????????????????????????????????????????????????????
		// delType?????????????????????,?????????????????????
		// String delType = zxGcsgCtContrApplyWorks.getDelType();
		// ?????????????????????
		String ctContrApplyId = zxGcsgCtContrApplyWorks.getCtContrApplyId();
		// ?????????????????????
		String filePath = Apih5Properties.getFilePath()
				+ zxGcsgCtContrApplyWorks.getAttachmentList().get(0).getRelativeUrl();
		// ???????????????????????????
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(ctContrApplyId);
		// check???????????????????????????
		if (StrUtil.equals("1", dbZxGcsgCtContrApply.getApih5FlowStatus())
				|| StrUtil.equals("2", dbZxGcsgCtContrApply.getApih5FlowStatus())) {
			return repEntity.layerMessage("no", "????????????????????????????????????????????????,????????????????????????");
		}
		// ????????????ID
		// String orgID = dbZxGcsgCtContrApply.getOrgID();
		String orgID = dbZxGcsgCtContrApply.getFirstID();
		// ????????????(P2?????????????????????)
		// String contractType = dbZxGcsgCtContrApply.getContractType();
		String contractType = "P2";
		// ?????????????????????(?????????????????????????????????)
		String mainTaxRate = dbZxGcsgCtContrApply.getTaxRate();
		// ????????????????????????ID???workType??????????????????
		ZxGcsgCtContrApplyWorks param = new ZxGcsgCtContrApplyWorks();
		param.setParentID("-1");
		param.setCtContrApplyId(ctContrApplyId);
		List<ZxGcsgCtContrApplyWorks> dbRootNodeList = zxGcsgCtContrApplyWorksMapper
				.selectByZxGcsgCtContrApplyWorksList(param);
		ZxGcsgCtContrApplyWorks worksRootNode = new ZxGcsgCtContrApplyWorks();
		// ?????????????????????????????????
		if (dbRootNodeList == null || dbRootNodeList.isEmpty()) {
			String workID = UuidUtil.generate();
			worksRootNode.setCtContrApplyWorksId(workID);
			worksRootNode.setParentID("-1");
			worksRootNode.setTreeNode("1000");
			// ??????????????????????????????ID
			worksRootNode.setOrgID(orgID);
			worksRootNode.setCtContrApplyId(ctContrApplyId);
			// { "10", "????????????" },{ "11", "???????????????" }, { "20", "????????????" } ;
			worksRootNode.setWorkType(10);
			worksRootNode.setWorkNo("-");
			worksRootNode.setWorkName(dbZxGcsgCtContrApply.getContractName());
			worksRootNode.setContractPrice(BigDecimal.ZERO);
			worksRootNode.setContractPriceNoTax(BigDecimal.ZERO);
			worksRootNode.setContractQty(BigDecimal.ZERO);
			worksRootNode.setContractAmt(BigDecimal.ZERO);
			worksRootNode.setQuantity(BigDecimal.ZERO);
			worksRootNode.setPrice(BigDecimal.ZERO);
			worksRootNode.setPriceNoTax(BigDecimal.ZERO);
			worksRootNode.setDeleted(0);
			// 0:???????????????
			worksRootNode.setIsLeaf(0);
			// 0?????? 1 ?????? 2 ?????? 3 ?????? ?????????????????????0
			worksRootNode.setExsitStatus(1);
			worksRootNode.setIsAssignable(0);
			worksRootNode.setEditTime(DateUtil.date());
			worksRootNode.setCheckQty(BigDecimal.ZERO);
			worksRootNode.setExpectChangeQty(BigDecimal.ZERO);
			worksRootNode.setExpectChangePrice(BigDecimal.ZERO);
			worksRootNode.setQty(BigDecimal.ZERO);
			worksRootNode.setUpdateFlag("N");
			worksRootNode.setCreateUserInfo(userKey, realName);
			sqlFlag = zxGcsgCtContrApplyWorksMapper.insert(worksRootNode);
			if (sqlFlag == 0) {
				return repEntity.layerMessage("no", "??????????????????????????????");
			}
		} else {
			worksRootNode = dbRootNodeList.get(0);
		}
		// ????????????
		String workType = String.valueOf(worksRootNode.getWorkType());
		// ID???????????????????????????parentID
		String specParentID = worksRootNode.getCtContrApplyWorksId();
		String specTreeNode = worksRootNode.getTreeNode();
		List<ZxGcsgCtContrApplyWorks> importValueList = Lists.newArrayList();
		// ??????excel
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String, Object>> excelList = reader.readAll();
			if (excelList == null || excelList.size() == 0) {
				return repEntity.layerMessage("no", "????????????,????????????????????????!");
			}
			// ???????????????????????????????????????2?????????????????????????????????????????????
			String costTitle = "";
			int stepNum = 0;
			for (Map.Entry<String, Object> entry : excelList.get(0).entrySet()) {
				if (stepNum == 4) {
					costTitle = entry.getKey();
					break;
				}
				stepNum++;
			}
			int sort = 0;
			if (excelList.size() >= 0) {
				// ????????????1?????????(?????????????????????)???????????????:1?????????????????????????????????
				for (int k = 0; k < excelList.size(); k++) {
					Map<String, Object> row = excelList.get(k);
					// ????????????
					String worksNo = String.valueOf(row.get("????????????"));
					// ???????????????????????????????????????????????????
					if (StrUtil.isEmpty(worksNo)) {
						continue;
					}
					// ????????????
					String worksName = String.valueOf(row.get("????????????"));
					// ??????
					String unit = "";
					if (!StrUtil.equals("4", inputWorkType)) {
						unit = String.valueOf(row.get("??????"));
					} else {
						unit = String.valueOf(row.get("????????????"));
					}
					// ??????
					BigDecimal quantity = BigDecimal.ZERO;
					// ???????????????????????????;?????????????????????
					if (StrUtil.isNotEmpty(String.valueOf(row.get("??????")))) {
						if (NumberUtil.isNumber(String.valueOf(row.get("??????")))) {
							quantity = new BigDecimal(String.valueOf(row.get("??????")));
						} else {
							String errMessage = "???Excel???" + (k + 1) + "??????" + 4 + "????????????????????????(??????????????????),???????????????????????????????????????????????????";
							return repEntity.layerMessage("no", errMessage);
						}
					}
					// ??????
					BigDecimal price = BigDecimal.ZERO;
					if (StrUtil.isNotEmpty(String.valueOf(row.get(costTitle)))) {
						if (NumberUtil.isNumber(String.valueOf(row.get(costTitle)))) {
							price = new BigDecimal(String.valueOf(row.get(costTitle)));
						} else {
							String errMessage = "???Excel???" + (k + 1) + "??????" + 5 + "????????????????????????(??????????????????),???????????????????????????????????????????????????";
							return repEntity.layerMessage("no", errMessage);
						}
					}
					String worksTaxRate = "";
					if (StrUtil.equals("?????????????????????", costTitle)) {
						worksTaxRate = String.valueOf(row.get("??????"));
					} else if (StrUtil.equals("??????????????????", costTitle) && StrUtil.equals("2", inputWorkType)) {
						worksTaxRate = String.valueOf(row.get("??????"));
					} else if (StrUtil.equals("??????????????????", costTitle) && !StrUtil.equals("2", inputWorkType)) {
						worksTaxRate = String.valueOf(row.get("??????(%)"));
					} else {
						// ?????????????????????check
						// String errMessage = "???Excel???" + (k + 1) + "??????" + 6 +
						// "????????????????????????(??????????????????),???????????????????????????????????????????????????";
						// return repEntity.layerMessage("no", errMessage);
					}
					// ??????????????????????????????
					worksNo = worksNo.replaceAll("\n", "");
					worksName = worksName.replaceAll("\n", "");
					unit = unit.replaceAll("\n", "");
					// ????????????????????????
					ZxGcsgCtContrApplyWorks excelWorks = new ZxGcsgCtContrApplyWorks();
					if ("?????????????????????".equals(costTitle)) {
						excelWorks.setPriceNoTax(price);
						if (StrUtil.isNotEmpty(contractType) && contractType.startsWith("P2")) {
							if (StrUtil.isNotEmpty(worksTaxRate) && NumberUtil.isNumber(worksTaxRate)) {
								// work.setPrice(price * (1 + taxRate * 0.01));
								excelWorks.setPrice(CalcUtils.calcMultiply(price, CalcUtils.calcAdd(BigDecimal.ONE,
										CalcUtils.calcMultiply(new BigDecimal(worksTaxRate), new BigDecimal(0.01)))));
							} else {
								excelWorks.setPrice(BigDecimal.ZERO);
							}
							excelWorks.setTaxRate(worksTaxRate);
						} else {
							if (StrUtil.isEmpty(mainTaxRate)) {
								excelWorks.setPrice(BigDecimal.ZERO);
							} else {
								excelWorks.setPrice(CalcUtils.calcMultiply(price, CalcUtils.calcAdd(BigDecimal.ONE,
										CalcUtils.calcMultiply(new BigDecimal(mainTaxRate), new BigDecimal(0.01)))));
							}
						}
					} else {
						excelWorks.setPrice(price);
						if (StrUtil.isNotEmpty(contractType) && contractType.startsWith("P2")) {
							if (StrUtil.isNotEmpty(worksTaxRate) && NumberUtil.isNumber(worksTaxRate)) {
								// work.setPriceNoTax(price / (1 + taxRate * 0.01));
								excelWorks.setPriceNoTax(CalcUtils.calcDivide(price, CalcUtils.calcAdd(BigDecimal.ONE,
										CalcUtils.calcMultiply(new BigDecimal(worksTaxRate), new BigDecimal(0.01))),
										6));
							} else {
								excelWorks.setPriceNoTax(BigDecimal.ZERO);
							}
							excelWorks.setTaxRate(worksTaxRate);
						} else {
							if (StrUtil.isEmpty(mainTaxRate)) {
								excelWorks.setPriceNoTax(BigDecimal.ZERO);
							} else {
								excelWorks.setPriceNoTax(CalcUtils.calcDivide(price, CalcUtils.calcAdd(BigDecimal.ONE,
										CalcUtils.calcMultiply(new BigDecimal(mainTaxRate), new BigDecimal(0.01))), 6));
							}
						}
					}
					// ??????
					excelWorks.setCtContrApplyWorksId(UuidUtil.generate());
					excelWorks.setCtContrApplyId(ctContrApplyId);
					// ??????????????????????????????????????????????????????????????????ID????????????????????????is??????
					excelWorks.setWorkNo(worksNo);
					excelWorks.setWorkName(worksName);
					excelWorks.setUnit(unit);
					excelWorks.setQuantity(quantity);
					// ????????????ID
					excelWorks.setOrgID(orgID);
					excelWorks.setWorkType(Integer.parseInt(workType));
					excelWorks.setSort(sort);
					excelWorks.setWorkNoLength(getWorkNoLength(worksNo, inputWorkType));
					// ????????????????????????????????????????????????????????????????????????????????????
					excelWorks.setIsLeaf(1);
					// 0?????? 1 ?????? 2 ?????? 3 ??????
					excelWorks.setExsitStatus(1);
					excelWorks.setIsAssignable(1);
					excelWorks.setQty(BigDecimal.ZERO);
					excelWorks.setCreateUserInfo(userKey, realName);
					excelWorks.setContractPrice(excelWorks.getPrice());
					excelWorks.setContractPriceNoTax(excelWorks.getPriceNoTax());
					excelWorks.setContractQty(quantity);
					// contractAmt += wmDto.getPrice() * wmDto.getQuantity() / 10000;
					excelWorks.setContractAmt(BigDecimal.ZERO);
					excelWorks.setEditTime(DateUtil.date());
					excelWorks.setCheckQty(quantity);
					excelWorks.setExpectChangeQty(quantity);
					excelWorks.setExpectChangePrice(excelWorks.getPrice());
					excelWorks.setInputWorkType(inputWorkType);
					// ???????????????????????????????????????
					// excelWorks.setTaxRate(worksTaxRate);
					importValueList.add(excelWorks);
					sort++;
				}
			} else {
				String errMessage = "???Excel????????????,???????????????????????????";
				return repEntity.layerMessage("no", errMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// ??????reader???????????????
			if (reader != null) {
				reader.close();
			}
		}
		// ?????????????????????????????????
		String checkInfo = null;
		try {
			checkInfo = workNoExistCheckForImp(importValueList, orgID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!StrUtil.equals("0", checkInfo)) {
			return repEntity.layerMessage("no", checkInfo);
		}
		// ????????????????????????????????????????????????
		int maxNoLen = 0;
		int minNoLen = 0;
		for (Iterator i = importValueList.iterator(); i.hasNext();) {
			ZxGcsgCtContrApplyWorks workNoInfo = (ZxGcsgCtContrApplyWorks) i.next();
			int len = workNoInfo.getWorkNoLength();
			if (maxNoLen < len) {
				maxNoLen = len;
			}
			if (minNoLen > len) {
				minNoLen = len;
			}
		}
		List<ZxGcsgCtContrApplyWorks> resultList = Lists.newArrayList();
		// ???????????????ID??????parentID??????treeNode
		// ??????????????????????????????TreeNode
		// ??????????????????????????????8???treeNode??????????????????
		int lenCount = 0;
		ZxGcsgCtContrApplyWorks ctContrApplyWorks = new ZxGcsgCtContrApplyWorks();
		ctContrApplyWorks.setCtContrApplyId(ctContrApplyId);
		ZxGcsgCtContrApplyWorks maxTreeNode = zxGcsgCtContrApplyWorksMapper
				.getZxGcsgCtContrApplyWorksMAXTreeNode(ctContrApplyWorks);
		String maxHaveCount = maxTreeNode != null ? maxTreeNode.getTreeNode() : "";
		if (StrUtil.isNotEmpty(maxHaveCount)) {
			maxHaveCount = maxHaveCount.substring(5, 8);
			lenCount = Integer.parseInt(maxHaveCount) + 1;
		} else {
			lenCount = 1;
		}
		String comparePWorkNo = "";
		int count = 1;
		for (int i = minNoLen; i <= maxNoLen; i++) {
			for (Iterator j = importValueList.iterator(); j.hasNext();) {
				ZxGcsgCtContrApplyWorks workNoInfo = (ZxGcsgCtContrApplyWorks) j.next();
				if (i == workNoInfo.getWorkNoLength()) {
					// ???????????????????????????????????????????????????????????????????????????????????????
					int index = workNoInfo.getSort();
					// ????????????????????????????????????????????????????????????
					boolean flag = true;
					for (int m = index; m >= 0; m--) {
						ZxGcsgCtContrApplyWorks pWorkNoInfo = (ZxGcsgCtContrApplyWorks) importValueList.get(m);
						if (pWorkNoInfo.getWorkNoLength() < workNoInfo.getWorkNoLength()) {
							// workNoInfo.setPWorkNo(pWorkNoInfo.getWorkNo());
							workNoInfo.setParentID(pWorkNoInfo.getCtContrApplyWorksId());
							if (!comparePWorkNo.equals(pWorkNoInfo.getWorkNo())) {
								comparePWorkNo = pWorkNoInfo.getWorkNo();
								count = 1;
							} else {
								count++;
							}
							int temp_TreeNode = 1000 + count;
							workNoInfo.setTreeNode(pWorkNoInfo.getTreeNode() + temp_TreeNode);
							resultList.add(workNoInfo);
							// ???????????????????????????????????????????????????
							flag = false;
							break;
						}
					}
					if (flag) {
						// ???????????????????????????????????????????????????ID
						// workNoInfo.setPWorkNo(temp_pWorkNo);
						workNoInfo.setParentID(specParentID);
						int temp_specTreeNode = 1000 + lenCount;
						workNoInfo.setTreeNode(specTreeNode + temp_specTreeNode);
						resultList.add(workNoInfo);
						lenCount++;
					}
				}
			}
		}
//		// double contractAmt = 0;// ??????????????????
//		BigDecimal contractAmt = BigDecimal.ZERO;
//		for (Iterator i = resultList.iterator(); i.hasNext();) {
//			ZxGcsgCtContrApplyWorks wmDto = (ZxGcsgCtContrApplyWorks) i.next();
//			contractAmt = CalcUtils.calcAdd(contractAmt, CalcUtils
//					.calcDivide(CalcUtils.calcMultiply(wmDto.getPrice(), wmDto.getQuantity()), new BigDecimal(10000)));
//			// contractAmt += wmDto.getPrice() * wmDto.getQuantity() / 10000;
//		}
		// 1.??????????????????SQL
		ZxGcsgCtContrApplyWorks delete = new ZxGcsgCtContrApplyWorks();
		delete.setCtContrApplyId(ctContrApplyId);
		// delType=0 ?????????????????????????????????????????? delType=1 ??????????????????????????????????????????????????????
		// if (StrUtil.equals("0", delType)) {
		// delete.setInputWorkType(inputWorkType);
		// }
		sqlFlag = zxGcsgCtContrApplyWorksMapper.deleteZxGcsgCtContrApplyWorksByCondition(delete);
		if (sqlFlag != 0) {
			// ?????????????????????
			ZxGcsgCtContrProcessGuajie guajie = new ZxGcsgCtContrProcessGuajie();
			guajie.setCtContrApplyId(ctContrApplyId);
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(guajie);
			// ????????????????????????????????????
			ZxGcsgCtPriceSys zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
			zxGcsgCtPriceSys.setCtContrApplyId(ctContrApplyId);
			zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItemByCondition(zxGcsgCtPriceSys);
		}
		// 2.??????????????????
		sqlFlag = zxGcsgCtContrApplyWorksMapper.batchInsertZxGcsgCtContrApplyWorks(resultList);
		// ??????????????????
		// String updateContract = "update iect_Contract set contractCost = " +
		// contractAmt + " where ID='" + orgID + "'";
		// jdbcTmp.execute(updateContract);
		// ????????????????????????????????????????????????????????????ID??????parentID?????????????????????????????????????????????????????????zhangdj 2009-03-07
		ZxGcsgCtContrApplyWorks update = new ZxGcsgCtContrApplyWorks();
		update.setCtContrApplyId(ctContrApplyId);
		sqlFlag = zxGcsgCtContrApplyWorksMapper.updateZxGcsgCtContrApplyWorksByLeaf(update);
		// ???????????????????????????????????????
		ZxGcsgCtContrApplyWorks contractCost = new ZxGcsgCtContrApplyWorks();
		contractCost.setCtContrApplyId(ctContrApplyId);
		ZxGcsgCtContrApplyWorks dbContractCost = zxGcsgCtContrApplyWorksMapper
				.getZxGcsgCtContrApplyWorksContractCost(contractCost);
		if (dbZxGcsgCtContrApply != null) {
			dbZxGcsgCtContrApply.setContractCost(dbContractCost.getContractAmt());
			dbZxGcsgCtContrApply.setContractCostNoTax(dbContractCost.getAmtNoTax());
			BigDecimal contractCostTax = CalcUtils.calcSubtract(dbContractCost.getContractAmt(),
					dbContractCost.getAmtNoTax());
			dbZxGcsgCtContrApply.setContractCostTax(contractCostTax);
			sqlFlag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
		}
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		// ?????????????????????
		File temp = new File(filePath);
		if (temp.exists()) {
			temp.delete();
		}
		return repEntity.ok("???????????????");
	}

	/**
	 * ??????????????????????????????????????????????????????-????????????????????????
	 * 
	 * @param workNo
	 * @return
	 */
	// ??????workNo??????????????????
	private String difWorkNo = ",";
	private boolean flag = false;

	private int getWorkNoLength(String workNo, String inputWorkType) {
		if (inputWorkType.equals("1")) {
			String[] workNoArr = workNo.split("-");
			// ????????? 103???104 ???????????????????????????
			if (workNoArr.length <= 1) {// ???workNo??????00??????????????????
				// ?????????????????????????????????????????????
				if ((workNo.length() > 1 && !"00".equals(workNo.substring(workNo.length() - 2, workNo.length())))) {
					// ????????????????????????????????????
					System.out.println("===:" + workNo);
					this.flag = true;
					// this.difWorkNo = this.difWorkNo + workNo + ",";
					return 2;
				}
			} else {
				if (flag) {
					// if (difWorkNo.indexOf("," + workNoArr[0] + ",") >= 0) {
					System.out.println("flag===:" + workNo);
					return (workNoArr.length + 2);

					// }
				} else {
					// if (difWorkNo.indexOf("," + workNoArr[0] + ",") >= 0) {
					return (workNoArr.length + 1);
					// }
				}
			}
			flag = false;
			return workNoArr.length;
		} else {
			// ??????????????????????????????????????????????????????????????????????????????????????????
			char[] charArray = workNo.toCharArray();
			int count = 0;
			for (int i = 0; i < charArray.length; i++) {
				if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
					continue;
				}
				count++;
			}
			return count;
		}
	}

	/**
	 * ????????????????????????????????????(????????????).
	 * 
	 * @param importValueList
	 * @param orgID
	 * @param workBookID
	 * @return
	 * @throws IEMSException
	 */
	private String workNoExistCheckForImp(List importValueList, String orgID) throws Exception {
		// ???????????????
		StringBuffer rtnStrBuf = new StringBuffer();

		if (importValueList != null && !importValueList.isEmpty()) {
			Map workNoMapInExcel = new HashMap();
			for (Iterator it = importValueList.iterator(); it.hasNext();) {
				ZxGcsgCtContrApplyWorks workModel = (ZxGcsgCtContrApplyWorks) it.next();
				if (workNoMapInExcel.containsKey(workModel.getWorkNo())) {
					rtnStrBuf.append("????????????").append(workModel.getWorkNo()).append("??????excel??????????????????\n");
				} else {
					workNoMapInExcel.put(workModel.getWorkNo(), workModel.getWorkNo());
				}
			}

		}

		String rtnStr = rtnStrBuf.toString();
		if (StrUtil.isEmpty(rtnStr)) {
			return "0";
		} else {
			return rtnStr;
		}
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksEditSubList(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		if (zxGcsgCtContrApplyWorks == null) {
			zxGcsgCtContrApplyWorks = new ZxGcsgCtContrApplyWorks();
		}
		// ????????????,?????????????????????????????????,???????????????????????????????????????
		List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getParentID())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())) {
			zxGcsgCtContrApplyWorksList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListByCondition(zxGcsgCtContrApplyWorks);
			if (zxGcsgCtContrApplyWorksList == null || zxGcsgCtContrApplyWorksList.size() == 0) {
				zxGcsgCtContrApplyWorks.setCtContrApplyWorksId(zxGcsgCtContrApplyWorks.getParentID());
				ZxGcsgCtContrApplyWorks dbParent = zxGcsgCtContrApplyWorksMapper
						.getZxGcsgCtContrApplyWorksByCondition(zxGcsgCtContrApplyWorks);
				if (dbParent != null) {
					ZxGcsgCtContrApplyWorks returnData = new ZxGcsgCtContrApplyWorks();
					returnData.setCtContrApplyWorksId(dbParent.getCtContrApplyWorksId());
					returnData.setCtContrApplyId(dbParent.getCtContrApplyId());
					returnData.setWorkType(dbParent.getWorkType());
					returnData.setWorkNo(dbParent.getWorkNo());
					zxGcsgCtContrApplyWorksList.add(returnData);
				}
			}
		}
		return repEntity.okList(zxGcsgCtContrApplyWorksList, zxGcsgCtContrApplyWorksList.size());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity batchEditSubListZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// check,???????????????????????????????????????(???????????????)(??????)
		Map<String, Object> globalCheck = new HashMap<String, Object>();
		ZxGcsgCtContrApplyWorks search = new ZxGcsgCtContrApplyWorks();
		search.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
		List<ZxGcsgCtContrApplyWorks> allWorksList = zxGcsgCtContrApplyWorksMapper
				.selectByZxGcsgCtContrApplyWorksList(search);
		if (allWorksList.size() > 0) {
			allWorksList.stream().forEach(works -> {
				globalCheck.put(works.getWorkNo(), works.getCtContrApplyWorksId());
			});
		}
		// ???????????????????????????parentID????????????(???????????????????????????)
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getParentID())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())) {
			ZxGcsgCtContrApplyWorks param = new ZxGcsgCtContrApplyWorks();
			param.setCtContrApplyWorksId(zxGcsgCtContrApplyWorks.getParentID());
			param.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			ZxGcsgCtContrApplyWorks dbParent = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksByCondition(param);
			param.setCtContrApplyWorksId("");
			param.setParentID(zxGcsgCtContrApplyWorks.getParentID());
			List<ZxGcsgCtContrApplyWorks> dbChildrenList = zxGcsgCtContrApplyWorksMapper
					.selectByZxGcsgCtContrApplyWorksList(param);
			// ??????db?????????????????????
			List<ZxGcsgCtContrApplyWorks> insertList = Lists.newArrayList();
			List<ZxGcsgCtContrApplyWorks> updateList = Lists.newArrayList();
			List<ZxGcsgCtContrApplyWorks> deleteList = Lists.newArrayList();
			List<ZxGcsgCtContrApplyWorks> pageUpdateList = Lists.newArrayList();
			List<ZxGcsgCtContrApplyWorks> pageChildrenList = zxGcsgCtContrApplyWorks.getCtContrApplyWorksList();
			// ?????????????????????
			int index = 1;
			if (dbParent != null && pageChildrenList.size() > 0) {
				for (ZxGcsgCtContrApplyWorks works : pageChildrenList) {
					int temp_TreeNode = 1000 + index;
					works.setTreeNode(dbParent.getTreeNode() + temp_TreeNode);
					index++;
				}
			}
			// 1?????????????????????????????????
			if (dbChildrenList != null && dbChildrenList.size() > 0 && pageChildrenList != null
					&& pageChildrenList.size() > 0) {
				for (int i = 0; i < dbChildrenList.size(); i++) {
					for (ZxGcsgCtContrApplyWorks pageApplyWorks : pageChildrenList) {
						if (StrUtil.equals(dbChildrenList.get(i).getCtContrApplyWorksId(),
								pageApplyWorks.getCtContrApplyWorksId())) {
							pageUpdateList.add(pageApplyWorks);
							dbChildrenList.remove(i);
							pageChildrenList.remove(pageApplyWorks);
							i--;
							break;
						}
					}
				}
				// ?????????
				if (dbChildrenList.size() > 0) {
					// ????????????????????????
					deleteList.addAll(dbChildrenList);
					// flag =
					// zxGcsgCtContrApplyWorksMapper.batchDeleteZxGcsgCtContrApplyWorks(dbChildrenList);
				}
				// int maxStepTreeNode = 1000;
				// ?????????
				if (pageUpdateList.size() > 0) {
					for (ZxGcsgCtContrApplyWorks ctContrApplyWorks : pageUpdateList) {
						// ??????????????????
						ZxGcsgCtContrApplyWorks dbUpdata = zxGcsgCtContrApplyWorksMapper
								.selectByPrimaryKey(ctContrApplyWorks.getCtContrApplyWorksId());
						if (dbUpdata != null) {
							// dbData.setWorkType(ctContrApplyWorks.getWorkType());
							dbUpdata.setWorkNo(ctContrApplyWorks.getWorkNo());
							dbUpdata.setWorkName(ctContrApplyWorks.getWorkName());
							dbUpdata.setUnit(ctContrApplyWorks.getUnit());
							dbUpdata.setQuantity(ctContrApplyWorks.getQuantity());
							dbUpdata.setContractPrice(ctContrApplyWorks.getContractPrice());
							dbUpdata.setTaxRate(ctContrApplyWorks.getTaxRate());
							// updata.setContractPriceNoTax(ctContrApplyWorks.getPriceNoTax());
							if (StrUtil.isNotEmpty(ctContrApplyWorks.getTaxRate())
									&& NumberUtil.isNumber(ctContrApplyWorks.getTaxRate())) {
								dbUpdata.setContractPriceNoTax(CalcUtils.calcDivide(
										ctContrApplyWorks.getContractPrice(),
										CalcUtils.calcAdd(BigDecimal.ONE, CalcUtils.calcMultiply(
												new BigDecimal(ctContrApplyWorks.getTaxRate()), new BigDecimal(0.01))),
										6));
							} else {
								dbUpdata.setContractPriceNoTax(BigDecimal.ZERO);
							}
							dbUpdata.setRuleID(ctContrApplyWorks.getRuleID());
							dbUpdata.setRuleName(ctContrApplyWorks.getRuleName());
							dbUpdata.setRemarks(ctContrApplyWorks.getRemarks());
							dbUpdata.setModifyUserInfo(userKey, realName);
							// ???????????????????????????,?????????????????????????????????
							dbUpdata.setPrice(ctContrApplyWorks.getContractPrice());
							dbUpdata.setPriceNoTax(dbUpdata.getContractPriceNoTax());
							dbUpdata.setContractQty(ctContrApplyWorks.getQuantity());
							// contractAmt += wmDto.getPrice() * wmDto.getQuantity() / 10000;
							dbUpdata.setCheckQty(ctContrApplyWorks.getQuantity());
							dbUpdata.setExpectChangeQty(ctContrApplyWorks.getQuantity());
							dbUpdata.setExpectChangePrice(ctContrApplyWorks.getContractPrice());
							dbUpdata.setTreeNode(ctContrApplyWorks.getTreeNode());
							updateList.add(dbUpdata);
//							if (NumberUtil.isNumber(dbUpdata.getTreeNode()) && dbUpdata.getTreeNode().length() > 3) {
//								String initStep = dbUpdata.getTreeNode().substring(dbUpdata.getTreeNode().length() - 4,
//										dbUpdata.getTreeNode().length());
//								if (maxStepTreeNode < Integer.parseInt(initStep)) {
//									maxStepTreeNode = Integer.parseInt(initStep);
//								}
//							}
						}
					}
				}
				// ?????????
				if (pageChildrenList.size() > 0) {
					// int index = 1;
					for (ZxGcsgCtContrApplyWorks ctContrApplyWorks : pageChildrenList) {
						ZxGcsgCtContrApplyWorks insertData = new ZxGcsgCtContrApplyWorks();
						// ??????
						insertData.setCtContrApplyWorksId(UuidUtil.generate());
						insertData.setContractPrice(ctContrApplyWorks.getContractPrice());
						insertData.setTaxRate(ctContrApplyWorks.getTaxRate());
						if (StrUtil.isNotEmpty(ctContrApplyWorks.getTaxRate())
								&& NumberUtil.isNumber(ctContrApplyWorks.getTaxRate())) {
							insertData
									.setContractPriceNoTax(CalcUtils.calcDivide(ctContrApplyWorks.getContractPrice(),
											CalcUtils.calcAdd(BigDecimal.ONE,
													CalcUtils.calcMultiply(
															new BigDecimal(ctContrApplyWorks.getTaxRate()),
															new BigDecimal(0.01))),
											6));
						} else {
							insertData.setContractPriceNoTax(BigDecimal.ZERO);
						}
						insertData.setPrice(ctContrApplyWorks.getContractPrice());
						insertData.setPriceNoTax(insertData.getContractPriceNoTax());
						// int temp_TreeNode = maxStepTreeNode + index;
						// insertData.setTreeNode(dbParent.getTreeNode() + temp_TreeNode);
						insertData.setTreeNode(ctContrApplyWorks.getTreeNode());
						// index++;
						insertData.setParentID(dbParent.getCtContrApplyWorksId());
						insertData.setCtContrApplyId(dbParent.getCtContrApplyId());
						// ??????????????????????????????????????????????????????????????????ID????????????????????????is??????
						insertData.setWorkNo(ctContrApplyWorks.getWorkNo());
						insertData.setWorkName(ctContrApplyWorks.getWorkName());
						insertData.setUnit(ctContrApplyWorks.getUnit());
						insertData.setQuantity(ctContrApplyWorks.getQuantity());
						// ????????????ID
						insertData.setOrgID(dbParent.getOrgID());
						// ????????????
						insertData.setWorkType(10);
						// insertData.setSort(sort);
						// ??????????????????????????????
						insertData.setIsLeaf(1);
						// 0?????? 1 ?????? 2 ?????? 3 ??????
						insertData.setExsitStatus(1);
						insertData.setIsAssignable(1);
						insertData.setQty(BigDecimal.ZERO);
						insertData.setCreateUserInfo(userKey, realName);
						insertData.setContractQty(ctContrApplyWorks.getQuantity());
						// contractAmt += wmDto.getPrice() * wmDto.getQuantity() / 10000;
						insertData.setContractAmt(BigDecimal.ZERO);
						insertData.setEditTime(DateUtil.date());
						insertData.setCheckQty(ctContrApplyWorks.getQuantity());
						insertData.setExpectChangeQty(ctContrApplyWorks.getQuantity());
						insertData.setExpectChangePrice(ctContrApplyWorks.getContractPrice());
						insertData.setRuleID(ctContrApplyWorks.getRuleID());
						insertData.setRuleName(ctContrApplyWorks.getRuleName());
						insertData.setRemarks(ctContrApplyWorks.getRemarks());
						// ???????????? 1????????? 2????????? 3?????????
						// ????????????????????????
						insertData.setInputWorkType(dbParent.getInputWorkType());
						insertData.setCreateUserInfo(userKey, realName);
						insertList.add(insertData);
					}
				}
			}
			// 2????????????????????????????????????
			else if ((dbChildrenList == null || dbChildrenList.size() < 1) && pageChildrenList != null
					&& pageChildrenList.size() > 0) {
				// ????????????
				// ??????????????????
				// int index = 1;
				for (ZxGcsgCtContrApplyWorks ctContrApplyWorks : pageChildrenList) {
					ZxGcsgCtContrApplyWorks insertData = new ZxGcsgCtContrApplyWorks();
					// ??????
					insertData.setCtContrApplyWorksId(UuidUtil.generate());
					insertData.setContractPrice(ctContrApplyWorks.getContractPrice());
					insertData.setTaxRate(ctContrApplyWorks.getTaxRate());
					if (StrUtil.isNotEmpty(ctContrApplyWorks.getTaxRate())
							&& NumberUtil.isNumber(ctContrApplyWorks.getTaxRate())) {
						insertData
								.setContractPriceNoTax(CalcUtils.calcDivide(ctContrApplyWorks.getContractPrice(),
										CalcUtils.calcAdd(BigDecimal.ONE, CalcUtils.calcMultiply(
												new BigDecimal(ctContrApplyWorks.getTaxRate()), new BigDecimal(0.01))),
										6));
					} else {
						insertData.setContractPriceNoTax(BigDecimal.ZERO);
					}
					insertData.setPrice(ctContrApplyWorks.getContractPrice());
					insertData.setPriceNoTax(insertData.getContractPriceNoTax());
					// int temp_TreeNode = 1000 + index;
					// insertData.setTreeNode(dbParent.getTreeNode() + temp_TreeNode);
					insertData.setTreeNode(ctContrApplyWorks.getTreeNode());
					// index++;
					insertData.setParentID(dbParent.getCtContrApplyWorksId());
					insertData.setCtContrApplyId(dbParent.getCtContrApplyId());
					// ??????????????????????????????????????????????????????????????????ID????????????????????????is??????
					insertData.setWorkNo(ctContrApplyWorks.getWorkNo());
					insertData.setWorkName(ctContrApplyWorks.getWorkName());
					insertData.setUnit(ctContrApplyWorks.getUnit());
					insertData.setQuantity(ctContrApplyWorks.getQuantity());
					// ????????????ID
					insertData.setOrgID(dbParent.getOrgID());
					// ????????????
					insertData.setWorkType(10);
					// insertData.setSort(sort);
					// ??????????????????????????????
					insertData.setIsLeaf(1);
					// 0?????? 1 ?????? 2 ?????? 3 ??????
					insertData.setExsitStatus(1);
					insertData.setIsAssignable(1);
					insertData.setQty(BigDecimal.ZERO);
					insertData.setCreateUserInfo(userKey, realName);
					insertData.setContractQty(ctContrApplyWorks.getQuantity());
					// contractAmt += wmDto.getPrice() * wmDto.getQuantity() / 10000;
					insertData.setContractAmt(BigDecimal.ZERO);
					insertData.setEditTime(DateUtil.date());
					insertData.setCheckQty(ctContrApplyWorks.getQuantity());
					insertData.setExpectChangeQty(ctContrApplyWorks.getQuantity());
					insertData.setExpectChangePrice(ctContrApplyWorks.getContractPrice());
					insertData.setRuleID(ctContrApplyWorks.getRuleID());
					insertData.setRuleName(ctContrApplyWorks.getRuleName());
					insertData.setRemarks(ctContrApplyWorks.getRemarks());
					// ???????????? 1????????? 2????????? 3?????????
					// ????????????????????????
					insertData.setInputWorkType(dbParent.getInputWorkType());
					insertData.setCreateUserInfo(userKey, realName);
					insertList.add(insertData);
				}
			}
			// 3????????????????????????????????????
			else if (dbChildrenList != null && dbChildrenList.size() > 0
					&& (pageChildrenList == null || pageChildrenList.size() < 1)) {
				// ????????????
				deleteList.addAll(dbChildrenList);
				// ?????????parentID???????????????
				// flag =
				// zxGcsgCtContrApplyWorksMapper.deleteZxGcsgCtContrApplyWorksByParentID(zxGcsgCtContrApplyWorks);
			}
			// check,???????????????????????????(??????)
			// 1??????????????????????????????check??????
			if (deleteList.size() > 0) {
				Iterator<ZxGcsgCtContrApplyWorks> it = deleteList.iterator();
				while (it.hasNext()) {
					ZxGcsgCtContrApplyWorks itData = it.next();
					if (itData.getIsLeaf() != 1) {
						return repEntity.layerMessage("no", "???????????????????????????,?????????????????????");
					}
					// ??????check
					globalCheck.remove(itData.getWorkNo());
				}
			}
			// 2??????????????????????????????check??????
			if (updateList.size() > 0) {
				updateList.stream().forEach(updateData -> {
					Iterator<Map.Entry<String, Object>> it = globalCheck.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String, Object> entry = it.next();
						if (StrUtil.equals(updateData.getCtContrApplyWorksId(), String.valueOf(entry.getValue())))
							it.remove();
					}
				});
			}
			// 3???check??????????????????(???????????????????????????????????????)
			List<ZxGcsgCtContrApplyWorks> allList = Lists.newArrayList();
			allList.addAll(updateList);
			allList.addAll(insertList);
			if (allList.size() > 0) {
				for (ZxGcsgCtContrApplyWorks works : allList) {
					// check????????????
					if (globalCheck.containsKey(works.getWorkNo())) {
						return repEntity.layerMessage("no", "????????????????????????,??????????????????");
					} else {
						globalCheck.put(works.getWorkNo(), works.getCtContrApplyWorksId());
					}
				}
			}
			// ??????????????????
			if (deleteList.size() > 0) {
				flag = zxGcsgCtContrApplyWorksMapper.batchDeleteZxGcsgCtContrApplyWorks(deleteList);
				if (updateList.size() < 1 && insertList.size() < 1 && flag != 0) {
					// ????????????????????????????????????
					dbParent.setIsLeaf(1);
					dbParent.setIsAssignable(1);
					flag = zxGcsgCtContrApplyWorksMapper.updateByPrimaryKey(dbParent);
				}
			}
			// ????????????????????????
			if (updateList.size() > 0) {
				flag = zxGcsgCtContrApplyWorksMapper.batchUpdateZxGcsgCtContrApplyWorks(updateList);
			}
			// ????????????????????????
			if (insertList.size() > 0) {
				flag = zxGcsgCtContrApplyWorksMapper.batchInsertZxGcsgCtContrApplyWorks(insertList);
				// ???????????????????????????????????????
				if (flag > 0) {
					dbParent.setIsLeaf(0);
					dbParent.setIsAssignable(0);
					flag = zxGcsgCtContrApplyWorksMapper.updateByPrimaryKey(dbParent);
				}
			}
			// ?????????????????????
			ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.selectByPrimaryKey(dbParent.getCtContrApplyId());
			ZxGcsgCtContrApplyWorks contractCost = new ZxGcsgCtContrApplyWorks();
			contractCost.setCtContrApplyId(dbParent.getCtContrApplyId());
			ZxGcsgCtContrApplyWorks dbContractCost = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksContractCost(contractCost);
			if (dbZxGcsgCtContrApply != null) {
				dbZxGcsgCtContrApply.setContractCost(dbContractCost.getContractAmt());
				dbZxGcsgCtContrApply.setContractCostNoTax(dbContractCost.getAmtNoTax());
				BigDecimal contractCostTax = CalcUtils.calcSubtract(dbContractCost.getContractAmt(),
						dbContractCost.getAmtNoTax());
				dbZxGcsgCtContrApply.setContractCostTax(contractCostTax);
				dbZxGcsgCtContrApply.setModifyUserInfo(userKey, realName);
				flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtContrApplyWorks);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity batchEditAllListZxGcsgCtContrApplyWorks(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// check,???????????????????????????????????????(??????)
		Map<String, Object> globalCheck = new HashMap<String, Object>();
		ZxGcsgCtContrApplyWorks search = new ZxGcsgCtContrApplyWorks();
		search.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
		List<ZxGcsgCtContrApplyWorks> allWorksList = zxGcsgCtContrApplyWorksMapper
				.selectByZxGcsgCtContrApplyWorksList(search);
		if (allWorksList.size() > 0) {
			allWorksList.stream().forEach(works -> {
				globalCheck.put(works.getWorkNo(), works.getCtContrApplyWorksId());
			});
		}
		// ??????????????????
		List<ZxGcsgCtContrApplyWorks> updateList = Lists.newArrayList();
		List<ZxGcsgCtContrApplyWorks> deleteList = Lists.newArrayList();
		// ??????????????????id????????????????????????????????????
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getTreeNode())) {
			ZxGcsgCtContrApplyWorks param = new ZxGcsgCtContrApplyWorks();
			param.setTreeNode(zxGcsgCtContrApplyWorks.getTreeNode());
			param.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			List<ZxGcsgCtContrApplyWorks> dbAllList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListByParentID(param);
			// 1???????????????????????????????????????????????????????????????????????????????????????
			if (dbAllList != null && dbAllList.size() > 0 && zxGcsgCtContrApplyWorks.getCtContrApplyWorksList() != null
					&& dbAllList.size() != zxGcsgCtContrApplyWorks.getCtContrApplyWorksList().size()) {
				for (int i = 0; i < dbAllList.size(); i++) {
					for (ZxGcsgCtContrApplyWorks ctContrApplyWorks : zxGcsgCtContrApplyWorks
							.getCtContrApplyWorksList()) {
						if (StrUtil.equals(dbAllList.get(i).getCtContrApplyWorksId(),
								ctContrApplyWorks.getCtContrApplyWorksId())) {
							dbAllList.remove(i);
							i--;
							break;
						}
					}
				}
				if (dbAllList.size() > 0) {
					deleteList.addAll(dbAllList);
					// ????????????????????????
					// flag =
					// zxGcsgCtContrApplyWorksMapper.batchDeleteZxGcsgCtContrApplyWorks(dbAllList);
				}
			}
			// 2??????????????????????????????
			if (zxGcsgCtContrApplyWorks.getCtContrApplyWorksList() != null
					&& zxGcsgCtContrApplyWorks.getCtContrApplyWorksList().size() > 0) {
				// List<ZxGcsgCtContrApplyWorks> updateList = Lists.newArrayList();
				// ??????????????????
				for (ZxGcsgCtContrApplyWorks ctContrApplyWorks : zxGcsgCtContrApplyWorks.getCtContrApplyWorksList()) {
					if (StrUtil.isNotEmpty(ctContrApplyWorks.getCtContrApplyWorksId())) {
						ZxGcsgCtContrApplyWorks updata = zxGcsgCtContrApplyWorksMapper
								.selectByPrimaryKey(ctContrApplyWorks.getCtContrApplyWorksId());
						if (updata != null) {
							updata.setWorkType(ctContrApplyWorks.getWorkType());
							updata.setWorkNo(ctContrApplyWorks.getWorkNo());
							updata.setWorkName(ctContrApplyWorks.getWorkName());
							updata.setUnit(ctContrApplyWorks.getUnit());
							updata.setQuantity(ctContrApplyWorks.getQuantity());
							updata.setContractPrice(ctContrApplyWorks.getContractPrice());
							updata.setTaxRate(ctContrApplyWorks.getTaxRate());
							// updata.setContractPriceNoTax(ctContrApplyWorks.getPriceNoTax());
							if (StrUtil.isNotEmpty(ctContrApplyWorks.getTaxRate())
									&& NumberUtil.isNumber(ctContrApplyWorks.getTaxRate())) {
								updata.setContractPriceNoTax(CalcUtils.calcDivide(ctContrApplyWorks.getContractPrice(),
										CalcUtils.calcAdd(BigDecimal.ONE, CalcUtils.calcMultiply(
												new BigDecimal(ctContrApplyWorks.getTaxRate()), new BigDecimal(0.01))),
										6));
							} else {
								updata.setContractPriceNoTax(BigDecimal.ZERO);
							}
							updata.setRuleID(ctContrApplyWorks.getRuleID());
							updata.setRuleName(ctContrApplyWorks.getRuleName());
							updata.setRemarks(ctContrApplyWorks.getRemarks());
							updata.setModifyUserInfo(userKey, realName);
							// ???????????????????????????,?????????????????????????????????
							updata.setPrice(ctContrApplyWorks.getContractPrice());
							updata.setPriceNoTax(updata.getContractPriceNoTax());
							updata.setContractQty(ctContrApplyWorks.getQuantity());
							// contractAmt += wmDto.getPrice() * wmDto.getQuantity() / 10000;
							updata.setCheckQty(ctContrApplyWorks.getQuantity());
							updata.setExpectChangeQty(ctContrApplyWorks.getQuantity());
							updata.setExpectChangePrice(ctContrApplyWorks.getContractPrice());
							updateList.add(updata);
						}
					}
				}
			}
			// check,???????????????????????????(??????)
			// 1??????????????????????????????check??????
			if (deleteList.size() > 0) {
				Iterator<ZxGcsgCtContrApplyWorks> it = deleteList.iterator();
				while (it.hasNext()) {
					ZxGcsgCtContrApplyWorks itData = it.next();
					if (itData.getIsLeaf() != 1) {
						return repEntity.layerMessage("no", "???????????????????????????,?????????????????????");
					}
					// ??????check
					globalCheck.remove(itData.getWorkNo());
				}
			}
			// 2??????????????????????????????check??????
			if (updateList.size() > 0) {
				updateList.stream().forEach(updateData -> {
					Iterator<Map.Entry<String, Object>> it = globalCheck.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String, Object> entry = it.next();
						if (StrUtil.equals(updateData.getCtContrApplyWorksId(), String.valueOf(entry.getValue())))
							it.remove();
					}
				});
				// 3???check??????????????????(???????????????????????????????????????)
				for (ZxGcsgCtContrApplyWorks works : updateList) {
					// check????????????
					if (globalCheck.containsKey(works.getWorkNo())) {
						return repEntity.layerMessage("no", "????????????????????????,??????????????????");
					} else {
						globalCheck.put(works.getWorkNo(), works.getCtContrApplyWorksId());
					}
				}
			}
			if (deleteList.size() > 0) {
				// ????????????????????????
				flag = zxGcsgCtContrApplyWorksMapper.batchDeleteZxGcsgCtContrApplyWorks(deleteList);
			}
			// ????????????????????????
			if (updateList.size() > 0) {
				flag = zxGcsgCtContrApplyWorksMapper.batchUpdateZxGcsgCtContrApplyWorks(updateList);
			}
			if (flag != 0) {
				// ???????????????????????????????????????????????????????????????????????????
				ZxGcsgCtContrApplyWorks leaf = new ZxGcsgCtContrApplyWorks();
				leaf.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
				zxGcsgCtContrApplyWorksMapper.updateZxGcsgCtContrApplyWorksByNotLeaf(leaf);
			}
			// ?????????????????????
			ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			ZxGcsgCtContrApplyWorks contractCost = new ZxGcsgCtContrApplyWorks();
			contractCost.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			ZxGcsgCtContrApplyWorks dbContractCost = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksContractCost(contractCost);
			if (dbZxGcsgCtContrApply != null) {
				dbZxGcsgCtContrApply.setContractCost(dbContractCost.getContractAmt());
				dbZxGcsgCtContrApply.setContractCostNoTax(dbContractCost.getAmtNoTax());
				BigDecimal contractCostTax = CalcUtils.calcSubtract(dbContractCost.getContractAmt(),
						dbContractCost.getAmtNoTax());
				dbZxGcsgCtContrApply.setContractCostTax(contractCostTax);
				zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			}
		}
		// if (flag == 0) {
		// return repEntity.errorSave();
		// } else {
		return repEntity.ok("sys.data.update", zxGcsgCtContrApplyWorks);
		// }
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksEditAllList(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		if (zxGcsgCtContrApplyWorks == null) {
			zxGcsgCtContrApplyWorks = new ZxGcsgCtContrApplyWorks();
		}
		// ????????????,??????????????????????????????????????????
		List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getTreeNode())) {
			zxGcsgCtContrApplyWorksList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListByParentID(zxGcsgCtContrApplyWorks);
		}
		return repEntity.okList(zxGcsgCtContrApplyWorksList, zxGcsgCtContrApplyWorksList.size());

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity autoHookZxGcsgCtContrApplyWorksProcess(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// ?????????????????????????????????,??????????????????????????????(??????????????????????????????-???????????????)
		// ?????????????????????????????????(???????????????????????????)
		List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())) {
			// ?????????????????????????????????-???????????????
			ZxGcsgCtContrProcessGuajie guajie = new ZxGcsgCtContrProcessGuajie();
			guajie.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(guajie);
			// ???????????????????????????????????????
			zxGcsgCtContrApplyWorks.setIsLeaf(1);
			zxGcsgCtContrApplyWorksList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListByParentID(zxGcsgCtContrApplyWorks);
		}
		// ??????????????????????????????????????????(contr?????????fjgxk?????????gdgxk?????????szgxk?????????tlgxk??????)
		if (StrUtil.equals("contr", zxGcsgCtContrApplyWorks.getInputWorkType())) {
			// ???????????????????????????
			List<ZxCtContrProcess> fbProcessList = zxCtContrProcessMapper
					.selectByZxCtContrProcessList(new ZxCtContrProcess());
			List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
			if (zxGcsgCtContrApplyWorksList.size() > 0 && fbProcessList.size() > 0) {
				for (ZxGcsgCtContrApplyWorks dbApplyWorks : zxGcsgCtContrApplyWorksList) {
					for (ZxCtContrProcess dbProcess : fbProcessList) {
						if (StrUtil.equals(dbApplyWorks.getWorkNo(), dbProcess.getProcessNo())) {
							ZxGcsgCtContrProcessGuajie insertData = new ZxGcsgCtContrProcessGuajie();
							insertData.setCtContrProcessGuajieId(UuidUtil.generate());
							insertData.setCcWorksId("");
							insertData.setApplyAlterWorksId(dbApplyWorks.getCtContrApplyWorksId());
							insertData.setCtContrApplyId(dbApplyWorks.getCtContrApplyId());
							insertData.setCtContractId("");
							insertData.setProcessID(dbProcess.getId());
							insertData.setProcessNo(dbProcess.getProcessNo());
							insertData.setProcessName(dbProcess.getProcessName());
							insertData.setProcessUnit(dbProcess.getProcessUnit());
							insertData.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
							if (dbProcess.getIsLeaf() != null && NumberUtil.isNumber(dbProcess.getIsLeaf())) {
								insertData.setIsLeaf(Integer.parseInt(dbProcess.getIsLeaf()));
							}
							insertData.setCreateUserInfo(userKey, realName);
							// check????????????????????????,??????????????????
							int count = zxGcsgCtContrProcessGuajieMapper
									.countZxGcsgCtContrProcessGuajieByCondition(insertData);
							if (count < 1) {
								insertList.add(insertData);
								// 1???check????????????????????????????????????????????????????????????
								ZxCtContrProcess zxCtContrProcess = new ZxCtContrProcess();
								zxCtContrProcess.setTreeNode(dbProcess.getTreeNode());
								List<ZxCtContrProcess> list = zxCtContrProcessMapper
										.selectByZxCtContrProcessList(zxCtContrProcess);
								if (list.size() > 0) {
									list.stream().forEach(process -> {
										ZxGcsgCtContrProcessGuajie insertData2 = new ZxGcsgCtContrProcessGuajie();
										insertData2.setCtContrProcessGuajieId(UuidUtil.generate());
										insertData2.setCcWorksId("");
										insertData2.setApplyAlterWorksId(dbApplyWorks.getCtContrApplyWorksId());
										insertData2.setCtContrApplyId(dbApplyWorks.getCtContrApplyId());
										insertData2.setCtContractId("");
										insertData2.setProcessID(process.getId());
										insertData2.setProcessNo(process.getProcessNo());
										insertData2.setProcessName(process.getProcessName());
										insertData2.setProcessUnit(process.getProcessUnit());
										insertData2.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
										if (process.getIsLeaf() != null && NumberUtil.isNumber(process.getIsLeaf())) {
											insertData2.setIsLeaf(Integer.parseInt(process.getIsLeaf()));
										}
										insertData2.setCreateUserInfo(userKey, realName);
										// check????????????????????????,??????????????????
										int count2 = zxGcsgCtContrProcessGuajieMapper
												.countZxGcsgCtContrProcessGuajieByCondition(insertData2);
										if (count2 < 1 && !StrUtil.equals(insertData.getProcessNo(),
												insertData2.getProcessNo())) {
											insertList.add(insertData2);
										}
									});
								} else {
									// 1???check?????????????????????????????????????????????????????????
									ZxCtContrProcess zxCtContrProcess2 = new ZxCtContrProcess();
									zxCtContrProcess2.setParentID(dbProcess.getParentID());
									List<ZxCtContrProcess> list2 = zxCtContrProcessMapper
											.selectByZxCtContrProcessList(zxCtContrProcess2);
									if (list2.size() == 1) {
										ZxCtContrProcess dbParent = zxCtContrProcessMapper
												.selectByPrimaryKey(dbProcess.getParentID());
										if (dbParent != null) {
											ZxGcsgCtContrProcessGuajie insertData3 = new ZxGcsgCtContrProcessGuajie();
											insertData3.setCtContrProcessGuajieId(UuidUtil.generate());
											insertData3.setCcWorksId("");
											insertData3.setApplyAlterWorksId(dbApplyWorks.getCtContrApplyWorksId());
											insertData3.setCtContrApplyId(dbApplyWorks.getCtContrApplyId());
											insertData3.setCtContractId("");
											insertData3.setProcessID(dbParent.getId());
											insertData3.setProcessNo(dbParent.getProcessNo());
											insertData3.setProcessName(dbParent.getProcessName());
											insertData3.setProcessUnit(dbParent.getProcessUnit());
											insertData3.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
											if (dbParent.getIsLeaf() != null
													&& NumberUtil.isNumber(dbParent.getIsLeaf())) {
												insertData3.setIsLeaf(Integer.parseInt(dbParent.getIsLeaf()));
											}
											insertData3.setCreateUserInfo(userKey, realName);
											// check????????????????????????,??????????????????
											int count3 = zxGcsgCtContrProcessGuajieMapper
													.countZxGcsgCtContrProcessGuajieByCondition(insertData3);
											if (count3 < 1) {
												insertList.add(insertData3);
											}
										}
									}
								}
							}
							// ?????????????????????????????????
							break;
						}
					}
				}
				if (insertList.size() > 0) {
					flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
				}
			}
		} else {
			// ??????????????????????????????
			ZxCtSZProcess zxCtSZProcess = new ZxCtSZProcess();
			zxCtSZProcess.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
			List<ZxCtSZProcess> qtProcessList = zxCtSZProcessMapper.selectByZxCtSZProcessList(zxCtSZProcess);
			List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
			if (zxGcsgCtContrApplyWorksList.size() > 0 && qtProcessList.size() > 0) {
				for (ZxGcsgCtContrApplyWorks dbApplyWorks : zxGcsgCtContrApplyWorksList) {
					for (ZxCtSZProcess dbProcess : qtProcessList) {
						if (StrUtil.equals(dbApplyWorks.getWorkNo(), dbProcess.getProcessNo())) {
							ZxGcsgCtContrProcessGuajie insertData = new ZxGcsgCtContrProcessGuajie();
							insertData.setCtContrProcessGuajieId(UuidUtil.generate());
							insertData.setCcWorksId("");
							insertData.setApplyAlterWorksId(dbApplyWorks.getCtContrApplyWorksId());
							insertData.setCtContrApplyId(dbApplyWorks.getCtContrApplyId());
							insertData.setCtContractId("");
							insertData.setProcessID(dbProcess.getId());
							insertData.setProcessNo(dbProcess.getProcessNo());
							insertData.setProcessName(dbProcess.getProcessName());
							insertData.setProcessUnit(dbProcess.getProcessUnit());
							insertData.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
							if (dbProcess.getIsLeaf() != null && NumberUtil.isNumber(dbProcess.getIsLeaf())) {
								insertData.setIsLeaf(Integer.parseInt(dbProcess.getIsLeaf()));
							}
							insertData.setCreateUserInfo(userKey, realName);
							// check????????????????????????,??????????????????
							int count = zxGcsgCtContrProcessGuajieMapper
									.countZxGcsgCtContrProcessGuajieByCondition(insertData);
							if (count < 1) {
								insertList.add(insertData);
								// 1???check????????????????????????????????????????????????????????????
								ZxCtSZProcess zxCtSZProcess2 = new ZxCtSZProcess();
								zxCtSZProcess2.setTreeNode(dbProcess.getTreeNode());
								List<ZxCtSZProcess> list = zxCtSZProcessMapper
										.selectByZxCtSZProcessList(zxCtSZProcess2);
								if (list.size() > 0) {
									list.stream().forEach(process -> {
										ZxGcsgCtContrProcessGuajie insertData2 = new ZxGcsgCtContrProcessGuajie();
										insertData2.setCtContrProcessGuajieId(UuidUtil.generate());
										insertData2.setCcWorksId("");
										insertData2.setApplyAlterWorksId(dbApplyWorks.getCtContrApplyWorksId());
										insertData2.setCtContrApplyId(dbApplyWorks.getCtContrApplyId());
										insertData2.setCtContractId("");
										insertData2.setProcessID(process.getId());
										insertData2.setProcessNo(process.getProcessNo());
										insertData2.setProcessName(process.getProcessName());
										insertData2.setProcessUnit(process.getProcessUnit());
										insertData2.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
										if (process.getIsLeaf() != null && NumberUtil.isNumber(process.getIsLeaf())) {
											insertData2.setIsLeaf(Integer.parseInt(process.getIsLeaf()));
										}
										insertData2.setCreateUserInfo(userKey, realName);
										// check????????????????????????,??????????????????
										int count2 = zxGcsgCtContrProcessGuajieMapper
												.countZxGcsgCtContrProcessGuajieByCondition(insertData2);
										if (count2 < 1 && !StrUtil.equals(insertData.getProcessNo(),
												insertData2.getProcessNo())) {
											insertList.add(insertData2);
										}
									});
								} else {
									// 1???check?????????????????????????????????????????????????????????
									ZxCtSZProcess zxCtSZProcess3 = new ZxCtSZProcess();
									zxCtSZProcess3.setParentID(dbProcess.getParentID());
									List<ZxCtSZProcess> list2 = zxCtSZProcessMapper
											.selectByZxCtSZProcessList(zxCtSZProcess3);
									if (list2.size() == 1) {
										ZxCtSZProcess dbParent = zxCtSZProcessMapper
												.selectByPrimaryKey(dbProcess.getParentID());
										if (dbParent != null) {
											ZxGcsgCtContrProcessGuajie insertData3 = new ZxGcsgCtContrProcessGuajie();
											insertData3.setCtContrProcessGuajieId(UuidUtil.generate());
											insertData3.setCcWorksId("");
											insertData3.setApplyAlterWorksId(dbApplyWorks.getCtContrApplyWorksId());
											insertData3.setCtContrApplyId(dbApplyWorks.getCtContrApplyId());
											insertData3.setCtContractId("");
											insertData3.setProcessID(dbParent.getId());
											insertData3.setProcessNo(dbParent.getProcessNo());
											insertData3.setProcessName(dbParent.getProcessName());
											insertData3.setProcessUnit(dbParent.getProcessUnit());
											insertData3.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
											if (dbParent.getIsLeaf() != null
													&& NumberUtil.isNumber(dbParent.getIsLeaf())) {
												insertData3.setIsLeaf(Integer.parseInt(dbParent.getIsLeaf()));
											}
											insertData3.setCreateUserInfo(userKey, realName);
											// check????????????????????????,??????????????????
											int count3 = zxGcsgCtContrProcessGuajieMapper
													.countZxGcsgCtContrProcessGuajieByCondition(insertData3);
											if (count3 < 1) {
												insertList.add(insertData3);
											}
										}
									}
								}
							}
							// ???????????????????????????????????????
							break;
						}
					}
				}
				// ????????????
				if (insertList.size() > 0) {
					flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
				}
			}
		}
//		// ??????????????????id?????????????????????????????????
//		ZxGcsgCtContrProcessGuajie deleteCondition = new ZxGcsgCtContrProcessGuajie();
//		deleteCondition.setCtContrApplyId(zxGcsgCtContrApplyWorksList.get(0).getCtContrApplyId());
//		// deleteCondition.setProcessID(processID);
//		deleteCondition.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
//		zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(deleteCondition);
		// if (flag == 0) {
		// return repEntity.errorSave();
		// } else {
		return repEntity.ok("sys.data.sava", zxGcsgCtContrApplyWorks);
		// }
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity manualHookZxGcsgCtContrApplyWorksProcess(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// ????????????????????????????????????
		// ???????????????????????????(contr?????????fjgxk?????????gdgxk?????????szgxk?????????tlgxk??????)
		ZxGcsgCtContrApplyWorks dbCtContrApplyWorks = zxGcsgCtContrApplyWorksMapper
				.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getCtContrApplyWorksId());
		// ????????????????????????????????????????????????????????????????????????(????????????????????????)
		List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
		if (dbCtContrApplyWorks != null && zxGcsgCtContrApplyWorks.getProcessArray() != null
				&& zxGcsgCtContrApplyWorks.getProcessArray().size() > 0) {
			JSONArray jsonArr = zxGcsgCtContrApplyWorks.getProcessArray();
			for (int i = 0; i < jsonArr.size(); i++) {
				ZxGcsgCtContrProcessGuajie insertData = new ZxGcsgCtContrProcessGuajie();
				insertData.setCtContrProcessGuajieId(UuidUtil.generate());
				insertData.setCcWorksId("");
				insertData.setApplyAlterWorksId(dbCtContrApplyWorks.getCtContrApplyWorksId());
				insertData.setCtContrApplyId(dbCtContrApplyWorks.getCtContrApplyId());
				insertData.setCtContractId("");
				insertData.setProcessID(jsonArr.getJSONObject(i).getStr("id"));
				insertData.setProcessNo(jsonArr.getJSONObject(i).getStr("processNo"));
				insertData.setProcessName(jsonArr.getJSONObject(i).getStr("processName"));
				insertData.setProcessUnit(jsonArr.getJSONObject(i).getStr("processUnit"));
				insertData.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
				if (jsonArr.getJSONObject(i).getStr("isLeaf") != null
						&& NumberUtil.isNumber(jsonArr.getJSONObject(i).getStr("isLeaf"))) {
					insertData.setIsLeaf(Integer.parseInt(jsonArr.getJSONObject(i).getStr("isLeaf")));
				}
				insertData.setCreateUserInfo(userKey, realName);
				insertList.add(insertData);
			}
		}
		// ????????????????????????id?????????????????????????????????
		if (dbCtContrApplyWorks != null && StrUtil.isNotEmpty(dbCtContrApplyWorks.getCtContrApplyWorksId())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getInputWorkType())) {
			ZxGcsgCtContrProcessGuajie deleteCondition = new ZxGcsgCtContrProcessGuajie();
			deleteCondition.setApplyAlterWorksId(dbCtContrApplyWorks.getCtContrApplyWorksId());
			deleteCondition.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(deleteCondition);
		}
		// ????????????
		if (insertList.size() > 0) {
			flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
		}
//		if (flag == 0) {
//			return repEntity.errorSave();
//		} else {
		return repEntity.ok("sys.data.sava", zxGcsgCtContrApplyWorks);
//		}
	}

	@Override
	public ResponseEntity manualHookZxGcsgCtContrApplyWorksRule(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// ???????????????????????????????????????
		ZxGcsgCtContrApplyWorks dbCtContrApplyWorks = zxGcsgCtContrApplyWorksMapper
				.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getCtContrApplyWorksId());
		// ????????????????????????????????????????????????(????????????????????????)
		String ruleName = "";
		if (StrUtil.equals("contr", zxGcsgCtContrApplyWorks.getInputWorkType())) {
			// ???????????????????????????
			ZxCtValuationRules dbZxCtValuationRules = zxCtValuationRulesMapper
					.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getRuleID());
			ruleName = dbZxCtValuationRules != null ? dbZxCtValuationRules.getRuleName() : "";
		} else {
			ZxCtValuationSZRules dbZxCtValuationSZRules = zxCtValuationSZRulesMapper
					.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getRuleID());
			ruleName = dbZxCtValuationSZRules != null ? dbZxCtValuationSZRules.getRuleName() : "";
		}
		if (dbCtContrApplyWorks != null) {
			dbCtContrApplyWorks.setRuleID(zxGcsgCtContrApplyWorks.getRuleID());
			dbCtContrApplyWorks.setRuleName(ruleName);
			dbCtContrApplyWorks.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContrApplyWorksMapper.updateByPrimaryKey(dbCtContrApplyWorks);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtContrApplyWorks);
		}
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksListAmount(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		if (zxGcsgCtContrApplyWorks == null) {
			zxGcsgCtContrApplyWorks = new ZxGcsgCtContrApplyWorks();
		}
		// ?????????????????????ctContrApplyId??????????????????
		// ????????????parentID???????????????????????????????????????????????????
		List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())
				|| StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getTreeNode())) {
			// ??????????????????
			zxGcsgCtContrApplyWorks.setParentID("");
			zxGcsgCtContrApplyWorksList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListByParentID(zxGcsgCtContrApplyWorks);
			if (zxGcsgCtContrApplyWorksList.size() > 0) {
				for (ZxGcsgCtContrApplyWorks dbWorks : zxGcsgCtContrApplyWorksList) {
					// ??????????????????????????????????????????????????????
					// ????????????=????????????*??????
					// ???????????????=????????????/(1+??????)
					if (dbWorks.getIsLeaf() == 1) {
						BigDecimal contractAmt = CalcUtils.calcMultiply(dbWorks.getContractPrice(),
								dbWorks.getContractQty());
						BigDecimal amtNoTax = BigDecimal.ZERO;
						if (dbWorks.getTaxRate() != null && NumberUtil.isNumber(dbWorks.getTaxRate())) {
							amtNoTax = CalcUtils.calcDivide(contractAmt, CalcUtils.calcAdd(BigDecimal.ONE,
									CalcUtils.calcDivide(new BigDecimal(dbWorks.getTaxRate()), new BigDecimal(100))),
									2);
						}
						dbWorks.setContractAmt(contractAmt);
						dbWorks.setAmtNoTax(amtNoTax);
					} else {
						// ????????????????????????????????????????????????
						ZxGcsgCtContrApplyWorks search = new ZxGcsgCtContrApplyWorks();
						search.setTreeNode(dbWorks.getTreeNode());
						search.setCtContrApplyId(dbWorks.getCtContrApplyId());
						ZxGcsgCtContrApplyWorks dbApplyWorks = zxGcsgCtContrApplyWorksMapper
								.getZxGcsgCtContrApplyWorksContractAmt(search);
						if (dbApplyWorks != null) {
							// BigDecimal amtNoTax = CalcUtils.calcDivide(dbApplyWorks.getContractAmt(),
							// CalcUtils.calcAdd(
							// BigDecimal.ONE,
							// CalcUtils.calcDivide(new BigDecimal(dbWorks.getTaxRate()), new
							// BigDecimal(100))),
							// 2);
							dbWorks.setContractAmt(dbApplyWorks.getContractAmt());
							dbWorks.setAmtNoTax(dbApplyWorks.getAmtNoTax());
						}
					}
				}
			}
		}
		// ??????????????????
		JSONArray arr = getTree(JSONUtil.parseArray(zxGcsgCtContrApplyWorksList));
		return repEntity.okList(arr, arr.size());
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksListProcess(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		// ????????????????????????????????????????????????
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if (zxGcsgCtContrApplyWorks != null && StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())) {
			mapList = zxGcsgCtContrApplyWorksMapper.getZxGcsgCtContrApplyWorksListProcess(zxGcsgCtContrApplyWorks);
		}
		return repEntity.okList(mapList, mapList.size());
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksTree(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		if (zxGcsgCtContrApplyWorks == null) {
			zxGcsgCtContrApplyWorks = new ZxGcsgCtContrApplyWorks();
		}
		// ????????????
		List<ZxGcsgCtContrApplyWorks> applyWorksList = zxGcsgCtContrApplyWorksMapper
				.selectByZxGcsgCtContrApplyWorksList(zxGcsgCtContrApplyWorks);
		// JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(applyWorksList),
		// "ctContrApplyWorksId", "parentID",
		// "workName", "isLeaf");
		return repEntity.okList(applyWorksList, applyWorksList.size());
	}

	private JSONArray getTree(JSONArray jsonArr) {
		String child = "children";
		JSONArray r = new JSONArray();
		JSONArray newArr = new JSONArray();
		JSONObject hash = new JSONObject();
		if (jsonArr != null && jsonArr.size() > 0) {
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				JSONObject newJSONObj = new JSONObject();
				newJSONObj.set("ctContrApplyWorksId", json.get("ctContrApplyWorksId"));
				newJSONObj.set("parentID", json.get("parentID"));
				newJSONObj.set("treeNode", json.get("treeNode"));
				newJSONObj.set("orgID", json.get("orgID"));
				newJSONObj.set("ctContrApplyId", json.get("ctContrApplyId"));
				newJSONObj.set("workType", json.get("workType"));
				newJSONObj.set("workNo", json.get("workNo"));
				newJSONObj.set("workName", json.get("workName"));
				newJSONObj.set("unit", json.get("unit"));
				newJSONObj.set("contractPrice", json.get("contractPrice"));
				newJSONObj.set("contractPriceNoTax", json.get("contractPriceNoTax"));
				newJSONObj.set("contractQty", json.get("contractQty"));
				newJSONObj.set("contractAmt", json.get("contractAmt"));
				newJSONObj.set("amtNoTax", json.get("amtNoTax"));
				newJSONObj.set("quantity", json.get("quantity"));
				newJSONObj.set("price", json.get("price"));
				newJSONObj.set("priceNoTax", json.get("priceNoTax"));
				newJSONObj.set("isLeaf", json.get("isLeaf"));
				newJSONObj.set("inputWorkType", json.get("inputWorkType"));
				newJSONObj.set("taxRate", json.get("taxRate"));
				newJSONObj.set("ruleID", json.get("ruleID"));
				newJSONObj.set("ruleName", json.get("ruleName"));
				newJSONObj.set("isDeduct", json.get("isDeduct"));
				newJSONObj.set("remarks", json.get("remarks"));
				newJSONObj.set("processNum", json.get("processNum"));
				hash.set(json.getStr("ctContrApplyWorksId"), newJSONObj);
				newArr.add(newJSONObj);
			}
			for (int j = 0; j < newArr.size(); j++) {
				JSONObject aVal = newArr.getJSONObject(Integer.valueOf(j));
				JSONObject hashVP = hash.getJSONObject(aVal.getStr("parentID"));
				if (hashVP != null) {
					if (hashVP.get(child) != null) {
						JSONArray ch = hashVP.getJSONArray(child);
						ch.add(aVal);
						hashVP.set(child, ch);
					} else {
						JSONArray ch = new JSONArray();
						ch.add(aVal);
						hashVP.set(child, ch);
					}
				} else
					r.add(aVal);
			}
		}
		return r;
	}

}
