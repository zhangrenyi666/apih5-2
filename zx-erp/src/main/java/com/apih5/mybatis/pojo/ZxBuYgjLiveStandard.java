package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuYgjLiveStandard extends BasePojo {
    // 主键
    private String zxBuYgjLiveStandardId;

    // 父节点id
    private String parentID;

    // 机构ID
    private String orgID;

    // 项目类型
    private String projType;

    // 树形编码
    private String treeCode;

    // 是否叶子
    private int leaf=0;

    // 名称
    private String name;

    // 节点类型
    private String nodeType;

    // 单位
    private String unit;

    // 数量
    private BigDecimal count;

    // 不含税单价/工期(月)
    private BigDecimal price;

    // 不含税金额(元)/金额(元)
    private BigDecimal money;

    // 是否生效
    private int activity=0;

    // 生效日期
    private Date activityDate;

    // 编号
    private String pp1;

    // 
    private String pp2;

    // 
    private String pp3;

    // 
    private String pp4;

    // 
    private String pp5;

    // 
    private String pp6;

    // 
    private String pp7;

    // 
    private String pp8;

    // 
    private String pp9;

    // 
    private String pp10;

    // 税金单价/标准
    private BigDecimal priceTax;

    // 进项税金(元)
    private BigDecimal moneyTax;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxBuYgjLiveStandardId() {
        return zxBuYgjLiveStandardId == null ? "" : zxBuYgjLiveStandardId.trim();
    }

    public void setZxBuYgjLiveStandardId(String zxBuYgjLiveStandardId) {
        this.zxBuYgjLiveStandardId = zxBuYgjLiveStandardId == null ? null : zxBuYgjLiveStandardId.trim();
    }

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getProjType() {
        return projType == null ? "" : projType.trim();
    }

    public void setProjType(String projType) {
        this.projType = projType == null ? null : projType.trim();
    }

    public String getTreeCode() {
        return treeCode == null ? "" : treeCode.trim();
    }

    public void setTreeCode(String treeCode) {
        this.treeCode = treeCode == null ? null : treeCode.trim();
    }

    public int getLeaf() {
        return leaf;
    }

    public void setLeaf(int leaf) {
        this.leaf = leaf;
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNodeType() {
        return nodeType == null ? "" : nodeType.trim();
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
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

    public BigDecimal getPriceTax() {
        return priceTax;
    }

    public void setPriceTax(BigDecimal priceTax) {
        this.priceTax = priceTax;
    }

    public BigDecimal getMoneyTax() {
        return moneyTax;
    }

    public void setMoneyTax(BigDecimal moneyTax) {
        this.moneyTax = moneyTax;
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
