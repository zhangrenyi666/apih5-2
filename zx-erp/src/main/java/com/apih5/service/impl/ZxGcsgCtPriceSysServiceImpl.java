package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxGcsgCcCoAlterWorkMapper;
import com.apih5.mybatis.dao.ZxGcsgCcWorksMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrApplyWorksMapper;
import com.apih5.mybatis.dao.ZxGcsgCtPriceSysItemMapper;
import com.apih5.mybatis.dao.ZxGcsgCtPriceSysMapper;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlterWork;
import com.apih5.mybatis.pojo.ZxGcsgCcWorks;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApplyWorks;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSys;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSysItem;
import com.apih5.service.ZxGcsgCtPriceSysService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxGcsgCtPriceSysService")
public class ZxGcsgCtPriceSysServiceImpl implements ZxGcsgCtPriceSysService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxGcsgCtPriceSysMapper zxGcsgCtPriceSysMapper;
	@Autowired(required = true)
	private ZxGcsgCtContrApplyWorksMapper zxGcsgCtContrApplyWorksMapper;
	@Autowired(required = true)
	private ZxGcsgCcWorksMapper zxGcsgCcWorksMapper;
	@Autowired(required = true)
	private ZxGcsgCcCoAlterWorkMapper zxGcsgCcCoAlterWorkMapper;
	@Autowired(required = true)
	private ZxGcsgCtPriceSysItemMapper zxGcsgCtPriceSysItemMapper;

	@Override
	public ResponseEntity getZxGcsgCtPriceSysListByCondition(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		if (zxGcsgCtPriceSys == null) {
			zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
		}
		// 分页查询
		PageHelper.startPage(zxGcsgCtPriceSys.getPage(), zxGcsgCtPriceSys.getLimit());
		// 获取数据
		List<ZxGcsgCtPriceSys> zxGcsgCtPriceSysList = zxGcsgCtPriceSysMapper
				.selectByZxGcsgCtPriceSysList(zxGcsgCtPriceSys);
		// 得到分页信息
		PageInfo<ZxGcsgCtPriceSys> p = new PageInfo<>(zxGcsgCtPriceSysList);

		return repEntity.okList(zxGcsgCtPriceSysList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCtPriceSysDetail(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		if (zxGcsgCtPriceSys == null) {
			zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
		}
		// 获取数据
		ZxGcsgCtPriceSys dbZxGcsgCtPriceSys = zxGcsgCtPriceSysMapper
				.selectByPrimaryKey(zxGcsgCtPriceSys.getCtPriceSysId());
		// 数据存在
		if (dbZxGcsgCtPriceSys != null) {
			return repEntity.ok(dbZxGcsgCtPriceSys);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxGcsgCtPriceSys.setCtPriceSysId(UuidUtil.generate());
		zxGcsgCtPriceSys.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgCtPriceSysMapper.insert(zxGcsgCtPriceSys);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCtPriceSys);
		}
	}

	@Override
	public ResponseEntity updateZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtPriceSys dbZxGcsgCtPriceSys = zxGcsgCtPriceSysMapper
				.selectByPrimaryKey(zxGcsgCtPriceSys.getCtPriceSysId());
		if (dbZxGcsgCtPriceSys != null && StrUtil.isNotEmpty(dbZxGcsgCtPriceSys.getCtPriceSysId())) {
			// 合同评审补充协议ID(contrApplyID)
			dbZxGcsgCtPriceSys.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
			// 合同管理ID(contractID)
			dbZxGcsgCtPriceSys.setCtContractId(zxGcsgCtPriceSys.getCtContractId());
			// 合同评审补充协议清单ID(applyWorkID)
			dbZxGcsgCtPriceSys.setApplyAlterWorksId(zxGcsgCtPriceSys.getApplyAlterWorksId());
			// 合同管理清单ID(workID)
			dbZxGcsgCtPriceSys.setCcWorksId(zxGcsgCtPriceSys.getCcWorksId());
			// 清单编号
			dbZxGcsgCtPriceSys.setWorkNo(zxGcsgCtPriceSys.getWorkNo());
			// 清单名称
			dbZxGcsgCtPriceSys.setWorkName(zxGcsgCtPriceSys.getWorkName());
			// 单位
			dbZxGcsgCtPriceSys.setWorkUnit(zxGcsgCtPriceSys.getWorkUnit());
			// 不含税单价
			dbZxGcsgCtPriceSys.setPriceNoTax(zxGcsgCtPriceSys.getPriceNoTax());
			// 不含税标后预算单价/限价
			dbZxGcsgCtPriceSys.setBhPriceNoTax(zxGcsgCtPriceSys.getBhPriceNoTax());
			// 业主清单单价
			dbZxGcsgCtPriceSys.setYzPriceNoTax(zxGcsgCtPriceSys.getYzPriceNoTax());
			// 对比(标后预算/限价-不含税合价)
			dbZxGcsgCtPriceSys.setDbPrice(zxGcsgCtPriceSys.getDbPrice());
			// 所属公司ID
			dbZxGcsgCtPriceSys.setComID(zxGcsgCtPriceSys.getComID());
			// 所属公司名称
			dbZxGcsgCtPriceSys.setComName(zxGcsgCtPriceSys.getComName());
			// 最后编辑时间
			dbZxGcsgCtPriceSys.setEditTime(zxGcsgCtPriceSys.getEditTime());
			// 主材
			dbZxGcsgCtPriceSys.setAmt1(zxGcsgCtPriceSys.getAmt1());
			// 管理费
			dbZxGcsgCtPriceSys.setAmt2(zxGcsgCtPriceSys.getAmt2());
			// 利润
			dbZxGcsgCtPriceSys.setAmt3(zxGcsgCtPriceSys.getAmt3());
			// 人工费
			dbZxGcsgCtPriceSys.setAmt4(zxGcsgCtPriceSys.getAmt4());
			// 周转材料
			dbZxGcsgCtPriceSys.setAmt5(zxGcsgCtPriceSys.getAmt5());
			// 机械设备
			dbZxGcsgCtPriceSys.setAmt6(zxGcsgCtPriceSys.getAmt6());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField1(zxGcsgCtPriceSys.getOpinionField1());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField2(zxGcsgCtPriceSys.getOpinionField2());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField3(zxGcsgCtPriceSys.getOpinionField3());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField4(zxGcsgCtPriceSys.getOpinionField4());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField5(zxGcsgCtPriceSys.getOpinionField5());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField6(zxGcsgCtPriceSys.getOpinionField6());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField7(zxGcsgCtPriceSys.getOpinionField7());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField8(zxGcsgCtPriceSys.getOpinionField8());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField9(zxGcsgCtPriceSys.getOpinionField9());
			// 备注
			dbZxGcsgCtPriceSys.setOpinionField10(zxGcsgCtPriceSys.getOpinionField10());
			// 流程id
			dbZxGcsgCtPriceSys.setApih5FlowId(zxGcsgCtPriceSys.getApih5FlowId());
			// work_id
			dbZxGcsgCtPriceSys.setWorkId(zxGcsgCtPriceSys.getWorkId());
			// 工序审核状态
			dbZxGcsgCtPriceSys.setApih5FlowStatus(zxGcsgCtPriceSys.getApih5FlowStatus());
			// 流程状态
			dbZxGcsgCtPriceSys.setApih5FlowNodeStatus(zxGcsgCtPriceSys.getApih5FlowNodeStatus());
			// 备注
			dbZxGcsgCtPriceSys.setRemarks(zxGcsgCtPriceSys.getRemarks());
			// 排序
			dbZxGcsgCtPriceSys.setSort(zxGcsgCtPriceSys.getSort());
			// 共通
			dbZxGcsgCtPriceSys.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtPriceSysMapper.updateByPrimaryKey(dbZxGcsgCtPriceSys);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtPriceSys);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxGcsgCtPriceSys(List<ZxGcsgCtPriceSys> zxGcsgCtPriceSysList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxGcsgCtPriceSysList != null && zxGcsgCtPriceSysList.size() > 0) {
			ZxGcsgCtPriceSys zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
			zxGcsgCtPriceSys.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtPriceSysMapper.batchDeleteUpdateZxGcsgCtPriceSys(zxGcsgCtPriceSysList, zxGcsgCtPriceSys);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCtPriceSysList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity psSyncHookDataZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		int flag = 0;
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// *原来的逻辑(旧系统有bug)
		// *获取清单工序挂接台账中的数据,处理后新增到单价分析表和单价分析明细表
		// *已经存在的分析表和分析明细表不做覆盖新增
		// *已经不存在的分析表和分析明细表数据需要删除
		// *单价分析表的合计金额需要重新计算
		if (zxGcsgCtPriceSys != null && StrUtil.isNotEmpty(zxGcsgCtPriceSys.getCtContrApplyId())) {
			// 一、获取清单工序挂接台账的数据
			List<ZxGcsgCtPriceSys> totalInsertPriceList = Lists.newArrayList();
			List<ZxGcsgCtPriceSysItem> totalInsertItemList = Lists.newArrayList();
			ZxGcsgCtContrApplyWorks applyWorks = new ZxGcsgCtContrApplyWorks();
			applyWorks.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
			List<Map<String, Object>> listProcessMapList = zxGcsgCtContrApplyWorksMapper
					.getZxGcsgCtContrApplyWorksListProcess(applyWorks);
			// 将清单工序台账进行整理
			if (listProcessMapList != null && listProcessMapList.size() > 0) {
				Map<String, List<Map<String, Object>>> listMap = new TreeMap<>();
				for (Map<String, Object> listProcessMap : listProcessMapList) {
					// 将清单id相同的工序合并
					if (listMap.containsKey(String.valueOf(listProcessMap.get("ctContrApplyWorksId")))) {
						List<Map<String, Object>> processList = listMap
								.get(String.valueOf(listProcessMap.get("ctContrApplyWorksId")));
						processList.add(listProcessMap);
						listMap.put(String.valueOf(listProcessMap.get("ctContrApplyWorksId")), processList);
					} else {
						List<Map<String, Object>> processList = Lists.newArrayList();
						processList.add(listProcessMap);
						listMap.put(String.valueOf(listProcessMap.get("ctContrApplyWorksId")), processList);
					}
				}
				if (listMap.size() > 0) {
					for (Map.Entry<String, List<Map<String, Object>>> listProcessMap : listMap.entrySet()) {
						List<Map<String, Object>> listProcessList = listProcessMap.getValue();
						if (listProcessList != null && listProcessList.size() > 0) {
							ZxGcsgCtPriceSys insertPrice = new ZxGcsgCtPriceSys();
							insertPrice.setCtPriceSysId(UuidUtil.generate());
							insertPrice.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
							insertPrice.setCtContractId("");
							insertPrice.setApplyAlterWorksId(
									String.valueOf(listProcessList.get(0).get("ctContrApplyWorksId")));
							insertPrice.setCcWorksId("");
							insertPrice.setWorkNo(String.valueOf(listProcessList.get(0).get("workNo")));
							insertPrice.setWorkName(String.valueOf(listProcessList.get(0).get("workName")));
							insertPrice.setWorkUnit(String.valueOf(listProcessList.get(0).get("unit")));
							insertPrice.setPriceNoTax(BigDecimal.ZERO);
							insertPrice.setBhPriceNoTax(BigDecimal.ZERO);
							insertPrice.setYzPriceNoTax(BigDecimal.ZERO);
							insertPrice.setDbPrice(BigDecimal.ZERO);
							insertPrice.setAmt1(BigDecimal.ZERO);
							insertPrice.setAmt2(BigDecimal.ZERO);
							insertPrice.setAmt3(BigDecimal.ZERO);
							insertPrice.setAmt4(BigDecimal.ZERO);
							insertPrice.setAmt5(BigDecimal.ZERO);
							insertPrice.setAmt6(BigDecimal.ZERO);
							insertPrice.setCreateUserInfo(userKey, realName);
							totalInsertPriceList.add(insertPrice);
							for (Map<String, Object> processMap : listProcessList) {
								ZxGcsgCtPriceSysItem insertItem = new ZxGcsgCtPriceSysItem();
								insertItem.setCtPriceSysItemId(UuidUtil.generate());
								insertItem.setCtPriceSysId(insertPrice.getCtPriceSysId());
								insertItem.setProcessID(String.valueOf(processMap.get("processID")));
								insertItem.setProcessNo(String.valueOf(processMap.get("processNo")));
								insertItem.setProcessName(String.valueOf(processMap.get("processName")));
								insertItem.setPrice(BigDecimal.ZERO);
								insertItem.setRgf(BigDecimal.ZERO);
								insertItem.setJxsb(BigDecimal.ZERO);
								insertItem.setZzcl(BigDecimal.ZERO);
								insertItem.setCreateUserInfo(userKey, realName);
								totalInsertItemList.add(insertItem);
							}
						}
					}
				}
			}
			// 二、获取数据库中单价分析表和单价分析明细表的数据
			// 根据合同评审id获取所有该合同的单价分析表数据
			ZxGcsgCtPriceSys priceSys = new ZxGcsgCtPriceSys();
			priceSys.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
			List<ZxGcsgCtPriceSys> dbPriceSysList = zxGcsgCtPriceSysMapper.selectByZxGcsgCtPriceSysList(priceSys);
			// 将数据库和新增的单价分析表进行对比
			// 1、获取需要删除的单价分析表数据进行级联删除分析表以及明细表
			// 2、获取需要新增的单价分析表进行新增
			// 3、获取需要过滤的单价分析表数据然后进一步对比明细表
			// 3.1获取需要删除的明细表进行删除重新核算单价分析表
			// 3.2获取需要过滤的明细表
			// 3.3获取新增的明细表
			if (dbPriceSysList.size() > 0 && totalInsertPriceList.size() > 0) {
				// 互相消除,消除的就是需要过滤的
				List<ZxGcsgCtPriceSys> filterPriceList = Lists.newArrayList();
				for (int i = 0; i < dbPriceSysList.size(); i++) {
					for (int j = 0; j < totalInsertPriceList.size(); j++) {
						if (StrUtil.equals(dbPriceSysList.get(i).getApplyAlterWorksId(),
								totalInsertPriceList.get(j).getApplyAlterWorksId())) {
							dbPriceSysList.get(i).setTempPrimaryKey(totalInsertPriceList.get(j).getCtPriceSysId());
							filterPriceList.add(dbPriceSysList.get(i));
							dbPriceSysList.remove(i);
							totalInsertPriceList.remove(j);
							i--;// j--;
							break;
						}
					}
				}
				// dbPriceSysList剩下的就是需要删除的(级联删除主表和子表)
				if (dbPriceSysList.size() > 0) {
					flag = zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItem(dbPriceSysList);
				}
				// totalInsertPriceList剩下的就是需要新增的(新增主表和字表)
				if (totalInsertPriceList.size() > 0) {
					// 新增主表
					flag = zxGcsgCtPriceSysMapper.batchInsertZxGcsgCtPriceSys(totalInsertPriceList);
					if (totalInsertItemList.size() > 0) {
						List<ZxGcsgCtPriceSysItem> insertItemList = Lists.newArrayList();
						for (ZxGcsgCtPriceSys insertPriceSys : totalInsertPriceList) {
							for (int i = 0; i < totalInsertItemList.size(); i++) {
								if (StrUtil.equals(insertPriceSys.getCtPriceSysId(),
										totalInsertItemList.get(i).getCtPriceSysId())) {
									insertItemList.add(totalInsertItemList.get(i));
									totalInsertItemList.remove(i);
									i--;
								}
							}
						}
						if (insertItemList.size() > 0) {
							// 新增子表
							flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
						}
					}
				}
				// 需要过滤的单价分析表(处理明细表)
				if (filterPriceList.size() > 0) {
					List<ZxGcsgCtPriceSysItem> finalInsertList = Lists.newArrayList();
					List<ZxGcsgCtPriceSysItem> finalDeleteList = Lists.newArrayList();
					List<ZxGcsgCtPriceSys> finalUpdateList = Lists.newArrayList();
					for (ZxGcsgCtPriceSys filterPrice : filterPriceList) {
						// 1、获取该单价分析表的db中明细集合
						ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem = new ZxGcsgCtPriceSysItem();
						zxGcsgCtPriceSysItem.setCtPriceSysId(filterPrice.getCtPriceSysId());
						List<ZxGcsgCtPriceSysItem> dbItemList = zxGcsgCtPriceSysItemMapper
								.selectByZxGcsgCtPriceSysItemList(zxGcsgCtPriceSysItem);
						/// 2、获取该单价分析表的需要新增的明细集合
						List<ZxGcsgCtPriceSysItem> insertItemList = Lists.newArrayList();
						if (totalInsertItemList.size() > 0) {
							for (int i = 0; i < totalInsertItemList.size(); i++) {
								// 注意id的变化
								if (StrUtil.equals(filterPrice.getTempPrimaryKey(),
										totalInsertItemList.get(i).getCtPriceSysId())) {
									totalInsertItemList.get(i).setCtPriceSysId(filterPrice.getCtPriceSysId());
									insertItemList.add(totalInsertItemList.get(i));
									totalInsertItemList.remove(i);
									i--;
								}
							}
						}
						if (dbItemList.size() > 0 && insertItemList.size() > 0) {
							// 相互消除,重复的工序忽略掉
							BigDecimal totalRgf = BigDecimal.ZERO;
							BigDecimal totalJxsb = BigDecimal.ZERO;
							BigDecimal totalZzcl = BigDecimal.ZERO;
							for (int i = 0; i < dbItemList.size(); i++) {
								for (int j = 0; j < insertItemList.size(); j++) {
									if (StrUtil.equals(dbItemList.get(i).getProcessID(),
											insertItemList.get(j).getProcessID())) {
										totalRgf = CalcUtils.calcAdd(totalRgf, dbItemList.get(i).getRgf());
										totalJxsb = CalcUtils.calcAdd(totalJxsb, dbItemList.get(i).getJxsb());
										totalZzcl = CalcUtils.calcAdd(totalZzcl, dbItemList.get(i).getZzcl());
										dbItemList.remove(i);
										insertItemList.remove(j);
										i--;
										break;
									}
								}
							}
							// dbItemList剩余的进行删除,重新计算分析表中金额
							if (dbItemList.size() > 0) {
								// flag =
								// zxGcsgCtPriceSysItemMapper.batchDeleteZxGcsgCtPriceSysItem(dbItemList);
								finalDeleteList.addAll(dbItemList);
								if (flag != 0) {
									filterPrice.setAmt4(totalRgf);
									filterPrice.setAmt5(totalZzcl);
									filterPrice.setAmt6(totalJxsb);
									BigDecimal priceNoTax = CalcUtils.calcAdd(filterPrice.getAmt6(), CalcUtils.calcAdd(
											filterPrice.getAmt5(),
											CalcUtils.calcAdd(filterPrice.getAmt4(), CalcUtils.calcAdd(
													filterPrice.getAmt3(),
													CalcUtils.calcAdd(filterPrice.getAmt1(), filterPrice.getAmt2())))));
									filterPrice.setPriceNoTax(priceNoTax);
									filterPrice.setDbPrice(
											CalcUtils.calcSubtract(filterPrice.getBhPriceNoTax(), priceNoTax));
									filterPrice.setModifyUserInfo(userKey, realName);
									// flag = zxGcsgCtPriceSysMapper.updateByPrimaryKey(filterPrice);
									finalUpdateList.add(filterPrice);
								}
							}
							// insertItemList剩余的进行新增
							if (insertItemList.size() > 0) {
								// flag =
								// zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
								finalInsertList.addAll(insertItemList);
							}
						} else if (dbItemList.size() == 0 && insertItemList.size() > 0) {
							// flag =
							// zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
							finalInsertList.addAll(insertItemList);
						} else if (dbItemList.size() > 0 && insertItemList.size() == 0) {
							// 正常没有这个情况
							finalDeleteList.addAll(dbItemList);
						}
					}
					if (finalInsertList.size() > 0) {
						flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(finalInsertList);
					}
					if (finalDeleteList.size() > 0) {
						flag = zxGcsgCtPriceSysItemMapper.batchDeleteZxGcsgCtPriceSysItem(finalDeleteList);
					}
					if (finalUpdateList.size() > 0) {
						flag = zxGcsgCtPriceSysMapper.batchUpdateZxGcsgCtPriceSys(finalUpdateList);
					}
				}
			} else if (dbPriceSysList.size() == 0 && totalInsertPriceList.size() > 0) {
				// 直接新增主表和字表
				flag = zxGcsgCtPriceSysMapper.batchInsertZxGcsgCtPriceSys(totalInsertPriceList);
				if (flag != 0) {
					flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(totalInsertItemList);
				}
			} else if (dbPriceSysList.size() > 0 && totalInsertPriceList.size() == 0) {
				// 级联删除
				ZxGcsgCtPriceSys delete = new ZxGcsgCtPriceSys();
				delete.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
				flag = zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItemByCondition(delete);
			}
		} else {
			return repEntity.layerMessage("no", "合同评审ID不能为空。");
		}
		return repEntity.ok("同步成功。");
	}

	@Override
	public ResponseEntity psglbxGetZxGcsgCtPriceSysDetails(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		if (zxGcsgCtPriceSys == null) {
			zxGcsgCtPriceSys = new ZxGcsgCtPriceSys();
		}
		// 获取数据
		ZxGcsgCtPriceSys dbZxGcsgCtPriceSys = zxGcsgCtPriceSysMapper
				.selectByPrimaryKey(zxGcsgCtPriceSys.getCtPriceSysId());
		if (dbZxGcsgCtPriceSys != null) {
			ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem = new ZxGcsgCtPriceSysItem();
			zxGcsgCtPriceSysItem.setCtPriceSysId(dbZxGcsgCtPriceSys.getCtPriceSysId());
			List<ZxGcsgCtPriceSysItem> list = zxGcsgCtPriceSysItemMapper
					.selectByZxGcsgCtPriceSysItemList(zxGcsgCtPriceSysItem);
			dbZxGcsgCtPriceSys.setCtPriceSysItemList(list);
		}
		// 数据存在
		if (dbZxGcsgCtPriceSys != null) {
			return repEntity.ok(dbZxGcsgCtPriceSys);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity psglbxUpdateZxGcsgCtPriceSysAndItem(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCtPriceSys dbZxGcsgCtPriceSys = zxGcsgCtPriceSysMapper
				.selectByPrimaryKey(zxGcsgCtPriceSys.getCtPriceSysId());
		if (dbZxGcsgCtPriceSys != null) {
			// 不含税标后预算单价/限价
			dbZxGcsgCtPriceSys.setBhPriceNoTax(zxGcsgCtPriceSys.getBhPriceNoTax());
			// 业主清单单价
			dbZxGcsgCtPriceSys.setYzPriceNoTax(zxGcsgCtPriceSys.getYzPriceNoTax());
			// 主材
			dbZxGcsgCtPriceSys.setAmt1(zxGcsgCtPriceSys.getAmt1());
			// 管理费
			dbZxGcsgCtPriceSys.setAmt2(zxGcsgCtPriceSys.getAmt2());
			// 利润
			dbZxGcsgCtPriceSys.setAmt3(zxGcsgCtPriceSys.getAmt3());
			if (zxGcsgCtPriceSys.getCtPriceSysItemList() == null
					|| zxGcsgCtPriceSys.getCtPriceSysItemList().size() < 1) {
				// 正常情况下不会出现这种情况
				// 人工费
				dbZxGcsgCtPriceSys.setAmt4(zxGcsgCtPriceSys.getAmt4());
				// 周转材料
				dbZxGcsgCtPriceSys.setAmt5(zxGcsgCtPriceSys.getAmt5());
				// 机械设备
				dbZxGcsgCtPriceSys.setAmt6(zxGcsgCtPriceSys.getAmt6());
				BigDecimal priceNoTax = CalcUtils.calcAdd(dbZxGcsgCtPriceSys.getAmt6(), CalcUtils.calcAdd(
						dbZxGcsgCtPriceSys.getAmt5(),
						CalcUtils.calcAdd(dbZxGcsgCtPriceSys.getAmt4(), CalcUtils.calcAdd(dbZxGcsgCtPriceSys.getAmt3(),
								CalcUtils.calcAdd(dbZxGcsgCtPriceSys.getAmt1(), dbZxGcsgCtPriceSys.getAmt2())))));
				// 不含税单价
				dbZxGcsgCtPriceSys.setPriceNoTax(priceNoTax);
				// 对比(标后预算/限价-不含税合价)
				dbZxGcsgCtPriceSys.setDbPrice(zxGcsgCtPriceSys.getDbPrice());
			} else {
				// 集合中前端需要把所有字段传过来(避免后台再去db查询一遍)
				BigDecimal totalRgf = BigDecimal.ZERO;
				BigDecimal totalZzcl = BigDecimal.ZERO;
				BigDecimal totalJxsb = BigDecimal.ZERO;
				for (ZxGcsgCtPriceSysItem dbPriceSysItem : zxGcsgCtPriceSys.getCtPriceSysItemList()) {
					if (StrUtil.isNotEmpty(dbPriceSysItem.getCtPriceSysItemId())) {
						dbPriceSysItem.setPrice(CalcUtils.calcAdd(dbPriceSysItem.getJxsb(),
								CalcUtils.calcAdd(dbPriceSysItem.getRgf(), dbPriceSysItem.getZzcl())));
						totalRgf = CalcUtils.calcAdd(totalRgf, dbPriceSysItem.getRgf());
						totalZzcl = CalcUtils.calcAdd(totalZzcl, dbPriceSysItem.getZzcl());
						totalJxsb = CalcUtils.calcAdd(totalJxsb, dbPriceSysItem.getJxsb());
					}
				}
				// flag = zxGcsgCtPriceSysItemMapper
				// .batchUpdateZxGcsgCtPriceSysItem(zxGcsgCtPriceSys.getCtPriceSysItemList());
				// 人工费
				dbZxGcsgCtPriceSys.setAmt4(totalRgf);
				// 周转材料
				dbZxGcsgCtPriceSys.setAmt5(totalZzcl);
				// 机械设备
				dbZxGcsgCtPriceSys.setAmt6(totalJxsb);
				BigDecimal priceNoTax = CalcUtils.calcAdd(totalJxsb,
						CalcUtils.calcAdd(totalZzcl, CalcUtils.calcAdd(totalRgf, CalcUtils.calcAdd(
								dbZxGcsgCtPriceSys.getAmt3(),
								CalcUtils.calcAdd(dbZxGcsgCtPriceSys.getAmt1(), dbZxGcsgCtPriceSys.getAmt2())))));

				// 不含税单价
				dbZxGcsgCtPriceSys.setPriceNoTax(priceNoTax);
				// 对比(标后预算/限价-不含税合价)
				dbZxGcsgCtPriceSys.setDbPrice(CalcUtils.calcSubtract(dbZxGcsgCtPriceSys.getBhPriceNoTax(), priceNoTax));
			}
			// 备注
			dbZxGcsgCtPriceSys.setRemarks(zxGcsgCtPriceSys.getRemarks());
			// 共通
			dbZxGcsgCtPriceSys.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCtPriceSysMapper.updateByPrimaryKey(dbZxGcsgCtPriceSys);
			if (flag != 0) {
				flag = zxGcsgCtPriceSysItemMapper
						.batchUpdateZxGcsgCtPriceSysItem(zxGcsgCtPriceSys.getCtPriceSysItemList());
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCtPriceSys);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity glSyncHookDataZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		int flag = 0;
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// *原来的逻辑(旧系统有bug)
		// *获取清单工序挂接台账中的数据,处理后新增到单价分析表和单价分析明细表
		// *已经存在的分析表和分析明细表不做覆盖新增
		// *已经不存在的分析表和分析明细表数据需要删除
		// *单价分析表的合计金额需要重新计算
		// *合同管理单价分析表的删除时注意特殊情况
		if (zxGcsgCtPriceSys != null && StrUtil.isNotEmpty(zxGcsgCtPriceSys.getCtContractId())) {
			// 一、获取合同管理清单工序挂接台账的数据
			List<ZxGcsgCtPriceSys> totalInsertPriceList = Lists.newArrayList();
			List<ZxGcsgCtPriceSysItem> totalInsertItemList = Lists.newArrayList();
			ZxGcsgCcWorks ccWorks = new ZxGcsgCcWorks();
			ccWorks.setCtContractId(zxGcsgCtPriceSys.getCtContractId());
			List<Map<String, Object>> listProcessMapList = zxGcsgCcWorksMapper.getZxGcsgCcWorksListProcess(ccWorks);
			// 将清单工序台账进行整理
			if (listProcessMapList != null && listProcessMapList.size() > 0) {
				Map<String, List<Map<String, Object>>> listMap = new TreeMap<>();
				for (Map<String, Object> listProcessMap : listProcessMapList) {
					// 将清单id相同的工序合并
					if (listMap.containsKey(String.valueOf(listProcessMap.get("ccWorksId")))) {
						List<Map<String, Object>> processList = listMap
								.get(String.valueOf(listProcessMap.get("ccWorksId")));
						processList.add(listProcessMap);
						listMap.put(String.valueOf(listProcessMap.get("ccWorksId")), processList);
					} else {
						List<Map<String, Object>> processList = Lists.newArrayList();
						processList.add(listProcessMap);
						listMap.put(String.valueOf(listProcessMap.get("ccWorksId")), processList);
					}
				}
				if (listMap.size() > 0) {
					for (Map.Entry<String, List<Map<String, Object>>> listProcessMap : listMap.entrySet()) {
						List<Map<String, Object>> listProcessList = listProcessMap.getValue();
						if (listProcessList != null && listProcessList.size() > 0) {
							ZxGcsgCtPriceSys insertPrice = new ZxGcsgCtPriceSys();
							insertPrice.setCtPriceSysId(UuidUtil.generate());
							insertPrice.setCtContrApplyId("");
							insertPrice.setCtContractId(zxGcsgCtPriceSys.getCtContractId());
							insertPrice.setApplyAlterWorksId("");
							insertPrice.setCcWorksId(String.valueOf(listProcessList.get(0).get("ccWorksId")));
							insertPrice.setWorkNo(String.valueOf(listProcessList.get(0).get("workNo")));
							insertPrice.setWorkName(String.valueOf(listProcessList.get(0).get("workName")));
							insertPrice.setWorkUnit(String.valueOf(listProcessList.get(0).get("unit")));
							insertPrice.setPriceNoTax(BigDecimal.ZERO);
							insertPrice.setBhPriceNoTax(BigDecimal.ZERO);
							insertPrice.setYzPriceNoTax(BigDecimal.ZERO);
							insertPrice.setDbPrice(BigDecimal.ZERO);
							insertPrice.setAmt1(BigDecimal.ZERO);
							insertPrice.setAmt2(BigDecimal.ZERO);
							insertPrice.setAmt3(BigDecimal.ZERO);
							insertPrice.setAmt4(BigDecimal.ZERO);
							insertPrice.setAmt5(BigDecimal.ZERO);
							insertPrice.setAmt6(BigDecimal.ZERO);
							insertPrice.setCreateUserInfo(userKey, realName);
							totalInsertPriceList.add(insertPrice);
							for (Map<String, Object> processMap : listProcessList) {
								ZxGcsgCtPriceSysItem insertItem = new ZxGcsgCtPriceSysItem();
								insertItem.setCtPriceSysItemId(UuidUtil.generate());
								insertItem.setCtPriceSysId(insertPrice.getCtPriceSysId());
								insertItem.setProcessID(String.valueOf(processMap.get("processID")));
								insertItem.setProcessNo(String.valueOf(processMap.get("processNo")));
								insertItem.setProcessName(String.valueOf(processMap.get("processName")));
								insertItem.setPrice(BigDecimal.ZERO);
								insertItem.setRgf(BigDecimal.ZERO);
								insertItem.setJxsb(BigDecimal.ZERO);
								insertItem.setZzcl(BigDecimal.ZERO);
								insertItem.setCreateUserInfo(userKey, realName);
								totalInsertItemList.add(insertItem);
							}
						}
					}
				}
			}
			// 二、获取数据库中单价分析表和单价分析明细表的数据
			// 根据合同管理id获取所有该合同的单价分析表数据
			ZxGcsgCtPriceSys priceSys = new ZxGcsgCtPriceSys();
			priceSys.setCtContractId(zxGcsgCtPriceSys.getCtContractId());
			List<ZxGcsgCtPriceSys> dbPriceSysList = zxGcsgCtPriceSysMapper.selectByZxGcsgCtPriceSysList(priceSys);
			// 将数据库和新增的单价分析表进行对比
			// 1、获取需要删除的单价分析表数据进行级联删除分析表以及明细表
			// 2、获取需要新增的单价分析表进行新增
			// 3、获取需要过滤的单价分析表数据然后进一步对比明细表
			// 3.1获取需要删除的明细表进行删除重新核算单价分析表
			// 3.2获取需要过滤的明细表
			// 3.3获取新增的明细表
			if (dbPriceSysList.size() > 0 && totalInsertPriceList.size() > 0) {
				// 互相消除,消除的就是需要过滤的
				List<ZxGcsgCtPriceSys> filterPriceList = Lists.newArrayList();
				for (int i = 0; i < dbPriceSysList.size(); i++) {
					for (int j = 0; j < totalInsertPriceList.size(); j++) {
						if (StrUtil.equals(dbPriceSysList.get(i).getCcWorksId(),
								totalInsertPriceList.get(j).getCcWorksId())) {
							dbPriceSysList.get(i).setTempPrimaryKey(totalInsertPriceList.get(j).getCtPriceSysId());
							filterPriceList.add(dbPriceSysList.get(i));
							dbPriceSysList.remove(i);
							totalInsertPriceList.remove(j);
							i--;// j--;
							break;
						}
					}
				}
				// dbPriceSysList剩下的就是需要删除的(级联删除主表和子表)
				if (dbPriceSysList.size() > 0) {
					flag = zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItem(dbPriceSysList);
				}
				// totalInsertPriceList剩下的就是需要新增的(新增主表和字表)
				if (totalInsertPriceList.size() > 0) {
					// 新增主表
					flag = zxGcsgCtPriceSysMapper.batchInsertZxGcsgCtPriceSys(totalInsertPriceList);
					if (totalInsertItemList.size() > 0) {
						List<ZxGcsgCtPriceSysItem> insertItemList = Lists.newArrayList();
						for (ZxGcsgCtPriceSys insertPriceSys : totalInsertPriceList) {
							for (int i = 0; i < totalInsertItemList.size(); i++) {
								if (StrUtil.equals(insertPriceSys.getCtPriceSysId(),
										totalInsertItemList.get(i).getCtPriceSysId())) {
									insertItemList.add(totalInsertItemList.get(i));
									totalInsertItemList.remove(i);
									i--;
								}
							}
						}
						if (insertItemList.size() > 0) {
							// 新增子表
							flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
						}
					}
				}
				// 需要过滤的单价分析表(处理明细表)
				if (filterPriceList.size() > 0) {
					List<ZxGcsgCtPriceSysItem> finalInsertList = Lists.newArrayList();
					List<ZxGcsgCtPriceSysItem> finalDeleteList = Lists.newArrayList();
					List<ZxGcsgCtPriceSys> finalUpdateList = Lists.newArrayList();
					for (ZxGcsgCtPriceSys filterPrice : filterPriceList) {
						// 1、获取该单价分析表的db中明细集合
						ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem = new ZxGcsgCtPriceSysItem();
						zxGcsgCtPriceSysItem.setCtPriceSysId(filterPrice.getCtPriceSysId());
						List<ZxGcsgCtPriceSysItem> dbItemList = zxGcsgCtPriceSysItemMapper
								.selectByZxGcsgCtPriceSysItemList(zxGcsgCtPriceSysItem);
						/// 2、获取该单价分析表的需要新增的明细集合
						List<ZxGcsgCtPriceSysItem> insertItemList = Lists.newArrayList();
						if (totalInsertItemList.size() > 0) {
							for (int i = 0; i < totalInsertItemList.size(); i++) {
								// 注意id的变化
								if (StrUtil.equals(filterPrice.getTempPrimaryKey(),
										totalInsertItemList.get(i).getCtPriceSysId())) {
									totalInsertItemList.get(i).setCtPriceSysId(filterPrice.getCtPriceSysId());
									insertItemList.add(totalInsertItemList.get(i));
									totalInsertItemList.remove(i);
									i--;
								}
							}
						}
						if (dbItemList.size() > 0 && insertItemList.size() > 0) {
							// 相互消除,重复的工序忽略掉
							BigDecimal totalRgf = BigDecimal.ZERO;
							BigDecimal totalJxsb = BigDecimal.ZERO;
							BigDecimal totalZzcl = BigDecimal.ZERO;
							for (int i = 0; i < dbItemList.size(); i++) {
								for (int j = 0; j < insertItemList.size(); j++) {
									if (StrUtil.equals(dbItemList.get(i).getProcessID(),
											insertItemList.get(j).getProcessID())) {
										totalRgf = CalcUtils.calcAdd(totalRgf, dbItemList.get(i).getRgf());
										totalJxsb = CalcUtils.calcAdd(totalJxsb, dbItemList.get(i).getJxsb());
										totalZzcl = CalcUtils.calcAdd(totalZzcl, dbItemList.get(i).getZzcl());
										dbItemList.remove(i);
										insertItemList.remove(j);
										i--;
										break;
									}
								}
							}
							// dbItemList剩余的进行删除,重新计算分析表中金额
							if (dbItemList.size() > 0) {
								// flag =
								// zxGcsgCtPriceSysItemMapper.batchDeleteZxGcsgCtPriceSysItem(dbItemList);
								finalDeleteList.addAll(dbItemList);
								if (flag != 0) {
									filterPrice.setAmt4(totalRgf);
									filterPrice.setAmt5(totalZzcl);
									filterPrice.setAmt6(totalJxsb);
									BigDecimal priceNoTax = CalcUtils.calcAdd(filterPrice.getAmt6(), CalcUtils.calcAdd(
											filterPrice.getAmt5(),
											CalcUtils.calcAdd(filterPrice.getAmt4(), CalcUtils.calcAdd(
													filterPrice.getAmt3(),
													CalcUtils.calcAdd(filterPrice.getAmt1(), filterPrice.getAmt2())))));
									filterPrice.setPriceNoTax(priceNoTax);
									filterPrice.setDbPrice(
											CalcUtils.calcSubtract(filterPrice.getBhPriceNoTax(), priceNoTax));
									filterPrice.setModifyUserInfo(userKey, realName);
									// flag = zxGcsgCtPriceSysMapper.updateByPrimaryKey(filterPrice);
									finalUpdateList.add(filterPrice);
								}
							}
							// insertItemList剩余的进行新增
							if (insertItemList.size() > 0) {
								// flag =
								// zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
								finalInsertList.addAll(insertItemList);
							}
						} else if (dbItemList.size() == 0 && insertItemList.size() > 0) {
							// flag =
							// zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
							finalInsertList.addAll(insertItemList);
						} else if (dbItemList.size() > 0 && insertItemList.size() == 0) {
							// 正常没有这个情况
							finalDeleteList.addAll(dbItemList);
						}
					}
					if (finalInsertList.size() > 0) {
						flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(finalInsertList);
					}
					if (finalDeleteList.size() > 0) {
						flag = zxGcsgCtPriceSysItemMapper.batchDeleteZxGcsgCtPriceSysItem(finalDeleteList);
					}
					if (finalUpdateList.size() > 0) {
						flag = zxGcsgCtPriceSysMapper.batchUpdateZxGcsgCtPriceSys(finalUpdateList);
					}

				}
			} else if (dbPriceSysList.size() == 0 && totalInsertPriceList.size() > 0) {
				// 直接新增主表和字表
				flag = zxGcsgCtPriceSysMapper.batchInsertZxGcsgCtPriceSys(totalInsertPriceList);
				if (flag != 0) {
					flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(totalInsertItemList);
				}
			} else if (dbPriceSysList.size() > 0 && totalInsertPriceList.size() == 0) {
				// 级联删除
				ZxGcsgCtPriceSys delete = new ZxGcsgCtPriceSys();
				delete.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
				flag = zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItemByCondition(delete);
			}
		} else {
			return repEntity.layerMessage("no", "合同管理ID不能为空。");
		}
		return repEntity.ok("同步成功。");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity bxSyncHookDataZxGcsgCtPriceSys(ZxGcsgCtPriceSys zxGcsgCtPriceSys) {
		int flag = 0;
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		// *原来的逻辑(旧系统有bug)
		// *获取清单工序挂接台账中的数据,处理后新增到单价分析表和单价分析明细表
		// *已经存在的分析表和分析明细表不做覆盖新增
		// *已经不存在的分析表和分析明细表数据需要删除
		// *单价分析表的合计金额需要重新计算
		if (zxGcsgCtPriceSys != null && StrUtil.isNotEmpty(zxGcsgCtPriceSys.getCtContrApplyId())) {
			// 一、获取补充协议清单工序挂接台账的数据
			List<ZxGcsgCtPriceSys> totalInsertPriceList = Lists.newArrayList();
			List<ZxGcsgCtPriceSysItem> totalInsertItemList = Lists.newArrayList();
			ZxGcsgCcCoAlterWork alterWork = new ZxGcsgCcCoAlterWork();
			alterWork.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
			List<Map<String, Object>> listProcessMapList = zxGcsgCcCoAlterWorkMapper
					.getZxGcsgCcCoAlterWorkListProcess(alterWork);
			// 将清单工序台账进行整理
			if (listProcessMapList != null && listProcessMapList.size() > 0) {
				Map<String, List<Map<String, Object>>> listMap = new TreeMap<>();
				for (Map<String, Object> listProcessMap : listProcessMapList) {
					// 将清单id相同的工序合并
					if (listMap.containsKey(String.valueOf(listProcessMap.get("ccCoAlterWorkId")))) {
						List<Map<String, Object>> processList = listMap
								.get(String.valueOf(listProcessMap.get("ccCoAlterWorkId")));
						processList.add(listProcessMap);
						listMap.put(String.valueOf(listProcessMap.get("ccCoAlterWorkId")), processList);
					} else {
						List<Map<String, Object>> processList = Lists.newArrayList();
						processList.add(listProcessMap);
						listMap.put(String.valueOf(listProcessMap.get("ccCoAlterWorkId")), processList);
					}
				}
				if (listMap.size() > 0) {
					for (Map.Entry<String, List<Map<String, Object>>> listProcessMap : listMap.entrySet()) {
						List<Map<String, Object>> listProcessList = listProcessMap.getValue();
						if (listProcessList != null && listProcessList.size() > 0) {
							ZxGcsgCtPriceSys insertPrice = new ZxGcsgCtPriceSys();
							insertPrice.setCtPriceSysId(UuidUtil.generate());
							insertPrice.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
							insertPrice.setCtContractId("");
							insertPrice.setApplyAlterWorksId(
									String.valueOf(listProcessList.get(0).get("ccCoAlterWorkId")));
							insertPrice.setCcWorksId("");
							insertPrice.setWorkNo(String.valueOf(listProcessList.get(0).get("workNo")));
							insertPrice.setWorkName(String.valueOf(listProcessList.get(0).get("workName")));
							insertPrice.setWorkUnit(String.valueOf(listProcessList.get(0).get("unit")));
							insertPrice.setPriceNoTax(BigDecimal.ZERO);
							insertPrice.setBhPriceNoTax(BigDecimal.ZERO);
							insertPrice.setYzPriceNoTax(BigDecimal.ZERO);
							insertPrice.setDbPrice(BigDecimal.ZERO);
							insertPrice.setAmt1(BigDecimal.ZERO);
							insertPrice.setAmt2(BigDecimal.ZERO);
							insertPrice.setAmt3(BigDecimal.ZERO);
							insertPrice.setAmt4(BigDecimal.ZERO);
							insertPrice.setAmt5(BigDecimal.ZERO);
							insertPrice.setAmt6(BigDecimal.ZERO);
							insertPrice.setCreateUserInfo(userKey, realName);
							totalInsertPriceList.add(insertPrice);
							for (Map<String, Object> processMap : listProcessList) {
								ZxGcsgCtPriceSysItem insertItem = new ZxGcsgCtPriceSysItem();
								insertItem.setCtPriceSysItemId(UuidUtil.generate());
								insertItem.setCtPriceSysId(insertPrice.getCtPriceSysId());
								insertItem.setProcessID(String.valueOf(processMap.get("processID")));
								insertItem.setProcessNo(String.valueOf(processMap.get("processNo")));
								insertItem.setProcessName(String.valueOf(processMap.get("processName")));
								insertItem.setPrice(BigDecimal.ZERO);
								insertItem.setRgf(BigDecimal.ZERO);
								insertItem.setJxsb(BigDecimal.ZERO);
								insertItem.setZzcl(BigDecimal.ZERO);
								insertItem.setCreateUserInfo(userKey, realName);
								totalInsertItemList.add(insertItem);
							}
						}
					}
				}
			}
			// 二、获取数据库中单价分析表和单价分析明细表的数据
			// 根据补充协议id获取所有该补充协议的单价分析表数据
			ZxGcsgCtPriceSys priceSys = new ZxGcsgCtPriceSys();
			priceSys.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
			List<ZxGcsgCtPriceSys> dbPriceSysList = zxGcsgCtPriceSysMapper.selectByZxGcsgCtPriceSysList(priceSys);
			// 将数据库和新增的单价分析表进行对比
			// 1、获取需要删除的单价分析表数据进行级联删除分析表以及明细表
			// 2、获取需要新增的单价分析表进行新增
			// 3、获取需要过滤的单价分析表数据然后进一步对比明细表
			// 3.1获取需要删除的明细表进行删除重新核算单价分析表
			// 3.2获取需要过滤的明细表
			// 3.3获取新增的明细表
			if (dbPriceSysList.size() > 0 && totalInsertPriceList.size() > 0) {
				// 互相消除,消除的就是需要过滤的
				List<ZxGcsgCtPriceSys> filterPriceList = Lists.newArrayList();
				for (int i = 0; i < dbPriceSysList.size(); i++) {
					for (int j = 0; j < totalInsertPriceList.size(); j++) {
						if (StrUtil.equals(dbPriceSysList.get(i).getApplyAlterWorksId(),
								totalInsertPriceList.get(j).getApplyAlterWorksId())) {
							dbPriceSysList.get(i).setTempPrimaryKey(totalInsertPriceList.get(j).getCtPriceSysId());
							filterPriceList.add(dbPriceSysList.get(i));
							dbPriceSysList.remove(i);
							totalInsertPriceList.remove(j);
							i--;// j--;
							break;
						}
					}
				}
				// dbPriceSysList剩下的就是需要删除的(级联删除主表和子表)
				if (dbPriceSysList.size() > 0) {
					flag = zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItem(dbPriceSysList);
				}
				// totalInsertPriceList剩下的就是需要新增的(新增主表和字表)
				if (totalInsertPriceList.size() > 0) {
					// 新增主表
					flag = zxGcsgCtPriceSysMapper.batchInsertZxGcsgCtPriceSys(totalInsertPriceList);
					if (totalInsertItemList.size() > 0) {
						List<ZxGcsgCtPriceSysItem> insertItemList = Lists.newArrayList();
						for (ZxGcsgCtPriceSys insertPriceSys : totalInsertPriceList) {
							for (int i = 0; i < totalInsertItemList.size(); i++) {
								if (StrUtil.equals(insertPriceSys.getCtPriceSysId(),
										totalInsertItemList.get(i).getCtPriceSysId())) {
									insertItemList.add(totalInsertItemList.get(i));
									totalInsertItemList.remove(i);
									i--;
								}
							}
						}
						if (insertItemList.size() > 0) {
							// 新增子表
							flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
						}
					}
				}
				// 需要过滤的单价分析表(处理明细表)
				if (filterPriceList.size() > 0) {
					List<ZxGcsgCtPriceSysItem> finalInsertList = Lists.newArrayList();
					List<ZxGcsgCtPriceSysItem> finalDeleteList = Lists.newArrayList();
					List<ZxGcsgCtPriceSys> finalUpdateList = Lists.newArrayList();
					for (ZxGcsgCtPriceSys filterPrice : filterPriceList) {
						// 1、获取该单价分析表的db中明细集合
						ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem = new ZxGcsgCtPriceSysItem();
						zxGcsgCtPriceSysItem.setCtPriceSysId(filterPrice.getCtPriceSysId());
						List<ZxGcsgCtPriceSysItem> dbItemList = zxGcsgCtPriceSysItemMapper
								.selectByZxGcsgCtPriceSysItemList(zxGcsgCtPriceSysItem);
						/// 2、获取该单价分析表的需要新增的明细集合
						List<ZxGcsgCtPriceSysItem> insertItemList = Lists.newArrayList();
						if (totalInsertItemList.size() > 0) {
							for (int i = 0; i < totalInsertItemList.size(); i++) {
								// 注意id的变化
								if (StrUtil.equals(filterPrice.getTempPrimaryKey(),
										totalInsertItemList.get(i).getCtPriceSysId())) {
									totalInsertItemList.get(i).setCtPriceSysId(filterPrice.getCtPriceSysId());
									insertItemList.add(totalInsertItemList.get(i));
									totalInsertItemList.remove(i);
									i--;
								}
							}
						}
						if (dbItemList.size() > 0 && insertItemList.size() > 0) {
							// 相互消除,重复的工序忽略掉
							BigDecimal totalRgf = BigDecimal.ZERO;
							BigDecimal totalJxsb = BigDecimal.ZERO;
							BigDecimal totalZzcl = BigDecimal.ZERO;
							for (int i = 0; i < dbItemList.size(); i++) {
								for (int j = 0; j < insertItemList.size(); j++) {
									if (StrUtil.equals(dbItemList.get(i).getProcessID(),
											insertItemList.get(j).getProcessID())) {
										totalRgf = CalcUtils.calcAdd(totalRgf, dbItemList.get(i).getRgf());
										totalJxsb = CalcUtils.calcAdd(totalJxsb, dbItemList.get(i).getJxsb());
										totalZzcl = CalcUtils.calcAdd(totalZzcl, dbItemList.get(i).getZzcl());
										dbItemList.remove(i);
										insertItemList.remove(j);
										i--;
										break;
									}
								}
							}
							// dbItemList剩余的进行删除,重新计算分析表中金额
							if (dbItemList.size() > 0) {
								// flag =
								// zxGcsgCtPriceSysItemMapper.batchDeleteZxGcsgCtPriceSysItem(dbItemList);
								finalDeleteList.addAll(dbItemList);
								if (flag != 0) {
									filterPrice.setAmt4(totalRgf);
									filterPrice.setAmt5(totalZzcl);
									filterPrice.setAmt6(totalJxsb);
									BigDecimal priceNoTax = CalcUtils.calcAdd(filterPrice.getAmt6(), CalcUtils.calcAdd(
											filterPrice.getAmt5(),
											CalcUtils.calcAdd(filterPrice.getAmt4(), CalcUtils.calcAdd(
													filterPrice.getAmt3(),
													CalcUtils.calcAdd(filterPrice.getAmt1(), filterPrice.getAmt2())))));
									filterPrice.setPriceNoTax(priceNoTax);
									filterPrice.setDbPrice(
											CalcUtils.calcSubtract(filterPrice.getBhPriceNoTax(), priceNoTax));
									filterPrice.setModifyUserInfo(userKey, realName);
									// flag = zxGcsgCtPriceSysMapper.updateByPrimaryKey(filterPrice);
									finalUpdateList.add(filterPrice);
								}
							}
							// insertItemList剩余的进行新增
							if (insertItemList.size() > 0) {
								// flag =
								// zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
								finalInsertList.addAll(insertItemList);
							}
						} else if (dbItemList.size() == 0 && insertItemList.size() > 0) {
							// flag =
							// zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(insertItemList);
							finalInsertList.addAll(insertItemList);
						} else if (dbItemList.size() > 0 && insertItemList.size() == 0) {
							// 正常没有这个情况
							finalDeleteList.addAll(dbItemList);
						}
					}
					if (finalInsertList.size() > 0) {
						flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(finalInsertList);
					}
					if (finalDeleteList.size() > 0) {
						flag = zxGcsgCtPriceSysItemMapper.batchDeleteZxGcsgCtPriceSysItem(finalDeleteList);
					}
					if (finalUpdateList.size() > 0) {
						flag = zxGcsgCtPriceSysMapper.batchUpdateZxGcsgCtPriceSys(finalUpdateList);
					}
				}
			} else if (dbPriceSysList.size() == 0 && totalInsertPriceList.size() > 0) {
				// 直接新增主表和字表
				flag = zxGcsgCtPriceSysMapper.batchInsertZxGcsgCtPriceSys(totalInsertPriceList);
				if (flag != 0) {
					flag = zxGcsgCtPriceSysItemMapper.batchInsertZxGcsgCtPriceSysItem(totalInsertItemList);
				}
			} else if (dbPriceSysList.size() > 0 && totalInsertPriceList.size() == 0) {
				// 级联删除
				ZxGcsgCtPriceSys delete = new ZxGcsgCtPriceSys();
				delete.setCtContrApplyId(zxGcsgCtPriceSys.getCtContrApplyId());
				flag = zxGcsgCtPriceSysMapper.cascadeDeleteZxGcsgCtPriceSysAndItemByCondition(delete);
			}
		} else {
			return repEntity.layerMessage("no", "补充协议ID不能为空。");
		}
		return repEntity.ok("同步成功。");
	}

}
