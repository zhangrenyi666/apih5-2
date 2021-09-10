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
import com.apih5.mybatis.dao.ZjLzehCountTangerTroubleMapper;
import com.apih5.mybatis.pojo.ZjLzehCountTangerTrouble;
import com.apih5.service.ZjLzehCountTangerTroubleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehCountTangerTroubleService")
public class ZjLzehCountTangerTroubleServiceImpl implements ZjLzehCountTangerTroubleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehCountTangerTroubleMapper zjLzehCountTangerTroubleMapper;

    @Override
    public ResponseEntity getZjLzehCountTangerTroubleListByCondition(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        if (zjLzehCountTangerTrouble == null) {
            zjLzehCountTangerTrouble = new ZjLzehCountTangerTrouble();
        }
        // 分页查询
        PageHelper.startPage(zjLzehCountTangerTrouble.getPage(),zjLzehCountTangerTrouble.getLimit());
        // 获取数据
        List<ZjLzehCountTangerTrouble> zjLzehCountTangerTroubleList = zjLzehCountTangerTroubleMapper.selectByZjLzehCountTangerTroubleList(zjLzehCountTangerTrouble);
        // 得到分页信息
        PageInfo<ZjLzehCountTangerTrouble> p = new PageInfo<>(zjLzehCountTangerTroubleList);

        return repEntity.okList(zjLzehCountTangerTroubleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehCountTangerTroubleDetail(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        if (zjLzehCountTangerTrouble == null) {
            zjLzehCountTangerTrouble = new ZjLzehCountTangerTrouble();
        }
        // 获取数据
        ZjLzehCountTangerTrouble dbZjLzehCountTangerTrouble = zjLzehCountTangerTroubleMapper.selectByPrimaryKey(zjLzehCountTangerTrouble.getZjLzehCountTangerTroubleId());
        // 数据存在
        if (dbZjLzehCountTangerTrouble != null) {
            return repEntity.ok(dbZjLzehCountTangerTrouble);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehCountTangerTrouble(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehCountTangerTrouble.setZjLzehCountTangerTroubleId(UuidUtil.generate());
        zjLzehCountTangerTrouble.setCreateUserInfo(userKey, realName);
        int flag = zjLzehCountTangerTroubleMapper.insert(zjLzehCountTangerTrouble);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehCountTangerTrouble);
        }
    }

    @Override
    public ResponseEntity updateZjLzehCountTangerTrouble(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehCountTangerTrouble dbZjLzehCountTangerTrouble = zjLzehCountTangerTroubleMapper.selectByPrimaryKey(zjLzehCountTangerTrouble.getZjLzehCountTangerTroubleId());
        if (dbZjLzehCountTangerTrouble != null && StrUtil.isNotEmpty(dbZjLzehCountTangerTrouble.getZjLzehCountTangerTroubleId())) {
           // 安全统计ID
           dbZjLzehCountTangerTrouble.setCountDangerId(zjLzehCountTangerTrouble.getCountDangerId());
           // 隐患总数
           dbZjLzehCountTangerTrouble.setTotalNum(zjLzehCountTangerTrouble.getTotalNum());
           // 已整改数
           dbZjLzehCountTangerTrouble.setFinishNum(zjLzehCountTangerTrouble.getFinishNum());
           // 本月隐患总数
           dbZjLzehCountTangerTrouble.setMonthTotalNum(zjLzehCountTangerTrouble.getMonthTotalNum());
           // 本月已整改数
           dbZjLzehCountTangerTrouble.setMonthFinishNum(zjLzehCountTangerTrouble.getMonthFinishNum());
           // 分部名称
           dbZjLzehCountTangerTrouble.setLevelName(zjLzehCountTangerTrouble.getLevelName());
           // 分部id
           dbZjLzehCountTangerTrouble.setLevelId(zjLzehCountTangerTrouble.getLevelId());
           // 质量统计ID
           dbZjLzehCountTangerTrouble.setCountTroubleId(zjLzehCountTangerTrouble.getCountTroubleId());
           // 总百分比
           dbZjLzehCountTangerTrouble.setTotalPercent(zjLzehCountTangerTrouble.getTotalPercent());
           // 月百分比
           dbZjLzehCountTangerTrouble.setMonthPercent(zjLzehCountTangerTrouble.getMonthPercent());
           // 备注
           dbZjLzehCountTangerTrouble.setRemarks(zjLzehCountTangerTrouble.getRemarks());
           // 排序
           dbZjLzehCountTangerTrouble.setSort(zjLzehCountTangerTrouble.getSort());
           // 共通
           dbZjLzehCountTangerTrouble.setModifyUserInfo(userKey, realName);
           flag = zjLzehCountTangerTroubleMapper.updateByPrimaryKey(dbZjLzehCountTangerTrouble);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehCountTangerTrouble);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehCountTangerTrouble(List<ZjLzehCountTangerTrouble> zjLzehCountTangerTroubleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehCountTangerTroubleList != null && zjLzehCountTangerTroubleList.size() > 0) {
           ZjLzehCountTangerTrouble zjLzehCountTangerTrouble = new ZjLzehCountTangerTrouble();
           zjLzehCountTangerTrouble.setModifyUserInfo(userKey, realName);
           flag = zjLzehCountTangerTroubleMapper.batchDeleteUpdateZjLzehCountTangerTrouble(zjLzehCountTangerTroubleList, zjLzehCountTangerTrouble);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehCountTangerTroubleList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity selectTroubleCountInfo(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        if (zjLzehCountTangerTrouble == null) {
            zjLzehCountTangerTrouble = new ZjLzehCountTangerTrouble();
        }
        // 分页查询
        PageHelper.startPage(zjLzehCountTangerTrouble.getPage(),zjLzehCountTangerTrouble.getLimit());
        // 获取数据
        List<ZjLzehCountTangerTrouble> zjLzehCountTangerTroubleList = zjLzehCountTangerTroubleMapper.selectTroubleCountInfo(zjLzehCountTangerTrouble);
        // 得到分页信息
        PageInfo<ZjLzehCountTangerTrouble> p = new PageInfo<>(zjLzehCountTangerTroubleList);

        return repEntity.okList(zjLzehCountTangerTroubleList, p.getTotal());
    }

    @Override
    public ResponseEntity selectDangerCountInfo(ZjLzehCountTangerTrouble zjLzehCountTangerTrouble) {
        if (zjLzehCountTangerTrouble == null) {
            zjLzehCountTangerTrouble = new ZjLzehCountTangerTrouble();
        }
        // 分页查询
        PageHelper.startPage(zjLzehCountTangerTrouble.getPage(),zjLzehCountTangerTrouble.getLimit());
        // 获取数据
        List<ZjLzehCountTangerTrouble> zjLzehCountTangerTroubleList = zjLzehCountTangerTroubleMapper.selectDangerCountInfo(zjLzehCountTangerTrouble);
        // 得到分页信息
        PageInfo<ZjLzehCountTangerTrouble> p = new PageInfo<>(zjLzehCountTangerTroubleList);

        return repEntity.okList(zjLzehCountTangerTroubleList, p.getTotal());
    }
}
