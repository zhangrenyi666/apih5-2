package com.apih5.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.mybatis.dao.SysFrequentContactsMapper;
import com.apih5.mybatis.pojo.SysFrequentContacts;
import com.apih5.service.SysFrequentContactsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("sysFrequentContactsService")
public class SysFrequentContactsServiceImpl implements SysFrequentContactsService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private SysFrequentContactsMapper sysFrequentContactsMapper;

	@Override
	public ResponseEntity getSysFrequentContactsListByCondition(SysFrequentContacts sysFrequentContacts) {
		if (sysFrequentContacts == null) {
			sysFrequentContacts = new SysFrequentContacts();
		}
		// 分页查询
		PageHelper.startPage(sysFrequentContacts.getPage(), sysFrequentContacts.getLimit());
		// 获取数据
		List<SysFrequentContacts> sysFrequentContactsList = sysFrequentContactsMapper
				.selectBySysFrequentContactsList(sysFrequentContacts);
		// 得到分页信息
		PageInfo<SysFrequentContacts> p = new PageInfo<>(sysFrequentContactsList);

		return repEntity.okList(sysFrequentContactsList, p.getTotal());
	}

	@Override
	public ResponseEntity saveSysFrequentContacts(SysFrequentContacts sysFrequentContacts) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		sysFrequentContacts.setFrequentContactsId(UuidUtil.generate());
		sysFrequentContacts.setCreateUserInfo(userKey, realName);
		int flag = sysFrequentContactsMapper.insert(sysFrequentContacts);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", sysFrequentContacts);
		}
	}

	@Override
	public ResponseEntity updateSysFrequentContacts(SysFrequentContacts sysFrequentContacts) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		SysFrequentContacts dbsysFrequentContacts = sysFrequentContactsMapper
				.selectByPrimaryKey(sysFrequentContacts.getFrequentContactsId());
		if (dbsysFrequentContacts != null && StrUtil.isNotEmpty(dbsysFrequentContacts.getFrequentContactsId())) {
			// 共通
			dbsysFrequentContacts.setModifyUserInfo(userKey, realName);
			flag = sysFrequentContactsMapper.updateByPrimaryKey(dbsysFrequentContacts);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", sysFrequentContacts);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateSysFrequentContacts(List<SysFrequentContacts> sysFrequentContactsList) {
		int flag = 0;
		if (sysFrequentContactsList != null && sysFrequentContactsList.size() > 0) {
			flag = sysFrequentContactsMapper.batchDeleteUpdateSysFrequentContacts(sysFrequentContactsList);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", sysFrequentContactsList);
		}
	}

	@Override
	public ResponseEntity appGetSysFrequentContactsList(SysFrequentContacts sysFrequentContacts) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String loginUserKey = TokenUtils.getUserKey(request);
		// String realName = TokenUtils.getRealName(request);
		List<SysFrequentContacts> sysFrequentContactsList = Lists.newArrayList();
		if (StrUtil.isNotEmpty(loginUserKey)) {
			SysFrequentContacts frequentContacts = new SysFrequentContacts();
			frequentContacts.setLoginUserKey(loginUserKey);
			frequentContacts.setCount(10);
			// 获取数据
			sysFrequentContactsList = sysFrequentContactsMapper.selectBySysFrequentContactsList(frequentContacts);
		}
		return repEntity.okList(sysFrequentContactsList, sysFrequentContactsList.size());
	}

	@Override
	public ResponseEntity appRemoveSysFrequentContacts(List<SysFrequentContacts> sysFrequentContactsList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String loginUserKey = TokenUtils.getUserKey(request);
		// String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (StrUtil.isNotEmpty(loginUserKey) && sysFrequentContactsList != null && sysFrequentContactsList.size() > 0) {
			// SysFrequentContacts frequentContacts = new SysFrequentContacts();
			// frequentContacts.setLoginUserKey(userKey);
			// frequentContacts.setSysFrequentContactsList(sysFrequentContactsList);
			// pc端各种参数都已返回可自定义选择
			sysFrequentContactsList.stream().forEach(data -> {
				data.setLoginUserKey(loginUserKey);
			});
			// flag =
			// sysFrequentContactsMapper.deleteSysFrequentContactsByCondition(frequentContacts);
			flag = sysFrequentContactsMapper.deleteSysFrequentContactsByCondition2(sysFrequentContactsList);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorDelete();
		} else {
			return repEntity.ok("sys.data.delete", sysFrequentContactsList);
		}
	}

	@Override
	public ResponseEntity appAddSysFrequentContacts(List<SysFrequentContacts> sysFrequentContactsList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (StrUtil.isNotEmpty(userKey) && sysFrequentContactsList != null && sysFrequentContactsList.size() > 0) {
			// check
//			SysFrequentContacts frequentContacts = new SysFrequentContacts();
//			frequentContacts.setLoginUserKey(userKey);
//			frequentContacts.setCount(10);
//			frequentContacts.setSysFrequentContactsList(sysFrequentContactsList);
//			int count = sysFrequentContactsMapper.countSysFrequentContactsList(frequentContacts);
//			if (count > 0) {
//				return repEntity.layerMessage("no", "所选人员已在常用联系人列表,请重新选择!");
//			}
			Iterator<SysFrequentContacts> it = sysFrequentContactsList.iterator();
			while (it.hasNext()) {
				SysFrequentContacts data = it.next();
				data.setLoginUserKey(userKey);
				data.setCount(10);
				data.setUserKey(data.getValue());
				data.setDepartmentId(data.getValuePid());
				int count = sysFrequentContactsMapper.countSysFrequentContactsList2(data);
				if (count > 0) {
					return repEntity.layerMessage("no", "所选人员已在常用联系人列表,请重新选择!");
				}
			}
			// 为了程序执行时间和统一数据先删除所选人员未达到规定次数(10次)的脏数据
//			sysFrequentContactsMapper.deleteSysFrequentContactsByCondition(frequentContacts);
			sysFrequentContactsMapper.deleteSysFrequentContactsByCondition2(sysFrequentContactsList);
			// 根据userKey集合获取数据完整人员数据
			List<SysFrequentContacts> dbFrequentContactsList = sysFrequentContactsMapper
					.getSysUserInfoListByUserKeyList(sysFrequentContactsList);
			Map<String, SysFrequentContacts> userInfoMap = new HashMap<String, SysFrequentContacts>();
			// 整理数据统一插入db
			if (dbFrequentContactsList.size() > 0) {
				dbFrequentContactsList.stream().forEach(data -> {
					userInfoMap.put(data.getUserKey(), data);
				});
				for (SysFrequentContacts sysFrequentContacts : sysFrequentContactsList) {
					sysFrequentContacts.setFrequentContactsId(UuidUtil.generate());
					// sysFrequentContacts.setLoginUserKey(userKey);
					// sysFrequentContacts.setCount(10);
					sysFrequentContacts.setUserId(userInfoMap.get(sysFrequentContacts.getUserKey()).getUserId());
					sysFrequentContacts.setRealName(userInfoMap.get(sysFrequentContacts.getUserKey()).getRealName());
					sysFrequentContacts.setMobile(userInfoMap.get(sysFrequentContacts.getUserKey()).getMobile());
					sysFrequentContacts.setGender(userInfoMap.get(sysFrequentContacts.getUserKey()).getGender());
					sysFrequentContacts
							.setIdentityCard(userInfoMap.get(sysFrequentContacts.getUserKey()).getIdentityCard());
					sysFrequentContacts.setExpirationDate(DateUtil.date());
					sysFrequentContacts.setCreateUserInfo(userKey, realName);
				}
				flag = sysFrequentContactsMapper.batchInsertSysFrequentContacts(sysFrequentContactsList);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", sysFrequentContactsList);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity jobForAddSysFrequentContacts() throws Exception {
		int flag = 0;
		// 获取规定时间段内流程表(tw_hz_worklist_done)数据(每天执行一次,每次统计一天的数据)
		SysFrequentContacts sysFrequentContacts = new SysFrequentContacts();
		sysFrequentContacts.setStartDate(DateUtil.date());
		sysFrequentContacts.setEndDate(DateUtil.date());
		List<SysFrequentContacts> sysFrequentContactsList = sysFrequentContactsMapper
				.getContactsListFromTwHzWorklistDone(sysFrequentContacts);
		if (sysFrequentContactsList.size() > 0) {
			List<SysFrequentContacts> batchUpdateList = Lists.newArrayList();
			List<SysFrequentContacts> batchInsertList = Lists.newArrayList();
			for (SysFrequentContacts sysFrequentContacts1 : sysFrequentContactsList) {
				// check数据库有无该条数据(有数据修改count, 无则新增一条)
				SysFrequentContacts sysFrequentContacts2 = new SysFrequentContacts();
				sysFrequentContacts2.setLoginUserKey(sysFrequentContacts1.getLoginUserKey());
				sysFrequentContacts2.setUserKey(sysFrequentContacts1.getUserKey());
				sysFrequentContacts2.setDepartmentId(sysFrequentContacts1.getDepartmentId());
				int count = sysFrequentContactsMapper.countSysFrequentContactsList2(sysFrequentContacts2);
				if (count > 0) {
					batchUpdateList.add(sysFrequentContacts1);
					// flag =
					// sysFrequentContactsMapper.updateContactsCountByCondition(sysFrequentContacts1);
				} else {
					sysFrequentContacts1.setFrequentContactsId(UuidUtil.generate());
					sysFrequentContacts1.setExpirationDate(DateUtil.date());
					sysFrequentContacts1.setCreateUserInfo("定时任务", "定时任务");
					batchInsertList.add(sysFrequentContacts1);
					// flag =
					// sysFrequentContactsMapper.insert(sysFrequentContacts1);
				}
			}
			if (batchUpdateList.size() > 0) {
				flag = sysFrequentContactsMapper.batchUpdateContactsCountByCondition(batchUpdateList);
				if (flag == 0) {
					throw new Apih5Exception("定时任务执行失败!");
				}
			}
			if (batchInsertList.size() > 0) {
				flag = sysFrequentContactsMapper.batchInsertSysFrequentContacts(batchInsertList);
				if (flag == 0) {
					throw new Apih5Exception("定时任务执行失败!");
				}
			}
		}
		return repEntity.ok("定时任务执行成功!");
	}
}
