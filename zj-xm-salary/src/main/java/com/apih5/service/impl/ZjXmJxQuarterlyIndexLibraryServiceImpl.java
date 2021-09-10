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
import com.apih5.mybatis.dao.ZjXmJxQuarterlyIndexLibraryMapper;
import com.apih5.mybatis.pojo.ZjXmJxQuarterlyIndexLibrary;
import com.apih5.service.ZjXmJxQuarterlyIndexLibraryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("zjXmJxQuarterlyIndexLibraryService")
public class ZjXmJxQuarterlyIndexLibraryServiceImpl implements ZjXmJxQuarterlyIndexLibraryService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmJxQuarterlyIndexLibraryMapper zjXmJxQuarterlyIndexLibraryMapper;

	@Override
	public ResponseEntity getZjXmJxQuarterlyIndexLibraryListByCondition(
			ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary) {
		if (zjXmJxQuarterlyIndexLibrary == null) {
			zjXmJxQuarterlyIndexLibrary = new ZjXmJxQuarterlyIndexLibrary();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxQuarterlyIndexLibrary.getPage(), zjXmJxQuarterlyIndexLibrary.getLimit());
		// 获取数据
		List<ZjXmJxQuarterlyIndexLibrary> zjXmJxQuarterlyIndexLibraryList = zjXmJxQuarterlyIndexLibraryMapper
				.selectByZjXmJxQuarterlyIndexLibraryList(zjXmJxQuarterlyIndexLibrary);
		if (zjXmJxQuarterlyIndexLibraryList.size() > 0) {
			for (ZjXmJxQuarterlyIndexLibrary dbData : zjXmJxQuarterlyIndexLibraryList) {
				JSONArray arr = JSONUtil.createArray();
				JSONObject obj = JSONUtil.createObj();
				obj.set("value", dbData.getPersonLiableKey());
				obj.set("label", dbData.getPersonLiableName());
				arr.add(obj);
				dbData.setPersonLiableArray(arr);
			}
		}
		// 得到分页信息
		PageInfo<ZjXmJxQuarterlyIndexLibrary> p = new PageInfo<>(zjXmJxQuarterlyIndexLibraryList);

		return repEntity.okList(zjXmJxQuarterlyIndexLibraryList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxQuarterlyIndexLibraryDetail(
			ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary) {
		if (zjXmJxQuarterlyIndexLibrary == null) {
			zjXmJxQuarterlyIndexLibrary = new ZjXmJxQuarterlyIndexLibrary();
		}
		// 获取数据
		ZjXmJxQuarterlyIndexLibrary dbZjXmJxQuarterlyIndexLibrary = zjXmJxQuarterlyIndexLibraryMapper
				.selectByPrimaryKey(zjXmJxQuarterlyIndexLibrary.getLibraryId());
		// 数据存在
		if (dbZjXmJxQuarterlyIndexLibrary != null) {
			return repEntity.ok(dbZjXmJxQuarterlyIndexLibrary);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxQuarterlyIndexLibrary(ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxQuarterlyIndexLibrary.setLibraryId(UuidUtil.generate());
		if (zjXmJxQuarterlyIndexLibrary.getPersonLiableArray() != null
				&& zjXmJxQuarterlyIndexLibrary.getPersonLiableArray().size() > 0) {
			zjXmJxQuarterlyIndexLibrary.setPersonLiableKey(
					zjXmJxQuarterlyIndexLibrary.getPersonLiableArray().getJSONObject(0).getStr("value"));
			zjXmJxQuarterlyIndexLibrary.setPersonLiableName(
					zjXmJxQuarterlyIndexLibrary.getPersonLiableArray().getJSONObject(0).getStr("label"));
		}
		zjXmJxQuarterlyIndexLibrary.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxQuarterlyIndexLibraryMapper.insert(zjXmJxQuarterlyIndexLibrary);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxQuarterlyIndexLibrary);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxQuarterlyIndexLibrary(ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxQuarterlyIndexLibrary dbZjXmJxQuarterlyIndexLibrary = zjXmJxQuarterlyIndexLibraryMapper
				.selectByPrimaryKey(zjXmJxQuarterlyIndexLibrary.getLibraryId());
		if (dbZjXmJxQuarterlyIndexLibrary != null && StrUtil.isNotEmpty(dbZjXmJxQuarterlyIndexLibrary.getLibraryId())) {
			// 部门ID
			dbZjXmJxQuarterlyIndexLibrary.setDeptId(zjXmJxQuarterlyIndexLibrary.getDeptId());
			// 部门名称
			dbZjXmJxQuarterlyIndexLibrary.setDeptName(zjXmJxQuarterlyIndexLibrary.getDeptName());
			// 项目状态id
			dbZjXmJxQuarterlyIndexLibrary.setProjectStatus(zjXmJxQuarterlyIndexLibrary.getProjectStatus());
			// 项目状态名称
			dbZjXmJxQuarterlyIndexLibrary.setProjectStatusName(zjXmJxQuarterlyIndexLibrary.getProjectStatusName());
			// 项目类型id
			dbZjXmJxQuarterlyIndexLibrary.setProjectType(zjXmJxQuarterlyIndexLibrary.getProjectType());
			// 项目类型名称
			dbZjXmJxQuarterlyIndexLibrary.setProjectTypeName(zjXmJxQuarterlyIndexLibrary.getProjectTypeName());
			// 考核标题
			dbZjXmJxQuarterlyIndexLibrary.setLibraryTitle(zjXmJxQuarterlyIndexLibrary.getLibraryTitle());
			// 考核内容
			dbZjXmJxQuarterlyIndexLibrary.setLibraryContent(zjXmJxQuarterlyIndexLibrary.getLibraryContent());
			if (zjXmJxQuarterlyIndexLibrary.getPersonLiableArray() != null
					&& zjXmJxQuarterlyIndexLibrary.getPersonLiableArray().size() > 0) {
				// 考核责任人key
				dbZjXmJxQuarterlyIndexLibrary.setPersonLiableKey(
						zjXmJxQuarterlyIndexLibrary.getPersonLiableArray().getJSONObject(0).getStr("value"));
				// 考核责任人姓名
				dbZjXmJxQuarterlyIndexLibrary.setPersonLiableName(
						zjXmJxQuarterlyIndexLibrary.getPersonLiableArray().getJSONObject(0).getStr("label"));
			}
			// 是否是固定分数
			dbZjXmJxQuarterlyIndexLibrary.setIsFixedScore(zjXmJxQuarterlyIndexLibrary.getIsFixedScore());
			// 分数
			dbZjXmJxQuarterlyIndexLibrary.setScore(zjXmJxQuarterlyIndexLibrary.getScore());
			// 加/减分下限
			dbZjXmJxQuarterlyIndexLibrary.setLowerLimitScore(zjXmJxQuarterlyIndexLibrary.getLowerLimitScore());
			// 加/减分上限
			dbZjXmJxQuarterlyIndexLibrary.setUpperLimitScore(zjXmJxQuarterlyIndexLibrary.getUpperLimitScore());
			// 是否是收尾项目
			dbZjXmJxQuarterlyIndexLibrary.setIsClosed(zjXmJxQuarterlyIndexLibrary.getIsClosed());
			// 备注
			dbZjXmJxQuarterlyIndexLibrary.setRemarks(zjXmJxQuarterlyIndexLibrary.getRemarks());
			// 排序
			dbZjXmJxQuarterlyIndexLibrary.setSort(zjXmJxQuarterlyIndexLibrary.getSort());
			// 共通
			dbZjXmJxQuarterlyIndexLibrary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyIndexLibraryMapper.updateByPrimaryKey(dbZjXmJxQuarterlyIndexLibrary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxQuarterlyIndexLibrary);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxQuarterlyIndexLibrary(
			List<ZjXmJxQuarterlyIndexLibrary> zjXmJxQuarterlyIndexLibraryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxQuarterlyIndexLibraryList != null && zjXmJxQuarterlyIndexLibraryList.size() > 0) {
			ZjXmJxQuarterlyIndexLibrary zjXmJxQuarterlyIndexLibrary = new ZjXmJxQuarterlyIndexLibrary();
			zjXmJxQuarterlyIndexLibrary.setModifyUserInfo(userKey, realName);
			flag = zjXmJxQuarterlyIndexLibraryMapper.batchDeleteUpdateZjXmJxQuarterlyIndexLibrary(
					zjXmJxQuarterlyIndexLibraryList, zjXmJxQuarterlyIndexLibrary);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxQuarterlyIndexLibraryList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
