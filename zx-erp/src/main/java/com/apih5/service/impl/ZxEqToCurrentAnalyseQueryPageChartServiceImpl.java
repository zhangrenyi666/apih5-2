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
import com.apih5.mybatis.dao.ZxEqToCurrentAnalyseQueryPageChartMapper;
import com.apih5.mybatis.pojo.ZxEqToCurrentAnalyseQueryPageChart;
import com.apih5.service.ZxEqToCurrentAnalyseQueryPageChartService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqToCurrentAnalyseQueryPageChartService")
public class ZxEqToCurrentAnalyseQueryPageChartServiceImpl implements ZxEqToCurrentAnalyseQueryPageChartService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqToCurrentAnalyseQueryPageChartMapper zxEqToCurrentAnalyseQueryPageChartMapper;

    @Override
    public ResponseEntity getZxEqToCurrentAnalyseQueryPageChartListByCondition(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        if (zxEqToCurrentAnalyseQueryPageChart == null) {
            zxEqToCurrentAnalyseQueryPageChart = new ZxEqToCurrentAnalyseQueryPageChart();
        }
        // 分页查询
        PageHelper.startPage(zxEqToCurrentAnalyseQueryPageChart.getPage(),zxEqToCurrentAnalyseQueryPageChart.getLimit());
        // 获取数据
        List<ZxEqToCurrentAnalyseQueryPageChart> zxEqToCurrentAnalyseQueryPageChartList = zxEqToCurrentAnalyseQueryPageChartMapper.selectByZxEqToCurrentAnalyseQueryPageChartList(zxEqToCurrentAnalyseQueryPageChart);
        // 得到分页信息
        PageInfo<ZxEqToCurrentAnalyseQueryPageChart> p = new PageInfo<>(zxEqToCurrentAnalyseQueryPageChartList);

        return repEntity.okList(zxEqToCurrentAnalyseQueryPageChartList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqToCurrentAnalyseQueryPageChartDetail(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        if (zxEqToCurrentAnalyseQueryPageChart == null) {
            zxEqToCurrentAnalyseQueryPageChart = new ZxEqToCurrentAnalyseQueryPageChart();
        }
        // 获取数据
        ZxEqToCurrentAnalyseQueryPageChart dbZxEqToCurrentAnalyseQueryPageChart = zxEqToCurrentAnalyseQueryPageChartMapper.selectByPrimaryKey(zxEqToCurrentAnalyseQueryPageChart.getZxEqToCurrentAnalyseQueryPageChartId());
        // 数据存在
        if (dbZxEqToCurrentAnalyseQueryPageChart != null) {
            return repEntity.ok(dbZxEqToCurrentAnalyseQueryPageChart);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqToCurrentAnalyseQueryPageChart(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqToCurrentAnalyseQueryPageChart.setZxEqToCurrentAnalyseQueryPageChartId(UuidUtil.generate());
        zxEqToCurrentAnalyseQueryPageChart.setCreateUserInfo(userKey, realName);
        int flag = zxEqToCurrentAnalyseQueryPageChartMapper.insert(zxEqToCurrentAnalyseQueryPageChart);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqToCurrentAnalyseQueryPageChart);
        }
    }

    @Override
    public ResponseEntity updateZxEqToCurrentAnalyseQueryPageChart(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqToCurrentAnalyseQueryPageChart dbZxEqToCurrentAnalyseQueryPageChart = zxEqToCurrentAnalyseQueryPageChartMapper.selectByPrimaryKey(zxEqToCurrentAnalyseQueryPageChart.getZxEqToCurrentAnalyseQueryPageChartId());
        if (dbZxEqToCurrentAnalyseQueryPageChart != null && StrUtil.isNotEmpty(dbZxEqToCurrentAnalyseQueryPageChart.getZxEqToCurrentAnalyseQueryPageChartId())) {
           // 年
           dbZxEqToCurrentAnalyseQueryPageChart.setYear(zxEqToCurrentAnalyseQueryPageChart.getYear());
           // 原值
           dbZxEqToCurrentAnalyseQueryPageChart.setAmt(zxEqToCurrentAnalyseQueryPageChart.getAmt());
           // 备注
           dbZxEqToCurrentAnalyseQueryPageChart.setRemarks(zxEqToCurrentAnalyseQueryPageChart.getRemarks());
           // 排序
           dbZxEqToCurrentAnalyseQueryPageChart.setSort(zxEqToCurrentAnalyseQueryPageChart.getSort());
           // 共通
           dbZxEqToCurrentAnalyseQueryPageChart.setModifyUserInfo(userKey, realName);
           flag = zxEqToCurrentAnalyseQueryPageChartMapper.updateByPrimaryKey(dbZxEqToCurrentAnalyseQueryPageChart);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqToCurrentAnalyseQueryPageChart);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqToCurrentAnalyseQueryPageChart(List<ZxEqToCurrentAnalyseQueryPageChart> zxEqToCurrentAnalyseQueryPageChartList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqToCurrentAnalyseQueryPageChartList != null && zxEqToCurrentAnalyseQueryPageChartList.size() > 0) {
           ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart = new ZxEqToCurrentAnalyseQueryPageChart();
           zxEqToCurrentAnalyseQueryPageChart.setModifyUserInfo(userKey, realName);
           flag = zxEqToCurrentAnalyseQueryPageChartMapper.batchDeleteUpdateZxEqToCurrentAnalyseQueryPageChart(zxEqToCurrentAnalyseQueryPageChartList, zxEqToCurrentAnalyseQueryPageChart);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqToCurrentAnalyseQueryPageChartList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity ureportZxEqToCurrentAnalyseQueryPageChartIdle(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        if (zxEqToCurrentAnalyseQueryPageChart == null) {
            zxEqToCurrentAnalyseQueryPageChart = new ZxEqToCurrentAnalyseQueryPageChart();
        }
        // 分页查询
        PageHelper.startPage(zxEqToCurrentAnalyseQueryPageChart.getPage(),zxEqToCurrentAnalyseQueryPageChart.getLimit());
        // 获取数据
        List<ZxEqToCurrentAnalyseQueryPageChart> zxEqToCurrentAnalyseQueryPageChartList = zxEqToCurrentAnalyseQueryPageChartMapper.selectZxEqToCurrentAnalyseQueryPageChart(zxEqToCurrentAnalyseQueryPageChart);
        // 得到分页信息
        PageInfo<ZxEqToCurrentAnalyseQueryPageChart> p = new PageInfo<>(zxEqToCurrentAnalyseQueryPageChartList);

        return repEntity.okList(zxEqToCurrentAnalyseQueryPageChartList, p.getTotal());
    }
    
    @Override
    public ResponseEntity ureportZxEqToCurrentAnalyseQueryPageIdle(ZxEqToCurrentAnalyseQueryPageChart zxEqToCurrentAnalyseQueryPageChart) {
        if (zxEqToCurrentAnalyseQueryPageChart == null) {
            zxEqToCurrentAnalyseQueryPageChart = new ZxEqToCurrentAnalyseQueryPageChart();
        }
        // 分页查询
        PageHelper.startPage(zxEqToCurrentAnalyseQueryPageChart.getPage(),zxEqToCurrentAnalyseQueryPageChart.getLimit());
        // 获取数据
        List<ZxEqToCurrentAnalyseQueryPageChart> zxEqToCurrentAnalyseQueryPageChartList = zxEqToCurrentAnalyseQueryPageChartMapper.selectZxEqToCurrentAnalyseQueryPage(zxEqToCurrentAnalyseQueryPageChart);
        // 得到分页信息
        PageInfo<ZxEqToCurrentAnalyseQueryPageChart> p = new PageInfo<>(zxEqToCurrentAnalyseQueryPageChartList);

        return repEntity.okList(zxEqToCurrentAnalyseQueryPageChartList, p.getTotal());
    }
}
