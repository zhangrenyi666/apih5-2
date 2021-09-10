package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxCtOtherManageMapper;
import com.apih5.mybatis.dao.ZxSaOtherEquipSettleItemMapper;
import com.apih5.mybatis.pojo.ZxCtOtherManage;
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtOtherManageAmtRateMapper;
import com.apih5.mybatis.pojo.ZxCtOtherManageAmtRate;
import com.apih5.service.ZxCtOtherManageAmtRateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtOtherManageAmtRateService")
public class ZxCtOtherManageAmtRateServiceImpl implements ZxCtOtherManageAmtRateService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateMapper zxCtOtherManageAmtRateMapper;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemMapper zxSaOtherEquipSettleItemMapper;

    @Override
    public ResponseEntity getZxCtOtherManageAmtRateListByCondition(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        if (zxCtOtherManageAmtRate == null) {
            zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
        }
        // 分页查询
        PageHelper.startPage(zxCtOtherManageAmtRate.getPage(),zxCtOtherManageAmtRate.getLimit());
        // 获取数据
        List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateMapper.selectByZxCtOtherManageAmtRateList(zxCtOtherManageAmtRate);
        // 得到分页信息
        PageInfo<ZxCtOtherManageAmtRate> p = new PageInfo<>(zxCtOtherManageAmtRateList);

        return repEntity.okList(zxCtOtherManageAmtRateList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherManageAmtRateDetail(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        if (zxCtOtherManageAmtRate == null) {
            zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
        }
        // 获取数据
        ZxCtOtherManageAmtRate dbZxCtOtherManageAmtRate = zxCtOtherManageAmtRateMapper.selectByPrimaryKey(zxCtOtherManageAmtRate.getZxCtOtherManageAmtRateId());
        // 数据存在
        if (dbZxCtOtherManageAmtRate != null) {
            return repEntity.ok(dbZxCtOtherManageAmtRate);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtOtherManageAmtRate(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 其他合同管理id不能为空
        if (StrUtil.isEmpty(zxCtOtherManageAmtRate.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "其他合同管理ID不能为空！");
        }
        zxCtOtherManageAmtRate.setZxCtOtherManageId(zxCtOtherManageAmtRate.getZxCtOtherManageId());
        zxCtOtherManageAmtRate.setAllowDelete("Y");
        zxCtOtherManageAmtRate.setUseCount(new BigDecimal(0));
        zxCtOtherManageAmtRate.setZxCtOtherManageAmtRateId(UuidUtil.generate());
        zxCtOtherManageAmtRate.setCreateUserInfo(userKey, realName);
        int flag = zxCtOtherManageAmtRateMapper.insert(zxCtOtherManageAmtRate);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOtherManageAmtRate);
        }
    }

    @Override
    public ResponseEntity updateZxCtOtherManageAmtRate(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherManageAmtRate dbZxCtOtherManageAmtRate = zxCtOtherManageAmtRateMapper.selectByPrimaryKey(zxCtOtherManageAmtRate.getZxCtOtherManageAmtRateId());
        if (dbZxCtOtherManageAmtRate != null && StrUtil.isNotEmpty(dbZxCtOtherManageAmtRate.getZxCtOtherManageAmtRateId())) {
           // 期限
           dbZxCtOtherManageAmtRate.setTimeLimit(zxCtOtherManageAmtRate.getTimeLimit());
           // 保证金
           dbZxCtOtherManageAmtRate.setStatisticsName(zxCtOtherManageAmtRate.getStatisticsName());
           // 返还条件
           dbZxCtOtherManageAmtRate.setBackCondition(zxCtOtherManageAmtRate.getBackCondition());
           // 扣除比例(%)
           dbZxCtOtherManageAmtRate.setStatisticsRate(zxCtOtherManageAmtRate.getStatisticsRate());
           // 备注
           dbZxCtOtherManageAmtRate.setRemark(zxCtOtherManageAmtRate.getRemark());
           // 排序
           dbZxCtOtherManageAmtRate.setSort(zxCtOtherManageAmtRate.getSort());
           // 共通
           dbZxCtOtherManageAmtRate.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherManageAmtRateMapper.updateByPrimaryKey(dbZxCtOtherManageAmtRate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherManageAmtRate);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOtherManageAmtRate(List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtOtherManageAmtRateList != null && zxCtOtherManageAmtRateList.size() > 0) {
            ZxCtOtherManageAmtRate zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
            zxCtOtherManageAmtRate.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherManageAmtRateMapper.batchDeleteUpdateZxCtOtherManageAmtRate(zxCtOtherManageAmtRateList, zxCtOtherManageAmtRate);
            for (ZxCtOtherManageAmtRate zxCtOtherManageAmtRate1 : zxCtOtherManageAmtRateList) {
                ZxSaOtherEquipSettleItem dbZxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
                dbZxSaOtherEquipSettleItem.setZxCtOtherManageAmtRateId(zxCtOtherManageAmtRate1.getZxCtOtherManageAmtRateId());
                List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList = zxSaOtherEquipSettleItemMapper.selectByZxSaOtherEquipSettleItemList(dbZxSaOtherEquipSettleItem);
                if (zxSaOtherEquipSettleItemList != null && zxSaOtherEquipSettleItemList.size() > 0) {
                    return repEntity.layerMessage("no", zxCtOtherManageAmtRate1.getStatisticsName()+"已被结算引用，不能删除！");
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtOtherManageAmtRateList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    @Override
    public ResponseEntity getZxCtOtherManageAmtRateByContractId(ZxCtOtherManageAmtRate zxCtOtherManageAmtRate) {
        if (StrUtil.isEmpty(zxCtOtherManageAmtRate.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "合同ID不能为空！");
        }

        String zxCtOtherManageId = zxCtOtherManageAmtRate.getZxCtOtherManageId();

        List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = new ArrayList<>();

        ZxCtOtherManage zxCtOtherManage = new ZxCtOtherManage();
        zxCtOtherManage.setZxCtOtherManageId(zxCtOtherManageId);
        List<ZxCtOtherManage> zxCtOtherManageList = zxCtOtherManageMapper.selectByZxCtOtherManageList(zxCtOtherManage);
        if (zxCtOtherManageList != null || zxCtOtherManageList.size() > 0) {
            zxCtOtherManageAmtRateList.add(getItem(zxCtOtherManageId, "100400", "1、质量保证金"));
            zxCtOtherManageAmtRateList.add(getItem(zxCtOtherManageId, "100500", "2、安全生产保证金"));
            zxCtOtherManageAmtRateList.add(getItem(zxCtOtherManageId, "100600", "3、农民工工资偿付保证金"));
            zxCtOtherManageAmtRateList.add(getItem(zxCtOtherManageId, "100610", "4、履约保证金"));
        }
        return repEntity.ok(zxCtOtherManageAmtRateList);
    }

    /**
     * 获取保证金明细
     * @param contractID		合同ID
     * @param statisticsNo		保证金编号
     * @param statisticsName	保证金名称
     * @return
     */
    private ZxCtOtherManageAmtRate getItem(String contractID,  String statisticsNo, String statisticsName) {
        ZxCtOtherManageAmtRate zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
        zxCtOtherManageAmtRate.setZxCtOtherManageId(contractID);
        zxCtOtherManageAmtRate.setStatisticsNo(statisticsNo);
        zxCtOtherManageAmtRate.setStatisticsName(statisticsName);
        zxCtOtherManageAmtRate.setStatisticsRate(new BigDecimal(0));
        zxCtOtherManageAmtRate.setTimeLimit("");
        zxCtOtherManageAmtRate.setBackCondition("");
        return zxCtOtherManageAmtRate;
    }
}
