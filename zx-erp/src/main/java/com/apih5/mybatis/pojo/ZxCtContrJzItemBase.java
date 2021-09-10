package com.apih5.mybatis.pojo;

import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZxCtContrJzItemBase extends BasePojo {
    // 主键
    private String id;

    // 建造合同类型
    private String jzType;

    // 项目
    private String subType;

    // 项目内容
    private String subDetail;

    // 排序
    private int orderStr=0;

    // mainID
    private String mainID;

    // 是否初始化数据
    private String isInit;

    // 项目子类
    private String subType2;

    // 项目内容明细
    private String subDetail2;

    // 行标识
    private String hangCode;

    // 行类型
    private String isHuizong;

    // 汇总到哪一行
    private String huizongCode;

    // 是否减项
    private String isReduce;

    // 备注
    private String remarks;
    
    
    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJzType() {
        return jzType == null ? "" : jzType.trim();
    }

    public void setJzType(String jzType) {
        this.jzType = jzType == null ? null : jzType.trim();
    }

    public String getSubType() {
        return subType == null ? "" : subType.trim();
    }

    public void setSubType(String subType) {
        this.subType = subType == null ? null : subType.trim();
    }

    public String getSubDetail() {
        return subDetail == null ? "" : subDetail.trim();
    }

    public void setSubDetail(String subDetail) {
        this.subDetail = subDetail == null ? null : subDetail.trim();
    }

    public int getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(int orderStr) {
        this.orderStr = orderStr;
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public String getIsInit() {
        return isInit == null ? "" : isInit.trim();
    }

    public void setIsInit(String isInit) {
        this.isInit = isInit == null ? null : isInit.trim();
    }

    public String getSubType2() {
        return subType2 == null ? "" : subType2.trim();
    }

    public void setSubType2(String subType2) {
        this.subType2 = subType2 == null ? null : subType2.trim();
    }

    public String getSubDetail2() {
        return subDetail2 == null ? "" : subDetail2.trim();
    }

    public void setSubDetail2(String subDetail2) {
        this.subDetail2 = subDetail2 == null ? null : subDetail2.trim();
    }

    public String getHangCode() {
        return hangCode == null ? "" : hangCode.trim();
    }

    public void setHangCode(String hangCode) {
        this.hangCode = hangCode == null ? null : hangCode.trim();
    }

    public String getIsHuizong() {
        return isHuizong == null ? "" : isHuizong.trim();
    }

    public void setIsHuizong(String isHuizong) {
        this.isHuizong = isHuizong == null ? null : isHuizong.trim();
    }

    public String getHuizongCode() {
        return huizongCode == null ? "" : huizongCode.trim();
    }

    public void setHuizongCode(String huizongCode) {
        this.huizongCode = huizongCode == null ? null : huizongCode.trim();
    }

    public String getIsReduce() {
        return isReduce == null ? "" : isReduce.trim();
    }

    public void setIsReduce(String isReduce) {
        this.isReduce = isReduce == null ? null : isReduce.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

}
