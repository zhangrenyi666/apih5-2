package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtContractContrastRpt extends BasePojo {
    // 主键
    private String zxCtContractContrastRptId;

    // 项目名称
    private String firstName;

    // 合同编号
    private String contractNo;

    // 供货单位
    private String customerName;

    // 供货单位
    private String secondName;

    // 物资大类
    private String workType;

    // 物资编码
    private String workNo;

    // 物资名称
    private String workName;

    // 规格型号
    private String spec;

    // 计量单位
    private String unit;

    // 物资合同单价
    private BigDecimal price;

    // 物资合同数量
    private BigDecimal qty;

    // 物资合同金额
    private BigDecimal contractSum;

    // 物资采购单价
    private BigDecimal inPrice;

    // 物资采购数量
    private BigDecimal inQty;

    // 物资采购金额
    private BigDecimal inAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

	private String supplyUnit;
    
    private String resTypeID;
    
    private String resID;
    
    private String orgID;
    
    public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getSupplyUnit() {
		return supplyUnit;
	}

	public void setSupplyUnit(String supplyUnit) {
		this.supplyUnit = supplyUnit;
	}

	public String getResTypeID() {
		return resTypeID;
	}

	public void setResTypeID(String resTypeID) {
		this.resTypeID = resTypeID;
	}

	public String getResID() {
		return resID;
	}

	public void setResID(String resID) {
		this.resID = resID;
	}

    public String getZxCtContractContrastRptId() {
        return zxCtContractContrastRptId == null ? "" : zxCtContractContrastRptId.trim();
    }

    public void setZxCtContractContrastRptId(String zxCtContractContrastRptId) {
        this.zxCtContractContrastRptId = zxCtContractContrastRptId == null ? null : zxCtContractContrastRptId.trim();
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName.trim();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getCustomerName() {
        return customerName == null ? "" : customerName.trim();
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getSecondName() {
        return secondName == null ? "" : secondName.trim();
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName == null ? null : secondName.trim();
    }

    public String getWorkType() {
        return workType == null ? "" : workType.trim();
    }

    public void setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
    }

    public String getWorkNo() {
        return workNo == null ? "" : workNo.trim();
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo == null ? null : workNo.trim();
    }

    public String getWorkName() {
        return workName == null ? "" : workName.trim();
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getContractSum() {
        return contractSum;
    }

    public void setContractSum(BigDecimal contractSum) {
        this.contractSum = contractSum;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    public BigDecimal getInQty() {
        return inQty;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getInAmt() {
        return inAmt;
    }

    public void setInAmt(BigDecimal inAmt) {
        this.inAmt = inAmt;
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
