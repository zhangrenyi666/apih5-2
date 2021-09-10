package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxPuMMReqPlan extends BasePojo {
    // 主键
    private String zxPuMMReqPlanId;

    // 项目名称
    private String projectName;

    // 分部分项
    private String cbsName;

    // 物资类别
    private String resCateName;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 计量单位
    private String unit;

    // 规格型号
    private String spec;

    // 单价
    private BigDecimal price;

    // 本月物资需用量
    private BigDecimal purNum;

    // 金额
    private BigDecimal totalMoney;

    // 下月预估用量
    private BigDecimal nextMonthNum;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;

	private String period;
    
    private String resCateID;
    
    private String resID;
    
    private String type;
    
    private Date beginDate;
    
    private Date endDate;
    
    public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getResCateID() {
		return resCateID;
	}

	public void setResCateID(String resCateID) {
		this.resCateID = resCateID;
	}

	public String getResID() {
		return resID;
	}

	public void setResID(String resID) {
		this.resID = resID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

    public String getZxPuMMReqPlanId() {
        return zxPuMMReqPlanId == null ? "" : zxPuMMReqPlanId.trim();
    }

    public void setZxPuMMReqPlanId(String zxPuMMReqPlanId) {
        this.zxPuMMReqPlanId = zxPuMMReqPlanId == null ? null : zxPuMMReqPlanId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getCbsName() {
        return cbsName == null ? "" : cbsName.trim();
    }

    public void setCbsName(String cbsName) {
        this.cbsName = cbsName == null ? null : cbsName.trim();
    }

    public String getResCateName() {
        return resCateName == null ? "" : resCateName.trim();
    }

    public void setResCateName(String resCateName) {
        this.resCateName = resCateName == null ? null : resCateName.trim();
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

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPurNum() {
        return purNum;
    }

    public void setPurNum(BigDecimal purNum) {
        this.purNum = purNum;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getNextMonthNum() {
        return nextMonthNum;
    }

    public void setNextMonthNum(BigDecimal nextMonthNum) {
        this.nextMonthNum = nextMonthNum;
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
