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
		// 分页查询
		PageHelper.startPage(zxGcsgCtContrApplyWorks.getPage(), zxGcsgCtContrApplyWorks.getLimit());
		// 获取数据
		List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList = zxGcsgCtContrApplyWorksMapper
				.selectByZxGcsgCtContrApplyWorksList(zxGcsgCtContrApplyWorks);
		// 得到分页信息
		PageInfo<ZxGcsgCtContrApplyWorks> p = new PageInfo<>(zxGcsgCtContrApplyWorksList);

		return repEntity.okList(zxGcsgCtContrApplyWorksList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksDetail(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		if (zxGcsgCtContrApplyWorks == null) {
			zxGcsgCtContrApplyWorks = new ZxGcsgCtContrApplyWorks();
		}
		// 获取数据
		ZxGcsgCtContrApplyWorks dbZxGcsgCtContrApplyWorks = zxGcsgCtContrApplyWorksMapper
				.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getCtContrApplyWorksId());
		// 数据存在
		if (dbZxGcsgCtContrApplyWorks != null) {
			return repEntity.ok(dbZxGcsgCtContrApplyWorks);
		} else {
			return repEntity.layerMessage("no", "无数据！");
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
			// 父节点ID
			dbZxGcsgCtContrApplyWorks.setParentID(zxGcsgCtContrApplyWorks.getParentID());
			// 树节点编号
			dbZxGcsgCtContrApplyWorks.setTreeNode(zxGcsgCtContrApplyWorks.getTreeNode());
			// 责任中心
			dbZxGcsgCtContrApplyWorks.setOrgID(zxGcsgCtContrApplyWorks.getOrgID());
			// 合同评审ID(contrApplyID)
			dbZxGcsgCtContrApplyWorks.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			// 清单类型
			dbZxGcsgCtContrApplyWorks.setWorkType(zxGcsgCtContrApplyWorks.getWorkType());
			// 清单编号
			dbZxGcsgCtContrApplyWorks.setWorkNo(zxGcsgCtContrApplyWorks.getWorkNo());
			// 清单名称
			dbZxGcsgCtContrApplyWorks.setWorkName(zxGcsgCtContrApplyWorks.getWorkName());
			// 计量单位
			dbZxGcsgCtContrApplyWorks.setUnit(zxGcsgCtContrApplyWorks.getUnit());
			// 合同单价
			dbZxGcsgCtContrApplyWorks.setContractPrice(zxGcsgCtContrApplyWorks.getContractPrice());
			// 合同数量
			dbZxGcsgCtContrApplyWorks.setContractQty(zxGcsgCtContrApplyWorks.getContractQty());
			// 合同金额
			dbZxGcsgCtContrApplyWorks.setContractAmt(zxGcsgCtContrApplyWorks.getContractAmt());
			// 工程量
			dbZxGcsgCtContrApplyWorks.setQuantity(zxGcsgCtContrApplyWorks.getQuantity());
			// 计量单价(含税合同单价)
			dbZxGcsgCtContrApplyWorks.setPrice(zxGcsgCtContrApplyWorks.getPrice());
			// 是否删除
			dbZxGcsgCtContrApplyWorks.setDeleted(zxGcsgCtContrApplyWorks.getDeleted());
			// 是否叶子节点
			dbZxGcsgCtContrApplyWorks.setIsLeaf(zxGcsgCtContrApplyWorks.getIsLeaf());
			// 变更状态
			dbZxGcsgCtContrApplyWorks.setExsitStatus(zxGcsgCtContrApplyWorks.getExsitStatus());
			// 是否可分配计量
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
			// 最后编辑时间
			dbZxGcsgCtContrApplyWorks.setEditTime(zxGcsgCtContrApplyWorks.getEditTime());
			// 核定数量
			dbZxGcsgCtContrApplyWorks.setCheckQty(zxGcsgCtContrApplyWorks.getCheckQty());
			// 预计变更后数量
			dbZxGcsgCtContrApplyWorks.setExpectChangeQty(zxGcsgCtContrApplyWorks.getExpectChangeQty());
			// 预计变更后单价
			dbZxGcsgCtContrApplyWorks.setExpectChangePrice(zxGcsgCtContrApplyWorks.getExpectChangePrice());
			// 清单类型
			dbZxGcsgCtContrApplyWorks.setInputWorkType(zxGcsgCtContrApplyWorks.getInputWorkType());
			// 用于判断是否要重算预算单价
			dbZxGcsgCtContrApplyWorks.setIsReCountAmt(zxGcsgCtContrApplyWorks.getIsReCountAmt());
			// old contract qty
			dbZxGcsgCtContrApplyWorks.setQty(zxGcsgCtContrApplyWorks.getQty());
			// 是否局下达
			dbZxGcsgCtContrApplyWorks.setIsGroup(zxGcsgCtContrApplyWorks.getIsGroup());
			// 要求修改
			dbZxGcsgCtContrApplyWorks.setRequestEdit(zxGcsgCtContrApplyWorks.getRequestEdit());
			// 修改人
			dbZxGcsgCtContrApplyWorks.setEditUserID(zxGcsgCtContrApplyWorks.getEditUserID());
			// 修改人
			dbZxGcsgCtContrApplyWorks.setEditUserName(zxGcsgCtContrApplyWorks.getEditUserName());
			// 修改日期
			dbZxGcsgCtContrApplyWorks.setEditDate(zxGcsgCtContrApplyWorks.getEditDate());
			// 原合同不含税合同单价
			dbZxGcsgCtContrApplyWorks.setContractPriceNoTax(zxGcsgCtContrApplyWorks.getContractPriceNoTax());
			// 变更后不含税合同单价
			dbZxGcsgCtContrApplyWorks.setPriceNoTax(zxGcsgCtContrApplyWorks.getPriceNoTax());
			// 税率
			dbZxGcsgCtContrApplyWorks.setTaxRate(zxGcsgCtContrApplyWorks.getTaxRate());
			// 不含税合同金额
			dbZxGcsgCtContrApplyWorks.setAmtNoTax(zxGcsgCtContrApplyWorks.getAmtNoTax());
			// 计价规则ID
			dbZxGcsgCtContrApplyWorks.setRuleID(zxGcsgCtContrApplyWorks.getRuleID());
			// 计价规则名称
			dbZxGcsgCtContrApplyWorks.setRuleName(zxGcsgCtContrApplyWorks.getRuleName());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField1(zxGcsgCtContrApplyWorks.getOpinionField1());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField2(zxGcsgCtContrApplyWorks.getOpinionField2());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField3(zxGcsgCtContrApplyWorks.getOpinionField3());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField4(zxGcsgCtContrApplyWorks.getOpinionField4());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField5(zxGcsgCtContrApplyWorks.getOpinionField5());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField6(zxGcsgCtContrApplyWorks.getOpinionField6());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField7(zxGcsgCtContrApplyWorks.getOpinionField7());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField8(zxGcsgCtContrApplyWorks.getOpinionField8());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField9(zxGcsgCtContrApplyWorks.getOpinionField9());
			// 备注
			dbZxGcsgCtContrApplyWorks.setOpinionField10(zxGcsgCtContrApplyWorks.getOpinionField10());
			// 流程id
			dbZxGcsgCtContrApplyWorks.setApih5FlowId(zxGcsgCtContrApplyWorks.getApih5FlowId());
			// work_id
			dbZxGcsgCtContrApplyWorks.setWorkId(zxGcsgCtContrApplyWorks.getWorkId());
			// 工序审核状态
			dbZxGcsgCtContrApplyWorks.setApih5FlowStatus(zxGcsgCtContrApplyWorks.getApih5FlowStatus());
			// 流程状态
			dbZxGcsgCtContrApplyWorks.setApih5FlowNodeStatus(zxGcsgCtContrApplyWorks.getApih5FlowNodeStatus());
			// 备注
			dbZxGcsgCtContrApplyWorks.setRemarks(zxGcsgCtContrApplyWorks.getRemarks());
			// 排序
			dbZxGcsgCtContrApplyWorks.setSort(zxGcsgCtContrApplyWorks.getSort());
			// 共通
			dbZxGcsgCtContrApplyWorks.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtContrApplyWorksMapper.updateByPrimaryKey(dbZxGcsgCtContrApplyWorks);
		}
		// 失败
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
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCtContrApplyWorksList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

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
			return repEntity.layerMessage("no", "请上传导入模板！");
		}
		// 清单类型1:公路 2:铁路 3:市政 4:房建
		String inputWorkType = zxGcsgCtContrApplyWorks.getInputWorkType();
		if (StrUtil.isNotEmpty(inputWorkType)
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getAttachmentList().get(0).getName())) {
			String fileName = zxGcsgCtContrApplyWorks.getAttachmentList().get(0).getName();
			if (StrUtil.equals("1", inputWorkType) && fileName.indexOf("公路") < 0) {
				return repEntity.layerMessage("no", "上传模板和所选清单类型不一致！");
			} else if (StrUtil.equals("2", inputWorkType) && fileName.indexOf("铁路") < 0) {
				return repEntity.layerMessage("no", "上传模板和所选清单类型不一致！");
			} else if (StrUtil.equals("3", inputWorkType) && fileName.indexOf("市政") < 0) {
				return repEntity.layerMessage("no", "上传模板和所选清单类型不一致！");
			} else if (StrUtil.equals("4", inputWorkType) && fileName.indexOf("房建") < 0) {
				return repEntity.layerMessage("no", "上传模板和所选清单类型不一致！");
			}
		}
		// delType=0 导入前清除相同清单类型的清单 delType=1 导入前清除此机构下所有清单类型的清单
		// delType字段好像没用了,整体覆盖性导入
		// String delType = zxGcsgCtContrApplyWorks.getDelType();
		// 合同评审表主键
		String ctContrApplyId = zxGcsgCtContrApplyWorks.getCtContrApplyId();
		// 模板物理全路径
		String filePath = Apih5Properties.getFilePath()
				+ zxGcsgCtContrApplyWorks.getAttachmentList().get(0).getRelativeUrl();
		// 获取合同评审表数据
		ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper.selectByPrimaryKey(ctContrApplyId);
		// check审核中的不允许导入
		if (StrUtil.equals("1", dbZxGcsgCtContrApply.getApih5FlowStatus())
				|| StrUtil.equals("2", dbZxGcsgCtContrApply.getApih5FlowStatus())) {
			return repEntity.layerMessage("no", "该合同评审正在审核中或已通过审核,不允许导入清单。");
		}
		// 责任中心ID
		// String orgID = dbZxGcsgCtContrApply.getOrgID();
		String orgID = dbZxGcsgCtContrApply.getFirstID();
		// 合同类型(P2工程施工类合同)
		// String contractType = dbZxGcsgCtContrApply.getContractType();
		String contractType = "P2";
		// 合同评审表税率(工程施工合同未存该字段)
		String mainTaxRate = dbZxGcsgCtContrApply.getTaxRate();
		// 获取清单表根节点ID和workType——清单类型
		ZxGcsgCtContrApplyWorks param = new ZxGcsgCtContrApplyWorks();
		param.setParentID("-1");
		param.setCtContrApplyId(ctContrApplyId);
		List<ZxGcsgCtContrApplyWorks> dbRootNodeList = zxGcsgCtContrApplyWorksMapper
				.selectByZxGcsgCtContrApplyWorksList(param);
		ZxGcsgCtContrApplyWorks worksRootNode = new ZxGcsgCtContrApplyWorks();
		// 如果顶级节点清单不存在
		if (dbRootNodeList == null || dbRootNodeList.isEmpty()) {
			String workID = UuidUtil.generate();
			worksRootNode.setCtContrApplyWorksId(workID);
			worksRootNode.setParentID("-1");
			worksRootNode.setTreeNode("1000");
			// 责任中心取自合同甲方ID
			worksRootNode.setOrgID(orgID);
			worksRootNode.setCtContrApplyId(ctContrApplyId);
			// { "10", "合同清单" },{ "11", "管理费清单" }, { "20", "拆分清单" } ;
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
			// 0:非叶子节点
			worksRootNode.setIsLeaf(0);
			// 0正常 1 新增 2 修改 3 删除 老系统此时为传0
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
				return repEntity.layerMessage("no", "新增清单根节点失败。");
			}
		} else {
			worksRootNode = dbRootNodeList.get(0);
		}
		// 清单类型
		String workType = String.valueOf(worksRootNode.getWorkType());
		// ID作为清单一级节点的parentID
		String specParentID = worksRootNode.getCtContrApplyWorksId();
		String specTreeNode = worksRootNode.getTreeNode();
		List<ZxGcsgCtContrApplyWorks> importValueList = Lists.newArrayList();
		// 读取excel
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String, Object>> excelList = reader.readAll();
			if (excelList == null || excelList.size() == 0) {
				return repEntity.layerMessage("no", "导入失败,模板中无导入数据!");
			}
			// 增加不含税合同单价的导入，2个只能导入一个，根据标题来控制
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
				// 数据从第1行开始(默认忽略标题行)，默认规则:1、不允许第一列有间隔行
				for (int k = 0; k < excelList.size(); k++) {
					Map<String, Object> row = excelList.get(k);
					// 清单编号
					String worksNo = String.valueOf(row.get("清单编号"));
					// 如果清单编号为空，对本行不进行处理
					if (StrUtil.isEmpty(worksNo)) {
						continue;
					}
					// 清单名称
					String worksName = String.valueOf(row.get("清单名称"));
					// 单位
					String unit = "";
					if (!StrUtil.equals("4", inputWorkType)) {
						unit = String.valueOf(row.get("单位"));
					} else {
						unit = String.valueOf(row.get("计量单位"));
					}
					// 数量
					BigDecimal quantity = BigDecimal.ZERO;
					// 判断数量单元的类型;判断为空的情况
					if (StrUtil.isNotEmpty(String.valueOf(row.get("数量")))) {
						if (NumberUtil.isNumber(String.valueOf(row.get("数量")))) {
							quantity = new BigDecimal(String.valueOf(row.get("数量")));
						} else {
							String errMessage = "该Excel第" + (k + 1) + "行第" + 4 + "列数据格式不正确(应为数值类型),不能正常进行导入！请调整后重新导入";
							return repEntity.layerMessage("no", errMessage);
						}
					}
					// 单价
					BigDecimal price = BigDecimal.ZERO;
					if (StrUtil.isNotEmpty(String.valueOf(row.get(costTitle)))) {
						if (NumberUtil.isNumber(String.valueOf(row.get(costTitle)))) {
							price = new BigDecimal(String.valueOf(row.get(costTitle)));
						} else {
							String errMessage = "该Excel第" + (k + 1) + "行第" + 5 + "列数据格式不正确(应为数值类型),不能正常进行导入！请调整后重新导入";
							return repEntity.layerMessage("no", errMessage);
						}
					}
					String worksTaxRate = "";
					if (StrUtil.equals("不含税合同单价", costTitle)) {
						worksTaxRate = String.valueOf(row.get("税率"));
					} else if (StrUtil.equals("含税合同单价", costTitle) && StrUtil.equals("2", inputWorkType)) {
						worksTaxRate = String.valueOf(row.get("税率"));
					} else if (StrUtil.equals("含税合同单价", costTitle) && !StrUtil.equals("2", inputWorkType)) {
						worksTaxRate = String.valueOf(row.get("税率(%)"));
					} else {
						// 老系统第六列无check
						// String errMessage = "该Excel第" + (k + 1) + "行第" + 6 +
						// "列数据格式不正确(应为数值类型),不能正常进行导入！请调整后重新导入";
						// return repEntity.layerMessage("no", errMessage);
					}
					// 去除名称中的回车换行
					worksNo = worksNo.replaceAll("\n", "");
					worksName = worksName.replaceAll("\n", "");
					unit = unit.replaceAll("\n", "");
					// 整理清单新增数据
					ZxGcsgCtContrApplyWorks excelWorks = new ZxGcsgCtContrApplyWorks();
					if ("不含税合同单价".equals(costTitle)) {
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
					// 主键
					excelWorks.setCtContrApplyWorksId(UuidUtil.generate());
					excelWorks.setCtContrApplyId(ctContrApplyId);
					// 给清单编序号——以导入的顺序编号，初始化清单ID，设置叶子节点和is标志
					excelWorks.setWorkNo(worksNo);
					excelWorks.setWorkName(worksName);
					excelWorks.setUnit(unit);
					excelWorks.setQuantity(quantity);
					// 责任中心ID
					excelWorks.setOrgID(orgID);
					excelWorks.setWorkType(Integer.parseInt(workType));
					excelWorks.setSort(sort);
					excelWorks.setWorkNoLength(getWorkNoLength(worksNo, inputWorkType));
					// 先设置所有清单为叶子节点，在新增后设置不是叶子节点的清单
					excelWorks.setIsLeaf(1);
					// 0正常 1 新增 2 修改 3 删除
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
					// 只有工程施工类合同新增税率
					// excelWorks.setTaxRate(worksTaxRate);
					importValueList.add(excelWorks);
					sort++;
				}
			} else {
				String errMessage = "该Excel没有数据,不能正常进行导入！";
				return repEntity.layerMessage("no", errMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭reader，释放内存
			if (reader != null) {
				reader.close();
			}
		}
		// 增加重复清单编号的判断
		String checkInfo = null;
		try {
			checkInfo = workNoExistCheckForImp(importValueList, orgID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!StrUtil.equals("0", checkInfo)) {
			return repEntity.layerMessage("no", checkInfo);
		}
		// 获得最大的编号长度、最小编号长度
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
		// 设置父节点ID——parentID以及treeNode
		// 以下三个变量用来设置TreeNode
		// 找出已有清单中长度为8的treeNode中最大的那个
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
					// 找到此节点之前的第一个编号长度比当前编号短的就是他的父节点
					int index = workNoInfo.getSort();
					// 判断是否能够找到编号比当前编号短的父节点
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
							// 找到编号长度比当前节点短的父节点了
							flag = false;
							break;
						}
					}
					if (flag) {
						// 没有找到，设置父节点的清单编号以及ID
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
//		// double contractAmt = 0;// 含税合同金额
//		BigDecimal contractAmt = BigDecimal.ZERO;
//		for (Iterator i = resultList.iterator(); i.hasNext();) {
//			ZxGcsgCtContrApplyWorks wmDto = (ZxGcsgCtContrApplyWorks) i.next();
//			contractAmt = CalcUtils.calcAdd(contractAmt, CalcUtils
//					.calcDivide(CalcUtils.calcMultiply(wmDto.getPrice(), wmDto.getQuantity()), new BigDecimal(10000)));
//			// contractAmt += wmDto.getPrice() * wmDto.getQuantity() / 10000;
//		}
		// 1.首先备份删除SQL
		ZxGcsgCtContrApplyWorks delete = new ZxGcsgCtContrApplyWorks();
		delete.setCtContrApplyId(ctContrApplyId);
		// delType=0 导入前清除相同清单类型的清单 delType=1 导入前清除此机构下所有清单类型的清单
		// if (StrUtil.equals("0", delType)) {
		// delete.setInputWorkType(inputWorkType);
		// }
		sqlFlag = zxGcsgCtContrApplyWorksMapper.deleteZxGcsgCtContrApplyWorksByCondition(delete);
		if (sqlFlag != 0) {
			// 删除工序关联表
			ZxGcsgCtContrProcessGuajie guajie = new ZxGcsgCtContrProcessGuajie();
			guajie.setCtContrApplyId(ctContrApplyId);
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(guajie);
			// 删除单价分析表以及明细表
			ZxGcsgCtPriceSys zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
			zxGcsgCtPriceSys.setCtContrApplyId(ctContrApplyId);
			zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItemByCondition(zxGcsgCtPriceSys);
		}
		// 2.执行插入数据
		sqlFlag = zxGcsgCtContrApplyWorksMapper.batchInsertZxGcsgCtContrApplyWorks(resultList);
		// 更新合同金额
		// String updateContract = "update iect_Contract set contractCost = " +
		// contractAmt + " where ID='" + orgID + "'";
		// jdbcTmp.execute(updateContract);
		// 重新设置是否叶子节点，判断条件为如果清单ID存在parentID中的话，就非叶子节点，否则为叶子节点。zhangdj 2009-03-07
		ZxGcsgCtContrApplyWorks update = new ZxGcsgCtContrApplyWorks();
		update.setCtContrApplyId(ctContrApplyId);
		sqlFlag = zxGcsgCtContrApplyWorksMapper.updateZxGcsgCtContrApplyWorksByLeaf(update);
		// 导入完成后更新主表合同金额
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
		// 删除上传的模板
		File temp = new File(filePath);
		if (temp.exists()) {
			temp.delete();
		}
		return repEntity.ok("导入成功。");
	}

	/**
	 * 获取编号“长度”——这里的长度是以“-”分割的字符数量
	 * 
	 * @param workNo
	 * @return
	 */
	// 保存workNo有差异的节点
	private String difWorkNo = ",";
	private boolean flag = false;

	private int getWorkNoLength(String workNo, String inputWorkType) {
		if (inputWorkType.equals("1")) {
			String[] workNoArr = workNo.split("-");
			// 如果是 103，104 等，也只节，不是章
			if (workNoArr.length <= 1) {// 当workNo是以00结尾时、是章
				// 处理百章下包含多个小百章的情况
				if ((workNo.length() > 1 && !"00".equals(workNo.substring(workNo.length() - 2, workNo.length())))) {
					// 用于记录这是小百章的情况
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
			// 判断是否存在中文字符，如果存在中文字符，该字符不计入编号长度
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
	 * 检查是否存在相同清单编号(导入专用).
	 * 
	 * @param importValueList
	 * @param orgID
	 * @param workBookID
	 * @return
	 * @throws IEMSException
	 */
	private String workNoExistCheckForImp(List importValueList, String orgID) throws Exception {
		// 返回字符串
		StringBuffer rtnStrBuf = new StringBuffer();

		if (importValueList != null && !importValueList.isEmpty()) {
			Map workNoMapInExcel = new HashMap();
			for (Iterator it = importValueList.iterator(); it.hasNext();) {
				ZxGcsgCtContrApplyWorks workModel = (ZxGcsgCtContrApplyWorks) it.next();
				if (workNoMapInExcel.containsKey(workModel.getWorkNo())) {
					rtnStrBuf.append("编号为“").append(workModel.getWorkNo()).append("”在excel中重复！！！\n");
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
		// 获取数据,有子节点则只显示子节点,无子节点显示父节点部分信息
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
		// check,清单编号、清单名称不能重复(新增、修改)(已加)
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
		// 一、编辑下级清单时parentID不能为空(可修改、新增、删除)
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
			// 对比db数据和页面数据
			List<ZxGcsgCtContrApplyWorks> insertList = Lists.newArrayList();
			List<ZxGcsgCtContrApplyWorks> updateList = Lists.newArrayList();
			List<ZxGcsgCtContrApplyWorks> deleteList = Lists.newArrayList();
			List<ZxGcsgCtContrApplyWorks> pageUpdateList = Lists.newArrayList();
			List<ZxGcsgCtContrApplyWorks> pageChildrenList = zxGcsgCtContrApplyWorks.getCtContrApplyWorksList();
			// 保存前端的排序
			int index = 1;
			if (dbParent != null && pageChildrenList.size() > 0) {
				for (ZxGcsgCtContrApplyWorks works : pageChildrenList) {
					int temp_TreeNode = 1000 + index;
					works.setTreeNode(dbParent.getTreeNode() + temp_TreeNode);
					index++;
				}
			}
			// 1、数据库、页面都有数据
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
				// ①删除
				if (dbChildrenList.size() > 0) {
					// 删除过滤后的集合
					deleteList.addAll(dbChildrenList);
					// flag =
					// zxGcsgCtContrApplyWorksMapper.batchDeleteZxGcsgCtContrApplyWorks(dbChildrenList);
				}
				// int maxStepTreeNode = 1000;
				// ②修改
				if (pageUpdateList.size() > 0) {
					for (ZxGcsgCtContrApplyWorks ctContrApplyWorks : pageUpdateList) {
						// 整理修改集合
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
							// 以上字段为页面字段,以下修改字段为联动字段
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
				// ③新增
				if (pageChildrenList.size() > 0) {
					// int index = 1;
					for (ZxGcsgCtContrApplyWorks ctContrApplyWorks : pageChildrenList) {
						ZxGcsgCtContrApplyWorks insertData = new ZxGcsgCtContrApplyWorks();
						// 主键
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
						// 给清单编序号——以导入的顺序编号，初始化清单ID，设置叶子节点和is标志
						insertData.setWorkNo(ctContrApplyWorks.getWorkNo());
						insertData.setWorkName(ctContrApplyWorks.getWorkName());
						insertData.setUnit(ctContrApplyWorks.getUnit());
						insertData.setQuantity(ctContrApplyWorks.getQuantity());
						// 责任中心ID
						insertData.setOrgID(dbParent.getOrgID());
						// 合同清单
						insertData.setWorkType(10);
						// insertData.setSort(sort);
						// 新增的一定是叶子节点
						insertData.setIsLeaf(1);
						// 0正常 1 新增 2 修改 3 删除
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
						// 清单类型 1：公路 2：铁路 3：市政
						// 取父节点清单类型
						insertData.setInputWorkType(dbParent.getInputWorkType());
						insertData.setCreateUserInfo(userKey, realName);
						insertList.add(insertData);
					}
				}
			}
			// 2、数据库为空、页面有数据
			else if ((dbChildrenList == null || dbChildrenList.size() < 1) && pageChildrenList != null
					&& pageChildrenList.size() > 0) {
				// 直接新增
				// 整理新增集合
				// int index = 1;
				for (ZxGcsgCtContrApplyWorks ctContrApplyWorks : pageChildrenList) {
					ZxGcsgCtContrApplyWorks insertData = new ZxGcsgCtContrApplyWorks();
					// 主键
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
					// 给清单编序号——以导入的顺序编号，初始化清单ID，设置叶子节点和is标志
					insertData.setWorkNo(ctContrApplyWorks.getWorkNo());
					insertData.setWorkName(ctContrApplyWorks.getWorkName());
					insertData.setUnit(ctContrApplyWorks.getUnit());
					insertData.setQuantity(ctContrApplyWorks.getQuantity());
					// 责任中心ID
					insertData.setOrgID(dbParent.getOrgID());
					// 合同清单
					insertData.setWorkType(10);
					// insertData.setSort(sort);
					// 新增的一定是叶子节点
					insertData.setIsLeaf(1);
					// 0正常 1 新增 2 修改 3 删除
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
					// 清单类型 1：公路 2：铁路 3：市政
					// 取父节点清单类型
					insertData.setInputWorkType(dbParent.getInputWorkType());
					insertData.setCreateUserInfo(userKey, realName);
					insertList.add(insertData);
				}
			}
			// 3、数据库有数据、页面为空
			else if (dbChildrenList != null && dbChildrenList.size() > 0
					&& (pageChildrenList == null || pageChildrenList.size() < 1)) {
				// 直接删除
				deleteList.addAll(dbChildrenList);
				// 删除该parentID下所有清单
				// flag =
				// zxGcsgCtContrApplyWorksMapper.deleteZxGcsgCtContrApplyWorksByParentID(zxGcsgCtContrApplyWorks);
			}
			// check,非叶子节点不能删除(已加)
			// 1、去掉删除集合里面的check数据
			if (deleteList.size() > 0) {
				Iterator<ZxGcsgCtContrApplyWorks> it = deleteList.iterator();
				while (it.hasNext()) {
					ZxGcsgCtContrApplyWorks itData = it.next();
					if (itData.getIsLeaf() != 1) {
						return repEntity.layerMessage("no", "非叶子节点不可删除,请检查后重试。");
					}
					// 清空check
					globalCheck.remove(itData.getWorkNo());
				}
			}
			// 2、去掉修改集合里面的check数据
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
			// 3、check编号是否重复(保持最终结果编号不重复即可)
			List<ZxGcsgCtContrApplyWorks> allList = Lists.newArrayList();
			allList.addAll(updateList);
			allList.addAll(insertList);
			if (allList.size() > 0) {
				for (ZxGcsgCtContrApplyWorks works : allList) {
					// check页面编号
					if (globalCheck.containsKey(works.getWorkNo())) {
						return repEntity.layerMessage("no", "清单编号不可重复,检查后再试。");
					} else {
						globalCheck.put(works.getWorkNo(), works.getCtContrApplyWorksId());
					}
				}
			}
			// 批量删除数据
			if (deleteList.size() > 0) {
				flag = zxGcsgCtContrApplyWorksMapper.batchDeleteZxGcsgCtContrApplyWorks(deleteList);
				if (updateList.size() < 1 && insertList.size() < 1 && flag != 0) {
					// 将上级节点设置为叶子节点
					dbParent.setIsLeaf(1);
					dbParent.setIsAssignable(1);
					flag = zxGcsgCtContrApplyWorksMapper.updateByPrimaryKey(dbParent);
				}
			}
			// 批量修改原有数据
			if (updateList.size() > 0) {
				flag = zxGcsgCtContrApplyWorksMapper.batchUpdateZxGcsgCtContrApplyWorks(updateList);
			}
			// 批量插入新增数据
			if (insertList.size() > 0) {
				flag = zxGcsgCtContrApplyWorksMapper.batchInsertZxGcsgCtContrApplyWorks(insertList);
				// 将上级节点设置为非叶子节点
				if (flag > 0) {
					dbParent.setIsLeaf(0);
					dbParent.setIsAssignable(0);
					flag = zxGcsgCtContrApplyWorksMapper.updateByPrimaryKey(dbParent);
				}
			}
			// 更新合同表数据
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
		// check,清单编号、清单名称不能重复(修改)
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
		// 可修改、删除
		List<ZxGcsgCtContrApplyWorks> updateList = Lists.newArrayList();
		List<ZxGcsgCtContrApplyWorks> deleteList = Lists.newArrayList();
		// 根据评审合同id获取该合同的所有清单数据
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getTreeNode())) {
			ZxGcsgCtContrApplyWorks param = new ZxGcsgCtContrApplyWorks();
			param.setTreeNode(zxGcsgCtContrApplyWorks.getTreeNode());
			param.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			List<ZxGcsgCtContrApplyWorks> dbAllList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListByParentID(param);
			// 1、用数据库数据和传递过来的需要修改的集合获取需要删除的集合
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
					// 删除过滤后的集合
					// flag =
					// zxGcsgCtContrApplyWorksMapper.batchDeleteZxGcsgCtContrApplyWorks(dbAllList);
				}
			}
			// 2、处理需要修改的集合
			if (zxGcsgCtContrApplyWorks.getCtContrApplyWorksList() != null
					&& zxGcsgCtContrApplyWorks.getCtContrApplyWorksList().size() > 0) {
				// List<ZxGcsgCtContrApplyWorks> updateList = Lists.newArrayList();
				// 整理修改集合
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
							// 以上字段为页面字段,以下修改字段为联动字段
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
			// check,非叶子节点不能删除(已加)
			// 1、去掉删除集合里面的check数据
			if (deleteList.size() > 0) {
				Iterator<ZxGcsgCtContrApplyWorks> it = deleteList.iterator();
				while (it.hasNext()) {
					ZxGcsgCtContrApplyWorks itData = it.next();
					if (itData.getIsLeaf() != 1) {
						return repEntity.layerMessage("no", "非叶子节点不可删除,请检查后重试。");
					}
					// 清空check
					globalCheck.remove(itData.getWorkNo());
				}
			}
			// 2、去掉修改集合里面的check数据
			if (updateList.size() > 0) {
				updateList.stream().forEach(updateData -> {
					Iterator<Map.Entry<String, Object>> it = globalCheck.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String, Object> entry = it.next();
						if (StrUtil.equals(updateData.getCtContrApplyWorksId(), String.valueOf(entry.getValue())))
							it.remove();
					}
				});
				// 3、check编号是否重复(保持最终结果编号不重复即可)
				for (ZxGcsgCtContrApplyWorks works : updateList) {
					// check页面编号
					if (globalCheck.containsKey(works.getWorkNo())) {
						return repEntity.layerMessage("no", "清单编号不可重复,检查后再试。");
					} else {
						globalCheck.put(works.getWorkNo(), works.getCtContrApplyWorksId());
					}
				}
			}
			if (deleteList.size() > 0) {
				// 删除过滤后的集合
				flag = zxGcsgCtContrApplyWorksMapper.batchDeleteZxGcsgCtContrApplyWorks(deleteList);
			}
			// 批量修改原有数据
			if (updateList.size() > 0) {
				flag = zxGcsgCtContrApplyWorksMapper.batchUpdateZxGcsgCtContrApplyWorks(updateList);
			}
			if (flag != 0) {
				// 批量将叶子节点被全部删除的非叶子节点修改为叶子节点
				ZxGcsgCtContrApplyWorks leaf = new ZxGcsgCtContrApplyWorks();
				leaf.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
				zxGcsgCtContrApplyWorksMapper.updateZxGcsgCtContrApplyWorksByNotLeaf(leaf);
			}
			// 更新合同表数据
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
		// 获取数据,显示父节点以及子节点所有信息
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
		// 将该合同评审的所有清单,挂接选中类型的工序库(已挂接的做不重复挂接-老系统业务)
		// 获取需要挂接的清单集合(该合同评审所有清单)
		List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())) {
			// 清空该评审所有挂接关系-新系统业务
			ZxGcsgCtContrProcessGuajie guajie = new ZxGcsgCtContrProcessGuajie();
			guajie.setCtContrApplyId(zxGcsgCtContrApplyWorks.getCtContrApplyId());
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(guajie);
			// 获取该评审所有清单叶子节点
			zxGcsgCtContrApplyWorks.setIsLeaf(1);
			zxGcsgCtContrApplyWorksList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListByParentID(zxGcsgCtContrApplyWorks);
		}
		// 根据选中类型获取对应的工序库(contr分包、fjgxk房建、gdgxk轨道、szgxk市政、tlgxk铁路)
		if (StrUtil.equals("contr", zxGcsgCtContrApplyWorks.getInputWorkType())) {
			// 获取分包标准工序库
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
							// check该关系是否已存在,已存在则过滤
							int count = zxGcsgCtContrProcessGuajieMapper
									.countZxGcsgCtContrProcessGuajieByCondition(insertData);
							if (count < 1) {
								insertList.add(insertData);
								// 1、check该工序如果有子节点则须将所有下级节点挂接
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
										// check该关系是否已存在,已存在则过滤
										int count2 = zxGcsgCtContrProcessGuajieMapper
												.countZxGcsgCtContrProcessGuajieByCondition(insertData2);
										if (count2 < 1 && !StrUtil.equals(insertData.getProcessNo(),
												insertData2.getProcessNo())) {
											insertList.add(insertData2);
										}
									});
								} else {
									// 1、check该工序如果没有兄弟节点则须将父节点挂接
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
											// check该关系是否已存在,已存在则过滤
											int count3 = zxGcsgCtContrProcessGuajieMapper
													.countZxGcsgCtContrProcessGuajieByCondition(insertData3);
											if (count3 < 1) {
												insertList.add(insertData3);
											}
										}
									}
								}
							}
							// 清单与工序编号不可重复
							break;
						}
					}
				}
				if (insertList.size() > 0) {
					flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
				}
			}
		} else {
			// 获取其它类型的工序库
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
							// check该关系是否已存在,已存在则过滤
							int count = zxGcsgCtContrProcessGuajieMapper
									.countZxGcsgCtContrProcessGuajieByCondition(insertData);
							if (count < 1) {
								insertList.add(insertData);
								// 1、check该工序如果有子节点则须将所有下级节点挂接
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
										// check该关系是否已存在,已存在则过滤
										int count2 = zxGcsgCtContrProcessGuajieMapper
												.countZxGcsgCtContrProcessGuajieByCondition(insertData2);
										if (count2 < 1 && !StrUtil.equals(insertData.getProcessNo(),
												insertData2.getProcessNo())) {
											insertList.add(insertData2);
										}
									});
								} else {
									// 1、check该工序如果没有兄弟节点则须将父节点挂接
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
											// check该关系是否已存在,已存在则过滤
											int count3 = zxGcsgCtContrProcessGuajieMapper
													.countZxGcsgCtContrProcessGuajieByCondition(insertData3);
											if (count3 < 1) {
												insertList.add(insertData3);
											}
										}
									}
								}
							}
							// 清单库与工序库编号不可重复
							break;
						}
					}
				}
				// 然后新增
				if (insertList.size() > 0) {
					flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
				}
			}
		}
//		// 根据合同评审id、工序库类型删除关系表
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
		// 选中一条清单进行挂接工序
		// 获取需要挂接的清单(contr分包、fjgxk房建、gdgxk轨道、szgxk市政、tlgxk铁路)
		ZxGcsgCtContrApplyWorks dbCtContrApplyWorks = zxGcsgCtContrApplyWorksMapper
				.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getCtContrApplyWorksId());
		// 获取选中的公路、房建、铁路、轨道、市政标准工序库(这里需要前端传入)
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
		// 根据合同评审清单id、工序库类型删除关系表
		if (dbCtContrApplyWorks != null && StrUtil.isNotEmpty(dbCtContrApplyWorks.getCtContrApplyWorksId())
				&& StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getInputWorkType())) {
			ZxGcsgCtContrProcessGuajie deleteCondition = new ZxGcsgCtContrProcessGuajie();
			deleteCondition.setApplyAlterWorksId(dbCtContrApplyWorks.getCtContrApplyWorksId());
			deleteCondition.setBaseType(zxGcsgCtContrApplyWorks.getInputWorkType());
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(deleteCondition);
		}
		// 然后新增
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
		// 获取需要挂接计价规则的清单
		ZxGcsgCtContrApplyWorks dbCtContrApplyWorks = zxGcsgCtContrApplyWorksMapper
				.selectByPrimaryKey(zxGcsgCtContrApplyWorks.getCtContrApplyWorksId());
		// 根据选中的工序库类型获取计价规则(这里需要前端传入)
		String ruleName = "";
		if (StrUtil.equals("contr", zxGcsgCtContrApplyWorks.getInputWorkType())) {
			// 调取分包计价规则库
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
		// 首次进入页面传ctContrApplyId获取所有清单
		// 页面中传parentID获取父节点以及父节点下面的所有清单
		List<ZxGcsgCtContrApplyWorks> zxGcsgCtContrApplyWorksList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getCtContrApplyId())
				|| StrUtil.isNotEmpty(zxGcsgCtContrApplyWorks.getTreeNode())) {
			// 避免前端传错
			zxGcsgCtContrApplyWorks.setParentID("");
			zxGcsgCtContrApplyWorksList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListByParentID(zxGcsgCtContrApplyWorks);
			if (zxGcsgCtContrApplyWorksList.size() > 0) {
				for (ZxGcsgCtContrApplyWorks dbWorks : zxGcsgCtContrApplyWorksList) {
					// 叶子节点需要用含税单价和数量进行计算
					// 含税金额=含税单价*数量
					// 不含税金额=含税金额/(1+税率)
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
						// 非叶子节点则需要直接获取合计金额
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
		// 返回树形结构
		JSONArray arr = getTree(JSONUtil.parseArray(zxGcsgCtContrApplyWorksList));
		return repEntity.okList(arr, arr.size());
	}

	@Override
	public ResponseEntity getZxGcsgCtContrApplyWorksListProcess(ZxGcsgCtContrApplyWorks zxGcsgCtContrApplyWorks) {
		// 获取该合同评审的清单工序挂接台账
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
		// 获取数据
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
