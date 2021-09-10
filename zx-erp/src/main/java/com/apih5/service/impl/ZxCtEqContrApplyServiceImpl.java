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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtEqContrApply.setComID("");
        	zxCtEqContrApply.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtEqContrApply.setComID(zxCtEqContrApply.getOrgID());
        	zxCtEqContrApply.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtEqContrApply.setOrgID(zxCtEqContrApply.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxCtEqContrApply.getPage(),zxCtEqContrApply.getLimit());
        // 获取数据
        List<ZxCtEqContrApply> zxCtEqContrApplyList = zxCtEqContrApplyMapper.selectByZxCtEqContrApplyList(zxCtEqContrApply);
        for (ZxCtEqContrApply zxCtEqContrApply2 : zxCtEqContrApplyList) {
        	ZxCtEqContrItem item = zxCtEqContrItemMapper.getZxCtEqContrItemContractSumTotal(zxCtEqContrApply2.getZxCtEqContrApplyId());
        	if(item != null && StrUtil.isNotEmpty(item.getZxCtEqContrItemId())) {
        		//含税合同金额(万元)
        		if(item.getContractSum() != null) {	
        			zxCtEqContrApply2.setContractCost(CalcUtils.calcDivide(item.getContractSum(), new BigDecimal("10000.000000"),6));
        		}
        		//不含税合同金额(万元)
        		if(item.getContractSumNoTax() != null) {	
        			zxCtEqContrApply2.setContractCostNoTax(CalcUtils.calcDivide(item.getContractSumNoTax(), new BigDecimal("10000.000000"),6));
        		}
        		//合同税额(万元)
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
        // 得到分页信息
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
    		// 获取数据
    		dbZxCtEqContrApply = zxCtEqContrApplyMapper.selectByPrimaryKey(zxCtEqContrApply.getZxCtEqContrApplyId());
    	}
    	if (dbZxCtEqContrApply != null && StrUtil.isNotEmpty(dbZxCtEqContrApply.getZxCtEqContrApplyId())) {
    		ZxCtEqContrItem record = new ZxCtEqContrItem();
    		record.setZxCtEqContrApplyId(dbZxCtEqContrApply.getZxCtEqContrApplyId());
    		List<ZxCtEqContrItem> itemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(record);
    		dbZxCtEqContrApply.setItemList(itemList);
    		
    		ZxCtEqContrItem item = zxCtEqContrItemMapper.getZxCtEqContrItemContractSumTotal(dbZxCtEqContrApply.getZxCtEqContrApplyId());
        	if(item != null && StrUtil.isNotEmpty(item.getZxCtEqContrItemId())) {
        		//含税合同金额(万元)
        		if(item.getContractSum() != null) {	
        			dbZxCtEqContrApply.setContractCost(CalcUtils.calcDivide(item.getContractSum(), new BigDecimal("10000"),6));
        		}
        		//不含税合同金额(万元)
        		if(item.getContractSumNoTax() != null) {	
        			dbZxCtEqContrApply.setContractCostNoTax(CalcUtils.calcDivide(item.getContractSumNoTax(), new BigDecimal("10000"),6));
        		}
        		//合同税额(万元)
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
    	
    	// 数据存在
    	if (dbZxCtEqContrApply != null) {
    		return repEntity.ok(dbZxCtEqContrApply);
    	} else {
    		return repEntity.layerMessage("no", "无数据！");
    	}
    }

    @Override
    public ResponseEntity saveZxCtEqContrApply(ZxCtEqContrApply zxCtEqContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtEqContrApply.setZxCtEqContrApplyId(UuidUtil.generate());
        zxCtEqContrApply.setApih5FlowStatus("-1");
        //协作单位类型==乙方数据对应的type=2 即，设备生产商
                     //乙方数据对应的type=3 即，设备出租商
                     //乙方数据对应的type=4 即，设备经销商
        ZxCrCustomerExtAttr customerExtAttr = zxCrCustomerExtAttrMapper.selectByPrimaryKey(zxCtEqContrApply.getSecondID());
        if(customerExtAttr != null && StrUtil.isNotEmpty(customerExtAttr.getZxCrCustomerExtAttrId())) {
        	if(StrUtil.equals(customerExtAttr.getType(), "2")) {
        		zxCtEqContrApply.setSecondOrgType("设备生产商");
        	}else if(StrUtil.equals(customerExtAttr.getType(), "3")) {
        		zxCtEqContrApply.setSecondOrgType("设备出租商");
        	}else if(StrUtil.equals(customerExtAttr.getType(), "4")) {
        		zxCtEqContrApply.setSecondOrgType("设备经销商");
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
           // 合同名称
           dbZxCtEqContrApply.setContractName(zxCtEqContrApply.getContractName());
           // 乙方id
           dbZxCtEqContrApply.setSecondID(zxCtEqContrApply.getSecondID());
           // 乙方名称
           dbZxCtEqContrApply.setSecondName(zxCtEqContrApply.getSecondName());
           //协作单位类型==乙方数据对应的type=2 即，设备生产商
           //乙方数据对应的type=3 即，设备出租商
           //乙方数据对应的type=4 即，设备经销商
           ZxCrCustomerExtAttr customerExtAttr = zxCrCustomerExtAttrMapper.selectByPrimaryKey(zxCtEqContrApply.getSecondID());
           if(customerExtAttr != null && StrUtil.isNotEmpty(customerExtAttr.getZxCrCustomerExtAttrId())) {
        	   if(StrUtil.equals(customerExtAttr.getType(), "2")) {
        		   dbZxCtEqContrApply.setSecondOrgType("设备生产商");
        	   }else if(StrUtil.equals(customerExtAttr.getType(), "3")) {
        		   dbZxCtEqContrApply.setSecondOrgType("设备出租商");
        	   }else if(StrUtil.equals(customerExtAttr.getType(), "4")) {
        		   dbZxCtEqContrApply.setSecondOrgType("设备经销商");
        	   }
           }
           // 合同工期
           dbZxCtEqContrApply.setCsTimeLimit(zxCtEqContrApply.getCsTimeLimit());
           // 合同签订人
           dbZxCtEqContrApply.setAgent(zxCtEqContrApply.getAgent());
           // 合同内容
           dbZxCtEqContrApply.setContent(zxCtEqContrApply.getContent());
           //是否抵扣
           dbZxCtEqContrApply.setIsDeduct(zxCtEqContrApply.getIsDeduct());
           // 备注
           dbZxCtEqContrApply.setRemark(zxCtEqContrApply.getRemark());
           // 流程id
           dbZxCtEqContrApply.setWorkId(zxCtEqContrApply.getWorkId());
           // 流程状态
           dbZxCtEqContrApply.setApih5FlowStatus(zxCtEqContrApply.getApih5FlowStatus());
           //流程的意见
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
			
           // 共通
           dbZxCtEqContrApply.setModifyUserInfo(userKey, realName);
           flag = zxCtEqContrApplyMapper.updateByPrimaryKey(dbZxCtEqContrApply);
           
           //附件先删除再新增
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

           
           //评审审核通过生成一条对应的台账数据
           if(StrUtil.equals(zxCtEqContrApply.getApih5FlowStatus(), "2")) {
        	   ZxCtEqContract contract = new ZxCtEqContract();
        	   BeanUtil.copyProperties(dbZxCtEqContrApply, contract);
        	   contract.setContractID(UuidUtil.generate());
        	   contract.setContent(dbZxCtEqContrApply.getContent());
        	   contract.setFirstId(dbZxCtEqContrApply.getFirstID());
        	   contract.setOrgID(dbZxCtEqContrApply.getFirstID());
        	   //合同评审通过生成的管理数据，把“合同签订人”带入“乙方代表”
        	   contract.setAgent("");
        	   contract.setSecondPrincipal(dbZxCtEqContrApply.getAgent());
        	   contract.setPp3(dbZxCtEqContrApply.getPp3());//合同类型
        	   contract.setContractCategory(dbZxCtEqContrApply.getContractCategory());//合同类别 根据源数据“合同类别”带入
        	   contract.setSettleType("实际无结算");//结算情况=默认实际无结算 
        	   contract.setSignatureDate(dbZxCtEqContrApply.getModifyTime());//签订时间=按评审通过的当前日期
        	   contract.setAuditStatus("评审通过");//合同录入类型=默认 评审通过
        	   contract.setContractStatus("1");//合同状态=默认 1执行中3终止
        	   ZxCtEqContrItem item = zxCtEqContrItemMapper.getZxCtEqContrItemContractSumTotal(dbZxCtEqContrApply.getZxCtEqContrApplyId());
    		   if(item != null && StrUtil.isNotEmpty(item.getZxCtEqContrItemId())) {
    			   //含税合同金额(万元)
    			   if(item.getContractSum() != null) {	
    				   contract.setContractCost(CalcUtils.calcDivide(item.getContractSum(), new BigDecimal("10000"),6));
    			   }
    			   //不含税合同金额(万元)
    			   if(item.getContractSumNoTax() != null) {	
    				   contract.setContractCostNoTax(CalcUtils.calcDivide(item.getContractSumNoTax(), new BigDecimal("10000"),6));
    			   }
    			   //合同税额(万元)
    			   if(item.getContractSum() != null) {	
    				   contract.setContractCostTax(CalcUtils.calcSubtract(contract.getContractCost(), contract.getContractCostNoTax()));
    			   }
    		   }
        	   contract.setCreateUserInfo(userKey, realName);
        	   flag = zxCtEqContractMapper.insert(contract);
        	   //将对应的合同清单也生成一下(这里只是将清单表中的contractID赋值，并不是新增，走的是修改)
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
        // 失败
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
        	//删除明细
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
        // 失败
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
						return repEntity.layerMessage("no", "业主合同台账清单未审核！");
					}
				}
				
			}else {
				return repEntity.layerMessage("no", "业主合同台账未审核！");

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
		//合同类别
		if(StrUtil.equals(zxCtEqContrApply.getContractCategory(), "0")) {
			contractNo = contractNo+"-JX-";
		}else if(StrUtil.equals(zxCtEqContrApply.getContractCategory(), "1")){
			contractNo = contractNo+"-SC-";
		}
		//查次数
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
	        
	        // 项目类型获取
	        JSONObject jsonObjectPar = new JSONObject();
	        jsonObjectPar.set("workId", workId);
		    String url = Apih5Properties.getWebUrl() + "getSysFlowUserProjectType?flag=" + flag;
		    String result = HttpUtil.sendPostToken(url, jsonObjectPar.toString(), token);
		    JSONObject jsonObject = new JSONObject(result);
		    jsonObject.set("ju", true);
	        jsonObject.set("ht", true);
		    
		    // 公司→总部或局
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
	            // 金额大于2000W，局审批
	            if(CalcUtils.compareTo(contractCost, compare)>=0) {
	                jsonObject.set("ju", false);
	                jsonObject.set("ht", true);
	            } else {
	                // 否则本项目流程（合同校对）
	                jsonObject.set("ju", true);
	                jsonObject.set("ht", false);
	            }
	        }
		     
	        return jsonObject;
	}

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
