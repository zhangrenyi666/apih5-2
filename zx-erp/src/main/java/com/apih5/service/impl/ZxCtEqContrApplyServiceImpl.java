package com.apih5.service.impl;

import java.math.BigDecimal;
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
import com.apih5.mybatis.dao.ZxCrCustomerExtAttrMapper;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxCtEqContrApplyMapper;
import com.apih5.mybatis.dao.ZxCtEqContrItemMapper;
import com.apih5.mybatis.dao.ZxCtEqContractMapper;
import com.apih5.mybatis.dao.ZxCtWorkBookMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxCtEqContrApply;
import com.apih5.mybatis.pojo.ZxCtEqContrItem;
import com.apih5.mybatis.pojo.ZxCtEqContract;
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtEqContrApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zxCtEqContrApplyService")
public class ZxCtEqContrApplyServiceImpl implements ZxCtEqContrApplyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtEqContrApplyMapper zxCtEqContrApplyMapper;
    
    @Autowired(required = true)
    private ZxCtEqContrItemMapper zxCtEqContrItemMapper;
    
    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;
    
    @Autowired(required = true)
    private ZxCtWorkBookMapper zxCtWorkBookMapper;
    
    @Autowired(required = true)
    private ZxCtEqContractMapper zxCtEqContractMapper;
    
    @Autowired(required = true)
    private ZxCrCustomerExtAttrMapper zxCrCustomerExtAttrMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    
    @Override
    public ResponseEntity getZxCtEqContrApplyListByCondition(ZxCtEqContrApply zxCtEqContrApply) {
        if (zxCtEqContrApply == null) {
            zxCtEqContrApply = new ZxCtEqContrApply();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtEqContrApply.setComID("");
        	zxCtEqContrApply.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtEqContrApply.setComID(zxCtEqContrApply.getOrgID());
        	zxCtEqContrApply.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtEqContrApply.setOrgID(zxCtEqContrApply.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxCtEqContrApply.getPage(),zxCtEqContrApply.getLimit());
        // ????????????
        List<ZxCtEqContrApply> zxCtEqContrApplyList = zxCtEqContrApplyMapper.selectByZxCtEqContrApplyList(zxCtEqContrApply);
        for (ZxCtEqContrApply zxCtEqContrApply2 : zxCtEqContrApplyList) {
        	ZxCtEqContrItem item = zxCtEqContrItemMapper.getZxCtEqContrItemContractSumTotal(zxCtEqContrApply2.getZxCtEqContrApplyId());
        	if(item != null && StrUtil.isNotEmpty(item.getZxCtEqContrItemId())) {
        		//??????????????????(??????)
        		if(item.getContractSum() != null) {	
        			zxCtEqContrApply2.setContractCost(CalcUtils.calcDivide(item.getContractSum(), new BigDecimal("10000.000000"),6));
        		}
        		//?????????????????????(??????)
        		if(item.getContractSumNoTax() != null) {	
        			zxCtEqContrApply2.setContractCostNoTax(CalcUtils.calcDivide(item.getContractSumNoTax(), new BigDecimal("10000.000000"),6));
        		}
        		//????????????(??????)
        		if(item.getContractSum() != null) {	
        			zxCtEqContrApply2.setContractCostTax(CalcUtils.calcSubtract(zxCtEqContrApply2.getContractCost(), zxCtEqContrApply2.getContractCostNoTax()));
        		}
        	}
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherType("0");
        	file.setOtherId(zxCtEqContrApply2.getZxCtEqContrApplyId());
        	List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxCtEqContrApply2.setZxErpFileList(zxErpFileList);
        	file.setOtherType("1");
        	List<ZxErpFile> zxErpMainFileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxCtEqContrApply2.setZxErpMainFileList(zxErpMainFileList);
        }
        // ??????????????????
        PageInfo<ZxCtEqContrApply> p = new PageInfo<>(zxCtEqContrApplyList);
        return repEntity.okList(zxCtEqContrApplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtEqContrApplyDetail(ZxCtEqContrApply zxCtEqContrApply) {
    	if (zxCtEqContrApply == null) {
    		zxCtEqContrApply = new ZxCtEqContrApply();
    	}
    	ZxCtEqContrApply dbZxCtEqContrApply = new ZxCtEqContrApply();
    	if(StrUtil.isNotEmpty(zxCtEqContrApply.getWorkId())) {
    		ZxCtEqContrApply contrCsjz = new ZxCtEqContrApply();
    		contrCsjz.setWorkId(zxCtEqContrApply.getWorkId());
    		List<ZxCtEqContrApply> csList = zxCtEqContrApplyMapper.selectByZxCtEqContrApplyList(contrCsjz);
    		if(csList != null && csList.size() >0) {
    			dbZxCtEqContrApply = csList.get(0);
    		}
    	}else if(StrUtil.isNotEmpty(zxCtEqContrApply.getZxCtEqContrApplyId())) {
    		// ????????????
    		dbZxCtEqContrApply = zxCtEqContrApplyMapper.selectByPrimaryKey(zxCtEqContrApply.getZxCtEqContrApplyId());
    	}
    	if (dbZxCtEqContrApply != null && StrUtil.isNotEmpty(dbZxCtEqContrApply.getZxCtEqContrApplyId())) {
    		ZxCtEqContrItem record = new ZxCtEqContrItem();
    		record.setZxCtEqContrApplyId(dbZxCtEqContrApply.getZxCtEqContrApplyId());
    		List<ZxCtEqContrItem> itemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(record);
    		dbZxCtEqContrApply.setItemList(itemList);
    		
    		ZxCtEqContrItem item = zxCtEqContrItemMapper.getZxCtEqContrItemContractSumTotal(dbZxCtEqContrApply.getZxCtEqContrApplyId());
        	if(item != null && StrUtil.isNotEmpty(item.getZxCtEqContrItemId())) {
        		//??????????????????(??????)
        		if(item.getContractSum() != null) {	
        			dbZxCtEqContrApply.setContractCost(CalcUtils.calcDivide(item.getContractSum(), new BigDecimal("10000"),6));
        		}
        		//?????????????????????(??????)
        		if(item.getContractSumNoTax() != null) {	
        			dbZxCtEqContrApply.setContractCostNoTax(CalcUtils.calcDivide(item.getContractSumNoTax(), new BigDecimal("10000"),6));
        		}
        		//????????????(??????)
        		if(item.getContractSum() != null) {	
        			dbZxCtEqContrApply.setContractCostTax(CalcUtils.calcSubtract(dbZxCtEqContrApply.getContractCost(), dbZxCtEqContrApply.getContractCostNoTax()));
        		}
        	}
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherType("0");
        	file.setOtherId(dbZxCtEqContrApply.getZxCtEqContrApplyId());
        	List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
        	dbZxCtEqContrApply.setZxErpFileList(zxErpFileList);
        	file.setOtherType("1");
        	List<ZxErpFile> zxErpMainFileList = zxErpFileMapper.selectByZxErpFileList(file);
        	dbZxCtEqContrApply.setZxErpMainFileList(zxErpMainFileList);
    	}
    	
    	// ????????????
    	if (dbZxCtEqContrApply != null) {
    		return repEntity.ok(dbZxCtEqContrApply);
    	} else {
    		return repEntity.layerMessage("no", "????????????");
    	}
    }

    @Override
    public ResponseEntity saveZxCtEqContrApply(ZxCtEqContrApply zxCtEqContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtEqContrApply.setZxCtEqContrApplyId(UuidUtil.generate());
        zxCtEqContrApply.setApih5FlowStatus("-1");
        //??????????????????==?????????????????????type=2 ?????????????????????
                     //?????????????????????type=3 ?????????????????????
                     //?????????????????????type=4 ?????????????????????
        ZxCrCustomerExtAttr customerExtAttr = zxCrCustomerExtAttrMapper.selectByPrimaryKey(zxCtEqContrApply.getSecondID());
        if(customerExtAttr != null && StrUtil.isNotEmpty(customerExtAttr.getZxCrCustomerExtAttrId())) {
        	if(StrUtil.equals(customerExtAttr.getType(), "2")) {
        		zxCtEqContrApply.setSecondOrgType("???????????????");
        	}else if(StrUtil.equals(customerExtAttr.getType(), "3")) {
        		zxCtEqContrApply.setSecondOrgType("???????????????");
        	}else if(StrUtil.equals(customerExtAttr.getType(), "4")) {
        		zxCtEqContrApply.setSecondOrgType("???????????????");
        	}
        }
        zxCtEqContrApply.setCreateUserInfo(userKey, realName);
        int flag = zxCtEqContrApplyMapper.insert(zxCtEqContrApply);
        if(zxCtEqContrApply.getZxErpFileList() != null && zxCtEqContrApply.getZxErpFileList().size() >0) {
        	for (ZxErpFile file : zxCtEqContrApply.getZxErpFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherType("0");
        		file.setOtherId(zxCtEqContrApply.getZxCtEqContrApplyId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if(zxCtEqContrApply.getZxErpMainFileList() != null && zxCtEqContrApply.getZxErpMainFileList().size() >0) {
        	for (ZxErpFile file : zxCtEqContrApply.getZxErpMainFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherType("1");
        		file.setOtherId(zxCtEqContrApply.getZxCtEqContrApplyId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtEqContrApply);
        }
    }

    @Override
    public ResponseEntity updateZxCtEqContrApply(ZxCtEqContrApply zxCtEqContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtEqContrApply dbZxCtEqContrApply = zxCtEqContrApplyMapper.selectByPrimaryKey(zxCtEqContrApply.getZxCtEqContrApplyId());
        if (dbZxCtEqContrApply != null && StrUtil.isNotEmpty(dbZxCtEqContrApply.getZxCtEqContrApplyId())) {
           // ????????????
           dbZxCtEqContrApply.setContractName(zxCtEqContrApply.getContractName());
           // ??????id
           dbZxCtEqContrApply.setSecondID(zxCtEqContrApply.getSecondID());
           // ????????????
           dbZxCtEqContrApply.setSecondName(zxCtEqContrApply.getSecondName());
           //??????????????????==?????????????????????type=2 ?????????????????????
           //?????????????????????type=3 ?????????????????????
           //?????????????????????type=4 ?????????????????????
           ZxCrCustomerExtAttr customerExtAttr = zxCrCustomerExtAttrMapper.selectByPrimaryKey(zxCtEqContrApply.getSecondID());
           if(customerExtAttr != null && StrUtil.isNotEmpty(customerExtAttr.getZxCrCustomerExtAttrId())) {
        	   if(StrUtil.equals(customerExtAttr.getType(), "2")) {
        		   dbZxCtEqContrApply.setSecondOrgType("???????????????");
        	   }else if(StrUtil.equals(customerExtAttr.getType(), "3")) {
        		   dbZxCtEqContrApply.setSecondOrgType("???????????????");
        	   }else if(StrUtil.equals(customerExtAttr.getType(), "4")) {
        		   dbZxCtEqContrApply.setSecondOrgType("???????????????");
        	   }
           }
           // ????????????
           dbZxCtEqContrApply.setCsTimeLimit(zxCtEqContrApply.getCsTimeLimit());
           // ???????????????
           dbZxCtEqContrApply.setAgent(zxCtEqContrApply.getAgent());
           // ????????????
           dbZxCtEqContrApply.setContent(zxCtEqContrApply.getContent());
           //????????????
           dbZxCtEqContrApply.setIsDeduct(zxCtEqContrApply.getIsDeduct());
           // ??????
           dbZxCtEqContrApply.setRemark(zxCtEqContrApply.getRemark());
           // ??????id
           dbZxCtEqContrApply.setWorkId(zxCtEqContrApply.getWorkId());
           // ????????????
           dbZxCtEqContrApply.setApih5FlowStatus(zxCtEqContrApply.getApih5FlowStatus());
           //???????????????
			if(StrUtil.equals("opinionField1", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField1(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField1()));
			}
			//
			if(StrUtil.equals("opinionField2", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField2(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField2()));
			}
			//
			if(StrUtil.equals("opinionField3", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField3(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField3()));
			}
			//
			if(StrUtil.equals("opinionField4", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField4(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField4()));
			}
			//
			if(StrUtil.equals("opinionField5", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField5(dbZxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField5()));
			}
			//
			if(StrUtil.equals("opinionField6", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField6(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField6()));
			}
			//
			if(StrUtil.equals("opinionField7", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField7(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField7()));
			}
			//
			if(StrUtil.equals("opinionField8", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField8(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField8()));
			}
			//
			if(StrUtil.equals("opinionField9", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField9(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField9()));
			}
			//
			if(StrUtil.equals("opinionField10", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField10(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField10()));
			}
			if(StrUtil.equals("opinionField11", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField11(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField11()));
			}
			if(StrUtil.equals("opinionField12", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField12(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField12()));
			}
			if(StrUtil.equals("opinionField13", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField13(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField13()));
			}
			if(StrUtil.equals("opinionField14", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField14(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField14()));
			}
			if(StrUtil.equals("opinionField15", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField15(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField15()));
			}
			if(StrUtil.equals("opinionField16", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField16(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField16()));
			}
			if(StrUtil.equals("opinionField17", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField17(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField17()));
			}
			if(StrUtil.equals("opinionField18", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField18(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField18()));
			}
			if(StrUtil.equals("opinionField19", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField19(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField19()));
			}
			if(StrUtil.equals("opinionField20", zxCtEqContrApply.getOpinionField(), true)){
				dbZxCtEqContrApply.setOpinionField20(zxCtEqContrApply.getOpinionContent(realName, dbZxCtEqContrApply.getOpinionField20()));
			}
			
           // ??????
           dbZxCtEqContrApply.setModifyUserInfo(userKey, realName);
           flag = zxCtEqContrApplyMapper.updateByPrimaryKey(dbZxCtEqContrApply);
           
           //????????????????????????
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(dbZxCtEqContrApply.getZxCtEqContrApplyId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxCtEqContrApply.getZxErpFileList() != null && zxCtEqContrApply.getZxErpFileList().size() >0) {
        	   for (ZxErpFile file : zxCtEqContrApply.getZxErpFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherType("0");
        		   file.setOtherId(dbZxCtEqContrApply.getZxCtEqContrApplyId());
        		   file.setCreateUserInfo(userKey, realName);
        		   flag = zxErpFileMapper.insert(file);
        	   }
           }
           if(zxCtEqContrApply.getZxErpMainFileList() != null && zxCtEqContrApply.getZxErpMainFileList().size() >0) {
        	   for (ZxErpFile file : zxCtEqContrApply.getZxErpMainFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherType("1");
        		   file.setOtherId(dbZxCtEqContrApply.getZxCtEqContrApplyId());
        		   file.setCreateUserInfo(userKey, realName);
        		   flag = zxErpFileMapper.insert(file);
        	   }
           }

           
           //???????????????????????????????????????????????????
           if(StrUtil.equals(zxCtEqContrApply.getApih5FlowStatus(), "2")) {
        	   ZxCtEqContract contract = new ZxCtEqContract();
        	   BeanUtil.copyProperties(dbZxCtEqContrApply, contract);
        	   contract.setContractID(UuidUtil.generate());
        	   contract.setContent(dbZxCtEqContrApply.getContent());
        	   contract.setFirstId(dbZxCtEqContrApply.getFirstID());
        	   contract.setOrgID(dbZxCtEqContrApply.getFirstID());
        	   //??????????????????????????????????????????????????????????????????????????????????????????
        	   contract.setAgent("");
        	   contract.setSecondPrincipal(dbZxCtEqContrApply.getAgent());
        	   contract.setPp3(dbZxCtEqContrApply.getPp3());//????????????
        	   contract.setContractCategory(dbZxCtEqContrApply.getContractCategory());//???????????? ???????????????????????????????????????
        	   contract.setSettleType("???????????????");//????????????=????????????????????? 
        	   contract.setSignatureDate(dbZxCtEqContrApply.getModifyTime());//????????????=??????????????????????????????
        	   contract.setAuditStatus("????????????");//??????????????????=?????? ????????????
        	   contract.setContractStatus("1");//????????????=?????? 1?????????3??????
        	   ZxCtEqContrItem item = zxCtEqContrItemMapper.getZxCtEqContrItemContractSumTotal(dbZxCtEqContrApply.getZxCtEqContrApplyId());
    		   if(item != null && StrUtil.isNotEmpty(item.getZxCtEqContrItemId())) {
    			   //??????????????????(??????)
    			   if(item.getContractSum() != null) {	
    				   contract.setContractCost(CalcUtils.calcDivide(item.getContractSum(), new BigDecimal("10000"),6));
    			   }
    			   //?????????????????????(??????)
    			   if(item.getContractSumNoTax() != null) {	
    				   contract.setContractCostNoTax(CalcUtils.calcDivide(item.getContractSumNoTax(), new BigDecimal("10000"),6));
    			   }
    			   //????????????(??????)
    			   if(item.getContractSum() != null) {	
    				   contract.setContractCostTax(CalcUtils.calcSubtract(contract.getContractCost(), contract.getContractCostNoTax()));
    			   }
    		   }
        	   contract.setCreateUserInfo(userKey, realName);
        	   flag = zxCtEqContractMapper.insert(contract);
        	   //???????????????????????????????????????(??????????????????????????????contractID??????????????????????????????????????????)
        	   ZxCtEqContrItem record = new ZxCtEqContrItem();
        	   record.setZxCtEqContrApplyId(dbZxCtEqContrApply.getZxCtEqContrApplyId());
        	   List<ZxCtEqContrItem> itemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(record);
        	   if(itemList != null &&itemList.size() >0) {
        		   for (ZxCtEqContrItem zxCtEqContrItem : itemList) {
        			   zxCtEqContrItem.setContractID(contract.getContractID());
        			   flag = zxCtEqContrItemMapper.updateByPrimaryKey(zxCtEqContrItem);
        		   }
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtEqContrApply);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtEqContrApply(List<ZxCtEqContrApply> zxCtEqContrApplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);  
        int flag = 0;
        if (zxCtEqContrApplyList != null && zxCtEqContrApplyList.size() > 0) {
        	JSONArray jsonArray = new JSONArray();
        	//????????????
        	for (ZxCtEqContrApply zxCtEqContrApply : zxCtEqContrApplyList) {
        		jsonArray.add(zxCtEqContrApply.getWorkId());
        		ZxCtEqContrItem delItem = new ZxCtEqContrItem();
        		delItem.setZxCtEqContrApplyId(zxCtEqContrApply.getZxCtEqContrApplyId());
        		List<ZxCtEqContrItem> delItemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(delItem);
        		if(delItemList != null && delItemList.size() >0) {
        			delItem.setModifyUserInfo(userKey, realName);
        			zxCtEqContrItemMapper.batchDeleteUpdateZxCtEqContrItem(delItemList, delItem);
        		}
        		ZxErpFile delFile = new ZxErpFile();
        		delFile.setOtherId(zxCtEqContrApply.getZxCtEqContrApplyId());
        		List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
        		if(delFileList != null && delFileList.size() >0) {
        			delFile.setModifyUserInfo(userKey, realName);
        			zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
        		}
        	}
        	String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
        	String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
        	ZxCtEqContrApply zxCtEqContrApply = new ZxCtEqContrApply();
        	zxCtEqContrApply.setModifyUserInfo(userKey, realName);
        	flag = zxCtEqContrApplyMapper.batchDeleteUpdateZxCtEqContrApply(zxCtEqContrApplyList, zxCtEqContrApply);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtEqContrApplyList);
        }
    }

	@Override
	public ResponseEntity appZxCtEqContrApplyJudgeZxCtContractByContractStatus(ZxCtEqContrApply zxCtEqContrApply) {
		ZxCtContract zxCtContract = new ZxCtContract();
		zxCtContract.setSecondID(zxCtEqContrApply.getFirstID());
		List<ZxCtContract> contracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
		if(contracts != null && contracts.size() >0) {
			if(StrUtil.equals(contracts.get(0).getContractStatus(), "2")) {
				ZxCtWorkBook workBook = new ZxCtWorkBook();
				workBook.setContractID(contracts.get(0).getId());
				List<ZxCtWorkBook> books = zxCtWorkBookMapper.selectByZxCtWorkBookList(workBook);
				for (ZxCtWorkBook zxCtWorkBook : books) {
					if(StrUtil.equals(zxCtWorkBook.getStatus(), "1")) {
						
					}else {
						return repEntity.layerMessage("no", "????????????????????????????????????");
					}
				}
				
			}else {
				return repEntity.layerMessage("no", "??????????????????????????????");

			}
		}
		return null;
	}

	@Override
	public ResponseEntity generateZxCtEqContrApplyContractNo(ZxCtEqContrApply zxCtEqContrApply) {
		String contractNo = "";
		ZxCtContract zxCtContract = zxCtContractMapper.selectByPrimaryKey(zxCtEqContrApply.getZxCtContractId());
		if(zxCtContract != null && StrUtil.isNotEmpty(zxCtContract.getId())) {
//			contractNo = zxCtContract.getCode();
			contractNo = zxCtContract.getContractNo();
		}
		//????????????
		if(StrUtil.equals(zxCtEqContrApply.getContractCategory(), "0")) {
			contractNo = contractNo+"-JX-";
		}else if(StrUtil.equals(zxCtEqContrApply.getContractCategory(), "1")){
			contractNo = contractNo+"-SC-";
		}
		//?????????
		ZxCtEqContrApply record = new ZxCtEqContrApply();
		record.setOrgID(zxCtEqContrApply.getOrgID());
		record.setContractCategory(zxCtEqContrApply.getContractCategory());
		List<ZxCtEqContrApply> contrApplyList = zxCtEqContrApplyMapper.selectByZxCtEqContrApplyListAll(record);
		if(contrApplyList != null && contrApplyList.size() >0) {
			String auto = String.format("%03d", contrApplyList.size()+1); 
			contractNo = contractNo+auto;
		}else {
			contractNo = contractNo+"001";
		}
		zxCtEqContrApply.setContractNo(contractNo);
		return repEntity.ok(zxCtEqContrApply);
	}

	@Override
	public JSONObject checkZxCtEqContrApplyByFlow(ZxCtEqContrApply zxCtEqContrApply) {
		  HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String token = TokenUtils.getToken(request);
	        String flag = request.getParameter("flag");
	        String money = request.getParameter("money");
	        String workId = request.getParameter("workId");
	        
	        // ??????????????????
	        JSONObject jsonObjectPar = new JSONObject();
	        jsonObjectPar.set("workId", workId);
		    String url = Apih5Properties.getWebUrl() + "getSysFlowUserProjectType?flag=" + flag;
		    String result = HttpUtil.sendPostToken(url, jsonObjectPar.toString(), token);
		    JSONObject jsonObject = new JSONObject(result);
		    jsonObject.set("ju", true);
	        jsonObject.set("ht", true);
		    
		    // ?????????????????????
		    if(!jsonObject.getBool("zb")) {
	            return jsonObject;
	        } else if(!jsonObject.getBool("tg")) {
	            return jsonObject;
	        } else {
	            ZxCtEqContrApply zxCtEqContrApplySelect = new ZxCtEqContrApply();
	            zxCtEqContrApplySelect.setWorkId(workId);
	            List<ZxCtEqContrApply> zxCtEqContrApplyList = zxCtEqContrApplyMapper.selectByZxCtEqContrApplyList(zxCtEqContrApplySelect);
	            BigDecimal contractCost = zxCtEqContrApplyList.get(0).getContractCost();
	            BigDecimal compare = new BigDecimal(money);
	            // ????????????2000W????????????
	            if(CalcUtils.compareTo(contractCost, compare)>=0) {
	                jsonObject.set("ju", false);
	                jsonObject.set("ht", true);
	            } else {
	                // ???????????????????????????????????????
	                jsonObject.set("ju", true);
	                jsonObject.set("ht", false);
	            }
	        }
		     
	        return jsonObject;
	}

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
