package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfCostMapper;
import com.apih5.mybatis.pojo.ZxSfCost;
import com.apih5.service.ZxSfCostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfCostService")
public class ZxSfCostServiceImpl implements ZxSfCostService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfCostMapper zxSfCostMapper;

    @Override
    public ResponseEntity getZxSfCostListByCondition(ZxSfCost zxSfCost) {
        if (zxSfCost == null) {
            zxSfCost = new ZxSfCost();
        }
        // 分页查询
        PageHelper.startPage(zxSfCost.getPage(), zxSfCost.getLimit());
        // 获取数据
        List<ZxSfCost> zxSfCostList = zxSfCostMapper.selectByZxSfCostList(zxSfCost);
        // 得到分页信息
        PageInfo<ZxSfCost> p = new PageInfo<>(zxSfCostList);

        return repEntity.okList(zxSfCostList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfCostDetail(ZxSfCost zxSfCost) {
        if (zxSfCost == null) {
            zxSfCost = new ZxSfCost();
        }
        // 获取数据
        ZxSfCost dbZxSfCost = zxSfCostMapper.selectByPrimaryKey(zxSfCost.getZxSfCostId());
        // 数据存在
        if (dbZxSfCost != null) {
            return repEntity.ok(dbZxSfCost);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfCost(ZxSfCost zxSfCost) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfCost.setZxSfCostId(UuidUtil.generate());
        zxSfCost.setCreateUserInfo(userKey, realName);
        int flag = zxSfCostMapper.insert(zxSfCost);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfCost);
        }
    }

    @Override
    public ResponseEntity updateZxSfCost(ZxSfCost zxSfCost) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCost dbZxSfCost = zxSfCostMapper.selectByPrimaryKey(zxSfCost.getZxSfCostId());
        if (dbZxSfCost != null && StrUtil.isNotEmpty(dbZxSfCost.getZxSfCostId())) {
            // 公司名称
            dbZxSfCost.setCompanyName(zxSfCost.getCompanyName());
            // 公司id
            dbZxSfCost.setCompanyId(zxSfCost.getCompanyId());
            // 项目ID
            dbZxSfCost.setOrgID(zxSfCost.getOrgID());
            // 项目名称
            dbZxSfCost.setOrgName(zxSfCost.getOrgName());
            // 在建合同额
            dbZxSfCost.setZjCost(zxSfCost.getZjCost());
            // 安全费用总预计（万元）
            dbZxSfCost.setZjSfCost(zxSfCost.getZjSfCost());
            // 已完合同额（万元）
            dbZxSfCost.setEndCost(zxSfCost.getEndCost());
            // 已完合同额安全费用预计（万元）
            dbZxSfCost.setEndSfCost(zxSfCost.getEndSfCost());
            // 实际支出
            dbZxSfCost.setCost(zxSfCost.getCost());
            // 季度
            dbZxSfCost.setSeason(zxSfCost.getSeason());
            // 工程类别
            dbZxSfCost.setProjType(zxSfCost.getProjType());
            // 共通
            dbZxSfCost.setModifyUserInfo(userKey, realName);
            flag = zxSfCostMapper.updateByPrimaryKey(dbZxSfCost);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCost);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfCost(List<ZxSfCost> zxSfCostList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfCostList != null && zxSfCostList.size() > 0) {
            ZxSfCost zxSfCost = new ZxSfCost();
            zxSfCost.setModifyUserInfo(userKey, realName);
            flag = zxSfCostMapper.batchDeleteUpdateZxSfCost(zxSfCostList, zxSfCost);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfCostList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getCompany(ZxSfCost zxSfCost) {
        // 获取数据
        ZxSfCost dbZxSfCost = zxSfCostMapper.getCompany(zxSfCost);
        // 数据存在
        if (dbZxSfCost != null) {
            return repEntity.ok(dbZxSfCost);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity getCompanyList(ZxSfCost zxSfCost) {
        // 获取数据
        List<ZxSfCost> dbZxSfCostList = zxSfCostMapper.getCompanyList(zxSfCost);
        // 数据存在
        if (dbZxSfCostList.size() > 0) {
            return repEntity.ok(dbZxSfCostList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity getOrgCostList(ZxSfCost zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        ZxSfCost guiDang = zxSfCostMapper.getGuiDang(zxSfCost, today);
        ZxSfCost jiaoGong = zxSfCostMapper.getJiaoGong(zxSfCost, today);
        ZxSfCost wanGong = zxSfCostMapper.getWanGong(zxSfCost, today);
        ZxSfCost kaiGong = zxSfCostMapper.getKaiGong(zxSfCost, today);
        List<ZxSfCost> orgCostList = new ArrayList<>();
        if (guiDang != null) {
            orgCostList.add(guiDang);
        }
        if (jiaoGong != null) {
            orgCostList.add(jiaoGong);
        }
        if (wanGong != null) {
            orgCostList.add(wanGong);
        }
        if (kaiGong != null) {
            orgCostList.add(kaiGong);
        }
        return repEntity.ok(orgCostList);
    }

    @Override
    public ResponseEntity getGuiDangList(ZxSfCost zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfCost> guiDangList = zxSfCostMapper.getGuiDangList(zxSfCost, today);
        return repEntity.ok(guiDangList);
    }

    @Override
    public ResponseEntity getJiaoGongList(ZxSfCost zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfCost> jiaoGongList = zxSfCostMapper.getJiaoGongList(zxSfCost, today);
        return repEntity.ok(jiaoGongList);
    }

    @Override
    public ResponseEntity getWanGongList(ZxSfCost zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfCost> wanGongList = zxSfCostMapper.getWanGongList(zxSfCost, today);
        return repEntity.ok(wanGongList);
    }

    @Override
    public ResponseEntity getKaiGongList(ZxSfCost zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfCost> kaiGongList = zxSfCostMapper.getKaiGongList(zxSfCost, today);
        return repEntity.ok(kaiGongList);
    }
}
