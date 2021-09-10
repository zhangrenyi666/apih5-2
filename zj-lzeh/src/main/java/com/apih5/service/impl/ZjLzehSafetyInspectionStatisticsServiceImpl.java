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
import com.apih5.mybatis.dao.ZjLzehSafetyInspectionStatisticsMapper;
import com.apih5.mybatis.pojo.ZjLzehSafetyInspectionStatistics;
import com.apih5.service.ZjLzehSafetyInspectionStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehSafetyInspectionStatisticsService")
public class ZjLzehSafetyInspectionStatisticsServiceImpl implements ZjLzehSafetyInspectionStatisticsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehSafetyInspectionStatisticsMapper zjLzehSafetyInspectionStatisticsMapper;

    @Override
    public ResponseEntity getZjLzehSafetyInspectionStatisticsListByCondition(ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics) {
        if (zjLzehSafetyInspectionStatistics == null) {
            zjLzehSafetyInspectionStatistics = new ZjLzehSafetyInspectionStatistics();
        }
        // 分页查询
        PageHelper.startPage(zjLzehSafetyInspectionStatistics.getPage(),zjLzehSafetyInspectionStatistics.getLimit());
        // 获取数据
        List<ZjLzehSafetyInspectionStatistics> zjLzehSafetyInspectionStatisticsList = zjLzehSafetyInspectionStatisticsMapper.selectByZjLzehSafetyInspectionStatisticsList(zjLzehSafetyInspectionStatistics);
        // 得到分页信息
        PageInfo<ZjLzehSafetyInspectionStatistics> p = new PageInfo<>(zjLzehSafetyInspectionStatisticsList);

        return repEntity.okList(zjLzehSafetyInspectionStatisticsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehSafetyInspectionStatisticsDetails(ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics) {
        if (zjLzehSafetyInspectionStatistics == null) {
            zjLzehSafetyInspectionStatistics = new ZjLzehSafetyInspectionStatistics();
        }
        // 获取数据
        ZjLzehSafetyInspectionStatistics dbZjLzehSafetyInspectionStatistics = zjLzehSafetyInspectionStatisticsMapper.selectByPrimaryKey(zjLzehSafetyInspectionStatistics.getSafetyInspectionStatisticsId());
        // 数据存在
        if (dbZjLzehSafetyInspectionStatistics != null) {
            return repEntity.ok(dbZjLzehSafetyInspectionStatistics);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehSafetyInspectionStatistics(ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehSafetyInspectionStatistics.setSafetyInspectionStatisticsId(UuidUtil.generate());
        zjLzehSafetyInspectionStatistics.setCreateUserInfo(userKey, realName);
        int flag = zjLzehSafetyInspectionStatisticsMapper.insert(zjLzehSafetyInspectionStatistics);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehSafetyInspectionStatistics);
        }
    }

    @Override
    public ResponseEntity updateZjLzehSafetyInspectionStatistics(ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehSafetyInspectionStatistics dbzjLzehSafetyInspectionStatistics = zjLzehSafetyInspectionStatisticsMapper.selectByPrimaryKey(zjLzehSafetyInspectionStatistics.getSafetyInspectionStatisticsId());
        if (dbzjLzehSafetyInspectionStatistics != null && StrUtil.isNotEmpty(dbzjLzehSafetyInspectionStatistics.getSafetyInspectionStatisticsId())) {
           // 排序
           dbzjLzehSafetyInspectionStatistics.setOrderFlag(zjLzehSafetyInspectionStatistics.getOrderFlag());
           // 名称
           dbzjLzehSafetyInspectionStatistics.setName(zjLzehSafetyInspectionStatistics.getName());
           // 总计数量
           dbzjLzehSafetyInspectionStatistics.setTotalNumber(zjLzehSafetyInspectionStatistics.getTotalNumber());
           // 解除数量
           dbzjLzehSafetyInspectionStatistics.setRemoveNumber(zjLzehSafetyInspectionStatistics.getRemoveNumber());
           // 共通
           dbzjLzehSafetyInspectionStatistics.setModifyUserInfo(userKey, realName);
           flag = zjLzehSafetyInspectionStatisticsMapper.updateByPrimaryKey(dbzjLzehSafetyInspectionStatistics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehSafetyInspectionStatistics);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehSafetyInspectionStatistics(List<ZjLzehSafetyInspectionStatistics> zjLzehSafetyInspectionStatisticsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehSafetyInspectionStatisticsList != null && zjLzehSafetyInspectionStatisticsList.size() > 0) {
           ZjLzehSafetyInspectionStatistics zjLzehSafetyInspectionStatistics = new ZjLzehSafetyInspectionStatistics();
           zjLzehSafetyInspectionStatistics.setModifyUserInfo(userKey, realName);
           flag = zjLzehSafetyInspectionStatisticsMapper.batchDeleteUpdateZjLzehSafetyInspectionStatistics(zjLzehSafetyInspectionStatisticsList, zjLzehSafetyInspectionStatistics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehSafetyInspectionStatisticsList);
        }
    }
}
