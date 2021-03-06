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
import com.apih5.mybatis.dao.ZjTzPolicyLocalMapper;
import com.apih5.mybatis.dao.ZjTzPolicyLocalReplyRecordMapper;
import com.apih5.mybatis.dao.ZjTzPolicyLocalReplyMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPolicyLocal;
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReply;
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReplyRecord;
import com.apih5.service.ZjTzPolicyLocalReplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPolicyLocalReplyService")
public class ZjTzPolicyLocalReplyServiceImpl implements ZjTzPolicyLocalReplyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPolicyLocalReplyMapper zjTzPolicyLocalReplyMapper;
    
    @Autowired(required = true)
    private ZjTzPolicyLocalReplyRecordMapper zjTzPolicyLocalReplyRecordMapper;
    
    @Autowired(required = true)
    private ZjTzPolicyLocalMapper zjTzPolicyLocalMapper;

    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Override
    public ResponseEntity getZjTzPolicyLocalReplyListByCondition(ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        if (zjTzPolicyLocalReply == null) {
            zjTzPolicyLocalReply = new ZjTzPolicyLocalReply();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
       
        //????????????????????????????????????  ??????????????????????????????????????? ??????????????????????????????
//        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
//        	zjTzPolicyLocalReply.setValue(userKey);
//        }
        
        if(StrUtil.isNotEmpty(zjTzPolicyLocalReply.getReplyFlag())) {
        	zjTzPolicyLocalReply.setValue(userKey);
        	zjTzPolicyLocalReply.setReleaseId("1");
        }
        
        // ????????????
        PageHelper.startPage(zjTzPolicyLocalReply.getPage(),zjTzPolicyLocalReply.getLimit());
        // ????????????
        List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList = zjTzPolicyLocalReplyMapper.selectByZjTzPolicyLocalReplyList(zjTzPolicyLocalReply);
        if(zjTzPolicyLocalReplyList != null && zjTzPolicyLocalReplyList.size()>0) {
            for(ZjTzPolicyLocalReply dbZjTzPolicyLocalReply:zjTzPolicyLocalReplyList) {
                ZjTzFile zjTzFileSelect = new ZjTzFile();
                zjTzFileSelect.setOtherId(dbZjTzPolicyLocalReply.getPolicyId());
                List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyLocalReply.setZjTzFileList(zjTzFileList);
                //??????
                zjTzFileSelect.setOtherId(dbZjTzPolicyLocalReply.getPolicyReplyId());
                List<ZjTzFile> zjTzPolicyLocalReplyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyLocalReply.setZjTzPolicyLocalReplyFileList(zjTzPolicyLocalReplyFileList);
            }
        }
        // ??????????????????
        PageInfo<ZjTzPolicyLocalReply> p = new PageInfo<>(zjTzPolicyLocalReplyList);

        return repEntity.okList(zjTzPolicyLocalReplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPolicyLocalReplyDetails(ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        if (zjTzPolicyLocalReply == null) {
            zjTzPolicyLocalReply = new ZjTzPolicyLocalReply();
        }
        // ????????????
        ZjTzPolicyLocalReply dbZjTzPolicyLocalReply = zjTzPolicyLocalReplyMapper.selectByPrimaryKey(zjTzPolicyLocalReply.getPolicyReplyId());
        // ????????????
        if (dbZjTzPolicyLocalReply != null) {
            return repEntity.ok(dbZjTzPolicyLocalReply);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzPolicyLocalReply(ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPolicyLocalReply.setPolicyReplyId(UuidUtil.generate());
        zjTzPolicyLocalReply.setCreateUserInfo(userKey, realName);
        int flag = zjTzPolicyLocalReplyMapper.insert(zjTzPolicyLocalReply);
     // ??????list
        List<ZjTzFile> zjTzFileList = zjTzPolicyLocalReply.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:zjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzPolicyLocalReply.getPolicyReplyId());
                zjTzFile.setOtherType("2");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPolicyLocalReply);
        }
    }

    @Override
    public ResponseEntity updateZjTzPolicyLocalReply(ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyLocalReply dbzjTzPolicyLocalReply = zjTzPolicyLocalReplyMapper.selectByPrimaryKey(zjTzPolicyLocalReply.getPolicyReplyId());
        if (dbzjTzPolicyLocalReply != null && StrUtil.isNotEmpty(dbzjTzPolicyLocalReply.getPolicyReplyId())) {
           // ????????????ID
           dbzjTzPolicyLocalReply.setPolicyId(zjTzPolicyLocalReply.getPolicyId());
//         // ??????
//         dbzjTzPolicyLocalReply.setTitle(zjTzPolicyLocalReply.getTitle());
//         // ??????
//         dbzjTzPolicyLocalReply.setSymbolNo(zjTzPolicyLocalReply.getSymbolNo());
//         // ??????????????????
//         dbzjTzPolicyLocalReply.setSysDate(zjTzPolicyLocalReply.getSysDate());
//         // ????????????
//         dbzjTzPolicyLocalReply.setDepartmentName(zjTzPolicyLocalReply.getDepartmentName());
//         // ??????????????????
//         dbzjTzPolicyLocalReply.setReleaseDate(zjTzPolicyLocalReply.getReleaseDate());
//         // ????????????
//         dbzjTzPolicyLocalReply.setRegisterUser(zjTzPolicyLocalReply.getRegisterUser());
//         // ????????????
//         dbzjTzPolicyLocalReply.setEffectiveId(zjTzPolicyLocalReply.getEffectiveId());
//         // ??????????????????
//         dbzjTzPolicyLocalReply.setEffectiveName(zjTzPolicyLocalReply.getEffectiveName());
//         // ????????????
//         dbzjTzPolicyLocalReply.setReport(zjTzPolicyLocalReply.getReport());
//         // userKey
//         dbzjTzPolicyLocalReply.setValue(zjTzPolicyLocalReply.getValue());
//         // userName
//         dbzjTzPolicyLocalReply.setLabel(zjTzPolicyLocalReply.getLabel());
//         // ??????
//         dbzjTzPolicyLocalReply.setType(zjTzPolicyLocalReply.getType());
         // ????????????
         dbzjTzPolicyLocalReply.setReplyInfo(zjTzPolicyLocalReply.getReplyInfo());
         dbzjTzPolicyLocalReply.setReplyTime(new Date());
         // ??????
         dbzjTzPolicyLocalReply.setModifyUserInfo(userKey, realName);
         flag = zjTzPolicyLocalReplyMapper.updateByPrimaryKey(dbzjTzPolicyLocalReply);
         
         // ????????????????????????????????????
         ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecordSelect = new ZjTzPolicyLocalReplyRecord();
         zjTzPolicyLocalReplyRecordSelect.setValue(userKey);
         zjTzPolicyLocalReplyRecordSelect.setPolicyReplyId(zjTzPolicyLocalReply.getPolicyReplyId());
         List<ZjTzPolicyLocalReplyRecord> dbZjTzPolicyLocalReplyRecordList = zjTzPolicyLocalReplyRecordMapper.selectByZjTzPolicyLocalReplyRecordList(zjTzPolicyLocalReplyRecordSelect);
         if(dbZjTzPolicyLocalReplyRecordList == null || dbZjTzPolicyLocalReplyRecordList.size()==0) {
             ZjTzPolicyLocal dbzjTzPolicyLocal = zjTzPolicyLocalMapper.selectByPrimaryKey(dbzjTzPolicyLocalReply.getPolicyId());
             int count = dbzjTzPolicyLocal.getPushInfoReply() + 1;
             dbzjTzPolicyLocal.setPushInfoReply(count);
             dbzjTzPolicyLocal.setModifyUserInfo(userKey, realName);
             zjTzPolicyLocalMapper.updateByPrimaryKey(dbzjTzPolicyLocal);
         }
         
         // ?????????????????????
         ZjTzFile zjTzFileSelect = new ZjTzFile();
         zjTzFileSelect.setOtherId(dbzjTzPolicyLocalReply.getPolicyReplyId());
         List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
         if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
             zjTzFileSelect.setModifyUserInfo(userKey, realName);
             zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
         }
         // ??????list
         List<ZjTzFile> zjTzReplyFileList = zjTzPolicyLocalReply.getZjTzPolicyLocalReplyFileList();
         if(zjTzReplyFileList != null && zjTzReplyFileList.size()>0) {
             for(ZjTzFile zjTzFile:zjTzReplyFileList) {
                 zjTzFile.setUid(UuidUtil.generate());
                 zjTzFile.setOtherId(dbzjTzPolicyLocalReply.getPolicyReplyId());
                 zjTzFile.setOtherType("2");
                 zjTzFile.setCreateUserInfo(userKey, realName);
                 zjTzFileMapper.insert(zjTzFile);
             }
         }
         
         // ??????????????????
         ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord = new ZjTzPolicyLocalReplyRecord();
         BeanUtil.copyProperties(dbzjTzPolicyLocalReply, zjTzPolicyLocalReplyRecord);
         zjTzPolicyLocalReplyRecord.setRecordId(UuidUtil.generate());
         zjTzPolicyLocalReplyRecord.setCreateUserInfo(userKey, realName);
         zjTzPolicyLocalReplyRecordMapper.insert(zjTzPolicyLocalReplyRecord);
      }
      // ??????
      if (flag == 0) {
          return repEntity.errorSave();
      }
      else {
          return repEntity.ok("sys.data.update",zjTzPolicyLocalReply);
      }

    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPolicyLocalReply(List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyLocalReplyList != null && zjTzPolicyLocalReplyList.size() > 0) {
           ZjTzPolicyLocalReply zjTzPolicyLocalReply = new ZjTzPolicyLocalReply();
           zjTzPolicyLocalReply.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyLocalReplyMapper.batchDeleteUpdateZjTzPolicyLocalReply(zjTzPolicyLocalReplyList, zjTzPolicyLocalReply);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPolicyLocalReplyList);
        }
    }
}
