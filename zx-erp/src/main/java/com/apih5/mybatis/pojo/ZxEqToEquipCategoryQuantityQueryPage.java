package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxEqToEquipCategoryQuantityQueryPage extends BasePojo {
    // 主键
    private String zxEqToEquipCategoryQuantityQueryPageId;

    // 机构
    private String departmentName;

    // 取得日期
    private Date queryDate;

    // 土方、准备作业机械自由
    private BigDecimal selfNum1;

    // 土方、准备作业机械外租
    private BigDecimal leaseNum1;

    // 土方、准备作业机械协作单位自带
    private BigDecimal outBringNum;

    // 压实机械自有
    private BigDecimal selfNum2;

    // 压实机械外租
    private BigDecimal leaseNum2;

    // 压实机械协作单位自带
    private BigDecimal outBringNum2;

    // 路面机械自有
    private BigDecimal selfNum3;

    // 路面机械外租
    private BigDecimal leaseNum3;

    // 路面机械协作单位自带
    private BigDecimal outBringNum3;

    // 石方开采及加工机械
    private BigDecimal selfNum4;

    // 石方开采及加工机械
    private BigDecimal leaseNum4;

    // 石方开采及加工机械协作单位自带
    private BigDecimal outBringNum4;

    // 桥涵机械自有
    private BigDecimal selfNum5;

    // 桥涵机械外租
    private BigDecimal leaseNum5;

    // 桥涵机械协作单位自带
    private BigDecimal outBringNum5;

    // 运输机械自有
    private BigDecimal selfNum6;

    // 运输机械外租
    private BigDecimal leaseNum6;

    // 运输机械协作单位自带
    private BigDecimal outBringNum6;

    // 装卸起重机械自有
    private BigDecimal selfNum7;

    // 装卸起重机械外租
    private BigDecimal leaseNum7;

    // 装卸起重机械协作单位自带
    private BigDecimal outBringNum7;

    // 动力设备自有
    private BigDecimal selfNum8;

    // 动力设备外租
    private BigDecimal leaseNum8;

    // 动力设备协作单位自带
    private BigDecimal outBringNum8;

    // 维修设备自有
    private BigDecimal selfNum9;

    // 维修设备外租
    private BigDecimal leaseNum9;

    // 维修设备协作单位自带
    private BigDecimal outBringNum9;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String catname;

	private String selfNum;
    
	private String leaseNum;

	private String parentID;

	private String orgID;
	
    public String getLeaseNum() {
		return leaseNum;
	}

	public void setLeaseNum(String leaseNum) {
		this.leaseNum = leaseNum;
	}
	
    public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public String getSelfNum() {
		return selfNum;
	}

	public void setSelfNum(String selfNum) {
		this.selfNum = selfNum;
	}
    
    public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}
	
    public String getZxEqToEquipCategoryQuantityQueryPageId() {
        return zxEqToEquipCategoryQuantityQueryPageId == null ? "" : zxEqToEquipCategoryQuantityQueryPageId.trim();
    }

    public void setZxEqToEquipCategoryQuantityQueryPageId(String zxEqToEquipCategoryQuantityQueryPageId) {
        this.zxEqToEquipCategoryQuantityQueryPageId = zxEqToEquipCategoryQuantityQueryPageId == null ? null : zxEqToEquipCategoryQuantityQueryPageId.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public BigDecimal getSelfNum1() {
        return selfNum1;
    }

    public void setSelfNum1(BigDecimal selfNum1) {
        this.selfNum1 = selfNum1;
    }

    public BigDecimal getLeaseNum1() {
        return leaseNum1;
    }

    public void setLeaseNum1(BigDecimal leaseNum1) {
        this.leaseNum1 = leaseNum1;
    }

    public BigDecimal getOutBringNum() {
        return outBringNum;
    }

    public void setOutBringNum(BigDecimal outBringNum) {
        this.outBringNum = outBringNum;
    }

    public BigDecimal getSelfNum2() {
        return selfNum2;
    }

    public void setSelfNum2(BigDecimal selfNum2) {
        this.selfNum2 = selfNum2;
    }

    public BigDecimal getLeaseNum2() {
        return leaseNum2;
    }

    public void setLeaseNum2(BigDecimal leaseNum2) {
        this.leaseNum2 = leaseNum2;
    }

    public BigDecimal getOutBringNum2() {
        return outBringNum2;
    }

    public void setOutBringNum2(BigDecimal outBringNum2) {
        this.outBringNum2 = outBringNum2;
    }

    public BigDecimal getSelfNum3() {
        return selfNum3;
    }

    public void setSelfNum3(BigDecimal selfNum3) {
        this.selfNum3 = selfNum3;
    }

    public BigDecimal getLeaseNum3() {
        return leaseNum3;
    }

    public void setLeaseNum3(BigDecimal leaseNum3) {
        this.leaseNum3 = leaseNum3;
    }

    public BigDecimal getOutBringNum3() {
        return outBringNum3;
    }

    public void setOutBringNum3(BigDecimal outBringNum3) {
        this.outBringNum3 = outBringNum3;
    }

    public BigDecimal getSelfNum4() {
        return selfNum4;
    }

    public void setSelfNum4(BigDecimal selfNum4) {
        this.selfNum4 = selfNum4;
    }

    public BigDecimal getLeaseNum4() {
        return leaseNum4;
    }

    public void setLeaseNum4(BigDecimal leaseNum4) {
        this.leaseNum4 = leaseNum4;
    }

    public BigDecimal getOutBringNum4() {
        return outBringNum4;
    }

    public void setOutBringNum4(BigDecimal outBringNum4) {
        this.outBringNum4 = outBringNum4;
    }

    public BigDecimal getSelfNum5() {
        return selfNum5;
    }

    public void setSelfNum5(BigDecimal selfNum5) {
        this.selfNum5 = selfNum5;
    }

    public BigDecimal getLeaseNum5() {
        return leaseNum5;
    }

    public void setLeaseNum5(BigDecimal leaseNum5) {
        this.leaseNum5 = leaseNum5;
    }

    public BigDecimal getOutBringNum5() {
        return outBringNum5;
    }

    public void setOutBringNum5(BigDecimal outBringNum5) {
        this.outBringNum5 = outBringNum5;
    }

    public BigDecimal getSelfNum6() {
        return selfNum6;
    }

    public void setSelfNum6(BigDecimal selfNum6) {
        this.selfNum6 = selfNum6;
    }

    public BigDecimal getLeaseNum6() {
        return leaseNum6;
    }

    public void setLeaseNum6(BigDecimal leaseNum6) {
        this.leaseNum6 = leaseNum6;
    }

    public BigDecimal getOutBringNum6() {
        return outBringNum6;
    }

    public void setOutBringNum6(BigDecimal outBringNum6) {
        this.outBringNum6 = outBringNum6;
    }

    public BigDecimal getSelfNum7() {
        return selfNum7;
    }

    public void setSelfNum7(BigDecimal selfNum7) {
        this.selfNum7 = selfNum7;
    }

    public BigDecimal getLeaseNum7() {
        return leaseNum7;
    }

    public void setLeaseNum7(BigDecimal leaseNum7) {
        this.leaseNum7 = leaseNum7;
    }

    public BigDecimal getOutBringNum7() {
        return outBringNum7;
    }

    public void setOutBringNum7(BigDecimal outBringNum7) {
        this.outBringNum7 = outBringNum7;
    }

    public BigDecimal getSelfNum8() {
        return selfNum8;
    }

    public void setSelfNum8(BigDecimal selfNum8) {
        this.selfNum8 = selfNum8;
    }

    public BigDecimal getLeaseNum8() {
        return leaseNum8;
    }

    public void setLeaseNum8(BigDecimal leaseNum8) {
        this.leaseNum8 = leaseNum8;
    }

    public BigDecimal getOutBringNum8() {
        return outBringNum8;
    }

    public void setOutBringNum8(BigDecimal outBringNum8) {
        this.outBringNum8 = outBringNum8;
    }

    public BigDecimal getSelfNum9() {
        return selfNum9;
    }

    public void setSelfNum9(BigDecimal selfNum9) {
        this.selfNum9 = selfNum9;
    }

    public BigDecimal getLeaseNum9() {
        return leaseNum9;
    }

    public void setLeaseNum9(BigDecimal leaseNum9) {
        this.leaseNum9 = leaseNum9;
    }

    public BigDecimal getOutBringNum9() {
        return outBringNum9;
    }

    public void setOutBringNum9(BigDecimal outBringNum9) {
        this.outBringNum9 = outBringNum9;
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
