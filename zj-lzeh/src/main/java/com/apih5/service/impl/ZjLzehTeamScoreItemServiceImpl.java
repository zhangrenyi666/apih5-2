package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehTeamScoreItemMapper;
import com.apih5.mybatis.pojo.ZjLzehTeamScoreItem;
import com.apih5.service.ZjLzehTeamScoreItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehTeamScoreItemService")
public class ZjLzehTeamScoreItemServiceImpl implements ZjLzehTeamScoreItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTeamScoreItemMapper zjLzehTeamScoreItemMapper;

    @Override
    public ResponseEntity getZjLzehTeamScoreItemListByCondition(ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        if (zjLzehTeamScoreItem == null) {
            zjLzehTeamScoreItem = new ZjLzehTeamScoreItem();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTeamScoreItem.getPage(),zjLzehTeamScoreItem.getLimit());
        // 获取数据
        List<ZjLzehTeamScoreItem> zjLzehTeamScoreItemList = zjLzehTeamScoreItemMapper.selectByZjLzehTeamScoreItemList(zjLzehTeamScoreItem);
        // 得到分页信息
        PageInfo<ZjLzehTeamScoreItem> p = new PageInfo<>(zjLzehTeamScoreItemList);

        return repEntity.okList(zjLzehTeamScoreItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehTeamScoreItemDetail(ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        if (zjLzehTeamScoreItem == null) {
            zjLzehTeamScoreItem = new ZjLzehTeamScoreItem();
        }
        // 获取数据
        ZjLzehTeamScoreItem dbZjLzehTeamScoreItem = zjLzehTeamScoreItemMapper.selectByPrimaryKey(zjLzehTeamScoreItem.getZjLzehTeamItemId());
        // 数据存在
        if (dbZjLzehTeamScoreItem != null) {
            return repEntity.ok(dbZjLzehTeamScoreItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehTeamScoreItem(ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTeamScoreItem.setZjLzehTeamItemId(UuidUtil.generate());
        zjLzehTeamScoreItem.setCreateUserInfo(userKey, realName);
        int flag = zjLzehTeamScoreItemMapper.insert(zjLzehTeamScoreItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehTeamScoreItem);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTeamScoreItem(ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehTeamScoreItem dbZjLzehTeamScoreItem = zjLzehTeamScoreItemMapper.selectByPrimaryKey(zjLzehTeamScoreItem.getZjLzehTeamItemId());
        if (dbZjLzehTeamScoreItem != null && StrUtil.isNotEmpty(dbZjLzehTeamScoreItem.getZjLzehTeamItemId())) {
           // 评分管理ID(主表ID)
           dbZjLzehTeamScoreItem.setZjLzehTeamScoreId(zjLzehTeamScoreItem.getZjLzehTeamScoreId());
           // 班组ID
           dbZjLzehTeamScoreItem.setTeamId(zjLzehTeamScoreItem.getTeamId());
           // 施工进度
           dbZjLzehTeamScoreItem.setBuildProgress(zjLzehTeamScoreItem.getBuildProgress());
           // 施工安全
           dbZjLzehTeamScoreItem.setBuildSafe(zjLzehTeamScoreItem.getBuildSafe());
           // 施工质量
           dbZjLzehTeamScoreItem.setBuildQuality(zjLzehTeamScoreItem.getBuildQuality());
           // 文明施工
           dbZjLzehTeamScoreItem.setBuildCivilized(zjLzehTeamScoreItem.getBuildCivilized());
           // 当月总分
           dbZjLzehTeamScoreItem.setMonthScore(zjLzehTeamScoreItem.getMonthScore());
           // 当月排名
           dbZjLzehTeamScoreItem.setMonthRank(zjLzehTeamScoreItem.getMonthRank());
           // 备注
           dbZjLzehTeamScoreItem.setRemarks(zjLzehTeamScoreItem.getRemarks());
           // 排序
           dbZjLzehTeamScoreItem.setSort(zjLzehTeamScoreItem.getSort());
           // 共通
           dbZjLzehTeamScoreItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamScoreItemMapper.updateByPrimaryKey(dbZjLzehTeamScoreItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehTeamScoreItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehTeamScoreItem(List<ZjLzehTeamScoreItem> zjLzehTeamScoreItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehTeamScoreItemList != null && zjLzehTeamScoreItemList.size() > 0) {
           ZjLzehTeamScoreItem zjLzehTeamScoreItem = new ZjLzehTeamScoreItem();
           zjLzehTeamScoreItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamScoreItemMapper.batchDeleteUpdateZjLzehTeamScoreItem(zjLzehTeamScoreItemList, zjLzehTeamScoreItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehTeamScoreItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getChartList(ZjLzehTeamScoreItem zjLzehTeamScoreItem) {

        List chartList = new ArrayList();

        // 获取数据
        List<ZjLzehTeamScoreItem> ScoreList = zjLzehTeamScoreItemMapper.getScoreInfo(zjLzehTeamScoreItem);
        List<ZjLzehTeamScoreItem> avgScoreList = zjLzehTeamScoreItemMapper.getAvgScoreInfo(zjLzehTeamScoreItem);
        chartList.add(ScoreList);
        chartList.add(avgScoreList);
        return repEntity.ok(chartList);
    }

    @Override
    public ResponseEntity getZjLzehTeamScoreItemByTeamScoreId(ZjLzehTeamScoreItem zjLzehTeamScoreItem) {
        if (zjLzehTeamScoreItem == null) {
            zjLzehTeamScoreItem = new ZjLzehTeamScoreItem();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTeamScoreItem.getPage(),zjLzehTeamScoreItem.getLimit());
        int sort = (zjLzehTeamScoreItem.getPage()-1)*zjLzehTeamScoreItem.getLimit();
        // 获取数据
        List<ZjLzehTeamScoreItem> zjLzehTeamScoreItemList = zjLzehTeamScoreItemMapper.getInfoListByTeamScoreId(zjLzehTeamScoreItem);
        // 得到分页信息
        PageInfo<ZjLzehTeamScoreItem> p = new PageInfo<>(zjLzehTeamScoreItemList);

        p.getList().stream().forEach(o->o.setMonthRank(o.getMonthRank()+sort));

        return repEntity.okList(zjLzehTeamScoreItemList, p.getTotal());
    }


    @Override
    public ResponseEntity getChartByMonth(ZjLzehTeamScoreItem zjLzehTeamScoreItem) {

        // 获取数据
        List<ZjLzehTeamScoreItem> ScoreList = zjLzehTeamScoreItemMapper.getInfoListByMonth(zjLzehTeamScoreItem);
        List<ZjLzehTeamScoreItem> avgScoreList = zjLzehTeamScoreItemMapper.getChartByMonth(zjLzehTeamScoreItem);
        ScoreList.stream().forEach( a->avgScoreList.stream().filter(b->b.getTeamId().equals(a.getTeamId())).forEach(b1->a.setAvgScore(b1.getAvgScore())));
        return repEntity.ok(ScoreList);
    }

}
