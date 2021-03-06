package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSaProjectFinishItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectFinishMapper;
import com.apih5.mybatis.pojo.ZxCtContract;
import com.apih5.mybatis.pojo.ZxSaProjectFinish;
import com.apih5.mybatis.pojo.ZxSaProjectFinishItem;
import com.apih5.service.ZxCtContractService;
import com.apih5.service.ZxSaProjectFinishService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("zxSaProjectFinishService")
public class ZxSaProjectFinishServiceImpl implements ZxSaProjectFinishService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectFinishMapper zxSaProjectFinishMapper;
    
    @Autowired(required = true)
    private ZxSaProjectFinishItemMapper zxSaProjectFinishItemMapper;
    
    @Autowired(required = true)
    private ZxCtContractService zxCtContractService;

    @Override
    public ResponseEntity getZxSaProjectFinishListByCondition(ZxSaProjectFinish zxSaProjectFinish) {
        if (zxSaProjectFinish == null) {
            zxSaProjectFinish = new ZxSaProjectFinish();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
        	zxSaProjectFinish.setComID("");
            zxSaProjectFinish.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxSaProjectFinish.setComID(zxSaProjectFinish.getOrgID());
        	zxSaProjectFinish.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxSaProjectFinish.setOrgID(zxSaProjectFinish.getOrgID());
        }
        
        // ????????????
        PageHelper.startPage(zxSaProjectFinish.getPage(),zxSaProjectFinish.getLimit());
        // ????????????
        List<ZxSaProjectFinish> zxSaProjectFinishList = zxSaProjectFinishMapper.selectByZxSaProjectFinishList(zxSaProjectFinish);
        for (ZxSaProjectFinish zxSaProjectFinish2 : zxSaProjectFinishList) {
       	 	ZxSaProjectFinishItem item = new ZxSaProjectFinishItem();
            item.setMainID(zxSaProjectFinish2.getId());
            List<ZxSaProjectFinishItem> itemList = zxSaProjectFinishItemMapper.selectByZxSaProjectFinishItemList(item);
            zxSaProjectFinish2.setItemList(itemList);
            
            String oldStatus = zxSaProjectFinish2.getFinishStatus();
            if (zxSaProjectFinish2.getRealSettleEndDate() != null && DateUtil.compare(DateUtil.endOfDay(new Date()), DateUtil.endOfDay(zxSaProjectFinish2.getRealSettleEndDate())) > 0) {
            	zxSaProjectFinish2.setFinishStatus("1");
			} else {
				zxSaProjectFinish2.setFinishStatus("0");
			}
            if (!StrUtil.equals(oldStatus, zxSaProjectFinish2.getFinishStatus())) {
            	zxSaProjectFinishMapper.updateByPrimaryKey(zxSaProjectFinish2);
			}
		}
        // ??????????????????
        PageInfo<ZxSaProjectFinish> p = new PageInfo<>(zxSaProjectFinishList);

        return repEntity.okList(zxSaProjectFinishList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaProjectFinishDetails(ZxSaProjectFinish zxSaProjectFinish) {
        if (zxSaProjectFinish == null) {
            zxSaProjectFinish = new ZxSaProjectFinish();
        }
        // ????????????
        ZxSaProjectFinish dbZxSaProjectFinish = zxSaProjectFinishMapper.selectByPrimaryKey(zxSaProjectFinish.getId());
        // ????????????
        if (dbZxSaProjectFinish != null) {
            return repEntity.ok(dbZxSaProjectFinish);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxSaProjectFinish(ZxSaProjectFinish zxSaProjectFinish) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectFinish.setId(UuidUtil.generate());
        zxSaProjectFinish.setCreateUserInfo(userKey, realName);
        int flag = zxSaProjectFinishMapper.insert(zxSaProjectFinish);
        if(zxSaProjectFinish.getItemList() != null && zxSaProjectFinish.getItemList().size() >0) {
        	for (ZxSaProjectFinishItem item : zxSaProjectFinish.getItemList()) {
        		item.setId(UuidUtil.generate());
        		item.setMainID(zxSaProjectFinish.getId());
        		item.setCreateUserInfo(userKey, realName);
        		flag = zxSaProjectFinishItemMapper.insert(item);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSaProjectFinish);
        }
    }

    @Override
    public ResponseEntity updateZxSaProjectFinish(ZxSaProjectFinish zxSaProjectFinish) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        
        int flag = 0;
        boolean isChange = false;
        ZxSaProjectFinish dbzxSaProjectFinish = zxSaProjectFinishMapper.selectByPrimaryKey(zxSaProjectFinish.getId());
        if (dbzxSaProjectFinish != null && StrUtil.isNotEmpty(dbzxSaProjectFinish.getId())) {
           // ??????id
           dbzxSaProjectFinish.setContractID(zxSaProjectFinish.getContractID());
           // ????????????
           dbzxSaProjectFinish.setContractNo(zxSaProjectFinish.getContractNo());
           // ????????????
           dbzxSaProjectFinish.setContractName(zxSaProjectFinish.getContractName());
           // ??????id
           dbzxSaProjectFinish.setOrgID(zxSaProjectFinish.getOrgID());
           // ????????????
           dbzxSaProjectFinish.setOrgName(zxSaProjectFinish.getOrgName());
           // ???????????????????????????
           dbzxSaProjectFinish.setContractCost(zxSaProjectFinish.getContractCost());
           // ?????????????????????????????????
           dbzxSaProjectFinish.setContractMoney(zxSaProjectFinish.getContractMoney());
           // ????????????
           dbzxSaProjectFinish.setProjectManager(zxSaProjectFinish.getProjectManager());
           // ??????????????????
           dbzxSaProjectFinish.setActualStartDate(zxSaProjectFinish.getActualStartDate());
           // ??????????????????
           dbzxSaProjectFinish.setActualEndDate(zxSaProjectFinish.getActualEndDate());
           // ?????????????????????
           dbzxSaProjectFinish.setCsTimeLimit(zxSaProjectFinish.getCsTimeLimit());
           // ??????????????????
           dbzxSaProjectFinish.setRealBeginDate(zxSaProjectFinish.getRealBeginDate());
           // ??????????????????
           dbzxSaProjectFinish.setRealEndDate(zxSaProjectFinish.getRealEndDate());
           // ??????????????????
           if (dbzxSaProjectFinish.getRealSettleEndDate() != null && zxSaProjectFinish.getRealSettleEndDate() != null) {
        	   if (DateUtil.compare(dbzxSaProjectFinish.getRealSettleEndDate(), zxSaProjectFinish.getRealSettleEndDate()) != 0) {
        		   isChange = true;
        	   }
           }
           dbzxSaProjectFinish.setRealSettleEndDate(zxSaProjectFinish.getRealSettleEndDate());
           // ????????????????????????
           dbzxSaProjectFinish.setPlanSettleCloseDate(zxSaProjectFinish.getPlanSettleCloseDate());
           // ????????????????????????
           dbzxSaProjectFinish.setRealSettleCloseDate(zxSaProjectFinish.getRealSettleCloseDate());
           // ????????????
           dbzxSaProjectFinish.setReportDate(zxSaProjectFinish.getReportDate());
           // ?????????
           dbzxSaProjectFinish.setReportPerson(zxSaProjectFinish.getReportPerson());
           // ??????
           dbzxSaProjectFinish.setRemark(zxSaProjectFinish.getRemark());
           // ????????????
           dbzxSaProjectFinish.setFinishStatus(zxSaProjectFinish.getFinishStatus());
           // ??????
           dbzxSaProjectFinish.setAuditStatus(zxSaProjectFinish.getAuditStatus());
           dbzxSaProjectFinish.setDetermineFilingDate(zxSaProjectFinish.getDetermineFilingDate());
           // ????????????id
           dbzxSaProjectFinish.setComID(zxSaProjectFinish.getComID());
           // ????????????name
           dbzxSaProjectFinish.setComName(zxSaProjectFinish.getComName());
           // ??????????????????
           dbzxSaProjectFinish.setComOrders(zxSaProjectFinish.getComOrders());
           // 
           dbzxSaProjectFinish.setCombProp(zxSaProjectFinish.getCombProp());
           // 
           dbzxSaProjectFinish.setPp1(zxSaProjectFinish.getPp1());
           // 
           dbzxSaProjectFinish.setPp2(zxSaProjectFinish.getPp2());
           // 
           dbzxSaProjectFinish.setPp3(zxSaProjectFinish.getPp3());
           // 
           dbzxSaProjectFinish.setPp4(zxSaProjectFinish.getPp4());
           // 
           dbzxSaProjectFinish.setPp5(zxSaProjectFinish.getPp5());
           // 
           dbzxSaProjectFinish.setPp6(zxSaProjectFinish.getPp6());
           // ???????????????
           dbzxSaProjectFinish.setPp7(zxSaProjectFinish.getPp7());
           // 
           dbzxSaProjectFinish.setPp8(zxSaProjectFinish.getPp8());
           // 
           dbzxSaProjectFinish.setPp9(zxSaProjectFinish.getPp9());
           // 
           dbzxSaProjectFinish.setPp10(zxSaProjectFinish.getPp10());
           // 
           dbzxSaProjectFinish.setGuidangDate(zxSaProjectFinish.getGuidangDate());
           // ??????
           dbzxSaProjectFinish.setModifyUserInfo(userKey, realName);
           
           if (dbzxSaProjectFinish.getRealSettleEndDate() != null && DateUtil.compare(DateUtil.endOfDay(new Date()), DateUtil.endOfDay(dbzxSaProjectFinish.getRealSettleEndDate())) > 0) {
   				zxSaProjectFinish.setFinishStatus("1");
			} else {
				zxSaProjectFinish.setFinishStatus("0");
			}
           
           flag = zxSaProjectFinishMapper.updateByPrimaryKey(dbzxSaProjectFinish);
           //??????????????????
           ZxSaProjectFinishItem delItem = new ZxSaProjectFinishItem();
           delItem.setMainID(dbzxSaProjectFinish.getId());
           List<ZxSaProjectFinishItem> delItemList = zxSaProjectFinishItemMapper.selectByZxSaProjectFinishItemList(delItem);
           if(delItemList != null &&delItemList.size() >0) {
        	   flag = zxSaProjectFinishItemMapper.batchDeleteUpdateZxSaProjectFinishItem(delItemList, delItem);
           }
           
           if(zxSaProjectFinish.getItemList() != null && zxSaProjectFinish.getItemList().size() >0) {
        	   for (ZxSaProjectFinishItem item : zxSaProjectFinish.getItemList()) {
        		   item.setId(UuidUtil.generate());
        		   item.setMainID(zxSaProjectFinish.getId());
        		   item.setCreateUserInfo(userKey, realName);
        		   flag = zxSaProjectFinishItemMapper.insert(item);
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// ????????????????????????????????????
        	if (isChange) {
        		String url = Apih5Properties.getWebUrl() + "updateSysProjectByMainFinish";
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("departmentId", dbzxSaProjectFinish.getOrgID());
                jsonObject.set("mainEndDate", dbzxSaProjectFinish.getRealSettleEndDate());
                HttpUtil.sendPostToken(url, jsonObject.toString(), token);
			}
            return repEntity.ok("sys.data.update",zxSaProjectFinish);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectFinish(List<ZxSaProjectFinish> zxSaProjectFinishList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectFinishList != null && zxSaProjectFinishList.size() > 0) {
        	//??????
        	for (ZxSaProjectFinish zxSaProjectFinish : zxSaProjectFinishList) {
        		ZxSaProjectFinishItem delItem = new ZxSaProjectFinishItem();
        		delItem.setMainID(zxSaProjectFinish.getId());
        		List<ZxSaProjectFinishItem> delItemList = zxSaProjectFinishItemMapper.selectByZxSaProjectFinishItemList(delItem);
        		if(delItemList != null &&delItemList.size() >0) {
        			flag = zxSaProjectFinishItemMapper.batchDeleteUpdateZxSaProjectFinishItem(delItemList, delItem);
        		}
        	}
        	ZxSaProjectFinish zxSaProjectFinish = new ZxSaProjectFinish();
           zxSaProjectFinish.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectFinishMapper.batchDeleteUpdateZxSaProjectFinish(zxSaProjectFinishList, zxSaProjectFinish);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSaProjectFinishList);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity syncZxSaProjectFinish(ZxSaProjectFinish zxSaProjectFinish) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String realName = TokenUtils.getRealName(request);
		
		// ??????????????????????????????????????????????????????????????????????????????????????????
		ZxCtContract zxCtContract = new ZxCtContract();
		zxCtContract.setContrStatus("1");
		zxCtContract.setOrgID(zxSaProjectFinish.getOrgID());
		List<ZxCtContract> zxCtContractList = (List<ZxCtContract>) zxCtContractService.getZxCtContractListByCondition(zxCtContract).getData();
		if (zxCtContractList != null && zxCtContractList.size() > 0) {
			zxCtContractList.forEach(contract -> {
				String contractId = contract.getId();
				// ????????????????????????
				ZxSaProjectFinish selectProjectFinish = new ZxSaProjectFinish();
				selectProjectFinish.setContractID(contractId);
				List<ZxSaProjectFinish> projectFinishList = zxSaProjectFinishMapper.selectByZxSaProjectFinishList(selectProjectFinish);
				
				// ????????????????????????
				if (projectFinishList != null && projectFinishList.size() > 0) {
					ZxSaProjectFinish dbZxSaProjectFinish = projectFinishList.get(0);
					dbZxSaProjectFinish.setContractNo(contract.getContractNo()); // ????????????
					dbZxSaProjectFinish.setContractName(contract.getContractName()); // ????????????
					dbZxSaProjectFinish.setOrgID(contract.getOrgID()); // ??????id
					dbZxSaProjectFinish.setOrgName(contract.getOrgName()); // ????????????
					dbZxSaProjectFinish.setContractCost(contract.getContractCost()); // ???????????????????????????
					dbZxSaProjectFinish.setContractMoney(contract.getContractMoney()); // ?????????????????????????????????
					dbZxSaProjectFinish.setProjectManager(contract.getProjectChiefName()); // ????????????
					dbZxSaProjectFinish.setActualStartDate(contract.getActualStartDate()); // ??????????????????
					dbZxSaProjectFinish.setActualEndDate(contract.getActualEndDate()); // ??????????????????
					dbZxSaProjectFinish.setCsTimeLimit(contract.getCsTimeLimit()); // ?????????????????????
					dbZxSaProjectFinish.setRealBeginDate(contract.getRealBeginDate()); // ??????????????????
					dbZxSaProjectFinish.setRealEndDate(contract.getRealEndDate());// ??????????????????
					dbZxSaProjectFinish.setRealSettleEndDate(contract.getRealEndDate()); // ??????????????????
					dbZxSaProjectFinish.setPp1(contract.getSubArea()); // ??????????????????
	        		if(contract.getRealEndDate() != null) {
	        			dbZxSaProjectFinish.setPlanSettleCloseDate(DateUtil.offsetMonth(contract.getRealEndDate(), 3)); // ????????????????????????
	        			dbZxSaProjectFinish.setRealSettleCloseDate(DateUtil.offsetMonth(contract.getRealEndDate(), 3)); // ????????????????????????
	        		}
	        		dbZxSaProjectFinish.setPp7(contract.getProjectLocation()); // ???????????????
					updateZxSaProjectFinish(dbZxSaProjectFinish);
				} else {
					// ??????????????????
	        		ZxSaProjectFinish addProjectFinish = new ZxSaProjectFinish();
	        		addProjectFinish.setContractID(contract.getId()); // ??????id
	        		addProjectFinish.setContractNo(contract.getContractNo()); // ????????????
	        		addProjectFinish.setContractName(contract.getContractName()); // ????????????
	        		addProjectFinish.setOrgID(contract.getOrgID()); // ??????id
	        		addProjectFinish.setOrgName(contract.getOrgName()); // ????????????
	        		addProjectFinish.setContractCost(contract.getContractCost()); // ???????????????????????????
	        		addProjectFinish.setContractMoney(contract.getContractMoney()); // ?????????????????????????????????
	        		addProjectFinish.setProjectManager(contract.getProjectChiefName()); // ????????????
	        		addProjectFinish.setActualStartDate(contract.getActualStartDate()); // ??????????????????
	        		addProjectFinish.setActualEndDate(contract.getActualEndDate()); // ??????????????????
	        		addProjectFinish.setCsTimeLimit(contract.getCsTimeLimit()); // ?????????????????????
	        		addProjectFinish.setRealBeginDate(contract.getRealBeginDate()); // ??????????????????
	        		addProjectFinish.setRealEndDate(contract.getRealEndDate());// ??????????????????
	        		addProjectFinish.setRealSettleEndDate(contract.getRealEndDate()); // ??????????????????
	        		addProjectFinish.setPp1(contract.getSubArea()); // ??????????????????
	        		if(contract.getRealEndDate() != null) {
	        			addProjectFinish.setPlanSettleCloseDate(DateUtil.offsetMonth(contract.getRealEndDate(), 3)); // ????????????????????????
	        			addProjectFinish.setRealSettleCloseDate(DateUtil.offsetMonth(contract.getRealEndDate(), 3)); // ????????????????????????
	        		}
	        		addProjectFinish.setReportDate(new Date()); // ????????????
	        		addProjectFinish.setReportPerson(realName); // ?????????
	        		addProjectFinish.setFinishStatus("0"); // ????????????
	        		addProjectFinish.setAuditStatus("0"); // ??????
	        		addProjectFinish.setComID(contract.getCompanyId()); // ????????????id
	        		addProjectFinish.setComName(contract.getCompanyName()); // ????????????name
	        		addProjectFinish.setPp7(contract.getProjectLocation()); // ???????????????

	        		List<ZxSaProjectFinishItem> itemList = new ArrayList<>();
	        		// ????????????
	        		ZxSaProjectFinishItem gcjsItem = new ZxSaProjectFinishItem();
	        		gcjsItem.setContractID(contract.getId());
	        		gcjsItem.setOrderNum("1");
	        		gcjsItem.setSettleTypeCode("P2");
	        		gcjsItem.setSettleType("????????????");
	        		gcjsItem.setReportDate(new Date()); // ????????????
	        		gcjsItem.setReportPerson(realName); // ?????????
	        		gcjsItem.setComID(contract.getCompanyId()); // ????????????id
	        		gcjsItem.setComName(contract.getCompanyName()); // ????????????name
	        		itemList.add(gcjsItem);
	        		// ????????????
	        		ZxSaProjectFinishItem cljsItem = new ZxSaProjectFinishItem();
	        		cljsItem.setContractID(contract.getId());
	        		cljsItem.setOrderNum("2");
	        		cljsItem.setSettleTypeCode("P5");
	        		cljsItem.setSettleType("????????????");
	        		cljsItem.setReportDate(new Date()); // ????????????
	        		cljsItem.setReportPerson(realName); // ?????????
	        		cljsItem.setComID(contract.getCompanyId()); // ????????????id
	        		cljsItem.setComName(contract.getCompanyName()); // ????????????name
	        		itemList.add(cljsItem);
	        		// ????????????
	        		ZxSaProjectFinishItem jxjsItem = new ZxSaProjectFinishItem();
	        		jxjsItem.setContractID(contract.getId());
	        		jxjsItem.setOrderNum("3");
	        		jxjsItem.setSettleTypeCode("P6");
	        		jxjsItem.setSettleType("????????????");
	        		jxjsItem.setReportDate(new Date()); // ????????????
	        		jxjsItem.setReportPerson(realName); // ?????????
	        		jxjsItem.setComID(contract.getCompanyId()); // ????????????id
	        		jxjsItem.setComName(contract.getCompanyName()); // ????????????name
	        		itemList.add(jxjsItem);
	        		// ????????????
	        		ZxSaProjectFinishItem qtjsItem = new ZxSaProjectFinishItem();
	        		qtjsItem.setContractID(contract.getId());
	        		qtjsItem.setOrderNum("4");
	        		qtjsItem.setSettleTypeCode("21");
	        		qtjsItem.setSettleType("????????????");
	        		qtjsItem.setReportDate(new Date()); // ????????????
	        		qtjsItem.setReportPerson(realName); // ?????????
	        		qtjsItem.setComID(contract.getCompanyId()); // ????????????id
	        		qtjsItem.setComName(contract.getCompanyName()); // ????????????name
	        		itemList.add(qtjsItem);

	        		addProjectFinish.setItemList(itemList);
	        		
	        		saveZxSaProjectFinish(addProjectFinish);
				}
			});
		}
		return repEntity.ok("???????????????");
	}

	@Override
	public ResponseEntity getZxSaProjectUnFinishList(ZxSaProjectFinish zxSaProjectFinish) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
        	zxSaProjectFinish.setComID("");
            zxSaProjectFinish.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxSaProjectFinish.setComID(zxSaProjectFinish.getOrgID());
        	zxSaProjectFinish.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxSaProjectFinish.setOrgID(zxSaProjectFinish.getOrgID());
        }
        
        List<ZxSaProjectFinish> zxSaProjectFinishList = zxSaProjectFinishMapper.getZxSaProjectUnFinishList(zxSaProjectFinish);
        
		return repEntity.ok(zxSaProjectFinishList == null ? new ArrayList<>() : zxSaProjectFinishList);
	}
}
