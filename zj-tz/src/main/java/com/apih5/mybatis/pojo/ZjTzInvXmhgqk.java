package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzInvXmhgqk extends BasePojo {
    private String id;

    private String invProId;

    private String hgq;

    private BigDecimal yshsk;

    private Date sjhgsj;

    private BigDecimal sjhgje = new BigDecimal(0);

    private BigDecimal hgjeBq = new BigDecimal(0);

    private String hgjeHgbl;

    private String periodType;

    private String periodValue;

    private String org;

    private String currency;

    private BigDecimal sort;

    private String createBy;

    private String createOrg;

    private Date createDate;

    private String updateBy;

    private String updateOrg;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    private BigDecimal bnlj = new BigDecimal(0);

    private BigDecimal kglj = new BigDecimal(0);

    private String bz;
    //累计回购期
    private BigDecimal ljhgq = new BigDecimal(0);
    // 偿还银行贷款
    private BigDecimal chyhdk = new BigDecimal(0);
    // 偿还贷款利息
    private BigDecimal chdklx = new BigDecimal(0);
    // 偿还资本金
    private BigDecimal chzbj = new BigDecimal(0);
    // 资金集中
    private BigDecimal zjjz = new BigDecimal(0);
    // 其他
    private BigDecimal qt = new BigDecimal(0);
    
    // 总投资额
    private BigDecimal ztze = new BigDecimal(0);
    //项目编号
    private String proNum;
    // 项目类型
    private String proInvCategory;
    
    // 管理单位
    private String comname;
    // 管理单位(月报)
    private String gldw;
    //	项目类别(月报)
    private String proCategory;
    //	项目名称
    private String proName;
    //	股权比例
    private BigDecimal gqbl = new BigDecimal(0);
    //	股权比例(所占股权)
    private BigDecimal szgq = new BigDecimal(0);
    // 预计回购总额
    private BigDecimal hgxyMoney = new BigDecimal(0);
    // 回购起始日
    private Date hgxyDate;
    // 回购款类型
    private String category;
    // 回购款金额
    private BigDecimal money;
    //年月
    private Date period;
    
    // 项目类别
    private String categoryName;
    // 项目类型
    private String typeName;
    
    //序号
    private String orderNum;
    
    private String hgxyDateStr;
    
    private String projectId;
    
    private String projectIdSql;
    
    private String proId;
    
    private String ext1;
    
    private String userId;
    
    private String shortName;
    
    private BigDecimal hte = new BigDecimal(0);
    //预计回购总额
    private BigDecimal yjhgze = new BigDecimal(0);
    
    private BigDecimal klhgbl = new BigDecimal(0);
    //預計回購總額
    private BigDecimal amount2;
    //开累已回购总额
    //本年应回购金额
    private BigDecimal bnyhg = new BigDecimal(0);
    
    private String hgDate;
    //未回购開累金额
    private BigDecimal wklhgje = new BigDecimal(0);
    //未回购本年金额
    private BigDecimal wbnlj = new BigDecimal(0);
    
    private String n1;
    
    private String n2;
    
    private BigDecimal klyhgje = new BigDecimal(0);
    
    private int firstYear;
    private int currentYear;
    private int lastYear;
    private String proProcessId;
    
    private BigDecimal amount1 = new BigDecimal(0);
    private BigDecimal tzwckl = new BigDecimal(0);
    private BigDecimal wtzwckl = new BigDecimal(0);
    private BigDecimal amount3 = new BigDecimal(0);
    private BigDecimal wcjafkl = new BigDecimal(0);
    private BigDecimal wwcjafkl = new BigDecimal(0);
    
    //起始年份
    private Date startYear;
    //终止年份
    private Date endYear;
    
    
	public BigDecimal getSzgq() {
		return szgq;
	}

	public void setSzgq(BigDecimal szgq) {
		this.szgq = szgq;
	}

	public Date getStartYear() {
		return startYear;
	}

	public void setStartYear(Date startYear) {
		this.startYear = startYear;
	}

	public Date getEndYear() {
		return endYear;
	}

	public void setEndYear(Date endYear) {
		this.endYear = endYear;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public BigDecimal getTzwckl() {
		return tzwckl;
	}

	public void setTzwckl(BigDecimal tzwckl) {
		this.tzwckl = tzwckl;
	}

	public BigDecimal getWtzwckl() {
		return wtzwckl;
	}

	public void setWtzwckl(BigDecimal wtzwckl) {
		this.wtzwckl = wtzwckl;
	}

	public BigDecimal getAmount3() {
		return amount3;
	}

	public void setAmount3(BigDecimal amount3) {
		this.amount3 = amount3;
	}

	public BigDecimal getWcjafkl() {
		return wcjafkl;
	}

	public void setWcjafkl(BigDecimal wcjafkl) {
		this.wcjafkl = wcjafkl;
	}

	public BigDecimal getWwcjafkl() {
		return wwcjafkl;
	}

	public void setWwcjafkl(BigDecimal wwcjafkl) {
		this.wwcjafkl = wwcjafkl;
	}


	public int getFirstYear() {
		return firstYear;
	}

	public void setFirstYear(int firstYear) {
		this.firstYear = firstYear;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getLastYear() {
		return lastYear;
	}

	public void setLastYear(int lastYear) {
		this.lastYear = lastYear;
	}

	public String getProProcessId() {
		return proProcessId == null ? "" : proProcessId.trim();
	}

	public void setProProcessId(String proProcessId) {
		this.proProcessId = proProcessId == null ? null : proProcessId.trim();
	}

	public BigDecimal getKlyhgje() {
		return klyhgje;
	}

	public void setKlyhgje(BigDecimal klyhgje) {
		this.klyhgje = klyhgje;
	}

	public String getN1() {
		return n1;
	}

	public void setN1(String n1) {
		this.n1 = n1;
	}

	public String getN2() {
		return n2;
	}

	public void setN2(String n2) {
		this.n2 = n2;
	}

	public BigDecimal getWklhgje() {
		return wklhgje;
	}

	public void setWklhgje(BigDecimal wklhgje) {
		this.wklhgje = wklhgje;
	}

	public BigDecimal getWbnlj() {
		return wbnlj;
	}

	public void setWbnlj(BigDecimal wbnlj) {
		this.wbnlj = wbnlj;
	}

	public String getHgDate() {
		return hgDate;
	}

	public void setHgDate(String hgDate) {
		this.hgDate = hgDate;
	}

	public BigDecimal getAmount2() {
		return amount2;
	}

	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}

	public BigDecimal getBnyhg() {
		return bnyhg;
	}

	public void setBnyhg(BigDecimal bnyhg) {
		this.bnyhg = bnyhg;
	}

	public String getShortName() {
		return shortName == null ? "" : shortName.trim();
	}

	public void setShortName(String shortName) {
		this.shortName = shortName == null ? null : shortName.trim();
	}

	public BigDecimal getHte() {
		return hte;
	}

	public void setHte(BigDecimal hte) {
		this.hte = hte;
	}

	public BigDecimal getYjhgze() {
		return yjhgze;
	}

	public void setYjhgze(BigDecimal yjhgze) {
		this.yjhgze = yjhgze;
	}

	public BigDecimal getKlhgbl() {
		return klhgbl;
	}

	public void setKlhgbl(BigDecimal klhgbl) {
		this.klhgbl = klhgbl;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getProjectId() {
		return projectId == null ? "" : projectId.trim();
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public String getProjectIdSql() {
		return projectIdSql == null ? "" : projectIdSql.trim();
	}

	public void setProjectIdSql(String projectIdSql) {
		this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
	}

	public String getOrderNum() {
		return orderNum == null ? "" : orderNum.trim();
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum == null ? null : orderNum.trim();
	}

	public String getCategoryName() {
		return categoryName == null ? "" : categoryName.trim();
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName == null ? null : categoryName.trim();
	}

	public String getTypeName() {
		return typeName == null ? "" : typeName.trim();
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null : typeName.trim();
	}

	public BigDecimal getChyhdk() {
		return chyhdk;
	}

	public void setChyhdk(BigDecimal chyhdk) {
		this.chyhdk = chyhdk;
	}

	public BigDecimal getChdklx() {
		return chdklx;
	}

	public void setChdklx(BigDecimal chdklx) {
		this.chdklx = chdklx;
	}

	public BigDecimal getChzbj() {
		return chzbj;
	}

	public void setChzbj(BigDecimal chzbj) {
		this.chzbj = chzbj;
	}

	public BigDecimal getZjjz() {
		return zjjz;
	}

	public void setZjjz(BigDecimal zjjz) {
		this.zjjz = zjjz;
	}

	public BigDecimal getQt() {
		return qt;
	}

	public void setQt(BigDecimal qt) {
		this.qt = qt;
	}

	public BigDecimal getZtze() {
		return ztze;
	}

	public void setZtze(BigDecimal ztze) {
		this.ztze = ztze;
	}

	public String getProNum() {
		return proNum == null ? "" : proNum.trim();
	}

	public void setProNum(String proNum) {
		this.proNum = proNum == null ? null : proNum.trim();
	}

	public String getProInvCategory() {
		return proInvCategory == null ? "" : proInvCategory.trim();
	}

	public void setProInvCategory(String proInvCategory) {
		this.proInvCategory = proInvCategory == null ? null : proInvCategory.trim();
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}


	public String getComname() {
		return comname == null ? "" : comname.trim();
	}

	public void setComname(String comname) {
		this.comname = comname == null ? null : comname.trim();
	}
	
	public String getGldw() {
		return gldw;
	}

	public void setGldw(String gldw) {
		this.gldw = gldw;
	}

	public String getProCategory() {
		return proCategory == null ? "" : proCategory.trim();
	}

	public void setProCategory(String proCategory) {
		this.proCategory = proCategory == null ? null : proCategory.trim();
	}

	public String getProName() {
		return proName == null ? "" : proName.trim();
	}

	public void setProName(String proName) {
		this.proName = proName == null ? null : proName.trim();
	}


	public BigDecimal getGqbl() {
		return gqbl;
	}

	public void setGqbl(BigDecimal gqbl) {
		this.gqbl = gqbl;
	}

	public BigDecimal getHgxyMoney() {
		return hgxyMoney;
	}

	public void setHgxyMoney(BigDecimal hgxyMoney) {
		this.hgxyMoney = hgxyMoney;
	}

	public Date getHgxyDate() {
		return hgxyDate;
	}

	public void setHgxyDate(Date hgxyDate) {
		this.hgxyDate = hgxyDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInvProId() {
        return invProId == null ? "" : invProId.trim();
    }

    public void setInvProId(String invProId) {
        this.invProId = invProId == null ? null : invProId.trim();
    }

    public String getHgq() {
        return hgq == null ? "" : hgq.trim();
    }

    public void setHgq(String hgq) {
        this.hgq = hgq == null ? null : hgq.trim();
    }

    public BigDecimal getYshsk() {
        return yshsk;
    }

    public void setYshsk(BigDecimal yshsk) {
        this.yshsk = yshsk;
    }

    public Date getSjhgsj() {
        return sjhgsj;
    }

    public void setSjhgsj(Date sjhgsj) {
        this.sjhgsj = sjhgsj;
    }

    public BigDecimal getSjhgje() {
        return sjhgje;
    }

    public void setSjhgje(BigDecimal sjhgje) {
        this.sjhgje = sjhgje;
    }

    public BigDecimal getHgjeBq() {
        return hgjeBq;
    }

    public void setHgjeBq(BigDecimal hgjeBq) {
        this.hgjeBq = hgjeBq;
    }

    public String getHgjeHgbl() {
        return hgjeHgbl == null ? "" : hgjeHgbl.trim();
    }

    public void setHgjeHgbl(String hgjeHgbl) {
        this.hgjeHgbl = hgjeHgbl == null ? null : hgjeHgbl.trim();
    }

    public String getPeriodType() {
        return periodType == null ? "" : periodType.trim();
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType == null ? null : periodType.trim();
    }

    public String getPeriodValue() {
        return periodValue == null ? "" : periodValue.trim();
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue == null ? null : periodValue.trim();
    }

    public String getOrg() {
        return org == null ? "" : org.trim();
    }

    public void setOrg(String org) {
        this.org = org == null ? null : org.trim();
    }

    public String getCurrency() {
        return currency == null ? "" : currency.trim();
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public String getCreateBy() {
        return createBy == null ? "" : createBy.trim();
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateOrg() {
        return createOrg == null ? "" : createOrg.trim();
    }

    public void setCreateOrg(String createOrg) {
        this.createOrg = createOrg == null ? null : createOrg.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy == null ? "" : updateBy.trim();
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdateOrg() {
        return updateOrg == null ? "" : updateOrg.trim();
    }

    public void setUpdateOrg(String updateOrg) {
        this.updateOrg = updateOrg == null ? null : updateOrg.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public BigDecimal getBnlj() {
        return bnlj;
    }

    public void setBnlj(BigDecimal bnlj) {
        this.bnlj = bnlj;
    }

    public BigDecimal getKglj() {
        return kglj;
    }

    public void setKglj(BigDecimal kglj) {
        this.kglj = kglj;
    }

    public String getBz() {
        return bz == null ? "" : bz.trim();
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    
    
	public BigDecimal getLjhgq() {
		return ljhgq;
	}

	public void setLjhgq(BigDecimal ljhgq) {
		this.ljhgq = ljhgq;
	}

	public String getHgxyDateStr() {
		return hgxyDateStr;
	}

	public void setHgxyDateStr(String hgxyDateStr) {
		this.hgxyDateStr = hgxyDateStr;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
    
    
}

