package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkStockDifMonthItem extends BasePojo {
    // 主键
    private String zxSkStockDifMonthItemId;

    // 主表ID
    private String mainID;

    // 物资ID
    private String resID;

    // 物资编号
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 单位
    private String unit;

    // 本月实际采购加权平均价
    private BigDecimal thisPrice;

    // 本月实际消耗量
    private BigDecimal thisQty;

    // 设计用量
    private BigDecimal designQty;

    // 局定额损耗率(%)
    private BigDecimal sunHaoLv;

    // 物资类别ID
    private String categoryID;

    // 物资类别
    private String categoryName;

    // 类型
    private String mtType;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //本月实际消耗金额
    private BigDecimal thisAmt;

    //设计用量金额
    private BigDecimal design;

    //理论消耗量
    private BigDecimal llQty;

    //理论消耗金额
    private BigDecimal llAmt;

    //设计节超数量
    private BigDecimal deJcQty;

    //设计节超金额
    private BigDecimal deJcAmt;

    //设计节超率
    private BigDecimal deJclv;

    //理论节超数量
    private BigDecimal llJcQty;

    //理论节超金额
    private BigDecimal llJcAmt;

    //理论节超率
    private BigDecimal llJclv;

    //开累实际消耗用量
    private BigDecimal klQty;

    //开累实际消耗金额
    private BigDecimal klAmt;

    //开累设计消耗用量
    private BigDecimal klDeQty;

    //开累设计消耗金额
    private BigDecimal klDeAmt;

    //开累设计节超数量
    private BigDecimal klDeJcQty;

    //开累设计节超金额
    private BigDecimal klDeJcAmt;

    //开累设计节超率
    private BigDecimal klDeJclv;

    //开累理论节超数量
    private BigDecimal klLlJcQty;

    //开累理论节超金额
    private BigDecimal klLlJcAmt;

    //开累理论节超率
    private BigDecimal klLlJclv;
    
    //凭证号
    private String voucherNo;
    
    //摘要
    private String outOrgName;

    //采购单价
    private BigDecimal stdPrice;
    
    //甲供
    private BigDecimal orsQty;
    
	//其他
    private BigDecimal otrQty;
    
    //自行采购
    private BigDecimal serQty;

    //预收
    private BigDecimal obuQty;
    
    //甲控
    private BigDecimal ocsQty;
    
    //收入合计数量
    private BigDecimal totalQty;
    
    //收入合计金额
    private BigDecimal inAmt;
    
    //工程耗用
    private BigDecimal otkQty;
    
    //调拨内调
    private BigDecimal withinQty;
    
    //调拨外调
    private BigDecimal rithinQty;
    
    //发出数量
    private BigDecimal inOrOutTotalQty;
    
    //发出平均单价
    private BigDecimal inOrOutStdPrice;
    
    //发出金额
    private BigDecimal inOrOutoutAmt;
    
    //盈亏数量
    private BigDecimal diskoutQty;
    
    //盈亏金额
    private BigDecimal outAmt;
    
    //结存数量
    private BigDecimal resultQty;
    
    //结存平均单价
    private BigDecimal resultAmt;
    
    //结存金额
    private BigDecimal resultPrice;
    
    //日期
    private Date inbusDate;
    
    private String inbusDateStr;
    
    //上月结存（数量）
    private BigDecimal stockQty;

	//上月结存（金额）
    private BigDecimal stockAmt;
    
    //上月结存（平均单价）
    private BigDecimal stockPrice;
    
    //本月收入合计（数量）
    private BigDecimal inQty;
    
    //本月收入合计（平均价格）
    private BigDecimal inPrice;
  
    //工程耗用数量
    private BigDecimal oswQty;
    
    //工程耗用金额
    private BigDecimal oswAmt;
    
    //工程耗用平均单价
    private BigDecimal oswPrice;
    
    //调用平均价格
    private BigDecimal otkPrice;
    
    //盈亏数量
    private BigDecimal vinQty;
    
    //盈亏金额
    private BigDecimal vinAmt;
    
    //本月结存数量
    private BigDecimal thisQtys;
    
    //本月结存金额
    private BigDecimal thisAmts;
    
    //本月结存平均价格
    private BigDecimal thisProce;
    
    private Date inDate;
    
	private Date outDate;
    
	private String departmentId;

	private String materialType;
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	
	  public Date getInDate() {
			return inDate;
		}

		public void setInDate(Date inDate) {
			this.inDate = inDate;
		}

		public Date getOutDate() {
			return outDate;
		}

		public void setOutDate(Date outDate) {
			this.outDate = outDate;
		}
    
    public BigDecimal getStockQty() {
		return stockQty;
	}

	public void setStockQty(BigDecimal stockQty) {
		this.stockQty = stockQty;
	}

	public BigDecimal getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(BigDecimal stockAmt) {
		this.stockAmt = stockAmt;
	}

	public BigDecimal getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(BigDecimal stockPrice) {
		this.stockPrice = stockPrice;
	}

	public BigDecimal getInQty() {
		return inQty;
	}

	public void setInQty(BigDecimal inQty) {
		this.inQty = inQty;
	}

	public BigDecimal getInPrice() {
		return inPrice;
	}

	public void setInPrice(BigDecimal inPrice) {
		this.inPrice = inPrice;
	}

	public BigDecimal getOswQty() {
		return oswQty;
	}

	public void setOswQty(BigDecimal oswQty) {
		this.oswQty = oswQty;
	}

	public BigDecimal getOswAmt() {
		return oswAmt;
	}

	public void setOswAmt(BigDecimal oswAmt) {
		this.oswAmt = oswAmt;
	}

	public BigDecimal getOswPrice() {
		return oswPrice;
	}

	public void setOswPrice(BigDecimal oswPrice) {
		this.oswPrice = oswPrice;
	}

	public BigDecimal getOtkPrice() {
		return otkPrice;
	}

	public void setOtkPrice(BigDecimal otkPrice) {
		this.otkPrice = otkPrice;
	}

	public BigDecimal getVinQty() {
		return vinQty;
	}

	public void setVinQty(BigDecimal vinQty) {
		this.vinQty = vinQty;
	}

	public BigDecimal getVinAmt() {
		return vinAmt;
	}

	public void setVinAmt(BigDecimal vinAmt) {
		this.vinAmt = vinAmt;
	}

	public BigDecimal getThisQtys() {
		return thisQtys;
	}

	public void setThisQtys(BigDecimal thisQtys) {
		this.thisQtys = thisQtys;
	}

	public BigDecimal getThisAmts() {
		return thisAmts;
	}

	public void setThisAmts(BigDecimal thisAmts) {
		this.thisAmts = thisAmts;
	}

	public BigDecimal getThisProce() {
		return thisProce;
	}

	public void setThisProce(BigDecimal thisProce) {
		this.thisProce = thisProce;
	}

    public String getInbusDateStr() {
		return inbusDateStr;
	}

	public void setInbusDateStr(String inbusDateStr) {
		this.inbusDateStr = inbusDateStr;
	}

	public Date getInbusDate() {
		return inbusDate;
	}

	public void setInbusDate(Date inbusDate) {
		this.inbusDate = inbusDate;
	}

	public BigDecimal getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(BigDecimal totalQty) {
		this.totalQty = totalQty;
	}

	public BigDecimal getInAmt() {
		return inAmt;
	}

	public void setInAmt(BigDecimal inAmt) {
		this.inAmt = inAmt;
	}

	public BigDecimal getOtkQty() {
		return otkQty;
	}

	public void setOtkQty(BigDecimal otkQty) {
		this.otkQty = otkQty;
	}

	public BigDecimal getWithinQty() {
		return withinQty;
	}

	public void setWithinQty(BigDecimal withinQty) {
		this.withinQty = withinQty;
	}

	public BigDecimal getRithinQty() {
		return rithinQty;
	}

	public void setRithinQty(BigDecimal rithinQty) {
		this.rithinQty = rithinQty;
	}

	public BigDecimal getInOrOutTotalQty() {
		return inOrOutTotalQty;
	}

	public void setInOrOutTotalQty(BigDecimal inOrOutTotalQty) {
		this.inOrOutTotalQty = inOrOutTotalQty;
	}

	public BigDecimal getInOrOutStdPrice() {
		return inOrOutStdPrice;
	}

	public void setInOrOutStdPrice(BigDecimal inOrOutStdPrice) {
		this.inOrOutStdPrice = inOrOutStdPrice;
	}

	public BigDecimal getInOrOutoutAmt() {
		return inOrOutoutAmt;
	}

	public void setInOrOutoutAmt(BigDecimal inOrOutoutAmt) {
		this.inOrOutoutAmt = inOrOutoutAmt;
	}

	public BigDecimal getDiskoutQty() {
		return diskoutQty;
	}

	public void setDiskoutQty(BigDecimal diskoutQty) {
		this.diskoutQty = diskoutQty;
	}

	public BigDecimal getOutAmt() {
		return outAmt;
	}

	public void setOutAmt(BigDecimal outAmt) {
		this.outAmt = outAmt;
	}

	public BigDecimal getResultQty() {
		return resultQty;
	}

	public void setResultQty(BigDecimal resultQty) {
		this.resultQty = resultQty;
	}

	public BigDecimal getResultAmt() {
		return resultAmt;
	}

	public void setResultAmt(BigDecimal resultAmt) {
		this.resultAmt = resultAmt;
	}

	public BigDecimal getResultPrice() {
		return resultPrice;
	}

	public void setResultPrice(BigDecimal resultPrice) {
		this.resultPrice = resultPrice;
	}
    
    public BigDecimal getOrsQty() {
		return orsQty;
	}

	public void setOrsQty(BigDecimal orsQty) {
		this.orsQty = orsQty;
	}

	public BigDecimal getOtrQty() {
		return otrQty;
	}

	public void setOtrQty(BigDecimal otrQty) {
		this.otrQty = otrQty;
	}

	public BigDecimal getSerQty() {
		return serQty;
	}

	public void setSerQty(BigDecimal serQty) {
		this.serQty = serQty;
	}

	public BigDecimal getObuQty() {
		return obuQty;
	}

	public void setObuQty(BigDecimal obuQty) {
		this.obuQty = obuQty;
	}

	public BigDecimal getOcsQty() {
		return ocsQty;
	}

	public void setOcsQty(BigDecimal ocsQty) {
		this.ocsQty = ocsQty;
	}
	
    public BigDecimal getStdPrice() {
		return stdPrice;
	}

	public void setStdPrice(BigDecimal stdPrice) {
		this.stdPrice = stdPrice;
	}

	public String getOutOrgName() {
		return outOrgName;
	}

	public void setOutOrgName(String outOrgName) {
		this.outOrgName = outOrgName;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public BigDecimal getKlQty() {
        return klQty;
    }

    public void setKlQty(BigDecimal klQty) {
        this.klQty = klQty;
    }

    public BigDecimal getKlAmt() {
        return klAmt;
    }

    public void setKlAmt(BigDecimal klAmt) {
        this.klAmt = klAmt;
    }

    public BigDecimal getKlDeQty() {
        return klDeQty;
    }

    public void setKlDeQty(BigDecimal klDeQty) {
        this.klDeQty = klDeQty;
    }

    public BigDecimal getKlDeAmt() {
        return klDeAmt;
    }

    public void setKlDeAmt(BigDecimal klDeAmt) {
        this.klDeAmt = klDeAmt;
    }

    public BigDecimal getKlDeJcQty() {
        return klDeJcQty;
    }

    public void setKlDeJcQty(BigDecimal klDeJcQty) {
        this.klDeJcQty = klDeJcQty;
    }

    public BigDecimal getKlDeJcAmt() {
        return klDeJcAmt;
    }

    public void setKlDeJcAmt(BigDecimal klDeJcAmt) {
        this.klDeJcAmt = klDeJcAmt;
    }

    public BigDecimal getKlDeJclv() {
        return klDeJclv;
    }

    public void setKlDeJclv(BigDecimal klDeJclv) {
        this.klDeJclv = klDeJclv;
    }

    public BigDecimal getKlLlJcQty() {
        return klLlJcQty;
    }

    public void setKlLlJcQty(BigDecimal klLlJcQty) {
        this.klLlJcQty = klLlJcQty;
    }

    public BigDecimal getKlLlJcAmt() {
        return klLlJcAmt;
    }

    public void setKlLlJcAmt(BigDecimal klLlJcAmt) {
        this.klLlJcAmt = klLlJcAmt;
    }

    public BigDecimal getKlLlJclv() {
        return klLlJclv;
    }

    public void setKlLlJclv(BigDecimal klLlJclv) {
        this.klLlJclv = klLlJclv;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getDesign() {
        return design;
    }

    public void setDesign(BigDecimal design) {
        this.design = design;
    }

    public BigDecimal getLlQty() {
        return llQty;
    }

    public void setLlQty(BigDecimal llQty) {
        this.llQty = llQty;
    }

    public BigDecimal getLlAmt() {
        return llAmt;
    }

    public void setLlAmt(BigDecimal llAmt) {
        this.llAmt = llAmt;
    }

    public BigDecimal getDeJcQty() {
        return deJcQty;
    }

    public void setDeJcQty(BigDecimal deJcQty) {
        this.deJcQty = deJcQty;
    }

    public BigDecimal getDeJcAmt() {
        return deJcAmt;
    }

    public void setDeJcAmt(BigDecimal deJcAmt) {
        this.deJcAmt = deJcAmt;
    }

    public BigDecimal getDeJclv() {
        return deJclv;
    }

    public void setDeJclv(BigDecimal deJclv) {
        this.deJclv = deJclv;
    }

    public BigDecimal getLlJcQty() {
        return llJcQty;
    }

    public void setLlJcQty(BigDecimal llJcQty) {
        this.llJcQty = llJcQty;
    }

    public BigDecimal getLlJcAmt() {
        return llJcAmt;
    }

    public void setLlJcAmt(BigDecimal llJcAmt) {
        this.llJcAmt = llJcAmt;
    }

    public BigDecimal getLlJclv() {
        return llJclv;
    }

    public void setLlJclv(BigDecimal llJclv) {
        this.llJclv = llJclv;
    }

    public String getZxSkStockDifMonthItemId() {
        return zxSkStockDifMonthItemId == null ? "" : zxSkStockDifMonthItemId.trim();
    }

    public void setZxSkStockDifMonthItemId(String zxSkStockDifMonthItemId) {
        this.zxSkStockDifMonthItemId = zxSkStockDifMonthItemId == null ? null : zxSkStockDifMonthItemId.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
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

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(BigDecimal thisPrice) {
        this.thisPrice = thisPrice;
    }

    public BigDecimal getThisQty() {
        return thisQty;
    }

    public void setThisQty(BigDecimal thisQty) {
        this.thisQty = thisQty;
    }

    public BigDecimal getDesignQty() {
        return designQty;
    }

    public void setDesignQty(BigDecimal designQty) {
        this.designQty = designQty;
    }

    public BigDecimal getSunHaoLv() {
        return sunHaoLv;
    }

    public void setSunHaoLv(BigDecimal sunHaoLv) {
        this.sunHaoLv = sunHaoLv;
    }

    public String getCategoryID() {
        return categoryID == null ? "" : categoryID.trim();
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID == null ? null : categoryID.trim();
    }

    public String getCategoryName() {
        return categoryName == null ? "" : categoryName.trim();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getMtType() {
        return mtType == null ? "" : mtType.trim();
    }

    public void setMtType(String mtType) {
        this.mtType = mtType == null ? null : mtType.trim();
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
