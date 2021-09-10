package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxEqToEquipCategoryQueryPage extends BasePojo {
    // 主键
    private String zxEqToEquipCategoryQueryPageId;

    // 机构ID
    private String departmentID;

    // 机构名称
    private String departmentName;

    // 截止时间
    private Date year;

    // 土方、准备作业机械
    private BigDecimal count1;

    // 土方、准备作业机械原值
    private BigDecimal orginalValue1;

    // 压实机械数量
    private BigDecimal count2;

    // 压实机械原值
    private BigDecimal orginalValue2;

    // 压实机械净值
    private BigDecimal leftValue2;

    // 路面机械数量
    private BigDecimal count3;

    // 路面机械原值
    private BigDecimal orginalValue3;

    // 路面机械净值
    private BigDecimal leftValue3;

    // 石方开采及加工机械数量
    private BigDecimal count4;

    // 石方开采及加工机械原值
    private BigDecimal orginalValue4;

    // 石方开采及加工机械净值
    private BigDecimal leftValue1;

    // 石方开采及加工机械净值
    private BigDecimal leftValue4;

    // 桥涵机械数量
    private BigDecimal count5;

    // 桥涵机械原值
    private BigDecimal orginalValue5;

    // 桥涵机械净值
    private BigDecimal leftValue5;

    // 运输机械数量
    private BigDecimal count6;

    // 运输机械原值
    private BigDecimal orginalValue6;

    // 运输机械净值
    private BigDecimal leftValue6;

    // 装卸起重机械数量
    private BigDecimal count7;

    // 装卸起重机械原值
    private BigDecimal orginalValue7;

    // 装卸起重机械净值
    private BigDecimal leftValue7;

    // 动力设备数量
    private BigDecimal count8;

    // 动力设备原值
    private BigDecimal orginalValue8;

    // 动力设备净值
    private BigDecimal leftValue8;

    // 维修设备数量
    private BigDecimal count9;

    // 维修设备原值
    private BigDecimal orginalValue9;

    // 维修设备净值
    private BigDecimal leftValue9;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String parentID;

	private String orgID;
	
	//数量图表
	private String idCount;

    public String getIdCount() {
		return idCount;
	}

	public void setIdCount(String idCount) {
		this.idCount = idCount;
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
	
    public String getZxEqToEquipCategoryQueryPageId() {
        return zxEqToEquipCategoryQueryPageId == null ? "" : zxEqToEquipCategoryQueryPageId.trim();
    }

    public void setZxEqToEquipCategoryQueryPageId(String zxEqToEquipCategoryQueryPageId) {
        this.zxEqToEquipCategoryQueryPageId = zxEqToEquipCategoryQueryPageId == null ? null : zxEqToEquipCategoryQueryPageId.trim();
    }

    public String getDepartmentID() {
        return departmentID == null ? "" : departmentID.trim();
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID == null ? null : departmentID.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public BigDecimal getCount1() {
        return count1;
    }

    public void setCount1(BigDecimal count1) {
        this.count1 = count1;
    }

    public BigDecimal getOrginalValue1() {
        return orginalValue1;
    }

    public void setOrginalValue1(BigDecimal orginalValue1) {
        this.orginalValue1 = orginalValue1;
    }

    public BigDecimal getCount2() {
        return count2;
    }

    public void setCount2(BigDecimal count2) {
        this.count2 = count2;
    }

    public BigDecimal getOrginalValue2() {
        return orginalValue2;
    }

    public void setOrginalValue2(BigDecimal orginalValue2) {
        this.orginalValue2 = orginalValue2;
    }

    public BigDecimal getLeftValue2() {
        return leftValue2;
    }

    public void setLeftValue2(BigDecimal leftValue2) {
        this.leftValue2 = leftValue2;
    }

    public BigDecimal getCount3() {
        return count3;
    }

    public void setCount3(BigDecimal count3) {
        this.count3 = count3;
    }

    public BigDecimal getOrginalValue3() {
        return orginalValue3;
    }

    public void setOrginalValue3(BigDecimal orginalValue3) {
        this.orginalValue3 = orginalValue3;
    }

    public BigDecimal getLeftValue3() {
        return leftValue3;
    }

    public void setLeftValue3(BigDecimal leftValue3) {
        this.leftValue3 = leftValue3;
    }

    public BigDecimal getCount4() {
        return count4;
    }

    public void setCount4(BigDecimal count4) {
        this.count4 = count4;
    }

    public BigDecimal getOrginalValue4() {
        return orginalValue4;
    }

    public void setOrginalValue4(BigDecimal orginalValue4) {
        this.orginalValue4 = orginalValue4;
    }

    public BigDecimal getLeftValue1() {
        return leftValue1;
    }

    public void setLeftValue1(BigDecimal leftValue1) {
        this.leftValue1 = leftValue1;
    }

    public BigDecimal getLeftValue4() {
        return leftValue4;
    }

    public void setLeftValue4(BigDecimal leftValue4) {
        this.leftValue4 = leftValue4;
    }

    public BigDecimal getCount5() {
        return count5;
    }

    public void setCount5(BigDecimal count5) {
        this.count5 = count5;
    }

    public BigDecimal getOrginalValue5() {
        return orginalValue5;
    }

    public void setOrginalValue5(BigDecimal orginalValue5) {
        this.orginalValue5 = orginalValue5;
    }

    public BigDecimal getLeftValue5() {
        return leftValue5;
    }

    public void setLeftValue5(BigDecimal leftValue5) {
        this.leftValue5 = leftValue5;
    }

    public BigDecimal getCount6() {
        return count6;
    }

    public void setCount6(BigDecimal count6) {
        this.count6 = count6;
    }

    public BigDecimal getOrginalValue6() {
        return orginalValue6;
    }

    public void setOrginalValue6(BigDecimal orginalValue6) {
        this.orginalValue6 = orginalValue6;
    }

    public BigDecimal getLeftValue6() {
        return leftValue6;
    }

    public void setLeftValue6(BigDecimal leftValue6) {
        this.leftValue6 = leftValue6;
    }

    public BigDecimal getCount7() {
        return count7;
    }

    public void setCount7(BigDecimal count7) {
        this.count7 = count7;
    }

    public BigDecimal getOrginalValue7() {
        return orginalValue7;
    }

    public void setOrginalValue7(BigDecimal orginalValue7) {
        this.orginalValue7 = orginalValue7;
    }

    public BigDecimal getLeftValue7() {
        return leftValue7;
    }

    public void setLeftValue7(BigDecimal leftValue7) {
        this.leftValue7 = leftValue7;
    }

    public BigDecimal getCount8() {
        return count8;
    }

    public void setCount8(BigDecimal count8) {
        this.count8 = count8;
    }

    public BigDecimal getOrginalValue8() {
        return orginalValue8;
    }

    public void setOrginalValue8(BigDecimal orginalValue8) {
        this.orginalValue8 = orginalValue8;
    }

    public BigDecimal getLeftValue8() {
        return leftValue8;
    }

    public void setLeftValue8(BigDecimal leftValue8) {
        this.leftValue8 = leftValue8;
    }

    public BigDecimal getCount9() {
        return count9;
    }

    public void setCount9(BigDecimal count9) {
        this.count9 = count9;
    }

    public BigDecimal getOrginalValue9() {
        return orginalValue9;
    }

    public void setOrginalValue9(BigDecimal orginalValue9) {
        this.orginalValue9 = orginalValue9;
    }

    public BigDecimal getLeftValue9() {
        return leftValue9;
    }

    public void setLeftValue9(BigDecimal leftValue9) {
        this.leftValue9 = leftValue9;
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
