package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzInvComInf extends BasePojo {
    private String id;

    private String proComName;

    private BigDecimal zczj;

    private String registerArea;

    private String fddbr;

    private Date registerDate;

    private String comZjl;

    private String zjlTel;

    private String comCwfzr;

    private String cwTel;

    private BigDecimal szgq;

    private String gldw;

    private String yyzz;

    private String gszc;

    private String createBy;

    private String createOrg;

    private Date createDate;

    private String updateBy;

    private String updateOrg;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    private String invProId;

    private String xmmc;

    private String wfgddbmc;

    private BigDecimal zgjjszgq;

    private String yyzzh;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProComName() {
        return proComName == null ? "" : proComName.trim();
    }

    public void setProComName(String proComName) {
        this.proComName = proComName == null ? null : proComName.trim();
    }

    public BigDecimal getZczj() {
        return zczj;
    }

    public void setZczj(BigDecimal zczj) {
        this.zczj = zczj;
    }

    public String getRegisterArea() {
        return registerArea == null ? "" : registerArea.trim();
    }

    public void setRegisterArea(String registerArea) {
        this.registerArea = registerArea == null ? null : registerArea.trim();
    }

    public String getFddbr() {
        return fddbr == null ? "" : fddbr.trim();
    }

    public void setFddbr(String fddbr) {
        this.fddbr = fddbr == null ? null : fddbr.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getComZjl() {
        return comZjl == null ? "" : comZjl.trim();
    }

    public void setComZjl(String comZjl) {
        this.comZjl = comZjl == null ? null : comZjl.trim();
    }

    public String getZjlTel() {
        return zjlTel == null ? "" : zjlTel.trim();
    }

    public void setZjlTel(String zjlTel) {
        this.zjlTel = zjlTel == null ? null : zjlTel.trim();
    }

    public String getComCwfzr() {
        return comCwfzr == null ? "" : comCwfzr.trim();
    }

    public void setComCwfzr(String comCwfzr) {
        this.comCwfzr = comCwfzr == null ? null : comCwfzr.trim();
    }

    public String getCwTel() {
        return cwTel == null ? "" : cwTel.trim();
    }

    public void setCwTel(String cwTel) {
        this.cwTel = cwTel == null ? null : cwTel.trim();
    }

    public BigDecimal getSzgq() {
        return szgq;
    }

    public void setSzgq(BigDecimal szgq) {
        this.szgq = szgq;
    }

    public String getGldw() {
        return gldw == null ? "" : gldw.trim();
    }

    public void setGldw(String gldw) {
        this.gldw = gldw == null ? null : gldw.trim();
    }

    public String getYyzz() {
        return yyzz == null ? "" : yyzz.trim();
    }

    public void setYyzz(String yyzz) {
        this.yyzz = yyzz == null ? null : yyzz.trim();
    }

    public String getGszc() {
        return gszc == null ? "" : gszc.trim();
    }

    public void setGszc(String gszc) {
        this.gszc = gszc == null ? null : gszc.trim();
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

    public String getInvProId() {
        return invProId == null ? "" : invProId.trim();
    }

    public void setInvProId(String invProId) {
        this.invProId = invProId == null ? null : invProId.trim();
    }

    public String getXmmc() {
        return xmmc == null ? "" : xmmc.trim();
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc == null ? null : xmmc.trim();
    }

    public String getWfgddbmc() {
        return wfgddbmc == null ? "" : wfgddbmc.trim();
    }

    public void setWfgddbmc(String wfgddbmc) {
        this.wfgddbmc = wfgddbmc == null ? null : wfgddbmc.trim();
    }

    public BigDecimal getZgjjszgq() {
        return zgjjszgq;
    }

    public void setZgjjszgq(BigDecimal zgjjszgq) {
        this.zgjjszgq = zgjjszgq;
    }

    public String getYyzzh() {
        return yyzzh == null ? "" : yyzzh.trim();
    }

    public void setYyzzh(String yyzzh) {
        this.yyzzh = yyzzh == null ? null : yyzzh.trim();
    }

}

