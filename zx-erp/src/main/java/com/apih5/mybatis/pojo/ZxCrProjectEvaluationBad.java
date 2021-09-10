package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrProjectEvaluationBad extends BasePojo {

    // id
    private String id;
    // 主键
    private String zxCrProjectEvaluationBadId;

    // mainID
    private String mainID;

    // 评价内容
    private String evalContent;

    // 评价细目
    private String scoreItem;

    // 是否存在严重不良行为
    private String isBad;

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

    // 备注
    private String remarks;

    // 排序
    private int sort = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZxCrProjectEvaluationBadId() {
        return zxCrProjectEvaluationBadId == null ? "" : zxCrProjectEvaluationBadId.trim();
    }

    public void setZxCrProjectEvaluationBadId(String zxCrProjectEvaluationBadId) {
        this.zxCrProjectEvaluationBadId = zxCrProjectEvaluationBadId == null ? null : zxCrProjectEvaluationBadId.trim();
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

    public String getIsBad() {
        return isBad == null ? "" : isBad.trim();
    }

    public void setIsBad(String isBad) {
        this.isBad = isBad == null ? null : isBad.trim();
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
