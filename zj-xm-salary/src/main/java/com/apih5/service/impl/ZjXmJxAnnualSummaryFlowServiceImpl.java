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
import com.apih5.mybatis.dao.ZjXmJxAnnualSummaryFlowMapper;
import com.apih5.mybatis.pojo.ZjXmJxAnnualSummaryFlow;
import com.apih5.service.ZjXmJxAnnualSummaryFlowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxAnnualSummaryFlowService")
public class ZjXmJxAnnualSummaryFlowServiceImpl implements ZjXmJxAnnualSummaryFlowService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmJxAnnualSummaryFlowMapper zjXmJxAnnualSummaryFlowMapper;

    @Override
    public ResponseEntity getZjXmJxAnnualSummaryFlowListByCondition(ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow) {
        if (zjXmJxAnnualSummaryFlow == null) {
            zjXmJxAnnualSummaryFlow = new ZjXmJxAnnualSummaryFlow();
        }
        // 分页查询
        PageHelper.startPage(zjXmJxAnnualSummaryFlow.getPage(),zjXmJxAnnualSummaryFlow.getLimit());
        // 获取数据
        List<ZjXmJxAnnualSummaryFlow> zjXmJxAnnualSummaryFlowList = zjXmJxAnnualSummaryFlowMapper.selectByZjXmJxAnnualSummaryFlowList(zjXmJxAnnualSummaryFlow);
        // 得到分页信息
        PageInfo<ZjXmJxAnnualSummaryFlow> p = new PageInfo<>(zjXmJxAnnualSummaryFlowList);

        return repEntity.okList(zjXmJxAnnualSummaryFlowList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmJxAnnualSummaryFlowDetails(ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow) {
        if (zjXmJxAnnualSummaryFlow == null) {
            zjXmJxAnnualSummaryFlow = new ZjXmJxAnnualSummaryFlow();
        }
        // 获取数据
        ZjXmJxAnnualSummaryFlow dbZjXmJxAnnualSummaryFlow = zjXmJxAnnualSummaryFlowMapper.selectByPrimaryKey(zjXmJxAnnualSummaryFlow.getAnnualFlowId());
        // 数据存在
        if (dbZjXmJxAnnualSummaryFlow != null) {
            return repEntity.ok(dbZjXmJxAnnualSummaryFlow);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmJxAnnualSummaryFlow(ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmJxAnnualSummaryFlow.setAnnualFlowId(UuidUtil.generate());
        zjXmJxAnnualSummaryFlow.setCreateUserInfo(userKey, realName);
        int flag = zjXmJxAnnualSummaryFlowMapper.insert(zjXmJxAnnualSummaryFlow);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmJxAnnualSummaryFlow);
        }
    }

    @Override
    public ResponseEntity updateZjXmJxAnnualSummaryFlow(ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmJxAnnualSummaryFlow dbzjXmJxAnnualSummaryFlow = zjXmJxAnnualSummaryFlowMapper.selectByPrimaryKey(zjXmJxAnnualSummaryFlow.getAnnualFlowId());
        if (dbzjXmJxAnnualSummaryFlow != null && StrUtil.isNotEmpty(dbzjXmJxAnnualSummaryFlow.getAnnualFlowId())) {
           // 考核年份
           dbzjXmJxAnnualSummaryFlow.setYear(zjXmJxAnnualSummaryFlow.getYear());
           // 项目id
           dbzjXmJxAnnualSummaryFlow.setProjectId(zjXmJxAnnualSummaryFlow.getProjectId());
           // 项目名称
           dbzjXmJxAnnualSummaryFlow.setProjectName(zjXmJxAnnualSummaryFlow.getProjectName());
           // 部门id
           dbzjXmJxAnnualSummaryFlow.setDeptId(zjXmJxAnnualSummaryFlow.getDeptId());
           // 部门名称
           dbzjXmJxAnnualSummaryFlow.setDeptName(zjXmJxAnnualSummaryFlow.getDeptName());
           // 汇总审核表类型
           dbzjXmJxAnnualSummaryFlow.setFlowType(zjXmJxAnnualSummaryFlow.getFlowType());
           // 备注
           dbzjXmJxAnnualSummaryFlow.setOpinionField1(zjXmJxAnnualSummaryFlow.getOpinionField1());
           // 备注
           dbzjXmJxAnnualSummaryFlow.setOpinionField2(zjXmJxAnnualSummaryFlow.getOpinionField2());
           // 备注
           dbzjXmJxAnnualSummaryFlow.setOpinionField3(zjXmJxAnnualSummaryFlow.getOpinionField3());
           // 备注
           dbzjXmJxAnnualSummaryFlow.setOpinionField4(zjXmJxAnnualSummaryFlow.getOpinionField4());
           // 备注
           dbzjXmJxAnnualSummaryFlow.setOpinionField5(zjXmJxAnnualSummaryFlow.getOpinionField5());
           // 流程id
           dbzjXmJxAnnualSummaryFlow.setApih5FlowId(zjXmJxAnnualSummaryFlow.getApih5FlowId());
           // work_id
           dbzjXmJxAnnualSummaryFlow.setWorkId(zjXmJxAnnualSummaryFlow.getWorkId());
           // 工序审核状态
           dbzjXmJxAnnualSummaryFlow.setApih5FlowStatus(zjXmJxAnnualSummaryFlow.getApih5FlowStatus());
           // 流程状态
           dbzjXmJxAnnualSummaryFlow.setApih5FlowNodeStatus(zjXmJxAnnualSummaryFlow.getApih5FlowNodeStatus());
           // 备注
           dbzjXmJxAnnualSummaryFlow.setRemarks(zjXmJxAnnualSummaryFlow.getRemarks());
           // 排序
           dbzjXmJxAnnualSummaryFlow.setSort(zjXmJxAnnualSummaryFlow.getSort());
           // 共通
           dbzjXmJxAnnualSummaryFlow.setModifyUserInfo(userKey, realName);
           flag = zjXmJxAnnualSummaryFlowMapper.updateByPrimaryKey(dbzjXmJxAnnualSummaryFlow);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmJxAnnualSummaryFlow);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmJxAnnualSummaryFlow(List<ZjXmJxAnnualSummaryFlow> zjXmJxAnnualSummaryFlowList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmJxAnnualSummaryFlowList != null && zjXmJxAnnualSummaryFlowList.size() > 0) {
           ZjXmJxAnnualSummaryFlow zjXmJxAnnualSummaryFlow = new ZjXmJxAnnualSummaryFlow();
           zjXmJxAnnualSummaryFlow.setModifyUserInfo(userKey, realName);
           flag = zjXmJxAnnualSummaryFlowMapper.batchDeleteUpdateZjXmJxAnnualSummaryFlow(zjXmJxAnnualSummaryFlowList, zjXmJxAnnualSummaryFlow);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmJxAnnualSummaryFlowList);
        }
    }
}