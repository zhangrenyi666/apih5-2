package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCtPriceSys extends BasePojo {
    // 主键
    private String ctPriceSysId;

    // 合同评审补充协议ID(contrApplyID)
    private String ctContrApplyId;

    // 合同管理ID(contractID)
    private String ctContractId;

    // 合同评审补充协议清单ID(applyWorkID)
    private String applyAlterWorksId;

    // 合同管理清单ID(workID)
    private String ccWorksId;

    // 清单编号
    private String workNo;

    // 清单名称
    private String workName;

    // 单位
    private String workUnit;

    // 不含税单价
    private BigDecimal priceNoTax;

    // 不含税标后预算单价/限价
    private BigDecimal bhPriceNoTax;

    // 业主清单单价
    private BigDecimal yzPriceNoTax;

    // 对比(标后预算/限价-不含税合价)
    private BigDecimal dbPrice;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 最后编辑时间
    private Date editTime;

    // 主材
    private BigDecimal amt1;

    // 管理费
    private BigDecimal amt2;

    // 利润
    private BigDecimal amt3;

    // 人工费
    private BigDecimal amt4;

    // 周转材料
    private BigDecimal amt5;

    // 机械设备
    private BigDecimal amt6;

    // 备注
    private String opinionField1;

    // 备注
    private String opinionField2;

    // 备注
    private String opinionField3;

    // 备注
    private String opinionField4;

    // 备注
    private String opinionField5;

    // 备注
    private String opinionField6;

    // 备注
    private String opinionField7;

    // 备注
    private String opinionField8;

    // 备注
    private String opinionField9;

    // 备注
    private String opinionField10;

    // 流程id
    private String apih5FlowId;

    // work_id
    private String workId;

    // 工序审核状态
    private String apih5FlowStatus;

    // 流程状态
    private String apih5FlowNodeStatus;

    // 临时用主键字段
    private String tempPrimaryKey;

    // 单价分析明细集合
    private List<ZxGcsgCtPriceSysItem> ctPriceSysItemList;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getCtPriceSysId() {
        return ctPriceSysId == null ? "" : ctPriceSysId.trim();
    }

    public void setCtPriceSysId(String ctPriceSysId) {
        this.ctPriceSysId = ctPriceSysId == null ? null : ctPriceSysId.trim();
    }

    public String getCtContrApplyId() {
        return ctContrApplyId == null ? "" : ctContrApplyId.trim();
    }

    public void setCtContrApplyId(String ctContrApplyId) {
        this.ctContrApplyId = ctContrApplyId == null ? null : ctContrApplyId.trim();
    }

    public String getCtContractId() {
        return ctContractId == null ? "" : ctContractId.trim();
    }

    public void setCtContractId(String ctContractId) {
        this.ctContractId = ctContractId == null ? null : ctContractId.trim();
    }

    public String getApplyAlterWorksId() {
        return applyAlterWorksId == null ? "" : applyAlterWorksId.trim();
    }

    public void setApplyAlterWorksId(String applyAlterWorksId) {
        this.applyAlterWorksId = applyAlterWorksId == null ? null : applyAlterWorksId.trim();
    }

    public String getCcWorksId() {
        return ccWorksId == null ? "" : ccWorksId.trim();
    }

    public void setCcWorksId(String ccWorksId) {
        this.ccWorksId = ccWorksId == null ? null : ccWorksId.trim();
    }

    public String getWorkNo() {
        return workNo == null ? "" : workNo.trim();
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo == null ? null : workNo.trim();
    }

    public String getWorkName() {
        return workName == null ? "" : workName.trim();
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public String getWorkUnit() {
        return workUnit == null ? "" : workUnit.trim();
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit == null ? null : workUnit.trim();
    }

    public BigDecimal getPriceNoTax() {
        return priceNoTax;
    }

    public void setPriceNoTax(BigDecimal priceNoTax) {
        this.priceNoTax = priceNoTax;
    }

    public BigDecimal getBhPriceNoTax() {
        return bhPriceNoTax;
    }

    public void setBhPriceNoTax(BigDecimal bhPriceNoTax) {
        this.bhPriceNoTax = bhPriceNoTax;
    }

    public BigDecimal getYzPriceNoTax() {
        return yzPriceNoTax;
    }

    public void setYzPriceNoTax(BigDecimal yzPriceNoTax) {
        this.yzPriceNoTax = yzPriceNoTax;
    }

    public BigDecimal getDbPrice() {
        return dbPrice;
    }

    public void setDbPrice(BigDecimal dbPrice) {
        this.dbPrice = dbPrice;
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public BigDecimal getAmt1() {
        return amt1;
    }

    public void setAmt1(BigDecimal amt1) {
        this.amt1 = amt1;
    }

    public BigDecimal getAmt2() {
        return amt2;
    }

    public void setAmt2(BigDecimal amt2) {
        this.amt2 = amt2;
    }

    public BigDecimal getAmt3() {
        return amt3;
    }

    public void setAmt3(BigDecimal amt3) {
        this.amt3 = amt3;
    }

    public BigDecimal getAmt4() {
        return amt4;
    }

    public void setAmt4(BigDecimal amt4) {
        this.amt4 = amt4;
    }

    public BigDecimal getAmt5() {
        return amt5;
    }

    public void setAmt5(BigDecimal amt5) {
        this.amt5 = amt5;
    }

    public BigDecimal getAmt6() {
        return amt6;
    }

    public void setAmt6(BigDecimal amt6) {
        this.amt6 = amt6;
    }

    public String getOpinionField1() {
        return opinionField1 == null ? "" : opinionField1.trim();
    }

    public void setOpinionField1(String opinionField1) {
        this.opinionField1 = opinionField1 == null ? null : opinionField1.trim();
    }

    public String getOpinionField2() {
        return opinionField2 == null ? "" : opinionField2.trim();
    }

    public void setOpinionField2(String opinionField2) {
        this.opinionField2 = opinionField2 == null ? null : opinionField2.trim();
    }

    public String getOpinionField3() {
        return opinionField3 == null ? "" : opinionField3.trim();
    }

    public void setOpinionField3(String opinionField3) {
        this.opinionField3 = opinionField3 == null ? null : opinionField3.trim();
    }

    public String getOpinionField4() {
        return opinionField4 == null ? "" : opinionField4.trim();
    }

    public void setOpinionField4(String opinionField4) {
        this.opinionField4 = opinionField4 == null ? null : opinionField4.trim();
    }

    public String getOpinionField5() {
        return opinionField5 == null ? "" : opinionField5.trim();
    }

    public void setOpinionField5(String opinionField5) {
        this.opinionField5 = opinionField5 == null ? null : opinionField5.trim();
    }

    public String getOpinionField6() {
        return opinionField6 == null ? "" : opinionField6.trim();
    }

    public void setOpinionField6(String opinionField6) {
        this.opinionField6 = opinionField6 == null ? null : opinionField6.trim();
    }

    public String getOpinionField7() {
        return opinionField7 == null ? "" : opinionField7.trim();
    }

    public void setOpinionField7(String opinionField7) {
        this.opinionField7 = opinionField7 == null ? null : opinionField7.trim();
    }

    public String getOpinionField8() {
        return opinionField8 == null ? "" : opinionField8.trim();
    }

    public void setOpinionField8(String opinionField8) {
        this.opinionField8 = opinionField8 == null ? null : opinionField8.trim();
    }

    public String getOpinionField9() {
        return opinionField9 == null ? "" : opinionField9.trim();
    }

    public void setOpinionField9(String opinionField9) {
        this.opinionField9 = opinionField9 == null ? null : opinionField9.trim();
    }

    public String getOpinionField10() {
        return opinionField10 == null ? "" : opinionField10.trim();
    }

    public void setOpinionField10(String opinionField10) {
        this.opinionField10 = opinionField10 == null ? null : opinionField10.trim();
    }

    public String getApih5FlowId() {
        return apih5FlowId == null ? "" : apih5FlowId.trim();
    }

    public void setApih5FlowId(String apih5FlowId) {
        this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
    }

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getApih5FlowStatus() {
        return apih5FlowStatus == null ? "" : apih5FlowStatus.trim();
    }

    public void setApih5FlowStatus(String apih5FlowStatus) {
        this.apih5FlowStatus = apih5FlowStatus == null ? null : apih5FlowStatus.trim();
    }

    public String getApih5FlowNodeStatus() {
        return apih5FlowNodeStatus == null ? "" : apih5FlowNodeStatus.trim();
    }

    public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
        this.apih5FlowNodeStatus = apih5FlowNodeStatus == null ? null : apih5FlowNodeStatus.trim();
    }

    public String getTempPrimaryKey() {
        return tempPrimaryKey == null ? "" : tempPrimaryKey.trim();
    }

    public void setTempPrimaryKey(String tempPrimaryKey) {
        this.tempPrimaryKey = tempPrimaryKey == null ? null : tempPrimaryKey.trim();
    }

    public List<ZxGcsgCtPriceSysItem> getCtPriceSysItemList() {
        return ctPriceSysItemList == null ? Lists.newArrayList() : ctPriceSysItemList;
    }

    public void setCtPriceSysItemList(List<ZxGcsgCtPriceSysItem> ctPriceSysItemList) {
        this.ctPriceSysItemList = ctPriceSysItemList == null ? null : ctPriceSysItemList;
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
