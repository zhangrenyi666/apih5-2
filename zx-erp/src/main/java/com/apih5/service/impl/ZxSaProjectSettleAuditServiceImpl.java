package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxGcsgCtContractMapper;
import com.apih5.mybatis.dao.ZxSaProjectPaySettleMapper;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditMapper;
import com.apih5.mybatis.dao.ZxSaProjectWorkSettleItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectWorkSettleMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxGcsgCcWorks;
import com.apih5.mybatis.pojo.ZxGcsgCtContract;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettle;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAudit;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAuditItem;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettle;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettleItem;
import com.apih5.service.ZxGcsgCcWorksService;
import com.apih5.service.ZxGcsgCtContractService;
import com.apih5.service.ZxSaProjectPaySettleService;
import com.apih5.service.ZxSaProjectSettleAuditItemService;
import com.apih5.service.ZxSaProjectSettleAuditService;
import com.apih5.service.ZxSaProjectWorkSettleService;
import com.apih5.utils.DigitalConversionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxSaProjectSettleAuditService")
public class ZxSaProjectSettleAuditServiceImpl implements ZxSaProjectSettleAuditService {
	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZxSaProjectSettleAuditMapper zxSaProjectSettleAuditMapper;

	@Autowired(required = true)
	private ZxSaProjectSettleAuditItemMapper zxSaProjectSettleAuditItemMapper;
	
	@Autowired(required = true)
	private ZxSaProjectSettleAuditItemService zxSaProjectSettleAuditItemService;

	@Autowired(required = true)
	private ZxErpFileServiceImpl zxErpFileServiceImpl;

	@Autowired(required = true)
	private ZxSaProjectSettleAuditItemServiceImpl zxSaProjectSettleAuditItemServiceImpl;

	@Autowired(required = true)
	private ZxGcsgCcWorksService zxGcsgCcWorksService;

	@Autowired(required = true)
	private ZxSaProjectWorkSettleServiceImpl zxSaProjectWorkSettleServiceImpl;

	@Autowired(required = true)
	private ZxSaProjectWorkSettleMapper zxSaProjectWorkSettleMapper;

	@Autowired(required = true)
	private ZxGcsgCtContractService zxGcsgCtContractService;

	@Autowired(required = true)
	private ZxSaProjectWorkSettleItemMapper zxSaProjectWorkSettleItemMapper;

	@Autowired(required = true)
	private ZxSaProjectPaySettleService zxSaProjectPaySettleService;

	@Autowired(required = true)
	private ZxSaProjectWorkSettleService zxSaProjectWorkSettleService;

	@Autowired(required = true)
	private ZxSaProjectPaySettleMapper zxSaProjectPaySettleMapper;
	
	@Autowired(required = true)
	private ZxGcsgCtContractMapper zxGcsgCtContractMapper;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxSaProjectSettleAuditListByCondition(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		if (zxSaProjectSettleAudit == null) {
			zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
		
		// 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
        	zxSaProjectSettleAudit.setCompanyId("");
        	zxSaProjectSettleAudit.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSaProjectSettleAudit.setCompanyId(zxSaProjectSettleAudit.getOrgID());
        	zxSaProjectSettleAudit.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSaProjectSettleAudit.setOrgID(zxSaProjectSettleAudit.getOrgID());
        }
		
		// 分页查询
		PageHelper.startPage(zxSaProjectSettleAudit.getPage(), zxSaProjectSettleAudit.getLimit());
		// 获取数据
		List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper.selectByZxSaProjectSettleAuditList(zxSaProjectSettleAudit);
		// 得到分页信息
		PageInfo<ZxSaProjectSettleAudit> p = new PageInfo<>(zxSaProjectSettleAuditList);

		for (ZxSaProjectSettleAudit projectSettleAudit : zxSaProjectSettleAuditList) {
			// 附件
			List<ZxErpFile> zxErpFileList = new ArrayList<>();
			List<ZxErpFile> textAttachmentList = new ArrayList<>();
			
			ZxErpFile zxErpFile = new ZxErpFile();
			zxErpFile.setOtherId(projectSettleAudit.getId());
			List<ZxErpFile> allFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
			
			if (allFileList != null && allFileList.size() > 0) {
				for (ZxErpFile file : allFileList) {
					if (StrUtil.equals("0", file.getOtherType())) {
						zxErpFileList.add(file);
					} else if (StrUtil.equals("1", file.getOtherType())) {
						textAttachmentList.add(file);
					}
				}
			}
			projectSettleAudit.setZxErpFileList(zxErpFileList);
			projectSettleAudit.setTextAttachmentList(textAttachmentList);
			
			if (StrUtil.isEmpty(projectSettleAudit.getApih5FlowStatus())) {
				projectSettleAudit.setApih5FlowStatus("-1");
			}

			// 明细
			ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
			zxSaProjectSettleAuditItem.setProjectSettleAuditId(projectSettleAudit.getId());
			List<ZxSaProjectSettleAuditItem> projectSettleAuditItemList = (List<ZxSaProjectSettleAuditItem>) zxSaProjectSettleAuditItemServiceImpl
					.getZxSaProjectSettleAuditItemListByCondition(zxSaProjectSettleAuditItem).getData();
			projectSettleAudit.setProjectSettleAuditItemList(projectSettleAuditItemList);
		}

		return repEntity.okList(zxSaProjectSettleAuditList, p.getTotal());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxSaProjectSettleAuditDetail(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		if (zxSaProjectSettleAudit == null) {
			zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		}
		
		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = null;
		
		if (StrUtil.isEmpty(zxSaProjectSettleAudit.getId())) {
			// 获取数据
			ZxSaProjectSettleAudit settleAudit = new ZxSaProjectSettleAudit();
			settleAudit.setWorkId(zxSaProjectSettleAudit.getWorkId());
			List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper.selectByZxSaProjectSettleAuditList(settleAudit);
			if (zxSaProjectSettleAuditList != null && zxSaProjectSettleAuditList.size() > 0) {
				dbZxSaProjectSettleAudit = zxSaProjectSettleAuditList.get(0);
			}
		} else {
			// 获取数据
			dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectSettleAudit.getId());
		}
		
		// 数据存在
		if (dbZxSaProjectSettleAudit != null) {
			// 附件
			List<ZxErpFile> zxErpFileList = new ArrayList<>();
			List<ZxErpFile> textAttachmentList = new ArrayList<>();
			
			ZxErpFile zxErpFile = new ZxErpFile();
			zxErpFile.setOtherId(dbZxSaProjectSettleAudit.getId());
			List<ZxErpFile> allFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
			
			if (allFileList != null && allFileList.size() > 0) {
				for (ZxErpFile file : allFileList) {
					if (StrUtil.equals("0", file.getOtherType())) {
						zxErpFileList.add(file);
					} else if (StrUtil.equals("1", file.getOtherType())) {
						textAttachmentList.add(file);
					}
				}
			}
			dbZxSaProjectSettleAudit.setZxErpFileList(zxErpFileList);
			dbZxSaProjectSettleAudit.setTextAttachmentList(textAttachmentList);
			
			// 获取数据
			ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
			zxSaProjectSettleAuditItem.setProjectSettleAuditId(dbZxSaProjectSettleAudit.getId());
	        List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(zxSaProjectSettleAuditItem);
	        dbZxSaProjectSettleAudit.setProjectSettleAuditItemList(zxSaProjectSettleAuditItemList);
			
			// 查询清单结算
			ZxSaProjectWorkSettle selectWorkSettle = new ZxSaProjectWorkSettle();
			selectWorkSettle.setContractID(dbZxSaProjectSettleAudit.getContractID());
			selectWorkSettle.setOrgID(dbZxSaProjectSettleAudit.getOrgID());
			selectWorkSettle.setPeriod(dbZxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectWorkSettle> workSettleList = (List<ZxSaProjectWorkSettle>) zxSaProjectWorkSettleService.getZxSaProjectWorkSettleListByCondition(selectWorkSettle).getData();
			if (workSettleList != null && workSettleList.size() > 0) {
				ZxSaProjectWorkSettle dbZxSaProjectWorkSettle = workSettleList.get(0);

				// 清单含税合同金额(万元)
				dbZxSaProjectSettleAudit.setWorkContractAmt(dbZxSaProjectWorkSettle.getContractAmt());
				// 清单变更后含税合同金额(万元)
				dbZxSaProjectSettleAudit.setWorkChangeAmt(dbZxSaProjectWorkSettle.getChangeAmt());
				// 清单本期清单结算含税金额(元)
				dbZxSaProjectSettleAudit.setWorkThisAmt(dbZxSaProjectWorkSettle.getThisAmt());
				// 清单累计清单结算含税金额(元)
				dbZxSaProjectSettleAudit.setWorkTotalAmt(dbZxSaProjectWorkSettle.getTotalAmt());
				// 清单本期清单结算不含税金额(元)
				dbZxSaProjectSettleAudit.setWorkThisAmtNoTax(dbZxSaProjectWorkSettle.getThisAmtNoTax());
				// 清单本期清单结算税额(元)
				dbZxSaProjectSettleAudit.setWorkThisAmtTax(dbZxSaProjectWorkSettle.getThisAmtTax());
				// 清单主表主键ID
				dbZxSaProjectSettleAudit.setProjectWorkSettleId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
				// 清单结算
				dbZxSaProjectSettleAudit.setWorkSettleItemList(dbZxSaProjectWorkSettle.getZxSaProjectWorkSettleItemList());
			}

			// 查询支付项结算
			ZxSaProjectPaySettle selectPaySettle = new ZxSaProjectPaySettle();
			selectPaySettle.setContractID(dbZxSaProjectSettleAudit.getContractID());
			selectPaySettle.setOrgID(dbZxSaProjectSettleAudit.getOrgID());
			selectPaySettle.setPeriod(dbZxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectPaySettle> paySettleList = (List<ZxSaProjectPaySettle>) zxSaProjectPaySettleService.getZxSaProjectPaySettleListByCondition(selectPaySettle).getData();
			if (paySettleList != null && paySettleList.size() > 0) {
				ZxSaProjectPaySettle dbZxSaProjectPaySettle = paySettleList.get(0);

				// 支付项本期支付项结算含税金额(元)
				dbZxSaProjectSettleAudit.setPayThisAmt(dbZxSaProjectPaySettle.getThisAmt());
				// 本期支付项结算不含税金额(元)
				dbZxSaProjectSettleAudit.setPayThisAmtNoTax(dbZxSaProjectPaySettle.getThisAmtNoTax());
				// 支付项累支付项计支付项结算金额(元)
				dbZxSaProjectSettleAudit.setPayTotalAmt(dbZxSaProjectPaySettle.getTotalAmt());
				// 支付项物资调拨费用本期结算小计(元)
				dbZxSaProjectSettleAudit.setPayMaterialAmt(dbZxSaProjectPaySettle.getMaterialAmt());
				// 支付项机械调拨费用本期结算小计(元)
				dbZxSaProjectSettleAudit.setPayMachineAmt(dbZxSaProjectPaySettle.getMachineAmt());
				// 支付项临时用工费本期结算小计(元)
				dbZxSaProjectSettleAudit.setPayTempAmt(dbZxSaProjectPaySettle.getTempAmt());
				// 支付项奖罚金额本期结算小计(元)
				dbZxSaProjectSettleAudit.setPayFineAmt(dbZxSaProjectPaySettle.getFineAmt());
				// 支付项补偿金额本期结算小计(元)
				dbZxSaProjectSettleAudit.setPayRecoupAmt(dbZxSaProjectPaySettle.getRecoupAmt());
				// 支付项其他款项本期结算小计(元)
				dbZxSaProjectSettleAudit.setPayOtherAmt(dbZxSaProjectPaySettle.getOtherAmt());
				// 支付项主表主键ID
				dbZxSaProjectSettleAudit.setProjectPaySettleId(dbZxSaProjectPaySettle.getProjectPaySettleId());

				dbZxSaProjectSettleAudit.setPaySettleItemList(dbZxSaProjectPaySettle.getProjectPaySettleItemList());
			}

			return repEntity.ok(dbZxSaProjectSettleAudit);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity saveZxSaProjectSettleAudit(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxSaProjectSettleAudit.setId(UuidUtil.generate());
		zxSaProjectSettleAudit.setCreateUserInfo(userKey, realName);
		zxSaProjectSettleAudit.setApih5FlowStatus("-1");
		zxSaProjectSettleAudit.setComID(zxSaProjectSettleAudit.getCompanyId());
		zxSaProjectSettleAudit.setComName(zxSaProjectSettleAudit.getCompanyName());

		if (zxSaProjectSettleAudit.getPeriodTimeWasted() != null) {
			zxSaProjectSettleAudit.setPeriod(DateUtil.format(DateUtil.date(zxSaProjectSettleAudit.getPeriodTimeWasted()), "yyyyMM"));
		}
		
		// check（不能添加小于等于本合同已填期次数据）（本期次以前是否有审核未通过数据）	
		ZxSaProjectSettleAudit checkProjectSettleAudit = new ZxSaProjectSettleAudit();
		checkProjectSettleAudit.setOrgID(zxSaProjectSettleAudit.getOrgID());
		checkProjectSettleAudit.setContractID(zxSaProjectSettleAudit.getContractID());
		List<ZxSaProjectSettleAudit> settleAuditList = zxSaProjectSettleAuditMapper.getZxSaProjectSettleAuditTotalList(checkProjectSettleAudit);
		// 验证
		if (settleAuditList != null && settleAuditList.size() > 0) {
			int period = Integer.valueOf(zxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectSettleAudit> greaterThan  = settleAuditList.stream().filter(settleAudit -> Integer.valueOf(settleAudit.getPeriod()) >= period).collect(Collectors.toList());
			if (greaterThan != null && greaterThan.size() > 0) {
				return repEntity.layerMessage("no", "该合同已存在大于等于本期的结算数据，请选择其他期次！");
			}
			List<ZxSaProjectSettleAudit> lessThan  = settleAuditList.stream().filter(settleAudit -> Integer.valueOf(settleAudit.getPeriod()) < period && !StrUtil.equals(settleAudit.getApih5FlowStatus(), "2")).collect(Collectors.toList());
			if (lessThan != null && lessThan.size() > 0) {
				return repEntity.layerMessage("no", "该合同往期数据存在审核未通过数据，请审核通过后再添加其他期次数据！");
			}
			zxSaProjectSettleAudit.setIsFirst("0"); // 是否首次结算（0：否 1：是）
		} else {
			zxSaProjectSettleAudit.setIsFirst("1"); // 是否首次结算（0：否 1：是）
		}

		// 获取明细中合计含税结算金额（小写）、应付工程款（小写）
		ZxSaProjectSettleAuditItem selectSettleAuditItem = new ZxSaProjectSettleAuditItem();
		selectSettleAuditItem.setContractID(zxSaProjectSettleAudit.getContractID());
		selectSettleAuditItem.setPeriod(zxSaProjectSettleAudit.getPeriod());
		List<ZxSaProjectSettleAuditItem> projectSettleAuditItemList = (List<ZxSaProjectSettleAuditItem>) zxSaProjectSettleAuditItemService.getZxSaProjectSettleAuditItemByContractId(selectSettleAuditItem).getData();
		
		// 新增清单信息（查询对外经营合同管理中工程施工合同下清单信息）
		ZxGcsgCcWorks zxGcsgCcWorks = new ZxGcsgCcWorks();
		zxGcsgCcWorks.setOrgID(zxSaProjectSettleAudit.getOrgID());
		zxGcsgCcWorks.setCtContractId(zxSaProjectSettleAudit.getContractID());
		zxGcsgCcWorks.setIsLeaf(1);
		List<ZxGcsgCcWorks> zxGcsgCcWorksList = (List<ZxGcsgCcWorks>) zxGcsgCcWorksService.getZxGcsgCcWorksListByCondition(zxGcsgCcWorks).getData();
		if (zxGcsgCcWorksList != null && zxGcsgCcWorksList.size() > 0) {
			ZxSaProjectWorkSettle zxSaProjectWorkSettle = new ZxSaProjectWorkSettle();
			zxSaProjectWorkSettle.setOrgID(zxSaProjectSettleAudit.getOrgID());
			zxSaProjectWorkSettle.setOrgName(zxSaProjectSettleAudit.getOrgName());
			zxSaProjectWorkSettle.setBillID(zxSaProjectSettleAudit.getId());
			zxSaProjectWorkSettle.setContractID(zxSaProjectSettleAudit.getContractID());
			zxSaProjectWorkSettle.setSignedNos(zxSaProjectSettleAudit.getSignedNo());
			zxSaProjectWorkSettle.setEditTime(new Date());
			zxSaProjectWorkSettle.setPeriod(zxSaProjectSettleAudit.getPeriod());
			zxSaProjectWorkSettle.setComID(zxSaProjectSettleAudit.getComID());
			zxSaProjectWorkSettle.setComName(zxSaProjectSettleAudit.getComName());
			zxSaProjectWorkSettle.setComOrders(zxSaProjectSettleAudit.getComOrders());
			// 查询原主合同信息（对外经营合同管理中工程施工合同）
			ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
			zxGcsgCtContract.setCtContractId(zxSaProjectSettleAudit.getContractID());
			ZxGcsgCtContract dbZxGcsgCtContract = (ZxGcsgCtContract) zxGcsgCtContractService.getZxGcsgCtContractDetail(zxGcsgCtContract).getData();
			if (dbZxGcsgCtContract != null) {
				// 含税合同金额(万元)
				zxSaProjectWorkSettle.setContractAmt(dbZxGcsgCtContract.getContractCost());
				// 变更后含税合同金额(万元)
				zxSaProjectWorkSettle.setChangeAmt(dbZxGcsgCtContract.getAlterContractSum());
			}

			// 本期清单结算含税金额(元)
			BigDecimal thisAmt = new BigDecimal(0);
			// 本期清单结算不含税金额(元)
			BigDecimal thisAmtNoTax = new BigDecimal(0);
			// 本期清单结算税额(元)
			BigDecimal thisAmtTax = new BigDecimal(0);
			// 累计清单结算含税金额(元)
			BigDecimal totalAmt = new BigDecimal(0);
			// 明细列表
			List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList = new ArrayList<>();

			for (ZxGcsgCcWorks works : zxGcsgCcWorksList) {
				ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
				BeanUtil.copyProperties(works, zxSaProjectWorkSettleItem, true);
				zxSaProjectWorkSettleItem.setWorkID(works.getCcWorksId());
				zxSaProjectWorkSettleItem.setContractID(zxSaProjectSettleAudit.getContractID());
				// 签认单编号
				zxSaProjectWorkSettleItem.setSignedNo(zxSaProjectSettleAudit.getSignedNo());
				// 含税单价(元)
				zxSaProjectWorkSettleItem.setPrice(works.getContractPrice());
				// 含税合同金额(元)
				zxSaProjectWorkSettleItem.setContractAmt(CalcUtils.calcMultiply(zxSaProjectWorkSettleItem.getContractQty(), zxSaProjectWorkSettleItem.getPrice()));
				// 变更后数量
				zxSaProjectWorkSettleItem.setChangeQty(works.getQuantity());
				// 变更后含税单价
				zxSaProjectWorkSettleItem.setChangePrice(works.getPrice());
				// 变更后含税金额
				zxSaProjectWorkSettleItem.setChangeAmt(CalcUtils.calcMultiply(works.getQuantity(), works.getPrice()));
				// 累计结算合同数量
				ZxSaProjectWorkSettleItem selectItem = new ZxSaProjectWorkSettleItem();
				selectItem.setContractID(zxSaProjectSettleAudit.getContractID());
				selectItem.setPeriod(zxSaProjectSettleAudit.getPeriod());
				selectItem.setTreeNode(works.getTreeNode());
				ZxSaProjectWorkSettleItem dbItem = zxSaProjectWorkSettleItemMapper.getCumulativeInfo(selectItem);
				if (dbItem != null) {
					// 累计结算合同数量
					zxSaProjectWorkSettleItem.setAllQty(dbItem.getAllQty());
					// 累计结算变更数量
					zxSaProjectWorkSettleItem.setAllChangeQty(dbItem.getAllChangeQty());
					// 累计结算数量小计
					zxSaProjectWorkSettleItem.setAllTotalQty(dbItem.getAllTotalQty());
					// 累计结算含税金额(元)
					zxSaProjectWorkSettleItem.setAllTotalAmt(dbItem.getAllTotalAmt());
				} else {
					// 累计结算合同数量
					zxSaProjectWorkSettleItem.setAllQty(new BigDecimal(0));
					// 累计结算变更数量
					zxSaProjectWorkSettleItem.setAllChangeQty(new BigDecimal(0));
					// 累计结算数量小计
					zxSaProjectWorkSettleItem.setAllTotalQty(new BigDecimal(0));
					// 累计结算含税金额(元)
					zxSaProjectWorkSettleItem.setAllTotalAmt(new BigDecimal(0));
				}
				// 最后编辑时间
				zxSaProjectWorkSettleItem.setEditTime(new Date());
				// 结算期次
				zxSaProjectWorkSettleItem.setPeriod(zxSaProjectSettleAudit.getPeriod());
				// 所属公司ID
				zxSaProjectWorkSettleItem.setComID(zxSaProjectSettleAudit.getComID());
				// 所属公司
				zxSaProjectWorkSettleItem.setComName(zxSaProjectSettleAudit.getComName());
				// 所属公司排序
				zxSaProjectWorkSettleItem.setComOrders(zxSaProjectSettleAudit.getComOrders());
				// 本期含税金额(元)
//				BigDecimal amt = CalcUtils.calcMultiply(zxSaProjectWorkSettleItem.getContractQty(), zxSaProjectWorkSettleItem.getContractAmt());
				BigDecimal amt = CalcUtils.calcMultiply(zxSaProjectWorkSettleItem.getThisQty(), zxSaProjectWorkSettleItem.getContractAmt());
				thisAmt = CalcUtils.calcAdd(thisAmt, amt);
				// 本期不含税金额(元)
				BigDecimal amtNoTax = CalcUtils.calcDivide(amt, CalcUtils.calcAdd(new BigDecimal(1), zxSaProjectWorkSettleItem.getTaxRate()), 6);
				thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, amtNoTax);
				zxSaProjectWorkSettleItemList.add(zxSaProjectWorkSettleItem);
			}
			thisAmtTax = CalcUtils.calcSubtract(thisAmt, thisAmtNoTax);
			// 查询该合同往期清单含税金额
			ZxSaProjectWorkSettle selectTotalAmt = new ZxSaProjectWorkSettle();
			selectTotalAmt.setContractID(zxSaProjectSettleAudit.getContractID());
			selectTotalAmt.setPeriod(zxSaProjectSettleAudit.getPeriod());
			ZxSaProjectWorkSettle dbZxSaProjectWorkSettle = zxSaProjectWorkSettleMapper.selectPastContractListTotalAmt(selectTotalAmt);
			if (dbZxSaProjectWorkSettle != null) {
				totalAmt = CalcUtils.calcAdd(thisAmt, dbZxSaProjectWorkSettle.getTotalAmt());
			}

			zxSaProjectWorkSettle.setThisAmt(thisAmt);
			zxSaProjectWorkSettle.setThisAmtNoTax(thisAmtNoTax);
			zxSaProjectWorkSettle.setThisAmtTax(thisAmtTax);
			zxSaProjectWorkSettle.setTotalAmt(totalAmt);
			zxSaProjectWorkSettle.setZxSaProjectWorkSettleItemList(zxSaProjectWorkSettleItemList);

			// 处理统计项金额
			if (projectSettleAuditItemList != null && projectSettleAuditItemList.size() > 0) {
				// 所有扣除保证金和
				BigDecimal totalItemAmt = new BigDecimal(0);
				BigDecimal totalTotalItemAmt = new BigDecimal(0);
				// 处理扣除保证金
				for (ZxSaProjectSettleAuditItem settleAuditItem : projectSettleAuditItemList) {
					// 扣除保证金类型计算总和及各项本期金额、累计金额
					if(StrUtil.equals("100400", settleAuditItem.getStatisticsType())) {
						BigDecimal thisItemAmt = CalcUtils.calcMultiply(zxSaProjectWorkSettle.getThisAmt(), CalcUtils.calcDivide(settleAuditItem.getRate(), new BigDecimal(100)));
						totalItemAmt = CalcUtils.calcAdd(totalItemAmt, thisItemAmt);
						settleAuditItem.setThisAmt(thisItemAmt + "");
						BigDecimal allItemAmt = CalcUtils.calcAdd(thisItemAmt, settleAuditItem.getUpAmt());
						totalTotalItemAmt = CalcUtils.calcAdd(totalTotalItemAmt, allItemAmt);
						settleAuditItem.setTotalAmt(CalcUtils.calcAdd(totalItemAmt, settleAuditItem.getUpAmt()) + "");
					}
				}
				
				// 处理各项金额及累计金额
				for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : projectSettleAuditItemList) {
					// 上期末累计金额
					BigDecimal upAmt = zxSaProjectSettleAuditItem.getUpAmt();
					if (StrUtil.equals("100100", zxSaProjectSettleAuditItem.getStatisticsNo())) { 
						// 合计含税结算金额（小写）、累计含税结算金额（小写）
						zxSaProjectSettleAuditItem.setThisAmt(zxSaProjectWorkSettle.getThisAmt() + "");
						zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmt(), upAmt) + "");
						zxSaProjectSettleAudit.setThisAmt(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
						zxSaProjectSettleAudit.setTotalAmt(new BigDecimal(zxSaProjectSettleAuditItem.getTotalAmt()));
					} else if (StrUtil.equals("100110", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// 合计不含税结算金额（小写）、累计不含税结算金额（小写）
						zxSaProjectSettleAuditItem.setThisAmt(zxSaProjectWorkSettle.getThisAmtNoTax() + "");
						zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmtNoTax(), upAmt) + "");
						zxSaProjectSettleAudit.setThisAmtNoTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
					} else if (StrUtil.equals("100120", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// 合计结算税额（小写）、累计结算税额（小写）
						zxSaProjectSettleAuditItem.setThisAmt(zxSaProjectWorkSettle.getThisAmtTax() + "");
						zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmtTax(), upAmt) + "");
						zxSaProjectSettleAudit.setThisAmtTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
					} else if (StrUtil.equals("100200", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// 合计含税结算金额（大写）、累计含税结算金额（大写）
						zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaProjectWorkSettle.getThisAmt()));
						zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmt(), upAmt)));
					} else if (StrUtil.equals("100210", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// 合计不含税结算金额（大写）、累计不含税结算金额（大写）
						zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaProjectWorkSettle.getThisAmtNoTax()));
						zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmtNoTax(), upAmt)));
					} else if (StrUtil.equals("100220", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// 合计结算税额（大写）、累计结算税额（大写）
						zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaProjectWorkSettle.getThisAmtTax()));
						zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmtTax(), upAmt)));
					} else if (StrUtil.equals("100300", zxSaProjectSettleAuditItem.getStatisticsType())) {
						// 其中扣除保证金合计
						zxSaProjectSettleAuditItem.setThisAmt(totalItemAmt + "");
						zxSaProjectSettleAuditItem.setTotalAmt(totalTotalItemAmt + "");
					} else if (StrUtil.equals("100700", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// 应付工程款（小写）
						zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcSubtract(zxSaProjectWorkSettle.getThisAmt(), totalItemAmt) + "");
						zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()), zxSaProjectSettleAuditItem.getUpAmt()) + "");
						zxSaProjectSettleAudit.setThisPayAmt(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
						zxSaProjectSettleAudit.setTotalPayAmt(new BigDecimal(zxSaProjectSettleAuditItem.getTotalAmt()));
					} else if(StrUtil.equals("100800", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// 应付工程款（大写）
						zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcMultiply(zxSaProjectWorkSettle.getThisAmt(), totalItemAmt)));
						zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcSubtract(zxSaProjectWorkSettle.getThisAmt(), totalItemAmt), zxSaProjectSettleAuditItem.getUpAmt())));
					}
				}
			}
			
			zxSaProjectWorkSettleServiceImpl.saveZxSaProjectWorkSettle(zxSaProjectWorkSettle);
		}

		int flag = zxSaProjectSettleAuditMapper.insert(zxSaProjectSettleAudit);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// 修改工程施工合同结算情况为（结算开始执行）
			ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
			zxGcsgCtContract.setCtContractId(zxSaProjectSettleAudit.getContractID());
			zxGcsgCtContract.setSettleType("结算开始执行");
			zxGcsgCtContract.setModifyUserInfo(userKey, realName);
			zxGcsgCtContractMapper.updateSettleTypeByPrimaryKey(zxGcsgCtContract);
			
			// 附件
			List<ZxErpFile> zxErpFileList = zxSaProjectSettleAudit.getZxErpFileList();
			if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(zxSaProjectSettleAudit.getId());
					zxErpFile.setOtherType("0");
					zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}

			// 新增支付项信息
			ZxSaProjectPaySettle zxSaProjectPaySettle = new ZxSaProjectPaySettle();
			zxSaProjectPaySettle.setOrgID(zxSaProjectSettleAudit.getOrgID());
			zxSaProjectPaySettle.setContractID(zxSaProjectSettleAudit.getContractID());
			zxSaProjectPaySettle.setPeriod(zxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectPaySettle> zxSaProjectPaySettleList = (List<ZxSaProjectPaySettle>) zxSaProjectPaySettleService.getZxSaProjectPaySettleListByCondition(zxSaProjectPaySettle).getData();
			if (zxSaProjectPaySettleList == null || zxSaProjectPaySettleList.size() == 0) {
				// 查询上期末支付项信息
				ZxSaProjectPaySettle upPaySettle = new ZxSaProjectPaySettle();
				upPaySettle.setOrgID(zxSaProjectSettleAudit.getOrgID());
				upPaySettle.setContractID(zxSaProjectSettleAudit.getContractID());
				upPaySettle.setPeriod(zxSaProjectSettleAudit.getPeriod());
				ZxSaProjectPaySettle upZxSaProjectPaySettle = zxSaProjectPaySettleMapper.getUpZxSaProjectPaySettle(upPaySettle);
				if (upZxSaProjectPaySettle == null) {
					upZxSaProjectPaySettle = new ZxSaProjectPaySettle();
				}

				ZxSaProjectPaySettle addZxSaProjectPaySettle = new ZxSaProjectPaySettle();
				// 项目ID
				addZxSaProjectPaySettle.setOrgID(zxSaProjectSettleAudit.getOrgID());
				// 项目名称
				addZxSaProjectPaySettle.setOrgName(zxSaProjectSettleAudit.getOrgName());
				// 合同ID
				addZxSaProjectPaySettle.setContractID(zxSaProjectSettleAudit.getContractID());
				// 本期支付项结算金额(元)
				addZxSaProjectPaySettle.setThisAmt(new BigDecimal(0));
				// 累计支付项结算金额(元)
				addZxSaProjectPaySettle.setTotalAmt(upZxSaProjectPaySettle.getTotalAmt());
				// 上期末累计支付项结算金额(元)
				addZxSaProjectPaySettle.setUpAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getThisAmt(), upZxSaProjectPaySettle.getUpAmt()));
				// 物资调拨费本期结算小计(元)
				addZxSaProjectPaySettle.setMaterialAmt(new BigDecimal(0));
				// 上期末物资调拨费结算小计(元)
				addZxSaProjectPaySettle.setUpMaterialAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getMaterialAmt(), upZxSaProjectPaySettle.getUpMaterialAmt()));
				// 机械使用费本期结算小计(元)
				addZxSaProjectPaySettle.setMachineAmt(new BigDecimal(0));
				// 上期末机械使用费结算小计(元)
				addZxSaProjectPaySettle.setUpMachineAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getMachineAmt(), upZxSaProjectPaySettle.getUpMachineAmt()));
				// 临时用工费本期结算小计(元)
				addZxSaProjectPaySettle.setTempAmt(new BigDecimal(0));
				// 上期末临时用工费结算小计(元)
				addZxSaProjectPaySettle.setUpTempAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getTempAmt(), upZxSaProjectPaySettle.getUpTempAmt()));
				// 奖罚金额本期结算小计(元)
				addZxSaProjectPaySettle.setFineAmt(new BigDecimal(0));
				// 上期末奖罚金额结算小计(元)
				addZxSaProjectPaySettle.setUpFineAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getFineAmt(), upZxSaProjectPaySettle.getUpFineAmt()));
				// 补偿金额本期结算小计(元)
				addZxSaProjectPaySettle.setRecoupAmt(new BigDecimal(0));
				// 上期末补偿金额结算小计(元)
				addZxSaProjectPaySettle.setUpRecoupAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getRecoupAmt(), upZxSaProjectPaySettle.getUpRecoupAmt()));
				// 其他款项本期结算小计(元)
				addZxSaProjectPaySettle.setOtherAmt(new BigDecimal(0));
				// 上期末其他款项结算小计(元)
				addZxSaProjectPaySettle.setUpOtherAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getOtherAmt(), upZxSaProjectPaySettle.getUpOtherAmt()));
				// 最后编辑时间
				addZxSaProjectPaySettle.setEditTime(new Date());
				// 结算期次
				addZxSaProjectPaySettle.setPeriod(zxSaProjectSettleAudit.getPeriod());
				// 所属公司ID
				addZxSaProjectPaySettle.setComID(zxSaProjectSettleAudit.getComID());
				// 所属公司
				addZxSaProjectPaySettle.setComName(zxSaProjectSettleAudit.getComName());
				// 所属公司排序
				addZxSaProjectPaySettle.setComOrders(zxSaProjectSettleAudit.getComOrders());
				// 本期支付项结算不含税金额(元)
				addZxSaProjectPaySettle.setThisAmtNoTax(new BigDecimal(0));
				// 本期结算税额
				addZxSaProjectPaySettle.setThisAmtTax(new BigDecimal(0));
				addZxSaProjectPaySettle.setBillID(zxSaProjectSettleAudit.getId());
				zxSaProjectPaySettleService.saveZxSaProjectPaySettle(addZxSaProjectPaySettle);
			}

			// 明细
			if (projectSettleAuditItemList != null && projectSettleAuditItemList.size() > 0) {
				for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : projectSettleAuditItemList) {
					zxSaProjectSettleAuditItem.setProjectSettleAuditId(zxSaProjectSettleAudit.getId());
					zxSaProjectSettleAuditItem.setOrgID(zxSaProjectSettleAudit.getOrgID());
					zxSaProjectSettleAuditItem.setComID(zxSaProjectSettleAudit.getComID());
					zxSaProjectSettleAuditItem.setComName(zxSaProjectSettleAudit.getComName());
					zxSaProjectSettleAuditItem.setComOrders(zxSaProjectSettleAudit.getComOrders());
					zxSaProjectSettleAuditItemServiceImpl.saveZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItem);
				}
			}

			return repEntity.ok("sys.data.sava", zxSaProjectSettleAudit);
		}
	}

	@Override
	public ResponseEntity updateZxSaProjectSettleAudit(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectSettleAudit.getId());

		List<ZxSaProjectSettleAuditItem> projectSettleAuditItemList = zxSaProjectSettleAudit.getProjectSettleAuditItemList();

		if (dbZxSaProjectSettleAudit != null && StrUtil.isNotEmpty(dbZxSaProjectSettleAudit.getId())) {
			// 工程结算表ID
			dbZxSaProjectSettleAudit.setBillID(zxSaProjectSettleAudit.getBillID());
			// 结算单编号
			dbZxSaProjectSettleAudit.setBillNo(zxSaProjectSettleAudit.getBillNo());
			// 项目ID
			dbZxSaProjectSettleAudit.setOrgID(zxSaProjectSettleAudit.getOrgID());
			// 项目名称
			dbZxSaProjectSettleAudit.setOrgName(zxSaProjectSettleAudit.getOrgName());
			// 结算期次时间蹉
			dbZxSaProjectSettleAudit.setPeriodTimeWasted(zxSaProjectSettleAudit.getPeriodTimeWasted());
			
			if (dbZxSaProjectSettleAudit.getPeriodTimeWasted() != null) {
				dbZxSaProjectSettleAudit.setPeriod(DateUtil.format(DateUtil.date(dbZxSaProjectSettleAudit.getPeriodTimeWasted()), "yyyyMM"));
			}
			// 合同ID
			dbZxSaProjectSettleAudit.setContractID(zxSaProjectSettleAudit.getContractID());
			// 合同编号
			dbZxSaProjectSettleAudit.setContractNo(zxSaProjectSettleAudit.getContractNo());
			// 合同名称
			dbZxSaProjectSettleAudit.setContractName(zxSaProjectSettleAudit.getContractName());
			// 乙方ID
			dbZxSaProjectSettleAudit.setSecondID(zxSaProjectSettleAudit.getSecondID());
			// 合同乙方
			dbZxSaProjectSettleAudit.setSecondName(zxSaProjectSettleAudit.getSecondName());
			// 结算类型
			dbZxSaProjectSettleAudit.setBillType(zxSaProjectSettleAudit.getBillType());
			// 结算期限开始时间
			dbZxSaProjectSettleAudit.setBeginDate(zxSaProjectSettleAudit.getBeginDate());
			// 结算期限结束时间
			dbZxSaProjectSettleAudit.setEndDate(zxSaProjectSettleAudit.getEndDate());
			// 施工内容
			dbZxSaProjectSettleAudit.setContent(zxSaProjectSettleAudit.getContent());
			// 业务日期
			dbZxSaProjectSettleAudit.setBusinessDate(zxSaProjectSettleAudit.getBusinessDate());
			// 填报日期
			dbZxSaProjectSettleAudit.setReportDate(zxSaProjectSettleAudit.getReportDate());
			// 填报人
			dbZxSaProjectSettleAudit.setReportPerson(zxSaProjectSettleAudit.getReportPerson());
			// 填报人电话
			dbZxSaProjectSettleAudit.setReportPersonTel(zxSaProjectSettleAudit.getReportPersonTel());
			// 计算人
			dbZxSaProjectSettleAudit.setCountPerson(zxSaProjectSettleAudit.getCountPerson());
			// 复核人
			dbZxSaProjectSettleAudit.setReCountPerson(zxSaProjectSettleAudit.getReCountPerson());
			// 发起人
			dbZxSaProjectSettleAudit.setFlowBeginPerson(zxSaProjectSettleAudit.getFlowBeginPerson());
			// 流程ID
			dbZxSaProjectSettleAudit.setWorkItemID(zxSaProjectSettleAudit.getWorkItemID());
			// 流程进度ID
			dbZxSaProjectSettleAudit.setInstProcessID(zxSaProjectSettleAudit.getInstProcessID());
			// 流程开始时间
			dbZxSaProjectSettleAudit.setFlowBeginDate(zxSaProjectSettleAudit.getFlowBeginDate());
			// 流程结束时间
			dbZxSaProjectSettleAudit.setFlowEndDate(zxSaProjectSettleAudit.getFlowEndDate());
			// 审批状态
			dbZxSaProjectSettleAudit.setAuditStatus(zxSaProjectSettleAudit.getAuditStatus());
			// 最后编辑时间
			dbZxSaProjectSettleAudit.setEditTime(zxSaProjectSettleAudit.getEditTime());
			// 公司ID
			dbZxSaProjectSettleAudit.setComID(zxSaProjectSettleAudit.getComID());
			// 公司名称
			dbZxSaProjectSettleAudit.setComName(zxSaProjectSettleAudit.getComName());
			// 公司排序
			dbZxSaProjectSettleAudit.setComOrders(zxSaProjectSettleAudit.getComOrders());
			// 是否锁定
			dbZxSaProjectSettleAudit.setFlowLock(zxSaProjectSettleAudit.getFlowLock());
			// isBack
			dbZxSaProjectSettleAudit.setIsBack(zxSaProjectSettleAudit.getIsBack());
			// 重新评审次数
			dbZxSaProjectSettleAudit.setNotPassNum(zxSaProjectSettleAudit.getNotPassNum());
			// 是否上报局
			dbZxSaProjectSettleAudit.setIsReport(zxSaProjectSettleAudit.getIsReport());
			// appInsHistID
			dbZxSaProjectSettleAudit.setAppInsHistID(zxSaProjectSettleAudit.getAppInsHistID());
			// 老流程ID
			dbZxSaProjectSettleAudit.setOldWorkItemID(zxSaProjectSettleAudit.getOldWorkItemID());
			// isFlag
			dbZxSaProjectSettleAudit.setIsFlag(zxSaProjectSettleAudit.getIsFlag());
			// sendToJuID
			dbZxSaProjectSettleAudit.setSendToJuID(zxSaProjectSettleAudit.getSendToJuID());
			// isFlagZhb
			dbZxSaProjectSettleAudit.setIsFlagZhb(zxSaProjectSettleAudit.getIsFlagZhb());
			// isReportZhb
			dbZxSaProjectSettleAudit.setIsReportZhb(zxSaProjectSettleAudit.getIsReportZhb());
			// sendToZhbID
			dbZxSaProjectSettleAudit.setSendToZhbID(zxSaProjectSettleAudit.getSendToZhbID());
			// appInsHistIDZhb
			dbZxSaProjectSettleAudit.setAppInsHistIDZhb(zxSaProjectSettleAudit.getAppInsHistIDZhb());
			// beginPer
			dbZxSaProjectSettleAudit.setBeginPer(zxSaProjectSettleAudit.getBeginPer());
			// 财务系统id
			dbZxSaProjectSettleAudit.setFiId(zxSaProjectSettleAudit.getFiId());
			// 结算方向
			dbZxSaProjectSettleAudit.setSetDir(zxSaProjectSettleAudit.getSetDir());
			// 核算单位内码
			dbZxSaProjectSettleAudit.setAccountUnitId(zxSaProjectSettleAudit.getAccountUnitId());
			// 核算单位编号
			dbZxSaProjectSettleAudit.setAccountUnitCode(zxSaProjectSettleAudit.getAccountUnitCode());
			// 核算单位名称
			dbZxSaProjectSettleAudit.setAccountUnitName(zxSaProjectSettleAudit.getAccountUnitName());
			// 考核单位Id
			dbZxSaProjectSettleAudit.setAssessUnitId(zxSaProjectSettleAudit.getAssessUnitId());
			// 考核单位编号
			dbZxSaProjectSettleAudit.setAssessUnitCode(zxSaProjectSettleAudit.getAssessUnitCode());
			// 考核单位名称
			dbZxSaProjectSettleAudit.setAssessUnitName(zxSaProjectSettleAudit.getAssessUnitName());
			// 责任单位Id
			dbZxSaProjectSettleAudit.setResponseUnitId(zxSaProjectSettleAudit.getResponseUnitId());
			// 责任单位编号
			dbZxSaProjectSettleAudit.setResponseUnitCode(zxSaProjectSettleAudit.getResponseUnitCode());
			// 责任单位名称
			dbZxSaProjectSettleAudit.setResponseUnitName(zxSaProjectSettleAudit.getResponseUnitName());
			// 核算部门内码
			dbZxSaProjectSettleAudit.setAccountDepId(zxSaProjectSettleAudit.getAccountDepId());
			// 核算部门编号
			dbZxSaProjectSettleAudit.setAccountDepCode(zxSaProjectSettleAudit.getAccountDepCode());
			// 核算部门名称
			dbZxSaProjectSettleAudit.setAccountDepName(zxSaProjectSettleAudit.getAccountDepName());
			// 是否签认
			dbZxSaProjectSettleAudit.setIsSign(zxSaProjectSettleAudit.getIsSign());
			// 业务类型
			dbZxSaProjectSettleAudit.setBusiTypeId(zxSaProjectSettleAudit.getBusiTypeId());
			// 项目编号
			dbZxSaProjectSettleAudit.setProjCode(zxSaProjectSettleAudit.getProjCode());
			// 债权人编号
			dbZxSaProjectSettleAudit.setSecondCode(zxSaProjectSettleAudit.getSecondCode());
			// 计量确认日期
			dbZxSaProjectSettleAudit.setConfirmDate(zxSaProjectSettleAudit.getConfirmDate());
			// 币种
			dbZxSaProjectSettleAudit.setCurrency(zxSaProjectSettleAudit.getCurrency());
			// 汇率
			dbZxSaProjectSettleAudit.setExchangeRate(zxSaProjectSettleAudit.getExchangeRate());
			// 附件张数
			dbZxSaProjectSettleAudit.setNumOfSheets(zxSaProjectSettleAudit.getNumOfSheets());
			// 摘要
			dbZxSaProjectSettleAudit.setSummary(zxSaProjectSettleAudit.getSummary());
			// 税率
			dbZxSaProjectSettleAudit.setTaxRate(zxSaProjectSettleAudit.getTaxRate());
			// 款项性质内码
			dbZxSaProjectSettleAudit.setPayNatureId(zxSaProjectSettleAudit.getPayNatureId());
			// 款项性质编号
			dbZxSaProjectSettleAudit.setPayNatureCode(zxSaProjectSettleAudit.getPayNatureCode());
			// 款项性质名称
			dbZxSaProjectSettleAudit.setPayNatureName(zxSaProjectSettleAudit.getPayNatureName());
			// 到期日期
			dbZxSaProjectSettleAudit.setExpDate(zxSaProjectSettleAudit.getExpDate());
			// 预计付款日期
			dbZxSaProjectSettleAudit.setEstPayDate(zxSaProjectSettleAudit.getEstPayDate());
			// notDisplay
			dbZxSaProjectSettleAudit.setNotDisplay(zxSaProjectSettleAudit.getNotDisplay());
			// 推送时间
			dbZxSaProjectSettleAudit.setSendDate(zxSaProjectSettleAudit.getSendDate());
			// 推送状态
			dbZxSaProjectSettleAudit.setIsSend(zxSaProjectSettleAudit.getIsSend());
			// secondIDCodeBh
			dbZxSaProjectSettleAudit.setSecondIDCodeBh(zxSaProjectSettleAudit.getSecondIDCodeBh());
			// 财务审批状态
			dbZxSaProjectSettleAudit.setCwStatus(zxSaProjectSettleAudit.getCwStatus());
			// 财务审批状态说明
			dbZxSaProjectSettleAudit.setCwStatusRemark(zxSaProjectSettleAudit.getCwStatusRemark());
			// auditWorkitemID
			dbZxSaProjectSettleAudit.setAuditWorkitemID(zxSaProjectSettleAudit.getAuditWorkitemID());
			// auditSys
			dbZxSaProjectSettleAudit.setAuditSys(zxSaProjectSettleAudit.getAuditSys());
			// 交工日期
			dbZxSaProjectSettleAudit.setFinishDate(zxSaProjectSettleAudit.getFinishDate());
			// 结算金额差值
			dbZxSaProjectSettleAudit.setTcje(zxSaProjectSettleAudit.getTcje());
			// 税额差值
			dbZxSaProjectSettleAudit.setTcse(zxSaProjectSettleAudit.getTcse());
			// 调差后累计结算金额
			dbZxSaProjectSettleAudit.setTchljjsje(zxSaProjectSettleAudit.getTchljjsje());
			// 本期调差后税额
			dbZxSaProjectSettleAudit.setBqtchse(zxSaProjectSettleAudit.getBqtchse());
			// 本期调整后结算金额
			dbZxSaProjectSettleAudit.setBqtchjsje(zxSaProjectSettleAudit.getBqtchjsje());
			// 计税方法
			dbZxSaProjectSettleAudit.setTaxCountWay(zxSaProjectSettleAudit.getTaxCountWay());
			// zjgcxm_nm
			dbZxSaProjectSettleAudit.setZJGCXMNM(zxSaProjectSettleAudit.getZJGCXMNM());
			// zjgcxm_xmbh
			dbZxSaProjectSettleAudit.setZJGCXMXMBH(zxSaProjectSettleAudit.getZJGCXMXMBH());
			// 项目
			dbZxSaProjectSettleAudit.setZJGCXMXMMC(zxSaProjectSettleAudit.getZJGCXMXMMC());
			// 本期调差后不含税金额
			dbZxSaProjectSettleAudit.setBqtchjsnotax(zxSaProjectSettleAudit.getBqtchjsnotax());
			// tcnotax
			dbZxSaProjectSettleAudit.setTcnotax(zxSaProjectSettleAudit.getTcnotax());
			// contractCost
			dbZxSaProjectSettleAudit.setContractCost(zxSaProjectSettleAudit.getContractCost());
			// zjxmhtb_nm
			dbZxSaProjectSettleAudit.setZjxmhtbNm(zxSaProjectSettleAudit.getZjxmhtbNm());
			// zjxmhtb_bh
			dbZxSaProjectSettleAudit.setZjxmhtbBh(zxSaProjectSettleAudit.getZjxmhtbBh());
			// zjxmhtb_mc
			dbZxSaProjectSettleAudit.setZjxmhtbMc(zxSaProjectSettleAudit.getZjxmhtbMc());
			// isRela
			dbZxSaProjectSettleAudit.setIsRela(zxSaProjectSettleAudit.getIsRela());
			// orgCertificate
			dbZxSaProjectSettleAudit.setOrgCertificate(zxSaProjectSettleAudit.getOrgCertificate());
			// 是否为首次结算
			dbZxSaProjectSettleAudit.setIsFirst(zxSaProjectSettleAudit.getIsFirst());
			// upWorkItemID
			dbZxSaProjectSettleAudit.setUpWorkItemID(zxSaProjectSettleAudit.getUpWorkItemID());
			// oaOrgID
			dbZxSaProjectSettleAudit.setOaOrgID(zxSaProjectSettleAudit.getOaOrgID());
			// 含税合同金额(万元)
			dbZxSaProjectSettleAudit.setContractAmt(zxSaProjectSettleAudit.getContractAmt());
			// 变更后含税合同金额(万元)
			dbZxSaProjectSettleAudit.setChangeAmt(zxSaProjectSettleAudit.getChangeAmt());
			// 是否完工后结算
			dbZxSaProjectSettleAudit.setIsFished(zxSaProjectSettleAudit.getIsFished());
			// 是否抵扣
			dbZxSaProjectSettleAudit.setIsDeduct(zxSaProjectSettleAudit.getIsDeduct());
			// 是否可以反审核
			dbZxSaProjectSettleAudit.setUseCount(zxSaProjectSettleAudit.getUseCount());
			// 是否最大期次
			dbZxSaProjectSettleAudit.setIsMaxPeriod(zxSaProjectSettleAudit.getIsMaxPeriod());
			// 签认单编号
			dbZxSaProjectSettleAudit.setSignedNo(zxSaProjectSettleAudit.getSignedNo());
			// 完成下列工程开始日期
			dbZxSaProjectSettleAudit.setStartDate(zxSaProjectSettleAudit.getStartDate());
			// 附图及附表(可另附页计算)
			dbZxSaProjectSettleAudit.setAttachmentInfo(zxSaProjectSettleAudit.getAttachmentInfo());
			// 工程质量评价
			dbZxSaProjectSettleAudit.setAppraisal(zxSaProjectSettleAudit.getAppraisal());
			// 流水号
			dbZxSaProjectSettleAudit.setSerialNumber(zxSaProjectSettleAudit.getSerialNumber());
			// 结算单初始化顺序号
			dbZxSaProjectSettleAudit.setInitSerialNumber(zxSaProjectSettleAudit.getInitSerialNumber());
			// 排序
			dbZxSaProjectSettleAudit.setSort(zxSaProjectSettleAudit.getSort());
			// 备注
			dbZxSaProjectSettleAudit.setRemark(zxSaProjectSettleAudit.getRemark());
			// 共通
			dbZxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);

			// ---------------金额处理----------------
			// 本期结算金额(元)
			dbZxSaProjectSettleAudit.setThisAmt(zxSaProjectSettleAudit.getThisAmt());
			// 开累结算金额(元)
			dbZxSaProjectSettleAudit.setTotalAmt(zxSaProjectSettleAudit.getTotalAmt());
			// 本期结算不含税金额(元)
			dbZxSaProjectSettleAudit.setThisAmtNoTax(zxSaProjectSettleAudit.getThisAmtNoTax());
			// 本期结算税额(元)
			dbZxSaProjectSettleAudit.setThisAmtTax(zxSaProjectSettleAudit.getThisAmtTax());

			if (projectSettleAuditItemList != null && projectSettleAuditItemList.size() > 0) {
				for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : projectSettleAuditItemList) {
					if (StrUtil.equals("100700", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// 本期应支付金额(元)
						dbZxSaProjectSettleAudit.setThisPayAmt(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
						// 开累应支付金额(元)
						dbZxSaProjectSettleAudit.setTotalPayAmt(new BigDecimal(zxSaProjectSettleAuditItem.getTotalAmt()));
						break;
					}
				}
			}
			flag = zxSaProjectSettleAuditMapper.updateByPrimaryKey(dbZxSaProjectSettleAudit);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// 附件
			ZxErpFile delZxErpFile = new ZxErpFile();
			delZxErpFile.setOtherId(dbZxSaProjectSettleAudit.getId());
			delZxErpFile.setOtherType("0");
			zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);

			List<ZxErpFile> zxErpFileList = zxSaProjectSettleAudit.getZxErpFileList();
			if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(dbZxSaProjectSettleAudit.getId());
					zxErpFile.setOtherType("0");
					zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
			
			// 明细---不删除直接修改
//        	ZxSaProjectSettleAuditItem delZxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
//        	delZxSaProjectSettleAuditItem.setProjectSettleAuditId(dbZxSaProjectSettleAudit.getId());
//        	zxSaProjectSettleAuditItemServiceImpl.deleteAllZxSaProjectSettleAuditItem(delZxSaProjectSettleAuditItem);

			if (projectSettleAuditItemList != null && projectSettleAuditItemList.size() > 0) {
				for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : projectSettleAuditItemList) {
					zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
					zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);

//					zxSaProjectSettleAuditItem.setProjectSettleAuditId(dbZxSaProjectSettleAudit.getId());
//					zxSaProjectSettleAuditItemServiceImpl.saveZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItem);
				}
			}

			return repEntity.ok("sys.data.update", zxSaProjectSettleAudit);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxSaProjectSettleAudit(List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxSaProjectSettleAuditList != null && zxSaProjectSettleAuditList.size() > 0) {
			ZxSaProjectSettleAudit zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
			zxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);
			flag = zxSaProjectSettleAuditMapper.batchDeleteUpdateZxSaProjectSettleAudit(zxSaProjectSettleAuditList, zxSaProjectSettleAudit);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			JSONArray jsonArray = new JSONArray();
			for (ZxSaProjectSettleAudit zxSaProjectSettleAudit : zxSaProjectSettleAuditList) {
				// 附件
				ZxErpFile delZxErpFile = new ZxErpFile();
				delZxErpFile.setOtherId(zxSaProjectSettleAudit.getId());
				delZxErpFile.setOtherType("0");
				zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);

				// 当某合同首次结算单被删除时，也要把对应合同中结算情况恢复为“实际无结算”(根据合同id修改)
				ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
				zxGcsgCtContract.setCtContractId(zxSaProjectSettleAudit.getContractID());
				zxGcsgCtContract.setSettleType("实际无结算");
				zxGcsgCtContract.setModifyUserInfo(userKey, realName);
				zxGcsgCtContractMapper.updateSettleTypeByPrimaryKey(zxGcsgCtContract);
				
				// 明细
				ZxSaProjectSettleAuditItem delZxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
				delZxSaProjectSettleAuditItem.setProjectSettleAuditId(zxSaProjectSettleAudit.getId());
				zxSaProjectSettleAuditItemServiceImpl.deleteAllZxSaProjectSettleAuditItem(delZxSaProjectSettleAuditItem);
				
				// 清单
				ZxSaProjectWorkSettle delWorkSettle = new ZxSaProjectWorkSettle();
				delWorkSettle.setBillID(zxSaProjectSettleAudit.getId());
				List<ZxSaProjectWorkSettle> delWorkSettleList = zxSaProjectWorkSettleMapper.selectByZxSaProjectWorkSettleList(delWorkSettle);
				if (delWorkSettleList != null && delWorkSettleList.size() > 0) {
					zxSaProjectWorkSettleService.batchDeleteUpdateZxSaProjectWorkSettle(delWorkSettleList);
				}
				
				// 支付项
				ZxSaProjectPaySettle delPaySettle = new ZxSaProjectPaySettle();
				delPaySettle.setBillID(zxSaProjectSettleAudit.getId());
				List<ZxSaProjectPaySettle> delPaySettleList = zxSaProjectPaySettleMapper.selectByZxSaProjectPaySettleList(delPaySettle);
				if (delPaySettleList != null && delPaySettleList.size() > 0) {
					zxSaProjectPaySettleService.batchDeleteUpdateZxSaProjectPaySettle(delPaySettleList);
				}
				
				if (StrUtil.isNotEmpty(zxSaProjectSettleAudit.getWorkId())) {
					jsonArray.add(zxSaProjectSettleAudit.getWorkId());
				}
			}

			// 删除流程后台数据
            String url = Apih5Properties.getWebUrl() + "batchDeleteFlow";
            if(jsonArray.size() > 0) {
                HttpUtil.sendPostToken(url, jsonArray.toString(), TokenUtils.getToken(request));
            }
			
			return repEntity.ok("sys.data.delete", zxSaProjectSettleAuditList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity getZxSaProjectSettleAuditProjectList(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		if (zxSaProjectSettleAudit == null) {
			zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		}
		// 分页查询
		PageHelper.startPage(zxSaProjectSettleAudit.getPage(), zxSaProjectSettleAudit.getLimit());
		// 获取数据
		List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper
				.getZxSaProjectSettleAuditProjectList(zxSaProjectSettleAudit);
		// 得到分页信息
		PageInfo<ZxSaProjectSettleAudit> p = new PageInfo<>(zxSaProjectSettleAuditList);

		return repEntity.okList(zxSaProjectSettleAuditList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxSaProjectSettleAuditContractNoList(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		if (zxSaProjectSettleAudit == null) {
			zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		}

		if (StrUtil.isEmpty(zxSaProjectSettleAudit.getOrgID())) {
			return repEntity.layerMessage("no", "orgID不能为空！");
		}

		// 分页查询
		PageHelper.startPage(zxSaProjectSettleAudit.getPage(), zxSaProjectSettleAudit.getLimit());
		// 获取数据
		List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper
				.getZxSaProjectSettleAuditContractNoList(zxSaProjectSettleAudit);
		// 得到分页信息
		PageInfo<ZxSaProjectSettleAudit> p = new PageInfo<>(zxSaProjectSettleAuditList);
		// 结算单编号、签认单编号生成(需处理--暂时不正确)
		for (ZxSaProjectSettleAudit projectSettleAudit : zxSaProjectSettleAuditList) {
			// 查询本期次本月结算份数
			ZxSaProjectSettleAudit selectProjectSettleAudit = new ZxSaProjectSettleAudit();
			selectProjectSettleAudit.setContractID(projectSettleAudit.getContractID());
			selectProjectSettleAudit.setCreateTime(new Date());
			List<ZxSaProjectSettleAudit> dbZxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper
					.selectByZxSaProjectSettleAuditList(selectProjectSettleAudit);
			if (dbZxSaProjectSettleAuditList == null || dbZxSaProjectSettleAuditList.size() == 0) {
				projectSettleAudit.setStatementNo(0);
//            	projectSettleAudit.setBillNo(projectSettleAudit.getContractNo() + "202011-01");
//            	projectSettleAudit.setSecondCode(projectSettleAudit.getContractNo() + "-SL-202011-01");
			} else {
				String billNo = dbZxSaProjectSettleAuditList.get(0).getBillNo();
				if (StrUtil.isEmpty(billNo)) {
					projectSettleAudit.setStatementNo(0);
				} else {
					billNo = billNo.substring(billNo.lastIndexOf("-") + 1);
					projectSettleAudit.setStatementNo(Integer.valueOf(billNo));
				}
//				int size = dbZxSaProjectSettleAuditList.size() + 1;
//				projectSettleAudit.setBillNo(projectSettleAudit.getContractNo() + "202011-" + (size < 10 ? "0" + size : size));
//            	projectSettleAudit.setSecondCode(projectSettleAudit.getContractNo() + "-SL-202011-" + (size < 10 ? "0" + size : size));
			}
		}

		return repEntity.okList(zxSaProjectSettleAuditList, p.getTotal());
	}

	@Override
	public ResponseEntity addZxSaProjectSettleAuditOnCommitFlow(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectSettleAudit.getId());
		if (dbZxSaProjectSettleAudit != null && StrUtil.isNotEmpty(dbZxSaProjectSettleAudit.getId())) {
			// 共通
			dbZxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);
			// 流程ID
			dbZxSaProjectSettleAudit.setWorkId(zxSaProjectSettleAudit.getWorkId());
			// 流程状态
			dbZxSaProjectSettleAudit.setApih5FlowStatus("1");

			flag = zxSaProjectSettleAuditMapper.updateByPrimaryKey(dbZxSaProjectSettleAudit);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// 附件
			ZxErpFile delZxErpFile = new ZxErpFile();
			delZxErpFile.setOtherId(dbZxSaProjectSettleAudit.getId());
			delZxErpFile.setOtherType("1");
			zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);

			List<ZxErpFile> zxErpFileList = zxSaProjectSettleAudit.getTextAttachmentList();
			if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(dbZxSaProjectSettleAudit.getId());
					zxErpFile.setOtherType("1");
					zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
			return repEntity.ok("sys.data.update", zxSaProjectSettleAudit);
		}
	}
	
	@Override
	public ResponseEntity updateZxSaProjectSettleAuditOnCommitFlow(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectSettleAudit.getId());
		if (dbZxSaProjectSettleAudit != null && StrUtil.isNotEmpty(dbZxSaProjectSettleAudit.getId())) {
			// 共通
			dbZxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);
			// 流程ID
			dbZxSaProjectSettleAudit.setWorkId(zxSaProjectSettleAudit.getWorkId());
			if (StrUtil.isNotEmpty(zxSaProjectSettleAudit.getApih5FlowStatus())) {
				// 流程状态
				dbZxSaProjectSettleAudit.setApih5FlowStatus(zxSaProjectSettleAudit.getApih5FlowStatus());
			}
			// 流程的意见
			if (StrUtil.equals("opinionField1", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField1(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField1()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField2", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField2(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField2()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField3", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField3(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField3()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField4", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField4(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField4()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField5", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField5(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField5()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField6", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField6(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField6()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField7", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField7(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField7()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField8", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField8(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField8()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField9", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField9(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField9()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField10", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField10(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField10()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField11", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField11(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField11()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField12", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField12(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField12()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField13", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField13(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField13()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField14", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField14(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField14()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField15", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField15(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField15()));
			}
			// 流程的意见
			if (StrUtil.equals("opinionField16", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField16(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField16()));
			}
			
			flag = zxSaProjectSettleAuditMapper.updateByPrimaryKey(dbZxSaProjectSettleAudit);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			if (StrUtil.equals("2", dbZxSaProjectSettleAudit.getApih5FlowStatus()) && StrUtil.equals("1", dbZxSaProjectSettleAudit.getBillType())) {
				// 当某合同的结算单类型为最终结算，审核通过后要把对应合同中结算情况更新为“已最终结算”(根据合同id修改)
				ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
				zxGcsgCtContract.setCtContractId(dbZxSaProjectSettleAudit.getContractID());
				zxGcsgCtContract.setSettleType("已最终结算");
				zxGcsgCtContract.setModifyUserInfo(userKey, realName);
				zxGcsgCtContractMapper.updateSettleTypeByPrimaryKey(zxGcsgCtContract);
			}
			return repEntity.ok("sys.data.update", zxSaProjectSettleAudit);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ZxSaProjectSettleAudit getZxSaProjectSettleAuditDetailNoToken(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		if (zxSaProjectSettleAudit == null) {
			zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		}
		// 获取数据
		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectSettleAudit.getId());
		// 数据存在
		if (dbZxSaProjectSettleAudit != null) {
			// 数据处理
			String period = ""; // 结算期限
			if (dbZxSaProjectSettleAudit.getBeginDate() != null) {
				period = DateUtil.format(dbZxSaProjectSettleAudit.getBeginDate(), "yyyy-MM-dd");
				if (dbZxSaProjectSettleAudit.getEndDate() != null) {
					period = period + "至" + DateUtil.format(dbZxSaProjectSettleAudit.getEndDate(), "yyyy-MM-dd");
				} else {
					period = period + "至--";
				}
			} else {
				period = "--";
				if (dbZxSaProjectSettleAudit.getEndDate() != null) {
					period = period + "至" + DateUtil.format(dbZxSaProjectSettleAudit.getEndDate(), "yyyy-MM-dd");
				} else {
					period = period + "至--";
				}
			}
			dbZxSaProjectSettleAudit.setSecondCode(period);
			
			// 查询应付工程款大写信息
			ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
			zxSaProjectSettleAuditItem.setProjectSettleAuditId(dbZxSaProjectSettleAudit.getId());
			List<ZxSaProjectSettleAuditItem> projectSettleAuditItemList = (List<ZxSaProjectSettleAuditItem>) zxSaProjectSettleAuditItemServiceImpl
					.getZxSaProjectSettleAuditItemListByCondition(zxSaProjectSettleAuditItem).getData();
			if (projectSettleAuditItemList != null && projectSettleAuditItemList.size() > 0) {
				projectSettleAuditItemList.forEach(item -> {
					if (StrUtil.equals("100700", item.getStatisticsNo())) {
						dbZxSaProjectSettleAudit.setZjxmhtbNm(item.getThisAmt());
						dbZxSaProjectSettleAudit.setZjxmhtbBh(item.getTotalAmt());
					} else if (StrUtil.equals("100800", item.getStatisticsNo())) {
						dbZxSaProjectSettleAudit.setZjxmhtbMc(item.getThisAmt());
						dbZxSaProjectSettleAudit.setOrgCertificate(item.getTotalAmt());
					}
				});
			}
			dbZxSaProjectSettleAudit.setRemark(dbZxSaProjectSettleAudit.getReportDate() == null ? "" : DateUtil.format(dbZxSaProjectSettleAudit.getReportDate(), "yyyy年MM月dd日"));
			return dbZxSaProjectSettleAudit;
		} else {
			return zxSaProjectSettleAudit;
		}
	}
}
