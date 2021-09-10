package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.api.wechatutils.StringUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxCtOtherSupplyAgreementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxCtOtherSupplyAgreementService")
public class ZxCtOtherSupplyAgreementServiceImpl implements ZxCtOtherSupplyAgreementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherSupplyAgreementMapper zxCtOtherSupplyAgreementMapper;

    @Autowired(required = true)
    private ZxCtOtherSupplyAgreementWorksMapper zxCtOtherSupplyAgreementWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;
    @Override
    public ResponseEntity getZxCtOtherSupplyAgreementListByCondition(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        if (zxCtOtherSupplyAgreement == null) {
            zxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtOtherSupplyAgreement.setCompanyId("");
            zxCtOtherSupplyAgreement.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCtOtherSupplyAgreement.setCompanyId(zxCtOtherSupplyAgreement.getOrgId());
            zxCtOtherSupplyAgreement.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCtOtherSupplyAgreement.setOrgId(zxCtOtherSupplyAgreement.getOrgId());
        }
        // 分页查询
        PageHelper.startPage(zxCtOtherSupplyAgreement.getPage(),zxCtOtherSupplyAgreement.getLimit());
        // 获取数据
        List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByZxCtOtherSupplyAgreementList(zxCtOtherSupplyAgreement);
        //查询附件
        for (ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement1 : zxCtOtherSupplyAgreementList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCtOtherSupplyAgreement1.getZxCtOtherSupplyAgreementId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCtOtherSupplyAgreement1.setZxErpFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxCtOtherSupplyAgreement> p = new PageInfo<>(zxCtOtherSupplyAgreementList);

        return repEntity.okList(zxCtOtherSupplyAgreementList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherSupplyAgreementDetail(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        if (zxCtOtherSupplyAgreement == null) {
            zxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        }
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        if(StrUtil.isNotEmpty(zxCtOtherSupplyAgreement.getWorkId())) {
            ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement1 = new ZxCtOtherSupplyAgreement();
            zxCtOtherSupplyAgreement1.setWorkId(zxCtOtherSupplyAgreement.getWorkId());
            List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByZxCtOtherSupplyAgreementList(zxCtOtherSupplyAgreement1);
            if(zxCtOtherSupplyAgreementList != null && zxCtOtherSupplyAgreementList.size() >0) {
                dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementList.get(0);
            }
        }else {
            // 获取数据
            dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
        }
        // 数据存在
        if (dbZxCtOtherSupplyAgreement != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            List<ZxErpFile> zw = new ArrayList<>();
            List<ZxErpFile> fj = new ArrayList<>();
            zxErpFiles.forEach(f -> {
                if ("1".equals(f.getOtherType())) {
                    zw.add(f);
                } else if ("0".equals(f.getOtherType())) {
                    fj.add(f);
                }
            });
            dbZxCtOtherSupplyAgreement.setZxErpFileList(fj);
            dbZxCtOtherSupplyAgreement.setZxZhengWenFileList(zw);
            return repEntity.ok(dbZxCtOtherSupplyAgreement);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 其他合同管理id不能为空
        if (StrUtil.isEmpty(zxCtOtherSupplyAgreement.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "其他合同管理ID不能为空！");
        }
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        dbZxCtOtherSupplyAgreement.setZxCtOtherManageId(zxCtOtherSupplyAgreement.getZxCtOtherManageId());
        List<ZxCtOtherSupplyAgreement> dbZxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByZxCtOtherSupplyAgreementList(dbZxCtOtherSupplyAgreement);
        if (dbZxCtOtherSupplyAgreementList != null && dbZxCtOtherSupplyAgreementList.size() > 0) {
            for (ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement1 : dbZxCtOtherSupplyAgreementList) {
                // 如果当前合同的补充协议有评审未完成的数据，不能新增补充协议
                if(!"2".equals(zxCtOtherSupplyAgreement1.getApih5FlowStatus())){
                    return repEntity.layerMessage("no", "该合同的补充协议存在未评审通过的数据，请先评审完！");
                }
            }
        }
        // 其他合同數據
        ZxCtOtherManage zxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherManageId());
        if (zxCtOtherManage == null) {
            return repEntity.layerMessage("no", "其他合同不存在！");
        }

        // 上期末变更后合同金额 初始化值就是变更后合同金额
        zxCtOtherSupplyAgreement.setUpAlterContractSum(zxCtOtherManage.getAlterContractSum() == null ? new BigDecimal(0) : zxCtOtherManage.getAlterContractSum());
        // 上期末变更后金额不含税 初始化值就是变更后不含税合同金额
        zxCtOtherSupplyAgreement.setUpAlterContractSumNoTax(zxCtOtherManage.getAlterContractSumNoTax() == null ? new BigDecimal(0) : zxCtOtherManage.getAlterContractSumNoTax());
        // 上期末变更后税额 初始化值就是变更后税额
        zxCtOtherSupplyAgreement.setUpAlterContractSumTax(zxCtOtherManage.getAlterContractSumTax() == null ? new BigDecimal(0) : zxCtOtherManage.getAlterContractSumTax());
        // 新增变更协议 变更后默认上期变更后
        zxCtOtherSupplyAgreement.setAlterContractSum(zxCtOtherSupplyAgreement.getUpAlterContractSum());
        zxCtOtherSupplyAgreement.setAlterContractSumNoTax(zxCtOtherSupplyAgreement.getUpAlterContractSumNoTax());
        zxCtOtherSupplyAgreement.setAlterContractSumTax(zxCtOtherSupplyAgreement.getUpAlterContractSumTax());
        zxCtOtherSupplyAgreement.setZxCtOtherManageId(zxCtOtherSupplyAgreement.getZxCtOtherManageId());
        // 评审状态设置为未评审
        zxCtOtherSupplyAgreement.setApih5FlowStatus("-1");
        // 发起人设置为当前登录用户
        zxCtOtherSupplyAgreement.setBeginPer(realName);
        zxCtOtherSupplyAgreement.setZxCtOtherSupplyAgreementId(UuidUtil.generate());
        zxCtOtherSupplyAgreement.setCreateUserInfo(userKey, realName);
        int flag = zxCtOtherSupplyAgreementMapper.insert(zxCtOtherSupplyAgreement);

        //添加附件
        List<ZxErpFile> fileList = zxCtOtherSupplyAgreement.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOtherSupplyAgreement);
        }
    }

    @Override
    public ResponseEntity updateZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
        if (dbZxCtOtherSupplyAgreement != null && StrUtil.isNotEmpty(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId())) {
            // 是否抵扣
            dbZxCtOtherSupplyAgreement.setIsDeduct(zxCtOtherSupplyAgreement.getIsDeduct());
            // 补充协议名称
            dbZxCtOtherSupplyAgreement.setSupplyAgreementName(zxCtOtherSupplyAgreement.getSupplyAgreementName());
            dbZxCtOtherSupplyAgreement.setProposer(zxCtOtherSupplyAgreement.getProposer());
            // 合同签定人
            dbZxCtOtherSupplyAgreement.setAgent(zxCtOtherSupplyAgreement.getAgent());
            // 含税合同金额(万元)
            dbZxCtOtherSupplyAgreement.setContractCost(zxCtOtherSupplyAgreement.getContractCost());
            // 不含税合同金额(万元)
            dbZxCtOtherSupplyAgreement.setContractCostNoTax(zxCtOtherSupplyAgreement.getContractCostNoTax());
            // 合同税额(万元)
            dbZxCtOtherSupplyAgreement.setContractCostTax(zxCtOtherSupplyAgreement.getContractCostTax());
            // 变更后含税金额
            dbZxCtOtherSupplyAgreement.setAlterContractSum(zxCtOtherSupplyAgreement.getAlterContractSum());
            // 变更后不含税金额
            dbZxCtOtherSupplyAgreement.setAlterContractSumNoTax(zxCtOtherSupplyAgreement.getAlterContractSumNoTax());
            // 变更后税额
            dbZxCtOtherSupplyAgreement.setAlterContractSumTax(zxCtOtherSupplyAgreement.getAlterContractSumTax());
            // 开工日期
            dbZxCtOtherSupplyAgreement.setStartDate(zxCtOtherSupplyAgreement.getStartDate());
            // 竣工日期
            dbZxCtOtherSupplyAgreement.setEndDate(zxCtOtherSupplyAgreement.getEndDate());
            // 合同工期
            dbZxCtOtherSupplyAgreement.setCsTimeLimit(zxCtOtherSupplyAgreement.getCsTimeLimit());
            // 合同内容
            dbZxCtOtherSupplyAgreement.setContent(zxCtOtherSupplyAgreement.getContent());
            // 备注
            dbZxCtOtherSupplyAgreement.setRemark(zxCtOtherSupplyAgreement.getRemark());
            // 批复单位
            dbZxCtOtherSupplyAgreement.setReplyUnit(zxCtOtherSupplyAgreement.getReplyUnit());
            // 批复时间
            dbZxCtOtherSupplyAgreement.setReplyDate(zxCtOtherSupplyAgreement.getReplyDate());
            // 变更内容
            dbZxCtOtherSupplyAgreement.setAlterContent(zxCtOtherSupplyAgreement.getAlterContent());
            // 变更原因
            dbZxCtOtherSupplyAgreement.setAlterReason(zxCtOtherSupplyAgreement.getAlterReason());
            // workId
            dbZxCtOtherSupplyAgreement.setWorkId(zxCtOtherSupplyAgreement.getWorkId());
            // 流程id
            dbZxCtOtherSupplyAgreement.setApih5FlowId(zxCtOtherSupplyAgreement.getApih5FlowId());
            // 审核状态
            dbZxCtOtherSupplyAgreement.setApih5FlowStatus(zxCtOtherSupplyAgreement.getApih5FlowStatus());
            // 审核节点状态
            dbZxCtOtherSupplyAgreement.setApih5FlowNodeStatus(zxCtOtherSupplyAgreement.getApih5FlowNodeStatus());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField1(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField1(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField2", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField2(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField2(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField3", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField3(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField3(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField4", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField4(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField4(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField5", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField5(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField5(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField6", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField6(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField6(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField7", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField7(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField7(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField8", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField8(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField8(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField9", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField9(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField9(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField10", zxCtOtherSupplyAgreement.getOpinionField(), true)){
                dbZxCtOtherSupplyAgreement.setOpinionField10(getOpinionContent(realName, dbZxCtOtherSupplyAgreement.getOpinionField10(), zxCtOtherSupplyAgreement.getOpinionContent()));
            }

           // 共通
           dbZxCtOtherSupplyAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherSupplyAgreementMapper.updateByPrimaryKey(dbZxCtOtherSupplyAgreement);

           // 修改补充协议清单明细表的是否抵扣
            ZxCtOtherSupplyAgreementWorks dbZxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
            dbZxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementId(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxCtOtherSupplyAgreementWorks> dbZxCtOtherSupplyAgreementWorksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(dbZxCtOtherSupplyAgreementWorks);
            if (dbZxCtOtherSupplyAgreementWorksList != null && dbZxCtOtherSupplyAgreementWorksList.size() > 0) {
                for(ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks : dbZxCtOtherSupplyAgreementWorksList) {
                    zxCtOtherSupplyAgreementWorks.setIsDeduct(dbZxCtOtherSupplyAgreement.getIsDeduct());
                    zxCtOtherSupplyAgreementWorksMapper.updateByPrimaryKey(zxCtOtherSupplyAgreementWorks);
                }
            }

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //明细list 0附件 1正文
            List<ZxErpFile> fileList = zxCtOtherSupplyAgreement.getZxErpFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                    zxErpFile.setOtherType("0");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }

            List<ZxErpFile> zhengWen = zxCtOtherSupplyAgreement.getZxZhengWenFileList();
            if (!CollectionUtils.isEmpty(zhengWen)) {
                for(ZxErpFile zxErpFile : zhengWen) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }

            // 评审通过
            if(StrUtil.equals(zxCtOtherSupplyAgreement.getApih5FlowStatus(), "2")) {
                this.zxCtOtherSupplyAgreementReviewApply(zxCtOtherSupplyAgreement);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherSupplyAgreement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOtherSupplyAgreement(List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zxCtOtherSupplyAgreementList != null && zxCtOtherSupplyAgreementList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement : zxCtOtherSupplyAgreementList) {
                //删除明细
                ZxCtOtherSupplyAgreementWorks delZxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
                delZxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                List<ZxCtOtherSupplyAgreementWorks> delWorkList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(delZxCtOtherSupplyAgreementWorks);
                if(delWorkList != null && delWorkList.size() > 0) {
                    delZxCtOtherSupplyAgreementWorks.setModifyUserInfo(userKey, realName);
                    zxCtOtherSupplyAgreementWorksMapper.batchDeleteUpdateZxCtOtherSupplyAgreementWorks(delWorkList, delZxCtOtherSupplyAgreementWorks);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                if(StrUtil.isNotEmpty(zxCtOtherSupplyAgreement.getWorkId())) {
                    jsonArray.add(zxCtOtherSupplyAgreement.getWorkId());
                }
            }
           ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
           zxCtOtherSupplyAgreement.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherSupplyAgreementMapper.batchDeleteUpdateZxCtOtherSupplyAgreement(zxCtOtherSupplyAgreementList, zxCtOtherSupplyAgreement);

            // 删除流程后台数据
            String url = Apih5Properties.getWebUrl() + "batchDeleteFlow";
            if(jsonArray.size()>0) {
                HttpUtil.sendPostToken(url, jsonArray.toString(), token);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtOtherSupplyAgreementList);
        }
    }

    /**
     *
     * @param realName ==姓名
     * @param dbOpinionContent==数据库里的值
     * @param opinionContent===过来的值
     * @return
     */
    private String getOpinionContent(String realName, String dbOpinionContent, String opinionContent){
        if(StrUtil.isNotEmpty(opinionContent)){
            opinionContent = StrUtil.isEmpty(dbOpinionContent)? opinionContent: dbOpinionContent+ "<br/><br/>" + opinionContent;
            opinionContent += "<br/>" + realName + "  " + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        return opinionContent;
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity zxCtOtherSupplyAgreementReviewApply(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
        if (dbZxCtOtherSupplyAgreement != null && StrUtil.isNotEmpty(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId())) {
            // 评审通过后修改合同补充协议表的评审状态
            dbZxCtOtherSupplyAgreement.setApih5FlowStatus("2");
            dbZxCtOtherSupplyAgreement.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherSupplyAgreementMapper.updateByPrimaryKey(dbZxCtOtherSupplyAgreement);

            //评审通过后改变对应的其他合同管理数据
            ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(dbZxCtOtherSupplyAgreement.getZxCtOtherManageId());
            // 变更后含税合同金额(万元)
            dbZxCtOtherManage.setAlterContractSum(dbZxCtOtherSupplyAgreement.getAlterContractSum());
            // 变更后不含税合同金额(万元)
            dbZxCtOtherManage.setAlterContractSumNoTax(dbZxCtOtherSupplyAgreement.getAlterContractSumNoTax());
            // 变更后合同税额(万元)
            dbZxCtOtherManage.setAlterContractSumTax(dbZxCtOtherSupplyAgreement.getAlterContractSumTax());
            dbZxCtOtherManage.setModifyUserInfo(userKey, realName);
            zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
            // 根据补充协议明细更新或插入合同管理明细表数据
            ZxCtOtherSupplyAgreementWorks dbWorks = new ZxCtOtherSupplyAgreementWorks();
            dbWorks.setZxCtOtherSupplyAgreementId(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxCtOtherSupplyAgreementWorks> worksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(dbWorks);
            for (ZxCtOtherSupplyAgreementWorks zxCtOtherSupplyAgreementWorks1 : worksList) {
                if ("2".equals(zxCtOtherSupplyAgreementWorks1.getAlterType())) {
                    ZxCtOtherWorks dbZxCtOtherWorks = zxCtOtherWorksMapper.selectByPrimaryKey(zxCtOtherSupplyAgreementWorks1.getZxCtOtherWorksId());
                    // 变更后数量
                    dbZxCtOtherWorks.setChangeQty(zxCtOtherSupplyAgreementWorks1.getChangeQty());
                    // 变更后含税金额
                    dbZxCtOtherWorks.setChangeContractSum(zxCtOtherSupplyAgreementWorks1.getChangeContractSum());
                    // 变更后不含税金额
                    dbZxCtOtherWorks.setChangeContractSumNoTax(zxCtOtherSupplyAgreementWorks1.getChangeContractSumNoTax());
                    // 变更后税额
                    dbZxCtOtherWorks.setChangeContractSumTax(zxCtOtherSupplyAgreementWorks1.getChangeContractSumTax());
                    // 变更后单价
                    if (dbZxCtOtherWorks.getChangePrice() == null) {
                        dbZxCtOtherWorks.setChangePrice(zxCtOtherSupplyAgreementWorks1.getChangePrice());
                    }
                    // 变更后不含税单价
                    if (dbZxCtOtherWorks.getChangePriceNoTax() == null) {
                        dbZxCtOtherWorks.setChangePriceNoTax(zxCtOtherSupplyAgreementWorks1.getChangePriceNoTax());
                    }
                    dbZxCtOtherWorks.setModifyUserInfo(userKey, realName);
                    zxCtOtherWorksMapper.updateByPrimaryKey(dbZxCtOtherWorks);
                } else {
                    ZxCtOtherWorks zxCtOtherWorks = new ZxCtOtherWorks();
                    // 清单编号
                    zxCtOtherWorks.setWorkNo(zxCtOtherSupplyAgreementWorks1.getWorkNo());
                    // 清单名称
                    zxCtOtherWorks.setWorkName(zxCtOtherSupplyAgreementWorks1.getWorkName());
                    // 单位
                    zxCtOtherWorks.setUnit(zxCtOtherSupplyAgreementWorks1.getUnit());
                    // 变更后数量
                    zxCtOtherWorks.setChangeQty(zxCtOtherSupplyAgreementWorks1.getChangeQty());
                    // 变更后单价
                    zxCtOtherWorks.setChangePrice(zxCtOtherSupplyAgreementWorks1.getChangePrice());
                    // 变更后不含税单价
                    zxCtOtherWorks.setChangePriceNoTax(zxCtOtherSupplyAgreementWorks1.getChangePriceNoTax());
                    // 变更后含税金额
                    zxCtOtherWorks.setChangeContractSum(zxCtOtherSupplyAgreementWorks1.getChangeContractSum());
                    // 变更后不含税金额
                    zxCtOtherWorks.setChangeContractSumNoTax(zxCtOtherSupplyAgreementWorks1.getChangeContractSumNoTax());
                    // 变更后税额
                    zxCtOtherWorks.setChangeContractSumTax(zxCtOtherSupplyAgreementWorks1.getChangeContractSumTax());
                    // 税率
                    zxCtOtherWorks.setTaxRate(zxCtOtherSupplyAgreementWorks1.getTaxRate());
                    // 是否抵扣
                    zxCtOtherWorks.setIsDeduct(zxCtOtherSupplyAgreementWorks1.getIsDeduct());
                    // 备注
                    zxCtOtherWorks.setRemark(zxCtOtherSupplyAgreementWorks1.getRemark());
                    // 合同管理id
                    zxCtOtherWorks.setZxCtOtherManageId(dbZxCtOtherManage.getZxCtOtherManageId());
                    zxCtOtherWorks.setZxCtOtherWorksId(UuidUtil.generate());
                    zxCtOtherWorks.setCreateUserInfo(userKey, realName);
                    zxCtOtherWorksMapper.insert(zxCtOtherWorks);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",dbZxCtOtherSupplyAgreement);
        }
    }

    @Override
    public ResponseEntity zxCtOtherSupplyAgreementReviewApplyCheck(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement = zxCtOtherSupplyAgreementMapper.selectByPrimaryKey(zxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
        if (dbZxCtOtherSupplyAgreement != null && StrUtil.isNotEmpty(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId())) {
            // 查询当前合同补充协议清单明细是否有数据，无数据提示清单未编辑不能发起评审
            ZxCtOtherSupplyAgreementWorks dbZxCtOtherSupplyAgreementWorks = new ZxCtOtherSupplyAgreementWorks();
            dbZxCtOtherSupplyAgreementWorks.setZxCtOtherSupplyAgreementId(dbZxCtOtherSupplyAgreement.getZxCtOtherSupplyAgreementId());
            List<ZxCtOtherSupplyAgreementWorks> dbZxCtOtherSupplyAgreementWorksList = zxCtOtherSupplyAgreementWorksMapper.selectByZxCtOtherSupplyAgreementWorksList(dbZxCtOtherSupplyAgreementWorks);
            if (dbZxCtOtherSupplyAgreementWorksList == null || dbZxCtOtherSupplyAgreementWorksList.size() == 0) {
                return repEntity.layerMessage("no", "清单未编辑不能发起评审！");
            }
        }
        return null;
    }

    @Override
    public ResponseEntity getZxCtOtherSupplyAgreementContractNo(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement) {
        if (StrUtil.isEmpty(zxCtOtherSupplyAgreement.getContractNo())) {
            return repEntity.layerMessage("no", "合同管理的合同编号不能为空！");
        }
        // 合同编号
        String contractNo = zxCtOtherSupplyAgreement.getContractNo();
        // 补充协议编号的顺序号
        List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByContractNo(contractNo);
        String initSerialNumber = String.format("%02d", zxCtOtherSupplyAgreementList.size()+1);

        ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement1 = new ZxCtOtherSupplyAgreement();
        zxCtOtherSupplyAgreement1.setContractNo(contractNo+"-补"+initSerialNumber);
        return repEntity.ok(zxCtOtherSupplyAgreement1);
    }

    @Override
    public void exportZxCtOtherSupplyAgreement(ZxCtOtherSupplyAgreement zxCtOtherSupplyAgreement, HttpServletResponse response) {
        if (zxCtOtherSupplyAgreement == null) {
            zxCtOtherSupplyAgreement = new ZxCtOtherSupplyAgreement();
        }
        // 获取数据
        List<ZxCtOtherSupplyAgreement> zxCtOtherSupplyAgreementList = zxCtOtherSupplyAgreementMapper.selectByZxCtOtherSupplyAgreementList(zxCtOtherSupplyAgreement);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("补充协议编号",
                "补充协议名称",
                "合同签订人",
                "含税合同金额（万元）",
                "本期含税变更增减金额（万元）",
                "变更后含税合同金额（万元）",
                "开工日期",
                "竣工日期",
                "发起人",
                "评审状态"
        );
        rowsList.add(row1);

        // 数据装载（与上面的表头对应）
        if (zxCtOtherSupplyAgreementList != null && zxCtOtherSupplyAgreementList.size() > 0) {
            for (ZxCtOtherSupplyAgreement dbZxCtOtherSupplyAgreement : zxCtOtherSupplyAgreementList) {
                rowsList.add(CollUtil.newArrayList(dbZxCtOtherSupplyAgreement.getContractNo(),
                        dbZxCtOtherSupplyAgreement.getSupplyAgreementName(),
                        dbZxCtOtherSupplyAgreement.getAgent(),
                        dbZxCtOtherSupplyAgreement.getContractCost(),
                        dbZxCtOtherSupplyAgreement.getApplyAmount(),
                        dbZxCtOtherSupplyAgreement.getAlterContractSum(),
                        dbZxCtOtherSupplyAgreement.getStartDate(),
                        dbZxCtOtherSupplyAgreement.getEndDate(),
                        dbZxCtOtherSupplyAgreement.getBeginPer(),
                        dbZxCtOtherSupplyAgreement.getApih5FlowStatus()
                        )
                );
            }

            // 报表名称
            //String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            //String fileName = "结算单报表-" + datestr + ".xlsx";
            List<List<?>> rows = CollUtil.newArrayList(rowsList);

            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // 设置response下载弹窗
            // response.reset();
            //response为HttpServletResponse对象
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("其他合同补充协议报表".getBytes("utf-8"), "iso-8859-1") + "\"");
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
        }
    }

}
