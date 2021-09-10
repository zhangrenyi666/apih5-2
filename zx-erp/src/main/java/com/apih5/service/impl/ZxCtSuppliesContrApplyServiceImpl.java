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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtSuppliesContrApply.setComID("");
        	zxCtSuppliesContrApply.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtSuppliesContrApply.setComID(zxCtSuppliesContrApply.getOrgID());
        	zxCtSuppliesContrApply.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtSuppliesContrApply.setOrgID(zxCtSuppliesContrApply.getOrgID());
        }    

        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrApply.getPage(),zxCtSuppliesContrApply.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList = zxCtSuppliesContrApplyMapper.selectByZxCtSuppliesContrApplyList(zxCtSuppliesContrApply);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrApply> p = new PageInfo<>(zxCtSuppliesContrApplyList);

        return repEntity.okList(zxCtSuppliesContrApplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrApplyDetail(ZxCtSuppliesContrApply zxCtSuppliesContrApply) {
        if (zxCtSuppliesContrApply == null) {
            zxCtSuppliesContrApply = new ZxCtSuppliesContrApply();
        }
        // 获取数据
        ZxCtSuppliesContrApply dbZxCtSuppliesContrApply = zxCtSuppliesContrApplyMapper.selectByPrimaryKey(zxCtSuppliesContrApply.getApplyId());
        // 数据存在
        if (dbZxCtSuppliesContrApply != null) {
            return repEntity.ok(dbZxCtSuppliesContrApply);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
        	//审批通过，直接新增同步到合同表中
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
           // 中标单位简称
           dbZxCtSuppliesContrApply.setCode3(zxCtSuppliesContrApply.getCode3());
           // 乙方名称
           dbZxCtSuppliesContrApply.setSecondName(zxCtSuppliesContrApply.getSecondName());
           // 乙方ID
           dbZxCtSuppliesContrApply.setSecondID(zxCtSuppliesContrApply.getSecondID());
           // 业主合同功能码
           dbZxCtSuppliesContrApply.setCodeP1(zxCtSuppliesContrApply.getCodeP1());
           // 协作单位类型
           dbZxCtSuppliesContrApply.setSecondOrgType(zxCtSuppliesContrApply.getSecondOrgType());
           // 项目所在省份简称
           dbZxCtSuppliesContrApply.setCode4(zxCtSuppliesContrApply.getCode4());
           // 项目简称
           dbZxCtSuppliesContrApply.setCode5(zxCtSuppliesContrApply.getCode5());
           // 物资来源
           dbZxCtSuppliesContrApply.setMaterialSource(zxCtSuppliesContrApply.getMaterialSource());
           // 往来单位名称
           dbZxCtSuppliesContrApply.setSecondIDCodeName(zxCtSuppliesContrApply.getSecondIDCodeName());
           // 往来单位编号
           dbZxCtSuppliesContrApply.setSecondIDCodeBh(zxCtSuppliesContrApply.getSecondIDCodeBh());
           // 往来单位编号
           dbZxCtSuppliesContrApply.setSecondIDCode(zxCtSuppliesContrApply.getSecondIDCode());
           // 同一公司
           dbZxCtSuppliesContrApply.setCode2T3(zxCtSuppliesContrApply.getCode2T3());
           // 税率(%)
           dbZxCtSuppliesContrApply.setTaxRate(zxCtSuppliesContrApply.getTaxRate());
           // 是否局指审批
           dbZxCtSuppliesContrApply.setIsFlagZhb(zxCtSuppliesContrApply.getIsFlagZhb());
           // 是否局审批
           dbZxCtSuppliesContrApply.setIsFlag(zxCtSuppliesContrApply.getIsFlag());
           // 是否抵扣
           isDeduct = dbZxCtSuppliesContrApply.getIsDeduct();
           dbZxCtSuppliesContrApply.setIsDeduct(zxCtSuppliesContrApply.getIsDeduct());
           // 上期末变更后税额
           dbZxCtSuppliesContrApply.setUpAlterContractSumTax(zxCtSuppliesContrApply.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
           dbZxCtSuppliesContrApply.setUpAlterContractSumNoTax(zxCtSuppliesContrApply.getUpAlterContractSumNoTax());
           // 上期末变更后金额
           dbZxCtSuppliesContrApply.setUpAlterContractSum(zxCtSuppliesContrApply.getUpAlterContractSum());
           // 清单
           dbZxCtSuppliesContrApply.setPp8(zxCtSuppliesContrApply.getPp8());
           // 评审状态
           dbZxCtSuppliesContrApply.setStatus(zxCtSuppliesContrApply.getStatus());
           // 流程实例ID
           dbZxCtSuppliesContrApply.setInstProcessId(zxCtSuppliesContrApply.getInstProcessId());
           // 开工日期
           dbZxCtSuppliesContrApply.setStartDate(zxCtSuppliesContrApply.getStartDate());
           // 竣工日期
           dbZxCtSuppliesContrApply.setEndDate(zxCtSuppliesContrApply.getEndDate());
           // 甲方名称
           dbZxCtSuppliesContrApply.setFirstName(zxCtSuppliesContrApply.getFirstName());
           // 甲方ID
           dbZxCtSuppliesContrApply.setFirstID(zxCtSuppliesContrApply.getFirstID());
           // 机构编码
           dbZxCtSuppliesContrApply.setCode1(zxCtSuppliesContrApply.getCode1());
           // 核算单位编号
           dbZxCtSuppliesContrApply.setAccountUnitCode(zxCtSuppliesContrApply.getAccountUnitCode());
           // 核算单位ID
           dbZxCtSuppliesContrApply.setAccountUnitId(zxCtSuppliesContrApply.getAccountUnitId());
           // 核算单位
           dbZxCtSuppliesContrApply.setAccountUnitName(zxCtSuppliesContrApply.getAccountUnitName());
           // 核算部门编号
           dbZxCtSuppliesContrApply.setAccountDepCode(zxCtSuppliesContrApply.getAccountDepCode());
           // 核算部门id
           dbZxCtSuppliesContrApply.setAccountDepId(zxCtSuppliesContrApply.getAccountDepId());
           // 核算部门
           dbZxCtSuppliesContrApply.setAccountDepName(zxCtSuppliesContrApply.getAccountDepName());
           // 合同序号
           dbZxCtSuppliesContrApply.setCode8(zxCtSuppliesContrApply.getCode8());
           // 合同税额(万元)
           dbZxCtSuppliesContrApply.setContractCostTax(zxCtSuppliesContrApply.getContractCostTax());
           // 合同签定人
           dbZxCtSuppliesContrApply.setAgent(zxCtSuppliesContrApply.getAgent());
           // 合同内容
           dbZxCtSuppliesContrApply.setContent(zxCtSuppliesContrApply.getContent());
           // 合同名称
           dbZxCtSuppliesContrApply.setContractName(zxCtSuppliesContrApply.getContractName());
           
           dbZxCtSuppliesContrApply.setShopNumber(zxCtSuppliesContrApply.getShopNumber());
           
           dbZxCtSuppliesContrApply.setShopWay(zxCtSuppliesContrApply.getShopWay());
           // 合同类型
           dbZxCtSuppliesContrApply.setContractType(zxCtSuppliesContrApply.getContractType());
           // 合同类别
           dbZxCtSuppliesContrApply.setCode7(zxCtSuppliesContrApply.getCode7());
           // 合同工期
           dbZxCtSuppliesContrApply.setCsTimeLimit(zxCtSuppliesContrApply.getCsTimeLimit());
           // 合同编码
           dbZxCtSuppliesContrApply.setCode(zxCtSuppliesContrApply.getCode());
           // 合同编号
           dbZxCtSuppliesContrApply.setContractNo(zxCtSuppliesContrApply.getContractNo());
           // 含税合同金额(万元)
           dbZxCtSuppliesContrApply.setContractCost(zxCtSuppliesContrApply.getContractCost());
           // 管理机构
           dbZxCtSuppliesContrApply.setOrgName(zxCtSuppliesContrApply.getOrgName());
           // 公文任务ID
           dbZxCtSuppliesContrApply.setWorkitemID(zxCtSuppliesContrApply.getWorkitemID());
           // 发送局指判断ID
           dbZxCtSuppliesContrApply.setSendToZhbID(zxCtSuppliesContrApply.getSendToZhbID());
           // 发送局判断ID
           dbZxCtSuppliesContrApply.setSendToJuID(zxCtSuppliesContrApply.getSendToJuID());
           // 发起人
           dbZxCtSuppliesContrApply.setBeginPer(zxCtSuppliesContrApply.getBeginPer());
           // 承建单位简称
           dbZxCtSuppliesContrApply.setCode2(zxCtSuppliesContrApply.getCode2());
           // 不含税合同金额(万元)
           dbZxCtSuppliesContrApply.setContractCostNoTax(zxCtSuppliesContrApply.getContractCostNoTax());
           // 标段号
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
           // 意见1
           dbZxCtSuppliesContrApply.setOpinionField1(zxCtSuppliesContrApply.getOpinionField1());
           // 意见2
           dbZxCtSuppliesContrApply.setOpinionField2(zxCtSuppliesContrApply.getOpinionField2());
           // 意见3
           dbZxCtSuppliesContrApply.setOpinionField3(zxCtSuppliesContrApply.getOpinionField3());
           // 意见4
           dbZxCtSuppliesContrApply.setOpinionField4(zxCtSuppliesContrApply.getOpinionField4());
           // 意见5
           dbZxCtSuppliesContrApply.setOpinionField5(zxCtSuppliesContrApply.getOpinionField5());
           // 意见6
           dbZxCtSuppliesContrApply.setOpinionField6(zxCtSuppliesContrApply.getOpinionField6());
           // 意见7
           dbZxCtSuppliesContrApply.setOpinionField7(zxCtSuppliesContrApply.getOpinionField7());
           // 意见8
           dbZxCtSuppliesContrApply.setOpinionField8(zxCtSuppliesContrApply.getOpinionField8());
           // 意见9
           dbZxCtSuppliesContrApply.setOpinionField9(zxCtSuppliesContrApply.getOpinionField9());
           // 意见10
           dbZxCtSuppliesContrApply.setOpinionField10(zxCtSuppliesContrApply.getOpinionField10());
           // 流程ID
           dbZxCtSuppliesContrApply.setApih5FlowId(zxCtSuppliesContrApply.getApih5FlowId());
           // work_id
//           dbZxCtSuppliesContrApply.setWorkId(zxCtSuppliesContrApply.getWorkId());
           // 审核状态
           dbZxCtSuppliesContrApply.setApih5FlowStatus(zxCtSuppliesContrApply.getApih5FlowStatus());
           // 审核节点状态
           dbZxCtSuppliesContrApply.setApih5FlowNodeStatus(zxCtSuppliesContrApply.getApih5FlowNodeStatus());
           // 物资类别ID
           dbZxCtSuppliesContrApply.setResCategoryID(zxCtSuppliesContrApply.getResCategoryID());
           // 物资类别
           dbZxCtSuppliesContrApply.setResCategoryName(zxCtSuppliesContrApply.getResCategoryName());
           // 备注
           dbZxCtSuppliesContrApply.setRemarks(zxCtSuppliesContrApply.getRemarks());
           // 排序
           dbZxCtSuppliesContrApply.setSort(zxCtSuppliesContrApply.getSort());
           // 共通
           dbZxCtSuppliesContrApply.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrApplyMapper.updateByPrimaryKey(dbZxCtSuppliesContrApply);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	//如果是否抵扣字段值发生了变化，则需要将其下清单的是否抵扣字段一起变化
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrApplyList);
        }
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
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
			// 申请负责单位
			if (StrUtil.equals("opinionField1", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// 公司办公室
			if (StrUtil.equals("opinionField2", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// 部门领导意见
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
			// 申请负责单位
			if (StrUtil.equals("opinionField1", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField1(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField1()));
			}
			// 公司办公室
			if (StrUtil.equals("opinionField2", zxCtSuppliesContrApply.getOpinionField(), true)) {
				flowDetail.setOpinionField2(zxCtSuppliesContrApply.getOpinionContent(userName, flowDetail.getOpinionField2()));					
			}
			// 部门领导意见
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
        	//审批通过，直接新增同步到合同表中
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
            //新增下面清单
            if(StrUtil.equals(flowDetail.getCode7(), "WZ") || StrUtil.equals(flowDetail.getCode7(), "LC")) {
            	ZxCtSuppliesContrApplyShopList applyShop = new ZxCtSuppliesContrApplyShopList();
            	applyShop.setPp5(flowDetail.getApplyId());            	
                List<ZxCtSuppliesContrApplyShopList> applyShopList = zxCtSuppliesContrApplyShopListMapper.selectByZxCtSuppliesContrApplyShopListList(applyShop);
                applyShopList.parallelStream().forEach((shop)->{
                	ZxCtSuppliesContrShopList zxCtSuppliesContrShopList = new ZxCtSuppliesContrShopList();
                    zxCtSuppliesContrShopList.setZxCtSuppliesContrShopListId(UuidUtil.generate());
                    zxCtSuppliesContrShopList.setContractID(shop.getContractID());
                    // 租期单位
                    zxCtSuppliesContrShopList.setRentUnit(shop.getRentUnit());
                    // 租期
                    zxCtSuppliesContrShopList.setContrTrrm(shop.getContrTrrm());
                    // 物资名称
                    zxCtSuppliesContrShopList.setWorkName(shop.getWorkName());
                    // 物资类别ID
                    zxCtSuppliesContrShopList.setWorkTypeID(shop.getWorkTypeID());
                    // 物资类别
                    zxCtSuppliesContrShopList.setWorkType(shop.getWorkType());
                    // 物资编码ID
                    zxCtSuppliesContrShopList.setWorkID(shop.getWorkID());
                    // 物资编码
                    zxCtSuppliesContrShopList.setWorkNo(shop.getWorkNo());
                    // 税率(%)
                    zxCtSuppliesContrShopList.setTaxRate(shop.getTaxRate());
                    // 数量
                    zxCtSuppliesContrShopList.setQty(shop.getQty());
                    // 是否网价
                    zxCtSuppliesContrShopList.setIsNetPrice(shop.getIsNetPrice());
                    // 实际开始时间
                    zxCtSuppliesContrShopList.setActualStartDate(shop.getActualStartDate());
                    // 实际结束时间
                    zxCtSuppliesContrShopList.setActualEndDate(shop.getActualEndDate());
                    // 评审ID
                    zxCtSuppliesContrShopList.setPp5(shop.getPp5());
                    // 界面展现类型
                    zxCtSuppliesContrShopList.setViewType(shop.getViewType());
                    // 计划开始时间
                    zxCtSuppliesContrShopList.setPlanStartDate(shop.getPlanStartDate());
                    // 计划结束时间
                    zxCtSuppliesContrShopList.setPlanEndDate(shop.getPlanEndDate());
                    // 合同ID
                    zxCtSuppliesContrShopList.setContractID(shop.getContractID());
                    // 含税金额
                    zxCtSuppliesContrShopList.setContractSum(shop.getContractSum());
                    // 含税单价
                    zxCtSuppliesContrShopList.setPrice(shop.getPrice());
                    // 规格型号
                    zxCtSuppliesContrShopList.setSpec(shop.getSpec());
                    // 单位
                    zxCtSuppliesContrShopList.setUnit(shop.getUnit());
                    // 单位
                    zxCtSuppliesContrShopList.setTreenode(shop.getTreenode());
                    // 不含税金额
                    zxCtSuppliesContrShopList.setContractSumNoTax(shop.getContractSumNoTax());
                    // 不含税单价
                    zxCtSuppliesContrShopList.setPriceNoTax(shop.getPriceNoTax());
                    // 变更日期
                    zxCtSuppliesContrShopList.setChangeDate(shop.getChangeDate());
                    // 变更后租期
                    zxCtSuppliesContrShopList.setAlterTrrm(shop.getAlterTrrm());
                    // 变更后数量
                    zxCtSuppliesContrShopList.setChangeQty(shop.getChangeQty());
                    // 变更后含税金额
                    zxCtSuppliesContrShopList.setChangeContractSum(shop.getChangeContractSum());
                    // 变更后含税单价
                    zxCtSuppliesContrShopList.setChangePrice(shop.getChangePrice());
                    // 变更后不含税金额
                    zxCtSuppliesContrShopList.setChangeContractSumNoTax(shop.getChangeContractSumNoTax());
                    // 变更后不含税单价
                    zxCtSuppliesContrShopList.setChangePriceNoTax(shop.getChangePriceNoTax());
                    // 变更后税额
                    zxCtSuppliesContrShopList.setChangeContractSumTax(shop.getChangeContractSumTax());
                    // 税额
                    zxCtSuppliesContrShopList.setContractSumTax(shop.getContractSumTax());                    
                    // 备注
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
                    // 租期单位
                    zxCtSuppliesContrLeaseList.setRentUnit(shop.getRentUnit());
                    // 租期
                    zxCtSuppliesContrLeaseList.setContrTrrm(shop.getContrTrrm());
                    // 物资名称
                    zxCtSuppliesContrLeaseList.setWorkName(shop.getWorkName());
                    // 物资类别ID
                    zxCtSuppliesContrLeaseList.setWorkTypeID(shop.getWorkTypeID());
                    // 物资类别
                    zxCtSuppliesContrLeaseList.setWorkType(shop.getWorkType());
                    // 物资编码ID
                    zxCtSuppliesContrLeaseList.setWorkID(shop.getWorkID());
                    // 物资编码
                    zxCtSuppliesContrLeaseList.setWorkNo(shop.getWorkNo());
                    // 税率(%)
                    zxCtSuppliesContrLeaseList.setTaxRate(shop.getTaxRate());
                    // 数量
                    zxCtSuppliesContrLeaseList.setQty(shop.getQty());
                    // 是否网价
                    zxCtSuppliesContrLeaseList.setIsNetPrice(shop.getIsNetPrice());
                    // 实际开始时间
                    zxCtSuppliesContrLeaseList.setActualStartDate(shop.getActualStartDate());
                    // 实际结束时间
                    zxCtSuppliesContrLeaseList.setActualEndDate(shop.getActualEndDate());
                    // 评审ID
                    zxCtSuppliesContrLeaseList.setPp5(shop.getPp5());
                    // 界面展现类型
                    zxCtSuppliesContrLeaseList.setViewType(shop.getViewType());
                    // 计划开始时间
                    zxCtSuppliesContrLeaseList.setPlanStartDate(shop.getPlanStartDate());
                    // 计划结束时间
                    zxCtSuppliesContrLeaseList.setPlanEndDate(shop.getPlanEndDate());
                    // 合同ID
                    zxCtSuppliesContrLeaseList.setContractID(shop.getContractID());
                    // 含税金额
                    zxCtSuppliesContrLeaseList.setContractSum(shop.getContractSum());
                    // 含税单价
                    zxCtSuppliesContrLeaseList.setPrice(shop.getPrice());
                    // 规格型号
                    zxCtSuppliesContrLeaseList.setSpec(shop.getSpec());
                    // 单位
                    zxCtSuppliesContrLeaseList.setTreenode(shop.getTreenode());
                    // 单位
                    zxCtSuppliesContrLeaseList.setUnit(shop.getUnit());
                    // 不含税金额
                    zxCtSuppliesContrLeaseList.setContractSumNoTax(shop.getContractSumNoTax());
                    // 不含税单价
                    zxCtSuppliesContrLeaseList.setPriceNoTax(shop.getPriceNoTax());
                    // 变更日期
                    zxCtSuppliesContrLeaseList.setChangeDate(shop.getChangeDate());
                    // 变更后租期
                    zxCtSuppliesContrLeaseList.setAlterTrrm(shop.getAlterTrrm());
                    // 变更后数量
                    zxCtSuppliesContrLeaseList.setChangeQty(shop.getChangeQty());
                    // 变更后含税金额
                    zxCtSuppliesContrLeaseList.setChangeContractSum(shop.getChangeContractSum());
                    // 变更后含税单价
                    zxCtSuppliesContrLeaseList.setChangePrice(shop.getChangePrice());
                    // 变更后不含税金额
                    zxCtSuppliesContrLeaseList.setChangeContractSumNoTax(shop.getChangeContractSumNoTax());
                    // 变更后税额
                    zxCtSuppliesContrLeaseList.setChangeContractSumTax(shop.getChangeContractSumTax());
                    // 税额
                    zxCtSuppliesContrLeaseList.setContractSumTax(shop.getContractSumTax());
                    // 变更后不含税单价
                    zxCtSuppliesContrLeaseList.setChangePriceNoTax(shop.getChangePriceNoTax());            
                    zxCtSuppliesContrLeaseList.setCreateUserInfo(userKey, userName);
                    zxCtSuppliesContrLeaseListMapper.insert(zxCtSuppliesContrLeaseList);            	
                });            	
            }
		}
		// 失败
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
        	//未评审:0;正在评审:1;评审通过:2;评审不通过:3;重新评审:4;评审终止:5;评审通过:6
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
//      //删除流程后台数据
        if(jsonArr.size()>0) {
            HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
        }
        // 失败
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
            ZxCtSuppliesContrApply zxCtSuppliesContrApplySelect = new ZxCtSuppliesContrApply();
            zxCtSuppliesContrApplySelect.setWorkId(workId);
            List<ZxCtSuppliesContrApply> zxCtSuppliesContrApplyList = zxCtSuppliesContrApplyMapper.selectByZxCtSuppliesContrApplyList(zxCtSuppliesContrApplySelect);
            BigDecimal contractCost = zxCtSuppliesContrApplyList.get(0).getContractCost();
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

}
