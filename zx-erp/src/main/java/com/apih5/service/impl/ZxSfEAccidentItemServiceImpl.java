package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.pojo.ZxSfAccidentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfEAccidentItemMapper;
import com.apih5.mybatis.pojo.ZxSfEAccidentItem;
import com.apih5.service.ZxSfEAccidentItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfEAccidentItemService")
public class ZxSfEAccidentItemServiceImpl implements ZxSfEAccidentItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfEAccidentItemMapper zxSfEAccidentItemMapper;

    @Override
    public ResponseEntity getZxSfEAccidentItemListByCondition(ZxSfEAccidentItem zxSfEAccidentItem) {
        if (zxSfEAccidentItem == null) {
            zxSfEAccidentItem = new ZxSfEAccidentItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfEAccidentItem.getPage(), zxSfEAccidentItem.getLimit());
        // 获取数据
        List<ZxSfEAccidentItem> zxSfEAccidentItemList = zxSfEAccidentItemMapper.selectByZxSfEAccidentItemList(zxSfEAccidentItem);
        // 得到分页信息
        PageInfo<ZxSfEAccidentItem> p = new PageInfo<>(zxSfEAccidentItemList);

        return repEntity.okList(zxSfEAccidentItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfEAccidentItemDetail(ZxSfEAccidentItem zxSfEAccidentItem) {
        if (zxSfEAccidentItem == null) {
            zxSfEAccidentItem = new ZxSfEAccidentItem();
        }
        // 获取数据
        ZxSfEAccidentItem dbZxSfEAccidentItem = zxSfEAccidentItemMapper.selectByPrimaryKey(zxSfEAccidentItem.getZxSfEAccidentItemId());
        // 数据存在
        if (dbZxSfEAccidentItem != null) {
            return repEntity.ok(dbZxSfEAccidentItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfEAccidentItem(ZxSfEAccidentItem zxSfEAccidentItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfEAccidentItem.setZxSfEAccidentItemId(UuidUtil.generate());
        zxSfEAccidentItem.setCreateUserInfo(userKey, realName);
        int flag = zxSfEAccidentItemMapper.insert(zxSfEAccidentItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfEAccidentItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfEAccidentItem(ZxSfEAccidentItem zxSfEAccidentItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfEAccidentItem dbZxSfEAccidentItem = zxSfEAccidentItemMapper.selectByPrimaryKey(zxSfEAccidentItem.getZxSfEAccidentItemId());
        if (dbZxSfEAccidentItem != null && StrUtil.isNotEmpty(dbZxSfEAccidentItem.getZxSfEAccidentItemId())) {
            // 月负伤频率（‰）
            dbZxSfEAccidentItem.setA10(zxSfEAccidentItem.getA10());
            // 备注
            dbZxSfEAccidentItem.setNotes(zxSfEAccidentItem.getNotes());
            // 直接经济损失（万元）
            dbZxSfEAccidentItem.setA6(zxSfEAccidentItem.getA6());
            // 单位
            dbZxSfEAccidentItem.setOrgName(zxSfEAccidentItem.getOrgName());
            // 死亡
            dbZxSfEAccidentItem.setA3(zxSfEAccidentItem.getA3());
            // 机构ID
            dbZxSfEAccidentItem.setOrgId(zxSfEAccidentItem.getOrgId());
            // 主表ID
            dbZxSfEAccidentItem.setEaId(zxSfEAccidentItem.getEaId());
            // 重伤
            dbZxSfEAccidentItem.setA4(zxSfEAccidentItem.getA4());
            // 重伤率（‰）
            dbZxSfEAccidentItem.setA9(zxSfEAccidentItem.getA9());
            // 死亡率（‰）
            dbZxSfEAccidentItem.setA8(zxSfEAccidentItem.getA8());
            // 伤亡人数合计
            dbZxSfEAccidentItem.setA2(zxSfEAccidentItem.getA2());
            // 操作工作日（工日）
            dbZxSfEAccidentItem.setA7(zxSfEAccidentItem.getA7());
            // 轻伤
            dbZxSfEAccidentItem.setA5(zxSfEAccidentItem.getA5());
            // 职工平均人数
            dbZxSfEAccidentItem.setA1(zxSfEAccidentItem.getA1());
            // 排序
            dbZxSfEAccidentItem.setSort(zxSfEAccidentItem.getSort());
            // 共通
            dbZxSfEAccidentItem.setModifyUserInfo(userKey, realName);
            flag = zxSfEAccidentItemMapper.updateByPrimaryKey(dbZxSfEAccidentItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfEAccidentItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfEAccidentItem(List<ZxSfEAccidentItem> zxSfEAccidentItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfEAccidentItemList != null && zxSfEAccidentItemList.size() > 0) {
            ZxSfEAccidentItem zxSfEAccidentItem = new ZxSfEAccidentItem();
            zxSfEAccidentItem.setModifyUserInfo(userKey, realName);
            flag = zxSfEAccidentItemMapper.batchDeleteUpdateZxSfEAccidentItem(zxSfEAccidentItemList, zxSfEAccidentItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfEAccidentItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getUReportFormYearList(ZxSfEAccidentItem zxSfAccidentItem) {
        if (zxSfAccidentItem == null) {
            zxSfAccidentItem = new ZxSfEAccidentItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfAccidentItem.getPage(), zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfEAccidentItem> zxSfAccidentItemList = zxSfEAccidentItemMapper.uReportYear(zxSfAccidentItem);
        // 得到分页信息
        PageInfo<ZxSfEAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return repEntity.okList(zxSfAccidentItemList, p.getTotal());
    }

    @Override
    public List<ZxSfEAccidentItem> uReportFormYear(ZxSfEAccidentItem zxSfAccidentItem) {
        // 分页查询
        //PageHelper.startPage(zxSfAccidentItem.getPage(),zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfEAccidentItem> zxSfAccidentItemList = zxSfEAccidentItemMapper.uReportYear(zxSfAccidentItem);
        // 得到分页信息
        // PageInfo<ZxSfAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return zxSfAccidentItemList;
    }


    @Override
    public ResponseEntity getUReportFormYearListCom(ZxSfEAccidentItem zxSfAccidentItem) {
        if (zxSfAccidentItem == null) {
            zxSfAccidentItem = new ZxSfEAccidentItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfAccidentItem.getPage(), zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfEAccidentItem> zxSfAccidentItemList = zxSfEAccidentItemMapper.uReportYearCom(zxSfAccidentItem);
        // 得到分页信息
        PageInfo<ZxSfEAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return repEntity.okList(zxSfAccidentItemList, p.getTotal());
    }

    @Override
    public List<ZxSfEAccidentItem> uReportFormYearCom(ZxSfEAccidentItem zxSfAccidentItem) {
        // 分页查询
        //PageHelper.startPage(zxSfAccidentItem.getPage(),zxSfAccidentItem.getLimit());
        // 获取数据
        List<ZxSfEAccidentItem> zxSfAccidentItemList = zxSfEAccidentItemMapper.uReportYearCom(zxSfAccidentItem);
        // 得到分页信息
        // PageInfo<ZxSfAccidentItem> p = new PageInfo<>(zxSfAccidentItemList);

        return zxSfAccidentItemList;
    }
}
