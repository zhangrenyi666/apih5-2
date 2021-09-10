package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCmDatumDetail extends BasePojo {
    // 主键
    private String cmDatumDetailId;

    // 主表id
    private String masterID;

    // 子系统
    private String subSystem;

    // 模块名
    private String moduleName;

    // saveDateStr
    private String saveDateStr;

    // 附件大小
    private BigDecimal fileSize;

    // 附件名
    private String fileName;

    // 是否删除
    private int deleted=0;

    // 最后编辑时间
    private Date editTime;

    // 请求地址
    private String url;

    // 标识
    private String identifyStr;

    // 分公司ID
    private String comID;

    // 分公司名称
    private String comName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getCmDatumDetailId() {
        return cmDatumDetailId == null ? "" : cmDatumDetailId.trim();
    }

    public void setCmDatumDetailId(String cmDatumDetailId) {
        this.cmDatumDetailId = cmDatumDetailId == null ? null : cmDatumDetailId.trim();
    }

    public String getMasterID() {
        return masterID == null ? "" : masterID.trim();
    }

    public void setMasterID(String masterID) {
        this.masterID = masterID == null ? null : masterID.trim();
    }

    public String getSubSystem() {
        return subSystem == null ? "" : subSystem.trim();
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem == null ? null : subSystem.trim();
    }

    public String getModuleName() {
        return moduleName == null ? "" : moduleName.trim();
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getSaveDateStr() {
        return saveDateStr == null ? "" : saveDateStr.trim();
    }

    public void setSaveDateStr(String saveDateStr) {
        this.saveDateStr = saveDateStr == null ? null : saveDateStr.trim();
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName == null ? "" : fileName.trim();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getUrl() {
        return url == null ? "" : url.trim();
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIdentifyStr() {
        return identifyStr == null ? "" : identifyStr.trim();
    }

    public void setIdentifyStr(String identifyStr) {
        this.identifyStr = identifyStr == null ? null : identifyStr.trim();
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

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
