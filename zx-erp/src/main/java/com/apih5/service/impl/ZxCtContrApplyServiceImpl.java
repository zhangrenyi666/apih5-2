package com.apih5.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZxCtContrApplyWorksMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrApplyMapper;
import com.apih5.service.ZxCtContrApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtContrApplyService")
public class ZxCtContrApplyServiceImpl implements ZxCtContrApplyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrApplyMapper zxCtContrApplyMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContrApplyWorksMapper zxCtContrApplyWorksMapper;

    @Override
    public ResponseEntity getZxCtContrApplyListByCondition(ZxCtContrApply zxCtContrApply) {
        if (zxCtContrApply == null) {
            zxCtContrApply = new ZxCtContrApply();
        }
        // 分页查询
        PageHelper.startPage(zxCtContrApply.getPage(), zxCtContrApply.getLimit());
        // 获取数据
        List<ZxCtContrApply> zxCtContrApplyList = zxCtContrApplyMapper.selectByZxCtContrApplyList(zxCtContrApply);

        //查询附件
        for (ZxCtContrApply zxCtContrApply1 : zxCtContrApplyList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCtContrApply1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCtContrApply1.setFileList(zxErpFiles);
        }

        // 得到分页信息
        PageInfo<ZxCtContrApply> p = new PageInfo<>(zxCtContrApplyList);

        return repEntity.okList(zxCtContrApplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrApplyDetail(ZxCtContrApply zxCtContrApply) {
        if (zxCtContrApply == null) {
            zxCtContrApply = new ZxCtContrApply();
        }
        // 获取数据
        ZxCtContrApply dbZxCtContrApply = zxCtContrApplyMapper.selectByPrimaryKey(zxCtContrApply.getId());
        // 数据存在
        if (dbZxCtContrApply != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxCtContrApply.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxCtContrApply.setFileList(zxErpFiles);
            return repEntity.ok(dbZxCtContrApply);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrApply(ZxCtContrApply zxCtContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContrApply.setId(UuidUtil.generate());
        zxCtContrApply.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrApplyMapper.insert(zxCtContrApply);

        //添加附件
        List<ZxErpFile> fileList = zxCtContrApply.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCtContrApply.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrApply);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrApply(ZxCtContrApply zxCtContrApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrApply dbZxCtContrApply = zxCtContrApplyMapper.selectByPrimaryKey(zxCtContrApply.getId());
        if (dbZxCtContrApply != null && StrUtil.isNotEmpty(dbZxCtContrApply.getId())) {
            // 合同编号
            dbZxCtContrApply.setContractNo(zxCtContrApply.getContractNo());
            // 合同名称
            dbZxCtContrApply.setContractName(zxCtContrApply.getContractName());
            // 合同类型
            dbZxCtContrApply.setContractType(zxCtContrApply.getContractType());
            // 含税合同金额(万元)
            dbZxCtContrApply.setContractCost(zxCtContrApply.getContractCost());
            // 甲方ID
            dbZxCtContrApply.setFirstId(zxCtContrApply.getFirstId());
            // 甲方名称
            dbZxCtContrApply.setFirstName(zxCtContrApply.getFirstName());
            // 乙方ID
            dbZxCtContrApply.setSecondId(zxCtContrApply.getSecondId());
            // 乙方名称
            dbZxCtContrApply.setSecondName(zxCtContrApply.getSecondName());
            // 开工日期
            dbZxCtContrApply.setStartDate(zxCtContrApply.getStartDate());
            // 竣工日期
            dbZxCtContrApply.setEndDate(zxCtContrApply.getEndDate());
            // 合同工期
            dbZxCtContrApply.setCsTimeLimit(zxCtContrApply.getCsTimeLimit());
            // 合同签订人
            dbZxCtContrApply.setAgent(zxCtContrApply.getAgent());
            // 合同内容
            dbZxCtContrApply.setContent(zxCtContrApply.getContent());
            // 备注
            dbZxCtContrApply.setRemark(zxCtContrApply.getRemark());
            // 发起人
            dbZxCtContrApply.setBeginPer(zxCtContrApply.getBeginPer());
            // 评审状态
            dbZxCtContrApply.setStatus(zxCtContrApply.getStatus());
            // 补充协议
            dbZxCtContrApply.setSupplyAgreement(zxCtContrApply.getSupplyAgreement());
            // pp4
            dbZxCtContrApply.setPp4(zxCtContrApply.getPp4());
            // pp5
            dbZxCtContrApply.setPp5(zxCtContrApply.getPp5());
            // 合同名称Id
            dbZxCtContrApply.setContractNameId(zxCtContrApply.getContractNameId());
            // pp10
            dbZxCtContrApply.setPp10(zxCtContrApply.getPp10());
            // 流程实例ID
            dbZxCtContrApply.setInstProcessId(zxCtContrApply.getInstProcessId());
            // 公文任务ID
            dbZxCtContrApply.setWorkitemId(zxCtContrApply.getWorkitemId());
            // 合同编码
            dbZxCtContrApply.setContractCode(zxCtContrApply.getContractCode());
            // 机构编码
            dbZxCtContrApply.setOrganCode(zxCtContrApply.getOrganCode());
            // 承建单位简称
            dbZxCtContrApply.setContractorAbbreviation(zxCtContrApply.getContractorAbbreviation());
            // 中标单位简称
            dbZxCtContrApply.setBidWinnerAbbreviation(zxCtContrApply.getBidWinnerAbbreviation());
            // 项目所在省份简称
            dbZxCtContrApply.setProjectProvinceAbbreviation(zxCtContrApply.getProjectProvinceAbbreviation());
            // 项目简称
            dbZxCtContrApply.setProjectAbbreviation(zxCtContrApply.getProjectAbbreviation());
            // 标段号
            dbZxCtContrApply.setLotNo(zxCtContrApply.getLotNo());
            // 合同类别
            dbZxCtContrApply.setContractCategory(zxCtContrApply.getContractCategory());
            // 合同序号
            dbZxCtContrApply.setContractNumber(zxCtContrApply.getContractNumber());
            // 业主合同编码
            dbZxCtContrApply.setOwnerContractCode(zxCtContrApply.getOwnerContractCode());
            // 同一公司
            dbZxCtContrApply.setOneFirm(zxCtContrApply.getOneFirm());
            // 流程开始时间
            dbZxCtContrApply.setFlowBeginDate(zxCtContrApply.getFlowBeginDate());
            // 流程结束时间
            dbZxCtContrApply.setFlowEndDate(zxCtContrApply.getFlowEndDate());
            // alterContractSum
            dbZxCtContrApply.setAlterContractSum(zxCtContrApply.getAlterContractSum());
            // 是否局审批
            dbZxCtContrApply.setIsFlag(zxCtContrApply.getIsFlag());
            // 是否报告
            dbZxCtContrApply.setIsReport(zxCtContrApply.getIsReport());
            // appInsHistId
            dbZxCtContrApply.setAppInsHistId(zxCtContrApply.getAppInsHistId());
            // 发送局判断ID
            dbZxCtContrApply.setSendToJuId(zxCtContrApply.getSendToJuId());
            // materialSource
            dbZxCtContrApply.setMaterialSource(zxCtContrApply.getMaterialSource());
            // 上期末变更后金额
            dbZxCtContrApply.setUpAlterContractSum(zxCtContrApply.getUpAlterContractSum());
            // 现场负责人
            dbZxCtContrApply.setLegalPerson(zxCtContrApply.getLegalPerson());
            // 委托代理人
            dbZxCtContrApply.setAgentPerson(zxCtContrApply.getAgentPerson());
            // 法定代表人
            dbZxCtContrApply.setChargePerson(zxCtContrApply.getChargePerson());
            // 是否局指审批
            dbZxCtContrApply.setIsFlagZhb(zxCtContrApply.getIsFlagZhb());
            // isReportZhb
            dbZxCtContrApply.setIsReportZhb(zxCtContrApply.getIsReportZhb());
            // 发送局指判断ID
            dbZxCtContrApply.setSendToZhbId(zxCtContrApply.getSendToZhbId());
            // appInsHistIDZhb
            dbZxCtContrApply.setAppInsHistIDZhb(zxCtContrApply.getAppInsHistIDZhb());
            // 不含税合同金额(万元)
            dbZxCtContrApply.setContractCostNoTax(zxCtContrApply.getContractCostNoTax());
            // 税率(%)
            dbZxCtContrApply.setTaxRate(zxCtContrApply.getTaxRate());
            // 上期末变更后金额不含税
            dbZxCtContrApply.setAlterContractSumNoTax(zxCtContrApply.getAlterContractSumNoTax());
            // 上期末变更后税额
            dbZxCtContrApply.setAlterContractSumTax(zxCtContrApply.getAlterContractSumTax());
            // pp4NoTax
            dbZxCtContrApply.setPp4NoTax(zxCtContrApply.getPp4NoTax());
            // pp4Tax
            dbZxCtContrApply.setPp4Tax(zxCtContrApply.getPp4Tax());
            // 是否抵扣
            dbZxCtContrApply.setIsDeduct(zxCtContrApply.getIsDeduct());
            // fromContractID
            dbZxCtContrApply.setFromContractId(zxCtContrApply.getFromContractId());
            // fromContractNo
            dbZxCtContrApply.setFromContractNo(zxCtContrApply.getFromContractNo());
            // fromContractName
            dbZxCtContrApply.setFromContractName(zxCtContrApply.getFromContractName());
            // 合同税额(万元)
            dbZxCtContrApply.setContractCostTax(zxCtContrApply.getContractCostTax());
            // 授权委托人姓名
            dbZxCtContrApply.setWtrName(zxCtContrApply.getWtrName());
            // 授权委托人身份证号
            dbZxCtContrApply.setWtrPhone(zxCtContrApply.getWtrPhone());
            // 推荐人姓名
            dbZxCtContrApply.setReferenceName(zxCtContrApply.getReferenceName());
            // 推荐人职务
            dbZxCtContrApply.setReferencePost(zxCtContrApply.getReferencePost());
            // 推荐人电话
            dbZxCtContrApply.setReferencePhone(zxCtContrApply.getReferencePhone());
            // 上期末变更后金额不含税
            dbZxCtContrApply.setUpAlterContractSumNoTax(zxCtContrApply.getUpAlterContractSumNoTax());
            // comID
            dbZxCtContrApply.setComId(zxCtContrApply.getComId());
            // 上期末变更后税额
            dbZxCtContrApply.setUpAlterContractSumTax(zxCtContrApply.getUpAlterContractSumTax());
            // 财务系统ID
            dbZxCtContrApply.setFiId(zxCtContrApply.getFiId());
            // actDepartment
            dbZxCtContrApply.setActDepartment(zxCtContrApply.getActDepartment());
            // actDepartmentBM
            dbZxCtContrApply.setActDepartmentBm(zxCtContrApply.getActDepartmentBm());
            // actDepartmentID
            dbZxCtContrApply.setActDepartmentId(zxCtContrApply.getActDepartmentId());
            // biddersId
            dbZxCtContrApply.setBiddersId(zxCtContrApply.getBiddersId());
            // biddersCode
            dbZxCtContrApply.setBiddersCode(zxCtContrApply.getBiddersCode());
            // biddersName
            dbZxCtContrApply.setBiddersName(zxCtContrApply.getBiddersName());
            // 核算单位ID
            dbZxCtContrApply.setAccountUnitId(zxCtContrApply.getAccountUnitId());
            // 核算单位编号
            dbZxCtContrApply.setAccountUnitCode(zxCtContrApply.getAccountUnitCode());
            // 核算单位
            dbZxCtContrApply.setAccountUnitName(zxCtContrApply.getAccountUnitName());
            // accountProjId
            dbZxCtContrApply.setAccountProjId(zxCtContrApply.getAccountProjId());
            // accountProjCode
            dbZxCtContrApply.setAccountProjCode(zxCtContrApply.getAccountProjCode());
            // accountProjName
            dbZxCtContrApply.setAccountProjName(zxCtContrApply.getAccountProjName());
            // projSite
            dbZxCtContrApply.setProjSite(zxCtContrApply.getProjSite());
            // busiSegments
            dbZxCtContrApply.setBusiSegments(zxCtContrApply.getBusiSegments());
            // projFundsSource
            dbZxCtContrApply.setProjFundsSource(zxCtContrApply.getProjFundsSource());
            // division
            dbZxCtContrApply.setDivision(zxCtContrApply.getDivision());
            // taxFilingCode
            dbZxCtContrApply.setTaxFilingCode(zxCtContrApply.getTaxFilingCode());
            // taxCountWay
            dbZxCtContrApply.setTaxCountWay(zxCtContrApply.getTaxCountWay());
            // taxAdvanceRate
            dbZxCtContrApply.setTaxAdvanceRate(zxCtContrApply.getTaxAdvanceRate());
            // taxUseWay
            dbZxCtContrApply.setTaxUseWay(zxCtContrApply.getTaxUseWay());
            // prepaidLand
            dbZxCtContrApply.setPrepaidLand(zxCtContrApply.getPrepaidLand());
            // nationalTax
            dbZxCtContrApply.setNationalTax(zxCtContrApply.getNationalTax());
            // nationalTaxTel
            dbZxCtContrApply.setNationalTaxTel(zxCtContrApply.getNationalTaxTel());
            // nationalTaxAdds
            dbZxCtContrApply.setNationalTaxAdds(zxCtContrApply.getNationalTaxAdds());
            // projDepAdds
            dbZxCtContrApply.setProjDepAdds(zxCtContrApply.getProjDepAdds());
            // projDepZipCode
            dbZxCtContrApply.setProjDepZipCode(zxCtContrApply.getProjDepZipCode());
            // projDepTel
            dbZxCtContrApply.setProjDepTel(zxCtContrApply.getProjDepTel());
            // projDepFax
            dbZxCtContrApply.setProjDepFax(zxCtContrApply.getProjDepFax());
            // projStage
            dbZxCtContrApply.setProjStage(zxCtContrApply.getProjStage());
            // pmFixedLine
            dbZxCtContrApply.setPmFixedLine(zxCtContrApply.getPmFixedLine());
            // pmMail
            dbZxCtContrApply.setPmMail(zxCtContrApply.getPmMail());
            // fiCharge
            dbZxCtContrApply.setFiCharge(zxCtContrApply.getFiCharge());
            // fiTel
            dbZxCtContrApply.setFiTel(zxCtContrApply.getFiTel());
            // fiFixedLine
            dbZxCtContrApply.setFiFixedLine(zxCtContrApply.getFiFixedLine());
            // fiMail
            dbZxCtContrApply.setFiMail(zxCtContrApply.getFiMail());
            // ctrCharge
            dbZxCtContrApply.setCtrCharge(zxCtContrApply.getCtrCharge());
            // ctrTel
            dbZxCtContrApply.setCtrTel(zxCtContrApply.getCtrTel());
            // ctrFixedLine
            dbZxCtContrApply.setCtrFixedLine(zxCtContrApply.getCtrFixedLine());
            // ctrMail
            dbZxCtContrApply.setCtrMail(zxCtContrApply.getCtrMail());
            // dcLeader
            dbZxCtContrApply.setDcLeader(zxCtContrApply.getDcLeader());
            // dcTel
            dbZxCtContrApply.setDcTel(zxCtContrApply.getDcTel());
            // dcFixedLine
            dbZxCtContrApply.setDcFixedLine(zxCtContrApply.getDcFixedLine());
            // dcMail
            dbZxCtContrApply.setDcMail(zxCtContrApply.getDcMail());
            // 创建时间
            dbZxCtContrApply.setCreateTime(zxCtContrApply.getCreateTime());
            // 复核日期
            dbZxCtContrApply.setDoubleCheckDate(zxCtContrApply.getDoubleCheckDate());
            // 录入日期
            dbZxCtContrApply.setWriteDate(zxCtContrApply.getWriteDate());
            // 核算部门id
            dbZxCtContrApply.setAccountDepId(zxCtContrApply.getAccountDepId());
            // 核算部门编号
            dbZxCtContrApply.setAccountDepCode(zxCtContrApply.getAccountDepCode());
            // 核算部门
            dbZxCtContrApply.setAccountDepName(zxCtContrApply.getAccountDepName());
            // 往来单位编号
            dbZxCtContrApply.setSecondIDCode(zxCtContrApply.getSecondIDCode());
            // 录入人
            dbZxCtContrApply.setWriter(zxCtContrApply.getWriter());
            // 系统编号
            dbZxCtContrApply.setSystemNo(zxCtContrApply.getSystemNo());
            // 币种编号
            dbZxCtContrApply.setCurrencyCode(zxCtContrApply.getCurrencyCode());
            // 合同性质
            dbZxCtContrApply.setCtrNature(zxCtContrApply.getCtrNature());
            // 合同更新状态
            dbZxCtContrApply.setCtrUpdateState(zxCtContrApply.getCtrUpdateState());
            // 财务合同状态
            dbZxCtContrApply.setFiCtrState(zxCtContrApply.getFiCtrState());
            // 收支方向
            dbZxCtContrApply.setRevenueDir(zxCtContrApply.getRevenueDir());
            // 发票类型
            dbZxCtContrApply.setInvoiceType(zxCtContrApply.getInvoiceType());
            // 制式合同
            dbZxCtContrApply.setStaCtr(zxCtContrApply.getStaCtr());
            // 重点合同
            dbZxCtContrApply.setKeyCtr(zxCtContrApply.getKeyCtr());
            // 管理机构ID
            dbZxCtContrApply.setOrgId(zxCtContrApply.getOrgId());
            // notDisplay
            dbZxCtContrApply.setNotDisplay(zxCtContrApply.getNotDisplay());
            // 复核人
            dbZxCtContrApply.setDoubleCheckPerson(zxCtContrApply.getDoubleCheckPerson());
            // 往来单位编号
            dbZxCtContrApply.setSecondIDCodeBh(zxCtContrApply.getSecondIDCodeBh());
            // auditWorkitemID
            dbZxCtContrApply.setAuditWorkitemId(zxCtContrApply.getAuditWorkitemId());
            // auditSys
            dbZxCtContrApply.setAuditSys(zxCtContrApply.getAuditSys());
            // 往来单位名称
            dbZxCtContrApply.setSecondIDCodeName(zxCtContrApply.getSecondIDCodeName());
            // 管理机构
            dbZxCtContrApply.setOrgName(zxCtContrApply.getOrgName());
            // 协作单位类型
            dbZxCtContrApply.setSecondOrgType(zxCtContrApply.getSecondOrgType());
            // firstOAorgID
            dbZxCtContrApply.setFirstOAorgId(zxCtContrApply.getFirstOAorgId());
            // 是否通过云电商进行招标
            dbZxCtContrApply.setIsBiddedOnCloud(zxCtContrApply.getIsBiddedOnCloud());
            // 招标方式
            dbZxCtContrApply.setBidType(zxCtContrApply.getBidType());
            // 云电商招标方案编号
            dbZxCtContrApply.setCloudBidNo(zxCtContrApply.getCloudBidNo());
            // 组织招标主体
            dbZxCtContrApply.setBidOrgType(zxCtContrApply.getBidOrgType());
            // 各单位主管部门是否参与
            dbZxCtContrApply.setIsAllDepartJoin(zxCtContrApply.getIsAllDepartJoin());
            // 参与方式
            dbZxCtContrApply.setJoinType(zxCtContrApply.getJoinType());
            // 各单位主管部门是否参与评标
            dbZxCtContrApply.setIsDepartJoinBid(zxCtContrApply.getIsDepartJoinBid());
            // 合同所属事业部
            dbZxCtContrApply.setContractDepart(zxCtContrApply.getContractDepart());
            // 包件编号
            dbZxCtContrApply.setPackageNo(zxCtContrApply.getPackageNo());
            // 包件ID
            dbZxCtContrApply.setCloudEcoId(zxCtContrApply.getCloudEcoId());
            // 投资合同编号
            dbZxCtContrApply.setInvestContractNo(zxCtContrApply.getInvestContractNo());
            // resCategoryID
            dbZxCtContrApply.setResCategoryId(zxCtContrApply.getResCategoryId());
            // resCategoryName
            dbZxCtContrApply.setResCategoryName(zxCtContrApply.getResCategoryName());
            // 排序
            dbZxCtContrApply.setSort(zxCtContrApply.getSort());
            // 共通
            dbZxCtContrApply.setModifyUserInfo(userKey, realName);
            flag = zxCtContrApplyMapper.updateByPrimaryKey(dbZxCtContrApply);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxCtContrApply.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //明细list
            List<ZxErpFile> fileList = zxCtContrApply.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtContrApply.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxCtContrApply);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrApply(List<ZxCtContrApply> zxCtContrApplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrApplyList != null && zxCtContrApplyList.size() > 0) {
            for (ZxCtContrApply zxCtContrApply : zxCtContrApplyList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCtContrApply.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }

            ZxCtContrApply zxCtContrApply = new ZxCtContrApply();
            zxCtContrApply.setModifyUserInfo(userKey, realName);
            flag = zxCtContrApplyMapper.batchDeleteUpdateZxCtContrApply(zxCtContrApplyList, zxCtContrApply);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtContrApplyList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    // 评审状态为“正在评审、评审通过、评审不通过”时，查看工程施工类补充协议清单明细
    @Override
    public ResponseEntity getZxCtContrApplyWorksDetailList(ZxCtContrApply zxCtContrApply) {
        if (zxCtContrApply == null) {
            zxCtContrApply = new ZxCtContrApply();
        }
        // 获取数据
        ZxCtContrApply dbZxCtContrApply = zxCtContrApplyMapper.selectByPrimaryKey(zxCtContrApply.getId());
        // 数据存在
        if (dbZxCtContrApply != null) {
            //工程施工类合同清单明细
            ZxCtContrApplyWorks zxCtContrApplyWorks = new ZxCtContrApplyWorks();
            zxCtContrApplyWorks.setId("work" + dbZxCtContrApply.getId());
            zxCtContrApplyWorks.setParentId("work" + dbZxCtContrApply.getId());
            List<List<ZxCtContrApplyWorks>> zxCtContrApplyWorksList = new ArrayList<List<ZxCtContrApplyWorks>>();
            List<ZxCtContrApplyWorks> zxCtContrApplyWorksParentList = zxCtContrApplyWorksMapper.selectByZxCtContrApplyWorksList(zxCtContrApplyWorks);
            //zxCtContrApplyWorksList.add(zxCtContrApplyWorksList);
            if (zxCtContrApplyWorksParentList != null && zxCtContrApplyWorksParentList.size() > 0) {
                for (ZxCtContrApplyWorks zxCtContrApplyWorks1 : zxCtContrApplyWorksParentList) {
                    zxCtContrApplyWorks1.setParentId(zxCtContrApplyWorks1.getId());
                    //List<ZxCtContrApplyWorks> zxCtContrApplyWorksList = zxCtContrApplyWorksMapper.selectByZxCtContrApplyWorksList(zxCtContrApplyWorks1);
                    //zxCtContrApplyWorksList.add(zxCtContrApplyWorksList);
                }
            }

            //dbZxCtContrApply.setZxCtContrApplyWorksList(zxCtContrApplyWorksParentList);
            return repEntity.ok(dbZxCtContrApply);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    /**
     * 获取某个父节点下面的所有子节点
     * @param workList
     * @param pid
     * @return
     */
    public  List<ZxCtContrApplyWorks> treeWorkList(List<ZxCtContrApplyWorks> workList, String pid) {
        List<ZxCtContrApplyWorks> childWorkList = new ArrayList<>();
        for (ZxCtContrApplyWorks zxCtContrApplyWorks : workList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (pid.equals(zxCtContrApplyWorks.getParentId())) {
                //递归遍历下一级
                this.treeWorkList(workList,zxCtContrApplyWorks.getId());
                childWorkList.add(zxCtContrApplyWorks);
            }
        }

//        workList.stream()
//                //过滤出父id等于参数的id
//                .filter(work -> StringUtils.isNotBlank(work.getParentId()) && work.getParentId().equals(pid))
//                .forEach(work -> {
//                    //递归遍历下一级
//                    treeWorkList(workList, work.getId());
//                    //添加
//                    childWorkList.add(work);
//                });
        return childWorkList;
    }

    // 工程施工类合同评审清单明细导入
    @Override
    public ResponseEntity importZxCtContrApplyWorks(ZxCtContrApplyWorks zxCtContrApplyWorks) {
        if (zxCtContrApplyWorks == null) {
            zxCtContrApplyWorks = new ZxCtContrApplyWorks();
        }

        // 上传文件为空
        if (zxCtContrApplyWorks.getImportFileList() == null || zxCtContrApplyWorks.getImportFileList().size() == 0) {
            return repEntity.layerMessage("no", "没有导入文件!");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        List<ZxCtContrApplyWorks> importExcelList = null;
        try {
            String filePath = zxCtContrApplyWorks.getImportFileList().get(0).getUrl();
            InputStream inputStream = ResourceUtil.getStream(filePath);
            //InputStream inputStream = ResourceUtil.getStream("C:\\Users\\sjr\\Desktop\\worksImport1Tax.xlsx");
            String[] excelHead = {"清单编号", "清单名称", "单位", "数量", "含税合同单价", "税率(%)"};
            String[] excelHeadAlias = {"workNo", "workName", "unit", "qty", "price", "taxRate"};
            ExcelReader reader = ExcelUtil.getReader(inputStream);
            List<Object> header = reader.readRow(0);
            if ("不含税合同单价".equals(header.get(4))) {
                excelHead[4] = excelHead[4].replaceAll("含税合同单价", "不含税合同单价");
                excelHeadAlias[4] = excelHeadAlias[4].replaceAll("price", "priceNoTax");
            }
            if ("税率".equals(header.get(5))) {
                excelHead[5] = excelHead[5].replaceAll("\\(\\%\\)", "");
            }
            //替换表头关键字
            if (StrUtil.hasEmpty(excelHead) || StrUtil.hasEmpty(excelHeadAlias) || excelHead.length != excelHeadAlias.length) {
                return null;
            } else {
                for (int i = 0; i < header.size(); i++) {
                    if (excelHead[i].equals(header.get(i))) {
                        reader.addHeaderAlias(excelHead[i], excelHeadAlias[i]);
                    } else {
                        return repEntity.layerMessage("no", "第" + i + "列标题错误！");
                    }
                }
            }
            importExcelList = reader.readAll(ZxCtContrApplyWorks.class);
            if (importExcelList == null || importExcelList.size() == 0) {
                return repEntity.layerMessage("no", "导入失败，无导入数据!");
            }
            if (importExcelList != null && importExcelList.size() > 0) {
                for (int i = 0; i < importExcelList.size(); i++) {
                    // 清单编号
                    if (StrUtil.isEmpty(importExcelList.get(i).getWorkNo())) {
                        continue;
                    }
                    zxCtContrApplyWorks.setWorkNo(importExcelList.get(i).getWorkNo());
                    // 清单名称
                    zxCtContrApplyWorks.setWorkName(importExcelList.get(i).getWorkName());
                    // 单位
                    zxCtContrApplyWorks.setUnit(importExcelList.get(i).getUnit());
                    // 数量
                    String qty = importExcelList.get(i).getQty().toString();
                    if (StrUtil.isNotEmpty(qty)) {
                        try {
                            BigDecimal qty1 = new BigDecimal(qty);
                            zxCtContrApplyWorks.setQty(qty1);
                        } catch (Exception e) {
                            return repEntity.layerMessage("no", "该Excel第" + i + "行第4列数据格式不正确(应为数值类型),不能正常进行导入！请调整后重新导入");
                        }
                    }
                    // 单价
                    String price = importExcelList.get(i).getPrice().toString();
                    // 税率
                    String taxRate = importExcelList.get(i).getTaxRate();
                    if (StrUtil.isNotEmpty(price)) {
                        try {
                            BigDecimal price1 = new BigDecimal(price);
                            zxCtContrApplyWorks.setPrice(price1);

                            if (StrUtil.isEmpty(taxRate) || StrUtil.equals("空", taxRate)) {
                                zxCtContrApplyWorks.setPriceNoTax(new BigDecimal(0));
                            } else {
                                zxCtContrApplyWorks.setTaxRate(taxRate);
                                zxCtContrApplyWorks.setPriceNoTax(CalcUtils.calcDivide(new BigDecimal(price), CalcUtils.calcMultiply(CalcUtils.calcAdd(new BigDecimal(1), new BigDecimal(taxRate)), new BigDecimal(0.01)), 2));
                            }
                        } catch (Exception e) {
                            return repEntity.layerMessage("no", "该Excel第" + i + "行第5列数据格式不正确(应为数值类型),不能正常进行导入！请调整后重新导入");
                        }
                    } else {
                        // 不含税合同单价
                        zxCtContrApplyWorks.setPriceNoTax(importExcelList.get(i).getPriceNoTax());
                    }
                    zxCtContrApplyWorks.setId(UuidUtil.generate());
                    zxCtContrApplyWorks.setCreateUserInfo(userKey, realName);
                    zxCtContrApplyWorksMapper.insert(zxCtContrApplyWorks);
                }
                LoggerUtils.printDebugLogger(logger, "导入数据一共【" + importExcelList.size() + "】行");
                LoggerUtils.printDebugLogger(logger, "导入的数据：" + importExcelList);
            }
        } catch (Exception e) {
            LoggerUtils.printDebugLogger(logger, e.getMessage());
            repEntity.layerMessage("no", "导入失败");
        }
        return repEntity.ok("成功导入" + importExcelList.size() + "条数据！");

    }

    @Override
    public ResponseEntity getZxCtContrApplyWorksTree(ZxCtContrApplyWorks zxCtContrApplyWorks) {
        if(zxCtContrApplyWorks == null) {
            zxCtContrApplyWorks = new ZxCtContrApplyWorks();
        }
        List<ZxCtContrApplyWorks> zxCtContrApplyWorksList = zxCtContrApplyWorksMapper.selectByZxCtContrApplyWorksList(zxCtContrApplyWorks);
        JSONArray jsonArray = listToTree(new JSONArray(zxCtContrApplyWorksList), "id", "parentId", "workName");
        return repEntity.ok(jsonArray);
    }

    @Override
    public ResponseEntity getZxCtContrApplyWorksTreeData(ZxCtContrApplyWorks zxCtContrApplyWorks) {
        if(zxCtContrApplyWorks == null) {
            zxCtContrApplyWorks = new ZxCtContrApplyWorks();
        }
        List<ZxCtContrApplyWorks> zxCtContrApplyWorksList = zxCtContrApplyWorksMapper.selectByZxCtContrApplyWorksList(zxCtContrApplyWorks);
        return repEntity.ok(zxCtContrApplyWorksList);
    }

    /**
     * list转tree
     *
     * @param arr
     * @param id
     * @param parentId
     * @param workName
     * @return tree
     */
    private static JSONArray listToTree(JSONArray arr, String id, String parentId, String workName) {
        String child = "childrenList";
        JSONArray r = new JSONArray();
        JSONArray newArr = new JSONArray();
        JSONObject hash = new JSONObject();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            JSONObject newJSONObject = new JSONObject();
            newJSONObject.set(id, json.get(id));
            newJSONObject.set(parentId, json.get(parentId));
            newJSONObject.set(workName, json.get(workName));
            newJSONObject.set("treeNode", json.get("treeNode"));
            newJSONObject.set("orgId", json.get("orgId"));
            newJSONObject.set("contrApplyId", json.get("contrApplyId"));
            newJSONObject.set("workType", json.get("workType"));
            newJSONObject.set("workNo", json.get("workNo"));
            newJSONObject.set("unit", json.get("unit"));
            newJSONObject.set("contractPrice", json.get("contractPrice"));
            newJSONObject.set("contractQty", json.get("contractQty"));
            newJSONObject.set("contractAmt", json.get("contractAmt"));
            newJSONObject.set("quantity", json.get("quantity"));
            newJSONObject.set("price", json.get("price"));
            newJSONObject.set("isLeaf", json.get("isLeaf"));
            newJSONObject.set("exsitStatus", json.get("exsitStatus"));
            newJSONObject.set("isAssignable", json.get("isAssignable"));
            newJSONObject.set("updateFlag", json.get("updateFlag"));
            newJSONObject.set("combProp", json.get("combProp"));
            newJSONObject.set("pp1", json.get("pp1"));
            newJSONObject.set("pp2", json.get("pp2"));
            newJSONObject.set("pp3", json.get("pp3"));
            newJSONObject.set("pp4", json.get("pp4"));
            newJSONObject.set("contractReview", json.get("contractReview"));
            newJSONObject.set("pp6", json.get("pp6"));
            newJSONObject.set("pp7", json.get("pp7"));
            newJSONObject.set("pp8", json.get("pp8"));
            newJSONObject.set("pp9", json.get("pp9"));
            newJSONObject.set("leaseTerm", json.get("leaseTerm"));
            newJSONObject.set("checkQty", json.get("checkQty"));
            newJSONObject.set("expectChangeQty", json.get("expectChangeQty"));
            newJSONObject.set("expectChangePrice", json.get("expectChangePrice"));
            newJSONObject.set("inputWorkType", json.get("inputWorkType"));
            newJSONObject.set("isReCountAmt", json.get("isReCountAmt"));
            newJSONObject.set("qty", json.get("qty"));
            newJSONObject.set("isGroup", json.get("isGroup"));
            newJSONObject.set("requestEdit", json.get("requestEdit"));
            newJSONObject.set("contractPriceNoTax", json.get("contractPriceNoTax"));
            newJSONObject.set("priceNoTax", json.get("priceNoTax"));
            newJSONObject.set("taxRate", json.get("taxRate"));
            newJSONObject.set("amtNoTax", json.get("amtNoTax"));
            newJSONObject.set("ruleId", json.get("ruleId"));
            newJSONObject.set("ruleName", json.get("ruleName"));
            hash.set(json.getStr(id), newJSONObject);
            newArr.add(newJSONObject);
        }

        for (int j = 0; j < newArr.size(); j++) {
            JSONObject aVal = newArr.getJSONObject(Integer.valueOf(j));
            JSONObject hashVP = hash.getJSONObject(aVal.getStr(parentId));
            if (hashVP != null) {
                if (hashVP.get(child) != null) {
                    JSONArray ch = hashVP.getJSONArray(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            } else
                r.add(aVal);
        }
        return r;
    }

    // 获取施工类合同评审新增时甲方名称
    @Override
    public ResponseEntity getZxCtContrApplyFirstName(ZxCtContrApply zxCtContrApply) {
        // 分页查询
        PageHelper.startPage(zxCtContrApply.getPage(), zxCtContrApply.getLimit());
        List<ZxCtContrApply> zxCtContrApplyList = new ArrayList<>();
        // 获取数据
//        List<ZxCtContrApply> zxCtContrApplyList = zxCtContrApplyMapper.selectByZxCtContrApplyFirstNameList(zxCtContrApply);
//        if (zxCtContrApplyList != null && zxCtContrApplyList.size() > 0) {
//            for (ZxCtContrApply zxCtContrApply1 : zxCtContrApplyList) {
//                int expenseNo = sequenceService.getSequenceNextval(zxCtContrApply1.getContractCode());
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.set("limitNo",zxCtContrApply1.getContractCode()+"-"+"FB"+"-"+String.format("%03d",expenseNo));
//                zxCtContrApply1.setContractCode(jsonObject.toString());
//            }
//
//        }
        // 得到分页信息
        PageInfo<ZxCtContrApply> p = new PageInfo<>(zxCtContrApplyList);
        return repEntity.okList(zxCtContrApplyList, p.getTotal());
    }
    public void level(List<ZxCtContrApplyWorks> importExcelList,String userKey,String realName) {
        String pWorkNo = importExcelList.get(0).getWorkNo();
        importExcelList.get(0).setParentId("-1");
        String genid = "work" + UuidUtil.generate();
        importExcelList.get(0).setId(genid);
        importExcelList.get(0).setCreateUserInfo(userKey,realName);
        zxCtContrApplyWorksMapper.insert(importExcelList.get(0));

        List<ZxCtContrApplyWorks> list = new ArrayList<>();
        for (int i = 1; i < importExcelList.size()-1; i++) {

            String workNo  = importExcelList.get(i).getWorkNo();
            if (pWorkNo.length() != workNo.length()) {
                list.add(importExcelList.get(i));
            } else if (pWorkNo.length() == workNo.length()) {
                list.get(0).setParentId(genid);
                list.get(0).setId(UuidUtil.generate());
                list.get(0).setCreateUserInfo(userKey,realName);
                zxCtContrApplyWorksMapper.insert(list.get(0));
                this.level2(list,userKey,realName);
                list.clear();

                genid = "work" + UuidUtil.generate();
                importExcelList.get(i).setId(genid);
                importExcelList.get(i).setParentId("-1");
                importExcelList.get(i).setCreateUserInfo(userKey,realName);
                zxCtContrApplyWorksMapper.insert(importExcelList.get(i));
                //workList = importExcelList.subList(index, importExcelList.size() - index);
                //break;
            } else if (importExcelList.get(i) == null){
                list.get(0).setParentId(genid);
                list.get(0).setId(UuidUtil.generate());
                list.get(0).setCreateUserInfo(userKey,realName);
                zxCtContrApplyWorksMapper.insert(list.get(0));
                this.level2(list,userKey,realName);
            }
        }
    }

    public void level2(List<ZxCtContrApplyWorks> list,String userKey,String realName) {
        String pWorkNo = list.get(0).getWorkNo();
        for (int j = 1; j < list.size(); j++) {
            String workNo  = list.get(j).getWorkNo();
            if(workNo.length() > pWorkNo.length()) {
                if(workNo.length() == list.get(j-1).getWorkNo().length()){
                    list.get(j).setParentId(list.get(j-1).getParentId());
                } else {
                    list.get(j).setParentId(list.get(j-1).getId());
                }
                list.get(j).setId(UuidUtil.generate());
            }
            if(workNo.length() == pWorkNo.length()){
                list.get(j).setParentId(list.get(0).getParentId());
                list.get(j).setId(UuidUtil.generate());
            }
            list.get(j).setCreateUserInfo(userKey,realName);
            zxCtContrApplyWorksMapper.insert(list.get(j));
        }
    }
}
