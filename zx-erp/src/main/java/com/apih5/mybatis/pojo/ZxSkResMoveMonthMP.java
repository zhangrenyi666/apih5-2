package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModelProperty;

public class ZxSkResMoveMonthMP extends BasePojo {
    // 主键
    private String zxSkResMoveMonthMPId;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 规格
    private String spec;

    // 计量单位
    private String unit;

    // 上月结存数量
    private BigDecimal stockQty;

    // 上月结存金额
    private BigDecimal stockAmt;

    // 上月结存平均单价
    private BigDecimal stockPrice;

    // 本月收入合计（数量）
    private BigDecimal inQty;

    // 本月收入合计（平均价格）
    private BigDecimal inPrice;

    // 本月收入金额
    private BigDecimal inAmt;

    // 甲供
    private BigDecimal orsQty;

    // 其他
    private BigDecimal otrQty;

    // 自行采购
    private BigDecimal serQty;

    // 预收
    private BigDecimal obuQty;

    // 甲控
    private BigDecimal ocsQty;

    // 工程耗用数量
    private BigDecimal oswQty;

    // 工程耗用金额
    private BigDecimal oswAmt;

    // 工程耗用平均单价
    private BigDecimal oswPrice;

    // 调拨金额
    private BigDecimal outAmt;

    // 调拨平均单价
    private BigDecimal otkPrice;

    // 调拨
    private BigDecimal otkQty;

    // 盈亏数量
    private BigDecimal vinQty;

    // 盈亏金额
    private BigDecimal vinAmt;

    // 本月结存数量
    private BigDecimal thisQtys;

    // 本月结存金额
    private BigDecimal thisAmts;

    // 本月结存平均价格
    private BigDecimal thisProce;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //加工改制
    private BigDecimal trsQty;

    //调拨数量
    private BigDecimal otkQtys;
    //项目ID
    private String orgID;
    //物资分类ID
    private String resTypeID;

    //是否完工
    private String isFinish;
    //期次
    private String period;

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getResTypeID() {
        return resTypeID;
    }

    public void setResTypeID(String resTypeID) {
        this.resTypeID = resTypeID;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getTrsQty() {
        return trsQty;
    }

    public void setTrsQty(BigDecimal trsQty) {
        this.trsQty = trsQty;
    }

    public BigDecimal getOtkQtys() {
        return otkQtys;
    }

    public void setOtkQtys(BigDecimal otkQtys) {
        this.otkQtys = otkQtys;
    }

    public String getZxSkResMoveMonthMPId() {
        return zxSkResMoveMonthMPId == null ? "" : zxSkResMoveMonthMPId.trim();
    }

    public void setZxSkResMoveMonthMPId(String zxSkResMoveMonthMPId) {
        this.zxSkResMoveMonthMPId = zxSkResMoveMonthMPId == null ? null : zxSkResMoveMonthMPId.trim();
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

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getStockQty() {
        return stockQty;
    }

    public void setStockQty(BigDecimal stockQty) {
        this.stockQty = stockQty;
    }

    public BigDecimal getStockAmt() {
        return stockAmt;
    }

    public void setStockAmt(BigDecimal stockAmt) {
        this.stockAmt = stockAmt;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public BigDecimal getInQty() {
        return inQty;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    public BigDecimal getInAmt() {
        return inAmt;
    }

    public void setInAmt(BigDecimal inAmt) {
        this.inAmt = inAmt;
    }

    public BigDecimal getOrsQty() {
        return orsQty;
    }

    public void setOrsQty(BigDecimal orsQty) {
        this.orsQty = orsQty;
    }

    public BigDecimal getOtrQty() {
        return otrQty;
    }

    public void setOtrQty(BigDecimal otrQty) {
        this.otrQty = otrQty;
    }

    public BigDecimal getSerQty() {
        return serQty;
    }

    public void setSerQty(BigDecimal serQty) {
        this.serQty = serQty;
    }

    public BigDecimal getObuQty() {
        return obuQty;
    }

    public void setObuQty(BigDecimal obuQty) {
        this.obuQty = obuQty;
    }

    public BigDecimal getOcsQty() {
        return ocsQty;
    }

    public void setOcsQty(BigDecimal ocsQty) {
        this.ocsQty = ocsQty;
    }

    public BigDecimal getOswQty() {
        return oswQty;
    }

    public void setOswQty(BigDecimal oswQty) {
        this.oswQty = oswQty;
    }

    public BigDecimal getOswAmt() {
        return oswAmt;
    }

    public void setOswAmt(BigDecimal oswAmt) {
        this.oswAmt = oswAmt;
    }

    public BigDecimal getOswPrice() {
        return oswPrice;
    }

    public void setOswPrice(BigDecimal oswPrice) {
        this.oswPrice = oswPrice;
    }

    public BigDecimal getOutAmt() {
        return outAmt;
    }

    public void setOutAmt(BigDecimal outAmt) {
        this.outAmt = outAmt;
    }

    public BigDecimal getOtkPrice() {
        return otkPrice;
    }

    public void setOtkPrice(BigDecimal otkPrice) {
        this.otkPrice = otkPrice;
    }

    public BigDecimal getOtkQty() {
        return otkQty;
    }

    public void setOtkQty(BigDecimal otkQty) {
        this.otkQty = otkQty;
    }

    public BigDecimal getVinQty() {
        return vinQty;
    }

    public void setVinQty(BigDecimal vinQty) {
        this.vinQty = vinQty;
    }

    public BigDecimal getVinAmt() {
        return vinAmt;
    }

    public void setVinAmt(BigDecimal vinAmt) {
        this.vinAmt = vinAmt;
    }

    public BigDecimal getThisQtys() {
        return thisQtys;
    }

    public void setThisQtys(BigDecimal thisQtys) {
        this.thisQtys = thisQtys;
    }

    public BigDecimal getThisAmts() {
        return thisAmts;
    }

    public void setThisAmts(BigDecimal thisAmts) {
        this.thisAmts = thisAmts;
    }

    public BigDecimal getThisProce() {
        return thisProce;
    }

    public void setThisProce(BigDecimal thisProce) {
        this.thisProce = thisProce;
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
