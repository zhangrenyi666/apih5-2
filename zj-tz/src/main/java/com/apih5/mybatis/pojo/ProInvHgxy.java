package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ProInvHgxy extends BasePojo {
    private String id;

    private String invHtinfoId;

    private String htmc;

    private String htjf;

    private String htyf;

    private BigDecimal hgje;

    private Date qdrq;

    private String hgxyfj;

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

    public String getHtmc() {
        return htmc == null ? "" : htmc.trim();
    }

    public void setHtmc(String htmc) {
        this.htmc = htmc == null ? null : htmc.trim();
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

    public BigDecimal getHgje() {
        return hgje;
    }

    public void setHgje(BigDecimal hgje) {
        this.hgje = hgje;
    }

    public Date getQdrq() {
        return qdrq;
    }

    public void setQdrq(Date qdrq) {
        this.qdrq = qdrq;
    }

    public String getHgxyfj() {
        return hgxyfj == null ? "" : hgxyfj.trim();
    }

    public void setHgxyfj(String hgxyfj) {
        this.hgxyfj = hgxyfj == null ? null : hgxyfj.trim();
    }

}

