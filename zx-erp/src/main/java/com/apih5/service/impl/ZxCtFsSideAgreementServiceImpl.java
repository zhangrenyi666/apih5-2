package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZxCtFsSideAgreementInventoryService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxCtFsSideAgreementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxCtFsSideAgreementService")
public class ZxCtFsSideAgreementServiceImpl implements ZxCtFsSideAgreementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtFsSideAgreementMapper zxCtFsSideAgreementMapper;

    @Autowired(required = true)
    private ZxCtFsSideAgreementInventoryService zxCtFsSideAgreementInventoryService;

    @Autowired(required = true)
    private ZxCtFsSideAgreementInventoryMapper zxCtFsSideAgreementInventoryMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtFsContractReviewDetailMapper zxCtFsContractReviewDetailMapper;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Override
    public ResponseEntity getZxCtFsSideAgreementListByCondition(ZxCtFsSideAgreement zxCtFsSideAgreement) {
        if (zxCtFsSideAgreement == null) {
            zxCtFsSideAgreement = new ZxCtFsSideAgreement();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtFsSideAgreement.setComID("");
            zxCtFsSideAgreement.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCtFsSideAgreement.setComID(zxCtFsSideAgreement.getOrgID());
            zxCtFsSideAgreement.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCtFsSideAgreement.setOrgID(zxCtFsSideAgreement.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxCtFsSideAgreement.getPage(), zxCtFsSideAgreement.getLimit());
        // 获取数据
        List<ZxCtFsSideAgreement> zxCtFsSideAgreementList = zxCtFsSideAgreementMapper.selectByZxCtFsSideAgreementList(zxCtFsSideAgreement);

        for (ZxCtFsSideAgreement zxCtFsSideAgreement1 : zxCtFsSideAgreementList) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxCtFsSideAgreement1.getZxCtFsSideAgreementId());
            file.setOtherType("1");
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxCtFsSideAgreement1.setZxErpFileList(zxErpFileList);
            file.setOtherType("2");
            List<ZxErpFile> zhengWenFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxCtFsSideAgreement1.setZxZhengWenFileList(zhengWenFileList);
        }
        // 得到分页信息
        PageInfo<ZxCtFsSideAgreement> p = new PageInfo<>(zxCtFsSideAgreementList);

        return repEntity.okList(zxCtFsSideAgreementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsSideAgreementDetail(ZxCtFsSideAgreement zxCtFsSideAgreement) {
        if (zxCtFsSideAgreement == null) {
            zxCtFsSideAgreement = new ZxCtFsSideAgreement();
        }
        ZxCtFsSideAgreement dbZxCtFsSideAgreement = new ZxCtFsSideAgreement();
        // 获取数据
        if (zxCtFsSideAgreement.getZxCtFsSideAgreementId() != "") {
            dbZxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryKey(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
        } else if (zxCtFsSideAgreement.getWorkId() != null) {
            dbZxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryWorkId(zxCtFsSideAgreement.getWorkId());
        }

        // 数据存在
        if (dbZxCtFsSideAgreement != null) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(dbZxCtFsSideAgreement.getZxCtFsSideAgreementId());
            file.setOtherType("1");
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxCtFsSideAgreement.setZxErpFileList(zxErpFileList);

            file.setOtherType("2");
            List<ZxErpFile> zhengWenFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxCtFsSideAgreement.setZxZhengWenFileList(zhengWenFileList);
            return repEntity.ok(dbZxCtFsSideAgreement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsSideAgreement.setZxCtFsSideAgreementId(UuidUtil.generate());
        zxCtFsSideAgreement.setCreateUserInfo(userKey, realName);
        zxCtFsSideAgreement.setBeginPer(realName);
        zxCtFsSideAgreement.setApih5FlowStatus("-1");
//        zxCtFsSideAgreement.setUpAlterContractSum(zxCtFsSideAgreement.getContractCost());//上期末变更后含税金额
//        zxCtFsSideAgreement.setUpAlterContractSumNoTax(zxCtFsSideAgreement.getContractCostNoTax());//上期末变更后不含税合同金额
        ZxCtFsSideAgreement zxCtFsSideAgreement2 = new ZxCtFsSideAgreement();
        zxCtFsSideAgreement2.setZxCtFsContractId(zxCtFsSideAgreement.getZxCtFsContractId());
        List<ZxCtFsSideAgreement> list = zxCtFsSideAgreementMapper.checkSideAgement(zxCtFsSideAgreement2);
        if (list.size() > 0) {
            return repEntity.layerMessage("no", "该合同存在未审核的补充协议！");
        }

        ZxCtFsSideAgreement zxCtFsSideAgreement3 = new ZxCtFsSideAgreement();
        zxCtFsSideAgreement3.setContractNo(zxCtFsSideAgreement.getContractNo());
        List<ZxCtFsSideAgreement> list2 = zxCtFsSideAgreementMapper.checkSideAgement(zxCtFsSideAgreement2);
        if (list2.size() > 0) {
            return repEntity.layerMessage("no", "该补充协议编号已经存在！");
        }
//        int sum= zxCtFsSideAgreementMapper.sumContractNo(zxCtFsSideAgreement.getContractNo())+1;
//        zxCtFsSideAgreement.setContractNo(zxCtFsSideAgreement.getContractNo()+"-"+"补"+String.format("%02d", sum));
        int flag = zxCtFsSideAgreementMapper.insert(zxCtFsSideAgreement);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 附件
            List<ZxErpFile> zxErpFileList = zxCtFsSideAgreement.getZxErpFileList();
            if (zxErpFileList != null && zxErpFileList.size() > 0) {
                for (ZxErpFile zxErpFile : zxErpFileList) {
                    zxErpFile.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(zxErpFile);
                    if (flag == 0) {
                        return repEntity.layerMessage("no", "附件上传失败！");
                    }
                }
            }
            return repEntity.ok("sys.data.sava", zxCtFsSideAgreement);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsSideAgreement dbZxCtFsSideAgreement = zxCtFsSideAgreementMapper.selectByPrimaryKey(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
        if (dbZxCtFsSideAgreement != null && StrUtil.isNotEmpty(dbZxCtFsSideAgreement.getZxCtFsSideAgreementId())) {
            // 补充协议编号
            dbZxCtFsSideAgreement.setContractNo(zxCtFsSideAgreement.getContractNo());
            // 合同名称
            dbZxCtFsSideAgreement.setContractName(zxCtFsSideAgreement.getContractName());
            // 合同类型
            dbZxCtFsSideAgreement.setContractType(zxCtFsSideAgreement.getContractType());
            // 含税合同金额(万元)
            dbZxCtFsSideAgreement.setContractCost(zxCtFsSideAgreement.getContractCost());
            // 甲方ID
            dbZxCtFsSideAgreement.setFirstID(zxCtFsSideAgreement.getFirstID());
            // 甲方名称
            dbZxCtFsSideAgreement.setFirstName(zxCtFsSideAgreement.getFirstName());
            // 乙方ID
            dbZxCtFsSideAgreement.setSecondID(zxCtFsSideAgreement.getSecondID());
            // 乙方名称
            dbZxCtFsSideAgreement.setSecondName(zxCtFsSideAgreement.getSecondName());
            // 开工日期
            dbZxCtFsSideAgreement.setStartDate(zxCtFsSideAgreement.getStartDate());
            // 竣工日期
            dbZxCtFsSideAgreement.setEndDate(zxCtFsSideAgreement.getEndDate());
            // 合同工期
            dbZxCtFsSideAgreement.setCsTimeLimit(zxCtFsSideAgreement.getCsTimeLimit());
            // 合同签定人
            dbZxCtFsSideAgreement.setAgent(zxCtFsSideAgreement.getAgent());
            // 合同内容
            dbZxCtFsSideAgreement.setContent(zxCtFsSideAgreement.getContent());
            // 发起人
            dbZxCtFsSideAgreement.setBeginPer(zxCtFsSideAgreement.getBeginPer());
            // 评审状态
            dbZxCtFsSideAgreement.setStatus(zxCtFsSideAgreement.getStatus());
            // combProp
            dbZxCtFsSideAgreement.setCombProp(zxCtFsSideAgreement.getCombProp());
            // pp1
            dbZxCtFsSideAgreement.setPp1(zxCtFsSideAgreement.getPp1());
            // pp2
            dbZxCtFsSideAgreement.setPp2(zxCtFsSideAgreement.getPp2());
            // 补充协议名称
            dbZxCtFsSideAgreement.setPp3(zxCtFsSideAgreement.getPp3());
            // 本期含税增减金额(万元)
            dbZxCtFsSideAgreement.setPp4(zxCtFsSideAgreement.getPp4());
            // pp5
            dbZxCtFsSideAgreement.setPp5(zxCtFsSideAgreement.getPp5());
            // 合同名称ID
            dbZxCtFsSideAgreement.setPp6(zxCtFsSideAgreement.getPp6());
            // pp7
            dbZxCtFsSideAgreement.setPp7(zxCtFsSideAgreement.getPp7());
            // 清单
            dbZxCtFsSideAgreement.setPp8(zxCtFsSideAgreement.getPp8());
            // pp9
            dbZxCtFsSideAgreement.setPp9(zxCtFsSideAgreement.getPp9());
            // pp10
            dbZxCtFsSideAgreement.setPp10(zxCtFsSideAgreement.getPp10());
            // 流程实例ID
            dbZxCtFsSideAgreement.setInstProcessId(zxCtFsSideAgreement.getInstProcessId());
            // 公文任务ID
            dbZxCtFsSideAgreement.setWorkitemID(zxCtFsSideAgreement.getWorkitemID());
            // name
            dbZxCtFsSideAgreement.setCode(zxCtFsSideAgreement.getCode());
            // 变更后含税金额
            dbZxCtFsSideAgreement.setAlterContractSum(zxCtFsSideAgreement.getAlterContractSum());
            // 是否局审批
            dbZxCtFsSideAgreement.setIsFlag(zxCtFsSideAgreement.getIsFlag());
            // 发送局判断ID
            dbZxCtFsSideAgreement.setSendToJuID(zxCtFsSideAgreement.getSendToJuID());
            // 物资来源
            dbZxCtFsSideAgreement.setMaterialSource(zxCtFsSideAgreement.getMaterialSource());
            // 上期末变更后合同金额
            dbZxCtFsSideAgreement.setUpAlterContractSum(zxCtFsSideAgreement.getUpAlterContractSum());
            // 是否局指审批
            dbZxCtFsSideAgreement.setIsFlagZhb(zxCtFsSideAgreement.getIsFlagZhb());
            // 发送局指判断ID
            dbZxCtFsSideAgreement.setSendToZhbID(zxCtFsSideAgreement.getSendToZhbID());
            // 税率(%)
            dbZxCtFsSideAgreement.setTaxRate(zxCtFsSideAgreement.getTaxRate());

            // 不含税合同金额(万元)
            dbZxCtFsSideAgreement.setContractCostNoTax(zxCtFsSideAgreement.getContractCostNoTax());
            // 变更后不含税金额
            dbZxCtFsSideAgreement.setAlterContractSumNoTax(zxCtFsSideAgreement.getAlterContractSumNoTax());
            // 变更后税额
            dbZxCtFsSideAgreement.setAlterContractSumTax(zxCtFsSideAgreement.getAlterContractSumTax());
            // 本期不含税增减金额(万元)
            dbZxCtFsSideAgreement.setPp4NoTax(zxCtFsSideAgreement.getPp4NoTax());
            // 本期增减税额(万元)
            dbZxCtFsSideAgreement.setPp4Tax(zxCtFsSideAgreement.getPp4Tax());
            // 合同税额(万元)
            dbZxCtFsSideAgreement.setContractCostTax(zxCtFsSideAgreement.getContractCostTax());
            // upAlterContractSumNoTax
            dbZxCtFsSideAgreement.setUpAlterContractSumNoTax(zxCtFsSideAgreement.getUpAlterContractSumNoTax());
            // comID
            dbZxCtFsSideAgreement.setComID(zxCtFsSideAgreement.getComID());
            // upAlterContractSumTax
            dbZxCtFsSideAgreement.setUpAlterContractSumTax(zxCtFsSideAgreement.getUpAlterContractSumTax());
            // orgID
            dbZxCtFsSideAgreement.setOrgID(zxCtFsSideAgreement.getOrgID());
            // 管理机构
            dbZxCtFsSideAgreement.setOrgName(zxCtFsSideAgreement.getOrgName());
            // 变更等级
            dbZxCtFsSideAgreement.setAlterLevel(zxCtFsSideAgreement.getAlterLevel());
            // 提出单位
            dbZxCtFsSideAgreement.setProposer(zxCtFsSideAgreement.getProposer());
            // 变更内容
            dbZxCtFsSideAgreement.setAlterContent(zxCtFsSideAgreement.getAlterContent());
            // 变更原因
            dbZxCtFsSideAgreement.setAlterReason(zxCtFsSideAgreement.getAlterReason());
            // 发生时间
            dbZxCtFsSideAgreement.setHappenDate(zxCtFsSideAgreement.getHappenDate());
            // 本期含税变更增减金额(万元)
            dbZxCtFsSideAgreement.setApplyAmount(zxCtFsSideAgreement.getApplyAmount());
            // 申报日期
            dbZxCtFsSideAgreement.setApplyDate(zxCtFsSideAgreement.getApplyDate());
            // 申报文号
            dbZxCtFsSideAgreement.setApplyNo(zxCtFsSideAgreement.getApplyNo());
            // 申报延期天数
            dbZxCtFsSideAgreement.setApplyDelay(zxCtFsSideAgreement.getApplyDelay());
            // 变更后含税合同金额(万元)
            dbZxCtFsSideAgreement.setReplyAmount(zxCtFsSideAgreement.getReplyAmount());
            // 批复日期
            dbZxCtFsSideAgreement.setReplyDate(zxCtFsSideAgreement.getReplyDate());
            // 补充协议书编号
            dbZxCtFsSideAgreement.setReplyNo(zxCtFsSideAgreement.getReplyNo());
            // 批复延期天数
            dbZxCtFsSideAgreement.setReplyDelay(zxCtFsSideAgreement.getReplyDelay());
            // 批复状态
            dbZxCtFsSideAgreement.setReplyStatus(zxCtFsSideAgreement.getReplyStatus());
            // 需要公司协助
            dbZxCtFsSideAgreement.setCompanyHelp(zxCtFsSideAgreement.getCompanyHelp());
            // 生效操作日期
            dbZxCtFsSideAgreement.setTakeEffectDate(zxCtFsSideAgreement.getTakeEffectDate());
            // 生效操作人
            dbZxCtFsSideAgreement.setTakeEffectMan(zxCtFsSideAgreement.getTakeEffectMan());
            // 状态
            dbZxCtFsSideAgreement.setAuditStatus(zxCtFsSideAgreement.getAuditStatus());
            // 记录人
            dbZxCtFsSideAgreement.setRecorder(zxCtFsSideAgreement.getRecorder());
            // 记录日期
            dbZxCtFsSideAgreement.setRecordDate(zxCtFsSideAgreement.getRecordDate());
            // 本期不含税变更增减金额(万元)
            dbZxCtFsSideAgreement.setApplyAmountNoTax(zxCtFsSideAgreement.getApplyAmountNoTax());
            // 本期变更增减税额(万元)
            dbZxCtFsSideAgreement.setApplyAmountTax(zxCtFsSideAgreement.getApplyAmountTax());
            // 变更后不含税合同金额(万元)
            dbZxCtFsSideAgreement.setReplyAmountNoTax(zxCtFsSideAgreement.getReplyAmountNoTax());
            // 变更后合同税额(万元)
            dbZxCtFsSideAgreement.setReplyAmountTax(zxCtFsSideAgreement.getReplyAmountTax());
            // 不含税合同金额(万元)
            dbZxCtFsSideAgreement.setPp2NoTax(zxCtFsSideAgreement.getPp2NoTax());
            // 合同税额(万元)
            dbZxCtFsSideAgreement.setPp2Tax(zxCtFsSideAgreement.getPp2Tax());
            // 界面显示类型
            dbZxCtFsSideAgreement.setViewType(zxCtFsSideAgreement.getViewType());
            // 协作清单书ID
            dbZxCtFsSideAgreement.setCoBookId(zxCtFsSideAgreement.getCoBookId());
            // 索赔工期
            dbZxCtFsSideAgreement.setClaimPeriod(zxCtFsSideAgreement.getClaimPeriod());
            // 系统分类
            dbZxCtFsSideAgreement.setSystemType(zxCtFsSideAgreement.getSystemType());
            // 附属合同ID
            dbZxCtFsSideAgreement.setZxCtFsContractId(zxCtFsSideAgreement.getZxCtFsContractId());
            // 备注
            dbZxCtFsSideAgreement.setRemarks(zxCtFsSideAgreement.getRemarks());
            // 排序
            dbZxCtFsSideAgreement.setSort(zxCtFsSideAgreement.getSort());
            dbZxCtFsSideAgreement.setApih5FlowStatus(zxCtFsSideAgreement.getApih5FlowStatus());
            dbZxCtFsSideAgreement.setApih5FlowId(zxCtFsSideAgreement.getApih5FlowId());
            dbZxCtFsSideAgreement.setApih5FlowNodeStatus(zxCtFsSideAgreement.getApih5FlowNodeStatus());
            dbZxCtFsSideAgreement.setWorkId(zxCtFsSideAgreement.getWorkId());
            if (StrUtil.equals("opinionField1", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField1(zxCtFsSideAgreement.getOpinionContent(realName,
                        dbZxCtFsSideAgreement.getOpinionField1()));
            }
            //
            if (StrUtil.equals("opinionField2", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField2(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField2()));
            }
            //
            if (StrUtil.equals("opinionField3", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField3(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField3()));
            }
            //
            if (StrUtil.equals("opinionField4", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField4(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField4()));
            }
            //
            if (StrUtil.equals("opinionField5", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField5(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField5()));
            }
            //
            if (StrUtil.equals("opinionField6", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField6(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField6()));
            }
            //
            if (StrUtil.equals("opinionField7", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField7(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField7()));
            }
            //
            if (StrUtil.equals("opinionField8", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField8(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField8()));
            }
            //
            if (StrUtil.equals("opinionField9", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField9(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField9()));
            }
            //
            if (StrUtil.equals("opinionField10", zxCtFsSideAgreement.getOpinionField(), true)) {
                dbZxCtFsSideAgreement.setOpinionField10(zxCtFsSideAgreement.getOpinionContent(realName, dbZxCtFsSideAgreement.getOpinionField10()));
            }
//            if(zxCtFsSideAgreement.getIsDeduct().equals(dbZxCtFsSideAgreement.getIsDeduct())){
//                ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory= new ZxCtFsSideAgreementInventory();
//                zxCtFsSideAgreementInventory.setZxCtFsSideAgreementId(dbZxCtFsSideAgreement.getZxCtFsSideAgreementId());
//              List<ZxCtFsSideAgreementInventory>  inventories= zxCtFsSideAgreementInventoryMapper.selectByZxCtFsSideAgreementInventoryList(zxCtFsSideAgreementInventory);
//                for (ZxCtFsSideAgreementInventory inventory:inventories
//                     ) {
//                    inventory.setIsDeduct(zxCtFsSideAgreement.getIsDeduct());
//                   flag= zxCtFsSideAgreementInventoryMapper.updateByPrimaryKey(inventory);
//                }
//                if(flag==0) {
//                    return repEntity.layerMessage("no", "更新清单是否抵扣信息失败！");
//                }
//            }
            // 是否抵扣
            dbZxCtFsSideAgreement.setIsDeduct(zxCtFsSideAgreement.getIsDeduct());
            // 共通
            dbZxCtFsSideAgreement.setModifyUserInfo(userKey, realName);
            if ("2".equals(dbZxCtFsSideAgreement.getApih5FlowStatus())) {
                synReviewDetails(dbZxCtFsSideAgreement);
                ZxCtFsContract zxCtFsContract = zxCtFsContractMapper.selectByPrimaryKey(zxCtFsSideAgreement.getZxCtFsContractId());
                //zxCtFsContract.setZxCtFsContractId(zxCtFsSideAgreement.getZxCtFsContractId());
                zxCtFsContract.setAlterContractSum(zxCtFsSideAgreement.getAlterContractSum());
                zxCtFsContract.setAlterContractSumNoTax(zxCtFsSideAgreement.getAlterContractSumNoTax());
                zxCtFsContract.setAlterContractSumTax(zxCtFsSideAgreement.getAlterContractSumTax());
                flag = zxCtFsContractMapper.updateByPrimaryKey(zxCtFsContract);
                if (flag == 0) {
                    return repEntity.layerMessage("no", "同步更新合同管理信息失败！");
                }
            }

            flag = zxCtFsSideAgreementMapper.updateByPrimaryKey(dbZxCtFsSideAgreement);

            //附件先删除再新增
            ZxErpFile delFile = new ZxErpFile();

            if (zxCtFsSideAgreement.getZxErpFileList() != null && zxCtFsSideAgreement.getZxErpFileList().size() > 0) {
                delFile.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                delFile.setOtherType("1");
                List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
                if (delFileList != null && delFileList.size() > 0) {
                    delFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
                }
                for (ZxErpFile file : zxCtFsSideAgreement.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }

            if (zxCtFsSideAgreement.getZxZhengWenFileList() != null && zxCtFsSideAgreement.getZxZhengWenFileList().size() > 0) {
                delFile.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                delFile.setOtherType("2");
                List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
                if (delFileList != null && delFileList.size() > 0) {
                    delFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
                }
                for (ZxErpFile file : zxCtFsSideAgreement.getZxZhengWenFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
                    file.setOtherType("2");
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxCtFsSideAgreement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsSideAgreement(List<ZxCtFsSideAgreement> zxCtFsSideAgreementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxCtFsSideAgreementList != null && zxCtFsSideAgreementList.size() > 0) {
            for (ZxCtFsSideAgreement zxCtFsSide : zxCtFsSideAgreementList) {
                if (StrUtil.isNotEmpty(zxCtFsSide.getWorkId())) {
                    jsonArr.add(zxCtFsSide.getWorkId());
                }
            }

            ZxCtFsSideAgreement zxCtFsSideAgreement = new ZxCtFsSideAgreement();
            zxCtFsSideAgreement.setModifyUserInfo(userKey, realName);
            flag = zxCtFsSideAgreementMapper.batchDeleteUpdateZxCtFsSideAgreement(zxCtFsSideAgreementList, zxCtFsSideAgreement);
            if (jsonArr.size() > 0) {
                HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsSideAgreementList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity updateAgreementAndInventoryList(ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception {
        if (zxCtFsSideAgreement != null && zxCtFsSideAgreement.getInventoryList().size() > 0) {
            updateZxCtFsSideAgreement(zxCtFsSideAgreement);
            zxCtFsSideAgreementInventoryService.batchUpdateOrAdd(zxCtFsSideAgreement.getInventoryList());
            return repEntity.ok("sys.data.update", zxCtFsSideAgreement);
        } else {
            return repEntity.layerMessage("no", "输入数据错误！");
        }
    }


    /**
     * 同步附属合同评审清单
     *
     * @param zxCtFsSideAgreement
     * @author suncg
     */
    @Transactional(rollbackFor = Exception.class)
    public void synReviewDetails(ZxCtFsSideAgreement zxCtFsSideAgreement) throws Exception {
        ZxCtFsSideAgreementInventory zxCtFsSideAgreementInventory = new ZxCtFsSideAgreementInventory();
        zxCtFsSideAgreementInventory.setZxCtFsSideAgreementId(zxCtFsSideAgreement.getZxCtFsSideAgreementId());
        List<ZxCtFsSideAgreementInventory> zxCtFsSideAgreementInventorys = zxCtFsSideAgreementInventoryMapper.selectByZxCtFsSideAgreementInventoryList(zxCtFsSideAgreementInventory);
        if (zxCtFsSideAgreementInventorys.size() > 0) {
            for (ZxCtFsSideAgreementInventory agreementInventory1 : zxCtFsSideAgreementInventorys
            ) {
                if ("1".equals(agreementInventory1.getAlterType())) {
                    ZxCtFsContractReviewDetail addReviewDetail = new ZxCtFsContractReviewDetail();
                    addReviewDetail.setConRevDetailId(UuidUtil.generate());
                    addReviewDetail.setZxCtFsContractId(agreementInventory1.getZxCtFsContractId());// 合同ID
                    addReviewDetail.setWorkNo(agreementInventory1.getWorkNo()); // 清单编号
                    addReviewDetail.setWorkName(agreementInventory1.getWorkName());// 清单名称
                    addReviewDetail.setSpec(agreementInventory1.getSpec()); // 型号
                    addReviewDetail.setUnit(agreementInventory1.getUnit()); // 计量单位
                    addReviewDetail.setWorkType(agreementInventory1.getWorkType());// 设备ID(暂用workType)
                    addReviewDetail.setTreenode(agreementInventory1.getTreenode());// 层次
                    addReviewDetail.setQty(agreementInventory1.getQty());// 合同数量
                    addReviewDetail.setPrice(agreementInventory1.getChangePrice());//含税合同单价
                    addReviewDetail.setPriceNoTax(agreementInventory1.getChangePrice());//不含税合同单价
                    addReviewDetail.setContractSum(agreementInventory1.getContractSum());// 含税合同金额
                    addReviewDetail.setViewType(agreementInventory1.getViewType());// 界面展现类型
                    addReviewDetail.setPlanStartDate(agreementInventory1.getPlanStartDate()); // 计划开始时间
                    addReviewDetail.setPlanEndDate(agreementInventory1.getPlanEndDate());// 计划结束时间
                    addReviewDetail.setActualStartDate(agreementInventory1.getActualStartDate());// 租赁开始时间
                    addReviewDetail.setActualEndDate(agreementInventory1.getActualEndDate());// 租赁结束时间
                    addReviewDetail.setChangeQty(agreementInventory1.getChangeQty());// 变更后数量
                    addReviewDetail.setChangePrice(agreementInventory1.getChangePrice()); // 变更后含税单价
                    addReviewDetail.setChangeContractSum(agreementInventory1.getChangeContractSum());// 变更后含税金额
                    addReviewDetail.setRentUnit(agreementInventory1.getRentUnit());// 租赁单位
                    addReviewDetail.setAlterRentStartDate(agreementInventory1.getActualStartDate()); // 变更后租赁开始时间
                    addReviewDetail.setActualEndDate(agreementInventory1.getActualEndDate());// 变更后租赁结束时间
                    addReviewDetail.setPriceNoTax(agreementInventory1.getPriceNoTax());// 不含税合同单价
                    addReviewDetail.setContractSumNoTax(agreementInventory1.getContractSumNoTax());// 不含税合同金额
                    addReviewDetail.setChangePriceNoTax(agreementInventory1.getChangePriceNoTax());// 变更后不含税单价
                    addReviewDetail.setChangeContractSumNoTax(agreementInventory1.getChangeContractSumNoTax());// 变更后不含税金额
                    addReviewDetail.setTaxRate(agreementInventory1.getTaxRate());// 税率(%)
                    addReviewDetail.setDelFlag("0");
                    addReviewDetail.setRemarks(agreementInventory1.getRemarks());
                    zxCtFsContractReviewDetailMapper.insert(addReviewDetail);
                } else if ("2".equals(agreementInventory1.getAlterType())) {
                    ZxCtFsContractReviewDetail addReviewDetail = new ZxCtFsContractReviewDetail();
                    addReviewDetail = zxCtFsContractReviewDetailMapper.selectByPrimaryKey(agreementInventory1.getConRevDetailId());
                    if (addReviewDetail != null) {
                        addReviewDetail.setConRevDetailId(agreementInventory1.getConRevDetailId());
                        //addReviewDetail.setZxCtFsContractId(agreementInventory1.getZxCtFsContractId());// 合同ID
                        addReviewDetail.setWorkNo(agreementInventory1.getWorkNo()); // 清单编号
                        addReviewDetail.setWorkName(agreementInventory1.getWorkName());// 清单名称
                        addReviewDetail.setSpec(agreementInventory1.getSpec()); // 型号
                        addReviewDetail.setUnit(agreementInventory1.getUnit()); // 计量单位
                        addReviewDetail.setWorkType(agreementInventory1.getWorkType());// 设备ID(暂用workType)
                        addReviewDetail.setTreenode(agreementInventory1.getTreenode());// 层次
                        addReviewDetail.setQty(agreementInventory1.getQty());// 合同数量
                        addReviewDetail.setPrice(agreementInventory1.getPrice());//含税合同单价
                        addReviewDetail.setContractSum(agreementInventory1.getContractSum());// 含税合同金额
                        addReviewDetail.setViewType(agreementInventory1.getViewType());// 界面展现类型
                        addReviewDetail.setPlanStartDate(agreementInventory1.getPlanStartDate()); // 计划开始时间
                        addReviewDetail.setPlanEndDate(agreementInventory1.getPlanEndDate());// 计划结束时间
                        addReviewDetail.setActualStartDate(agreementInventory1.getActualStartDate());// 租赁开始时间
                        addReviewDetail.setActualEndDate(agreementInventory1.getActualEndDate());// 租赁结束时间
                        addReviewDetail.setChangeQty(agreementInventory1.getChangeQty());// 变更后数量
                        addReviewDetail.setChangePrice(agreementInventory1.getChangePrice()); // 变更后含税单价
                        addReviewDetail.setChangeContractSum(agreementInventory1.getChangeContractSum());// 变更后含税金额
                        addReviewDetail.setRentUnit(agreementInventory1.getRentUnit());// 租赁单位
                        addReviewDetail.setAlterRentStartDate(agreementInventory1.getActualStartDate()); // 变更后租赁开始时间
                        addReviewDetail.setActualEndDate(agreementInventory1.getActualEndDate());// 变更后租赁结束时间
                        addReviewDetail.setPriceNoTax(agreementInventory1.getPriceNoTax());// 不含税合同单价
                        addReviewDetail.setContractSumNoTax(agreementInventory1.getContractSumNoTax());// 不含税合同金额
                        addReviewDetail.setChangePriceNoTax(agreementInventory1.getChangePriceNoTax());// 变更后不含税单价
                        addReviewDetail.setChangeContractSumNoTax(agreementInventory1.getChangeContractSumNoTax());// 变更后不含税金额
                        addReviewDetail.setTaxRate(agreementInventory1.getTaxRate());// 税率(%)
                        addReviewDetail.setDelFlag("0");
                        addReviewDetail.setRemarks(agreementInventory1.getRemarks());
                        zxCtFsContractReviewDetailMapper.updateByPrimaryKey(addReviewDetail);
                    }
                }
            }

        }
    }

    /**
     * 导出附属合同补充协议列表
     *
     * @param response
     * @author suncg
     */

    @Override
    public ResponseEntity exportSideAgreement(ZxCtFsSideAgreement zxCtFsSideAgreement, HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // HttpServletResponse response = new HttpServletResponse();
        if (zxCtFsSideAgreement == null) {
            zxCtFsSideAgreement = new ZxCtFsSideAgreement();
        }

        // 获取数据
        List<ZxCtFsSideAgreement> zxCtFsSideAgreementList = zxCtFsSideAgreementMapper.selectByZxCtFsSideAgreementList(zxCtFsSideAgreement);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("补充协议编号", "补充协议名称", "合同名称", "合同类型", "甲方名称", "乙方名称",
                "合同签订人", "含税合同金额（万元）", "本期含税增减金额（万元）", "变更后含税金额（万元）", "是否抵扣", "开工日期", "竣工日期", "发起人", "评审状态");
        rowsList.add(row1);

        // 数据装载（与上面的表头对应）
        if (zxCtFsSideAgreementList != null && zxCtFsSideAgreementList.size() > 0) {
            for (ZxCtFsSideAgreement zxCtFsSideAgreement1 : zxCtFsSideAgreementList) {
                rowsList.add(CollUtil.newArrayList(zxCtFsSideAgreement1.getContractNo(),
                        zxCtFsSideAgreement1.getContractNo(),
                        zxCtFsSideAgreement1.getPp3(),
                        zxCtFsSideAgreement1.getContractName(),
                        zxCtFsSideAgreement1.getContractType(),
                        zxCtFsSideAgreement1.getFirstName(),
                        zxCtFsSideAgreement1.getSecondName(),
                        zxCtFsSideAgreement1.getAgent(),
                        zxCtFsSideAgreement1.getContractCost(),
                        zxCtFsSideAgreement1.getPp4(),
                        zxCtFsSideAgreement1.getAlterContractSum(),
                        zxCtFsSideAgreement1.getIsDeduct(),
                        zxCtFsSideAgreement1.getStartDate(),
                        zxCtFsSideAgreement1.getEndDate(),
                        zxCtFsSideAgreement1.getBeginPer(),
                        zxCtFsSideAgreement1.getStatus()
                        )
                );
            }

            // 报表名称
            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            String fileName = "xxx报表-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("fdsafdsa".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // 一次性写出内容，使用默认样式，强制输出标题
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭writer，释放内存
                if (writer != null) {
                    writer.close();
                }
                // 此处记得关闭输出Servlet流
                if (out != null) {
                    IoUtil.close(out);
                }
            }

            //String url = HttpUtil.
            return null;
        } else {
            return repEntity.ok("无数据");
        }
    }

    @Override
    public ResponseEntity getContractNo(ZxCtFsSideAgreement zxCtFsSideAgreement) {
        if (zxCtFsSideAgreement == null) {
            repEntity.layerMessage("no", "合同编号不能为空");
        } else if (zxCtFsSideAgreement.getContractNo().equals("")) {
            repEntity.layerMessage("no", "合同编号不能为空");
        }

        int sum = zxCtFsSideAgreementMapper.sumContractNo(zxCtFsSideAgreement.getContractNo()) + 1;
        zxCtFsSideAgreement.setContractNo(zxCtFsSideAgreement.getContractNo() + "-" + "补" + String.format("%02d", sum));
        //ZxCtFsContractReview dbZxCtFsContractReview = new ZxCtFsContractReview();
        return repEntity.ok(zxCtFsSideAgreement);

    }
}
