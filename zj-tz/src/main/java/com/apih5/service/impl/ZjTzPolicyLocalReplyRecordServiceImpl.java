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
import com.apih5.mybatis.dao.ZjTzPolicyLocalReplyRecordMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReplyRecord;
import com.apih5.service.ZjTzPolicyLocalReplyRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPolicyLocalReplyRecordService")
public class ZjTzPolicyLocalReplyRecordServiceImpl implements ZjTzPolicyLocalReplyRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPolicyLocalReplyRecordMapper zjTzPolicyLocalReplyRecordMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzPolicyLocalReplyRecordListByCondition(ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord) {
        if (zjTzPolicyLocalReplyRecord == null) {
            zjTzPolicyLocalReplyRecord = new ZjTzPolicyLocalReplyRecord();
        }
        // 分页查询
        PageHelper.startPage(zjTzPolicyLocalReplyRecord.getPage(),zjTzPolicyLocalReplyRecord.getLimit());
        // 获取数据
        List<ZjTzPolicyLocalReplyRecord> zjTzPolicyLocalReplyRecordList = zjTzPolicyLocalReplyRecordMapper.selectByZjTzPolicyLocalReplyRecordList(zjTzPolicyLocalReplyRecord);
        for (ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord2 : zjTzPolicyLocalReplyRecordList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherType("2");
        	zjTzFileSelect.setOtherId(zjTzPolicyLocalReplyRecord2.getPolicyReplyId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzPolicyLocalReplyRecord2.setZjTzFileList(zjTzFileList);
		}
        // 得到分页信息
        PageInfo<ZjTzPolicyLocalReplyRecord> p = new PageInfo<>(zjTzPolicyLocalReplyRecordList);

        return repEntity.okList(zjTzPolicyLocalReplyRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPolicyLocalReplyRecordDetails(ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord) {
        if (zjTzPolicyLocalReplyRecord == null) {
            zjTzPolicyLocalReplyRecord = new ZjTzPolicyLocalReplyRecord();
        }
        // 获取数据
        ZjTzPolicyLocalReplyRecord dbZjTzPolicyLocalReplyRecord = zjTzPolicyLocalReplyRecordMapper.selectByPrimaryKey(zjTzPolicyLocalReplyRecord.getRecordId());
        // 数据存在
        if (dbZjTzPolicyLocalReplyRecord != null) {
            return repEntity.ok(dbZjTzPolicyLocalReplyRecord);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPolicyLocalReplyRecord(ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPolicyLocalReplyRecord.setRecordId(UuidUtil.generate());
        zjTzPolicyLocalReplyRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzPolicyLocalReplyRecordMapper.insert(zjTzPolicyLocalReplyRecord);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPolicyLocalReplyRecord);
        }
    }

    @Override
    public ResponseEntity updateZjTzPolicyLocalReplyRecord(ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyLocalReplyRecord dbzjTzPolicyLocalReplyRecord = zjTzPolicyLocalReplyRecordMapper.selectByPrimaryKey(zjTzPolicyLocalReplyRecord.getRecordId());
        if (dbzjTzPolicyLocalReplyRecord != null && StrUtil.isNotEmpty(dbzjTzPolicyLocalReplyRecord.getRecordId())) {
           // 回复ID
           dbzjTzPolicyLocalReplyRecord.setPolicyReplyId(zjTzPolicyLocalReplyRecord.getPolicyReplyId());
           // 地方政策ID
           dbzjTzPolicyLocalReplyRecord.setPolicyId(zjTzPolicyLocalReplyRecord.getPolicyId());
           // 标题
           dbzjTzPolicyLocalReplyRecord.setTitle(zjTzPolicyLocalReplyRecord.getTitle());
           // 文号
           dbzjTzPolicyLocalReplyRecord.setSymbolNo(zjTzPolicyLocalReplyRecord.getSymbolNo());
           // 系统发布日期
           dbzjTzPolicyLocalReplyRecord.setSysDate(zjTzPolicyLocalReplyRecord.getSysDate());
           // 发文部门
           dbzjTzPolicyLocalReplyRecord.setDepartmentName(zjTzPolicyLocalReplyRecord.getDepartmentName());
           // 原文发布日期
           dbzjTzPolicyLocalReplyRecord.setReleaseDate(zjTzPolicyLocalReplyRecord.getReleaseDate());
           // 登记用户
           dbzjTzPolicyLocalReplyRecord.setRegisterUser(zjTzPolicyLocalReplyRecord.getRegisterUser());
           // 有效文件
           dbzjTzPolicyLocalReplyRecord.setEffectiveId(zjTzPolicyLocalReplyRecord.getEffectiveId());
           // 有效文件名称
           dbzjTzPolicyLocalReplyRecord.setEffectiveName(zjTzPolicyLocalReplyRecord.getEffectiveName());
           // 分析报告
           dbzjTzPolicyLocalReplyRecord.setReport(zjTzPolicyLocalReplyRecord.getReport());
           // 公司ID
           dbzjTzPolicyLocalReplyRecord.setCompanyId(zjTzPolicyLocalReplyRecord.getCompanyId());
           // 公司名称
           dbzjTzPolicyLocalReplyRecord.setCompanyName(zjTzPolicyLocalReplyRecord.getCompanyName());
           // 项目ID
           dbzjTzPolicyLocalReplyRecord.setProjectId(zjTzPolicyLocalReplyRecord.getProjectId());
           // 项目名称
           dbzjTzPolicyLocalReplyRecord.setProjectName(zjTzPolicyLocalReplyRecord.getProjectName());
           // userKey
           dbzjTzPolicyLocalReplyRecord.setValue(zjTzPolicyLocalReplyRecord.getValue());
           // userName
           dbzjTzPolicyLocalReplyRecord.setLabel(zjTzPolicyLocalReplyRecord.getLabel());
           // 类型
           dbzjTzPolicyLocalReplyRecord.setType(zjTzPolicyLocalReplyRecord.getType());
           // 回复内容
           dbzjTzPolicyLocalReplyRecord.setReplyInfo(zjTzPolicyLocalReplyRecord.getReplyInfo());
           // 回复时间
           dbzjTzPolicyLocalReplyRecord.setReplyTime(zjTzPolicyLocalReplyRecord.getReplyTime());
           // 共通
           dbzjTzPolicyLocalReplyRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyLocalReplyRecordMapper.updateByPrimaryKey(dbzjTzPolicyLocalReplyRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyLocalReplyRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPolicyLocalReplyRecord(List<ZjTzPolicyLocalReplyRecord> zjTzPolicyLocalReplyRecordList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyLocalReplyRecordList != null && zjTzPolicyLocalReplyRecordList.size() > 0) {
           ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord = new ZjTzPolicyLocalReplyRecord();
           zjTzPolicyLocalReplyRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyLocalReplyRecordMapper.batchDeleteUpdateZjTzPolicyLocalReplyRecord(zjTzPolicyLocalReplyRecordList, zjTzPolicyLocalReplyRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPolicyLocalReplyRecordList);
        }
    }
}
