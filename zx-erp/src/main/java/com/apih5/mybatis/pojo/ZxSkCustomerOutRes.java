package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkCustomerOutRes extends BasePojo {
    // 主键
    private String zxSkCustomerOutResId;

    // 项目名称
    private String projectName;

    // 供应商
    private String outOrgName;

    // 业务日期
    private Date busDate;

    // 单据编号
    private String billNo;

    // 物资大类
    private String resourceName;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 计量单位
    private String resUnit;

    // 数量
    private BigDecimal inQty;

    // 含税采购单价
    private BigDecimal inPrice;

    // 含税采购金额
    private BigDecimal resAllFee;

    // 不含税采购单价
    private BigDecimal inPriceNoTax;

    // 不含税采购金额
    private BigDecimal resAllFeeNoTax;

    // 入账金额
    private BigDecimal inAmt;

    // 市场来源
    private String asmaterialSource;

    // 是否预收
    private String precollecte;

    // 是否有合同
    private String purchType;

    // 合同编号
    private String contractNo;

    // 备注
    private String remarks;
    
    //项目ID
    private String orgID;

	private String outOrgID;
    
    private String appOrgID;
    
    // 开始时间
    private Date beginDate;
    
    //结束时间
    private Date endDate;
    
    private String resTypeID;
    
    private String secondResTypeID;
    
    private String resID;

    // 排序
    private int sort=0;
    
    public String getOrgID() {
 		return orgID;
 	}

 	public void setOrgID(String orgID) {
 		this.orgID = orgID;
 	}

 	public String getOutOrgID() {
 		return outOrgID;
 	}

 	public void setOutOrgID(String outOrgID) {
 		this.outOrgID = outOrgID;
 	}

 	public String getAppOrgID() {
 		return appOrgID;
 	}

 	public void setAppOrgID(String appOrgID) {
 		this.appOrgID = appOrgID;
 	}

 	public Date getBeginDate() {
 		return beginDate;
 	}

 	public void setBeginDate(Date beginDate) {
 		this.beginDate = beginDate;
 	}

 	public Date getEndDate() {
 		return endDate;
 	}

 	public void setEndDate(Date endDate) {
 		this.endDate = endDate;
 	}

 	public String getResTypeID() {
 		return resTypeID;
 	}

 	public void setResTypeID(String resTypeID) {
 		this.resTypeID = resTypeID;
 	}

 	public String getSecondResTypeID() {
 		return secondResTypeID;
 	}

 	public void setSecondResTypeID(String secondResTypeID) {
 		this.secondResTypeID = secondResTypeID;
 	}

 	public String getResID() {
 		return resID;
 	}

 	public void setResID(String resID) {
 		this.resID = resID;
 	}

    public String getZxSkCustomerOutResId() {
        return zxSkCustomerOutResId == null ? "" : zxSkCustomerOutResId.trim();
    }

    public void setZxSkCustomerOutResId(String zxSkCustomerOutResId) {
        this.zxSkCustomerOutResId = zxSkCustomerOutResId == null ? null : zxSkCustomerOutResId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getOutOrgName() {
        return outOrgName == null ? "" : outOrgName.trim();
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName == null ? null : outOrgName.trim();
    }

    public Date getBusDate() {
        return busDate;
    }

    public void setBusDate(Date busDate) {
        this.busDate = busDate;
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getResourceName() {
        return resourceName == null ? "" : resourceName.trim();
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
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

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getResUnit() {
        return resUnit == null ? "" : resUnit.trim();
    }

    public void setResUnit(String resUnit) {
        this.resUnit = resUnit == null ? null : resUnit.trim();
    }

    public BigDecimal getInQty() {
        return inQty;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    public BigDecimal getResAllFee() {
        return resAllFee;
    }

    public void setResAllFee(BigDecimal resAllFee) {
        this.resAllFee = resAllFee;
    }

    public BigDecimal getInPriceNoTax() {
        return inPriceNoTax;
    }

    public void setInPriceNoTax(BigDecimal inPriceNoTax) {
        this.inPriceNoTax = inPriceNoTax;
    }

    public BigDecimal getResAllFeeNoTax() {
        return resAllFeeNoTax;
    }

    public void setResAllFeeNoTax(BigDecimal resAllFeeNoTax) {
        this.resAllFeeNoTax = resAllFeeNoTax;
    }

    public BigDecimal getInAmt() {
        return inAmt;
    }

    public void setInAmt(BigDecimal inAmt) {
        this.inAmt = inAmt;
    }

    public String getAsmaterialSource() {
        return asmaterialSource == null ? "" : asmaterialSource.trim();
    }

    public void setAsmaterialSource(String asmaterialSource) {
        this.asmaterialSource = asmaterialSource == null ? null : asmaterialSource.trim();
    }

    public String getPrecollecte() {
        return precollecte == null ? "" : precollecte.trim();
    }

    public void setPrecollecte(String precollecte) {
        this.precollecte = precollecte == null ? null : precollecte.trim();
    }

    public String getPurchType() {
        return purchType == null ? "" : purchType.trim();
    }

    public void setPurchType(String purchType) {
        this.purchType = purchType == null ? null : purchType.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
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
