package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtEqCoContractAmtRateMapper;
import com.apih5.mybatis.dao.ZxCtEqContrItemMapper;
import com.apih5.mybatis.dao.ZxCtEqContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSaEquipPaySettleItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipPaySettleMapper;
import com.apih5.mybatis.dao.ZxSaEquipResSettleItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipResSettleMapper;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditMapper;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAudit;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAuditItem;
import com.apih5.mybatis.pojo.ZxCtEqCoContractAmtRate;
import com.apih5.mybatis.pojo.ZxCtEqContrItem;
import com.apih5.mybatis.pojo.ZxCtEqContract;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettle;
import com.apih5.mybatis.pojo.ZxSaEquipPaySettleItem;
import com.apih5.mybatis.pojo.ZxSaEquipResSettle;
import com.apih5.mybatis.pojo.ZxSaEquipResSettleItem;
import com.apih5.service.ZxSaEquipSettleAuditService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxSaEquipSettleAuditService")
public class ZxSaEquipSettleAuditServiceImpl implements ZxSaEquipSettleAuditService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaEquipSettleAuditMapper zxSaEquipSettleAuditMapper;

    @Autowired(required = true)
    private ZxSaEquipSettleAuditItemMapper zxSaEquipSettleAuditItemMapper;
    
    @Autowired(required = true)
    private ZxSaEquipResSettleMapper zxSaEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaEquipResSettleItemMapper zxSaEquipResSettleItemMapper;
    
    @Autowired(required = true)
    private ZxCtEqContractMapper zxCtEqContractMapper;
    
    @Autowired(required = true)
    private ZxCtEqContrItemMapper zxCtEqContrItemMapper;
    
    @Autowired(required = true)
    private ZxSaEquipPaySettleMapper zxSaEquipPaySettleMapper;
    
    @Autowired(required = true)
    private ZxSaEquipPaySettleItemMapper zxSaEquipPaySettleItemMapper;
    
    @Autowired(required = true)
    private ZxCtEqCoContractAmtRateMapper zxCtEqCoContractAmtRateMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSaEquipSettleAuditListByCondition(ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        if (zxSaEquipSettleAudit == null) {
            zxSaEquipSettleAudit = new ZxSaEquipSettleAudit();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSaEquipSettleAudit.setComID("");
        	zxSaEquipSettleAudit.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSaEquipSettleAudit.setComID(zxSaEquipSettleAudit.getOrgID());
        	zxSaEquipSettleAudit.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSaEquipSettleAudit.setOrgID(zxSaEquipSettleAudit.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSaEquipSettleAudit.getPage(),zxSaEquipSettleAudit.getLimit());
        // 获取数据
        List<ZxSaEquipSettleAudit> zxSaEquipSettleAuditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(zxSaEquipSettleAudit);
        for (ZxSaEquipSettleAudit zxSaEquipSettleAudit2 : zxSaEquipSettleAuditList) {
        	//附件
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherType("0");
        	file.setOtherId(zxSaEquipSettleAudit2.getZxSaEquipSettleAuditId());
        	List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxSaEquipSettleAudit2.setZxErpFileList(zxErpFileList);
        	file.setOtherType("1");
    		List<ZxErpFile> zxErpMainFileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxSaEquipSettleAudit2.setZxErpMainFileList(zxErpMainFileList);
        	//清单
        	ZxSaEquipResSettle resSettle = new ZxSaEquipResSettle();
        	resSettle.setZxSaEquipSettleAuditId(zxSaEquipSettleAudit2.getZxSaEquipSettleAuditId());
        	List<ZxSaEquipResSettle> resSettles = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(resSettle);
        	if(resSettles != null && resSettles.size() >0) {
        		zxSaEquipSettleAudit2.setZxSaEquipResSettleId(resSettles.get(0).getZxSaEquipResSettleId());
        		zxSaEquipSettleAudit2.setContractAmt(resSettles.get(0).getContractAmt());
        		zxSaEquipSettleAudit2.setChangeAmt(resSettles.get(0).getChangeAmt());
        		zxSaEquipSettleAudit2.setUpAmtRes(resSettles.get(0).getUpAmt());
        		zxSaEquipSettleAudit2.setThisAmtRes(resSettles.get(0).getThisAmt());
        		zxSaEquipSettleAudit2.setTotalAmtRes(resSettles.get(0).getTotalAmt());
        		zxSaEquipSettleAudit2.setThisAmtNoTaxRes(resSettles.get(0).getThisAmtNoTax());
        		zxSaEquipSettleAudit2.setThisAmtTaxRes(resSettles.get(0).getThisAmtTax());
        		//清单明细
        		ZxSaEquipResSettleItem resSettleItem = new ZxSaEquipResSettleItem();   
        		resSettleItem.setZxSaEquipResSettleId(resSettles.get(0).getZxSaEquipResSettleId());
        		List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(resSettleItem);
        		zxSaEquipSettleAudit2.setZxSaEquipResSettleItemList(zxSaEquipResSettleItemList);
        	}
        	//支付项
        	ZxSaEquipPaySettle paySettle = new ZxSaEquipPaySettle();
        	paySettle.setZxSaEquipSettleAuditId(zxSaEquipSettleAudit2.getZxSaEquipSettleAuditId());
        	List<ZxSaEquipPaySettle> paySettles = zxSaEquipPaySettleMapper.selectByZxSaEquipPaySettleList(paySettle);
        	if(paySettles != null && paySettles.size() >0) {
        		zxSaEquipSettleAudit2.setZxSaEquipPaySettleId(paySettles.get(0).getZxSaEquipPaySettleId());
        		zxSaEquipSettleAudit2.setThisAmtPay(paySettles.get(0).getThisAmt());
        		zxSaEquipSettleAudit2.setTotalAmtPay(paySettles.get(0).getTotalAmt());
        		zxSaEquipSettleAudit2.setInOutAmt(paySettles.get(0).getInOutAmt());
        		zxSaEquipSettleAudit2.setFineAmt(paySettles.get(0).getFineAmt());
        		zxSaEquipSettleAudit2.setFoodAmt(paySettles.get(0).getFoodAmt());
        		zxSaEquipSettleAudit2.setOtherAmt(paySettles.get(0).getOtherAmt());
        		zxSaEquipSettleAudit2.setThisAmtNoTaxPay(paySettles.get(0).getThisAmtNoTax());
        		zxSaEquipSettleAudit2.setThisAmtTaxPay(paySettles.get(0).getThisAmtTax());
        	
        		zxSaEquipSettleAudit2.setUpAmtPay(paySettles.get(0).getUpAmt());
        		zxSaEquipSettleAudit2.setUpInOutAmt(paySettles.get(0).getUpInOutAmt());
        		zxSaEquipSettleAudit2.setUpFineAmt(paySettles.get(0).getUpFineAmt());
        		zxSaEquipSettleAudit2.setUpFoodAmt(paySettles.get(0).getUpFoodAmt());
        		zxSaEquipSettleAudit2.setUpOtherAmt(paySettles.get(0).getUpOtherAmt());
        	}
        	//统计项
        	ZxSaEquipSettleAuditItem settleAuditItem = new ZxSaEquipSettleAuditItem();
        	settleAuditItem.setZxSaEquipSettleAuditId(zxSaEquipSettleAudit2.getZxSaEquipSettleAuditId());
        	List<ZxSaEquipSettleAuditItem> zxSaEquipSettleAuditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(settleAuditItem);
        	zxSaEquipSettleAudit2.setZxSaEquipSettleAuditItemList(zxSaEquipSettleAuditItemList);
		}
        // 得到分页信息
        PageInfo<ZxSaEquipSettleAudit> p = new PageInfo<>(zxSaEquipSettleAuditList);

        return repEntity.okList(zxSaEquipSettleAuditList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaEquipSettleAuditDetail(ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        if (zxSaEquipSettleAudit == null) {
            zxSaEquipSettleAudit = new ZxSaEquipSettleAudit();
        }
        ZxSaEquipSettleAudit dbZxSaEquipSettleAudit = new ZxSaEquipSettleAudit();
        if(StrUtil.isNotEmpty(zxSaEquipSettleAudit.getWorkId())) {
        	ZxSaEquipSettleAudit audit = new ZxSaEquipSettleAudit();
        	audit.setWorkId(zxSaEquipSettleAudit.getWorkId());
        	List<ZxSaEquipSettleAudit> auditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(audit);
        	if(auditList != null && auditList.size() >0) {
        		dbZxSaEquipSettleAudit = auditList.get(0); 
        	}
        }else {
        	// 获取数据
        	dbZxSaEquipSettleAudit = zxSaEquipSettleAuditMapper.selectByPrimaryKey(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        }
        // 数据存在
        if (dbZxSaEquipSettleAudit != null) {
        	//附件
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherType("0");
        	file.setOtherId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        	List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
        	dbZxSaEquipSettleAudit.setZxErpFileList(zxErpFileList);
        	file.setOtherType("1");
    		List<ZxErpFile> zxErpMainFileList = zxErpFileMapper.selectByZxErpFileList(file);
    		dbZxSaEquipSettleAudit.setZxErpMainFileList(zxErpMainFileList);
        	//清单
        	ZxSaEquipResSettle resSettle = new ZxSaEquipResSettle();
        	resSettle.setZxSaEquipSettleAuditId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        	List<ZxSaEquipResSettle> resSettles = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(resSettle);
        	if(resSettles != null && resSettles.size() >0) {
        		dbZxSaEquipSettleAudit.setZxSaEquipResSettleId(resSettles.get(0).getZxSaEquipResSettleId());
        		dbZxSaEquipSettleAudit.setContractAmt(resSettles.get(0).getContractAmt());
        		dbZxSaEquipSettleAudit.setChangeAmt(resSettles.get(0).getChangeAmt());
        		dbZxSaEquipSettleAudit.setUpAmtRes(resSettles.get(0).getUpAmt());
        		dbZxSaEquipSettleAudit.setThisAmtRes(resSettles.get(0).getThisAmt());
        		dbZxSaEquipSettleAudit.setTotalAmtRes(resSettles.get(0).getTotalAmt());
        		dbZxSaEquipSettleAudit.setThisAmtNoTaxRes(resSettles.get(0).getThisAmtNoTax());
        		dbZxSaEquipSettleAudit.setThisAmtTaxRes(resSettles.get(0).getThisAmtTax());
        		//清单明细
        		ZxSaEquipResSettleItem resSettleItem = new ZxSaEquipResSettleItem();   
        		resSettleItem.setZxSaEquipResSettleId(resSettles.get(0).getZxSaEquipResSettleId());
        		List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(resSettleItem);
        		dbZxSaEquipSettleAudit.setZxSaEquipResSettleItemList(zxSaEquipResSettleItemList);
        	}
        	//支付项
        	ZxSaEquipPaySettle paySettle = new ZxSaEquipPaySettle();
        	paySettle.setZxSaEquipSettleAuditId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        	List<ZxSaEquipPaySettle> paySettles = zxSaEquipPaySettleMapper.selectByZxSaEquipPaySettleList(paySettle);
        	if(paySettles != null && paySettles.size() >0) {
        		dbZxSaEquipSettleAudit.setZxSaEquipPaySettleId(paySettles.get(0).getZxSaEquipPaySettleId());
        		dbZxSaEquipSettleAudit.setThisAmtPay(paySettles.get(0).getThisAmt());
        		dbZxSaEquipSettleAudit.setTotalAmtPay(paySettles.get(0).getTotalAmt());
        		dbZxSaEquipSettleAudit.setInOutAmt(paySettles.get(0).getInOutAmt());
        		dbZxSaEquipSettleAudit.setFineAmt(paySettles.get(0).getFineAmt());
        		dbZxSaEquipSettleAudit.setFoodAmt(paySettles.get(0).getFoodAmt());
        		dbZxSaEquipSettleAudit.setOtherAmt(paySettles.get(0).getOtherAmt());
        		dbZxSaEquipSettleAudit.setThisAmtNoTaxPay(paySettles.get(0).getThisAmtNoTax());
        		dbZxSaEquipSettleAudit.setThisAmtTaxPay(paySettles.get(0).getThisAmtTax());
        		dbZxSaEquipSettleAudit.setUpAmtPay(paySettles.get(0).getUpAmt());
        		dbZxSaEquipSettleAudit.setUpInOutAmt(paySettles.get(0).getUpInOutAmt());
        		dbZxSaEquipSettleAudit.setUpFineAmt(paySettles.get(0).getUpFineAmt());
        		dbZxSaEquipSettleAudit.setUpFoodAmt(paySettles.get(0).getUpFoodAmt());
        		dbZxSaEquipSettleAudit.setUpOtherAmt(paySettles.get(0).getUpOtherAmt());
        	
        		ZxSaEquipPaySettleItem paySettleItem = new ZxSaEquipPaySettleItem();
        		paySettleItem.setZxSaEquipPaySettleId(paySettles.get(0).getZxSaEquipPaySettleId());
        		List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(paySettleItem);
        		dbZxSaEquipSettleAudit.setZxSaEquipPaySettleItemList(zxSaEquipPaySettleItemList);
        	
        	}
        	
        	//统计项
        	ZxSaEquipSettleAuditItem settleAuditItem = new ZxSaEquipSettleAuditItem();
        	settleAuditItem.setZxSaEquipSettleAuditId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        	List<ZxSaEquipSettleAuditItem> zxSaEquipSettleAuditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(settleAuditItem);
        	dbZxSaEquipSettleAudit.setZxSaEquipSettleAuditItemList(zxSaEquipSettleAuditItemList);
            return repEntity.ok(dbZxSaEquipSettleAudit);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaEquipSettleAudit(ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        String period = "";
        //校验
        if(zxSaEquipSettleAudit.getPeriodDate() != null) {
			period = DateUtil.format(zxSaEquipSettleAudit.getPeriodDate(), "yyyyMM");
		}else {
			return repEntity.layerMessage("no", "结算期次【periodDate】必填");
		}
        // check（不能添加小于等于本合同已填期次数据）
        ZxSaEquipSettleAudit checkProjectSettleAudit = new ZxSaEquipSettleAudit();
        checkProjectSettleAudit.setOrgID(zxSaEquipSettleAudit.getOrgID());
        checkProjectSettleAudit.setContractID(zxSaEquipSettleAudit.getContractID());
        checkProjectSettleAudit.setPeriod(period);
        List<ZxSaEquipSettleAudit> checkList = zxSaEquipSettleAuditMapper.getZxSaEquipSettleAuditTotalList(checkProjectSettleAudit);
        if (checkList != null && checkList.size() > 0) {
			return repEntity.layerMessage("no", "该合同已存在大于本期的结算数据，请选择其他期次！");
		}
        //判断上次结算是否完成
        checkProjectSettleAudit.setPeriod("");
        checkProjectSettleAudit.setApih5FlowStatusFlagNo2("查询审核未通过的所有数据");
        List<ZxSaEquipSettleAudit> settleAuditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(checkProjectSettleAudit);
        if (settleAuditList != null && settleAuditList.size() > 0) {
			return repEntity.layerMessage("no", "该合同存在未审核完成的结算数据，请先完成结算审核！");
		}
        //根据项目id【orgId】,合同id【contractID】,判断是不是首次结算
        ZxSaEquipSettleAudit equipSettleAudit = new ZxSaEquipSettleAudit();
        equipSettleAudit.setOrgID(zxSaEquipSettleAudit.getOrgID());
        equipSettleAudit.setContractID(zxSaEquipSettleAudit.getContractID());
        List<ZxSaEquipSettleAudit> zxSaEquipSettleAuditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(equipSettleAudit);
        if(zxSaEquipSettleAuditList != null && zxSaEquipSettleAuditList.size() >0) {
        	//不是
        }else {
        	//是
        	//修对应合同下的数据状态==问题票3654
            //1.引用某合同首次建立一条结算单时，保存后要把 对应的合同中结算情况 更新为“结算开始执行”
            ZxCtEqContract contract = zxCtEqContractMapper.selectByPrimaryKey(zxSaEquipSettleAudit.getContractID());
            if(contract != null && StrUtil.isNotEmpty(contract.getContractID())) {
            	contract.setSettleType("结算开始执行");
            	contract.setModifyUserInfo(userKey, realName);
            	flag = zxCtEqContractMapper.updateByPrimaryKey(contract);
            }
        }
        
        
        //add结算单
        zxSaEquipSettleAudit.setZxSaEquipSettleAuditId(UuidUtil.generate());
        zxSaEquipSettleAudit.setPeriod(period);
        zxSaEquipSettleAudit.setAutoNum(zxSaEquipSettleAudit.getAutoNum());
        zxSaEquipSettleAudit.setApih5FlowStatus("-1");
        zxSaEquipSettleAudit.setSendDate(new Date());
        zxSaEquipSettleAudit.setCreateUserInfo(userKey, realName);
       
        //add结算单附件
        if(zxSaEquipSettleAudit.getZxErpFileList() != null && zxSaEquipSettleAudit.getZxErpFileList().size() >0) {
     	   for (ZxErpFile file : zxSaEquipSettleAudit.getZxErpFileList()) {
     		   file.setUid(UuidUtil.generate());
     		   file.setOtherType("0");
     		   file.setOtherId(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
     		   file.setCreateUserInfo(userKey, realName);
     		   flag = zxErpFileMapper.insert(file);
     	   }
        }
        if(zxSaEquipSettleAudit.getZxErpMainFileList() != null && zxSaEquipSettleAudit.getZxErpMainFileList().size() >0) {
        	for (ZxErpFile file : zxSaEquipSettleAudit.getZxErpMainFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherType("1");
        		file.setOtherId(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
//add清单结算
        //【含税合同金额(万元)contractAmt,变更后含税合同金额(万元)changeAmt,本期清单结算含税金额(元)thisAmt】
        //【本期清单结算不含税金额(元)thisAmtNoTax,本期清单结算税额(元)thisAmtTax,累计清单结算含税金额(元)totalAmt】
        ZxSaEquipResSettle addRes = new ZxSaEquipResSettle();
        BeanUtil.copyProperties(zxSaEquipSettleAudit, addRes, true);
        addRes.setZxSaEquipResSettleId(UuidUtil.generate());
        addRes.setContrType("P6");
        //根据contractID去设备合同台账中查相关的金额信息和清单明细
        ZxCtEqContract eqContractSelect = zxCtEqContractMapper.selectByPrimaryKey(zxSaEquipSettleAudit.getContractID());
        if(eqContractSelect != null && StrUtil.isNotEmpty(eqContractSelect.getContractID())) {
        	 //含税合同金额(万元)=原主合同含税金额
            addRes.setContractAmt(eqContractSelect.getContractCost());
            // 变更后含税合同金额(万元)=变更后合同含税金额
            addRes.setChangeAmt(eqContractSelect.getAlterContractSum());
        }
        addRes.setThisAmt(new BigDecimal("0.00"));//本期清单结算含税金额(元)
        addRes.setThisAmtNoTax(new BigDecimal("0.00"));//本期清单结算不含税金额(元)
        addRes.setThisAmtTax(new BigDecimal("0.00"));//本期清单结算税额(元)
        //新增清单的时候要判断一下该合同对应的清单有没有多期的清单，为了进行后面的金额计算
        BigDecimal totalAmt_res_big = new BigDecimal("0");// 累计清单结算含税金额(元)
        BigDecimal totalAmtNoTax_res_big = new BigDecimal("0");// 累计清单结算不含税金额(元)
        BigDecimal upAmt_res_big = new BigDecimal("0");// 上期末清单结算金额(元))
        ZxSaEquipResSettle resLess = new ZxSaEquipResSettle();
        resLess.setContractID(zxSaEquipSettleAudit.getContractID());
        resLess.setAutoNum(zxSaEquipSettleAudit.getAutoNum()+"");
        List<ZxSaEquipResSettle> resLessList = zxSaEquipResSettleMapper.getZxSaEquipResSettleListLessAutoNum(resLess);
        if(resLessList != null && resLessList.size() >0) {
        	//多期
        	for (ZxSaEquipResSettle resLesss : resLessList) {
//        		upAmt_res_big = CalcUtils.calcAdd(upAmt_res_big, resLesss.getThisAmt());//这个值计算的有可能是不对的。。
        		upAmt_res_big = CalcUtils.calcAdd(upAmt_res_big, resLesss.getThisAmt());
        		totalAmt_res_big = CalcUtils.calcAdd(totalAmt_res_big, resLesss.getThisAmt());
        		totalAmtNoTax_res_big = CalcUtils.calcAdd(totalAmtNoTax_res_big, resLesss.getThisAmtNoTax());
        	}
        	//累计清单结算含税金额(元)=累计该合同往期清单含税金额
        	addRes.setTotalAmt(totalAmt_res_big);
        	addRes.setTotalAmtNoTax(totalAmtNoTax_res_big);
        	addRes.setUpAmt(upAmt_res_big);
        }else {
        	//首期
        	addRes.setTotalAmt(new BigDecimal("0"));
        	addRes.setTotalAmtNoTax(new BigDecimal("0"));
        	addRes.setUpAmt(new BigDecimal("0"));
        }
        addRes.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipResSettleMapper.insert(addRes);
    //add清单结算明细
        ZxCtEqContrItem contrItem = new ZxCtEqContrItem();
        contrItem.setContractID(eqContractSelect.getContractID());
        List<ZxCtEqContrItem> contrItemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(contrItem);
        if(contrItemList != null && contrItemList.size() >0) {
        	for (ZxCtEqContrItem zxCtEqContrItem : contrItemList) {
        		ZxSaEquipResSettleItem addResItem = new ZxSaEquipResSettleItem();
        		BeanUtil.copyProperties(zxSaEquipSettleAudit, addResItem, true);
        		BeanUtil.copyProperties(zxCtEqContrItem, addResItem, true);
        		addResItem.setZxSaEquipResSettleItemId(UuidUtil.generate());
        		addResItem.setZxSaEquipResSettleId(addRes.getZxSaEquipResSettleId());
                addResItem.setContractItemID(zxCtEqContrItem.getZxCtEqContrItemId());
                addResItem.setEquipCode(zxCtEqContrItem.getCatCode());
                addResItem.setEquipName(zxCtEqContrItem.getCatName());
                addResItem.setCreateUserInfo(userKey, realName);
                // 起租月份
//                addResItem.setStartDate(zxCtEqContrItem.getStartDate());
                // 单价(元)
                addResItem.setContractPrice(zxCtEqContrItem.getPrice());
                // 合同数量
                addResItem.setContractQty(zxCtEqContrItem.getQty());
                // 合同含税金额(元)=含税合同金额
                addResItem.setContractAmt(zxCtEqContrItem.getContractSum());
                // 变更后含税金额(元)=变更后含税金额
                addResItem.setChangedAmt(zxCtEqContrItem.getAlterAmt());
                // 变更后数量
                addResItem.setChangedQty(zxCtEqContrItem.getAlterTrrm());
                //税率%
                addResItem.setTaxRate(zxCtEqContrItem.getTaxRate());
//===start===新增是空的，到清单的tab页手动填写的数据
                addResItem.setThisQty(new BigDecimal("0"));//本期结算数量
                addResItem.setRunHour(new BigDecimal("0"));//本期运转台时(小时)
                addResItem.setUseOil(new BigDecimal("0"));//本期油耗(L)
                addResItem.setRemark("");//备注
        //下面3个字段是由上面计算得来的，所以新增时也是0
                addResItem.setThisAmt(new BigDecimal("0"));//本期清单结算含税金额(元)= 本期结算数量*含税单价
                addResItem.setThisAmtNoTax(new BigDecimal("0"));//本期结算不含税金额(元) = 本期清单结算含税金额/(1+税率/100)
                addResItem.setThisAmtTax(new BigDecimal("0"));// 本期清单结算税额(元) = 本期结算金额(元) - 本期结算不含税金额(元)
//====end===新增是空的，到清单的tab页手动填写的数据
            	//每次新增之前都判断有没有往期的数据
            	ZxSaEquipResSettleItem resItemBefore = new ZxSaEquipResSettleItem();
            	resItemBefore.setContractID(addResItem.getContractID());
            	resItemBefore.setContractItemID(addResItem.getContractItemID());
            	List<ZxSaEquipResSettleItem> resItemBeforeList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(resItemBefore);
            	if(resItemBeforeList != null && resItemBeforeList.size() >0) {
            		//有往期
            		//至上期末累计结算数量upQty
            		//至上期末累计结算含税金额(元)upAmt
            		//至本期末累计结算数量totalQty = upQty_res_small+ThisQty
            		//至本期末累计结算金额totalAmt = upAmt_res_small+ThisAmt
            		BigDecimal upQty_res_small = new BigDecimal("0");
            		BigDecimal upAmt_res_small = new BigDecimal("0");
            		BigDecimal totalQty_res_small = new BigDecimal("0");
            		BigDecimal totalAmt_res_small = new BigDecimal("0");
            		for (ZxSaEquipResSettleItem resSettleItem : resItemBeforeList) {
            			upQty_res_small = CalcUtils.calcAdd(upQty_res_small, resSettleItem.getThisQty());
            			upAmt_res_small = CalcUtils.calcAdd(upAmt_res_small, resSettleItem.getThisAmt());
    				}
            		totalQty_res_small = CalcUtils.calcAdd(upQty_res_small, addResItem.getThisQty());
            		totalAmt_res_small = CalcUtils.calcAdd(upAmt_res_small, addResItem.getThisAmt());
            		//
            		addResItem.setUpQty(upQty_res_small);
            		addResItem.setUpAmt(upAmt_res_small);
            		addResItem.setTotalQty(totalQty_res_small);
            		addResItem.setTotalAmt(totalAmt_res_small);
            	}else {
            		//没有往期
            		//至上期末累计结算数量upQty
            		//至上期末累计结算含税金额(元)upAmt
            		//至本期末累计结算数量totalQty = ThisQty
            		//至本期末累计结算金额totalAmt = ThisAmt		
            		addResItem.setUpQty(new BigDecimal("0"));
            		addResItem.setUpAmt(new BigDecimal("0"));
            		addResItem.setTotalQty(addResItem.getThisQty());
            		addResItem.setTotalAmt(addResItem.getThisAmt());
            	}
        		flag = zxSaEquipResSettleItemMapper.insert(addResItem);
        	}
        }
//add支付项==&&&&&不管哪期初始化数据都为0&&&&&&&&&&根据项目id【orgId】去
        //【本期支付项结算含税金额(元),本期支付项结算不含税金额(元),本期支付项结算税额(元)】
        //【累计支付项结算金额(元),本期进退场退,本期奖罚】
        //【本期伙食费,本期其他款项】
        //本期支付项结算含税金额(元)==合计本期的{物资调拨费+机械调拨费+临时用工费+奖罚金额+补偿金额+其他款项}含税合同金额
        ZxSaEquipPaySettle addPay = new ZxSaEquipPaySettle();
        BeanUtil.copyProperties(zxSaEquipSettleAudit, addPay, true);
        addPay.setZxSaEquipPaySettleId(UuidUtil.generate());
        addPay.setCreateUserInfo(userKey, realName);
        addPay.setThisAmt(new BigDecimal("0"));//本期支付项结算金额(元)
        addPay.setThisAmtNoTax(new BigDecimal("0"));//本期支付项结算不含税金额(元)
        addPay.setThisAmtTax(new BigDecimal("0"));//本期支付项结算税额(元)
        //看看支付项是否有往期
        ZxSaEquipPaySettle payLess = new ZxSaEquipPaySettle();
        payLess.setContractID(zxSaEquipSettleAudit.getContractID());
        payLess.setAutoNum(zxSaEquipSettleAudit.getAutoNum()+"");
        List<ZxSaEquipPaySettle> payLessList = zxSaEquipPaySettleMapper.getZxSaEquipPaySettleListLessAutoNum(payLess);
        BigDecimal totalAmt_pay_big = new BigDecimal("0");//累计支付项结算金额(元)
        BigDecimal totalAmtNoTax_pay_big = new BigDecimal("0");//累计支付项结算不含税金额(元)
        BigDecimal upAmt_pay_big = new BigDecimal("0");//上期末累计支付项结算金额(元)
        BigDecimal upInOutAmt_pay_big = new BigDecimal("0");//上期末进退场费
        BigDecimal upFineAmt_pay_big = new BigDecimal("0");//上期末奖罚
        BigDecimal upFoodAmt_pay_big = new BigDecimal("0");//上期末伙食费
        BigDecimal upOtherAmt_pay_big = new BigDecimal("0");//上期末其他款项
        if(payLessList != null && payLessList.size() >0) {
        	//有往期==往期的进行累加
        	for (ZxSaEquipPaySettle payLesss : payLessList) {
        		totalAmt_pay_big = CalcUtils.calcAdd(totalAmt_pay_big, payLesss.getThisAmt());
        		totalAmtNoTax_pay_big = CalcUtils.calcAdd(totalAmtNoTax_pay_big, payLesss.getThisAmtNoTax());
        		upAmt_pay_big = CalcUtils.calcAdd(upAmt_pay_big, payLesss.getThisAmt());
            //查往期支付项下的清单，为了计算上期末的那4个值
        		ZxSaEquipPaySettleItem payItemLess = new ZxSaEquipPaySettleItem();
        		payItemLess.setZxSaEquipPaySettleId(payLesss.getZxSaEquipPaySettleId());
                List<ZxSaEquipPaySettleItem> payItemLessList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(payItemLess);
                if(payItemLessList != null && payItemLessList.size() >0) {
                	for (ZxSaEquipPaySettleItem payItemLesss : payItemLessList) {
                		if(StrUtil.equals(payItemLesss.getPayType(), "伙食费")) {
                			upFoodAmt_pay_big = CalcUtils.calcAdd(upFoodAmt_pay_big,payItemLesss.getThisAmt());
                    	}else if(StrUtil.equals(payItemLesss.getPayType(), "奖罚")) {
                    		upFineAmt_pay_big = CalcUtils.calcAdd(upFineAmt_pay_big,payItemLesss.getThisAmt());
                    	}else if(StrUtil.equals(payItemLesss.getPayType(), "进退场费")) {
                    		upInOutAmt_pay_big = CalcUtils.calcAdd(upInOutAmt_pay_big,payItemLesss.getThisAmt());
                    	}else if(StrUtil.equals(payItemLesss.getPayType(), "其他款项")) {
                    		upOtherAmt_pay_big = CalcUtils.calcAdd(upOtherAmt_pay_big,payItemLesss.getThisAmt());
                    	}
					}
                }
        	}
        		addPay.setTotalAmt(totalAmt_pay_big);//累计支付项结算金额(元)
                addPay.setTotalAmtNoTax(totalAmtNoTax_pay_big);//累计支付项结算不含税金额(元)
                addPay.setUpAmt(upAmt_pay_big);//上期末累计支付项结算金额(元)
                addPay.setUpInOutAmt(upInOutAmt_pay_big);//上期末进退场费
                addPay.setUpFineAmt(upFineAmt_pay_big);//上期末奖罚
                addPay.setUpFoodAmt(upInOutAmt_pay_big);//上期末伙食费
                addPay.setUpOtherAmt(upOtherAmt_pay_big);//上期末其他款项
        }else {
        	//没有往期=默认都是0
        	 addPay.setTotalAmt(new BigDecimal("0"));//累计支付项结算金额(元)
             addPay.setTotalAmtNoTax(new BigDecimal("0"));//累计支付项结算不含税金额(元)
             addPay.setUpAmt(new BigDecimal("0"));//上期末累计支付项结算金额(元)
             addPay.setUpInOutAmt(new BigDecimal("0"));//上期末进退场费
             addPay.setUpFineAmt(new BigDecimal("0"));//上期末奖罚
             addPay.setUpFoodAmt(new BigDecimal("0"));//上期末伙食费
             addPay.setUpOtherAmt(new BigDecimal("0"));//上期末其他款项
        }
        addPay.setInOutAmt(new BigDecimal("0"));//本期进退场费
        addPay.setFineAmt(new BigDecimal("0"));//本期奖罚
        addPay.setFoodAmt(new BigDecimal("0"));//本期伙食费
        addPay.setOtherAmt(new BigDecimal("0"));//本期其他款项
        flag = zxSaEquipPaySettleMapper.insert(addPay);
 //add结算单明细==统计项
        //构造统计项
        ZxSaEquipSettleAuditItem eqAuditItemCommon = new ZxSaEquipSettleAuditItem();
        eqAuditItemCommon.setPeriod(zxSaEquipSettleAudit.getPeriod());
        eqAuditItemCommon.setBillNo(zxSaEquipSettleAudit.getBillNo());
        eqAuditItemCommon.setOrgID(zxSaEquipSettleAudit.getOrgID());
        eqAuditItemCommon.setContractID(zxSaEquipSettleAudit.getContractID());
        eqAuditItemCommon.setZxSaEquipSettleAuditId(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        
        //合计结算含税金额(小写)=res本期清单结算含税金额(元)ThisAmt+pay本期支付项结算含税金额(元)ThisAmt
        ZxSaEquipSettleAuditItem eqAuditItem_100 = new ZxSaEquipSettleAuditItem();
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_100, true);
        eqAuditItem_100.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_100.setStatisticsNo("100100");
        eqAuditItem_100.setThisAmt(CalcUtils.calcAdd(addRes.getThisAmt(), addPay.getThisAmt()).toString());//本期(元)
        eqAuditItem_100.setTotalAmt(CalcUtils.calcAdd(addRes.getTotalAmt(), addPay.getTotalAmt()).toString());//开累(元)
        eqAuditItem_100.setSort(1);
        eqAuditItem_100.setBaseFlag("false");
        eqAuditItem_100.setStatisticsName("合计结算含税金额(小写)");//统计项
        eqAuditItem_100.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_100);
        //合计不含税结算金额（小写）=res本期清单结算不含税金额(元)ThisAmtNoTax+pay本期支付项结算不含税金额(元)ThisAmtNoTax
        ZxSaEquipSettleAuditItem eqAuditItem_110 = new ZxSaEquipSettleAuditItem();
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_110, true);
        eqAuditItem_110.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_110.setStatisticsNo("100110");
        eqAuditItem_110.setThisAmt(CalcUtils.calcAdd(addRes.getThisAmtNoTax(), addPay.getThisAmtNoTax()).toString());//本期(元)
        eqAuditItem_110.setTotalAmt(CalcUtils.calcAdd(totalAmtNoTax_res_big, addPay.getTotalAmtNoTax()).toString());//开累(元)
        eqAuditItem_110.setStatisticsName("合计不含税结算金额(小写)");//统计项
        eqAuditItem_110.setSort(2);
        eqAuditItem_110.setBaseFlag("false");
        eqAuditItem_110.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_110);
        //合计结算税额 = 合计结算含税金额(小写)eqAuditItem_100 - 合计不含税结算金额（小写）eqAuditItem_110
        ZxSaEquipSettleAuditItem eqAuditItem_120 = new ZxSaEquipSettleAuditItem();
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_120, true);
        eqAuditItem_120.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_120.setStatisticsNo("100120");
        eqAuditItem_120.setThisAmt(CalcUtils.calcSubtract(new BigDecimal(eqAuditItem_100.getThisAmt()),new BigDecimal(eqAuditItem_110.getThisAmt()))+"");//本期(元)
        eqAuditItem_120.setTotalAmt(CalcUtils.calcSubtract(new BigDecimal(eqAuditItem_100.getTotalAmt()),new BigDecimal(eqAuditItem_110.getTotalAmt()))+"");//本期(元)
        eqAuditItem_120.setStatisticsName("合计结算税额(小写)");//统计项
        eqAuditItem_120.setSort(3);
        eqAuditItem_120.setBaseFlag("false");
        eqAuditItem_120.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_120);
        //合计含税结算金额(大写)= eqAuditItem_100
        ZxSaEquipSettleAuditItem eqAuditItem_200 = new ZxSaEquipSettleAuditItem();
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_200, true);
        eqAuditItem_200.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_200.setStatisticsNo("100200");
        eqAuditItem_200.setThisAmt(Convert.digitToChinese(new BigDecimal(eqAuditItem_100.getThisAmt())));//本期(元)
        eqAuditItem_200.setTotalAmt(Convert.digitToChinese(new BigDecimal(eqAuditItem_100.getTotalAmt())));//开累(元)
        eqAuditItem_200.setStatisticsName("合计含税结算金额(大写)");//统计项
        eqAuditItem_200.setSort(4);
        eqAuditItem_200.setBaseFlag("false");
        eqAuditItem_200.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_200);
        //合计不含税结算金额（大写）=eqAuditItem_110
        ZxSaEquipSettleAuditItem eqAuditItem_210 = new ZxSaEquipSettleAuditItem();
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_210, true);
        eqAuditItem_210.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_210.setStatisticsNo("100210");
        eqAuditItem_210.setThisAmt(Convert.digitToChinese(new BigDecimal(eqAuditItem_110.getThisAmt())));//本期(元)
        eqAuditItem_210.setTotalAmt(Convert.digitToChinese(new BigDecimal(eqAuditItem_110.getTotalAmt())));//开累(元)
        eqAuditItem_210.setStatisticsName("合计不含税结算金额(大写)");//统计项=totalAmtNoTax_res_big
        eqAuditItem_210.setSort(5);
        eqAuditItem_210.setBaseFlag("false");
        eqAuditItem_210.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_210);
        //合计结算税额(大写) = eqAuditItem_120
        ZxSaEquipSettleAuditItem eqAuditItem_220 = new ZxSaEquipSettleAuditItem();
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_220, true);
        eqAuditItem_220.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_220.setStatisticsNo("100220");
        eqAuditItem_220.setThisAmt(Convert.digitToChinese(new BigDecimal(eqAuditItem_120.getThisAmt())));//本期(元)
        eqAuditItem_220.setTotalAmt(Convert.digitToChinese(new BigDecimal(eqAuditItem_120.getTotalAmt())));//开累(元)
        eqAuditItem_220.setStatisticsName("合计结算税额(大写)");//统计项
        eqAuditItem_220.setSort(6);
        eqAuditItem_220.setBaseFlag("false");
        eqAuditItem_220.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_220);
        //其中扣除保证金合计=每一个设置的扣除比例*本期清单结算含税金额(元) 对应问题票4300
        ZxSaEquipSettleAuditItem eqAuditItem_300 = new ZxSaEquipSettleAuditItem();
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_300, true);
        eqAuditItem_300.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_300.setStatisticsNo("100300");
        eqAuditItem_300.setThisAmt("0");
        BigDecimal totalAmt_300 = new BigDecimal("0");
        //查看有没有多期结算
        boolean moreFlag = false;
        ZxSaEquipSettleAudit auditSelect = new ZxSaEquipSettleAudit();
        auditSelect.setOrgID(zxSaEquipSettleAudit.getOrgID());
        auditSelect.setContractID(zxSaEquipSettleAudit.getContractID());
        List<ZxSaEquipSettleAudit> auditSelectList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(auditSelect);
        if(auditSelectList != null && auditSelectList.size() >0) {
        	moreFlag = true;
        	//多期
        	for (ZxSaEquipSettleAudit audit : auditSelectList) {
        		ZxSaEquipSettleAuditItem auditItem = new ZxSaEquipSettleAuditItem();
        		auditItem.setZxSaEquipSettleAuditId(audit.getZxSaEquipSettleAuditId());
        		List<ZxSaEquipSettleAuditItem> auditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(auditItem);
        		if(auditItemList !=null && auditItemList.size() >0) {
        			for (ZxSaEquipSettleAuditItem auditItem2 : auditItemList) {
        				if(StrUtil.equals(auditItem2.getStatisticsNo(), "100400")) {
        					totalAmt_300 = CalcUtils.calcAdd(totalAmt_300, new BigDecimal(auditItem2.getThisAmt()));
        				}
					}
        		}
			}
        }
        eqAuditItem_300.setTotalAmt(totalAmt_300+"");
        eqAuditItem_300.setStatisticsName("其中扣除保证金合计");//统计项
        eqAuditItem_300.setSort(7);
        eqAuditItem_300.setBaseFlag("false");
        eqAuditItem_300.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_300);
        int sort = 8;
        //查一遍设备保证金表
        BigDecimal rate_This = new BigDecimal("0");
        BigDecimal rate_Total = new BigDecimal("0");
        ZxCtEqCoContractAmtRate rate = new ZxCtEqCoContractAmtRate();
        rate.setContractID(zxSaEquipSettleAudit.getContractID());
        List<ZxCtEqCoContractAmtRate> rateList = zxCtEqCoContractAmtRateMapper.selectByZxCtEqCoContractAmtRateList(rate);
        if(rateList != null && rateList.size() >0) {
        	for (ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate : rateList) {
        		rate_This = CalcUtils.calcAdd(rate_This, CalcUtils.calcMultiply(CalcUtils.calcDivide(zxCtEqCoContractAmtRate.getStatisticsRate(), new BigDecimal("100")), new BigDecimal(eqAuditItem_100.getThisAmt())));
        		rate_Total = CalcUtils.calcAdd(rate_Total, CalcUtils.calcMultiply(CalcUtils.calcDivide(zxCtEqCoContractAmtRate.getStatisticsRate(), new BigDecimal("100")), new BigDecimal(eqAuditItem_100.getTotalAmt())));
        	}
        }
        //新增设备下的保证金==扣除比例*合计结算含税金额(小写)
        if(rateList != null && rateList.size() >0) {
        	for (ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate : rateList) {
        		ZxSaEquipSettleAuditItem eqAuditItem_rate = new ZxSaEquipSettleAuditItem(); 
        		BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_rate, true);
        		eqAuditItem_rate.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        		eqAuditItem_rate.setStatisticsID(zxCtEqCoContractAmtRate.getZxCtEqCoContractAmtRateId());
        		eqAuditItem_rate.setStatisticsNo("100400");
        		eqAuditItem_rate.setThisAmt(CalcUtils.calcMultiply(CalcUtils.calcDivide(zxCtEqCoContractAmtRate.getStatisticsRate(), new BigDecimal("100")), new BigDecimal(eqAuditItem_100.getThisAmt()))+"");
        		eqAuditItem_rate.setTotalAmt(CalcUtils.calcMultiply(CalcUtils.calcDivide(zxCtEqCoContractAmtRate.getStatisticsRate(), new BigDecimal("100")), new BigDecimal(eqAuditItem_100.getTotalAmt()))+"");
        		eqAuditItem_rate.setStatisticsName(zxCtEqCoContractAmtRate.getStatisticsName());//统计项
        		eqAuditItem_rate.setSort(sort);
        		eqAuditItem_rate.setBaseFlag("false");
        		eqAuditItem_rate.setCreateUserInfo(userKey, realName);
        		flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_rate);
        		sort++;
			}
        }  
        //返还保证金合计：根据显示出扣除保证金款项，显示返还保证金款项，并可以可手动编辑每条返还保证金的金额，将每条金额合计就=返还保证金合计
        ZxSaEquipSettleAuditItem eqAuditItem_500 = new ZxSaEquipSettleAuditItem(); 
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_500, true);
        eqAuditItem_500.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_500.setStatisticsNo("100500");
        eqAuditItem_500.setThisAmt("0");
        BigDecimal totalAmt_500 = new BigDecimal("0");
        if(auditSelectList != null && auditSelectList.size() >0) {
        	moreFlag = true;
        	//多期
        	for (ZxSaEquipSettleAudit audit : auditSelectList) {
        		ZxSaEquipSettleAuditItem auditItem = new ZxSaEquipSettleAuditItem();
        		auditItem.setZxSaEquipSettleAuditId(audit.getZxSaEquipSettleAuditId());
        		List<ZxSaEquipSettleAuditItem> auditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(auditItem);
        		if(auditItemList !=null && auditItemList.size() >0) {
        			for (ZxSaEquipSettleAuditItem auditItem2 : auditItemList) {
        				if(StrUtil.equals(auditItem2.getStatisticsNo(), "100600")) {
        					totalAmt_500 = CalcUtils.calcAdd(totalAmt_500, new BigDecimal(auditItem2.getThisAmt()));
        				}
					}
        		}
			}
        }
        eqAuditItem_500.setTotalAmt(totalAmt_500+"");
        eqAuditItem_500.setStatisticsName("返还保证金合计");//统计项
        eqAuditItem_500.setSort(sort);
        eqAuditItem_500.setBaseFlag("false");
        eqAuditItem_500.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_500);
        sort++;
        //返还保证金下的保证金
        if(rateList != null && rateList.size() >0) {
        	for (ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate : rateList) {
        		ZxSaEquipSettleAuditItem eqAuditItem_rate = new ZxSaEquipSettleAuditItem(); 
        		BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_rate, true);
        		eqAuditItem_rate.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        		eqAuditItem_rate.setStatisticsID(zxCtEqCoContractAmtRate.getZxCtEqCoContractAmtRateId());
        		eqAuditItem_rate.setStatisticsNo("100600");
        		eqAuditItem_rate.setThisAmt("0");
        		BigDecimal totalAmt_700 = new BigDecimal("0");
        		if(moreFlag) {
        			//多期
        			for (ZxSaEquipSettleAudit audit : auditSelectList) {
        				ZxSaEquipSettleAuditItem auditItem = new ZxSaEquipSettleAuditItem();
                		auditItem.setZxSaEquipSettleAuditId(audit.getZxSaEquipSettleAuditId());
                		List<ZxSaEquipSettleAuditItem> auditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(auditItem);
                		if(auditItemList !=null && auditItemList.size() >0) {
                			for (ZxSaEquipSettleAuditItem auditItem2 : auditItemList) {
                				if(StrUtil.equals(auditItem2.getStatisticsNo(), "100600") && StrUtil.equals(auditItem2.getStatisticsName(), zxCtEqCoContractAmtRate.getStatisticsName())) {
                					totalAmt_700 = CalcUtils.calcAdd(totalAmt_700, new BigDecimal(auditItem2.getThisAmt()));
                				}
        					}
                		}
        			}
        		}
        		eqAuditItem_rate.setTotalAmt(totalAmt_700+"");
        		eqAuditItem_rate.setStatisticsName(zxCtEqCoContractAmtRate.getStatisticsName());//统计项
        		eqAuditItem_rate.setSort(sort);
        		eqAuditItem_rate.setBaseFlag("true");
        		eqAuditItem_rate.setCreateUserInfo(userKey, realName);
        		flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_rate);
        		sort++;
			}
        } 
        //应付租赁款=合计结算含税金额-其中扣除保证金合计+返还保证金合计
        //应付租赁款（小写）=合计结算含税金额100-其中扣除保证金合计300+返还保证金合计500
        ZxSaEquipSettleAuditItem eqAuditItem_700 = new ZxSaEquipSettleAuditItem();   
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_700, true);
        eqAuditItem_700.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_700.setStatisticsNo("100700");
        eqAuditItem_700.setThisAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(new BigDecimal(eqAuditItem_100.getThisAmt()), new BigDecimal(eqAuditItem_300.getThisAmt())), new BigDecimal(eqAuditItem_500.getThisAmt()))+"");
        eqAuditItem_700.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(new BigDecimal(eqAuditItem_100.getTotalAmt()), new BigDecimal(eqAuditItem_300.getTotalAmt())), new BigDecimal(eqAuditItem_500.getTotalAmt()))+"");
        eqAuditItem_700.setStatisticsName("应付租赁款（小写）");//统计项
        eqAuditItem_700.setSort(sort);
        eqAuditItem_700.setBaseFlag("false");
        eqAuditItem_700.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_700);
        sort++;
        //应付租赁款大写=eqAuditItem_700
        ZxSaEquipSettleAuditItem eqAuditItem_800 = new ZxSaEquipSettleAuditItem();   
        BeanUtil.copyProperties(eqAuditItemCommon, eqAuditItem_800, true);
        eqAuditItem_800.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        eqAuditItem_800.setStatisticsNo("100800");
        eqAuditItem_800.setThisAmt(Convert.digitToChinese(new BigDecimal(eqAuditItem_700.getThisAmt())));
        eqAuditItem_800.setTotalAmt(Convert.digitToChinese(new BigDecimal(eqAuditItem_700.getTotalAmt())));
        eqAuditItem_800.setStatisticsName("应付租赁款（大写）");//统计项
        eqAuditItem_800.setSort(sort);
        eqAuditItem_800.setBaseFlag("false");
        eqAuditItem_800.setCreateUserInfo(userKey, realName);
        flag = zxSaEquipSettleAuditItemMapper.insert(eqAuditItem_800);
        
        //本期结算含税金额thisAmt	= eqAuditItem_100
        //开累结算含税金额totalAmt = eqAuditItem_100
        //本期应支付含税金额thisPayAmt = eqAuditItem_700
        //开累应支付含税金额totalPayAmt = eqAuditItem_700
        zxSaEquipSettleAudit.setThisAmt(new BigDecimal(eqAuditItem_100.getThisAmt()));
        zxSaEquipSettleAudit.setTotalAmt(new BigDecimal(eqAuditItem_100.getTotalAmt()));
        zxSaEquipSettleAudit.setThisPayAmt(new BigDecimal(eqAuditItem_700.getThisAmt()));
        zxSaEquipSettleAudit.setTotalPayAmt(new BigDecimal(eqAuditItem_700.getTotalAmt()));
        flag = zxSaEquipSettleAuditMapper.insert(zxSaEquipSettleAudit);
        
        //初始化结束
        zxSaEquipSettleAudit.setZxSaEquipResSettleId(addRes.getZxSaEquipResSettleId());
        zxSaEquipSettleAudit.setZxSaEquipPaySettleId(addPay.getZxSaEquipPaySettleId());
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaEquipSettleAudit);
        }
    }

    @Override
    public ResponseEntity updateZxSaEquipSettleAudit(ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaEquipSettleAudit dbZxSaEquipSettleAudit = zxSaEquipSettleAuditMapper.selectByPrimaryKey(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        if (dbZxSaEquipSettleAudit != null && StrUtil.isNotEmpty(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId())) {
        	//顺序号
        	dbZxSaEquipSettleAudit.setAutoNum(zxSaEquipSettleAudit.getAutoNum());
        	// 填报人
        	dbZxSaEquipSettleAudit.setReportPerson(zxSaEquipSettleAudit.getReportPerson());
        	// 计算人
        	dbZxSaEquipSettleAudit.setCountPerson(zxSaEquipSettleAudit.getCountPerson());
        	// 复核人
        	dbZxSaEquipSettleAudit.setReCountPerson(zxSaEquipSettleAudit.getReCountPerson());
        	// 填报日期
        	dbZxSaEquipSettleAudit.setReportDate(zxSaEquipSettleAudit.getReportDate());
        	// 业务日期
        	dbZxSaEquipSettleAudit.setBusinessDate(zxSaEquipSettleAudit.getBusinessDate());
        	// 结算期限开始时间
        	dbZxSaEquipSettleAudit.setBeginDate(zxSaEquipSettleAudit.getBeginDate());
        	// 结算期限结束时间
        	dbZxSaEquipSettleAudit.setEndDate(zxSaEquipSettleAudit.getEndDate());
        	//机械性能及运转情况评价
        	dbZxSaEquipSettleAudit.setAppraisal(zxSaEquipSettleAudit.getAppraisal());
        	//结算内容及说明
        	dbZxSaEquipSettleAudit.setContent(zxSaEquipSettleAudit.getContent());
        	// 备注
        	dbZxSaEquipSettleAudit.setRemark(zxSaEquipSettleAudit.getRemark());
        	// 流程id
        	dbZxSaEquipSettleAudit.setWorkId(zxSaEquipSettleAudit.getWorkId());
        	// 流程状态
        	dbZxSaEquipSettleAudit.setApih5FlowStatus(zxSaEquipSettleAudit.getApih5FlowStatus());
        	//流程的意见
//        	if(StrUtil.equals("opinionField1", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField1(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField1()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField2", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField2(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField2()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField3", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField3(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField3()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField4", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField4(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField4()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField5", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField5(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField5()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField6", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField6(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField6()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField7", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField7(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField7()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField8", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField8(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField8()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField9", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField9(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField9()));
//        	}
//        	//
//        	if(StrUtil.equals("opinionField10", zxSaEquipSettleAudit.getOpinionField(), true)){
//        		dbZxSaEquipSettleAudit.setOpinionField10(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField10()));
//        	}

			//修对应合同下的数据状态==问题票3654
			//3.当 某合同的结算单类型为最终结算 ，审核通过后 要把 对应合同中结算情况更新为“已最终结算”
			if(StrUtil.equals(zxSaEquipSettleAudit.getApih5FlowStatus(), "2")) {
				//0-中期
				//1-最终
				//2-返还保证金
				if(StrUtil.equals(dbZxSaEquipSettleAudit.getBillType(), "1")) {
					ZxCtEqContract contract = zxCtEqContractMapper.selectByPrimaryKey(dbZxSaEquipSettleAudit.getContractID());
					if(contract != null && StrUtil.isNotEmpty(contract.getContractID())) {
						contract.setSettleType("已最终结算");
						contract.setModifyUserInfo(userKey, realName);
						flag = zxCtEqContractMapper.updateByPrimaryKey(contract);
					}
				}
			}
           // 共通
           dbZxSaEquipSettleAudit.setModifyUserInfo(userKey, realName);
           flag = zxSaEquipSettleAuditMapper.updateByPrimaryKey(dbZxSaEquipSettleAudit);
       
           //附件先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   flag = zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxSaEquipSettleAudit.getZxErpFileList() != null && zxSaEquipSettleAudit.getZxErpFileList().size() >0) {
        	   for (ZxErpFile file : zxSaEquipSettleAudit.getZxErpFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherType("0");
        		   file.setOtherId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
        		   file.setCreateUserInfo(userKey, realName);
        		   flag = zxErpFileMapper.insert(file);
        	   }
           }
           if(zxSaEquipSettleAudit.getZxErpMainFileList() != null && zxSaEquipSettleAudit.getZxErpMainFileList().size() >0) {
           	for (ZxErpFile file : zxSaEquipSettleAudit.getZxErpMainFileList()) {
           		file.setUid(UuidUtil.generate());
           		file.setOtherType("1");
           		file.setOtherId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
           		file.setCreateUserInfo(userKey, realName);
           		flag = zxErpFileMapper.insert(file);
   			}
           }

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaEquipSettleAudit);
        }
    }

    @Override
	public ResponseEntity updateZxSaEquipSettleAuditForFlow(ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
    	   HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
           String userKey = TokenUtils.getUserKey(request);
           String realName = TokenUtils.getRealName(request);
           int flag = 0;
           ZxSaEquipSettleAudit dbZxSaEquipSettleAudit = zxSaEquipSettleAuditMapper.selectByPrimaryKey(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
           if (dbZxSaEquipSettleAudit != null && StrUtil.isNotEmpty(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId())) {
           	// 流程id
           	dbZxSaEquipSettleAudit.setWorkId(zxSaEquipSettleAudit.getWorkId());
           	// 流程状态
           	dbZxSaEquipSettleAudit.setApih5FlowStatus(zxSaEquipSettleAudit.getApih5FlowStatus());
           	//流程的意见
           	if(StrUtil.equals("opinionField1", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField1(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField1()));
           	}
           	//
           	if(StrUtil.equals("opinionField2", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField2(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField2()));
           	}
           	//
           	if(StrUtil.equals("opinionField3", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField3(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField3()));
           	}
           	//
           	if(StrUtil.equals("opinionField4", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField4(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField4()));
           	}
           	//
           	if(StrUtil.equals("opinionField5", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField5(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField5()));
           	}
           	//
           	if(StrUtil.equals("opinionField6", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField6(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField6()));
           	}
           	//
           	if(StrUtil.equals("opinionField7", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField7(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField7()));
           	}
           	//
           	if(StrUtil.equals("opinionField8", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField8(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField8()));
           	}
           	//
           	if(StrUtil.equals("opinionField9", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField9(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField9()));
           	}
           	//
           	if(StrUtil.equals("opinionField10", zxSaEquipSettleAudit.getOpinionField(), true)){
           		dbZxSaEquipSettleAudit.setOpinionField10(dbZxSaEquipSettleAudit.getOpinionContent(realName, dbZxSaEquipSettleAudit.getOpinionField10()));
           	}

   			//修对应合同下的数据状态==问题票3654
   			//3.当 某合同的结算单类型为最终结算 ，审核通过后 要把 对应合同中结算情况更新为“已最终结算”
   			if(StrUtil.equals(zxSaEquipSettleAudit.getApih5FlowStatus(), "2")) {
   				//0-中期
   				//1-最终
   				//2-返还保证金
   				if(StrUtil.equals(dbZxSaEquipSettleAudit.getBillType(), "1")) {
   					ZxCtEqContract contract = zxCtEqContractMapper.selectByPrimaryKey(dbZxSaEquipSettleAudit.getContractID());
   					if(contract != null && StrUtil.isNotEmpty(contract.getContractID())) {
   						contract.setSettleType("已最终结算");
   						contract.setModifyUserInfo(userKey, realName);
   						flag = zxCtEqContractMapper.updateByPrimaryKey(contract);
   					}
   				}
   			}
              // 共通
              dbZxSaEquipSettleAudit.setModifyUserInfo(userKey, realName);
              flag = zxSaEquipSettleAuditMapper.updateByPrimaryKey(dbZxSaEquipSettleAudit);
          
              //附件先删除再新增
              ZxErpFile delFile = new ZxErpFile();
              delFile.setOtherType("1");
              delFile.setOtherId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
              List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
              if(delFileList != null && delFileList.size() >0) {
           	   delFile.setModifyUserInfo(userKey, realName);
           	   flag = zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
              }
       
              if(zxSaEquipSettleAudit.getZxErpMainFileList() != null && zxSaEquipSettleAudit.getZxErpMainFileList().size() >0) {
              	for (ZxErpFile file : zxSaEquipSettleAudit.getZxErpMainFileList()) {
              		file.setUid(UuidUtil.generate());
              		file.setOtherType("1");
              		file.setOtherId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
              		file.setCreateUserInfo(userKey, realName);
              		flag = zxErpFileMapper.insert(file);
      			}
              }
           }
           // 失败
           if (flag == 0) {
               return repEntity.errorSave();
           } else {
               return repEntity.ok("sys.data.update",zxSaEquipSettleAudit);
           }
	}
    
    @Override
    public ResponseEntity batchDeleteUpdateZxSaEquipSettleAudit(List<ZxSaEquipSettleAudit> zxSaEquipSettleAuditList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	String token = TokenUtils.getToken(request);  
    	int flag = 0;
    	if (zxSaEquipSettleAuditList != null && zxSaEquipSettleAuditList.size() > 0) {
    		JSONArray jsonArray = new JSONArray();
    		for (ZxSaEquipSettleAudit zxSaEquipSettleAudit : zxSaEquipSettleAuditList) {
    			jsonArray.add(zxSaEquipSettleAudit.getWorkId());
    			//del附件
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				flag = zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    			//del清单
    			ZxSaEquipResSettle delResSettle = new ZxSaEquipResSettle();
    			delResSettle.setZxSaEquipSettleAuditId(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
    			List<ZxSaEquipResSettle> delResSettleList = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(delResSettle);
    			if(delResSettleList != null && delResSettleList.size() >0) {
    				for (ZxSaEquipResSettle zxSaEquipResSettle : delResSettleList) {
    					//del清单明细
    					ZxSaEquipResSettleItem delResSettleItem = new ZxSaEquipResSettleItem();
    					delResSettleItem.setZxSaEquipResSettleId(zxSaEquipResSettle.getZxSaEquipResSettleId());
    					List<ZxSaEquipResSettleItem> delResSettleItemList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(delResSettleItem);
    					if(delResSettleItemList != null && delResSettleItemList.size() >0) {
    						delResSettleItem.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipResSettleItemMapper.batchDeleteUpdateZxSaEquipResSettleItem(delResSettleItemList, delResSettleItem);
    					}
    				}
    				delResSettle.setModifyUserInfo(userKey, realName);
    				flag = zxSaEquipResSettleMapper.batchDeleteUpdateZxSaEquipResSettle(delResSettleList, delResSettle);
    			}
    			//del支付项
    			ZxSaEquipPaySettle delPaySettle = new ZxSaEquipPaySettle();
    			delPaySettle.setZxSaEquipSettleAuditId(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
    			List<ZxSaEquipPaySettle> delPaySettleList = zxSaEquipPaySettleMapper.selectByZxSaEquipPaySettleList(delPaySettle);
    			if(delPaySettleList != null && delPaySettleList.size() >0) {
    				for (ZxSaEquipPaySettle zxSaEquipPaySettle : delPaySettleList) {
    					//del支付项明细
    					ZxSaEquipPaySettleItem delPaySettleItem = new ZxSaEquipPaySettleItem();
    					delPaySettleItem.setZxSaEquipPaySettleId(zxSaEquipPaySettle.getZxSaEquipPaySettleId());
    					List<ZxSaEquipPaySettleItem> delPaySettleItemList = zxSaEquipPaySettleItemMapper.selectByZxSaEquipPaySettleItemList(delPaySettleItem);
    					if(delPaySettleItemList != null && delPaySettleItemList.size() >0) {
    						delPaySettleItem.setModifyUserInfo(userKey, realName);
    						flag = zxSaEquipPaySettleItemMapper.batchDeleteUpdateZxSaEquipPaySettleItem(delPaySettleItemList, delPaySettleItem);
    					}
    				}
    				delPaySettle.setModifyUserInfo(userKey, realName);
    				flag = zxSaEquipPaySettleMapper.batchDeleteUpdateZxSaEquipPaySettle(delPaySettleList, delPaySettle);
    			}
    			//del统计项
    			ZxSaEquipSettleAuditItem delAuditItem = new ZxSaEquipSettleAuditItem();
    			delAuditItem.setZxSaEquipSettleAuditId(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
    			List<ZxSaEquipSettleAuditItem> delAuditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(delAuditItem);
    			if(delAuditItemList != null && delAuditItemList.size() >0) {
    				flag = zxSaEquipSettleAuditItemMapper.batchDeleteUpdateZxSaEquipSettleAuditItem(delAuditItemList, delAuditItem);
    			}
    		}
    		//del流程
    		String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
    		String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
    		//del业务主数据
    		ZxSaEquipSettleAudit zxSaEquipSettleAudit = new ZxSaEquipSettleAudit();
    		zxSaEquipSettleAudit.setModifyUserInfo(userKey, realName);
    		flag = zxSaEquipSettleAuditMapper.batchDeleteUpdateZxSaEquipSettleAudit(zxSaEquipSettleAuditList, zxSaEquipSettleAudit);
    
    		//修对应合同下的数据状态==问题票3654
	        //2.当 某合同首次结算单被删除时，也要把 对应合同中结算情况恢复为“实际无结算”
			ZxSaEquipSettleAudit record = new ZxSaEquipSettleAudit();
			record.setContractID(zxSaEquipSettleAuditList.get(0).getContractID());
			List<ZxSaEquipSettleAudit> recordList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(record);
			if(recordList != null && recordList.size() >0) {
				
			}else {
				//当这个合同下没有结算单的时候才修改
				ZxCtEqContract contract = zxCtEqContractMapper.selectByPrimaryKey(zxSaEquipSettleAuditList.get(0).getContractID());
				if(contract != null && StrUtil.isNotEmpty(contract.getContractID())) {
					contract.setSettleType("实际无结算");
					contract.setModifyUserInfo(userKey, realName);
					flag = zxCtEqContractMapper.updateByPrimaryKey(contract);
				}
			}
    	}
    	// 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaEquipSettleAuditList);
        }
    }
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
	public ResponseEntity generateZxSaEquipSettleAuditAutoNum(ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
		if (zxSaEquipSettleAudit == null) {
			zxSaEquipSettleAudit = new ZxSaEquipSettleAudit();
		}
		int autoNum = 0;
		int countNo = 0;
		//根据项目id【orgId】,合同id【contractID】,
		ZxSaEquipSettleAudit equipSettleAudit = new ZxSaEquipSettleAudit();
		equipSettleAudit.setOrgID(zxSaEquipSettleAudit.getOrgID());
		equipSettleAudit.setContractID(zxSaEquipSettleAudit.getContractID());
		List<ZxSaEquipSettleAudit> zxSaEquipSettleAuditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(equipSettleAudit);
		if(zxSaEquipSettleAuditList != null && zxSaEquipSettleAuditList.size() >0) {
			countNo = zxSaEquipSettleAuditList.size()+1;
			autoNum = zxSaEquipSettleAuditList.get(0).getAutoNum();
		}else {
			autoNum = 0;
		}
		String settleTypeFlag = "flase";
		ZxCtEqContract contract = zxCtEqContractMapper.selectByPrimaryKey(zxSaEquipSettleAudit.getContractID());
		if(contract != null && StrUtil.isNotEmpty(contract.getContractID())) {
			if(StrUtil.equals(contract.getSettleType(), "已最终结算")) {
				settleTypeFlag = "true";
			}
		}
		zxSaEquipSettleAudit.setSettleTypeFlag(settleTypeFlag);
		zxSaEquipSettleAudit.setCountNo(countNo);
		zxSaEquipSettleAudit.setAutoNum(autoNum);
		//这个接口返回给前台signedNos和billNo和autoNum,让前台走新增的接口的时候再给我返回来，存到表里
		return repEntity.ok(zxSaEquipSettleAudit);
	}

	@Override
	public ResponseEntity taxZxSaEquipSettleAudit(ZxSaEquipSettleAudit zxSaEquipSettleAudit) {
		 if (zxSaEquipSettleAudit == null) {
	            zxSaEquipSettleAudit = new ZxSaEquipSettleAudit();
	        }
	        ZxSaEquipSettleAudit dbZxSaEquipSettleAudit = new ZxSaEquipSettleAudit();
	        if(StrUtil.isNotEmpty(zxSaEquipSettleAudit.getWorkId())) {
	        	ZxSaEquipSettleAudit audit = new ZxSaEquipSettleAudit();
	        	audit.setWorkId(zxSaEquipSettleAudit.getWorkId());
	        	List<ZxSaEquipSettleAudit> auditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(audit);
	        	if(auditList != null && auditList.size() >0) {
	        		dbZxSaEquipSettleAudit = auditList.get(0); 
	        	}
	        }else {
	        	// 获取数据
	        	dbZxSaEquipSettleAudit = zxSaEquipSettleAuditMapper.selectByPrimaryKey(zxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
	        }
	        // 数据存在
	        if (dbZxSaEquipSettleAudit != null) {	      
	        	//清单
	        	ZxSaEquipResSettle resSettle = new ZxSaEquipResSettle();
	        	resSettle.setZxSaEquipSettleAuditId(dbZxSaEquipSettleAudit.getZxSaEquipSettleAuditId());
	        	List<ZxSaEquipResSettle> resSettles = zxSaEquipResSettleMapper.selectByZxSaEquipResSettleList(resSettle);
	        	if(resSettles != null && resSettles.size() >0) {
	        		dbZxSaEquipSettleAudit.setZxSaEquipResSettleId(resSettles.get(0).getZxSaEquipResSettleId());
	        		dbZxSaEquipSettleAudit.setContractAmt(resSettles.get(0).getContractAmt());
	        		dbZxSaEquipSettleAudit.setChangeAmt(resSettles.get(0).getChangeAmt());
	        		dbZxSaEquipSettleAudit.setUpAmtRes(resSettles.get(0).getUpAmt());
	        		dbZxSaEquipSettleAudit.setThisAmtRes(resSettles.get(0).getThisAmt());
	        		dbZxSaEquipSettleAudit.setTotalAmtRes(resSettles.get(0).getTotalAmt());
	        		dbZxSaEquipSettleAudit.setThisAmtNoTaxRes(resSettles.get(0).getThisAmtNoTax());
	        		dbZxSaEquipSettleAudit.setThisAmtTaxRes(resSettles.get(0).getThisAmtTax());
	        		//清单明细
	        		ZxSaEquipResSettleItem resSettleItem = new ZxSaEquipResSettleItem();   
	        		resSettleItem.setZxSaEquipResSettleId(resSettles.get(0).getZxSaEquipResSettleId());
	        		List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList = zxSaEquipResSettleItemMapper.selectByZxSaEquipResSettleItemList(resSettleItem);
	        		for (ZxSaEquipResSettleItem resItem : zxSaEquipResSettleItemList) {
	        			resItem.setContractPriceNoTax(CalcUtils.calcDivide(resItem.getContractPrice(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));//含税合同单价/(1+税率/100)
	        			resItem.setContractAmtNoTax(CalcUtils.calcDivide(resItem.getContractAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));//=含税金额/(1+税率/100)
	        			resItem.setContractAmtTax(CalcUtils.calcSubtract(resItem.getContractAmt(), resItem.getContractAmtNoTax()));//含税合同金额 - 不含税合同金额
	        			resItem.setChangedAmtNoTax(CalcUtils.calcDivide(resItem.getChangedAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));//=含税金额/(1+税率/100)
	        			resItem.setChangedAmtTax(CalcUtils.calcSubtract(resItem.getChangedAmt(), resItem.getChangedAmtNoTax()));//含税合同金额 - 不含税合同金额
	        			resItem.setTotalAmtNoTax(CalcUtils.calcDivide(resItem.getTotalAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));//=含税金额/(1+税率/100)
	        			resItem.setTotalAmtTax(CalcUtils.calcSubtract(resItem.getTotalAmt(), resItem.getTotalAmtNoTax()));//含税合同金额 - 不含税合同金额
	        			resItem.setUpAmtNoTax(CalcUtils.calcDivide(resItem.getUpAmt(), CalcUtils.calcAdd(new BigDecimal("1"), CalcUtils.calcDivide(new BigDecimal(resItem.getTaxRate()), new BigDecimal("100")))));
	        			resItem.setUpAmtTax(CalcUtils.calcSubtract(resItem.getUpAmt(), resItem.getUpAmtNoTax()));
					}
	        		dbZxSaEquipSettleAudit.setZxSaEquipResSettleItemList(zxSaEquipResSettleItemList);
	        	}
	            return repEntity.ok(dbZxSaEquipSettleAudit);
	        } else {
	            return repEntity.layerMessage("no", "无数据！");
	        }
	}


}
