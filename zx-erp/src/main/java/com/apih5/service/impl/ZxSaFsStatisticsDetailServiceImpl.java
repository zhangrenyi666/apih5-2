package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSaFsSettlementMapper;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import com.apih5.utils.DigitalConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaFsStatisticsDetailMapper;
import com.apih5.mybatis.pojo.ZxSaFsStatisticsDetail;
import com.apih5.service.ZxSaFsStatisticsDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaFsStatisticsDetailService")
public class ZxSaFsStatisticsDetailServiceImpl implements ZxSaFsStatisticsDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaFsStatisticsDetailMapper zxSaFsStatisticsDetailMapper;

    @Autowired(required = true)
    private ZxSaFsSettlementMapper zxSaFsSettlementMapper;

    @Override
    public ResponseEntity getZxSaFsStatisticsDetailListByCondition(ZxSaFsStatisticsDetail zxSaFsStatisticsDetail) {
        if (zxSaFsStatisticsDetail == null) {
            zxSaFsStatisticsDetail = new ZxSaFsStatisticsDetail();
        }
        // 分页查询
        PageHelper.startPage(zxSaFsStatisticsDetail.getPage(), zxSaFsStatisticsDetail.getLimit());
        // 获取数据
        List<ZxSaFsStatisticsDetail> zxSaFsStatisticsDetailList = zxSaFsStatisticsDetailMapper.selectByZxSaFsStatisticsDetailList(zxSaFsStatisticsDetail);
        DecimalFormat format = new DecimalFormat("0.00");

        for (ZxSaFsStatisticsDetail zx : zxSaFsStatisticsDetailList
        ) {
            if (zx.getStatisticsType().equals("100100") || zx.getStatisticsType().equals("100110") || zx.getStatisticsType().equals("100120") || zx.getStatisticsType().equals("100500") || zx.getStatisticsType().equals("100700") || zx.getStatisticsType().equals("")) {
                String thisAmt = zx.getThisAmt() == null ? "0" : zx.getThisAmt();
                String totalAmt = zx.getTotalAmt() == null ? "0" : zx.getTotalAmt();
                zx.setThisAmt(format.format(new BigDecimal(thisAmt)));
                zx.setTotalAmt(format.format(new BigDecimal(totalAmt)));
            }
        }
        // 得到分页信息
        PageInfo<ZxSaFsStatisticsDetail> p = new PageInfo<>(zxSaFsStatisticsDetailList);

        return repEntity.okList(zxSaFsStatisticsDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaFsStatisticsDetailDetail(ZxSaFsStatisticsDetail zxSaFsStatisticsDetail) {
        if (zxSaFsStatisticsDetail == null) {
            zxSaFsStatisticsDetail = new ZxSaFsStatisticsDetail();
        }
        // 获取数据
        ZxSaFsStatisticsDetail dbZxSaFsStatisticsDetail = zxSaFsStatisticsDetailMapper.selectByPrimaryKey(zxSaFsStatisticsDetail.getZxSaFsStatisticsDetailId());
        // 数据存在
        if (dbZxSaFsStatisticsDetail != null) {
            return repEntity.ok(dbZxSaFsStatisticsDetail);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaFsStatisticsDetail(ZxSaFsStatisticsDetail zxSaFsStatisticsDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaFsStatisticsDetail.setZxSaFsStatisticsDetailId(UuidUtil.generate());
        zxSaFsStatisticsDetail.setCreateUserInfo(userKey, realName);
        int flag = zxSaFsStatisticsDetailMapper.insert(zxSaFsStatisticsDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaFsStatisticsDetail);
        }
    }

    @Override
    public ResponseEntity updateZxSaFsStatisticsDetail(ZxSaFsStatisticsDetail zxSaFsStatisticsDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaFsStatisticsDetail dbZxSaFsStatisticsDetail = zxSaFsStatisticsDetailMapper.selectByPrimaryKey(zxSaFsStatisticsDetail.getZxSaFsStatisticsDetailId());
        if (dbZxSaFsStatisticsDetail != null && StrUtil.isNotEmpty(dbZxSaFsStatisticsDetail.getZxSaFsStatisticsDetailId())) {
            // 附属类结算ID
            dbZxSaFsStatisticsDetail.setZxSaFsSettlementId(zxSaFsStatisticsDetail.getZxSaFsSettlementId());
            // 项目ID
            dbZxSaFsStatisticsDetail.setOrgID(zxSaFsStatisticsDetail.getOrgID());
            // 合同ID
            dbZxSaFsStatisticsDetail.setZxCtFsContractId(zxSaFsStatisticsDetail.getZxCtFsContractId());
            // 结算期次
            dbZxSaFsStatisticsDetail.setPeriod(zxSaFsStatisticsDetail.getPeriod());
            // 结算单编号
            dbZxSaFsStatisticsDetail.setBillNo(zxSaFsStatisticsDetail.getBillNo());
            // 统计项ID
            dbZxSaFsStatisticsDetail.setStatisticsID(zxSaFsStatisticsDetail.getStatisticsID());
            // 统计项编号
            dbZxSaFsStatisticsDetail.setStatisticsNo(zxSaFsStatisticsDetail.getStatisticsNo());
            // 统计项名称
            dbZxSaFsStatisticsDetail.setStatisticsName(zxSaFsStatisticsDetail.getStatisticsName());
            // 统计项类型
            dbZxSaFsStatisticsDetail.setStatisticsType(zxSaFsStatisticsDetail.getStatisticsType());

            // 上期末累计支付项结算金额(元)
            dbZxSaFsStatisticsDetail.setUpAmt(zxSaFsStatisticsDetail.getUpAmt());
            // 比例
            dbZxSaFsStatisticsDetail.setRate(zxSaFsStatisticsDetail.getRate());
            // 最后编辑时间
            dbZxSaFsStatisticsDetail.setEditTime(zxSaFsStatisticsDetail.getEditTime());
            // 所属公司ID
            dbZxSaFsStatisticsDetail.setComID(zxSaFsStatisticsDetail.getComID());
            // 所属公司名称
            dbZxSaFsStatisticsDetail.setComName(zxSaFsStatisticsDetail.getComName());
            // 所属公司排序
            dbZxSaFsStatisticsDetail.setComOrders(zxSaFsStatisticsDetail.getComOrders());
            // 备注
            dbZxSaFsStatisticsDetail.setRemarks(zxSaFsStatisticsDetail.getRemarks());
            // 排序
            dbZxSaFsStatisticsDetail.setSort(zxSaFsStatisticsDetail.getSort());
            // 共通
            dbZxSaFsStatisticsDetail.setModifyUserInfo(userKey, realName);

            if (2 == dbZxSaFsStatisticsDetail.getSort()) {
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetail1 = new ZxSaFsStatisticsDetail();
                zxSaFsStatisticsDetail1.setZxSaFsSettlementId(dbZxSaFsStatisticsDetail.getZxSaFsSettlementId());
                zxSaFsStatisticsDetail1.setPeriod(dbZxSaFsStatisticsDetail.getPeriod());
                zxSaFsStatisticsDetail1.setStatisticsType("100500");
                zxSaFsStatisticsDetail1.setZxCtFsContractId(dbZxSaFsStatisticsDetail.getZxCtFsContractId());
                ZxSaFsStatisticsDetail zxSaFsStatisticsDetail2 = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(zxSaFsStatisticsDetail1);
                BigDecimal thisAmt = new BigDecimal("0");
                BigDecimal totalAmt = new BigDecimal("0");
                BigDecimal oldThisAmt = new BigDecimal("0");
                BigDecimal oldTotalAmt = new BigDecimal("0");
                if (zxSaFsStatisticsDetail2.getThisAmt() != null) {
                    thisAmt = new BigDecimal(zxSaFsStatisticsDetail2.getThisAmt());
                }
                if (zxSaFsStatisticsDetail2.getTotalAmt() != null) {
                    totalAmt = new BigDecimal(zxSaFsStatisticsDetail2.getTotalAmt());
                }
                if (dbZxSaFsStatisticsDetail.getThisAmt() != null) {
                    oldThisAmt = new BigDecimal(dbZxSaFsStatisticsDetail.getThisAmt());
                }
                if (dbZxSaFsStatisticsDetail.getTotalAmt() != null) {
                    oldTotalAmt = new BigDecimal(dbZxSaFsStatisticsDetail.getTotalAmt());
                }

                zxSaFsStatisticsDetail2.setThisAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(zxSaFsStatisticsDetail.getThisAmt()), CalcUtils.calcSubtract(thisAmt, oldThisAmt))));
                zxSaFsStatisticsDetail2.setTotalAmt(String.valueOf(CalcUtils.calcAdd(totalAmt, CalcUtils.calcSubtract(new BigDecimal(zxSaFsStatisticsDetail.getThisAmt()), oldThisAmt))));
                flag = zxSaFsStatisticsDetailMapper.updateByPrimaryKey(zxSaFsStatisticsDetail2);
                if (flag == 0) {
                    return repEntity.errorSave();
                }
                zxSaFsStatisticsDetail1.setStatisticsType("100700");
                ZxSaFsStatisticsDetail htX = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(zxSaFsStatisticsDetail1);
                htX.setThisAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(htX.getThisAmt()), CalcUtils.calcSubtract(new BigDecimal(zxSaFsStatisticsDetail2.getThisAmt()), thisAmt))));
                htX.setTotalAmt(String.valueOf(CalcUtils.calcAdd(new BigDecimal(htX.getTotalAmt()), CalcUtils.calcSubtract(new BigDecimal(zxSaFsStatisticsDetail2.getThisAmt()), thisAmt))));
                zxSaFsStatisticsDetail1.setStatisticsType("100800");
                ZxSaFsStatisticsDetail htD = zxSaFsStatisticsDetailMapper.selectBaoZhengJin(zxSaFsStatisticsDetail1);
                htD.setThisAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(htX.getThisAmt())));
                htD.setTotalAmt(DigitalConversionUtil.digitUppercase(new BigDecimal(htX.getTotalAmt())));
                flag = zxSaFsStatisticsDetailMapper.updateByPrimaryKey(htX);
                flag = zxSaFsStatisticsDetailMapper.updateByPrimaryKey(htD);

                ZxSaFsSettlement zxSaFsSettlement = zxSaFsSettlementMapper.selectByPrimaryKey(zxSaFsStatisticsDetail.getZxSaFsSettlementId());

                if (zxSaFsSettlement != null) {
                    zxSaFsSettlement.setThisPayAmt(new BigDecimal(htX.getThisAmt()));
                    zxSaFsSettlement.setTotalPayAmt(new BigDecimal(htX.getTotalAmt()));
                    flag = zxSaFsSettlementMapper.updateByPrimaryKey(zxSaFsSettlement);
                } else {
                    return repEntity.layerMessage("no", "同步更新主表应付失败");
                }

            }

            BigDecimal totalAmt = new BigDecimal("0");
            BigDecimal thisAmt = new BigDecimal("0");
            BigDecimal oldThisAmt = new BigDecimal("0");
            if (zxSaFsStatisticsDetail.getTotalAmt() != null) {
                totalAmt = new BigDecimal(zxSaFsStatisticsDetail.getTotalAmt());
            }
            if (zxSaFsStatisticsDetail.getThisAmt() != null) {
                thisAmt = new BigDecimal(zxSaFsStatisticsDetail.getThisAmt());
            }
            if (dbZxSaFsStatisticsDetail.getThisAmt() != null) {
                oldThisAmt = new BigDecimal(dbZxSaFsStatisticsDetail.getThisAmt());
            }
            // 本期支付项结算金额(元)
            dbZxSaFsStatisticsDetail.setThisAmt(String.valueOf(thisAmt));
            // 累计支付项结算金额(元)
            dbZxSaFsStatisticsDetail.setTotalAmt(String.valueOf(CalcUtils.calcSubtract(CalcUtils.calcAdd(totalAmt, thisAmt), oldThisAmt)));
            flag = zxSaFsStatisticsDetailMapper.updateByPrimaryKey(dbZxSaFsStatisticsDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSaFsStatisticsDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaFsStatisticsDetail(List<ZxSaFsStatisticsDetail> zxSaFsStatisticsDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaFsStatisticsDetailList != null && zxSaFsStatisticsDetailList.size() > 0) {
            ZxSaFsStatisticsDetail zxSaFsStatisticsDetail = new ZxSaFsStatisticsDetail();
            zxSaFsStatisticsDetail.setModifyUserInfo(userKey, realName);
            flag = zxSaFsStatisticsDetailMapper.batchDeleteUpdateZxSaFsStatisticsDetail(zxSaFsStatisticsDetailList, zxSaFsStatisticsDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSaFsStatisticsDetailList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
