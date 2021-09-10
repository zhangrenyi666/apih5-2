package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSkTurnOverScrapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnOverScrapService")
public class ZxSkTurnOverScrapServiceImpl implements ZxSkTurnOverScrapService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnOverScrapMapper zxSkTurnOverScrapMapper;

    @Autowired(required = true)
    private ZxSkTurnOverScrapItemMapper zxSkTurnOverScrapItemMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Autowired(required = true)
    private ZxSkTurnOverStockMapper zxSkTurnOverStockMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;


    @Override
    public ResponseEntity getZxSkTurnOverScrapListByCondition(ZxSkTurnOverScrap zxSkTurnOverScrap) {
        if (zxSkTurnOverScrap == null) {
            zxSkTurnOverScrap = new ZxSkTurnOverScrap();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkTurnOverScrap.setCompanyId("");
            zxSkTurnOverScrap.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkTurnOverScrap.setCompanyId(zxSkTurnOverScrap.getOrgID());
            zxSkTurnOverScrap.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkTurnOverScrap.setOrgID(zxSkTurnOverScrap.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverScrap.getPage(),zxSkTurnOverScrap.getLimit());
        // 获取数据
        List<ZxSkTurnOverScrap> zxSkTurnOverScrapList = zxSkTurnOverScrapMapper.selectByZxSkTurnOverScrapList(zxSkTurnOverScrap);
        //查询明细
        for (ZxSkTurnOverScrap zxSkTurnOverScrap1 : zxSkTurnOverScrapList) {
            ZxSkTurnOverScrapItem zxSkTurnOverScrapItem = new ZxSkTurnOverScrapItem();
            zxSkTurnOverScrapItem.setBillID(zxSkTurnOverScrap1.getId());
            List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItems = zxSkTurnOverScrapItemMapper.selectByZxSkTurnOverScrapItemList(zxSkTurnOverScrapItem);
            zxSkTurnOverScrap1.setZxSkTurnOverScrapItemList(zxSkTurnOverScrapItems);
        }
        // 得到分页信息
        PageInfo<ZxSkTurnOverScrap> p = new PageInfo<>(zxSkTurnOverScrapList);
        return repEntity.okList(zxSkTurnOverScrapList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnOverScrapDetail(ZxSkTurnOverScrap zxSkTurnOverScrap) {
        if (zxSkTurnOverScrap == null) {
            zxSkTurnOverScrap = new ZxSkTurnOverScrap();
        }
        ZxErpFile zxErpFile = new ZxErpFile();
        //附件

        if(StrUtil.isNotEmpty(zxSkTurnOverScrap.getId())){
            zxSkTurnOverScrap = zxSkTurnOverScrapMapper.selectByPrimaryKey(zxSkTurnOverScrap.getId());
        }else if(StrUtil.isNotEmpty(zxSkTurnOverScrap.getWorkId())){
            List<ZxSkTurnOverScrap> zxSkTurnOverScraps = zxSkTurnOverScrapMapper.selectByZxSkTurnOverScrapList(zxSkTurnOverScrap);
            if(zxSkTurnOverScraps != null && zxSkTurnOverScraps.size() >0){
                zxSkTurnOverScrap = zxSkTurnOverScraps.get(0);
            }
        }
        // 获取数据
//        ZxSkTurnOverScrap dbZxSkTurnOverScrap = zxSkTurnOverScrapMapper.selectByPrimaryKey(zxSkTurnOverScrap.getId());
        // 数据存在
        if (zxSkTurnOverScrap != null) {
            ZxSkTurnOverScrapItem zxSkTurnOverScrapItem = new ZxSkTurnOverScrapItem();
            zxSkTurnOverScrapItem.setBillID(zxSkTurnOverScrap.getId());
            List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItems = zxSkTurnOverScrapItemMapper.selectByZxSkTurnOverScrapItemList(zxSkTurnOverScrapItem);
            zxSkTurnOverScrap.setZxSkTurnOverScrapItemList(zxSkTurnOverScrapItems);

            zxErpFile.setOtherId(zxSkTurnOverScrap.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkTurnOverScrap.setFileList(zxErpFiles);
            return repEntity.ok(zxSkTurnOverScrap);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnOverScrap(ZxSkTurnOverScrap zxSkTurnOverScrap) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnOverScrap.setId(UuidUtil.generate());
        zxSkTurnOverScrap.setCreateUserInfo(userKey, realName);
        //新增-1
        zxSkTurnOverScrap.setApih5FlowStatus("-1");
        //创建明细
        List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItemList = zxSkTurnOverScrap.getZxSkTurnOverScrapItemList();
        if(zxSkTurnOverScrapItemList != null && zxSkTurnOverScrapItemList.size()>0) {
            for (ZxSkTurnOverScrapItem zxSkTurnOverScrapItem : zxSkTurnOverScrapItemList) {
                zxSkTurnOverScrapItem.setBillID(zxSkTurnOverScrap.getId());
                zxSkTurnOverScrapItem.setId((UuidUtil.generate()));
                zxSkTurnOverScrapItem.setCreateUserInfo(userKey, realName);
                zxSkTurnOverScrapItemMapper.insert(zxSkTurnOverScrapItem);
            }
        }
        int flag = zxSkTurnOverScrapMapper.insert(zxSkTurnOverScrap);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnOverScrap);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnOverScrap(ZxSkTurnOverScrap zxSkTurnOverScrap) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnOverScrap dbZxSkTurnOverScrap = zxSkTurnOverScrapMapper.selectByPrimaryKey(zxSkTurnOverScrap.getId());
        if (dbZxSkTurnOverScrap != null && StrUtil.isNotEmpty(dbZxSkTurnOverScrap.getId()) && (StrUtil.equals(zxSkTurnOverScrap.getApih5FlowStatus(),"-1") || StrUtil.equals(zxSkTurnOverScrap.getApih5FlowStatus(),"0"))) {
            if(StrUtil.equals(zxSkTurnOverScrap.getApih5FlowStatus(),"0")){
                // 流程id
                dbZxSkTurnOverScrap.setWorkId(zxSkTurnOverScrap.getWorkId());
                // 流程状态
                dbZxSkTurnOverScrap.setApih5FlowStatus(zxSkTurnOverScrap.getApih5FlowStatus());
            }
           // 报废单位ID
           dbZxSkTurnOverScrap.setOrgID(zxSkTurnOverScrap.getOrgID());
           // 报废单位
           dbZxSkTurnOverScrap.setOrgName(zxSkTurnOverScrap.getOrgName());
           // 收购单位名称
           dbZxSkTurnOverScrap.setAcceptOrgName(zxSkTurnOverScrap.getAcceptOrgName());
           // 单据编号
           dbZxSkTurnOverScrap.setBillNo(zxSkTurnOverScrap.getBillNo());
           // 日期
           dbZxSkTurnOverScrap.setBusDate(zxSkTurnOverScrap.getBusDate());
           // 经办人
           dbZxSkTurnOverScrap.setConsignee(zxSkTurnOverScrap.getConsignee());
           // 单据状态
           dbZxSkTurnOverScrap.setBillStatus(zxSkTurnOverScrap.getBillStatus());
           // 流程ID
           dbZxSkTurnOverScrap.setWorkItemID(zxSkTurnOverScrap.getWorkItemID());
           // instProcessId
           dbZxSkTurnOverScrap.setInstProcessID(zxSkTurnOverScrap.getInstProcessID());
            // 公司id
            dbZxSkTurnOverScrap.setCompanyId(zxSkTurnOverScrap.getCompanyId());
            // 公司名称
            dbZxSkTurnOverScrap.setCompanyName(zxSkTurnOverScrap.getCompanyName());
           // 备注
           dbZxSkTurnOverScrap.setRemarks(zxSkTurnOverScrap.getRemarks());
           // 排序
           dbZxSkTurnOverScrap.setSort(zxSkTurnOverScrap.getSort());
           // 共通
           dbZxSkTurnOverScrap.setModifyUserInfo(userKey, realName);
            //删除在新增
            ZxSkTurnOverScrapItem zxSkTurnOverScrapItem = new ZxSkTurnOverScrapItem();
            zxSkTurnOverScrapItem.setBillID(zxSkTurnOverScrap.getId());
            List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItems = zxSkTurnOverScrapItemMapper.selectByZxSkTurnOverScrapItemList(zxSkTurnOverScrapItem);
            if(zxSkTurnOverScrapItems != null && zxSkTurnOverScrapItems.size()>0) {
                zxSkTurnOverScrapItem.setModifyUserInfo(userKey, realName);
                zxSkTurnOverScrapItemMapper.batchDeleteUpdateZxSkTurnOverScrapItem(zxSkTurnOverScrapItems, zxSkTurnOverScrapItem);
            }
            //明细list
            List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItemList = zxSkTurnOverScrap.getZxSkTurnOverScrapItemList();
            if(zxSkTurnOverScrapItemList != null && zxSkTurnOverScrapItemList.size()>0) {
                for(ZxSkTurnOverScrapItem zxSkTurnOverScrapItem1 : zxSkTurnOverScrapItemList) {
                    zxSkTurnOverScrapItem1.setId(UuidUtil.generate());
                    zxSkTurnOverScrapItem1.setBillID(zxSkTurnOverScrap.getId());
                    zxSkTurnOverScrapItem1.setCreateUserInfo(userKey, realName);
                    zxSkTurnOverScrapItemMapper.insert(zxSkTurnOverScrapItem1);
                }
            }
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkTurnOverScrap.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkTurnOverScrap.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkTurnOverScrap.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
           flag = zxSkTurnOverScrapMapper.updateByPrimaryKey(dbZxSkTurnOverScrap);
        }else {
            // 流程id
            dbZxSkTurnOverScrap.setWorkId(zxSkTurnOverScrap.getWorkId());
            // 流程状态
            dbZxSkTurnOverScrap.setApih5FlowStatus(zxSkTurnOverScrap.getApih5FlowStatus());
            //流程的意见
            if(StrUtil.equals("opinionField1", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField1(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField1()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField2", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField2(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField2()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField3", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField3(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField3()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField4", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField4(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField4()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField5", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField5(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField5()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField6", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField6(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField6()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField7", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField7(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField7()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField8", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField8(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField8()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField9", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField9(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField9()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField10", zxSkTurnOverScrap.getOpinionField(), true)){
                dbZxSkTurnOverScrap.setOpinionField10(zxSkTurnOverScrap.getOpinionContent(realName, dbZxSkTurnOverScrap.getOpinionField10()));
            }
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkTurnOverScrap.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkTurnOverScrap.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkTurnOverScrap.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
            // 共通
            dbZxSkTurnOverScrap.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnOverScrapMapper.updateByPrimaryKey(dbZxSkTurnOverScrap);
        }
        if(StrUtil.equals(zxSkTurnOverScrap.getApih5FlowStatus(),"2")){
            //审核修改库存
            List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItemList = zxSkTurnOverScrap.getZxSkTurnOverScrapItemList();
            for (ZxSkTurnOverScrapItem zxSkTurnOverScrapItem : zxSkTurnOverScrapItemList) {
                ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
                zxSkTurnOverStock.setBatchNo(zxSkTurnOverScrapItem.getBatchNo());
                List<ZxSkTurnOverStock> zxSkTurnOverStocks = zxSkTurnOverStockMapper.selectByZxSkTurnOverStockList(zxSkTurnOverStock);
                if(zxSkTurnOverStocks!=null && zxSkTurnOverStocks.size()>0){
                    ZxSkTurnOverStock zxSkTurnOverStock1 = zxSkTurnOverStocks.get(0);
                    //库存原数量
                    BigDecimal oldNum = zxSkTurnOverStock1.getOriginalQty();
                    //处理数量
                    BigDecimal num = zxSkTurnOverScrapItem.getDiscardQty();
                    //库存数量 - 处理数量
                    zxSkTurnOverStock1.setStockQty(CalcUtils.calcSubtract(zxSkTurnOverStock1.getStockQty(),num));
                    //库存原数量 - 报废数量
                    zxSkTurnOverStock1.setOriginalQty(CalcUtils.calcSubtract(oldNum,num));
                    //审核后的库存原值=审核前的库存原值/审核前的原数量*审核后的原数量
                    zxSkTurnOverStock1.setOriginalAmt(CalcUtils.calcMultiply(CalcUtils.calcDivide(zxSkTurnOverStock1.getOriginalAmt(),oldNum),CalcUtils.calcSubtract(oldNum,num)));
                    //审核后的库存净值=审核前的库存净值/审核前的原数量*审核后的原数量
                    zxSkTurnOverStock1.setRemainAmt(CalcUtils.calcMultiply(CalcUtils.calcDivide(zxSkTurnOverStock1.getRemainAmt(),oldNum),CalcUtils.calcSubtract(oldNum,num)));
                    zxSkTurnOverScrapItem.setModifyUserInfo(userKey, realName);
                    //更新库存
                    flag = zxSkTurnOverStockMapper.updateZxSkTurnOverStockScrap(zxSkTurnOverStock1);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnOverScrap);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnOverScrap(List<ZxSkTurnOverScrap> zxSkTurnOverScrapList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxSkTurnOverScrapList != null && zxSkTurnOverScrapList.size() > 0) {
            for (ZxSkTurnOverScrap zxSkTurnOverScrap : zxSkTurnOverScrapList) {
                // 删除明细
                ZxSkTurnOverScrapItem zxSkTurnOverScrapItem = new ZxSkTurnOverScrapItem();
                zxSkTurnOverScrapItem.setBillID(zxSkTurnOverScrap.getId());
                List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItems = zxSkTurnOverScrapItemMapper.selectByZxSkTurnOverScrapItemList(zxSkTurnOverScrapItem);
                if(zxSkTurnOverScrapItems != null && zxSkTurnOverScrapItems.size()>0) {
                    zxSkTurnOverScrapItem.setModifyUserInfo(userKey, realName);
                    zxSkTurnOverScrapItemMapper.batchDeleteUpdateZxSkTurnOverScrapItem(zxSkTurnOverScrapItems, zxSkTurnOverScrapItem);
                }
                if(StrUtil.isNotEmpty(zxSkTurnOverScrap.getWorkId())) {
                    jsonArr.add(zxSkTurnOverScrap.getWorkId());
                }
            }
           ZxSkTurnOverScrap zxSkTurnOverScrap = new ZxSkTurnOverScrap();
           zxSkTurnOverScrap.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverScrapMapper.batchDeleteUpdateZxSkTurnOverScrap(zxSkTurnOverScrapList, zxSkTurnOverScrap);
        }
        //删除流程后台数据
        if(jsonArr.size()>0) {
            HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnOverScrapList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //“业主合同编号”+“周材报废第”+“年份-月份-顺序号”+“号”
    @Override
    public ResponseEntity getZxSkTurnOverScrapNo(ZxSkTurnOverScrap zxSkTurnOverScrap) {
        if(StrUtil.isEmpty(zxSkTurnOverScrap.getOrgID()) || zxSkTurnOverScrap.getBusDate() == null){
            return repEntity.ok(null);
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        String orgID = zxSkTurnOverScrap.getOrgID();
        zxCtContract.setOrgID(orgID);
        List<ZxCtContract> zxCtContracts = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        String contractNo = zxCtContracts.get(0).getContractNo();
        int year = DateUtil.year(zxSkTurnOverScrap.getBusDate());
        int month = DateUtil.month(zxSkTurnOverScrap.getBusDate())+1;
        int day = DateUtil.dayOfMonth(zxSkTurnOverScrap.getBusDate());
        if(CalcUtils.compareTo(new BigDecimal(day), new BigDecimal("26"))>=0){
            month = month + 1;
            if(CalcUtils.compareTo(new BigDecimal(month),new BigDecimal("12"))>0){
                year = year + 1;
                month = 1;
            }
        }
        String monthStr = String.valueOf(month);
        String result = String.valueOf(year) + "-" +  (monthStr.length() == 1 ? "0"+ monthStr : monthStr);
        String str = String.valueOf(zxSkTurnOverScrapMapper.getZxSkTurnOverScrapCount(result,orgID));
        str = String.valueOf(CalcUtils.calcAdd(new BigDecimal(str),new BigDecimal("1")));
        if(str.length() == 1){
            str = "00"+ str;
        }else if(str.length() == 2){
            str = "0" + str;
        }
        String no = contractNo + " 周材报废第 " + result + "-" + str + " 号";
        return repEntity.ok(no);
    }

    @Override
    public ResponseEntity getZxSkTurnOverScrapResourceList(ZxSkTurnOverScrap zxSkTurnOverScrap) {
        if (StrUtil.isEmpty(zxSkTurnOverScrap.getOrgID())) {
            return repEntity.ok(new ArrayList<>()); //            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverScrap.getPage(),zxSkTurnOverScrap.getLimit());
        // 获取数据(通过库存)
        List<ZxSkTurnOverScrapItem> zxSkTurnoverOutItems = zxSkTurnOverScrapMapper.getZxSkTurnOverScrapResourceList(zxSkTurnOverScrap);
        // 得到分页信息
        PageInfo<ZxSkTurnOverScrapItem> p = new PageInfo<>(zxSkTurnoverOutItems);
        return repEntity.okList(zxSkTurnoverOutItems, p.getTotal());
    }

}
