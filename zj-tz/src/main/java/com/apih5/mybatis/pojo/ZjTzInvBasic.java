package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzInvBasic extends BasePojo {
    private String id;

    private String proName;

    private String proNum;

    private String proCategory;

    private String proInvCategory;

    private String isReport;

    private String hzModel;

    private String invModel;

    private String proArea;

    private String gmjjhyCategory;

    private String dyqyBrand;

    private BigDecimal estimateInv;

    private String proBuildXz;

    private String invDirection;

    private String zbf;

    private String isPppPro;

    private String isRk;

    private String isZjkg;

    private String isYgjkg;

    private BigDecimal proZbjbl;

    private String proZjly;

    private String isSpvCom;

    private String proContent;

    private String mainInvCondition;

    private String currency;

    private String createBy;

    private String createOrg;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String updateOrg;

    private String remarks;

    private String delFlag;

    private String column01;

    private String column02;

    private String column03;

    private String column04;

    private String column05;

    private BigDecimal zyzjbl;

    private BigDecimal yhdkbl;

    private String gzxxId;

    private BigDecimal zjpftze;

    private Date pfsj;

    private String pfwh;

    private BigDecimal jsnx;

    private BigDecimal yynx;

    private BigDecimal hgnx;

    private BigDecimal kfzq;

    private BigDecimal kfmj;

    private String org;

    private String gclb;

    private Date xmzzsj;

    private String shortName;

    private String isBb;

    private String ffms;

    private BigDecimal gllxcd;

    private BigDecimal glgsglcd;

    private BigDecimal glqlcd;

    private BigDecimal glsddd;

    private Integer gltdq;

    private Integer gldq;

    private Integer glzq;

    private BigDecimal qlqlcd;

    private Integer qltdq;

    private Integer qldq;

    private Integer qlzq;

    private BigDecimal sdsddd;

    private Integer gkmtbw;

    private Integer gkssbw;

    private BigDecimal gkmtbwcd;

    private BigDecimal wkzdmj;

    private BigDecimal gdlxcd;

    private BigDecimal tllxcd;

    private Integer jcjc;

    private Integer cyyqyq;

    private BigDecimal cyyqyqghmj;

    private String sybhf;

    private String zfcnyhtj;

    private BigDecimal zbjzykhzb;

    private String xmjd;

    private String sfrk;

    private Date rksj;

    private Date ygjjtpfrq;

    private Date sjkgrq;

    private Date jgrq;

    private Date kshgyyrq;

    private Date jsrq;

    private Date zbrq;

    private BigDecimal pgjdjcsy;

    private BigDecimal pgjdtrzsy;

    private BigDecimal cnmbjcsy;

    private BigDecimal cnmutrzsy;

    private BigDecimal tpyjcltxsl;

    private BigDecimal tpyjsr;

    private BigDecimal zhjcsyl;

    private String bz;

    private BigDecimal xmcwnbsyl;

    private BigDecimal zbjcwnbsyl;

    private BigDecimal dttzhsqn;

    private BigDecimal pgsjdjcsyl;

    private BigDecimal sglr;

    private BigDecimal tszhjcsyl;

    private BigDecimal ysjjcsyl;

    private String sftzxm;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProName() {
        return proName == null ? "" : proName.trim();
    }

    public void setProName(String proName) {
        this.proName = proName == null ? null : proName.trim();
    }

    public String getProNum() {
        return proNum == null ? "" : proNum.trim();
    }

    public void setProNum(String proNum) {
        this.proNum = proNum == null ? null : proNum.trim();
    }

    public String getProCategory() {
        return proCategory == null ? "" : proCategory.trim();
    }

    public void setProCategory(String proCategory) {
        this.proCategory = proCategory == null ? null : proCategory.trim();
    }

    public String getProInvCategory() {
        return proInvCategory == null ? "" : proInvCategory.trim();
    }

    public void setProInvCategory(String proInvCategory) {
        this.proInvCategory = proInvCategory == null ? null : proInvCategory.trim();
    }

    public String getIsReport() {
        return isReport == null ? "" : isReport.trim();
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport == null ? null : isReport.trim();
    }

    public String getHzModel() {
        return hzModel == null ? "" : hzModel.trim();
    }

    public void setHzModel(String hzModel) {
        this.hzModel = hzModel == null ? null : hzModel.trim();
    }

    public String getInvModel() {
        return invModel == null ? "" : invModel.trim();
    }

    public void setInvModel(String invModel) {
        this.invModel = invModel == null ? null : invModel.trim();
    }

    public String getProArea() {
        return proArea == null ? "" : proArea.trim();
    }

    public void setProArea(String proArea) {
        this.proArea = proArea == null ? null : proArea.trim();
    }

    public String getGmjjhyCategory() {
        return gmjjhyCategory == null ? "" : gmjjhyCategory.trim();
    }

    public void setGmjjhyCategory(String gmjjhyCategory) {
        this.gmjjhyCategory = gmjjhyCategory == null ? null : gmjjhyCategory.trim();
    }

    public String getDyqyBrand() {
        return dyqyBrand == null ? "" : dyqyBrand.trim();
    }

    public void setDyqyBrand(String dyqyBrand) {
        this.dyqyBrand = dyqyBrand == null ? null : dyqyBrand.trim();
    }

    public BigDecimal getEstimateInv() {
        return estimateInv;
    }

    public void setEstimateInv(BigDecimal estimateInv) {
        this.estimateInv = estimateInv;
    }

    public String getProBuildXz() {
        return proBuildXz == null ? "" : proBuildXz.trim();
    }

    public void setProBuildXz(String proBuildXz) {
        this.proBuildXz = proBuildXz == null ? null : proBuildXz.trim();
    }

    public String getInvDirection() {
        return invDirection == null ? "" : invDirection.trim();
    }

    public void setInvDirection(String invDirection) {
        this.invDirection = invDirection == null ? null : invDirection.trim();
    }

    public String getZbf() {
        return zbf == null ? "" : zbf.trim();
    }

    public void setZbf(String zbf) {
        this.zbf = zbf == null ? null : zbf.trim();
    }

    public String getIsPppPro() {
        return isPppPro == null ? "" : isPppPro.trim();
    }

    public void setIsPppPro(String isPppPro) {
        this.isPppPro = isPppPro == null ? null : isPppPro.trim();
    }

    public String getIsRk() {
        return isRk == null ? "" : isRk.trim();
    }

    public void setIsRk(String isRk) {
        this.isRk = isRk == null ? null : isRk.trim();
    }

    public String getIsZjkg() {
        return isZjkg == null ? "" : isZjkg.trim();
    }

    public void setIsZjkg(String isZjkg) {
        this.isZjkg = isZjkg == null ? null : isZjkg.trim();
    }

    public String getIsYgjkg() {
        return isYgjkg == null ? "" : isYgjkg.trim();
    }

    public void setIsYgjkg(String isYgjkg) {
        this.isYgjkg = isYgjkg == null ? null : isYgjkg.trim();
    }

    public BigDecimal getProZbjbl() {
        return proZbjbl;
    }

    public void setProZbjbl(BigDecimal proZbjbl) {
        this.proZbjbl = proZbjbl;
    }

    public String getProZjly() {
        return proZjly == null ? "" : proZjly.trim();
    }

    public void setProZjly(String proZjly) {
        this.proZjly = proZjly == null ? null : proZjly.trim();
    }

    public String getIsSpvCom() {
        return isSpvCom == null ? "" : isSpvCom.trim();
    }

    public void setIsSpvCom(String isSpvCom) {
        this.isSpvCom = isSpvCom == null ? null : isSpvCom.trim();
    }

    public String getProContent() {
        return proContent == null ? "" : proContent.trim();
    }

    public void setProContent(String proContent) {
        this.proContent = proContent == null ? null : proContent.trim();
    }

    public String getMainInvCondition() {
        return mainInvCondition == null ? "" : mainInvCondition.trim();
    }

    public void setMainInvCondition(String mainInvCondition) {
        this.mainInvCondition = mainInvCondition == null ? null : mainInvCondition.trim();
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateOrg() {
        return updateOrg == null ? "" : updateOrg.trim();
    }

    public void setUpdateOrg(String updateOrg) {
        this.updateOrg = updateOrg == null ? null : updateOrg.trim();
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

    public String getColumn01() {
        return column01 == null ? "" : column01.trim();
    }

    public void setColumn01(String column01) {
        this.column01 = column01 == null ? null : column01.trim();
    }

    public String getColumn02() {
        return column02 == null ? "" : column02.trim();
    }

    public void setColumn02(String column02) {
        this.column02 = column02 == null ? null : column02.trim();
    }

    public String getColumn03() {
        return column03 == null ? "" : column03.trim();
    }

    public void setColumn03(String column03) {
        this.column03 = column03 == null ? null : column03.trim();
    }

    public String getColumn04() {
        return column04 == null ? "" : column04.trim();
    }

    public void setColumn04(String column04) {
        this.column04 = column04 == null ? null : column04.trim();
    }

    public String getColumn05() {
        return column05 == null ? "" : column05.trim();
    }

    public void setColumn05(String column05) {
        this.column05 = column05 == null ? null : column05.trim();
    }

    public BigDecimal getZyzjbl() {
        return zyzjbl;
    }

    public void setZyzjbl(BigDecimal zyzjbl) {
        this.zyzjbl = zyzjbl;
    }

    public BigDecimal getYhdkbl() {
        return yhdkbl;
    }

    public void setYhdkbl(BigDecimal yhdkbl) {
        this.yhdkbl = yhdkbl;
    }

    public String getGzxxId() {
        return gzxxId == null ? "" : gzxxId.trim();
    }

    public void setGzxxId(String gzxxId) {
        this.gzxxId = gzxxId == null ? null : gzxxId.trim();
    }

    public BigDecimal getZjpftze() {
        return zjpftze;
    }

    public void setZjpftze(BigDecimal zjpftze) {
        this.zjpftze = zjpftze;
    }

    public Date getPfsj() {
        return pfsj;
    }

    public void setPfsj(Date pfsj) {
        this.pfsj = pfsj;
    }

    public String getPfwh() {
        return pfwh == null ? "" : pfwh.trim();
    }

    public void setPfwh(String pfwh) {
        this.pfwh = pfwh == null ? null : pfwh.trim();
    }

    public BigDecimal getJsnx() {
        return jsnx;
    }

    public void setJsnx(BigDecimal jsnx) {
        this.jsnx = jsnx;
    }

    public BigDecimal getYynx() {
        return yynx;
    }

    public void setYynx(BigDecimal yynx) {
        this.yynx = yynx;
    }

    public BigDecimal getHgnx() {
        return hgnx;
    }

    public void setHgnx(BigDecimal hgnx) {
        this.hgnx = hgnx;
    }

    public BigDecimal getKfzq() {
        return kfzq;
    }

    public void setKfzq(BigDecimal kfzq) {
        this.kfzq = kfzq;
    }

    public BigDecimal getKfmj() {
        return kfmj;
    }

    public void setKfmj(BigDecimal kfmj) {
        this.kfmj = kfmj;
    }

    public String getOrg() {
        return org == null ? "" : org.trim();
    }

    public void setOrg(String org) {
        this.org = org == null ? null : org.trim();
    }

    public String getGclb() {
        return gclb == null ? "" : gclb.trim();
    }

    public void setGclb(String gclb) {
        this.gclb = gclb == null ? null : gclb.trim();
    }

    public Date getXmzzsj() {
        return xmzzsj;
    }

    public void setXmzzsj(Date xmzzsj) {
        this.xmzzsj = xmzzsj;
    }

    public String getShortName() {
        return shortName == null ? "" : shortName.trim();
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getIsBb() {
        return isBb == null ? "" : isBb.trim();
    }

    public void setIsBb(String isBb) {
        this.isBb = isBb == null ? null : isBb.trim();
    }

    public String getFfms() {
        return ffms == null ? "" : ffms.trim();
    }

    public void setFfms(String ffms) {
        this.ffms = ffms == null ? null : ffms.trim();
    }

    public BigDecimal getGllxcd() {
        return gllxcd;
    }

    public void setGllxcd(BigDecimal gllxcd) {
        this.gllxcd = gllxcd;
    }

    public BigDecimal getGlgsglcd() {
        return glgsglcd;
    }

    public void setGlgsglcd(BigDecimal glgsglcd) {
        this.glgsglcd = glgsglcd;
    }

    public BigDecimal getGlqlcd() {
        return glqlcd;
    }

    public void setGlqlcd(BigDecimal glqlcd) {
        this.glqlcd = glqlcd;
    }

    public BigDecimal getGlsddd() {
        return glsddd;
    }

    public void setGlsddd(BigDecimal glsddd) {
        this.glsddd = glsddd;
    }

    public Integer getGltdq() {
        return gltdq;
    }

    public void setGltdq(Integer gltdq) {
        this.gltdq = gltdq;
    }

    public Integer getGldq() {
        return gldq;
    }

    public void setGldq(Integer gldq) {
        this.gldq = gldq;
    }

    public Integer getGlzq() {
        return glzq;
    }

    public void setGlzq(Integer glzq) {
        this.glzq = glzq;
    }

    public BigDecimal getQlqlcd() {
        return qlqlcd;
    }

    public void setQlqlcd(BigDecimal qlqlcd) {
        this.qlqlcd = qlqlcd;
    }

    public Integer getQltdq() {
        return qltdq;
    }

    public void setQltdq(Integer qltdq) {
        this.qltdq = qltdq;
    }

    public Integer getQldq() {
        return qldq;
    }

    public void setQldq(Integer qldq) {
        this.qldq = qldq;
    }

    public Integer getQlzq() {
        return qlzq;
    }

    public void setQlzq(Integer qlzq) {
        this.qlzq = qlzq;
    }

    public BigDecimal getSdsddd() {
        return sdsddd;
    }

    public void setSdsddd(BigDecimal sdsddd) {
        this.sdsddd = sdsddd;
    }

    public Integer getGkmtbw() {
        return gkmtbw;
    }

    public void setGkmtbw(Integer gkmtbw) {
        this.gkmtbw = gkmtbw;
    }

    public Integer getGkssbw() {
        return gkssbw;
    }

    public void setGkssbw(Integer gkssbw) {
        this.gkssbw = gkssbw;
    }

    public BigDecimal getGkmtbwcd() {
        return gkmtbwcd;
    }

    public void setGkmtbwcd(BigDecimal gkmtbwcd) {
        this.gkmtbwcd = gkmtbwcd;
    }

    public BigDecimal getWkzdmj() {
        return wkzdmj;
    }

    public void setWkzdmj(BigDecimal wkzdmj) {
        this.wkzdmj = wkzdmj;
    }

    public BigDecimal getGdlxcd() {
        return gdlxcd;
    }

    public void setGdlxcd(BigDecimal gdlxcd) {
        this.gdlxcd = gdlxcd;
    }

    public BigDecimal getTllxcd() {
        return tllxcd;
    }

    public void setTllxcd(BigDecimal tllxcd) {
        this.tllxcd = tllxcd;
    }

    public Integer getJcjc() {
        return jcjc;
    }

    public void setJcjc(Integer jcjc) {
        this.jcjc = jcjc;
    }

    public Integer getCyyqyq() {
        return cyyqyq;
    }

    public void setCyyqyq(Integer cyyqyq) {
        this.cyyqyq = cyyqyq;
    }

    public BigDecimal getCyyqyqghmj() {
        return cyyqyqghmj;
    }

    public void setCyyqyqghmj(BigDecimal cyyqyqghmj) {
        this.cyyqyqghmj = cyyqyqghmj;
    }

    public String getSybhf() {
        return sybhf == null ? "" : sybhf.trim();
    }

    public void setSybhf(String sybhf) {
        this.sybhf = sybhf == null ? null : sybhf.trim();
    }

    public String getZfcnyhtj() {
        return zfcnyhtj == null ? "" : zfcnyhtj.trim();
    }

    public void setZfcnyhtj(String zfcnyhtj) {
        this.zfcnyhtj = zfcnyhtj == null ? null : zfcnyhtj.trim();
    }

    public BigDecimal getZbjzykhzb() {
        return zbjzykhzb;
    }

    public void setZbjzykhzb(BigDecimal zbjzykhzb) {
        this.zbjzykhzb = zbjzykhzb;
    }

    public String getXmjd() {
        return xmjd == null ? "" : xmjd.trim();
    }

    public void setXmjd(String xmjd) {
        this.xmjd = xmjd == null ? null : xmjd.trim();
    }

    public String getSfrk() {
        return sfrk == null ? "" : sfrk.trim();
    }

    public void setSfrk(String sfrk) {
        this.sfrk = sfrk == null ? null : sfrk.trim();
    }

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
    }

    public Date getYgjjtpfrq() {
        return ygjjtpfrq;
    }

    public void setYgjjtpfrq(Date ygjjtpfrq) {
        this.ygjjtpfrq = ygjjtpfrq;
    }

    public Date getSjkgrq() {
        return sjkgrq;
    }

    public void setSjkgrq(Date sjkgrq) {
        this.sjkgrq = sjkgrq;
    }

    public Date getJgrq() {
        return jgrq;
    }

    public void setJgrq(Date jgrq) {
        this.jgrq = jgrq;
    }

    public Date getKshgyyrq() {
        return kshgyyrq;
    }

    public void setKshgyyrq(Date kshgyyrq) {
        this.kshgyyrq = kshgyyrq;
    }

    public Date getJsrq() {
        return jsrq;
    }

    public void setJsrq(Date jsrq) {
        this.jsrq = jsrq;
    }

    public Date getZbrq() {
        return zbrq;
    }

    public void setZbrq(Date zbrq) {
        this.zbrq = zbrq;
    }

    public BigDecimal getPgjdjcsy() {
        return pgjdjcsy;
    }

    public void setPgjdjcsy(BigDecimal pgjdjcsy) {
        this.pgjdjcsy = pgjdjcsy;
    }

    public BigDecimal getPgjdtrzsy() {
        return pgjdtrzsy;
    }

    public void setPgjdtrzsy(BigDecimal pgjdtrzsy) {
        this.pgjdtrzsy = pgjdtrzsy;
    }

    public BigDecimal getCnmbjcsy() {
        return cnmbjcsy;
    }

    public void setCnmbjcsy(BigDecimal cnmbjcsy) {
        this.cnmbjcsy = cnmbjcsy;
    }

    public BigDecimal getCnmutrzsy() {
        return cnmutrzsy;
    }

    public void setCnmutrzsy(BigDecimal cnmutrzsy) {
        this.cnmutrzsy = cnmutrzsy;
    }

    public BigDecimal getTpyjcltxsl() {
        return tpyjcltxsl;
    }

    public void setTpyjcltxsl(BigDecimal tpyjcltxsl) {
        this.tpyjcltxsl = tpyjcltxsl;
    }

    public BigDecimal getTpyjsr() {
        return tpyjsr;
    }

    public void setTpyjsr(BigDecimal tpyjsr) {
        this.tpyjsr = tpyjsr;
    }

    public BigDecimal getZhjcsyl() {
        return zhjcsyl;
    }

    public void setZhjcsyl(BigDecimal zhjcsyl) {
        this.zhjcsyl = zhjcsyl;
    }

    public String getBz() {
        return bz == null ? "" : bz.trim();
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public BigDecimal getXmcwnbsyl() {
        return xmcwnbsyl;
    }

    public void setXmcwnbsyl(BigDecimal xmcwnbsyl) {
        this.xmcwnbsyl = xmcwnbsyl;
    }

    public BigDecimal getZbjcwnbsyl() {
        return zbjcwnbsyl;
    }

    public void setZbjcwnbsyl(BigDecimal zbjcwnbsyl) {
        this.zbjcwnbsyl = zbjcwnbsyl;
    }

    public BigDecimal getDttzhsqn() {
        return dttzhsqn;
    }

    public void setDttzhsqn(BigDecimal dttzhsqn) {
        this.dttzhsqn = dttzhsqn;
    }

    public BigDecimal getPgsjdjcsyl() {
        return pgsjdjcsyl;
    }

    public void setPgsjdjcsyl(BigDecimal pgsjdjcsyl) {
        this.pgsjdjcsyl = pgsjdjcsyl;
    }

    public BigDecimal getSglr() {
        return sglr;
    }

    public void setSglr(BigDecimal sglr) {
        this.sglr = sglr;
    }

    public BigDecimal getTszhjcsyl() {
        return tszhjcsyl;
    }

    public void setTszhjcsyl(BigDecimal tszhjcsyl) {
        this.tszhjcsyl = tszhjcsyl;
    }

    public BigDecimal getYsjjcsyl() {
        return ysjjcsyl;
    }

    public void setYsjjcsyl(BigDecimal ysjjcsyl) {
        this.ysjjcsyl = ysjjcsyl;
    }

    public String getSftzxm() {
        return sftzxm == null ? "" : sftzxm.trim();
    }

    public void setSftzxm(String sftzxm) {
        this.sftzxm = sftzxm == null ? null : sftzxm.trim();
    }

}

