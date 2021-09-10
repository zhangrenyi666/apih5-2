package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrCustomerInfoDire extends BasePojo {
    // 主键
    private String zxCrCustomerInfoDireId;

    // 关联业务id
    private String bisID;

    // 单位类别ID
    private String catID;

    // 单位工程代码
    private String catCode;

    // 单位工程
    private String catName;

    // 分类代码ID
    private String resID;

    // 分类代码
    private String resCode;

    // 分类名称
    private String resName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String zxCrCustomerInfoId;

    public String getZxCrCustomerInfoId() {
		return zxCrCustomerInfoId;
	}

	public void setZxCrCustomerInfoId(String zxCrCustomerInfoId) {
		this.zxCrCustomerInfoId = zxCrCustomerInfoId;
	}

	public String getZxCrCustomerInfoDireId() {
        return zxCrCustomerInfoDireId == null ? "" : zxCrCustomerInfoDireId.trim();
    }

    public void setZxCrCustomerInfoDireId(String zxCrCustomerInfoDireId) {
        this.zxCrCustomerInfoDireId = zxCrCustomerInfoDireId == null ? null : zxCrCustomerInfoDireId.trim();
    }

    public String getBisID() {
        return bisID == null ? "" : bisID.trim();
    }

    public void setBisID(String bisID) {
        this.bisID = bisID == null ? null : bisID.trim();
    }

    public String getCatID() {
        return catID == null ? "" : catID.trim();
    }

    public void setCatID(String catID) {
        this.catID = catID == null ? null : catID.trim();
    }

    public String getCatCode() {
        return catCode == null ? "" : catCode.trim();
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode == null ? null : catCode.trim();
    }

    public String getCatName() {
        return catName == null ? "" : catName.trim();
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
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
