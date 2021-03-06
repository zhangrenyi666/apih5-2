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
        // ????????????
        PageHelper.startPage(zxSaProjectSettleAuditItem.getPage(),zxSaProjectSettleAuditItem.getLimit());
        // ????????????
        List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(zxSaProjectSettleAuditItem);
        // ??????????????????
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
        // ????????????
        ZxSaProjectSettleAuditItem dbZxSaProjectSettleAuditItem = zxSaProjectSettleAuditItemMapper.selectByPrimaryKey(zxSaProjectSettleAuditItem.getProjectSettleAuditItemId());
        // ????????????
        if (dbZxSaProjectSettleAuditItem != null) {
            return repEntity.ok(dbZxSaProjectSettleAuditItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
           // ???????????????ID
           dbZxSaProjectSettleAuditItem.setProjectSettleAuditId(zxSaProjectSettleAuditItem.getProjectSettleAuditId());
           // ??????ID
           dbZxSaProjectSettleAuditItem.setOrgID(zxSaProjectSettleAuditItem.getOrgID());
           // ??????ID
           dbZxSaProjectSettleAuditItem.setContractID(zxSaProjectSettleAuditItem.getContractID());
           // ??????
           dbZxSaProjectSettleAuditItem.setPeriod(zxSaProjectSettleAuditItem.getPeriod());
           // ???????????????
           dbZxSaProjectSettleAuditItem.setStatisticsNo(zxSaProjectSettleAuditItem.getStatisticsNo());
           // ?????????
           dbZxSaProjectSettleAuditItem.setStatisticsName(zxSaProjectSettleAuditItem.getStatisticsName());
           // ??????(???)
           dbZxSaProjectSettleAuditItem.setThisAmt(zxSaProjectSettleAuditItem.getThisAmt());
           // ??????(???)
           dbZxSaProjectSettleAuditItem.setTotalAmt(CalcUtils.calcAdd(dbZxSaProjectSettleAuditItem.getUpAmt(), new BigDecimal(StrUtil.isEmpty(zxSaProjectSettleAuditItem.getThisAmt()) ? "0" : zxSaProjectSettleAuditItem.getThisAmt())) + "");
           // ??????????????????
           dbZxSaProjectSettleAuditItem.setEditTime(zxSaProjectSettleAuditItem.getEditTime());
           // ??????
           dbZxSaProjectSettleAuditItem.setRate(zxSaProjectSettleAuditItem.getRate());
           // ?????????ID
           dbZxSaProjectSettleAuditItem.setStatisticsID(zxSaProjectSettleAuditItem.getStatisticsID());
           // ???????????????
           dbZxSaProjectSettleAuditItem.setStatisticsType(zxSaProjectSettleAuditItem.getStatisticsType());
           // ????????????
//           dbZxSaProjectSettleAuditItem.setUpAmt(zxSaProjectSettleAuditItem.getUpAmt());
           // ????????????ID
           dbZxSaProjectSettleAuditItem.setComID(zxSaProjectSettleAuditItem.getComID());
           // ????????????
           dbZxSaProjectSettleAuditItem.setComName(zxSaProjectSettleAuditItem.getComName());
           // ??????????????????
           dbZxSaProjectSettleAuditItem.setComOrders(zxSaProjectSettleAuditItem.getComOrders());
           // ????????????ID
           dbZxSaProjectSettleAuditItem.setFiID(zxSaProjectSettleAuditItem.getFiID());
           // ??????
           dbZxSaProjectSettleAuditItem.setSort(zxSaProjectSettleAuditItem.getSort());
           // ??????
           dbZxSaProjectSettleAuditItem.setRemark(zxSaProjectSettleAuditItem.getRemark());
           // ??????
           dbZxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectSettleAuditItemMapper.updateByPrimaryKey(dbZxSaProjectSettleAuditItem);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	ZxSaProjectSettleAudit dbZxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
        	
        	// ????????????????????????????????????????????????????????????
        	ZxSaProjectSettleAuditItem selectItem = new ZxSaProjectSettleAuditItem();
        	selectItem.setProjectSettleAuditId(dbZxSaProjectSettleAuditItem.getProjectSettleAuditId());
        	List<ZxSaProjectSettleAuditItem> settleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(selectItem);
        	if (settleAuditItemList != null && settleAuditItemList.size() > 0) {
				BigDecimal thisAmt = null, totalThisAmt = null, deductionAmt = null, totalDeductionAmt = null; // ????????????????????????????????????????????????
				BigDecimal returnAmt = new BigDecimal(0); // ???????????????????????????
				BigDecimal totalReturnAmt = new BigDecimal(0); // ???????????????????????????
				List<ZxSaProjectSettleAuditItem> updateItemList = new ArrayList<>(); // ????????????(????????????????????????????????????????????????)
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
        	// ???????????????????????????????????????????????????????????????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaProjectSettleAuditItemList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

	@Override
	public ResponseEntity deleteAllZxSaProjectSettleAuditItem(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null || StrUtil.isEmpty(zxSaProjectSettleAuditItem.getProjectSettleAuditId())) {
			return repEntity.layerMessage("no", "???????????????ID???????????????");
		}
		
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectSettleAuditItem.setModifyUserInfo(userKey, realName);
        int flag = zxSaProjectSettleAuditItemMapper.deleteAllZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItem);
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("???????????????");
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity getZxSaProjectSettleAuditItemByContractId(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null) {
			zxSaProjectSettleAuditItem = new ZxSaProjectSettleAuditItem();
		}
		
		if (StrUtil.isEmpty(zxSaProjectSettleAuditItem.getContractID())) {
			return repEntity.layerMessage("no", "??????ID???????????????");
		}
		
		if (StrUtil.isEmpty(zxSaProjectSettleAuditItem.getPeriod())) {
			return repEntity.layerMessage("no", "?????????????????????");
		}
		
		String contractID = zxSaProjectSettleAuditItem.getContractID();
		String period = zxSaProjectSettleAuditItem.getPeriod();
		
		// ????????????????????????(??????) ???????????????????????????(??????) ??????????????????(??????) ????????????????????????(??????) ???????????????????????????(??????) ??????????????????(??????) 
		// ???????????????????????????   1?????????????????? 2???????????????????????? 3????????????????????????????????? 
		// ?????????????????????          1?????????????????? 2???????????????????????? 3?????????????????????????????????  
		// ???????????????(??????) ???????????????(??????)
		List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = new ArrayList<>();
		
		// ???????????????????????????????????????(??????) ???????????????????????????(??????) ??????????????????(??????)
		ZxSaProjectSettleAudit zxSaProjectSettleAudit = new ZxSaProjectSettleAudit();
		zxSaProjectSettleAudit.setContractID(contractID);
		zxSaProjectSettleAudit.setPeriod(period);
		List<ZxSaProjectSettleAudit> zxSaProjectSettleAuditList = (List<ZxSaProjectSettleAudit>) zxSaProjectSettleAuditServiceImpl.getZxSaProjectSettleAuditListByCondition(zxSaProjectSettleAudit).getData();
		
		if (zxSaProjectSettleAuditList == null || zxSaProjectSettleAuditList.size() == 0) {
			// ??????orgID???contractId??????????????????????????????-?????????????????????????????????
			ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate = new ZxGcsgCtCoContractAmtRate();
			zxGcsgCtCoContractAmtRate.setCtContractId(contractID);
			List<ZxGcsgCtCoContractAmtRate> contractAmtRateList = (List<ZxGcsgCtCoContractAmtRate>) zxGcsgCtCoContractAmtRateService.getZxGcsgCtCoContractAmtRateListByCondition(zxGcsgCtCoContractAmtRate).getData();
			// ?????????
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
			// ??????????????????---???????????????????????????????????????
			List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemTotalList = zxSaProjectSettleAuditItemMapper.getZxSaProjectSettleAuditItemBeforeTotalList(zxSaProjectSettleAuditItem);
			if (zxSaProjectSettleAuditItemTotalList == null || zxSaProjectSettleAuditItemTotalList.size() == 0) {
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100100", "????????????????????????????????????", "0", "0", "100100", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100110", "???????????????????????????????????????", "0", "0", "100110", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100120", "??????????????????????????????", "0", "0", "100120", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100200", "????????????????????????????????????", "?????????", "?????????", "100200", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100210", "???????????????????????????????????????", "?????????", "?????????", "100210", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100220", "??????????????????????????????", "?????????", "?????????", "100220", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100300", "???????????????????????????", "0", "0", "100300", false, new BigDecimal(0), new BigDecimal(0)));
				for (Map<String, Object> map : bondMapList) {
					zxSaProjectSettleAuditItemList.add(getItem(contractID, period, String.valueOf(map.get("statisticsNo")), String.valueOf(map.get("statisticsName")), "0", "0", String.valueOf(map.get("statisticsType")), false, new BigDecimal(0), new BigDecimal(String.valueOf(map.get("rate")))));
				}
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100300", "?????????????????????", "0", "0", "100500", true, new BigDecimal(0), new BigDecimal(0)));
				for (Map<String, Object> map : bondMapList) {
					zxSaProjectSettleAuditItemList.add(getItem(contractID, period, String.valueOf(map.get("statisticsNo")), String.valueOf(map.get("statisticsName")), "0", "0", "100600", true, new BigDecimal(0), new BigDecimal(String.valueOf(map.get("rate")))));
				}
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100700", "???????????????????????????", "0", "0", "100700", false, new BigDecimal(0), new BigDecimal(0)));
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100800", "???????????????????????????", "?????????", "?????????", "100800", false, new BigDecimal(0), new BigDecimal(0)));
			} else {
				// ?????????????????????????????????ID???????????????????????????
				ZxSaProjectSettleAuditItem selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "????????????????????????????????????", contractID + "_100100");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100100", "????????????????????????????????????", "0", selectItem.getTotalAmt(), "100100", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "???????????????????????????????????????", contractID + "_100110");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100110", "???????????????????????????????????????", "0", selectItem.getTotalAmt(), "100110", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "??????????????????????????????", contractID + "_100120");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100120", "??????????????????????????????", "0", selectItem.getTotalAmt(), "100120", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "????????????????????????????????????", contractID + "_100200");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100200", "????????????????????????????????????", "?????????", selectItem.getTotalAmt(), "100200", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "???????????????????????????????????????", contractID + "_100210");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100210", "???????????????????????????????????????", "?????????", selectItem.getTotalAmt(), "100210", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "??????????????????????????????", contractID + "_100220");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100220", "??????????????????????????????", "?????????", selectItem.getTotalAmt(), "100220", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "???????????????????????????", contractID + "_100300");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100300", "???????????????????????????", "0", selectItem.getTotalAmt(), "100300", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
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
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "?????????????????????", contractID + "_100300_RETURN");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100300", "?????????????????????", "0", selectItem.getTotalAmt(), "100500", true, selectItem.getUpAmt(), new BigDecimal(0)));
				
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
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "???????????????????????????", contractID + "_100700");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100700", "???????????????????????????", "0", selectItem.getTotalAmt(), "100700", false, selectItem.getUpAmt(), new BigDecimal(0)));
				
				selectItem = selectItem(zxSaProjectSettleAuditItemTotalList, "???????????????????????????", contractID + "_100800");
				zxSaProjectSettleAuditItemList.add(getItem(contractID, period, "100800", "???????????????????????????", "?????????", selectItem.getTotalAmt(), "100800", false, selectItem.getUpAmt(), new BigDecimal(0)));
			}
		} else {
			// ????????????????????????????????????????????????????????????
			ZxSaProjectSettleAuditItem selectItem = new ZxSaProjectSettleAuditItem();
			selectItem.setProjectSettleAuditId(zxSaProjectSettleAuditList.get(0).getId());
			zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(selectItem);
		}
		return repEntity.ok(zxSaProjectSettleAuditItemList);
	}
	
	/**
	 * ??????????????????
	 * @param contractID		??????ID
	 * @param period			??????
	 * @param statisticsNo		???????????????
	 * @param statisticsName	?????????
	 * @param thisAmt			??????(???)
	 * @param totalAmt			??????(???)
	 * @param statisticsType	???????????????
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
	 * ???????????????
	 * @param zxSaProjectSettleAuditItemTotalList
	 * @param statisticsName
	 * @param statisticsID
	 * @return
	 */
	private ZxSaProjectSettleAuditItem selectItem(List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemTotalList, String statisticsName, String statisticsID) {
		for (ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem : zxSaProjectSettleAuditItemTotalList) {
			if (StrUtil.equals(statisticsID, zxSaProjectSettleAuditItem.getStatisticsID()) && StrUtil.equals(statisticsName, zxSaProjectSettleAuditItem.getStatisticsName())) {
				String totalAmt = zxSaProjectSettleAuditItem.getTotalAmt();
				if (StrUtil.contains(totalAmt, "???")) {
					zxSaProjectSettleAuditItem.setUpAmt(DigitalConversionUtil.upperCaseToLowerCase(totalAmt));
				} else {
					zxSaProjectSettleAuditItem.setUpAmt(new BigDecimal(totalAmt));
				}
				return zxSaProjectSettleAuditItem;
			}
		}
		// ????????????---??????contractID???period???statisticsName???statisticsID?????????????????????????????????
		ZxSaProjectSettleAuditItem projectSettleAuditItem = zxSaProjectSettleAuditItemTotalList.get(0);
		projectSettleAuditItem.setStatisticsID(statisticsID);
		projectSettleAuditItem.setStatisticsName(statisticsName);
		ZxSaProjectSettleAuditItem selectItem = zxSaProjectSettleAuditItemMapper.selectTotalAmtUpAmt(projectSettleAuditItem);
		
		if (selectItem == null) {
			selectItem = new ZxSaProjectSettleAuditItem();
		}
		
		if (StrUtil.isEmpty(selectItem.getTotalAmt())) {
			selectItem.setTotalAmt(StrUtil.contains(statisticsName, "??????") ? "?????????" : "0");
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
        // ????????????
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
		// ????????????
		List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList = zxSaProjectSettleAuditItemMapper.selectByZxSaProjectSettleAuditItemList(zxSaProjectSettleAuditItem);
		if (zxSaProjectSettleAuditItemList != null && zxSaProjectSettleAuditItemList.size() > 0) {
			List<ZxSaProjectSettleAuditItem> returnSettleAuditItemList = new ArrayList<>();
			for (ZxSaProjectSettleAuditItem item : zxSaProjectSettleAuditItemList) {
				if (StrUtil.equalsAny(item.getStatisticsType(), "100500", "100700")) {
					ZxSaProjectSettleAuditItem addItem = new ZxSaProjectSettleAuditItem();
					BeanUtil.copyProperties(item, addItem);
					addItem.setStatisticsName("??????????????????????????????");
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
			return repEntity.layerMessage("no", "ProjectSettleAuditId???????????????");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "???????????????????????????";
		// ????????????
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=" + zxSaProjectSettleAuditItem.getRemark() + "&url=" + webUrl + "&projectSettleAuditId=" + zxSaProjectSettleAuditItem.getProjectSettleAuditId()+ "&id=" + zxSaProjectSettleAuditItem.getProjectSettleAuditId();
		return repEntity.ok(fileUrl);
	}
	
	@Override
	public ResponseEntity exportZxSaProjectSettleAuditCountersignature(ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
		if (zxSaProjectSettleAuditItem == null || StrUtil.isEmpty(zxSaProjectSettleAuditItem.getProjectSettleAuditId())) {
			return repEntity.layerMessage("no", "ProjectSettleAuditId???????????????");
		}
		String reportUrl = publicConfig.getProperty("report.web.url","");
		String webUrl = Apih5Properties.getWebUrl();
		
		String fileName = "????????????????????????";
		// ????????????
		String fileUrl = reportUrl + "excel?_u=file:" + fileName + ".xml&_n=" + zxSaProjectSettleAuditItem.getRemark() + "&url=" + webUrl + "&projectSettleAuditId=" + zxSaProjectSettleAuditItem.getProjectSettleAuditId()+ "&id=" + zxSaProjectSettleAuditItem.getProjectSettleAuditId();
		return repEntity.ok(fileUrl);
	}
}
