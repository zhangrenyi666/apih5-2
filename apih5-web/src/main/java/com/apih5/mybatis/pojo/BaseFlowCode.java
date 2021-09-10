package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class BaseFlowCode extends BasePojo {
    private String flowCodeId;

    private String apih5FlowId;

    private String apih5FlowName;

    private String apih5NodeId;

    private String apih5NodeName;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String thumbUrl;
    
    private String relativeUrl;
    
    private List<BaseFlowCode> xmlFileList;
    
    private List<BaseFlowCode> baseFlowCodeList;
    
    private List<BaseFlowCode> baseFlowCodeList1;

    public String getFlowCodeId() {
        return flowCodeId == null ? "" : flowCodeId.trim();
    }

    public void setFlowCodeId(String flowCodeId) {
        this.flowCodeId = flowCodeId == null ? null : flowCodeId.trim();
    }

    public String getApih5FlowId() {
        return apih5FlowId == null ? "" : apih5FlowId.trim();
    }

    public void setApih5FlowId(String apih5FlowId) {
        this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
    }

    public String getApih5FlowName() {
        return apih5FlowName == null ? "" : apih5FlowName.trim();
    }

    public void setApih5FlowName(String apih5FlowName) {
        this.apih5FlowName = apih5FlowName == null ? null : apih5FlowName.trim();
    }

    public String getApih5NodeId() {
        return apih5NodeId == null ? "" : apih5NodeId.trim();
    }

    public void setApih5NodeId(String apih5NodeId) {
        this.apih5NodeId = apih5NodeId == null ? null : apih5NodeId.trim();
    }

    public String getApih5NodeName() {
        return apih5NodeName == null ? "" : apih5NodeName.trim();
    }

    public void setApih5NodeName(String apih5NodeName) {
        this.apih5NodeName = apih5NodeName == null ? null : apih5NodeName.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public List<BaseFlowCode> getXmlFileList() {
		return xmlFileList;
	}

	public void setXmlFileList(List<BaseFlowCode> xmlFileList) {
		this.xmlFileList = xmlFileList;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public List<BaseFlowCode> getBaseFlowCodeList() {
		return baseFlowCodeList;
	}

	public void setBaseFlowCodeList(List<BaseFlowCode> baseFlowCodeList) {
		this.baseFlowCodeList = baseFlowCodeList;
	}

	public List<BaseFlowCode> getBaseFlowCodeList1() {
		return baseFlowCodeList1;
	}

	public void setBaseFlowCodeList1(List<BaseFlowCode> baseFlowCodeList1) {
		this.baseFlowCodeList1 = baseFlowCodeList1;
	}

}

