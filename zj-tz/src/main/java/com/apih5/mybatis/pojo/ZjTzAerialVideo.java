package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzAerialVideo extends BasePojo {
    private String aerialVideo;

    private Integer sort;

    private String companyId;

    private String companyName;

    private String projectId;

    private String projectShortName;

    private String projectName;

    private String proNo;

    private String keyFlag;

    private Date yearDate;

    private String yearStr;

    private String issue;

    private String videoName;

    private String videoPreviewUrl;

    private String videoDownUrl;

    private String videoSize;

    private String videoType;

    private String videoUid;

    private String imageName;

    private String imageUrl;

    private String size;

    private String type;

    private String thumbUrl;

    private String mobileUrl;

    private String mobileThumbUrl;

    private String relativeUrl;

    private String relativeThumbUrl;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<JSONObject> imageList;

    private List<JSONObject> videoList;

    private String projectIdSql;
    
    private String companySort;
    //新增
    private String proId;
    
    public String getAerialVideo() {
        return aerialVideo == null ? "" : aerialVideo.trim();
    }

    public void setAerialVideo(String aerialVideo) {
        this.aerialVideo = aerialVideo == null ? null : aerialVideo.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectShortName() {
        return projectShortName == null ? "" : projectShortName.trim();
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName == null ? null : projectShortName.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProNo() {
        return proNo == null ? "" : proNo.trim();
    }

    public void setProNo(String proNo) {
        this.proNo = proNo == null ? null : proNo.trim();
    }

    public String getKeyFlag() {
        return keyFlag == null ? "" : keyFlag.trim();
    }

    public void setKeyFlag(String keyFlag) {
        this.keyFlag = keyFlag == null ? null : keyFlag.trim();
    }

    public Date getYearDate() {
        return yearDate;
    }

    public void setYearDate(Date yearDate) {
        this.yearDate = yearDate;
    }

    public String getYearStr() {
        return yearStr == null ? "" : yearStr.trim();
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr == null ? null : yearStr.trim();
    }

    public String getIssue() {
        return issue == null ? "" : issue.trim();
    }

    public void setIssue(String issue) {
        this.issue = issue == null ? null : issue.trim();
    }

    public String getVideoName() {
        return videoName == null ? "" : videoName.trim();
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName == null ? null : videoName.trim();
    }

    public String getVideoPreviewUrl() {
        return videoPreviewUrl == null ? "" : videoPreviewUrl.trim();
    }

    public void setVideoPreviewUrl(String videoPreviewUrl) {
        this.videoPreviewUrl = videoPreviewUrl == null ? null : videoPreviewUrl.trim();
    }

    public String getVideoDownUrl() {
        return videoDownUrl == null ? "" : videoDownUrl.trim();
    }

    public void setVideoDownUrl(String videoDownUrl) {
        this.videoDownUrl = videoDownUrl == null ? null : videoDownUrl.trim();
    }

    public String getVideoSize() {
        return videoSize == null ? "" : videoSize.trim();
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize == null ? null : videoSize.trim();
    }

    public String getVideoType() {
        return videoType == null ? "" : videoType.trim();
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType == null ? null : videoType.trim();
    }

    public String getVideoUid() {
        return videoUid == null ? "" : videoUid.trim();
    }

    public void setVideoUid(String videoUid) {
        this.videoUid = videoUid == null ? null : videoUid.trim();
    }

    public String getImageName() {
        return imageName == null ? "" : imageName.trim();
    }

    public void setImageName(String imageName) {
        this.imageName = imageName == null ? null : imageName.trim();
    }

    public String getImageUrl() {
        return imageUrl == null ? "" : imageUrl.trim();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getSize() {
        return size == null ? "" : size.trim();
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getThumbUrl() {
        return thumbUrl == null ? "" : thumbUrl.trim();
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl == null ? null : thumbUrl.trim();
    }

    public String getMobileUrl() {
        return mobileUrl == null ? "" : mobileUrl.trim();
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl == null ? null : mobileUrl.trim();
    }

    public String getMobileThumbUrl() {
        return mobileThumbUrl == null ? "" : mobileThumbUrl.trim();
    }

    public void setMobileThumbUrl(String mobileThumbUrl) {
        this.mobileThumbUrl = mobileThumbUrl == null ? null : mobileThumbUrl.trim();
    }

    public String getRelativeUrl() {
        return relativeUrl == null ? "" : relativeUrl.trim();
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl == null ? null : relativeUrl.trim();
    }

    public String getRelativeThumbUrl() {
        return relativeThumbUrl == null ? "" : relativeThumbUrl.trim();
    }

    public void setRelativeThumbUrl(String relativeThumbUrl) {
        this.relativeThumbUrl = relativeThumbUrl == null ? null : relativeThumbUrl.trim();
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

    public List<JSONObject> getImageList() {
        return imageList;
    }

    public void setImageList(List<JSONObject> imageList) {
        this.imageList = imageList;
    }

    public List<JSONObject> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<JSONObject> videoList) {
        this.videoList = videoList;
    }

    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

	public String getCompanySort() {
		return companySort;
	}

	public void setCompanySort(String companySort) {
		this.companySort = companySort;
	}

	public String getProId() {
		return proId == null ? "" : proId.trim();
	}

	public void setProId(String proId) {
		this.proId = proId == null ? null : proId.trim();
	}

}

