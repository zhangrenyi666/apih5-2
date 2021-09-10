package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkMosResMovStatRep extends BasePojo {
    // 主键
    private String zxSkMosResMovStatRepId;

    // 物资id
    private String resourceID;

    // catCode
    private String catCode;

    // 物资名称
    private String resName;

    // 单位
    private String unit;

    // 物资编码
    private String resCode;

    // 年初数量
    private BigDecimal stockQty;

    // 年初金额
    private BigDecimal stockAmt;

    // 自行采购数量
    private BigDecimal serQty;

    // 自行采购金额
    private BigDecimal serAmt;

    // 自行采购不含税金额
    private BigDecimal serAmtNoTax;

    // 甲供数量
    private BigDecimal orsQty;

    // 甲供金额
    private BigDecimal orsAmt;

    // 甲供不含税金额
    private BigDecimal orsAmtNoTax;

    // 其他数量
    private BigDecimal otrQty;

    // 其他金额
    private BigDecimal otrAmt;

    // 其他不含税金额
    private BigDecimal otrAmtNoTax;

    // 甲控数量
    private BigDecimal ocsQty;

    // 甲控金额
    private BigDecimal ocsAmt;

    // 甲控不含税金额
    private BigDecimal ocsAmtNoTax;

    // 预收数量
    private BigDecimal obuQty;

    // 工程耗用数量
    private BigDecimal oswQty;

    // 工程耗用金额
    private BigDecimal oswAmt;

    // 调拨数量
    private BigDecimal otkQty;

    // 调拨金额
    private BigDecimal otkAmt;

    // 盈亏数量
    private BigDecimal vinQty;

    // 盈亏金额
    private BigDecimal vinAmt;

    // 数量
    private BigDecimal qty;

    // 不含税单价
    private BigDecimal priceNoTax;

    // 含税单价
    private BigDecimal price;

    // 不含税金额
    private BigDecimal amtNoTax;

    // 含税金额
    private BigDecimal amt;

    // 统计字母或代码
    private String code;

    // 期末库存量
    private BigDecimal endQty;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 公司ID
    private String comID;

    // 公司名称
    private String comName;

    // 集团ID
    private String blocID;

    // 集团名称
    private String blocName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //
    private String orderNo;

    //统计字母或代码
    private String statisCode;

    public String getStatisCode() {
        return statisCode;
    }

    public void setStatisCode(String statisCode) {
        this.statisCode = statisCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getZxSkMosResMovStatRepId() {
        return zxSkMosResMovStatRepId == null ? "" : zxSkMosResMovStatRepId.trim();
    }

    public void setZxSkMosResMovStatRepId(String zxSkMosResMovStatRepId) {
        this.zxSkMosResMovStatRepId = zxSkMosResMovStatRepId == null ? null : zxSkMosResMovStatRepId.trim();
    }

    public String getResourceID() {
        return resourceID == null ? "" : resourceID.trim();
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID == null ? null : resourceID.trim();
    }

    public String getCatCode() {
        return catCode == null ? "" : catCode.trim();
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode == null ? null : catCode.trim();
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

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
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

    public BigDecimal getSerQty() {
        return serQty;
    }

    public void setSerQty(BigDecimal serQty) {
        this.serQty = serQty;
    }

    public BigDecimal getSerAmt() {
        return serAmt;
    }

    public void setSerAmt(BigDecimal serAmt) {
        this.serAmt = serAmt;
    }

    public BigDecimal getSerAmtNoTax() {
        return serAmtNoTax;
    }

    public void setSerAmtNoTax(BigDecimal serAmtNoTax) {
        this.serAmtNoTax = serAmtNoTax;
    }

    public BigDecimal getOrsQty() {
        return orsQty;
    }

    public void setOrsQty(BigDecimal orsQty) {
        this.orsQty = orsQty;
    }

    public BigDecimal getOrsAmt() {
        return orsAmt;
    }

    public void setOrsAmt(BigDecimal orsAmt) {
        this.orsAmt = orsAmt;
    }

    public BigDecimal getOrsAmtNoTax() {
        return orsAmtNoTax;
    }

    public void setOrsAmtNoTax(BigDecimal orsAmtNoTax) {
        this.orsAmtNoTax = orsAmtNoTax;
    }

    public BigDecimal getOtrQty() {
        return otrQty;
    }

    public void setOtrQty(BigDecimal otrQty) {
        this.otrQty = otrQty;
    }

    public BigDecimal getOtrAmt() {
        return otrAmt;
    }

    public void setOtrAmt(BigDecimal otrAmt) {
        this.otrAmt = otrAmt;
    }

    public BigDecimal getOtrAmtNoTax() {
        return otrAmtNoTax;
    }

    public void setOtrAmtNoTax(BigDecimal otrAmtNoTax) {
        this.otrAmtNoTax = otrAmtNoTax;
    }

    public BigDecimal getOcsQty() {
        return ocsQty;
    }

    public void setOcsQty(BigDecimal ocsQty) {
        this.ocsQty = ocsQty;
    }

    public BigDecimal getOcsAmt() {
        return ocsAmt;
    }

    public void setOcsAmt(BigDecimal ocsAmt) {
        this.ocsAmt = ocsAmt;
    }

    public BigDecimal getOcsAmtNoTax() {
        return ocsAmtNoTax;
    }

    public void setOcsAmtNoTax(BigDecimal ocsAmtNoTax) {
        this.ocsAmtNoTax = ocsAmtNoTax;
    }

    public BigDecimal getObuQty() {
        return obuQty;
    }

    public void setObuQty(BigDecimal obuQty) {
        this.obuQty = obuQty;
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

    public BigDecimal getOtkQty() {
        return otkQty;
    }

    public void setOtkQty(BigDecimal otkQty) {
        this.otkQty = otkQty;
    }

    public BigDecimal getOtkAmt() {
        return otkAmt;
    }

    public void setOtkAmt(BigDecimal otkAmt) {
        this.otkAmt = otkAmt;
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

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPriceNoTax() {
        return priceNoTax;
    }

    public void setPriceNoTax(BigDecimal priceNoTax) {
        this.priceNoTax = priceNoTax;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmtNoTax() {
        return amtNoTax;
    }

    public void setAmtNoTax(BigDecimal amtNoTax) {
        this.amtNoTax = amtNoTax;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getCode() {
        return code == null ? "" : code.trim();
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public BigDecimal getEndQty() {
        return endQty;
    }

    public void setEndQty(BigDecimal endQty) {
        this.endQty = endQty;
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getBlocID() {
        return blocID == null ? "" : blocID.trim();
    }

    public void setBlocID(String blocID) {
        this.blocID = blocID == null ? null : blocID.trim();
    }

    public String getBlocName() {
        return blocName == null ? "" : blocName.trim();
    }

    public void setBlocName(String blocName) {
        this.blocName = blocName == null ? null : blocName.trim();
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
