package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzInvHtInfo extends BasePojo {
    private String id;

    private String invProId;

    private String agreementName;

    private String agreementNum;

    private String dyqyBrand;

    private String invXyjf;

    private String invXyyf;

    private String htbf;

    private String htdf;

    private Date htqdDate;

    private Date sbjtsj;

    private Date nrxtglsj;

    private Date kgDate;

    private Date sjkgrq;

    private BigDecimal jafMoney;

    private BigDecimal bcjafMoney;

    private BigDecimal zcfMoney;

    private BigDecimal bczcfMoney;

    private BigDecimal glfMoney;

    private BigDecimal bcglfMoney;

    private BigDecimal jlfMoney;

    private BigDecimal bcjlfMoney;

    private BigDecimal othMoney;

    private BigDecimal bcothMoney;

    private BigDecimal ztze;

    private Integer htgq;

    private BigDecimal bchhte;

    private BigDecimal bchhtgq;

    private BigDecimal zbjbl;

    private BigDecimal zyzjbl;

    private BigDecimal yhjdbl;

    private BigDecimal buildDate;

    private BigDecimal yyhgDate;

    private String yylc;

    private String currency;

    private String createBy;

    private String createOrg;

    private Date createDate;

    private String updateBy;

    private String updateOrg;

    private Date updateDate;

    private String remarks;

    private String delFlag;

    private BigDecimal ccsjfMoney;

    private BigDecimal bcccsjfMoney;

    private Date htjgrq;

    private Date ydkshgrq;

    private BigDecimal jjbl;

    private BigDecimal qtgdzjbl;

    private BigDecimal qtzjbl;

    private BigDecimal rzbl;

    private BigDecimal rzJjbl;

    private BigDecimal rzYgjrgjjbl;

    private BigDecimal rzXtdqt;

    private BigDecimal zfbtbl;

    private BigDecimal zbjYgjrgjjbl;

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

    public String getAgreementName() {
        return agreementName == null ? "" : agreementName.trim();
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName == null ? null : agreementName.trim();
    }

    public String getAgreementNum() {
        return agreementNum == null ? "" : agreementNum.trim();
    }

    public void setAgreementNum(String agreementNum) {
        this.agreementNum = agreementNum == null ? null : agreementNum.trim();
    }

    public String getDyqyBrand() {
        return dyqyBrand == null ? "" : dyqyBrand.trim();
    }

    public void setDyqyBrand(String dyqyBrand) {
        this.dyqyBrand = dyqyBrand == null ? null : dyqyBrand.trim();
    }

    public String getInvXyjf() {
        return invXyjf == null ? "" : invXyjf.trim();
    }

    public void setInvXyjf(String invXyjf) {
        this.invXyjf = invXyjf == null ? null : invXyjf.trim();
    }

    public String getInvXyyf() {
        return invXyyf == null ? "" : invXyyf.trim();
    }

    public void setInvXyyf(String invXyyf) {
        this.invXyyf = invXyyf == null ? null : invXyyf.trim();
    }

    public String getHtbf() {
        return htbf == null ? "" : htbf.trim();
    }

    public void setHtbf(String htbf) {
        this.htbf = htbf == null ? null : htbf.trim();
    }

    public String getHtdf() {
        return htdf == null ? "" : htdf.trim();
    }

    public void setHtdf(String htdf) {
        this.htdf = htdf == null ? null : htdf.trim();
    }

    public Date getHtqdDate() {
        return htqdDate;
    }

    public void setHtqdDate(Date htqdDate) {
        this.htqdDate = htqdDate;
    }

    public Date getSbjtsj() {
        return sbjtsj;
    }

    public void setSbjtsj(Date sbjtsj) {
        this.sbjtsj = sbjtsj;
    }

    public Date getNrxtglsj() {
        return nrxtglsj;
    }

    public void setNrxtglsj(Date nrxtglsj) {
        this.nrxtglsj = nrxtglsj;
    }

    public Date getKgDate() {
        return kgDate;
    }

    public void setKgDate(Date kgDate) {
        this.kgDate = kgDate;
    }

    public Date getSjkgrq() {
        return sjkgrq;
    }

    public void setSjkgrq(Date sjkgrq) {
        this.sjkgrq = sjkgrq;
    }

    public BigDecimal getJafMoney() {
        return jafMoney;
    }

    public void setJafMoney(BigDecimal jafMoney) {
        this.jafMoney = jafMoney;
    }

    public BigDecimal getBcjafMoney() {
        return bcjafMoney;
    }

    public void setBcjafMoney(BigDecimal bcjafMoney) {
        this.bcjafMoney = bcjafMoney;
    }

    public BigDecimal getZcfMoney() {
        return zcfMoney;
    }

    public void setZcfMoney(BigDecimal zcfMoney) {
        this.zcfMoney = zcfMoney;
    }

    public BigDecimal getBczcfMoney() {
        return bczcfMoney;
    }

    public void setBczcfMoney(BigDecimal bczcfMoney) {
        this.bczcfMoney = bczcfMoney;
    }

    public BigDecimal getGlfMoney() {
        return glfMoney;
    }

    public void setGlfMoney(BigDecimal glfMoney) {
        this.glfMoney = glfMoney;
    }

    public BigDecimal getBcglfMoney() {
        return bcglfMoney;
    }

    public void setBcglfMoney(BigDecimal bcglfMoney) {
        this.bcglfMoney = bcglfMoney;
    }

    public BigDecimal getJlfMoney() {
        return jlfMoney;
    }

    public void setJlfMoney(BigDecimal jlfMoney) {
        this.jlfMoney = jlfMoney;
    }

    public BigDecimal getBcjlfMoney() {
        return bcjlfMoney;
    }

    public void setBcjlfMoney(BigDecimal bcjlfMoney) {
        this.bcjlfMoney = bcjlfMoney;
    }

    public BigDecimal getOthMoney() {
        return othMoney;
    }

    public void setOthMoney(BigDecimal othMoney) {
        this.othMoney = othMoney;
    }

    public BigDecimal getBcothMoney() {
        return bcothMoney;
    }

    public void setBcothMoney(BigDecimal bcothMoney) {
        this.bcothMoney = bcothMoney;
    }

    public BigDecimal getZtze() {
        return ztze;
    }

    public void setZtze(BigDecimal ztze) {
        this.ztze = ztze;
    }

    public Integer getHtgq() {
        return htgq;
    }

    public void setHtgq(Integer htgq) {
        this.htgq = htgq;
    }

    public BigDecimal getBchhte() {
        return bchhte;
    }

    public void setBchhte(BigDecimal bchhte) {
        this.bchhte = bchhte;
    }

    public BigDecimal getBchhtgq() {
        return bchhtgq;
    }

    public void setBchhtgq(BigDecimal bchhtgq) {
        this.bchhtgq = bchhtgq;
    }

    public BigDecimal getZbjbl() {
        return zbjbl;
    }

    public void setZbjbl(BigDecimal zbjbl) {
        this.zbjbl = zbjbl;
    }

    public BigDecimal getZyzjbl() {
        return zyzjbl;
    }

    public void setZyzjbl(BigDecimal zyzjbl) {
        this.zyzjbl = zyzjbl;
    }

    public BigDecimal getYhjdbl() {
        return yhjdbl;
    }

    public void setYhjdbl(BigDecimal yhjdbl) {
        this.yhjdbl = yhjdbl;
    }

    public BigDecimal getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(BigDecimal buildDate) {
        this.buildDate = buildDate;
    }

    public BigDecimal getYyhgDate() {
        return yyhgDate;
    }

    public void setYyhgDate(BigDecimal yyhgDate) {
        this.yyhgDate = yyhgDate;
    }

    public String getYylc() {
        return yylc == null ? "" : yylc.trim();
    }

    public void setYylc(String yylc) {
        this.yylc = yylc == null ? null : yylc.trim();
    }

    public String getCurrency() {
        return currency == null ? "" : currency.trim();
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
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

    public BigDecimal getCcsjfMoney() {
        return ccsjfMoney;
    }

    public void setCcsjfMoney(BigDecimal ccsjfMoney) {
        this.ccsjfMoney = ccsjfMoney;
    }

    public BigDecimal getBcccsjfMoney() {
        return bcccsjfMoney;
    }

    public void setBcccsjfMoney(BigDecimal bcccsjfMoney) {
        this.bcccsjfMoney = bcccsjfMoney;
    }

    public Date getHtjgrq() {
        return htjgrq;
    }

    public void setHtjgrq(Date htjgrq) {
        this.htjgrq = htjgrq;
    }

    public Date getYdkshgrq() {
        return ydkshgrq;
    }

    public void setYdkshgrq(Date ydkshgrq) {
        this.ydkshgrq = ydkshgrq;
    }

    public BigDecimal getJjbl() {
        return jjbl;
    }

    public void setJjbl(BigDecimal jjbl) {
        this.jjbl = jjbl;
    }

    public BigDecimal getQtgdzjbl() {
        return qtgdzjbl;
    }

    public void setQtgdzjbl(BigDecimal qtgdzjbl) {
        this.qtgdzjbl = qtgdzjbl;
    }

    public BigDecimal getQtzjbl() {
        return qtzjbl;
    }

    public void setQtzjbl(BigDecimal qtzjbl) {
        this.qtzjbl = qtzjbl;
    }

    public BigDecimal getRzbl() {
        return rzbl;
    }

    public void setRzbl(BigDecimal rzbl) {
        this.rzbl = rzbl;
    }

    public BigDecimal getRzJjbl() {
        return rzJjbl;
    }

    public void setRzJjbl(BigDecimal rzJjbl) {
        this.rzJjbl = rzJjbl;
    }

    public BigDecimal getRzYgjrgjjbl() {
        return rzYgjrgjjbl;
    }

    public void setRzYgjrgjjbl(BigDecimal rzYgjrgjjbl) {
        this.rzYgjrgjjbl = rzYgjrgjjbl;
    }

    public BigDecimal getRzXtdqt() {
        return rzXtdqt;
    }

    public void setRzXtdqt(BigDecimal rzXtdqt) {
        this.rzXtdqt = rzXtdqt;
    }

    public BigDecimal getZfbtbl() {
        return zfbtbl;
    }

    public void setZfbtbl(BigDecimal zfbtbl) {
        this.zfbtbl = zfbtbl;
    }

    public BigDecimal getZbjYgjrgjjbl() {
        return zbjYgjrgjjbl;
    }

    public void setZbjYgjrgjjbl(BigDecimal zbjYgjrgjjbl) {
        this.zbjYgjrgjjbl = zbjYgjrgjjbl;
    }

}

