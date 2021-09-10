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
        
        //这块我给注释掉的原因是，  推送给谁谁能看见该条数据， 否则数量那块逻辑不通
//        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
//            zjTzPolicyCountryReply.setValue(userKey);
//        }
        if(StrUtil.isNotEmpty(zjTzPolicyCountryReply.getReplyFlag())) {
        	zjTzPolicyCountryReply.setValue(userKey);
        	zjTzPolicyCountryReply.setReleaseId("1");
        }
        // 分页查询
        PageHelper.startPage(zjTzPolicyCountryReply.getPage(),zjTzPolicyCountryReply.getLimit());
        // 获取数据
        List<ZjTzPolicyCountryReply> zjTzPolicyCountryReplyList = zjTzPolicyCountryReplyMapper.selectByZjTzPolicyCountryReplyList(zjTzPolicyCountryReply);
        if(zjTzPolicyCountryReplyList != null && zjTzPolicyCountryReplyList.size()>0) {
            for(ZjTzPolicyCountryReply dbZjTzPolicyCountryReply:zjTzPolicyCountryReplyList) {
                ZjTzFile zjTzFileSelect = new ZjTzFile();
                zjTzFileSelect.setOtherId(dbZjTzPolicyCountryReply.getPolicyId());
                List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyCountryReply.setZjTzFileList(zjTzFileList);
                //回复
                zjTzFileSelect.setOtherId(dbZjTzPolicyCountryReply.getPolicyReplyId());
                List<ZjTzFile> zjTzPolicyCountryReplyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyCountryReply.setZjTzPolicyCountryReplyFileList(zjTzPolicyCountryReplyFileList);
            }
        }
        // 得到分页信息
        PageInfo<ZjTzPolicyCountryReply> p = new PageInfo<>(zjTzPolicyCountryReplyList);

        return repEntity.okList(zjTzPolicyCountryReplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPolicyCountryReplyDetails(ZjTzPolicyCountryReply zjTzPolicyCountryReply) {
        if (zjTzPolicyCountryReply == null) {
            zjTzPolicyCountryReply = new ZjTzPolicyCountryReply();
        }
        // 获取数据
        ZjTzPolicyCountryReply dbZjTzPolicyCountryReply = zjTzPolicyCountryReplyMapper.selectByPrimaryKey(zjTzPolicyCountryReply.getPolicyReplyId());
        // 数据存在
        if (dbZjTzPolicyCountryReply != null) {
            return repEntity.ok(dbZjTzPolicyCountryReply);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
        
        // 附件list
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
           // 国家政策ID
           dbzjTzPolicyCountryReply.setPolicyId(zjTzPolicyCountryReply.getPolicyId());
//           // 标题
//           dbzjTzPolicyCountryReply.setTitle(zjTzPolicyCountryReply.getTitle());
//           // 文号
//           dbzjTzPolicyCountryReply.setSymbolNo(zjTzPolicyCountryReply.getSymbolNo());
//           // 系统发布日期
//           dbzjTzPolicyCountryReply.setSysDate(zjTzPolicyCountryReply.getSysDate());
//           // 发文部门
//           dbzjTzPolicyCountryReply.setDepartmentName(zjTzPolicyCountryReply.getDepartmentName());
//           // 原文发布日期
//           dbzjTzPolicyCountryReply.setReleaseDate(zjTzPolicyCountryReply.getReleaseDate());
//           // 登记用户
//           dbzjTzPolicyCountryReply.setRegisterUser(zjTzPolicyCountryReply.getRegisterUser());
//           // 有效文件
//           dbzjTzPolicyCountryReply.setEffectiveId(zjTzPolicyCountryReply.getEffectiveId());
//           // 有效文件名称
//           dbzjTzPolicyCountryReply.setEffectiveName(zjTzPolicyCountryReply.getEffectiveName());
//           // 分析报告
//           dbzjTzPolicyCountryReply.setReport(zjTzPolicyCountryReply.getReport());
//           // userKey
//           dbzjTzPolicyCountryReply.setValue(zjTzPolicyCountryReply.getValue());
//           // userName
//           dbzjTzPolicyCountryReply.setLabel(zjTzPolicyCountryReply.getLabel());
//           // 类型
//           dbzjTzPolicyCountryReply.setType(zjTzPolicyCountryReply.getType());
           // 回复内容
           dbzjTzPolicyCountryReply.setReplyInfo(zjTzPolicyCountryReply.getReplyInfo());
           dbzjTzPolicyCountryReply.setReplyTime(new Date());
           // 共通
           dbzjTzPolicyCountryReply.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyCountryReplyMapper.updateByPrimaryKey(dbzjTzPolicyCountryReply);
           
           // 更新主表是否回复完成数据
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
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzPolicyCountryReply.getPolicyReplyId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
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
           
           // 插入历史记录
           ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord = new ZjTzPolicyCountryReplyRecord();
           BeanUtil.copyProperties(dbzjTzPolicyCountryReply, zjTzPolicyCountryReplyRecord);
           zjTzPolicyCountryReplyRecord.setRecordId(UuidUtil.generate());
           zjTzPolicyCountryReplyRecord.setCreateUserInfo(userKey, realName);
           zjTzPolicyCountryReplyRecordMapper.insert(zjTzPolicyCountryReplyRecord);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPolicyCountryReplyList);
        }
    }
}
