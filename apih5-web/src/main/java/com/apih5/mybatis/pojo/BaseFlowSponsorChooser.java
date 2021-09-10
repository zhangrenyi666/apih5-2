package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.apih5.framework.entity.TreeNodeEntity;

public class BaseFlowSponsorChooser extends BasePojo {
    private String sponsorChooserId;

    private String flowNodeCustom;

    private String apih5FlowId;

    private String startNodeId;

    private String endNodeId;

    private String sponsorIds;

    private String chooserIds;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String apih5FlowName;
    
    private String startNodeName;
    
    private String endNodeName;
    
    private List<TreeNodeEntity> launcherList;
    
    private List<TreeNodeEntity> candidateList;

    public String getSponsorChooserId() {
        return sponsorChooserId == null ? "" : sponsorChooserId.trim();
    }

    public void setSponsorChooserId(String sponsorChooserId) {
        this.sponsorChooserId = sponsorChooserId == null ? null : sponsorChooserId.trim();
    }

    public String getFlowNodeCustom() {
        return flowNodeCustom == null ? "" : flowNodeCustom.trim();
    }

    public void setFlowNodeCustom(String flowNodeCustom) {
        this.flowNodeCustom = flowNodeCustom == null ? null : flowNodeCustom.trim();
    }

    public String getApih5FlowId() {
        return apih5FlowId == null ? "" : apih5FlowId.trim();
    }

    public void setApih5FlowId(String apih5FlowId) {
        this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
    }

    public String getStartNodeId() {
        return startNodeId == null ? "" : startNodeId.trim();
    }

    public void setStartNodeId(String startNodeId) {
        this.startNodeId = startNodeId == null ? null : startNodeId.trim();
    }

    public String getEndNodeId() {
        return endNodeId == null ? "" : endNodeId.trim();
    }

    public void setEndNodeId(String endNodeId) {
        this.endNodeId = endNodeId == null ? null : endNodeId.trim();
    }

    public String getSponsorIds() {
        return sponsorIds == null ? "" : sponsorIds.trim();
    }

    public void setSponsorIds(String sponsorIds) {
        this.sponsorIds = sponsorIds == null ? null : sponsorIds.trim();
    }

    public String getChooserIds() {
        return chooserIds == null ? "" : chooserIds.trim();
    }

    public void setChooserIds(String chooserIds) {
        this.chooserIds = chooserIds == null ? null : chooserIds.trim();
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

	public List<TreeNodeEntity> getLauncherList() {
		return launcherList;
	}

	public void setLauncherList(List<TreeNodeEntity> launcherList) {
		this.launcherList = launcherList;
	}

	public List<TreeNodeEntity> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List<TreeNodeEntity> candidateList) {
		this.candidateList = candidateList;
	}

	public String getApih5FlowName() {
		return apih5FlowName;
	}

	public void setApih5FlowName(String apih5FlowName) {
		this.apih5FlowName = apih5FlowName;
	}

	public String getStartNodeName() {
		return startNodeName;
	}

	public void setStartNodeName(String startNodeName) {
		this.startNodeName = startNodeName;
	}

	public String getEndNodeName() {
		return endNodeName;
	}

	public void setEndNodeName(String endNodeName) {
		this.endNodeName = endNodeName;
	}

}

