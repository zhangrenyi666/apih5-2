package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtWorksMapper;
import com.apih5.mybatis.dao.ZxGcsgSaCoWorkLinkWorkMapper;
import com.apih5.mybatis.pojo.ZxCtWorks;
import com.apih5.mybatis.pojo.ZxGcsgSaCoWorkLinkWork;
import com.apih5.service.ZxGcsgSaCoWorkLinkWorkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("zxGcsgSaCoWorkLinkWorkService")
public class ZxGcsgSaCoWorkLinkWorkServiceImpl implements ZxGcsgSaCoWorkLinkWorkService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxGcsgSaCoWorkLinkWorkMapper zxGcsgSaCoWorkLinkWorkMapper;
	@Autowired(required = true)
	private ZxCtWorksMapper zxCtWorksMapper;

	@Override
	public ResponseEntity getZxGcsgSaCoWorkLinkWorkListByCondition(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		if (zxGcsgSaCoWorkLinkWork == null) {
			zxGcsgSaCoWorkLinkWork = new ZxGcsgSaCoWorkLinkWork();
		}
		// 分页查询
		PageHelper.startPage(zxGcsgSaCoWorkLinkWork.getPage(), zxGcsgSaCoWorkLinkWork.getLimit());
		// 获取数据
		List<ZxGcsgSaCoWorkLinkWork> zxGcsgSaCoWorkLinkWorkList = zxGcsgSaCoWorkLinkWorkMapper
				.selectByZxGcsgSaCoWorkLinkWorkList(zxGcsgSaCoWorkLinkWork);
		// 得到分页信息
		PageInfo<ZxGcsgSaCoWorkLinkWork> p = new PageInfo<>(zxGcsgSaCoWorkLinkWorkList);

		return repEntity.okList(zxGcsgSaCoWorkLinkWorkList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgSaCoWorkLinkWorkDetail(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		if (zxGcsgSaCoWorkLinkWork == null) {
			zxGcsgSaCoWorkLinkWork = new ZxGcsgSaCoWorkLinkWork();
		}
		// 获取数据
		ZxGcsgSaCoWorkLinkWork dbZxGcsgSaCoWorkLinkWork = zxGcsgSaCoWorkLinkWorkMapper
				.selectByPrimaryKey(zxGcsgSaCoWorkLinkWork.getSaCoWorkLinkWorkId());
		// 数据存在
		if (dbZxGcsgSaCoWorkLinkWork != null) {
			return repEntity.ok(dbZxGcsgSaCoWorkLinkWork);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgSaCoWorkLinkWork(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxGcsgSaCoWorkLinkWork.setSaCoWorkLinkWorkId(UuidUtil.generate());
		zxGcsgSaCoWorkLinkWork.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgSaCoWorkLinkWorkMapper.insert(zxGcsgSaCoWorkLinkWork);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgSaCoWorkLinkWork);
		}
	}

	@Override
	public ResponseEntity updateZxGcsgSaCoWorkLinkWork(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgSaCoWorkLinkWork dbZxGcsgSaCoWorkLinkWork = zxGcsgSaCoWorkLinkWorkMapper
				.selectByPrimaryKey(zxGcsgSaCoWorkLinkWork.getSaCoWorkLinkWorkId());
		if (dbZxGcsgSaCoWorkLinkWork != null && StrUtil.isNotEmpty(dbZxGcsgSaCoWorkLinkWork.getSaCoWorkLinkWorkId())) {
			// 项目ID
			dbZxGcsgSaCoWorkLinkWork.setOrgID(zxGcsgSaCoWorkLinkWork.getOrgID());
			// 项目名称
			dbZxGcsgSaCoWorkLinkWork.setOrgName(zxGcsgSaCoWorkLinkWork.getOrgName());
			// 合同ID(contractID)
			dbZxGcsgSaCoWorkLinkWork.setCtContractId(zxGcsgSaCoWorkLinkWork.getCtContractId());
			// 合同编号
			dbZxGcsgSaCoWorkLinkWork.setContractNo(zxGcsgSaCoWorkLinkWork.getContractNo());
			// 合同名称
			dbZxGcsgSaCoWorkLinkWork.setContractName(zxGcsgSaCoWorkLinkWork.getContractName());
			// 分包清单ID(coWorkID)
			dbZxGcsgSaCoWorkLinkWork.setCcWorksId(zxGcsgSaCoWorkLinkWork.getCcWorksId());
			// 分包清单编号
			dbZxGcsgSaCoWorkLinkWork.setCoWorkNo(zxGcsgSaCoWorkLinkWork.getCoWorkNo());
			// 分包清单名称
			dbZxGcsgSaCoWorkLinkWork.setCoWorkName(zxGcsgSaCoWorkLinkWork.getCoWorkName());
			// 分包清单单位
			dbZxGcsgSaCoWorkLinkWork.setCoUnit(zxGcsgSaCoWorkLinkWork.getCoUnit());
			// 是否为主项
			dbZxGcsgSaCoWorkLinkWork.setIsMain(zxGcsgSaCoWorkLinkWork.getIsMain());
			// 业主清单ID
			dbZxGcsgSaCoWorkLinkWork.setWorkID(zxGcsgSaCoWorkLinkWork.getWorkID());
			// 业主清单编号
			dbZxGcsgSaCoWorkLinkWork.setWorkNo(zxGcsgSaCoWorkLinkWork.getWorkNo());
			// 业主清单名称
			dbZxGcsgSaCoWorkLinkWork.setWorkName(zxGcsgSaCoWorkLinkWork.getWorkName());
			// 最后编辑时间
			dbZxGcsgSaCoWorkLinkWork.setEditTime(zxGcsgSaCoWorkLinkWork.getEditTime());
			// 所属公司ID
			dbZxGcsgSaCoWorkLinkWork.setComID(zxGcsgSaCoWorkLinkWork.getComID());
			// 所属公司名称
			dbZxGcsgSaCoWorkLinkWork.setComName(zxGcsgSaCoWorkLinkWork.getComName());
			// 所属公司排序
			dbZxGcsgSaCoWorkLinkWork.setComOrders(zxGcsgSaCoWorkLinkWork.getComOrders());
			// 备注
			dbZxGcsgSaCoWorkLinkWork.setRemarks(zxGcsgSaCoWorkLinkWork.getRemarks());
			// 排序
			dbZxGcsgSaCoWorkLinkWork.setSort(zxGcsgSaCoWorkLinkWork.getSort());
			// 共通
			dbZxGcsgSaCoWorkLinkWork.setModifyUserInfo(userKey, realName);
			flag = zxGcsgSaCoWorkLinkWorkMapper.updateByPrimaryKey(dbZxGcsgSaCoWorkLinkWork);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgSaCoWorkLinkWork);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxGcsgSaCoWorkLinkWork(
			List<ZxGcsgSaCoWorkLinkWork> zxGcsgSaCoWorkLinkWorkList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxGcsgSaCoWorkLinkWorkList != null && zxGcsgSaCoWorkLinkWorkList.size() > 0) {
			ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork = new ZxGcsgSaCoWorkLinkWork();
			zxGcsgSaCoWorkLinkWork.setModifyUserInfo(userKey, realName);
			flag = zxGcsgSaCoWorkLinkWorkMapper.batchDeleteUpdateZxGcsgSaCoWorkLinkWork(zxGcsgSaCoWorkLinkWorkList,
					zxGcsgSaCoWorkLinkWork);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgSaCoWorkLinkWorkList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity getZxGcsgSaCoWorkLinkWorkLeftTree(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		if (zxGcsgSaCoWorkLinkWork == null) {
			zxGcsgSaCoWorkLinkWork = new ZxGcsgSaCoWorkLinkWork();
		}
		// 分页查询
		// PageHelper.startPage(zxGcsgSaCoWorkLinkWork.getPage(),
		// zxGcsgSaCoWorkLinkWork.getLimit());
		// 获取数据
		List<ZxGcsgSaCoWorkLinkWork> zxGcsgSaCoWorkLinkWorkList = zxGcsgSaCoWorkLinkWorkMapper
				.getZxGcsgSaCoWorkLinkWorkLeftTree(zxGcsgSaCoWorkLinkWork);
		// 得到分页信息
		// PageInfo<ZxGcsgSaCoWorkLinkWork> p = new
		// PageInfo<>(zxGcsgSaCoWorkLinkWorkList);
		JSONArray arr = getTree(JSONUtil.parseArray(zxGcsgSaCoWorkLinkWorkList));
		return repEntity.okList(arr, arr.size());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity clickAddOrDeleteZxGcsgSaCoWorkLinkWork(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 新逻辑是先删后增(老系统是增/删/改)
		// 根据选中左侧的合同id和分包清单id删除关联表数据
		if (StrUtil.isNotEmpty(zxGcsgSaCoWorkLinkWork.getCtContractId())
				&& StrUtil.isNotEmpty(zxGcsgSaCoWorkLinkWork.getCcWorksId())) {
			ZxGcsgSaCoWorkLinkWork delete = new ZxGcsgSaCoWorkLinkWork();
			delete.setCtContractId(zxGcsgSaCoWorkLinkWork.getCtContractId());
			delete.setCcWorksId(zxGcsgSaCoWorkLinkWork.getCcWorksId());
			zxGcsgSaCoWorkLinkWorkMapper.deleteZxGcsgSaCoWorkLinkWorkByCondition(delete);
			// 判断是否传递业主清单id
			if (StrUtil.isNotEmpty(zxGcsgSaCoWorkLinkWork.getWorkID())) {
				zxGcsgSaCoWorkLinkWork.setSaCoWorkLinkWorkId(UuidUtil.generate());
				// orgID、orgName、comID、comName、等所有字段皆前端传入
				zxGcsgSaCoWorkLinkWork.setCreateUserInfo(userKey, realName);
				flag = zxGcsgSaCoWorkLinkWorkMapper.insert(zxGcsgSaCoWorkLinkWork);
			}
		}
		return repEntity.ok("sys.data.sava", zxGcsgSaCoWorkLinkWork);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity updateZxGcsgSaCoWorkLinkWorkIsMain(ZxGcsgSaCoWorkLinkWork zxGcsgSaCoWorkLinkWork) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// 点击左侧分包清单是否主项-验证已挂接的业主清单是否为叶子节点
		// 如果是主项且业主清单是非叶子节点,需要清空挂接数据
		ZxGcsgSaCoWorkLinkWork dbZxGcsgSaCoWorkLinkWork = zxGcsgSaCoWorkLinkWorkMapper
				.selectByPrimaryKey(zxGcsgSaCoWorkLinkWork.getSaCoWorkLinkWorkId());
		// 先改后判删
		if (dbZxGcsgSaCoWorkLinkWork != null) {
			// 是否为主项
			dbZxGcsgSaCoWorkLinkWork.setIsMain(zxGcsgSaCoWorkLinkWork.getIsMain());
			// 共通
			dbZxGcsgSaCoWorkLinkWork.setModifyUserInfo(userKey, realName);
			int flag = zxGcsgSaCoWorkLinkWorkMapper.updateByPrimaryKey(dbZxGcsgSaCoWorkLinkWork);
			if (StrUtil.equals("1", zxGcsgSaCoWorkLinkWork.getIsMain())) {
				ZxCtWorks dbZxCtWorks = zxCtWorksMapper.selectByPrimaryKey(dbZxGcsgSaCoWorkLinkWork.getWorkID());
				if (dbZxCtWorks != null && dbZxCtWorks.getIsLeaf() == 0) {
					zxGcsgSaCoWorkLinkWorkMapper.deleteByPrimaryKey(dbZxGcsgSaCoWorkLinkWork.getSaCoWorkLinkWorkId());
				}
			}
		}
		// 保存成功
		return repEntity.ok("sys.data.sava", zxGcsgSaCoWorkLinkWork);
	}

	private JSONArray getTree(JSONArray jsonArr) {
		String child = "children";
		JSONArray r = new JSONArray();
		JSONArray newArr = new JSONArray();
		JSONObject hash = new JSONObject();
		if (jsonArr != null && jsonArr.size() > 0) {
			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject json = (JSONObject) jsonArr.get(i);
				JSONObject newJSONObj = new JSONObject();
				newJSONObj.set("parentID", json.get("parentID"));
				newJSONObj.set("coWorkNo", json.get("coWorkNo"));
				newJSONObj.set("coWorkName", json.get("coWorkName"));
				newJSONObj.set("coUnit", json.get("coUnit"));
				newJSONObj.set("isMain", json.get("isMain"));
				newJSONObj.set("workNo", json.get("workNo"));
				newJSONObj.set("workName", json.get("workName"));
				newJSONObj.set("ctContractId", json.get("ctContractId"));
				newJSONObj.set("ccWorksId", json.get("ccWorksId"));
				newJSONObj.set("workID", json.get("workID"));
				newJSONObj.set("saCoWorkLinkWorkId", json.get("saCoWorkLinkWorkId"));
				newJSONObj.set("isLeaf", json.get("isLeaf"));
				hash.set(json.getStr("ccWorksId"), newJSONObj);
				newArr.add(newJSONObj);
			}
			for (int j = 0; j < newArr.size(); j++) {
				JSONObject aVal = newArr.getJSONObject(Integer.valueOf(j));
				JSONObject hashVP = hash.getJSONObject(aVal.getStr("parentID"));
				if (hashVP != null) {
					if (hashVP.get(child) != null) {
						JSONArray ch = hashVP.getJSONArray(child);
						ch.add(aVal);
						hashVP.set(child, ch);
					} else {
						JSONArray ch = new JSONArray();
						ch.add(aVal);
						hashVP.set(child, ch);
					}
				} else
					r.add(aVal);
			}
		}
		return r;
	}

}
