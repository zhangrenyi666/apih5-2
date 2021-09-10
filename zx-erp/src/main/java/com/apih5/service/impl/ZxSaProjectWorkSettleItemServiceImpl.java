package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSaProjectPaySettleItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditMapper;
import com.apih5.mybatis.dao.ZxSaProjectWorkSettleItemMapper;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApply;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettleItem;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAudit;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettle;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettleItem;
import com.apih5.service.ZxGcsgCtContrApplyService;
import com.apih5.service.ZxSaProjectWorkSettleItemService;
import com.apih5.service.ZxSaProjectWorkSettleService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSaProjectWorkSettleItemService")
public class ZxSaProjectWorkSettleItemServiceImpl implements ZxSaProjectWorkSettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectWorkSettleItemMapper zxSaProjectWorkSettleItemMapper;
    
    @Autowired(required = true)
    private ZxSaProjectWorkSettleService zxSaProjectWorkSettleService;
    
    @Autowired(required = true)
    private ZxSaProjectSettleAuditMapper zxSaProjectSettleAuditMapper;
    
    @Autowired(required = true)
    private ZxSaProjectPaySettleItemMapper zxSaProjectPaySettleItemMapper;
    
    @Autowired(required = true)
    private ZxGcsgCtContrApplyService zxGcsgCtContrApplyService;
    
    @ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
	private Config publicConfig;
    
    @Override
    public ResponseEntity getZxSaProjectWorkSettleItemListByCondition(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        if (zxSaProjectWorkSettleItem == null) {
            zxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
        }
        // 分页查询
        PageHelper.startPage(zxSaProjectWorkSettleItem.getPage(),zxSaProjectWorkSettleItem.getLimit());
        // 获取数据
        List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList = zxSaProjectWorkSettleItemMapper.selectByZxSaProjectWorkSettleItemList(zxSaProjectWorkSettleItem);
        // 得到分页信息
        PageInfo<ZxSaProjectWorkSettleItem> p = new PageInfo<>(zxSaProjectWorkSettleItemList);

        for (ZxSaProjectWorkSettleItem workSettleItem : zxSaProjectWorkSettleItemList) {
        	// 上期末累计结算数量 = 累计结算数量 - 本期结算数量
        	workSettleItem.setUpAllQty(CalcUtils.calcSubtract(workSettleItem.getAllQty(), workSettleItem.getThisQty()));
        	// 上期末累计结算含税金额 = 累计结算含税金额 - 本期结算含税金额 
        	workSettleItem.setUpAllTotalAmt(CalcUtils.calcSubtract(workSettleItem.getAllTotalAmt(), workSettleItem.getThisTotalAmt()));
		}
        
        return repEntity.okList(zxSaProjectWorkSettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaProjectWorkSettleItemDetail(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        if (zxSaProjectWorkSettleItem == null) {
            zxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
        }
        // 获取数据
        ZxSaProjectWorkSettleItem dbZxSaProjectWorkSettleItem = zxSaProjectWorkSettleItemMapper.selectByPrimaryKey(zxSaProjectWorkSettleItem.getProjectWorkSettleItemId());
        // 数据存在
        if (dbZxSaProjectWorkSettleItem != null) {
            return repEntity.ok(dbZxSaProjectWorkSettleItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaProjectWorkSettleItem(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectWorkSettleItem.setProjectWorkSettleItemId(UuidUtil.generate());
        zxSaProjectWorkSettleItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaProjectWorkSettleItemMapper.insert(zxSaProjectWorkSettleItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaProjectWorkSettleItem);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity updateZxSaProjectWorkSettleItem(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaProjectWorkSettleItem dbZxSaProjectWorkSettleItem = zxSaProjectWorkSettleItemMapper.selectByPrimaryKey(zxSaProjectWorkSettleItem.getProjectWorkSettleItemId());
        if (dbZxSaProjectWorkSettleItem != null && StrUtil.isNotEmpty(dbZxSaProjectWorkSettleItem.getProjectWorkSettleItemId())) {
        	// 上期末累计结算数量 = 累计结算数量 - 本期结算数量
        	BigDecimal upAllQty = CalcUtils.calcSubtract(dbZxSaProjectWorkSettleItem.getAllQty(), dbZxSaProjectWorkSettleItem.getThisQty());
        	// 查询原合同是否做过补充协议
        	ZxGcsgCtContrApply zxGcsgCtContrApply = new ZxGcsgCtContrApply();
        	zxGcsgCtContrApply.setCtContractId(dbZxSaProjectWorkSettleItem.getContractID());
        	List<ZxGcsgCtContrApply> zxGcsgCtContrApplyList = (List<ZxGcsgCtContrApply>) zxGcsgCtContrApplyService.getZxGcsgCtContrApplyListByCondition(zxGcsgCtContrApply).getData();
        	if (zxGcsgCtContrApplyList == null || zxGcsgCtContrApplyList.size() == 0) {
        		// 本期结算数量，根据清单数据情况 分别不能大于a，合同数量 -至上期末结算数量 b，变更后合同数量 -至上期末结算数量
            	if (CalcUtils.compareToZero(CalcUtils.calcSubtract(zxSaProjectWorkSettleItem.getThisQty(), CalcUtils.calcSubtract(dbZxSaProjectWorkSettleItem.getContractQty(), upAllQty))) > 0) {
    				return repEntity.layerMessage("no", "本期结算数量不能大于合同数量 -至上期末结算数量,请重新修改！");
    			}
			} else {
				if (CalcUtils.compareToZero(CalcUtils.calcSubtract(zxSaProjectWorkSettleItem.getThisQty(), CalcUtils.calcSubtract(dbZxSaProjectWorkSettleItem.getChangeQty(), upAllQty))) > 0) {
					return repEntity.layerMessage("no", "本期结算数量不能大于变更后合同数量 -至上期末结算数量,请重新修改！");
				}
			}
        	
        	// 清单表主键
           dbZxSaProjectWorkSettleItem.setProjectWorkSettleId(zxSaProjectWorkSettleItem.getProjectWorkSettleId());
           // 合同ID
           dbZxSaProjectWorkSettleItem.setContractID(zxSaProjectWorkSettleItem.getContractID());
           // 序号
           dbZxSaProjectWorkSettleItem.setOrderNum(zxSaProjectWorkSettleItem.getOrderNum());
           // 签认单ID
           dbZxSaProjectWorkSettleItem.setSignedOrderID(zxSaProjectWorkSettleItem.getSignedOrderID());
           // 签认单编号
           dbZxSaProjectWorkSettleItem.setSignedNo(zxSaProjectWorkSettleItem.getSignedNo());
           // 签认单明细ID
           dbZxSaProjectWorkSettleItem.setSignedOrderItemID(zxSaProjectWorkSettleItem.getSignedOrderItemID());
           // 细目ID
           dbZxSaProjectWorkSettleItem.setWorkID(zxSaProjectWorkSettleItem.getWorkID());
           // 细目编号
           dbZxSaProjectWorkSettleItem.setWorkNo(zxSaProjectWorkSettleItem.getWorkNo());
           // 细目名称
           dbZxSaProjectWorkSettleItem.setWorkName(zxSaProjectWorkSettleItem.getWorkName());
           // 父节点
           dbZxSaProjectWorkSettleItem.setParentID(zxSaProjectWorkSettleItem.getParentID());
           // 树节点
           dbZxSaProjectWorkSettleItem.setTreeNode(zxSaProjectWorkSettleItem.getTreeNode());
           // 是否叶子节点
           dbZxSaProjectWorkSettleItem.setIsLeaf(zxSaProjectWorkSettleItem.getIsLeaf());
           // 单位
           dbZxSaProjectWorkSettleItem.setUnit(zxSaProjectWorkSettleItem.getUnit());
           // 含税单价(元)
           dbZxSaProjectWorkSettleItem.setPrice(zxSaProjectWorkSettleItem.getPrice());
           // 合同数量
           dbZxSaProjectWorkSettleItem.setContractQty(zxSaProjectWorkSettleItem.getContractQty());
           // 含税合同金额(元)
           dbZxSaProjectWorkSettleItem.setContractAmt(zxSaProjectWorkSettleItem.getContractAmt());
           // 变更后数量
           dbZxSaProjectWorkSettleItem.setChangeQty(zxSaProjectWorkSettleItem.getChangeQty());
           // 变更后含税金额
           dbZxSaProjectWorkSettleItem.setChangeAmt(zxSaProjectWorkSettleItem.getChangeAmt());
           // 累计结算合同数量
           dbZxSaProjectWorkSettleItem.setAllQty(CalcUtils.calcAdd(zxSaProjectWorkSettleItem.getThisQty(), CalcUtils.calcSubtract(dbZxSaProjectWorkSettleItem.getAllQty(), dbZxSaProjectWorkSettleItem.getThisQty())));
           // 本期结算合同数量
           dbZxSaProjectWorkSettleItem.setThisQty(zxSaProjectWorkSettleItem.getThisQty());
           // 累计结算变更数量
           dbZxSaProjectWorkSettleItem.setAllChangeQty(CalcUtils.calcAdd(zxSaProjectWorkSettleItem.getThisChangeQty(), CalcUtils.calcSubtract(dbZxSaProjectWorkSettleItem.getAllChangeQty(), dbZxSaProjectWorkSettleItem.getThisChangeQty())));
           // 本期结算变更数量
           dbZxSaProjectWorkSettleItem.setThisChangeQty(zxSaProjectWorkSettleItem.getThisChangeQty());
           // 累计结算数量小计
           dbZxSaProjectWorkSettleItem.setAllTotalQty(CalcUtils.calcAdd(zxSaProjectWorkSettleItem.getThisTotalQty(), CalcUtils.calcSubtract(dbZxSaProjectWorkSettleItem.getAllTotalQty(), dbZxSaProjectWorkSettleItem.getThisTotalQty())));
           // 本期结算数量小计
           dbZxSaProjectWorkSettleItem.setThisTotalQty(zxSaProjectWorkSettleItem.getThisTotalQty());
           // 累计结算含税金额(元)
           dbZxSaProjectWorkSettleItem.setAllTotalAmt(CalcUtils.calcAdd(zxSaProjectWorkSettleItem.getThisTotalAmt(), CalcUtils.calcSubtract(dbZxSaProjectWorkSettleItem.getAllTotalAmt(), dbZxSaProjectWorkSettleItem.getThisTotalAmt())));
           // 本期结算含税金额(元)
           dbZxSaProjectWorkSettleItem.setThisTotalAmt(zxSaProjectWorkSettleItem.getThisTotalAmt());
           // 最后编辑时间
           dbZxSaProjectWorkSettleItem.setEditTime(zxSaProjectWorkSettleItem.getEditTime());
           // 结算期次
           dbZxSaProjectWorkSettleItem.setPeriod(zxSaProjectWorkSettleItem.getPeriod());
           // 所属公司ID
           dbZxSaProjectWorkSettleItem.setComID(zxSaProjectWorkSettleItem.getComID());
           // 所属公司
           dbZxSaProjectWorkSettleItem.setComName(zxSaProjectWorkSettleItem.getComName());
           // 所属公司排序
           dbZxSaProjectWorkSettleItem.setComOrders(zxSaProjectWorkSettleItem.getComOrders());
           // 变更后含税单价
           dbZxSaProjectWorkSettleItem.setChangePrice(zxSaProjectWorkSettleItem.getChangePrice());
           // 税率
           dbZxSaProjectWorkSettleItem.setTaxRate(zxSaProjectWorkSettleItem.getTaxRate());
           // 计算式
           dbZxSaProjectWorkSettleItem.setPlanning(zxSaProjectWorkSettleItem.getPlanning());
           // 图号
           dbZxSaProjectWorkSettleItem.setMapNum(zxSaProjectWorkSettleItem.getMapNum());
           // 备注
           dbZxSaProjectWorkSettleItem.setRemark(zxSaProjectWorkSettleItem.getRemark());
           // 排序
           dbZxSaProjectWorkSettleItem.setSort(zxSaProjectWorkSettleItem.getSort());
           // 共通
           dbZxSaProjectWorkSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectWorkSettleItemMapper.updateByPrimaryKey(dbZxSaProjectWorkSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改清单结算（本期清单结算含税金额(元)、本期清单结算不含税金额(元)、本期清单结算税额(元)、累计清单结算含税金额(元)）
        	
        	// 查询以上各金额
        	ZxSaProjectWorkSettleItem settleItem = new ZxSaProjectWorkSettleItem();
        	settleItem.setProjectWorkSettleId(dbZxSaProjectWorkSettleItem.getProjectWorkSettleId());
        	settleItem.setContractID(dbZxSaProjectWorkSettleItem.getContractID());
        	settleItem.setPeriod(dbZxSaProjectWorkSettleItem.getPeriod());
        	ZxSaProjectWorkSettleItem dbSettleItem = zxSaProjectWorkSettleItemMapper.selectAmtInfo(settleItem);
        	
        	// 查询结算清单主表信息
        	ZxSaProjectWorkSettle workSettle = new ZxSaProjectWorkSettle();
        	workSettle.setProjectWorkSettleId(dbZxSaProjectWorkSettleItem.getProjectWorkSettleId());
        	ZxSaProjectWorkSettle dbWorkSettle = (ZxSaProjectWorkSettle) zxSaProjectWorkSettleService.getZxSaProjectWorkSettleDetail(workSettle).getData();
        	if (dbWorkSettle != null && StrUtil.isNotEmpty(dbWorkSettle.getProjectWorkSettleId())) {
				if (dbSettleItem != null) {
					dbWorkSettle.setThisAmt(dbSettleItem.getThisAmt());
					dbWorkSettle.setThisAmtNoTax(dbSettleItem.getThisAmtNoTax());
					dbWorkSettle.setThisAmtTax(CalcUtils.calcSubtract(dbSettleItem.getThisAmt(), dbSettleItem.getThisAmtNoTax()));
					dbWorkSettle.setTotalAmt(dbSettleItem.getTotalAmt());
					zxSaProjectWorkSettleService.updateZxSaProjectWorkSettle(dbWorkSettle);
				}
			}
        	
            return repEntity.ok("sys.data.update",zxSaProjectWorkSettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectWorkSettleItem(List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectWorkSettleItemList != null && zxSaProjectWorkSettleItemList.size() > 0) {
           ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
           zxSaProjectWorkSettleItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectWorkSettleItemMapper.batchDeleteUpdateZxSaProjectWorkSettleItem(zxSaProjectWorkSettleItemList, zxSaProjectWorkSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaProjectWorkSettleItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity deleteAllZxSaProjectWorkSettleItem(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
    	if (zxSaProjectWorkSettleItem == null || StrUtil.isEmpty(zxSaProjectWorkSettleItem.getProjectWorkSettleId())) {
			return repEntity.layerMessage("no", "projectWorkSettleId不能为空！");
		}
    	
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        zxSaProjectWorkSettleItem.setModifyUserInfo(userKey, realName);
        flag = zxSaProjectWorkSettleItemMapper.deleteAllZxSaProjectWorkSettleItem(zxSaProjectWorkSettleItem);
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("删除成功！");
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxSaProjectSettleAuditYgzInfo(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
		if (zxSaProjectWorkSettleItem == null || StrUtil.isEmpty(zxSaProjectWorkSettleItem.getSettleauditId())) {
			return repEntity.layerMessage("no", "结算单主键ID不能为空！");
		}
		
		// 1、查询结算单信息
		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectWorkSettleItem.getSettleauditId());
		if (dbZxSaProjectSettleAudit != null && StrUtil.isNotEmpty(dbZxSaProjectSettleAudit.getId())) {
			List<ZxSaProjectWorkSettleItem> returnList = new ArrayList<>();
			
			BigDecimal zero = new BigDecimal(0);
			
			// 2、查询清单信息
			ZxSaProjectWorkSettleItem selectWorkSettleItem = new ZxSaProjectWorkSettleItem();
			selectWorkSettleItem.setContractID(dbZxSaProjectSettleAudit.getContractID());
			selectWorkSettleItem.setPeriod(dbZxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectWorkSettleItem> workSettleItemList = zxSaProjectWorkSettleItemMapper.getYgzWorkSettleItemList(selectWorkSettleItem);
			if (workSettleItemList != null && workSettleItemList.size() > 0) {
				// 累计原合同含税合同金额（元）、变更后含税合同金额（元）、本期结算含税金额（元）、至上期末累计结算含税金额（元）、至本期末累计结算含税金额（元）
				BigDecimal totalContractAmt = new BigDecimal(0); // 原合同含税合同金额（元）
				BigDecimal totalContractAmtNoTax = new BigDecimal(0); // 原合同不含税合同金额（元）
				BigDecimal totalContractAmtTax = new BigDecimal(0); // 原合同合同税额（元）
				BigDecimal totalChangeAmt = new BigDecimal(0); // 变更后含税合同金额（元）
				BigDecimal totalChangeAmtNoTax = new BigDecimal(0); // 变更后不含税合同金额（元）
				BigDecimal totalChangeAmtTax = new BigDecimal(0); // 变更后合同税额（元）
				BigDecimal totalThisTotalAmt = new BigDecimal(0); // 本期结算含税金额（元）
				BigDecimal totalThisTotalAmtNoTax = new BigDecimal(0); // 本期结算不含税金额（元）
				BigDecimal totalThisTotalAmtTax = new BigDecimal(0); // 本期结算税额（元）
				BigDecimal totalUpTotalAmt = new BigDecimal(0); // 至上期末累计结算含税金额（元）
				BigDecimal totalUpTotalAmtNoTax = new BigDecimal(0); // 至上期末累计结算不含税金额（元）
				BigDecimal totalUpTotalAmtTax = new BigDecimal(0); // 至上期末累计结算税额（元）
				BigDecimal totalAllTotalAmt = new BigDecimal(0); // 至本期末累计结算含税金额（元）
				BigDecimal totalAllTotalAmtNoTax = new BigDecimal(0); // 至本期末累计结算不含税金额（元）
				BigDecimal totalAllTotalAmtTax = new BigDecimal(0); // 至本期末累计结算税额（元）
				for (ZxSaProjectWorkSettleItem settleItem : workSettleItemList) {
					settleItem.setPrice(settleItem.getPrice() == null ? zero : settleItem.getPrice());
					settleItem.setContractQty(settleItem.getContractQty() == null ? zero : settleItem.getContractQty());
					settleItem.setContractAmt(settleItem.getContractAmt() == null ? zero : settleItem.getContractAmt());
					settleItem.setChangeQty(settleItem.getChangeQty() == null ? zero : settleItem.getChangeQty());
					settleItem.setChangeAmt(settleItem.getChangeAmt() == null ? zero : settleItem.getChangeAmt());
					settleItem.setThisQty(settleItem.getThisQty() == null ? zero : settleItem.getThisQty());
					settleItem.setThisAmt(settleItem.getThisAmt() == null ? zero : settleItem.getThisAmt());
					settleItem.setThisAmtSubTotal(settleItem.getThisQty());
					settleItem.setOrderNum(settleItem.getOrderNum() == 0 ? 0 : settleItem.getOrderNum());
					settleItem.setThisTotalAmt(settleItem.getThisTotalAmt() == null ? zero : settleItem.getThisTotalAmt());
					settleItem.setUpQty(settleItem.getUpQty() == null ? zero : settleItem.getUpQty());
					settleItem.setUpAmtSubTotal(settleItem.getUpQty());
					settleItem.setUpTotalAmt(settleItem.getUpTotalAmt() == null ? zero : settleItem.getUpTotalAmt());
					settleItem.setAllQty(settleItem.getAllQty() == null ? zero : settleItem.getAllQty());
					settleItem.setAllAmtSubTotal(settleItem.getAllQty());
					settleItem.setAllTotalAmt(settleItem.getAllTotalAmt() == null ? zero : settleItem.getAllTotalAmt());
					returnList.add(settleItem);
					
					totalContractAmt = CalcUtils.calcAdd(totalContractAmt, settleItem.getContractAmt());
					totalContractAmtNoTax = CalcUtils.calcAdd(totalContractAmtNoTax, settleItem.getContractAmtNoTax());
					totalContractAmtTax = CalcUtils.calcAdd(totalContractAmtTax, settleItem.getContractAmtTax());
					totalChangeAmt = CalcUtils.calcAdd(totalChangeAmt, settleItem.getChangeAmt());
					totalChangeAmtNoTax = CalcUtils.calcAdd(totalChangeAmtNoTax, settleItem.getChangeAmtNoTax());
					totalChangeAmtTax = CalcUtils.calcAdd(totalChangeAmtTax, settleItem.getChangeAmtTax());
					totalThisTotalAmt = CalcUtils.calcAdd(totalThisTotalAmt, settleItem.getThisTotalAmt());
					totalThisTotalAmtNoTax = CalcUtils.calcAdd(totalThisTotalAmtNoTax, settleItem.getThisTotalAmtNoTax());
					totalThisTotalAmtTax = CalcUtils.calcAdd(totalThisTotalAmtTax, settleItem.getThisTotalAmtTax());
					totalUpTotalAmt = CalcUtils.calcAdd(totalUpTotalAmt, settleItem.getUpTotalAmt());
					totalUpTotalAmtNoTax = CalcUtils.calcAdd(totalUpTotalAmtNoTax, settleItem.getUpTotalAmtNoTax());
					totalUpTotalAmtTax = CalcUtils.calcAdd(totalUpTotalAmtTax, settleItem.getUpTotalAmtTax());
					totalAllTotalAmt = CalcUtils.calcAdd(totalAllTotalAmt, settleItem.getAllTotalAmt());
					totalAllTotalAmtNoTax = CalcUtils.calcAdd(totalAllTotalAmtNoTax, settleItem.getAllTotalAmtNoTax());
					totalAllTotalAmtTax = CalcUtils.calcAdd(totalAllTotalAmtTax, settleItem.getAllTotalAmtTax());
				}
				// 增加清单小计行
				ZxSaProjectWorkSettleItem workTotalItem = new ZxSaProjectWorkSettleItem();
				workTotalItem.setProjectWorkSettleItemId(UuidUtil.generate());
				workTotalItem.setWorkNo("A");
				workTotalItem.setWorkName("清单小计");
				workTotalItem.setUnit("元");
				workTotalItem.setPrice(null);
				workTotalItem.setContractQty(null);
				workTotalItem.setContractAmt(totalContractAmt);
				workTotalItem.setContractAmtNoTax(totalContractAmtNoTax);
				workTotalItem.setContractAmtTax(totalContractAmtTax);
				workTotalItem.setChangeQty(null);
				workTotalItem.setChangeAmt(totalChangeAmt);
				workTotalItem.setChangeAmtNoTax(totalChangeAmtNoTax);
				workTotalItem.setChangeAmtTax(totalChangeAmtTax);
				workTotalItem.setThisQty(null);
				workTotalItem.setThisAmt(totalThisTotalAmt);
				workTotalItem.setThisAmtNoTax(totalThisTotalAmtNoTax);
				workTotalItem.setThisAmtTax(totalThisTotalAmtTax);
				workTotalItem.setOrderNum(-1001);
				workTotalItem.setThisTotalAmt(totalThisTotalAmt);
				workTotalItem.setThisTotalAmtNoTax(totalThisTotalAmtNoTax);
				workTotalItem.setThisTotalAmtTax(totalThisTotalAmtTax);
				workTotalItem.setUpQty(null);
				workTotalItem.setUpTotalAmt(totalUpTotalAmt);
				workTotalItem.setUpTotalAmtNoTax(totalUpTotalAmtNoTax);
				workTotalItem.setUpTotalAmtTax(totalUpTotalAmtTax);
				workTotalItem.setAllQty(null);
				workTotalItem.setAllTotalAmt(totalAllTotalAmt);
				workTotalItem.setAllTotalAmtNoTax(totalAllTotalAmtNoTax);
				workTotalItem.setAllTotalAmtTax(totalAllTotalAmtTax);
				workTotalItem.setRemark("");
				returnList.add(workTotalItem);
			} else {
				// 增加清单小计行
				ZxSaProjectWorkSettleItem workTotalItem = new ZxSaProjectWorkSettleItem();
				workTotalItem.setProjectWorkSettleItemId(UuidUtil.generate());
				workTotalItem.setWorkNo("A");
				workTotalItem.setWorkName("清单小计");
				workTotalItem.setUnit("元");
				workTotalItem.setPrice(null);
				workTotalItem.setContractQty(null);
				workTotalItem.setContractAmt(zero);
				workTotalItem.setContractAmtNoTax(zero);
				workTotalItem.setContractAmtTax(zero);
				workTotalItem.setChangeQty(null);
				workTotalItem.setChangeAmt(zero);
				workTotalItem.setChangeAmtNoTax(zero);
				workTotalItem.setChangeAmtTax(zero);
				workTotalItem.setThisQty(null);
				workTotalItem.setThisAmt(zero);
				workTotalItem.setThisAmtNoTax(zero);
				workTotalItem.setThisAmtTax(zero);
				workTotalItem.setOrderNum(-1001);
				workTotalItem.setThisTotalAmt(zero);
				workTotalItem.setThisTotalAmtNoTax(zero);
				workTotalItem.setThisTotalAmtTax(zero);
				workTotalItem.setUpQty(null);
				workTotalItem.setUpTotalAmt(zero);
				workTotalItem.setUpTotalAmtNoTax(zero);
				workTotalItem.setUpTotalAmtTax(zero);
				workTotalItem.setAllQty(null);
				workTotalItem.setAllTotalAmt(zero);
				workTotalItem.setAllTotalAmtNoTax(zero);
				workTotalItem.setAllTotalAmtTax(zero);
				workTotalItem.setRemark("");
				returnList.add(workTotalItem);
			}
			
			// 3、查询本期次之前已做支付项
			ZxSaProjectPaySettleItem selectPaySettleItem = new ZxSaProjectPaySettleItem();
			selectPaySettleItem.setContractID(dbZxSaProjectSettleAudit.getContractID());
			selectPaySettleItem.setPeriod(dbZxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectPaySettleItem> paySettleItemList = zxSaProjectPaySettleItemMapper.selectAlreadyDoneItemList(selectPaySettleItem);
			
			// 累计本期结算含税金额（元）、至上期末累计结算含税金额（元）、至本期末累计结算含税金额（元）
			BigDecimal totalThisTotalAmt = new BigDecimal(0); // 累计本期结算含税金额（元）
			BigDecimal totalThisTotalAmtNoTax = new BigDecimal(0); // 累计本期结算不含税金额（元）
			BigDecimal totalThisTotalAmtTax = new BigDecimal(0); // 累计本期结算税额（元）
			BigDecimal totalUpTotalAmt = new BigDecimal(0); // 至上期末累计结算含税金额（元）
			BigDecimal totalUpTotalAmtNoTax = new BigDecimal(0); // 至上期末累计结算不含税金额（元）
			BigDecimal totalUpTotalAmtTax = new BigDecimal(0); // 至上期末累计结算税额（元）
			BigDecimal totalAllTotalAmt = new BigDecimal(0); // 至本期末累计结算含税金额（元）
			BigDecimal totalAllTotalAmtNoTax = new BigDecimal(0); // 至本期末累计结算不含税金额（元）
			BigDecimal totalAllTotalAmtTax = new BigDecimal(0); // 至本期末累计结算税额（元）
			
			if (paySettleItemList != null && paySettleItemList.size() > 0) {
				ZxSaProjectWorkSettleItem workTotalInfo = returnList.get(returnList.size() - 1);
				
				List<Map<String, Object>> allTypeList = new ArrayList<>();
				
				// 3.1 处理材料调拨费(查询类型为材料调拨费数据)
				List<ZxSaProjectPaySettleItem> cldbfList = paySettleItemList.stream().filter(cldbf->StrUtil.equals("材料调拨费", cldbf.getPayType())).collect(Collectors.toList());
				Map<String, Object> cldbfMap = new HashMap<>();
				cldbfMap.put("name", "材料调拨费");
				cldbfMap.put("no", "B");
				cldbfMap.put("itemList", cldbfList);
				allTypeList.add(cldbfMap);
				
				// 3.2 处理零星工程机械(查询类型为零星工程机械数据)
				List<ZxSaProjectPaySettleItem> lxgcjxList = paySettleItemList.stream().filter(lxgcjx->StrUtil.equals("零星工程机械", lxgcjx.getPayType())).collect(Collectors.toList());
				Map<String, Object> lxgcjxMap = new HashMap<>();
				lxgcjxMap.put("name", "零星工程机械");
				lxgcjxMap.put("no", "C");
				lxgcjxMap.put("itemList", lxgcjxList);
				allTypeList.add(lxgcjxMap);
				
				// 3.3 处理零星工程劳务(查询类型为零星工程劳务数据)
				List<ZxSaProjectPaySettleItem> lxgclwList = paySettleItemList.stream().filter(lxgclw->StrUtil.equals("零星工程劳务", lxgclw.getPayType())).collect(Collectors.toList());
				Map<String, Object> lxgclwMap = new HashMap<>();
				lxgclwMap.put("name", "零星工程劳务");
				lxgclwMap.put("no", "D");
				lxgclwMap.put("itemList", lxgclwList);
				allTypeList.add(lxgclwMap);
				
				// 3.4 处理奖罚金额(查询类型为奖罚金额数据)
				List<ZxSaProjectPaySettleItem> cfjeList = paySettleItemList.stream().filter(cfje->StrUtil.equals("奖罚金额", cfje.getPayType())).collect(Collectors.toList());
				Map<String, Object> cfjeMap = new HashMap<>();
				cfjeMap.put("name", "奖罚金额");
				cfjeMap.put("no", "E");
				cfjeMap.put("itemList", cfjeList);
				allTypeList.add(cfjeMap);
				
				// 3.5 处理补偿金额(查询类型为补偿金额数据)
				List<ZxSaProjectPaySettleItem> bcjeList = paySettleItemList.stream().filter(bcje->StrUtil.equals("补偿金额", bcje.getPayType())).collect(Collectors.toList());
				Map<String, Object> bcjeMap = new HashMap<>();
				bcjeMap.put("name", "补偿金额");
				bcjeMap.put("no", "F");
				bcjeMap.put("itemList", bcjeList);
				allTypeList.add(bcjeMap);
				
				// 3.6 其他款项(查询类型为其他款项数据)
				List<ZxSaProjectPaySettleItem> qtkxList = paySettleItemList.stream().filter(qtkx->StrUtil.equals("其他款项", qtkx.getPayType())).collect(Collectors.toList());
				Map<String, Object> qtkxMap = new HashMap<>();
				qtkxMap.put("name", "其他款项");
				qtkxMap.put("no", "G");
				qtkxMap.put("itemList", qtkxList);
				allTypeList.add(qtkxMap);
				
				for (Map<String, Object> itemMap : allTypeList) {
					String workName = String.valueOf(itemMap.get("name"));
					String workNo = String.valueOf(itemMap.get("no"));
					List<ZxSaProjectPaySettleItem> itemList = (List<ZxSaProjectPaySettleItem>) itemMap.get("itemList");
					
					if (itemList != null && itemList.size() > 0) {
						// 累计本期结算含税金额（元）、至上期末累计结算含税金额（元）、至本期末累计结算含税金额（元）
						BigDecimal itemTotalThisTotalAmt = new BigDecimal(0); // 累计本期结算含税金额（元）
						BigDecimal itemTotalThisTotalAmtNoTax = new BigDecimal(0); // 累计本期结算不含税金额（元）
						BigDecimal itemTotalThisTotalAmtTax = new BigDecimal(0); // 累计本期结算税额（元）
						BigDecimal itemTotalUpTotalAmt = new BigDecimal(0); // 至上期末累计结算含税金额（元）
						BigDecimal itemTotalUpTotalAmtNoTax = new BigDecimal(0); // 至上期末累计结算不含税金额（元）
						BigDecimal itemTotalUpTotalAmtTax = new BigDecimal(0); // 至上期末累计结算税额（元）
						BigDecimal itemTotalAllTotalAmt = new BigDecimal(0); // 至本期末累计结算含税金额（元）
						BigDecimal itemTotalAllTotalAmtNoTax = new BigDecimal(0); // 至本期末累计结算不含税金额（元）
						BigDecimal itemTotalAllTotalAmtTax = new BigDecimal(0); // 至本期末累计结算税额（元）
						
						// 按payName分组
						List<List<ZxSaProjectPaySettleItem>> groupList = new ArrayList<>();
						itemList.stream().collect(Collectors.groupingBy(ZxSaProjectPaySettleItem::getPayName, Collectors.toList()))
				        .forEach((payName, listByName)-> {
				        	groupList.add(listByName);
				        });
						
						List<ZxSaProjectWorkSettleItem> showList = new ArrayList<>();
						
						// 处理分组后结果集
						for (List<ZxSaProjectPaySettleItem> payList : groupList) {
							ZxSaProjectPaySettleItem paySettleItem = payList.get(0);
							// 是否本期次
							boolean isThisPeriod = StrUtil.equals(dbZxSaProjectSettleAudit.getPeriod(), paySettleItem.getPeriod());
							
							// 增加单项信息
							ZxSaProjectWorkSettleItem workItem = new ZxSaProjectWorkSettleItem();
							workItem.setProjectWorkSettleItemId(UuidUtil.createUUID());
							workItem.setWorkNo("");
							workItem.setWorkName(paySettleItem.getPayName());
							workItem.setUnit(paySettleItem.getUnit());
							workItem.setPrice(paySettleItem.getPrice());
							if (isThisPeriod) {
								workItem.setThisQty(paySettleItem.getQty() == null ? zero : paySettleItem.getQty());
								workItem.setThisTotalAmt(paySettleItem.getThisAmt() == null ? zero : paySettleItem.getThisAmt());
								workItem.setThisTotalAmtNoTax(paySettleItem.getThisAmtNoTax() == null ? zero : paySettleItem.getThisAmtNoTax());
								workItem.setThisTotalAmtTax(paySettleItem.getThisAmtTax() == null ? zero : paySettleItem.getThisAmtTax());
								itemTotalThisTotalAmt = CalcUtils.calcAdd(itemTotalThisTotalAmt, paySettleItem.getThisAmt());
								itemTotalThisTotalAmtNoTax = CalcUtils.calcAdd(itemTotalThisTotalAmtNoTax, paySettleItem.getThisAmtNoTax());
								itemTotalThisTotalAmtTax = CalcUtils.calcAdd(itemTotalThisTotalAmtTax, paySettleItem.getThisAmtTax());
								// 累计往期结算金额
								if (payList.size() > 1) {
									BigDecimal upQty = new BigDecimal(0);
									BigDecimal amt = new BigDecimal(0);
									BigDecimal amtNoTax = new BigDecimal(0);
									BigDecimal amtTax = new BigDecimal(0);
									for (ZxSaProjectPaySettleItem pay : payList) {
										if (!StrUtil.equals(pay.getPeriod(), paySettleItem.getPeriod())) {
											upQty = CalcUtils.calcAdd(upQty, pay.getQty());
											amt = CalcUtils.calcAdd(amt, pay.getThisAmt());
											amtNoTax = CalcUtils.calcAdd(amtNoTax, pay.getThisAmtNoTax());
											amtTax = CalcUtils.calcAdd(amtTax, pay.getThisAmtTax());
										}
									}
									
									workItem.setUpQty(upQty == null ? zero : upQty);
									workItem.setUpTotalAmt(amt);
									itemTotalUpTotalAmt = CalcUtils.calcAdd(itemTotalUpTotalAmt, amt);
									workItem.setUpTotalAmtNoTax(amtNoTax);
									itemTotalUpTotalAmtNoTax = CalcUtils.calcAdd(itemTotalUpTotalAmtNoTax, amtNoTax);
									workItem.setUpTotalAmtTax(amtTax);
									itemTotalUpTotalAmtTax = CalcUtils.calcAdd(itemTotalUpTotalAmtTax, amtTax);
								} else {
									workItem.setUpQty(zero);
									workItem.setUpTotalAmt(zero);
									workItem.setUpTotalAmtNoTax(zero);
									workItem.setUpTotalAmtTax(zero);
								}
							} else {
								workItem.setThisQty(zero);
								workItem.setThisTotalAmt(zero);
								workItem.setThisTotalAmtNoTax(zero);
								workItem.setThisTotalAmtTax(zero);
								
								// 累计本期次之前所有期次数据
								BigDecimal upQty = new BigDecimal(0);
								BigDecimal amt = new BigDecimal(0);
								BigDecimal amtNoTax = new BigDecimal(0);
								BigDecimal amtTax = new BigDecimal(0);
								for (ZxSaProjectPaySettleItem pay : payList) {
									upQty = CalcUtils.calcAdd(upQty, pay.getQty());
									amt = CalcUtils.calcAdd(amt, pay.getThisAmt());
									amtNoTax = CalcUtils.calcAdd(amtNoTax, pay.getThisAmtNoTax());
									amtTax = CalcUtils.calcAdd(amtTax, pay.getThisAmtTax());
								}
								workItem.setUpQty(upQty);
								workItem.setUpTotalAmt(amt);
								itemTotalUpTotalAmt = CalcUtils.calcAdd(itemTotalUpTotalAmt, amt);
								workItem.setUpTotalAmtNoTax(amtNoTax);
								itemTotalUpTotalAmtNoTax = CalcUtils.calcAdd(itemTotalUpTotalAmtNoTax, amtNoTax);
								workItem.setUpTotalAmtTax(amtTax);
								itemTotalUpTotalAmtTax = CalcUtils.calcAdd(itemTotalUpTotalAmtTax, amtTax);
							}
							
							BigDecimal allQty = CalcUtils.calcAdd(workItem.getThisQty(), workItem.getUpQty());
							workItem.setAllQty(allQty == null ? zero : allQty);
							BigDecimal allTotalAmt = CalcUtils.calcAdd(workItem.getThisTotalAmt(), workItem.getUpTotalAmt());
							workItem.setAllTotalAmt(allTotalAmt == null ? zero : allTotalAmt);
							BigDecimal allTotalAmtNoTax = CalcUtils.calcAdd(workItem.getThisTotalAmtNoTax(), itemTotalUpTotalAmtNoTax);
							workItem.setAllTotalAmtNoTax(allTotalAmtNoTax == null ? zero : allTotalAmtNoTax);
							BigDecimal allTotalAmtTax = CalcUtils.calcAdd(workItem.getThisTotalAmtTax(), itemTotalUpTotalAmtTax);
							workItem.setAllTotalAmtTax(allTotalAmtTax == null ? zero : allTotalAmtTax);
							itemTotalAllTotalAmt = CalcUtils.calcAdd(itemTotalAllTotalAmt, workItem.getAllTotalAmt());
							itemTotalAllTotalAmtNoTax = CalcUtils.calcAdd(itemTotalAllTotalAmtNoTax, workItem.getAllTotalAmtNoTax());
							itemTotalAllTotalAmtTax = CalcUtils.calcAdd(itemTotalAllTotalAmtTax, workItem.getAllTotalAmtTax());
							
							BigDecimal oneHundred = new BigDecimal(100);
							workItem.setPriceNoTax(CalcUtils.calcMultiply(CalcUtils.calcDivide(paySettleItem.getPrice(), CalcUtils.calcAdd(oneHundred, paySettleItem.getTaxRate() == null ? new BigDecimal(0) : new BigDecimal(paySettleItem.getTaxRate())), 6), oneHundred));
							workItem.setContractTax(CalcUtils.calcSubtract(workItem.getPrice(), workItem.getPriceNoTax()));
							workItem.setTaxRate(new BigDecimal(StrUtil.isEmpty(paySettleItem.getTaxRate()) ? "0" : paySettleItem.getTaxRate()));
							workItem.setRemark(paySettleItem.getRemark());
							workItem.setOrderNum(-1001);
							
							showList.add(workItem);
						}
						
						// 增加合计行
						ZxSaProjectWorkSettleItem workTotalItem = new ZxSaProjectWorkSettleItem();
						workTotalItem.setProjectWorkSettleItemId(UuidUtil.createUUID());
						workTotalItem.setWorkNo(workNo);
						workTotalItem.setWorkName(workName);
						workTotalItem.setUnit("元");
						workTotalItem.setOrderNum(-1001);
						workTotalItem.setThisTotalAmt(itemTotalThisTotalAmt);
						workTotalItem.setThisTotalAmtNoTax(itemTotalThisTotalAmtNoTax);
						workTotalItem.setThisTotalAmtTax(itemTotalThisTotalAmtTax);
						totalThisTotalAmt = CalcUtils.calcAdd(totalThisTotalAmt, itemTotalThisTotalAmt);
						totalThisTotalAmtNoTax = CalcUtils.calcAdd(totalThisTotalAmtNoTax, itemTotalThisTotalAmtNoTax);
						totalThisTotalAmtTax = CalcUtils.calcAdd(totalThisTotalAmtTax, itemTotalThisTotalAmtTax);
						workTotalItem.setUpTotalAmt(itemTotalUpTotalAmt);
						workTotalItem.setUpTotalAmtNoTax(itemTotalUpTotalAmtNoTax);
						workTotalItem.setUpTotalAmtTax(itemTotalUpTotalAmtTax);
						totalUpTotalAmt = CalcUtils.calcAdd(totalUpTotalAmt, itemTotalUpTotalAmt);
						totalUpTotalAmtNoTax = CalcUtils.calcAdd(totalUpTotalAmtNoTax, itemTotalUpTotalAmtNoTax);
						totalUpTotalAmtTax = CalcUtils.calcAdd(totalUpTotalAmtTax, itemTotalUpTotalAmtTax);
						workTotalItem.setAllTotalAmt(CalcUtils.calcAdd(workTotalItem.getThisTotalAmt(), workTotalItem.getUpTotalAmt()));
						workTotalItem.setAllTotalAmtNoTax(CalcUtils.calcAdd(workTotalItem.getThisTotalAmtNoTax(), workTotalItem.getUpTotalAmtNoTax()));
						workTotalItem.setAllTotalAmtTax(CalcUtils.calcAdd(workTotalItem.getThisTotalAmtTax(), workTotalItem.getUpTotalAmtTax()));
						totalAllTotalAmt = CalcUtils.calcAdd(totalAllTotalAmt, workTotalItem.getAllTotalAmt());
						totalAllTotalAmtNoTax = CalcUtils.calcAdd(totalAllTotalAmtNoTax, workTotalItem.getAllTotalAmtNoTax());
						totalAllTotalAmtTax = CalcUtils.calcAdd(totalAllTotalAmtTax, workTotalItem.getAllTotalAmtTax());
						workTotalItem.setPriceNoTax(null);
						workTotalItem.setContractTax(null);
						workTotalItem.setTaxRate(null);
						workTotalItem.setRemark("");
						returnList.add(workTotalItem);
						
						returnList.addAll(showList);
					} else {
						// 增加材料调拨费合计行
						ZxSaProjectWorkSettleItem workTotalItem = new ZxSaProjectWorkSettleItem();
						workTotalItem.setProjectWorkSettleItemId(UuidUtil.createUUID());
						workTotalItem.setWorkNo(workNo);
						workTotalItem.setWorkName(workName);
						workTotalItem.setUnit("元");
						workTotalItem.setPrice(null);
						workTotalItem.setContractQty(null);
						workTotalItem.setContractAmt(null);
						workTotalItem.setChangeQty(null);
						workTotalItem.setChangeAmt(null);
						workTotalItem.setThisQty(null);
						workTotalItem.setOrderNum(-1001);
						workTotalItem.setThisTotalAmt(zero);
						workTotalItem.setThisTotalAmtNoTax(zero);
						workTotalItem.setThisTotalAmtTax(zero);
						workTotalItem.setUpQty(null);
						workTotalItem.setUpTotalAmt(zero);
						workTotalItem.setUpTotalAmtNoTax(zero);
						workTotalItem.setUpTotalAmtTax(zero);
						workTotalItem.setAllQty(null);
						workTotalItem.setAllTotalAmt(zero);
						workTotalItem.setAllTotalAmtNoTax(zero);
						workTotalItem.setAllTotalAmtTax(zero);
						returnList.add(workTotalItem);
					}
				}
				
				totalThisTotalAmt = CalcUtils.calcAdd(totalThisTotalAmt, workTotalInfo.getThisTotalAmt());
				totalThisTotalAmtNoTax = CalcUtils.calcAdd(totalThisTotalAmtNoTax, workTotalInfo.getThisTotalAmtNoTax());
				totalThisTotalAmtTax = CalcUtils.calcAdd(totalThisTotalAmtTax, workTotalInfo.getThisTotalAmtTax());
				totalUpTotalAmt = CalcUtils.calcAdd(totalUpTotalAmt, workTotalInfo.getUpTotalAmt());
				totalUpTotalAmtNoTax = CalcUtils.calcAdd(totalUpTotalAmtNoTax, workTotalInfo.getUpTotalAmtNoTax());
				totalUpTotalAmtTax = CalcUtils.calcAdd(totalUpTotalAmtTax, workTotalInfo.getUpTotalAmtTax());
				totalAllTotalAmt = CalcUtils.calcAdd(totalAllTotalAmt, workTotalInfo.getAllTotalAmt());
				totalAllTotalAmtNoTax = CalcUtils.calcAdd(totalAllTotalAmtNoTax, workTotalInfo.getAllTotalAmtNoTax());
				totalAllTotalAmtTax = CalcUtils.calcAdd(totalAllTotalAmtTax, workTotalInfo.getAllTotalAmtTax());
			}
			// 增加总合计行
			ZxSaProjectWorkSettleItem allTotalItem = new ZxSaProjectWorkSettleItem();
			allTotalItem.setProjectWorkSettleItemId(UuidUtil.createUUID());
			allTotalItem.setWorkNo("H");
			allTotalItem.setWorkName("合计结算金额");
			allTotalItem.setUnit("元");
			allTotalItem.setOrderNum(-1001);
			allTotalItem.setThisTotalAmt(totalThisTotalAmt);
			allTotalItem.setThisTotalAmtNoTax(totalThisTotalAmtNoTax);
			allTotalItem.setThisTotalAmtTax(totalThisTotalAmtTax);
			allTotalItem.setUpTotalAmt(totalUpTotalAmt);
			allTotalItem.setUpTotalAmtNoTax(totalUpTotalAmtNoTax);
			allTotalItem.setUpTotalAmtTax(totalUpTotalAmtTax);
			allTotalItem.setAllTotalAmt(totalAllTotalAmt);
			allTotalItem.setAllTotalAmtNoTax(totalAllTotalAmtNoTax);
			allTotalItem.setAllTotalAmtTax(totalAllTotalAmtTax);
			returnList.add(allTotalItem);
			
			dbZxSaProjectSettleAudit.setWorkSettleItemList(returnList);
			return repEntity.ok(dbZxSaProjectSettleAudit);
		} else {
			return repEntity.layerMessage("no", "未查询到相应信息！") ;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ZxSaProjectWorkSettleItem> getZxSaProjectSettleAuditYgzList(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
		// 1、查询结算单信息
		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = zxSaProjectSettleAuditMapper.selectByPrimaryKey(zxSaProjectWorkSettleItem.getSettleauditId());
		if (dbZxSaProjectSettleAudit != null && StrUtil.isNotEmpty(dbZxSaProjectSettleAudit.getId())) {
			List<ZxSaProjectWorkSettleItem> returnList = new ArrayList<>();
			
			BigDecimal zero = new BigDecimal(0);
			
			// 2、查询清单信息
			ZxSaProjectWorkSettleItem selectWorkSettleItem = new ZxSaProjectWorkSettleItem();
			selectWorkSettleItem.setContractID(dbZxSaProjectSettleAudit.getContractID());
			selectWorkSettleItem.setPeriod(dbZxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectWorkSettleItem> workSettleItemList = zxSaProjectWorkSettleItemMapper.getYgzWorkSettleItemList(selectWorkSettleItem);
			if (workSettleItemList != null && workSettleItemList.size() > 0) {
				// 累计原合同含税合同金额（元）、变更后含税合同金额（元）、本期结算含税金额（元）、至上期末累计结算含税金额（元）、至本期末累计结算含税金额（元）
				BigDecimal totalContractAmt = new BigDecimal(0); // 原合同含税合同金额（元）
				BigDecimal totalContractAmtNoTax = new BigDecimal(0); // 原合同不含税合同金额（元）
				BigDecimal totalContractAmtTax = new BigDecimal(0); // 原合同合同税额（元）
				BigDecimal totalChangeAmt = new BigDecimal(0); // 变更后含税合同金额（元）
				BigDecimal totalChangeAmtNoTax = new BigDecimal(0); // 变更后不含税合同金额（元）
				BigDecimal totalChangeAmtTax = new BigDecimal(0); // 变更后合同税额（元）
				BigDecimal totalThisTotalAmt = new BigDecimal(0); // 本期结算含税金额（元）
				BigDecimal totalThisTotalAmtNoTax = new BigDecimal(0); // 本期结算不含税金额（元）
				BigDecimal totalThisTotalAmtTax = new BigDecimal(0); // 本期结算税额（元）
				BigDecimal totalUpTotalAmt = new BigDecimal(0); // 至上期末累计结算含税金额（元）
				BigDecimal totalUpTotalAmtNoTax = new BigDecimal(0); // 至上期末累计结算不含税金额（元）
				BigDecimal totalUpTotalAmtTax = new BigDecimal(0); // 至上期末累计结算税额（元）
				BigDecimal totalAllTotalAmt = new BigDecimal(0); // 至本期末累计结算含税金额（元）
				BigDecimal totalAllTotalAmtNoTax = new BigDecimal(0); // 至本期末累计结算不含税金额（元）
				BigDecimal totalAllTotalAmtTax = new BigDecimal(0); // 至本期末累计结算税额（元）
				for (ZxSaProjectWorkSettleItem settleItem : workSettleItemList) {
					settleItem.setPrice(settleItem.getPrice() == null ? zero : settleItem.getPrice());
					settleItem.setContractQty(settleItem.getContractQty() == null ? zero : settleItem.getContractQty());
					settleItem.setContractAmt(settleItem.getContractAmt() == null ? zero : settleItem.getContractAmt());
					settleItem.setChangeQty(settleItem.getChangeQty() == null ? zero : settleItem.getChangeQty());
					settleItem.setChangeAmt(settleItem.getChangeAmt() == null ? zero : settleItem.getChangeAmt());
					settleItem.setThisQty(settleItem.getThisQty() == null ? zero : settleItem.getThisQty());
					settleItem.setThisAmt(settleItem.getThisAmt() == null ? zero : settleItem.getThisAmt());
					settleItem.setThisAmtSubTotal(settleItem.getThisQty());
					settleItem.setOrderNum(settleItem.getOrderNum() == 0 ? 0 : settleItem.getOrderNum());
					settleItem.setThisTotalAmt(settleItem.getThisTotalAmt() == null ? zero : settleItem.getThisTotalAmt());
					settleItem.setUpQty(settleItem.getUpQty() == null ? zero : settleItem.getUpQty());
					settleItem.setUpAmtSubTotal(settleItem.getUpQty());
					settleItem.setUpTotalAmt(settleItem.getUpTotalAmt() == null ? zero : settleItem.getUpTotalAmt());
					settleItem.setAllQty(settleItem.getAllQty() == null ? zero : settleItem.getAllQty());
					settleItem.setAllAmtSubTotal(settleItem.getAllQty());
					settleItem.setAllTotalAmt(settleItem.getAllTotalAmt() == null ? zero : settleItem.getAllTotalAmt());
					returnList.add(settleItem);
					
					totalContractAmt = CalcUtils.calcAdd(totalContractAmt, settleItem.getContractAmt());
					totalContractAmtNoTax = CalcUtils.calcAdd(totalContractAmtNoTax, settleItem.getContractAmtNoTax());
					totalContractAmtTax = CalcUtils.calcAdd(totalContractAmtTax, settleItem.getContractAmtTax());
					totalChangeAmt = CalcUtils.calcAdd(totalChangeAmt, settleItem.getChangeAmt());
					totalChangeAmtNoTax = CalcUtils.calcAdd(totalChangeAmtNoTax, settleItem.getChangeAmtNoTax());
					totalChangeAmtTax = CalcUtils.calcAdd(totalChangeAmtTax, settleItem.getChangeAmtTax());
					totalThisTotalAmt = CalcUtils.calcAdd(totalThisTotalAmt, settleItem.getThisTotalAmt());
					totalThisTotalAmtNoTax = CalcUtils.calcAdd(totalThisTotalAmtNoTax, settleItem.getThisTotalAmtNoTax());
					totalThisTotalAmtTax = CalcUtils.calcAdd(totalThisTotalAmtTax, settleItem.getThisTotalAmtTax());
					totalUpTotalAmt = CalcUtils.calcAdd(totalUpTotalAmt, settleItem.getUpTotalAmt());
					totalUpTotalAmtNoTax = CalcUtils.calcAdd(totalUpTotalAmtNoTax, settleItem.getUpTotalAmtNoTax());
					totalUpTotalAmtTax = CalcUtils.calcAdd(totalUpTotalAmtTax, settleItem.getUpTotalAmtTax());
					totalAllTotalAmt = CalcUtils.calcAdd(totalAllTotalAmt, settleItem.getAllTotalAmt());
					totalAllTotalAmtNoTax = CalcUtils.calcAdd(totalAllTotalAmtNoTax, settleItem.getAllTotalAmtNoTax());
					totalAllTotalAmtTax = CalcUtils.calcAdd(totalAllTotalAmtTax, settleItem.getAllTotalAmtTax());
				}
				// 增加清单小计行
				ZxSaProjectWorkSettleItem workTotalItem = new ZxSaProjectWorkSettleItem();
				workTotalItem.setProjectWorkSettleItemId(UuidUtil.generate());
				workTotalItem.setWorkNo("A");
				workTotalItem.setWorkName("清单小计");
				workTotalItem.setUnit("元");
				workTotalItem.setPrice(null);
				workTotalItem.setContractQty(null);
				workTotalItem.setContractAmt(totalContractAmt);
				workTotalItem.setContractAmtNoTax(totalContractAmtNoTax);
				workTotalItem.setContractAmtTax(totalContractAmtTax);
				workTotalItem.setChangeQty(null);
				workTotalItem.setChangeAmt(totalChangeAmt);
				workTotalItem.setChangeAmtNoTax(totalChangeAmtNoTax);
				workTotalItem.setChangeAmtTax(totalChangeAmtTax);
				workTotalItem.setThisQty(null);
				workTotalItem.setThisAmt(totalThisTotalAmt);
				workTotalItem.setThisAmtNoTax(totalThisTotalAmtNoTax);
				workTotalItem.setThisAmtTax(totalThisTotalAmtTax);
				workTotalItem.setOrderNum(-1001);
				workTotalItem.setThisTotalAmt(totalThisTotalAmt);
				workTotalItem.setThisTotalAmtNoTax(totalThisTotalAmtNoTax);
				workTotalItem.setThisTotalAmtTax(totalThisTotalAmtTax);
				workTotalItem.setUpQty(null);
				workTotalItem.setUpTotalAmt(totalUpTotalAmt);
				workTotalItem.setUpTotalAmtNoTax(totalUpTotalAmtNoTax);
				workTotalItem.setUpTotalAmtTax(totalUpTotalAmtTax);
				workTotalItem.setAllQty(null);
				workTotalItem.setAllTotalAmt(totalAllTotalAmt);
				workTotalItem.setAllTotalAmtNoTax(totalAllTotalAmtNoTax);
				workTotalItem.setAllTotalAmtTax(totalAllTotalAmtTax);
				workTotalItem.setRemark("");
				returnList.add(workTotalItem);
			} else {
				// 增加清单小计行
				ZxSaProjectWorkSettleItem workTotalItem = new ZxSaProjectWorkSettleItem();
				workTotalItem.setProjectWorkSettleItemId(UuidUtil.generate());
				workTotalItem.setWorkNo("A");
				workTotalItem.setWorkName("清单小计");
				workTotalItem.setUnit("元");
				workTotalItem.setPrice(null);
				workTotalItem.setContractQty(null);
				workTotalItem.setContractAmt(zero);
				workTotalItem.setContractAmtNoTax(zero);
				workTotalItem.setContractAmtTax(zero);
				workTotalItem.setChangeQty(null);
				workTotalItem.setChangeAmt(zero);
				workTotalItem.setChangeAmtNoTax(zero);
				workTotalItem.setChangeAmtTax(zero);
				workTotalItem.setThisQty(null);
				workTotalItem.setThisAmt(zero);
				workTotalItem.setThisAmtNoTax(zero);
				workTotalItem.setThisAmtTax(zero);
				workTotalItem.setOrderNum(-1001);
				workTotalItem.setThisTotalAmt(zero);
				workTotalItem.setThisTotalAmtNoTax(zero);
				workTotalItem.setThisTotalAmtTax(zero);
				workTotalItem.setUpQty(null);
				workTotalItem.setUpTotalAmt(zero);
				workTotalItem.setUpTotalAmtNoTax(zero);
				workTotalItem.setUpTotalAmtTax(zero);
				workTotalItem.setAllQty(null);
				workTotalItem.setAllTotalAmt(zero);
				workTotalItem.setAllTotalAmtNoTax(zero);
				workTotalItem.setAllTotalAmtTax(zero);
				workTotalItem.setRemark("");
				returnList.add(workTotalItem);
			}
			
			// 3、查询本期次之前已做支付项
			ZxSaProjectPaySettleItem selectPaySettleItem = new ZxSaProjectPaySettleItem();
			selectPaySettleItem.setContractID(dbZxSaProjectSettleAudit.getContractID());
			selectPaySettleItem.setPeriod(dbZxSaProjectSettleAudit.getPeriod());
			List<ZxSaProjectPaySettleItem> paySettleItemList = zxSaProjectPaySettleItemMapper.selectAlreadyDoneItemList(selectPaySettleItem);
			
			// 累计本期结算含税金额（元）、至上期末累计结算含税金额（元）、至本期末累计结算含税金额（元）
			BigDecimal totalThisTotalAmt = new BigDecimal(0); // 累计本期结算含税金额（元）
			BigDecimal totalThisTotalAmtNoTax = new BigDecimal(0); // 累计本期结算不含税金额（元）
			BigDecimal totalThisTotalAmtTax = new BigDecimal(0); // 累计本期结算税额（元）
			BigDecimal totalUpTotalAmt = new BigDecimal(0); // 至上期末累计结算含税金额（元）
			BigDecimal totalUpTotalAmtNoTax = new BigDecimal(0); // 至上期末累计结算不含税金额（元）
			BigDecimal totalUpTotalAmtTax = new BigDecimal(0); // 至上期末累计结算税额（元）
			BigDecimal totalAllTotalAmt = new BigDecimal(0); // 至本期末累计结算含税金额（元）
			BigDecimal totalAllTotalAmtNoTax = new BigDecimal(0); // 至本期末累计结算不含税金额（元）
			BigDecimal totalAllTotalAmtTax = new BigDecimal(0); // 至本期末累计结算税额（元）
			
			if (paySettleItemList != null && paySettleItemList.size() > 0) {
				ZxSaProjectWorkSettleItem workTotalInfo = returnList.get(returnList.size() - 1);
				
				List<Map<String, Object>> allTypeList = new ArrayList<>();
				
				// 3.1 处理材料调拨费(查询类型为材料调拨费数据)
				List<ZxSaProjectPaySettleItem> cldbfList = paySettleItemList.stream().filter(cldbf->StrUtil.equals("材料调拨费", cldbf.getPayType())).collect(Collectors.toList());
				Map<String, Object> cldbfMap = new HashMap<>();
				cldbfMap.put("name", "材料调拨费");
				cldbfMap.put("no", "B");
				cldbfMap.put("itemList", cldbfList);
				allTypeList.add(cldbfMap);
				
				// 3.2 处理零星工程机械(查询类型为零星工程机械数据)
				List<ZxSaProjectPaySettleItem> lxgcjxList = paySettleItemList.stream().filter(lxgcjx->StrUtil.equals("零星工程机械", lxgcjx.getPayType())).collect(Collectors.toList());
				Map<String, Object> lxgcjxMap = new HashMap<>();
				lxgcjxMap.put("name", "零星工程机械");
				lxgcjxMap.put("no", "C");
				lxgcjxMap.put("itemList", lxgcjxList);
				allTypeList.add(lxgcjxMap);
				
				// 3.3 处理零星工程劳务(查询类型为零星工程劳务数据)
				List<ZxSaProjectPaySettleItem> lxgclwList = paySettleItemList.stream().filter(lxgclw->StrUtil.equals("零星工程劳务", lxgclw.getPayType())).collect(Collectors.toList());
				Map<String, Object> lxgclwMap = new HashMap<>();
				lxgclwMap.put("name", "零星工程劳务");
				lxgclwMap.put("no", "D");
				lxgclwMap.put("itemList", lxgclwList);
				allTypeList.add(lxgclwMap);
				
				// 3.4 处理奖罚金额(查询类型为奖罚金额数据)
				List<ZxSaProjectPaySettleItem> cfjeList = paySettleItemList.stream().filter(cfje->StrUtil.equals("奖罚金额", cfje.getPayType())).collect(Collectors.toList());
				Map<String, Object> cfjeMap = new HashMap<>();
				cfjeMap.put("name", "奖罚金额");
				cfjeMap.put("no", "E");
				cfjeMap.put("itemList", cfjeList);
				allTypeList.add(cfjeMap);
				
				// 3.5 处理补偿金额(查询类型为补偿金额数据)
				List<ZxSaProjectPaySettleItem> bcjeList = paySettleItemList.stream().filter(bcje->StrUtil.equals("补偿金额", bcje.getPayType())).collect(Collectors.toList());
				Map<String, Object> bcjeMap = new HashMap<>();
				bcjeMap.put("name", "补偿金额");
				bcjeMap.put("no", "F");
				bcjeMap.put("itemList", bcjeList);
				allTypeList.add(bcjeMap);
				
				// 3.6 其他款项(查询类型为其他款项数据)
				List<ZxSaProjectPaySettleItem> qtkxList = paySettleItemList.stream().filter(qtkx->StrUtil.equals("其他款项", qtkx.getPayType())).collect(Collectors.toList());
				Map<String, Object> qtkxMap = new HashMap<>();
				qtkxMap.put("name", "其他款项");
				qtkxMap.put("no", "G");
				qtkxMap.put("itemList", qtkxList);
				allTypeList.add(qtkxMap);
				
				for (Map<String, Object> itemMap : allTypeList) {
					String workName = String.valueOf(itemMap.get("name"));
					String workNo = String.valueOf(itemMap.get("no"));
					List<ZxSaProjectPaySettleItem> itemList = (List<ZxSaProjectPaySettleItem>) itemMap.get("itemList");
					
					if (itemList != null && itemList.size() > 0) {
						// 累计本期结算含税金额（元）、至上期末累计结算含税金额（元）、至本期末累计结算含税金额（元）
						BigDecimal itemTotalThisTotalAmt = new BigDecimal(0); // 累计本期结算含税金额（元）
						BigDecimal itemTotalThisTotalAmtNoTax = new BigDecimal(0); // 累计本期结算不含税金额（元）
						BigDecimal itemTotalThisTotalAmtTax = new BigDecimal(0); // 累计本期结算税额（元）
						BigDecimal itemTotalUpTotalAmt = new BigDecimal(0); // 至上期末累计结算含税金额（元）
						BigDecimal itemTotalUpTotalAmtNoTax = new BigDecimal(0); // 至上期末累计结算不含税金额（元）
						BigDecimal itemTotalUpTotalAmtTax = new BigDecimal(0); // 至上期末累计结算税额（元）
						BigDecimal itemTotalAllTotalAmt = new BigDecimal(0); // 至本期末累计结算含税金额（元）
						BigDecimal itemTotalAllTotalAmtNoTax = new BigDecimal(0); // 至本期末累计结算不含税金额（元）
						BigDecimal itemTotalAllTotalAmtTax = new BigDecimal(0); // 至本期末累计结算税额（元）
						
						// 按payName分组
						List<List<ZxSaProjectPaySettleItem>> groupList = new ArrayList<>();
						itemList.stream().collect(Collectors.groupingBy(ZxSaProjectPaySettleItem::getPayName, Collectors.toList()))
				        .forEach((payName, listByName)-> {
				        	groupList.add(listByName);
				        });
						
						List<ZxSaProjectWorkSettleItem> showList = new ArrayList<>();
						
						// 处理分组后结果集
						for (List<ZxSaProjectPaySettleItem> payList : groupList) {
							ZxSaProjectPaySettleItem paySettleItem = payList.get(0);
							// 是否本期次
							boolean isThisPeriod = StrUtil.equals(dbZxSaProjectSettleAudit.getPeriod(), paySettleItem.getPeriod());
							
							// 增加单项信息
							ZxSaProjectWorkSettleItem workItem = new ZxSaProjectWorkSettleItem();
							workItem.setProjectWorkSettleItemId(UuidUtil.createUUID());
							workItem.setWorkNo("");
							workItem.setWorkName(paySettleItem.getPayName());
							workItem.setUnit(paySettleItem.getUnit());
							workItem.setPrice(paySettleItem.getPrice());
							if (isThisPeriod) {
								workItem.setThisQty(paySettleItem.getQty() == null ? zero : paySettleItem.getQty());
								workItem.setThisTotalAmt(paySettleItem.getThisAmt() == null ? zero : paySettleItem.getThisAmt());
								workItem.setThisTotalAmtNoTax(paySettleItem.getThisAmtNoTax() == null ? zero : paySettleItem.getThisAmtNoTax());
								workItem.setThisTotalAmtTax(paySettleItem.getThisAmtTax() == null ? zero : paySettleItem.getThisAmtTax());
								itemTotalThisTotalAmt = CalcUtils.calcAdd(itemTotalThisTotalAmt, paySettleItem.getThisAmt());
								itemTotalThisTotalAmtNoTax = CalcUtils.calcAdd(itemTotalThisTotalAmtNoTax, paySettleItem.getThisAmtNoTax());
								itemTotalThisTotalAmtTax = CalcUtils.calcAdd(itemTotalThisTotalAmtTax, paySettleItem.getThisAmtTax());
								// 累计往期结算金额
								if (payList.size() > 1) {
									BigDecimal upQty = new BigDecimal(0);
									BigDecimal amt = new BigDecimal(0);
									BigDecimal amtNoTax = new BigDecimal(0);
									BigDecimal amtTax = new BigDecimal(0);
									for (ZxSaProjectPaySettleItem pay : payList) {
										if (!StrUtil.equals(pay.getPeriod(), paySettleItem.getPeriod())) {
											upQty = CalcUtils.calcAdd(upQty, pay.getQty());
											amt = CalcUtils.calcAdd(amt, pay.getThisAmt());
											amtNoTax = CalcUtils.calcAdd(amtNoTax, pay.getThisAmtNoTax());
											amtTax = CalcUtils.calcAdd(amtTax, pay.getThisAmtTax());
										}
									}
									
									workItem.setUpQty(upQty == null ? zero : upQty);
									workItem.setUpTotalAmt(amt);
									itemTotalUpTotalAmt = CalcUtils.calcAdd(itemTotalUpTotalAmt, amt);
									workItem.setUpTotalAmtNoTax(amtNoTax);
									itemTotalUpTotalAmtNoTax = CalcUtils.calcAdd(itemTotalUpTotalAmtNoTax, amtNoTax);
									workItem.setUpTotalAmtTax(amtTax);
									itemTotalUpTotalAmtTax = CalcUtils.calcAdd(itemTotalUpTotalAmtTax, amtTax);
								} else {
									workItem.setUpQty(zero);
									workItem.setUpTotalAmt(zero);
									workItem.setUpTotalAmtNoTax(zero);
									workItem.setUpTotalAmtTax(zero);
								}
							} else {
								workItem.setThisQty(zero);
								workItem.setThisTotalAmt(zero);
								workItem.setThisTotalAmtNoTax(zero);
								workItem.setThisTotalAmtTax(zero);
								
								// 累计本期次之前所有期次数据
								BigDecimal upQty = new BigDecimal(0);
								BigDecimal amt = new BigDecimal(0);
								BigDecimal amtNoTax = new BigDecimal(0);
								BigDecimal amtTax = new BigDecimal(0);
								for (ZxSaProjectPaySettleItem pay : payList) {
									upQty = CalcUtils.calcAdd(upQty, pay.getQty());
									amt = CalcUtils.calcAdd(amt, pay.getThisAmt());
									amtNoTax = CalcUtils.calcAdd(amtNoTax, pay.getThisAmtNoTax());
									amtTax = CalcUtils.calcAdd(amtTax, pay.getThisAmtTax());
								}
								workItem.setUpQty(upQty);
								workItem.setUpTotalAmt(amt);
								itemTotalUpTotalAmt = CalcUtils.calcAdd(itemTotalUpTotalAmt, amt);
								workItem.setUpTotalAmtNoTax(amtNoTax);
								itemTotalUpTotalAmtNoTax = CalcUtils.calcAdd(itemTotalUpTotalAmtNoTax, amtNoTax);
								workItem.setUpTotalAmtTax(amtTax);
								itemTotalUpTotalAmtTax = CalcUtils.calcAdd(itemTotalUpTotalAmtTax, amtTax);
							}
							
							BigDecimal allQty = CalcUtils.calcAdd(workItem.getThisQty(), workItem.getUpQty());
							workItem.setAllQty(allQty == null ? zero : allQty);
							BigDecimal allTotalAmt = CalcUtils.calcAdd(workItem.getThisTotalAmt(), workItem.getUpTotalAmt());
							workItem.setAllTotalAmt(allTotalAmt == null ? zero : allTotalAmt);
							BigDecimal allTotalAmtNoTax = CalcUtils.calcAdd(workItem.getThisTotalAmtNoTax(), itemTotalUpTotalAmtNoTax);
							workItem.setAllTotalAmtNoTax(allTotalAmtNoTax == null ? zero : allTotalAmtNoTax);
							BigDecimal allTotalAmtTax = CalcUtils.calcAdd(workItem.getThisTotalAmtTax(), itemTotalUpTotalAmtTax);
							workItem.setAllTotalAmtTax(allTotalAmtTax == null ? zero : allTotalAmtTax);
							itemTotalAllTotalAmt = CalcUtils.calcAdd(itemTotalAllTotalAmt, workItem.getAllTotalAmt());
							itemTotalAllTotalAmtNoTax = CalcUtils.calcAdd(itemTotalAllTotalAmtNoTax, workItem.getAllTotalAmtNoTax());
							itemTotalAllTotalAmtTax = CalcUtils.calcAdd(itemTotalAllTotalAmtTax, workItem.getAllTotalAmtTax());
							
							BigDecimal oneHundred = new BigDecimal(100);
							workItem.setPriceNoTax(CalcUtils.calcMultiply(CalcUtils.calcDivide(paySettleItem.getPrice(), CalcUtils.calcAdd(oneHundred, paySettleItem.getTaxRate() == null ? new BigDecimal(0) : new BigDecimal(paySettleItem.getTaxRate())), 6), oneHundred));
							workItem.setContractTax(CalcUtils.calcSubtract(workItem.getPrice(), workItem.getPriceNoTax()));
							workItem.setTaxRate(new BigDecimal(StrUtil.isEmpty(paySettleItem.getTaxRate()) ? "0" : paySettleItem.getTaxRate()));
							workItem.setRemark(paySettleItem.getRemark());
							workItem.setOrderNum(-1001);
							
							showList.add(workItem);
						}
						
						// 增加合计行
						ZxSaProjectWorkSettleItem workTotalItem = new ZxSaProjectWorkSettleItem();
						workTotalItem.setProjectWorkSettleItemId(UuidUtil.createUUID());
						workTotalItem.setWorkNo(workNo);
						workTotalItem.setWorkName(workName);
						workTotalItem.setUnit("元");
						workTotalItem.setOrderNum(-1001);
						workTotalItem.setThisTotalAmt(itemTotalThisTotalAmt);
						workTotalItem.setThisTotalAmtNoTax(itemTotalThisTotalAmtNoTax);
						workTotalItem.setThisTotalAmtTax(itemTotalThisTotalAmtTax);
						totalThisTotalAmt = CalcUtils.calcAdd(totalThisTotalAmt, itemTotalThisTotalAmt);
						totalThisTotalAmtNoTax = CalcUtils.calcAdd(totalThisTotalAmtNoTax, itemTotalThisTotalAmtNoTax);
						totalThisTotalAmtTax = CalcUtils.calcAdd(totalThisTotalAmtTax, itemTotalThisTotalAmtTax);
						workTotalItem.setUpTotalAmt(itemTotalUpTotalAmt);
						workTotalItem.setUpTotalAmtNoTax(itemTotalUpTotalAmtNoTax);
						workTotalItem.setUpTotalAmtTax(itemTotalUpTotalAmtTax);
						totalUpTotalAmt = CalcUtils.calcAdd(totalUpTotalAmt, itemTotalUpTotalAmt);
						totalUpTotalAmtNoTax = CalcUtils.calcAdd(totalUpTotalAmtNoTax, itemTotalUpTotalAmtNoTax);
						totalUpTotalAmtTax = CalcUtils.calcAdd(totalUpTotalAmtTax, itemTotalUpTotalAmtTax);
						workTotalItem.setAllTotalAmt(CalcUtils.calcAdd(workTotalItem.getThisTotalAmt(), workTotalItem.getUpTotalAmt()));
						workTotalItem.setAllTotalAmtNoTax(CalcUtils.calcAdd(workTotalItem.getThisTotalAmtNoTax(), workTotalItem.getUpTotalAmtNoTax()));
						workTotalItem.setAllTotalAmtTax(CalcUtils.calcAdd(workTotalItem.getThisTotalAmtTax(), workTotalItem.getUpTotalAmtTax()));
						totalAllTotalAmt = CalcUtils.calcAdd(totalAllTotalAmt, workTotalItem.getAllTotalAmt());
						totalAllTotalAmtNoTax = CalcUtils.calcAdd(totalAllTotalAmtNoTax, workTotalItem.getAllTotalAmtNoTax());
						totalAllTotalAmtTax = CalcUtils.calcAdd(totalAllTotalAmtTax, workTotalItem.getAllTotalAmtTax());
						workTotalItem.setPriceNoTax(null);
						workTotalItem.setContractTax(null);
						workTotalItem.setTaxRate(null);
						workTotalItem.setRemark("");
						returnList.add(workTotalItem);
						
						returnList.addAll(showList);
					} else {
						// 增加材料调拨费合计行
						ZxSaProjectWorkSettleItem workTotalItem = new ZxSaProjectWorkSettleItem();
						workTotalItem.setProjectWorkSettleItemId(UuidUtil.createUUID());
						workTotalItem.setWorkNo(workNo);
						workTotalItem.setWorkName(workName);
						workTotalItem.setUnit("元");
						workTotalItem.setPrice(null);
						workTotalItem.setContractQty(null);
						workTotalItem.setContractAmt(null);
						workTotalItem.setChangeQty(null);
						workTotalItem.setChangeAmt(null);
						workTotalItem.setThisQty(null);
						workTotalItem.setOrderNum(-1001);
						workTotalItem.setThisTotalAmt(zero);
						workTotalItem.setThisTotalAmtNoTax(zero);
						workTotalItem.setThisTotalAmtTax(zero);
						workTotalItem.setUpQty(null);
						workTotalItem.setUpTotalAmt(zero);
						workTotalItem.setUpTotalAmtNoTax(zero);
						workTotalItem.setUpTotalAmtTax(zero);
						workTotalItem.setAllQty(null);
						workTotalItem.setAllTotalAmt(zero);
						workTotalItem.setAllTotalAmtNoTax(zero);
						workTotalItem.setAllTotalAmtTax(zero);
						returnList.add(workTotalItem);
					}
				}
				
				totalThisTotalAmt = CalcUtils.calcAdd(totalThisTotalAmt, workTotalInfo.getThisTotalAmt());
				totalThisTotalAmtNoTax = CalcUtils.calcAdd(totalThisTotalAmtNoTax, workTotalInfo.getThisTotalAmtNoTax());
				totalThisTotalAmtTax = CalcUtils.calcAdd(totalThisTotalAmtTax, workTotalInfo.getThisTotalAmtTax());
				totalUpTotalAmt = CalcUtils.calcAdd(totalUpTotalAmt, workTotalInfo.getUpTotalAmt());
				totalUpTotalAmtNoTax = CalcUtils.calcAdd(totalUpTotalAmtNoTax, workTotalInfo.getUpTotalAmtNoTax());
				totalUpTotalAmtTax = CalcUtils.calcAdd(totalUpTotalAmtTax, workTotalInfo.getUpTotalAmtTax());
				totalAllTotalAmt = CalcUtils.calcAdd(totalAllTotalAmt, workTotalInfo.getAllTotalAmt());
				totalAllTotalAmtNoTax = CalcUtils.calcAdd(totalAllTotalAmtNoTax, workTotalInfo.getAllTotalAmtNoTax());
				totalAllTotalAmtTax = CalcUtils.calcAdd(totalAllTotalAmtTax, workTotalInfo.getAllTotalAmtTax());
			}
			// 增加总合计行
			ZxSaProjectWorkSettleItem allTotalItem = new ZxSaProjectWorkSettleItem();
			allTotalItem.setProjectWorkSettleItemId(UuidUtil.createUUID());
			allTotalItem.setWorkNo("H");
			allTotalItem.setWorkName("合计结算金额");
			allTotalItem.setUnit("元");
			allTotalItem.setOrderNum(-1001);
			allTotalItem.setThisTotalAmt(totalThisTotalAmt);
			allTotalItem.setThisTotalAmtNoTax(totalThisTotalAmtNoTax);
			allTotalItem.setThisTotalAmtTax(totalThisTotalAmtTax);
			allTotalItem.setUpTotalAmt(totalUpTotalAmt);
			allTotalItem.setUpTotalAmtNoTax(totalUpTotalAmtNoTax);
			allTotalItem.setUpTotalAmtTax(totalUpTotalAmtTax);
			allTotalItem.setAllTotalAmt(totalAllTotalAmt);
			allTotalItem.setAllTotalAmtNoTax(totalAllTotalAmtNoTax);
			allTotalItem.setAllTotalAmtTax(totalAllTotalAmtTax);
			returnList.add(allTotalItem);
			
			for (int i = 0; i < returnList.size(); i++) {
				returnList.get(i).setSort(i + 1);
			}
			
			return returnList;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public ResponseEntity exportZxSaProjectSettleAuditYgzInfo(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
		if (zxSaProjectWorkSettleItem == null || StrUtil.isEmpty(zxSaProjectWorkSettleItem.getSettleauditId())) {
			return repEntity.layerMessage("no", "SettleauditId不能为空！");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "工程结算单营改增";
		// 文件路径
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=工程结算单营改增&url=" + webUrl + "&settleauditId=" + zxSaProjectWorkSettleItem.getSettleauditId()+ "&id=" + zxSaProjectWorkSettleItem.getSettleauditId();
		return repEntity.ok(fileUrl);
	}
	
	@Override
	public ResponseEntity exportZxSaProjectSettleAuditGcjsjsb(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
		if (zxSaProjectWorkSettleItem == null || StrUtil.isEmpty(zxSaProjectWorkSettleItem.getSettleauditId())) {
			return repEntity.layerMessage("no", "SettleauditId不能为空！");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "工程类结算单结算表一览";
		// 文件路径
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=" + zxSaProjectWorkSettleItem.getRemark() + "&url=" + webUrl + "&settleauditId=" + zxSaProjectWorkSettleItem.getSettleauditId()+ "&id=" + zxSaProjectWorkSettleItem.getSettleauditId();
		return repEntity.ok(fileUrl);
	}
	
	@Override
	public ResponseEntity exportZxSaProjectSettleAuditGcjsjsbDyb(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
		if (zxSaProjectWorkSettleItem == null || StrUtil.isEmpty(zxSaProjectWorkSettleItem.getSettleauditId())) {
			return repEntity.layerMessage("no", "SettleauditId不能为空！");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "工程类结算单结算表打印版";
		// 文件路径
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=" + zxSaProjectWorkSettleItem.getRemark() + "&url=" + webUrl + "&settleauditId=" + zxSaProjectWorkSettleItem.getSettleauditId()+ "&id=" + zxSaProjectWorkSettleItem.getSettleauditId();
		return repEntity.ok(fileUrl);
	}
	
	@Override
	public ResponseEntity exportZxSaProjectSettleAuditGcjsjsdYgz(ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
		if (zxSaProjectWorkSettleItem == null || StrUtil.isEmpty(zxSaProjectWorkSettleItem.getSettleauditId())) {
			return repEntity.layerMessage("no", "SettleauditId不能为空！");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "工程类结算单结算单营改增一览";
		// 文件路径
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=" + zxSaProjectWorkSettleItem.getRemark() + "&url=" + webUrl + "&settleauditId=" + zxSaProjectWorkSettleItem.getSettleauditId()+ "&id=" + zxSaProjectWorkSettleItem.getSettleauditId();
		return repEntity.ok(fileUrl);
	}
}
