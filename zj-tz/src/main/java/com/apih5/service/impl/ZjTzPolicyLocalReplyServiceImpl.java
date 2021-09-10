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
       
        //这块我给注释掉的原因是，  推送给谁谁能看见该条数据， 否则数量那块逻辑不通
//        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
//        	zjTzPolicyLocalReply.setValue(userKey);
//        }
        
        if(StrUtil.isNotEmpty(zjTzPolicyLocalReply.getReplyFlag())) {
        	zjTzPolicyLocalReply.setValue(userKey);
        	zjTzPolicyLocalReply.setReleaseId("1");
        }
        
        // 分页查询
        PageHelper.startPage(zjTzPolicyLocalReply.getPage(),zjTzPolicyLocalReply.getLimit());
        // 获取数据
        List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList = zjTzPolicyLocalReplyMapper.selectByZjTzPolicyLocalReplyList(zjTzPolicyLocalReply);
        if(zjTzPolicyLocalReplyList != null && zjTzPolicyLocalReplyList.size()>0) {
            for(ZjTzPolicyLocalReply dbZjTzPolicyLocalReply:zjTzPolicyLocalReplyList) {
                ZjTzFile zjTzFileSelect = new ZjTzFile();
                zjTzFileSelect.setOtherId(dbZjTzPolicyLocalReply.getPolicyId());
                List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyLocalReply.setZjTzFileList(zjTzFileList);
                //回复
                zjTzFileSelect.setOtherId(dbZjTzPolicyLocalReply.getPolicyReplyId());
                List<ZjTzFile> zjTzPolicyLocalReplyFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyLocalReply.setZjTzPolicyLocalReplyFileList(zjTzPolicyLocalReplyFileList);
            }
        }
        // 得到分页信息
        PageInfo<ZjTzPolicyLocalReply> p = new PageInfo<>(zjTzPolicyLocalReplyList);

        return repEntity.okList(zjTzPolicyLocalReplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPolicyLocalReplyDetails(ZjTzPolicyLocalReply zjTzPolicyLocalReply) {
        if (zjTzPolicyLocalReply == null) {
            zjTzPolicyLocalReply = new ZjTzPolicyLocalReply();
        }
        // 获取数据
        ZjTzPolicyLocalReply dbZjTzPolicyLocalReply = zjTzPolicyLocalReplyMapper.selectByPrimaryKey(zjTzPolicyLocalReply.getPolicyReplyId());
        // 数据存在
        if (dbZjTzPolicyLocalReply != null) {
            return repEntity.ok(dbZjTzPolicyLocalReply);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
     // 附件list
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
           // 地方政策ID
           dbzjTzPolicyLocalReply.setPolicyId(zjTzPolicyLocalReply.getPolicyId());
//         // 标题
//         dbzjTzPolicyLocalReply.setTitle(zjTzPolicyLocalReply.getTitle());
//         // 文号
//         dbzjTzPolicyLocalReply.setSymbolNo(zjTzPolicyLocalReply.getSymbolNo());
//         // 系统发布日期
//         dbzjTzPolicyLocalReply.setSysDate(zjTzPolicyLocalReply.getSysDate());
//         // 发文部门
//         dbzjTzPolicyLocalReply.setDepartmentName(zjTzPolicyLocalReply.getDepartmentName());
//         // 原文发布日期
//         dbzjTzPolicyLocalReply.setReleaseDate(zjTzPolicyLocalReply.getReleaseDate());
//         // 登记用户
//         dbzjTzPolicyLocalReply.setRegisterUser(zjTzPolicyLocalReply.getRegisterUser());
//         // 有效文件
//         dbzjTzPolicyLocalReply.setEffectiveId(zjTzPolicyLocalReply.getEffectiveId());
//         // 有效文件名称
//         dbzjTzPolicyLocalReply.setEffectiveName(zjTzPolicyLocalReply.getEffectiveName());
//         // 分析报告
//         dbzjTzPolicyLocalReply.setReport(zjTzPolicyLocalReply.getReport());
//         // userKey
//         dbzjTzPolicyLocalReply.setValue(zjTzPolicyLocalReply.getValue());
//         // userName
//         dbzjTzPolicyLocalReply.setLabel(zjTzPolicyLocalReply.getLabel());
//         // 类型
//         dbzjTzPolicyLocalReply.setType(zjTzPolicyLocalReply.getType());
         // 回复内容
         dbzjTzPolicyLocalReply.setReplyInfo(zjTzPolicyLocalReply.getReplyInfo());
         dbzjTzPolicyLocalReply.setReplyTime(new Date());
         // 共通
         dbzjTzPolicyLocalReply.setModifyUserInfo(userKey, realName);
         flag = zjTzPolicyLocalReplyMapper.updateByPrimaryKey(dbzjTzPolicyLocalReply);
         
         // 更新主表是否回复完成数据
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
         
         // 删除附件再新增
         ZjTzFile zjTzFileSelect = new ZjTzFile();
         zjTzFileSelect.setOtherId(dbzjTzPolicyLocalReply.getPolicyReplyId());
         List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
         if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
             zjTzFileSelect.setModifyUserInfo(userKey, realName);
             zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
         }
         // 附件list
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
         
         // 插入历史记录
         ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord = new ZjTzPolicyLocalReplyRecord();
         BeanUtil.copyProperties(dbzjTzPolicyLocalReply, zjTzPolicyLocalReplyRecord);
         zjTzPolicyLocalReplyRecord.setRecordId(UuidUtil.generate());
         zjTzPolicyLocalReplyRecord.setCreateUserInfo(userKey, realName);
         zjTzPolicyLocalReplyRecordMapper.insert(zjTzPolicyLocalReplyRecord);
      }
      // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPolicyLocalReplyList);
        }
    }
}
