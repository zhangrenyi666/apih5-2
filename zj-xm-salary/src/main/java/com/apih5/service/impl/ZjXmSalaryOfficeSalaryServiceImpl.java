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
import com.apih5.mybatis.dao.ZjXmSalaryOfficeSalaryMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryOfficeSalary;
import com.apih5.service.ZjXmSalaryOfficeSalaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryOfficeSalaryService")
public class ZjXmSalaryOfficeSalaryServiceImpl implements ZjXmSalaryOfficeSalaryService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmSalaryOfficeSalaryMapper zjXmSalaryOfficeSalaryMapper;

	@Override
	public ResponseEntity getZjXmSalaryOfficeSalaryListByCondition(ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary) {
		if (zjXmSalaryOfficeSalary == null) {
			zjXmSalaryOfficeSalary = new ZjXmSalaryOfficeSalary();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryOfficeSalary.getPage(), zjXmSalaryOfficeSalary.getLimit());
		// 获取数据
		List<ZjXmSalaryOfficeSalary> zjXmSalaryOfficeSalaryList = zjXmSalaryOfficeSalaryMapper
				.selectByZjXmSalaryOfficeSalaryList(zjXmSalaryOfficeSalary);
		// 得到分页信息
		PageInfo<ZjXmSalaryOfficeSalary> p = new PageInfo<>(zjXmSalaryOfficeSalaryList);

		return repEntity.okList(zjXmSalaryOfficeSalaryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryOfficeSalaryDetails(ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary) {
		if (zjXmSalaryOfficeSalary == null) {
			zjXmSalaryOfficeSalary = new ZjXmSalaryOfficeSalary();
		}
		// 获取数据
		ZjXmSalaryOfficeSalary dbZjXmSalaryOfficeSalary = zjXmSalaryOfficeSalaryMapper
				.selectByPrimaryKey(zjXmSalaryOfficeSalary.getSalaryId());
		// 数据存在
		if (dbZjXmSalaryOfficeSalary != null) {
			return repEntity.ok(dbZjXmSalaryOfficeSalary);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryOfficeSalary(ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryOfficeSalary.setSalaryId(UuidUtil.generate());
		zjXmSalaryOfficeSalary.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryOfficeSalaryMapper.insert(zjXmSalaryOfficeSalary);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryOfficeSalary);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryOfficeSalary(ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryOfficeSalary dbzjXmSalaryOfficeSalary = zjXmSalaryOfficeSalaryMapper
				.selectByPrimaryKey(zjXmSalaryOfficeSalary.getSalaryId());
		if (dbzjXmSalaryOfficeSalary != null && StrUtil.isNotEmpty(dbzjXmSalaryOfficeSalary.getSalaryId())) {
			// 标题
			dbzjXmSalaryOfficeSalary.setTitle(zjXmSalaryOfficeSalary.getTitle());
			// 年月
			dbzjXmSalaryOfficeSalary.setYearMonth(zjXmSalaryOfficeSalary.getYearMonth());
			// 总计（元）
			dbzjXmSalaryOfficeSalary.setTotalAmount(zjXmSalaryOfficeSalary.getTotalAmount());
			// 备注
			dbzjXmSalaryOfficeSalary.setOpinionField1(zjXmSalaryOfficeSalary.getOpinionField1());
			// 备注
			dbzjXmSalaryOfficeSalary.setOpinionField2(zjXmSalaryOfficeSalary.getOpinionField2());
			// 备注
			dbzjXmSalaryOfficeSalary.setOpinionField3(zjXmSalaryOfficeSalary.getOpinionField3());
			// 备注
			dbzjXmSalaryOfficeSalary.setOpinionField4(zjXmSalaryOfficeSalary.getOpinionField4());
			// 备注
			dbzjXmSalaryOfficeSalary.setOpinionField5(zjXmSalaryOfficeSalary.getOpinionField5());
			// 流程id
			dbzjXmSalaryOfficeSalary.setApih5FlowId(zjXmSalaryOfficeSalary.getApih5FlowId());
			// work_id
			dbzjXmSalaryOfficeSalary.setWorkId(zjXmSalaryOfficeSalary.getWorkId());
			// 工序审核状态
			dbzjXmSalaryOfficeSalary.setApih5FlowStatus(zjXmSalaryOfficeSalary.getApih5FlowStatus());
			// 流程状态
			dbzjXmSalaryOfficeSalary.setApih5FlowNodeStatus(zjXmSalaryOfficeSalary.getApih5FlowNodeStatus());
			// 备注
			dbzjXmSalaryOfficeSalary.setRemarks(zjXmSalaryOfficeSalary.getRemarks());
			// 排序
			dbzjXmSalaryOfficeSalary.setSort(zjXmSalaryOfficeSalary.getSort());
			// 共通
			dbzjXmSalaryOfficeSalary.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryOfficeSalaryMapper.updateByPrimaryKey(dbzjXmSalaryOfficeSalary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryOfficeSalary);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryOfficeSalary(
			List<ZjXmSalaryOfficeSalary> zjXmSalaryOfficeSalaryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryOfficeSalaryList != null && zjXmSalaryOfficeSalaryList.size() > 0) {
			ZjXmSalaryOfficeSalary zjXmSalaryOfficeSalary = new ZjXmSalaryOfficeSalary();
			zjXmSalaryOfficeSalary.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryOfficeSalaryMapper.batchDeleteUpdateZjXmSalaryOfficeSalary(zjXmSalaryOfficeSalaryList,
					zjXmSalaryOfficeSalary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryOfficeSalaryList);
		}

	}
}