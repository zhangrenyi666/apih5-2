package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtSuppliesContractMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesLeaseSettlementItemMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesMarginRatioMapper;
import com.apih5.mybatis.dao.ZxCtSuppliesShopSettlementItemMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContract;
import com.apih5.mybatis.pojo.ZxCtSuppliesLeaseSettlementItem;
import com.apih5.mybatis.pojo.ZxCtSuppliesMarginRatio;
import com.apih5.mybatis.pojo.ZxCtSuppliesShopSettlementItem;
import com.apih5.service.ZxCtSuppliesMarginRatioService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesMarginRatioService")
public class ZxCtSuppliesMarginRatioServiceImpl implements ZxCtSuppliesMarginRatioService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesMarginRatioMapper zxCtSuppliesMarginRatioMapper;

    @Autowired(required = true)
    private ZxCtSuppliesContractMapper zxCtSuppliesContractMapper;

    @Autowired(required = true)
    private ZxCtSuppliesLeaseSettlementItemMapper zxCtSuppliesLeaseSettlementItemMapper;

    @Autowired(required = true)
    private ZxCtSuppliesShopSettlementItemMapper zxCtSuppliesShopSettlementItemMapper;
    
    @Override
    public ResponseEntity getZxCtSuppliesMarginRatioListByCondition(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        if (zxCtSuppliesMarginRatio == null) {
            zxCtSuppliesMarginRatio = new ZxCtSuppliesMarginRatio();
        }
        // ????????????
        PageHelper.startPage(zxCtSuppliesMarginRatio.getPage(),zxCtSuppliesMarginRatio.getLimit());
        // ????????????
        List<ZxCtSuppliesMarginRatio> zxCtSuppliesMarginRatioList = zxCtSuppliesMarginRatioMapper.selectByZxCtSuppliesMarginRatioList(zxCtSuppliesMarginRatio);
        // ??????????????????
        PageInfo<ZxCtSuppliesMarginRatio> p = new PageInfo<>(zxCtSuppliesMarginRatioList);

        return repEntity.okList(zxCtSuppliesMarginRatioList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesMarginRatioDetail(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        if (zxCtSuppliesMarginRatio == null) {
            zxCtSuppliesMarginRatio = new ZxCtSuppliesMarginRatio();
        }
        // ????????????
        ZxCtSuppliesMarginRatio dbZxCtSuppliesMarginRatio = zxCtSuppliesMarginRatioMapper.selectByPrimaryKey(zxCtSuppliesMarginRatio.getZxCtSuppliesMarginRatioId());
        // ????????????
        if (dbZxCtSuppliesMarginRatio != null) {
            return repEntity.ok(dbZxCtSuppliesMarginRatio);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesMarginRatio(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesMarginRatio.setZxCtSuppliesMarginRatioId(UuidUtil.generate());
        zxCtSuppliesMarginRatio.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesMarginRatioMapper.insert(zxCtSuppliesMarginRatio);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesMarginRatio);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesMarginRatio(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesMarginRatio dbZxCtSuppliesMarginRatio = zxCtSuppliesMarginRatioMapper.selectByPrimaryKey(zxCtSuppliesMarginRatio.getZxCtSuppliesMarginRatioId());
        if (dbZxCtSuppliesMarginRatio != null && StrUtil.isNotEmpty(dbZxCtSuppliesMarginRatio.getZxCtSuppliesMarginRatioId())) {
           // ??????????????????
           dbZxCtSuppliesMarginRatio.setEditTime(zxCtSuppliesMarginRatio.getEditTime());
           // ??????ID
           dbZxCtSuppliesMarginRatio.setOrgID(zxCtSuppliesMarginRatio.getOrgID());
           // ??????????????????
           dbZxCtSuppliesMarginRatio.setAllowDelete(zxCtSuppliesMarginRatio.getAllowDelete());
           // ??????
           dbZxCtSuppliesMarginRatio.setTimeLimit(zxCtSuppliesMarginRatio.getTimeLimit());
           // ????????????(%)
           dbZxCtSuppliesMarginRatio.setStatisticsRate(zxCtSuppliesMarginRatio.getStatisticsRate());
           // ??????ID
           dbZxCtSuppliesMarginRatio.setContractID(zxCtSuppliesMarginRatio.getContractID());
           // ????????????
           dbZxCtSuppliesMarginRatio.setBackCondition(zxCtSuppliesMarginRatio.getBackCondition());
           // ???????????????
           dbZxCtSuppliesMarginRatio.setStatisticsNo(zxCtSuppliesMarginRatio.getStatisticsNo());
           // ?????????
           dbZxCtSuppliesMarginRatio.setStatisticsName(zxCtSuppliesMarginRatio.getStatisticsName());
           // ??????
           dbZxCtSuppliesMarginRatio.setRemarks(zxCtSuppliesMarginRatio.getRemarks());
           // ??????
           dbZxCtSuppliesMarginRatio.setSort(zxCtSuppliesMarginRatio.getSort());
           // ??????
           dbZxCtSuppliesMarginRatio.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesMarginRatioMapper.updateByPrimaryKey(dbZxCtSuppliesMarginRatio);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesMarginRatio);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesMarginRatio(List<ZxCtSuppliesMarginRatio> zxCtSuppliesMarginRatioList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesMarginRatioList != null && zxCtSuppliesMarginRatioList.size() > 0) {
        	for(ZxCtSuppliesMarginRatio ratio : zxCtSuppliesMarginRatioList) {
        		ZxCtSuppliesContract contract = zxCtSuppliesContractMapper.selectByPrimaryKey(ratio.getContractID());
        		if(StrUtil.equals(contract.getCode7(), "WZ") || StrUtil.equals(contract.getCode7(), "LC") ) {
        			ZxCtSuppliesShopSettlementItem item = new ZxCtSuppliesShopSettlementItem();
        			item.setContractID(ratio.getContractID());
        			item.setStatisticsNo(ratio.getZxCtSuppliesMarginRatioId());
        			if(zxCtSuppliesShopSettlementItemMapper.selectByZxCtSuppliesShopSettlementItemList(item).size() > 0) {
        				return repEntity.layerMessage("NO", "??????????????????????????????????????????????????????");
        			}
        		}else if(StrUtil.equals(contract.getCode7(), "WL")) {
        			ZxCtSuppliesLeaseSettlementItem item = new ZxCtSuppliesLeaseSettlementItem();
        			item.setContractID(ratio.getContractID());
        			item.setStatisticsNo(ratio.getZxCtSuppliesMarginRatioId());
        			if(zxCtSuppliesLeaseSettlementItemMapper.selectByZxCtSuppliesLeaseSettlementItemList(item).size()>0) {
        				return repEntity.layerMessage("NO", "??????????????????????????????????????????????????????");
        			}
        		}
        	}
           ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio = new ZxCtSuppliesMarginRatio();
           zxCtSuppliesMarginRatio.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesMarginRatioMapper.batchDeleteUpdateZxCtSuppliesMarginRatio(zxCtSuppliesMarginRatioList, zxCtSuppliesMarginRatio);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesMarginRatioList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity saveZxCtSuppliesMarginRatioByConID(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesMarginRatio.setZxCtSuppliesMarginRatioId(UuidUtil.generate());
        zxCtSuppliesMarginRatio.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesMarginRatioMapper.insert(zxCtSuppliesMarginRatio);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesMarginRatio);
        }
    }
    
    @Override
    public ResponseEntity updateZxCtSuppliesMarginRatioByConID(ZxCtSuppliesMarginRatio zxCtSuppliesMarginRatio) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesMarginRatio dbZxCtSuppliesMarginRatio = zxCtSuppliesMarginRatioMapper.selectByPrimaryKey(zxCtSuppliesMarginRatio.getZxCtSuppliesMarginRatioId());
        if (dbZxCtSuppliesMarginRatio != null && StrUtil.isNotEmpty(dbZxCtSuppliesMarginRatio.getZxCtSuppliesMarginRatioId())) {
           // ??????????????????
           dbZxCtSuppliesMarginRatio.setEditTime(zxCtSuppliesMarginRatio.getEditTime());
           // ??????ID
           dbZxCtSuppliesMarginRatio.setOrgID(zxCtSuppliesMarginRatio.getOrgID());
           // ??????????????????
           dbZxCtSuppliesMarginRatio.setAllowDelete(zxCtSuppliesMarginRatio.getAllowDelete());
           // ??????
           dbZxCtSuppliesMarginRatio.setTimeLimit(zxCtSuppliesMarginRatio.getTimeLimit());
           // ????????????(%)
           dbZxCtSuppliesMarginRatio.setStatisticsRate(zxCtSuppliesMarginRatio.getStatisticsRate());
           // ??????ID
           dbZxCtSuppliesMarginRatio.setContractID(zxCtSuppliesMarginRatio.getContractID());
           // ????????????
           dbZxCtSuppliesMarginRatio.setBackCondition(zxCtSuppliesMarginRatio.getBackCondition());
           // ???????????????
           dbZxCtSuppliesMarginRatio.setStatisticsNo(zxCtSuppliesMarginRatio.getStatisticsNo());
           // ?????????
           dbZxCtSuppliesMarginRatio.setStatisticsName(zxCtSuppliesMarginRatio.getStatisticsName());
           // ??????
           dbZxCtSuppliesMarginRatio.setRemarks(zxCtSuppliesMarginRatio.getRemarks());
           // ??????
           dbZxCtSuppliesMarginRatio.setSort(zxCtSuppliesMarginRatio.getSort());
           // ??????
           dbZxCtSuppliesMarginRatio.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesMarginRatioMapper.updateByPrimaryKey(dbZxCtSuppliesMarginRatio);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesMarginRatio);
        }
    }
}
