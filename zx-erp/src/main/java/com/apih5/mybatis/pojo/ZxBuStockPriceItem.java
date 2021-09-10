package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuStockPriceItem extends BasePojo {
    // 主键
    private String zxBuStockPriceItemId;

    // 材料编号
    private String resCode;

    // 材料名称
    private String resName;

    // 资源ID
    private String resID;

    // 资源单价
    private BigDecimal resPrice;

    // 
    private Date editTime;

    // 物资价格库ID
    private String stockPriceID;

    // 单位
    private String unit;

    // 是否复合物资
    private String isOrNotCom;

    // 市场价
    private BigDecimal constrPrice;

    // 总量
    private BigDecimal allAmt;

    // 已完成量
    private BigDecimal compleQty;

    // 平均单价
    private BigDecimal avgPrice;

    // 首次标后预算资源单价(不含税)
    private BigDecimal scbhPrice;

    // 末次标后预算资源单价(不含税)
    private BigDecimal mcbhPrice;

    // 首次施工预算资源单价(不含税)
    private BigDecimal scsgPrice;

    // 材料价格(不含税)
    private BigDecimal mcsgPrice;

    // 启用该资源
    private String isStartRes;

    // 参考值
    private BigDecimal referValue;

    // 剩余工程
    private BigDecimal mcsgAPrice;

    // 数量
    private BigDecimal qty;

    // 税率
    private String taxRate;

    // 税金单价
    private BigDecimal mcsgPriceTax;

    // 运费(不含税)
    private BigDecimal ysFee;

    // 税率
    private String ysTaxRate;

    // 税金单价
    private BigDecimal ysFeeTax;

    // 不含税到场价(元)
    private BigDecimal scenePrice;

    // 进项税金(元)
    private BigDecimal taxAmt;

    // 不含税总价(元)
    private BigDecimal sumAmt;

    // bhAmt
    private BigDecimal bhAmt;

    // 拌合运输费(元/m3)
    private BigDecimal ysAmt;

    // totalAmt
    private BigDecimal totalAmt;

    // mixType
    private String mixType;

    // qty1
    private BigDecimal qty1;

    // price1
    private BigDecimal price1;

    // amt1
    private BigDecimal amt1;

    // qty2
    private BigDecimal qty2;

    // price2
    private BigDecimal price2;

    // amt3
    private BigDecimal amt3;

    // qty3
    private BigDecimal qty3;

    // price3
    private BigDecimal price3;

    // amt12
    private BigDecimal amt12;

    // amt2
    private BigDecimal amt2;

    // qty4
    private BigDecimal qty4;

    // price4
    private BigDecimal price4;

    // amt4
    private BigDecimal amt4;

    // qty5
    private BigDecimal qty5;

    // price5
    private BigDecimal price5;

    // amt5
    private BigDecimal amt5;

    // qty6
    private BigDecimal qty6;

    // price6
    private BigDecimal price6;

    // amt6
    private BigDecimal amt6;

    // qty7
    private BigDecimal qty7;

    // price7
    private BigDecimal price7;

    // amt7
    private BigDecimal amt7;

    // qty8
    private BigDecimal qty8;

    // price8
    private BigDecimal price8;

    // amt8
    private BigDecimal amt8;

    // qty9
    private BigDecimal qty9;

    // price9
    private BigDecimal price9;

    // amt9
    private BigDecimal amt9;

    // qty10
    private BigDecimal qty10;

    // price10
    private BigDecimal price10;

    // amt10
    private BigDecimal amt10;

    // qty11
    private BigDecimal qty11;

    // price11
    private BigDecimal price11;

    // amt11
    private BigDecimal amt11;

    // qty12
    private BigDecimal qty12;

    // price12
    private BigDecimal price12;

    // 规格型号
    private String spec;

    // qty13
    private BigDecimal qty13;

    // price13
    private BigDecimal price13;

    // amt13
    private BigDecimal amt13;

    // flg
    private BigDecimal flg;

    // 机构ID
    private String orgID;

    // 是否调差
    private String isAdjustment;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private Integer sheetHeadNumber;

    private Integer sheetStartNumber;

    //组装id
    private String stockVOID;

    public String getStockVOID() {
        return stockVOID;
    }

    public void setStockVOID(String stockVOID) {
        this.stockVOID = stockVOID;
    }

    public Integer getSheetHeadNumber() {
        return sheetHeadNumber;
    }

    public void setSheetHeadNumber(Integer sheetHeadNumber) {
        this.sheetHeadNumber = sheetHeadNumber;
    }

    public Integer getSheetStartNumber() {
        return sheetStartNumber;
    }

    public void setSheetStartNumber(Integer sheetStartNumber) {
        this.sheetStartNumber = sheetStartNumber;
    }

    public String getZxBuStockPriceItemId() {
        return zxBuStockPriceItemId == null ? "" : zxBuStockPriceItemId.trim();
    }

    public void setZxBuStockPriceItemId(String zxBuStockPriceItemId) {
        this.zxBuStockPriceItemId = zxBuStockPriceItemId == null ? null : zxBuStockPriceItemId.trim();
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

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public BigDecimal getResPrice() {
        return resPrice;
    }

    public void setResPrice(BigDecimal resPrice) {
        this.resPrice = resPrice;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getStockPriceID() {
        return stockPriceID == null ? "" : stockPriceID.trim();
    }

    public void setStockPriceID(String stockPriceID) {
        this.stockPriceID = stockPriceID == null ? null : stockPriceID.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getIsOrNotCom() {
        return isOrNotCom == null ? "" : isOrNotCom.trim();
    }

    public void setIsOrNotCom(String isOrNotCom) {
        this.isOrNotCom = isOrNotCom == null ? null : isOrNotCom.trim();
    }

    public BigDecimal getConstrPrice() {
        return constrPrice;
    }

    public void setConstrPrice(BigDecimal constrPrice) {
        this.constrPrice = constrPrice;
    }

    public BigDecimal getAllAmt() {
        return allAmt;
    }

    public void setAllAmt(BigDecimal allAmt) {
        this.allAmt = allAmt;
    }

    public BigDecimal getCompleQty() {
        return compleQty;
    }

    public void setCompleQty(BigDecimal compleQty) {
        this.compleQty = compleQty;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getScbhPrice() {
        return scbhPrice;
    }

    public void setScbhPrice(BigDecimal scbhPrice) {
        this.scbhPrice = scbhPrice;
    }

    public BigDecimal getMcbhPrice() {
        return mcbhPrice;
    }

    public void setMcbhPrice(BigDecimal mcbhPrice) {
        this.mcbhPrice = mcbhPrice;
    }

    public BigDecimal getScsgPrice() {
        return scsgPrice;
    }

    public void setScsgPrice(BigDecimal scsgPrice) {
        this.scsgPrice = scsgPrice;
    }

    public BigDecimal getMcsgPrice() {
        return mcsgPrice;
    }

    public void setMcsgPrice(BigDecimal mcsgPrice) {
        this.mcsgPrice = mcsgPrice;
    }

    public String getIsStartRes() {
        return isStartRes == null ? "" : isStartRes.trim();
    }

    public void setIsStartRes(String isStartRes) {
        this.isStartRes = isStartRes == null ? null : isStartRes.trim();
    }

    public BigDecimal getReferValue() {
        return referValue;
    }

    public void setReferValue(BigDecimal referValue) {
        this.referValue = referValue;
    }

    public BigDecimal getMcsgAPrice() {
        return mcsgAPrice;
    }

    public void setMcsgAPrice(BigDecimal mcsgAPrice) {
        this.mcsgAPrice = mcsgAPrice;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public BigDecimal getMcsgPriceTax() {
        return mcsgPriceTax;
    }

    public void setMcsgPriceTax(BigDecimal mcsgPriceTax) {
        this.mcsgPriceTax = mcsgPriceTax;
    }

    public BigDecimal getYsFee() {
        return ysFee;
    }

    public void setYsFee(BigDecimal ysFee) {
        this.ysFee = ysFee;
    }

    public String getYsTaxRate() {
        return ysTaxRate == null ? "" : ysTaxRate.trim();
    }

    public void setYsTaxRate(String ysTaxRate) {
        this.ysTaxRate = ysTaxRate == null ? null : ysTaxRate.trim();
    }

    public BigDecimal getYsFeeTax() {
        return ysFeeTax;
    }

    public void setYsFeeTax(BigDecimal ysFeeTax) {
        this.ysFeeTax = ysFeeTax;
    }

    public BigDecimal getScenePrice() {
        return scenePrice;
    }

    public void setScenePrice(BigDecimal scenePrice) {
        this.scenePrice = scenePrice;
    }

    public BigDecimal getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(BigDecimal taxAmt) {
        this.taxAmt = taxAmt;
    }

    public BigDecimal getSumAmt() {
        return sumAmt;
    }

    public void setSumAmt(BigDecimal sumAmt) {
        this.sumAmt = sumAmt;
    }

    public BigDecimal getBhAmt() {
        return bhAmt;
    }

    public void setBhAmt(BigDecimal bhAmt) {
        this.bhAmt = bhAmt;
    }

    public BigDecimal getYsAmt() {
        return ysAmt;
    }

    public void setYsAmt(BigDecimal ysAmt) {
        this.ysAmt = ysAmt;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getMixType() {
        return mixType == null ? "" : mixType.trim();
    }

    public void setMixType(String mixType) {
        this.mixType = mixType == null ? null : mixType.trim();
    }

    public BigDecimal getQty1() {
        return qty1;
    }

    public void setQty1(BigDecimal qty1) {
        this.qty1 = qty1;
    }

    public BigDecimal getPrice1() {
        return price1;
    }

    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    public BigDecimal getAmt1() {
        return amt1;
    }

    public void setAmt1(BigDecimal amt1) {
        this.amt1 = amt1;
    }

    public BigDecimal getQty2() {
        return qty2;
    }

    public void setQty2(BigDecimal qty2) {
        this.qty2 = qty2;
    }

    public BigDecimal getPrice2() {
        return price2;
    }

    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    public BigDecimal getAmt3() {
        return amt3;
    }

    public void setAmt3(BigDecimal amt3) {
        this.amt3 = amt3;
    }

    public BigDecimal getQty3() {
        return qty3;
    }

    public void setQty3(BigDecimal qty3) {
        this.qty3 = qty3;
    }

    public BigDecimal getPrice3() {
        return price3;
    }

    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
    }

    public BigDecimal getAmt12() {
        return amt12;
    }

    public void setAmt12(BigDecimal amt12) {
        this.amt12 = amt12;
    }

    public BigDecimal getAmt2() {
        return amt2;
    }

    public void setAmt2(BigDecimal amt2) {
        this.amt2 = amt2;
    }

    public BigDecimal getQty4() {
        return qty4;
    }

    public void setQty4(BigDecimal qty4) {
        this.qty4 = qty4;
    }

    public BigDecimal getPrice4() {
        return price4;
    }

    public void setPrice4(BigDecimal price4) {
        this.price4 = price4;
    }

    public BigDecimal getAmt4() {
        return amt4;
    }

    public void setAmt4(BigDecimal amt4) {
        this.amt4 = amt4;
    }

    public BigDecimal getQty5() {
        return qty5;
    }

    public void setQty5(BigDecimal qty5) {
        this.qty5 = qty5;
    }

    public BigDecimal getPrice5() {
        return price5;
    }

    public void setPrice5(BigDecimal price5) {
        this.price5 = price5;
    }

    public BigDecimal getAmt5() {
        return amt5;
    }

    public void setAmt5(BigDecimal amt5) {
        this.amt5 = amt5;
    }

    public BigDecimal getQty6() {
        return qty6;
    }

    public void setQty6(BigDecimal qty6) {
        this.qty6 = qty6;
    }

    public BigDecimal getPrice6() {
        return price6;
    }

    public void setPrice6(BigDecimal price6) {
        this.price6 = price6;
    }

    public BigDecimal getAmt6() {
        return amt6;
    }

    public void setAmt6(BigDecimal amt6) {
        this.amt6 = amt6;
    }

    public BigDecimal getQty7() {
        return qty7;
    }

    public void setQty7(BigDecimal qty7) {
        this.qty7 = qty7;
    }

    public BigDecimal getPrice7() {
        return price7;
    }

    public void setPrice7(BigDecimal price7) {
        this.price7 = price7;
    }

    public BigDecimal getAmt7() {
        return amt7;
    }

    public void setAmt7(BigDecimal amt7) {
        this.amt7 = amt7;
    }

    public BigDecimal getQty8() {
        return qty8;
    }

    public void setQty8(BigDecimal qty8) {
        this.qty8 = qty8;
    }

    public BigDecimal getPrice8() {
        return price8;
    }

    public void setPrice8(BigDecimal price8) {
        this.price8 = price8;
    }

    public BigDecimal getAmt8() {
        return amt8;
    }

    public void setAmt8(BigDecimal amt8) {
        this.amt8 = amt8;
    }

    public BigDecimal getQty9() {
        return qty9;
    }

    public void setQty9(BigDecimal qty9) {
        this.qty9 = qty9;
    }

    public BigDecimal getPrice9() {
        return price9;
    }

    public void setPrice9(BigDecimal price9) {
        this.price9 = price9;
    }

    public BigDecimal getAmt9() {
        return amt9;
    }

    public void setAmt9(BigDecimal amt9) {
        this.amt9 = amt9;
    }

    public BigDecimal getQty10() {
        return qty10;
    }

    public void setQty10(BigDecimal qty10) {
        this.qty10 = qty10;
    }

    public BigDecimal getPrice10() {
        return price10;
    }

    public void setPrice10(BigDecimal price10) {
        this.price10 = price10;
    }

    public BigDecimal getAmt10() {
        return amt10;
    }

    public void setAmt10(BigDecimal amt10) {
        this.amt10 = amt10;
    }

    public BigDecimal getQty11() {
        return qty11;
    }

    public void setQty11(BigDecimal qty11) {
        this.qty11 = qty11;
    }

    public BigDecimal getPrice11() {
        return price11;
    }

    public void setPrice11(BigDecimal price11) {
        this.price11 = price11;
    }

    public BigDecimal getAmt11() {
        return amt11;
    }

    public void setAmt11(BigDecimal amt11) {
        this.amt11 = amt11;
    }

    public BigDecimal getQty12() {
        return qty12;
    }

    public void setQty12(BigDecimal qty12) {
        this.qty12 = qty12;
    }

    public BigDecimal getPrice12() {
        return price12;
    }

    public void setPrice12(BigDecimal price12) {
        this.price12 = price12;
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public BigDecimal getQty13() {
        return qty13;
    }

    public void setQty13(BigDecimal qty13) {
        this.qty13 = qty13;
    }

    public BigDecimal getPrice13() {
        return price13;
    }

    public void setPrice13(BigDecimal price13) {
        this.price13 = price13;
    }

    public BigDecimal getAmt13() {
        return amt13;
    }

    public void setAmt13(BigDecimal amt13) {
        this.amt13 = amt13;
    }

    public BigDecimal getFlg() {
        return flg;
    }

    public void setFlg(BigDecimal flg) {
        this.flg = flg;
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getIsAdjustment() {
        return isAdjustment == null ? "" : isAdjustment.trim();
    }

    public void setIsAdjustment(String isAdjustment) {
        this.isAdjustment = isAdjustment == null ? null : isAdjustment.trim();
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
