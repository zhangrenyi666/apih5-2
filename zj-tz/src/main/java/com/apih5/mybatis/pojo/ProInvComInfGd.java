package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ProInvComInfGd extends BasePojo {
    private String gdCategory;

    private String proComId;

    private String gdName;

    private BigDecimal cgbl;

    private BigDecimal fpbl;

    private BigDecimal cze;

    private String invProId;

    private String gdId;

    private String id;

    public String getGdCategory() {
        return gdCategory == null ? "" : gdCategory.trim();
    }

    public void setGdCategory(String gdCategory) {
        this.gdCategory = gdCategory == null ? null : gdCategory.trim();
    }

    public String getProComId() {
        return proComId == null ? "" : proComId.trim();
    }

    public void setProComId(String proComId) {
        this.proComId = proComId == null ? null : proComId.trim();
    }

    public String getGdName() {
        return gdName == null ? "" : gdName.trim();
    }

    public void setGdName(String gdName) {
        this.gdName = gdName == null ? null : gdName.trim();
    }

    public BigDecimal getCgbl() {
        return cgbl;
    }

    public void setCgbl(BigDecimal cgbl) {
        this.cgbl = cgbl;
    }

    public BigDecimal getFpbl() {
        return fpbl;
    }

    public void setFpbl(BigDecimal fpbl) {
        this.fpbl = fpbl;
    }

    public BigDecimal getCze() {
        return cze;
    }

    public void setCze(BigDecimal cze) {
        this.cze = cze;
    }

    public String getInvProId() {
        return invProId == null ? "" : invProId.trim();
    }

    public void setInvProId(String invProId) {
        this.invProId = invProId == null ? null : invProId.trim();
    }

    public String getGdId() {
        return gdId == null ? "" : gdId.trim();
    }

    public void setGdId(String gdId) {
        this.gdId = gdId == null ? null : gdId.trim();
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

}

