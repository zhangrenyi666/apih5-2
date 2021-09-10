package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditMapper;
import com.apih5.mybatis.dao.ZxSaProjectWorkSettleItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectWorkSettleMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettle;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAudit;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAuditItem;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettle;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettleItem;
import com.apih5.service.ZxSaProjectWorkSettleService;
import com.apih5.utils.DigitalConversionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSaProjectWorkSettleService")
public class ZxSaProjectWorkSettleServiceImpl implements ZxSaProjectWorkSettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectWorkSettleMapper zxSaProjectWorkSettleMapper;
    
    @Autowired(required = true)
    private ZxErpFileServiceImpl zxErpFileServiceImpl;
    
    @Autowired(required = true)
    private ZxSaProjectWorkSettleItemServiceImpl zxSaProjectWorkSettleItemServiceImpl;
    
    @Autowired(required = true)
    private ZxSaProjectWorkSettleItemMapper zxSaProjectWorkSettleItemMapper;
    
    @Autowired(required = true)
    private ZxSaProjectSettleAuditServiceImpl zxSaProjectSettleAuditServiceImpl;
    
    @Autowired(required = true)
    private ZxSaProjectPaySettleServiceImpl zxSaProjectPaySettleServiceImpl;
    
    @Autowired(required = true)
    private ZxSaProjectSettleAuditItemMapper zxSaProjectSettleAuditItemMapper;
    
    @Autowired(required = true)
    private ZxSaProjectSettleAuditMapper zxSaProjectSettleAuditMapper;

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxSaProjectWorkSettleListByCondition(ZxSaProjectWorkSettle zxSaProjectWorkSettle) {
        if (zxSaProjectWorkSettle == null) {
            zxSaProjectWorkSettle = new ZxSaProjectWorkSettle();
        }
        // 分页查询
        PageHelper.startPage(zxSaProjectWorkSettle.getPage(),zxSaProjectWorkSettle.getLimit());
        // 获取数据
        List<ZxSaProjectWorkSettle> zxSaProjectWorkSettleList = zxSaProjectWorkSettleMapper.selectByZxSaProjectWorkSettleList(zxSaProjectWorkSettle);
        // 得到分页信息
        PageInfo<ZxSaProjectWorkSettle> p = new PageInfo<>(zxSaProjectWorkSettleList);

        for (ZxSaProjectWorkSettle projectWorkSettle : zxSaProjectWorkSettleList) {
        	// 上期末累计清单结算含税金额(元) = 累计清单结算含税金额(元) - 本期清单结算含税金额(元)
        	projectWorkSettle.setUpTotalAmt(CalcUtils.calcSubtract(projectWorkSettle.getTotalAmt(), projectWorkSettle.getThisAmt()));
        	
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(projectWorkSettle.getProjectWorkSettleId());
        	zxErpFile.setOtherType("0");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	projectWorkSettle.setZxErpFileList(zxErpFileList);
        	
        	// 子表明细
        	ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
        	zxSaProjectWorkSettleItem.setProjectWorkSettleId(projectWorkSettle.getProjectWorkSettleId());
        	List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList = (List<ZxSaProjectWorkSettleItem>) zxSaProjectWorkSettleItemServiceImpl.getZxSaProjectWorkSettleItemListByCondition(zxSaProjectWorkSettleItem).getData();
        	projectWorkSettle.setZxSaProjectWorkSettleItemList(zxSaProjectWorkSettleItemList);
		}
        
        return repEntity.okList(zxSaProjectWorkSettleList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxSaProjectWorkSettleDetail(ZxSaProjectWorkSettle zxSaProjectWorkSettle) {
        if (zxSaProjectWorkSettle == null) {
            zxSaProjectWorkSettle = new ZxSaProjectWorkSettle();
        }
        // 获取数据
        ZxSaProjectWorkSettle dbZxSaProjectWorkSettle = zxSaProjectWorkSettleMapper.selectByPrimaryKey(zxSaProjectWorkSettle.getProjectWorkSettleId());
        // 数据存在
        if (dbZxSaProjectWorkSettle != null) {
        	// 上期末累计清单结算含税金额(元) = 累计清单结算含税金额(元) - 本期清单结算含税金额(元)
        	dbZxSaProjectWorkSettle.setUpTotalAmt(CalcUtils.calcSubtract(dbZxSaProjectWorkSettle.getTotalAmt(), dbZxSaProjectWorkSettle.getThisAmt()));
        	
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
        	zxErpFile.setOtherType("0");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxSaProjectWorkSettle.setZxErpFileList(zxErpFileList);
        	
        	// 子表明细
        	ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
        	zxSaProjectWorkSettleItem.setProjectWorkSettleId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
        	List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList = (List<ZxSaProjectWorkSettleItem>) zxSaProjectWorkSettleItemServiceImpl.getZxSaProjectWorkSettleItemListByCondition(zxSaProjectWorkSettleItem).getData();
        	dbZxSaProjectWorkSettle.setZxSaProjectWorkSettleItemList(zxSaProjectWorkSettleItemList);
            return repEntity.ok(dbZxSaProjectWorkSettle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaProjectWorkSettle(ZxSaProjectWorkSettle zxSaProjectWorkSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectWorkSettle.setProjectWorkSettleId(UuidUtil.generate());
        zxSaProjectWorkSettle.setCreateUserInfo(userKey, realName);
        int flag = zxSaProjectWorkSettleMapper.insert(zxSaProjectWorkSettle);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxSaProjectWorkSettle.getZxErpFileList();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(zxSaProjectWorkSettle.getProjectWorkSettleId());
		        	zxErpFile.setOtherType("0");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// 子表明细
        	List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList = zxSaProjectWorkSettle.getZxSaProjectWorkSettleItemList();
        	if (zxSaProjectWorkSettleItemList != null && zxSaProjectWorkSettleItemList.size() > 0) {
				for (ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem : zxSaProjectWorkSettleItemList) {
					zxSaProjectWorkSettleItem.setProjectWorkSettleId(zxSaProjectWorkSettle.getProjectWorkSettleId());
					zxSaProjectWorkSettleItemServiceImpl.saveZxSaProjectWorkSettleItem(zxSaProjectWorkSettleItem);
				}
			}
            return repEntity.ok("sys.data.sava", zxSaProjectWorkSettle);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity updateZxSaProjectWorkSettle(ZxSaProjectWorkSettle zxSaProjectWorkSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        
        ZxSaProjectWorkSettle dbZxSaProjectWorkSettle = zxSaProjectWorkSettleMapper.selectByPrimaryKey(zxSaProjectWorkSettle.getProjectWorkSettleId());
        if (dbZxSaProjectWorkSettle != null && StrUtil.isNotEmpty(dbZxSaProjectWorkSettle.getProjectWorkSettleId())) {
           // 项目ID
           dbZxSaProjectWorkSettle.setOrgID(zxSaProjectWorkSettle.getOrgID());
           // 项目名称
           dbZxSaProjectWorkSettle.setOrgName(zxSaProjectWorkSettle.getOrgName());
           // 合同ID
           dbZxSaProjectWorkSettle.setContractID(zxSaProjectWorkSettle.getContractID());
           // 工程结算表ID
           dbZxSaProjectWorkSettle.setBillID(zxSaProjectWorkSettle.getBillID());
           // 签认单ID
           dbZxSaProjectWorkSettle.setSignedOrders(zxSaProjectWorkSettle.getSignedOrders());
           // 签认单编号
           dbZxSaProjectWorkSettle.setSignedNos(zxSaProjectWorkSettle.getSignedNos());
           // 含税合同金额(万元)
           dbZxSaProjectWorkSettle.setContractAmt(zxSaProjectWorkSettle.getContractAmt());
           // 变更后含税合同金额(万元)
           dbZxSaProjectWorkSettle.setChangeAmt(zxSaProjectWorkSettle.getChangeAmt());
           // 本期清单结算含税金额(元)
           dbZxSaProjectWorkSettle.setThisAmt(zxSaProjectWorkSettle.getThisAmt());
           // 累计清单结算含税金额(元)
           dbZxSaProjectWorkSettle.setTotalAmt(zxSaProjectWorkSettle.getTotalAmt());
           // 最后编辑时间
           dbZxSaProjectWorkSettle.setEditTime(zxSaProjectWorkSettle.getEditTime());
           // 结算期次
           dbZxSaProjectWorkSettle.setPeriod(zxSaProjectWorkSettle.getPeriod());
           // 所属公司ID
           dbZxSaProjectWorkSettle.setComID(zxSaProjectWorkSettle.getComID());
           // 所属公司
           dbZxSaProjectWorkSettle.setComName(zxSaProjectWorkSettle.getComName());
           // 所属公司排序
           dbZxSaProjectWorkSettle.setComOrders(zxSaProjectWorkSettle.getComOrders());
           // 本期清单结算不含税金额(元)
           dbZxSaProjectWorkSettle.setThisAmtNoTax(zxSaProjectWorkSettle.getThisAmtNoTax());
           // 本期清单结算税额(元)
           dbZxSaProjectWorkSettle.setThisAmtTax(zxSaProjectWorkSettle.getThisAmtTax());
           // 备注
           dbZxSaProjectWorkSettle.setRemark(zxSaProjectWorkSettle.getRemark());
           // 排序
           dbZxSaProjectWorkSettle.setSort(zxSaProjectWorkSettle.getSort());
           // 共通
           dbZxSaProjectWorkSettle.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectWorkSettleMapper.updateByPrimaryKey(dbZxSaProjectWorkSettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改结算单（本期结算金额(元)、开累结算金额(元)、本期应支付金额(元)、 开累应支付金额(元)及子项金额
        	ZxSaProjectSettleAudit zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
        	String userId = TokenUtils.getUserId(request);
            String ext1 = TokenUtils.getExt1(request);
    		
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
            	zxSaProjectSettleAudit.setOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            	zxSaProjectSettleAudit.setOrgID(dbZxSaProjectWorkSettle.getComID());
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            	zxSaProjectSettleAudit.setOrgID(dbZxSaProjectWorkSettle.getOrgID());
            }
        	zxSaProjectSettleAudit.setContractID(dbZxSaProjectWorkSettle.getContractID());
        	zxSaProjectSettleAudit.setPeriod(dbZxSaProjectWorkSettle.getPeriod());
        	
        	List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = (List<ZxSaProjectSettleAudit>) zxSaProjectSettleAuditServiceImpl.getZxSaProjectSettleAuditListByCondition(zxSaProjectSettleAudit).getData();
        	if (zxSaProjectSettleAuditList != null && zxSaProjectSettleAuditList.size() > 0) {
        		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = zxSaProjectSettleAuditList.get(0);
        		
        		// 查询支付项信息
        		ZxSaProjectPaySettle zxSaProjectPaySettle = new ZxSaProjectPaySettle();
        		zxSaProjectPaySettle.setOrgID(dbZxSaProjectWorkSettle.getOrgID());
        		zxSaProjectPaySettle.setContractID(dbZxSaProjectWorkSettle.getContractID());
        		zxSaProjectPaySettle.setPeriod(dbZxSaProjectWorkSettle.getPeriod());
        		List<ZxSaProjectPaySettle> zxSaProjectPaySettleList = (List<ZxSaProjectPaySettle>) zxSaProjectPaySettleServiceImpl.getZxSaProjectPaySettleListByCondition(zxSaProjectPaySettle).getData();
        		ZxSaProjectPaySettle dbZxSaProjectPaySettle = null;
        		if (zxSaProjectPaySettleList != null && zxSaProjectPaySettleList.size() > 0) {
        			dbZxSaProjectPaySettle = zxSaProjectPaySettleList.get(0);
				} else {
					dbZxSaProjectPaySettle = new ZxSaProjectPaySettle();
				}
        		
        		List<ZxSaProjectSettleAuditItem> dbZxSaProjectSettleAuditItemList = dbZxSaProjectSettleAudit.getProjectSettleAuditItemList();
        		if (dbZxSaProjectSettleAuditItemList != null && dbZxSaProjectSettleAuditItemList.size() > 0) {
        			List<ZxSaProjectSettleAuditItem> marginDeductionList = new ArrayList<>(); // 其中扣除保证金合计
        			ZxSaProjectSettleAuditItem returnMarginDeduction = null; // 返还保证金
					// 应付工程款（小写）
					ZxSaProjectSettleAuditItem paymentLowerCase = null;
					ZxSaProjectSettleAuditItem paymentUpperCase = null;
        			for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : dbZxSaProjectSettleAuditItemList) {
						if (StrUtil.equals("100100", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)合计含税结算金额（小写）= 清单结算含税金额 + 支付项结算含税金额
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()), new BigDecimal(1), 2) + "");
							// (totalAmt)开累金额 = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())), new BigDecimal(1), 2) + "");
							
							dbZxSaProjectSettleAudit.setThisAmt(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
							dbZxSaProjectSettleAudit.setTotalAmt(new BigDecimal(zxSaProjectSettleAuditItem.getTotalAmt()));
							
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if(StrUtil.equals("100200", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)合计含税结算金额（大写）= 清单结算含税金额 + 支付项结算含税金额
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()), new BigDecimal(1), 2);
							zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
							// (totalAmt)开累金额 = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), thisAmt), new BigDecimal(1), 2)));
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100110", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)合计不含税结算金额（小写）= 清单结算不含税金额 + 支付项结算不含税金额
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2) + "");
							// (totalAmt)开累金额  = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())), new BigDecimal(1), 2) + "");
							
							dbZxSaProjectSettleAudit.setThisAmtNoTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
							
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100210", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)合计不含税结算金额（大写）= 清单结算不含税金额 + 支付项结算不含税金额
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2);
							zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
							// (totalAmt)开累金额  = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), thisAmt), new BigDecimal(1), 2)));
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100120", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)合计结算税额（小写）= 合计含税结算金额（小写）- 合计不含税结算金额（小写）
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()), new BigDecimal(1), 2);
							BigDecimal thisAmtNoTax = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2);
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcSubtract(thisAmt, thisAmtNoTax) + "");
							// (totalAmt)开累金额  = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())) + "");
							
							dbZxSaProjectSettleAudit.setThisAmtTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
							
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100220", zxSaProjectSettleAuditItem.getStatisticsType())) {
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()), new BigDecimal(1), 2);
							BigDecimal thisAmtNoTax = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2);
							// (thisAmt)合计不含税结算金额（大写）= 合计含税结算金额（小写）- 合计不含税结算金额（小写）
							zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(thisAmt, thisAmtNoTax)));
							// (totalAmt)开累金额  = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), CalcUtils.calcSubtract(thisAmt, thisAmtNoTax))));
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equalsAny(zxSaProjectSettleAuditItem.getStatisticsType(), "100300", "100400")) {
							marginDeductionList.add(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals(zxSaProjectSettleAuditItem.getStatisticsType(), "100500")) {
							returnMarginDeduction = zxSaProjectSettleAuditItem;
	        			} else if (StrUtil.equals(zxSaProjectSettleAuditItem.getStatisticsType(), "100700")) {
	        				paymentLowerCase = zxSaProjectSettleAuditItem;
	        			} else if (StrUtil.equals(zxSaProjectSettleAuditItem.getStatisticsType(), "100800")) {
	        				paymentUpperCase = zxSaProjectSettleAuditItem;
	        			}
					}
        			
        			// 处理其中扣除保证金合计
        			ZxSaProjectSettleAuditItem totalMarginDeduction = null;
        			BigDecimal totalAmt = new BigDecimal(0);
        			for (ZxSaProjectSettleAuditItem settleAuditItem : marginDeductionList) {
						if (StrUtil.equals("100300", settleAuditItem.getStatisticsType())) {
							totalMarginDeduction = settleAuditItem;
						} else {
							BigDecimal thisAmt = CalcUtils.calcMultiply(dbZxSaProjectWorkSettle.getThisAmt(), CalcUtils.calcDivide(settleAuditItem.getRate(), new BigDecimal(100)));
							totalAmt = CalcUtils.calcAdd(totalAmt, thisAmt);
							settleAuditItem.setThisAmt(thisAmt + "");
							settleAuditItem.setTotalAmt(CalcUtils.calcAdd(thisAmt, settleAuditItem.getUpAmt()) + "");
							settleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(settleAuditItem);
						}
					}
        			if (totalMarginDeduction != null) {
        				totalMarginDeduction.setThisAmt(totalAmt + "");
        				totalMarginDeduction.setTotalAmt(CalcUtils.calcAdd(totalAmt, totalMarginDeduction.getUpAmt()) + "");
        				totalMarginDeduction.setModifyUserInfo(userKey, realName);
						zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(totalMarginDeduction);
					}
        			
        			// 处理返还保证金合计手填无需处理
        			
        			// 应付工程款（大小写）= 本期的{合计含税金额-扣除保证金+返还的保证金}
					BigDecimal thisAmt = CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt());
					BigDecimal returnAmt = new BigDecimal(0);
					if (returnMarginDeduction != null) {
						returnAmt = new BigDecimal(returnMarginDeduction.getThisAmt());
					}
    				
					BigDecimal paymentTotalAmt = CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt, totalAmt), returnAmt);
        			// 应付工程款小写
        			if (paymentLowerCase != null) {
        				paymentLowerCase.setThisAmt(paymentTotalAmt + "");
        				paymentLowerCase.setTotalAmt(CalcUtils.calcAdd(paymentTotalAmt, paymentLowerCase.getUpAmt()) + "");
        				
        				dbZxSaProjectSettleAudit.setThisPayAmt(paymentTotalAmt);
						dbZxSaProjectSettleAudit.setTotalPayAmt(CalcUtils.calcAdd(paymentTotalAmt, paymentLowerCase.getUpAmt()));
        				
        				paymentLowerCase.setModifyUserInfo(userKey, realName);
						zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(paymentLowerCase);
					}
        			// 应付工程款大写
        			if (paymentUpperCase != null) {
        				paymentUpperCase.setThisAmt(DigitalConversionUtil.digitUppercase(paymentTotalAmt));
        				paymentUpperCase.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(paymentTotalAmt, paymentUpperCase.getUpAmt())));
        				paymentUpperCase.setModifyUserInfo(userKey, realName);
        				zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(paymentUpperCase);
        			}
        			
        			dbZxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);
        			zxSaProjectSettleAuditMapper.updateByPrimaryKey(dbZxSaProjectSettleAudit);
				}
			}
        	
        	// 附件
        	ZxErpFile delZxErpFile = new ZxErpFile();
        	delZxErpFile.setOtherId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
        	delZxErpFile.setOtherType("0");
        	zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxSaProjectWorkSettle.getZxErpFileList();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
		        	zxErpFile.setOtherType("0");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// 子表明细--前端无新增删除 此处不删除直接修改
//        	ZxSaProjectWorkSettleItem delZxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
//        	delZxSaProjectWorkSettleItem.setProjectWorkSettleId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
//        	zxSaProjectWorkSettleItemServiceImpl.deleteAllZxSaProjectWorkSettleItem(delZxSaProjectWorkSettleItem);
        	
        	List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList = zxSaProjectWorkSettle.getZxSaProjectWorkSettleItemList();
        	if (zxSaProjectWorkSettleItemList != null && zxSaProjectWorkSettleItemList.size() > 0) {
				for (ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem : zxSaProjectWorkSettleItemList) {
					zxSaProjectWorkSettleItem.setModifyUserInfo(userKey, realName);
					zxSaProjectWorkSettleItemMapper.updateByPrimaryKey(zxSaProjectWorkSettleItem);
					
//					zxSaProjectWorkSettleItem.setProjectWorkSettleId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
//					zxSaProjectWorkSettleItemServiceImpl.saveZxSaProjectWorkSettleItem(zxSaProjectWorkSettleItem);
				}
			}
        	
            return repEntity.ok("sys.data.update",zxSaProjectWorkSettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectWorkSettle(List<ZxSaProjectWorkSettle> zxSaProjectWorkSettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectWorkSettleList != null && zxSaProjectWorkSettleList.size() > 0) {
           ZxSaProjectWorkSettle zxSaProjectWorkSettle = new ZxSaProjectWorkSettle();
           zxSaProjectWorkSettle.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectWorkSettleMapper.batchDeleteUpdateZxSaProjectWorkSettle(zxSaProjectWorkSettleList, zxSaProjectWorkSettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxSaProjectWorkSettle zxSaProjectWorkSettle : zxSaProjectWorkSettleList) {
        		// 附件
            	ZxErpFile delZxErpFile = new ZxErpFile();
            	delZxErpFile.setOtherId(zxSaProjectWorkSettle.getProjectWorkSettleId());
            	delZxErpFile.setOtherType("0");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);
            	
            	// 子表明细
            	ZxSaProjectWorkSettleItem delZxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
            	delZxSaProjectWorkSettleItem.setProjectWorkSettleId(zxSaProjectWorkSettle.getProjectWorkSettleId());
            	zxSaProjectWorkSettleItemServiceImpl.deleteAllZxSaProjectWorkSettleItem(delZxSaProjectWorkSettleItem);
			}
        	
            return repEntity.ok("sys.data.delete",zxSaProjectWorkSettleList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
