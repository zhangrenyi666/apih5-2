package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrCustomerInfoDatum extends BasePojo {
    // 主键
    private String zxCrCustomerInfoDatumId;

    // 关联业务id
    private String bisID;

    // 名称
    private String datumName;

    // 编号
    private String datumNo;

    // 附件id
    private String datumID;

    // 编辑时间
    private Date editTime;

    // comID
    private String comID;

    // comName
    private String comName;

    // comOrders
    private String comOrders;

    // 排序
    private String orderStr;

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

	public String getZxCrCustomerInfoDatumId() {
        return zxCrCustomerInfoDatumId == null ? "" : zxCrCustomerInfoDatumId.trim();
    }

    public void setZxCrCustomerInfoDatumId(String zxCrCustomerInfoDatumId) {
        this.zxCrCustomerInfoDatumId = zxCrCustomerInfoDatumId == null ? null : zxCrCustomerInfoDatumId.trim();
    }

    public String getBisID() {
        return bisID == null ? "" : bisID.trim();
    }

    public void setBisID(String bisID) {
        this.bisID = bisID == null ? null : bisID.trim();
    }

    public String getDatumName() {
        return datumName == null ? "" : datumName.trim();
    }

    public void setDatumName(String datumName) {
        this.datumName = datumName == null ? null : datumName.trim();
    }

    public String getDatumNo() {
        return datumNo == null ? "" : datumNo.trim();
    }

    public void setDatumNo(String datumNo) {
        this.datumNo = datumNo == null ? null : datumNo.trim();
    }

    public String getDatumID() {
        return datumID == null ? "" : datumID.trim();
    }

    public void setDatumID(String datumID) {
        this.datumID = datumID == null ? null : datumID.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getOrderStr() {
        return orderStr == null ? "" : orderStr.trim();
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr == null ? null : orderStr.trim();
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
