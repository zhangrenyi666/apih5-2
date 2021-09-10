package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzInvXmtzqk extends BasePojo {
    private String id;

    private String invProId;

    private BigDecimal ztze = new BigDecimal(0);

    private BigDecimal zjaf;

    private BigDecimal zzcf = new BigDecimal(0);

    private BigDecimal tzwcbq;

    private BigDecimal tzwcbn = new BigDecimal(0);

    private BigDecimal tzwckl = new BigDecimal(0);

    private BigDecimal tzwcbl;

    private BigDecimal wcjafbq;

    private BigDecimal wcjafbn = new BigDecimal(0);

    private BigDecimal wcjafkl = new BigDecimal(0);

    private BigDecimal wcjafbl;

    private BigDecimal zcjafbq;

    private BigDecimal zcjafbn;

    private BigDecimal zcjafkl;

    private BigDecimal zcjafbl;

    private BigDecimal wczcfbq;

    private BigDecimal wczcfbn;

    private BigDecimal wczcfkl;

    private BigDecimal wczcfbl;

    private BigDecimal zczcfbq;

    private BigDecimal zczcfbn;

    private BigDecimal zczcfkl;

    private BigDecimal zczcfbl;

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

    private BigDecimal ygjjtjaf;

    private BigDecimal xmztwcjlbq;

    private BigDecimal xmztwcjlbn;

    private BigDecimal xmztwcjlkl;

    private BigDecimal xmztwcjlbl;

    private BigDecimal ygjjtwcjafbq;

    private BigDecimal ygjjtwcjafbn = new BigDecimal(0);

    private BigDecimal ygjjtwcjafkl;

    private BigDecimal ygjjtwcjafbl;

    private BigDecimal ygjjtwcjlbq;

    private BigDecimal ygjjtwcjlbn;

    private BigDecimal ygjjtwcjlkl;

    private BigDecimal ygjjtwcjlbl;

    private BigDecimal ygjjtzcjafbq;

    private BigDecimal ygjjtzcjafbn;

    private BigDecimal ygjjtzcjafkl;

    private BigDecimal ygjjtzcjafbl;

    private String bz;

    private String yesOrNoJGC;

    private BigDecimal wcjafbqJGC;

    private BigDecimal wcjafbnJGC;

    private BigDecimal wcjafklJGC;

    private BigDecimal wcjafblJGC;

    private BigDecimal xmztwcjlbqJGC;

    private BigDecimal xmztwcjlbnJGC;

    private BigDecimal xmztwcjlklJGC;

    private BigDecimal xmztwcjlblJGC;

    private BigDecimal zcjafbqJGC;

    private BigDecimal zcjafbnJGC;

    private BigDecimal zcjafklJGC;

    private BigDecimal zcjafblJGC;

    private BigDecimal ygjwcbqJGC;

    private BigDecimal ygjwcbnJGC;

    private BigDecimal ygjwcklJGC;

    private BigDecimal ygjwcblJGC;

    private BigDecimal ygjjlbqJGC;

    private BigDecimal ygjjlbnJGC;

    private BigDecimal ygjjlklJGC;

    private BigDecimal ygjjlblJGC;

    private BigDecimal qytzwcbq;

    private BigDecimal qytzwcbn;

    private BigDecimal qytzwckl;

    private BigDecimal qytzwcbl;
    // 权益投资完成本季
    private BigDecimal qytzwcbj = new BigDecimal(0);
    
    private BigDecimal tzwcbj = new BigDecimal(0);

    private BigDecimal wcjafbj = new BigDecimal(0);

    private BigDecimal xmztwcjlbj = new BigDecimal(0);

    private BigDecimal zcjafbj;

    private BigDecimal ygjjtwcjafbj;

    private BigDecimal ygjjtwcjlbj;

    private BigDecimal ygjjtzcjafbj;

    private BigDecimal wczcfbj;

    private BigDecimal zczcfbj;
    //项目编号
    private String proNum;
    //	项目名称
    private String proName;
    // 管理单位
    private String comname;
    // 管理单位
    private String gldw;
    // 股权比例
    private BigDecimal szgq = new BigDecimal(0);
    // 股权比例
    private BigDecimal gqbl = new BigDecimal(0);
    // 项目类别
    private String categoryName;
    // 项目类别
    private String proCategory;
    // 一公局施工份额(我方施工比例)
    private BigDecimal ygjsgfe = new BigDecimal(0);
    //	总建安费
    private BigDecimal jafMoney = new BigDecimal(0);
    // 批复名称
    private String pfname;
    //	总征拆费
    private BigDecimal zcfMoney = new BigDecimal(0);
    //总建安费*一公局施工份额
    private BigDecimal ygjjaf = new BigDecimal(0);
    
    private Date period;
    // 项目类型
    private String typeName;
    
    private String orderNum;
    // 项目id
    private String projectId;
    
    private String projectIdSql;
    
    private String proId;
    
    private String ext1;
    
    private String userId;
    // 项目简称
    private String shortName;
    // 本年完成权益投资百分比 
    private BigDecimal bnqytzbfb;
    // 本年完成局建安费百分比
    private BigDecimal bnjjafbfb = new BigDecimal(0.00);
    // 截至上季度实际完成权益投资
    private BigDecimal total1;
    // 截至上季度计划完成权益投资
    private BigDecimal total2;
    // 截至上季度实际完成局建安费
    private BigDecimal total3;
    // 截至上季度计划完成局建安费
    private BigDecimal total4;
    // 本季完成权益投资占季度计划百分比
    private BigDecimal bjqytzbfb = new BigDecimal(0.00);
    // 本季完成局建安费占季度计划百分比
    private BigDecimal bjjjafbfb = new BigDecimal(0.00);
    // 上季完成权益投资占季度计划百分比
    private BigDecimal sjqytzbfb = new BigDecimal(0.00);
    // 上季完成局建安费占季度计划百分比
    private BigDecimal sjjjafbfb = new BigDecimal(0.00);
    // 本年总投资百分比
    private BigDecimal bnztzbfb = new BigDecimal(0.00);
    
    private String quarter;
    // 本年计划完成权益投资
    private BigDecimal jhwcqytzbn = new BigDecimal(0);
    // 本年计划完成局建安费
    private BigDecimal jhwcjjafbn = new BigDecimal(0);
    //本年计划完成总投资
    private BigDecimal jhwcztzbn = new BigDecimal(0);
    //本年计划完成建安费
    private BigDecimal jhwcjafbn = new BigDecimal(0);
    //本季计划完成权益投资
    private BigDecimal jhwcqytzbj;
    // 本季计划完成局建安费
    private BigDecimal jhwcjjafbj;
    // 本季计划完成总投资
    private BigDecimal jhwcztzbj = new BigDecimal(0);
    // 本季计划完成建安费
    private BigDecimal jhwcjafbj = new BigDecimal(0);
    
    private String sortType;
    
    private String dataType;
    
    private String endTime;
    
    private String type;
    //项目数量
    private BigDecimal count = new BigDecimal(0);
    //投资项目数量
    private BigDecimal invCount = new BigDecimal(0);
    // 公司管理下项目总合同额
    private BigDecimal xmzht = new BigDecimal(0);
    // 公司管理下项目总建安费
    private BigDecimal xmzja = new BigDecimal(0);
    // 总合同额
    private BigDecimal zht = new BigDecimal(0);
    // 总建安费
    private BigDecimal zja = new BigDecimal(0);
    // 统计类型
    private String tjType;
    //本年投资完成
    private BigDecimal ztzwcbn = new BigDecimal(0);
    //变化率
    private BigDecimal tzwcbhl = new BigDecimal(0);
    // 本年完成建安
    private BigDecimal zwcjafbn = new BigDecimal(0);
    // 变化率
    private BigDecimal wcjafbhl = new BigDecimal(0);
    
    private String areaName;
    // 未完成总投资
    private BigDecimal wtzwckl = new BigDecimal(0);
    // 未完成建安费
    private BigDecimal wwcjafkl = new BigDecimal(0);
    //本年未完成总投资
    private BigDecimal wtzwcbn = new BigDecimal(0);
    // 本年未完成建安费
    private BigDecimal wwcjafbn = new BigDecimal(0);
    //本季未完成总投资
    private BigDecimal wtzwcbj = new BigDecimal(0);
    //本季未完成建安费
    private BigDecimal wwcjafbj = new BigDecimal(0);
    // 总投资额
    private BigDecimal amount1 = new BigDecimal(0);
    // 总建安费
    private BigDecimal amount3 = new BigDecimal(0);
    
    private String proProcessId;
    // 项目页面趋势数据-数据
    private String data;
    //起始年份
    private Date startYear;
    //终止年份
    private Date endYear;
    // 上季完成总投资百分比 
    private BigDecimal sjztzbfb = new BigDecimal(0);
    // 上季完成建安费百分比
    private BigDecimal sjjafbfb = new BigDecimal(0);
    // 上年完成总投资百分比 
    private BigDecimal snztzbfb	 = new BigDecimal(0);
    // 上年完成建安费百分比
    private BigDecimal snjafbfb = new BigDecimal(0);
    //预警筛选
    private String warnSelect;
    //预警标识
    private String colourFlag;
    //预警种类
    private String warnType;
    
    private String n;
    
    private String jidu;
    
    private ZjTzInvXmyyqk zjTzInvXmyyqk;
    
    private ZjTzInvXmhgqk zjTzInvXmhgqk;
    //本年计划权益投资总额
    private BigDecimal bnjhwcqytz = new BigDecimal(0);
    //本年计划局建安费
    private BigDecimal ygjbnjhwcja = new BigDecimal(0);
    //本年计划完成总投资
    private BigDecimal bnjhwcztz = new BigDecimal(0);
    //项目整体本年计划完成建安
    private BigDecimal bnjhwcjaf = new BigDecimal(0);
    //本年签约合同额
    private BigDecimal bnamount1 = new BigDecimal(0);
    //本年签约合同额开累
    private BigDecimal klamount1 = new BigDecimal(0);
    //本年权益签约合同额
    private BigDecimal bnamount2 = new BigDecimal(0);
    //本年权益签约合同额开累
    private BigDecimal klamount2 = new BigDecimal(0);
    //签约合同额同比
    private BigDecimal tbamount = new BigDecimal(0);
    //签约权益合同额同比
    private BigDecimal tbqyamount = new BigDecimal(0);
    //完成投资额同比
    private BigDecimal tbtzwc = new BigDecimal(0);
    //完成权益投资额同比
    private BigDecimal tbqytzwc = new BigDecimal(0);
    //本季权益投资百分比排名
    private int bjqytzpm;
    //上季权益投资百分比排名
    private int sjqytzpm;
    //年度总投资百分比排名
    private int pmztz;
    
    //本季局建安费百分比排名
    private int bjjjafpm;
    //上季局建安费百分比排名
    private int sjjjafpm;
    
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

    public BigDecimal getZtze() {
        return ztze;
    }

    public void setZtze(BigDecimal ztze) {
        this.ztze = ztze;
    }

    public BigDecimal getZjaf() {
        return zjaf;
    }

    public void setZjaf(BigDecimal zjaf) {
        this.zjaf = zjaf;
    }

    public BigDecimal getZzcf() {
        return zzcf;
    }

    public void setZzcf(BigDecimal zzcf) {
        this.zzcf = zzcf;
    }

    public BigDecimal getTzwcbq() {
        return tzwcbq;
    }

    public void setTzwcbq(BigDecimal tzwcbq) {
        this.tzwcbq = tzwcbq;
    }

    public BigDecimal getTzwcbn() {
        return tzwcbn;
    }

    public void setTzwcbn(BigDecimal tzwcbn) {
        this.tzwcbn = tzwcbn;
    }

    public BigDecimal getTzwckl() {
        return tzwckl;
    }

    public void setTzwckl(BigDecimal tzwckl) {
        this.tzwckl = tzwckl;
    }

    public BigDecimal getTzwcbl() {
        return tzwcbl;
    }

    public void setTzwcbl(BigDecimal tzwcbl) {
        this.tzwcbl = tzwcbl;
    }

    public BigDecimal getWcjafbq() {
        return wcjafbq;
    }

    public void setWcjafbq(BigDecimal wcjafbq) {
        this.wcjafbq = wcjafbq;
    }

    public BigDecimal getWcjafbn() {
        return wcjafbn;
    }

    public void setWcjafbn(BigDecimal wcjafbn) {
        this.wcjafbn = wcjafbn;
    }

    public BigDecimal getWcjafkl() {
        return wcjafkl;
    }

    public void setWcjafkl(BigDecimal wcjafkl) {
        this.wcjafkl = wcjafkl;
    }

    public BigDecimal getWcjafbl() {
        return wcjafbl;
    }

    public void setWcjafbl(BigDecimal wcjafbl) {
        this.wcjafbl = wcjafbl;
    }

    public BigDecimal getZcjafbq() {
        return zcjafbq;
    }

    public void setZcjafbq(BigDecimal zcjafbq) {
        this.zcjafbq = zcjafbq;
    }

    public BigDecimal getZcjafbn() {
        return zcjafbn;
    }

    public void setZcjafbn(BigDecimal zcjafbn) {
        this.zcjafbn = zcjafbn;
    }

    public BigDecimal getZcjafkl() {
        return zcjafkl;
    }

    public void setZcjafkl(BigDecimal zcjafkl) {
        this.zcjafkl = zcjafkl;
    }

    public BigDecimal getZcjafbl() {
        return zcjafbl;
    }

    public void setZcjafbl(BigDecimal zcjafbl) {
        this.zcjafbl = zcjafbl;
    }

    public BigDecimal getWczcfbq() {
        return wczcfbq;
    }

    public void setWczcfbq(BigDecimal wczcfbq) {
        this.wczcfbq = wczcfbq;
    }

    public BigDecimal getWczcfbn() {
        return wczcfbn;
    }

    public void setWczcfbn(BigDecimal wczcfbn) {
        this.wczcfbn = wczcfbn;
    }

    public BigDecimal getWczcfkl() {
        return wczcfkl;
    }

    public void setWczcfkl(BigDecimal wczcfkl) {
        this.wczcfkl = wczcfkl;
    }

    public BigDecimal getWczcfbl() {
        return wczcfbl;
    }

    public void setWczcfbl(BigDecimal wczcfbl) {
        this.wczcfbl = wczcfbl;
    }

    public BigDecimal getZczcfbq() {
        return zczcfbq;
    }

    public void setZczcfbq(BigDecimal zczcfbq) {
        this.zczcfbq = zczcfbq;
    }

    public BigDecimal getZczcfbn() {
        return zczcfbn;
    }

    public void setZczcfbn(BigDecimal zczcfbn) {
        this.zczcfbn = zczcfbn;
    }

    public BigDecimal getZczcfkl() {
        return zczcfkl;
    }

    public void setZczcfkl(BigDecimal zczcfkl) {
        this.zczcfkl = zczcfkl;
    }

    public BigDecimal getZczcfbl() {
        return zczcfbl;
    }

    public void setZczcfbl(BigDecimal zczcfbl) {
        this.zczcfbl = zczcfbl;
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

    public BigDecimal getYgjjtjaf() {
        return ygjjtjaf;
    }

    public void setYgjjtjaf(BigDecimal ygjjtjaf) {
        this.ygjjtjaf = ygjjtjaf;
    }

    public BigDecimal getXmztwcjlbq() {
        return xmztwcjlbq;
    }

    public void setXmztwcjlbq(BigDecimal xmztwcjlbq) {
        this.xmztwcjlbq = xmztwcjlbq;
    }

    public BigDecimal getXmztwcjlbn() {
        return xmztwcjlbn;
    }

    public void setXmztwcjlbn(BigDecimal xmztwcjlbn) {
        this.xmztwcjlbn = xmztwcjlbn;
    }

    public BigDecimal getXmztwcjlkl() {
        return xmztwcjlkl;
    }

    public void setXmztwcjlkl(BigDecimal xmztwcjlkl) {
        this.xmztwcjlkl = xmztwcjlkl;
    }

    public BigDecimal getXmztwcjlbl() {
        return xmztwcjlbl;
    }

    public void setXmztwcjlbl(BigDecimal xmztwcjlbl) {
        this.xmztwcjlbl = xmztwcjlbl;
    }

    public BigDecimal getYgjjtwcjafbq() {
        return ygjjtwcjafbq;
    }

    public void setYgjjtwcjafbq(BigDecimal ygjjtwcjafbq) {
        this.ygjjtwcjafbq = ygjjtwcjafbq;
    }

    public BigDecimal getYgjjtwcjafbn() {
        return ygjjtwcjafbn;
    }

    public void setYgjjtwcjafbn(BigDecimal ygjjtwcjafbn) {
        this.ygjjtwcjafbn = ygjjtwcjafbn;
    }

    public BigDecimal getYgjjtwcjafkl() {
        return ygjjtwcjafkl;
    }

    public void setYgjjtwcjafkl(BigDecimal ygjjtwcjafkl) {
        this.ygjjtwcjafkl = ygjjtwcjafkl;
    }

    public BigDecimal getYgjjtwcjafbl() {
        return ygjjtwcjafbl;
    }

    public void setYgjjtwcjafbl(BigDecimal ygjjtwcjafbl) {
        this.ygjjtwcjafbl = ygjjtwcjafbl;
    }

    public BigDecimal getYgjjtwcjlbq() {
        return ygjjtwcjlbq;
    }

    public void setYgjjtwcjlbq(BigDecimal ygjjtwcjlbq) {
        this.ygjjtwcjlbq = ygjjtwcjlbq;
    }

    public BigDecimal getYgjjtwcjlbn() {
        return ygjjtwcjlbn;
    }

    public void setYgjjtwcjlbn(BigDecimal ygjjtwcjlbn) {
        this.ygjjtwcjlbn = ygjjtwcjlbn;
    }

    public BigDecimal getYgjjtwcjlkl() {
        return ygjjtwcjlkl;
    }

    public void setYgjjtwcjlkl(BigDecimal ygjjtwcjlkl) {
        this.ygjjtwcjlkl = ygjjtwcjlkl;
    }

    public BigDecimal getYgjjtwcjlbl() {
        return ygjjtwcjlbl;
    }

    public void setYgjjtwcjlbl(BigDecimal ygjjtwcjlbl) {
        this.ygjjtwcjlbl = ygjjtwcjlbl;
    }

    public BigDecimal getYgjjtzcjafbq() {
        return ygjjtzcjafbq;
    }

    public void setYgjjtzcjafbq(BigDecimal ygjjtzcjafbq) {
        this.ygjjtzcjafbq = ygjjtzcjafbq;
    }

    public BigDecimal getYgjjtzcjafbn() {
        return ygjjtzcjafbn;
    }

    public void setYgjjtzcjafbn(BigDecimal ygjjtzcjafbn) {
        this.ygjjtzcjafbn = ygjjtzcjafbn;
    }

    public BigDecimal getYgjjtzcjafkl() {
        return ygjjtzcjafkl;
    }

    public void setYgjjtzcjafkl(BigDecimal ygjjtzcjafkl) {
        this.ygjjtzcjafkl = ygjjtzcjafkl;
    }

    public BigDecimal getYgjjtzcjafbl() {
        return ygjjtzcjafbl;
    }

    public void setYgjjtzcjafbl(BigDecimal ygjjtzcjafbl) {
        this.ygjjtzcjafbl = ygjjtzcjafbl;
    }

    public String getBz() {
        return bz == null ? "" : bz.trim();
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getYesOrNoJGC() {
        return yesOrNoJGC == null ? "" : yesOrNoJGC.trim();
    }

    public void setYesOrNoJGC(String yesOrNoJGC) {
        this.yesOrNoJGC = yesOrNoJGC == null ? null : yesOrNoJGC.trim();
    }

    public BigDecimal getWcjafbqJGC() {
        return wcjafbqJGC;
    }

    public void setWcjafbqJGC(BigDecimal wcjafbqJGC) {
        this.wcjafbqJGC = wcjafbqJGC;
    }

    public BigDecimal getWcjafbnJGC() {
        return wcjafbnJGC;
    }

    public void setWcjafbnJGC(BigDecimal wcjafbnJGC) {
        this.wcjafbnJGC = wcjafbnJGC;
    }

    public BigDecimal getWcjafklJGC() {
        return wcjafklJGC;
    }

    public void setWcjafklJGC(BigDecimal wcjafklJGC) {
        this.wcjafklJGC = wcjafklJGC;
    }

    public BigDecimal getWcjafblJGC() {
        return wcjafblJGC;
    }

    public void setWcjafblJGC(BigDecimal wcjafblJGC) {
        this.wcjafblJGC = wcjafblJGC;
    }

    public BigDecimal getXmztwcjlbqJGC() {
        return xmztwcjlbqJGC;
    }

    public void setXmztwcjlbqJGC(BigDecimal xmztwcjlbqJGC) {
        this.xmztwcjlbqJGC = xmztwcjlbqJGC;
    }

    public BigDecimal getXmztwcjlbnJGC() {
        return xmztwcjlbnJGC;
    }

    public void setXmztwcjlbnJGC(BigDecimal xmztwcjlbnJGC) {
        this.xmztwcjlbnJGC = xmztwcjlbnJGC;
    }

    public BigDecimal getXmztwcjlklJGC() {
        return xmztwcjlklJGC;
    }

    public void setXmztwcjlklJGC(BigDecimal xmztwcjlklJGC) {
        this.xmztwcjlklJGC = xmztwcjlklJGC;
    }

    public BigDecimal getXmztwcjlblJGC() {
        return xmztwcjlblJGC;
    }

    public void setXmztwcjlblJGC(BigDecimal xmztwcjlblJGC) {
        this.xmztwcjlblJGC = xmztwcjlblJGC;
    }

    public BigDecimal getZcjafbqJGC() {
        return zcjafbqJGC;
    }

    public void setZcjafbqJGC(BigDecimal zcjafbqJGC) {
        this.zcjafbqJGC = zcjafbqJGC;
    }

    public BigDecimal getZcjafbnJGC() {
        return zcjafbnJGC;
    }

    public void setZcjafbnJGC(BigDecimal zcjafbnJGC) {
        this.zcjafbnJGC = zcjafbnJGC;
    }

    public BigDecimal getZcjafklJGC() {
        return zcjafklJGC;
    }

    public void setZcjafklJGC(BigDecimal zcjafklJGC) {
        this.zcjafklJGC = zcjafklJGC;
    }

    public BigDecimal getZcjafblJGC() {
        return zcjafblJGC;
    }

    public void setZcjafblJGC(BigDecimal zcjafblJGC) {
        this.zcjafblJGC = zcjafblJGC;
    }

    public BigDecimal getYgjwcbqJGC() {
        return ygjwcbqJGC;
    }

    public void setYgjwcbqJGC(BigDecimal ygjwcbqJGC) {
        this.ygjwcbqJGC = ygjwcbqJGC;
    }

    public BigDecimal getYgjwcbnJGC() {
        return ygjwcbnJGC;
    }

    public void setYgjwcbnJGC(BigDecimal ygjwcbnJGC) {
        this.ygjwcbnJGC = ygjwcbnJGC;
    }

    public BigDecimal getYgjwcklJGC() {
        return ygjwcklJGC;
    }

    public void setYgjwcklJGC(BigDecimal ygjwcklJGC) {
        this.ygjwcklJGC = ygjwcklJGC;
    }

    public BigDecimal getYgjwcblJGC() {
        return ygjwcblJGC;
    }

    public void setYgjwcblJGC(BigDecimal ygjwcblJGC) {
        this.ygjwcblJGC = ygjwcblJGC;
    }

    public BigDecimal getYgjjlbqJGC() {
        return ygjjlbqJGC;
    }

    public void setYgjjlbqJGC(BigDecimal ygjjlbqJGC) {
        this.ygjjlbqJGC = ygjjlbqJGC;
    }

    public BigDecimal getYgjjlbnJGC() {
        return ygjjlbnJGC;
    }

    public void setYgjjlbnJGC(BigDecimal ygjjlbnJGC) {
        this.ygjjlbnJGC = ygjjlbnJGC;
    }

    public BigDecimal getYgjjlklJGC() {
        return ygjjlklJGC;
    }

    public void setYgjjlklJGC(BigDecimal ygjjlklJGC) {
        this.ygjjlklJGC = ygjjlklJGC;
    }

    public BigDecimal getYgjjlblJGC() {
        return ygjjlblJGC;
    }

    public void setYgjjlblJGC(BigDecimal ygjjlblJGC) {
        this.ygjjlblJGC = ygjjlblJGC;
    }

    public BigDecimal getQytzwcbq() {
        return qytzwcbq;
    }

    public void setQytzwcbq(BigDecimal qytzwcbq) {
        this.qytzwcbq = qytzwcbq;
    }

    public BigDecimal getQytzwcbn() {
        return qytzwcbn;
    }

    public void setQytzwcbn(BigDecimal qytzwcbn) {
        this.qytzwcbn = qytzwcbn;
    }

    public BigDecimal getQytzwckl() {
        return qytzwckl;
    }

    public void setQytzwckl(BigDecimal qytzwckl) {
        this.qytzwckl = qytzwckl;
    }

    public BigDecimal getQytzwcbl() {
        return qytzwcbl;
    }

    public void setQytzwcbl(BigDecimal qytzwcbl) {
        this.qytzwcbl = qytzwcbl;
    }
    
	public BigDecimal getQytzwcbj() {
		return qytzwcbj;
	}

	public void setQytzwcbj(BigDecimal qytzwcbj) {
		this.qytzwcbj = qytzwcbj;
	}

	public BigDecimal getTzwcbj() {
		return tzwcbj;
	}

	public void setTzwcbj(BigDecimal tzwcbj) {
		this.tzwcbj = tzwcbj;
	}

	public BigDecimal getWcjafbj() {
		return wcjafbj;
	}

	public void setWcjafbj(BigDecimal wcjafbj) {
		this.wcjafbj = wcjafbj;
	}

	public BigDecimal getXmztwcjlbj() {
		return xmztwcjlbj;
	}

	public void setXmztwcjlbj(BigDecimal xmztwcjlbj) {
		this.xmztwcjlbj = xmztwcjlbj;
	}

	public BigDecimal getZcjafbj() {
		return zcjafbj;
	}

	public void setZcjafbj(BigDecimal zcjafbj) {
		this.zcjafbj = zcjafbj;
	}

	public BigDecimal getYgjjtwcjafbj() {
		return ygjjtwcjafbj;
	}

	public void setYgjjtwcjafbj(BigDecimal ygjjtwcjafbj) {
		this.ygjjtwcjafbj = ygjjtwcjafbj;
	}

	public BigDecimal getYgjjtwcjlbj() {
		return ygjjtwcjlbj;
	}

	public void setYgjjtwcjlbj(BigDecimal ygjjtwcjlbj) {
		this.ygjjtwcjlbj = ygjjtwcjlbj;
	}

	public BigDecimal getYgjjtzcjafbj() {
		return ygjjtzcjafbj;
	}

	public void setYgjjtzcjafbj(BigDecimal ygjjtzcjafbj) {
		this.ygjjtzcjafbj = ygjjtzcjafbj;
	}

	public BigDecimal getWczcfbj() {
		return wczcfbj;
	}

	public void setWczcfbj(BigDecimal wczcfbj) {
		this.wczcfbj = wczcfbj;
	}

	public BigDecimal getZczcfbj() {
		return zczcfbj;
	}

	public void setZczcfbj(BigDecimal zczcfbj) {
		this.zczcfbj = zczcfbj;
	}

	public String getProNum() {
		return proNum == null ? "" : proNum.trim();
	}

	public void setProNum(String proNum) {
		this.proNum = proNum == null ? null : proNum.trim();
	}

	public String getProName() {
		return proName == null ? "" : proName.trim();
	}

	public void setProName(String proName) {
		this.proName = proName == null ? null : proName.trim();
	}

	public String getComname() {
		return comname == null ? "" : comname.trim();
	}

	public void setComname(String comname) {
		this.comname = comname == null ? null : comname.trim();
	}

	public String getGldw() {
		return gldw == null ? "" : gldw.trim();
	}

	public void setGldw(String gldw) {
		this.gldw = gldw == null ? null : gldw.trim();
	}

	public BigDecimal getSzgq() {
		return szgq;
	}

	public void setSzgq(BigDecimal szgq) {
		this.szgq = szgq;
	}

	public BigDecimal getGqbl() {
		return gqbl;
	}

	public void setGqbl(BigDecimal gqbl) {
		this.gqbl = gqbl;
	}

	public String getCategoryName() {
		return categoryName == null ? "" : categoryName.trim();
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName == null ? null : categoryName.trim();
	}

	public String getProCategory() {
		return proCategory == null ? "" : proCategory.trim();
	}

	public void setProCategory(String proCategory) {
		this.proCategory = proCategory == null ? null : proCategory.trim();
	}

	public BigDecimal getYgjsgfe() {
		return ygjsgfe;
	}

	public void setYgjsgfe(BigDecimal ygjsgfe) {
		this.ygjsgfe = ygjsgfe;
	}

	public BigDecimal getJafMoney() {
		return jafMoney;
	}

	public void setJafMoney(BigDecimal jafMoney) {
		this.jafMoney = jafMoney;
	}

	public String getPfname() {
		return pfname == null ? "" : pfname.trim();
	}

	public void setPfname(String pfname) {
		this.pfname = pfname == null ? null : pfname.trim();
	}

	public BigDecimal getZcfMoney() {
		return zcfMoney;
	}

	public void setZcfMoney(BigDecimal zcfMoney) {
		this.zcfMoney = zcfMoney;
	}

	public BigDecimal getYgjjaf() {
		return ygjjaf;
	}

	public void setYgjjaf(BigDecimal ygjjaf) {
		this.ygjjaf = ygjjaf;
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public String getTypeName() {
		return typeName == null ? "" : typeName.trim();
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null : typeName.trim();
	}

	public String getOrderNum() {
		return orderNum == null ? "" : orderNum.trim();
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum == null ? null : orderNum.trim();
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

	public String getProId() {
		return proId == null ? "" : proId.trim();
	}

	public void setProId(String proId) {
		this.proId = proId == null ? null : proId.trim();
	}

	public String getExt1() {
		return ext1 == null ? "" : ext1.trim();
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1 == null ? null : ext1.trim();
	}

	public String getUserId() {
		return userId == null ? "" : userId.trim();
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getShortName() {
		return shortName == null ? "" : shortName.trim();
	}

	public void setShortName(String shortName) {
		this.shortName = shortName == null ? null : shortName.trim();
	}

	public BigDecimal getBnqytzbfb() {
		return bnqytzbfb;
	}

	public void setBnqytzbfb(BigDecimal bnqytzbfb) {
		this.bnqytzbfb = bnqytzbfb;
	}

	public BigDecimal getBnjjafbfb() {
		return bnjjafbfb;
	}

	public void setBnjjafbfb(BigDecimal bnjjafbfb) {
		this.bnjjafbfb = bnjjafbfb;
	}

	public BigDecimal getTotal1() {
		return total1;
	}

	public void setTotal1(BigDecimal total1) {
		this.total1 = total1;
	}

	public BigDecimal getTotal2() {
		return total2;
	}

	public void setTotal2(BigDecimal total2) {
		this.total2 = total2;
	}

	public BigDecimal getTotal3() {
		return total3;
	}

	public void setTotal3(BigDecimal total3) {
		this.total3 = total3;
	}

	public BigDecimal getTotal4() {
		return total4;
	}

	public void setTotal4(BigDecimal total4) {
		this.total4 = total4;
	}

	public BigDecimal getBjqytzbfb() {
		return bjqytzbfb;
	}

	public void setBjqytzbfb(BigDecimal bjqytzbfb) {
		this.bjqytzbfb = bjqytzbfb;
	}

	public BigDecimal getBjjjafbfb() {
		return bjjjafbfb;
	}

	public void setBjjjafbfb(BigDecimal bjjjafbfb) {
		this.bjjjafbfb = bjjjafbfb;
	}

	public BigDecimal getSjqytzbfb() {
		return sjqytzbfb;
	}

	public void setSjqytzbfb(BigDecimal sjqytzbfb) {
		this.sjqytzbfb = sjqytzbfb;
	}

	public BigDecimal getSjjjafbfb() {
		return sjjjafbfb;
	}

	public void setSjjjafbfb(BigDecimal sjjjafbfb) {
		this.sjjjafbfb = sjjjafbfb;
	}

	public BigDecimal getBnztzbfb() {
		return bnztzbfb;
	}

	public void setBnztzbfb(BigDecimal bnztzbfb) {
		this.bnztzbfb = bnztzbfb;
	}

	public String getQuarter() {
		return quarter == null ? "" : quarter.trim();
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter == null ? null : quarter.trim();
	}

	public BigDecimal getJhwcqytzbn() {
		return jhwcqytzbn;
	}

	public void setJhwcqytzbn(BigDecimal jhwcqytzbn) {
		this.jhwcqytzbn = jhwcqytzbn;
	}

	public BigDecimal getJhwcjjafbn() {
		return jhwcjjafbn;
	}

	public void setJhwcjjafbn(BigDecimal jhwcjjafbn) {
		this.jhwcjjafbn = jhwcjjafbn;
	}

	public BigDecimal getJhwcztzbn() {
		return jhwcztzbn;
	}

	public void setJhwcztzbn(BigDecimal jhwcztzbn) {
		this.jhwcztzbn = jhwcztzbn;
	}

	public BigDecimal getJhwcjafbn() {
		return jhwcjafbn;
	}

	public void setJhwcjafbn(BigDecimal jhwcjafbn) {
		this.jhwcjafbn = jhwcjafbn;
	}

	public BigDecimal getJhwcqytzbj() {
		return jhwcqytzbj;
	}

	public void setJhwcqytzbj(BigDecimal jhwcqytzbj) {
		this.jhwcqytzbj = jhwcqytzbj;
	}

	public BigDecimal getJhwcjjafbj() {
		return jhwcjjafbj;
	}

	public void setJhwcjjafbj(BigDecimal jhwcjjafbj) {
		this.jhwcjjafbj = jhwcjjafbj;
	}

	public BigDecimal getJhwcztzbj() {
		return jhwcztzbj;
	}

	public void setJhwcztzbj(BigDecimal jhwcztzbj) {
		this.jhwcztzbj = jhwcztzbj;
	}

	public BigDecimal getJhwcjafbj() {
		return jhwcjafbj;
	}

	public void setJhwcjafbj(BigDecimal jhwcjafbj) {
		this.jhwcjafbj = jhwcjafbj;
	}

	public String getSortType() {
		return sortType == null ? "" : sortType.trim();
	}

	public void setSortType(String sortType) {
		this.sortType = sortType == null ? null : sortType.trim();
	}

	public String getDataType() {
		return dataType == null ? "" : dataType.trim();
	}

	public void setDataType(String dataType) {
		this.dataType = dataType == null ? null : dataType.trim();
	}

	public String getEndTime() {
		return endTime == null ? "" : endTime.trim();
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}

	public String getType() {
		return type == null ? "" : type.trim();
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public BigDecimal getInvCount() {
		return invCount;
	}

	public void setInvCount(BigDecimal invCount) {
		this.invCount = invCount;
	}

	public BigDecimal getXmzht() {
		return xmzht;
	}

	public void setXmzht(BigDecimal xmzht) {
		this.xmzht = xmzht;
	}

	public BigDecimal getXmzja() {
		return xmzja;
	}

	public void setXmzja(BigDecimal xmzja) {
		this.xmzja = xmzja;
	}

	public BigDecimal getZht() {
		return zht;
	}

	public void setZht(BigDecimal zht) {
		this.zht = zht;
	}

	public BigDecimal getZja() {
		return zja;
	}

	public void setZja(BigDecimal zja) {
		this.zja = zja;
	}

	public String getTjType() {
		return tjType == null ? "" : tjType.trim();
	}

	public void setTjType(String tjType) {
		this.tjType = tjType == null ? null : tjType.trim();
	}

	public BigDecimal getZtzwcbn() {
		return ztzwcbn;
	}

	public void setZtzwcbn(BigDecimal ztzwcbn) {
		this.ztzwcbn = ztzwcbn;
	}

	public BigDecimal getTzwcbhl() {
		return tzwcbhl;
	}

	public void setTzwcbhl(BigDecimal tzwcbhl) {
		this.tzwcbhl = tzwcbhl;
	}

	public BigDecimal getZwcjafbn() {
		return zwcjafbn;
	}

	public void setZwcjafbn(BigDecimal zwcjafbn) {
		this.zwcjafbn = zwcjafbn;
	}

	public BigDecimal getWcjafbhl() {
		return wcjafbhl;
	}

	public void setWcjafbhl(BigDecimal wcjafbhl) {
		this.wcjafbhl = wcjafbhl;
	}

	public String getAreaName() {
		return areaName  == null ? "" : areaName.trim();
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName == null ? null : areaName.trim();
	}

	public BigDecimal getWtzwckl() {
		return wtzwckl;
	}

	public void setWtzwckl(BigDecimal wtzwckl) {
		this.wtzwckl = wtzwckl;
	}

	public BigDecimal getWwcjafkl() {
		return wwcjafkl;
	}

	public void setWwcjafkl(BigDecimal wwcjafkl) {
		this.wwcjafkl = wwcjafkl;
	}

	public BigDecimal getWtzwcbn() {
		return wtzwcbn;
	}

	public void setWtzwcbn(BigDecimal wtzwcbn) {
		this.wtzwcbn = wtzwcbn;
	}

	public BigDecimal getWwcjafbn() {
		return wwcjafbn;
	}

	public void setWwcjafbn(BigDecimal wwcjafbn) {
		this.wwcjafbn = wwcjafbn;
	}

	public BigDecimal getWtzwcbj() {
		return wtzwcbj;
	}

	public void setWtzwcbj(BigDecimal wtzwcbj) {
		this.wtzwcbj = wtzwcbj;
	}

	public BigDecimal getWwcjafbj() {
		return wwcjafbj;
	}

	public void setWwcjafbj(BigDecimal wwcjafbj) {
		this.wwcjafbj = wwcjafbj;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public BigDecimal getAmount3() {
		return amount3;
	}

	public void setAmount3(BigDecimal amount3) {
		this.amount3 = amount3;
	}

	public String getProProcessId() {
		return proProcessId == null ? "" : proProcessId.trim();
	}

	public void setProProcessId(String proProcessId) {
		this.proProcessId = proProcessId == null ? null : proProcessId.trim();
	}

	public String getData() {
		return data == null ? "" : data.trim();
	}

	public void setData(String data) {
		this.data = data == null ? null : data.trim();
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

	public BigDecimal getSjztzbfb() {
		return sjztzbfb;
	}

	public void setSjztzbfb(BigDecimal sjztzbfb) {
		this.sjztzbfb = sjztzbfb;
	}

	public BigDecimal getSjjafbfb() {
		return sjjafbfb;
	}

	public void setSjjafbfb(BigDecimal sjjafbfb) {
		this.sjjafbfb = sjjafbfb;
	}

	public BigDecimal getSnztzbfb() {
		return snztzbfb;
	}

	public void setSnztzbfb(BigDecimal snztzbfb) {
		this.snztzbfb = snztzbfb;
	}

	public BigDecimal getSnjafbfb() {
		return snjafbfb;
	}

	public void setSnjafbfb(BigDecimal snjafbfb) {
		this.snjafbfb = snjafbfb;
	}

	public String getWarnSelect() {
		return warnSelect == null ? "" : warnSelect.trim();
	}

	public void setWarnSelect(String warnSelect) {
		this.warnSelect = warnSelect == null ? null : warnSelect.trim();
	}

	public String getColourFlag() {
		return colourFlag == null ? "" : colourFlag.trim();
	}

	public void setColourFlag(String colourFlag) {
		this.colourFlag = colourFlag == null ? null : colourFlag.trim();
	}

	public String getWarnType() {
		return warnType == null ? "" : warnType.trim();
	}

	public void setWarnType(String warnType) {
		this.warnType = warnType == null ? null : warnType.trim();
	}

	public String getN() {
		return n == null ? "" : n.trim();
	}

	public void setN(String n) {
		this.n = n == null ? null : n.trim();
	}

	public String getJidu() {
		return jidu == null ? "" : jidu.trim();
	}

	public void setJidu(String jidu) {
		this.jidu = jidu == null ? null : jidu.trim();
	}

	public ZjTzInvXmyyqk getZjTzInvXmyyqk() {
		return zjTzInvXmyyqk;
	}

	public void setZjTzInvXmyyqk(ZjTzInvXmyyqk zjTzInvXmyyqk) {
		this.zjTzInvXmyyqk = zjTzInvXmyyqk;
	}

	public ZjTzInvXmhgqk getZjTzInvXmhgqk() {
		return zjTzInvXmhgqk;
	}

	public void setZjTzInvXmhgqk(ZjTzInvXmhgqk zjTzInvXmhgqk) {
		this.zjTzInvXmhgqk = zjTzInvXmhgqk;
	}

	public BigDecimal getBnjhwcqytz() {
		return bnjhwcqytz;
	}

	public void setBnjhwcqytz(BigDecimal bnjhwcqytz) {
		this.bnjhwcqytz = bnjhwcqytz;
	}

	public BigDecimal getYgjbnjhwcja() {
		return ygjbnjhwcja;
	}

	public void setYgjbnjhwcja(BigDecimal ygjbnjhwcja) {
		this.ygjbnjhwcja = ygjbnjhwcja;
	}

	public BigDecimal getBnjhwcztz() {
		return bnjhwcztz;
	}

	public void setBnjhwcztz(BigDecimal bnjhwcztz) {
		this.bnjhwcztz = bnjhwcztz;
	}

	public BigDecimal getBnjhwcjaf() {
		return bnjhwcjaf;
	}

	public void setBnjhwcjaf(BigDecimal bnjhwcjaf) {
		this.bnjhwcjaf = bnjhwcjaf;
	}

	public BigDecimal getBnamount1() {
		return bnamount1;
	}

	public void setBnamount1(BigDecimal bnamount1) {
		this.bnamount1 = bnamount1;
	}

	public BigDecimal getKlamount1() {
		return klamount1;
	}

	public void setKlamount1(BigDecimal klamount1) {
		this.klamount1 = klamount1;
	}

	public BigDecimal getBnamount2() {
		return bnamount2;
	}

	public void setBnamount2(BigDecimal bnamount2) {
		this.bnamount2 = bnamount2;
	}

	public BigDecimal getKlamount2() {
		return klamount2;
	}

	public void setKlamount2(BigDecimal klamount2) {
		this.klamount2 = klamount2;
	}

	public BigDecimal getTbamount() {
		return tbamount;
	}

	public void setTbamount(BigDecimal tbamount) {
		this.tbamount = tbamount;
	}

	public BigDecimal getTbqyamount() {
		return tbqyamount;
	}

	public void setTbqyamount(BigDecimal tbqyamount) {
		this.tbqyamount = tbqyamount;
	}

	public BigDecimal getTbtzwc() {
		return tbtzwc;
	}

	public void setTbtzwc(BigDecimal tbtzwc) {
		this.tbtzwc = tbtzwc;
	}

	public BigDecimal getTbqytzwc() {
		return tbqytzwc;
	}

	public void setTbqytzwc(BigDecimal tbqytzwc) {
		this.tbqytzwc = tbqytzwc;
	}

	public int getBjqytzpm() {
		return bjqytzpm;
	}

	public void setBjqytzpm(int bjqytzpm) {
		this.bjqytzpm = bjqytzpm;
	}

	public int getSjqytzpm() {
		return sjqytzpm;
	}

	public void setSjqytzpm(int sjqytzpm) {
		this.sjqytzpm = sjqytzpm;
	}

	public int getPmztz() {
		return pmztz;
	}

	public void setPmztz(int pmztz) {
		this.pmztz = pmztz;
	}

	public int getBjjjafpm() {
		return bjjjafpm;
	}

	public void setBjjjafpm(int bjjjafpm) {
		this.bjjjafpm = bjjjafpm;
	}

	public int getSjjjafpm() {
		return sjjjafpm;
	}

	public void setSjjjafpm(int sjjjafpm) {
		this.sjjjafpm = sjjjafpm;
	}
	
	
}

