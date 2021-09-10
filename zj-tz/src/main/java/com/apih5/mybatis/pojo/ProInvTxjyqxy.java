package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ProInvTxjyqxy extends BasePojo {
    private String id;

    private String invHtinfoId;

    private Integer xh;

    private String agreementName;

    private String htjf;

    private String htyf;

    private BigDecimal htMoney;

    private Date htqdDate;

    private Integer yy;

    private Date yyqsDate;

    private String htFile;

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

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getAgreementName() {
        return agreementName == null ? "" : agreementName.trim();
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName == null ? null : agreementName.trim();
    }

    public String getHtjf() {
        return htjf == null ? "" : htjf.trim();
    }

    public void setHtjf(String htjf) {
        this.htjf = htjf == null ? null : htjf.trim();
    }

    public String getHtyf() {
        return htyf == null ? "" : htyf.trim();
    }

    public void setHtyf(String htyf) {
        this.htyf = htyf == null ? null : htyf.trim();
    }

    public BigDecimal getHtMoney() {
        return htMoney;
    }

    public void setHtMoney(BigDecimal htMoney) {
        this.htMoney = htMoney;
    }

    public Date getHtqdDate() {
        return htqdDate;
    }

    public void setHtqdDate(Date htqdDate) {
        this.htqdDate = htqdDate;
    }

    public Integer getYy() {
        return yy;
    }

    public void setYy(Integer yy) {
        this.yy = yy;
    }

    public Date getYyqsDate() {
        return yyqsDate;
    }

    public void setYyqsDate(Date yyqsDate) {
        this.yyqsDate = yyqsDate;
    }

    public String getHtFile() {
        return htFile == null ? "" : htFile.trim();
    }

    public void setHtFile(String htFile) {
        this.htFile = htFile == null ? null : htFile.trim();
    }

}

