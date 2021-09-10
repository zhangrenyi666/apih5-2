package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSkWornOutItemMapper;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkWornOutMapper;
import com.apih5.service.ZxSkWornOutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkWornOutService")
public class ZxSkWornOutServiceImpl implements ZxSkWornOutService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkWornOutMapper zxSkWornOutMapper;

    @Autowired(required = true)
    private ZxSkWornOutItemMapper zxSkWornOutItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSkWornOutListByCondition(ZxSkWornOut zxSkWornOut) {
        if (zxSkWornOut == null) {
            zxSkWornOut = new ZxSkWornOut();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkWornOut.setComID("");
            zxSkWornOut.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkWornOut.setComID(zxSkWornOut.getOrgID());
            zxSkWornOut.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkWornOut.setOrgID(zxSkWornOut.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSkWornOut.getPage(),zxSkWornOut.getLimit());
        // 获取数据
        List<ZxSkWornOut> zxSkWornOutList = zxSkWornOutMapper.selectByZxSkWornOutList(zxSkWornOut);
        //查询明细
        for (ZxSkWornOut zxSkWornOut1 : zxSkWornOutList) {
            ZxSkWornOutItem zxSkWornOutItem = new ZxSkWornOutItem();
            zxSkWornOutItem.setBillID(zxSkWornOut1.getZxSkWornOutId());
            List<ZxSkWornOutItem> zxSkWornOutItems = zxSkWornOutItemMapper.selectByZxSkWornOutItemList(zxSkWornOutItem);
            zxSkWornOut1.setZxSkWornOutItemList(zxSkWornOutItems);
        }
        // 得到分页信息
        PageInfo<ZxSkWornOut> p = new PageInfo<>(zxSkWornOutList);

        return repEntity.okList(zxSkWornOutList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkWornOutDetail(ZxSkWornOut zxSkWornOut) {
        if (zxSkWornOut == null) {
            zxSkWornOut = new ZxSkWornOut();
        }
        ZxErpFile zxErpFile = new ZxErpFile();


        if(StrUtil.isNotEmpty(zxSkWornOut.getZxSkWornOutId())){
            zxSkWornOut = zxSkWornOutMapper.selectByPrimaryKey(zxSkWornOut.getZxSkWornOutId());
        }else if(StrUtil.isNotEmpty(zxSkWornOut.getWorkId())){
            List<ZxSkWornOut> zxSkWornOuts = zxSkWornOutMapper.selectByZxSkWornOutList(zxSkWornOut);
            if(zxSkWornOuts != null && zxSkWornOuts.size() >0){
                zxSkWornOut = zxSkWornOuts.get(0);
            }
        }
        // 获取数据
        ZxSkWornOut dbZxSkWornOut = zxSkWornOutMapper.selectByPrimaryKey(zxSkWornOut.getZxSkWornOutId());
        // 数据存在
        if (dbZxSkWornOut != null) {
            ZxSkWornOutItem zxSkWornOutItem = new ZxSkWornOutItem();
            zxSkWornOutItem.setBillID(dbZxSkWornOut.getZxSkWornOutId());
            List<ZxSkWornOutItem> zxSkWornOutItems = zxSkWornOutItemMapper.selectByZxSkWornOutItemList(zxSkWornOutItem);
            dbZxSkWornOut.setZxSkWornOutItemList(zxSkWornOutItems);

            zxErpFile.setOtherId(zxSkWornOut.getZxSkWornOutId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            //附件
            dbZxSkWornOut.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkWornOut);


        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkWornOut(ZxSkWornOut zxSkWornOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkWornOut.setZxSkWornOutId(UuidUtil.generate());
        zxSkWornOut.setCreateUserInfo(userKey, realName);
        //新增-1
        zxSkWornOut.setApih5FlowStatus("-1");
        //创建明细
        List<ZxSkWornOutItem> zxSkWornOutItemList = zxSkWornOut.getZxSkWornOutItemList();
        if (zxSkWornOutItemList != null && zxSkWornOutItemList.size() > 0) {
            for (ZxSkWornOutItem zxSkWornOutItem : zxSkWornOutItemList) {
                zxSkWornOutItem.setBillID(zxSkWornOut.getZxSkWornOutId());
                zxSkWornOutItem.setZxSkWornOutItemId((UuidUtil.generate()));
                zxSkWornOutItem.setCreateUserInfo(userKey, realName);
                zxSkWornOutItemMapper.insert(zxSkWornOutItem);
            }
        }
        int flag = zxSkWornOutMapper.insert(zxSkWornOut);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkWornOut);
        }
    }

    @Override
    public ResponseEntity updateZxSkWornOut(ZxSkWornOut zxSkWornOut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkWornOut dbZxSkWornOut = zxSkWornOutMapper.selectByPrimaryKey(zxSkWornOut.getZxSkWornOutId());
        if (dbZxSkWornOut != null && StrUtil.isNotEmpty(dbZxSkWornOut.getZxSkWornOutId()) && (StrUtil.equals(zxSkWornOut.getApih5FlowStatus(),"-1") || StrUtil.equals(zxSkWornOut.getApih5FlowStatus(),"0"))) {
            if(StrUtil.equals(zxSkWornOut.getApih5FlowStatus(),"0")){
                // 流程id
                dbZxSkWornOut.setWorkId(zxSkWornOut.getWorkId());
                // 流程状态
                dbZxSkWornOut.setApih5FlowStatus(zxSkWornOut.getApih5FlowStatus());
            }
           // 单据编号
           dbZxSkWornOut.setBillNo(zxSkWornOut.getBillNo());
           // 项目名称
           dbZxSkWornOut.setOrgName(zxSkWornOut.getOrgName());
           // 项目名称
           dbZxSkWornOut.setOrgID(zxSkWornOut.getOrgID());
           // 申请单位
           dbZxSkWornOut.setApplyOrgName(zxSkWornOut.getApplyOrgName());
           // 申请单位ID
           dbZxSkWornOut.setApplyOrgID(zxSkWornOut.getApplyOrgID());
           // 拟处理金额（万元）
           dbZxSkWornOut.setPurchaseAmt(zxSkWornOut.getPurchaseAmt());
           // 废旧物资处理说明
           dbZxSkWornOut.setApproval(zxSkWornOut.getApproval());
           // 发起人
           dbZxSkWornOut.setBeginPer(zxSkWornOut.getBeginPer());
           // 评审状态
           dbZxSkWornOut.setAuditStatus(zxSkWornOut.getAuditStatus());
           // 流程实例ID
           dbZxSkWornOut.setInstProcessId(zxSkWornOut.getInstProcessId());
           // 公文任务ID
           dbZxSkWornOut.setWorkitemID(zxSkWornOut.getWorkitemID());
           // 是否局审批
           dbZxSkWornOut.setIsFlag(zxSkWornOut.getIsFlag());
           // 是否局指审批
           dbZxSkWornOut.setIsFlagZhb(zxSkWornOut.getIsFlagZhb());
           // 发送局判断ID
           dbZxSkWornOut.setSendToJuID(zxSkWornOut.getSendToJuID());
           // 发送局指判断ID
           dbZxSkWornOut.setSendToZhbID(zxSkWornOut.getSendToZhbID());
           // flowBeginDate
           dbZxSkWornOut.setFlowBeginDate(zxSkWornOut.getFlowBeginDate());
           // flowEndDate
           dbZxSkWornOut.setFlowEndDate(zxSkWornOut.getFlowEndDate());
           // isReport
           dbZxSkWornOut.setIsReport(zxSkWornOut.getIsReport());
           // isReportZhb
           dbZxSkWornOut.setIsReportZhb(zxSkWornOut.getIsReportZhb());
           // appInsHistID
           dbZxSkWornOut.setAppInsHistID(zxSkWornOut.getAppInsHistID());
           // appInsHistIDZhb
           dbZxSkWornOut.setAppInsHistIDZhb(zxSkWornOut.getAppInsHistIDZhb());
           // comID
           dbZxSkWornOut.setComID(zxSkWornOut.getComID());
           // 公司名称
           dbZxSkWornOut.setComName(zxSkWornOut.getComName());
           // comOrders
           dbZxSkWornOut.setComOrders(zxSkWornOut.getComOrders());
           // 填报日期
           dbZxSkWornOut.setReportDate(zxSkWornOut.getReportDate());

           // 备注
           dbZxSkWornOut.setRemarks(zxSkWornOut.getRemarks());
           // 排序
           dbZxSkWornOut.setSort(zxSkWornOut.getSort());
           // 共通
           dbZxSkWornOut.setModifyUserInfo(userKey, realName);
            //删除在新增
            ZxSkWornOutItem zxSkWornOutItem = new ZxSkWornOutItem();
            zxSkWornOutItem.setBillID(zxSkWornOut.getZxSkWornOutId());
            List<ZxSkWornOutItem> zxSkWornOutItems = zxSkWornOutItemMapper.selectByZxSkWornOutItemList(zxSkWornOutItem);
            if (zxSkWornOutItems != null && zxSkWornOutItems.size() > 0) {
                zxSkWornOutItem.setModifyUserInfo(userKey, realName);
                zxSkWornOutItemMapper.batchDeleteUpdateZxSkWornOutItem(zxSkWornOutItems, zxSkWornOutItem);
            }
            //明细list
            List<ZxSkWornOutItem> zxSkWornOutItemList = zxSkWornOut.getZxSkWornOutItemList();
            if (zxSkWornOutItemList != null && zxSkWornOutItemList.size() > 0) {
                for (ZxSkWornOutItem zxSkWornOutItem1 : zxSkWornOutItemList) {
                    zxSkWornOutItem1.setZxSkWornOutItemId(UuidUtil.generate());
                    zxSkWornOutItem1.setBillID(zxSkWornOut.getZxSkWornOutId());
                    zxSkWornOutItem1.setCreateUserInfo(userKey, realName);
                    zxSkWornOutItemMapper.insert(zxSkWornOutItem1);
                }
            }
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkWornOut.getZxSkWornOutId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkWornOut.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkWornOut.getZxSkWornOutId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
           flag = zxSkWornOutMapper.updateByPrimaryKey(dbZxSkWornOut);
        }else {
            // 流程id
            dbZxSkWornOut.setWorkId(zxSkWornOut.getWorkId());
            // 流程状态
            dbZxSkWornOut.setApih5FlowStatus(zxSkWornOut.getApih5FlowStatus());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField1(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField1()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField2", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField2(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField2()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField3", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField3(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField3()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField4", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField4(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField4()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField5", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField5(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField5()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField6", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField6(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField6()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField7", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField7(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField7()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField8", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField8(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField8()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField9", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField9(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField9()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField10", zxSkWornOut.getOpinionField(), true)){
                dbZxSkWornOut.setOpinionField10(zxSkWornOut.getOpinionContent(realName, dbZxSkWornOut.getOpinionField10()));
            }

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkWornOut.getZxSkWornOutId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkWornOut.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkWornOut.getZxSkWornOutId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            // 共通
            dbZxSkWornOut.setModifyUserInfo(userKey, realName);
            flag = zxSkWornOutMapper.updateByPrimaryKey(dbZxSkWornOut);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkWornOut);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkWornOut(List<ZxSkWornOut> zxSkWornOutList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxSkWornOutList != null && zxSkWornOutList.size() > 0) {
            for (ZxSkWornOut zxSkWornOut : zxSkWornOutList) {
                // 删除明细
                ZxSkWornOutItem zxSkWornOutItem = new ZxSkWornOutItem();
                zxSkWornOutItem.setBillID(zxSkWornOut.getZxSkWornOutId());
                List<ZxSkWornOutItem> zxSkWornOutItems = zxSkWornOutItemMapper.selectByZxSkWornOutItemList(zxSkWornOutItem);
                if (zxSkWornOutItems != null && zxSkWornOutItems.size() > 0) {
                    zxSkWornOutItem.setModifyUserInfo(userKey, realName);
                    zxSkWornOutItemMapper.batchDeleteUpdateZxSkWornOutItem(zxSkWornOutItems, zxSkWornOutItem);
                }
                if(StrUtil.isNotEmpty(zxSkWornOut.getWorkId())) {
                    jsonArr.add(zxSkWornOut.getWorkId());
                }
            }
           ZxSkWornOut zxSkWornOut = new ZxSkWornOut();
           zxSkWornOut.setModifyUserInfo(userKey, realName);
           flag = zxSkWornOutMapper.batchDeleteUpdateZxSkWornOut(zxSkWornOutList, zxSkWornOut);
        }
        //删除流程后台数据
        if(jsonArr.size()>0) {
            HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkWornOutList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //规则：合同编号+CL+顺序号，顺序号每个项目均从001开始，依次顺延。
    @Override
    public ResponseEntity getZxSkWornOutNo(ZxSkWornOut zxSkWornOut) {
        if(StrUtil.isEmpty(zxSkWornOut.getOrgID())){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        zxCtContract.setOrgID(zxSkWornOut.getOrgID());
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        //业主合同编号
        String contractNo = zxCtContracts.get(0).getContractNo();

        String str = String.valueOf(zxSkWornOutMapper.getZxSkWornOutCount(zxSkWornOut.getOrgID()));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + "-" + "CL" + "-" + str + " 号";
        return repEntity.ok(no);
    }


}
