package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ProInvBgbcxy extends BasePojo {
    private String id;

    private String invHtinfoId;

    private String bchtName;

    private BigDecimal bcjafMoney;

    private BigDecimal bchjafMoney;

    private BigDecimal bczcfMoney;

    private BigDecimal bchzcfMoney;

    private BigDecimal bcglfMoney;

    private BigDecimal bchglfMoney;

    private BigDecimal bcjlfMoney;

    private BigDecimal bchjlfMoney;

    private BigDecimal bcccsjfMoney;

    private BigDecimal bchccsjfMoney;

    private BigDecimal bcothMoney;

    private BigDecimal bchothMoney;

    private BigDecimal bchhtMoney;

    private BigDecimal bchhtgq;

    private String isNewlabel;

    private Date nrxtglDate;

    private Date sbjtglDate;

    private Date bcxyqdDate;

    private String currency;

    private String createBy;

    private String createOrg;

    private Date createDate;

    private String updateBy;

    private String updateOrg;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInvHtinfoId() {
        return invHtinfoId == null ? "" : invHtinfoId.trim();
    }

    public void setInvHtinfoId(String invHtinfoId) {
        this.invHtinfoId = invHtinfoId == null ? null : invHtinfoId.trim();
    }

    public String getBchtName() {
        return bchtName == null ? "" : bchtName.trim();
    }

    public void setBchtName(String bchtName) {
        this.bchtName = bchtName == null ? null : bchtName.trim();
    }

    public BigDecimal getBcjafMoney() {
        return bcjafMoney;
    }

    public void setBcjafMoney(BigDecimal bcjafMoney) {
        this.bcjafMoney = bcjafMoney;
    }

    public BigDecimal getBchjafMoney() {
        return bchjafMoney;
    }

    public void setBchjafMoney(BigDecimal bchjafMoney) {
        this.bchjafMoney = bchjafMoney;
    }

    public BigDecimal getBczcfMoney() {
        return bczcfMoney;
    }

    public void setBczcfMoney(BigDecimal bczcfMoney) {
        this.bczcfMoney = bczcfMoney;
    }

    public BigDecimal getBchzcfMoney() {
        return bchzcfMoney;
    }

    public void setBchzcfMoney(BigDecimal bchzcfMoney) {
        this.bchzcfMoney = bchzcfMoney;
    }

    public BigDecimal getBcglfMoney() {
        return bcglfMoney;
    }

    public void setBcglfMoney(BigDecimal bcglfMoney) {
        this.bcglfMoney = bcglfMoney;
    }

    public BigDecimal getBchglfMoney() {
        return bchglfMoney;
    }

    public void setBchglfMoney(BigDecimal bchglfMoney) {
        this.bchglfMoney = bchglfMoney;
    }

    public BigDecimal getBcjlfMoney() {
        return bcjlfMoney;
    }

    public void setBcjlfMoney(BigDecimal bcjlfMoney) {
        this.bcjlfMoney = bcjlfMoney;
    }

    public BigDecimal getBchjlfMoney() {
        return bchjlfMoney;
    }

    public void setBchjlfMoney(BigDecimal bchjlfMoney) {
        this.bchjlfMoney = bchjlfMoney;
    }

    public BigDecimal getBcccsjfMoney() {
        return bcccsjfMoney;
    }

    public void setBcccsjfMoney(BigDecimal bcccsjfMoney) {
        this.bcccsjfMoney = bcccsjfMoney;
    }

    public BigDecimal getBchccsjfMoney() {
        return bchccsjfMoney;
    }

    public void setBchccsjfMoney(BigDecimal bchccsjfMoney) {
        this.bchccsjfMoney = bchccsjfMoney;
    }

    public BigDecimal getBcothMoney() {
        return bcothMoney;
    }

    public void setBcothMoney(BigDecimal bcothMoney) {
        this.bcothMoney = bcothMoney;
    }

    public BigDecimal getBchothMoney() {
        return bchothMoney;
    }

    public void setBchothMoney(BigDecimal bchothMoney) {
        this.bchothMoney = bchothMoney;
    }

    public BigDecimal getBchhtMoney() {
        return bchhtMoney;
    }

    public void setBchhtMoney(BigDecimal bchhtMoney) {
        this.bchhtMoney = bchhtMoney;
    }

    public BigDecimal getBchhtgq() {
        return bchhtgq;
    }

    public void setBchhtgq(BigDecimal bchhtgq) {
        this.bchhtgq = bchhtgq;
    }

    public String getIsNewlabel() {
        return isNewlabel == null ? "" : isNewlabel.trim();
    }

    public void setIsNewlabel(String isNewlabel) {
        this.isNewlabel = isNewlabel == null ? null : isNewlabel.trim();
    }

    public Date getNrxtglDate() {
        return nrxtglDate;
    }

    public void setNrxtglDate(Date nrxtglDate) {
        this.nrxtglDate = nrxtglDate;
    }

    public Date getSbjtglDate() {
        return sbjtglDate;
    }

    public void setSbjtglDate(Date sbjtglDate) {
        this.sbjtglDate = sbjtglDate;
    }

    public Date getBcxyqdDate() {
        return bcxyqdDate;
    }

    public void setBcxyqdDate(Date bcxyqdDate) {
        this.bcxyqdDate = bcxyqdDate;
    }

    public String getCurrency() {
        return currency == null ? "" : currency.trim();
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getCreateBy() {
        return createBy == null ? "" : createBy.trim();
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateOrg() {
        return createOrg == null ? "" : createOrg.trim();
    }

    public void setCreateOrg(String createOrg) {
        this.createOrg = createOrg == null ? null : createOrg.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy == null ? "" : updateBy.trim();
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdateOrg() {
        return updateOrg == null ? "" : updateOrg.trim();
    }

    public void setUpdateOrg(String updateOrg) {
        this.updateOrg = updateOrg == null ? null : updateOrg.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

}

