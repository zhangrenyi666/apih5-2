package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.apih5.mybatis.dao.ZxSaFsSettlementMapper;

import com.apih5.mybatis.pojo.ZxSaFsSettlement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtFsContractBondMapper;
import com.apih5.mybatis.pojo.ZxCtFsContractBond;
import com.apih5.service.ZxCtFsContractBondService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zxCtFsContractBondService")
public class ZxCtFsContractBondServiceImpl implements ZxCtFsContractBondService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtFsContractBondMapper zxCtFsContractBondMapper;


    @Autowired(required = true)
    private ZxSaFsSettlementMapper zxSaFsSettlementMapper;

    @Override
    public ResponseEntity getZxCtFsContractBondListByCondition(ZxCtFsContractBond zxCtFsContractBond) {
        if (zxCtFsContractBond == null) {
            zxCtFsContractBond = new ZxCtFsContractBond();
        }
        // 分页查询
        PageHelper.startPage(zxCtFsContractBond.getPage(), zxCtFsContractBond.getLimit());
        // 获取数据
        List<ZxCtFsContractBond> zxCtFsContractBondList = zxCtFsContractBondMapper.selectByZxCtFsContractBondList(zxCtFsContractBond);
        // 得到分页信息
        PageInfo<ZxCtFsContractBond> p = new PageInfo<>(zxCtFsContractBondList);

        return repEntity.okList(zxCtFsContractBondList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsContractBondDetail(ZxCtFsContractBond zxCtFsContractBond) {
        if (zxCtFsContractBond == null) {
            zxCtFsContractBond = new ZxCtFsContractBond();
        }
        // 获取数据
        ZxCtFsContractBond dbZxCtFsContractBond = zxCtFsContractBondMapper.selectByPrimaryKey(zxCtFsContractBond.getZxCtFsContractBondId());
        // 数据存在
        if (dbZxCtFsContractBond != null) {
            return repEntity.ok(dbZxCtFsContractBond);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsContractBond(ZxCtFsContractBond zxCtFsContractBond) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsContractBond.setZxCtFsContractBondId(UuidUtil.generate());
        zxCtFsContractBond.setCreateUserInfo(userKey, realName);
        int flag = zxCtFsContractBondMapper.insert(zxCtFsContractBond);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtFsContractBond);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsContractBond(ZxCtFsContractBond zxCtFsContractBond) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsContractBond dbZxCtFsContractBond = zxCtFsContractBondMapper.selectByPrimaryKey(zxCtFsContractBond.getZxCtFsContractBondId());
        if (dbZxCtFsContractBond != null && StrUtil.isNotEmpty(dbZxCtFsContractBond.getZxCtFsContractBondId())) {
            // 项目ID
            dbZxCtFsContractBond.setOrgID(zxCtFsContractBond.getOrgID());
            // 附属合同ID
            dbZxCtFsContractBond.setZxCtFsContractId(zxCtFsContractBond.getZxCtFsContractId());
            // 保证金编号
            dbZxCtFsContractBond.setStatisticsNo(zxCtFsContractBond.getStatisticsNo());
            // 保证金
            dbZxCtFsContractBond.setStatisticsName(zxCtFsContractBond.getStatisticsName());
            // 扣除比例(%)
            dbZxCtFsContractBond.setStatisticsRate(zxCtFsContractBond.getStatisticsRate());
            // 返还条件
            dbZxCtFsContractBond.setBackCondition(zxCtFsContractBond.getBackCondition());
            // 期限
            dbZxCtFsContractBond.setTimeLimit(zxCtFsContractBond.getTimeLimit());
            // 是否允许删除
            dbZxCtFsContractBond.setAllowDelete(zxCtFsContractBond.getAllowDelete());
            // 最后编辑时间
            dbZxCtFsContractBond.setEditTime(zxCtFsContractBond.getEditTime());
            // 是否被结算引用
            dbZxCtFsContractBond.setUseCount(zxCtFsContractBond.getUseCount());
            // 是否被设备结算引用
            dbZxCtFsContractBond.setEquipUseCount(zxCtFsContractBond.getEquipUseCount());
            // 是否被物资结算引用
            dbZxCtFsContractBond.setStockUseCount(zxCtFsContractBond.getStockUseCount());
            // 备注
            dbZxCtFsContractBond.setRemarks(zxCtFsContractBond.getRemarks());
            // 排序
            dbZxCtFsContractBond.setSort(zxCtFsContractBond.getSort());
            // 共通
            dbZxCtFsContractBond.setModifyUserInfo(userKey, realName);
            flag = zxCtFsContractBondMapper.updateByPrimaryKey(dbZxCtFsContractBond);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxCtFsContractBond);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsContractBond(List<ZxCtFsContractBond> zxCtFsContractBondList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtFsContractBondList != null && zxCtFsContractBondList.size() > 0) {
            for (ZxCtFsContractBond zxBond : zxCtFsContractBondList
            ) {
                ZxSaFsSettlement zxSaFsSettlement = new ZxSaFsSettlement();
                zxSaFsSettlement.setZxCtFsContractId(zxBond.getZxCtFsContractId());
                if (zxSaFsSettlementMapper.selectByZxSaFsSettlementList(zxSaFsSettlement).size() > 0) {
                    return repEntity.layerMessage("no", "发起结算的保证金不能删除！");

                }
            }
            ZxCtFsContractBond zxCtFsContractBond = new ZxCtFsContractBond();
            zxCtFsContractBond.setModifyUserInfo(userKey, realName);
            flag = zxCtFsContractBondMapper.batchDeleteUpdateZxCtFsContractBond(zxCtFsContractBondList, zxCtFsContractBond);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsContractBondList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
