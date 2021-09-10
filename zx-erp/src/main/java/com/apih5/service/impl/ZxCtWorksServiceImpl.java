package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.dao.ZxCtWorkAlterSingleMapper;
import com.apih5.mybatis.dao.ZxCtWorkToMUMapper;
import com.apih5.mybatis.dao.ZxCtWorksMapper;
import com.apih5.mybatis.pojo.ZxCtBalanceItem;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxCtWorkAlterSingle;
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import com.apih5.mybatis.pojo.ZxCtWorkToMU;
import com.apih5.mybatis.pojo.ZxCtWorks;
import com.apih5.service.ZxCtWorksService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zxCtWorksService")
public class ZxCtWorksServiceImpl implements ZxCtWorksService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZxCtWorksMapper zxCtWorksMapper;

	@Autowired(required = true)
	private ZxCtWorkBookServiceImpl zxCtWorkBookServiceImpl;

	@Autowired(required = true)
	private ZxCtWorkAlterSingleServiceImpl zxCtWorkAlterSingleServiceImpl;

	@Autowired(required = true)
	private ZxCtBalanceItemServiceImpl zxCtBalanceItemServiceImpl;

	@Autowired(required = true)
	private ZxCtContractMapper zxCtContractMapper;

	@Autowired(required = true)
	private ZxCtWorkToMUMapper zxCtWorkToMUMapper;

	@Autowired(required = true)
	private ZxCtWorkAlterSingleMapper zxCtWorkAlterSingleMapper;

	@Autowired(required = true)
	private ZxCtWorkToMUServiceImpl zxCtWorkToMUServiceImpl;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxCtWorksListByCondition(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null) {
			zxCtWorks = new ZxCtWorks();
		}
		// 分页查询
		PageHelper.startPage(zxCtWorks.getPage(), zxCtWorks.getLimit());
		// 获取数据
		List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.selectByZxCtWorksList(zxCtWorks);

		if (zxCtWorksList == null) {
			zxCtWorksList = new ArrayList<>();
		}

		// 根节点无数据需要创建一条根节点数据
		if (StrUtil.equals("-1", zxCtWorks.getParentID()) && zxCtWorksList.size() == 0) {
			HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
			String userKey = TokenUtils.getUserKey(request);
			String realName = TokenUtils.getRealName(request);

			// 查询WorkBookID
			ZxCtWorkBook zxCtWorkBook = new ZxCtWorkBook();
			zxCtWorkBook.setOrgID(zxCtWorks.getOrgID());
			List<ZxCtWorkBook> zxCtWorkBookList = (List<ZxCtWorkBook>) zxCtWorkBookServiceImpl
					.getZxCtWorkBookListByCondition(zxCtWorkBook).getData();

			ZxCtWorks addZxCtWorks = new ZxCtWorks();
			addZxCtWorks.setParentID("-1");
			addZxCtWorks.setTreeNode("1000");
			addZxCtWorks.setOrgID(zxCtWorks.getOrgID());
			if (zxCtWorkBookList != null && zxCtWorkBookList.size() > 0) {
				addZxCtWorks.setWorkBookID(zxCtWorkBookList.get(0).getId());
				addZxCtWorks.setWorkName(zxCtWorkBookList.get(0).getWorkBookName());
			}
			addZxCtWorks.setWorkType("10");
			addZxCtWorks.setWorkNo("-");
			addZxCtWorks.setIsLeaf(1);
			addZxCtWorks.setExsitStatus(0);
			addZxCtWorks.setIsAssignable(0);
			addZxCtWorks.setUpdateFlag("N");
			addZxCtWorks.setId(UuidUtil.generate());
			addZxCtWorks.setCreateUserInfo(userKey, realName);
			zxCtWorksMapper.insert(addZxCtWorks);
			zxCtWorksList.add(addZxCtWorks);
		}

		// 得到分页信息
		PageInfo<ZxCtWorks> p = new PageInfo<>(zxCtWorksList);

		return repEntity.okList(zxCtWorksList, p.getTotal());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxCtWorksDetail(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null) {
			zxCtWorks = new ZxCtWorks();
		}
		// 获取数据
		ZxCtWorks dbZxCtWorks = zxCtWorksMapper.selectByPrimaryKey(zxCtWorks.getId());
		// 数据存在
		if (dbZxCtWorks != null) {
			// 变更记录
			ZxCtWorkAlterSingle zxCtWorkAlterSingle = new ZxCtWorkAlterSingle();
			zxCtWorkAlterSingle.setWorkID(dbZxCtWorks.getId());
			List<ZxCtWorkAlterSingle> zxCtWorkAlterSingleList = (List<ZxCtWorkAlterSingle>) zxCtWorkAlterSingleServiceImpl
					.getZxCtWorkAlterSingleListByCondition(zxCtWorkAlterSingle).getData();
			dbZxCtWorks.setChangeRecord(zxCtWorkAlterSingleList);

			return repEntity.ok(dbZxCtWorks);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxCtWorks(ZxCtWorks zxCtWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxCtWorks.setCreateUserInfo(userKey, realName);

		if (StrUtil.isEmpty(zxCtWorks.getParentID()) && StrUtil.isEmpty(zxCtWorks.getId())) {
			return repEntity.layerMessage("no", "id、parentID不能同时为空！");
		}

		// treeNode处理
		if (StrUtil.isNotEmpty(zxCtWorks.getParentID())) { // 同级新增
			String treeNode = zxCtWorks.getTreeNode();
			BigDecimal intTreeNode = new BigDecimal(treeNode);
			zxCtWorks.setTreeNode(intTreeNode.add(new BigDecimal(1)) + "");
		} else if (StrUtil.isNotEmpty(zxCtWorks.getId())) { // 子级新增
			zxCtWorks.setParentID(zxCtWorks.getId());
			ZxCtWorks selectZxCtWorks = new ZxCtWorks();
			selectZxCtWorks.setOrgID(zxCtWorks.getOrgID());
			selectZxCtWorks.setParentID(zxCtWorks.getId());
			List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.selectByZxCtWorksList(selectZxCtWorks);
			if (zxCtWorksList == null || zxCtWorksList.size() == 0) {
				zxCtWorks.setTreeNode(zxCtWorks.getTreeNode() + "1001");
			} else {
				String treeNode = zxCtWorksList.get(zxCtWorksList.size() - 1).getTreeNode();
				BigDecimal intTreeNode = new BigDecimal(treeNode);
				zxCtWorks.setTreeNode(intTreeNode.add(new BigDecimal(1)) + "");
			}
		}
		zxCtWorks.setId(UuidUtil.generate());
		zxCtWorks.setWorkType("10");
		zxCtWorks.setExsitStatus(0);
		zxCtWorks.setIsAssignable(0);
		zxCtWorks.setUpdateFlag("N");
		zxCtWorks.setIsLeaf(1);

		int flag = zxCtWorksMapper.insert(zxCtWorks);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// 修改父节点IsLeaf为0
			ZxCtWorks updateZxCtWorks = new ZxCtWorks();
			updateZxCtWorks.setIsLeaf(0);
			updateZxCtWorks.setId(zxCtWorks.getParentID());
			zxCtWorksMapper.updateIsLeafByPrimaryKey(updateZxCtWorks);
			return repEntity.ok("sys.data.sava", zxCtWorks);
		}
	}

	@Override
	public ResponseEntity updateZxCtWorks(ZxCtWorks zxCtWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		BigDecimal originalQuantity = null, originalPrice = null;

		int flag = 0;
		ZxCtWorks dbZxCtWorks = zxCtWorksMapper.selectByPrimaryKey(zxCtWorks.getId());
		if (dbZxCtWorks != null && StrUtil.isNotEmpty(dbZxCtWorks.getId())) {
			// 父节点ID
			dbZxCtWorks.setParentID(zxCtWorks.getParentID());
			// 树节点编号
			dbZxCtWorks.setTreeNode(zxCtWorks.getTreeNode());
			// 项目机构ID
			dbZxCtWorks.setOrgID(zxCtWorks.getOrgID());
			// 清单书ID
			dbZxCtWorks.setWorkBookID(zxCtWorks.getWorkBookID());
			// 清单类型
			dbZxCtWorks.setWorkType(zxCtWorks.getWorkType());
			// 清单编号
			dbZxCtWorks.setWorkNo(zxCtWorks.getWorkNo());
			// 清单名称
			dbZxCtWorks.setWorkName(zxCtWorks.getWorkName());
			// 计量单位
			dbZxCtWorks.setUnit(zxCtWorks.getUnit());
			// 合同单价
			dbZxCtWorks.setContractPrice(zxCtWorks.getContractPrice());
			// 合同数量
			dbZxCtWorks.setContractQty(zxCtWorks.getContractQty());
			// 合同金额
			dbZxCtWorks.setContractAmt(zxCtWorks.getContractAmt());
			originalQuantity = dbZxCtWorks.getQuantity();
			originalPrice = dbZxCtWorks.getPrice();
			// 变更后数量
			dbZxCtWorks.setQuantity(zxCtWorks.getQuantity());
			// 变更后单价
			dbZxCtWorks.setPrice(zxCtWorks.getPrice());
			// 是否禁用
			dbZxCtWorks.setDeleted(zxCtWorks.getDeleted());
			// 是否叶子节点
			dbZxCtWorks.setIsLeaf(zxCtWorks.getIsLeaf());
			// 现有状态
			dbZxCtWorks.setExsitStatus(zxCtWorks.getExsitStatus());
			// 可分配的
			dbZxCtWorks.setIsAssignable(zxCtWorks.getIsAssignable());
			// 修改状态
			dbZxCtWorks.setUpdateFlag(zxCtWorks.getUpdateFlag());
			// combProp
			dbZxCtWorks.setCombProp(zxCtWorks.getCombProp());
			// pp1
			dbZxCtWorks.setPp1(zxCtWorks.getPp1());
			// pp2
			dbZxCtWorks.setPp2(zxCtWorks.getPp2());
			// pp3
			dbZxCtWorks.setPp3(zxCtWorks.getPp3());
			// pp4
			dbZxCtWorks.setPp4(zxCtWorks.getPp4());
			// pp5
			dbZxCtWorks.setPp5(zxCtWorks.getPp5());
			// pp6
			dbZxCtWorks.setPp6(zxCtWorks.getPp6());
			// pp7
			dbZxCtWorks.setPp7(zxCtWorks.getPp7());
			// pp8
			dbZxCtWorks.setPp8(zxCtWorks.getPp8());
			// pp9
			dbZxCtWorks.setPp9(zxCtWorks.getPp9());
			// pp10
			dbZxCtWorks.setPp10(zxCtWorks.getPp10());
			// 最后修改时间
			dbZxCtWorks.setEditTime(zxCtWorks.getEditTime());
			// 核查数量
			dbZxCtWorks.setCheckQty(zxCtWorks.getCheckQty());
			// expectChangeQty
			dbZxCtWorks.setExpectChangeQty(zxCtWorks.getExpectChangeQty());
			// expectChangePrice
			dbZxCtWorks.setExpectChangePrice(zxCtWorks.getExpectChangePrice());
			// inputWorkType
			dbZxCtWorks.setInputWorkType(zxCtWorks.getInputWorkType());
			// isReCountAmt
			dbZxCtWorks.setIsReCountAmt(zxCtWorks.getIsReCountAmt());
			// qty
			dbZxCtWorks.setQty(zxCtWorks.getQty());
			// isGroup
			dbZxCtWorks.setIsGroup(zxCtWorks.getIsGroup());
			// contractPriceNoTax
			dbZxCtWorks.setContractPriceNoTax(zxCtWorks.getContractPriceNoTax());
			// priceNoTax
			dbZxCtWorks.setPriceNoTax(zxCtWorks.getPriceNoTax());
			// contractAmtNoTax
			dbZxCtWorks.setContractAmtNoTax(zxCtWorks.getContractAmtNoTax());
			// amtNoTax
			dbZxCtWorks.setAmtNoTax(zxCtWorks.getAmtNoTax());
			// taxRate
			dbZxCtWorks.setTaxRate(zxCtWorks.getTaxRate());
			// 备注
			dbZxCtWorks.setRemarks(zxCtWorks.getRemarks());
			// 排序
			dbZxCtWorks.setSort(zxCtWorks.getSort());
			// 共通
			dbZxCtWorks.setModifyUserInfo(userKey, realName);
			flag = zxCtWorksMapper.updateByPrimaryKey(dbZxCtWorks);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// 变更记录
			ZxCtWorkAlterSingle zxCtWorkAlterSingle = new ZxCtWorkAlterSingle();
			zxCtWorkAlterSingle.setWorkID(dbZxCtWorks.getId());
			zxCtWorkAlterSingle.setWorkNo(dbZxCtWorks.getWorkNo());
			zxCtWorkAlterSingle.setWorkName(dbZxCtWorks.getWorkName());
			zxCtWorkAlterSingle.setUnit(dbZxCtWorks.getUnit());
			zxCtWorkAlterSingle.setQuantity(dbZxCtWorks.getQuantity());
			zxCtWorkAlterSingle.setPrice(dbZxCtWorks.getPrice());
			zxCtWorkAlterSingle.setAlterPerson(realName);
			zxCtWorkAlterSingle.setAlterDate(new Date());
			zxCtWorkAlterSingle.setAlterType("修改");
			zxCtWorkAlterSingle.setOriginalQuantity(originalQuantity);
			zxCtWorkAlterSingle.setOriginalPrice(originalPrice);
			zxCtWorkAlterSingle.setEditTime(new Date());
			zxCtWorkAlterSingle.setIsLeaf(dbZxCtWorks.getIsLeaf());
			zxCtWorkAlterSingleServiceImpl.saveZxCtWorkAlterSingle(zxCtWorkAlterSingle);
			return repEntity.ok("sys.data.update", zxCtWorks);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCtWorks(List<ZxCtWorks> zxCtWorksList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtWorksList != null && zxCtWorksList.size() > 0) {
			ZxCtWorks zxCtWorks = new ZxCtWorks();
			zxCtWorks.setModifyUserInfo(userKey, realName);
			flag = zxCtWorksMapper.batchDeleteUpdateZxCtWorks(zxCtWorksList, zxCtWorks);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			for (ZxCtWorks zxCtWorks : zxCtWorksList) {
				// 查询是否有子节点
				ZxCtWorks selectZxCtWorks = new ZxCtWorks();
				selectZxCtWorks.setParentID(zxCtWorks.getId());
				List<ZxCtWorks> worksList = zxCtWorksMapper.selectByZxCtWorksList(selectZxCtWorks);

				if (worksList == null || worksList.size() == 0) {
					// 无根节点修改 IsLeaf为1
					ZxCtWorks updateZxCtWorks = new ZxCtWorks();
					updateZxCtWorks.setIsLeaf(1);
					updateZxCtWorks.setId(zxCtWorks.getId());
					zxCtWorksMapper.updateIsLeafByPrimaryKey(updateZxCtWorks);
				}
			}
			return repEntity.ok("sys.data.delete", zxCtWorksList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxCtWorksWorkNameTree(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null) {
			zxCtWorks = new ZxCtWorks();
		}

		if (StrUtil.isEmpty(zxCtWorks.getOrgID())) {
			return repEntity.layerMessage("no", "项目ID不能为空！");
		}

		// 获取数据
		List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.selectByZxCtWorksList(zxCtWorks);

		if (zxCtWorksList == null || zxCtWorksList.size() == 0) {
			HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
			String userKey = TokenUtils.getUserKey(request);
			String realName = TokenUtils.getRealName(request);

			// 查询WorkBookID
			ZxCtWorkBook zxCtWorkBook = new ZxCtWorkBook();
			zxCtWorkBook.setOrgID(zxCtWorks.getOrgID());
			List<ZxCtWorkBook> zxCtWorkBookList = (List<ZxCtWorkBook>) zxCtWorkBookServiceImpl
					.getZxCtWorkBookListByCondition(zxCtWorkBook).getData();

			ZxCtWorks addZxCtWorks = new ZxCtWorks();
			addZxCtWorks.setParentID("-1");
			addZxCtWorks.setTreeNode("1000");
			addZxCtWorks.setOrgID(zxCtWorks.getOrgID());
			if (zxCtWorkBookList != null && zxCtWorkBookList.size() > 0) {
				addZxCtWorks.setWorkBookID(zxCtWorkBookList.get(0).getId());
				addZxCtWorks.setWorkName(zxCtWorkBookList.get(0).getWorkBookName());
			}
			addZxCtWorks.setWorkType("10");
			addZxCtWorks.setWorkNo("-");
			addZxCtWorks.setIsLeaf(1);
			addZxCtWorks.setExsitStatus(0);
			addZxCtWorks.setIsAssignable(0);
			addZxCtWorks.setUpdateFlag("N");
			addZxCtWorks.setId(UuidUtil.generate());
			addZxCtWorks.setCreateUserInfo(userKey, realName);
			zxCtWorksMapper.insert(addZxCtWorks);
			zxCtWorksList.add(addZxCtWorks);
		}

		// 转换树形
		JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(zxCtWorksList), "id", "parentID", "workName",
				"workNo");
		return repEntity.ok(jsonArray);
	}

	@Override
	public ResponseEntity getZxCtWorksTreeList(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null) {
			zxCtWorks = new ZxCtWorks();
		}

		if (StrUtil.isEmpty(zxCtWorks.getParentID())) {
			return repEntity.layerMessage("no", "父ID不能为空！");
		}
		if (StrUtil.isEmpty(zxCtWorks.getOrgID())) {
			return repEntity.layerMessage("no", "项目不能为空！");
		}

		if (StrUtil.equals("-1", zxCtWorks.getParentID())) {
			zxCtWorks.setParentID("");
		}

		// 分页查询
		PageHelper.startPage(zxCtWorks.getPage(), zxCtWorks.getLimit());
		// 获取数据
		List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.getZxCtWorksTreeList(zxCtWorks);

		PageInfo<ZxCtWorks> p = new PageInfo<>(zxCtWorksList);

		List<Map<String, Object>> mapList = new ArrayList<>();
		for (ZxCtWorks ctWorks : zxCtWorksList) {
			ctWorks.setAddFlag("0");
			if (ctWorks.getContractPrice() == null) {
				ctWorks.setContractPrice(new BigDecimal(0));
			}
			if (ctWorks.getContractQty() == null) {
				ctWorks.setContractQty(new BigDecimal(0));
			}
			if (ctWorks.getContractAmt() == null) {
				ctWorks.setContractAmt(new BigDecimal(0));
			}
			if (ctWorks.getCheckQty() == null) {
				ctWorks.setCheckQty(new BigDecimal(0));
			}
			if (ctWorks.getCheckAmt() == null) {
				ctWorks.setCheckAmt(new BigDecimal(0));
			}

			Map<String, Object> map = BeanUtil.beanToMap(ctWorks);
			map.remove("children");
			mapList.add(map);
		}

		return repEntity.okList(mapList, p.getTotal());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxCtWorksBalanceList(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null) {
			zxCtWorks = new ZxCtWorks();
		}

		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);

		if (StrUtil.equals("-1", zxCtWorks.getParentID()) && StrUtil.isEmpty(zxCtWorks.getOrgID())) {
			return repEntity.layerMessage("no", "根节点查询时orgID不能为空！");
		}

		if (StrUtil.isEmpty(zxCtWorks.getBalID())) {
			return repEntity.layerMessage("no", "BalID不能为空！");
		}

		if (StrUtil.equals("-1", zxCtWorks.getParentID())) {
			ZxCtWorks dbZxCtWorks = null;

			// 查询根节点及第一级子节点
			ZxCtWorks selectZxCtWorks = new ZxCtWorks();
			selectZxCtWorks.setOrgID(zxCtWorks.getOrgID());
			selectZxCtWorks.setParentID("-1");
			List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.selectByZxCtWorksList(selectZxCtWorks);
			if (zxCtWorksList != null && zxCtWorksList.size() > 0) {
				dbZxCtWorks = zxCtWorksList.get(0);
				// 查询第一级子节点
				zxCtWorks.setParentID(dbZxCtWorks.getId());
				// 获取数据
				List<ZxCtWorks> zxCtWorksChildList = zxCtWorksMapper.getZxCtWorksBalanceList(zxCtWorks);
				for (ZxCtWorks works : zxCtWorksChildList) {
					works.setContractAmt(CalcUtils.calcMultiply(works.getContractPrice(), works.getContractQty()));
					works.setCheckAmt(CalcUtils.calcMultiply(works.getContractPrice(), works.getCheckQty()));
					works.setChangeAmt(CalcUtils.calcMultiply(works.getPrice(), works.getQuantity()));
					// 假数据
					works.setBalAmt(works.getThisTotalAmt());
					works.setBalID(zxCtWorks.getBalID());
				}

				dbZxCtWorks.setChildren(zxCtWorksChildList);
			} else {
				// 查询WorkBookID
				ZxCtWorkBook zxCtWorkBook = new ZxCtWorkBook();
				zxCtWorkBook.setOrgID(zxCtWorks.getOrgID());
				List<ZxCtWorkBook> zxCtWorkBookList = (List<ZxCtWorkBook>) zxCtWorkBookServiceImpl
						.getZxCtWorkBookListByCondition(zxCtWorkBook).getData();

				ZxCtWorks addZxCtWorks = new ZxCtWorks();
				addZxCtWorks.setParentID("-1");
				addZxCtWorks.setTreeNode("1000");
				addZxCtWorks.setOrgID(zxCtWorks.getOrgID());
				if (zxCtWorkBookList != null && zxCtWorkBookList.size() > 0) {
					addZxCtWorks.setWorkBookID(zxCtWorkBookList.get(0).getId());
					addZxCtWorks.setWorkName(zxCtWorkBookList.get(0).getWorkBookName());
				}
				addZxCtWorks.setWorkType("10");
				addZxCtWorks.setWorkNo("-");
				addZxCtWorks.setIsLeaf(1);
				addZxCtWorks.setExsitStatus(0);
				addZxCtWorks.setIsAssignable(0);
				addZxCtWorks.setUpdateFlag("N");
				addZxCtWorks.setId(UuidUtil.generate());
				addZxCtWorks.setCreateUserInfo(userKey, realName);
				zxCtWorksMapper.insert(addZxCtWorks);
				dbZxCtWorks = addZxCtWorks;
			}
			List<ZxCtWorks> dbZxCtWorksList = new ArrayList<>();
			dbZxCtWorksList.add(dbZxCtWorks);
			return repEntity.okList(dbZxCtWorksList, 1);
		} else {
			// 分页查询
			PageHelper.startPage(zxCtWorks.getPage(), zxCtWorks.getLimit());
			// 获取数据
			List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.getZxCtWorksBalanceList(zxCtWorks);

			PageInfo<ZxCtWorks> p = new PageInfo<>(zxCtWorksList);

			for (ZxCtWorks works : zxCtWorksList) {
				works.setContractAmt(CalcUtils.calcMultiply(works.getContractPrice(), works.getContractQty()));
				works.setCheckAmt(CalcUtils.calcMultiply(works.getContractPrice(), works.getCheckQty()));
				works.setChangeAmt(CalcUtils.calcMultiply(works.getPrice(), works.getQuantity()));
				// 假数据
				works.setBalAmt(works.getThisTotalAmt());
				works.setBalID(zxCtWorks.getBalID());
			}

			return repEntity.okList(zxCtWorksList, p.getTotal());
		}
	}

	@Override
	public ResponseEntity getZxCtWorksBalanceEditList(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null) {
			zxCtWorks = new ZxCtWorks();
		}

		if (StrUtil.isEmpty(zxCtWorks.getOrgID())) {
			return repEntity.layerMessage("no", "orgID不能为空！");
		}

		if (StrUtil.isEmpty(zxCtWorks.getBalID())) {
			return repEntity.layerMessage("no", "BalID不能为空！");
		}

		// 查询没有跟节点数据
		List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.getZxCtWorksBalanceEditList(zxCtWorks);

		List<Map<String, Object>> mapList = new ArrayList<>();
		for (ZxCtWorks works : zxCtWorksList) {
			works.setContractAmt(CalcUtils.calcMultiply(works.getContractPrice(), works.getContractQty()));
			works.setCheckAmt(CalcUtils.calcMultiply(works.getContractPrice(), works.getCheckQty()));
			works.setChangeAmt(CalcUtils.calcMultiply(works.getPrice(), works.getQuantity()));
			// 假数据
			works.setBalAmt(works.getThisTotalAmt());
			works.setBalID(zxCtWorks.getBalID());
			if (works.getContractPrice() == null) {
				works.setContractPrice(new BigDecimal(0));
			}
			if (works.getContractQty() == null) {
				works.setContractQty(new BigDecimal(0));
			}
			if (works.getCheckQty() == null) {
				works.setCheckQty(new BigDecimal(0));
			}

			Map<String, Object> map = BeanUtil.beanToMap(works);
			map.remove("children");
			mapList.add(map);
		}

		return repEntity.ok(mapList);
	}

	@Override
	public ResponseEntity updateZxCtWorksBalanceList(List<ZxCtWorks> zxCtWorksList) {
		if (zxCtWorksList == null || zxCtWorksList.size() == 0) {
			return repEntity.layerMessage("no", "请选择数据！");
		}

		for (ZxCtWorks zxCtWorks : zxCtWorksList) {
			if (StrUtil.isEmpty(zxCtWorks.getBalItemId())) {
				// 新增
				ZxCtBalanceItem zxCtBalanceItem = new ZxCtBalanceItem();
				zxCtBalanceItem.setBalID(zxCtWorks.getBalID());
				zxCtBalanceItem.setMuID("MU" + zxCtWorks.getOrgID());
				zxCtBalanceItem.setWorkID(zxCtWorks.getId());
				zxCtBalanceItem.setPrice(zxCtWorks.getPrice());
				zxCtBalanceItem.setBalQty(zxCtWorks.getBalQty());
				zxCtBalanceItem.setBalAltQty(zxCtWorks.getBalAltQty());
				zxCtBalanceItem
						.setBalAmt(
								CalcUtils
										.calcAdd(
												CalcUtils.calcMultiply(zxCtWorks.getPrice(),
														CalcUtils.calcAdd(zxCtWorks.getBalQty(),
																zxCtWorks.getBalAltQty())),
												zxCtWorks.getChangeAltAmt()));
				zxCtBalanceItem.setChangeAltAmt(zxCtWorks.getChangeAltAmt());
				zxCtBalanceItemServiceImpl.saveZxCtBalanceItem(zxCtBalanceItem);
			} else {
				// 修改
				ZxCtBalanceItem zxCtBalanceItem = new ZxCtBalanceItem();
				zxCtBalanceItem.setId(zxCtWorks.getBalItemId());
				zxCtBalanceItem.setBalQty(zxCtWorks.getBalQty());
				zxCtBalanceItem.setBalAltQty(zxCtWorks.getBalAltQty());
				zxCtBalanceItem.setChangeAltAmt(zxCtWorks.getChangeAltAmt());
				zxCtBalanceItem
						.setBalAmt(
								CalcUtils
										.calcAdd(
												CalcUtils.calcMultiply(zxCtWorks.getPrice(),
														CalcUtils.calcAdd(zxCtWorks.getBalQty(),
																zxCtWorks.getBalAltQty())),
												zxCtWorks.getChangeAltAmt()));
				zxCtBalanceItemServiceImpl.updateZxCtBalanceItemById(zxCtBalanceItem);
			}
		}

		return repEntity.ok("更新成功");
	}

	@Override
	public ResponseEntity updateZxCtWorksList(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null || zxCtWorks.getChildren() == null) {
			return repEntity.layerMessage("no", "请选择数据!");
		}

		if (StrUtil.isEmpty(zxCtWorks.getParentID()) && StrUtil.isEmpty(zxCtWorks.getOrgID())) {
			return repEntity.layerMessage("no", "parentID、orgID不能同时为空!");
		}

		List<ZxCtWorks> zxCtWorksList = zxCtWorks.getChildren();
		if (zxCtWorksList == null) {
			zxCtWorksList = new ArrayList<>();
		}

		// 清单编号唯一检索验证（先判断新增数据是否与所有数据有相同清单编号，再去数据库查询是否有相同清单编号）
		String msg = checkWorks(zxCtWorksList, zxCtWorks);
		if (StrUtil.isNotEmpty(msg)) {
			return repEntity.layerMessage("no", msg);
		}

		// 删除数据
		zxCtWorksMapper.deleteAllByTreeNode(zxCtWorks);

		for (ZxCtWorks works : zxCtWorksList) {
			if (StrUtil.equals("1", works.getAddFlag())) {
				works.setId(works.getParentID());
				works.setParentID("");
				works.setTreeNode(zxCtWorks.getTreeNode());
				saveZxCtWorks(works);
			} else {
				zxCtWorksMapper.insert(works);
			}
		}

		// 修改父节点IsLeaf
		ZxCtWorks updateZxCtWorks = new ZxCtWorks();
		updateZxCtWorks.setIsLeaf(zxCtWorksList.size() == 0 ? 1 : 0);
		updateZxCtWorks.setId(zxCtWorks.getId());
		zxCtWorksMapper.updateIsLeafByPrimaryKey(updateZxCtWorks);

		return repEntity.ok("修改成功！");
	}

	@Override
	public ResponseEntity saveZxCtWorksList(ZxCtWorks zxCtWorks) {
		List<ZxCtWorks> zxCtWorksList = zxCtWorks.getChildren();

		if (zxCtWorksList == null) {
			zxCtWorksList = new ArrayList<>();
		}

		// 清单编号唯一检索验证（先判断新增数据是否与所有数据有相同清单编号，再去数据库查询是否有相同清单编号）
		String msg = checkWorks(zxCtWorksList, zxCtWorks);
		if (StrUtil.isNotEmpty(msg)) {
			return repEntity.layerMessage("no", msg);
		}

		// 删除children下清单
		zxCtWorksMapper.deleteAllChildren(zxCtWorks.getChildren());

		for (ZxCtWorks works : zxCtWorksList) {
			// 插入未删除数据
			if (!StrUtil.equals("2", works.getAddFlag())) {
				zxCtWorksMapper.insert(works);
			}
		}

		return repEntity.ok("修改成功！");
	}

	/**
	 * 检索清单编号是否有重复项
	 * 
	 * @param zxCtWorksList
	 * @param zxCtWorks
	 * @return
	 */
	private String checkWorks(List<ZxCtWorks> zxCtWorksList, ZxCtWorks zxCtWorks) {
		// 清单编号唯一检索验证（先判断新增数据是否与所有数据有相同清单编号，再去数据库查询是否有相同清单编号）
		if (zxCtWorksList.size() > 0) {
			List<String> workNoList = new ArrayList<>();
			for (int i = 0; i < zxCtWorksList.size(); i++) {
				ZxCtWorks works = zxCtWorksList.get(i);
				if (workNoList.contains(works.getWorkNo())) {
					int haveNo = workNoList.indexOf(works.getWorkNo());
					return "第" + (haveNo + 1) + "个清单与第" + (i + 1) + "个清单的编号重复，不允许提交！！！";
				} else {
					workNoList.add(works.getWorkNo());
				}
			}
			// 去数据库查询是否有重复编号
			List<ZxCtWorks> alredyHaveWorks = zxCtWorksMapper.selectAlredyHaveWorks(zxCtWorks, zxCtWorksList);
			if (alredyHaveWorks != null && alredyHaveWorks.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (ZxCtWorks haveWorks : alredyHaveWorks) {
					sb.append(haveWorks.getWorkNo() + ",");
				}
				return "编号为" + sb.substring(0, sb.length() - 1) + "的清单编号已存在！！！";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	@Override
	public ResponseEntity importZxCtWorks(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null || zxCtWorks.getAttachment() == null || zxCtWorks.getAttachment().size() == 0) {
			return repEntity.layerMessage("no", "请上传导入模板！");
		}

		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);

		String filePath = Apih5Properties.getFilePath() + zxCtWorks.getAttachment().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);

		String workBookID = zxCtWorks.getWorkBookID();
		String inputWorkType = zxCtWorks.getInputWorkType();

		ZxCtWorkBook zxCtWorkBook = new ZxCtWorkBook();
		zxCtWorkBook.setId(workBookID);
		ZxCtWorkBook dbWorkBookMap = (ZxCtWorkBook) zxCtWorkBookServiceImpl.getZxCtWorkBookDetail(zxCtWorkBook)
				.getData();

		// 责任中心ID
		String orgID = dbWorkBookMap.getOrgID();

		// 获取合同信息
		ZxCtContract zxCtContract = new ZxCtContract();
		zxCtContract.setOrgID(orgID);
		List<ZxCtContract> zxCtContractList = zxCtContractMapper.selectByZxCtContractList(zxCtContract);

		String taxRate = "";

		// 合同清单都是合同评审生成的
		if (zxCtContractList != null) {
			taxRate = zxCtContractList.get(0).getTaxRate();
		}

		// 在iecc_works中有一条记录，取出其ID和workType——清单类型
		ZxCtWorks selectZxCtWorks = new ZxCtWorks();
		selectZxCtWorks.setWorkBookID(workBookID);
		selectZxCtWorks.setParentID("-1");
		List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.selectByZxCtWorksList(selectZxCtWorks);

		ZxCtWorks dbZxCtWorks = new ZxCtWorks(); // worksMap
		// 如果顶级节点清单不存在
		if (zxCtWorksList == null || zxCtWorksList.size() == 0) {
			dbZxCtWorks.setParentID("-1");
			dbZxCtWorks.setTreeNode("1000");
			dbZxCtWorks.setOrgID(orgID);
			dbZxCtWorks.setWorkBookID(workBookID);
			dbZxCtWorks.setWorkName(dbWorkBookMap.getWorkBookName());
			dbZxCtWorks.setWorkType("10");
			dbZxCtWorks.setWorkNo("-");
			dbZxCtWorks.setIsLeaf(1);
			dbZxCtWorks.setExsitStatus(0);
			dbZxCtWorks.setIsAssignable(0);
			dbZxCtWorks.setUpdateFlag("N");
			dbZxCtWorks.setId(UuidUtil.generate());
			dbZxCtWorks.setCreateUserInfo(userKey, realName);
		} else {
			dbZxCtWorks = zxCtWorksList.get(0);
			dbZxCtWorks.setId(UuidUtil.generate());
		}

		// 清单类型
		String workType = dbZxCtWorks.getWorkType();
		// ID作为清单一级节点的parentID
		String specParentID = dbZxCtWorks.getId();
		String specTreeNode = dbZxCtWorks.getTreeNode();

		List<ZxCtWorks> importValueList = new ArrayList<>();

		try {
			List<Map<String, Object>> readAll = reader.readAll();
			if (readAll == null || readAll.size() == 0) {
				return repEntity.layerMessage("no", "导入失败，无导入数据!");
			}

			int k = 0;
			for (Map<String, Object> map : readAll) {
				ZxCtWorks importZxCtWorks = new ZxCtWorks();
				importZxCtWorks.setId(UuidUtil.generate());

				// 清单编号
				String workNo = String.valueOf(map.getOrDefault("清单编号", ""));
				if (StrUtil.isEmpty(workNo)) {
					return repEntity.layerMessage("no", "该Excel第" + (k + 1) + "行第1列数据格式不正确(不能为空),不能正常进行导入！请调整后重新导入");
				}
				importZxCtWorks.setWorkNo(workNo);

				// 清单名称
				String workName = String.valueOf(map.getOrDefault("清单名称", ""));
				importZxCtWorks.setWorkName(workName);

				// 清单单位
				String unit = String.valueOf(map.getOrDefault("单位", ""));
				importZxCtWorks.setUnit(unit);

				// 数量
				String quantity = String.valueOf(map.getOrDefault("数量", ""));
				if (StrUtil.isNotEmpty(quantity)) {
					try {
						BigDecimal contractQty = new BigDecimal(quantity);
						importZxCtWorks.setContractQty(contractQty);
						importZxCtWorks.setCheckQty(contractQty);
					} catch (Exception e) {
						return repEntity.layerMessage("no",
								"该Excel第" + (k + 1) + "行第4列数据格式不正确(应为数值类型),不能正常进行导入！请调整后重新导入");
					}
				}

				// 单价
				String price = String.valueOf(map.getOrDefault("单价", ""));
				if (StrUtil.isNotEmpty(price)) {
					try {
						BigDecimal contractPrice = new BigDecimal(price);
						importZxCtWorks.setContractPrice(contractPrice);
						importZxCtWorks.setContractAmt(
								CalcUtils.calcMultiply(contractPrice, importZxCtWorks.getContractQty()));

						if (StrUtil.isEmpty(taxRate) || StrUtil.equals("空", taxRate)) {
							importZxCtWorks.setPriceNoTax(new BigDecimal(0));
						} else {
							importZxCtWorks.setPriceNoTax(CalcUtils.calcDivide(new BigDecimal(price),
									CalcUtils.calcMultiply(
											CalcUtils.calcAdd(new BigDecimal(1), new BigDecimal(taxRate)),
											new BigDecimal(0.01)),
									2));
						}
					} catch (Exception e) {
						return repEntity.layerMessage("no",
								"该Excel第" + (k + 1) + "行第5列数据格式不正确(应为数值类型),不能正常进行导入！请调整后重新导入");
					}
				}
				importZxCtWorks.setOrgID(orgID);
				importZxCtWorks.setWorkBookID(workBookID);
				importZxCtWorks.setWorkType(workType);
				importZxCtWorks.setIsLeaf(1);
				importZxCtWorks.setIsAssignable(1);
				importZxCtWorks.setQty(new BigDecimal(0));
				importZxCtWorks.setSort(k);
				importZxCtWorks.setWorkNoLength(getWorkNoLength(workNo, inputWorkType));
				importZxCtWorks.setCreateUserInfo(userKey, realName);
				importValueList.add(importZxCtWorks);
				k++;
			}
			reader.close();

			// 增加重复清单编号的判断
			String checkInfo = workNoExistCheckForImp(importValueList, orgID, workBookID);
			if (!"0".equals(checkInfo)) {
				return repEntity.layerMessage("no", checkInfo);
			}

			// 获得最大的长度、最小长度
			int maxNoLen = 0;
			for (ZxCtWorks works : importValueList) {
				int len = works.getWorkNoLength();
				if (maxNoLen < len) {
					maxNoLen = len;
				}
			}

			List<ZxCtWorks> resultList = new ArrayList<>();
			// 设置父节点ID——parentID以及treeNode
			// 以下三个变量用来设置TreeNode
			// 找出已有清单中长度为8的treeNode中最大的那个
			int lenCount = 0;

			ZxCtWorks maxTreeNodeWorks = zxCtWorksMapper.getMaxTreeNode(zxCtWorks);

			String maxHaveCount = maxTreeNodeWorks == null ? "" : maxTreeNodeWorks.getTreeNode();
			if (StrUtil.isNotEmpty(maxHaveCount)) {
				maxHaveCount = maxHaveCount.substring(5, 8);
				lenCount = Integer.parseInt(maxHaveCount) + 1;
			} else {
				lenCount = 1;
			}

			String comparePWorkNo = "";
			int count = 1;
			for (int i = 1; i <= maxNoLen; i++) {
				for (ZxCtWorks workNoInfo : importValueList) {
					if (i == workNoInfo.getWorkNoLength()) {
						// 找到此节点之前的第一个编号长度比当前编号短的就是他的父节点
						int sIndex = workNoInfo.getSort();
						// 判断是否能够找到编号比当前编号短的父节点
						boolean flag = true;
						for (int m = sIndex; m >= 0; m--) {
							ZxCtWorks pWorkNoInfo = importValueList.get(m);
							if (pWorkNoInfo.getWorkNoLength() < workNoInfo.getWorkNoLength()) {
								workNoInfo.setParentID(pWorkNoInfo.getId());
								if (!comparePWorkNo.equals(pWorkNoInfo.getWorkNo())) {
									comparePWorkNo = pWorkNoInfo.getWorkNo();
									count = 1;
								} else {
									count++;
								}
								int temp_TreeNode = 1000 + count;
								workNoInfo.setTreeNode(pWorkNoInfo.getTreeNode() + temp_TreeNode);
								resultList.add(workNoInfo);
								// 找到编号长度比当前节点短的父节点了
								flag = false;
								break;
							}
						}
						if (flag) {
							// 没有找到，设置父节点的清单编号以及ID
							workNoInfo.setParentID(specParentID);
							int temp_specTreeNode = 1000 + lenCount;
							workNoInfo.setTreeNode(specTreeNode + temp_specTreeNode);
							resultList.add(workNoInfo);
							lenCount++;
						}
					}
				}
			}

			BigDecimal contractAmt = new BigDecimal(0);// 合同金额
			// 新增数据
			for (ZxCtWorks wmDto : resultList) {
				contractAmt = CalcUtils.calcAdd(contractAmt, CalcUtils.calcDivide(
						CalcUtils.calcMultiply(wmDto.getPrice(), wmDto.getQuantity()), new BigDecimal(10000)));
			}
			// 1..删除清单书下所有数据
			ZxCtWorkToMU zxCtWorkToMU = new ZxCtWorkToMU();
			zxCtWorkToMU.setWorkBookID(workBookID);
			zxCtWorkToMU.setModifyUserInfo(userKey, realName);
			zxCtWorkToMUMapper.deleteByWorkBookID(zxCtWorkToMU);
			ZxCtWorks deleteZxCtWorks = new ZxCtWorks();
			deleteZxCtWorks.setWorkBookID(workBookID);
			deleteZxCtWorks.setModifyUserInfo(userKey, realName);
			zxCtWorksMapper.deleteByWorkBookID(deleteZxCtWorks);

			// 3.执行插入数据
			zxCtWorksMapper.insert(dbZxCtWorks);
			for (ZxCtWorks addZxCtWorks : resultList) {
				zxCtWorksMapper.insert(addZxCtWorks);
			}

			// 更新合同金额
//			ZxCtContract updateZxCtContract = new ZxCtContract();
//			updateZxCtContract.setOrgID(orgID);
//			updateZxCtContract.setContractCost(contractAmt);
//			updateZxCtContract.setModifyUserInfo(userKey, realName);
//			zxCtContractMapper.updateContractCostByOrgID(updateZxCtContract);

			// 重新设置是否叶子节点，判断条件为如果清单ID存在parentID中的话，就非叶子节点，否则为叶子节点。
			ZxCtWorks isLeafZxCtWorks = new ZxCtWorks();
			isLeafZxCtWorks.setOrgID(orgID);
			isLeafZxCtWorks.setModifyUserInfo(userKey, realName);
			zxCtWorksMapper.updateIsLeaf(isLeafZxCtWorks);

			// 清单导入时自动挂接管理单元
			ZxCtWorkToMU addZxCtWorkToMU = new ZxCtWorkToMU();
			addZxCtWorkToMU.setOrgID(orgID);
			zxCtWorkToMUServiceImpl.addAllWorkToMUByOrgID(addZxCtWorkToMU);
			return repEntity.ok(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return repEntity.layerMessage("no", "导入异常！");
		}
	}

	/**
	 * 获取编号“长度”——这里的长度是以“-”分割的字符数量
	 * 
	 * @param workNo
	 * @param inputWorkType
	 * @return
	 */
	private boolean flag = false;

	private int getWorkNoLength(String workNo, String inputWorkType) {
		if (inputWorkType.equals("1")) {
			String[] workNoArr = workNo.split("-");
			// 如果是 103，104 等，也只节，不是章
			if (workNoArr.length <= 1) { // 当workNo是以00结尾时、是章
				// 处理百章下包含多个小百章的情况
				if ((workNo.length() > 1 && !"00".equals(workNo.substring(workNo.length() - 2, workNo.length())))) {
					// 用于记录这是小百章的情况
					this.flag = true;
					return 2;
				}
			} else {
				if (flag) {
					return (workNoArr.length + 2);
				} else {
					return (workNoArr.length + 1);
				}
			}
			flag = false;
			return workNoArr.length;
		} else {
			// 判断是否存在中文字符，如果存在中文字符，该字符不计入编号长度
			char[] charArray = workNo.toCharArray();
			int count = 0;
			for (int i = 0; i < charArray.length; i++) {
				if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
					continue;
				}
				count++;
			}
			return count;
		}
	}

	/**
	 * 检查是否存在相同清单编号(导入专用).
	 * 
	 * @param importValueList
	 * @param orgID
	 * @param workBookID
	 * @return
	 */
	private String workNoExistCheckForImp(List<ZxCtWorks> importValueList, String orgID, String workBookID) {
		// 返回字符串
		StringBuffer rtnStrBuf = new StringBuffer();
		if (importValueList != null && !importValueList.isEmpty()) {
			List<String> workNoMapInExcel = new ArrayList<>();
			for (ZxCtWorks zxCtWorks : importValueList) {
				if (workNoMapInExcel.contains(zxCtWorks.getWorkNo())) {
					rtnStrBuf.append("编号为“").append(zxCtWorks.getWorkNo()).append("”在excel中重复！！！\n");
				} else {
					workNoMapInExcel.add(zxCtWorks.getWorkNo());
				}
			}
		}

		String rtnStr = rtnStrBuf.toString();
		if (StrUtil.isEmpty(rtnStr)) {
			return "0";
		} else {
			return rtnStr;
		}
	}

	@Override
	public ResponseEntity addZxCtWorksChange(ZxCtWorks zxCtWorks) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxCtWorks.setCreateUserInfo(userKey, realName);

		if (StrUtil.isEmpty(zxCtWorks.getId())) {
			return repEntity.layerMessage("no", "id不能为空！");
		}

		// 清单编号唯一性检索
		ZxCtWorks checkZxCtWorks = new ZxCtWorks();
		checkZxCtWorks.setOrgID(zxCtWorks.getOrgID());
		checkZxCtWorks.setWorkNo(zxCtWorks.getWorkNo());
		List<ZxCtWorks> checkWorksList = zxCtWorksMapper.selectByZxCtWorksList(checkZxCtWorks);
		if (checkWorksList != null && checkWorksList.size() > 0) {
			return repEntity.layerMessage("no", "该清单编号" + zxCtWorks.getWorkNo() + "已存在！！！");
		}

		// treeNode处理 ---子级新增
		zxCtWorks.setParentID(zxCtWorks.getId());
		ZxCtWorks selectZxCtWorks = new ZxCtWorks();
		selectZxCtWorks.setOrgID(zxCtWorks.getOrgID());
		selectZxCtWorks.setParentID(zxCtWorks.getId());
		selectZxCtWorks.setIsLeaf(0);
		List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.selectByZxCtWorksList(selectZxCtWorks);
		if (zxCtWorksList == null || zxCtWorksList.size() == 0) {
			zxCtWorks.setTreeNode(zxCtWorks.getTreeNode() + "1001");
		} else {
			String treeNode = zxCtWorksList.get(zxCtWorksList.size() - 1).getTreeNode();
			BigDecimal intTreeNode = new BigDecimal(treeNode);
			zxCtWorks.setTreeNode(intTreeNode.add(new BigDecimal(1)) + "");
		}
		zxCtWorks.setContractQty(zxCtWorks.getContractQty() == null ? new BigDecimal(0) : zxCtWorks.getContractQty());
		zxCtWorks.setContractPrice(
				zxCtWorks.getContractPrice() == null ? new BigDecimal(0) : zxCtWorks.getContractPrice());
		zxCtWorks.setId(UuidUtil.generate());
		zxCtWorks.setWorkType("10");
		zxCtWorks.setExsitStatus(0);
		zxCtWorks.setIsAssignable(0);
		zxCtWorks.setUpdateFlag("N");
		zxCtWorks.setIsLeaf(zxCtWorks.getIsLeaf());

		int flag = zxCtWorksMapper.insert(zxCtWorks);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			ZxCtWorkAlterSingle zxCtWorkAlterSingle = new ZxCtWorkAlterSingle();
			zxCtWorkAlterSingle.setId(UuidUtil.generate());
			zxCtWorkAlterSingle.setCreateUserInfo(userKey, realName);
			zxCtWorkAlterSingle.setAlterType("新增");
			zxCtWorkAlterSingle.setWorkID(zxCtWorks.getId());
			zxCtWorkAlterSingle.setWorkNo(zxCtWorks.getWorkNo());
			zxCtWorkAlterSingle.setWorkName(zxCtWorks.getWorkName());
			zxCtWorkAlterSingle.setUnit(zxCtWorks.getUnit());
			zxCtWorkAlterSingle.setQuantity(zxCtWorks.getQuantity());
			zxCtWorkAlterSingle.setPrice(zxCtWorks.getPrice());
			zxCtWorkAlterSingle.setAlterPerson(realName);
			zxCtWorkAlterSingle.setAlterDate(new Date());
			zxCtWorkAlterSingle.setOriginalQuantity(zxCtWorks.getContractQty());
			zxCtWorkAlterSingle.setOriginalPrice(zxCtWorks.getContractPrice());
			zxCtWorkAlterSingleMapper.insert(zxCtWorkAlterSingle);
			return repEntity.ok("sys.data.sava", zxCtWorks);
		}
	}

	@Override
	public ResponseEntity delZxCtWorksByOrgIDWorkBookID(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null || StrUtil.isEmpty(zxCtWorks.getOrgID())) {
			return repEntity.layerMessage("no", "orgID不能为空！");
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxCtWorks.setModifyUserInfo(userKey, realName);

		int flag = zxCtWorksMapper.delZxCtWorksByOrgIDWorkBookID(zxCtWorks);

		if (flag == 0) {
			return repEntity.errorDelete();
		} else {
			return repEntity.ok("删除成功！");
		}
	}

	@Override
	public ResponseEntity getZxCtWorksRightTree(ZxCtWorks zxCtWorks) {
		if (zxCtWorks == null) {
			zxCtWorks = new ZxCtWorks();
		}
		if (StrUtil.isEmpty(zxCtWorks.getOrgID())) {
			return repEntity.layerMessage("no", "项目ID不能为空！");
		}
		// 获取数据
		List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.selectByZxCtWorksList(zxCtWorks);
		JSONArray arr = getTree(JSONUtil.parseArray(zxCtWorksList));
		return repEntity.okList(arr, arr.size());
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
				newJSONObj.set("id", json.get("id"));
				newJSONObj.set("parentID", json.get("parentID"));
				newJSONObj.set("treeNode", json.get("treeNode"));
				newJSONObj.set("workNo", json.get("workNo"));
				newJSONObj.set("workName", json.get("workName"));
				newJSONObj.set("unit", json.get("unit"));
				newJSONObj.set("isLeaf", json.get("isLeaf"));
				hash.set(json.getStr("id"), newJSONObj);
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
