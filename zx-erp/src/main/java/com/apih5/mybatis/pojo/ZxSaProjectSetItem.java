package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZxSaProjectSetItem extends BasePojo {
    private String id;

    private String mainID;

    private String contrType;

    private String orgID;

    private String workNo;

    private String workType;

    private String workName;

    private String unit;

    private String remark;

    private String orderNum;

    private String comID;

    private String comName;

    private String comOrders;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private int quoteFlag1;
    private int quoteFlag2;
    private int quoteFlag3;
    private int quoteFlag4;
    
    
    private String contrTypeFlag1;
    
    private String contrTypeFlag2;
    
    private String contrTypeFlag3;
    
    private String contrTypeFlag4;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getWorkNo() {
        return workNo == null ? "" : workNo.trim();
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo == null ? null : workNo.trim();
    }

    public String getWorkType() {
        return workType == null ? "" : workType.trim();
    }

    public void setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
    }

    public String getWorkName() {
        return workName == null ? "" : workName.trim();
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOrderNum() {
        return orderNum == null ? "" : orderNum.trim();
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
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

	public int getQuoteFlag1() {
		return quoteFlag1;
	}

	public void setQuoteFlag1(int quoteFlag1) {
		this.quoteFlag1 = quoteFlag1;
	}

	public int getQuoteFlag2() {
		return quoteFlag2;
	}

	public void setQuoteFlag2(int quoteFlag2) {
		this.quoteFlag2 = quoteFlag2;
	}

	public int getQuoteFlag3() {
		return quoteFlag3;
	}

	public void setQuoteFlag3(int quoteFlag3) {
		this.quoteFlag3 = quoteFlag3;
	}

	public int getQuoteFlag4() {
		return quoteFlag4;
	}

	public void setQuoteFlag4(int quoteFlag4) {
		this.quoteFlag4 = quoteFlag4;
	}

	public String getContrTypeFlag1() {
		return contrTypeFlag1;
	}

	public void setContrTypeFlag1(String contrTypeFlag1) {
		this.contrTypeFlag1 = contrTypeFlag1;
	}

	public String getContrTypeFlag2() {
		return contrTypeFlag2;
	}

	public void setContrTypeFlag2(String contrTypeFlag2) {
		this.contrTypeFlag2 = contrTypeFlag2;
	}

	public String getContrTypeFlag3() {
		return contrTypeFlag3;
	}

	public void setContrTypeFlag3(String contrTypeFlag3) {
		this.contrTypeFlag3 = contrTypeFlag3;
	}

	public String getContrTypeFlag4() {
		return contrTypeFlag4;
	}

	public void setContrTypeFlag4(String contrTypeFlag4) {
		this.contrTypeFlag4 = contrTypeFlag4;
	}
    
}