package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ProInvBasicPfinfo extends BasePojo {
    private String id;

    private String invProId;

    private String replyCategory;

    private BigDecimal jaf;

    private BigDecimal zcf;

    private BigDecimal glf;

    private BigDecimal jlf;

    private BigDecimal kcsjf;

    private BigDecimal oth;

    private BigDecimal pfzje;

    private Date replyDate;

    private String pgPf;

    private String bz;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInvProId() {
        return invProId == null ? "" : invProId.trim();
    }

    public void setInvProId(String invProId) {
        this.invProId = invProId == null ? null : invProId.trim();
    }

    public String getReplyCategory() {
        return replyCategory == null ? "" : replyCategory.trim();
    }

    public void setReplyCategory(String replyCategory) {
        this.replyCategory = replyCategory == null ? null : replyCategory.trim();
    }

    public BigDecimal getJaf() {
        return jaf;
    }

    public void setJaf(BigDecimal jaf) {
        this.jaf = jaf;
    }

    public BigDecimal getZcf() {
        return zcf;
    }

    public void setZcf(BigDecimal zcf) {
        this.zcf = zcf;
    }

    public BigDecimal getGlf() {
        return glf;
    }

    public void setGlf(BigDecimal glf) {
        this.glf = glf;
    }

    public BigDecimal getJlf() {
        return jlf;
    }

    public void setJlf(BigDecimal jlf) {
        this.jlf = jlf;
    }

    public BigDecimal getKcsjf() {
        return kcsjf;
    }

    public void setKcsjf(BigDecimal kcsjf) {
        this.kcsjf = kcsjf;
    }

    public BigDecimal getOth() {
        return oth;
    }

    public void setOth(BigDecimal oth) {
        this.oth = oth;
    }

    public BigDecimal getPfzje() {
        return pfzje;
    }

    public void setPfzje(BigDecimal pfzje) {
        this.pfzje = pfzje;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getPgPf() {
        return pgPf == null ? "" : pgPf.trim();
    }

    public void setPgPf(String pgPf) {
        this.pgPf = pgPf == null ? null : pgPf.trim();
    }

    public String getBz() {
        return bz == null ? "" : bz.trim();
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

}

