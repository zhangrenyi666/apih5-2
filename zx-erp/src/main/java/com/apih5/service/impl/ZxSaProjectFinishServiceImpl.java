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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
        	zxSaProjectFinish.setComID("");
            zxSaProjectFinish.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSaProjectFinish.setComID(zxSaProjectFinish.getOrgID());
        	zxSaProjectFinish.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSaProjectFinish.setOrgID(zxSaProjectFinish.getOrgID());
        }
        
        // 分页查询
        PageHelper.startPage(zxSaProjectFinish.getPage(),zxSaProjectFinish.getLimit());
        // 获取数据
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
        // 得到分页信息
        PageInfo<ZxSaProjectFinish> p = new PageInfo<>(zxSaProjectFinishList);

        return repEntity.okList(zxSaProjectFinishList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaProjectFinishDetails(ZxSaProjectFinish zxSaProjectFinish) {
        if (zxSaProjectFinish == null) {
            zxSaProjectFinish = new ZxSaProjectFinish();
        }
        // 获取数据
        ZxSaProjectFinish dbZxSaProjectFinish = zxSaProjectFinishMapper.selectByPrimaryKey(zxSaProjectFinish.getId());
        // 数据存在
        if (dbZxSaProjectFinish != null) {
            return repEntity.ok(dbZxSaProjectFinish);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 合同id
           dbzxSaProjectFinish.setContractID(zxSaProjectFinish.getContractID());
           // 合同编号
           dbzxSaProjectFinish.setContractNo(zxSaProjectFinish.getContractNo());
           // 合同名称
           dbzxSaProjectFinish.setContractName(zxSaProjectFinish.getContractName());
           // 项目id
           dbzxSaProjectFinish.setOrgID(zxSaProjectFinish.getOrgID());
           // 项目名称
           dbzxSaProjectFinish.setOrgName(zxSaProjectFinish.getOrgName());
           // 合同总造价（万元）
           dbzxSaProjectFinish.setContractCost(zxSaProjectFinish.getContractCost());
           // 当前合同总造价（万元）
           dbzxSaProjectFinish.setContractMoney(zxSaProjectFinish.getContractMoney());
           // 项目经理
           dbzxSaProjectFinish.setProjectManager(zxSaProjectFinish.getProjectManager());
           // 合同开工日期
           dbzxSaProjectFinish.setActualStartDate(zxSaProjectFinish.getActualStartDate());
           // 合同竣工日期
           dbzxSaProjectFinish.setActualEndDate(zxSaProjectFinish.getActualEndDate());
           // 合同工期（天）
           dbzxSaProjectFinish.setCsTimeLimit(zxSaProjectFinish.getCsTimeLimit());
           // 实际开工日期
           dbzxSaProjectFinish.setRealBeginDate(zxSaProjectFinish.getRealBeginDate());
           // 实际竣工日期
           dbzxSaProjectFinish.setRealEndDate(zxSaProjectFinish.getRealEndDate());
           // 实际完工日期
           if (dbzxSaProjectFinish.getRealSettleEndDate() != null && zxSaProjectFinish.getRealSettleEndDate() != null) {
        	   if (DateUtil.compare(dbzxSaProjectFinish.getRealSettleEndDate(), zxSaProjectFinish.getRealSettleEndDate()) != 0) {
        		   isChange = true;
        	   }
           }
           dbzxSaProjectFinish.setRealSettleEndDate(zxSaProjectFinish.getRealSettleEndDate());
           // 计划结算关闭日期
           dbzxSaProjectFinish.setPlanSettleCloseDate(zxSaProjectFinish.getPlanSettleCloseDate());
           // 实际结算关闭日期
           dbzxSaProjectFinish.setRealSettleCloseDate(zxSaProjectFinish.getRealSettleCloseDate());
           // 填报日期
           dbzxSaProjectFinish.setReportDate(zxSaProjectFinish.getReportDate());
           // 填报人
           dbzxSaProjectFinish.setReportPerson(zxSaProjectFinish.getReportPerson());
           // 备注
           dbzxSaProjectFinish.setRemark(zxSaProjectFinish.getRemark());
           // 完工状态
           dbzxSaProjectFinish.setFinishStatus(zxSaProjectFinish.getFinishStatus());
           // 状态
           dbzxSaProjectFinish.setAuditStatus(zxSaProjectFinish.getAuditStatus());
           dbzxSaProjectFinish.setDetermineFilingDate(zxSaProjectFinish.getDetermineFilingDate());
           // 所属公司id
           dbzxSaProjectFinish.setComID(zxSaProjectFinish.getComID());
           // 所属公司name
           dbzxSaProjectFinish.setComName(zxSaProjectFinish.getComName());
           // 所属公司排序
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
           // 项目所在地
           dbzxSaProjectFinish.setPp7(zxSaProjectFinish.getPp7());
           // 
           dbzxSaProjectFinish.setPp8(zxSaProjectFinish.getPp8());
           // 
           dbzxSaProjectFinish.setPp9(zxSaProjectFinish.getPp9());
           // 
           dbzxSaProjectFinish.setPp10(zxSaProjectFinish.getPp10());
           // 
           dbzxSaProjectFinish.setGuidangDate(zxSaProjectFinish.getGuidangDate());
           // 共通
           dbzxSaProjectFinish.setModifyUserInfo(userKey, realName);
           
           if (dbzxSaProjectFinish.getRealSettleEndDate() != null && DateUtil.compare(DateUtil.endOfDay(new Date()), DateUtil.endOfDay(dbzxSaProjectFinish.getRealSettleEndDate())) > 0) {
   				zxSaProjectFinish.setFinishStatus("1");
			} else {
				zxSaProjectFinish.setFinishStatus("0");
			}
           
           flag = zxSaProjectFinishMapper.updateByPrimaryKey(dbzxSaProjectFinish);
           //先删除再新增
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改项目立项主体完工日期
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
        	//删除
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
        // 失败
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
		
		// 获取到【业主合同台账】已审核状态的数据。更新到【完工审核】中
		ZxCtContract zxCtContract = new ZxCtContract();
		zxCtContract.setContrStatus("1");
		zxCtContract.setOrgID(zxSaProjectFinish.getOrgID());
		List<ZxCtContract> zxCtContractList = (List<ZxCtContract>) zxCtContractService.getZxCtContractListByCondition(zxCtContract).getData();
		if (zxCtContractList != null && zxCtContractList.size() > 0) {
			zxCtContractList.forEach(contract -> {
				String contractId = contract.getId();
				// 查询完工审核信息
				ZxSaProjectFinish selectProjectFinish = new ZxSaProjectFinish();
				selectProjectFinish.setContractID(contractId);
				List<ZxSaProjectFinish> projectFinishList = zxSaProjectFinishMapper.selectByZxSaProjectFinishList(selectProjectFinish);
				
				// 有则更新无则新增
				if (projectFinishList != null && projectFinishList.size() > 0) {
					ZxSaProjectFinish dbZxSaProjectFinish = projectFinishList.get(0);
					dbZxSaProjectFinish.setContractNo(contract.getContractNo()); // 合同编号
					dbZxSaProjectFinish.setContractName(contract.getContractName()); // 合同名称
					dbZxSaProjectFinish.setOrgID(contract.getOrgID()); // 项目id
					dbZxSaProjectFinish.setOrgName(contract.getOrgName()); // 项目名称
					dbZxSaProjectFinish.setContractCost(contract.getContractCost()); // 合同总造价（万元）
					dbZxSaProjectFinish.setContractMoney(contract.getContractMoney()); // 当前合同总造价（万元）
					dbZxSaProjectFinish.setProjectManager(contract.getProjectChiefName()); // 项目经理
					dbZxSaProjectFinish.setActualStartDate(contract.getActualStartDate()); // 合同开工日期
					dbZxSaProjectFinish.setActualEndDate(contract.getActualEndDate()); // 合同竣工日期
					dbZxSaProjectFinish.setCsTimeLimit(contract.getCsTimeLimit()); // 合同工期（天）
					dbZxSaProjectFinish.setRealBeginDate(contract.getRealBeginDate()); // 实际开工日期
					dbZxSaProjectFinish.setRealEndDate(contract.getRealEndDate());// 实际竣工日期
					dbZxSaProjectFinish.setRealSettleEndDate(contract.getRealEndDate()); // 实际完工日期
					dbZxSaProjectFinish.setPp1(contract.getSubArea()); // 项目所在区域
	        		if(contract.getRealEndDate() != null) {
	        			dbZxSaProjectFinish.setPlanSettleCloseDate(DateUtil.offsetMonth(contract.getRealEndDate(), 3)); // 计划结算关闭日期
	        			dbZxSaProjectFinish.setRealSettleCloseDate(DateUtil.offsetMonth(contract.getRealEndDate(), 3)); // 实际结算关闭日期
	        		}
	        		dbZxSaProjectFinish.setPp7(contract.getProjectLocation()); // 项目所在地
					updateZxSaProjectFinish(dbZxSaProjectFinish);
				} else {
					// 增加完工审核
	        		ZxSaProjectFinish addProjectFinish = new ZxSaProjectFinish();
	        		addProjectFinish.setContractID(contract.getId()); // 合同id
	        		addProjectFinish.setContractNo(contract.getContractNo()); // 合同编号
	        		addProjectFinish.setContractName(contract.getContractName()); // 合同名称
	        		addProjectFinish.setOrgID(contract.getOrgID()); // 项目id
	        		addProjectFinish.setOrgName(contract.getOrgName()); // 项目名称
	        		addProjectFinish.setContractCost(contract.getContractCost()); // 合同总造价（万元）
	        		addProjectFinish.setContractMoney(contract.getContractMoney()); // 当前合同总造价（万元）
	        		addProjectFinish.setProjectManager(contract.getProjectChiefName()); // 项目经理
	        		addProjectFinish.setActualStartDate(contract.getActualStartDate()); // 合同开工日期
	        		addProjectFinish.setActualEndDate(contract.getActualEndDate()); // 合同竣工日期
	        		addProjectFinish.setCsTimeLimit(contract.getCsTimeLimit()); // 合同工期（天）
	        		addProjectFinish.setRealBeginDate(contract.getRealBeginDate()); // 实际开工日期
	        		addProjectFinish.setRealEndDate(contract.getRealEndDate());// 实际竣工日期
	        		addProjectFinish.setRealSettleEndDate(contract.getRealEndDate()); // 实际完工日期
	        		addProjectFinish.setPp1(contract.getSubArea()); // 项目所在区域
	        		if(contract.getRealEndDate() != null) {
	        			addProjectFinish.setPlanSettleCloseDate(DateUtil.offsetMonth(contract.getRealEndDate(), 3)); // 计划结算关闭日期
	        			addProjectFinish.setRealSettleCloseDate(DateUtil.offsetMonth(contract.getRealEndDate(), 3)); // 实际结算关闭日期
	        		}
	        		addProjectFinish.setReportDate(new Date()); // 填报日期
	        		addProjectFinish.setReportPerson(realName); // 填报人
	        		addProjectFinish.setFinishStatus("0"); // 完工状态
	        		addProjectFinish.setAuditStatus("0"); // 状态
	        		addProjectFinish.setComID(contract.getCompanyId()); // 所属公司id
	        		addProjectFinish.setComName(contract.getCompanyName()); // 所属公司name
	        		addProjectFinish.setPp7(contract.getProjectLocation()); // 项目所在地

	        		List<ZxSaProjectFinishItem> itemList = new ArrayList<>();
	        		// 工程结算
	        		ZxSaProjectFinishItem gcjsItem = new ZxSaProjectFinishItem();
	        		gcjsItem.setContractID(contract.getId());
	        		gcjsItem.setOrderNum("1");
	        		gcjsItem.setSettleTypeCode("P2");
	        		gcjsItem.setSettleType("工程结算");
	        		gcjsItem.setReportDate(new Date()); // 填报日期
	        		gcjsItem.setReportPerson(realName); // 填报人
	        		gcjsItem.setComID(contract.getCompanyId()); // 所属公司id
	        		gcjsItem.setComName(contract.getCompanyName()); // 所属公司name
	        		itemList.add(gcjsItem);
	        		// 材料结算
	        		ZxSaProjectFinishItem cljsItem = new ZxSaProjectFinishItem();
	        		cljsItem.setContractID(contract.getId());
	        		cljsItem.setOrderNum("2");
	        		cljsItem.setSettleTypeCode("P5");
	        		cljsItem.setSettleType("材料结算");
	        		cljsItem.setReportDate(new Date()); // 填报日期
	        		cljsItem.setReportPerson(realName); // 填报人
	        		cljsItem.setComID(contract.getCompanyId()); // 所属公司id
	        		cljsItem.setComName(contract.getCompanyName()); // 所属公司name
	        		itemList.add(cljsItem);
	        		// 机械结算
	        		ZxSaProjectFinishItem jxjsItem = new ZxSaProjectFinishItem();
	        		jxjsItem.setContractID(contract.getId());
	        		jxjsItem.setOrderNum("3");
	        		jxjsItem.setSettleTypeCode("P6");
	        		jxjsItem.setSettleType("机械结算");
	        		jxjsItem.setReportDate(new Date()); // 填报日期
	        		jxjsItem.setReportPerson(realName); // 填报人
	        		jxjsItem.setComID(contract.getCompanyId()); // 所属公司id
	        		jxjsItem.setComName(contract.getCompanyName()); // 所属公司name
	        		itemList.add(jxjsItem);
	        		// 其他结算
	        		ZxSaProjectFinishItem qtjsItem = new ZxSaProjectFinishItem();
	        		qtjsItem.setContractID(contract.getId());
	        		qtjsItem.setOrderNum("4");
	        		qtjsItem.setSettleTypeCode("21");
	        		qtjsItem.setSettleType("其他结算");
	        		qtjsItem.setReportDate(new Date()); // 填报日期
	        		qtjsItem.setReportPerson(realName); // 填报人
	        		qtjsItem.setComID(contract.getCompanyId()); // 所属公司id
	        		qtjsItem.setComName(contract.getCompanyName()); // 所属公司name
	        		itemList.add(qtjsItem);

	        		addProjectFinish.setItemList(itemList);
	        		
	        		saveZxSaProjectFinish(addProjectFinish);
				}
			});
		}
		return repEntity.ok("同步成功！");
	}

	@Override
	public ResponseEntity getZxSaProjectUnFinishList(ZxSaProjectFinish zxSaProjectFinish) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
        	zxSaProjectFinish.setComID("");
            zxSaProjectFinish.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSaProjectFinish.setComID(zxSaProjectFinish.getOrgID());
        	zxSaProjectFinish.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSaProjectFinish.setOrgID(zxSaProjectFinish.getOrgID());
        }
        
        List<ZxSaProjectFinish> zxSaProjectFinishList = zxSaProjectFinishMapper.getZxSaProjectUnFinishList(zxSaProjectFinish);
        
		return repEntity.ok(zxSaProjectFinishList == null ? new ArrayList<>() : zxSaProjectFinishList);
	}
}
