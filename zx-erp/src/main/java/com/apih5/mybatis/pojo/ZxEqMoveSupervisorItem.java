package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZxEqMoveSupervisorItem extends BasePojo {
    private String id;

    private String masID;

    private String equipID;

    private String equipName;

    private String financeNo;

    private String spec;

    private String factory;

    private Date outFactoryDate;

    private BigDecimal qty;

    private String unit;

    private BigDecimal orginalValue;

    private BigDecimal deprevalue;

    private BigDecimal leftvalue;

    private BigDecimal sellValue;

    private String remark;

    private Date editTime;

    private String newManageNo;

    private String model;

    private String equipNo;

    private String useOrgId;

    private String useOrgName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
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
    private String serialNo;

    
    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMasID() {
        return masID == null ? "" : masID.trim();
    }

    public void setMasID(String masID) {
        this.masID = masID == null ? null : masID.trim();
    }

    public String getEquipID() {
        return equipID == null ? "" : equipID.trim();
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID == null ? null : equipID.trim();
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

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(BigDecimal orginalValue) {
        this.orginalValue = orginalValue;
    }

    public BigDecimal getDeprevalue() {
        return deprevalue;
    }

    public void setDeprevalue(BigDecimal deprevalue) {
        this.deprevalue = deprevalue;
    }

    public BigDecimal getLeftvalue() {
        return leftvalue;
    }

    public void setLeftvalue(BigDecimal leftvalue) {
        this.leftvalue = leftvalue;
    }

    public BigDecimal getSellValue() {
        return sellValue;
    }

    public void setSellValue(BigDecimal sellValue) {
        this.sellValue = sellValue;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getNewManageNo() {
        return newManageNo == null ? "" : newManageNo.trim();
    }

    public void setNewManageNo(String newManageNo) {
        this.newManageNo = newManageNo == null ? null : newManageNo.trim();
    }

    public String getModel() {
        return model == null ? "" : model.trim();
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getUseOrgId() {
        return useOrgId == null ? "" : useOrgId.trim();
    }

    public void setUseOrgId(String useOrgId) {
        this.useOrgId = useOrgId == null ? null : useOrgId.trim();
    }

    public String getUseOrgName() {
        return useOrgName == null ? "" : useOrgName.trim();
    }

    public void setUseOrgName(String useOrgName) {
        this.useOrgName = useOrgName == null ? null : useOrgName.trim();
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

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getOutOrgID() {
		return outOrgID;
	}

	public void setOutOrgID(String outOrgID) {
		this.outOrgID = outOrgID;
	}

	public String getOutOrgName() {
		return outOrgName;
	}

	public void setOutOrgName(String outOrgName) {
		this.outOrgName = outOrgName;
	}

	public String getInOrgID() {
		return inOrgID;
	}

	public void setInOrgID(String inOrgID) {
		this.inOrgID = inOrgID;
	}

	public String getInOrgName() {
		return inOrgName;
	}

	public void setInOrgName(String inOrgName) {
		this.inOrgName = inOrgName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
		return outman1;
	}

	public void setOutman1(String outman1) {
		this.outman1 = outman1;
	}

	public String getOutman2() {
		return outman2;
	}

	public void setOutman2(String outman2) {
		this.outman2 = outman2;
	}

	public String getOutman5() {
		return outman5;
	}

	public void setOutman5(String outman5) {
		this.outman5 = outman5;
	}

	public String getOutman3() {
		return outman3;
	}

	public void setOutman3(String outman3) {
		this.outman3 = outman3;
	}

	public String getOutman4() {
		return outman4;
	}

	public void setOutman4(String outman4) {
		this.outman4 = outman4;
	}

	public String getInman1() {
		return inman1;
	}

	public void setInman1(String inman1) {
		this.inman1 = inman1;
	}

	public String getInman2() {
		return inman2;
	}

	public void setInman2(String inman2) {
		this.inman2 = inman2;
	}

	public String getInman3() {
		return inman3;
	}

	public void setInman3(String inman3) {
		this.inman3 = inman3;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
    
}

