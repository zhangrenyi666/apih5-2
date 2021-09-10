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
import com.apih5.mybatis.dao.ZjXmSalaryWageBasicLibraryMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryWageBasicLibrary;
import com.apih5.service.ZjXmSalaryWageBasicLibraryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryWageBasicLibraryService")
public class ZjXmSalaryWageBasicLibraryServiceImpl implements ZjXmSalaryWageBasicLibraryService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmSalaryWageBasicLibraryMapper zjXmSalaryWageBasicLibraryMapper;

	@Override
	public ResponseEntity getZjXmSalaryWageBasicLibraryListByCondition(
			ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary) {
		if (zjXmSalaryWageBasicLibrary == null) {
			zjXmSalaryWageBasicLibrary = new ZjXmSalaryWageBasicLibrary();
		}
		// 分页查询
		PageHelper.startPage(zjXmSalaryWageBasicLibrary.getPage(), zjXmSalaryWageBasicLibrary.getLimit());
		// 获取数据
		List<ZjXmSalaryWageBasicLibrary> zjXmSalaryWageBasicLibraryList = zjXmSalaryWageBasicLibraryMapper
				.selectByZjXmSalaryWageBasicLibraryList(zjXmSalaryWageBasicLibrary);
		// 得到分页信息
		PageInfo<ZjXmSalaryWageBasicLibrary> p = new PageInfo<>(zjXmSalaryWageBasicLibraryList);

		return repEntity.okList(zjXmSalaryWageBasicLibraryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmSalaryWageBasicLibraryDetails(ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary) {
		if (zjXmSalaryWageBasicLibrary == null) {
			zjXmSalaryWageBasicLibrary = new ZjXmSalaryWageBasicLibrary();
		}
		// 获取数据
		ZjXmSalaryWageBasicLibrary dbZjXmSalaryWageBasicLibrary = zjXmSalaryWageBasicLibraryMapper
				.selectByPrimaryKey(zjXmSalaryWageBasicLibrary.getLibraryId());
		// 数据存在
		if (dbZjXmSalaryWageBasicLibrary != null) {
			return repEntity.ok(dbZjXmSalaryWageBasicLibrary);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmSalaryWageBasicLibrary(ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmSalaryWageBasicLibrary.setLibraryId(UuidUtil.generate());
		zjXmSalaryWageBasicLibrary.setCreateUserInfo(userKey, realName);
		int flag = zjXmSalaryWageBasicLibraryMapper.insert(zjXmSalaryWageBasicLibrary);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmSalaryWageBasicLibrary);
		}
	}

	@Override
	public ResponseEntity updateZjXmSalaryWageBasicLibrary(ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmSalaryWageBasicLibrary dbzjXmSalaryWageBasicLibrary = zjXmSalaryWageBasicLibraryMapper
				.selectByPrimaryKey(zjXmSalaryWageBasicLibrary.getLibraryId());
		if (dbzjXmSalaryWageBasicLibrary != null && StrUtil.isNotEmpty(dbzjXmSalaryWageBasicLibrary.getLibraryId())) {
			// sysUser表主键
			dbzjXmSalaryWageBasicLibrary.setUserKey(zjXmSalaryWageBasicLibrary.getUserKey());
			// sysUser扩展表主键
			dbzjXmSalaryWageBasicLibrary.setExtensionId(zjXmSalaryWageBasicLibrary.getExtensionId());
			// 姓名
			dbzjXmSalaryWageBasicLibrary.setRealName(zjXmSalaryWageBasicLibrary.getRealName());
			// 证件类型
			dbzjXmSalaryWageBasicLibrary.setIdType(zjXmSalaryWageBasicLibrary.getIdType());
			// 证件号码
			dbzjXmSalaryWageBasicLibrary.setIdNumber(zjXmSalaryWageBasicLibrary.getIdNumber());
			// 人员类别
			dbzjXmSalaryWageBasicLibrary.setUserType(zjXmSalaryWageBasicLibrary.getUserType());
			// 岗薪基数
			dbzjXmSalaryWageBasicLibrary.setSalaryBase(zjXmSalaryWageBasicLibrary.getSalaryBase());
			// 岗位工资
			dbzjXmSalaryWageBasicLibrary.setPositionSalary(zjXmSalaryWageBasicLibrary.getPositionSalary());
			// 岗位等级id
			dbzjXmSalaryWageBasicLibrary.setLevelId(zjXmSalaryWageBasicLibrary.getLevelId());
			// 档位岗薪id
			dbzjXmSalaryWageBasicLibrary.setSalaryId(zjXmSalaryWageBasicLibrary.getSalaryId());
			// 岗级及岗薪id
			dbzjXmSalaryWageBasicLibrary.setLevelSalaryId(zjXmSalaryWageBasicLibrary.getLevelSalaryId());
			// 学历津贴
			dbzjXmSalaryWageBasicLibrary.setEducationAllowance(zjXmSalaryWageBasicLibrary.getEducationAllowance());
			// 工龄津贴
			dbzjXmSalaryWageBasicLibrary.setYearAllowance(zjXmSalaryWageBasicLibrary.getYearAllowance());
			// 职称津贴
			dbzjXmSalaryWageBasicLibrary.setTitleAllowance(zjXmSalaryWageBasicLibrary.getTitleAllowance());
			// 儿补
			dbzjXmSalaryWageBasicLibrary.setChildAllowance(zjXmSalaryWageBasicLibrary.getChildAllowance());
			// 高原津贴
			dbzjXmSalaryWageBasicLibrary.setPlateauAllowance(zjXmSalaryWageBasicLibrary.getPlateauAllowance());
			// 高温津贴
			dbzjXmSalaryWageBasicLibrary.setHighTempAllowance(zjXmSalaryWageBasicLibrary.getHighTempAllowance());
			// 证书使用费
			dbzjXmSalaryWageBasicLibrary.setCertificateAllowance(zjXmSalaryWageBasicLibrary.getCertificateAllowance());
			// 通讯津贴
			dbzjXmSalaryWageBasicLibrary
					.setCommunicationAllowance(zjXmSalaryWageBasicLibrary.getCommunicationAllowance());
			// 个人起征点
			dbzjXmSalaryWageBasicLibrary.setPersonalThreshold(zjXmSalaryWageBasicLibrary.getPersonalThreshold());
			// 备注
			dbzjXmSalaryWageBasicLibrary.setRemarks(zjXmSalaryWageBasicLibrary.getRemarks());
			// 排序
			dbzjXmSalaryWageBasicLibrary.setSort(zjXmSalaryWageBasicLibrary.getSort());
			// 共通
			dbzjXmSalaryWageBasicLibrary.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryWageBasicLibraryMapper.updateByPrimaryKey(dbzjXmSalaryWageBasicLibrary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmSalaryWageBasicLibrary);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmSalaryWageBasicLibrary(
			List<ZjXmSalaryWageBasicLibrary> zjXmSalaryWageBasicLibraryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmSalaryWageBasicLibraryList != null && zjXmSalaryWageBasicLibraryList.size() > 0) {
			ZjXmSalaryWageBasicLibrary zjXmSalaryWageBasicLibrary = new ZjXmSalaryWageBasicLibrary();
			zjXmSalaryWageBasicLibrary.setModifyUserInfo(userKey, realName);
			flag = zjXmSalaryWageBasicLibraryMapper.batchDeleteUpdateZjXmSalaryWageBasicLibrary(
					zjXmSalaryWageBasicLibraryList, zjXmSalaryWageBasicLibrary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmSalaryWageBasicLibraryList);
		}
	}
}