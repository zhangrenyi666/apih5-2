package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ProInvXmhgInfo extends BasePojo {
    private String id;

    private String invHtinfoId;

    private Date hgxyDate;

    private BigDecimal hgxyMoney;

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

    public Date getHgxyDate() {
        return hgxyDate;
    }

    public void setHgxyDate(Date hgxyDate) {
        this.hgxyDate = hgxyDate;
    }

    public BigDecimal getHgxyMoney() {
        return hgxyMoney;
    }

    public void setHgxyMoney(BigDecimal hgxyMoney) {
        this.hgxyMoney = hgxyMoney;
    }

}

