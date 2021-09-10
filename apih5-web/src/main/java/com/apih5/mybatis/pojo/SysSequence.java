package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class SysSequence extends BasePojo {
    private String seqName;

    private Integer currentVal;

    private Integer incrementVal;

    private List<SysSequence> sysSequenceList;

    public String getSeqName() {
        return seqName == null ? "" : seqName.trim();
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName == null ? null : seqName.trim();
    }

    public Integer getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(Integer currentVal) {
        this.currentVal = currentVal;
    }

    public Integer getIncrementVal() {
        return incrementVal;
    }

    public void setIncrementVal(Integer incrementVal) {
        this.incrementVal = incrementVal;
    }

    public List<SysSequence> getSysSequenceList() {
        return sysSequenceList;
    }

    public void setSysSequenceList(List<SysSequence> sysSequenceList) {
        this.sysSequenceList = sysSequenceList;
    }

}

