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
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrProcessMapper;
import com.apih5.mybatis.dao.ZxCtSZProcessMapper;
import com.apih5.mybatis.dao.ZxCtValuationRulesMapper;
import com.apih5.mybatis.dao.ZxCtValuationSZRulesMapper;
import com.apih5.mybatis.dao.ZxGcsgCcWorksMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrProcessGuajieMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContractMapper;
import com.apih5.mybatis.dao.ZxGcsgCtPriceSysMapper;
import com.apih5.mybatis.dao.ZxGcsgSaCoWorkLinkWorkMapper;
import com.apih5.mybatis.pojo.ZxCtContrProcess;
import com.apih5.mybatis.pojo.ZxCtSZProcess;
import com.apih5.mybatis.pojo.ZxCtValuationRules;
import com.apih5.mybatis.pojo.ZxCtValuationSZRules;
import com.apih5.mybatis.pojo.ZxGcsgCcWorks;
import com.apih5.mybatis.pojo.ZxGcsgCtContrProcessGuajie;
import com.apih5.mybatis.pojo.ZxGcsgCtContract;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSys;
import com.apih5.mybatis.pojo.ZxGcsgSaCoWorkLinkWork;
import com.apih5.service.ZxGcsgCcWorksService;
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

@Service("zxGcsgCcWorksService")
public class ZxGcsgCcWorksServiceImpl implements ZxGcsgCcWorksService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxGcsgCcWorksMapper zxGcsgCcWorksMapper;
	@Autowired(required = true)
	private ZxGcsgCtContractMapper zxGcsgCtContractMapper;
	@Autowired(required = true)
	private ZxCtContrProcessMapper zxCtContrProcessMapper;
	@Autowired(required = true)
	private ZxGcsgCtContrProcessGuajieMapper zxGcsgCtContrProcessGuajieMapper;
	@Autowired(required = true)
	private ZxCtSZProcessMapper zxCtSZProcessMapper;
	@Autowired(required = true)
	private ZxCtValuationRulesMapper zxCtValuationRulesMapper;
	@Autowired(required = true)
	private ZxCtValuationSZRulesMapper zxCtValuationSZRulesMapper;
	@Autowired(required = true)
	private ZxGcsgCtPriceSysMapper zxGcsgCtPriceSysMapper;
	@Autowired(required = true)
	private ZxGcsgSaCoWorkLinkWorkMapper zxGcsgSaCoWorkLinkWorkMapper;

	@Override
	public ResponseEntity getZxGcsgCcWorksListByCondition(ZxGcsgCcWorks zxGcsgCcWorks) {
		if (zxGcsgCcWorks == null) {
			zxGcsgCcWorks = new ZxGcsgCcWorks();
		}
		// 分页查询
		PageHelper.startPage(zxGcsgCcWorks.getPage(), zxGcsgCcWorks.getLimit());
		// 获取数据
		List<ZxGcsgCcWorks> zxGcsgCcWorksList = zxGcsgCcWorksMapper.selectByZxGcsgCcWorksList(zxGcsgCcWorks);
		// 得到分页信息
		PageInfo<ZxGcsgCcWorks> p = new PageInfo<>(zxGcsgCcWorksList);

		return repEntity.okList(zxGcsgCcWorksList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCcWorksDetail(ZxGcsgCcWorks zxGcsgCcWorks) {
		if (zxGcsgCcWorks == null) {
			zxGcsgCcWorks = new ZxGcsgCcWorks();
		}
		// 获取数据
		ZxGcsgCcWorks dbZxGcsgCcWorks = zxGcsgCcWorksMapper.selectByPrimaryKey(zxGcsgCcWorks.getCcWorksId());
		// 数据存在
		if (dbZxGcsgCcWorks != null) {
			return repEntity.ok(dbZxGcsgCcWorks);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgCcWorks(ZxGcsgCcWorks zxGcsgCcWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxGcsgCcWorks.setCcWorksId(UuidUtil.generate());
		zxGcsgCcWorks.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgCcWorksMapper.insert(zxGcsgCcWorks);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCcWorks);
		}
	}

	@Override
	public ResponseEntity updateZxGcsgCcWorks(ZxGcsgCcWorks zxGcsgCcWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCcWorks dbZxGcsgCcWorks = zxGcsgCcWorksMapper.selectByPrimaryKey(zxGcsgCcWorks.getCcWorksId());
		if (dbZxGcsgCcWorks != null && StrUtil.isNotEmpty(dbZxGcsgCcWorks.getCcWorksId())) {
			// 父节点ID
			dbZxGcsgCcWorks.setParentID(zxGcsgCcWorks.getParentID());
			// 树节点编号
			dbZxGcsgCcWorks.setTreeNode(zxGcsgCcWorks.getTreeNode());
			// 责任中心
			dbZxGcsgCcWorks.setOrgID(zxGcsgCcWorks.getOrgID());
			// 清单书ID(workBookID)
			dbZxGcsgCcWorks.setCcCoBookId(zxGcsgCcWorks.getCcCoBookId());
			// 合同管理ID(新建字段)
			dbZxGcsgCcWorks.setCtContractId(zxGcsgCcWorks.getCtContractId());
			// 清单类型
			dbZxGcsgCcWorks.setWorkType(zxGcsgCcWorks.getWorkType());
			// 清单编号
			dbZxGcsgCcWorks.setWorkNo(zxGcsgCcWorks.getWorkNo());
			// 清单名称
			dbZxGcsgCcWorks.setWorkName(zxGcsgCcWorks.getWorkName());
			// 计量单位
			dbZxGcsgCcWorks.setUnit(zxGcsgCcWorks.getUnit());
			// 合同单价
			dbZxGcsgCcWorks.setContractPrice(zxGcsgCcWorks.getContractPrice());
			// 合同数量
			dbZxGcsgCcWorks.setContractQty(zxGcsgCcWorks.getContractQty());
			// 合同金额
			dbZxGcsgCcWorks.setContractAmt(zxGcsgCcWorks.getContractAmt());
			// 工程量
			dbZxGcsgCcWorks.setQuantity(zxGcsgCcWorks.getQuantity());
			// 计量单价
			dbZxGcsgCcWorks.setPrice(zxGcsgCcWorks.getPrice());
			// 是否删除
			dbZxGcsgCcWorks.setDeleted(zxGcsgCcWorks.getDeleted());
			// 是否叶子节点
			dbZxGcsgCcWorks.setIsLeaf(zxGcsgCcWorks.getIsLeaf());
			// 变更状态
			dbZxGcsgCcWorks.setExsitStatus(zxGcsgCcWorks.getExsitStatus());
			// 是否可分配计量
			dbZxGcsgCcWorks.setIsAssignable(zxGcsgCcWorks.getIsAssignable());
			// updateFlag
			dbZxGcsgCcWorks.setUpdateFlag(zxGcsgCcWorks.getUpdateFlag());
			// 废弃字段
			dbZxGcsgCcWorks.setCombProp(zxGcsgCcWorks.getCombProp());
			// pp1
			dbZxGcsgCcWorks.setPp1(zxGcsgCcWorks.getPp1());
			// pp2
			dbZxGcsgCcWorks.setPp2(zxGcsgCcWorks.getPp2());
			// pp3
			dbZxGcsgCcWorks.setPp3(zxGcsgCcWorks.getPp3());
			// pp4
			dbZxGcsgCcWorks.setPp4(zxGcsgCcWorks.getPp4());
			// pp5
			dbZxGcsgCcWorks.setPp5(zxGcsgCcWorks.getPp5());
			// pp6
			dbZxGcsgCcWorks.setPp6(zxGcsgCcWorks.getPp6());
			// pp7
			dbZxGcsgCcWorks.setPp7(zxGcsgCcWorks.getPp7());
			// pp8
			dbZxGcsgCcWorks.setPp8(zxGcsgCcWorks.getPp8());
			// pp9
			dbZxGcsgCcWorks.setPp9(zxGcsgCcWorks.getPp9());
			// pp10
			dbZxGcsgCcWorks.setPp10(zxGcsgCcWorks.getPp10());
			// 最后编辑时间
			dbZxGcsgCcWorks.setEditTime(zxGcsgCcWorks.getEditTime());
			// 核定数量
			dbZxGcsgCcWorks.setCheckQty(zxGcsgCcWorks.getCheckQty());
			// 预计变更后数量
			dbZxGcsgCcWorks.setExpectChangeQty(zxGcsgCcWorks.getExpectChangeQty());
			// 预计变更后单价
			dbZxGcsgCcWorks.setExpectChangePrice(zxGcsgCcWorks.getExpectChangePrice());
			// 清单类型
			dbZxGcsgCcWorks.setInputWorkType(zxGcsgCcWorks.getInputWorkType());
			// 用于判断是否要重算预算单价
			dbZxGcsgCcWorks.setIsReCountAmt(zxGcsgCcWorks.getIsReCountAmt());
			// old contract qty
			dbZxGcsgCcWorks.setQty(zxGcsgCcWorks.getQty());
			// 是否局下达
			dbZxGcsgCcWorks.setIsGroup(zxGcsgCcWorks.getIsGroup());
			// 原合同不含税合同单价
			dbZxGcsgCcWorks.setContractPriceNoTax(zxGcsgCcWorks.getContractPriceNoTax());
			// 变更后不含税合同单价
			dbZxGcsgCcWorks.setPriceNoTax(zxGcsgCcWorks.getPriceNoTax());
			// 原合同不含税合同金额
			dbZxGcsgCcWorks.setContractAmtNoTax(zxGcsgCcWorks.getContractAmtNoTax());
			// 变更后不含税金额
			dbZxGcsgCcWorks.setAmtNoTax(zxGcsgCcWorks.getAmtNoTax());
			// 税率
			dbZxGcsgCcWorks.setTaxRate(zxGcsgCcWorks.getTaxRate());
			// 备注
			dbZxGcsgCcWorks.setOpinionField1(zxGcsgCcWorks.getOpinionField1());
			// 备注
			dbZxGcsgCcWorks.setOpinionField2(zxGcsgCcWorks.getOpinionField2());
			// 备注
			dbZxGcsgCcWorks.setOpinionField3(zxGcsgCcWorks.getOpinionField3());
			// 备注
			dbZxGcsgCcWorks.setOpinionField4(zxGcsgCcWorks.getOpinionField4());
			// 备注
			dbZxGcsgCcWorks.setOpinionField5(zxGcsgCcWorks.getOpinionField5());
			// 备注
			dbZxGcsgCcWorks.setOpinionField6(zxGcsgCcWorks.getOpinionField6());
			// 备注
			dbZxGcsgCcWorks.setOpinionField7(zxGcsgCcWorks.getOpinionField7());
			// 备注
			dbZxGcsgCcWorks.setOpinionField8(zxGcsgCcWorks.getOpinionField8());
			// 备注
			dbZxGcsgCcWorks.setOpinionField9(zxGcsgCcWorks.getOpinionField9());
			// 备注
			dbZxGcsgCcWorks.setOpinionField10(zxGcsgCcWorks.getOpinionField10());
			// 流程id
			dbZxGcsgCcWorks.setApih5FlowId(zxGcsgCcWorks.getApih5FlowId());
			// work_id
			dbZxGcsgCcWorks.setWorkId(zxGcsgCcWorks.getWorkId());
			// 工序审核状态
			dbZxGcsgCcWorks.setApih5FlowStatus(zxGcsgCcWorks.getApih5FlowStatus());
			// 流程状态
			dbZxGcsgCcWorks.setApih5FlowNodeStatus(zxGcsgCcWorks.getApih5FlowNodeStatus());
			// 备注
			dbZxGcsgCcWorks.setRemarks(zxGcsgCcWorks.getRemarks());
			// 排序
			dbZxGcsgCcWorks.setSort(zxGcsgCcWorks.getSort());
			// 共通
			dbZxGcsgCcWorks.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcWorksMapper.updateByPrimaryKey(dbZxGcsgCcWorks);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCcWorks);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxGcsgCcWorks(List<ZxGcsgCcWorks> zxGcsgCcWorksList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxGcsgCcWorksList != null && zxGcsgCcWorksList.size() > 0) {
			ZxGcsgCcWorks zxGcsgCcWorks = new ZxGcsgCcWorks();
			zxGcsgCcWorks.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcWorksMapper.batchDeleteUpdateZxGcsgCcWorks(zxGcsgCcWorksList, zxGcsgCcWorks);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCcWorksList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity importZxGcsgCcWorks(ZxGcsgCcWorks zxGcsgCcWorks) {
		// http://192.168.1.133:8889/iecmDataImportAction.iems?p=importWorksExcelFile
		// &workBookID=34632505-175b0374bb9-bf9153e05ed5f2346f3f6269fbe6b2fc
		// &isMasterContr=false&isFromContrApply=true&inputWorkType=1&delType=0
		int sqlFlag = 0;
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		if (zxGcsgCcWorks == null || zxGcsgCcWorks.getAttachmentList() == null
				|| zxGcsgCcWorks.getAttachmentList().size() == 0) {
			return repEntity.layerMessage("no", "请上传导入模板！");
		}
		// 清单类型1:公路 2:铁路 3:市政 4:房建
		String inputWorkType = zxGcsgCcWorks.getInputWorkType();
		if (StrUtil.isNotEmpty(inputWorkType)
				&& StrUtil.isNotEmpty(zxGcsgCcWorks.getAttachmentList().get(0).getName())) {
			String fileName = zxGcsgCcWorks.getAttachmentList().get(0).getName();
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
		// 合同管理表主键
		String ctContractId = zxGcsgCcWorks.getCtContractId();
		// 模板物理全路径
		String filePath = Apih5Properties.getFilePath() + zxGcsgCcWorks.getAttachmentList().get(0).getRelativeUrl();
		// 获取合同管理表
		ZxGcsgCtContract dbZxGcsgCtContract = zxGcsgCtContractMapper.selectByPrimaryKey(ctContractId);
		// 责任中心ID
		String orgID = dbZxGcsgCtContract.getFirstId();
		// 合同类型(P2工程施工类合同)
		// String contractType = dbZxGcsgCtContract.getContractType();
		String contractType = "P2";
		// 合同管理表税率(工程施工合同未存该字段)
		String mainTaxRate = dbZxGcsgCtContract.getTaxRate();
		// 获取清单表根节点ID和workType——清单类型
		ZxGcsgCcWorks param = new ZxGcsgCcWorks();
		param.setParentID("-1");
		param.setCtContractId(ctContractId);
		List<ZxGcsgCcWorks> dbRootNodeList = zxGcsgCcWorksMapper.selectByZxGcsgCcWorksList(param);
		ZxGcsgCcWorks worksRootNode = new ZxGcsgCcWorks();
		// 如果顶级节点清单不存在
		if (dbRootNodeList == null || dbRootNodeList.isEmpty()) {
			String workID = UuidUtil.generate();
			worksRootNode.setCcWorksId(workID);
			worksRootNode.setParentID("-1");
			worksRootNode.setTreeNode("1000");
			// 责任中心取自合同甲方ID
			worksRootNode.setOrgID(orgID);
			worksRootNode.setCtContractId(ctContractId);
			// { "10", "合同清单" },{ "11", "管理费清单" }, { "20", "拆分清单" } ;
			worksRootNode.setWorkType(10);
			worksRootNode.setWorkNo("-");
			worksRootNode.setWorkName(dbZxGcsgCtContract.getContractName());
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
			// 0正常 1 新增 2 修改 3 删除(老系统传的是0)
			worksRootNode.setExsitStatus(1);
			worksRootNode.setIsAssignable(0);
			worksRootNode.setEditTime(DateUtil.date());
			worksRootNode.setCheckQty(BigDecimal.ZERO);
			worksRootNode.setExpectChangeQty(BigDecimal.ZERO);
			worksRootNode.setExpectChangePrice(BigDecimal.ZERO);
			worksRootNode.setQty(BigDecimal.ZERO);
			worksRootNode.setUpdateFlag("N");
			worksRootNode.setCreateUserInfo(userKey, realName);
			sqlFlag = zxGcsgCcWorksMapper.insert(worksRootNode);
			if (sqlFlag == 0) {
				return repEntity.layerMessage("no", "新增清单根节点失败。");
			}
		} else {
			worksRootNode = dbRootNodeList.get(0);
		}
		// 清单类型
		String workType = String.valueOf(worksRootNode.getWorkType());
		// ID作为清单一级节点的parentID
		String specParentID = worksRootNode.getCcWorksId();
		String specTreeNode = worksRootNode.getTreeNode();
		List<ZxGcsgCcWorks> importValueList = Lists.newArrayList();
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
					ZxGcsgCcWorks excelWorks = new ZxGcsgCcWorks();
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
					excelWorks.setCcWorksId(UuidUtil.generate());
					excelWorks.setCtContractId(ctContractId);
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
					// 0正常 1 新增 2 修改 3 删除(老系统传的是0)
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
			ZxGcsgCcWorks workNoInfo = (ZxGcsgCcWorks) i.next();
			int len = workNoInfo.getWorkNoLength();
			if (maxNoLen < len) {
				maxNoLen = len;
			}
			if (minNoLen > len) {
				minNoLen = len;
			}
		}
		List<ZxGcsgCcWorks> resultList = Lists.newArrayList();
		// 设置父节点ID——parentID以及treeNode
		// 以下三个变量用来设置TreeNode
		// 找出已有清单中长度为8的treeNode中最大的那个
		int lenCount = 0;
		ZxGcsgCcWorks ccWorks = new ZxGcsgCcWorks();
		ccWorks.setCtContractId(ctContractId);
		ZxGcsgCcWorks maxTreeNode = zxGcsgCcWorksMapper.getZxGcsgCcWorksMAXTreeNode(ccWorks);
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
			Iterator<ZxGcsgCcWorks> j = importValueList.iterator();
			while (j.hasNext()) {
				ZxGcsgCcWorks workNoInfo = j.next();
				if (i == workNoInfo.getWorkNoLength()) {
					// 找到此节点之前的第一个编号长度比当前编号短的就是他的父节点
					int index = workNoInfo.getSort();
					// 判断是否能够找到编号比当前编号短的父节点
					boolean flag = true;
					for (int m = index; m >= 0; m--) {
						ZxGcsgCcWorks pWorkNoInfo = importValueList.get(m);
						if (pWorkNoInfo.getWorkNoLength() < workNoInfo.getWorkNoLength()) {
							// workNoInfo.setPWorkNo(pWorkNoInfo.getWorkNo());
							workNoInfo.setParentID(pWorkNoInfo.getCcWorksId());
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
//		// double contractAmt = 0;// 合同金额
//		BigDecimal contractAmt = BigDecimal.ZERO;// 合同金额
//		for (Iterator i = resultList.iterator(); i.hasNext();) {
//			ZxGcsgCcWorks wmDto = (ZxGcsgCcWorks) i.next();
//			contractAmt = CalcUtils.calcAdd(contractAmt, CalcUtils
//					.calcDivide(CalcUtils.calcMultiply(wmDto.getPrice(), wmDto.getQuantity()), new BigDecimal(10000)));
//			// contractAmt += wmDto.getPrice() * wmDto.getQuantity() / 10000;
//		}
		// 1.首先备份删除SQL
		ZxGcsgCcWorks delete = new ZxGcsgCcWorks();
		delete.setCtContractId(ctContractId);
		// delType=0 导入前清除相同清单类型的清单 delType=1 导入前清除此机构下所有清单类型的清单
		// if (StrUtil.equals("0", delType)) {
		// delete.setInputWorkType(inputWorkType);
		// }
		sqlFlag = zxGcsgCcWorksMapper.deleteZxGcsgCcWorksByCondition(delete);
		if (sqlFlag != 0) {
			// 删除工序关联表
			ZxGcsgCtContrProcessGuajie guajie = new ZxGcsgCtContrProcessGuajie();
			guajie.setCtContractId(ctContractId);
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(guajie);
			// 删除单价分析表以及明细表
			ZxGcsgCtPriceSys zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
			zxGcsgCtPriceSys.setCtContractId(ctContractId);
			zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItemByCondition(zxGcsgCtPriceSys);
			// 删除分包清单与业主合同清单关联表
			ZxGcsgSaCoWorkLinkWork workLinkWork = new ZxGcsgSaCoWorkLinkWork();
			workLinkWork.setCtContractId(ctContractId);
			zxGcsgSaCoWorkLinkWorkMapper.deleteZxGcsgSaCoWorkLinkWorkByCondition(workLinkWork);
		}
		// 2.执行插入数据
		sqlFlag = zxGcsgCcWorksMapper.batchInsertZxGcsgCcWorks(resultList);
		// 更新合同金额
		// String updateContract = "update iect_Contract set contractCost = " +
		// contractAmt + " where ID='" + orgID + "'";
		// jdbcTmp.execute(updateContract);
		// 重新设置是否叶子节点，判断条件为如果清单ID存在parentID中的话，就非叶子节点，否则为叶子节点。zhangdj 2009-03-07
		ZxGcsgCcWorks update = new ZxGcsgCcWorks();
		update.setCtContractId(ctContractId);
		sqlFlag = zxGcsgCcWorksMapper.updateZxGcsgCcWorksByLeaf(update);
		// 导入完成后更新主表合同金额
		ZxGcsgCcWorks contractCost = new ZxGcsgCcWorks();
		contractCost.setCtContractId(ctContractId);
		ZxGcsgCcWorks dbContractCost = zxGcsgCcWorksMapper.getZxGcsgCcWorksContractCost(contractCost);
		if (dbZxGcsgCtContract != null) {
			dbZxGcsgCtContract.setContractCost(dbContractCost.getContractAmt());
			dbZxGcsgCtContract.setContractCostNoTax(dbContractCost.getContractAmtNoTax());
			BigDecimal contractCostTax = CalcUtils.calcSubtract(dbContractCost.getContractAmt(),
					dbContractCost.getContractAmtNoTax());
			dbZxGcsgCtContract.setContractCostTax(contractCostTax);
			dbZxGcsgCtContract.setAlterContractSum(dbContractCost.getContractAmt());
			dbZxGcsgCtContract.setAlterContractSumNoTax(dbContractCost.getContractAmtNoTax());
			dbZxGcsgCtContract.setAlterContractSumTax(contractCostTax);
			sqlFlag = zxGcsgCtContractMapper.updateByPrimaryKey(dbZxGcsgCtContract);
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
				ZxGcsgCcWorks workModel = (ZxGcsgCcWorks) it.next();
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
	public ResponseEntity getZxGcsgCcWorksListAmount(ZxGcsgCcWorks zxGcsgCcWorks) {
		if (zxGcsgCcWorks == null) {
			zxGcsgCcWorks = new ZxGcsgCcWorks();
		}
		// 首次进入页面传ctContrApplyId获取所有清单
		// 页面中传parentID获取父节点以及父节点下面的所有清单
		List<ZxGcsgCcWorks> zxGcsgCcWorksList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCcWorks.getCtContractId()) && StrUtil.isNotEmpty(zxGcsgCcWorks.getTreeNode())) {
			zxGcsgCcWorksList = zxGcsgCcWorksMapper.getZxGcsgCcWorksListByParentID(zxGcsgCcWorks);
			if (zxGcsgCcWorksList.size() > 0) {
				zxGcsgCcWorksList.stream().forEach(dbWorks -> {
					// 叶子节点需要用含税单价和数量进行计算
					// 含税金额=含税单价*数量
					// 不含税金额=含税金额/(1+税率)
					if (dbWorks.getIsLeaf() == 1) {
						BigDecimal contractAmt = CalcUtils.calcMultiply(dbWorks.getContractPrice(),
								dbWorks.getContractQty());
						BigDecimal amt = CalcUtils.calcMultiply(dbWorks.getPrice(), dbWorks.getQuantity());
						BigDecimal contractAmtNoTax = BigDecimal.ZERO;
						BigDecimal amtNoTax = BigDecimal.ZERO;
						if (dbWorks.getTaxRate() != null && NumberUtil.isNumber(dbWorks.getTaxRate())) {
							contractAmtNoTax = CalcUtils.calcDivide(contractAmt,
									CalcUtils.calcAdd(BigDecimal.ONE, CalcUtils
											.calcDivide(new BigDecimal(dbWorks.getTaxRate()), new BigDecimal(100), 3)),
									2);
							amtNoTax = CalcUtils.calcDivide(amt,
									CalcUtils.calcAdd(BigDecimal.ONE, CalcUtils
											.calcDivide(new BigDecimal(dbWorks.getTaxRate()), new BigDecimal(100), 3)),
									2);
						} else {
							/*
							 * contractAmtNoTax = CalcUtils.calcMultiply(dbWorks.getContractPriceNoTax(),
							 * dbWorks.getContractQty()); amtNoTax =
							 * CalcUtils.calcMultiply(dbWorks.getPriceNoTax(), dbWorks.getQuantity());
							 */
						}
						dbWorks.setContractAmt(contractAmt);
						dbWorks.setContractAmtNoTax(contractAmtNoTax);
						dbWorks.setAmt(amt);
						dbWorks.setAmtNoTax(amtNoTax);
						// 不是变更类型的数据置为空
						if (dbWorks.getExsitStatus() != 2) {
							dbWorks.setPrice(null);
							dbWorks.setPriceNoTax(null);
							dbWorks.setQuantity(null);
							dbWorks.setAmt(null);
							dbWorks.setAmtNoTax(null);
						}
						if (dbWorks.getContractPrice() == null && dbWorks.getContractQty() == null) {
							dbWorks.setContractAmt(null);
							dbWorks.setContractAmtNoTax(null);
						}
					} else {
						// 非叶子节点则需要直接获取合计金额
						ZxGcsgCcWorks search = new ZxGcsgCcWorks();
						search.setTreeNode(dbWorks.getTreeNode());
						ZxGcsgCcWorks dbCCWorks = zxGcsgCcWorksMapper.getZxGcsgCcWorksContractAmt(search);
						if (dbCCWorks != null) {
//							BigDecimal contractAmtNoTax = CalcUtils.calcDivide(dbCCWorks.getContractAmt(),
//									CalcUtils.calcAdd(BigDecimal.ONE, CalcUtils
//											.calcDivide(new BigDecimal(dbWorks.getTaxRate()), new BigDecimal(100))),
//									2);
//							BigDecimal amtNoTax = CalcUtils.calcDivide(dbCCWorks.getAmt(), CalcUtils.calcAdd(
//									BigDecimal.ONE,
//									CalcUtils.calcDivide(new BigDecimal(dbWorks.getTaxRate()), new BigDecimal(100))),
//									2);
							dbWorks.setContractAmt(dbCCWorks.getContractAmt());
							dbWorks.setContractAmtNoTax(dbCCWorks.getContractAmtNoTax());
							dbWorks.setAmt(dbCCWorks.getAmt());
							dbWorks.setAmtNoTax(dbCCWorks.getAmtNoTax());
							// 非叶子节点如果统计变更后金额为0,则认为其下级所有节点皆未发生变更
							if (dbCCWorks.getAmt().compareTo(BigDecimal.ZERO) == 0) {
								dbWorks.setPrice(null);
								dbWorks.setPriceNoTax(null);
								dbWorks.setQuantity(null);
								dbWorks.setAmt(null);
								dbWorks.setAmtNoTax(null);
							}
						}
					}
				});
			}
		}
		// 返回树形结构
		JSONArray arr = getTree(JSONUtil.parseArray(zxGcsgCcWorksList));
		return repEntity.okList(arr, arr.size());
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
				newJSONObj.set("ccWorksId", json.get("ccWorksId"));
				newJSONObj.set("parentID", json.get("parentID"));
				newJSONObj.set("treeNode", json.get("treeNode"));
				newJSONObj.set("orgID", json.get("orgID"));
				newJSONObj.set("ctContractId", json.get("ctContractId"));
				newJSONObj.set("workType", json.get("workType"));
				newJSONObj.set("workNo", json.get("workNo"));
				newJSONObj.set("workName", json.get("workName"));
				newJSONObj.set("unit", json.get("unit"));
				newJSONObj.set("contractPrice", json.get("contractPrice"));
				newJSONObj.set("contractPriceNoTax", json.get("contractPriceNoTax"));
				newJSONObj.set("contractQty", json.get("contractQty"));
				newJSONObj.set("contractAmt", json.get("contractAmt"));
				newJSONObj.set("contractAmtNoTax", json.get("contractAmtNoTax"));
				newJSONObj.set("price", json.get("price"));
				newJSONObj.set("priceNoTax", json.get("priceNoTax"));
				newJSONObj.set("quantity", json.get("quantity"));
				newJSONObj.set("amt", json.get("amt"));
				newJSONObj.set("amtNoTax", json.get("amtNoTax"));
				newJSONObj.set("isLeaf", json.get("isLeaf"));
				newJSONObj.set("inputWorkType", json.get("inputWorkType"));
				newJSONObj.set("taxRate", json.get("taxRate"));
				newJSONObj.set("ruleID", json.get("ruleID"));
				newJSONObj.set("ruleName", json.get("ruleName"));
				newJSONObj.set("isDeduct", json.get("isDeduct"));
				newJSONObj.set("remarks", json.get("remarks"));
				newJSONObj.set("processNum", json.get("processNum"));
				newJSONObj.set("yzWorkNo", json.get("yzWorkNo"));
				newJSONObj.set("yzWorkName", json.get("yzWorkName"));
				hash.set(json.getStr("ccWorksId"), newJSONObj);
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity autoHookZxGcsgCcWorksProcess(ZxGcsgCcWorks zxGcsgCcWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 将该合同管理的所有清单,挂接选中类型的工序库(已挂接的做不重复挂接-老系统业务)
		// 获取需要挂接的清单集合(该合同管理所有清单)
		List<ZxGcsgCcWorks> zxGcsgCcWorksList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(zxGcsgCcWorks.getCtContractId())) {
			// 清空该所有挂接关系-新系统业务
			ZxGcsgCtContrProcessGuajie guajie = new ZxGcsgCtContrProcessGuajie();
			guajie.setCtContractId(zxGcsgCcWorks.getCtContractId());
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(guajie);
			// 获取该合同管理所有清单叶子节点
			zxGcsgCcWorks.setIsLeaf(1);
			zxGcsgCcWorksList = zxGcsgCcWorksMapper.getZxGcsgCcWorksListByParentID(zxGcsgCcWorks);
		}
		// 根据选中类型获取对应的工序库
		if (StrUtil.equals("contr", zxGcsgCcWorks.getInputWorkType())) {
			// 获取分包标准工序库
			List<ZxCtContrProcess> fbProcessList = zxCtContrProcessMapper
					.selectByZxCtContrProcessList(new ZxCtContrProcess());
			List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
			if (zxGcsgCcWorksList.size() > 0 && fbProcessList.size() > 0) {
				for (ZxGcsgCcWorks dbZxGcsgCcWorks : zxGcsgCcWorksList) {
					for (ZxCtContrProcess dbProcess : fbProcessList) {
						if (StrUtil.equals(dbZxGcsgCcWorks.getWorkNo(), dbProcess.getProcessNo())) {
							ZxGcsgCtContrProcessGuajie insertData = new ZxGcsgCtContrProcessGuajie();
							insertData.setCtContrProcessGuajieId(UuidUtil.generate());
							insertData.setCcWorksId(dbZxGcsgCcWorks.getCcWorksId());
							insertData.setApplyAlterWorksId("");
							insertData.setCtContrApplyId("");
							insertData.setCtContractId(dbZxGcsgCcWorks.getCtContractId());
							insertData.setProcessID(dbProcess.getId());
							insertData.setProcessNo(dbProcess.getProcessNo());
							insertData.setProcessName(dbProcess.getProcessName());
							insertData.setProcessUnit(dbProcess.getProcessUnit());
							insertData.setBaseType(zxGcsgCcWorks.getInputWorkType());
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
										insertData2.setCcWorksId(dbZxGcsgCcWorks.getCcWorksId());
										insertData2.setApplyAlterWorksId("");
										insertData2.setCtContrApplyId("");
										insertData2.setCtContractId(dbZxGcsgCcWorks.getCtContractId());
										insertData2.setProcessID(process.getId());
										insertData2.setProcessNo(process.getProcessNo());
										insertData2.setProcessName(process.getProcessName());
										insertData2.setProcessUnit(process.getProcessUnit());
										insertData2.setBaseType(zxGcsgCcWorks.getInputWorkType());
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
											insertData3.setCcWorksId(dbZxGcsgCcWorks.getCcWorksId());
											insertData3.setApplyAlterWorksId("");
											insertData3.setCtContrApplyId("");
											insertData3.setCtContractId(dbZxGcsgCcWorks.getCtContractId());
											insertData3.setProcessID(dbParent.getId());
											insertData3.setProcessNo(dbParent.getProcessNo());
											insertData3.setProcessName(dbParent.getProcessName());
											insertData3.setProcessUnit(dbParent.getProcessUnit());
											insertData3.setBaseType(zxGcsgCcWorks.getInputWorkType());
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
				flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
			}
		} else {
			// 获取其它类型的工序库
			ZxCtSZProcess zxCtSZProcess = new ZxCtSZProcess();
			zxCtSZProcess.setBaseType(zxGcsgCcWorks.getInputWorkType());
			List<ZxCtSZProcess> qtProcessList = zxCtSZProcessMapper.selectByZxCtSZProcessList(zxCtSZProcess);
			List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
			if (zxGcsgCcWorksList.size() > 0 && qtProcessList.size() > 0) {
				for (ZxGcsgCcWorks dbZxGcsgCcWorks : zxGcsgCcWorksList) {
					for (ZxCtSZProcess dbProcess : qtProcessList) {
						if (StrUtil.equals(dbZxGcsgCcWorks.getWorkNo(), dbProcess.getProcessNo())) {
							ZxGcsgCtContrProcessGuajie insertData = new ZxGcsgCtContrProcessGuajie();
							insertData.setCtContrProcessGuajieId(UuidUtil.generate());
							insertData.setCcWorksId(dbZxGcsgCcWorks.getCcWorksId());
							insertData.setApplyAlterWorksId("");
							insertData.setCtContrApplyId("");
							insertData.setCtContractId(dbZxGcsgCcWorks.getCtContractId());
							insertData.setProcessID(dbProcess.getId());
							insertData.setProcessNo(dbProcess.getProcessNo());
							insertData.setProcessName(dbProcess.getProcessName());
							insertData.setProcessUnit(dbProcess.getProcessUnit());
							insertData.setBaseType(zxGcsgCcWorks.getInputWorkType());
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
										insertData2.setCcWorksId(dbZxGcsgCcWorks.getCcWorksId());
										insertData2.setApplyAlterWorksId("");
										insertData2.setCtContrApplyId("");
										insertData2.setCtContractId(dbZxGcsgCcWorks.getCtContractId());
										insertData2.setProcessID(process.getId());
										insertData2.setProcessNo(process.getProcessNo());
										insertData2.setProcessName(process.getProcessName());
										insertData2.setProcessUnit(process.getProcessUnit());
										insertData2.setBaseType(zxGcsgCcWorks.getInputWorkType());
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
											insertData3.setCcWorksId(dbZxGcsgCcWorks.getCcWorksId());
											insertData3.setApplyAlterWorksId("");
											insertData3.setCtContrApplyId("");
											insertData3.setCtContractId(dbZxGcsgCcWorks.getCtContractId());
											insertData3.setProcessID(dbParent.getId());
											insertData3.setProcessNo(dbParent.getProcessNo());
											insertData3.setProcessName(dbParent.getProcessName());
											insertData3.setProcessUnit(dbParent.getProcessUnit());
											insertData3.setBaseType(zxGcsgCcWorks.getInputWorkType());
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
				flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCcWorks);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity manualHookZxGcsgCcWorksProcess(ZxGcsgCcWorks zxGcsgCcWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 选中一条清单进行挂接工序
		// 获取需要挂接的清单
		ZxGcsgCcWorks dbZxGcsgCcWorks = zxGcsgCcWorksMapper.selectByPrimaryKey(zxGcsgCcWorks.getCcWorksId());
		// 获取选中的公路、房建、铁路、轨道、市政标准工序库(这里需要前端传入)
		List<ZxGcsgCtContrProcessGuajie> insertList = Lists.newArrayList();
		if (dbZxGcsgCcWorks != null && zxGcsgCcWorks.getProcessArray() != null
				&& zxGcsgCcWorks.getProcessArray().size() > 0) {
			JSONArray jsonArr = zxGcsgCcWorks.getProcessArray();
			for (int i = 0; i < jsonArr.size(); i++) {
				ZxGcsgCtContrProcessGuajie insertData = new ZxGcsgCtContrProcessGuajie();
				insertData.setCtContrProcessGuajieId(UuidUtil.generate());
				insertData.setCcWorksId(dbZxGcsgCcWorks.getCcWorksId());
				insertData.setApplyAlterWorksId("");
				insertData.setCtContrApplyId("");
				insertData.setCtContractId(dbZxGcsgCcWorks.getCtContractId());
				insertData.setProcessID(jsonArr.getJSONObject(i).getStr("id"));
				insertData.setProcessNo(jsonArr.getJSONObject(i).getStr("processNo"));
				insertData.setProcessName(jsonArr.getJSONObject(i).getStr("processName"));
				insertData.setProcessUnit(jsonArr.getJSONObject(i).getStr("processUnit"));
				insertData.setBaseType(zxGcsgCcWorks.getInputWorkType());
				if (jsonArr.getJSONObject(i).getStr("isLeaf") != null
						&& NumberUtil.isNumber(jsonArr.getJSONObject(i).getStr("isLeaf"))) {
					insertData.setIsLeaf(Integer.parseInt(jsonArr.getJSONObject(i).getStr("isLeaf")));
				}
				insertData.setCreateUserInfo(userKey, realName);
				insertList.add(insertData);
			}
		}
		// 根据合同管理清单id、工序库类型删除关系表
		if (dbZxGcsgCcWorks != null && StrUtil.isNotEmpty(dbZxGcsgCcWorks.getCcWorksId())
				&& StrUtil.isNotEmpty(zxGcsgCcWorks.getInputWorkType())) {
			ZxGcsgCtContrProcessGuajie deleteCondition = new ZxGcsgCtContrProcessGuajie();
			deleteCondition.setCcWorksId(dbZxGcsgCcWorks.getCcWorksId());
			deleteCondition.setBaseType(zxGcsgCcWorks.getInputWorkType());
			zxGcsgCtContrProcessGuajieMapper.deleteZxGcsgCtContrProcessGuajieByCondition(deleteCondition);
		}
		// 然后新增
		if (insertList.size() > 0) {
			flag = zxGcsgCtContrProcessGuajieMapper.batchInsertZxGcsgCtContrProcessGuajie(insertList);
		}
//		if (flag == 0) {
//			return repEntity.errorSave();
//		} else {
		return repEntity.ok("sys.data.sava", zxGcsgCcWorks);
//		}
	}

	@Override
	public ResponseEntity manualHookZxGcsgCcWorksRule(ZxGcsgCcWorks zxGcsgCcWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 获取需要挂接计价规则的清单
		ZxGcsgCcWorks dbZxGcsgCcWorks = zxGcsgCcWorksMapper.selectByPrimaryKey(zxGcsgCcWorks.getCcWorksId());
		// 根据选中的工序库类型获取计价规则(这里需要前端传入)
		String ruleName = "";
		if (StrUtil.equals("contr", zxGcsgCcWorks.getInputWorkType())) {
			// 调取分包计价规则库
			ZxCtValuationRules dbZxCtValuationRules = zxCtValuationRulesMapper
					.selectByPrimaryKey(zxGcsgCcWorks.getRuleID());
			ruleName = dbZxCtValuationRules != null ? dbZxCtValuationRules.getRuleName() : "";
		} else {
			ZxCtValuationSZRules dbZxCtValuationSZRules = zxCtValuationSZRulesMapper
					.selectByPrimaryKey(zxGcsgCcWorks.getRuleID());
			ruleName = dbZxCtValuationSZRules != null ? dbZxCtValuationSZRules.getRuleName() : "";
		}
		if (dbZxGcsgCcWorks != null) {
			dbZxGcsgCcWorks.setRuleID(zxGcsgCcWorks.getRuleID());
			dbZxGcsgCcWorks.setRuleName(ruleName);
			dbZxGcsgCcWorks.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcWorksMapper.updateByPrimaryKey(dbZxGcsgCcWorks);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCcWorks);
		}
	}

	@Override
	public ResponseEntity getZxGcsgCcWorksListProcess(ZxGcsgCcWorks zxGcsgCcWorks) {
		// 获取该合同管理的清单工序挂接台账
		List<Map<String, Object>> mapList = Lists.newArrayList();
		if (zxGcsgCcWorks != null && StrUtil.isNotEmpty(zxGcsgCcWorks.getCtContractId())) {
			mapList = zxGcsgCcWorksMapper.getZxGcsgCcWorksListProcess(zxGcsgCcWorks);
		}
		return repEntity.okList(mapList, mapList.size());
	}

	@Override
	public ResponseEntity getZxGcsgCcWorksSelect(ZxGcsgCcWorks zxGcsgCcWorks) {
		if (zxGcsgCcWorks == null) {
			zxGcsgCcWorks = new ZxGcsgCcWorks();
		}
		// 分页查询
		PageHelper.startPage(zxGcsgCcWorks.getPage(), zxGcsgCcWorks.getLimit());
		// 获取数据
		List<ZxGcsgCcWorks> zxGcsgCcWorksList = zxGcsgCcWorksMapper.selectByZxGcsgCcWorksList(zxGcsgCcWorks);
		if (zxGcsgCcWorksList.size() > 0) {
			zxGcsgCcWorksList.stream().filter(f -> f.getIsLeaf() == 1).forEach(dbWorks -> {
				BigDecimal contractAmt = CalcUtils.calcMultiply(dbWorks.getContractPrice(), dbWorks.getContractQty());
				BigDecimal amt = CalcUtils.calcMultiply(dbWorks.getPrice(), dbWorks.getQuantity());
				BigDecimal contractAmtNoTax = BigDecimal.ZERO;
				BigDecimal amtNoTax = BigDecimal.ZERO;
				if (dbWorks.getTaxRate() != null && NumberUtil.isNumber(dbWorks.getTaxRate())) {
					contractAmtNoTax = CalcUtils.calcDivide(contractAmt,
							CalcUtils.calcAdd(BigDecimal.ONE,
									CalcUtils.calcDivide(new BigDecimal(dbWorks.getTaxRate()), new BigDecimal(100), 3)),
							2);
					amtNoTax = CalcUtils.calcDivide(amt,
							CalcUtils.calcAdd(BigDecimal.ONE,
									CalcUtils.calcDivide(new BigDecimal(dbWorks.getTaxRate()), new BigDecimal(100), 3)),
							2);
				} else {
					contractAmtNoTax = CalcUtils.calcMultiply(dbWorks.getContractPriceNoTax(),
							dbWorks.getContractQty());
					amtNoTax = CalcUtils.calcMultiply(dbWorks.getPriceNoTax(), dbWorks.getQuantity());
				}
				dbWorks.setContractAmt(contractAmt);
				dbWorks.setContractAmtNoTax(contractAmtNoTax);
				dbWorks.setAmt(amt);
				dbWorks.setAmtNoTax(amtNoTax);
			});
		}
		// 得到分页信息
		PageInfo<ZxGcsgCcWorks> p = new PageInfo<>(zxGcsgCcWorksList);
		return repEntity.okList(zxGcsgCcWorksList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCcWorksTree(ZxGcsgCcWorks zxGcsgCcWorks) {
		if (zxGcsgCcWorks == null) {
			zxGcsgCcWorks = new ZxGcsgCcWorks();
		}
		// 获取数据
		List<ZxGcsgCcWorks> zxGcsgCcWorksList = zxGcsgCcWorksMapper.selectByZxGcsgCcWorksList(zxGcsgCcWorks);
		// JSONArray jsonArray = ConvertUtil.listToTree(new
		// JSONArray(zxGcsgCcWorksList),
		// "ccWorksId", "parentID",
		// "workName", "isLeaf");
		return repEntity.okList(zxGcsgCcWorksList, zxGcsgCcWorksList.size());
	}

}
