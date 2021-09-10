package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzPolicyCountryReplyRecordMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReplyRecord;
import com.apih5.service.ZjTzPolicyCountryReplyRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPolicyCountryReplyRecordService")
public class ZjTzPolicyCountryReplyRecordServiceImpl implements ZjTzPolicyCountryReplyRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPolicyCountryReplyRecordMapper zjTzPolicyCountryReplyRecordMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzPolicyCountryReplyRecordListByCondition(ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord) {
        if (zjTzPolicyCountryReplyRecord == null) {
            zjTzPolicyCountryReplyRecord = new ZjTzPolicyCountryReplyRecord();
        }
        // 分页查询
        PageHelper.startPage(zjTzPolicyCountryReplyRecord.getPage(),zjTzPolicyCountryReplyRecord.getLimit());
        // 获取数据
        List<ZjTzPolicyCountryReplyRecord> zjTzPolicyCountryReplyRecordList = zjTzPolicyCountryReplyRecordMapper.selectByZjTzPolicyCountryReplyRecordList(zjTzPolicyCountryReplyRecord);
        for (ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord2 : zjTzPolicyCountryReplyRecordList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherType("2");
        	zjTzFileSelect.setOtherId(zjTzPolicyCountryReplyRecord2.getPolicyReplyId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzPolicyCountryReplyRecord2.setZjTzFileList(zjTzFileList);
        	
		}
        // 得到分页信息
        PageInfo<ZjTzPolicyCountryReplyRecord> p = new PageInfo<>(zjTzPolicyCountryReplyRecordList);

        return repEntity.okList(zjTzPolicyCountryReplyRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPolicyCountryReplyRecordDetails(ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord) {
        if (zjTzPolicyCountryReplyRecord == null) {
            zjTzPolicyCountryReplyRecord = new ZjTzPolicyCountryReplyRecord();
        }
        // 获取数据
        ZjTzPolicyCountryReplyRecord dbZjTzPolicyCountryReplyRecord = zjTzPolicyCountryReplyRecordMapper.selectByPrimaryKey(zjTzPolicyCountryReplyRecord.getRecordId());
        // 数据存在
        if (dbZjTzPolicyCountryReplyRecord != null) {
            return repEntity.ok(dbZjTzPolicyCountryReplyRecord);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPolicyCountryReplyRecord(ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPolicyCountryReplyRecord.setRecordId(UuidUtil.generate());
        zjTzPolicyCountryReplyRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzPolicyCountryReplyRecordMapper.insert(zjTzPolicyCountryReplyRecord);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPolicyCountryReplyRecord);
        }
    }

    @Override
    public ResponseEntity updateZjTzPolicyCountryReplyRecord(ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyCountryReplyRecord dbzjTzPolicyCountryReplyRecord = zjTzPolicyCountryReplyRecordMapper.selectByPrimaryKey(zjTzPolicyCountryReplyRecord.getRecordId());
        if (dbzjTzPolicyCountryReplyRecord != null && StrUtil.isNotEmpty(dbzjTzPolicyCountryReplyRecord.getRecordId())) {
           // 回复ID
           dbzjTzPolicyCountryReplyRecord.setPolicyReplyId(zjTzPolicyCountryReplyRecord.getPolicyReplyId());
           // 国家政策ID
           dbzjTzPolicyCountryReplyRecord.setPolicyId(zjTzPolicyCountryReplyRecord.getPolicyId());
           // 标题
           dbzjTzPolicyCountryReplyRecord.setTitle(zjTzPolicyCountryReplyRecord.getTitle());
           // 文号
           dbzjTzPolicyCountryReplyRecord.setSymbolNo(zjTzPolicyCountryReplyRecord.getSymbolNo());
           // 系统发布日期
           dbzjTzPolicyCountryReplyRecord.setSysDate(zjTzPolicyCountryReplyRecord.getSysDate());
           // 发文部门
           dbzjTzPolicyCountryReplyRecord.setDepartmentName(zjTzPolicyCountryReplyRecord.getDepartmentName());
           // 原文发布日期
           dbzjTzPolicyCountryReplyRecord.setReleaseDate(zjTzPolicyCountryReplyRecord.getReleaseDate());
           // 登记用户
           dbzjTzPolicyCountryReplyRecord.setRegisterUser(zjTzPolicyCountryReplyRecord.getRegisterUser());
           // 有效文件
           dbzjTzPolicyCountryReplyRecord.setEffectiveId(zjTzPolicyCountryReplyRecord.getEffectiveId());
           // 有效文件名称
           dbzjTzPolicyCountryReplyRecord.setEffectiveName(zjTzPolicyCountryReplyRecord.getEffectiveName());
           // 分析报告
           dbzjTzPolicyCountryReplyRecord.setReport(zjTzPolicyCountryReplyRecord.getReport());
           // userKey
           dbzjTzPolicyCountryReplyRecord.setValue(zjTzPolicyCountryReplyRecord.getValue());
           // userName
           dbzjTzPolicyCountryReplyRecord.setLabel(zjTzPolicyCountryReplyRecord.getLabel());
           // 类型
           dbzjTzPolicyCountryReplyRecord.setType(zjTzPolicyCountryReplyRecord.getType());
           // 回复内容
           dbzjTzPolicyCountryReplyRecord.setReplyInfo(zjTzPolicyCountryReplyRecord.getReplyInfo());
           // 共通
           dbzjTzPolicyCountryReplyRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyCountryReplyRecordMapper.updateByPrimaryKey(dbzjTzPolicyCountryReplyRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyCountryReplyRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPolicyCountryReplyRecord(List<ZjTzPolicyCountryReplyRecord> zjTzPolicyCountryReplyRecordList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyCountryReplyRecordList != null && zjTzPolicyCountryReplyRecordList.size() > 0) {
           ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord = new ZjTzPolicyCountryReplyRecord();
           zjTzPolicyCountryReplyRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyCountryReplyRecordMapper.batchDeleteUpdateZjTzPolicyCountryReplyRecord(zjTzPolicyCountryReplyRecordList, zjTzPolicyCountryReplyRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPolicyCountryReplyRecordList);
        }
    }
}
