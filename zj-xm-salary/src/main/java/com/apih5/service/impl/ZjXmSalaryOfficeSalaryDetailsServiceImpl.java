package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmSalaryOfficeSalaryDetailsMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryOfficeSalaryDetails;
import com.apih5.service.ZjXmSalaryOfficeSalaryDetailsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryOfficeSalaryDetailsService")
public class ZjXmSalaryOfficeSalaryDetailsServiceImpl implements ZjXmSalaryOfficeSalaryDetailsService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmSalaryOfficeSalaryDetailsMapper zjXmSalaryOfficeSalaryDetailsMapper;

	@Override
	public ResponseEntity getZjXmSalaryOfficeSalaryDetailsListByCondition(
			ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails) {
		if (zjXmSalaryOfficeSalaryDetails == null) {
			zjXmSalaryOfficeSalaryDetails = new ZjXmSalaryOfficeSalaryDetails();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryOfficeSalaryDetails.getPage(), zjXmSalaryOfficeSalaryDetails.getLimit());
		// 获取数据
		List<ZjXmSalaryOfficeSalaryDetails> zjXmSalaryOfficeSalaryDetailsList = zjXmSalaryOfficeSalaryDetailsMapper
				.selectByZjXmSalaryOfficeSalaryDetailsList(zjXmSalaryOfficeSalaryDetails);
		// 得到分页信息
		PageInfo<ZjXmSalaryOfficeSalaryDetails> p = new PageInfo<>(zjXmSalaryOfficeSalaryDetailsList);

		return repEntity.okList(zjXmSalaryOfficeSalaryDetailsList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryOfficeSalaryDetailsDetails(
			ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails) {
		if (zjXmSalaryOfficeSalaryDetails == null) {
			zjXmSalaryOfficeSalaryDetails = new ZjXmSalaryOfficeSalaryDetails();
		}
		// 获取数据
		ZjXmSalaryOfficeSalaryDetails dbZjXmSalaryOfficeSalaryDetails = zjXmSalaryOfficeSalaryDetailsMapper
				.selectByPrimaryKey(zjXmSalaryOfficeSalaryDetails.getDetailsId());
		// 数据存在
		if (dbZjXmSalaryOfficeSalaryDetails != null) {
			return repEntity.ok(dbZjXmSalaryOfficeSalaryDetails);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryOfficeSalaryDetails(
			ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryOfficeSalaryDetails.setDetailsId(UuidUtil.generate());
		zjXmSalaryOfficeSalaryDetails.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryOfficeSalaryDetailsMapper.insert(zjXmSalaryOfficeSalaryDetails);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryOfficeSalaryDetails);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryOfficeSalaryDetails(
			ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryOfficeSalaryDetails dbzjXmSalaryOfficeSalaryDetails = zjXmSalaryOfficeSalaryDetailsMapper
				.selectByPrimaryKey(zjXmSalaryOfficeSalaryDetails.getDetailsId());
		if (dbzjXmSalaryOfficeSalaryDetails != null
				&& StrUtil.isNotEmpty(dbzjXmSalaryOfficeSalaryDetails.getDetailsId())) {
			// sysUser表主键
			dbzjXmSalaryOfficeSalaryDetails.setUserKey(zjXmSalaryOfficeSalaryDetails.getUserKey());
			// 姓名
			dbzjXmSalaryOfficeSalaryDetails.setRealName(zjXmSalaryOfficeSalaryDetails.getRealName());
			// 证件类型
			dbzjXmSalaryOfficeSalaryDetails.setIdType(zjXmSalaryOfficeSalaryDetails.getIdType());
			// 证件号码
			dbzjXmSalaryOfficeSalaryDetails.setIdNumber(zjXmSalaryOfficeSalaryDetails.getIdNumber());
			// 人员类别
			dbzjXmSalaryOfficeSalaryDetails.setUserType(zjXmSalaryOfficeSalaryDetails.getUserType());
			// 所属项目
			dbzjXmSalaryOfficeSalaryDetails.setProjectId(zjXmSalaryOfficeSalaryDetails.getProjectId());
			// 岗薪基数
			dbzjXmSalaryOfficeSalaryDetails.setSalaryBase(zjXmSalaryOfficeSalaryDetails.getSalaryBase());
			// 出勤天数
			dbzjXmSalaryOfficeSalaryDetails.setAttendanceDays(zjXmSalaryOfficeSalaryDetails.getAttendanceDays());
			// 日历天数
			dbzjXmSalaryOfficeSalaryDetails.setCalendarDays(zjXmSalaryOfficeSalaryDetails.getCalendarDays());
			// 岗位工资
			dbzjXmSalaryOfficeSalaryDetails.setPositionSalary(zjXmSalaryOfficeSalaryDetails.getPositionSalary());
			// 岗位等级id
			dbzjXmSalaryOfficeSalaryDetails.setLevelId(zjXmSalaryOfficeSalaryDetails.getLevelId());
			// 档位岗薪id
			dbzjXmSalaryOfficeSalaryDetails.setSalaryId(zjXmSalaryOfficeSalaryDetails.getSalaryId());
			// 岗级及岗薪id
			dbzjXmSalaryOfficeSalaryDetails.setLevelSalaryId(zjXmSalaryOfficeSalaryDetails.getLevelSalaryId());
			// 学历津贴
			dbzjXmSalaryOfficeSalaryDetails
					.setEducationAllowance(zjXmSalaryOfficeSalaryDetails.getEducationAllowance());
			// 工龄
			dbzjXmSalaryOfficeSalaryDetails.setWorkYears(zjXmSalaryOfficeSalaryDetails.getWorkYears());
			// 工龄津贴
			dbzjXmSalaryOfficeSalaryDetails.setYearAllowance(zjXmSalaryOfficeSalaryDetails.getYearAllowance());
			// 职称津贴
			dbzjXmSalaryOfficeSalaryDetails.setTitleAllowance(zjXmSalaryOfficeSalaryDetails.getTitleAllowance());
			// 儿补
			dbzjXmSalaryOfficeSalaryDetails.setChildAllowance(zjXmSalaryOfficeSalaryDetails.getChildAllowance());
			// 高原津贴
			dbzjXmSalaryOfficeSalaryDetails.setPlateauAllowance(zjXmSalaryOfficeSalaryDetails.getPlateauAllowance());
			// 高温津贴
			dbzjXmSalaryOfficeSalaryDetails.setHighTempAllowance(zjXmSalaryOfficeSalaryDetails.getHighTempAllowance());
			// 证书使用费
			dbzjXmSalaryOfficeSalaryDetails
					.setCertificateAllowance(zjXmSalaryOfficeSalaryDetails.getCertificateAllowance());
			// 安全绩效费
			dbzjXmSalaryOfficeSalaryDetails
					.setPerformanceAllowance(zjXmSalaryOfficeSalaryDetails.getPerformanceAllowance());
			// 其他工资
			dbzjXmSalaryOfficeSalaryDetails.setOtherWages(zjXmSalaryOfficeSalaryDetails.getOtherWages());
			// 养老保险
			dbzjXmSalaryOfficeSalaryDetails
					.setEndowmentInsurance(zjXmSalaryOfficeSalaryDetails.getEndowmentInsurance());
			// 失业保险
			dbzjXmSalaryOfficeSalaryDetails
					.setUnemploymentInsurance(zjXmSalaryOfficeSalaryDetails.getUnemploymentInsurance());
			// 医疗保险
			dbzjXmSalaryOfficeSalaryDetails.setMedicalInsurance(zjXmSalaryOfficeSalaryDetails.getMedicalInsurance());
			// 住房公积金
			dbzjXmSalaryOfficeSalaryDetails.setHousingFund(zjXmSalaryOfficeSalaryDetails.getHousingFund());
			// 企业年金
			dbzjXmSalaryOfficeSalaryDetails.setEnterpriseAnnuity(zjXmSalaryOfficeSalaryDetails.getEnterpriseAnnuity());
			// 个人所得税
			dbzjXmSalaryOfficeSalaryDetails
					.setIndividualIncomeTax(zjXmSalaryOfficeSalaryDetails.getIndividualIncomeTax());
			// 个人起征点
			dbzjXmSalaryOfficeSalaryDetails.setPersonalThreshold(zjXmSalaryOfficeSalaryDetails.getPersonalThreshold());
			// 个人专项抵扣
			dbzjXmSalaryOfficeSalaryDetails
					.setPersonalSpecialDeduction(zjXmSalaryOfficeSalaryDetails.getPersonalSpecialDeduction());
			// 其他应税工资
			dbzjXmSalaryOfficeSalaryDetails.setOtherTaxableWages(zjXmSalaryOfficeSalaryDetails.getOtherTaxableWages());
			// 通讯津贴
			dbzjXmSalaryOfficeSalaryDetails
					.setCommunicationAllowance(zjXmSalaryOfficeSalaryDetails.getCommunicationAllowance());
			// 本次应纳税所得额
			dbzjXmSalaryOfficeSalaryDetails
					.setCurrentTaxableIncome(zjXmSalaryOfficeSalaryDetails.getCurrentTaxableIncome());
			// 累计应纳税所得额
			dbzjXmSalaryOfficeSalaryDetails
					.setAccumulatedTaxableIncome(zjXmSalaryOfficeSalaryDetails.getAccumulatedTaxableIncome());
			// 累计个税
			dbzjXmSalaryOfficeSalaryDetails
					.setAccumulatedPersonalIncomeTax(zjXmSalaryOfficeSalaryDetails.getAccumulatedPersonalIncomeTax());
			// 累计已扣个税
			dbzjXmSalaryOfficeSalaryDetails.setAccumulatedIndividualIncomeTaxDeducted(
					zjXmSalaryOfficeSalaryDetails.getAccumulatedIndividualIncomeTaxDeducted());
			// 实发合计
			dbzjXmSalaryOfficeSalaryDetails.setTotalPaid(zjXmSalaryOfficeSalaryDetails.getTotalPaid());
			// 备注
			dbzjXmSalaryOfficeSalaryDetails.setOpinionField1(zjXmSalaryOfficeSalaryDetails.getOpinionField1());
			// 备注
			dbzjXmSalaryOfficeSalaryDetails.setOpinionField2(zjXmSalaryOfficeSalaryDetails.getOpinionField2());
			// 备注
			dbzjXmSalaryOfficeSalaryDetails.setOpinionField3(zjXmSalaryOfficeSalaryDetails.getOpinionField3());
			// 备注
			dbzjXmSalaryOfficeSalaryDetails.setOpinionField4(zjXmSalaryOfficeSalaryDetails.getOpinionField4());
			// 备注
			dbzjXmSalaryOfficeSalaryDetails.setOpinionField5(zjXmSalaryOfficeSalaryDetails.getOpinionField5());
			// 流程id
			dbzjXmSalaryOfficeSalaryDetails.setApih5FlowId(zjXmSalaryOfficeSalaryDetails.getApih5FlowId());
			// work_id
			dbzjXmSalaryOfficeSalaryDetails.setWorkId(zjXmSalaryOfficeSalaryDetails.getWorkId());
			// 工序审核状态
			dbzjXmSalaryOfficeSalaryDetails.setApih5FlowStatus(zjXmSalaryOfficeSalaryDetails.getApih5FlowStatus());
			// 流程状态
			dbzjXmSalaryOfficeSalaryDetails
					.setApih5FlowNodeStatus(zjXmSalaryOfficeSalaryDetails.getApih5FlowNodeStatus());
			// 备注
			dbzjXmSalaryOfficeSalaryDetails.setRemarks(zjXmSalaryOfficeSalaryDetails.getRemarks());
			// 排序
			dbzjXmSalaryOfficeSalaryDetails.setSort(zjXmSalaryOfficeSalaryDetails.getSort());
			// 共通
			dbzjXmSalaryOfficeSalaryDetails.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryOfficeSalaryDetailsMapper.updateByPrimaryKey(dbzjXmSalaryOfficeSalaryDetails);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryOfficeSalaryDetails);
		}
	}

	@Override
    public ResponseEntity batchDeleteUpdateZjXmSalaryOfficeSalaryDetails(List<ZjXmSalaryOfficeSalaryDetails> zjXmSalaryOfficeSalaryDetailsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmSalaryOfficeSalaryDetailsList != null && zjXmSalaryOfficeSalaryDetailsList.size() > 0) {
           ZjXmSalaryOfficeSalaryDetails zjXmSalaryOfficeSalaryDetails = new ZjXmSalaryOfficeSalaryDetails();
           zjXmSalaryOfficeSalaryDetails.setModifyUserInfo(userKey, realName);
           flag = zjXmSalaryOfficeSalaryDetailsMapper.batchDeleteUpdateZjXmSalaryOfficeSalaryDetails(zjXmSalaryOfficeSalaryDetailsList, zjXmSalaryOfficeSalaryDetails);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmSalaryOfficeSalaryDetailsList);
        }
    }
}