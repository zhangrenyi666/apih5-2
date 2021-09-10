package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzInvXmhgqkHgljsyqk extends BasePojo {
    private String id;

    private String xmhgqkId;

    private String category;

    private BigDecimal money;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getXmhgqkId() {
        return xmhgqkId == null ? "" : xmhgqkId.trim();
    }

    public void setXmhgqkId(String xmhgqkId) {
        this.xmhgqkId = xmhgqkId == null ? null : xmhgqkId.trim();
    }

    public String getCategory() {
        return category == null ? "" : category.trim();
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

}

