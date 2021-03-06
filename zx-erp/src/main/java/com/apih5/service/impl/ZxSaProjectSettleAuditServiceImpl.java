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
		
		// ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
        	zxSaProjectSettleAudit.setCompanyId("");
        	zxSaProjectSettleAudit.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxSaProjectSettleAudit.setCompanyId(zxSaProjectSettleAudit.getOrgID());
        	zxSaProjectSettleAudit.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxSaProjectSettleAudit.setOrgID(zxSaProjectSettleAudit.getOrgID());
        }
		
		// ????????????
		PageHelper.startPage(zxSaProjectSettleAudit.getPage(), zxSaProjectSettleAudit.getLimit());
		// ????????????
		List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper.selectByZxSaProjectSettleAuditList(zxSaProjectSettleAudit);
		// ??????????????????
		PageInfo<ZxSaProjectSettleAudit> p = new PageInfo<>(zxSaProjectSettleAuditList);

		for (ZxSaProjectSettleAudit projectSettleAudit : zxSaProjectSettleAuditList) {
			// ??????
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

			// ??????
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
			// ????????????
			ZxSaProjectSettleAudit settleAudit = new ZxSaProjectSettleAudit();
			settleAudit.setWorkId(zxSaProjectSettleAudit.getWorkId());
			List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper.selectByZxSaProjectSettleAuditList(settleAudit);
			if (zxSaProjectSettleAuditList != null && zxSaProjectSettleAuditList.size() > 0) {
				dbZxSaProjectSettleAudit = zxSaProjectSettleAuditList.get(0);
			}
		} else {
			// ????????????
			dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectSettleAudit.getId());
		}
		
		// ????????????
		if (dbZxSaProjectSettleAudit != null) {
			// ??????
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
			
			// ????????????
			ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
			zxSaProjectSettleAuditItem.setProjectSettleAuditId(dbZxSaProjectSettleAudit.getId());
	        List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(zxSaProjectSettleAuditItem);
	        dbZxSaProjectSettleAudit.setProjectSettleAuditItemList(zxSaProjectSettleAuditItemList);
			
			// ??????????????????
			ZxSaProjectWorkSettle selectWorkSettle = new ZxSaProjectWorkSettle();
			selectWorkSettle.setContractID(dbZxSaProjectSettleAudit.getContractID());
			selectWorkSettle.setOrgID(dbZxSaProjectSettleAudit.getOrgID());
			selectWorkSettle.setPeriod(dbZxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectWorkSettle> workSettleList = (List<ZxSaProjectWorkSettle>) zxSaProjectWorkSettleService.getZxSaProjectWorkSettleListByCondition(selectWorkSettle).getData();
			if (workSettleList != null && workSettleList.size() > 0) {
				ZxSaProjectWorkSettle dbZxSaProjectWorkSettle = workSettleList.get(0);

				// ????????????????????????(??????)
				dbZxSaProjectSettleAudit.setWorkContractAmt(dbZxSaProjectWorkSettle.getContractAmt());
				// ?????????????????????????????????(??????)
				dbZxSaProjectSettleAudit.setWorkChangeAmt(dbZxSaProjectWorkSettle.getChangeAmt());
				// ????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setWorkThisAmt(dbZxSaProjectWorkSettle.getThisAmt());
				// ????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setWorkTotalAmt(dbZxSaProjectWorkSettle.getTotalAmt());
				// ???????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setWorkThisAmtNoTax(dbZxSaProjectWorkSettle.getThisAmtNoTax());
				// ??????????????????????????????(???)
				dbZxSaProjectSettleAudit.setWorkThisAmtTax(dbZxSaProjectWorkSettle.getThisAmtTax());
				// ??????????????????ID
				dbZxSaProjectSettleAudit.setProjectWorkSettleId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
				// ????????????
				dbZxSaProjectSettleAudit.setWorkSettleItemList(dbZxSaProjectWorkSettle.getZxSaProjectWorkSettleItemList());
			}

			// ?????????????????????
			ZxSaProjectPaySettle selectPaySettle = new ZxSaProjectPaySettle();
			selectPaySettle.setContractID(dbZxSaProjectSettleAudit.getContractID());
			selectPaySettle.setOrgID(dbZxSaProjectSettleAudit.getOrgID());
			selectPaySettle.setPeriod(dbZxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectPaySettle> paySettleList = (List<ZxSaProjectPaySettle>) zxSaProjectPaySettleService.getZxSaProjectPaySettleListByCondition(selectPaySettle).getData();
			if (paySettleList != null && paySettleList.size() > 0) {
				ZxSaProjectPaySettle dbZxSaProjectPaySettle = paySettleList.get(0);

				// ??????????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayThisAmt(dbZxSaProjectPaySettle.getThisAmt());
				// ????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayThisAmtNoTax(dbZxSaProjectPaySettle.getThisAmtNoTax());
				// ?????????????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayTotalAmt(dbZxSaProjectPaySettle.getTotalAmt());
				// ?????????????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayMaterialAmt(dbZxSaProjectPaySettle.getMaterialAmt());
				// ?????????????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayMachineAmt(dbZxSaProjectPaySettle.getMachineAmt());
				// ??????????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayTempAmt(dbZxSaProjectPaySettle.getTempAmt());
				// ???????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayFineAmt(dbZxSaProjectPaySettle.getFineAmt());
				// ???????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayRecoupAmt(dbZxSaProjectPaySettle.getRecoupAmt());
				// ???????????????????????????????????????(???)
				dbZxSaProjectSettleAudit.setPayOtherAmt(dbZxSaProjectPaySettle.getOtherAmt());
				// ?????????????????????ID
				dbZxSaProjectSettleAudit.setProjectPaySettleId(dbZxSaProjectPaySettle.getProjectPaySettleId());

				dbZxSaProjectSettleAudit.setPaySettleItemList(dbZxSaProjectPaySettle.getProjectPaySettleItemList());
			}

			return repEntity.ok(dbZxSaProjectSettleAudit);
		} else {
			return repEntity.layerMessage("no", "????????????");
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
		
		// check????????????????????????????????????????????????????????????????????????????????????????????????????????????	
		ZxSaProjectSettleAudit checkProjectSettleAudit = new ZxSaProjectSettleAudit();
		checkProjectSettleAudit.setOrgID(zxSaProjectSettleAudit.getOrgID());
		checkProjectSettleAudit.setContractID(zxSaProjectSettleAudit.getContractID());
		List<ZxSaProjectSettleAudit> settleAuditList = zxSaProjectSettleAuditMapper.getZxSaProjectSettleAuditTotalList(checkProjectSettleAudit);
		// ??????
		if (settleAuditList != null && settleAuditList.size() > 0) {
			int period = Integer.valueOf(zxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectSettleAudit> greaterThan  = settleAuditList.stream().filter(settleAudit -> Integer.valueOf(settleAudit.getPeriod()) >= period).collect(Collectors.toList());
			if (greaterThan != null && greaterThan.size() > 0) {
				return repEntity.layerMessage("no", "??????????????????????????????????????????????????????????????????????????????");
			}
			List<ZxSaProjectSettleAudit> lessThan  = settleAuditList.stream().filter(settleAudit -> Integer.valueOf(settleAudit.getPeriod()) < period && !StrUtil.equals(settleAudit.getApih5FlowStatus(), "2")).collect(Collectors.toList());
			if (lessThan != null && lessThan.size() > 0) {
				return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????????????????????????????????????????");
			}
			zxSaProjectSettleAudit.setIsFirst("0"); // ?????????????????????0?????? 1?????????
		} else {
			zxSaProjectSettleAudit.setIsFirst("1"); // ?????????????????????0?????? 1?????????
		}

		// ?????????????????????????????????????????????????????????????????????????????????
		ZxSaProjectSettleAuditItem selectSettleAuditItem = new ZxSaProjectSettleAuditItem();
		selectSettleAuditItem.setContractID(zxSaProjectSettleAudit.getContractID());
		selectSettleAuditItem.setPeriod(zxSaProjectSettleAudit.getPeriod());
		List<ZxSaProjectSettleAuditItem> projectSettleAuditItemList = (List<ZxSaProjectSettleAuditItem>) zxSaProjectSettleAuditItemService.getZxSaProjectSettleAuditItemByContractId(selectSettleAuditItem).getData();
		
		// ??????????????????????????????????????????????????????????????????????????????????????????
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
			// ???????????????????????????????????????????????????????????????????????????
			ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
			zxGcsgCtContract.setCtContractId(zxSaProjectSettleAudit.getContractID());
			ZxGcsgCtContract dbZxGcsgCtContract = (ZxGcsgCtContract) zxGcsgCtContractService.getZxGcsgCtContractDetail(zxGcsgCtContract).getData();
			if (dbZxGcsgCtContract != null) {
				// ??????????????????(??????)
				zxSaProjectWorkSettle.setContractAmt(dbZxGcsgCtContract.getContractCost());
				// ???????????????????????????(??????)
				zxSaProjectWorkSettle.setChangeAmt(dbZxGcsgCtContract.getAlterContractSum());
			}

			// ??????????????????????????????(???)
			BigDecimal thisAmt = new BigDecimal(0);
			// ?????????????????????????????????(???)
			BigDecimal thisAmtNoTax = new BigDecimal(0);
			// ????????????????????????(???)
			BigDecimal thisAmtTax = new BigDecimal(0);
			// ??????????????????????????????(???)
			BigDecimal totalAmt = new BigDecimal(0);
			// ????????????
			List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList = new ArrayList<>();

			for (ZxGcsgCcWorks works : zxGcsgCcWorksList) {
				ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
				BeanUtil.copyProperties(works, zxSaProjectWorkSettleItem, true);
				zxSaProjectWorkSettleItem.setWorkID(works.getCcWorksId());
				zxSaProjectWorkSettleItem.setContractID(zxSaProjectSettleAudit.getContractID());
				// ???????????????
				zxSaProjectWorkSettleItem.setSignedNo(zxSaProjectSettleAudit.getSignedNo());
				// ????????????(???)
				zxSaProjectWorkSettleItem.setPrice(works.getContractPrice());
				// ??????????????????(???)
				zxSaProjectWorkSettleItem.setContractAmt(CalcUtils.calcMultiply(zxSaProjectWorkSettleItem.getContractQty(), zxSaProjectWorkSettleItem.getPrice()));
				// ???????????????
				zxSaProjectWorkSettleItem.setChangeQty(works.getQuantity());
				// ?????????????????????
				zxSaProjectWorkSettleItem.setChangePrice(works.getPrice());
				// ?????????????????????
				zxSaProjectWorkSettleItem.setChangeAmt(CalcUtils.calcMultiply(works.getQuantity(), works.getPrice()));
				// ????????????????????????
				ZxSaProjectWorkSettleItem selectItem = new ZxSaProjectWorkSettleItem();
				selectItem.setContractID(zxSaProjectSettleAudit.getContractID());
				selectItem.setPeriod(zxSaProjectSettleAudit.getPeriod());
				selectItem.setTreeNode(works.getTreeNode());
				ZxSaProjectWorkSettleItem dbItem = zxSaProjectWorkSettleItemMapper.getCumulativeInfo(selectItem);
				if (dbItem != null) {
					// ????????????????????????
					zxSaProjectWorkSettleItem.setAllQty(dbItem.getAllQty());
					// ????????????????????????
					zxSaProjectWorkSettleItem.setAllChangeQty(dbItem.getAllChangeQty());
					// ????????????????????????
					zxSaProjectWorkSettleItem.setAllTotalQty(dbItem.getAllTotalQty());
					// ????????????????????????(???)
					zxSaProjectWorkSettleItem.setAllTotalAmt(dbItem.getAllTotalAmt());
				} else {
					// ????????????????????????
					zxSaProjectWorkSettleItem.setAllQty(new BigDecimal(0));
					// ????????????????????????
					zxSaProjectWorkSettleItem.setAllChangeQty(new BigDecimal(0));
					// ????????????????????????
					zxSaProjectWorkSettleItem.setAllTotalQty(new BigDecimal(0));
					// ????????????????????????(???)
					zxSaProjectWorkSettleItem.setAllTotalAmt(new BigDecimal(0));
				}
				// ??????????????????
				zxSaProjectWorkSettleItem.setEditTime(new Date());
				// ????????????
				zxSaProjectWorkSettleItem.setPeriod(zxSaProjectSettleAudit.getPeriod());
				// ????????????ID
				zxSaProjectWorkSettleItem.setComID(zxSaProjectSettleAudit.getComID());
				// ????????????
				zxSaProjectWorkSettleItem.setComName(zxSaProjectSettleAudit.getComName());
				// ??????????????????
				zxSaProjectWorkSettleItem.setComOrders(zxSaProjectSettleAudit.getComOrders());
				// ??????????????????(???)
//				BigDecimal amt = CalcUtils.calcMultiply(zxSaProjectWorkSettleItem.getContractQty(), zxSaProjectWorkSettleItem.getContractAmt());
				BigDecimal amt = CalcUtils.calcMultiply(zxSaProjectWorkSettleItem.getThisQty(), zxSaProjectWorkSettleItem.getContractAmt());
				thisAmt = CalcUtils.calcAdd(thisAmt, amt);
				// ?????????????????????(???)
				BigDecimal amtNoTax = CalcUtils.calcDivide(amt, CalcUtils.calcAdd(new BigDecimal(1), zxSaProjectWorkSettleItem.getTaxRate()), 6);
				thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, amtNoTax);
				zxSaProjectWorkSettleItemList.add(zxSaProjectWorkSettleItem);
			}
			thisAmtTax = CalcUtils.calcSubtract(thisAmt, thisAmtNoTax);
			// ???????????????????????????????????????
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

			// ?????????????????????
			if (projectSettleAuditItemList != null && projectSettleAuditItemList.size() > 0) {
				// ????????????????????????
				BigDecimal totalItemAmt = new BigDecimal(0);
				BigDecimal totalTotalItemAmt = new BigDecimal(0);
				// ?????????????????????
				for (ZxSaProjectSettleAuditItem settleAuditItem : projectSettleAuditItemList) {
					// ?????????????????????????????????????????????????????????????????????
					if(StrUtil.equals("100400", settleAuditItem.getStatisticsType())) {
						BigDecimal thisItemAmt = CalcUtils.calcMultiply(zxSaProjectWorkSettle.getThisAmt(), CalcUtils.calcDivide(settleAuditItem.getRate(), new BigDecimal(100)));
						totalItemAmt = CalcUtils.calcAdd(totalItemAmt, thisItemAmt);
						settleAuditItem.setThisAmt(thisItemAmt + "");
						BigDecimal allItemAmt = CalcUtils.calcAdd(thisItemAmt, settleAuditItem.getUpAmt());
						totalTotalItemAmt = CalcUtils.calcAdd(totalTotalItemAmt, allItemAmt);
						settleAuditItem.setTotalAmt(CalcUtils.calcAdd(totalItemAmt, settleAuditItem.getUpAmt()) + "");
					}
				}
				
				// ?????????????????????????????????
				for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : projectSettleAuditItemList) {
					// ?????????????????????
					BigDecimal upAmt = zxSaProjectSettleAuditItem.getUpAmt();
					if (StrUtil.equals("100100", zxSaProjectSettleAuditItem.getStatisticsNo())) { 
						// ???????????????????????????????????????????????????????????????????????????
						zxSaProjectSettleAuditItem.setThisAmt(zxSaProjectWorkSettle.getThisAmt() + "");
						zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmt(), upAmt) + "");
						zxSaProjectSettleAudit.setThisAmt(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
						zxSaProjectSettleAudit.setTotalAmt(new BigDecimal(zxSaProjectSettleAuditItem.getTotalAmt()));
					} else if (StrUtil.equals("100110", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// ?????????????????????????????????????????????????????????????????????????????????
						zxSaProjectSettleAuditItem.setThisAmt(zxSaProjectWorkSettle.getThisAmtNoTax() + "");
						zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmtNoTax(), upAmt) + "");
						zxSaProjectSettleAudit.setThisAmtNoTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
					} else if (StrUtil.equals("100120", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// ???????????????????????????????????????????????????????????????
						zxSaProjectSettleAuditItem.setThisAmt(zxSaProjectWorkSettle.getThisAmtTax() + "");
						zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmtTax(), upAmt) + "");
						zxSaProjectSettleAudit.setThisAmtTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
					} else if (StrUtil.equals("100200", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// ???????????????????????????????????????????????????????????????????????????
						zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaProjectWorkSettle.getThisAmt()));
						zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmt(), upAmt)));
					} else if (StrUtil.equals("100210", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// ?????????????????????????????????????????????????????????????????????????????????
						zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaProjectWorkSettle.getThisAmtNoTax()));
						zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmtNoTax(), upAmt)));
					} else if (StrUtil.equals("100220", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// ???????????????????????????????????????????????????????????????
						zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(zxSaProjectWorkSettle.getThisAmtTax()));
						zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectWorkSettle.getThisAmtTax(), upAmt)));
					} else if (StrUtil.equals("100300", zxSaProjectSettleAuditItem.getStatisticsType())) {
						// ???????????????????????????
						zxSaProjectSettleAuditItem.setThisAmt(totalItemAmt + "");
						zxSaProjectSettleAuditItem.setTotalAmt(totalTotalItemAmt + "");
					} else if (StrUtil.equals("100700", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// ???????????????????????????
						zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcSubtract(zxSaProjectWorkSettle.getThisAmt(), totalItemAmt) + "");
						zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()), zxSaProjectSettleAuditItem.getUpAmt()) + "");
						zxSaProjectSettleAudit.setThisPayAmt(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
						zxSaProjectSettleAudit.setTotalPayAmt(new BigDecimal(zxSaProjectSettleAuditItem.getTotalAmt()));
					} else if(StrUtil.equals("100800", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// ???????????????????????????
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
			// ???????????????????????????????????????????????????????????????
			ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
			zxGcsgCtContract.setCtContractId(zxSaProjectSettleAudit.getContractID());
			zxGcsgCtContract.setSettleType("??????????????????");
			zxGcsgCtContract.setModifyUserInfo(userKey, realName);
			zxGcsgCtContractMapper.updateSettleTypeByPrimaryKey(zxGcsgCtContract);
			
			// ??????
			List<ZxErpFile> zxErpFileList = zxSaProjectSettleAudit.getZxErpFileList();
			if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(zxSaProjectSettleAudit.getId());
					zxErpFile.setOtherType("0");
					zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}

			// ?????????????????????
			ZxSaProjectPaySettle zxSaProjectPaySettle = new ZxSaProjectPaySettle();
			zxSaProjectPaySettle.setOrgID(zxSaProjectSettleAudit.getOrgID());
			zxSaProjectPaySettle.setContractID(zxSaProjectSettleAudit.getContractID());
			zxSaProjectPaySettle.setPeriod(zxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectPaySettle> zxSaProjectPaySettleList = (List<ZxSaProjectPaySettle>) zxSaProjectPaySettleService.getZxSaProjectPaySettleListByCondition(zxSaProjectPaySettle).getData();
			if (zxSaProjectPaySettleList == null || zxSaProjectPaySettleList.size() == 0) {
				// ??????????????????????????????
				ZxSaProjectPaySettle upPaySettle = new ZxSaProjectPaySettle();
				upPaySettle.setOrgID(zxSaProjectSettleAudit.getOrgID());
				upPaySettle.setContractID(zxSaProjectSettleAudit.getContractID());
				upPaySettle.setPeriod(zxSaProjectSettleAudit.getPeriod());
				ZxSaProjectPaySettle upZxSaProjectPaySettle = zxSaProjectPaySettleMapper.getUpZxSaProjectPaySettle(upPaySettle);
				if (upZxSaProjectPaySettle == null) {
					upZxSaProjectPaySettle = new ZxSaProjectPaySettle();
				}

				ZxSaProjectPaySettle addZxSaProjectPaySettle = new ZxSaProjectPaySettle();
				// ??????ID
				addZxSaProjectPaySettle.setOrgID(zxSaProjectSettleAudit.getOrgID());
				// ????????????
				addZxSaProjectPaySettle.setOrgName(zxSaProjectSettleAudit.getOrgName());
				// ??????ID
				addZxSaProjectPaySettle.setContractID(zxSaProjectSettleAudit.getContractID());
				// ???????????????????????????(???)
				addZxSaProjectPaySettle.setThisAmt(new BigDecimal(0));
				// ???????????????????????????(???)
				addZxSaProjectPaySettle.setTotalAmt(upZxSaProjectPaySettle.getTotalAmt());
				// ????????????????????????????????????(???)
				addZxSaProjectPaySettle.setUpAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getThisAmt(), upZxSaProjectPaySettle.getUpAmt()));
				// ?????????????????????????????????(???)
				addZxSaProjectPaySettle.setMaterialAmt(new BigDecimal(0));
				// ????????????????????????????????????(???)
				addZxSaProjectPaySettle.setUpMaterialAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getMaterialAmt(), upZxSaProjectPaySettle.getUpMaterialAmt()));
				// ?????????????????????????????????(???)
				addZxSaProjectPaySettle.setMachineAmt(new BigDecimal(0));
				// ????????????????????????????????????(???)
				addZxSaProjectPaySettle.setUpMachineAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getMachineAmt(), upZxSaProjectPaySettle.getUpMachineAmt()));
				// ?????????????????????????????????(???)
				addZxSaProjectPaySettle.setTempAmt(new BigDecimal(0));
				// ????????????????????????????????????(???)
				addZxSaProjectPaySettle.setUpTempAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getTempAmt(), upZxSaProjectPaySettle.getUpTempAmt()));
				// ??????????????????????????????(???)
				addZxSaProjectPaySettle.setFineAmt(new BigDecimal(0));
				// ?????????????????????????????????(???)
				addZxSaProjectPaySettle.setUpFineAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getFineAmt(), upZxSaProjectPaySettle.getUpFineAmt()));
				// ??????????????????????????????(???)
				addZxSaProjectPaySettle.setRecoupAmt(new BigDecimal(0));
				// ?????????????????????????????????(???)
				addZxSaProjectPaySettle.setUpRecoupAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getRecoupAmt(), upZxSaProjectPaySettle.getUpRecoupAmt()));
				// ??????????????????????????????(???)
				addZxSaProjectPaySettle.setOtherAmt(new BigDecimal(0));
				// ?????????????????????????????????(???)
				addZxSaProjectPaySettle.setUpOtherAmt(CalcUtils.calcAdd(upZxSaProjectPaySettle.getOtherAmt(), upZxSaProjectPaySettle.getUpOtherAmt()));
				// ??????????????????
				addZxSaProjectPaySettle.setEditTime(new Date());
				// ????????????
				addZxSaProjectPaySettle.setPeriod(zxSaProjectSettleAudit.getPeriod());
				// ????????????ID
				addZxSaProjectPaySettle.setComID(zxSaProjectSettleAudit.getComID());
				// ????????????
				addZxSaProjectPaySettle.setComName(zxSaProjectSettleAudit.getComName());
				// ??????????????????
				addZxSaProjectPaySettle.setComOrders(zxSaProjectSettleAudit.getComOrders());
				// ????????????????????????????????????(???)
				addZxSaProjectPaySettle.setThisAmtNoTax(new BigDecimal(0));
				// ??????????????????
				addZxSaProjectPaySettle.setThisAmtTax(new BigDecimal(0));
				addZxSaProjectPaySettle.setBillID(zxSaProjectSettleAudit.getId());
				zxSaProjectPaySettleService.saveZxSaProjectPaySettle(addZxSaProjectPaySettle);
			}

			// ??????
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
			// ???????????????ID
			dbZxSaProjectSettleAudit.setBillID(zxSaProjectSettleAudit.getBillID());
			// ???????????????
			dbZxSaProjectSettleAudit.setBillNo(zxSaProjectSettleAudit.getBillNo());
			// ??????ID
			dbZxSaProjectSettleAudit.setOrgID(zxSaProjectSettleAudit.getOrgID());
			// ????????????
			dbZxSaProjectSettleAudit.setOrgName(zxSaProjectSettleAudit.getOrgName());
			// ?????????????????????
			dbZxSaProjectSettleAudit.setPeriodTimeWasted(zxSaProjectSettleAudit.getPeriodTimeWasted());
			
			if (dbZxSaProjectSettleAudit.getPeriodTimeWasted() != null) {
				dbZxSaProjectSettleAudit.setPeriod(DateUtil.format(DateUtil.date(dbZxSaProjectSettleAudit.getPeriodTimeWasted()), "yyyyMM"));
			}
			// ??????ID
			dbZxSaProjectSettleAudit.setContractID(zxSaProjectSettleAudit.getContractID());
			// ????????????
			dbZxSaProjectSettleAudit.setContractNo(zxSaProjectSettleAudit.getContractNo());
			// ????????????
			dbZxSaProjectSettleAudit.setContractName(zxSaProjectSettleAudit.getContractName());
			// ??????ID
			dbZxSaProjectSettleAudit.setSecondID(zxSaProjectSettleAudit.getSecondID());
			// ????????????
			dbZxSaProjectSettleAudit.setSecondName(zxSaProjectSettleAudit.getSecondName());
			// ????????????
			dbZxSaProjectSettleAudit.setBillType(zxSaProjectSettleAudit.getBillType());
			// ????????????????????????
			dbZxSaProjectSettleAudit.setBeginDate(zxSaProjectSettleAudit.getBeginDate());
			// ????????????????????????
			dbZxSaProjectSettleAudit.setEndDate(zxSaProjectSettleAudit.getEndDate());
			// ????????????
			dbZxSaProjectSettleAudit.setContent(zxSaProjectSettleAudit.getContent());
			// ????????????
			dbZxSaProjectSettleAudit.setBusinessDate(zxSaProjectSettleAudit.getBusinessDate());
			// ????????????
			dbZxSaProjectSettleAudit.setReportDate(zxSaProjectSettleAudit.getReportDate());
			// ?????????
			dbZxSaProjectSettleAudit.setReportPerson(zxSaProjectSettleAudit.getReportPerson());
			// ???????????????
			dbZxSaProjectSettleAudit.setReportPersonTel(zxSaProjectSettleAudit.getReportPersonTel());
			// ?????????
			dbZxSaProjectSettleAudit.setCountPerson(zxSaProjectSettleAudit.getCountPerson());
			// ?????????
			dbZxSaProjectSettleAudit.setReCountPerson(zxSaProjectSettleAudit.getReCountPerson());
			// ?????????
			dbZxSaProjectSettleAudit.setFlowBeginPerson(zxSaProjectSettleAudit.getFlowBeginPerson());
			// ??????ID
			dbZxSaProjectSettleAudit.setWorkItemID(zxSaProjectSettleAudit.getWorkItemID());
			// ????????????ID
			dbZxSaProjectSettleAudit.setInstProcessID(zxSaProjectSettleAudit.getInstProcessID());
			// ??????????????????
			dbZxSaProjectSettleAudit.setFlowBeginDate(zxSaProjectSettleAudit.getFlowBeginDate());
			// ??????????????????
			dbZxSaProjectSettleAudit.setFlowEndDate(zxSaProjectSettleAudit.getFlowEndDate());
			// ????????????
			dbZxSaProjectSettleAudit.setAuditStatus(zxSaProjectSettleAudit.getAuditStatus());
			// ??????????????????
			dbZxSaProjectSettleAudit.setEditTime(zxSaProjectSettleAudit.getEditTime());
			// ??????ID
			dbZxSaProjectSettleAudit.setComID(zxSaProjectSettleAudit.getComID());
			// ????????????
			dbZxSaProjectSettleAudit.setComName(zxSaProjectSettleAudit.getComName());
			// ????????????
			dbZxSaProjectSettleAudit.setComOrders(zxSaProjectSettleAudit.getComOrders());
			// ????????????
			dbZxSaProjectSettleAudit.setFlowLock(zxSaProjectSettleAudit.getFlowLock());
			// isBack
			dbZxSaProjectSettleAudit.setIsBack(zxSaProjectSettleAudit.getIsBack());
			// ??????????????????
			dbZxSaProjectSettleAudit.setNotPassNum(zxSaProjectSettleAudit.getNotPassNum());
			// ???????????????
			dbZxSaProjectSettleAudit.setIsReport(zxSaProjectSettleAudit.getIsReport());
			// appInsHistID
			dbZxSaProjectSettleAudit.setAppInsHistID(zxSaProjectSettleAudit.getAppInsHistID());
			// ?????????ID
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
			// ????????????id
			dbZxSaProjectSettleAudit.setFiId(zxSaProjectSettleAudit.getFiId());
			// ????????????
			dbZxSaProjectSettleAudit.setSetDir(zxSaProjectSettleAudit.getSetDir());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAccountUnitId(zxSaProjectSettleAudit.getAccountUnitId());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAccountUnitCode(zxSaProjectSettleAudit.getAccountUnitCode());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAccountUnitName(zxSaProjectSettleAudit.getAccountUnitName());
			// ????????????Id
			dbZxSaProjectSettleAudit.setAssessUnitId(zxSaProjectSettleAudit.getAssessUnitId());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAssessUnitCode(zxSaProjectSettleAudit.getAssessUnitCode());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAssessUnitName(zxSaProjectSettleAudit.getAssessUnitName());
			// ????????????Id
			dbZxSaProjectSettleAudit.setResponseUnitId(zxSaProjectSettleAudit.getResponseUnitId());
			// ??????????????????
			dbZxSaProjectSettleAudit.setResponseUnitCode(zxSaProjectSettleAudit.getResponseUnitCode());
			// ??????????????????
			dbZxSaProjectSettleAudit.setResponseUnitName(zxSaProjectSettleAudit.getResponseUnitName());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAccountDepId(zxSaProjectSettleAudit.getAccountDepId());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAccountDepCode(zxSaProjectSettleAudit.getAccountDepCode());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAccountDepName(zxSaProjectSettleAudit.getAccountDepName());
			// ????????????
			dbZxSaProjectSettleAudit.setIsSign(zxSaProjectSettleAudit.getIsSign());
			// ????????????
			dbZxSaProjectSettleAudit.setBusiTypeId(zxSaProjectSettleAudit.getBusiTypeId());
			// ????????????
			dbZxSaProjectSettleAudit.setProjCode(zxSaProjectSettleAudit.getProjCode());
			// ???????????????
			dbZxSaProjectSettleAudit.setSecondCode(zxSaProjectSettleAudit.getSecondCode());
			// ??????????????????
			dbZxSaProjectSettleAudit.setConfirmDate(zxSaProjectSettleAudit.getConfirmDate());
			// ??????
			dbZxSaProjectSettleAudit.setCurrency(zxSaProjectSettleAudit.getCurrency());
			// ??????
			dbZxSaProjectSettleAudit.setExchangeRate(zxSaProjectSettleAudit.getExchangeRate());
			// ????????????
			dbZxSaProjectSettleAudit.setNumOfSheets(zxSaProjectSettleAudit.getNumOfSheets());
			// ??????
			dbZxSaProjectSettleAudit.setSummary(zxSaProjectSettleAudit.getSummary());
			// ??????
			dbZxSaProjectSettleAudit.setTaxRate(zxSaProjectSettleAudit.getTaxRate());
			// ??????????????????
			dbZxSaProjectSettleAudit.setPayNatureId(zxSaProjectSettleAudit.getPayNatureId());
			// ??????????????????
			dbZxSaProjectSettleAudit.setPayNatureCode(zxSaProjectSettleAudit.getPayNatureCode());
			// ??????????????????
			dbZxSaProjectSettleAudit.setPayNatureName(zxSaProjectSettleAudit.getPayNatureName());
			// ????????????
			dbZxSaProjectSettleAudit.setExpDate(zxSaProjectSettleAudit.getExpDate());
			// ??????????????????
			dbZxSaProjectSettleAudit.setEstPayDate(zxSaProjectSettleAudit.getEstPayDate());
			// notDisplay
			dbZxSaProjectSettleAudit.setNotDisplay(zxSaProjectSettleAudit.getNotDisplay());
			// ????????????
			dbZxSaProjectSettleAudit.setSendDate(zxSaProjectSettleAudit.getSendDate());
			// ????????????
			dbZxSaProjectSettleAudit.setIsSend(zxSaProjectSettleAudit.getIsSend());
			// secondIDCodeBh
			dbZxSaProjectSettleAudit.setSecondIDCodeBh(zxSaProjectSettleAudit.getSecondIDCodeBh());
			// ??????????????????
			dbZxSaProjectSettleAudit.setCwStatus(zxSaProjectSettleAudit.getCwStatus());
			// ????????????????????????
			dbZxSaProjectSettleAudit.setCwStatusRemark(zxSaProjectSettleAudit.getCwStatusRemark());
			// auditWorkitemID
			dbZxSaProjectSettleAudit.setAuditWorkitemID(zxSaProjectSettleAudit.getAuditWorkitemID());
			// auditSys
			dbZxSaProjectSettleAudit.setAuditSys(zxSaProjectSettleAudit.getAuditSys());
			// ????????????
			dbZxSaProjectSettleAudit.setFinishDate(zxSaProjectSettleAudit.getFinishDate());
			// ??????????????????
			dbZxSaProjectSettleAudit.setTcje(zxSaProjectSettleAudit.getTcje());
			// ????????????
			dbZxSaProjectSettleAudit.setTcse(zxSaProjectSettleAudit.getTcse());
			// ???????????????????????????
			dbZxSaProjectSettleAudit.setTchljjsje(zxSaProjectSettleAudit.getTchljjsje());
			// ?????????????????????
			dbZxSaProjectSettleAudit.setBqtchse(zxSaProjectSettleAudit.getBqtchse());
			// ???????????????????????????
			dbZxSaProjectSettleAudit.setBqtchjsje(zxSaProjectSettleAudit.getBqtchjsje());
			// ????????????
			dbZxSaProjectSettleAudit.setTaxCountWay(zxSaProjectSettleAudit.getTaxCountWay());
			// zjgcxm_nm
			dbZxSaProjectSettleAudit.setZJGCXMNM(zxSaProjectSettleAudit.getZJGCXMNM());
			// zjgcxm_xmbh
			dbZxSaProjectSettleAudit.setZJGCXMXMBH(zxSaProjectSettleAudit.getZJGCXMXMBH());
			// ??????
			dbZxSaProjectSettleAudit.setZJGCXMXMMC(zxSaProjectSettleAudit.getZJGCXMXMMC());
			// ??????????????????????????????
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
			// ?????????????????????
			dbZxSaProjectSettleAudit.setIsFirst(zxSaProjectSettleAudit.getIsFirst());
			// upWorkItemID
			dbZxSaProjectSettleAudit.setUpWorkItemID(zxSaProjectSettleAudit.getUpWorkItemID());
			// oaOrgID
			dbZxSaProjectSettleAudit.setOaOrgID(zxSaProjectSettleAudit.getOaOrgID());
			// ??????????????????(??????)
			dbZxSaProjectSettleAudit.setContractAmt(zxSaProjectSettleAudit.getContractAmt());
			// ???????????????????????????(??????)
			dbZxSaProjectSettleAudit.setChangeAmt(zxSaProjectSettleAudit.getChangeAmt());
			// ?????????????????????
			dbZxSaProjectSettleAudit.setIsFished(zxSaProjectSettleAudit.getIsFished());
			// ????????????
			dbZxSaProjectSettleAudit.setIsDeduct(zxSaProjectSettleAudit.getIsDeduct());
			// ?????????????????????
			dbZxSaProjectSettleAudit.setUseCount(zxSaProjectSettleAudit.getUseCount());
			// ??????????????????
			dbZxSaProjectSettleAudit.setIsMaxPeriod(zxSaProjectSettleAudit.getIsMaxPeriod());
			// ???????????????
			dbZxSaProjectSettleAudit.setSignedNo(zxSaProjectSettleAudit.getSignedNo());
			// ??????????????????????????????
			dbZxSaProjectSettleAudit.setStartDate(zxSaProjectSettleAudit.getStartDate());
			// ???????????????(??????????????????)
			dbZxSaProjectSettleAudit.setAttachmentInfo(zxSaProjectSettleAudit.getAttachmentInfo());
			// ??????????????????
			dbZxSaProjectSettleAudit.setAppraisal(zxSaProjectSettleAudit.getAppraisal());
			// ?????????
			dbZxSaProjectSettleAudit.setSerialNumber(zxSaProjectSettleAudit.getSerialNumber());
			// ???????????????????????????
			dbZxSaProjectSettleAudit.setInitSerialNumber(zxSaProjectSettleAudit.getInitSerialNumber());
			// ??????
			dbZxSaProjectSettleAudit.setSort(zxSaProjectSettleAudit.getSort());
			// ??????
			dbZxSaProjectSettleAudit.setRemark(zxSaProjectSettleAudit.getRemark());
			// ??????
			dbZxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);

			// ---------------????????????----------------
			// ??????????????????(???)
			dbZxSaProjectSettleAudit.setThisAmt(zxSaProjectSettleAudit.getThisAmt());
			// ??????????????????(???)
			dbZxSaProjectSettleAudit.setTotalAmt(zxSaProjectSettleAudit.getTotalAmt());
			// ???????????????????????????(???)
			dbZxSaProjectSettleAudit.setThisAmtNoTax(zxSaProjectSettleAudit.getThisAmtNoTax());
			// ??????????????????(???)
			dbZxSaProjectSettleAudit.setThisAmtTax(zxSaProjectSettleAudit.getThisAmtTax());

			if (projectSettleAuditItemList != null && projectSettleAuditItemList.size() > 0) {
				for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : projectSettleAuditItemList) {
					if (StrUtil.equals("100700", zxSaProjectSettleAuditItem.getStatisticsNo())) {
						// ?????????????????????(???)
						dbZxSaProjectSettleAudit.setThisPayAmt(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
						// ?????????????????????(???)
						dbZxSaProjectSettleAudit.setTotalPayAmt(new BigDecimal(zxSaProjectSettleAuditItem.getTotalAmt()));
						break;
					}
				}
			}
			flag = zxSaProjectSettleAuditMapper.updateByPrimaryKey(dbZxSaProjectSettleAudit);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// ??????
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
			
			// ??????---?????????????????????
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
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			JSONArray jsonArray = new JSONArray();
			for (ZxSaProjectSettleAudit zxSaProjectSettleAudit : zxSaProjectSettleAuditList) {
				// ??????
				ZxErpFile delZxErpFile = new ZxErpFile();
				delZxErpFile.setOtherId(zxSaProjectSettleAudit.getId());
				delZxErpFile.setOtherType("0");
				zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);

				// ????????????????????????????????????????????????????????????????????????????????????????????????????????????(????????????id??????)
				ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
				zxGcsgCtContract.setCtContractId(zxSaProjectSettleAudit.getContractID());
				zxGcsgCtContract.setSettleType("???????????????");
				zxGcsgCtContract.setModifyUserInfo(userKey, realName);
				zxGcsgCtContractMapper.updateSettleTypeByPrimaryKey(zxGcsgCtContract);
				
				// ??????
				ZxSaProjectSettleAuditItem delZxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
				delZxSaProjectSettleAuditItem.setProjectSettleAuditId(zxSaProjectSettleAudit.getId());
				zxSaProjectSettleAuditItemServiceImpl.deleteAllZxSaProjectSettleAuditItem(delZxSaProjectSettleAuditItem);
				
				// ??????
				ZxSaProjectWorkSettle delWorkSettle = new ZxSaProjectWorkSettle();
				delWorkSettle.setBillID(zxSaProjectSettleAudit.getId());
				List<ZxSaProjectWorkSettle> delWorkSettleList = zxSaProjectWorkSettleMapper.selectByZxSaProjectWorkSettleList(delWorkSettle);
				if (delWorkSettleList != null && delWorkSettleList.size() > 0) {
					zxSaProjectWorkSettleService.batchDeleteUpdateZxSaProjectWorkSettle(delWorkSettleList);
				}
				
				// ?????????
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

			// ????????????????????????
            String url = Apih5Properties.getWebUrl() + "batchDeleteFlow";
            if(jsonArray.size() > 0) {
                HttpUtil.sendPostToken(url, jsonArray.toString(), TokenUtils.getToken(request));
            }
			
			return repEntity.ok("sys.data.delete", zxSaProjectSettleAuditList);
		}
	}

	// ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

	@Override
	public ResponseEntity getZxSaProjectSettleAuditProjectList(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		if (zxSaProjectSettleAudit == null) {
			zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		}
		// ????????????
		PageHelper.startPage(zxSaProjectSettleAudit.getPage(), zxSaProjectSettleAudit.getLimit());
		// ????????????
		List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper
				.getZxSaProjectSettleAuditProjectList(zxSaProjectSettleAudit);
		// ??????????????????
		PageInfo<ZxSaProjectSettleAudit> p = new PageInfo<>(zxSaProjectSettleAuditList);

		return repEntity.okList(zxSaProjectSettleAuditList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxSaProjectSettleAuditContractNoList(ZxSaProjectSettleAudit zxSaProjectSettleAudit) {
		if (zxSaProjectSettleAudit == null) {
			zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		}

		if (StrUtil.isEmpty(zxSaProjectSettleAudit.getOrgID())) {
			return repEntity.layerMessage("no", "orgID???????????????");
		}

		// ????????????
		PageHelper.startPage(zxSaProjectSettleAudit.getPage(), zxSaProjectSettleAudit.getLimit());
		// ????????????
		List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = zxSaProjectSettleAuditMapper
				.getZxSaProjectSettleAuditContractNoList(zxSaProjectSettleAudit);
		// ??????????????????
		PageInfo<ZxSaProjectSettleAudit> p = new PageInfo<>(zxSaProjectSettleAuditList);
		// ???????????????????????????????????????(?????????--???????????????)
		for (ZxSaProjectSettleAudit projectSettleAudit : zxSaProjectSettleAuditList) {
			// ?????????????????????????????????
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
			// ??????
			dbZxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);
			// ??????ID
			dbZxSaProjectSettleAudit.setWorkId(zxSaProjectSettleAudit.getWorkId());
			// ????????????
			dbZxSaProjectSettleAudit.setApih5FlowStatus("1");

			flag = zxSaProjectSettleAuditMapper.updateByPrimaryKey(dbZxSaProjectSettleAudit);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// ??????
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
			// ??????
			dbZxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);
			// ??????ID
			dbZxSaProjectSettleAudit.setWorkId(zxSaProjectSettleAudit.getWorkId());
			if (StrUtil.isNotEmpty(zxSaProjectSettleAudit.getApih5FlowStatus())) {
				// ????????????
				dbZxSaProjectSettleAudit.setApih5FlowStatus(zxSaProjectSettleAudit.getApih5FlowStatus());
			}
			// ???????????????
			if (StrUtil.equals("opinionField1", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField1(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField2(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField2()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField3", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField3(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField3()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField4", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField4(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField4()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField5", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField5(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField5()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField6", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField6(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField6()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField7", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField7(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField7()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField8", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField8(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField8()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField9", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField9(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField9()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField10", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField10(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField10()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField11", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField11(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField11()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField12", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField12(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField12()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField13", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField13(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField13()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField14", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField14(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField14()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField15", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField15(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField15()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField16", zxSaProjectSettleAudit.getOpinionField(), true)) {
				dbZxSaProjectSettleAudit.setOpinionField16(zxSaProjectSettleAudit.getOpinionContent(realName, dbZxSaProjectSettleAudit.getOpinionField16()));
			}
			
			flag = zxSaProjectSettleAuditMapper.updateByPrimaryKey(dbZxSaProjectSettleAudit);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			if (StrUtil.equals("2", dbZxSaProjectSettleAudit.getApih5FlowStatus()) && StrUtil.equals("1", dbZxSaProjectSettleAudit.getBillType())) {
				// ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????(????????????id??????)
				ZxGcsgCtContract zxGcsgCtContract = new ZxGcsgCtContract();
				zxGcsgCtContract.setCtContractId(dbZxSaProjectSettleAudit.getContractID());
				zxGcsgCtContract.setSettleType("???????????????");
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
		// ????????????
		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectSettleAudit.getId());
		// ????????????
		if (dbZxSaProjectSettleAudit != null) {
			// ????????????
			String period = ""; // ????????????
			if (dbZxSaProjectSettleAudit.getBeginDate() != null) {
				period = DateUtil.format(dbZxSaProjectSettleAudit.getBeginDate(), "yyyy-MM-dd");
				if (dbZxSaProjectSettleAudit.getEndDate() != null) {
					period = period + "???" + DateUtil.format(dbZxSaProjectSettleAudit.getEndDate(), "yyyy-MM-dd");
				} else {
					period = period + "???--";
				}
			} else {
				period = "--";
				if (dbZxSaProjectSettleAudit.getEndDate() != null) {
					period = period + "???" + DateUtil.format(dbZxSaProjectSettleAudit.getEndDate(), "yyyy-MM-dd");
				} else {
					period = period + "???--";
				}
			}
			dbZxSaProjectSettleAudit.setSecondCode(period);
			
			// ?????????????????????????????????
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
			dbZxSaProjectSettleAudit.setRemark(dbZxSaProjectSettleAudit.getReportDate() == null ? "" : DateUtil.format(dbZxSaProjectSettleAudit.getReportDate(), "yyyy???MM???dd???"));
			return dbZxSaProjectSettleAudit;
		} else {
			return zxSaProjectSettleAudit;
		}
	}
}
