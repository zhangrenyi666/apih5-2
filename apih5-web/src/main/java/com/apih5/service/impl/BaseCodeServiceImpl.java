package com.apih5.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.api.sysdb.entity.BaseCode;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseCodeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("baseCodeService")
public class BaseCodeServiceImpl implements BaseCodeService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private BaseCodeMapper baseCodeMapper;
	@Autowired(required = true)
	private SequenceService sequenceService;

	@Override
	public ResponseEntity getBaseCodeListByCondition(BaseCode baseCode) {
		if (baseCode == null) {
			baseCode = new BaseCode();
		}
		// 分页查询
		PageHelper.startPage(baseCode.getPage(), baseCode.getLimit());
		// 获取数据
		List<BaseCode> baseCodeList = baseCodeMapper.selectByBaseCodeList(baseCode);
		// 得到分页信息
		PageInfo<BaseCode> p = new PageInfo<>(baseCodeList);

		return repEntity.okList(baseCodeList, p.getTotal());
	}

	@Override
	public ResponseEntity saveBaseCode(BaseCode baseCode) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (baseCode != null) {
			// check(如果是新增根节点时，则itemId不能跟其他根节点重复)
//			if (StrUtil.equals("0", baseCode.getCodePid())) {
				BaseCode checkBaseCode = new BaseCode();
				checkBaseCode.setCodePid(baseCode.getCodePid());
				checkBaseCode.setItemId(baseCode.getItemId());
				int count = baseCodeMapper.countBaseCodeListByCodePid(checkBaseCode);
				if (count > 0) {
					return repEntity.layerMessage("no", "字典ID在其它根目录已存在,请换个试试!");
				}
//			}
			// String str = "0000000000";
			// int order = sequenceService.getSequenceNextval("base_code");
			// String codeSort = str.substring(String.valueOf(order).length()) +
			// order;
			baseCode.setCodeId(UuidUtil.generate());
			baseCode.setCodeSort(999);
			// 上一节点的pid_all(包含上一节点id)
			if (StrUtil.isNotEmpty(baseCode.getPidAll())) {
				String pidAll = baseCode.getPidAll();
				baseCode.setPidAll(pidAll + "," + baseCode.getCodeId());
			} else {
				// 新增根节点时情况
				baseCode.setPidAll(baseCode.getCodeId());
			}
			// 上一节点的pid_name_all(包含上一节点name)
			if (StrUtil.isNotEmpty(baseCode.getPidNameAll())) {
				baseCode.setPidNameAll(baseCode.getPidNameAll() + "," + baseCode.getItemName());
			} else {
				// 新增根节点时情况
				baseCode.setPidNameAll(baseCode.getItemName());
			}
			baseCode.setCreateUserInfo(userKey, realName);
			flag = baseCodeMapper.insert(baseCode);
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", baseCode);
		}
	}

	@Override
	public ResponseEntity updateBaseCode(BaseCode baseCode) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		BaseCode dbbaseCode = baseCodeMapper.selectByPrimaryKey(baseCode.getCodeId());
		if (dbbaseCode != null) {
			String oldItemName = dbbaseCode.getItemName();
			// check(如果是更新根节点时，则itemId不能跟其他根节点重复)
			if (StrUtil.equals("0", dbbaseCode.getCodePid())) {
				BaseCode checkBaseCode = new BaseCode();
				checkBaseCode.setCodePid(dbbaseCode.getCodePid());
				checkBaseCode.setItemId(baseCode.getItemId());
				int count = baseCodeMapper.countBaseCodeListByCodePid(checkBaseCode);
				if (count > 0) {
					return repEntity.layerMessage("no", "字典ID在其它根目录已存在,请换个试试!");
				}
			}
			dbbaseCode.setItemId(baseCode.getItemId());
			dbbaseCode.setItemName(baseCode.getItemName());
			dbbaseCode.setItemAsName(baseCode.getItemAsName());
			dbbaseCode.setExt1(baseCode.getExt1());
			dbbaseCode.setExt2(baseCode.getExt2());
			dbbaseCode.setExt3(baseCode.getExt3());
			dbbaseCode.setExt4(baseCode.getExt4());
			dbbaseCode.setExt5(baseCode.getExt5());
			dbbaseCode.setRemarks(baseCode.getRemarks());
			// dbbaseCode.setCodeSort(baseCode.getCodeSort());
			// 共通
			dbbaseCode.setModifyUserInfo(userKey, realName);
			flag = baseCodeMapper.updateByPrimaryKey(dbbaseCode);
			if (flag != 0) {
				// 批量更新层级表pid_name_all字段
				BaseCode baseCode1 = new BaseCode();
				baseCode1.setOldItemName(oldItemName);
				baseCode1.setNewItemName(dbbaseCode.getItemName());
				baseCode1.setCodeId(dbbaseCode.getCodeId());
				flag = baseCodeMapper.batchUpdateBaseCodePidNameAll(baseCode1);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update", baseCode);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity batchDeleteUpdateBaseCode(List<BaseCode> baseCodeList) throws Exception {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		int flag = 0;
		// 定义最终删掉的层级
		List<BaseCode> delBaseCodeList = Lists.newArrayList();
		if (baseCodeList != null && baseCodeList.size() > 0) {
			for (int i = 0; i < baseCodeList.size(); i++) {
				BaseCode dbBaseCode = baseCodeMapper.selectByPrimaryKey(baseCodeList.get(i).getCodeId());
				if (dbBaseCode != null) {
					// 获取该层级下面所有的层级集合(包含自己)
					BaseCode baseCode = new BaseCode();
					baseCode.setPidAll(dbBaseCode.getCodeId());
					List<BaseCode> dbBaseCodeList = baseCodeMapper.selectByBaseCodeListByLike(baseCode);
					delBaseCodeList.addAll(dbBaseCodeList);
				}
			}
		}
		if (delBaseCodeList != null && delBaseCodeList.size() > 0) {
			flag = baseCodeMapper.batchDeleteUpdateBaseCode(delBaseCodeList);
			// if (flag == 0) {
			// throw new Apih5Exception("删除失败!");
			// }
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorDelete();
		} else {
			return repEntity.ok("sys.data.delete", delBaseCodeList);
		}

	}

	// ---扩展
	@Override
	public ResponseEntity getBaseCodeSelect(BaseCode baseCode) {
		if (baseCode == null) {
			baseCode = new BaseCode();
		}

		List<BaseCode> baseCodeListReturn = Lists.newArrayList();
		// 获取数据
		List<BaseCode> baseCodeList = baseCodeMapper.selectByBaseCodeList(baseCode);
		if (baseCodeList != null && baseCodeList.size() > 0) {
			BaseCode dbBaseCode = baseCodeList.get(0);
			BaseCode baseCodeSelect = new BaseCode();
			baseCodeSelect.setCodePid(dbBaseCode.getCodeId());
			baseCodeList = baseCodeMapper.selectByBaseCodeListByLike(baseCodeSelect);
			for (BaseCode baseCodeReturn : baseCodeList) {
				if (!StrUtil.equals("0", baseCodeReturn.getCodePid())) {
					baseCodeListReturn.add(baseCodeReturn);
				}
			}
		}
		return repEntity.ok(baseCodeListReturn);
	}

	@Override
	public ResponseEntity getBaseCodeTree(BaseCode baseCode) {
		List<BaseCode> baseCodeList = baseCodeMapper.getBaseCodeTree(baseCode);
		JSONArray jsonArray = listToTree(new JSONArray(baseCodeList), "codeId", "codePid", "itemName");
		return repEntity.ok(jsonArray);
	}

	/**
	 * 指定层级
	 */
	@Override
	public ResponseEntity getBaseCodeTreeByLevel(BaseCode baseCode) {
	    List<BaseCode> baseCodeList = baseCodeMapper.selectBaseCodeTreeByLevel(baseCode);
	    JSONArray jsonArray = listToTree(new JSONArray(baseCodeList), "codeId", "codePid", "itemName");
	    return repEntity.ok(jsonArray);
	}

	/**
	 * 通过主ID、子ID获取名称
	 * 
	 * @param itemId 主ID
	 * @param subItemId 子ID
	 * @return 名称
	 */
	@Override
	public String getBaseCodeItemName(String itemId, String subItemId) {
		if (StrUtil.isEmpty(itemId) || StrUtil.isEmpty(subItemId)) {
			return "";
		}
		BaseCode baseCode = new BaseCode();
		baseCode.setItemId(itemId);
		baseCode.setSubItemId(subItemId);
		// 获取数据
		List<BaseCode> baseCodeList = baseCodeMapper.selectByBaseCodeList(baseCode);
		if (baseCodeList != null && baseCodeList.size() > 0) {
			BaseCode dbBaseCode = baseCodeList.get(0);
			// 查询子item内容
			BaseCode baseCodeSelect = new BaseCode();
			baseCodeSelect.setPidAll(dbBaseCode.getCodeId());
			baseCodeSelect.setItemId(baseCode.getSubItemId());
			baseCodeList = baseCodeMapper.selectByBaseCodeListByLike(baseCodeSelect);
			if (baseCodeList != null && baseCodeList.size() > 0) {
				dbBaseCode = baseCodeList.get(0);
				return dbBaseCode.getItemName();
			}
		}
		return "";
	}

	/**
	 * 通过主ID、子ID获取名称
	 * 
	 * @param itemId 主ID
	 * @param subItemId 子ID
	 * @return 名称
	 */
	@Override
	public BaseCode getBaseCodeByItem(String itemId, String subItemId) {
		if (StrUtil.isEmpty(itemId) || StrUtil.isEmpty(subItemId)) {
			return new BaseCode();
		}
		BaseCode baseCode = new BaseCode();
		baseCode.setItemId(itemId);
		baseCode.setSubItemId(subItemId);
		// 获取数据
		List<BaseCode> baseCodeList = baseCodeMapper.selectByBaseCodeList(baseCode);
		if (baseCodeList != null && baseCodeList.size() > 0) {
			BaseCode dbBaseCode = baseCodeList.get(0);
			// 查询子item内容
			BaseCode baseCodeSelect = new BaseCode();
			baseCodeSelect.setPidAll(dbBaseCode.getCodeId());
			baseCodeSelect.setItemId(baseCode.getSubItemId());
			baseCodeList = baseCodeMapper.selectByBaseCodeListByLike(baseCodeSelect);
			if (baseCodeList != null && baseCodeList.size() > 0) {
				dbBaseCode = baseCodeList.get(0);
				return dbBaseCode;
			}
		}
		return new BaseCode();
	}

	@Override
	public ResponseEntity getBaseCodeUIConfig(BaseCode baseCode) {
		BaseCode dbData = baseCodeMapper.selectByPrimaryKey(baseCode.getCodeId());
		String config1 = "{\"info\":{\"formConfig\":[{\"type\":\"string\",\"label\":\"字典ID\",\"field\":\"itemId\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"字典名称\",\"field\":\"itemName\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展1\",\"field\":\"ext1\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展2\",\"field\":\"ext2\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展3\",\"field\":\"ext3\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展4\",\"field\":\"ext4\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展5\",\"field\":\"ext5\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"备注\",\"field\":\"remarks\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8}]},\"table\":{\"isShowRowSelect\":false,\"antd\":{\"rowKey\":\"codeId\",\"title\":\"属性列表\"},\"formConfig\":[{\"isInTable\":false,\"form\":{\"field\":\"codeId\",\"hide\":true,\"type\":\"string\"}},{\"table\":{\"title\":\"名称\",\"dataIndex\":\"itemName\",\"key\":\"itemName\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"别名\",\"dataIndex\":\"itemAsName\",\"key\":\"itemAsName\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"ID\",\"dataIndex\":\"itemId\",\"key\":\"itemId\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展1\",\"dataIndex\":\"ext1\",\"key\":\"ext1\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展2\",\"dataIndex\":\"ext2\",\"key\":\"ext2\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展3\",\"dataIndex\":\"ext3\",\"key\":\"ext3\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展4\",\"dataIndex\":\"ext4\",\"key\":\"ext4\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展5\",\"dataIndex\":\"ext5\",\"key\":\"ext5\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"备注\",\"dataIndex\":\"remarks\",\"key\":\"remarks\",\"tooltip\":20},\"form\":{\"type\":\"textarea\",\"placeholder\":\"请输入...\"}}]}}";
		String config2 = "{\"info\":{\"formConfig\":[{\"type\":\"string\",\"label\":\"名称\",\"field\":\"itemName\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"别名\",\"field\":\"itemAsName\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"ID\",\"field\":\"itemId\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展1\",\"field\":\"ext1\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展2\",\"field\":\"ext2\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展3\",\"field\":\"ext3\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展4\",\"field\":\"ext4\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展5\",\"field\":\"ext5\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"备注\",\"field\":\"remarks\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8}]}}";
		String config3 = "{\"info\":{\"formConfig\":[{\"type\":\"string\",\"label\":\"名称\",\"field\":\"itemName\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"别名\",\"field\":\"itemAsName\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"ID\",\"field\":\"itemId\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展1\",\"field\":\"ext1\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展2\",\"field\":\"ext2\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展3\",\"field\":\"ext3\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展4\",\"field\":\"ext4\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"扩展5\",\"field\":\"ext5\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8},{\"type\":\"string\",\"label\":\"备注\",\"field\":\"remarks\",\"placeholder\":\"请输入\",\"disabled\":true,\"span\":8}]},\"table\":{\"isShowRowSelect\":false,\"antd\":{\"rowKey\":\"codeId\",\"title\":\"下级属性列表\"},\"formConfig\":[{\"isInTable\":false,\"form\":{\"field\":\"codeId\",\"hide\":true,\"type\":\"string\"}},{\"table\":{\"title\":\"名称\",\"dataIndex\":\"itemName\",\"key\":\"itemName\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"别名\",\"dataIndex\":\"itemAsName\",\"key\":\"itemAsName\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"ID\",\"dataIndex\":\"itemId\",\"key\":\"itemId\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展1\",\"dataIndex\":\"ext1\",\"key\":\"ext1\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展2\",\"dataIndex\":\"ext2\",\"key\":\"ext2\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展3\",\"dataIndex\":\"ext3\",\"key\":\"ext3\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展4\",\"dataIndex\":\"ext4\",\"key\":\"ext4\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"扩展5\",\"dataIndex\":\"ext5\",\"key\":\"ext5\"},\"form\":{\"type\":\"string\",\"placeholder\":\"请输入...\"}},{\"table\":{\"title\":\"备注\",\"dataIndex\":\"remarks\",\"key\":\"remarks\",\"tooltip\":20},\"form\":{\"type\":\"textarea\",\"placeholder\":\"请输入...\"}}]}}";
		JSONObject obj = null;
		if (dbData != null) {
			// check该层级下面是否挂接子节点(含子节点展示列表否则隐藏列表)
			BaseCode baseCode1 = new BaseCode();
			baseCode1.setCodePid(baseCode.getCodeId());
			int count = baseCodeMapper.countBaseCodeListByCodePid(baseCode1);
			// 根节点一定包含子节点
			if (StrUtil.equals("0", dbData.getCodePid())) {
				obj = JSONUtil.parseObj(config1);
			}
			// 不包含子节点(隐藏列表)
			else if (count < 1) {
				obj = JSONUtil.parseObj(config2);
			}
			// 中间普通节点
			else {
				obj = JSONUtil.parseObj(config3);
			}
		}
		return repEntity.ok(obj);
	}

	@Override
	public ResponseEntity pcUpdateBaseCodeOnTree(BaseCode baseCode) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		BaseCode dbbaseCode = baseCodeMapper.selectByPrimaryKey(baseCode.getCodeId());
		// 只改名称
		if (dbbaseCode != null) {
			String oldItemName = dbbaseCode.getItemName();
			dbbaseCode.setItemName(baseCode.getItemName());
			// 共通
			dbbaseCode.setModifyUserInfo(userKey, realName);
			flag = baseCodeMapper.updateByPrimaryKey(dbbaseCode);
			if (flag != 0) {
				// 批量更新层级表pid_name_all字段
				BaseCode baseCode1 = new BaseCode();
				baseCode1.setOldItemName(oldItemName);
				baseCode1.setNewItemName(dbbaseCode.getItemName());
				baseCode1.setCodeId(dbbaseCode.getCodeId());
				flag = baseCodeMapper.batchUpdateBaseCodePidNameAll(baseCode1);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update", baseCode);
		}
	}

	@Override
	public ResponseEntity pcExchangeBaseCode(BaseCode baseCode) {
		int flag = 0;
		// HttpServletRequest request =
		// requestHolderConfiguration.getHttpServletRequest();
		// String userKey = TokenUtils.getUserKey(request);
		// String realName = TokenUtils.getRealName(request);
		// if (baseCode != null && baseCode.getBaseCodeList() != null &&
		// baseCode.getBaseCodeList().size() > 0) {
		// for (BaseCode baseCode1 : baseCode.getBaseCodeList()) {
		// BaseCode dbBaseCode =
		// baseCodeMapper.selectByPrimaryKey(baseCode1.getCodeId());
		// if (dbBaseCode != null) {
		// String str = "0000000000";
		// int order = sequenceService.getSequenceNextval("base_code");
		// String orderStr = str.substring(String.valueOf(order).length()) +
		// order;
		// dbBaseCode.setCodeSort(orderStr);
		// dbBaseCode.setModifyUserInfo(userKey, realName);
		// flag = baseCodeMapper.updateByPrimaryKey(dbBaseCode);
		// }
		// }
		// } else {
		// return repEntity.layerMessage("no", "缺少参数或参数不正确");
		// }
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update", baseCode);
		}
	}

	// --内部私有方法
	/**
	 * list转tree
	 * 
	 * @param arr
	 * @param id
	 * @param pid
	 * @param name
	 * @return tree
	 */
	private static JSONArray listToTree(JSONArray arr, String id, String pid, String name) {
		String child = "children";
		JSONArray r = new JSONArray();
		JSONArray newArr = new JSONArray();
		JSONObject hash = new JSONObject();
		for (int i = 0; i < arr.size(); i++) {
			JSONObject json = (JSONObject) arr.get(i);
			JSONObject newJSONObject = new JSONObject();
			newJSONObject.put(id, json.get(id));
			newJSONObject.put(pid, json.get(pid));
			newJSONObject.put(name, json.get(name));
			newJSONObject.put("itemAsName", json.get("itemAsName"));
			newJSONObject.put("itemId", json.get("itemId"));
			newJSONObject.put("pidAll", json.get("pidAll"));
			newJSONObject.put("pidNameAll", json.get("pidNameAll"));
			newJSONObject.put("ext1", json.get("ext1"));
			newJSONObject.put("ext2", json.get("ext2"));
			newJSONObject.put("ext3", json.get("ext3"));
			newJSONObject.put("ext4", json.get("ext4"));
			newJSONObject.put("ext5", json.get("ext5"));
			newJSONObject.put("remarks", json.get("remarks"));
			hash.put(json.getStr(id), newJSONObject);
			newArr.add(newJSONObject);
		}

		for (int j = 0; j < newArr.size(); j++) {
			JSONObject aVal = newArr.getJSONObject(Integer.valueOf(j));
			JSONObject hashVP = hash.getJSONObject(aVal.getStr(pid));
			if (hashVP != null) {
				if (hashVP.get(child) != null) {
					JSONArray ch = hashVP.getJSONArray(child);
					ch.add(aVal);
					hashVP.put(child, ch);
				} else {
					JSONArray ch = new JSONArray();
					ch.add(aVal);
					hashVP.put(child, ch);
				}
			} else
				r.add(aVal);
		}
		return r;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity moveUpdateBaseCode(BaseCode baseCode) throws Exception {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 移动后菜单的新父节点id(一定存在[根节点传0])
		String codePid = baseCode.getCodeParent();
		// 要移动的菜单id(一定存在)
		String toMoveCodeId = baseCode.getCodeToMove();
		// 移动后菜单的同级下面的节点id(可能存在)
		String afterCodeId = baseCode.getCodeAfter();
		// 移动后菜单的同级上面的节点id(可能存在)
		String beforeCodeId = baseCode.getCodeBefore();

		// 父节点对象(不一定存在[根节点时不存在])
		BaseCode baseCodeParent = baseCodeMapper.selectByPrimaryKey(codePid);
		// 要移动节点对象(一定存在)
		BaseCode baseCodeToMove = baseCodeMapper.selectByPrimaryKey(toMoveCodeId);
		// 上一个节点对象(可能存在)
		BaseCode baseCodeBefore = baseCodeMapper.selectByPrimaryKey(beforeCodeId);
		if ((!StrUtil.equals("0", codePid) && baseCodeParent == null) || baseCodeToMove == null) {
			return repEntity.layerMessage("no", "传入参数有误!");
		}
		// 暂存要移动节点原属性
		String oldCodePid = baseCodeToMove.getCodePid();
		String oldPidAll = baseCodeToMove.getPidAll();
		String oldPidNameAll = baseCodeToMove.getPidNameAll();
		// 获取新父节点下原有的所有子节点集合
		BaseCode code = new BaseCode();
		code.setCodePid(codePid);
		LinkedList<BaseCode> baseCodeList = new LinkedList<BaseCode>(baseCodeMapper.selectByBaseCodeList(code));
		// 如果要移动的节点本来就在当前父节点集合下则先将其移除
		if (baseCodeList.size() > 0 && StrUtil.equals(codePid, oldCodePid)) {
			for (int i = 0; i < baseCodeList.size(); i++) {
				// 从数据集合中先移出当前移动节点
				if (StrUtil.equals(toMoveCodeId, baseCodeList.get(i).getCodeId())) {
					baseCodeList.remove(i);
					break;
				}
			}
		} else {
			// 如果要移动的节点不在当前父节点集合下则重新构建移动后节点数据
			if (StrUtil.equals("0", codePid)) {
				baseCodeToMove.setPidAll(baseCodeToMove.getCodeId());
				baseCodeToMove.setPidNameAll(baseCodeToMove.getItemName());
				baseCodeToMove.setCodePid(codePid);
			} else {
				baseCodeToMove.setPidAll(baseCodeParent.getPidAll() + "," + baseCodeToMove.getCodeId());
				baseCodeToMove.setPidNameAll(baseCodeParent.getPidNameAll() + "," + baseCodeToMove.getItemName());
				baseCodeToMove.setCodePid(codePid);
			}
			// 如果要移动的节点不在当前父节点集合下则需要处理要移动节点的下级节点数据
			BaseCode baseCodeCondition = new BaseCode();
			baseCodeCondition.setPidAll(baseCodeToMove.getCodeId());
			int count = baseCodeMapper.countBaseCodeListByCodePid(baseCodeCondition);
			if (count > 1) {
				baseCodeCondition.setOldPidAll(oldPidAll);
				baseCodeCondition.setOldPidNameAll(oldPidNameAll);
				baseCodeCondition.setPidAll(baseCodeToMove.getPidAll());
				baseCodeCondition.setPidNameAll(baseCodeToMove.getPidNameAll());
				flag = baseCodeMapper.batchUpdatePidAllAndPidNameAll(baseCodeCondition);
				if (flag < 2) {
					throw new Apih5Exception("处理要移动节点的子节点数据失败!");
				}
			}
		}
		baseCodeToMove.setModifyUserInfo(userKey, realName);
		// 处理要移动节点和移动后节点的同级兄弟节点的sort(分为以下四种情况)
		// befor、after同时存在时、说明移动到了中间位置
		if (StrUtil.isNotEmpty(beforeCodeId) && StrUtil.isNotEmpty(afterCodeId)) {
			for (int i = 0; i < baseCodeList.size(); i++) {
				baseCodeList.get(i).setCodeSort(i);
				if (StrUtil.equals(baseCodeList.get(i).getCodeId(), beforeCodeId)) {
					// 将要移动的节点加入集合
					baseCodeList.add(i + 1, baseCodeToMove);
				}
			}
			// 将节点放在中间每次移动都将批量修改集合
			flag = baseCodeMapper.batchUpdateBaseCode(baseCodeList);
		}
		// 只有befor存在时、说明移动到了最后面，移动后的节点后面没有内容
		else if (StrUtil.isNotEmpty(beforeCodeId) && StrUtil.isEmpty(afterCodeId)) {
			baseCodeToMove.setCodeSort(baseCodeBefore.getCodeSort() + 1);
			flag = baseCodeMapper.updateByPrimaryKey(baseCodeToMove);
		}
		// 只有after存在时、说明移动到了最前面面，移动后的节点前面没有内容
		else if (StrUtil.isEmpty(beforeCodeId) && StrUtil.isNotEmpty(afterCodeId)) {
			// 将要移动的节点加入集合
			baseCodeList.add(0, baseCodeToMove);
			for (int i = 0; i < baseCodeList.size(); i++) {
				baseCodeList.get(i).setCodeSort(i);
			}
			// 将节点移到最前面每次都将批量修改集合
			flag = baseCodeMapper.batchUpdateBaseCode(baseCodeList);
		}
		// befor、after都不存在时，说明前后不存在节点（即当前父节点下只有一个节点）
		else if (StrUtil.isEmpty(beforeCodeId) && StrUtil.isEmpty(afterCodeId)) {
			if (baseCodeList.size() > 0) {
				// 将插入的放在最后面
				baseCodeToMove.setCodeSort(baseCodeList.get(baseCodeList.size() - 1).getCodeSort() + 1);
			} else {
				// 随意设置一个排序即可
				baseCodeToMove.setCodeSort(1);
			}
			flag = baseCodeMapper.updateByPrimaryKey(baseCodeToMove);
		}
		// 失败
		if (flag == 0) {
			throw new Apih5Exception("字典移动失败!");
		} else {
			return repEntity.ok("sys.data.update", baseCode);
		}
	}
}
