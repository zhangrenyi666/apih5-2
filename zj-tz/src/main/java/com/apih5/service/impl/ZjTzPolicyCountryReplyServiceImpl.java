package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzPolicyCountryMapper;
import com.apih5.mybatis.dao.ZjTzPolicyCountryReplyMapper;
import com.apih5.mybatis.dao.ZjTzPolicyCountryReplyRecordMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPolicyCountry;
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReply;
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReplyRecord;
import com.apih5.service.ZjTzPolicyCountryReplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPolicyCountryReplyService")
public class ZjTzPolicyCountryReplyServiceImpl implements ZjTzPolicyCountryReplyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPolicyCountryReplyMapper zjTzPolicyCountryReplyMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPolicyCountryReplyRecordMapper zjTzPolicyCountryReplyRecordMapper;
    
    @Autowired(required = true)
    private ZjTzPolicyCountryMapper zjTzPolicyCountryMapper;

    @Override
    public ResponseEntity getZjTzPolicyCountryReplyListByCondition(ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        if (zjTzPolicyCountryReply == null) {
            zjTzPolicyCountryReply = new ZjTzPolicyCountryReply();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        
        //????????????????????????????????????  ??????????????????????????????????????? ??????????????????????????????
//        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
//            zjTzPolicyCountryReply.setValue(userKey);
//        }
        if(StrUtil.isNotEmpty(zjTzPolicyCountryReply.getReplyFlag())) {
        	zjTzPolicyCountryReply.setValue(userKey);
        	zjTzPolicyCountryReply.setReleaseId("1");
        }
        // ????????????
        PageHelper.startPage(zjTzPolicyCountryReply.getPage(),zjTzPolicyCountryReply.getLimit());
        // ????????????
        List<ZjTzPolicyCountryReply> zjTzPolicyCountryReplyList = zjTzPolicyCountryReplyMapper.selectByZjTzPolicyCountryReplyList(zjTzPolicyCountryReply);
        if(zjTzPolicyCountryReplyList != null && zjTzPolicyCountryReplyList.size()>0) {
            for(ZjTzPolicyCountryReply dbZjTzPolicyCountryReply:zjTzPolicyCountryReplyList) {
                ZjTzFile zjTzFileSelect = new ZjTzFile();
                zjTzFileSelect.setOtherId(dbZjTzPolicyCountryReply.getPolicyId());
                List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyCountryReply.setZjTzFileList(zjTzFileList);
                //??????
                zjTzFileSelect.setOtherId(dbZjTzPolicyCountryReply.getPolicyReplyId());
                List<ZjTzFile> zjTzPolicyCountryReplyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyCountryReply.setZjTzPolicyCountryReplyFileList(zjTzPolicyCountryReplyFileList);
            }
        }
        // ??????????????????
        PageInfo<ZjTzPolicyCountryReply> p = new PageInfo<>(zjTzPolicyCountryReplyList);

        return repEntity.okList(zjTzPolicyCountryReplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPolicyCountryReplyDetails(ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        if (zjTzPolicyCountryReply == null) {
            zjTzPolicyCountryReply = new ZjTzPolicyCountryReply();
        }
        // ????????????
        ZjTzPolicyCountryReply dbZjTzPolicyCountryReply = zjTzPolicyCountryReplyMapper.selectByPrimaryKey(zjTzPolicyCountryReply.getPolicyReplyId());
        // ????????????
        if (dbZjTzPolicyCountryReply != null) {
            return repEntity.ok(dbZjTzPolicyCountryReply);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzPolicyCountryReply(ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPolicyCountryReply.setPolicyReplyId(UuidUtil.generate());
        zjTzPolicyCountryReply.setCreateUserInfo(userKey, realName);
        int flag = zjTzPolicyCountryReplyMapper.insert(zjTzPolicyCountryReply);
        
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzPolicyCountryReply.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:zjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzPolicyCountryReply.getPolicyReplyId());
                zjTzFile.setOtherType("2");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }

        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPolicyCountryReply);
        }
    }

    @Override
    public ResponseEntity updateZjTzPolicyCountryReply(ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyCountryReply dbzjTzPolicyCountryReply = zjTzPolicyCountryReplyMapper.selectByPrimaryKey(zjTzPolicyCountryReply.getPolicyReplyId());
        if (dbzjTzPolicyCountryReply != null && StrUtil.isNotEmpty(dbzjTzPolicyCountryReply.getPolicyReplyId())) {
           // ????????????ID
           dbzjTzPolicyCountryReply.setPolicyId(zjTzPolicyCountryReply.getPolicyId());
//           // ??????
//           dbzjTzPolicyCountryReply.setTitle(zjTzPolicyCountryReply.getTitle());
//           // ??????
//           dbzjTzPolicyCountryReply.setSymbolNo(zjTzPolicyCountryReply.getSymbolNo());
//           // ??????????????????
//           dbzjTzPolicyCountryReply.setSysDate(zjTzPolicyCountryReply.getSysDate());
//           // ????????????
//           dbzjTzPolicyCountryReply.setDepartmentName(zjTzPolicyCountryReply.getDepartmentName());
//           // ??????????????????
//           dbzjTzPolicyCountryReply.setReleaseDate(zjTzPolicyCountryReply.getReleaseDate());
//           // ????????????
//           dbzjTzPolicyCountryReply.setRegisterUser(zjTzPolicyCountryReply.getRegisterUser());
//           // ????????????
//           dbzjTzPolicyCountryReply.setEffectiveId(zjTzPolicyCountryReply.getEffectiveId());
//           // ??????????????????
//           dbzjTzPolicyCountryReply.setEffectiveName(zjTzPolicyCountryReply.getEffectiveName());
//           // ????????????
//           dbzjTzPolicyCountryReply.setReport(zjTzPolicyCountryReply.getReport());
//           // userKey
//           dbzjTzPolicyCountryReply.setValue(zjTzPolicyCountryReply.getValue());
//           // userName
//           dbzjTzPolicyCountryReply.setLabel(zjTzPolicyCountryReply.getLabel());
//           // ??????
//           dbzjTzPolicyCountryReply.setType(zjTzPolicyCountryReply.getType());
           // ????????????
           dbzjTzPolicyCountryReply.setReplyInfo(zjTzPolicyCountryReply.getReplyInfo());
           dbzjTzPolicyCountryReply.setReplyTime(new Date());
           // ??????
           dbzjTzPolicyCountryReply.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyCountryReplyMapper.updateByPrimaryKey(dbzjTzPolicyCountryReply);
           
           // ????????????????????????????????????
           ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecordSelect = new ZjTzPolicyCountryReplyRecord();
           zjTzPolicyCountryReplyRecordSelect.setValue(userKey);
           zjTzPolicyCountryReplyRecordSelect.setPolicyReplyId(zjTzPolicyCountryReply.getPolicyReplyId());
           List<ZjTzPolicyCountryReplyRecord> dbZjTzPolicyCountryReplyRecordList = zjTzPolicyCountryReplyRecordMapper.selectByZjTzPolicyCountryReplyRecordList(zjTzPolicyCountryReplyRecordSelect);
           if(dbZjTzPolicyCountryReplyRecordList == null || dbZjTzPolicyCountryReplyRecordList.size()==0) {
        	   ZjTzPolicyCountry dbzjTzPolicyCountry = zjTzPolicyCountryMapper.selectByPrimaryKey(dbzjTzPolicyCountryReply.getPolicyId());
               int count = dbzjTzPolicyCountry.getPushInfoReply() + 1;
               dbzjTzPolicyCountry.setPushInfoReply(count);
               dbzjTzPolicyCountry.setModifyUserInfo(userKey, realName);
               zjTzPolicyCountryMapper.updateByPrimaryKey(dbzjTzPolicyCountry);
           }
           
           // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzPolicyCountryReply.getPolicyReplyId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // ??????list
           List<ZjTzFile> zjTzFileList = zjTzPolicyCountryReply.getZjTzPolicyCountryReplyFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(dbzjTzPolicyCountryReply.getPolicyReplyId());
                   zjTzFile.setOtherType("2");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           
           // ??????????????????
           ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord = new ZjTzPolicyCountryReplyRecord();
           BeanUtil.copyProperties(dbzjTzPolicyCountryReply, zjTzPolicyCountryReplyRecord);
           zjTzPolicyCountryReplyRecord.setRecordId(UuidUtil.generate());
           zjTzPolicyCountryReplyRecord.setCreateUserInfo(userKey, realName);
           zjTzPolicyCountryReplyRecordMapper.insert(zjTzPolicyCountryReplyRecord);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyCountryReply);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPolicyCountryReply(List<ZjTzPolicyCountryReply> zjTzPolicyCountryReplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyCountryReplyList != null && zjTzPolicyCountryReplyList.size() > 0) {
           ZjTzPolicyCountryReply zjTzPolicyCountryReply = new ZjTzPolicyCountryReply();
           zjTzPolicyCountryReply.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyCountryReplyMapper.batchDeleteUpdateZjTzPolicyCountryReply(zjTzPolicyCountryReplyList, zjTzPolicyCountryReply);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPolicyCountryReplyList);
        }
    }
}
