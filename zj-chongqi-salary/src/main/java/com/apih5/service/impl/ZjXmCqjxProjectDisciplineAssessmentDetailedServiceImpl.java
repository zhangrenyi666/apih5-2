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
import com.apih5.mybatis.dao.ZjXmCqjxProjectDisciplineAssessmentDetailedMapper;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessmentDetailed;
import com.apih5.service.ZjXmCqjxProjectDisciplineAssessmentDetailedService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmCqjxProjectDisciplineAssessmentDetailedService")
public class ZjXmCqjxProjectDisciplineAssessmentDetailedServiceImpl implements ZjXmCqjxProjectDisciplineAssessmentDetailedService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentDetailedMapper zjXmCqjxProjectDisciplineAssessmentDetailedMapper;

    @Override
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentDetailedListByCondition(ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed) {
        if (zjXmCqjxProjectDisciplineAssessmentDetailed == null) {
            zjXmCqjxProjectDisciplineAssessmentDetailed = new ZjXmCqjxProjectDisciplineAssessmentDetailed();
        }
        // 分页查询
        PageHelper.startPage(zjXmCqjxProjectDisciplineAssessmentDetailed.getPage(),zjXmCqjxProjectDisciplineAssessmentDetailed.getLimit());
        // 获取数据
        List<ZjXmCqjxProjectDisciplineAssessmentDetailed> zjXmCqjxProjectDisciplineAssessmentDetailedList = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.selectByZjXmCqjxProjectDisciplineAssessmentDetailedList(zjXmCqjxProjectDisciplineAssessmentDetailed);
        // 得到分页信息
        PageInfo<ZjXmCqjxProjectDisciplineAssessmentDetailed> p = new PageInfo<>(zjXmCqjxProjectDisciplineAssessmentDetailedList);

        return repEntity.okList(zjXmCqjxProjectDisciplineAssessmentDetailedList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentDetailedDetails(ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed) {
        if (zjXmCqjxProjectDisciplineAssessmentDetailed == null) {
            zjXmCqjxProjectDisciplineAssessmentDetailed = new ZjXmCqjxProjectDisciplineAssessmentDetailed();
        }
        // 获取数据
        ZjXmCqjxProjectDisciplineAssessmentDetailed dbZjXmCqjxProjectDisciplineAssessmentDetailed = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessmentDetailed.getDisciplineDetailedId());
        // 数据存在
        if (dbZjXmCqjxProjectDisciplineAssessmentDetailed != null) {
            return repEntity.ok(dbZjXmCqjxProjectDisciplineAssessmentDetailed);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmCqjxProjectDisciplineAssessmentDetailed(ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmCqjxProjectDisciplineAssessmentDetailed.setDisciplineDetailedId(UuidUtil.generate());
        zjXmCqjxProjectDisciplineAssessmentDetailed.setCreateUserInfo(userKey, realName);
        int flag = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.insert(zjXmCqjxProjectDisciplineAssessmentDetailed);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmCqjxProjectDisciplineAssessmentDetailed);
        }
    }

    @Override
    public ResponseEntity updateZjXmCqjxProjectDisciplineAssessmentDetailed(ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmCqjxProjectDisciplineAssessmentDetailed dbzjXmCqjxProjectDisciplineAssessmentDetailed = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.selectByPrimaryKey(zjXmCqjxProjectDisciplineAssessmentDetailed.getDisciplineDetailedId());
        if (dbzjXmCqjxProjectDisciplineAssessmentDetailed != null && StrUtil.isNotEmpty(dbzjXmCqjxProjectDisciplineAssessmentDetailed.getDisciplineDetailedId())) {
           // 纪律性考核ID
           dbzjXmCqjxProjectDisciplineAssessmentDetailed.setDisciplineId(zjXmCqjxProjectDisciplineAssessmentDetailed.getDisciplineId());
           // 员工ID
           dbzjXmCqjxProjectDisciplineAssessmentDetailed.setUserKey(zjXmCqjxProjectDisciplineAssessmentDetailed.getUserKey());
           // 员工名称
           dbzjXmCqjxProjectDisciplineAssessmentDetailed.setUserName(zjXmCqjxProjectDisciplineAssessmentDetailed.getUserName());
           // 所在部门
           dbzjXmCqjxProjectDisciplineAssessmentDetailed.setDepartmentName(zjXmCqjxProjectDisciplineAssessmentDetailed.getDepartmentName());
           // 手机号
           dbzjXmCqjxProjectDisciplineAssessmentDetailed.setMobile(zjXmCqjxProjectDisciplineAssessmentDetailed.getMobile());
           // 评分
           dbzjXmCqjxProjectDisciplineAssessmentDetailed.setDisciplineDetailedScore(zjXmCqjxProjectDisciplineAssessmentDetailed.getDisciplineDetailedScore());
           // 备注
           dbzjXmCqjxProjectDisciplineAssessmentDetailed.setDisciplineDetailedContent(zjXmCqjxProjectDisciplineAssessmentDetailed.getDisciplineDetailedContent());
           // 共通
           dbzjXmCqjxProjectDisciplineAssessmentDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.updateByPrimaryKey(dbzjXmCqjxProjectDisciplineAssessmentDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmCqjxProjectDisciplineAssessmentDetailed);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentDetailed(List<ZjXmCqjxProjectDisciplineAssessmentDetailed> zjXmCqjxProjectDisciplineAssessmentDetailedList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmCqjxProjectDisciplineAssessmentDetailedList != null && zjXmCqjxProjectDisciplineAssessmentDetailedList.size() > 0) {
           ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed = new ZjXmCqjxProjectDisciplineAssessmentDetailed();
           zjXmCqjxProjectDisciplineAssessmentDetailed.setModifyUserInfo(userKey, realName);
           flag = zjXmCqjxProjectDisciplineAssessmentDetailedMapper.batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentDetailed(zjXmCqjxProjectDisciplineAssessmentDetailedList, zjXmCqjxProjectDisciplineAssessmentDetailed);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmCqjxProjectDisciplineAssessmentDetailedList);
        }
    }
}