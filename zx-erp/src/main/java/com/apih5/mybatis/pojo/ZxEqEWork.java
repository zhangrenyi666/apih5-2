package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

/**
 * @author zx01
 *
 */
public class ZxEqEWork extends BasePojo {
    private String id;

    private String orgName;

    private String orgID;

    private Date bizDate;

    private String billNo;

    private String equipID;

    private String equipName;

    private String equipNo;

    private String resCatalogID;

    private String resCatalogName;

    private String spec;

    private String status;

    private String remark;

    private String comID;

    private String comName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxEqEWorkItem> itemList;

    private List<ZxErpFile> fileList;
    //报表字段
    private BigDecimal calendarNumDay;
    private BigDecimal wellDays;
    private String wellPercen;
    private BigDecimal runDay;
    private BigDecimal runHour;
    private String runDayPercen;
    private BigDecimal loadMiles;
    private BigDecimal gasoline;
    private BigDecimal diesel;
    private BigDecimal consumption;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
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

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getResCatalogID() {
        return resCatalogID == null ? "" : resCatalogID.trim();
    }

    public void setResCatalogID(String resCatalogID) {
        this.resCatalogID = resCatalogID == null ? null : resCatalogID.trim();
    }

    public String getResCatalogName() {
        return resCatalogName == null ? "" : resCatalogName.trim();
    }

    public void setResCatalogName(String resCatalogName) {
        this.resCatalogName = resCatalogName == null ? null : resCatalogName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public List<ZxEqEWorkItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ZxEqEWorkItem> itemList) {
        this.itemList = itemList;
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

	public BigDecimal getCalendarNumDay() {
		return calendarNumDay;
	}

	public void setCalendarNumDay(BigDecimal calendarNumDay) {
		this.calendarNumDay = calendarNumDay;
	}

	public BigDecimal getWellDays() {
		return wellDays;
	}

	public void setWellDays(BigDecimal wellDays) {
		this.wellDays = wellDays;
	}

	public String getWellPercen() {
		return wellPercen;
	}

	public void setWellPercen(String wellPercen) {
		this.wellPercen = wellPercen;
	}

	public BigDecimal getRunDay() {
		return runDay;
	}

	public void setRunDay(BigDecimal runDay) {
		this.runDay = runDay;
	}

	public BigDecimal getRunHour() {
		return runHour;
	}

	public void setRunHour(BigDecimal runHour) {
		this.runHour = runHour;
	}

	public String getRunDayPercen() {
		return runDayPercen;
	}

	public void setRunDayPercen(String runDayPercen) {
		this.runDayPercen = runDayPercen;
	}

	public BigDecimal getLoadMiles() {
		return loadMiles;
	}

	public void setLoadMiles(BigDecimal loadMiles) {
		this.loadMiles = loadMiles;
	}

	public BigDecimal getGasoline() {
		return gasoline;
	}

	public void setGasoline(BigDecimal gasoline) {
		this.gasoline = gasoline;
	}

	public BigDecimal getDiesel() {
		return diesel;
	}

	public void setDiesel(BigDecimal diesel) {
		this.diesel = diesel;
	}

	public BigDecimal getConsumption() {
		return consumption;
	}

	public void setConsumption(BigDecimal consumption) {
		this.consumption = consumption;
	}
    
}