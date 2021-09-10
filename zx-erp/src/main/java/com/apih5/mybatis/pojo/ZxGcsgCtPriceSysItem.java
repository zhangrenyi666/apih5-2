package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCtPriceSysItem extends BasePojo {
    // 主键
    private String ctPriceSysItemId;

    // 合同单价分析表ID(mainID)
    private String ctPriceSysId;

    // 标准工序ID
    private String processID;

    // 工序编码
    private String processNo;

    // 标准工序名称
    private String processName;

    // 最后编辑时间
    private Date editTime;

    // 人工费
    private BigDecimal rgf;

    // 机械设备
    private BigDecimal jxsb;

    // 周转材料
    private BigDecimal zzcl;

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

    // 单价
    private BigDecimal price;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getCtPriceSysItemId() {
        return ctPriceSysItemId == null ? "" : ctPriceSysItemId.trim();
    }

    public void setCtPriceSysItemId(String ctPriceSysItemId) {
        this.ctPriceSysItemId = ctPriceSysItemId == null ? null : ctPriceSysItemId.trim();
    }

    public String getCtPriceSysId() {
        return ctPriceSysId == null ? "" : ctPriceSysId.trim();
    }

    public void setCtPriceSysId(String ctPriceSysId) {
        this.ctPriceSysId = ctPriceSysId == null ? null : ctPriceSysId.trim();
    }

    public String getProcessID() {
        return processID == null ? "" : processID.trim();
    }

    public void setProcessID(String processID) {
        this.processID = processID == null ? null : processID.trim();
    }

    public String getProcessNo() {
        return processNo == null ? "" : processNo.trim();
    }

    public void setProcessNo(String processNo) {
        this.processNo = processNo == null ? null : processNo.trim();
    }

    public String getProcessName() {
        return processName == null ? "" : processName.trim();
    }

    public void setProcessName(String processName) {
        this.processName = processName == null ? null : processName.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public BigDecimal getRgf() {
        return rgf;
    }

    public void setRgf(BigDecimal rgf) {
        this.rgf = rgf;
    }

    public BigDecimal getJxsb() {
        return jxsb;
    }

    public void setJxsb(BigDecimal jxsb) {
        this.jxsb = jxsb;
    }

    public BigDecimal getZzcl() {
        return zzcl;
    }

    public void setZzcl(BigDecimal zzcl) {
        this.zzcl = zzcl;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
