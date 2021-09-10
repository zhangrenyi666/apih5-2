package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectSettleAuditMapper;
import com.apih5.mybatis.pojo.ZxGcsgCtCoContractAmtRate;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAudit;
import com.apih5.mybatis.pojo.ZxSaProjectSettleAuditItem;
import com.apih5.service.ZxGcsgCtCoContractAmtRateService;
import com.apih5.service.ZxSaProjectSettleAuditItemService;
import com.apih5.utils.DigitalConversionUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxSaProjectSettleAuditItemService")
public class ZxSaProjectSettleAuditItemServiceImpl implements ZxSaProjectSettleAuditItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectSettleAuditItemMapper zxSaProjectSettleAuditItemMapper;
    
    @Autowired(required = true)
    private ZxSaProjectSettleAuditServiceImpl zxSaProjectSettleAuditServiceImpl;
    
    @Autowired(required = true)
    private ZxGcsgCtCoContractAmtRateService zxGcsgCtCoContractAmtRateService;
    
    @Autowired(required = true)
    private ZxSaProjectSettleAuditMapper zxSaProjectSettleAuditMapper;
    
    @ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
	private Config publicConfig;

    @Override
    public ResponseEntity getZxSaProjectSettleAuditItemListByCondition(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        if (zxSaProjectSettleAuditItem == null) {
            zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
        }
        // 分页查询
        PageHelper.startPage(zxSaProjectSettleAuditItem.getPage(),zxSaProjectSettleAuditItem.getLimit());
        // 获取数据
        List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(zxSaProjectSettleAuditItem);
        // 得到分页信息
        PageInfo<ZxSaProjectSettleAuditItem> p = new PageInfo<>(zxSaProjectSettleAuditItemList);

        for (ZxSaProjectSettleAuditItem settleAuditItem : zxSaProjectSettleAuditItemList) {
			if (StrUtil.equals(settleAuditItem.getStatisticsType(), "100600")) {
				settleAuditItem.setSort(2);
			}
		}
        
        return repEntity.okList(zxSaProjectSettleAuditItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaProjectSettleAuditItemDetail(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        if (zxSaProjectSettleAuditItem == null) {
            zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
        }
        // 获取数据
        ZxSaProjectSettleAuditItem dbZxSaProjectSettleAuditItem = zxSaProjectSettleAuditItemMapper.selectByPrimaryKey(zxSaProjectSettleAuditItem.getProjectSettleAuditItemId());
        // 数据存在
        if (dbZxSaProjectSettleAuditItem != null) {
            return repEntity.ok(dbZxSaProjectSettleAuditItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaProjectSettleAuditItem(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectSettleAuditItem.setProjectSettleAuditItemId(UuidUtil.generate());
        zxSaProjectSettleAuditItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaProjectSettleAuditItemMapper.insert(zxSaProjectSettleAuditItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaProjectSettleAuditItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaProjectSettleAuditItem(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaProjectSettleAuditItem dbZxSaProjectSettleAuditItem = zxSaProjectSettleAuditItemMapper.selectByPrimaryKey(zxSaProjectSettleAuditItem.getProjectSettleAuditItemId());
        if (dbZxSaProjectSettleAuditItem != null && StrUtil.isNotEmpty(dbZxSaProjectSettleAuditItem.getProjectSettleAuditItemId())) {
           // 结算单主键ID
           dbZxSaProjectSettleAuditItem.setProjectSettleAuditId(zxSaProjectSettleAuditItem.getProjectSettleAuditId());
           // 项目ID
           dbZxSaProjectSettleAuditItem.setOrgID(zxSaProjectSettleAuditItem.getOrgID());
           // 合同ID
           dbZxSaProjectSettleAuditItem.setContractID(zxSaProjectSettleAuditItem.getContractID());
           // 期次
           dbZxSaProjectSettleAuditItem.setPeriod(zxSaProjectSettleAuditItem.getPeriod());
           // 统计项编号
           dbZxSaProjectSettleAuditItem.setStatisticsNo(zxSaProjectSettleAuditItem.getStatisticsNo());
           // 统计项
           dbZxSaProjectSettleAuditItem.setStatisticsName(zxSaProjectSettleAuditItem.getStatisticsName());
           // 本期(元)
           dbZxSaProjectSettleAuditItem.setThisAmt(zxSaProjectSettleAuditItem.getThisAmt());
           // 开累(元)
           dbZxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(dbZxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(StrUtil.isEmpty(zxSaProjectSettleAuditItem.getThisAmt()) ? "0" : zxSaProjectSettleAuditItem.getThisAmt())) + "");
           // 最后编辑时间
           dbZxSaProjectSettleAuditItem.setEditTime(zxSaProjectSettleAuditItem.getEditTime());
           // 比例
           dbZxSaProjectSettleAuditItem.setRate(zxSaProjectSettleAuditItem.getRate());
           // 统计项ID
           dbZxSaProjectSettleAuditItem.setStatisticsID(zxSaProjectSettleAuditItem.getStatisticsID());
           // 统计项类型
           dbZxSaProjectSettleAuditItem.setStatisticsType(zxSaProjectSettleAuditItem.getStatisticsType());
           // 上期金额
//           dbZxSaProjectSettleAuditItem.setUpAmt(zxSaProjectSettleAuditItem.getUpAmt());
           // 所属公司ID
           dbZxSaProjectSettleAuditItem.setComID(zxSaProjectSettleAuditItem.getComID());
           // 所属公司
           dbZxSaProjectSettleAuditItem.setComName(zxSaProjectSettleAuditItem.getComName());
           // 所属公司排序
           dbZxSaProjectSettleAuditItem.setComOrders(zxSaProjectSettleAuditItem.getComOrders());
           // 财务系统ID
           dbZxSaProjectSettleAuditItem.setFiID(zxSaProjectSettleAuditItem.getFiID());
           // 排序
           dbZxSaProjectSettleAuditItem.setSort(zxSaProjectSettleAuditItem.getSort());
           // 备注
           dbZxSaProjectSettleAuditItem.setRemark(zxSaProjectSettleAuditItem.getRemark());
           // 共通
           dbZxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(dbZxSaProjectSettleAuditItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
        	
        	// 修改返还保证金合计及应付工程款（大小写）
        	ZxSaProjectSettleAuditItem selectItem = new ZxSaProjectSettleAuditItem();
        	selectItem.setProjectSettleAuditId(dbZxSaProjectSettleAuditItem.getProjectSettleAuditId());
        	List<ZxSaProjectSettleAuditItem> settleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(selectItem);
        	if (settleAuditItemList != null && settleAuditItemList.size() > 0) {
				BigDecimal thisAmt = null, totalThisAmt = null, deductionAmt = null, totalDeductionAmt = null; // 合计含税金额、扣除保证金合计金额
				BigDecimal returnAmt = new BigDecimal(0); // 返回保证金合计金额
				BigDecimal totalReturnAmt = new BigDecimal(0); // 返回保证金合计金额
				List<ZxSaProjectSettleAuditItem> updateItemList = new ArrayList<>(); // 待修改项(返还保证金合计、应付工程款大小写)
        		for (ZxSaProjectSettleAuditItem auditItem : settleAuditItemList) {
					if (StrUtil.equals("100100", auditItem.getStatisticsType())) {
						thisAmt = new BigDecimal(auditItem.getThisAmt());
						totalThisAmt = new BigDecimal(auditItem.getTotalAmt());
					} else if(StrUtil.equals("100300", auditItem.getStatisticsType())) {
						deductionAmt = new BigDecimal(auditItem.getThisAmt());
						totalDeductionAmt = new BigDecimal(auditItem.getTotalAmt());
					} else if(StrUtil.equals("100600", auditItem.getStatisticsType())) {
						returnAmt = CalcUtils.calcAdd(returnAmt, new BigDecimal(auditItem.getThisAmt()));
						totalReturnAmt = CalcUtils.calcAdd(totalReturnAmt, new BigDecimal(auditItem.getTotalAmt()));
					} else if(StrUtil.equalsAny(auditItem.getStatisticsType(), "100500", "100700", "100800")) {
						updateItemList.add(auditItem);
					}
				}
        		for (ZxSaProjectSettleAuditItem updateAuditItem : updateItemList) {
					if (StrUtil.equals("100500", updateAuditItem.getStatisticsType())) {
						updateAuditItem.setThisAmt(returnAmt + "");
						updateAuditItem.setTotalAmt(totalReturnAmt + "");
					} else if (StrUtil.equals("100700", updateAuditItem.getStatisticsType())) {
						updateAuditItem.setThisAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt, deductionAmt), returnAmt) + "");
						updateAuditItem.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalThisAmt, totalDeductionAmt), totalReturnAmt) + "");
						dbZxSaProjectSettleAudit.setId(updateAuditItem.getProjectSettleAuditId());
						dbZxSaProjectSettleAudit.setThisPayAmt(new BigDecimal(updateAuditItem.getThisAmt()));
			        	dbZxSaProjectSettleAudit.setTotalPayAmt(new BigDecimal(updateAuditItem.getTotalAmt()));
					} else if (StrUtil.equals("100800", updateAuditItem.getStatisticsType())) {
						updateAuditItem.setThisAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt, deductionAmt), returnAmt)));
						updateAuditItem.setTotalAmt(DigitalConversionUtil.digitUppercase(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalThisAmt, totalDeductionAmt), totalReturnAmt)));
					}
					updateAuditItem.setModifyUserInfo(userKey, realName);
					zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(updateAuditItem);
				}
			}
        	// 修改本期应支付含税金额、开累应支付含税金额
        	dbZxSaProjectSettleAudit.setModifyUserInfo(userKey, realName);
        	
        	zxSaProjectSettleAuditMapper.updatePayInfoByPrimaryKey(dbZxSaProjectSettleAudit);
            return repEntity.ok("sys.data.update",zxSaProjectSettleAuditItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectSettleAuditItem(List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectSettleAuditItemList != null && zxSaProjectSettleAuditItemList.size() > 0) {
           ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
           zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectSettleAuditItemMapper.batchDeleteUpdateZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItemList, zxSaProjectSettleAuditItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaProjectSettleAuditItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity deleteAllZxSaProjectSettleAuditItem(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null || StrUtil.isEmpty(zxSaProjectSettleAuditItem.getProjectSettleAuditId())) {
			return repEntity.layerMessage("no", "结算单主键ID不能为空！");
		}
		
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
        int flag = zxSaProjectSettleAuditItemMapper.deleteAllZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItem);
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("删除成功！");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxSaProjectSettleAuditItemByContractId(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null) {
			zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
		}
		
		if (StrUtil.isEmpty(zxSaProjectSettleAuditItem.getContractID())) {
			return repEntity.layerMessage("no", "合同ID不能为空！");
		}
		
		if (StrUtil.isEmpty(zxSaProjectSettleAuditItem.getPeriod())) {
			return repEntity.layerMessage("no", "期次不能为空！");
		}
		
		String contractID = zxSaProjectSettleAuditItem.getContractID();
		String period = zxSaProjectSettleAuditItem.getPeriod();
		
		// 合计含税结算金额(小写) 合计不含税结算金额(小写) 合计结算税额(小写) 台计含税结算金额(大写) 合计不含税结算金额(大写) 台计结算税额(大写) 
		// 其中扣除保证金合计   1、质量保证金 2、安全生产保证金 3、农民工工资偿付保证金 
		// 返还保证金合计          1、质量保证金 2、安全生产保证金 3、农民工工资偿付保证金  
		// 应付工程款(小写) 应付工程款(大写)
		List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = new ArrayList<>();
		
		// 查询本期次合计含税结算金额(小写) 合计不含税结算金额(小写) 合计结算税额(小写)
		ZxSaProjectSettleAudit zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		zxSaProjectSettleAudit.setContractID(contractID);
		zxSaProjectSettleAudit.setPeriod(period);
		List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = (List<ZxSaProjectSettleAudit>) zxSaProjectSettleAuditServiceImpl.getZxSaProjectSettleAuditListByCondition(zxSaProjectSettleAudit).getData();
		
		if (zxSaProjectSettleAuditList == null || zxSaProjectSettleAuditList.size() == 0) {
			// 根据orgID、contractId查询对外经营合同管理-工程施工合同保证金信息
			ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate = new ZxGcsgCtCoContractAmtRate();
			zxGcsgCtCoContractAmtRate.setCtContractId(contractID);
			List<ZxGcsgCtCoContractAmtRate> contractAmtRateList = (List<ZxGcsgCtCoContractAmtRate>) zxGcsgCtCoContractAmtRateService.getZxGcsgCtCoContractAmtRateListByCondition(zxGcsgCtCoContractAmtRate).getData();
			// 保证金
			List<Map<String, Object>> bondMapList = new ArrayList<>();
			if (contractAmtRateList != null && contractAmtRateList.size() > 0) {
				contractAmtRateList.forEach(contractAmtRate -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("statisticsNo", contractAmtRate.getStatisticsNo());
					map.put("statisticsName", contractAmtRate.getStatisticsName());
					map.put("statisticsType", "100400");
					map.put("rate", contractAmtRate.getStatisticsRate());
					bondMapList.add(map);
				});
			}
			// 查询开累信息---无主表信息查询前一期次信息
			List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemTotalList = zxSaProjectSettleAuditItemMapper.getZxSaProjectSettleAuditItemBeforeTotalList(zxSaProjectSettleAuditItem);
			if (zxSaProjectSettleAuditItemTotalList == null || zxSaProjectSettleAuditItemTotalList.size() == 0) {
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100100", "合计含税结算金额（小写）", "0", "0", "100100", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100110", "合计不含税结算金额（小写）", "0", "0", "100110", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100120", "合计结算税额（小写）", "0", "0", "100120", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100200", "合计含税结算金额（大写）", "零元整", "零元整", "100200", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100210", "合计不含税结算金额（大写）", "零元整", "零元整", "100210", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100220", "合计结算税额（大写）", "零元整", "零元整", "100220", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100300", "其中扣除保证金合计", "0", "0", "100300", false, new BigDecimal(0), new BigDecimal(0)));
				for (Map<String, Object> map : bondMapList) {
					zxSaProjectSettleAuditItemList.add(getItem(contractID, period, String.valueOf(map.get("statisticsNo")), String.valueOf(map.get("statisticsName")), "0", "0", String.valueOf(map.get("statisticsType")), false, new BigDecimal(0), new BigDecimal(String.valueOf(map.get("rate")))));
				}
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100300", "返还保证金合计", "0", "0", "100500", true, new BigDecimal(0), new BigDecimal(0)));
				for (Map<String, Object> map : bondMapList) {
					zxSaProjectSettleAuditItemList.add(getItem(contractID, period, String.valueOf(map.get("statisticsNo")), String.valueOf(map.get("statisticsName")), "0", "0", "100600", true, new BigDecimal(0), new BigDecimal(String.valueOf(map.get("rate")))));
				}
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100700", "应付工程款（小写）", "0", "0", "100700", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100800", "应付工程款（大写）", "零元整", "零元整", "100800", false, new BigDecimal(0), new BigDecimal(0)));
			} else {
				// 根据支付项名称、支付项ID取上一期次开累信息
				ZxSaProjectSettleAuditItem selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "合计含税结算金额（小写）", contractID + "_100100");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100100", "合计含税结算金额（小写）", "0", selectItem.getTotalAmt(), "100100", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "合计不含税结算金额（小写）", contractID + "_100110");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100110", "合计不含税结算金额（小写）", "0", selectItem.getTotalAmt(), "100110", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "合计结算税额（小写）", contractID + "_100120");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100120", "合计结算税额（小写）", "0", selectItem.getTotalAmt(), "100120", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "合计含税结算金额（大写）", contractID + "_100200");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100200", "合计含税结算金额（大写）", "零元整", selectItem.getTotalAmt(), "100200", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "合计不含税结算金额（大写）", contractID + "_100210");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100210", "合计不含税结算金额（大写）", "零元整", selectItem.getTotalAmt(), "100210", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "合计结算税额（大写）", contractID + "_100220");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100220", "合计结算税额（大写）", "零元整", selectItem.getTotalAmt(), "100220", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "其中扣除保证金合计", contractID + "_100300");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100300", "其中扣除保证金合计", "0", selectItem.getTotalAmt(), "100300", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				for (Map<String, Object> map : bondMapList) {
					String statisticsID = contractID;
					String statisticsNo = String.valueOf(map.get("statisticsNo"));
					if (StrUtil.isNotEmpty(statisticsNo)) {
						statisticsID += "_" + statisticsNo;
					}
					String statisticsName = String.valueOf(map.get("statisticsName"));
					selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, statisticsName, statisticsID);
					zxSaProjectSettleAuditItemList.add(getItem(contractID, period, statisticsNo, statisticsName, "0", selectItem.getTotalAmt(), String.valueOf(map.get("statisticsType")), false, selectItem.getUpAmt(), new BigDecimal(String.valueOf(map.get("rate")))));
				}
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "返还保证金合计", contractID + "_100300_RETURN");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100300", "返还保证金合计", "0", selectItem.getTotalAmt(), "100500", true, selectItem.getUpAmt(), new BigDecimal(0)));
				
				for (Map<String, Object> map : bondMapList) {
					String statisticsID = contractID;
					String statisticsNo = String.valueOf(map.get("statisticsNo"));
					if (StrUtil.isNotEmpty(statisticsNo)) {
						statisticsID += "_" + statisticsNo + "_RETURN";
					} else {
						statisticsID += "_RETURN";
					}
					String statisticsName = String.valueOf(map.get("statisticsName"));
					
					selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, statisticsName, statisticsID);
					zxSaProjectSettleAuditItemList.add(getItem(contractID, period, statisticsNo, statisticsName, "0", selectItem.getTotalAmt(), "100600", true, selectItem.getUpAmt(), new BigDecimal(String.valueOf(map.get("rate")))));
				}
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "应付工程款（小写）", contractID + "_100700");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100700", "应付工程款（小写）", "0", selectItem.getTotalAmt(), "100700", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "应付工程款（大写）", contractID + "_100800");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100800", "应付工程款（大写）", "零元整", selectItem.getTotalAmt(), "100800", false, selectItem.getUpAmt(), new BigDecimal(0)));
			}
		} else {
			// 根据已有工程类结算单查询工程类结算单明细
			ZxSaProjectSettleAuditItem selectItem = new ZxSaProjectSettleAuditItem();
			selectItem.setProjectSettleAuditId(zxSaProjectSettleAuditList.get(0).getId());
			zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(selectItem);
		}
		return repEntity.ok(zxSaProjectSettleAuditItemList);
	}
	
	/**
	 * 获取子表明细
	 * @param contractID		合同ID
	 * @param period			期次
	 * @param statisticsNo		统计项编号
	 * @param statisticsName	统计项
	 * @param thisAmt			本期(元)
	 * @param totalAmt			开累(元)
	 * @param statisticsType	统计项类型
	 * @return
	 */
	private ZxSaProjectSettleAuditItem getItem(String contractID, String period, String statisticsNo, String statisticsName, 
			String thisAmt, String totalAmt, String statisticsType, boolean returnFlag, BigDecimal upAmt, BigDecimal rate) {
		ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
		zxSaProjectSettleAuditItem.setContractID(contractID);
		zxSaProjectSettleAuditItem.setPeriod(period);
		zxSaProjectSettleAuditItem.setStatisticsNo(statisticsNo);
		zxSaProjectSettleAuditItem.setStatisticsName(statisticsName);
		zxSaProjectSettleAuditItem.setThisAmt(thisAmt);
		zxSaProjectSettleAuditItem.setUpAmt(upAmt);
		zxSaProjectSettleAuditItem.setTotalAmt(totalAmt);
		zxSaProjectSettleAuditItem.setStatisticsType(statisticsType);
		zxSaProjectSettleAuditItem.setRate(rate);
		if (returnFlag) {
			if (StrUtil.isEmpty(statisticsNo)) {
				zxSaProjectSettleAuditItem.setStatisticsID(contractID + "_RETURN");
			} else {
				zxSaProjectSettleAuditItem.setStatisticsID(contractID + "_" + statisticsNo + "_RETURN");
			}
		} else {
			if (StrUtil.isEmpty(statisticsNo)) {
				zxSaProjectSettleAuditItem.setStatisticsID(contractID);
			} else {
				zxSaProjectSettleAuditItem.setStatisticsID(contractID + "_" + statisticsNo);
			}
		}
		return zxSaProjectSettleAuditItem;
	}
	
	/**
	 * 查找匹配项
	 * @param zxSaProjectSettleAuditItemTotalList
	 * @param statisticsName
	 * @param statisticsID
	 * @return
	 */
	private ZxSaProjectSettleAuditItem selectItem(List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemTotalList, String statisticsName, String statisticsID) {
		for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : zxSaProjectSettleAuditItemTotalList) {
			if (StrUtil.equals(statisticsID, zxSaProjectSettleAuditItem.getStatisticsID()) && StrUtil.equals(statisticsName, zxSaProjectSettleAuditItem.getStatisticsName())) {
				String totalAmt = zxSaProjectSettleAuditItem.getTotalAmt();
				if (StrUtil.contains(totalAmt, "元")) {
					zxSaProjectSettleAuditItem.setUpAmt(DigitalConversionUtil.upperCaseToLowerCase(totalAmt));
				} else {
					zxSaProjectSettleAuditItem.setUpAmt(new BigDecimal(totalAmt));
				}
				return zxSaProjectSettleAuditItem;
			}
		}
		// 无匹配项---根据contractID、period、statisticsName、statisticsID查询开累金额及上期金额
		ZxSaProjectSettleAuditItem projectSettleAuditItem = zxSaProjectSettleAuditItemTotalList.get(0);
		projectSettleAuditItem.setStatisticsID(statisticsID);
		projectSettleAuditItem.setStatisticsName(statisticsName);
		ZxSaProjectSettleAuditItem selectItem = zxSaProjectSettleAuditItemMapper.selectTotalAmtUpAmt(projectSettleAuditItem);
		
		if (selectItem == null) {
			selectItem = new ZxSaProjectSettleAuditItem();
		}
		
		if (StrUtil.isEmpty(selectItem.getTotalAmt())) {
			selectItem.setTotalAmt(StrUtil.contains(statisticsName, "大写") ? "零元整" : "0");
		}
		
		if (selectItem.getUpAmt() == null) {
			selectItem.setUpAmt(new BigDecimal(0));
		}
		
		return selectItem;
	}

	@Override
	public List<ZxSaProjectSettleAuditItem> getZxSaProjectSettleAuditItemListNoToken(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null) {
            zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
        }
        // 获取数据
        List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(zxSaProjectSettleAuditItem);
		if (zxSaProjectSettleAuditItemList != null && zxSaProjectSettleAuditItemList.size() > 0) {
			List<ZxSaProjectSettleAuditItem> returnSettleAuditItemList = zxSaProjectSettleAuditItemList.stream().filter(item -> !StrUtil.equalsAny(item.getStatisticsNo(), "100700", "100800")).collect(Collectors.toList());
			return returnSettleAuditItemList == null ? new ArrayList<>() : returnSettleAuditItemList;
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<ZxSaProjectSettleAuditItem> getZxSaProjectSettleAuditAllItemList(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null) {
			zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
		}
		// 获取数据
		List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(zxSaProjectSettleAuditItem);
		if (zxSaProjectSettleAuditItemList != null && zxSaProjectSettleAuditItemList.size() > 0) {
			List<ZxSaProjectSettleAuditItem> returnSettleAuditItemList = new ArrayList<>();
			for (ZxSaProjectSettleAuditItem item : zxSaProjectSettleAuditItemList) {
				if (StrUtil.equalsAny(item.getStatisticsType(), "100500", "100700")) {
					ZxSaProjectSettleAuditItem addItem = new ZxSaProjectSettleAuditItem();
					BeanUtil.copyProperties(item, addItem);
					addItem.setStatisticsName("其它保证金（小写元）");
					addItem.setThisAmt("");
					addItem.setTotalAmt("");
					addItem.setProjectSettleAuditItemId(UuidUtil.createUUID());
					returnSettleAuditItemList.add(addItem);
				}
				returnSettleAuditItemList.add(item);
			}
			
			return returnSettleAuditItemList == null ? new ArrayList<>() : returnSettleAuditItemList;
		}
		return new ArrayList<>();
	}

	@Override
	public ResponseEntity exportZxSaProjectSettleAuditItemList(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null || StrUtil.isEmpty(zxSaProjectSettleAuditItem.getProjectSettleAuditId())) {
			return repEntity.layerMessage("no", "ProjectSettleAuditId不能为空！");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "工程类结算单结算单";
		// 文件路径
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=" + zxSaProjectSettleAuditItem.getRemark() + "&url=" + webUrl + "&projectSettleAuditId=" + zxSaProjectSettleAuditItem.getProjectSettleAuditId()+ "&id=" + zxSaProjectSettleAuditItem.getProjectSettleAuditId();
		return repEntity.ok(fileUrl);
	}
	
	@Override
	public ResponseEntity exportZxSaProjectSettleAuditCountersignature(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null || StrUtil.isEmpty(zxSaProjectSettleAuditItem.getProjectSettleAuditId())) {
			return repEntity.layerMessage("no", "ProjectSettleAuditId不能为空！");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "工程结算单会签单";
		// 文件路径
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=" + zxSaProjectSettleAuditItem.getRemark() + "&url=" + webUrl + "&projectSettleAuditId=" + zxSaProjectSettleAuditItem.getProjectSettleAuditId()+ "&id=" + zxSaProjectSettleAuditItem.getProjectSettleAuditId();
		return repEntity.ok(fileUrl);
	}
}
