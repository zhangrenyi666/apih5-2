package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzThousandDeduct extends BasePojo {
    private String thousandDeductId;

    private String thousandCheckId;

    private String thousandCheckBaseId;

    private String evalPro;

    private String evalIndex;

    private String evalContent;

    private BigDecimal score;

    private BigDecimal actualScore;

    private BigDecimal deduct;

    private String deductDesc;

    private Integer orderFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String groupByFlagCodePid;

    private String count;

    private String updateFlag;
    
    private List<ZjTzThousandDeduct> zjTzThousandDeductList;

    public String getThousandDeductId() {
        return thousandDeductId == null ? "" : thousandDeductId.trim();
    }

    public void setThousandDeductId(String thousandDeductId) {
        this.thousandDeductId = thousandDeductId == null ? null : thousandDeductId.trim();
    }

    public String getThousandCheckId() {
        return thousandCheckId == null ? "" : thousandCheckId.trim();
    }

    public void setThousandCheckId(String thousandCheckId) {
        this.thousandCheckId = thousandCheckId == null ? null : thousandCheckId.trim();
    }

    public String getThousandCheckBaseId() {
        return thousandCheckBaseId == null ? "" : thousandCheckBaseId.trim();
    }

    public void setThousandCheckBaseId(String thousandCheckBaseId) {
        this.thousandCheckBaseId = thousandCheckBaseId == null ? null : thousandCheckBaseId.trim();
    }

    public String getEvalPro() {
        return evalPro == null ? "" : evalPro.trim();
    }

    public void setEvalPro(String evalPro) {
        this.evalPro = evalPro == null ? null : evalPro.trim();
    }

    public String getEvalIndex() {
        return evalIndex == null ? "" : evalIndex.trim();
    }

    public void setEvalIndex(String evalIndex) {
        this.evalIndex = evalIndex == null ? null : evalIndex.trim();
    }

    public String getEvalContent() {
        return evalContent == null ? "" : evalContent.trim();
    }

    public void setEvalContent(String evalContent) {
        this.evalContent = evalContent == null ? null : evalContent.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getActualScore() {
        return actualScore;
    }

    public void setActualScore(BigDecimal actualScore) {
        this.actualScore = actualScore;
    }

    public BigDecimal getDeduct() {
        return deduct;
    }

    public void setDeduct(BigDecimal deduct) {
        this.deduct = deduct;
    }

    public String getDeductDesc() {
        return deductDesc == null ? "" : deductDesc.trim();
    }

    public void setDeductDesc(String deductDesc) {
        this.deductDesc = deductDesc == null ? null : deductDesc.trim();
    }

    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

    public String getGroupByFlagCodePid() {
        return groupByFlagCodePid == null ? "" : groupByFlagCodePid.trim();
    }

    public void setGroupByFlagCodePid(String groupByFlagCodePid) {
        this.groupByFlagCodePid = groupByFlagCodePid == null ? null : groupByFlagCodePid.trim();
    }

    public String getCount() {
        return count == null ? "" : count.trim();
    }

    public void setCount(String count) {
        this.count = count == null ? null : count.trim();
    }

    public String getUpdateFlag() {
        return updateFlag == null ? "" : updateFlag.trim();
    }

    public void setUpdateFlag(String updateFlag) {
        this.updateFlag = updateFlag == null ? null : updateFlag.trim();
    }

	public List<ZjTzThousandDeduct> getZjTzThousandDeductList() {
		return zjTzThousandDeductList;
	}

	public void setZjTzThousandDeductList(List<ZjTzThousandDeduct> zjTzThousandDeductList) {
		this.zjTzThousandDeductList = zjTzThousandDeductList;
	}
    
    

}

