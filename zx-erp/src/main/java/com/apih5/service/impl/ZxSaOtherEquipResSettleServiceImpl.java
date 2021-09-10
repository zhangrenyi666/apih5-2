package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSaOtherEquipResSettleItemMapper;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaOtherEquipResSettleMapper;
import com.apih5.service.ZxSaOtherEquipResSettleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaOtherEquipResSettleService")
public class ZxSaOtherEquipResSettleServiceImpl implements ZxSaOtherEquipResSettleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleMapper zxSaOtherEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleItemMapper zxSaOtherEquipResSettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemServiceImpl zxSaOtherEquipSettleItemServiceImpl;

    @Override
    public ResponseEntity getZxSaOtherEquipResSettleListByCondition(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle) {
        if (zxSaOtherEquipResSettle == null) {
            zxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
        }
        // 分页查询
        PageHelper.startPage(zxSaOtherEquipResSettle.getPage(),zxSaOtherEquipResSettle.getLimit());
        // 获取数据
        List<ZxSaOtherEquipResSettle> zxSaOtherEquipResSettleList = zxSaOtherEquipResSettleMapper.selectByZxSaOtherEquipResSettleList(zxSaOtherEquipResSettle);
        for (ZxSaOtherEquipResSettle zxSaOtherEquipResSettle1 : zxSaOtherEquipResSettleList) {
            // 细目结算明细list
            ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
            dbZxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleId(zxSaOtherEquipResSettle1.getZxSaOtherEquipResSettleId());
            List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemMapper.selectByZxSaOtherEquipResSettleItemList(dbZxSaOtherEquipResSettleItem);
            zxSaOtherEquipResSettle1.setZxSaOtherEquipResSettleItemList(zxSaOtherEquipResSettleItemList);
        }
        // 得到分页信息
        PageInfo<ZxSaOtherEquipResSettle> p = new PageInfo<>(zxSaOtherEquipResSettleList);

        return repEntity.okList(zxSaOtherEquipResSettleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaOtherEquipResSettleDetail(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle) {
        if (zxSaOtherEquipResSettle == null) {
            zxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
        }
        // 获取数据
        ZxSaOtherEquipResSettle dbZxSaOtherEquipResSettle = zxSaOtherEquipResSettleMapper.selectByPrimaryKey(zxSaOtherEquipResSettle.getZxSaOtherEquipResSettleId());
        // 数据存在
        if (dbZxSaOtherEquipResSettle != null) {
            // 细目结算清单明细列表list
            ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem = new ZxSaOtherEquipResSettleItem();
            zxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleId(dbZxSaOtherEquipResSettle.getZxSaOtherEquipResSettleId());
            List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemMapper.selectByZxSaOtherEquipResSettleItemList(zxSaOtherEquipResSettleItem);
            dbZxSaOtherEquipResSettle.setZxSaOtherEquipResSettleItemList(zxSaOtherEquipResSettleItemList);
            return repEntity.ok(dbZxSaOtherEquipResSettle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaOtherEquipResSettle(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 其他结算单id不能为空
        if (StrUtil.isEmpty(zxSaOtherEquipResSettle.getZxSaOtherEquipSettleId())) {
            return repEntity.layerMessage("no", "其他结算单ID不能为空！");
        }
        zxSaOtherEquipResSettle.setZxSaOtherEquipSettleId(zxSaOtherEquipResSettle.getZxSaOtherEquipSettleId());
        zxSaOtherEquipResSettle.setZxSaOtherEquipResSettleId(UuidUtil.generate());
        zxSaOtherEquipResSettle.setCreateUserInfo(userKey, realName);
        int flag = zxSaOtherEquipResSettleMapper.insert(zxSaOtherEquipResSettle);
        //  添加细目结算清单明细
        List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettle.getZxSaOtherEquipResSettleItemList();

        if(zxSaOtherEquipResSettleItemList != null && zxSaOtherEquipResSettleItemList.size()>0) {
            for(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem : zxSaOtherEquipResSettleItemList) {
                zxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleId(zxSaOtherEquipResSettle.getZxSaOtherEquipResSettleId());
                zxSaOtherEquipResSettleItem.setZxSaOtherEquipResSettleItemId(UuidUtil.createUUID());
                zxSaOtherEquipResSettleItem.setCreateUserInfo(userKey, realName);
                zxSaOtherEquipResSettleItemMapper.insert(zxSaOtherEquipResSettleItem);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaOtherEquipResSettle);
        }
    }

    @Override
    public ResponseEntity updateZxSaOtherEquipResSettle(ZxSaOtherEquipResSettle zxSaOtherEquipResSettle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipResSettle dbZxSaOtherEquipResSettle = zxSaOtherEquipResSettleMapper.selectByPrimaryKey(zxSaOtherEquipResSettle.getZxSaOtherEquipResSettleId());
        if (dbZxSaOtherEquipResSettle != null && StrUtil.isNotEmpty(dbZxSaOtherEquipResSettle.getZxSaOtherEquipResSettleId())) {
           // 税率(%)
           dbZxSaOtherEquipResSettle.setTaxRate(zxSaOtherEquipResSettle.getTaxRate());
           // 是否抵扣
           dbZxSaOtherEquipResSettle.setIsDeduct(zxSaOtherEquipResSettle.getIsDeduct());
           // 本期清单结算不含税金额(元)
           dbZxSaOtherEquipResSettle.setThisAmtNoTax(zxSaOtherEquipResSettle.getThisAmtNoTax());
           // 本期清单结算税额(元)
           dbZxSaOtherEquipResSettle.setThisAmtTax(zxSaOtherEquipResSettle.getThisAmtTax());
           // 签认单编号
           dbZxSaOtherEquipResSettle.setSignedNos(zxSaOtherEquipResSettle.getSignedNos());
           // 含税合同金额(万元)
           dbZxSaOtherEquipResSettle.setContractAmt(zxSaOtherEquipResSettle.getContractAmt());
           // 本期清单结算含税金额(元)
           dbZxSaOtherEquipResSettle.setThisAmt(zxSaOtherEquipResSettle.getThisAmt());
           // 项目ID
           dbZxSaOtherEquipResSettle.setOrgId(zxSaOtherEquipResSettle.getOrgId());
           // 项目名称
           dbZxSaOtherEquipResSettle.setOrgName(zxSaOtherEquipResSettle.getOrgName());
           // 签认单ID
           dbZxSaOtherEquipResSettle.setSignedOrders(zxSaOtherEquipResSettle.getSignedOrders());
           // 结算期次
           dbZxSaOtherEquipResSettle.setPeriod(zxSaOtherEquipResSettle.getPeriod());
           // 结算单编号
           dbZxSaOtherEquipResSettle.setBillNo(zxSaOtherEquipResSettle.getBillNo());
           // 变更后含税合同金额(万元)
           dbZxSaOtherEquipResSettle.setChangeAmt(zxSaOtherEquipResSettle.getChangeAmt());
           // 累计清单结算含税金额(元)
           dbZxSaOtherEquipResSettle.setTotalAmt(zxSaOtherEquipResSettle.getTotalAmt());
           // 上期末清单结算金额(元)
            if (zxSaOtherEquipResSettle.getUpAmt() == null || "".equals(zxSaOtherEquipResSettle.getUpAmt())) {
                return repEntity.layerMessage("no", "上期末清单结算金额(元)不能为空！");
            }
           dbZxSaOtherEquipResSettle.setUpAmt(zxSaOtherEquipResSettle.getUpAmt());
           // 所属公司ID
           dbZxSaOtherEquipResSettle.setComId(zxSaOtherEquipResSettle.getComId());
           // 所属公司名称
           dbZxSaOtherEquipResSettle.setComName(zxSaOtherEquipResSettle.getComName());
           // 所属公司排序
           dbZxSaOtherEquipResSettle.setComOrders(zxSaOtherEquipResSettle.getComOrders());
           // 是否首次结算
           dbZxSaOtherEquipResSettle.setIsFirst(zxSaOtherEquipResSettle.getIsFirst());
           // 备注
           dbZxSaOtherEquipResSettle.setRemark(zxSaOtherEquipResSettle.getRemark());
           // 排序
           dbZxSaOtherEquipResSettle.setSort(zxSaOtherEquipResSettle.getSort());
           // 共通
           dbZxSaOtherEquipResSettle.setModifyUserInfo(userKey, realName);
           flag = zxSaOtherEquipResSettleMapper.updateByPrimaryKey(dbZxSaOtherEquipResSettle);

            // 更新细目结算清单明细 list
            List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettle.getZxSaOtherEquipResSettleItemList();
            if(zxSaOtherEquipResSettleItemList != null && zxSaOtherEquipResSettleItemList.size()>0) {
                BigDecimal totalQty = null;
                for(ZxSaOtherEquipResSettleItem zxSaOtherEquipResSettleItem : zxSaOtherEquipResSettleItemList) {
                    ZxSaOtherEquipResSettleItem dbZxSaOtherEquipResSettleItem = zxSaOtherEquipResSettleItemMapper.selectByPrimaryKey(zxSaOtherEquipResSettleItem.getZxSaOtherEquipResSettleItemId());
                    // 本期结算数量+ 上期末累计结算数量，也就是至本期末累计结算数量
                    totalQty = CalcUtils.calcAdd(zxSaOtherEquipResSettleItem.getThisQty(),zxSaOtherEquipResSettleItem.getUpQty());
                    BigDecimal qty;
                    if (dbZxSaOtherEquipResSettleItem.getChangedQty() != null && dbZxSaOtherEquipResSettleItem.getChangedQty().compareTo(new BigDecimal(0)) > 0) {
                        qty = dbZxSaOtherEquipResSettleItem.getChangedQty();
                    } else {
                        qty = dbZxSaOtherEquipResSettleItem.getContractQty();
                    }
                    if (CalcUtils.compareTo(totalQty, qty) > 0) {
                        return repEntity.layerMessage("no", "本期结算数量+上期末累计结算数量，也就是至本期末累计结算数量不能超过合同数量！");
                    }
                    // 本期结算数量
                    dbZxSaOtherEquipResSettleItem.setThisQty(zxSaOtherEquipResSettleItem.getThisQty());
                    // 本期结算含税金额(元)
                    dbZxSaOtherEquipResSettleItem.setThisAmt(zxSaOtherEquipResSettleItem.getThisAmt());
                    // 本期结算不含税金额(元)
                    dbZxSaOtherEquipResSettleItem.setThisAmtNoTax(zxSaOtherEquipResSettleItem.getThisAmtNoTax());
                    // 本期结算税额
                    dbZxSaOtherEquipResSettleItem.setThisAmtTax(zxSaOtherEquipResSettleItem.getThisAmtTax());
                    // 计算式
                    dbZxSaOtherEquipResSettleItem.setPlanning(zxSaOtherEquipResSettleItem.getPlanning());
                    // 备注
                    dbZxSaOtherEquipResSettleItem.setRemark(zxSaOtherEquipResSettleItem.getRemark());
                    // 至本期末累计结算数量
                    dbZxSaOtherEquipResSettleItem.setTotalQty(totalQty);
                    // 至本期末累计结算含税金额(元) = 本期结算含税金额(元) + 至上期末累计结算含税金额(元)
                    dbZxSaOtherEquipResSettleItem.setTotalAmt(CalcUtils.calcAdd(zxSaOtherEquipResSettleItem.getThisAmt(),zxSaOtherEquipResSettleItem.getUpAmt()));
                    dbZxSaOtherEquipResSettleItem.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipResSettleItemMapper.updateByPrimaryKey(dbZxSaOtherEquipResSettleItem);
                }
            }

            // 初始化统计项子表页面数据
            ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
            // 结算单主表id
            zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipResSettle.getZxSaOtherEquipSettleId());
            // 合同管理id
            zxSaOtherEquipSettleItem.setZxCtOtherManageId(dbZxSaOtherEquipResSettle.getZxCtOtherManageId());
            zxSaOtherEquipSettleItemServiceImpl.updateZxSaOtherEquipSettleItemStatistics(zxSaOtherEquipSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaOtherEquipResSettle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipResSettle(List<ZxSaOtherEquipResSettle> zxSaOtherEquipResSettleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaOtherEquipResSettleList != null && zxSaOtherEquipResSettleList.size() > 0) {
           ZxSaOtherEquipResSettle zxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
           zxSaOtherEquipResSettle.setModifyUserInfo(userKey, realName);
           flag = zxSaOtherEquipResSettleMapper.batchDeleteUpdateZxSaOtherEquipResSettle(zxSaOtherEquipResSettleList, zxSaOtherEquipResSettle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaOtherEquipResSettleList);
        }
    }
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
