package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzPartManage extends BasePojo {
    private String partManageId;

    private String designFlowId;

    private String designFlowTempId;

    private String designFlowTempName;

    private Integer orderNum;

    private String designPartId;

    private String designPartName;

    private String bidPartId;

    private String bidPartName;

    private String buId;

    private String buName;

    private Date planDate;

    private Date actualDate;

    private String fileFlag;

    private String updateFlag;

    private String lockFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private String codeId;

    private String colorFlag;
    
    private List<ZjTzPartManage> zjTzPartManageList;

    // 新增字段：是否更新
    private String renew;
    
    public String getPartManageId() {
        return partManageId == null ? "" : partManageId.trim();
    }

    public void setPartManageId(String partManageId) {
        this.partManageId = partManageId == null ? null : partManageId.trim();
    }

    public String getDesignFlowId() {
        return designFlowId == null ? "" : designFlowId.trim();
    }

    public void setDesignFlowId(String designFlowId) {
        this.designFlowId = designFlowId == null ? null : designFlowId.trim();
    }

    public String getDesignFlowTempId() {
        return designFlowTempId == null ? "" : designFlowTempId.trim();
    }

    public void setDesignFlowTempId(String designFlowTempId) {
        this.designFlowTempId = designFlowTempId == null ? null : designFlowTempId.trim();
    }

    public String getDesignFlowTempName() {
        return designFlowTempName == null ? "" : designFlowTempName.trim();
    }

    public void setDesignFlowTempName(String designFlowTempName) {
        this.designFlowTempName = designFlowTempName == null ? null : designFlowTempName.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getDesignPartId() {
        return designPartId == null ? "" : designPartId.trim();
    }

    public void setDesignPartId(String designPartId) {
        this.designPartId = designPartId == null ? null : designPartId.trim();
    }

    public String getDesignPartName() {
        return designPartName == null ? "" : designPartName.trim();
    }

    public void setDesignPartName(String designPartName) {
        this.designPartName = designPartName == null ? null : designPartName.trim();
    }

    public String getBidPartId() {
        return bidPartId == null ? "" : bidPartId.trim();
    }

    public void setBidPartId(String bidPartId) {
        this.bidPartId = bidPartId == null ? null : bidPartId.trim();
    }

    public String getBidPartName() {
        return bidPartName == null ? "" : bidPartName.trim();
    }

    public void setBidPartName(String bidPartName) {
        this.bidPartName = bidPartName == null ? null : bidPartName.trim();
    }

    public String getBuId() {
        return buId == null ? "" : buId.trim();
    }

    public void setBuId(String buId) {
        this.buId = buId == null ? null : buId.trim();
    }

    public String getBuName() {
        return buName == null ? "" : buName.trim();
    }

    public void setBuName(String buName) {
        this.buName = buName == null ? null : buName.trim();
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public String getFileFlag() {
        return fileFlag == null ? "" : fileFlag.trim();
    }

    public void setFileFlag(String fileFlag) {
        this.fileFlag = fileFlag == null ? null : fileFlag.trim();
    }

    public String getUpdateFlag() {
        return updateFlag == null ? "" : updateFlag.trim();
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag == null ? null : updateFlag.trim();
    }

    public String getLockFlag() {
        return lockFlag == null ? "" : lockFlag.trim();
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag == null ? null : lockFlag.trim();
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

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }

    public String getCodeId() {
        return codeId == null ? "" : codeId.trim();
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId == null ? null : codeId.trim();
    }

    public String getColorFlag() {
        return colorFlag == null ? "" : colorFlag.trim();
    }

    public void setColorFlag(String colorFlag) {
        this.colorFlag = colorFlag == null ? null : colorFlag.trim();
    }

	public List<ZjTzPartManage> getZjTzPartManageList() {
		return zjTzPartManageList;
	}

	public void setZjTzPartManageList(List<ZjTzPartManage> zjTzPartManageList) {
		this.zjTzPartManageList = zjTzPartManageList;
	}

	public String getRenew() {
		return renew;
	}

	public void setRenew(String renew) {
		this.renew = renew;
	}

}