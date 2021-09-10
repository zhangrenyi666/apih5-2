package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehTaskCensusItem extends BasePojo {
    // 主键
    private String zjLzehTaskCensusItemId;

    // 任务统计ID
    private String zjLzehTaskCensusId;

    // 人员姓名
    private String personName;

    // 临时任务数
    private Integer allNum;

    // 临时完成任务数
    private Integer cNum;

    // 临时未完成任务数
    private Integer num;

    // 经营任务数
    private Integer mallNum;

    // 经营完成任务数
    private Integer mcNum;

    // 经营未完成任务
    private Integer mnum;

    // 生产任务数
    private Integer pallNum;

    // 生产完成任务数
    private Integer pcNum;

    // 生产未完成任务数
    private Integer pnum;

    // 所有任务数
    private Integer allqty;

    // 所有完成任务数
    private Integer cqty;

    // 所有未完成任务数
    private Integer qty;

    // 任务完成比例
    private BigDecimal cRate;

    // 序号/排名
    private int xuhao=0;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private Date cenMonth;

    private BigDecimal avgRate;

    public BigDecimal getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(BigDecimal avgRate) {
        this.avgRate = avgRate;
    }

    public Date getCenMonth() {
        return cenMonth;
    }

    public void setCenMonth(Date cenMonth) {
        this.cenMonth = cenMonth;
    }

    public String getZjLzehTaskCensusItemId() {
        return zjLzehTaskCensusItemId == null ? "" : zjLzehTaskCensusItemId.trim();
    }

    public void setZjLzehTaskCensusItemId(String zjLzehTaskCensusItemId) {
        this.zjLzehTaskCensusItemId = zjLzehTaskCensusItemId == null ? null : zjLzehTaskCensusItemId.trim();
    }

    public String getZjLzehTaskCensusId() {
        return zjLzehTaskCensusId == null ? "" : zjLzehTaskCensusId.trim();
    }

    public void setZjLzehTaskCensusId(String zjLzehTaskCensusId) {
        this.zjLzehTaskCensusId = zjLzehTaskCensusId == null ? null : zjLzehTaskCensusId.trim();
    }

    public String getPersonName() {
        return personName == null ? "" : personName.trim();
    }

    public void setPersonName(String personName) {
        this.personName = personName == null ? null : personName.trim();
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Integer getcNum() {
        return cNum;
    }

    public void setcNum(Integer cNum) {
        this.cNum = cNum;
    }

    public BigDecimal getcRate() {
        return cRate;
    }

    public void setcRate(BigDecimal cRate) {
        this.cRate = cRate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getMallNum() {
        return mallNum;
    }

    public void setMallNum(Integer mallNum) {
        this.mallNum = mallNum;
    }

    public Integer getMcNum() {
        return mcNum;
    }

    public void setMcNum(Integer mcNum) {
        this.mcNum = mcNum;
    }

    public Integer getMnum() {
        return mnum;
    }

    public void setMnum(Integer mnum) {
        this.mnum = mnum;
    }

    public Integer getPallNum() {
        return pallNum;
    }

    public void setPallNum(Integer pallNum) {
        this.pallNum = pallNum;
    }

    public Integer getPcNum() {
        return pcNum;
    }

    public void setPcNum(Integer pcNum) {
        this.pcNum = pcNum;
    }

    public Integer getPnum() {
        return pnum;
    }

    public void setPnum(Integer pnum) {
        this.pnum = pnum;
    }

    public Integer getAllqty() {
        return allqty;
    }

    public void setAllqty(Integer allqty) {
        this.allqty = allqty;
    }

    public Integer getCqty() {
        return cqty;
    }

    public void setCqty(Integer cqty) {
        this.cqty = cqty;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }


    public int getXuhao() {
        return xuhao;
    }

    public void setXuhao(int xuhao) {
        this.xuhao = xuhao;
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
