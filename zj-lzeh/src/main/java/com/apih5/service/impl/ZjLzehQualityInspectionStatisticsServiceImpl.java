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
import com.apih5.mybatis.dao.ZjLzehQualityInspectionStatisticsMapper;
import com.apih5.mybatis.pojo.ZjLzehQualityInspectionStatistics;
import com.apih5.service.ZjLzehQualityInspectionStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehQualityInspectionStatisticsService")
public class ZjLzehQualityInspectionStatisticsServiceImpl implements ZjLzehQualityInspectionStatisticsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehQualityInspectionStatisticsMapper zjLzehQualityInspectionStatisticsMapper;

    @Override
    public ResponseEntity getZjLzehQualityInspectionStatisticsListByCondition(ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics) {
        if (zjLzehQualityInspectionStatistics == null) {
            zjLzehQualityInspectionStatistics = new ZjLzehQualityInspectionStatistics();
        }
        // 分页查询
        PageHelper.startPage(zjLzehQualityInspectionStatistics.getPage(),zjLzehQualityInspectionStatistics.getLimit());
        // 获取数据
        List<ZjLzehQualityInspectionStatistics> zjLzehQualityInspectionStatisticsList = zjLzehQualityInspectionStatisticsMapper.selectByZjLzehQualityInspectionStatisticsList(zjLzehQualityInspectionStatistics);
        // 得到分页信息
        PageInfo<ZjLzehQualityInspectionStatistics> p = new PageInfo<>(zjLzehQualityInspectionStatisticsList);

        return repEntity.okList(zjLzehQualityInspectionStatisticsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehQualityInspectionStatisticsDetails(ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics) {
        if (zjLzehQualityInspectionStatistics == null) {
            zjLzehQualityInspectionStatistics = new ZjLzehQualityInspectionStatistics();
        }
        // 获取数据
        ZjLzehQualityInspectionStatistics dbZjLzehQualityInspectionStatistics = zjLzehQualityInspectionStatisticsMapper.selectByPrimaryKey(zjLzehQualityInspectionStatistics.getQualityInspectionStatisticsId());
        // 数据存在
        if (dbZjLzehQualityInspectionStatistics != null) {
            return repEntity.ok(dbZjLzehQualityInspectionStatistics);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehQualityInspectionStatistics(ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehQualityInspectionStatistics.setQualityInspectionStatisticsId(UuidUtil.generate());
        zjLzehQualityInspectionStatistics.setCreateUserInfo(userKey, realName);
        int flag = zjLzehQualityInspectionStatisticsMapper.insert(zjLzehQualityInspectionStatistics);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehQualityInspectionStatistics);
        }
    }

    @Override
    public ResponseEntity updateZjLzehQualityInspectionStatistics(ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehQualityInspectionStatistics dbzjLzehQualityInspectionStatistics = zjLzehQualityInspectionStatisticsMapper.selectByPrimaryKey(zjLzehQualityInspectionStatistics.getQualityInspectionStatisticsId());
        if (dbzjLzehQualityInspectionStatistics != null && StrUtil.isNotEmpty(dbzjLzehQualityInspectionStatistics.getQualityInspectionStatisticsId())) {
           // 排序
           dbzjLzehQualityInspectionStatistics.setOrderFlag(zjLzehQualityInspectionStatistics.getOrderFlag());
           // 名称
           dbzjLzehQualityInspectionStatistics.setName(zjLzehQualityInspectionStatistics.getName());
           // 总计数量
           dbzjLzehQualityInspectionStatistics.setTotalNumber(zjLzehQualityInspectionStatistics.getTotalNumber());
           // 解除数量
           dbzjLzehQualityInspectionStatistics.setRemoveNumber(zjLzehQualityInspectionStatistics.getRemoveNumber());
           // 共通
           dbzjLzehQualityInspectionStatistics.setModifyUserInfo(userKey, realName);
           flag = zjLzehQualityInspectionStatisticsMapper.updateByPrimaryKey(dbzjLzehQualityInspectionStatistics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehQualityInspectionStatistics);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehQualityInspectionStatistics(List<ZjLzehQualityInspectionStatistics> zjLzehQualityInspectionStatisticsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehQualityInspectionStatisticsList != null && zjLzehQualityInspectionStatisticsList.size() > 0) {
           ZjLzehQualityInspectionStatistics zjLzehQualityInspectionStatistics = new ZjLzehQualityInspectionStatistics();
           zjLzehQualityInspectionStatistics.setModifyUserInfo(userKey, realName);
           flag = zjLzehQualityInspectionStatisticsMapper.batchDeleteUpdateZjLzehQualityInspectionStatistics(zjLzehQualityInspectionStatisticsList, zjLzehQualityInspectionStatistics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehQualityInspectionStatisticsList);
        }
    }
}
