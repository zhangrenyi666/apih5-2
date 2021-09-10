package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqMoveSupervisor extends BasePojo {
    private String id;

    private String billNo;

    private String outOrgID;

    private String outOrgName;

    private String inOrgID;

    private String inOrgName;

    private String reason;

    private Date busDate;

    private Date inDate;

    private String outman1;

    private String outman2;

    private String outman5;

    private String outman3;

    private String outman4;

    private String inman1;

    private String inman2;

    private String inman3;

    private String auditStatus;

    private String orderID;

    private String remark;

    private String serialNo;

    private String comID;

    private String comName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxEqMoveSupervisorItem> itemList;

    private String equipName;

    private String equipID;
    
    private String seeFlagForCom;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getOutOrgID() {
        return outOrgID == null ? "" : outOrgID.trim();
    }

    public void setOutOrgID(String outOrgID) {
        this.outOrgID = outOrgID == null ? null : outOrgID.trim();
    }

    public String getOutOrgName() {
        return outOrgName == null ? "" : outOrgName.trim();
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName == null ? null : outOrgName.trim();
    }

    public String getInOrgID() {
        return inOrgID == null ? "" : inOrgID.trim();
    }

    public void setInOrgID(String inOrgID) {
        this.inOrgID = inOrgID == null ? null : inOrgID.trim();
    }

    public String getInOrgName() {
        return inOrgName == null ? "" : inOrgName.trim();
    }

    public void setInOrgName(String inOrgName) {
        this.inOrgName = inOrgName == null ? null : inOrgName.trim();
    }

    public String getReason() {
        return reason == null ? "" : reason.trim();
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getBusDate() {
        return busDate;
    }

    public void setBusDate(Date busDate) {
        this.busDate = busDate;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getOutman1() {
        return outman1 == null ? "" : outman1.trim();
    }

    public void setOutman1(String outman1) {
        this.outman1 = outman1 == null ? null : outman1.trim();
    }

    public String getOutman2() {
        return outman2 == null ? "" : outman2.trim();
    }

    public void setOutman2(String outman2) {
        this.outman2 = outman2 == null ? null : outman2.trim();
    }

    public String getOutman5() {
        return outman5 == null ? "" : outman5.trim();
    }

    public void setOutman5(String outman5) {
        this.outman5 = outman5 == null ? null : outman5.trim();
    }

    public String getOutman3() {
        return outman3 == null ? "" : outman3.trim();
    }

    public void setOutman3(String outman3) {
        this.outman3 = outman3 == null ? null : outman3.trim();
    }

    public String getOutman4() {
        return outman4 == null ? "" : outman4.trim();
    }

    public void setOutman4(String outman4) {
        this.outman4 = outman4 == null ? null : outman4.trim();
    }

    public String getInman1() {
        return inman1 == null ? "" : inman1.trim();
    }

    public void setInman1(String inman1) {
        this.inman1 = inman1 == null ? null : inman1.trim();
    }

    public String getInman2() {
        return inman2 == null ? "" : inman2.trim();
    }

    public void setInman2(String inman2) {
        this.inman2 = inman2 == null ? null : inman2.trim();
    }

    public String getInman3() {
        return inman3 == null ? "" : inman3.trim();
    }

    public void setInman3(String inman3) {
        this.inman3 = inman3 == null ? null : inman3.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getOrderID() {
        return orderID == null ? "" : orderID.trim();
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID == null ? null : orderID.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSerialNo() {
        return serialNo == null ? "" : serialNo.trim();
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
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

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

    public List<ZxEqMoveSupervisorItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ZxEqMoveSupervisorItem> itemList) {
        this.itemList = itemList;
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getEquipID() {
        return equipID == null ? "" : equipID.trim();
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID == null ? null : equipID.trim();
    }

	public String getSeeFlagForCom() {
		return seeFlagForCom;
	}

	public void setSeeFlagForCom(String seeFlagForCom) {
		this.seeFlagForCom = seeFlagForCom;
	}

	
}

