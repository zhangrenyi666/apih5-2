package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesContractChangeDetail extends BasePojo {
    // 主键
    private String zxCtSuppliesContractChangeDetailId;

    // 申报单价
    private BigDecimal applyPrice;

    // 申报新增数量
    private BigDecimal applyAddQty;

    // 申报数量
    private BigDecimal applyQty;

    // 批复数量
    private BigDecimal replyQty;

    // 批复单价
    private BigDecimal replyPrice;

    // 标的类型
    private String bidType;

    // 变更类型
    private String alterType;

    // 变更ID
    private String alterID;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCtSuppliesContractChangeDetailId() {
        return zxCtSuppliesContractChangeDetailId == null ? "" : zxCtSuppliesContractChangeDetailId.trim();
    }

    public void setZxCtSuppliesContractChangeDetailId(String zxCtSuppliesContractChangeDetailId) {
        this.zxCtSuppliesContractChangeDetailId = zxCtSuppliesContractChangeDetailId == null ? null : zxCtSuppliesContractChangeDetailId.trim();
    }

    public BigDecimal getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(BigDecimal applyPrice) {
        this.applyPrice = applyPrice;
    }

    public BigDecimal getApplyAddQty() {
        return applyAddQty;
    }

    public void setApplyAddQty(BigDecimal applyAddQty) {
        this.applyAddQty = applyAddQty;
    }

    public BigDecimal getApplyQty() {
        return applyQty;
    }

    public void setApplyQty(BigDecimal applyQty) {
        this.applyQty = applyQty;
    }

    public BigDecimal getReplyQty() {
        return replyQty;
    }

    public void setReplyQty(BigDecimal replyQty) {
        this.replyQty = replyQty;
    }

    public BigDecimal getReplyPrice() {
        return replyPrice;
    }

    public void setReplyPrice(BigDecimal replyPrice) {
        this.replyPrice = replyPrice;
    }

    public String getBidType() {
        return bidType == null ? "" : bidType.trim();
    }

    public void setBidType(String bidType) {
        this.bidType = bidType == null ? null : bidType.trim();
    }

    public String getAlterType() {
        return alterType == null ? "" : alterType.trim();
    }

    public void setAlterType(String alterType) {
        this.alterType = alterType == null ? null : alterType.trim();
    }

    public String getAlterID() {
        return alterID == null ? "" : alterID.trim();
    }

    public void setAlterID(String alterID) {
        this.alterID = alterID == null ? null : alterID.trim();
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
