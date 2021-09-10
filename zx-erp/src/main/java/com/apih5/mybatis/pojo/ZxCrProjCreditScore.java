package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrProjCreditScore extends BasePojo {
    // 主键
    private String id;

    // 评价内容
    private String evalContent;

    // 评分细目
    private String scoreItem;

    // 标准分值
    private BigDecimal standardScore;

    // 是否启用
    private String isUse;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // department
    private String department;

    // appraiser
    private String appraiser;

    // 排序
    private String orderStr;

    private BigDecimal getScore;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getEvalContent() {
        return evalContent == null ? "" : evalContent.trim();
    }

    public void setEvalContent(String evalContent) {
        this.evalContent = evalContent == null ? null : evalContent.trim();
    }

    public String getScoreItem() {
        return scoreItem == null ? "" : scoreItem.trim();
    }

    public void setScoreItem(String scoreItem) {
        this.scoreItem = scoreItem == null ? null : scoreItem.trim();
    }

    public BigDecimal getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(BigDecimal standardScore) {
        this.standardScore = standardScore;
    }

    public String getIsUse() {
        return isUse == null ? "" : isUse.trim();
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse == null ? null : isUse.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getDepartment() {
        return department == null ? "" : department.trim();
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getAppraiser() {
        return appraiser == null ? "" : appraiser.trim();
    }

    public void setAppraiser(String appraiser) {
        this.appraiser = appraiser == null ? null : appraiser.trim();
    }

    public String getOrderStr() {
        return orderStr == null ? "" : orderStr.trim();
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr == null ? null : orderStr.trim();
    }

    public BigDecimal getGetScore() {
        return getScore;
    }

    public void setGetScore(BigDecimal getScore) {
        this.getScore = getScore;
    }
}
