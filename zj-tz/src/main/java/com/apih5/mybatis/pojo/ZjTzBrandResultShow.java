package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import cn.hutool.json.JSONObject;
import com.apih5.framework.entity.BasePojo;

public class ZjTzBrandResultShow extends BasePojo {
    private String resultShowId;

    private String title;

    private String comId;

    private String companyId;

    private String companyName;

    private String resultTypeId;

    private String resultTypeName;

    private String content;

    private String homeShow;

    private Date homeShowTime;

    private Date homeShowStartDate;

    private Date homeShowEndDate;

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

    private List<JSONObject> projectList;

    private ZjTzSpecialResultShow zjTzSpecialResultShow;

    private String resultUrl;
    // 区分标识
    private String flag;
    //新增字段
    //获得主体id
    private String getSubjectId;
    //成果级别id
    private String resultLevelId;
    //获得时间
    private Date getTime;
    //备注
    private String bz;
    //成果权属单位
    private String resultUnit;
    // manage表中管理单位
    private String manageUnit;
    
    public String getResultShowId() {
        return resultShowId == null ? "" : resultShowId.trim();
    }

    public void setResultShowId(String resultShowId) {
        this.resultShowId = resultShowId == null ? null : resultShowId.trim();
    }

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getComId() {
        return comId == null ? "" : comId.trim();
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getResultTypeId() {
        return resultTypeId == null ? "" : resultTypeId.trim();
    }

    public void setResultTypeId(String resultTypeId) {
        this.resultTypeId = resultTypeId == null ? null : resultTypeId.trim();
    }

    public String getResultTypeName() {
        return resultTypeName == null ? "" : resultTypeName.trim();
    }

    public void setResultTypeName(String resultTypeName) {
        this.resultTypeName = resultTypeName == null ? null : resultTypeName.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getHomeShow() {
        return homeShow == null ? "" : homeShow.trim();
    }

    public void setHomeShow(String homeShow) {
        this.homeShow = homeShow == null ? null : homeShow.trim();
    }

    public Date getHomeShowTime() {
        return homeShowTime;
    }

    public void setHomeShowTime(Date homeShowTime) {
        this.homeShowTime = homeShowTime;
    }

    public Date getHomeShowStartDate() {
        return homeShowStartDate;
    }

    public void setHomeShowStartDate(Date homeShowStartDate) {
        this.homeShowStartDate = homeShowStartDate;
    }

    public Date getHomeShowEndDate() {
        return homeShowEndDate;
    }

    public void setHomeShowEndDate(Date homeShowEndDate) {
        this.homeShowEndDate = homeShowEndDate;
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

    public List<JSONObject> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<JSONObject> projectList) {
        this.projectList = projectList;
    }

    public ZjTzSpecialResultShow getZjTzSpecialResultShow() {
		return zjTzSpecialResultShow;
	}

	public void setZjTzSpecialResultShow(ZjTzSpecialResultShow zjTzSpecialResultShow) {
		this.zjTzSpecialResultShow = zjTzSpecialResultShow;
	}

	public String getResultUrl() {
        return resultUrl == null ? "" : resultUrl.trim();
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl == null ? null : resultUrl.trim();
    }

	public String getFlag() {
		return flag == null ? "" : flag.trim();
	}

	public void setFlag(String flag) {
		this.flag = flag == null ? null : flag.trim();
	}

	public String getGetSubjectId() {
		return getSubjectId == null ? "" : getSubjectId.trim();
	}

	public void setGetSubjectId(String getSubjectId) {
		this.getSubjectId = getSubjectId == null ? null : getSubjectId.trim();
	}

	public String getResultLevelId() {
		return resultLevelId == null ? "" : resultLevelId.trim();
	}

	public void setResultLevelId(String resultLevelId) {
		this.resultLevelId = resultLevelId == null ? null : resultLevelId.trim();
	}

	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public String getBz() {
		return bz == null ? "" : bz.trim();
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}

	public String getResultUnit() {
		return resultUnit == null ? "" : resultUnit.trim();
	}

	public void setResultUnit(String resultUnit) {
		this.resultUnit = resultUnit == null ? null : resultUnit.trim();
	}

	public String getManageUnit() {
		return manageUnit == null ? "" : manageUnit.trim();
	}

	public void setManageUnit(String manageUnit) {
		this.manageUnit = manageUnit == null ? null : manageUnit.trim();
	}

}

