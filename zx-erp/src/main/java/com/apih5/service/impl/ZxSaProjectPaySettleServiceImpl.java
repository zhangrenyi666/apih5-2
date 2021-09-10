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
        // 分页查询
        PageHelper.startPage(zxSaProjectPaySettle.getPage(),zxSaProjectPaySettle.getLimit());
        // 获取数据
        List<ZxSaProjectPaySettle> zxSaProjectPaySettleList = zxSaProjectPaySettleMapper.selectByZxSaProjectPaySettleList(zxSaProjectPaySettle);
        // 得到分页信息
        PageInfo<ZxSaProjectPaySettle> p = new PageInfo<>(zxSaProjectPaySettleList);
        
        for (ZxSaProjectPaySettle projectPaySettle : zxSaProjectPaySettleList) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(projectPaySettle.getProjectPaySettleId());
        	zxErpFile.setOtherType("0");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileService.getZxErpFileListByCondition(zxErpFile).getData();
        	projectPaySettle.setZxErpFileList(zxErpFileList);
        	
        	// 子表明细
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
        // 获取数据
        ZxSaProjectPaySettle dbZxSaProjectPaySettle = zxSaProjectPaySettleMapper.selectByPrimaryKey(zxSaProjectPaySettle.getProjectPaySettleId());
        // 数据存在
        if (dbZxSaProjectPaySettle != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxSaProjectPaySettle.getProjectPaySettleId());
        	zxErpFile.setOtherType("0");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileService.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxSaProjectPaySettle.setZxErpFileList(zxErpFileList);
        	
        	// 子表明细
        	ZxSaProjectPaySettleItem zxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
        	zxSaProjectPaySettleItem.setProjectPaySettleId(dbZxSaProjectPaySettle.getProjectPaySettleId());
        	List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList = (List<ZxSaProjectPaySettleItem>) zxSaProjectPaySettleItemService.getZxSaProjectPaySettleItemListByCondition(zxSaProjectPaySettleItem).getData();
        	dbZxSaProjectPaySettle.setProjectPaySettleItemList(zxSaProjectPaySettleItemList);
            return repEntity.ok(dbZxSaProjectPaySettle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxSaProjectPaySettle.getZxErpFileList();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(zxSaProjectPaySettle.getProjectPaySettleId());
		        	zxErpFile.setOtherType("0");
		        	zxErpFileService.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// 子表明细
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
           // 项目ID
           dbZxSaProjectPaySettle.setOrgID(zxSaProjectPaySettle.getOrgID());
           // 项目名称
           dbZxSaProjectPaySettle.setOrgName(zxSaProjectPaySettle.getOrgName());
           // 合同ID
           dbZxSaProjectPaySettle.setContractID(zxSaProjectPaySettle.getContractID());
           // 工程结算表ID
           dbZxSaProjectPaySettle.setBillID(zxSaProjectPaySettle.getBillID());
           
           if (zxSaProjectPaySettleItemList != null && zxSaProjectPaySettleItemList.size() > 0) {
				BigDecimal thisAmt = new BigDecimal(0); // 本期支付项结算金额(元)
				BigDecimal thisAmtNoTax = new BigDecimal(0); // 本期支付项结算不含税金额(元)
				BigDecimal materialAmt = new BigDecimal(0); // 物资调拨费本期结算小计(元)
				BigDecimal machineAmt = new BigDecimal(0); // 机械使用费本期结算小计(元)
				BigDecimal tempAmt = new BigDecimal(0); // 临时用工费本期结算小计(元)
				BigDecimal fineAmt = new BigDecimal(0); // 奖罚金额本期结算小计(元)
				BigDecimal recoupAmt = new BigDecimal(0); // 补偿金额本期结算小计(元)
				BigDecimal otherAmt = new BigDecimal(0); // 其他款项本期结算小计(元)

				for (ZxSaProjectPaySettleItem zxSaProjectPaySettleItem : zxSaProjectPaySettleItemList) {
					// 本期结算含税金额（元）
					BigDecimal thisAmtNew = CalcUtils.calcMultiply(zxSaProjectPaySettleItem.getQty(), zxSaProjectPaySettleItem.getPrice());
					zxSaProjectPaySettleItem.setThisAmt(thisAmtNew);
					
					// 本期结算不含税金额（元）
					BigDecimal thisAmtNoTaxNew = new BigDecimal(0);
					// 本期结算税额(元)
					BigDecimal thisAmtTax = new BigDecimal(0); 
					
					if (StrUtil.isNotEmpty(zxSaProjectPaySettleItem.getTaxRate())) {
						thisAmtNoTaxNew = CalcUtils.calcMultiply(CalcUtils.calcDivide(thisAmtNew, CalcUtils.calcAdd(new BigDecimal(100), new BigDecimal(zxSaProjectPaySettleItem.getTaxRate())), 4), new BigDecimal(100));
						zxSaProjectPaySettleItem.setThisAmtNoTax(thisAmtNoTaxNew);
						
						thisAmtTax = CalcUtils.calcSubtract(thisAmtNew, thisAmtNoTaxNew);
						zxSaProjectPaySettleItem.setThisAmtTax(thisAmtTax);
					}
					thisAmt = CalcUtils.calcAdd(thisAmt, thisAmtNew);
					thisAmtNoTax = CalcUtils.calcAdd(thisAmtNoTax, thisAmtNoTaxNew);
					
					if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10001", "补偿金额")) { // 补偿金额
						recoupAmt = CalcUtils.calcAdd(recoupAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10002", "材料调拨费", "物资调拨费")) { // 材料调拨费---物资调拨费
						materialAmt = CalcUtils.calcAdd(materialAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10003", "奖罚金额")) { // 奖罚金额
						fineAmt = CalcUtils.calcAdd(fineAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10004", "零星工程机械", "机械调拨费", "机械使用费")) { // 零星工程机械---机械调拨费---机械使用费
						machineAmt = CalcUtils.calcAdd(machineAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10005", "零星工程劳务", "临时用工费")) { // 零星工程劳务---临时用工费
						tempAmt = CalcUtils.calcAdd(tempAmt, zxSaProjectPaySettleItem.getThisAmt());
					} else if (StrUtil.containsAny(zxSaProjectPaySettleItem.getPayType(), "10006", "其他款项")) { // 其他款项
						otherAmt = CalcUtils.calcAdd(otherAmt, zxSaProjectPaySettleItem.getThisAmt());
					}
				}

				// 本期支付项结算金额(元)
				dbZxSaProjectPaySettle.setThisAmt(thisAmt);
				// 本期支付项结算不含税金额(元)
				dbZxSaProjectPaySettle.setThisAmtNoTax(thisAmtNoTax);
				// 本期结算税额
				dbZxSaProjectPaySettle.setThisAmtTax(CalcUtils.calcSubtract(thisAmt, thisAmtNoTax));
				// 累计支付项结算金额(元)
				dbZxSaProjectPaySettle.setTotalAmt(CalcUtils.calcAdd(thisAmt, dbZxSaProjectPaySettle.getUpAmt()));
				// 物资调拨费本期结算小计(元)
				dbZxSaProjectPaySettle.setMaterialAmt(materialAmt);
				// 机械使用费本期结算小计(元)
				dbZxSaProjectPaySettle.setMachineAmt(machineAmt);
				// 临时用工费本期结算小计(元)
				dbZxSaProjectPaySettle.setTempAmt(tempAmt);
				// 奖罚金额本期结算小计(元)
				dbZxSaProjectPaySettle.setFineAmt(fineAmt);
				// 补偿金额本期结算小计(元)
				dbZxSaProjectPaySettle.setRecoupAmt(recoupAmt);
				// 其他款项本期结算小计(元)
				dbZxSaProjectPaySettle.setOtherAmt(otherAmt);
           } else {
        	   // 本期支付项结算金额(元)
               dbZxSaProjectPaySettle.setThisAmt(new BigDecimal(0));
               // 本期支付项结算不含税金额(元)
               dbZxSaProjectPaySettle.setThisAmtNoTax(new BigDecimal(0));
               // 本期结算税额
               dbZxSaProjectPaySettle.setThisAmtTax(new BigDecimal(0));
               // 累计支付项结算金额(元)
               dbZxSaProjectPaySettle.setTotalAmt(dbZxSaProjectPaySettle.getUpAmt());
               // 物资调拨费本期结算小计(元)
               dbZxSaProjectPaySettle.setMaterialAmt(new BigDecimal(0));
               // 机械使用费本期结算小计(元)
               dbZxSaProjectPaySettle.setMachineAmt(new BigDecimal(0));
               // 临时用工费本期结算小计(元)
               dbZxSaProjectPaySettle.setTempAmt(new BigDecimal(0));
               // 奖罚金额本期结算小计(元)
               dbZxSaProjectPaySettle.setFineAmt(new BigDecimal(0));
               // 补偿金额本期结算小计(元)
               dbZxSaProjectPaySettle.setRecoupAmt(new BigDecimal(0));
               // 其他款项本期结算小计(元)
               dbZxSaProjectPaySettle.setOtherAmt(new BigDecimal(0));
           }
           
           // 上期末累计支付项结算金额(元)
           dbZxSaProjectPaySettle.setUpAmt(zxSaProjectPaySettle.getUpAmt());
           // 上期末物资调拨费结算小计(元)
           dbZxSaProjectPaySettle.setUpMaterialAmt(zxSaProjectPaySettle.getUpMaterialAmt());
           // 上期末机械使用费结算小计(元)
           dbZxSaProjectPaySettle.setUpMachineAmt(zxSaProjectPaySettle.getUpMachineAmt());
           // 上期末临时用工费结算小计(元)
           dbZxSaProjectPaySettle.setUpTempAmt(zxSaProjectPaySettle.getUpTempAmt());
           // 上期末奖罚金额结算小计(元)
           dbZxSaProjectPaySettle.setUpFineAmt(zxSaProjectPaySettle.getUpFineAmt());
           // 上期末补偿金额结算小计(元)
           dbZxSaProjectPaySettle.setUpRecoupAmt(zxSaProjectPaySettle.getUpRecoupAmt());
           // 上期末其他款项结算小计(元)
           dbZxSaProjectPaySettle.setUpOtherAmt(zxSaProjectPaySettle.getUpOtherAmt());
           // 最后编辑时间
           dbZxSaProjectPaySettle.setEditTime(new Date());
           // 结算期次
           dbZxSaProjectPaySettle.setPeriod(zxSaProjectPaySettle.getPeriod());
           // 所属公司ID
           dbZxSaProjectPaySettle.setComID(zxSaProjectPaySettle.getComID());
           // 所属公司
           dbZxSaProjectPaySettle.setComName(zxSaProjectPaySettle.getComName());
           // 所属公司排序
           dbZxSaProjectPaySettle.setComOrders(zxSaProjectPaySettle.getComOrders());
           // 税率
           dbZxSaProjectPaySettle.setTaxRate(zxSaProjectPaySettle.getTaxRate());
           // 备注
           dbZxSaProjectPaySettle.setRemark(zxSaProjectPaySettle.getRemark());
           // 排序
           dbZxSaProjectPaySettle.setSort(zxSaProjectPaySettle.getSort());
           // 共通
           dbZxSaProjectPaySettle.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectPaySettleMapper.updateByPrimaryKey(dbZxSaProjectPaySettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	/*
			 * 修改结算单（thisAmt 本期结算金额(元)， totalAmt 开累结算金额(元)，thisPayAmt 本期应支付金额(元)，totalPayAmt
			 * 开累应支付金额(元), thisAmtNoTax 本期结算不含税金额(元), thisAmtTax 本期结算税额(元)）金额
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
        		// 查询清单信息
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
        			// 其中扣除保证金合计---返还保证金
        			ZxSaProjectSettleAuditItem marginDeduction = null, returnMarginDeduction = null; 
					// 应付工程款（大小写）
        			ZxSaProjectSettleAuditItem paymentUpperCase = null, paymentLowerCase = null;
        			for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : dbZxSaProjectSettleAuditItemList) {
						if (StrUtil.equals("100100", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)合计含税结算金额（小写）= 清单结算含税金额 + 支付项结算含税金额
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt()) + "");
							// (totalAmt)开累金额 = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())) + "");
							
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
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()) + "");
							// (totalAmt)开累金额  = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())) + "");
							
							dbZxSaProjectSettleAudit.setThisAmtNoTax(new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt()));
							
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100210", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)合计不含税结算金额（大写）= 清单结算不含税金额 + 支付项结算不含税金额
							BigDecimal thisAmt = CalcUtils.calcDivide(CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax()), new BigDecimal(1), 2);
							zxSaProjectSettleAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(thisAmt));
							// (totalAmt)开累金额  = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), thisAmt)));
							zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
							zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(zxSaProjectSettleAuditItem);
						} else if (StrUtil.equals("100120", zxSaProjectSettleAuditItem.getStatisticsType())) {
							// (thisAmt)合计结算税额（小写）= 合计含税结算金额（小写）- 合计不含税结算金额（小写）
							BigDecimal thisAmt = CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmt(), dbZxSaProjectPaySettle.getThisAmt());
							BigDecimal thisAmtNoTax = CalcUtils.calcAdd(dbZxSaProjectWorkSettle.getThisAmtNoTax(), dbZxSaProjectPaySettle.getThisAmtNoTax());
							zxSaProjectSettleAuditItem.setThisAmt(CalcUtils.calcSubtract(thisAmt, thisAmtNoTax) + "");
							// (totalAmt)开累金额  = 上期累计金额(upAmt) + 本期金额(thisAmt)
							zxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcDivide(CalcUtils.calcAdd(zxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(zxSaProjectSettleAuditItem.getThisAmt())), new BigDecimal(1), 2) + "");
							
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
        			
        			// 处理其中扣除保证金合计---清单未改变无需处理
        			
        			// 处理返还保证金合计手填无需处理
        			
        			// 应付工程款（大小写）= 本期的{合计含税金额-扣除保证金+返还的保证金}
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
        	
        	// 子表明细
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxSaProjectPaySettle zxSaProjectPaySettle : zxSaProjectPaySettleList) {
        		// 附件
            	ZxErpFile delZxErpFile = new ZxErpFile();
            	delZxErpFile.setOtherId(zxSaProjectPaySettle.getProjectPaySettleId());
            	delZxErpFile.setOtherType("0");
            	zxErpFileService.deleteAllZxErpFile(delZxErpFile);
            	
            	// 子表明细
            	ZxSaProjectPaySettleItem delZxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
            	delZxSaProjectPaySettleItem.setProjectPaySettleId(zxSaProjectPaySettle.getProjectPaySettleId());
            	zxSaProjectPaySettleItemService.deleteAllZxSaProjectPaySettleItem(delZxSaProjectPaySettleItem);
			}
            return repEntity.ok("sys.data.delete",zxSaProjectPaySettleList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
