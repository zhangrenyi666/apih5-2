package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
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
import com.apih5.mybatis.dao.ZxSaProjectPaySettleItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectPaySettleMapper;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettle;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettleItem;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAudit;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAuditItem;
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettle;
import com.apih5.service.ZxErpFileService;
import com.apih5.service.ZxSaProjectPaySettleItemService;
import com.apih5.service.ZxSaProjectPaySettleService;
import com.apih5.service.ZxSaProjectSettleAuditService;
import com.apih5.utils.DigitalConversionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSaProjectPaySettleService")
public class ZxSaProjectPaySettleServiceImpl implements ZxSaProjectPaySettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectPaySettleMapper zxSaProjectPaySettleMapper;
    
    @Autowired(required = true)
    private ZxErpFileService zxErpFileService;
    
    @Autowired(required = true)
    private ZxSaProjectPaySettleItemService zxSaProjectPaySettleItemService;
    
    @Autowired(required = true)
    private ZxSaProjectSettleAuditService zxSaProjectSettleAuditService;
    
    @Autowired(required = true)
    private ZxSaProjectWorkSettleServiceImpl zxSaProjectWorkSettleServiceImpl;

    @Autowired(required = true)
    private ZxSaProjectSettleAuditItemMapper zxSaProjectSettleAuditItemMapper;
    
    @Autowired(required = true)
    private ZxSaProjectSettleAuditMapper zxSaProjectSettleAuditMapper;
    
    @Autowired(required = true)
    private ZxSaProjectPaySettleItemMapper zxSaProjectPaySettleItemMapper;

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxSaProjectPaySettleListByCondition(ZxSaProjectPaySettle zxSaProjectPaySettle) {
        if (zxSaProjectPaySettle == null) {
            zxSaProjectPaySettle = new ZxSaProjectPaySettle();
        }
        // ????????????
        PageHelper.startPage(zxSaProjectPaySettle.getPage(),zxSaProjectPaySettle.getLimit());
        // ????????????
        List<ZxSaProjectPaySettle> zxSaProjectPaySettleList = zxSaProjectPaySettleMapper.selectByZxSaProjectPaySettleList(zxSaProjectPaySettle);
        // ??????????????????
        PageInfo<ZxSaProjectPaySettle> p = new PageInfo<>(zxSaProjectPaySettleList);
        
        for (ZxSaProjectPaySettle projectPaySettle : zxSaProjectPaySettleList) {
        	// ??????
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(projectPaySettle.getProjectPaySettleId());
        	zxErpFile.setOtherType("0");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileService.getZxErpFileListByCondition(zxErpFile).getData();
        	projectPaySettle.setZxErpFileList(zxErpFileList);
        	
        	// ????????????
        	ZxSaProjectPaySettleItem zxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
        	zxSaProjectPaySettleItem.setProjectPaySettleId(projectPaySettle.getProjectPaySettleId());
        	List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList = (List<ZxSaProjectPaySettleItem>) zxSaProjectPaySettleItemService.getZxSaProjectPaySettleItemListByCondition(zxSaProjectPaySettleItem).getData();
        	projectPaySettle.setProjectPaySettleItemList(zxSaProjectPaySettleItemList);
		}

        return repEntity.okList(zxSaProjectPaySettleList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxSaProjectPaySettleDetail(ZxSaProjectPaySettle zxSaProjectPaySettle) {
        if (zxSaProjectPaySettle == null) {
            zxSaProjectPaySettle = new ZxSaProjectPaySettle();
        }
        // ????????????
        ZxSaProjectPaySettle dbZxSaProjectPaySettle = zxSaProjectPaySettleMapper.selectByPrimaryKey(zxSaProjectPaySettle.getProjectPaySettleId());
        // ????????????
        if (dbZxSaProjectPaySettle != null) {
        	// ??????
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxSaProjectPaySettle.getProjectPaySettleId());
        	zxErpFile.setOtherType("0");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileService.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxSaProjectPaySettle.setZxErpFileList(zxErpFileList);
        	
        	// ????????????
        	ZxSaProjectPaySettleItem zxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
        	zxSaProjectPaySettleItem.setProjectPaySettleId(dbZxSaProjectPaySettle.getProjectPaySettleId());
        	List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList = (List<ZxSaProjectPaySettleItem>) zxSaProjectPaySettleItemService.getZxSaProjectPaySettleItemListByCondition(zxSaProjectPaySettleItem).getData();
        	dbZxSaProjectPaySettle.setProjectPaySettleItemList(zxSaProjectPaySettleItemList);
            return repEntity.ok(dbZxSaProjectPaySettle);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

	@Override
    public ResponseEntity saveZxSaProjectPaySettle(ZxSaProjectPaySettle zxSaProjectPaySettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectPaySettle.setProjectPaySettleId(UuidUtil.generate());
        zxSaProjectPaySettle.setCreateUserInfo(userKey, realName);
        int flag = zxSaProjectPaySettleMapper.insert(zxSaProjectPaySettle);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// ??????
        	List<ZxErpFile> zxErpFileList = zxSaProjectPaySettle.getZxErpFileList();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(zxSaProjectPaySettle.getProjectPaySettleId());
		        	zxErpFile.setOtherType("0");
		        	zxErpFileService.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// ????????????
        	List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList = zxSaProjectPaySettle.getProjectPaySettleItemList();
        	if (zxSaProjectPaySettleItemList != null && zxSaProjectPaySettleItemList.size() > 0) {
				for (ZxSaProjectPaySettleItem zxSaProjectPaySettleItem : zxSaProjectPaySettleItemList) {
					zxSaProjectPaySettleItem.setProjectPaySettleId(zxSaProjectPaySettle.getProjectPaySettleId());
					zxSaProjectPaySettleItemService.saveZxSaProjectPaySettleItem(zxSaProjectPaySettleItem);
				}
			}
            return repEntity.ok("sys.data.sava", zxSaProjectPaySettle);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity updateZxSaProjectPaySettle(ZxSaProjectPaySettle zxSaProjectPaySettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList = zxSaProjectPaySettle.getProjectPaySettleItemList();
        
        int flag = 0;
        ZxSaProjectPaySettle dbZxSaProjectPaySettle = zxSaProjectPaySettleMapper.selectByPrimaryKey(zxSaProjectPaySettle.getProjectPaySettleId());
        if (dbZxSaProjectPaySettle != null && StrUtil.isNotEmpty(dbZxSaProjectPaySettle.getProjectPaySettleId())) {
           // ??????ID
           dbZxSaProjectPaySettle.setOrgID(zxSaProjectPaySettle.getOrgID());
           // ????????????
           dbZxSaProjectPaySettle.setOrgName(zxSaProjectPaySettle.getOrgName());
           // ??????ID
           dbZxSaProjectPaySettle.setContractID(zxSaProjectPaySettle.getContractID());
           // ???????????????ID
           dbZxSaProjectPaySettle.setBillID(zxSaProjectPaySettle.getBillID());
           
           if (zxSaProjectPaySettleItemList != null && zxSaProjectPaySettleItemList.size() > 0) {
				BigDecimal thisAmt = new BigDecimal(0); // ???????????????????????????(???)
				BigDecimal thisAmtNoTax = new BigDecimal(0); // ????????????????????????????????????(???)
				BigDecimal materialAmt = new BigDecimal(0); // ?????????????????????????????????(???)
				BigDecimal machineAmt = new BigDecimal(0); // ?????????????????????????????????(???)
				BigDecimal tempAmt = new BigDecimal(0); // ?????????????????????????????????(???)
				BigDecimal fineAmt = new BigDecimal(0); // ??????????????????????????????(???)
				BigDecimal recoupAmt = new BigDecimal(0); // ??????????????????????????????(???)
				BigDecimal otherAmt = new BigDecimal(0); // ??????????????????????????????(???)

				for (ZxSaProjectPaySettleItem zxSaProjectPaySettleItem : zxSaProjectPaySettleItemList) {
					// ?????????????????????????????????
					BigDecimal thisAmtNew = CalcUtils.calcMultiply(zxSaProjectPaySettleItem.getQty(), zxSaProjectPaySettleItem.getPrice());
					zxSaProjectPaySettleItem.setThisAmt(thisAmtNew);
					
					// ????????????????????????????????????
					BigDecimal thisAmtNoTaxNew = new BigDecimal(0);
					// ??????????????????(???)
					BigDecimal thisAmtTax = new BigDecimal(0); 
					
					if (StrUtil.isNotEmpty(zxSaProjectPaySettleItem.getTaxRate())) {
						thisAmtNoTaxNew = CalcUtils.calcMultiply(CalcUtils.calcDivide(thisAmtNew, CalcUtils.calcAdd(new BigDecimal(100), new BigDecimal(zxSaProjectPaySettleItem.getTaxRate())), 4), new BigDecimal(100));
						zxSaProjectPaySettleItem.setThisAmtNoTax(thisAmtNoTaxNew);
						
						thisAmtTax = CalcUtils.calcSubtract(thisAmtNew, thisAmtNoTaxNew);
						zxSaProjectPaySettleItem.setThisAmtTax(thisAmtTax);
					}
					thisAmt = CalcUtils.calcAdd(thisAmt, thisAmtNew);
					thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, thisAmtNoTaxNew);
					
					if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10001", "????????????")) { // ????????????
						recoupAmt = CalcUtils.calcAdd(recoupAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10002", "???????????????", "???????????????")) { // ???????????????---???????????????
						materialAmt = CalcUtils.calcAdd(materialAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10003", "????????????")) { // ????????????
						fineAmt = CalcUtils.calcAdd(fineAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10004", "??????????????????", "???????????????", "???????????????")) { // ??????????????????---???????????????---???????????????
						machineAmt = CalcUtils.calcAdd(machineAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10005", "??????????????????", "???????????????")) { // ??????????????????---???????????????
						tempAmt = CalcUtils.calcAdd(tempAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10006", "????????????")) { // ????????????
						otherAmt = CalcUtils.calcAdd(otherAmt, zxSaProjectPaySettleItem.getThisAmt());
					}
				}

				// ???????????????????????????(???)
				dbZxSaProjectPaySettle.setThisAmt(thisAmt);
				// ????????????????????????????????????(???)
				dbZxSaProjectPaySettle.setThisAmtNoTax(thisAmtNoTax);
				// ??????????????????
				dbZxSaProjectPaySettle.setThisAmtTax(CalcUtils.calcSubtract(thisAmt, thisAmtNoTax));
				// ???????????????????????????(???)
				dbZxSaProjectPaySettle.setTotalAmt(CalcUtils.calcAdd(thisAmt, dbZxSaProjectPaySettle.getUpAmt()));
				// ?????????????????????????????????(???)
				dbZxSaProjectPaySettle.setMaterialAmt(materialAmt);
				// ?????????????????????????????????(???)
				dbZxSaProjectPaySettle.setMachineAmt(machineAmt);
				// ?????????????????????????????????(???)
				dbZxSaProjectPaySettle.setTempAmt(tempAmt);
				// ??????????????????????????????(???)
				dbZxSaProjectPaySettle.setFineAmt(fineAmt);
				// ??????????????????????????????(???)
				dbZxSaProjectPaySettle.setRecoupAmt(recoupAmt);
				// ??????????????????????????????(???)
				dbZxSaProjectPaySettle.setOtherAmt(otherAmt);
           } else {
        	   // ???????????????????????????(???)
               dbZxSaProjectPaySettle.setThisAmt(new BigDecimal(0));
               // ????????????????????????????????????(???)
               dbZxSaProjectPaySettle.setThisAmtNoTax(new BigDecimal(0));
               // ??????????????????
               dbZxSaProjectPaySettle.setThisAmtTax(new BigDecimal(0));
               // ???????????????????????????(???)
               dbZxSaProjectPaySettle.setTotalAmt(dbZxSaProjectPaySettle.getUpAmt());
               // ?????????????????????????????????(???)
               dbZxSaProjectPaySettle.setMaterialAmt(new BigDecimal(0));
               // ?????????????????????????????????(???)
               dbZxSaProjectPaySettle.setMachineAmt(new BigDecimal(0));
               // ?????????????????????????????????(???)
               dbZxSaProjectPaySettle.setTempAmt(new BigDecimal(0));
               // ??????????????????????????????(???)
               dbZxSaProjectPaySettle.setFineAmt(new BigDecimal(0));
               // ??????????????????????????????(???)
               dbZxSaProjectPaySettle.setRecoupAmt(new BigDecimal(0));
               // ??????????????????????????????(???)
               dbZxSaProjectPaySettle.setOtherAmt(new BigDecimal(0));
           }
           
           // ????????????????????????????????????(???)
           dbZxSaProjectPaySettle.setUpAmt(zxSaProjectPaySettle.getUpAmt());
           // ????????????????????????????????????(???)
           dbZxSaProjectPaySettle.setUpMaterialAmt(zxSaProjectPaySettle.getUpMaterialAmt());
           // ????????????????????????????????????(???)
           dbZxSaProjectPaySettle.setUpMachineAmt(zxSaProjectPaySettle.getUpMachineAmt());
           // ????????????????????????????????????(???)
           dbZxSaProjectPaySettle.setUpTempAmt(zxSaProjectPaySettle.getUpTempAmt());
           // ?????????????????????????????????(???)
           dbZxSaProjectPaySettle.setUpFineAmt(zxSaProjectPaySettle.getUpFineAmt());
           // ?????????????????????????????????(???)
           dbZxSaProjectPaySettle.setUpRecoupAmt(zxSaProjectPaySettle.getUpRecoupAmt());
           // ?????????????????????????????????(???)
           dbZxSaProjectPaySettle.setUpOtherAmt(zxSaProjectPaySettle.getUpOtherAmt());
           // ??????????????????
           dbZxSaProjectPaySettle.setEditTime(new Date());
           // ????????????
           dbZxSaProjectPaySettle.setPeriod(zxSaProjectPaySettle.getPeriod());
           // ????????????ID
           dbZxSaProjectPaySettle.setComID(zxSaProjectPaySettle.getComID());
           // ????????????
           dbZxSaProjectPaySettle.setComName(zxSaProjectPaySettle.getComName());
           // ??????????????????
           dbZxSaProjectPaySettle.setComOrders(zxSaProjectPaySettle.getComOrders());
           // ??????
           dbZxSaProjectPaySettle.setTaxRate(zxSaProjectPaySettle.getTaxRate());
           // ??????
           dbZxSaProjectPaySettle.setRemark(zxSaProjectPaySettle.getRemark());
           // ??????
           dbZxSaProjectPaySettle.setSort(zxSaProjectPaySettle.getSort());
           // ??????
           dbZxSaProjectPaySettle.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectPaySettleMapper.updateByPrimaryKey(dbZxSaProjectPaySettle);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	/*
			 * ??????????????????thisAmt ??????????????????(???)??? totalAmt ??????????????????(???)???thisPayAmt ?????????????????????(???)???totalPayAmt
			 * ?????????????????????(???), thisAmtNoTax ???????????????????????????(???), thisAmtTax ??????????????????(???)?????????
			 */
        	ZxSaProjectSettleAudit selectUpProjectSettleAudit = new ZxSaProjectSettleAudit();
            String userId = TokenUtils.getUserId(request);
            String ext1 = TokenUtils.getExt1(request);
    		
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
    			selectUpProjectSettleAudit.setOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
    			selectUpProjectSettleAudit.setOrgID(zxSaProjectPaySettle.getComID());
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            	selectUpProjectSettleAudit.setOrgID(zxSaProjectPaySettle.getOrgID());
            }
        	selectUpProjectSettleAudit.setContractID(zxSaProjectPaySettle.getContractID());
        	selectUpProjectSettleAudit.setPeriod(zxSaProjectPaySettle.getPeriod());
        	List<ZxSaProjectSettleAudit> projectSettleAuditList = (List<ZxSaProjectSettleAudit>) zxSaProjectSettleAuditService.getZxSaProjectSettleAuditListByCondition(selectUpProjectSettleAudit).getData();
        	if (projectSettleAuditList != null && projectSettleAuditList.size() > 0) {
        		ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = projectSettleAuditList.get(0);
        		// ??????????????????
        		ZxSaProjectWorkSettle zxSaProjectWorkSettle = new ZxSaProjectWorkSettle();
        		zxSaProjectWorkSettle.setOrgID(dbZxSaProjectPaySettle.getOrgID());
        		zxSaProjectWorkSettle.setContractID(dbZxSaProjectPaySettle.getContractID());
        		zxSaProjectWorkSettle.setPeriod(dbZxSaProjectPaySettle.getPeriod());
        		List<ZxSaProjectWorkSettle> zxSaProjectWorkSettleList = (List<ZxSaProjectWorkSettle>) zxSaProjectWorkSettleServiceImpl.getZxSaProjectWorkSettleListByCondition(zxSaProjectWorkSettle).getData();
        		ZxSaProjectWorkSettle dbZxSaProjectWorkSettle = null;
        		if (zxSaProjectWorkSettleList != null && zxSaProjectWorkSettleList.size() > 0) {
        			dbZxSaProjectWorkSettle = zxSaProjectWorkSettleList.get(0);
				} else {
					dbZxSaProjectWorkSettle = new ZxSaProjectWorkSettle();
				}
        		
        		List<ZxSaProjectSettleAuditItem> dbZxSaProjectSettleAuditItemList = dbZxSaProjectSettleAudit.getProjectSettleAuditItemList();
        		if (dbZxSaProjectSettleAuditItemList != null && dbZxSaProjectSettleAuditItemList.size() > 0) {
        			// ???????????????????????????---???????????????
        			ZxSaProjectSettleAuditItem marginDeduction = null, returnMarginDeduction = null; 
					// ??????????????????????????????
        			ZxSaProjectSettleAuditItem paymentUpperCase = null, paymentLowerCase = null;
        			for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : dbZxSaProjectSettleAuditItemList) {
						if (StrUtil.equals("100100", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)????????????????????????????????????= ???????????????????????? + ???????????????????????????
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()) + "");
							// (totalAmt)???????????? = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())) + "");
							
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
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()) + "");
							// (totalAmt)????????????  = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())) + "");
							
							dbZxSaProjectSettleAudit.setThisAmtNoTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
							
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100210", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)???????????????????????????????????????= ??????????????????????????? + ??????????????????????????????
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2);
							zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
							// (totalAmt)????????????  = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), thisAmt)));
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100120", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)??????????????????????????????= ????????????????????????????????????- ???????????????????????????????????????
							BigDecimal thisAmt = CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt());
							BigDecimal thisAmtNoTax = CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax());
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcSubtract(thisAmt, thisAmtNoTax) + "");
							// (totalAmt)????????????  = ??????????????????(upAmt) + ????????????(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())), new BigDecimal(1), 2) + "");
							
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
						} else if (StrUtil.equals(zxSaProjectSettleAuditItem.getStatisticsType(), "100300")) {
							marginDeduction = zxSaProjectSettleAuditItem;
						} else if (StrUtil.equals(zxSaProjectSettleAuditItem.getStatisticsType(), "100500")) {
							returnMarginDeduction = zxSaProjectSettleAuditItem;
	        			} else if (StrUtil.equals(zxSaProjectSettleAuditItem.getStatisticsType(), "100700")) {
	        				paymentLowerCase = zxSaProjectSettleAuditItem;
	        			} else if (StrUtil.equals(zxSaProjectSettleAuditItem.getStatisticsType(), "100800")) {
	        				paymentUpperCase = zxSaProjectSettleAuditItem;
	        			}
					}
        			
        			// ?????????????????????????????????---???????????????????????????
        			
        			// ?????????????????????????????????????????????
        			
        			// ??????????????????????????????= ?????????{??????????????????-???????????????+??????????????????}
					BigDecimal thisAmt = CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt());
					BigDecimal deductionAmt = new BigDecimal(0);
					BigDecimal returnAmt = new BigDecimal(0);
					
					if (marginDeduction != null) {
						deductionAmt = new BigDecimal(marginDeduction.getThisAmt());
					}
					
					if (returnMarginDeduction != null) {
						returnAmt = new BigDecimal(returnMarginDeduction.getThisAmt());
					}
    				
					BigDecimal paymentTotalAmt = CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt, deductionAmt), returnAmt);
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
        	delZxErpFile.setOtherId(dbZxSaProjectPaySettle.getProjectPaySettleId());
        	delZxErpFile.setOtherType("0");
        	zxErpFileService.deleteAllZxErpFile(delZxErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxSaProjectPaySettle.getZxErpFileList();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(dbZxSaProjectPaySettle.getProjectPaySettleId());
		        	zxErpFile.setOtherType("0");
		        	zxErpFileService.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// ????????????
        	ZxSaProjectPaySettleItem delZxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
        	delZxSaProjectPaySettleItem.setProjectPaySettleId(dbZxSaProjectPaySettle.getProjectPaySettleId());
        	zxSaProjectPaySettleItemService.deleteAllZxSaProjectPaySettleItem(delZxSaProjectPaySettleItem);
        	
        	if (zxSaProjectPaySettleItemList != null && zxSaProjectPaySettleItemList.size() > 0) {
				for (ZxSaProjectPaySettleItem zxSaProjectPaySettleItem : zxSaProjectPaySettleItemList) {
					zxSaProjectPaySettleItem.setProjectPaySettleItemId(UuidUtil.generate());
			        zxSaProjectPaySettleItem.setCreateUserInfo(userKey, realName);
			        zxSaProjectPaySettleItem.setProjectPaySettleId(dbZxSaProjectPaySettle.getProjectPaySettleId());
			        zxSaProjectPaySettleItemMapper.insert(zxSaProjectPaySettleItem);
				}
			}
            return repEntity.ok("sys.data.update",zxSaProjectPaySettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectPaySettle(List<ZxSaProjectPaySettle> zxSaProjectPaySettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectPaySettleList != null && zxSaProjectPaySettleList.size() > 0) {
           ZxSaProjectPaySettle zxSaProjectPaySettle = new ZxSaProjectPaySettle();
           zxSaProjectPaySettle.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectPaySettleMapper.batchDeleteUpdateZxSaProjectPaySettle(zxSaProjectPaySettleList, zxSaProjectPaySettle);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxSaProjectPaySettle zxSaProjectPaySettle : zxSaProjectPaySettleList) {
        		// ??????
            	ZxErpFile delZxErpFile = new ZxErpFile();
            	delZxErpFile.setOtherId(zxSaProjectPaySettle.getProjectPaySettleId());
            	delZxErpFile.setOtherType("0");
            	zxErpFileService.deleteAllZxErpFile(delZxErpFile);
            	
            	// ????????????
            	ZxSaProjectPaySettleItem delZxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
            	delZxSaProjectPaySettleItem.setProjectPaySettleId(zxSaProjectPaySettle.getProjectPaySettleId());
            	zxSaProjectPaySettleItemService.deleteAllZxSaProjectPaySettleItem(delZxSaProjectPaySettleItem);
			}
            return repEntity.ok("sys.data.delete",zxSaProjectPaySettleList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
