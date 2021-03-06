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
        // ????????????
        PageHelper.startPage(zxSaProjectWorkSettle.getPage(),zxSaProjectWorkSettle.getLimit());
        // ????????????
        List<ZxSaProjectWorkSettle> zxSaProjectWorkSettleList = zxSaProjectWorkSettleMapper.selectByZxSaProjectWorkSettleList(zxSaProjectWorkSettle);
        // ??????????????????
        PageInfo<ZxSaProjectWorkSettle> p = new PageInfo<>(zxSaProjectWorkSettleList);

        for (ZxSaProjectWorkSettle projectWorkSettle : zxSaProjectWorkSettleList) {
        	// ???????????????????????????????????????(???) = ??????????????????????????????(???) - ??????????????????????????????(???)
        	projectWorkSettle.setUpTotalAmt(CalcUtils.calcSubtract(projectWorkSettle.getTotalAmt(), projectWorkSettle.getThisAmt()));
        	
        	// ??????
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(projectWorkSettle.getProjectWorkSettleId());
        	zxErpFile.setOtherType("0");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	projectWorkSettle.setZxErpFileList(zxErpFileList);
        	
        	// ????????????
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
        // ????????????
        ZxSaProjectWorkSettle dbZxSaProjectWorkSettle = zxSaProjectWorkSettleMapper.selectByPrimaryKey(zxSaProjectWorkSettle.getProjectWorkSettleId());
        // ????????????
        if (dbZxSaProjectWorkSettle != null) {
        	// ???????????????????????????????????????(???) = ??????????????????????????????(???) - ??????????????????????????????(???)
        	dbZxSaProjectWorkSettle.setUpTotalAmt(CalcUtils.calcSubtract(dbZxSaProjectWorkSettle.getTotalAmt(), dbZxSaProjectWorkSettle.getThisAmt()));
        	
        	// ??????
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
        	zxErpFile.setOtherType("0");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxSaProjectWorkSettle.setZxErpFileList(zxErpFileList);
        	
        	// ????????????
        	ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
        	zxSaProjectWorkSettleItem.setProjectWorkSettleId(dbZxSaProjectWorkSettle.getProjectWorkSettleId());
        	List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList = (List<ZxSaProjectWorkSettleItem>) zxSaProjectWorkSettleItemServiceImpl.getZxSaProjectWorkSettleItemListByCondition(zxSaProjectWorkSettleItem).getData();
        	dbZxSaProjectWorkSettle.setZxSaProjectWorkSettleItemList(zxSaProjectWorkSettleItemList);
            return repEntity.ok(dbZxSaProjectWorkSettle);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
        	// ??????
        	List<ZxErpFile> zxErpFileList = zxSaProjectWorkSettle.getZxErpFileList();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(zxSaProjectWorkSettle.getProjectWorkSettleId());
		        	zxErpFile.setOtherType("0");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// ????????????
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
           // ??????ID
           dbZxSaProjectWorkSettle.setOrgID(zxSaProjectWorkSettle.getOrgID());
           // ????????????
           dbZxSaProjectWorkSettle.setOrgName(zxSaProjectWorkSettle.getOrgName());
           // ??????ID
           dbZxSaProjectWorkSettle.setContractID(zxSaProjectWorkSettle.getContractID());
           // ???????????????ID
           dbZxSaProjectWorkSettle.setBillID(zxSaProjectWorkSettle.getBillID());
           // ?????????ID
           dbZxSaProjectWorkSettle.setSignedOrders(zxSaProjectWorkSettle.getSignedOrders());
           // ???????????????
           dbZxSaProjectWorkSettle.setSignedNos(zxSaProjectWorkSettle.getSignedNos());
           // ??????????????????(??????)
           dbZxSaProjectWorkSettle.setContractAmt(zxSaProjectWorkSettle.getContractAmt());
           // ???????????????????????????(??????)
           dbZxSaProjectWorkSettle.setChangeAmt(zxSaProjectWorkSettle.getChangeAmt());
           // ??????????????????????????????(???)
           dbZxSaProjectWorkSettle.setThisAmt(zxSaProjectWorkSettle.getThisAmt());
           // ??????????????????????????????(???)
           dbZxSaProjectWorkSettle.setTotalAmt(zxSaProjectWorkSettle.getTotalAmt());
           // ??????????????????
           dbZxSaProjectWorkSettle.setEditTime(zxSaProjectWorkSettle.getEditTime());
           // ????????????
           dbZxSaProjectWorkSettle.setPeriod(zxSaProjectWorkSettle.getPeriod());
           // ????????????ID
           dbZxSaProjectWorkSettle.setComID(zxSaProjectWorkSettle.getComID());
           // ????????????
           dbZxSaProjectWorkSettle.setComName(zxSaProjectWorkSettle.getComName());
           // ??????????????????
           dbZxSaProjectWorkSettle.setComOrders(zxSaProjectWorkSettle.getComOrders());
           // ?????????????????????????????????(???)
           dbZxSaProjectWorkSettle.setThisAmtNoTax(zxSaProjectWorkSettle.getThisAmtNoTax());
           // ????????????????????????(???)
           dbZxSaProjectWorkSettle.setThisAmtTax(zxSaProjectWorkSettle.getThisAmtTax());
           // ??????
           dbZxSaProjectWorkSettle.setRemark(zxSaProjectWorkSettle.getRemark());
           // ??????
           dbZxSaProjectWorkSettle.setSort(zxSaProjectWorkSettle.getSort());
           // ??????
           dbZxSaProjectWorkSettle.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectWorkSettleMapper.updateByPrimaryKey(dbZxSaProjectWorkSettle);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// ????????????????????????????????????(???)?????????????????????(???)????????????????????????(???)??? ?????????????????????(???)???????????????
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
        		
        		// ?????????????????????
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
        			List<ZxSaProjectSettleAuditItem> marginDeductionList = new ArrayList<>(); // ???????????????????????????
        			ZxSaProjectSettleAuditItem returnMarginDeduction = null; // ???????????????
					// ???????????????????????????
					ZxSaProjectSettleAuditItem paymentLowerCase = null;
					ZxSaProjectSettleAuditItem paymentUpperCase = null;
        			for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : dbZxSaProjectSettleAuditItemList) {
						if (StrUtil.equals("100100", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)????????????????????????????????????= ???????????????????????? + ???????????????????????????
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()), new BigDecimal(1), 2) + "");
							// (totalAmt)???????????? = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())), new BigDecimal(1), 2) + "");
							
							dbZxSaProjectSettleAudit.setThisAmt(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
							dbZxSaProjectSettleAudit.setTotalAmt(new BigDecimal(zxSaProjectSettleAuditItem.getTotalAmt()));
							
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if(StrUtil.equals("100200", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)????????????????????????????????????= ???????????????????????? + ???????????????????????????
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()), new BigDecimal(1), 2);
							zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
							// (totalAmt)???????????? = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), thisAmt), new BigDecimal(1), 2)));
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100110", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)???????????????????????????????????????= ??????????????????????????? + ??????????????????????????????
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2) + "");
							// (totalAmt)????????????  = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())), new BigDecimal(1), 2) + "");
							
							dbZxSaProjectSettleAudit.setThisAmtNoTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
							
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100210", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)???????????????????????????????????????= ??????????????????????????? + ??????????????????????????????
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2);
							zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
							// (totalAmt)????????????  = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), thisAmt), new BigDecimal(1), 2)));
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100120", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)??????????????????????????????= ????????????????????????????????????- ???????????????????????????????????????
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()), new BigDecimal(1), 2);
							BigDecimal thisAmtNoTax = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2);
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcSubtract(thisAmt, thisAmtNoTax) + "");
							// (totalAmt)????????????  = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())) + "");
							
							dbZxSaProjectSettleAudit.setThisAmtTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
							
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100220", zxSaProjectSettleAuditItem.getStatisticsType())) {
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()), new BigDecimal(1), 2);
							BigDecimal thisAmtNoTax = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2);
							// (thisAmt)???????????????????????????????????????= ????????????????????????????????????- ???????????????????????????????????????
							zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcSubtract(thisAmt, thisAmtNoTax)));
							// (totalAmt)????????????  = ??????????????????(upAmt) + ????????????(thisAmt)
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
        			
        			// ?????????????????????????????????
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
        			
        			// ?????????????????????????????????????????????
        			
        			// ??????????????????????????????= ?????????{??????????????????-???????????????+??????????????????}
					BigDecimal thisAmt = CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt());
					BigDecimal returnAmt = new BigDecimal(0);
					if (returnMarginDeduction != null) {
						returnAmt = new BigDecimal(returnMarginDeduction.getThisAmt());
					}
    				
					BigDecimal paymentTotalAmt = CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt, totalAmt), returnAmt);
        			// ?????????????????????
        			if (paymentLowerCase != null) {
        				paymentLowerCase.setThisAmt(paymentTotalAmt + "");
        				paymentLowerCase.setTotalAmt(CalcUtils.calcAdd(paymentTotalAmt, paymentLowerCase.getUpAmt()) + "");
        				
        				dbZxSaProjectSettleAudit.setThisPayAmt(paymentTotalAmt);
						dbZxSaProjectSettleAudit.setTotalPayAmt(CalcUtils.calcAdd(paymentTotalAmt, paymentLowerCase.getUpAmt()));
        				
        				paymentLowerCase.setModifyUserInfo(userKey, realName);
						zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(paymentLowerCase);
					}
        			// ?????????????????????
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
        	
        	// ??????
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
        	
        	// ????????????--????????????????????? ???????????????????????????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxSaProjectWorkSettle zxSaProjectWorkSettle : zxSaProjectWorkSettleList) {
        		// ??????
            	ZxErpFile delZxErpFile = new ZxErpFile();
            	delZxErpFile.setOtherId(zxSaProjectWorkSettle.getProjectWorkSettleId());
            	delZxErpFile.setOtherType("0");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);
            	
            	// ????????????
            	ZxSaProjectWorkSettleItem delZxSaProjectWorkSettleItem = new ZxSaProjectWorkSettleItem();
            	delZxSaProjectWorkSettleItem.setProjectWorkSettleId(zxSaProjectWorkSettle.getProjectWorkSettleId());
            	zxSaProjectWorkSettleItemServiceImpl.deleteAllZxSaProjectWorkSettleItem(delZxSaProjectWorkSettleItem);
			}
        	
            return repEntity.ok("sys.data.delete",zxSaProjectWorkSettleList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
