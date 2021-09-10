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
import com.apih5.mybatis.dao.ZxSkTurnoverTotalRptMapper;
import com.apih5.mybatis.pojo.ZxSkTurnoverTotalRpt;
import com.apih5.service.ZxSkTurnoverTotalRptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnoverTotalRptService")
public class ZxSkTurnoverTotalRptServiceImpl implements ZxSkTurnoverTotalRptService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnoverTotalRptMapper zxSkTurnoverTotalRptMapper;

    @Override
    public ResponseEntity getZxSkTurnoverTotalRptListByCondition(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        if (zxSkTurnoverTotalRpt == null) {
            zxSkTurnoverTotalRpt = new ZxSkTurnoverTotalRpt();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverTotalRpt.getPage(),zxSkTurnoverTotalRpt.getLimit());
        // 获取数据
        List<ZxSkTurnoverTotalRpt> zxSkTurnoverTotalRptList = zxSkTurnoverTotalRptMapper.selectByZxSkTurnoverTotalRptList(zxSkTurnoverTotalRpt);
        // 得到分页信息
        PageInfo<ZxSkTurnoverTotalRpt> p = new PageInfo<>(zxSkTurnoverTotalRptList);

        return repEntity.okList(zxSkTurnoverTotalRptList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnoverTotalRptDetail(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        if (zxSkTurnoverTotalRpt == null) {
            zxSkTurnoverTotalRpt = new ZxSkTurnoverTotalRpt();
        }
        // 获取数据
        ZxSkTurnoverTotalRpt dbZxSkTurnoverTotalRpt = zxSkTurnoverTotalRptMapper.selectByPrimaryKey(zxSkTurnoverTotalRpt.getZxSkTurnoverTotalRptId());
        // 数据存在
        if (dbZxSkTurnoverTotalRpt != null) {
            return repEntity.ok(dbZxSkTurnoverTotalRpt);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnoverTotalRpt(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnoverTotalRpt.setZxSkTurnoverTotalRptId(UuidUtil.generate());
        zxSkTurnoverTotalRpt.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnoverTotalRptMapper.insert(zxSkTurnoverTotalRpt);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnoverTotalRpt);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnoverTotalRpt(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnoverTotalRpt dbZxSkTurnoverTotalRpt = zxSkTurnoverTotalRptMapper.selectByPrimaryKey(zxSkTurnoverTotalRpt.getZxSkTurnoverTotalRptId());
        if (dbZxSkTurnoverTotalRpt != null && StrUtil.isNotEmpty(dbZxSkTurnoverTotalRpt.getZxSkTurnoverTotalRptId())) {
           // 编码
           dbZxSkTurnoverTotalRpt.setResCode(zxSkTurnoverTotalRpt.getResCode());
           // 物资名称
           dbZxSkTurnoverTotalRpt.setResName(zxSkTurnoverTotalRpt.getResName());
           // 规格
           dbZxSkTurnoverTotalRpt.setSpec(zxSkTurnoverTotalRpt.getSpec());
           // 单位
           dbZxSkTurnoverTotalRpt.setResUnit(zxSkTurnoverTotalRpt.getResUnit());
           // 单价
           dbZxSkTurnoverTotalRpt.setInPrice(zxSkTurnoverTotalRpt.getInPrice());
           // 年初库存数量
           dbZxSkTurnoverTotalRpt.setLastQty(zxSkTurnoverTotalRpt.getLastQty());
           // 年初库存原值
           dbZxSkTurnoverTotalRpt.setLastInAmt(zxSkTurnoverTotalRpt.getLastInAmt());
           // 年初库存净值
           dbZxSkTurnoverTotalRpt.setLastremainAmt(zxSkTurnoverTotalRpt.getLastremainAmt());
           // 本期收入数量
           dbZxSkTurnoverTotalRpt.setInQtya(zxSkTurnoverTotalRpt.getInQtya());
           // 本期收入累积数量
           dbZxSkTurnoverTotalRpt.setInQty(zxSkTurnoverTotalRpt.getInQty());
           // 本期收入金额
           dbZxSkTurnoverTotalRpt.setInAmta(zxSkTurnoverTotalRpt.getInAmta());
           // 本期收入累积金额
           dbZxSkTurnoverTotalRpt.setInAmt(zxSkTurnoverTotalRpt.getInAmt());
           // 本期摊销
           dbZxSkTurnoverTotalRpt.setShareAmt(zxSkTurnoverTotalRpt.getShareAmt());
           // 本年累计摊销
           dbZxSkTurnoverTotalRpt.setShareAmts(zxSkTurnoverTotalRpt.getShareAmts());
           // 开工累计摊销
           dbZxSkTurnoverTotalRpt.setShareAmtTotal(zxSkTurnoverTotalRpt.getShareAmtTotal());
           // 本期调出数量
           dbZxSkTurnoverTotalRpt.setThisDiscQty(zxSkTurnoverTotalRpt.getThisDiscQty());
           // 本年开累调出数量
           dbZxSkTurnoverTotalRpt.setAllOutQty(zxSkTurnoverTotalRpt.getAllOutQty());
           // 开工累计调出数量
           dbZxSkTurnoverTotalRpt.setTotalOutQty(zxSkTurnoverTotalRpt.getTotalOutQty());
           // 本期调出原值
           dbZxSkTurnoverTotalRpt.setThisBuyAmt(zxSkTurnoverTotalRpt.getThisBuyAmt());
           // 本期调出净值
           dbZxSkTurnoverTotalRpt.setThisCurrentAmt(zxSkTurnoverTotalRpt.getThisCurrentAmt());
           // 开累本年调出原值
           dbZxSkTurnoverTotalRpt.setAllBuyAmt(zxSkTurnoverTotalRpt.getAllBuyAmt());
           // 开累本年调出净值
           dbZxSkTurnoverTotalRpt.setAllCurrentAmt(zxSkTurnoverTotalRpt.getAllCurrentAmt());
           // 开工累计原值
           dbZxSkTurnoverTotalRpt.setTotalBuyAmt(zxSkTurnoverTotalRpt.getTotalBuyAmt());
           // 开工累计净值
           dbZxSkTurnoverTotalRpt.setTotalCurrentAmt(zxSkTurnoverTotalRpt.getTotalCurrentAmt());
           // 本期报废数量
           dbZxSkTurnoverTotalRpt.setThisBadQty(zxSkTurnoverTotalRpt.getThisBadQty());
           // 本年开累报废数量
           dbZxSkTurnoverTotalRpt.setAllDiscQty(zxSkTurnoverTotalRpt.getAllDiscQty());
           // 开工累计数量
           dbZxSkTurnoverTotalRpt.setTotalDiscQty(zxSkTurnoverTotalRpt.getTotalDiscQty());
           // 本期报废原值
           dbZxSkTurnoverTotalRpt.setThisOrigAmt(zxSkTurnoverTotalRpt.getThisOrigAmt());
           // 本期报废净值
           dbZxSkTurnoverTotalRpt.setThisRemainAmt(zxSkTurnoverTotalRpt.getThisRemainAmt());
           // 开累本年报废原值
           dbZxSkTurnoverTotalRpt.setAllOrigAmt(zxSkTurnoverTotalRpt.getAllOrigAmt());
           // 开累本年报废净值
           dbZxSkTurnoverTotalRpt.setAllRemainAmt(zxSkTurnoverTotalRpt.getAllRemainAmt());
           // 开工累计原值
           dbZxSkTurnoverTotalRpt.setTotalOrigAmt(zxSkTurnoverTotalRpt.getTotalOrigAmt());
           // 开工累计净值
           dbZxSkTurnoverTotalRpt.setTotalRemainAmt(zxSkTurnoverTotalRpt.getTotalRemainAmt());
           // 期末库存数量
           dbZxSkTurnoverTotalRpt.setThisQty(zxSkTurnoverTotalRpt.getThisQty());
           // 在库数量
           dbZxSkTurnoverTotalRpt.setInUse(zxSkTurnoverTotalRpt.getInUse());
           // 在用数量
           dbZxSkTurnoverTotalRpt.setAtUse(zxSkTurnoverTotalRpt.getAtUse());
           // 期末库存原值
           dbZxSkTurnoverTotalRpt.setThisInAmt(zxSkTurnoverTotalRpt.getThisInAmt());
           // 期末库存净值
           dbZxSkTurnoverTotalRpt.setThisBadAmt(zxSkTurnoverTotalRpt.getThisBadAmt());
           // 所属单位
           dbZxSkTurnoverTotalRpt.setOrgName(zxSkTurnoverTotalRpt.getOrgName());
           // 备注
           dbZxSkTurnoverTotalRpt.setRemarks(zxSkTurnoverTotalRpt.getRemarks());
           // 排序
           dbZxSkTurnoverTotalRpt.setSort(zxSkTurnoverTotalRpt.getSort());
           // 共通
           dbZxSkTurnoverTotalRpt.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverTotalRptMapper.updateByPrimaryKey(dbZxSkTurnoverTotalRpt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnoverTotalRpt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnoverTotalRpt(List<ZxSkTurnoverTotalRpt> zxSkTurnoverTotalRptList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnoverTotalRptList != null && zxSkTurnoverTotalRptList.size() > 0) {
           ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt = new ZxSkTurnoverTotalRpt();
           zxSkTurnoverTotalRpt.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnoverTotalRptMapper.batchDeleteUpdateZxSkTurnoverTotalRpt(zxSkTurnoverTotalRptList, zxSkTurnoverTotalRpt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnoverTotalRptList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxSkTurnoverTotalRpt> ureportZxSkTurnoverTotalRpt(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
    	if(zxSkTurnoverTotalRpt==null) {    		
    		zxSkTurnoverTotalRpt = new ZxSkTurnoverTotalRpt();
    	}
    	List<ZxSkTurnoverTotalRpt> zxSkTurnoverTotalRptlist = zxSkTurnoverTotalRptMapper.selectZxSkTurnoverTotalRpt(zxSkTurnoverTotalRpt);
    	
    	return zxSkTurnoverTotalRptlist;
    }
    
    @Override
    public ResponseEntity ureportZxSkTurnoverTotalRptIdle(ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        if (zxSkTurnoverTotalRpt == null) {
            zxSkTurnoverTotalRpt = new ZxSkTurnoverTotalRpt();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnoverTotalRpt.getPage(),zxSkTurnoverTotalRpt.getLimit());
        // 获取数据
        List<ZxSkTurnoverTotalRpt> zxSkTurnoverTotalRptlist = zxSkTurnoverTotalRptMapper.selectZxSkTurnoverTotalRpt(zxSkTurnoverTotalRpt);
        // 得到分页信息
        PageInfo<ZxSkTurnoverTotalRpt> p = new PageInfo<>(zxSkTurnoverTotalRptlist);

        return repEntity.okList(zxSkTurnoverTotalRptlist, p.getTotal());
    }
}
