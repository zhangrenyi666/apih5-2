package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

import cn.hutool.json.JSONObject;

public class ZjTzVideo extends BasePojo {
    private String videoId;

    private String title;

    private String releaseRankId;

    private String releaseRankName;

    private Date releaseDate;

    private String keynoteSpeaker;

    private String content;

    private Date registeredDate;

    private String homeShow;

    private Date homeShowTime;

    private Date homeShowStartDate;

    private Date homeShowEndDate;

    private String statusId;

    private String statusName;

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

    private List<ZjTzFile> zjTzCoverList;
    
    private String videoUrl;
    
    private List<JSONObject> imageList;

    private List<JSONObject> videoList;
//    
//    private List<JSONObject> zjTzFileList;
//
//    private List<JSONObject> zjTzCoverList;
    
    public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoId() {
        return videoId == null ? "" : videoId.trim();
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getReleaseRankId() {
        return releaseRankId == null ? "" : releaseRankId.trim();
    }

    public void setReleaseRankId(String releaseRankId) {
        this.releaseRankId = releaseRankId == null ? null : releaseRankId.trim();
    }

    public String getReleaseRankName() {
        return releaseRankName == null ? "" : releaseRankName.trim();
    }

    public void setReleaseRankName(String releaseRankName) {
        this.releaseRankName = releaseRankName == null ? null : releaseRankName.trim();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getKeynoteSpeaker() {
        return keynoteSpeaker == null ? "" : keynoteSpeaker.trim();
    }

    public void setKeynoteSpeaker(String keynoteSpeaker) {
        this.keynoteSpeaker = keynoteSpeaker == null ? null : keynoteSpeaker.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
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

    public String getStatusId() {
        return statusId == null ? "" : statusId.trim();
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId == null ? null : statusId.trim();
    }

    public String getStatusName() {
        return statusName == null ? "" : statusName.trim();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
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

//	public List<JSONObject> getZjTzFileList() {
//		return zjTzFileList;
//	}
//
//	public void setZjTzFileList(List<JSONObject> zjTzFileList) {
//		this.zjTzFileList = zjTzFileList;
//	}
//
//	public List<JSONObject> getZjTzCoverList() {
//		return zjTzCoverList;
//	}
//
//	public void setZjTzCoverList(List<JSONObject> zjTzCoverList) {
//		this.zjTzCoverList = zjTzCoverList;
//	}

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }

    public List<ZjTzFile> getZjTzCoverList() {
        return zjTzCoverList;
    }

    public void setZjTzCoverList(List<ZjTzFile> zjTzCoverList) {
        this.zjTzCoverList = zjTzCoverList;
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
    
}

