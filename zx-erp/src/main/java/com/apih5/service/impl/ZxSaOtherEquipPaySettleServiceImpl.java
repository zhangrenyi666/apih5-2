package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSaOtherEquipPaySettleItemMapper;
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaOtherEquipPaySettleMapper;
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettle;
import com.apih5.service.ZxSaOtherEquipPaySettleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaOtherEquipPaySettleService")
public class ZxSaOtherEquipPaySettleServiceImpl implements ZxSaOtherEquipPaySettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleMapper zxSaOtherEquipPaySettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleItemMapper zxSaOtherEquipPaySettleItemMapper;


    @Override
    public ResponseEntity getZxSaOtherEquipPaySettleListByCondition(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle) {
        if (zxSaOtherEquipPaySettle == null) {
            zxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
        }
        // 分页查询
        PageHelper.startPage(zxSaOtherEquipPaySettle.getPage(),zxSaOtherEquipPaySettle.getLimit());
        // 获取数据
        List<ZxSaOtherEquipPaySettle> zxSaOtherEquipPaySettleList = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(zxSaOtherEquipPaySettle);
        for (ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle1 : zxSaOtherEquipPaySettleList) {
            // 支付项结算明细list
            ZxSaOtherEquipPaySettleItem dbZxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
            dbZxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettle1.getZxSaOtherEquipPaySettleId());
            List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(dbZxSaOtherEquipPaySettleItem);
            zxSaOtherEquipPaySettle1.setZxSaOtherEquipPaySettleItemList(zxSaOtherEquipResSettleItemList);
        }
        // 得到分页信息
        PageInfo<ZxSaOtherEquipPaySettle> p = new PageInfo<>(zxSaOtherEquipPaySettleList);

        return repEntity.okList(zxSaOtherEquipPaySettleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaOtherEquipPaySettleDetail(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle) {
        if (zxSaOtherEquipPaySettle == null) {
            zxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
        }
        // 获取数据
        ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(zxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleId());
        // 数据存在
        if (dbZxSaOtherEquipPaySettle != null) {
            // 支付项结算清单明细列表list
            ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
            zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(dbZxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleId());
            List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(zxSaOtherEquipPaySettleItem);
            dbZxSaOtherEquipPaySettle.setZxSaOtherEquipPaySettleItemList(zxSaOtherEquipPaySettleItemList);
            return repEntity.ok(dbZxSaOtherEquipPaySettle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaOtherEquipPaySettle(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 其他结算单id不能为空
        if (StrUtil.isEmpty(zxSaOtherEquipPaySettle.getZxSaOtherEquipSettleId())) {
            return repEntity.layerMessage("no", "其他结算单ID不能为空！");
        }
        zxSaOtherEquipPaySettle.setZxSaOtherEquipSettleId(zxSaOtherEquipPaySettle.getZxSaOtherEquipSettleId());
        zxSaOtherEquipPaySettle.setZxSaOtherEquipPaySettleId(UuidUtil.generate());
        zxSaOtherEquipPaySettle.setCreateUserInfo(userKey, realName);
        int flag = zxSaOtherEquipPaySettleMapper.insert(zxSaOtherEquipPaySettle);

        //  添加支付项结算清单明细
        List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleItemList();
        if(zxSaOtherEquipPaySettleItemList != null && zxSaOtherEquipPaySettleItemList.size()>0) {
            for(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem : zxSaOtherEquipPaySettleItemList) {
                zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleId());
                zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleItemId(UuidUtil.createUUID());
                zxSaOtherEquipPaySettleItem.setCreateUserInfo(userKey, realName);
                zxSaOtherEquipPaySettleItemMapper.insert(zxSaOtherEquipPaySettleItem);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaOtherEquipPaySettle);
        }
    }

    @Override
    public ResponseEntity updateZxSaOtherEquipPaySettle(ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipPaySettle dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByPrimaryKey(zxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleId());
        if (dbZxSaOtherEquipPaySettle != null && StrUtil.isNotEmpty(dbZxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleId())) {
           // 其他支付项上期末累计
           dbZxSaOtherEquipPaySettle.setCheckUpOtherAmt(zxSaOtherEquipPaySettle.getCheckUpOtherAmt());
           // 本期支付项结算不含税金额(元)
           dbZxSaOtherEquipPaySettle.setThisAmtNoTax(zxSaOtherEquipPaySettle.getThisAmtNoTax());
           // 本期支付项结算税额(元)
           dbZxSaOtherEquipPaySettle.setThisAmtTax(zxSaOtherEquipPaySettle.getThisAmtTax());
           // 合同类型
           dbZxSaOtherEquipPaySettle.setContrType(zxSaOtherEquipPaySettle.getContrType());
           // 本期支付项结算金额(元)
           dbZxSaOtherEquipPaySettle.setThisAmt(zxSaOtherEquipPaySettle.getThisAmt());
           // 本期进退场费
           dbZxSaOtherEquipPaySettle.setInOutAmt(zxSaOtherEquipPaySettle.getInOutAmt());
           // 上期末奖罚
           dbZxSaOtherEquipPaySettle.setUpFineAmt(zxSaOtherEquipPaySettle.getUpFineAmt());
           // 本期其它结算支付项
           dbZxSaOtherEquipPaySettle.setOtherAmt(zxSaOtherEquipPaySettle.getOtherAmt());
           // 所属公司名称
           dbZxSaOtherEquipPaySettle.setComName(zxSaOtherEquipPaySettle.getComName());
           // 项目ID
           dbZxSaOtherEquipPaySettle.setOrgId(zxSaOtherEquipPaySettle.getOrgId());
           // 项目名称
           dbZxSaOtherEquipPaySettle.setOrgName(zxSaOtherEquipPaySettle.getOrgName());
           // 合同ID
           dbZxSaOtherEquipPaySettle.setZxCtOtherManageId(zxSaOtherEquipPaySettle.getZxCtOtherManageId());
           // 结算单编号
           dbZxSaOtherEquipPaySettle.setBillNo(zxSaOtherEquipPaySettle.getBillNo());
           // 结算期次
           dbZxSaOtherEquipPaySettle.setPeriod(zxSaOtherEquipPaySettle.getPeriod());
           // 累计支付项结算金额(元)
           dbZxSaOtherEquipPaySettle.setTotalAmt(zxSaOtherEquipPaySettle.getTotalAmt());
           // 上期末进退场费
           dbZxSaOtherEquipPaySettle.setUpInOutAmt(zxSaOtherEquipPaySettle.getUpInOutAmt());
           // 本期奖罚
           dbZxSaOtherEquipPaySettle.setFineAmt(zxSaOtherEquipPaySettle.getFineAmt());
           // 本期伙食费
           dbZxSaOtherEquipPaySettle.setFoodAmt(zxSaOtherEquipPaySettle.getFoodAmt());
           // 上期末伙食费
           dbZxSaOtherEquipPaySettle.setUpFoodAmt(zxSaOtherEquipPaySettle.getUpFoodAmt());
           // 上期末其它结算支付项
           dbZxSaOtherEquipPaySettle.setUpOtherAmt(zxSaOtherEquipPaySettle.getUpOtherAmt());
           // 上期末累计支付项结算金额(元)
           dbZxSaOtherEquipPaySettle.setUpAmt(zxSaOtherEquipPaySettle.getUpAmt());
           // 所属公司ID
           dbZxSaOtherEquipPaySettle.setComId(zxSaOtherEquipPaySettle.getComId());
           // 所属公司排序
           dbZxSaOtherEquipPaySettle.setComOrders(zxSaOtherEquipPaySettle.getComOrders());
           // 备注
           dbZxSaOtherEquipPaySettle.setRemark(zxSaOtherEquipPaySettle.getRemark());
           // 排序
           dbZxSaOtherEquipPaySettle.setSort(zxSaOtherEquipPaySettle.getSort());
           // 共通
           dbZxSaOtherEquipPaySettle.setModifyUserInfo(userKey, realName);
           flag = zxSaOtherEquipPaySettleMapper.updateByPrimaryKey(dbZxSaOtherEquipPaySettle);

            // 支付项结算清单明细删除
            ZxSaOtherEquipPaySettleItem delZxSaOtherEquipPaySettleItem = new ZxSaOtherEquipPaySettleItem();
            delZxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleId());
            List<ZxSaOtherEquipPaySettleItem> paySettleItems = zxSaOtherEquipPaySettleItemMapper.selectByZxSaOtherEquipPaySettleItemList(delZxSaOtherEquipPaySettleItem);
            if (paySettleItems != null && paySettleItems.size() > 0) {
                delZxSaOtherEquipPaySettleItem.setModifyUserInfo(userKey, realName);
                zxSaOtherEquipPaySettleItemMapper.batchDeleteUpdateZxSaOtherEquipPaySettleItem(paySettleItems, delZxSaOtherEquipPaySettleItem);
            }

            // 添加支付项结算清单明细list
            List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleItemList();
            if(zxSaOtherEquipPaySettleItemList != null && zxSaOtherEquipPaySettleItemList.size()>0) {
                for(ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem : zxSaOtherEquipPaySettleItemList) {
                    zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleId(zxSaOtherEquipPaySettle.getZxSaOtherEquipPaySettleId());
                    zxSaOtherEquipPaySettleItem.setZxSaOtherEquipPaySettleItemId(UuidUtil.createUUID());
                    zxSaOtherEquipPaySettleItem.setCreateUserInfo(userKey, realName);
                    zxSaOtherEquipPaySettleItemMapper.insert(zxSaOtherEquipPaySettleItem);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaOtherEquipPaySettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipPaySettle(List<ZxSaOtherEquipPaySettle> zxSaOtherEquipPaySettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaOtherEquipPaySettleList != null && zxSaOtherEquipPaySettleList.size() > 0) {
           ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
           zxSaOtherEquipPaySettle.setModifyUserInfo(userKey, realName);
           flag = zxSaOtherEquipPaySettleMapper.batchDeleteUpdateZxSaOtherEquipPaySettle(zxSaOtherEquipPaySettleList, zxSaOtherEquipPaySettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaOtherEquipPaySettleList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
