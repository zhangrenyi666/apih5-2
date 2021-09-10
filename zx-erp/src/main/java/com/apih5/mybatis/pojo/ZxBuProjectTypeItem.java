package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuProjectTypeItem extends BasePojo {
    // 主键
    private String zxBuProjectTypeItemId;

    // 项目工程类别ID
    private String mainID;

    // 类别ID类别ID
    private String checkLevel2ID;

    // 类别
    private String checkLevel2Name;

    // 子类别ID
    private String checkLevel3ID;

    // 子类别
    private String checkLevel3Name;

    // 系数
    private BigDecimal rate;

    // 关系一
    private String operate1;

    // 参数一
    private BigDecimal rate1;

    // 关系二
    private String operate2;

    // 参数二
    private BigDecimal rate2;

    // 参考系数
    private String dispRate;

    // 最后修改时间
    private Date editTime;

    // combProp
    private String combProp;

    // pp1
    private String pp1;

    // pp2
    private String pp2;

    // pp3
    private String pp3;

    // pp4
    private String pp4;

    // pp5
    private String pp5;

    // pp6
    private String pp6;

    // pp7
    private String pp7;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxBuProjectTypeItemId() {
        return zxBuProjectTypeItemId == null ? "" : zxBuProjectTypeItemId.trim();
    }

    public void setZxBuProjectTypeItemId(String zxBuProjectTypeItemId) {
        this.zxBuProjectTypeItemId = zxBuProjectTypeItemId == null ? null : zxBuProjectTypeItemId.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public String getCheckLevel2ID() {
        return checkLevel2ID == null ? "" : checkLevel2ID.trim();
    }

    public void setCheckLevel2ID(String checkLevel2ID) {
        this.checkLevel2ID = checkLevel2ID == null ? null : checkLevel2ID.trim();
    }

    public String getCheckLevel2Name() {
        return checkLevel2Name == null ? "" : checkLevel2Name.trim();
    }

    public void setCheckLevel2Name(String checkLevel2Name) {
        this.checkLevel2Name = checkLevel2Name == null ? null : checkLevel2Name.trim();
    }

    public String getCheckLevel3ID() {
        return checkLevel3ID == null ? "" : checkLevel3ID.trim();
    }

    public void setCheckLevel3ID(String checkLevel3ID) {
        this.checkLevel3ID = checkLevel3ID == null ? null : checkLevel3ID.trim();
    }

    public String getCheckLevel3Name() {
        return checkLevel3Name == null ? "" : checkLevel3Name.trim();
    }

    public void setCheckLevel3Name(String checkLevel3Name) {
        this.checkLevel3Name = checkLevel3Name == null ? null : checkLevel3Name.trim();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getOperate1() {
        return operate1 == null ? "" : operate1.trim();
    }

    public void setOperate1(String operate1) {
        this.operate1 = operate1 == null ? null : operate1.trim();
    }

    public BigDecimal getRate1() {
        return rate1;
    }

    public void setRate1(BigDecimal rate1) {
        this.rate1 = rate1;
    }

    public String getOperate2() {
        return operate2 == null ? "" : operate2.trim();
    }

    public void setOperate2(String operate2) {
        this.operate2 = operate2 == null ? null : operate2.trim();
    }

    public BigDecimal getRate2() {
        return rate2;
    }

    public void setRate2(BigDecimal rate2) {
        this.rate2 = rate2;
    }

    public String getDispRate() {
        return dispRate == null ? "" : dispRate.trim();
    }

    public void setDispRate(String dispRate) {
        this.dispRate = dispRate == null ? null : dispRate.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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
