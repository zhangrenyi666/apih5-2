package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

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
import com.apih5.mybatis.dao.ZxCrCustomerExtAttrMapper;
import com.apih5.mybatis.dao.ZxCrCustomerNewMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrApplyLeaseListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrApplyMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrApplyShopListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrLeaseListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContrShopListMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApply;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyLeaseList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrApplyShopList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrLeaseList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrShopList;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtSuppliesContrApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zxCtSuppliesContrApplyService")
public class ZxCtSuppliesContrApplyServiceImpl implements ZxCtSuppliesContrApplyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyMapper zxCtSuppliesContrApplyMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;

    @Autowired(required = true)
    private ZxCrCustomerExtAttrMapper zxCrCustomerExtAttrMapper;

    @Autowired(required = true)
    private ZxCrCustomerNewMapper zxCrCustomerNewMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrApplyShopListMapper zxCtSuppliesContrApplyShopListMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContrApplyLeaseListMapper zxCtSuppliesContrApplyLeaseListMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrShopListMapper zxCtSuppliesContrShopListMapper;
    
    @Autowired(required = true)
    private ZxCtSuppliesContrLeaseListMapper zxCtSuppliesContrLeaseListMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesContrApplyListByCondition(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        if (zxCtSuppliesContrApply == null) {
            zxCtSuppliesContrApply = new ZxCtSuppliesContrApply();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContrApply.setComID("");
        	zxCtSuppliesContrApply.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtSuppliesContrApply.setComID(zxCtSuppliesContrApply.getOrgID());
        	zxCtSuppliesContrApply.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtSuppliesContrApply.setOrgID(zxCtSuppliesContrApply.getOrgID());
        }    

        // ????????????
        PageHelper.startPage(zxCtSuppliesContrApply.getPage(),zxCtSuppliesContrApply.getLimit());
        // ????????????
        List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList = zxCtSuppliesContrApplyMapper.selectByZxCtSuppliesContrApplyList(zxCtSuppliesContrApply);
        // ??????????????????
        PageInfo<ZxCtSuppliesContrApply> p = new PageInfo<>(zxCtSuppliesContrApplyList);

        return repEntity.okList(zxCtSuppliesContrApplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrApplyDetail(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        if (zxCtSuppliesContrApply == null) {
            zxCtSuppliesContrApply = new ZxCtSuppliesContrApply();
        }
        // ????????????
        ZxCtSuppliesContrApply dbZxCtSuppliesContrApply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApply.getApplyId());
        // ????????????
        if (dbZxCtSuppliesContrApply != null) {
            return repEntity.ok(dbZxCtSuppliesContrApply);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrApply(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrApply.setApplyId(UuidUtil.generate());
        zxCtSuppliesContrApply.setApih5FlowStatus("-1");
        zxCtSuppliesContrApply.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrApplyMapper.insert(zxCtSuppliesContrApply);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//????????????????????????????????????????????????
        	ZxCtSuppliesContract zxCtSuppliesContract = new ZxCtSuppliesContract(); 
            zxCtSuppliesContract.setZxCtSuppliesContractId(UuidUtil.generate());
            zxCtSuppliesContract.setFromApplyID(zxCtSuppliesContrApply.getApplyId());
            zxCtSuppliesContract.setContractName(zxCtSuppliesContrApply.getContractName());
            zxCtSuppliesContract.setContractNo(zxCtSuppliesContrApply.getContractNo());
            zxCtSuppliesContract.setContractType(zxCtSuppliesContrApply.getContractType());
            zxCtSuppliesContract.setFirstName(zxCtSuppliesContrApply.getFirstName());
            zxCtSuppliesContract.setFirstId(zxCtSuppliesContrApply.getFirstID());
            zxCtSuppliesContract.setSecondID(zxCtSuppliesContrApply.getSecondID());
            zxCtSuppliesContract.setOrgID(zxCtSuppliesContrApply.getOrgID());
            zxCtSuppliesContract.setOrgName(zxCtSuppliesContrApply.getOrgName());
            zxCtSuppliesContract.setSecondName(zxCtSuppliesContrApply.getSecondName());
            zxCtSuppliesContract.setContractCost(zxCtSuppliesContrApply.getContractCost());
            zxCtSuppliesContract.setContractCostNoTax(zxCtSuppliesContrApply.getContractCostNoTax());
            zxCtSuppliesContract.setContractCostTax(zxCtSuppliesContrApply.getContractCostTax());
            zxCtSuppliesContract.setAlterContractSumTax(zxCtSuppliesContrApply.getUpAlterContractSumTax());
            zxCtSuppliesContract.setAlterContractSum(zxCtSuppliesContrApply.getUpAlterContractSum());
            zxCtSuppliesContract.setCsTimeLimit(zxCtSuppliesContrApply.getCsTimeLimit());
            zxCtSuppliesContract.setIsDeduct(zxCtSuppliesContrApply.getIsDeduct());
            zxCtSuppliesContract.setMaterialSource(zxCtSuppliesContrApply.getMaterialSource());
            zxCtSuppliesContract.setCode7(zxCtSuppliesContrApply.getCode7());
            zxCtSuppliesContract.setSettleType("1");
            zxCtSuppliesContract.setPp9("0");
            zxCtSuppliesContract.setContractStatus("0");
            zxCtSuppliesContract.setCreateUserInfo(userKey, realName);
            zxCtSuppliesContractMapper.insert(zxCtSuppliesContract);            	
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrApply);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrApply(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String isDeduct = "";
        int flag = 0;
        ZxCtSuppliesContrApply dbZxCtSuppliesContrApply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApply.getApplyId());
        if (dbZxCtSuppliesContrApply != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApply.getApplyId())) {
           // ??????????????????
           dbZxCtSuppliesContrApply.setCode3(zxCtSuppliesContrApply.getCode3());
           // ????????????
           dbZxCtSuppliesContrApply.setSecondName(zxCtSuppliesContrApply.getSecondName());
           // ??????ID
           dbZxCtSuppliesContrApply.setSecondID(zxCtSuppliesContrApply.getSecondID());
           // ?????????????????????
           dbZxCtSuppliesContrApply.setCodeP1(zxCtSuppliesContrApply.getCodeP1());
           // ??????????????????
           dbZxCtSuppliesContrApply.setSecondOrgType(zxCtSuppliesContrApply.getSecondOrgType());
           // ????????????????????????
           dbZxCtSuppliesContrApply.setCode4(zxCtSuppliesContrApply.getCode4());
           // ????????????
           dbZxCtSuppliesContrApply.setCode5(zxCtSuppliesContrApply.getCode5());
           // ????????????
           dbZxCtSuppliesContrApply.setMaterialSource(zxCtSuppliesContrApply.getMaterialSource());
           // ??????????????????
           dbZxCtSuppliesContrApply.setSecondIDCodeName(zxCtSuppliesContrApply.getSecondIDCodeName());
           // ??????????????????
           dbZxCtSuppliesContrApply.setSecondIDCodeBh(zxCtSuppliesContrApply.getSecondIDCodeBh());
           // ??????????????????
           dbZxCtSuppliesContrApply.setSecondIDCode(zxCtSuppliesContrApply.getSecondIDCode());
           // ????????????
           dbZxCtSuppliesContrApply.setCode2T3(zxCtSuppliesContrApply.getCode2T3());
           // ??????(%)
           dbZxCtSuppliesContrApply.setTaxRate(zxCtSuppliesContrApply.getTaxRate());
           // ??????????????????
           dbZxCtSuppliesContrApply.setIsFlagZhb(zxCtSuppliesContrApply.getIsFlagZhb());
           // ???????????????
           dbZxCtSuppliesContrApply.setIsFlag(zxCtSuppliesContrApply.getIsFlag());
           // ????????????
           isDeduct = dbZxCtSuppliesContrApply.getIsDeduct();
           dbZxCtSuppliesContrApply.setIsDeduct(zxCtSuppliesContrApply.getIsDeduct());
           // ????????????????????????
           dbZxCtSuppliesContrApply.setUpAlterContractSumTax(zxCtSuppliesContrApply.getUpAlterContractSumTax());
           // ?????????????????????????????????
           dbZxCtSuppliesContrApply.setUpAlterContractSumNoTax(zxCtSuppliesContrApply.getUpAlterContractSumNoTax());
           // ????????????????????????
           dbZxCtSuppliesContrApply.setUpAlterContractSum(zxCtSuppliesContrApply.getUpAlterContractSum());
           // ??????
           dbZxCtSuppliesContrApply.setPp8(zxCtSuppliesContrApply.getPp8());
           // ????????????
           dbZxCtSuppliesContrApply.setStatus(zxCtSuppliesContrApply.getStatus());
           // ????????????ID
           dbZxCtSuppliesContrApply.setInstProcessId(zxCtSuppliesContrApply.getInstProcessId());
           // ????????????
           dbZxCtSuppliesContrApply.setStartDate(zxCtSuppliesContrApply.getStartDate());
           // ????????????
           dbZxCtSuppliesContrApply.setEndDate(zxCtSuppliesContrApply.getEndDate());
           // ????????????
           dbZxCtSuppliesContrApply.setFirstName(zxCtSuppliesContrApply.getFirstName());
           // ??????ID
           dbZxCtSuppliesContrApply.setFirstID(zxCtSuppliesContrApply.getFirstID());
           // ????????????
           dbZxCtSuppliesContrApply.setCode1(zxCtSuppliesContrApply.getCode1());
           // ??????????????????
           dbZxCtSuppliesContrApply.setAccountUnitCode(zxCtSuppliesContrApply.getAccountUnitCode());
           // ????????????ID
           dbZxCtSuppliesContrApply.setAccountUnitId(zxCtSuppliesContrApply.getAccountUnitId());
           // ????????????
           dbZxCtSuppliesContrApply.setAccountUnitName(zxCtSuppliesContrApply.getAccountUnitName());
           // ??????????????????
           dbZxCtSuppliesContrApply.setAccountDepCode(zxCtSuppliesContrApply.getAccountDepCode());
           // ????????????id
           dbZxCtSuppliesContrApply.setAccountDepId(zxCtSuppliesContrApply.getAccountDepId());
           // ????????????
           dbZxCtSuppliesContrApply.setAccountDepName(zxCtSuppliesContrApply.getAccountDepName());
           // ????????????
           dbZxCtSuppliesContrApply.setCode8(zxCtSuppliesContrApply.getCode8());
           // ????????????(??????)
           dbZxCtSuppliesContrApply.setContractCostTax(zxCtSuppliesContrApply.getContractCostTax());
           // ???????????????
           dbZxCtSuppliesContrApply.setAgent(zxCtSuppliesContrApply.getAgent());
           // ????????????
           dbZxCtSuppliesContrApply.setContent(zxCtSuppliesContrApply.getContent());
           // ????????????
           dbZxCtSuppliesContrApply.setContractName(zxCtSuppliesContrApply.getContractName());
           
           dbZxCtSuppliesContrApply.setShopNumber(zxCtSuppliesContrApply.getShopNumber());
           
           dbZxCtSuppliesContrApply.setShopWay(zxCtSuppliesContrApply.getShopWay());
           // ????????????
           dbZxCtSuppliesContrApply.setContractType(zxCtSuppliesContrApply.getContractType());
           // ????????????
           dbZxCtSuppliesContrApply.setCode7(zxCtSuppliesContrApply.getCode7());
           // ????????????
           dbZxCtSuppliesContrApply.setCsTimeLimit(zxCtSuppliesContrApply.getCsTimeLimit());
           // ????????????
           dbZxCtSuppliesContrApply.setCode(zxCtSuppliesContrApply.getCode());
           // ????????????
           dbZxCtSuppliesContrApply.setContractNo(zxCtSuppliesContrApply.getContractNo());
           // ??????????????????(??????)
           dbZxCtSuppliesContrApply.setContractCost(zxCtSuppliesContrApply.getContractCost());
           // ????????????
           dbZxCtSuppliesContrApply.setOrgName(zxCtSuppliesContrApply.getOrgName());
           // ????????????ID
           dbZxCtSuppliesContrApply.setWorkitemID(zxCtSuppliesContrApply.getWorkitemID());
           // ??????????????????ID
           dbZxCtSuppliesContrApply.setSendToZhbID(zxCtSuppliesContrApply.getSendToZhbID());
           // ???????????????ID
           dbZxCtSuppliesContrApply.setSendToJuID(zxCtSuppliesContrApply.getSendToJuID());
           // ?????????
           dbZxCtSuppliesContrApply.setBeginPer(zxCtSuppliesContrApply.getBeginPer());
           // ??????????????????
           dbZxCtSuppliesContrApply.setCode2(zxCtSuppliesContrApply.getCode2());
           // ?????????????????????(??????)
           dbZxCtSuppliesContrApply.setContractCostNoTax(zxCtSuppliesContrApply.getContractCostNoTax());
           // ?????????
           dbZxCtSuppliesContrApply.setCode6(zxCtSuppliesContrApply.getCode6());
           // pp9
           dbZxCtSuppliesContrApply.setPp9(zxCtSuppliesContrApply.getPp9());
           // pp7
           dbZxCtSuppliesContrApply.setPp7(zxCtSuppliesContrApply.getPp7());
           // pp6
           dbZxCtSuppliesContrApply.setPp6(zxCtSuppliesContrApply.getPp6());
           // pp5
           dbZxCtSuppliesContrApply.setPp5(zxCtSuppliesContrApply.getPp5());
           // pp4
           dbZxCtSuppliesContrApply.setPp4(zxCtSuppliesContrApply.getPp4());
           // pp3
           dbZxCtSuppliesContrApply.setPp3(zxCtSuppliesContrApply.getPp3());
           // pp2
           dbZxCtSuppliesContrApply.setPp2(zxCtSuppliesContrApply.getPp2());
           // pp10
           dbZxCtSuppliesContrApply.setPp10(zxCtSuppliesContrApply.getPp10());
           // pp1
           dbZxCtSuppliesContrApply.setPp1(zxCtSuppliesContrApply.getPp1());
           // orgID
           dbZxCtSuppliesContrApply.setOrgID(zxCtSuppliesContrApply.getOrgID());
           // comID
           dbZxCtSuppliesContrApply.setComID(zxCtSuppliesContrApply.getComID());
           // combProp
           dbZxCtSuppliesContrApply.setCombProp(zxCtSuppliesContrApply.getCombProp());
           // ??????1
           dbZxCtSuppliesContrApply.setOpinionField1(zxCtSuppliesContrApply.getOpinionField1());
           // ??????2
           dbZxCtSuppliesContrApply.setOpinionField2(zxCtSuppliesContrApply.getOpinionField2());
           // ??????3
           dbZxCtSuppliesContrApply.setOpinionField3(zxCtSuppliesContrApply.getOpinionField3());
           // ??????4
           dbZxCtSuppliesContrApply.setOpinionField4(zxCtSuppliesContrApply.getOpinionField4());
           // ??????5
           dbZxCtSuppliesContrApply.setOpinionField5(zxCtSuppliesContrApply.getOpinionField5());
           // ??????6
           dbZxCtSuppliesContrApply.setOpinionField6(zxCtSuppliesContrApply.getOpinionField6());
           // ??????7
           dbZxCtSuppliesContrApply.setOpinionField7(zxCtSuppliesContrApply.getOpinionField7());
           // ??????8
           dbZxCtSuppliesContrApply.setOpinionField8(zxCtSuppliesContrApply.getOpinionField8());
           // ??????9
           dbZxCtSuppliesContrApply.setOpinionField9(zxCtSuppliesContrApply.getOpinionField9());
           // ??????10
           dbZxCtSuppliesContrApply.setOpinionField10(zxCtSuppliesContrApply.getOpinionField10());
           // ??????ID
           dbZxCtSuppliesContrApply.setApih5FlowId(zxCtSuppliesContrApply.getApih5FlowId());
           // work_id
//           dbZxCtSuppliesContrApply.setWorkId(zxCtSuppliesContrApply.getWorkId());
           // ????????????
           dbZxCtSuppliesContrApply.setApih5FlowStatus(zxCtSuppliesContrApply.getApih5FlowStatus());
           // ??????????????????
           dbZxCtSuppliesContrApply.setApih5FlowNodeStatus(zxCtSuppliesContrApply.getApih5FlowNodeStatus());
           // ????????????ID
           dbZxCtSuppliesContrApply.setResCategoryID(zxCtSuppliesContrApply.getResCategoryID());
           // ????????????
           dbZxCtSuppliesContrApply.setResCategoryName(zxCtSuppliesContrApply.getResCategoryName());
           // ??????
           dbZxCtSuppliesContrApply.setRemarks(zxCtSuppliesContrApply.getRemarks());
           // ??????
           dbZxCtSuppliesContrApply.setSort(zxCtSuppliesContrApply.getSort());
           // ??????
           dbZxCtSuppliesContrApply.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyMapper.updateByPrimaryKey(dbZxCtSuppliesContrApply);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//??????????????????????????????????????????????????????????????????????????????????????????????????????
        	if(!StrUtil.equals(isDeduct, zxCtSuppliesContrApply.getIsDeduct())) {
        		ZxCtSuppliesContrApplyShopList applyShop = new ZxCtSuppliesContrApplyShopList(); 
            	applyShop.setPp5(zxCtSuppliesContrApply.getPp5());        		
        		List<ZxCtSuppliesContrApplyShopList> applyShopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
        		for(ZxCtSuppliesContrApplyShopList shop : applyShopList) {
        			shop.setIsDeduct(zxCtSuppliesContrApply.getIsDeduct());
        			shop.setModifyUserInfo(userKey, realName);
        			zxCtSuppliesContrApplyShopListMapper.updateByPrimaryKeySelective(shop);
        		}
        		ZxCtSuppliesContrApplyLeaseList applyLease = new ZxCtSuppliesContrApplyLeaseList();
        		applyLease.setPp5(zxCtSuppliesContrApply.getPp5());        		
        		List<ZxCtSuppliesContrApplyLeaseList> applyLeaseList = zxCtSuppliesContrApplyLeaseListMapper.selectByZxCtSuppliesContrApplyLeaseListList(applyLease);
        		for(ZxCtSuppliesContrApplyLeaseList lease : applyLeaseList) {
        			lease.setIsDeduct(zxCtSuppliesContrApply.getIsDeduct());
        			lease.setModifyUserInfo(userKey, realName);
        			zxCtSuppliesContrApplyLeaseListMapper.updateByPrimaryKeySelective(lease);
        		}        		
        	}
            return repEntity.ok("sys.data.update",zxCtSuppliesContrApply);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApply(List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrApplyList != null && zxCtSuppliesContrApplyList.size() > 0) {
           ZxCtSuppliesContrApply zxCtSuppliesContrApply = new ZxCtSuppliesContrApply();
           zxCtSuppliesContrApply.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyMapper.batchDeleteUpdateZxCtSuppliesContrApply(zxCtSuppliesContrApplyList, zxCtSuppliesContrApply);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyList);
        }
    }
    
    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
	@Override
	public ResponseEntity getZxCtSuppliesContrApplyFlowDetail(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
		ZxCtSuppliesContrApply dbApply = zxCtSuppliesContrApplyMapper.getDetailByWorkId(zxCtSuppliesContrApply.getWorkId());
		if(dbApply != null) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(dbApply.getApplyId());
        	dbApply.setApplyFileList(zxErpFileMapper.selectByZxErpFileList(file));
		}
        return repEntity.ok(dbApply);
	}

	@Override
	public ResponseEntity updateZxCtSuppliesContrApplyFlow(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);		
		String userName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtSuppliesContrApply.getApih5FlowStatus().equals("1")) {
			ZxCtSuppliesContrApply flowDetail = zxCtSuppliesContrApplyMapper
					.getDetailByWorkId(zxCtSuppliesContrApply.getWorkId());
			// ??????????????????
			if (StrUtil.equals("opinionField1", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// ??????????????????
			if (StrUtil.equals("opinionField3", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setWorkId(zxCtSuppliesContrApply.getWorkId());
			flowDetail.setApih5FlowStatus("1");
			flowDetail.setModifyUserInfo(userKey, userName);
			flag = zxCtSuppliesContrApplyMapper.updateByPrimaryKey(flowDetail);
			if (flag == 0) {
				return repEntity.errorSave();
			} else {
	        	if(zxCtSuppliesContrApply.getApplyFileList() != null && zxCtSuppliesContrApply.getApplyFileList().size()>0) {
	            	ZxErpFile file = new ZxErpFile();
	            	file.setOtherId(flowDetail.getApplyId());
	            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
	            	file.setModifyUserInfo(userKey, userName);
	            	if(fileList.size()>0) {
	            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
	            	}
	         	   for(ZxErpFile zxErpFile : zxCtSuppliesContrApply.getApplyFileList()) {
	                    zxErpFile.setUid(UuidUtil.generate());
	                    zxErpFile.setOtherId(flowDetail.getApplyId());
	                    zxErpFile.setCreateUserInfo(userKey, userName);
	                    zxErpFileMapper.insert(zxErpFile);  
	         	   }        		
	        	}
				return repEntity.ok("sys.data.update", flowDetail);
			}
		} else if (zxCtSuppliesContrApply.getApih5FlowStatus().equals("2")) {
			ZxCtSuppliesContrApply flowDetail = zxCtSuppliesContrApplyMapper
					.getDetailByWorkId(zxCtSuppliesContrApply.getWorkId());
			// ??????????????????
			if (StrUtil.equals("opinionField1", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// ???????????????
			if (StrUtil.equals("opinionField2", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// ??????????????????
			if (StrUtil.equals("opinionField3", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField3(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField3()));
			}
			flowDetail.setApih5FlowStatus("2");
			flag = zxCtSuppliesContrApplyMapper.updateByPrimaryKeySelective(flowDetail);
        	if(zxCtSuppliesContrApply.getApplyFileList() != null && zxCtSuppliesContrApply.getApplyFileList().size()>0) {
            	ZxErpFile file = new ZxErpFile();
            	file.setOtherId(flowDetail.getApplyId());
            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            	file.setModifyUserInfo(userKey, userName);
            	if(fileList.size()>0) {
            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
            	}
         	   for(ZxErpFile zxErpFile : zxCtSuppliesContrApply.getApplyFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(flowDetail.getApplyId());
                    zxErpFile.setCreateUserInfo(userKey, userName);
                    zxErpFileMapper.insert(zxErpFile);  
         	   }        		
        	}			
        	//????????????????????????????????????????????????
			ZxCtSuppliesContract contract = new ZxCtSuppliesContract();
			contract.setFromApplyID(flowDetail.getApplyId());
			List<ZxCtSuppliesContract> contractList = zxCtSuppliesContractMapper.selectByZxCtSuppliesContractList(contract);
			if(contractList.size()>0) {
				ZxCtSuppliesContract dbContract = contractList.get(0);
				dbContract.setPp9("1");
				dbContract.setContractCost(flowDetail.getContractCost());
				dbContract.setContractCostNoTax(flowDetail.getContractCostNoTax());
				dbContract.setContractCostTax(flowDetail.getContractCostTax());
				dbContract.setAlterContractSumTax(flowDetail.getUpAlterContractSumTax());
				dbContract.setAlterContractSum(flowDetail.getUpAlterContractSum());		
				dbContract.setContent(flowDetail.getContent());
				dbContract.setComID(flowDetail.getComID());
				dbContract.setRemarks(flowDetail.getRemarks());
				dbContract.setContractStatus("1");		
				dbContract.setContractName(flowDetail.getContractName());		
				dbContract.setSecondID(flowDetail.getSecondID());
				dbContract.setAgent(flowDetail.getAgent());		
				dbContract.setIsDeduct(flowDetail.getIsDeduct());
				dbContract.setCsTimeLimit(flowDetail.getCsTimeLimit());	
				dbContract.setContent(flowDetail.getContent());			
				dbContract.setRemarks(flowDetail.getRemarks());			
				dbContract.setSignatureDate(new Date());
				dbContract.setSecondPrincipal(flowDetail.getAgent());
				dbContract.setModifyUserInfo(userKey, userName);
				zxCtSuppliesContractMapper.updateByPrimaryKeySelective(dbContract);
			}			
            //??????????????????
            if(StrUtil.equals(flowDetail.getCode7(), "WZ") || StrUtil.equals(flowDetail.getCode7(), "LC")) {
            	ZxCtSuppliesContrApplyShopList applyShop = new ZxCtSuppliesContrApplyShopList();
            	applyShop.setPp5(flowDetail.getApplyId());            	
                List<ZxCtSuppliesContrApplyShopList> applyShopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
                applyShopList.parallelStream().forEach((shop)->{
                	ZxCtSuppliesContrShopList zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
                    zxCtSuppliesContrShopList.setZxCtSuppliesContrShopListId(UuidUtil.generate());
                    zxCtSuppliesContrShopList.setContractID(shop.getContractID());
                    // ????????????
                    zxCtSuppliesContrShopList.setRentUnit(shop.getRentUnit());
                    // ??????
                    zxCtSuppliesContrShopList.setContrTrrm(shop.getContrTrrm());
                    // ????????????
                    zxCtSuppliesContrShopList.setWorkName(shop.getWorkName());
                    // ????????????ID
                    zxCtSuppliesContrShopList.setWorkTypeID(shop.getWorkTypeID());
                    // ????????????
                    zxCtSuppliesContrShopList.setWorkType(shop.getWorkType());
                    // ????????????ID
                    zxCtSuppliesContrShopList.setWorkID(shop.getWorkID());
                    // ????????????
                    zxCtSuppliesContrShopList.setWorkNo(shop.getWorkNo());
                    // ??????(%)
                    zxCtSuppliesContrShopList.setTaxRate(shop.getTaxRate());
                    // ??????
                    zxCtSuppliesContrShopList.setQty(shop.getQty());
                    // ????????????
                    zxCtSuppliesContrShopList.setIsNetPrice(shop.getIsNetPrice());
                    // ??????????????????
                    zxCtSuppliesContrShopList.setActualStartDate(shop.getActualStartDate());
                    // ??????????????????
                    zxCtSuppliesContrShopList.setActualEndDate(shop.getActualEndDate());
                    // ??????ID
                    zxCtSuppliesContrShopList.setPp5(shop.getPp5());
                    // ??????????????????
                    zxCtSuppliesContrShopList.setViewType(shop.getViewType());
                    // ??????????????????
                    zxCtSuppliesContrShopList.setPlanStartDate(shop.getPlanStartDate());
                    // ??????????????????
                    zxCtSuppliesContrShopList.setPlanEndDate(shop.getPlanEndDate());
                    // ??????ID
                    zxCtSuppliesContrShopList.setContractID(shop.getContractID());
                    // ????????????
                    zxCtSuppliesContrShopList.setContractSum(shop.getContractSum());
                    // ????????????
                    zxCtSuppliesContrShopList.setPrice(shop.getPrice());
                    // ????????????
                    zxCtSuppliesContrShopList.setSpec(shop.getSpec());
                    // ??????
                    zxCtSuppliesContrShopList.setUnit(shop.getUnit());
                    // ??????
                    zxCtSuppliesContrShopList.setTreenode(shop.getTreenode());
                    // ???????????????
                    zxCtSuppliesContrShopList.setContractSumNoTax(shop.getContractSumNoTax());
                    // ???????????????
                    zxCtSuppliesContrShopList.setPriceNoTax(shop.getPriceNoTax());
                    // ????????????
                    zxCtSuppliesContrShopList.setChangeDate(shop.getChangeDate());
                    // ???????????????
                    zxCtSuppliesContrShopList.setAlterTrrm(shop.getAlterTrrm());
                    // ???????????????
                    zxCtSuppliesContrShopList.setChangeQty(shop.getChangeQty());
                    // ?????????????????????
                    zxCtSuppliesContrShopList.setChangeContractSum(shop.getChangeContractSum());
                    // ?????????????????????
                    zxCtSuppliesContrShopList.setChangePrice(shop.getChangePrice());
                    // ????????????????????????
                    zxCtSuppliesContrShopList.setChangeContractSumNoTax(shop.getChangeContractSumNoTax());
                    // ????????????????????????
                    zxCtSuppliesContrShopList.setChangePriceNoTax(shop.getChangePriceNoTax());
                    // ???????????????
                    zxCtSuppliesContrShopList.setChangeContractSumTax(shop.getChangeContractSumTax());
                    // ??????
                    zxCtSuppliesContrShopList.setContractSumTax(shop.getContractSumTax());                    
                    // ??????
                    zxCtSuppliesContrShopList.setRemarks(zxCtSuppliesContrShopList.getRemarks());
                    zxCtSuppliesContrShopList.setCreateUserInfo(userKey, userName);
                    zxCtSuppliesContrShopListMapper.insert(zxCtSuppliesContrShopList);                	
                });
            }else if(StrUtil.equals(flowDetail.getCode7(), "WL")){
            	ZxCtSuppliesContrApplyLeaseList applyShop = new ZxCtSuppliesContrApplyLeaseList();
            	applyShop.setPp5(flowDetail.getApplyId());            	
                List<ZxCtSuppliesContrApplyLeaseList> applyShopList = zxCtSuppliesContrApplyLeaseListMapper.selectByZxCtSuppliesContrApplyLeaseListList(applyShop);
                applyShopList.parallelStream().forEach((shop)->{
                	ZxCtSuppliesContrLeaseList zxCtSuppliesContrLeaseList = new ZxCtSuppliesContrLeaseList();
                    zxCtSuppliesContrLeaseList.setZxCtSuppliesContrLeaseListId(UuidUtil.generate());
                    zxCtSuppliesContrLeaseList.setContractID(shop.getContractID());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setRentUnit(shop.getRentUnit());
                    // ??????
                    zxCtSuppliesContrLeaseList.setContrTrrm(shop.getContrTrrm());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setWorkName(shop.getWorkName());
                    // ????????????ID
                    zxCtSuppliesContrLeaseList.setWorkTypeID(shop.getWorkTypeID());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setWorkType(shop.getWorkType());
                    // ????????????ID
                    zxCtSuppliesContrLeaseList.setWorkID(shop.getWorkID());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setWorkNo(shop.getWorkNo());
                    // ??????(%)
                    zxCtSuppliesContrLeaseList.setTaxRate(shop.getTaxRate());
                    // ??????
                    zxCtSuppliesContrLeaseList.setQty(shop.getQty());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setIsNetPrice(shop.getIsNetPrice());
                    // ??????????????????
                    zxCtSuppliesContrLeaseList.setActualStartDate(shop.getActualStartDate());
                    // ??????????????????
                    zxCtSuppliesContrLeaseList.setActualEndDate(shop.getActualEndDate());
                    // ??????ID
                    zxCtSuppliesContrLeaseList.setPp5(shop.getPp5());
                    // ??????????????????
                    zxCtSuppliesContrLeaseList.setViewType(shop.getViewType());
                    // ??????????????????
                    zxCtSuppliesContrLeaseList.setPlanStartDate(shop.getPlanStartDate());
                    // ??????????????????
                    zxCtSuppliesContrLeaseList.setPlanEndDate(shop.getPlanEndDate());
                    // ??????ID
                    zxCtSuppliesContrLeaseList.setContractID(shop.getContractID());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setContractSum(shop.getContractSum());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setPrice(shop.getPrice());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setSpec(shop.getSpec());
                    // ??????
                    zxCtSuppliesContrLeaseList.setTreenode(shop.getTreenode());
                    // ??????
                    zxCtSuppliesContrLeaseList.setUnit(shop.getUnit());
                    // ???????????????
                    zxCtSuppliesContrLeaseList.setContractSumNoTax(shop.getContractSumNoTax());
                    // ???????????????
                    zxCtSuppliesContrLeaseList.setPriceNoTax(shop.getPriceNoTax());
                    // ????????????
                    zxCtSuppliesContrLeaseList.setChangeDate(shop.getChangeDate());
                    // ???????????????
                    zxCtSuppliesContrLeaseList.setAlterTrrm(shop.getAlterTrrm());
                    // ???????????????
                    zxCtSuppliesContrLeaseList.setChangeQty(shop.getChangeQty());
                    // ?????????????????????
                    zxCtSuppliesContrLeaseList.setChangeContractSum(shop.getChangeContractSum());
                    // ?????????????????????
                    zxCtSuppliesContrLeaseList.setChangePrice(shop.getChangePrice());
                    // ????????????????????????
                    zxCtSuppliesContrLeaseList.setChangeContractSumNoTax(shop.getChangeContractSumNoTax());
                    // ???????????????
                    zxCtSuppliesContrLeaseList.setChangeContractSumTax(shop.getChangeContractSumTax());
                    // ??????
                    zxCtSuppliesContrLeaseList.setContractSumTax(shop.getContractSumTax());
                    // ????????????????????????
                    zxCtSuppliesContrLeaseList.setChangePriceNoTax(shop.getChangePriceNoTax());            
                    zxCtSuppliesContrLeaseList.setCreateUserInfo(userKey, userName);
                    zxCtSuppliesContrLeaseListMapper.insert(zxCtSuppliesContrLeaseList);            	
                });            	
            }
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {		
			return repEntity.ok("sys.data.update", zxCtSuppliesContrApply);
		}
	}

	@Override
	public ResponseEntity addZxCtSuppliesContrApplyFlow(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);		
		String realName = TokenUtils.getRealName(request);
        ZxCtSuppliesContrApply dbZxCtSuppliesContrApply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApply.getApplyId());
        if (dbZxCtSuppliesContrApply != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrApply.getApplyId())) {
        	dbZxCtSuppliesContrApply.setApih5FlowStatus("0");
        	//?????????:0;????????????:1;????????????:2;???????????????:3;????????????:4;????????????:5;????????????:6
        	dbZxCtSuppliesContrApply.setStatus("1");
        	dbZxCtSuppliesContrApply.setWorkId(zxCtSuppliesContrApply.getWorkId());
        	dbZxCtSuppliesContrApply.setModifyUserInfo(userKey, realName);
        	zxCtSuppliesContrApplyMapper.updateByPrimaryKey(dbZxCtSuppliesContrApply);
        	if(zxCtSuppliesContrApply.getApplyFileList() != null && zxCtSuppliesContrApply.getApplyFileList().size()>0) {
            	ZxErpFile file = new ZxErpFile();
            	file.setOtherId(dbZxCtSuppliesContrApply.getApplyId());
            	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            	file.setModifyUserInfo(userKey, realName);
            	if(fileList.size()>0) {
            		zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
            	}
         	   for(ZxErpFile zxErpFile : zxCtSuppliesContrApply.getApplyFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(dbZxCtSuppliesContrApply.getApplyId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);  
         	   }        		
        	}
        }
		return repEntity.ok("sys.data.update", zxCtSuppliesContrApply);
	}

	@Override
	public ResponseEntity getZxCtSuppliesContrNoByCode(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
		String code = "";
		code = zxCtSuppliesContrApplyMapper.getZxCtSuppliesContrNoByCode(zxCtSuppliesContrApply);
		if(StrUtil.isNotEmpty(code)) {
			DecimalFormat decimalFormat=new DecimalFormat("000");		
//			String headCode = code.substring(0, code.lastIndexOf("-")+1);
			 String[] numArr = code.split("-", code.lastIndexOf("-"));
		        String num =decimalFormat.format(Integer.parseInt(numArr[numArr.length-1])+1);
//		        code =  headCode + num;
		        code =  zxCtSuppliesContrApply.getContractNum()+"-"+zxCtSuppliesContrApply.getCode7() +"-"+ num;
		}else {
			code = zxCtSuppliesContrApply.getContractNum()+"-"+zxCtSuppliesContrApply.getCode7()+"-001";
		}
		return repEntity.ok(code);
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCtSuppliesContrApplyFlow(
			List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxCtSuppliesContrApplyList != null && zxCtSuppliesContrApplyList.size() > 0) {
            for(ZxCtSuppliesContrApply apply : zxCtSuppliesContrApplyList){
            	if(StrUtil.isNotEmpty(apply.getWorkId())) {
            		jsonArr.add(apply.getWorkId());
            	}
            }
           ZxCtSuppliesContrApply zxCtSuppliesContrApply = new ZxCtSuppliesContrApply();
           zxCtSuppliesContrApply.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyMapper.batchDeleteUpdateZxCtSuppliesContrApply(zxCtSuppliesContrApplyList, zxCtSuppliesContrApply);
        }
//      //????????????????????????
        if(jsonArr.size()>0) {
            HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyList);
        }
	}

	@Override
    public JSONObject checkZxCtSuppliesContrApplyByFlow(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
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
            ZxCtSuppliesContrApply zxCtSuppliesContrApplySelect = new ZxCtSuppliesContrApply();
            zxCtSuppliesContrApplySelect.setWorkId(workId);
            List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList = zxCtSuppliesContrApplyMapper.selectByZxCtSuppliesContrApplyList(zxCtSuppliesContrApplySelect);
            BigDecimal contractCost = zxCtSuppliesContrApplyList.get(0).getContractCost();
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

}
