package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.apih5.utils.DigitalConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxSaOtherEquipSettleItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaOtherEquipSettleItemService")
public class ZxSaOtherEquipSettleItemServiceImpl implements ZxSaOtherEquipSettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleItemMapper zxSaOtherEquipSettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleMapper zxSaOtherEquipResSettleMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleMapper zxSaOtherEquipPaySettleMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateMapper zxCtOtherManageAmtRateMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipResSettleItemMapper zxSaOtherEquipResSettleItemMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleMapper zxSaOtherEquipSettleMapper;

    @Override
    public ResponseEntity getZxSaOtherEquipSettleItemListByCondition(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        if (zxSaOtherEquipSettleItem == null) {
            zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        }
        // 分页查询
        PageHelper.startPage(zxSaOtherEquipSettleItem.getPage(),zxSaOtherEquipSettleItem.getLimit());
        // 获取数据
        List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList = zxSaOtherEquipSettleItemMapper.selectByZxSaOtherEquipSettleItemList(zxSaOtherEquipSettleItem);
        // 得到分页信息
        PageInfo<ZxSaOtherEquipSettleItem> p = new PageInfo<>(zxSaOtherEquipSettleItemList);

        return repEntity.okList(zxSaOtherEquipSettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaOtherEquipSettleItemDetail(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        if (zxSaOtherEquipSettleItem == null) {
            zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        }
        // 获取数据
        ZxSaOtherEquipSettleItem dbZxSaOtherEquipSettleItem = zxSaOtherEquipSettleItemMapper.selectByPrimaryKey(zxSaOtherEquipSettleItem.getZxSaOtherEquipSettleItemId());
        // 数据存在
        if (dbZxSaOtherEquipSettleItem != null) {
            return repEntity.ok(dbZxSaOtherEquipSettleItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaOtherEquipSettleItem(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaOtherEquipSettleItem.setZxSaOtherEquipSettleItemId(UuidUtil.generate());
        zxSaOtherEquipSettleItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaOtherEquipSettleItemMapper.insert(zxSaOtherEquipSettleItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaOtherEquipSettleItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaOtherEquipSettleItem(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaOtherEquipSettleItem dbZxSaOtherEquipSettleItem = zxSaOtherEquipSettleItemMapper.selectByPrimaryKey(zxSaOtherEquipSettleItem.getZxSaOtherEquipSettleItemId());
        if (dbZxSaOtherEquipSettleItem != null && StrUtil.isNotEmpty(dbZxSaOtherEquipSettleItem.getZxSaOtherEquipSettleItemId())) {
            // 本期(元)
            dbZxSaOtherEquipSettleItem.setThisAmt(zxSaOtherEquipSettleItem.getThisAmt());
            // 开累(元) 当前统计项的往期开累加上本期
            BigDecimal totalAmt = CalcUtils.calcAdd(dbZxSaOtherEquipSettleItem.getUpAmt(),new BigDecimal(zxSaOtherEquipSettleItem.getThisAmt()));
            dbZxSaOtherEquipSettleItem.setTotalAmt(totalAmt+"");
            // 共通
            dbZxSaOtherEquipSettleItem.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(dbZxSaOtherEquipSettleItem);

            // 返还保证金合计 本期
            BigDecimal returnThisAmt = new BigDecimal(0);
            // 返还保证金合计 开累
            BigDecimal returnUpAmt = new BigDecimal(0);
            // 返还保证金合计 本期 实体
            ZxSaOtherEquipSettleItem item = null;
            ZxSaOtherEquipSettleItem returnItem = new ZxSaOtherEquipSettleItem();
            // 结算单主表id
            returnItem.setZxSaOtherEquipSettleId(dbZxSaOtherEquipSettleItem.getZxSaOtherEquipSettleId());
            // 查询统计项为返还的几项 List
            List<ZxSaOtherEquipSettleItem> dbReturnItemList = zxSaOtherEquipSettleItemMapper.selectByZxSaOtherEquipSettleItemList(returnItem);
            if (dbReturnItemList != null && dbReturnItemList.size() > 0) {
                for (ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem2 : dbReturnItemList) {
                    if("100500".equals(zxSaOtherEquipSettleItem2.getStatisticsType())){
                        item = zxSaOtherEquipSettleItem2;
                    }
                    if("100600".equals(zxSaOtherEquipSettleItem2.getStatisticsType())){
                        returnThisAmt = CalcUtils.calcAdd(returnThisAmt, new BigDecimal(zxSaOtherEquipSettleItem2.getThisAmt()));
                        returnUpAmt = CalcUtils.calcAdd(returnUpAmt, zxSaOtherEquipSettleItem2.getUpAmt());
                    }
                }
                item.setThisAmt(returnThisAmt + "");
                item.setTotalAmt(returnUpAmt.add(returnThisAmt) + "");
                item.setModifyUserInfo(userKey, realName);
                zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(item);
            }

            // 更新统计项和结算单主表数据
            ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem1 = new ZxSaOtherEquipSettleItem();
            // 结算单主表id
            zxSaOtherEquipSettleItem1.setZxSaOtherEquipSettleId(dbZxSaOtherEquipSettleItem.getZxSaOtherEquipSettleId());
            // 合同管理id
            zxSaOtherEquipSettleItem1.setZxCtOtherManageId(dbZxSaOtherEquipSettleItem.getZxCtOtherManageId());
            this.updateZxSaOtherEquipSettleItemStatistics(zxSaOtherEquipSettleItem1);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaOtherEquipSettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipSettleItem(List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaOtherEquipSettleItemList != null && zxSaOtherEquipSettleItemList.size() > 0) {
            ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
            zxSaOtherEquipSettleItem.setModifyUserInfo(userKey, realName);
            flag = zxSaOtherEquipSettleItemMapper.batchDeleteUpdateZxSaOtherEquipSettleItem(zxSaOtherEquipSettleItemList, zxSaOtherEquipSettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaOtherEquipSettleItemList);
        }
    }

    @Override
    public ResponseEntity getZxSaOtherEquipSettleItemByContractId(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        if (zxSaOtherEquipSettleItem == null) {
            zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        }

        if (StrUtil.isEmpty(zxSaOtherEquipSettleItem.getZxCtOtherManageId())) {
            return repEntity.layerMessage("no", "合同ID不能为空！");
        }

        if (StrUtil.isEmpty(zxSaOtherEquipSettleItem.getPeriod())) {
            return repEntity.layerMessage("no", "期次不能为空！");
        }

        String zxCtOtherManageId = zxSaOtherEquipSettleItem.getZxCtOtherManageId();
        String period = zxSaOtherEquipSettleItem.getPeriod();

        // 合计含税结算金额(小写) 合计不含税结算金额(小写) 合计结算税额(小写) 合计含税结算金额(大写) 合计不含税结算金额(大写) 台计结算税额(大写)
        // 其中扣除保证金合计   1、质量保证金 2、安全生产保证金 3、农民工工资偿付保证金
        // 返还保证金合计          1、质量保证金 2、安全生产保证金 3、农民工工资偿付保证金
        // 应付合同款(小写) 应付合同款(大写)
        List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList = new ArrayList<>();

        // 查询本期次合计含税结算金额(小写) 合计不含税结算金额(小写) 合计结算税额(小写)
        ZxSaOtherEquipSettle zxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
        zxSaOtherEquipSettle.setZxCtOtherManageId(zxCtOtherManageId);
        List<ZxSaOtherEquipSettle> zxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(zxSaOtherEquipSettle);
        if (zxSaOtherEquipSettleList != null || zxSaOtherEquipSettleList.size() > 0) {
            // 根据合同管理id查询合同管理保证金子表的数据
            ZxCtOtherManageAmtRate dbZxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
            dbZxCtOtherManageAmtRate.setZxCtOtherManageId(zxSaOtherEquipSettle.getZxCtOtherManageId());
            List<ZxCtOtherManageAmtRate> dbZxCtOtherManageAmtRateList = zxCtOtherManageAmtRateMapper.selectByZxCtOtherManageAmtRateList(dbZxCtOtherManageAmtRate);

            // 根据合同管理id查询当前合同的统计项数据，有数据才统计开累和上期末
            ZxSaOtherEquipSettleItem dbZxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
            dbZxSaOtherEquipSettleItem.setZxCtOtherManageId(zxSaOtherEquipSettleItem.getZxCtOtherManageId());
            List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemTotalList = zxSaOtherEquipSettleItemMapper.selectByZxSaOtherEquipSettleItemList(dbZxSaOtherEquipSettleItem);
            if (zxSaOtherEquipSettleItemTotalList == null || zxSaOtherEquipSettleItemTotalList.size() == 0) {
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100100", "合计含税结算金额（小写）", "0", "0", "100100", false, new BigDecimal(0), new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100110", "合计不含税结算金额（小写）", "0", "0", "100110", false, new BigDecimal(0), new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100120", "合计结算税额（小写）", "0", "0", "100120", false, new BigDecimal(0), new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100200", "合计含税结算金额（大写）", "零元整", "零元整", "100200", false, new BigDecimal(0), new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100210", "合计不含税结算金额（大写）", "零元整", "零元整", "100210", false, new BigDecimal(0), new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100220", "合计结算税额（大写）", "零元整", "零元整", "100220", false, new BigDecimal(0), new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100300", "其中扣除保证金合计", "0", "0", "100300", false, new BigDecimal(0), new BigDecimal(0)));
                if (dbZxCtOtherManageAmtRateList != null || dbZxCtOtherManageAmtRateList.size() > 0) {
                    for (ZxCtOtherManageAmtRate zxCtOtherManageAmtRate : dbZxCtOtherManageAmtRateList) {
                        String statisticsName = zxCtOtherManageAmtRate.getStatisticsName();
                        String zxCtOtherManageAmtRateId = zxCtOtherManageAmtRate.getZxCtOtherManageAmtRateId();
                        zxSaOtherEquipSettleItemList.add(getItem(zxCtOtherManageAmtRateId,zxCtOtherManageId, period, zxCtOtherManageAmtRate.getStatisticsNo() + "", statisticsName, "0", "0", "100400", false, new BigDecimal(0), zxCtOtherManageAmtRate.getStatisticsRate()));
                        // 保证金表是否被结算引用字段加1
                        zxCtOtherManageAmtRate.setUseCount(CalcUtils.calcAdd(zxCtOtherManageAmtRate.getUseCount(), new BigDecimal(1)));
                        zxCtOtherManageAmtRateMapper.updateByPrimaryKey(zxCtOtherManageAmtRate);
                    }
                }
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100300", "返还保证金合计", "0", "0", "100500", true, new BigDecimal(0), new BigDecimal(0)));
                if (dbZxCtOtherManageAmtRateList != null || dbZxCtOtherManageAmtRateList.size() > 0) {
                    for (ZxCtOtherManageAmtRate zxCtOtherManageAmtRate : dbZxCtOtherManageAmtRateList) {
                        String statisticsName = zxCtOtherManageAmtRate.getStatisticsName();
                        String zxCtOtherManageAmtRateId = zxCtOtherManageAmtRate.getZxCtOtherManageAmtRateId();
                        zxSaOtherEquipSettleItemList.add(getItem(zxCtOtherManageAmtRateId,zxCtOtherManageId, period, zxCtOtherManageAmtRate.getStatisticsNo() + "", statisticsName, "0", "0", "100600", true, new BigDecimal(0), zxCtOtherManageAmtRate.getStatisticsRate()));
                        // 保证金表是否被结算引用字段加1
                        zxCtOtherManageAmtRate.setUseCount(CalcUtils.calcAdd(zxCtOtherManageAmtRate.getUseCount(), new BigDecimal(1)));
                        zxCtOtherManageAmtRateMapper.updateByPrimaryKey(zxCtOtherManageAmtRate);
                    }
                }
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100700", "应付合同款（小写）", "0", "0", "100700", false, new BigDecimal(0), new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100800", "应付合同款（大写）", "零元整", "零元整", "100800", false, new BigDecimal(0), new BigDecimal(0)));
            } else {

                ZxSaOtherEquipSettleItem selectItem = selectItem(zxCtOtherManageId, period, "合计含税结算金额（小写）", zxCtOtherManageId + "_100100");
                // 合计含税结算金额（小写） 开累
                String totalAmt1 = selectItem.getTotalAmt();
                // 合计含税结算金额（小写） 上期末
                BigDecimal upAmt1 = selectItem.getUpAmt();
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100100", "合计含税结算金额（小写）", "0", totalAmt1, "100100", false, upAmt1, new BigDecimal(0)));

                selectItem = selectItem(zxCtOtherManageId, period, "合计不含税结算金额（小写）", zxCtOtherManageId + "_100110");
                // 合计不含税结算金额（小写） 开累
                String totalAmt2 = selectItem.getTotalAmt();
                // 合计不含税结算金额（小写） 上期末
                BigDecimal upAmt2 = selectItem.getUpAmt();
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100110", "合计不含税结算金额（小写）", "0", totalAmt2, "100110", false, upAmt2, new BigDecimal(0)));

                selectItem = selectItem(zxCtOtherManageId, period, "合计结算税额（小写）", zxCtOtherManageId + "_100120");
                // 合计结算税额（小写） 开累
                String totalAmt3 = selectItem.getTotalAmt();
                // 合计结算税额（小写） 上期末
                BigDecimal upAmt3 = selectItem.getUpAmt();
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100120", "合计结算税额（小写）", "0", totalAmt3, "100120", false, upAmt3, new BigDecimal(0)));

                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100200", "合计含税结算金额（大写）", "零元整", DigitalConversionUtil.digitUppercase(new BigDecimal(totalAmt1)), "100200", false, upAmt1, new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100210", "合计不含税结算金额（大写）", "零元整", DigitalConversionUtil.digitUppercase(new BigDecimal(totalAmt2)), "100210", false, upAmt2, new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100220", "合计结算税额（大写）", "零元整", DigitalConversionUtil.digitUppercase(new BigDecimal(totalAmt3)), "100220", false, upAmt3, new BigDecimal(0)));

                selectItem = selectItem(zxCtOtherManageId, period, "其中扣除保证金合计", zxCtOtherManageId + "_100300");
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100300", "其中扣除保证金合计", "0", selectItem.getTotalAmt(), "100300", false, selectItem.getUpAmt(), new BigDecimal(0)));

                if (dbZxCtOtherManageAmtRateList != null || dbZxCtOtherManageAmtRateList.size() > 0) {
                    for (ZxCtOtherManageAmtRate zxCtOtherManageAmtRate : dbZxCtOtherManageAmtRateList) {
                        String statisticsID = zxCtOtherManageId;
                        String statisticsNo = zxCtOtherManageAmtRate.getStatisticsNo() + "";
                        if (StrUtil.isNotEmpty(statisticsNo)) {
                            statisticsID += "_" + statisticsNo;
                        }
                        String statisticsName = zxCtOtherManageAmtRate.getStatisticsName();
                        String zxCtOtherManageAmtRateId = zxCtOtherManageAmtRate.getZxCtOtherManageAmtRateId();
                        selectItem = selectItem(zxCtOtherManageId, period, statisticsName, statisticsID);
                        zxSaOtherEquipSettleItemList.add(getItem(zxCtOtherManageAmtRateId,zxCtOtherManageId, period, statisticsNo, statisticsName, "0", selectItem.getTotalAmt(), "100400", false, selectItem.getUpAmt(), zxCtOtherManageAmtRate.getStatisticsRate()));
                        zxCtOtherManageAmtRate.setUseCount(CalcUtils.calcAdd(zxCtOtherManageAmtRate.getUseCount(), new BigDecimal(1)));
                        zxCtOtherManageAmtRateMapper.updateByPrimaryKey(zxCtOtherManageAmtRate);
                    }
                }

                selectItem = selectItem(zxCtOtherManageId, period, "返还保证金合计", zxCtOtherManageId + "_100300_RETURN");
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100300", "返还保证金合计", "0", selectItem.getTotalAmt(), "100500", true, selectItem.getUpAmt(), new BigDecimal(0)));

                if (dbZxCtOtherManageAmtRateList != null || dbZxCtOtherManageAmtRateList.size() > 0) {
                    for (ZxCtOtherManageAmtRate zxCtOtherManageAmtRate : dbZxCtOtherManageAmtRateList) {
                        String statisticsID = zxCtOtherManageId;
                        String statisticsNo = zxCtOtherManageAmtRate.getStatisticsNo() + "";
                        if (StrUtil.isNotEmpty(statisticsNo)) {
                            statisticsID += "_" + statisticsNo + "_RETURN";
                        } else {
                            statisticsID += "_RETURN";
                        }
                        String statisticsName = zxCtOtherManageAmtRate.getStatisticsName();
                        String zxCtOtherManageAmtRateId = zxCtOtherManageAmtRate.getZxCtOtherManageAmtRateId();
                        selectItem = selectItem(zxCtOtherManageId, period, statisticsName, statisticsID);
                        zxSaOtherEquipSettleItemList.add(getItem(zxCtOtherManageAmtRateId,zxCtOtherManageId, period, statisticsNo, statisticsName, "0", selectItem.getTotalAmt(), "100600", true, selectItem.getUpAmt(), zxCtOtherManageAmtRate.getStatisticsRate()));
                        zxCtOtherManageAmtRate.setUseCount(CalcUtils.calcAdd(zxCtOtherManageAmtRate.getUseCount(), new BigDecimal(1)));
                        zxCtOtherManageAmtRateMapper.updateByPrimaryKey(zxCtOtherManageAmtRate);
                    }
                }

                selectItem = selectItem(zxCtOtherManageId, period, "应付合同款（小写）", zxCtOtherManageId + "_100700");
                // 应付合同款（小写） 开累
                String totalAmt4 = selectItem.getTotalAmt();
                // 应付合同款（小写） 上期末
                BigDecimal upAmt4 = selectItem.getUpAmt();
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100700", "应付合同款（小写）", "0", totalAmt4, "100700", false, selectItem.getUpAmt(), new BigDecimal(0)));
                zxSaOtherEquipSettleItemList.add(getItem("",zxCtOtherManageId, period, "100800", "应付合同款（大写）", "零元整", DigitalConversionUtil.digitUppercase(new BigDecimal(totalAmt4)), "100800", false, upAmt4, new BigDecimal(0)));
            }
        }

        return repEntity.ok(zxSaOtherEquipSettleItemList);
    }


    /**
     * 获取子表明细
     *
     * @param contractID     合同ID
     * @param period         期次
     * @param statisticsNo   统计项编号
     * @param statisticsName 统计项
     * @param thisAmt        本期(元)
     * @param totalAmt       开累(元)
     * @param statisticsType 统计项类型
     * @return
     */
    private ZxSaOtherEquipSettleItem getItem(String zxCtOtherManageAmtRateId,String contractID, String period, String statisticsNo, String statisticsName,
                                             String thisAmt, String totalAmt, String statisticsType, boolean returnFlag, BigDecimal upAmt, BigDecimal rate) {
        ZxSaOtherEquipSettleItem item = new ZxSaOtherEquipSettleItem();
        item.setZxCtOtherManageAmtRateId(zxCtOtherManageAmtRateId);
        item.setZxCtOtherManageId(contractID);
        item.setPeriod(period);
        item.setStatisticsNo(statisticsNo);
        item.setStatisticsName(statisticsName);
        item.setThisAmt(thisAmt);
        item.setUpAmt(upAmt);
        item.setTotalAmt(totalAmt);
        item.setStatisticsType(statisticsType);
        item.setRate(rate);
        if (returnFlag) {
            if (StrUtil.isEmpty(statisticsNo)) {
                item.setStatisticsId(contractID + "_RETURN");
            } else {
                item.setStatisticsId(contractID + "_" + statisticsNo + "_RETURN");
            }
        } else {
            if (StrUtil.isEmpty(statisticsNo)) {
                item.setStatisticsId(contractID);
            } else {
                item.setStatisticsId(contractID + "_" + statisticsNo);
            }
        }
        return item;
    }

    /**
     * 查找匹配项
     *
     * @param zxCtOtherManageId
     * @param period
     * @param statisticsName
     * @param statisticsID
     * @return
     */
    private ZxSaOtherEquipSettleItem selectItem(String zxCtOtherManageId, String period, String statisticsName, String statisticsID) {
        // 根据contractID、period、statisticsID查询开累金额及上期末金额
        ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        zxSaOtherEquipSettleItem.setZxCtOtherManageId(zxCtOtherManageId);
        zxSaOtherEquipSettleItem.setPeriod(period);
        zxSaOtherEquipSettleItem.setStatisticsId(statisticsID);
        zxSaOtherEquipSettleItem.setStatisticsName(statisticsName);
        ZxSaOtherEquipSettleItem selectItem = zxSaOtherEquipSettleItemMapper.selectTotalAmtUpAmt(zxSaOtherEquipSettleItem);

        if (selectItem == null) {
            selectItem = new ZxSaOtherEquipSettleItem();
        }

        if (StrUtil.isEmpty(selectItem.getTotalAmt())) {
            selectItem.setTotalAmt(StrUtil.contains(statisticsName, "大写") ? "零元整" : "0");
        }

        if (selectItem.getUpAmt() == null) {
            selectItem.setUpAmt(new BigDecimal(0));
        }

        return selectItem;
    }


    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    public void updateZxSaOtherEquipSettleItemStatistics(ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 更新统计项子表数据
        // 根据当前结算单id查询细目结算主表数据dbZxSaOtherEquipResSettle
        ZxSaOtherEquipResSettle dbResSettle = new ZxSaOtherEquipResSettle();
        dbResSettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettleItem.getZxSaOtherEquipSettleId());
        List<ZxSaOtherEquipResSettle> dbZxSaOtherEquipResSettle = zxSaOtherEquipResSettleMapper.selectByZxSaOtherEquipResSettleList(dbResSettle);
        // 根据当前结算单id 查询支付项结算主表数据dbZxSaOtherEquipPaySettle
        ZxSaOtherEquipPaySettle dbPaySettle = new ZxSaOtherEquipPaySettle();
        dbPaySettle.setZxSaOtherEquipSettleId(zxSaOtherEquipSettleItem.getZxSaOtherEquipSettleId());
        List<ZxSaOtherEquipPaySettle> dbZxSaOtherEquipPaySettle = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(dbPaySettle);
        // 合计含税结算金额（小写）本期（元) = 清单中本期含税金额+本期支付项含税金额
        BigDecimal totalAmt = CalcUtils.calcAdd(dbZxSaOtherEquipResSettle.get(0).getThisAmt(), dbZxSaOtherEquipPaySettle.get(0).getThisAmt());
        // 合计不含税结算金额（小写）本期（元) = 清单中本期不含税金额+本期支付项不含税金额
        BigDecimal totalAmtNoTax = CalcUtils.calcAdd(dbZxSaOtherEquipResSettle.get(0).getThisAmtNoTax(), dbZxSaOtherEquipPaySettle.get(0).getThisAmtNoTax());
        // 合计结算税额（小写）本期（元) = 清单中本期结算税额+本期支付项结算税额
        BigDecimal totalThisAmtTax = CalcUtils.calcAdd(dbZxSaOtherEquipResSettle.get(0).getThisAmtTax(), dbZxSaOtherEquipPaySettle.get(0).getThisAmtTax());
        // 累计清单结算含税金额(元)，累计清单结算不含税金额(元)，累计清单结算税额(元) 获取值
        ZxSaOtherEquipResSettle dbAllZxSaOtherEquipResSettle = new ZxSaOtherEquipResSettle();
        dbAllZxSaOtherEquipResSettle.setZxCtOtherManageId(dbZxSaOtherEquipResSettle.get(0).getZxCtOtherManageId());
        // 根据合同id查询当前合同的往期所有细目结算主表数据
        List<ZxSaOtherEquipResSettle> dbZxSaOtherEquipResSettleList = zxSaOtherEquipResSettleMapper.selectByZxSaOtherEquipResSettleList(dbAllZxSaOtherEquipResSettle);
        BigDecimal totalResThisAmt = null;
        BigDecimal totalResThisAmtNoTax = null;
        BigDecimal totalResThisAmtTax = null;
        if (dbZxSaOtherEquipResSettleList != null && dbZxSaOtherEquipResSettleList.size() > 0) {
            for (ZxSaOtherEquipResSettle zxSaOtherEquipResSettle1 : dbZxSaOtherEquipResSettleList) {
                // 往期所有细目结算主表的本期清单结算含税金额(元)值汇总
                totalResThisAmt = CalcUtils.calcAdd(totalResThisAmt, zxSaOtherEquipResSettle1.getThisAmt());
                // 往期所有细目结算主表的本期清单结算不含税金额(元)值汇总
                totalResThisAmtNoTax = CalcUtils.calcAdd(totalResThisAmtNoTax, zxSaOtherEquipResSettle1.getThisAmtNoTax());
                // 往期所有细目结算主表的本期清单结算税额(元)(元)值汇总
                totalResThisAmtTax = CalcUtils.calcAdd(totalResThisAmtTax, zxSaOtherEquipResSettle1.getThisAmtTax());
            }
        }
        //  累计支付项结算含税金额(元)，累计支付项结算不含税金额(元)，累计支付项结算税额(元) 获取值
        ZxSaOtherEquipPaySettle dbAllZxSaOtherEquipPaySettle = new ZxSaOtherEquipPaySettle();
        dbAllZxSaOtherEquipPaySettle.setZxCtOtherManageId(dbZxSaOtherEquipPaySettle.get(0).getZxCtOtherManageId());
        // 根据合同id查询当前合同的往期所有支付项结算主表数据
        List<ZxSaOtherEquipPaySettle> dbZxSaOtherEquipPaySettleList = zxSaOtherEquipPaySettleMapper.selectByZxSaOtherEquipPaySettleList(dbAllZxSaOtherEquipPaySettle);
        BigDecimal totalPayThisAmt = null;
        BigDecimal totalPayThisAmtNoTax = null;
        BigDecimal totalPayThisAmtTax = null;
        if (dbZxSaOtherEquipPaySettleList != null && dbZxSaOtherEquipPaySettleList.size() > 0) {
            for (ZxSaOtherEquipPaySettle zxSaOtherEquipPaySettle1 : dbZxSaOtherEquipPaySettleList) {
                // 往期所有支付项结算主表的本期支付项结算含税金额(元)值汇总
                totalPayThisAmt = CalcUtils.calcAdd(totalPayThisAmt, zxSaOtherEquipPaySettle1.getThisAmt());
                // 往期所有支付项结算主表的本期支付项结算不含税金额(元)值汇总
                totalPayThisAmtNoTax = CalcUtils.calcAdd(totalPayThisAmtNoTax, zxSaOtherEquipPaySettle1.getThisAmtNoTax());
                // 往期所有支付项结算主表的本期支付项结算税额(元)(元)值汇总
                totalPayThisAmtTax = CalcUtils.calcAdd(totalPayThisAmtTax, zxSaOtherEquipPaySettle1.getThisAmtTax());
            }
        }

        // 合计含税结算金额（小写）开累（元) = 累计清单结算含税金额(元)+累计支付项结算含税金额(元)
        BigDecimal totalTotalAmt = CalcUtils.calcAdd(totalResThisAmt, totalPayThisAmt);
        // 合计不含税结算金额（小写）开累（元) = 累计清单结算不含税金额(元)+累计支付项结算不含税金额(元)
        BigDecimal totalTotalAmtNoTax = CalcUtils.calcAdd(totalResThisAmtNoTax, totalPayThisAmtNoTax);
        // 合计结算税额（小写）开累（元) = 累计清单结算结算税额元)+累计支付项结算不含税金额(元)
        BigDecimal totalTotalAmtTax = CalcUtils.calcAdd(totalResThisAmtTax, totalPayThisAmtTax);
        // 根据结算单主表id查询统计项数据
        ZxSaOtherEquipSettleItem dbZxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
        dbZxSaOtherEquipSettleItem.setZxSaOtherEquipSettleId(zxSaOtherEquipSettleItem.getZxSaOtherEquipSettleId());
        List<ZxSaOtherEquipSettleItem> ZxSaOtherEquipSettleItemList = zxSaOtherEquipSettleItemMapper.selectByZxSaOtherEquipSettleItemList(dbZxSaOtherEquipSettleItem);
        // 其中扣除保证金合计本期
        BigDecimal sumThisAmt = new BigDecimal(0);
        // 其中扣除保证金合计开累
        BigDecimal sumTotalThisAmt = new BigDecimal(0);
        // 返还保证金合计本期
        BigDecimal sumThisAmtReturn = new BigDecimal(0);
        // 返还保证金合计开累
        BigDecimal sumTotalThisAmtReturn = new BigDecimal(0);
        // 应付合同款小写本期
        BigDecimal thisPayAmt = new BigDecimal(0);
        // 应付合同款小写开累
        BigDecimal thisTotalPayAmt = new BigDecimal(0);
        // 其中扣除保证金合计
        ZxSaOtherEquipSettleItem item = null;
        // 返还保证金合计
        ZxSaOtherEquipSettleItem item1 = null;

        // 应付合同款小写开累实体
        ZxSaOtherEquipSettleItem item2 = null;
        // 应付合同款大写开累实体
        ZxSaOtherEquipSettleItem item3 = null;
        if (ZxSaOtherEquipSettleItemList != null && ZxSaOtherEquipSettleItemList.size() > 0) {
            for (ZxSaOtherEquipSettleItem zxSaOtherEquipSettleItem1 : ZxSaOtherEquipSettleItemList) {
                // 合计含税结算金额（小写）
                if (StrUtil.equals("100100", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    // 合计含税结算金额（小写）本期（元)
                    zxSaOtherEquipSettleItem1.setThisAmt(totalAmt + "");
                    // 合计含税结算金额（小写）开累（元)
                    zxSaOtherEquipSettleItem1.setTotalAmt(totalTotalAmt + "");
                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                }
                // 合计不含税结算金额（小写）
                if (StrUtil.equals("100110", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    // 合计不含税结算金额（小写）本期（元)
                    zxSaOtherEquipSettleItem1.setThisAmt(totalAmtNoTax + "");
                    // 合计不含税结算金额（小写）开累（元)
                    zxSaOtherEquipSettleItem1.setTotalAmt(totalTotalAmtNoTax + "");
                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                }
                // 合计结算税额（小写）
                if (StrUtil.equals("100120", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    // 合计结算税额（小写）本期（元)
                    zxSaOtherEquipSettleItem1.setThisAmt(totalThisAmtTax + "");
                    // 合计结算税额（小写）开累（元)
                    zxSaOtherEquipSettleItem1.setTotalAmt(totalTotalAmtTax + "");
                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                }
                // 合计含税结算金额（大写）
                if (StrUtil.equals("100200", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    // 合计含税结算金额（大写）本期（元)
                    zxSaOtherEquipSettleItem1.setThisAmt(DigitalConversionUtil.digitUppercase(totalAmt));
                    // 合计含税结算金额（大写）开累（元)
                    zxSaOtherEquipSettleItem1.setTotalAmt(DigitalConversionUtil.digitUppercase(totalTotalAmt));
                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                }
                // 合计不含税结算金额（大写）
                if (StrUtil.equals("100210", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    // 合计不含税结算金额（大写）本期（元)
                    zxSaOtherEquipSettleItem1.setThisAmt(DigitalConversionUtil.digitUppercase(totalAmtNoTax));
                    // 合计不含税结算金额（大写）开累（元)
                    zxSaOtherEquipSettleItem1.setTotalAmt(DigitalConversionUtil.digitUppercase(totalTotalAmtNoTax));
                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                }
                //合计结算税额（大写）
                if (StrUtil.equals("100220", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    // 合计结算税额（大写）本期（元)
                    zxSaOtherEquipSettleItem1.setThisAmt(DigitalConversionUtil.digitUppercase(totalThisAmtTax));
                    // 合计结算税额（大写）开累（元)
                    zxSaOtherEquipSettleItem1.setTotalAmt(DigitalConversionUtil.digitUppercase(totalTotalAmtTax));
                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                }
                //扣除保证金单项
                if (StrUtil.equals("100400", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    // 本期
                    // 清单中本期含税金额 *【其它合同管理】模块扣除比例
                    BigDecimal thisAmt = CalcUtils.calcMultiply(dbZxSaOtherEquipResSettle.get(0).getThisAmt(), CalcUtils.calcDivide(zxSaOtherEquipSettleItem1.getRate(), new BigDecimal(100)));
                    zxSaOtherEquipSettleItem1.setThisAmt(thisAmt + "");
                    // 开累
                    // 往期+本期汇总 往期累计所有 清单中本期含税金额*【其它合同管理】模块扣除比例+清单中本期含税金额**【其它合同管理】模块扣除比例
                    BigDecimal totalThisAmt = CalcUtils.calcAdd(zxSaOtherEquipSettleItem1.getUpAmt(), thisAmt);
                    zxSaOtherEquipSettleItem1.setTotalAmt(totalThisAmt + "");
                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                    // 扣除保证金合计本期
                    sumThisAmt = CalcUtils.calcAdd(sumThisAmt, thisAmt);
                    // 扣除保证金合计开累
                    sumTotalThisAmt = CalcUtils.calcAdd(sumTotalThisAmt, totalThisAmt);
                }
                //返还保证金单项
                if (StrUtil.equals("100600", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    BigDecimal thisAmt = new BigDecimal(zxSaOtherEquipSettleItem1.getThisAmt());
                    sumThisAmtReturn = CalcUtils.calcAdd(sumThisAmtReturn, thisAmt);
                    sumTotalThisAmtReturn = CalcUtils.calcAdd(sumTotalThisAmtReturn, zxSaOtherEquipSettleItem1.getUpAmt().add(thisAmt));
                }
                // 其中扣除保证金合计
                if (StrUtil.equals("100300", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    item = zxSaOtherEquipSettleItem1;
                }
                // 返还保证金合计
                if (StrUtil.equals("100500", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    item1 = zxSaOtherEquipSettleItem1;
                }
                // 应付合同款（小写）
                if (StrUtil.equals("100700", zxSaOtherEquipSettleItem1.getStatisticsType())) {
                    // 应付合同款（小写）本期   合计含税结算金额-其中扣除保证金合计 本期+返还保证金合计 本期
//                    thisPayAmt = CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt, sumThisAmt), new BigDecimal(item1.getThisAmt()));
//                    zxSaOtherEquipSettleItem1.setThisAmt(thisPayAmt + "");
//                    ZxSaOtherEquipSettleItem ZxSaOtherEquipSettleItem = new ZxSaOtherEquipSettleItem();
//                    ZxSaOtherEquipSettleItem.setZxCtOtherManageId(zxSaOtherEquipSettleItem1.getZxCtOtherManageId());
//                    ZxSaOtherEquipSettleItem.setPeriod(zxSaOtherEquipSettleItem1.getPeriod());
//                    ZxSaOtherEquipSettleItem.setStatisticsId(zxSaOtherEquipSettleItem1.getStatisticsId());
//                    ZxSaOtherEquipSettleItem.setStatisticsName(zxSaOtherEquipSettleItem1.getStatisticsName());
//                    item3 = zxSaOtherEquipSettleItemMapper.selectTotalAmtUpAmt(ZxSaOtherEquipSettleItem);
//                    // 应付合同款（小写）开累
//                    thisTotalPayAmt = new BigDecimal(item3.getTotalAmt());
//                    zxSaOtherEquipSettleItem1.setTotalAmt(thisTotalPayAmt + "");
//                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
//                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                    item2 = zxSaOtherEquipSettleItem1;
                }

                // 应付合同款（大写）
                if (StrUtil.equals("100800", zxSaOtherEquipSettleItem1.getStatisticsType())) {
//                    // 应付合同款（大写）本期   合计含税结算金额-其中扣除保证金合计 本期+返还保证金合计,返还保证金合计为0
//                    BigDecimal thisAmt = CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt, sumThisAmt), new BigDecimal(item1.getThisAmt()));
//                    String amt = DigitalConversionUtil.digitUppercase(thisAmt);
//                    zxSaOtherEquipSettleItem1.setThisAmt(amt);
//                    // 应付合同款（大写）开累
//                    zxSaOtherEquipSettleItem1.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(item2.getTotalAmt())));
//                    zxSaOtherEquipSettleItem1.setModifyUserInfo(userKey, realName);
//                    zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(zxSaOtherEquipSettleItem1);
                    item3 = zxSaOtherEquipSettleItem1;
                }
            }

            // 其中扣除保证金合计 本期
            item.setThisAmt(sumThisAmt + "");
            // 其中扣除保证金合计 开累
            item.setTotalAmt(sumTotalThisAmt + "");
            item.setModifyUserInfo(userKey, realName);
            // 更新其中扣除保证金合计
            zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(item);
            // 返还保证金合计 本期
            item1.setThisAmt(sumThisAmtReturn + "");
            // 返还保证金合计 开累
            item1.setTotalAmt(sumTotalThisAmtReturn + "");
            // 更新返还保证金合计
            zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(item1);
            // 应付合同款（小写）本期   合计含税结算金额-其中扣除保证金合计 本期+返还保证金合计 本期
            thisPayAmt = CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt, sumThisAmt), sumThisAmtReturn);
            thisTotalPayAmt = CalcUtils.calcAdd(CalcUtils.calcSubtract(totalTotalAmt, sumTotalThisAmt), sumTotalThisAmtReturn);
            item2.setThisAmt(thisPayAmt + "");
            item2.setTotalAmt(thisTotalPayAmt + "");
            zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(item2);
            item3.setThisAmt(DigitalConversionUtil.digitUppercase(thisPayAmt));
            item3.setTotalAmt(DigitalConversionUtil.digitUppercase(thisTotalPayAmt));
            zxSaOtherEquipSettleItemMapper.updateByPrimaryKey(item3);
        }

        // 更新结算单主表金额数据
        ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = zxSaOtherEquipSettleMapper.selectByPrimaryKey(zxSaOtherEquipSettleItem.getZxSaOtherEquipSettleId());
        // 本期结算金额 是统计项的合计结算含税金额本期
        dbZxSaOtherEquipSettle.setThisAmt(totalAmt);
        // 开累结算金额 是统计项的合计结算含税金额开累
        dbZxSaOtherEquipSettle.setTotalAmt(totalTotalAmt);
        // 本期结算不含税金额(元) 是统计项的合计结算不含税金额
        dbZxSaOtherEquipSettle.setThisAmtNoTax(totalAmtNoTax);
        // 本期结算税额(元)  是统计项的合计结算税额
        dbZxSaOtherEquipSettle.setThisAmtTax(totalThisAmtTax);
        // 本期应付金额
        dbZxSaOtherEquipSettle.setThisPayAmt(thisPayAmt);
        // 开累应付金额
        dbZxSaOtherEquipSettle.setTotalPayAmt(thisTotalPayAmt);
        dbZxSaOtherEquipSettle.setModifyUserInfo(userKey, realName);
        zxSaOtherEquipSettleMapper.updateByPrimaryKey(dbZxSaOtherEquipSettle);
    }
}
