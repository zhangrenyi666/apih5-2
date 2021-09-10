package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZxEqMoveUseOrgItem extends BasePojo {
    private String id;

    private String moveID;

    private String equipID;

    private Date acceptDate;

    private String consignee;

    private String outTransactor;

    private String techCheckup;

    private String balUnit;

    private BigDecimal balPrice;

    private String remark;

    private BigDecimal orginalValue;

    private BigDecimal leftValue;

    private String startPlace;

    private Date startDate;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String equipNo;

    private String equipName;

    private String financeNo;

    private String spec;

    private String model;

    private String factory;

    private Date outFactoryDate;
    
    private String transferNo;
    private Date movedate;
    private String outOrgName;
    private String acceptOrgName;
    private String reason;
    private String careinfo;
    private String adminLeader;
    private String equipAdmin;
    private String finance;
    private String lister;
    private String outFactoryDateStr;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMoveID() {
        return moveID == null ? "" : moveID.trim();
    }

    public void setMoveID(String moveID) {
        this.moveID = moveID == null ? null : moveID.trim();
    }

    public String getEquipID() {
        return equipID == null ? "" : equipID.trim();
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID == null ? null : equipID.trim();
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public String getConsignee() {
        return consignee == null ? "" : consignee.trim();
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getOutTransactor() {
        return outTransactor == null ? "" : outTransactor.trim();
    }

    public void setOutTransactor(String outTransactor) {
        this.outTransactor = outTransactor == null ? null : outTransactor.trim();
    }

    public String getTechCheckup() {
        return techCheckup == null ? "" : techCheckup.trim();
    }

    public void setTechCheckup(String techCheckup) {
        this.techCheckup = techCheckup == null ? null : techCheckup.trim();
    }

    public String getBalUnit() {
        return balUnit == null ? "" : balUnit.trim();
    }

    public void setBalUnit(String balUnit) {
        this.balUnit = balUnit == null ? null : balUnit.trim();
    }

    public BigDecimal getBalPrice() {
        return balPrice;
    }

    public void setBalPrice(BigDecimal balPrice) {
        this.balPrice = balPrice;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(BigDecimal orginalValue) {
        this.orginalValue = orginalValue;
    }

    public BigDecimal getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(BigDecimal leftValue) {
        this.leftValue = leftValue;
    }

    public String getStartPlace() {
        return startPlace == null ? "" : startPlace.trim();
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace == null ? null : startPlace.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getFinanceNo() {
        return financeNo == null ? "" : financeNo.trim();
    }

    public void setFinanceNo(String financeNo) {
        this.financeNo = financeNo == null ? null : financeNo.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getModel() {
        return model == null ? "" : model.trim();
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getFactory() {
        return factory == null ? "" : factory.trim();
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

	public Date getOutFactoryDate() {
		return outFactoryDate;
	}

	public void setOutFactoryDate(Date outFactoryDate) {
		this.outFactoryDate = outFactoryDate;
	}

	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	public Date getMovedate() {
		return movedate;
	}

	public void setMovedate(Date movedate) {
		this.movedate = movedate;
	}

	public String getOutOrgName() {
		return outOrgName;
	}

	public void setOutOrgName(String outOrgName) {
		this.outOrgName = outOrgName;
	}

	public String getAcceptOrgName() {
		return acceptOrgName;
	}

	public void setAcceptOrgName(String acceptOrgName) {
		this.acceptOrgName = acceptOrgName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCareinfo() {
		return careinfo;
	}

	public void setCareinfo(String careinfo) {
		this.careinfo = careinfo;
	}

	public String getAdminLeader() {
		return adminLeader;
	}

	public void setAdminLeader(String adminLeader) {
		this.adminLeader = adminLeader;
	}

	public String getEquipAdmin() {
		return equipAdmin;
	}

	public void setEquipAdmin(String equipAdmin) {
		this.equipAdmin = equipAdmin;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	public String getLister() {
		return lister;
	}

	public void setLister(String lister) {
		this.lister = lister;
	}

	public String getOutFactoryDateStr() {
		return outFactoryDateStr;
	}

	public void setOutFactoryDateStr(String outFactoryDateStr) {
		this.outFactoryDateStr = outFactoryDateStr;
	}
	
}