package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzContractConditionRecordMapper;
import com.apih5.mybatis.pojo.ZjTzContractConditionRecord;
import com.apih5.service.ZjTzContractConditionRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzContractConditionRecordService")
public class ZjTzContractConditionRecordServiceImpl implements ZjTzContractConditionRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzContractConditionRecordMapper zjTzContractConditionRecordMapper;
    
    @Override
    public ResponseEntity getZjTzContractConditionRecordListByCondition(ZjTzContractConditionRecord zjTzContractConditionRecord) {
        if (zjTzContractConditionRecord == null) {
            zjTzContractConditionRecord = new ZjTzContractConditionRecord();
        }
        // 分页查询
        PageHelper.startPage(zjTzContractConditionRecord.getPage(),zjTzContractConditionRecord.getLimit());
        // 获取数据
        List<ZjTzContractConditionRecord> zjTzContractConditionRecordList = zjTzContractConditionRecordMapper.selectByZjTzContractConditionRecordList(zjTzContractConditionRecord);
        // 得到分页信息
        PageInfo<ZjTzContractConditionRecord> p = new PageInfo<>(zjTzContractConditionRecordList);

        return repEntity.okList(zjTzContractConditionRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzContractConditionRecordDetails(ZjTzContractConditionRecord zjTzContractConditionRecord) {
        if (zjTzContractConditionRecord == null) {
            zjTzContractConditionRecord = new ZjTzContractConditionRecord();
        }
        // 获取数据
        ZjTzContractConditionRecord dbZjTzContractConditionRecord = zjTzContractConditionRecordMapper.selectByPrimaryKey(zjTzContractConditionRecord.getContractConditionRecordId());
        // 数据存在
        if (dbZjTzContractConditionRecord != null) {
            return repEntity.ok(dbZjTzContractConditionRecord);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzContractConditionRecord(ZjTzContractConditionRecord zjTzContractConditionRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzContractConditionRecord.setContractConditionRecordId(UuidUtil.generate());
        zjTzContractConditionRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzContractConditionRecordMapper.insert(zjTzContractConditionRecord);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzContractConditionRecord);
        }
    }

    @Override
    public ResponseEntity updateZjTzContractConditionRecord(ZjTzContractConditionRecord zjTzContractConditionRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzContractConditionRecord dbzjTzContractConditionRecord = zjTzContractConditionRecordMapper.selectByPrimaryKey(zjTzContractConditionRecord.getContractConditionRecordId());
        if (dbzjTzContractConditionRecord != null && StrUtil.isNotEmpty(dbzjTzContractConditionRecord.getContractConditionRecordId())) {
           // 投资规模控制记录id
           dbzjTzContractConditionRecord.setSizeControlRecordId(zjTzContractConditionRecord.getSizeControlRecordId());
           // 投资规模控制id
           dbzjTzContractConditionRecord.setSizeControlId(zjTzContractConditionRecord.getSizeControlId());
           // 项目id
           dbzjTzContractConditionRecord.setProjectId(zjTzContractConditionRecord.getProjectId());
           // 登记日期
           dbzjTzContractConditionRecord.setRegisterDate(zjTzContractConditionRecord.getRegisterDate());
           // 登记人
           dbzjTzContractConditionRecord.setRegistrant(zjTzContractConditionRecord.getRegistrant());
           // 投资收益模式id
           dbzjTzContractConditionRecord.setInvestId(zjTzContractConditionRecord.getInvestId());
           // 投资收益模式name
           dbzjTzContractConditionRecord.setInvestName(zjTzContractConditionRecord.getInvestName());
           // 一公局集团股比
           dbzjTzContractConditionRecord.setJuShare(zjTzContractConditionRecord.getJuShare());
           // 一公局集团控制性地位id
           dbzjTzContractConditionRecord.setJuId(zjTzContractConditionRecord.getJuId());
           // 一公局集团控制性地位name
           dbzjTzContractConditionRecord.setJuName(zjTzContractConditionRecord.getJuName());
           // 总承包结算模式id
           dbzjTzContractConditionRecord.setZcbId(zjTzContractConditionRecord.getZcbId());
           // 总承包结算模式name
           dbzjTzContractConditionRecord.setZcbName(zjTzContractConditionRecord.getZcbName());
           // 施工总承包比例
           dbzjTzContractConditionRecord.setZcbShare(zjTzContractConditionRecord.getZcbShare());
           // 设计管理情况
           dbzjTzContractConditionRecord.setExt1(zjTzContractConditionRecord.getExt1());
           // 合同对投资规模控制的要求
           dbzjTzContractConditionRecord.setExt2(zjTzContractConditionRecord.getExt2());
           // 设计变更特殊条款
           dbzjTzContractConditionRecord.setExt3(zjTzContractConditionRecord.getExt3());
           // 共通
           dbzjTzContractConditionRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzContractConditionRecordMapper.updateByPrimaryKey(dbzjTzContractConditionRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzContractConditionRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzContractConditionRecord(List<ZjTzContractConditionRecord> zjTzContractConditionRecordList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzContractConditionRecordList != null && zjTzContractConditionRecordList.size() > 0) {
           ZjTzContractConditionRecord zjTzContractConditionRecord = new ZjTzContractConditionRecord();
           zjTzContractConditionRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzContractConditionRecordMapper.batchDeleteUpdateZjTzContractConditionRecord(zjTzContractConditionRecordList, zjTzContractConditionRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzContractConditionRecordList);
        }
    }
}
