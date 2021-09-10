package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSkMonthPurItemMapper;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkMonthPurMapper;
import com.apih5.service.ZxSkMonthPurService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkMonthPurService")
public class ZxSkMonthPurServiceImpl implements ZxSkMonthPurService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkMonthPurMapper zxSkMonthPurMapper;

    @Autowired(required = true)
    private ZxSkMonthPurItemMapper zxSkMonthPurItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSkMonthPurListByCondition(ZxSkMonthPur zxSkMonthPur) {
        if (zxSkMonthPur == null) {
            zxSkMonthPur = new ZxSkMonthPur();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkMonthPur.setCompanyId("");
            zxSkMonthPur.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkMonthPur.getOrgID2())){
                zxSkMonthPur.setProjectID(zxSkMonthPur.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkMonthPur.setCompanyId(zxSkMonthPur.getProjectID());
            zxSkMonthPur.setProjectID("");
            if(StrUtil.isNotEmpty(zxSkMonthPur.getOrgID2())){
                zxSkMonthPur.setProjectID(zxSkMonthPur.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkMonthPur.setProjectID(zxSkMonthPur.getProjectID());
            if(StrUtil.isNotEmpty(zxSkMonthPur.getOrgID2())){
                zxSkMonthPur.setProjectID(zxSkMonthPur.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkMonthPur.getPage(),zxSkMonthPur.getLimit());
        // 获取数据
        List<ZxSkMonthPur> zxSkMonthPurList = zxSkMonthPurMapper.selectByZxSkMonthPurList(zxSkMonthPur);

        //查询明细
        for (ZxSkMonthPur zxSkMonthPur1 : zxSkMonthPurList) {
            ZxSkMonthPurItem zxSkMonthPurItem = new ZxSkMonthPurItem();
            zxSkMonthPurItem.setMonthPurID(zxSkMonthPur1.getId());
            List<ZxSkMonthPurItem> zxSkMonthPurItems = zxSkMonthPurItemMapper.selectByZxSkMonthPurItemList(zxSkMonthPurItem);
            zxSkMonthPur1.setZxSkMonthPurItemList(zxSkMonthPurItems);
        }

        // 得到分页信息
        PageInfo<ZxSkMonthPur> p = new PageInfo<>(zxSkMonthPurList);

        return repEntity.okList(zxSkMonthPurList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkMonthPurDetails(ZxSkMonthPur zxSkMonthPur) {
        if (zxSkMonthPur == null) {
            zxSkMonthPur = new ZxSkMonthPur();
        }
        ZxErpFile zxErpFile = new ZxErpFile();
        if(StrUtil.isNotEmpty(zxSkMonthPur.getId())){
            zxSkMonthPur = zxSkMonthPurMapper.selectByPrimaryKey(zxSkMonthPur.getId());
        }else if(StrUtil.isNotEmpty(zxSkMonthPur.getWorkId())){
            List<ZxSkMonthPur> zxSkMonthPurs = zxSkMonthPurMapper.selectByZxSkMonthPurList(zxSkMonthPur);
            if(zxSkMonthPurs != null && zxSkMonthPurs.size() >0){
                zxSkMonthPur = zxSkMonthPurs.get(0);
            }
        }

        // 数据存在
        if (zxSkMonthPur != null) {
            ZxSkMonthPurItem zxSkMonthPurItem = new ZxSkMonthPurItem();
            zxSkMonthPurItem.setMonthPurID(zxSkMonthPur.getId());
            List<ZxSkMonthPurItem> zxSkMonthPurItems = zxSkMonthPurItemMapper.selectByZxSkMonthPurItemList(zxSkMonthPurItem);
            zxSkMonthPur.setZxSkMonthPurItemList(zxSkMonthPurItems);

            //附件
            zxErpFile.setOtherId(zxSkMonthPur.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkMonthPur.setFileList(zxErpFiles);
            return repEntity.ok(zxSkMonthPur);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkMonthPur(ZxSkMonthPur zxSkMonthPur) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkMonthPur.setId(UuidUtil.generate());
        zxSkMonthPur.setCreateUserInfo(userKey, realName);
        //新增-1
        zxSkMonthPur.setApih5FlowStatus("-1");
        int flag = zxSkMonthPurMapper.insert(zxSkMonthPur);
        //附件新增
        if(zxSkMonthPur.getFileList() != null && zxSkMonthPur.getFileList().size() >0){
            for (ZxErpFile zxErpFile : zxSkMonthPur.getFileList()) {
                zxErpFile.setUid(UuidUtil.generate());
                zxErpFile.setOtherId(zxSkMonthPur.getId());
                zxErpFile.setCreateUserInfo(userKey, realName);
                flag = zxErpFileMapper.insert(zxErpFile);
            }
        }
        //创建明细
        List<ZxSkMonthPurItem> zxSkMonthPurItemList = zxSkMonthPur.getZxSkMonthPurItemList();
        if(zxSkMonthPurItemList!=null&&zxSkMonthPurItemList.size()>0) {
            for (ZxSkMonthPurItem zxSkMmReqPlanItem : zxSkMonthPurItemList) {
                zxSkMmReqPlanItem.setMonthPurID(zxSkMonthPur.getId());
                zxSkMmReqPlanItem.setId((UuidUtil.generate()));
                zxSkMmReqPlanItem.setCreateUserInfo(userKey, realName);
                zxSkMonthPurItemMapper.insert(zxSkMmReqPlanItem);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSkMonthPur);
        }
    }

    @Override
    public ResponseEntity updateZxSkMonthPur(ZxSkMonthPur zxSkMonthPur) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkMonthPur dbzxSkMonthPur = zxSkMonthPurMapper.selectByPrimaryKey(zxSkMonthPur.getId());
        if (dbzxSkMonthPur != null && StrUtil.isNotEmpty(dbzxSkMonthPur.getId()) && (StrUtil.equals(zxSkMonthPur.getApih5FlowStatus(),"-1")||StrUtil.equals(zxSkMonthPur.getApih5FlowStatus(),"0"))) {
            if(StrUtil.equals(zxSkMonthPur.getApih5FlowStatus(),"0")){
                // 流程id
                dbzxSkMonthPur.setWorkId(zxSkMonthPur.getWorkId());
                // 流程状态
                dbzxSkMonthPur.setApih5FlowStatus(zxSkMonthPur.getApih5FlowStatus());
            }
           // 项目ID
           dbzxSkMonthPur.setProjectID(zxSkMonthPur.getProjectID());
           // 项目名称
           dbzxSkMonthPur.setProjectName(zxSkMonthPur.getProjectName());
           // 日期
           dbzxSkMonthPur.setCreateDate(zxSkMonthPur.getCreateDate());
           // 金额
           dbzxSkMonthPur.setTotalMoney(zxSkMonthPur.getTotalMoney());
           // 状态
           dbzxSkMonthPur.setStatus(zxSkMonthPur.getStatus());
           // 备注
           dbzxSkMonthPur.setRemark(zxSkMonthPur.getRemark());
           // 明细
           dbzxSkMonthPur.setCombProp(zxSkMonthPur.getCombProp());
           // 计划编号
           dbzxSkMonthPur.setProjectNumber(zxSkMonthPur.getProjectNumber());
           // 编制人
           dbzxSkMonthPur.setAurhorizedPersonnel(zxSkMonthPur.getAurhorizedPersonnel());
           // workitemID
           dbzxSkMonthPur.setWorkitemID(zxSkMonthPur.getWorkitemID());
           // instProcessId
           dbzxSkMonthPur.setInstProcessId(zxSkMonthPur.getInstProcessId());
           // 公司id
           dbzxSkMonthPur.setCompanyId(zxSkMonthPur.getCompanyId());
           // 公司名称
           dbzxSkMonthPur.setCompanyName(zxSkMonthPur.getCompanyName());
           // 共通
           dbzxSkMonthPur.setModifyUserInfo(userKey, realName);
           flag = zxSkMonthPurMapper.updateByPrimaryKey(dbzxSkMonthPur);

            //删除在新增
            ZxSkMonthPurItem zxSkMonthPurItem = new ZxSkMonthPurItem();
            zxSkMonthPurItem.setMonthPurID(dbzxSkMonthPur.getId());
            List<ZxSkMonthPurItem> zxSkMonthPurItems = zxSkMonthPurItemMapper.selectByZxSkMonthPurItemList(zxSkMonthPurItem);
            if(zxSkMonthPurItems != null && zxSkMonthPurItems.size()>0) {
                zxSkMonthPurItem.setModifyUserInfo(userKey, realName);
                zxSkMonthPurItemMapper.batchDeleteUpdateZxSkMonthPurItem(zxSkMonthPurItems, zxSkMonthPurItem);
            }
            //明细list
            List<ZxSkMonthPurItem> zxSkMonthPurItemList = zxSkMonthPur.getZxSkMonthPurItemList();
            if(zxSkMonthPurItemList != null && zxSkMonthPurItemList.size()>0) {
                for(ZxSkMonthPurItem zxSkMonthPurItem1 : zxSkMonthPurItemList) {
                    zxSkMonthPurItem1.setId(UuidUtil.generate());
                    zxSkMonthPurItem1.setMonthPurID(dbzxSkMonthPur.getId());
                    zxSkMonthPurItem1.setCreateUserInfo(userKey, realName);
                    zxSkMonthPurItemMapper.insert(zxSkMonthPurItem1);
                }
            }

            //附件/删除在新增
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbzxSkMonthPur.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
            }

            List<ZxErpFile> fileList = zxSkMonthPur.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile1 : fileList) {
                    zxErpFile1.setUid(UuidUtil.generate());
                    zxErpFile1.setOtherId(zxSkMonthPur.getId());
                    zxErpFile1.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile1);
                }
            }
        }else {
            // 流程id
            dbzxSkMonthPur.setWorkId(zxSkMonthPur.getWorkId());
            // 流程状态
            dbzxSkMonthPur.setApih5FlowStatus(zxSkMonthPur.getApih5FlowStatus());

            //流程的意见
            if(StrUtil.equals("opinionField1", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField1(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField1()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField2", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField2(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField2()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField3", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField3(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField3()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField4", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField4(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField4()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField5", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField5(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField5()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField6", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField6(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField6()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField7", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField7(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField7()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField8", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField8(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField8()));
            }//流程的意见
            if(StrUtil.equals("opinionField9", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField9(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField9()));
            }
            //流程的意见
            if(StrUtil.equals("opinionField10", zxSkMonthPur.getOpinionField(), true)){
                dbzxSkMonthPur.setOpinionField10(zxSkMonthPur.getOpinionContent(realName, dbzxSkMonthPur.getOpinionField10()));
            }
            // 共通
            dbzxSkMonthPur.setModifyUserInfo(userKey, realName);
            flag = zxSkMonthPurMapper.updateByPrimaryKey(dbzxSkMonthPur);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkMonthPur.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkMonthPur.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkMonthPur.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkMonthPur);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkMonthPur(List<ZxSkMonthPur> zxSkMonthPurList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxSkMonthPurList != null && zxSkMonthPurList.size() > 0) {
            for (ZxSkMonthPur zxSkMonthPur : zxSkMonthPurList) {
                // 删除明细
                ZxSkMonthPurItem zxSkMonthPurItem = new ZxSkMonthPurItem();
                zxSkMonthPurItem.setMonthPurID(zxSkMonthPur.getId());
                List<ZxSkMonthPurItem> zxSkMonthPurItems = zxSkMonthPurItemMapper.selectByZxSkMonthPurItemList(zxSkMonthPurItem);
                if(zxSkMonthPurItems != null && zxSkMonthPurItems.size()>0) {
                    zxSkMonthPurItem.setModifyUserInfo(userKey, realName);
                    zxSkMonthPurItemMapper.batchDeleteUpdateZxSkMonthPurItem(zxSkMonthPurItems, zxSkMonthPurItem);
                }
                if(StrUtil.isNotEmpty(zxSkMonthPur.getWorkId())) {
                    jsonArr.add(zxSkMonthPur.getWorkId());
                }
            }
           ZxSkMonthPur zxSkMonthPur = new ZxSkMonthPur();
           zxSkMonthPur.setModifyUserInfo(userKey, realName);
           flag = zxSkMonthPurMapper.batchDeleteUpdateZxSkMonthPur(zxSkMonthPurList, zxSkMonthPur);
        }
        //删除流程后台数据
        if(jsonArr.size()>0) {
            HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSkMonthPurList);
        }
    }






}
