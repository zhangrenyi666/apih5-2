package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrProjectEvaluationScore extends BasePojo {

    // 前端用
    private String id;
    // 主键
    private String zxCrProjectEvaluationScoreId;
    // mainID
    private String mainID;

    // 评价内容
    private String evalContent;

    // 评价细目
    private String scoreItem;

    // 标准分值
    private BigDecimal standardScore;

    // 项目减分
    private BigDecimal deductScore;

    // 项目得分
    private BigDecimal getScore;

    // editTime
    private Date editTime;

    // comID
    private String comID;

    // comName
    private String comName;

    // comOrders
    private String comOrders;

    // 打分考核id
    private String evalID;

    // 排序
    private String orderStr;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZxCrProjectEvaluationScoreId() {
        return zxCrProjectEvaluationScoreId == null ? "" : zxCrProjectEvaluationScoreId.trim();
    }

    public void setZxCrProjectEvaluationScoreId(String zxCrProjectEvaluationScoreId) {
        this.zxCrProjectEvaluationScoreId = zxCrProjectEvaluationScoreId == null ? null : zxCrProjectEvaluationScoreId.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
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

    public BigDecimal getDeductScore() {
        return deductScore;
    }

    public void setDeductScore(BigDecimal deductScore) {
        this.deductScore = deductScore;
    }

    public BigDecimal getGetScore() {
        return getScore;
    }

    public void setGetScore(BigDecimal getScore) {
        this.getScore = getScore;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public String getEvalID() {
        return evalID == null ? "" : evalID.trim();
    }

    public void setEvalID(String evalID) {
        this.evalID = evalID == null ? null : evalID.trim();
    }

    public String getOrderStr() {
        return orderStr == null ? "" : orderStr.trim();
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr == null ? null : orderStr.trim();
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
