package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSkTurnoverStatTotalRptMapper;
import com.apih5.mybatis.pojo.ZxSkTurnoverStatTotalRpt;
import com.apih5.service.ZxSkTurnoverStatTotalRptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnoverStatTotalRptService")
public class ZxSkTurnoverStatTotalRptServiceImpl implements ZxSkTurnoverStatTotalRptService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnoverStatTotalRptMapper zxSkTurnoverStatTotalRptMapper;

    @Override
    public ResponseEntity getZxSkTurnoverStatTotalRptListByCondition(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        if (zxSkTurnoverStatTotalRpt == null) {
            zxSkTurnoverStatTotalRpt = new ZxSkTurnoverStatTotalRpt();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverStatTotalRpt.getPage(),zxSkTurnoverStatTotalRpt.getLimit());
        // 获取数据
        List<ZxSkTurnoverStatTotalRpt> zxSkTurnoverStatTotalRptList = zxSkTurnoverStatTotalRptMapper.selectByZxSkTurnoverStatTotalRptList(zxSkTurnoverStatTotalRpt);
        // 得到分页信息
        PageInfo<ZxSkTurnoverStatTotalRpt> p = new PageInfo<>(zxSkTurnoverStatTotalRptList);

        return repEntity.okList(zxSkTurnoverStatTotalRptList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverStatTotalRptDetail(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        if (zxSkTurnoverStatTotalRpt == null) {
            zxSkTurnoverStatTotalRpt = new ZxSkTurnoverStatTotalRpt();
        }
        // 获取数据
        ZxSkTurnoverStatTotalRpt dbZxSkTurnoverStatTotalRpt = zxSkTurnoverStatTotalRptMapper.selectByPrimaryKey(zxSkTurnoverStatTotalRpt.getZxSkTurnoverStatTotalRptId());
        // 数据存在
        if (dbZxSkTurnoverStatTotalRpt != null) {
            return repEntity.ok(dbZxSkTurnoverStatTotalRpt);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverStatTotalRpt(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverStatTotalRpt.setZxSkTurnoverStatTotalRptId(UuidUtil.generate());
        zxSkTurnoverStatTotalRpt.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnoverStatTotalRptMapper.insert(zxSkTurnoverStatTotalRpt);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnoverStatTotalRpt);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnoverStatTotalRpt(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnoverStatTotalRpt dbZxSkTurnoverStatTotalRpt = zxSkTurnoverStatTotalRptMapper.selectByPrimaryKey(zxSkTurnoverStatTotalRpt.getZxSkTurnoverStatTotalRptId());
        if (dbZxSkTurnoverStatTotalRpt != null && StrUtil.isNotEmpty(dbZxSkTurnoverStatTotalRpt.getZxSkTurnoverStatTotalRptId())) {
           // 物资编码
           dbZxSkTurnoverStatTotalRpt.setResCode(zxSkTurnoverStatTotalRpt.getResCode());
           // 物资名称
           dbZxSkTurnoverStatTotalRpt.setResName(zxSkTurnoverStatTotalRpt.getResName());
           // 年初库存原值
           dbZxSkTurnoverStatTotalRpt.setLastInAmt(zxSkTurnoverStatTotalRpt.getLastInAmt());
           // 年初库存净值
           dbZxSkTurnoverStatTotalRpt.setLastremainAmt(zxSkTurnoverStatTotalRpt.getLastremainAmt());
           // 本年累计收入金额
           dbZxSkTurnoverStatTotalRpt.setInAmt(zxSkTurnoverStatTotalRpt.getInAmt());
           // 本年累计摊销
           dbZxSkTurnoverStatTotalRpt.setShareAmts(zxSkTurnoverStatTotalRpt.getShareAmts());
           // 开工累计摊销
           dbZxSkTurnoverStatTotalRpt.setShareAmtTotal(zxSkTurnoverStatTotalRpt.getShareAmtTotal());
           // 开累本年调出原值
           dbZxSkTurnoverStatTotalRpt.setAllBuyAmt(zxSkTurnoverStatTotalRpt.getAllBuyAmt());
           // 开累本年调出净值
           dbZxSkTurnoverStatTotalRpt.setAllCurrentAmt(zxSkTurnoverStatTotalRpt.getAllCurrentAmt());
           // 开累本年报废原值
           dbZxSkTurnoverStatTotalRpt.setAllOrigAmt(zxSkTurnoverStatTotalRpt.getAllOrigAmt());
           // 开累本年报废净值
           dbZxSkTurnoverStatTotalRpt.setAllRemainAmt(zxSkTurnoverStatTotalRpt.getAllRemainAmt());
           // 期末库存原值
           dbZxSkTurnoverStatTotalRpt.setThisInAmt(zxSkTurnoverStatTotalRpt.getThisInAmt());
           // 期末库存净值
           dbZxSkTurnoverStatTotalRpt.setThisBadAmt(zxSkTurnoverStatTotalRpt.getThisBadAmt());
           // 备注
           dbZxSkTurnoverStatTotalRpt.setRemarks(zxSkTurnoverStatTotalRpt.getRemarks());
           // 排序
           dbZxSkTurnoverStatTotalRpt.setSort(zxSkTurnoverStatTotalRpt.getSort());
           // 共通
           dbZxSkTurnoverStatTotalRpt.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverStatTotalRptMapper.updateByPrimaryKey(dbZxSkTurnoverStatTotalRpt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnoverStatTotalRpt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnoverStatTotalRpt(List<ZxSkTurnoverStatTotalRpt> zxSkTurnoverStatTotalRptList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnoverStatTotalRptList != null && zxSkTurnoverStatTotalRptList.size() > 0) {
           ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt = new ZxSkTurnoverStatTotalRpt();
           zxSkTurnoverStatTotalRpt.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverStatTotalRptMapper.batchDeleteUpdateZxSkTurnoverStatTotalRpt(zxSkTurnoverStatTotalRptList, zxSkTurnoverStatTotalRpt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverStatTotalRptList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxSkTurnoverStatTotalRpt> ureportZxSkTurnoverStatTotalRpt(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
    	List<ZxSkTurnoverStatTotalRpt> zxSkTurnoverStatTotalRptList = zxSkTurnoverStatTotalRptMapper.selectZxSkTurnoverStatTotalRpt(zxSkTurnoverStatTotalRpt);
    	return zxSkTurnoverStatTotalRptList;
    }
    
    @Override
    public ResponseEntity ureportZxSkTurnoverStatTotalRptIdle(ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        if (zxSkTurnoverStatTotalRpt == null) {
            zxSkTurnoverStatTotalRpt = new ZxSkTurnoverStatTotalRpt();
        }
        // 年度假值
        zxSkTurnoverStatTotalRpt.setBeginDate(DateUtil.parse("20201226"));
        zxSkTurnoverStatTotalRpt.setEndDate(DateUtil.parse("20211226"));
        zxSkTurnoverStatTotalRpt.setYearBeginDate("2021");
        // 分页查询
        PageHelper.startPage(zxSkTurnoverStatTotalRpt.getPage(),zxSkTurnoverStatTotalRpt.getLimit());
        // 获取数据
        List<ZxSkTurnoverStatTotalRpt> zxSkTurnoverStatTotalRptList = zxSkTurnoverStatTotalRptMapper.selectZxSkTurnoverStatTotalRpt(zxSkTurnoverStatTotalRpt);
        // 得到分页信息
        PageInfo<ZxSkTurnoverStatTotalRpt> p = new PageInfo<>(zxSkTurnoverStatTotalRptList);

        return repEntity.okList(zxSkTurnoverStatTotalRptList, p.getTotal());
    }
}
