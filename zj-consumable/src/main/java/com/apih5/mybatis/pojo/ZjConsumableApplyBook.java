package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjConsumableApplyBook extends BasePojo {
    private String applyBookId;

    private String setId;

    private String applyId;

    private String deptName;

    private String name;

    private Date appDate;

    private String category;

    private String brand;

    private String model;

    private String colour;

    private Integer applyNum;

    private String status;

    private Date applyDate;

    private String useStatus;

    private Date useDate;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<Date> appDateSearch;
    
    private List<Date> applyDateSearch;
    
    private List<Date> useDateSearch;
    
    private Date appStartTime;
    
    private Date appEndTime;
    
    private Date applyStartTime;
    
    private Date applyEndTime;
    
    private Date useStartTime;
    
    private Date useEndTime;
    
    private String appDateStr;
    
    private String applyDateStr;
    
    private String useDateStr;

    public String getApplyBookId() {
        return applyBookId == null ? "" : applyBookId.trim();
    }

    public void setApplyBookId(String applyBookId) {
        this.applyBookId = applyBookId == null ? null : applyBookId.trim();
    }

    public String getSetId() {
        return setId == null ? "" : setId.trim();
    }

    public void setSetId(String setId) {
        this.setId = setId == null ? null : setId.trim();
    }

    public String getApplyId() {
        return applyId == null ? "" : applyId.trim();
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getDeptName() {
        return deptName == null ? "" : deptName.trim();
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getCategory() {
        return category == null ? "" : category.trim();
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getBrand() {
        return brand == null ? "" : brand.trim();
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getModel() {
        return model == null ? "" : model.trim();
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getColour() {
        return colour == null ? "" : colour.trim();
    }

    public void setColour(String colour) {
        this.colour = colour == null ? null : colour.trim();
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getUseStatus() {
        return useStatus == null ? "" : useStatus.trim();
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus == null ? null : useStatus.trim();
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
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

	public List<Date> getAppDateSearch() {
		return appDateSearch;
	}

	public void setAppDateSearch(List<Date> appDateSearch) {
		this.appDateSearch = appDateSearch;
	}

	public List<Date> getApplyDateSearch() {
		return applyDateSearch;
	}

	public void setApplyDateSearch(List<Date> applyDateSearch) {
		this.applyDateSearch = applyDateSearch;
	}

	public List<Date> getUseDateSearch() {
		return useDateSearch;
	}

	public void setUseDateSearch(List<Date> useDateSearch) {
		this.useDateSearch = useDateSearch;
	}

	public Date getAppStartTime() {
		return appStartTime;
	}

	public void setAppStartTime(Date appStartTime) {
		this.appStartTime = appStartTime;
	}

	public Date getAppEndTime() {
		return appEndTime;
	}

	public void setAppEndTime(Date appEndTime) {
		this.appEndTime = appEndTime;
	}

	public Date getApplyStartTime() {
		return applyStartTime;
	}

	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}

	public Date getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public Date getUseStartTime() {
		return useStartTime;
	}

	public void setUseStartTime(Date useStartTime) {
		this.useStartTime = useStartTime;
	}

	public Date getUseEndTime() {
		return useEndTime;
	}

	public void setUseEndTime(Date useEndTime) {
		this.useEndTime = useEndTime;
	}

	public String getAppDateStr() {
		return appDateStr;
	}

	public void setAppDateStr(String appDateStr) {
		this.appDateStr = appDateStr;
	}

	public String getApplyDateStr() {
		return applyDateStr;
	}

	public void setApplyDateStr(String applyDateStr) {
		this.applyDateStr = applyDateStr;
	}

	public String getUseDateStr() {
		return useDateStr;
	}

	public void setUseDateStr(String useDateStr) {
		this.useDateStr = useDateStr;
	}

}