package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZxCtDayworkItem extends BasePojo {
    // 主键ID
    private String id;

    // 单据ID
    private String billID;

    // 资源ID
    private String resID;

    // 资源编号
    private String resCode;

    // 资源名称
    private String resName;

    // 规格型号
    private String spec;

    // 单位
    private String resUnit;

    // 数量
    private BigDecimal quantity;

    // 单价
    private BigDecimal price;

    // 其它费
    private BigDecimal otherFee;

    // 金额
    private BigDecimal amount;

    // 管理单元ID
    private String muID;

    // 成本管理单元
    private String muName;

    // cbsID
    private String cbsID;

    // 工作内容
    private String workContent;

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

    // 资源类型
    private String resstyle;

    // 最后编辑时间
    private Date editTime;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getResUnit() {
        return resUnit == null ? "" : resUnit.trim();
    }

    public void setResUnit(String resUnit) {
        this.resUnit = resUnit == null ? null : resUnit.trim();
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(BigDecimal otherFee) {
        this.otherFee = otherFee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMuID() {
        return muID == null ? "" : muID.trim();
    }

    public void setMuID(String muID) {
        this.muID = muID == null ? null : muID.trim();
    }

    public String getMuName() {
        return muName == null ? "" : muName.trim();
    }

    public void setMuName(String muName) {
        this.muName = muName == null ? null : muName.trim();
    }

    public String getCbsID() {
        return cbsID == null ? "" : cbsID.trim();
    }

    public void setCbsID(String cbsID) {
        this.cbsID = cbsID == null ? null : cbsID.trim();
    }

    public String getWorkContent() {
        return workContent == null ? "" : workContent.trim();
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent == null ? null : workContent.trim();
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

    public String getResstyle() {
        return resstyle == null ? "" : resstyle.trim();
    }

    public void setResstyle(String resstyle) {
        this.resstyle = resstyle == null ? null : resstyle.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
