package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzInvXmyyqk extends BasePojo {
    private String id;

    private String invProId;

    private BigDecimal bqYyzsr = new BigDecimal(0);

    private BigDecimal bqYywsr;

    private BigDecimal bqLxzc;

    private BigDecimal bqLyze;

    private BigDecimal bqLr;

    private BigDecimal ljZcze;

    private BigDecimal ljFzze;

    private BigDecimal ljCltxsl;

    private Integer rjcltxsl;

    private Integer dnrjyyclslycz;

    private Integer dnrjyyclslsjz;

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

    private Date yyksrq;

    private Date yyjsrq;

    private BigDecimal ljjyqs;

    private BigDecimal ljyxsj;

    private BigDecimal bnYyzsr = new BigDecimal(0);

    private BigDecimal bnYywsr;

    private BigDecimal bnLxzc;

    private BigDecimal bnLyze;

    private BigDecimal bnLr;

    private BigDecimal klYyzsr = new BigDecimal(0);

    private BigDecimal klYywsr;

    private BigDecimal klLxzc;

    private BigDecimal klLyze;

    private BigDecimal klLr;

    private BigDecimal cltxqkBysl;

    private BigDecimal cltxqkByrjtxsl;

    private BigDecimal cltxqkBnsl;

    private BigDecimal cltxqkZyyksrjtxsl;

    private String bz;
    //非admin账号拼接sql
    private String projectIdSql;
    //项目id
    private String projectId;
    //项目编号
    private String proNum;
    // 管理单位
    private String comname;
    //	项目名称
    private String proName;
    // 项目类别
    private String categoryName;
    // 项目类型
    private String typeName;
    // 股权比例
    private BigDecimal gqbl = new BigDecimal(0);
    // 股权比例
    private BigDecimal szgq = new BigDecimal(0);
    // 投评月均收入
    private BigDecimal tpyjsr = new BigDecimal(0);
    // 投评月均车辆通行数量（辆）
    private BigDecimal tpyjcltxsl = new BigDecimal(0);
    // 批复名称
    private String pfname;
    //
    private String proId;
    
    private String shortName;
    //投资总额
    private BigDecimal hte = new BigDecimal(0);
    //开累投评收入
    private BigDecimal kltpsr = new BigDecimal(0);
    //开累完成投评百分比
    private BigDecimal kltpbfb = new BigDecimal(0);
    //本年投评收入
    private BigDecimal bntpsr = new BigDecimal(0);
    //本年完成投评百分比
    private BigDecimal bntpbfb = new BigDecimal(0);
    //本月投评收入
    private BigDecimal bqtpsr = new BigDecimal(0);
    //本月完成投评百分比
    private BigDecimal bqtpbfb = new BigDecimal(0);
    
    private BigDecimal evaluate13 = new BigDecimal(0);
    //未完成总投评
    private BigDecimal wklYyzsr = new BigDecimal(0);
    //未完成本年投评
    private BigDecimal wbnYyzsr = new BigDecimal(0);
    //未完成本期投评
    private BigDecimal wbqYyzsr = new BigDecimal(0);
    
    private String n;
    
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
    
    // 年月
    private Date period;
    
    //起始年份
    private Date startYear;
    //终止年份
    private Date endYear;
    
    
    
	public BigDecimal getEvaluate13() {
		return evaluate13;
	}

	public void setEvaluate13(BigDecimal evaluate13) {
		this.evaluate13 = evaluate13;
	}

	public BigDecimal getBntpsr() {
		return bntpsr;
	}

	public void setBntpsr(BigDecimal bntpsr) {
		this.bntpsr = bntpsr;
	}

	public BigDecimal getBntpbfb() {
		return bntpbfb;
	}

	public void setBntpbfb(BigDecimal bntpbfb) {
		this.bntpbfb = bntpbfb;
	}

	public BigDecimal getBqtpsr() {
		return bqtpsr;
	}

	public void setBqtpsr(BigDecimal bqtpsr) {
		this.bqtpsr = bqtpsr;
	}

	public BigDecimal getBqtpbfb() {
		return bqtpbfb;
	}

	public void setBqtpbfb(BigDecimal bqtpbfb) {
		this.bqtpbfb = bqtpbfb;
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

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
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

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public BigDecimal getWklYyzsr() {
		return wklYyzsr;
	}

	public void setWklYyzsr(BigDecimal wklYyzsr) {
		this.wklYyzsr = wklYyzsr;
	}

	public BigDecimal getWbnYyzsr() {
		return wbnYyzsr;
	}

	public void setWbnYyzsr(BigDecimal wbnYyzsr) {
		this.wbnYyzsr = wbnYyzsr;
	}

	public BigDecimal getWbqYyzsr() {
		return wbqYyzsr;
	}

	public void setWbqYyzsr(BigDecimal wbqYyzsr) {
		this.wbqYyzsr = wbqYyzsr;
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

	public BigDecimal getKltpsr() {
		return kltpsr;
	}

	public void setKltpsr(BigDecimal kltpsr) {
		this.kltpsr = kltpsr;
	}

	public BigDecimal getKltpbfb() {
		return kltpbfb;
	}

	public void setKltpbfb(BigDecimal kltpbfb) {
		this.kltpbfb = kltpbfb;
	}

	public String getProId() {
		return proId == null ? "" : proId.trim();
	}

	public void setProId(String proId) {
		this.proId = proId == null ? null : proId.trim();
	}

	public String getPfname() {
		return pfname == null ? "" : pfname.trim();
	}

	public void setPfname(String pfname) {
		this.pfname = pfname == null ? null : pfname.trim();
	}

	public String getProNum() {
		return proNum == null ? "" : proNum.trim();
	}

	public void setProNum(String proNum) {
		this.proNum = proNum == null ? null : proNum.trim();
	}

	public String getComname() {
		return comname == null ? "" : comname.trim();
	}

	public void setComname(String comname) {
		this.comname = comname == null ? null : comname.trim();
	}

	public String getProName() {
		return proName == null ? "" : proName.trim();
	}

	public void setProName(String proName) {
		this.proName = proName == null ? null : proName.trim();
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

	public BigDecimal getGqbl() {
		return gqbl;
	}

	public void setGqbl(BigDecimal gqbl) {
		this.gqbl = gqbl;
	}
	
	public BigDecimal getSzgq() {
		return szgq;
	}

	public void setSzgq(BigDecimal szgq) {
		this.szgq = szgq;
	}

	public String getProjectId() {
		return projectId == null ? "" : projectId.trim();
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public String getProjectIdSql() {
		return projectIdSql  == null ? "" : projectIdSql.trim();
	}

	public void setProjectIdSql(String projectIdSql) {
		this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
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

    public BigDecimal getBqYyzsr() {
        return bqYyzsr;
    }

    public void setBqYyzsr(BigDecimal bqYyzsr) {
        this.bqYyzsr = bqYyzsr;
    }

    public BigDecimal getBqYywsr() {
        return bqYywsr;
    }

    public void setBqYywsr(BigDecimal bqYywsr) {
        this.bqYywsr = bqYywsr;
    }

    public BigDecimal getBqLxzc() {
        return bqLxzc;
    }

    public void setBqLxzc(BigDecimal bqLxzc) {
        this.bqLxzc = bqLxzc;
    }

    public BigDecimal getBqLyze() {
        return bqLyze;
    }

    public void setBqLyze(BigDecimal bqLyze) {
        this.bqLyze = bqLyze;
    }

    public BigDecimal getBqLr() {
        return bqLr;
    }

    public void setBqLr(BigDecimal bqLr) {
        this.bqLr = bqLr;
    }

    public BigDecimal getLjZcze() {
        return ljZcze;
    }

    public void setLjZcze(BigDecimal ljZcze) {
        this.ljZcze = ljZcze;
    }

    public BigDecimal getLjFzze() {
        return ljFzze;
    }

    public void setLjFzze(BigDecimal ljFzze) {
        this.ljFzze = ljFzze;
    }

    public BigDecimal getLjCltxsl() {
        return ljCltxsl;
    }

    public void setLjCltxsl(BigDecimal ljCltxsl) {
        this.ljCltxsl = ljCltxsl;
    }

    public Integer getRjcltxsl() {
        return rjcltxsl;
    }

    public void setRjcltxsl(Integer rjcltxsl) {
        this.rjcltxsl = rjcltxsl;
    }

    public Integer getDnrjyyclslycz() {
        return dnrjyyclslycz;
    }

    public void setDnrjyyclslycz(Integer dnrjyyclslycz) {
        this.dnrjyyclslycz = dnrjyyclslycz;
    }

    public Integer getDnrjyyclslsjz() {
        return dnrjyyclslsjz;
    }

    public void setDnrjyyclslsjz(Integer dnrjyyclslsjz) {
        this.dnrjyyclslsjz = dnrjyyclslsjz;
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

    public Date getYyksrq() {
        return yyksrq;
    }

    public void setYyksrq(Date yyksrq) {
        this.yyksrq = yyksrq;
    }

    public Date getYyjsrq() {
        return yyjsrq;
    }

    public void setYyjsrq(Date yyjsrq) {
        this.yyjsrq = yyjsrq;
    }

    public BigDecimal getLjjyqs() {
        return ljjyqs;
    }

    public void setLjjyqs(BigDecimal ljjyqs) {
        this.ljjyqs = ljjyqs;
    }

    public BigDecimal getLjyxsj() {
        return ljyxsj;
    }

    public void setLjyxsj(BigDecimal ljyxsj) {
        this.ljyxsj = ljyxsj;
    }

    public BigDecimal getBnYyzsr() {
        return bnYyzsr;
    }

    public void setBnYyzsr(BigDecimal bnYyzsr) {
        this.bnYyzsr = bnYyzsr;
    }

    public BigDecimal getBnYywsr() {
        return bnYywsr;
    }

    public void setBnYywsr(BigDecimal bnYywsr) {
        this.bnYywsr = bnYywsr;
    }

    public BigDecimal getBnLxzc() {
        return bnLxzc;
    }

    public void setBnLxzc(BigDecimal bnLxzc) {
        this.bnLxzc = bnLxzc;
    }

    public BigDecimal getBnLyze() {
        return bnLyze;
    }

    public void setBnLyze(BigDecimal bnLyze) {
        this.bnLyze = bnLyze;
    }

    public BigDecimal getBnLr() {
        return bnLr;
    }

    public void setBnLr(BigDecimal bnLr) {
        this.bnLr = bnLr;
    }

    public BigDecimal getKlYyzsr() {
        return klYyzsr;
    }

    public void setKlYyzsr(BigDecimal klYyzsr) {
        this.klYyzsr = klYyzsr;
    }

    public BigDecimal getKlYywsr() {
        return klYywsr;
    }

    public void setKlYywsr(BigDecimal klYywsr) {
        this.klYywsr = klYywsr;
    }

    public BigDecimal getKlLxzc() {
        return klLxzc;
    }

    public void setKlLxzc(BigDecimal klLxzc) {
        this.klLxzc = klLxzc;
    }

    public BigDecimal getKlLyze() {
        return klLyze;
    }

    public void setKlLyze(BigDecimal klLyze) {
        this.klLyze = klLyze;
    }

    public BigDecimal getKlLr() {
        return klLr;
    }

    public void setKlLr(BigDecimal klLr) {
        this.klLr = klLr;
    }

    public BigDecimal getCltxqkBysl() {
        return cltxqkBysl;
    }

    public void setCltxqkBysl(BigDecimal cltxqkBysl) {
        this.cltxqkBysl = cltxqkBysl;
    }

    public BigDecimal getCltxqkByrjtxsl() {
        return cltxqkByrjtxsl;
    }

    public void setCltxqkByrjtxsl(BigDecimal cltxqkByrjtxsl) {
        this.cltxqkByrjtxsl = cltxqkByrjtxsl;
    }

    public BigDecimal getCltxqkBnsl() {
        return cltxqkBnsl;
    }

    public void setCltxqkBnsl(BigDecimal cltxqkBnsl) {
        this.cltxqkBnsl = cltxqkBnsl;
    }

    public BigDecimal getCltxqkZyyksrjtxsl() {
        return cltxqkZyyksrjtxsl;
    }

    public void setCltxqkZyyksrjtxsl(BigDecimal cltxqkZyyksrjtxsl) {
        this.cltxqkZyyksrjtxsl = cltxqkZyyksrjtxsl;
    }

    public String getBz() {
        return bz == null ? "" : bz.trim();
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

	public BigDecimal getTpyjsr() {
		return tpyjsr;
	}

	public void setTpyjsr(BigDecimal tpyjsr) {
		this.tpyjsr = tpyjsr;
	}

	public BigDecimal getTpyjcltxsl() {
		return tpyjcltxsl;
	}

	public void setTpyjcltxsl(BigDecimal tpyjcltxsl) {
		this.tpyjcltxsl = tpyjcltxsl;
	}
    
    
}

