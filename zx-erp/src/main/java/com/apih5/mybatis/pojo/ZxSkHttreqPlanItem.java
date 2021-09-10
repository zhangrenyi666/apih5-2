package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkHttreqPlanItem extends BasePojo {
    // 主键
    private String zxSkHttreqPlanItemId;

    // 主表ID
    private String ttReqPlanID;

    // 总需用计划明细ID
    private String ttReqPlanItemID;

    // 操作人
    private String oper;

    // 操作时间
    private Date opTime;

    // 原始量
    private BigDecimal sourceNum;

    // 变更量
    private BigDecimal changeNum;

    // 操作
    private String op;

    // null
    private String combProp;

    // null
    private String pp1;

    // null
    private String pp2;

    // null
    private String pp3;

    // null
    private String pp4;

    // null
    private String pp5;

    // null
    private String pp6;

    // null
    private String pp7;

    // null
    private String pp8;

    // null
    private String pp9;

    // null
    private String pp10;

    // null
    private Date editTime;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxSkHttreqPlanItemId() {
        return zxSkHttreqPlanItemId == null ? "" : zxSkHttreqPlanItemId.trim();
    }

    public void setZxSkHttreqPlanItemId(String zxSkHttreqPlanItemId) {
        this.zxSkHttreqPlanItemId = zxSkHttreqPlanItemId == null ? null : zxSkHttreqPlanItemId.trim();
    }

    public String getTtReqPlanID() {
        return ttReqPlanID == null ? "" : ttReqPlanID.trim();
    }

    public void setTtReqPlanID(String ttReqPlanID) {
        this.ttReqPlanID = ttReqPlanID == null ? null : ttReqPlanID.trim();
    }

    public String getTtReqPlanItemID() {
        return ttReqPlanItemID == null ? "" : ttReqPlanItemID.trim();
    }

    public void setTtReqPlanItemID(String ttReqPlanItemID) {
        this.ttReqPlanItemID = ttReqPlanItemID == null ? null : ttReqPlanItemID.trim();
    }

    public String getOper() {
        return oper == null ? "" : oper.trim();
    }

    public void setOper(String oper) {
        this.oper = oper == null ? null : oper.trim();
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public BigDecimal getSourceNum() {
        return sourceNum;
    }

    public void setSourceNum(BigDecimal sourceNum) {
        this.sourceNum = sourceNum;
    }

    public BigDecimal getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(BigDecimal changeNum) {
        this.changeNum = changeNum;
    }

    public String getOp() {
        return op == null ? "" : op.trim();
    }

    public void setOp(String op) {
        this.op = op == null ? null : op.trim();
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getPp2() {
        return pp2 == null ? "" : pp2.trim();
    }

    public void setPp2(String pp2) {
        this.pp2 = pp2 == null ? null : pp2.trim();
    }

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public String getPp4() {
        return pp4 == null ? "" : pp4.trim();
    }

    public void setPp4(String pp4) {
        this.pp4 = pp4 == null ? null : pp4.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
    }

    public String getPp7() {
        return pp7 == null ? "" : pp7.trim();
    }

    public void setPp7(String pp7) {
        this.pp7 = pp7 == null ? null : pp7.trim();
    }

    public String getPp8() {
        return pp8 == null ? "" : pp8.trim();
    }

    public void setPp8(String pp8) {
        this.pp8 = pp8 == null ? null : pp8.trim();
    }

    public String getPp9() {
        return pp9 == null ? "" : pp9.trim();
    }

    public void setPp9(String pp9) {
        this.pp9 = pp9 == null ? null : pp9.trim();
    }

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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
