package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuWorksToStockPrice extends BasePojo {
    // 主键
    private String zxBuWorksToStockPriceId;

    // 项目清单ID
    private String projWorkID;

    // VOID
    private String stockVOID;

    // 分类id
    private String businessType;

    // 耗用量
    private BigDecimal gjQty1;

    // 不含税金额
    private BigDecimal gjAmt1;

    // 耗用量
    private BigDecimal gjQty2;

    // 不含税金额
    private BigDecimal gjAmt2;

    // 损耗系数
    private BigDecimal gjLossCoefficient1;

    // 损耗系数
    private BigDecimal gjLossCoefficient2;

    // 折算系数
    private BigDecimal gjConCoefficient1;

    // 折算系数
    private BigDecimal gjConCoefficient2;

    // 期次
    private String period;

    // 材料编号
    private String resCode;

    // 材料名称
    private String resName;

    // 材料单位
    private String unit;

    // 规格型号
    private String spec;

    // 单价
    private BigDecimal scenePrice1;

    // 单价
    private BigDecimal scenePrice2;

    // 机构id
    private String orgID;

    // 全局均价
    private BigDecimal comPrice;

    // 本公司均价
    private BigDecimal ownerComPrice;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private String stockPriceID;

    public String getStockPriceID() {
        return stockPriceID;
    }

    public void setStockPriceID(String stockPriceID) {
        this.stockPriceID = stockPriceID;
    }

    public String getZxBuWorksToStockPriceId() {
        return zxBuWorksToStockPriceId == null ? "" : zxBuWorksToStockPriceId.trim();
    }

    public void setZxBuWorksToStockPriceId(String zxBuWorksToStockPriceId) {
        this.zxBuWorksToStockPriceId = zxBuWorksToStockPriceId == null ? null : zxBuWorksToStockPriceId.trim();
    }

    public String getProjWorkID() {
        return projWorkID == null ? "" : projWorkID.trim();
    }

    public void setProjWorkID(String projWorkID) {
        this.projWorkID = projWorkID == null ? null : projWorkID.trim();
    }

    public String getStockVOID() {
        return stockVOID == null ? "" : stockVOID.trim();
    }

    public void setStockVOID(String stockVOID) {
        this.stockVOID = stockVOID == null ? null : stockVOID.trim();
    }

    public String getBusinessType() {
        return businessType == null ? "" : businessType.trim();
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public BigDecimal getGjQty1() {
        return gjQty1;
    }

    public void setGjQty1(BigDecimal gjQty1) {
        this.gjQty1 = gjQty1;
    }

    public BigDecimal getGjAmt1() {
        return gjAmt1;
    }

    public void setGjAmt1(BigDecimal gjAmt1) {
        this.gjAmt1 = gjAmt1;
    }

    public BigDecimal getGjQty2() {
        return gjQty2;
    }

    public void setGjQty2(BigDecimal gjQty2) {
        this.gjQty2 = gjQty2;
    }

    public BigDecimal getGjAmt2() {
        return gjAmt2;
    }

    public void setGjAmt2(BigDecimal gjAmt2) {
        this.gjAmt2 = gjAmt2;
    }

    public BigDecimal getGjLossCoefficient1() {
        return gjLossCoefficient1;
    }

    public void setGjLossCoefficient1(BigDecimal gjLossCoefficient1) {
        this.gjLossCoefficient1 = gjLossCoefficient1;
    }

    public BigDecimal getGjLossCoefficient2() {
        return gjLossCoefficient2;
    }

    public void setGjLossCoefficient2(BigDecimal gjLossCoefficient2) {
        this.gjLossCoefficient2 = gjLossCoefficient2;
    }

    public BigDecimal getGjConCoefficient1() {
        return gjConCoefficient1;
    }

    public void setGjConCoefficient1(BigDecimal gjConCoefficient1) {
        this.gjConCoefficient1 = gjConCoefficient1;
    }

    public BigDecimal getGjConCoefficient2() {
        return gjConCoefficient2;
    }

    public void setGjConCoefficient2(BigDecimal gjConCoefficient2) {
        this.gjConCoefficient2 = gjConCoefficient2;
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public BigDecimal getScenePrice1() {
        return scenePrice1;
    }

    public void setScenePrice1(BigDecimal scenePrice1) {
        this.scenePrice1 = scenePrice1;
    }

    public BigDecimal getScenePrice2() {
        return scenePrice2;
    }

    public void setScenePrice2(BigDecimal scenePrice2) {
        this.scenePrice2 = scenePrice2;
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public BigDecimal getComPrice() {
        return comPrice;
    }

    public void setComPrice(BigDecimal comPrice) {
        this.comPrice = comPrice;
    }

    public BigDecimal getOwnerComPrice() {
        return ownerComPrice;
    }

    public void setOwnerComPrice(BigDecimal ownerComPrice) {
        this.ownerComPrice = ownerComPrice;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
