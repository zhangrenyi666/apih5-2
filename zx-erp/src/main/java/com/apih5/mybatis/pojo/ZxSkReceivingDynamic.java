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

public class ZxSkReceivingDynamic extends BasePojo {
    // id
    private String id;

    // 日期
    private Date busDate;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 规格
    private String spec;

    // 计量单位
    private String resUnit;

    // 凭证号
    private String voucherNo;

    // 摘要
    private String outOrgName;

    // 购入单价
    private BigDecimal inPrice;

    // 收入-自行采购
    private BigDecimal serQty;

    // 收入-甲供
    private BigDecimal orsQty;

    // 收入-其它
    private BigDecimal otrQty;

    // 收入-预收
    private BigDecimal obuQty;

    // 收入-甲控
    private BigDecimal ocsQty;

    // 收入-合计-数量
    private BigDecimal allInQty;

    // 收入-合计-金额(元)
    private BigDecimal allInAmt;

    // 发出-工程耗用
    private BigDecimal oswQty;

    // 发出-加工改制
    private BigDecimal trsQty;

    // 发出-调拨-内调
    private BigDecimal otsQty;

    // 发出-调拨-外调
    private BigDecimal otkQty;

    // 发出-合计-数量
    private BigDecimal allOutQty;

    // 发出-合计-平均单价
    private BigDecimal oswPrice;

    // 发出-合计-金额(元)
    private BigDecimal allOutAmt;

    // 盈亏(+)-数量
    private BigDecimal vinoutQty;

    // 盈亏(+)-金额
    private BigDecimal vinoutAmt;

    // 结存-数量
    private BigDecimal lastQty;

    // 结存-平均单价
    private BigDecimal lastPrice;

    // 结存-金额
    private BigDecimal lastAmt;

    // 开始时间
    private Date beginDate;

    // 结束时间
    private Date endDate;

    // 备注
    private String remarks;

    private String orgID;

    private String resID;

    private String resTypeID;

    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public String getResTypeID() {
        return resTypeID;
    }

    public void setResTypeID(String resTypeID) {
        this.resTypeID = resTypeID;
    }

    // 排序
    private int sort=0;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getBusDate() {
        return busDate;
    }

    public void setBusDate(Date busDate) {
        this.busDate = busDate;
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

    public String getResUnit() {
        return resUnit == null ? "" : resUnit.trim();
    }

    public void setResUnit(String resUnit) {
        this.resUnit = resUnit == null ? null : resUnit.trim();
    }

    public String getVoucherNo() {
        return voucherNo == null ? "" : voucherNo.trim();
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo == null ? null : voucherNo.trim();
    }

    public String getOutOrgName() {
        return outOrgName == null ? "" : outOrgName.trim();
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName == null ? null : outOrgName.trim();
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    public BigDecimal getSerQty() {
        return serQty;
    }

    public void setSerQty(BigDecimal serQty) {
        this.serQty = serQty;
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

    public BigDecimal getAllInQty() {
        return allInQty;
    }

    public void setAllInQty(BigDecimal allInQty) {
        this.allInQty = allInQty;
    }

    public BigDecimal getAllInAmt() {
        return allInAmt;
    }

    public void setAllInAmt(BigDecimal allInAmt) {
        this.allInAmt = allInAmt;
    }

    public BigDecimal getOswQty() {
        return oswQty;
    }

    public void setOswQty(BigDecimal oswQty) {
        this.oswQty = oswQty;
    }

    public BigDecimal getTrsQty() {
        return trsQty;
    }

    public void setTrsQty(BigDecimal trsQty) {
        this.trsQty = trsQty;
    }

    public BigDecimal getOtsQty() {
        return otsQty;
    }

    public void setOtsQty(BigDecimal otsQty) {
        this.otsQty = otsQty;
    }

    public BigDecimal getOtkQty() {
        return otkQty;
    }

    public void setOtkQty(BigDecimal otkQty) {
        this.otkQty = otkQty;
    }

    public BigDecimal getAllOutQty() {
        return allOutQty;
    }

    public void setAllOutQty(BigDecimal allOutQty) {
        this.allOutQty = allOutQty;
    }

    public BigDecimal getOswPrice() {
        return oswPrice;
    }

    public void setOswPrice(BigDecimal oswPrice) {
        this.oswPrice = oswPrice;
    }

    public BigDecimal getAllOutAmt() {
        return allOutAmt;
    }

    public void setAllOutAmt(BigDecimal allOutAmt) {
        this.allOutAmt = allOutAmt;
    }

    public BigDecimal getVinoutQty() {
        return vinoutQty;
    }

    public void setVinoutQty(BigDecimal vinoutQty) {
        this.vinoutQty = vinoutQty;
    }

    public BigDecimal getVinoutAmt() {
        return vinoutAmt;
    }

    public void setVinoutAmt(BigDecimal vinoutAmt) {
        this.vinoutAmt = vinoutAmt;
    }

    public BigDecimal getLastQty() {
        return lastQty;
    }

    public void setLastQty(BigDecimal lastQty) {
        this.lastQty = lastQty;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(BigDecimal lastPrice) {
        this.lastPrice = lastPrice;
    }

    public BigDecimal getLastAmt() {
        return lastAmt;
    }

    public void setLastAmt(BigDecimal lastAmt) {
        this.lastAmt = lastAmt;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
