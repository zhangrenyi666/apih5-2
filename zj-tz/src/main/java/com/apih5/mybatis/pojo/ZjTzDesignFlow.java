package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzDesignFlow extends BasePojo {
    private String designFlowId;

    private String projectId;

    private String projectName;

    private String subprojectInfoId;

    private String subprojectName;

    private String skillTypeId;

    private String skillTypeName;

    private String designPartId;

    private String designPartName;

    private String remarks;

    private String releaseId;

    private String releaseName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private String warnFlag;
    
    private List<ZjTzPartManage> zjTzPartManageList;
    
    private String projectIdSql;
    
    private String ext1SeeFlag;
    // 新增字段：是否更新
    private String ext1Flag1;
    
    private String ext1Flag2;
    
    private String ext1Flag3;
    
    private String renew1;
    
    private String renew2;
    
    private String renew3;
    
    private String renew4;
    public String getDesignFlowId() {
        return designFlowId == null ? "" : designFlowId.trim();
    }

    public void setDesignFlowId(String designFlowId) {
        this.designFlowId = designFlowId == null ? null : designFlowId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getSubprojectInfoId() {
        return subprojectInfoId == null ? "" : subprojectInfoId.trim();
    }

    public void setSubprojectInfoId(String subprojectInfoId) {
        this.subprojectInfoId = subprojectInfoId == null ? null : subprojectInfoId.trim();
    }

    public String getSubprojectName() {
        return subprojectName == null ? "" : subprojectName.trim();
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName == null ? null : subprojectName.trim();
    }

    public String getSkillTypeId() {
        return skillTypeId == null ? "" : skillTypeId.trim();
    }

    public void setSkillTypeId(String skillTypeId) {
        this.skillTypeId = skillTypeId == null ? null : skillTypeId.trim();
    }

    public String getSkillTypeName() {
        return skillTypeName == null ? "" : skillTypeName.trim();
    }

    public void setSkillTypeName(String skillTypeName) {
        this.skillTypeName = skillTypeName == null ? null : skillTypeName.trim();
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

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getReleaseId() {
        return releaseId == null ? "" : releaseId.trim();
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId == null ? null : releaseId.trim();
    }

    public String getReleaseName() {
        return releaseName == null ? "" : releaseName.trim();
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName == null ? null : releaseName.trim();
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

    public String getWarnFlag() {
        return warnFlag == null ? "" : warnFlag.trim();
    }

    public void setWarnFlag(String warnFlag) {
        this.warnFlag = warnFlag == null ? null : warnFlag.trim();
    }

    public List<ZjTzPartManage> getZjTzPartManageList() {
        return zjTzPartManageList;
    }

    public void setZjTzPartManageList(List<ZjTzPartManage> zjTzPartManageList) {
        this.zjTzPartManageList = zjTzPartManageList;
    }

    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
    public String getExt1SeeFlag() {
		return ext1SeeFlag == null ? "" : ext1SeeFlag.trim();
	}

	public void setExt1SeeFlag(String ext1SeeFlag) {
		this.ext1SeeFlag = ext1SeeFlag == null ? null : ext1SeeFlag.trim();
	}

	public String getExt1Flag1() {
		return ext1Flag1 == null ? "" : ext1Flag1.trim();
	}

	public void setExt1Flag1(String ext1Flag1) {
		this.ext1Flag1 = ext1Flag1 == null ? null : ext1Flag1.trim();
	}

	public String getExt1Flag2() {
		return ext1Flag2 == null ? "" : ext1Flag2.trim();
	}

	public void setExt1Flag2(String ext1Flag2) {
		this.ext1Flag2 = ext1Flag2 == null ? null : ext1Flag2.trim();
	}

	public String getExt1Flag3() {
		return ext1Flag3 == null ? "" : ext1Flag3.trim();
	}

	public void setExt1Flag3(String ext1Flag3) {
		this.ext1Flag3 = ext1Flag3 == null ? null : ext1Flag3.trim();
	}

	public String getRenew1() {
		return renew1 == null ? "" : renew1.trim();
	}

	public void setRenew1(String renew1) {
		this.renew1 = renew1 == null ? null : renew1.trim();
	}

	public String getRenew2() {
		return renew2 == null ? "" : renew2.trim();
	}

	public void setRenew2(String renew2) {
		this.renew2 = renew2 == null ? null : renew2.trim();
	}

	public String getRenew3() {
		return renew3 == null ? "" : renew3.trim();
	}

	public void setRenew3(String renew3) {
		this.renew3 = renew3 == null ? null : renew3.trim();
	}

	public String getRenew4() {
		return renew4 == null ? "" : renew4.trim();
	}

	public void setRenew4(String renew4) {
		this.renew4 = renew4 == null ? null : renew4.trim();
	}
	
}

