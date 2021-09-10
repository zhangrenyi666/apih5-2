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

public class ZxSkResWasAdmList extends BasePojo {
    // 主键
    private String zxSkResWasAdmListId;

    private String cbsID;

    // 分部分项名称
    private String cbsName;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 物资规格
    private String spec;

    // 物资单位
    private String resUnit;

    // 单价
    private BigDecimal price;

    // 数量
    private BigDecimal outQty;

    // 金额
    private BigDecimal outAmt;

    // 项目id
    private String orgID;

    // 材料分类
    private String resTypeID;

    // 期次
    private Date period;

    // 开始时间
    private Date beginDate;

    // 结束时间
    private Date endDate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxSkResWasAdmListId() {
        return zxSkResWasAdmListId == null ? "" : zxSkResWasAdmListId.trim();
    }

    public void setZxSkResWasAdmListId(String zxSkResWasAdmListId) {
        this.zxSkResWasAdmListId = zxSkResWasAdmListId == null ? null : zxSkResWasAdmListId.trim();
    }

    public String getCbsName() {
        return cbsName == null ? "" : cbsName.trim();
    }

    public void setCbsName(String cbsName) {
        this.cbsName = cbsName == null ? null : cbsName.trim();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOutQty() {
        return outQty;
    }

    public void setOutQty(BigDecimal outQty) {
        this.outQty = outQty;
    }

    public BigDecimal getOutAmt() {
        return outAmt;
    }

    public void setOutAmt(BigDecimal outAmt) {
        this.outAmt = outAmt;
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getResTypeID() {
        return resTypeID == null ? "" : resTypeID.trim();
    }

    public void setResTypeID(String resTypeID) {
        this.resTypeID = resTypeID == null ? null : resTypeID.trim();
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
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

    public String getCbsID() {
        return cbsID;
    }

    public void setCbsID(String cbsID) {
        this.cbsID = cbsID;
    }
}
