package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZxCrCustomerInfoQa extends BasePojo {
    // 主键ID
    private String ID;

    // 协作单位id
    private String bisID;

    // 附件id
    private String datumID;

    // 供应商编号
    private String customerNo;

    // 资质名称
    private String qaName;

    // 等级
    private String quLevel;

    // comID
    private String comID;

    // comName
    private String comName;

    // comOrders
    private String comOrders;

    // 序号
    private String orderStr;

    // 编辑时间
    private Date editTime;

    // 资质名称ID
    private String qaID;
    
    private String zxCrCustomerInfoId;

    public String getZxCrCustomerInfoId() {
		return zxCrCustomerInfoId;
	}

	public void setZxCrCustomerInfoId(String zxCrCustomerInfoId) {
		this.zxCrCustomerInfoId = zxCrCustomerInfoId;
	}

	// 等级ID
    private String quID;

	public String getID() {
        return ID == null ? "" : ID.trim();
    }

    public void setID(String ID) {
        this.ID = ID == null ? null : ID.trim();
    }

    public String getBisID() {
        return bisID == null ? "" : bisID.trim();
    }

    public void setBisID(String bisID) {
        this.bisID = bisID == null ? null : bisID.trim();
    }

    public String getDatumID() {
        return datumID == null ? "" : datumID.trim();
    }

    public void setDatumID(String datumID) {
        this.datumID = datumID == null ? null : datumID.trim();
    }

    public String getCustomerNo() {
        return customerNo == null ? "" : customerNo.trim();
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getQaName() {
        return qaName == null ? "" : qaName.trim();
    }

    public void setQaName(String qaName) {
        this.qaName = qaName == null ? null : qaName.trim();
    }

    public String getQuLevel() {
        return quLevel == null ? "" : quLevel.trim();
    }

    public void setQuLevel(String quLevel) {
        this.quLevel = quLevel == null ? null : quLevel.trim();
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

    public String getOrderStr() {
        return orderStr == null ? "" : orderStr.trim();
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr == null ? null : orderStr.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getQaID() {
        return qaID == null ? "" : qaID.trim();
    }

    public void setQaID(String qaID) {
        this.qaID = qaID == null ? null : qaID.trim();
    }

    public String getQuID() {
        return quID == null ? "" : quID.trim();
    }

    public void setQuID(String quID) {
        this.quID = quID == null ? null : quID.trim();
    }
}
