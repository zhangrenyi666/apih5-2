package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxSaProjectSet extends BasePojo {
    private String id;

    private String contrType;

    private String orgID;

    private String orgName;

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

    private List<ZxSaProjectSetItem> itemList;
    
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

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
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

    public List<ZxSaProjectSetItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ZxSaProjectSetItem> itemList) {
        this.itemList = itemList;
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

