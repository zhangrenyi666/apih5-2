package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzProManage extends BasePojo {
    private String projectId;

    private String sort1;
    
    private String keyFlag;

    private String companyId;

    private String companyName;
    
    private String basicName;

    private String proNo;

    private String projectName;

    private String projectShortName;

    private String proCategoryId;

    private String proCategoryName;

    private String proTypeId;

    private String proTypeName;

    private String proProcessId;

    private String proProcessName;

    private String unitId;

    private String unitName;

    private String investPatten;

    private Integer sequence;

    private BigDecimal constructPeriod;

    private String runPeriod;

    private Date signDate1;

    private Date signDate2;

    private Date signDate3;

    private Date actualDate;

    private Date checkDate;

    private Date planDate;

    private Date runDate1;

    private Date runDate2;

    private Date runDate3;

    private Date runDate4;

    private String zjAppFlag;

    private String mergeFlag;

    private String areaId;

    private String areaName;

    private Date zjDate;

    private BigDecimal zjAmount1;

    private BigDecimal zjAmount2;

    private String zjNo;

    private BigDecimal goverAmount;

    private String proProfile;

    private String ext1;

    private String ext2;

    private String ext3;

    private String ext4;

    private String signFlag;

    private BigDecimal amount1 = new BigDecimal(0);

    private BigDecimal amount2 = new BigDecimal(0);

    private BigDecimal amount3 = new BigDecimal(0);

    private BigDecimal amount4;

    private BigDecimal amount5;

    private BigDecimal amount6;

    private BigDecimal amount7;

    private BigDecimal amount8;

    private BigDecimal fund1;

    private BigDecimal fund2;

    private BigDecimal fund3;

    private BigDecimal fund4;

    private BigDecimal fund5;

    private BigDecimal fund6;

    private BigDecimal fund7;

    private BigDecimal fund8;

    private BigDecimal fund9;

    private BigDecimal fund10;

    private BigDecimal fund11;

    private BigDecimal fund12;

    private String evaluate1;

    private BigDecimal evaluate2;

    private BigDecimal evaluate3;

    private String evaluate4;

    private String evaluate5;

    private BigDecimal evaluate6;

    private String evaluate7;

    private BigDecimal evaluate8;

    private BigDecimal evaluate9;

    private BigDecimal evaluate10;

    private BigDecimal evaluate11;

    private BigDecimal evaluate12;

    private BigDecimal evaluate13;

    private BigDecimal evaluate14;

    private String company1;

    private Date company2;

    private String company3;

    private BigDecimal company4;

    private BigDecimal company5;

    private BigDecimal company6;

    private BigDecimal company7 = new BigDecimal(0);

    private BigDecimal company8;

    private BigDecimal company9;

    private String company10;

    private String member1;

    private String member2;

    private String member3;

    private String member4;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String constructEnd;

    private Date handoverDatePlan;

    private Date complateDatePlan;

    private Date handoverDateActrual;

    private Date complateDateActrual;

    private String replyType;

    private String managerDepartmentName;

    private String projectType;

    private String legalPersonName;

    private String businessLicense;

    private String bylaw;

    private String location;
    
    private List<ZjTzFile> zjTzFileList;
    
    private List<ZjTzFile> zjTzBylawFileList;
    
    private List<ZjTzFile> zjTzLicenseFileList;

    private List<JSONObject> companyList;

    private String projectIdSql;
    
    private String proNoSelect;
    
    private String projectIdSelect;
    
    //项目总数
    private BigDecimal count = new BigDecimal(0);
    //判断类型
    private String type;
    //不同项目进展对应的项目数
    private BigDecimal processCount = new BigDecimal(0);
    //不同项目类型对应的项目数
    private BigDecimal typeCount = new BigDecimal(0);
    
    private BigDecimal percent = new BigDecimal(0);
    
    //起始日期
    private Date startDate;
    //终止日期
    private Date endDate;
    //当前日期
    private Date currDate;
    //建设期实际天数
    private int constructActualDay;
    //竣工期实际天数
    private int completeActualDay;
    //当前建设期天数
    private int constructCurrentDay;
    //当前竣工期天数
    private int completeCurrentDay;
    //已超期交工天数
    private int handoverOverdueDay;
    //已超期竣工天数
    private int completeOverdueDay;
    //距离交工剩余天数
    private int handoverRemainDay;
    //距离竣工剩余天数
    private int completeRemainDay;
    //实际超期交工天数
    private int handoverOverdueDayActual;
    //实际超期竣工天数
    private int completeOverdueDayActual;
    
    //工期状态
    private String periodFlag;
    
    //当前超期项目数量
    private int overdueCount;
    
    // 超期筛选
    private String overdueSelect;
    // 预警星星
    private String colourFlag;
    // 你以后的getset方法往后面加
    private String status;
    // 新增字段
    // 工期分析主体
    private String analySubject;
    // 策划批复开工日期
    private Date approvalStartDate;
    // 策划批复交工日期
    private Date approvalHandoverDate;
    // 策划批复竣工日期
    private Date approvalCompleteDate;
    // 预警日期类型
    private String dateType;
    // 工期预警
    private String periodWarn;
    // 子项目Id
    private String subprojectId;
    // 工期状态标识
    private String periodId;
    // 子项目名称
    private String subprojectName;
    // 规模控制主体
    private String sizeControlSubject;
    
	public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getSort1() {
    	return sort1 == null ? "" : sort1.trim();
	}

	public void setSort1(String sort1) {
		this.sort1 = sort1 == null ? null : sort1.trim();
	}
	
	public String getKeyFlag() {
		return keyFlag == null ? "" : keyFlag.trim();
	}

	public void setKeyFlag(String keyFlag) {
		this.keyFlag = keyFlag == null ? null : keyFlag.trim();
	}

	public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
    	 this.companyName = companyName == null ? null : companyName.trim();
    }
    
    public String getBasicName() {
    	return basicName == null ? "" : basicName.trim();
	}

	public void setBasicName(String basicName) {
		this.basicName = basicName == null ? null : basicName.trim();
	}

	public String getProNo() {
        return proNo == null ? "" : proNo.trim();
    }

    public void setProNo(String proNo) {
        this.proNo = proNo == null ? null : proNo.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }


    public void setProjectName(String projectName) {
    	 this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectShortName() {
        return projectShortName == null ? "" : projectShortName.trim();
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName == null ? null : projectShortName.trim();
    }

    public String getProCategoryId() {
        return proCategoryId == null ? "" : proCategoryId.trim();
    }

    public void setProCategoryId(String proCategoryId) {
        this.proCategoryId = proCategoryId == null ? null : proCategoryId.trim();
    }

    public String getProCategoryName() {
        return proCategoryName == null ? "" : proCategoryName.trim();
    }

    public void setProCategoryName(String proCategoryName) {
        this.proCategoryName = proCategoryName == null ? null : proCategoryName.trim();
    }

    public String getProTypeId() {
        return proTypeId == null ? "" : proTypeId.trim();
    }

    public void setProTypeId(String proTypeId) {
        this.proTypeId = proTypeId == null ? null : proTypeId.trim();
    }

    public String getProTypeName() {
        return proTypeName == null ? "" : proTypeName.trim();
    }

    public void setProTypeName(String proTypeName) {
    	this.proTypeName = proTypeName == null ? null : proTypeName.trim();
    }

    public String getProProcessId() {
        return proProcessId == null ? "" : proProcessId.trim();
    }

    public void setProProcessId(String proProcessId) {
        this.proProcessId = proProcessId == null ? null : proProcessId.trim();
    }

    public String getProProcessName() {
        return proProcessName == null ? "" : proProcessName.trim();
    }

    public void setProProcessName(String proProcessName) {
        this.proProcessName = proProcessName == null ? null : proProcessName.trim();
    }

    public String getUnitId() {
        return unitId == null ? "" : unitId.trim();
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    public String getUnitName() {
        return unitName == null ? "" : unitName.trim();
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getInvestPatten() {
        return investPatten == null ? "" : investPatten.trim();
    }

    public void setInvestPatten(String investPatten) {
        this.investPatten = investPatten == null ? null : investPatten.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public BigDecimal getConstructPeriod() {
        return constructPeriod;
    }

    public void setConstructPeriod(BigDecimal constructPeriod) {
        this.constructPeriod = constructPeriod;
    }

    public String getRunPeriod() {
        return runPeriod == null ? "" : runPeriod.trim();
    }

    public void setRunPeriod(String runPeriod) {
        this.runPeriod = runPeriod == null ? null : runPeriod.trim();
    }

    public Date getSignDate1() {
        return signDate1;
    }

    public void setSignDate1(Date signDate1) {
        this.signDate1 = signDate1;
    }

    public Date getSignDate2() {
        return signDate2;
    }

    public void setSignDate2(Date signDate2) {
        this.signDate2 = signDate2;
    }

    public Date getSignDate3() {
        return signDate3;
    }

    public void setSignDate3(Date signDate3) {
        this.signDate3 = signDate3;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getRunDate1() {
        return runDate1;
    }

    public void setRunDate1(Date runDate1) {
        this.runDate1 = runDate1;
    }

    public Date getRunDate2() {
        return runDate2;
    }

    public void setRunDate2(Date runDate2) {
        this.runDate2 = runDate2;
    }

    public Date getRunDate3() {
        return runDate3;
    }

    public void setRunDate3(Date runDate3) {
        this.runDate3 = runDate3;
    }

    public Date getRunDate4() {
        return runDate4;
    }

    public void setRunDate4(Date runDate4) {
        this.runDate4 = runDate4;
    }

    public String getZjAppFlag() {
        return zjAppFlag == null ? "" : zjAppFlag.trim();
    }

    public void setZjAppFlag(String zjAppFlag) {
        this.zjAppFlag = zjAppFlag == null ? null : zjAppFlag.trim();
    }

    public String getMergeFlag() {
        return mergeFlag == null ? "" : mergeFlag.trim();
    }

    public void setMergeFlag(String mergeFlag) {
        this.mergeFlag = mergeFlag == null ? null : mergeFlag.trim();
    }

    public String getAreaId() {
        return areaId == null ? "" : areaId.trim();
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getAreaName() {
        return areaName == null ? "" : areaName.trim();
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public Date getZjDate() {
        return zjDate;
    }

    public void setZjDate(Date zjDate) {
        this.zjDate = zjDate;
    }

    public BigDecimal getZjAmount1() {
        return zjAmount1;
    }

    public void setZjAmount1(BigDecimal zjAmount1) {
        this.zjAmount1 = zjAmount1;
    }

    public BigDecimal getZjAmount2() {
        return zjAmount2;
    }

    public void setZjAmount2(BigDecimal zjAmount2) {
        this.zjAmount2 = zjAmount2;
    }

    public String getZjNo() {
        return zjNo == null ? "" : zjNo.trim();
    }

    public void setZjNo(String zjNo) {
        this.zjNo = zjNo == null ? null : zjNo.trim();
    }

    public BigDecimal getGoverAmount() {
        return goverAmount;
    }

    public void setGoverAmount(BigDecimal goverAmount) {
        this.goverAmount = goverAmount;
    }

    public String getProProfile() {
        return proProfile == null ? "" : proProfile.trim();
    }

    public void setProProfile(String proProfile) {
        this.proProfile = proProfile == null ? null : proProfile.trim();
    }

    public String getExt1() {
        return ext1 == null ? "" : ext1.trim();
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2 == null ? "" : ext2.trim();
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getExt3() {
        return ext3 == null ? "" : ext3.trim();
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    public String getExt4() {
        return ext4 == null ? "" : ext4.trim();
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    public String getSignFlag() {
        return signFlag == null ? "" : signFlag.trim();
    }

    public void setSignFlag(String signFlag) {
        this.signFlag = signFlag == null ? null : signFlag.trim();
    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public BigDecimal getAmount3() {
        return amount3;
    }

    public void setAmount3(BigDecimal amount3) {
        this.amount3 = amount3;
    }

    public BigDecimal getAmount4() {
        return amount4;
    }

    public void setAmount4(BigDecimal amount4) {
        this.amount4 = amount4;
    }

    public BigDecimal getAmount5() {
        return amount5;
    }

    public void setAmount5(BigDecimal amount5) {
        this.amount5 = amount5;
    }

    public BigDecimal getAmount6() {
        return amount6;
    }

    public void setAmount6(BigDecimal amount6) {
        this.amount6 = amount6;
    }

    public BigDecimal getAmount7() {
        return amount7;
    }

    public void setAmount7(BigDecimal amount7) {
        this.amount7 = amount7;
    }

    public BigDecimal getAmount8() {
        return amount8;
    }

    public void setAmount8(BigDecimal amount8) {
        this.amount8 = amount8;
    }

    public BigDecimal getFund1() {
        return fund1;
    }

    public void setFund1(BigDecimal fund1) {
        this.fund1 = fund1;
    }

    public BigDecimal getFund2() {
        return fund2;
    }

    public void setFund2(BigDecimal fund2) {
        this.fund2 = fund2;
    }

    public BigDecimal getFund3() {
        return fund3;
    }

    public void setFund3(BigDecimal fund3) {
        this.fund3 = fund3;
    }

    public BigDecimal getFund4() {
        return fund4;
    }

    public void setFund4(BigDecimal fund4) {
        this.fund4 = fund4;
    }

    public BigDecimal getFund5() {
        return fund5;
    }

    public void setFund5(BigDecimal fund5) {
        this.fund5 = fund5;
    }

    public BigDecimal getFund6() {
        return fund6;
    }

    public void setFund6(BigDecimal fund6) {
        this.fund6 = fund6;
    }

    public BigDecimal getFund7() {
        return fund7;
    }

    public void setFund7(BigDecimal fund7) {
        this.fund7 = fund7;
    }

    public BigDecimal getFund8() {
        return fund8;
    }

    public void setFund8(BigDecimal fund8) {
        this.fund8 = fund8;
    }

    public BigDecimal getFund9() {
        return fund9;
    }

    public void setFund9(BigDecimal fund9) {
        this.fund9 = fund9;
    }

    public BigDecimal getFund10() {
        return fund10;
    }

    public void setFund10(BigDecimal fund10) {
        this.fund10 = fund10;
    }

    public BigDecimal getFund11() {
        return fund11;
    }

    public void setFund11(BigDecimal fund11) {
        this.fund11 = fund11;
    }

    public BigDecimal getFund12() {
        return fund12;
    }

    public void setFund12(BigDecimal fund12) {
        this.fund12 = fund12;
    }

    public String getEvaluate1() {
        return evaluate1 == null ? "" : evaluate1.trim();
    }

    public void setEvaluate1(String evaluate1) {
        this.evaluate1 = evaluate1 == null ? null : evaluate1.trim();
    }

    public BigDecimal getEvaluate2() {
        return evaluate2;
    }

    public void setEvaluate2(BigDecimal evaluate2) {
        this.evaluate2 = evaluate2;
    }

    public BigDecimal getEvaluate3() {
        return evaluate3;
    }

    public void setEvaluate3(BigDecimal evaluate3) {
        this.evaluate3 = evaluate3;
    }

    public String getEvaluate4() {
        return evaluate4 == null ? "" : evaluate4.trim();
    }

    public void setEvaluate4(String evaluate4) {
        this.evaluate4 = evaluate4 == null ? null : evaluate4.trim();
    }

    public String getEvaluate5() {
        return evaluate5 == null ? "" : evaluate5.trim();
    }

    public void setEvaluate5(String evaluate5) {
        this.evaluate5 = evaluate5 == null ? null : evaluate5.trim();
    }

    public BigDecimal getEvaluate6() {
        return evaluate6;
    }

    public void setEvaluate6(BigDecimal evaluate6) {
        this.evaluate6 = evaluate6;
    }

    public String getEvaluate7() {
        return evaluate7 == null ? "" : evaluate7.trim();
    }

    public void setEvaluate7(String evaluate7) {
        this.evaluate7 = evaluate7 == null ? null : evaluate7.trim();
    }

    public BigDecimal getEvaluate8() {
        return evaluate8;
    }

    public void setEvaluate8(BigDecimal evaluate8) {
        this.evaluate8 = evaluate8;
    }

    public BigDecimal getEvaluate9() {
        return evaluate9;
    }

    public void setEvaluate9(BigDecimal evaluate9) {
        this.evaluate9 = evaluate9;
    }

    public BigDecimal getEvaluate10() {
        return evaluate10;
    }

    public void setEvaluate10(BigDecimal evaluate10) {
        this.evaluate10 = evaluate10;
    }

    public BigDecimal getEvaluate11() {
        return evaluate11;
    }

    public void setEvaluate11(BigDecimal evaluate11) {
        this.evaluate11 = evaluate11;
    }

    public BigDecimal getEvaluate12() {
        return evaluate12;
    }

    public void setEvaluate12(BigDecimal evaluate12) {
        this.evaluate12 = evaluate12;
    }

    public BigDecimal getEvaluate13() {
        return evaluate13;
    }

    public void setEvaluate13(BigDecimal evaluate13) {
        this.evaluate13 = evaluate13;
    }

    public BigDecimal getEvaluate14() {
        return evaluate14;
    }

    public void setEvaluate14(BigDecimal evaluate14) {
        this.evaluate14 = evaluate14;
    }

    public String getCompany1() {
        return company1 == null ? "" : company1.trim();
    }

    public void setCompany1(String company1) {
    	this.company1 = company1 == null ? null : company1.trim();
    }

    public Date getCompany2() {
        return company2;
    }

    public void setCompany2(Date company2) {
        this.company2 = company2;
    }

    public String getCompany3() {
        return company3 == null ? "" : company3.trim();
    }

    public void setCompany3(String company3) {
        this.company3 = company3 == null ? null : company3.trim();
    }

    public BigDecimal getCompany4() {
        return company4;
    }

    public void setCompany4(BigDecimal company4) {
        this.company4 = company4;
    }

    public BigDecimal getCompany5() {
        return company5;
    }

    public void setCompany5(BigDecimal company5) {
        this.company5 = company5;
    }

    public BigDecimal getCompany6() {
        return company6;
    }

    public void setCompany6(BigDecimal company6) {
        this.company6 = company6;
    }

    public BigDecimal getCompany7() {
        return company7;
    }

    public BigDecimal setCompany7(BigDecimal company7) {
    	return this.company7 = company7;
    }

    public BigDecimal getCompany8() {
        return company8;
    }

    public void setCompany8(BigDecimal company8) {
        this.company8 = company8;
    }

    public BigDecimal getCompany9() {
        return company9;
    }

    public void setCompany9(BigDecimal company9) {
        this.company9 = company9;
    }

    public String getCompany10() {
        return company10 == null ? "" : company10.trim();
    }

    public void setCompany10(String company10) {
        this.company10 = company10 == null ? null : company10.trim();
    }

    public String getMember1() {
        return member1 == null ? "" : member1.trim();
    }

    public void setMember1(String member1) {
        this.member1 = member1 == null ? null : member1.trim();
    }

    public String getMember2() {
        return member2 == null ? "" : member2.trim();
    }

    public void setMember2(String member2) {
        this.member2 = member2 == null ? null : member2.trim();
    }

    public String getMember3() {
        return member3 == null ? "" : member3.trim();
    }

    public void setMember3(String member3) {
        this.member3 = member3 == null ? null : member3.trim();
    }

    public String getMember4() {
        return member4 == null ? "" : member4.trim();
    }

    public void setMember4(String member4) {
        this.member4 = member4 == null ? null : member4.trim();
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

    public String getConstructEnd() {
        return constructEnd == null ? "" : constructEnd.trim();
    }

    public void setConstructEnd(String constructEnd) {
        this.constructEnd = constructEnd == null ? null : constructEnd.trim();
    }

    public Date getHandoverDatePlan() {
        return handoverDatePlan;
    }

    public void setHandoverDatePlan(Date handoverDatePlan) {
        this.handoverDatePlan = handoverDatePlan;
    }

    public Date getComplateDatePlan() {
        return complateDatePlan;
    }

    public void setComplateDatePlan(Date complateDatePlan) {
        this.complateDatePlan = complateDatePlan;
    }

    public Date getHandoverDateActrual() {
        return handoverDateActrual;
    }

    public void setHandoverDateActrual(Date handoverDateActrual) {
        this.handoverDateActrual = handoverDateActrual;
    }

    public Date getComplateDateActrual() {
        return complateDateActrual;
    }

    public void setComplateDateActrual(Date complateDateActrual) {
        this.complateDateActrual = complateDateActrual;
    }

    public String getReplyType() {
        return replyType == null ? "" : replyType.trim();
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType == null ? null : replyType.trim();
    }

    public String getManagerDepartmentName() {
        return managerDepartmentName == null ? "" : managerDepartmentName.trim();
    }

    public void setManagerDepartmentName(String managerDepartmentName) {
        this.managerDepartmentName = managerDepartmentName == null ? null : managerDepartmentName.trim();
    }

    public String getProjectType() {
        return projectType == null ? "" : projectType.trim();
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getLegalPersonName() {
        return legalPersonName == null ? "" : legalPersonName.trim();
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName == null ? null : legalPersonName.trim();
    }

    public String getBusinessLicense() {
        return businessLicense == null ? "" : businessLicense.trim();
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public String getBylaw() {
        return bylaw == null ? "" : bylaw.trim();
    }

    public void setBylaw(String bylaw) {
        this.bylaw = bylaw == null ? null : bylaw.trim();
    }


    public String getLocation() {
        return location == null ? "" : location.trim();
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }
    
    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }

    public List<JSONObject> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<JSONObject> companyList) {
        this.companyList = companyList;
    }
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

	public String getProNoSelect() {
		return proNoSelect;
	}

	public void setProNoSelect(String proNoSelect) {
		this.proNoSelect = proNoSelect;
	}

	public String getProjectIdSelect() {
		return projectIdSelect;
	}

	public void setProjectIdSelect(String projectIdSelect) {
		this.projectIdSelect = projectIdSelect;
	}

	public List<ZjTzFile> getZjTzBylawFileList() {
		return zjTzBylawFileList;
	}

	public void setZjTzBylawFileList(List<ZjTzFile> zjTzBylawFileList) {
		this.zjTzBylawFileList = zjTzBylawFileList;
	}

	public List<ZjTzFile> getZjTzLicenseFileList() {
		return zjTzLicenseFileList;
	}

	public void setZjTzLicenseFileList(List<ZjTzFile> zjTzLicenseFileList) {
		this.zjTzLicenseFileList = zjTzLicenseFileList;
	}
	
	public String getColourFlag() {
		return colourFlag == null ? "" : colourFlag.trim();
	}

	public void setColourFlag(String colourFlag) {
		this.colourFlag = colourFlag == null ? null : colourFlag.trim();
	}

	public String getOverdueSelect() {
		return overdueSelect == null ? "" : overdueSelect.trim();
	}

	public void setOverdueSelect(String overdueSelect) {
		this.overdueSelect = overdueSelect == null ? null : overdueSelect.trim();
	}

	public int getOverdueCount() {
		return overdueCount;
	}

	public void setOverdueCount(int overdueCount) {
		this.overdueCount = overdueCount;
	}

	public String getPeriodFlag() {
		return periodFlag == null ? "" : periodFlag.trim();
	}

	public void setPeriodFlag(String periodFlag) {
		this.periodFlag = periodFlag == null ? null : periodFlag.trim();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}



	public int getConstructActualDay() {
		return constructActualDay;
	}

	public void setConstructActualDay(int constructActualDay) {
		this.constructActualDay = constructActualDay;
	}

	public int getCompleteActualDay() {
		return completeActualDay;
	}

	public void setCompleteActualDay(int completeActualDay) {
		this.completeActualDay = completeActualDay;
	}

	public int getConstructCurrentDay() {
		return constructCurrentDay;
	}

	public void setConstructCurrentDay(int constructCurrentDay) {
		this.constructCurrentDay = constructCurrentDay;
	}

	public int getCompleteCurrentDay() {
		return completeCurrentDay;
	}

	public void setCompleteCurrentDay(int completeCurrentDay) {
		this.completeCurrentDay = completeCurrentDay;
	}

	public int getHandoverOverdueDay() {
		return handoverOverdueDay;
	}

	public void setHandoverOverdueDay(int handoverOverdueDay) {
		this.handoverOverdueDay = handoverOverdueDay;
	}

	public int getCompleteOverdueDay() {
		return completeOverdueDay;
	}

	public void setCompleteOverdueDay(int completeOverdueDay) {
		this.completeOverdueDay = completeOverdueDay;
	}

	public int getHandoverRemainDay() {
		return handoverRemainDay;
	}

	public void setHandoverRemainDay(int handoverRemainDay) {
		this.handoverRemainDay = handoverRemainDay;
	}

	public int getCompleteRemainDay() {
		return completeRemainDay;
	}

	public void setCompleteRemainDay(int completeRemainDay) {
		this.completeRemainDay = completeRemainDay;
	}


	public int getHandoverOverdueDayActual() {
		return handoverOverdueDayActual;
	}

	public void setHandoverOverdueDayActual(int handoverOverdueDayActual) {
		this.handoverOverdueDayActual = handoverOverdueDayActual;
	}

	public int getCompleteOverdueDayActual() {
		return completeOverdueDayActual;
	}

	public void setCompleteOverdueDayActual(int completeOverdueDayActual) {
		this.completeOverdueDayActual = completeOverdueDayActual;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getProcessCount() {
		return processCount;
	}

	public void setProcessCount(BigDecimal processCount) {
		this.processCount = processCount;
	}

	public BigDecimal getTypeCount() {
		return typeCount;
	}

	public void setTypeCount(BigDecimal typeCount) {
		this.typeCount = typeCount;
	}

	public String getStatus() {
		return status == null ? "" : status.trim();
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getAnalySubject() {
		return analySubject == null ? "" : analySubject.trim();
	}

	public void setAnalySubject(String analySubject) {
		this.analySubject = analySubject == null ? null : analySubject.trim();
	}
	
	public Date getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(Date approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	public Date getApprovalHandoverDate() {
		return approvalHandoverDate;
	}

	public void setApprovalHandoverDate(Date approvalHandoverDate) {
		this.approvalHandoverDate = approvalHandoverDate;
	}

	public Date getApprovalCompleteDate() {
		return approvalCompleteDate;
	}

	public void setApprovalCompleteDate(Date approvalCompleteDate) {
		this.approvalCompleteDate = approvalCompleteDate;
	}

	public String getDateType() {
		return dateType == null ? "" : dateType.trim();
	}

	public void setDateType(String dateType) {
		this.dateType = dateType == null ? null : dateType.trim();
	}

	public String getPeriodWarn() {
		return periodWarn == null ? "" : periodWarn.trim();
	}

	public void setPeriodWarn(String periodWarn) {
		this.periodWarn = periodWarn == null ? null : periodWarn.trim();
	}

	public String getSubprojectId() {
		return subprojectId == null ? "" : subprojectId.trim();
	}

	public void setSubprojectId(String subprojectId) {
		this.subprojectId = subprojectId == null ? null : subprojectId.trim();
	}

	public String getPeriodId() {
		return periodId == null ? "" : periodId.trim();
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId == null ? null : periodId.trim();
	}

	public String getSubprojectName() {
		return subprojectName;
	}

	public void setSubprojectName(String subprojectName) {
		this.subprojectName = subprojectName;
	}

	public String getSizeControlSubject() {
		return sizeControlSubject;
	}

	public void setSizeControlSubject(String sizeControlSubject) {
		this.sizeControlSubject = sizeControlSubject;
	}
	
}

