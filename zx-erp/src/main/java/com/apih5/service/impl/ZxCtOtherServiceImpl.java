package com.apih5.service.impl;

import java.io.File;
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
import cn.hutool.core.io.FileUtil;
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
import flex.messaging.util.URLEncoder;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxCtOtherService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtOtherService")
public class ZxCtOtherServiceImpl implements ZxCtOtherService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherMapper zxCtOtherMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxCtOtherWorksMapper zxCtOtherWorksMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateMapper zxCtOtherManageAmtRateMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateServiceImpl zxCtOtherManageAmtRateServiceImpl;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Override
    public ResponseEntity getZxCtOtherListByCondition(ZxCtOther zxCtOther) {
        if (zxCtOther == null) {
            zxCtOther = new ZxCtOther();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtOther.setCompanyId("");
            zxCtOther.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCtOther.setCompanyId(zxCtOther.getOrgId());
            zxCtOther.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCtOther.setOrgId(zxCtOther.getOrgId());
        }
        // 分页查询
        PageHelper.startPage(zxCtOther.getPage(),zxCtOther.getLimit());
        // 获取数据
        List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByZxCtOtherList(zxCtOther);

        //查询附件
        for (ZxCtOther other : zxCtOtherList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(other.getZxCtOtherId());
            zxErpFile.setOtherType("0");
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            other.setZxErpFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxCtOther> p = new PageInfo<>(zxCtOtherList);
        return repEntity.okList(zxCtOtherList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherDetail(ZxCtOther zxCtOther) {
        if (zxCtOther == null) {
            zxCtOther = new ZxCtOther();
        }
        ZxCtOther dbZxCtOther = new ZxCtOther();
        if(StrUtil.isNotEmpty(zxCtOther.getWorkId())) {
            ZxCtOther zxCtOther1 = new ZxCtOther();
            zxCtOther1.setWorkId(zxCtOther.getWorkId());
            List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByZxCtOtherList(zxCtOther1);
            if(zxCtOtherList != null && zxCtOtherList.size() >0) {
                dbZxCtOther = zxCtOtherList.get(0);
            }
        }else {
            // 获取数据
            dbZxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOther.getZxCtOtherId());
        }
        // 数据存在
        if (dbZxCtOther != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxCtOther.getZxCtOtherId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            List<ZxErpFile> fj = new ArrayList<>();
            List<ZxErpFile> zw = new ArrayList<>();
            zxErpFiles.forEach(f -> {
                // 0 附件 1 正文
                if ("0".equals(f.getOtherType())) {
                    fj.add(f);
                } else if ("1".equals(f.getOtherType())) {
                    zw.add(f);
                }
            });
            dbZxCtOther.setZxErpFileList(fj);
            dbZxCtOther.setZxErpMainFileList(zw);
            return repEntity.ok(dbZxCtOther);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtOther(ZxCtOther zxCtOther) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 合同编码不能为空
        if (StrUtil.isEmpty(zxCtOther.getContractCode())) {
            return repEntity.layerMessage("no", "合同编码不能为空,对应业主台账的contractNo！");
        }
        zxCtOther.setContractCode(zxCtOther.getContractCode());
        // 管理机构id 和甲方id相同
        zxCtOther.setOrgId(zxCtOther.getFirstId());
        // 管理机构名称 和甲方名称相同
        zxCtOther.setOrgName(zxCtOther.getFirstName());
        // 评审状态设置为未评审
        zxCtOther.setApih5FlowStatus("-1");
        // 发起人设置为当前登录用户
        zxCtOther.setBeginPer(realName);
        zxCtOther.setZxCtOtherId(UuidUtil.generate());
        zxCtOther.setCreateUserInfo(userKey, realName);
        int flag = zxCtOtherMapper.insert(zxCtOther);
        //添加附件
        List<ZxErpFile> fileList = zxCtOther.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOther);
        }
    }

    @Override
    public ResponseEntity updateZxCtOther(ZxCtOther zxCtOther) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOther dbZxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOther.getZxCtOtherId());
        if (dbZxCtOther != null && StrUtil.isNotEmpty(dbZxCtOther.getZxCtOtherId())) {
            // 合同名称
            dbZxCtOther.setContractName(zxCtOther.getContractName());
            // 乙方id
            dbZxCtOther.setSecondId(zxCtOther.getSecondId());
            // 乙方名称
            dbZxCtOther.setSecondName(zxCtOther.getSecondName());
            // 合同签定人
            dbZxCtOther.setAgent(zxCtOther.getAgent());
            // 合同工期
            dbZxCtOther.setCsTimeLimit(zxCtOther.getCsTimeLimit());
            // 合同内容
            dbZxCtOther.setContent(zxCtOther.getContent());
            // 合同类别
            dbZxCtOther.setContractCategory(zxCtOther.getContractCategory());
            // 是否抵扣
            dbZxCtOther.setIsDeduct(zxCtOther.getIsDeduct());
            // 备注
            dbZxCtOther.setRemark(zxCtOther.getRemark());
            // workId
            dbZxCtOther.setWorkId(zxCtOther.getWorkId());
            // 审核状态
            dbZxCtOther.setApih5FlowStatus(zxCtOther.getApih5FlowStatus());
            // 审核节点状态
            dbZxCtOther.setApih5FlowNodeStatus(zxCtOther.getApih5FlowNodeStatus());
            // 流程id
            dbZxCtOther.setApih5FlowId(zxCtOther.getApih5FlowId());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField1(getOpinionContent(realName, dbZxCtOther.getOpinionField1(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField2", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField2(getOpinionContent(realName, dbZxCtOther.getOpinionField2(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField3", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField3(getOpinionContent(realName, dbZxCtOther.getOpinionField3(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField4", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField4(getOpinionContent(realName, dbZxCtOther.getOpinionField4(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField5", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField5(getOpinionContent(realName, dbZxCtOther.getOpinionField5(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField6", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField6(getOpinionContent(realName, dbZxCtOther.getOpinionField6(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField7", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField7(getOpinionContent(realName, dbZxCtOther.getOpinionField7(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField8", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField8(getOpinionContent(realName, dbZxCtOther.getOpinionField8(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField9", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField9(getOpinionContent(realName, dbZxCtOther.getOpinionField9(), zxCtOther.getOpinionContent()));
            }
            //
            if(StrUtil.equals("opinionField10", zxCtOther.getOpinionField(), true)){
                dbZxCtOther.setOpinionField10(getOpinionContent(realName, dbZxCtOther.getOpinionField10(), zxCtOther.getOpinionContent()));
            }
            // 共通
            dbZxCtOther.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherMapper.updateByPrimaryKey(dbZxCtOther);

            // 修改合同评审清单明细表的是否抵扣
            ZxCtOtherWorks dbZxCtOtherWorks = new ZxCtOtherWorks();
            dbZxCtOtherWorks.setZxCtOtherId(dbZxCtOther.getZxCtOtherId());
            List<ZxCtOtherWorks> dbZxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(dbZxCtOtherWorks);
            if (dbZxCtOtherWorksList != null && dbZxCtOtherWorksList.size() > 0) {
                for(ZxCtOtherWorks zxCtOtherWorks : dbZxCtOtherWorksList) {
                    zxCtOtherWorks.setIsDeduct(dbZxCtOther.getIsDeduct());
                    zxCtOtherWorksMapper.updateByPrimaryKey(zxCtOtherWorks);
                }
            }
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxCtOther.getZxCtOtherId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //添加附件 0附件 1正文
            //明细list
            List<ZxErpFile> fileList = zxCtOther.getZxErpFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
                    zxErpFile.setOtherType("0");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            List<ZxErpFile> zhengWenList = zxCtOther.getZxErpMainFileList();
            if(zhengWenList != null && zhengWenList.size()>0) {
                for(ZxErpFile zxErpFile : zhengWenList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            // 评审通过
            if(StrUtil.equals(zxCtOther.getApih5FlowStatus(), "2")) {
                this.zxCtOtherReviewApply(zxCtOther);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOther);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOther(List<ZxCtOther> zxCtOtherList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zxCtOtherList != null && zxCtOtherList.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (ZxCtOther zxCtOther : zxCtOtherList) {
                //删除明细
                ZxCtOtherWorks delZxCtOtherWorks = new ZxCtOtherWorks();
                delZxCtOtherWorks.setZxCtOtherId(zxCtOther.getZxCtOtherId());
                List<ZxCtOtherWorks> delWorkList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(delZxCtOtherWorks);
                if(delWorkList != null && delWorkList.size() > 0) {
                    delZxCtOtherWorks.setModifyUserInfo(userKey, realName);
                    zxCtOtherWorksMapper.batchDeleteUpdateZxCtOtherWorks(delWorkList, delZxCtOtherWorks);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                if(StrUtil.isNotEmpty(zxCtOther.getWorkId())) {
                    jsonArray.add(zxCtOther.getWorkId());
                }
            }
            ZxCtOther zxCtOther = new ZxCtOther();
            zxCtOther.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherMapper.batchDeleteUpdateZxCtOther(zxCtOtherList, zxCtOther);
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
            return repEntity.ok("sys.data.delete",zxCtOtherList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity zxCtOtherReviewApplyCheck(ZxCtOther zxCtOther) {
        ZxCtOther dbZxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOther.getZxCtOtherId());
        if (dbZxCtOther != null && StrUtil.isNotEmpty(dbZxCtOther.getZxCtOtherId())) {
            if (dbZxCtOther != null && StrUtil.isNotEmpty(dbZxCtOther.getZxCtOtherId())) {
                // 查询当前合同评审清单明细是否有数据，无数据提示清单未编辑不能发起评审
                ZxCtOtherWorks dbZxCtOtherWorks = new ZxCtOtherWorks();
                dbZxCtOtherWorks.setZxCtOtherId(dbZxCtOther.getZxCtOtherId());
                List<ZxCtOtherWorks> dbZxCtOtherWorksList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(dbZxCtOtherWorks);
                if (dbZxCtOtherWorksList == null || dbZxCtOtherWorksList.size() == 0) {
                    return repEntity.layerMessage("no", "清单未编辑不能发起评审！");
                }
            }
        }
        return null;
    }

    @Override
    public ResponseEntity zxCtOtherReviewApply(ZxCtOther zxCtOther) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOther dbZxCtOther = zxCtOtherMapper.selectByPrimaryKey(zxCtOther.getZxCtOtherId());
        if (dbZxCtOther != null && StrUtil.isNotEmpty(dbZxCtOther.getZxCtOtherId())) {
            // 评审通过后修改合同评审表的评审状态
            dbZxCtOther.setApih5FlowStatus("2");
            dbZxCtOther.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherMapper.updateByPrimaryKey(dbZxCtOther);
            //评审通过生成一条对应的其他合同管理数据
            ZxCtOtherManage zxCtOtherManage = new ZxCtOtherManage();
            BeanUtil.copyProperties(dbZxCtOther, zxCtOtherManage);
            // 设置合同评审ID（旧系统字段fromApplyId），合同评审表主键zxCtOtherId是合同管理的合同评审ID 字段zxCtOtherId
            zxCtOtherManage.setZxCtOtherId(dbZxCtOther.getZxCtOtherId());
            // 签订日期  对应评审通过日期
            zxCtOtherManage.setSignatureDate(dbZxCtOther.getModifyTime());
            zxCtOtherManage.setFirstId(dbZxCtOther.getFirstId());
            zxCtOtherManage.setOrgId(dbZxCtOther.getFirstId());
            // 乙方性质 合同评审的合同签订人字段也是agent，所以copyProperties之后数据过来了，其实是两个字段
            zxCtOtherManage.setAgent("");
            // 乙方代表 对应合同评审里的合同签订人
            zxCtOtherManage.setSecondPrincipal(dbZxCtOther.getAgent());
            // 合同录入类型
            zxCtOtherManage.setAuditStatus("auditPassed");
            // 合同状态设置执行中
            zxCtOtherManage.setContractStatus("1");
            // 结算情况设置实际无结算
            zxCtOtherManage.setSettleType("实际无结算");
            // 累计结算金额设置为0
            zxCtOtherManage.setTotalSettleAmount(new BigDecimal(0));
            zxCtOtherManage.setZxCtOtherManageId(UuidUtil.generate());
            zxCtOtherManage.setCreateUserInfo(userKey, realName);
            flag = zxCtOtherManageMapper.insert(zxCtOtherManage);

            //评审通过后修改对应的合同清单明细，将合同管理表的主键zxCtOtherManageId 赋值给合同清单明细的合同管理外键zxCtOtherManageId
            ZxCtOtherWorks otherWorks = new ZxCtOtherWorks();
            otherWorks.setZxCtOtherId(zxCtOther.getZxCtOtherId());
            List<ZxCtOtherWorks> workList = zxCtOtherWorksMapper.selectByZxCtOtherWorksList(otherWorks);
            if (workList != null && workList.size() > 0) {
                for (ZxCtOtherWorks work : workList) {
                    work.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
                    work.setModifyUserInfo(userKey, realName);
                    flag = zxCtOtherWorksMapper.updateByPrimaryKey(work);
                }
            }

            // 合同管理附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCtOther.getZxCtOtherId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                for (ZxErpFile file : zxErpFiles) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtOtherManage.getZxCtOtherManageId());
                    file.setModifyUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }

            // 添加保证金数据，初始化其他合同管理的保证金
            ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManage.getZxCtOtherManageId());
            ZxCtOtherManageAmtRate zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
            zxCtOtherManageAmtRate.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
            List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = (List<ZxCtOtherManageAmtRate>) zxCtOtherManageAmtRateServiceImpl.getZxCtOtherManageAmtRateByContractId(zxCtOtherManageAmtRate).getData();
            if (zxCtOtherManageAmtRateList != null && zxCtOtherManageAmtRateList.size() > 0) {
                for (ZxCtOtherManageAmtRate zxCtOtherManageAmtRate1 : zxCtOtherManageAmtRateList) {
                    BeanUtil.copyProperties(dbZxCtOtherManage, zxCtOtherManageAmtRate1);
                    zxCtOtherManageAmtRate1.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
                    zxCtOtherManageAmtRate1.setAllowDelete("N");
                    zxCtOtherManageAmtRate1.setUseCount(new BigDecimal(0));
                    zxCtOtherManageAmtRate1.setZxCtOtherManageAmtRateId(UuidUtil.generate());
                    zxCtOtherManageAmtRate1.setCreateUserInfo(userKey, realName);
                    flag = zxCtOtherManageAmtRateMapper.insert(zxCtOtherManageAmtRate1);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOther);
        }
    }

    @Override
    public ResponseEntity getZxCtOtherContractNo(ZxCtOther zxCtOther) {
        if (StrUtil.isEmpty(zxCtOther.getFirstId())) {
            return repEntity.layerMessage("no", "甲方编号不能为空！");
        }
        if (StrUtil.isEmpty(zxCtOther.getContractCategory())) {
            return repEntity.layerMessage("no", "合同类别不能为空！");
        }
        // 甲方编号
        String firstNo = zxCtOther.getFirstId();
        // 合同类别
        String contractCategory = zxCtOther.getContractCategory();
        ZxCtOther dbZxCtOther = new ZxCtOther();
        // 合同编码 对应业主台账的contractNo 前台把contractNo通过FirstId传过来
        dbZxCtOther.setContractCode(firstNo);
        dbZxCtOther.setContractCategory(contractCategory);
        // 初始化顺序号
        List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByContractCodeAndCategory(dbZxCtOther);
        String initSerialNumber = String.format("%03d", zxCtOtherList.size()+1);
        // 合同编号
        ZxCtOther zxCtOther1 = new ZxCtOther();
        zxCtOther1.setContractNo(firstNo+"-"+contractCategory+"-"+initSerialNumber);
        return repEntity.ok(zxCtOther1);
    }

    @Override
    public void exportZxCtOther(ZxCtOther zxCtOther, HttpServletResponse response) {
        if (zxCtOther == null) {
            zxCtOther = new ZxCtOther();
        }
        // 获取数据
        List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByZxCtOtherList(zxCtOther);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("合同编号",
                "合同名称",
                "甲方名称",
                "乙方名称",
                "协作单位类型",
                "合同签订人",
                "含税合同金额（万元）",
                "是否抵扣",
                "合同工期",
                "合同类别",
                "发起人",
                "评审状态"
        );
        rowsList.add(row1);

        // 数据装载（与上面的表头对应）
        if (zxCtOtherList != null && zxCtOtherList.size() > 0) {
            for (ZxCtOther dbZxCtOther : zxCtOtherList) {
                rowsList.add(CollUtil.newArrayList(dbZxCtOther.getContractNo(),
                        dbZxCtOther.getContractName(),
                        dbZxCtOther.getFirstName(),
                        dbZxCtOther.getSecondName(),
                        dbZxCtOther.getSecondOrgType(),
                        dbZxCtOther.getAgent(),
                        dbZxCtOther.getContractCost(),
                        dbZxCtOther.getIsDeduct(),
                        dbZxCtOther.getCsTimeLimit(),
                        dbZxCtOther.getContractCategory(),
                        dbZxCtOther.getBeginPer(),
                        dbZxCtOther.getApih5FlowStatus()
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
                        "attachment; filename=\"" + new String("其他合同评审报表".getBytes("utf-8"), "iso-8859-1") + "\"");
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

    @Override
    public void downLoadZxCtOtherFile(ZxCtOther zxCtOther, HttpServletResponse response) {
        if (zxCtOther.getZxErpFileList() != null && zxCtOther.getZxErpFileList().size() > 0) {
            for (ZxErpFile zxErpFile : zxCtOther.getZxErpFileList()) {
                // 从请求中获取文件名
                String fileName = zxErpFile.getName();
                if(StringUtil.isNotEmpty(fileName)){
                    //获取文件路径
                    String path = Apih5Properties.getFilePath() + zxErpFile.getRelativeUrl();
                    File file = new File(path);
                    //文件存在
                    if(file.exists()) {
                        // 设置返回内容格式
                        response.setContentType("application/octet-stream");
                        // out为OutputStream，需要写出到的目标流
                        ServletOutputStream outputStream = null;
                        try {
                            // 创建输出流对象
                            outputStream = response.getOutputStream();

                            //以字节数组的形式读取文件
                            byte[] bytes = FileUtil.readBytes(file);

                            // 设置下载弹窗的文件名和格式（文件名要包括名字和文件格式）
                            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

                            // 返回数据到输出流对象中
                            outputStream.write(bytes);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            // 此处记得关闭输出Servlet流
                            if (outputStream != null) {
                                // 关闭流对象
                                IoUtil.close(outputStream);
                            }
                        }
                    }
                }

            }
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

}
