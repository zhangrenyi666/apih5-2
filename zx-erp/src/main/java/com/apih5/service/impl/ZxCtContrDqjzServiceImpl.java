package com.apih5.service.impl;

import java.util.ArrayList;
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
import com.apih5.mybatis.dao.ZxCtContrCsjzMapper;
import com.apih5.mybatis.dao.ZxCtContrDqjzMapper;
import com.apih5.mybatis.dao.ZxCtContrJzBaseMapper;
import com.apih5.mybatis.dao.ZxCtContrJzItemBaseMapper;
import com.apih5.mybatis.dao.ZxCtContrJzItemMapper;
import com.apih5.mybatis.pojo.ZxCtContrCsjz;
import com.apih5.mybatis.pojo.ZxCtContrDqjz;
import com.apih5.mybatis.pojo.ZxCtContrJzBase;
import com.apih5.mybatis.pojo.ZxCtContrJzItem;
import com.apih5.mybatis.pojo.ZxCtContrJzItemBase;
import com.apih5.service.ZxCtContrDqjzService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxCtContrDqjzService")
public class ZxCtContrDqjzServiceImpl implements ZxCtContrDqjzService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrDqjzMapper zxCtContrDqjzMapper;

    @Autowired(required = true)
    private ZxCtContrJzItemMapper zxCtContrJzItemMapper;

    @Autowired(required = true)
    private ZxCtContrJzBaseMapper zxCtContrJzBaseMapper;
    
    @Autowired(required = true)
    private ZxCtContrJzItemBaseMapper zxCtContrJzItemBaseMapper;
    
    @Autowired(required = true)
    private ZxCtContrCsjzMapper zxCtContrCsjzMapper;

    @Override
    public ResponseEntity getZxCtContrDqjzListByCondition(ZxCtContrDqjz zxCtContrDqjz) {
        if (zxCtContrDqjz == null) {
            zxCtContrDqjz = new ZxCtContrDqjz();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtContrDqjz.setComID("");
        	zxCtContrDqjz.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtContrDqjz.setComID(zxCtContrDqjz.getOrgID());
        	zxCtContrDqjz.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtContrDqjz.setOrgID(zxCtContrDqjz.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxCtContrDqjz.getPage(),zxCtContrDqjz.getLimit());
        // 获取数据
        List<ZxCtContrDqjz> zxCtContrDqjzList = zxCtContrDqjzMapper.selectByZxCtContrDqjzList(zxCtContrDqjz);

        //查询明细
        for (ZxCtContrDqjz ctIectContrDqjz1 : zxCtContrDqjzList) {
            ZxCtContrJzItem zxCtContrJzItem = new ZxCtContrJzItem();
            zxCtContrJzItem.setMainID(ctIectContrDqjz1.getOrgID());
            List<ZxCtContrJzItem> zxCtContrJzItems = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(zxCtContrJzItem);
            ctIectContrDqjz1.setZxCtContrJzItemList(zxCtContrJzItems);
        }
        // 得到分页信息
        PageInfo<ZxCtContrDqjz> p = new PageInfo<>(zxCtContrDqjzList);

        return repEntity.okList(zxCtContrDqjzList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrDqjzDetail(ZxCtContrDqjz zxCtContrDqjz) {
        if (zxCtContrDqjz == null) {
            zxCtContrDqjz = new ZxCtContrDqjz();
        }
        ZxCtContrDqjz dbZxCtContrDqjz = new ZxCtContrDqjz();
    	if(StrUtil.isNotEmpty(zxCtContrDqjz.getWorkId())) {
    		ZxCtContrDqjz contrCsjz = new ZxCtContrDqjz();
    		contrCsjz.setWorkId(zxCtContrDqjz.getWorkId());
    		List<ZxCtContrDqjz> csList = zxCtContrDqjzMapper.selectByZxCtContrDqjzList(contrCsjz);
    		if(csList != null && csList.size() >0) {
    			dbZxCtContrDqjz = csList.get(0);
    		}
    	}else if(StrUtil.isNotEmpty(zxCtContrDqjz.getId())) {
    		// 获取数据
    		dbZxCtContrDqjz = zxCtContrDqjzMapper.selectByPrimaryKey(zxCtContrDqjz.getId());
    	}
    	// 数据存在
    	if (dbZxCtContrDqjz != null) {
    		 ZxCtContrJzItem zxCtContrJzItem = new ZxCtContrJzItem();
             zxCtContrJzItem.setMainID(dbZxCtContrDqjz.getOrgID());
             List<ZxCtContrJzItem> zxCtContrJzItems = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(zxCtContrJzItem);
             dbZxCtContrDqjz.setZxCtContrJzItemList(zxCtContrJzItems);
    		return repEntity.ok(dbZxCtContrDqjz);
    	} else {
    		return repEntity.layerMessage("no", "无数据！");
    	}
    }

    @Override
    public ResponseEntity saveZxCtContrDqjz(ZxCtContrDqjz zxCtContrDqjz) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

        //判断该项目上一次是否审批完成
        ZxCtContrDqjz dqjzSelect = new ZxCtContrDqjz();
        dqjzSelect.setOrgID(zxCtContrDqjz.getOrgID());
        dqjzSelect.setApih5FlowStatus("2");
        List<ZxCtContrDqjz> dqjzList = zxCtContrDqjzMapper.selectByZxCtContrDqjzUnFinishList(dqjzSelect);
        if(dqjzList != null && dqjzList.size() >0) {
        	return repEntity.layerMessage("no", "该项目上次还未审核完，请先去审批！");
        }

        
        ZxCtContrCsjz csjz = new ZxCtContrCsjz();
        csjz.setOrgID(zxCtContrDqjz.getOrgID());
        List<ZxCtContrCsjz> csjzs = zxCtContrCsjzMapper.selectByZxCtContrCsjzList(csjz);
        if(csjzs != null && csjzs.size() >0) {
        	//初始预计总收入	规则：调用初始建造合同-初始预计总收入 数据
        	zxCtContrDqjz.setCsBudgetAmt(csjzs.get(0).getCsBudgetAmt());
        	//初始预计总成本	字符		规则：调用初始建造合同-初始预计总成本 数据
        	zxCtContrDqjz.setBhBudgetCost(csjzs.get(0).getSgBudgetCost());
        	 //判断该项目是不是第一次新增
            ZxCtContrDqjz dqjz = new ZxCtContrDqjz();
            dqjz.setOrgID(zxCtContrDqjz.getOrgID());
            List<ZxCtContrDqjz> dqjzs = zxCtContrDqjzMapper.selectByZxCtContrDqjzList(dqjz);
            if(dqjzs != null && dqjzs.size() >0) {
            	zxCtContrDqjz.setBhBudgetAmt(dqjzs.get(0).getBhBudgetAmt());
            	zxCtContrDqjz.setSgBudgetCost(dqjzs.get(0).getSgBudgetCost());
            }else {
            	zxCtContrDqjz.setBhBudgetAmt(zxCtContrDqjz.getCsBudgetAmt());
            	zxCtContrDqjz.setSgBudgetCost(zxCtContrDqjz.getBhBudgetCost());
            }
        	//当前预计总收入			规则：第一次新建时：等于  初始预计总收入
            //                       清单编辑完数据后，更新为清单中当前预计总收入的金额
            //                       第二次新建时：等于 前一次评审通过后的“当前预计总收入”
            //                       清单编辑完数据后，更新为清单中当前预计总收入的金额
            //                       后面以此类推
        	//当前预计总成本                    规则：第一次新建时：等于  初始预计总成本
            //                      清单编辑完数据后，更新为清单中当前预计总成本的金额
            //                      第二次新建时：等于 前一次评审通过后的“当前预计总成本”
            //                      清单编辑完数据后，更新为清单中当前预计总成本的金额
            //                      后面以此类推
        }
        zxCtContrDqjz.setId(UuidUtil.generate());
        zxCtContrDqjz.setApih5FlowStatus("-1");
        zxCtContrDqjz.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrDqjzMapper.insert(zxCtContrDqjz);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrDqjz);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrDqjz(ZxCtContrDqjz zxCtContrDqjz) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrDqjz dbZxCtContrDqjz = zxCtContrDqjzMapper.selectByPrimaryKey(zxCtContrDqjz.getId());
        if (dbZxCtContrDqjz != null && StrUtil.isNotEmpty(dbZxCtContrDqjz.getId())) {
           // 建立时间
           dbZxCtContrDqjz.setBuildTime(zxCtContrDqjz.getBuildTime());
           // 发起人id
           dbZxCtContrDqjz.setReporterID(zxCtContrDqjz.getReporterID());
           // 发起人
           dbZxCtContrDqjz.setReporter(zxCtContrDqjz.getReporter());
           // 初始预计总收入
           dbZxCtContrDqjz.setCsBudgetAmt(zxCtContrDqjz.getCsBudgetAmt());
           // 当前预计总收入
           dbZxCtContrDqjz.setBhBudgetAmt(zxCtContrDqjz.getBhBudgetAmt());
           // 初始预计总成本
           dbZxCtContrDqjz.setBhBudgetCost(zxCtContrDqjz.getBhBudgetCost());
           // 当前预计总成本
           dbZxCtContrDqjz.setSgBudgetCost(zxCtContrDqjz.getSgBudgetCost());
           // 流程实例id
           dbZxCtContrDqjz.setInstProcessId(zxCtContrDqjz.getInstProcessId());
           // workId
           dbZxCtContrDqjz.setWorkId(zxCtContrDqjz.getWorkId());
           if(StrUtil.isNotEmpty(zxCtContrDqjz.getApih5FlowStatus())) {
        	   // 审批状态
        	   dbZxCtContrDqjz.setApih5FlowStatus(zxCtContrDqjz.getApih5FlowStatus());
           }
           //流程的意见
			if(StrUtil.equals("opinionField1", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField1(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField1()));
			}
			//
			if(StrUtil.equals("opinionField2", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField2(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField2()));
			}
			//
			if(StrUtil.equals("opinionField3", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField3(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField3()));
			}
			//
			if(StrUtil.equals("opinionField4", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField4(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField4()));
			}
			//
			if(StrUtil.equals("opinionField5", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField5(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField5()));
			}
			//
			if(StrUtil.equals("opinionField6", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField6(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField6()));
			}
			//
			if(StrUtil.equals("opinionField7", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField7(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField7()));
			}
			//
			if(StrUtil.equals("opinionField8", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField8(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField8()));
			}
			//
			if(StrUtil.equals("opinionField9", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField9(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField9()));
			}
			//
			if(StrUtil.equals("opinionField10", zxCtContrDqjz.getOpinionField(), true)){
				dbZxCtContrDqjz.setOpinionField10(zxCtContrDqjz.getOpinionContent(realName, dbZxCtContrDqjz.getOpinionField10()));
			}
			// 清单
           dbZxCtContrDqjz.setItem(zxCtContrDqjz.getItem());
           // 说明
           dbZxCtContrDqjz.setRemark(zxCtContrDqjz.getRemark());
           // isReport
           dbZxCtContrDqjz.setIsReport(zxCtContrDqjz.getIsReport());
           // isFlag
           dbZxCtContrDqjz.setIsFlag(zxCtContrDqjz.getIsFlag());
           // auditSys
           dbZxCtContrDqjz.setAuditSys(zxCtContrDqjz.getAuditSys());
           // auditWorkitemID
           dbZxCtContrDqjz.setAuditWorkitemID(zxCtContrDqjz.getAuditWorkitemID());
           // 上期预计总收入
           dbZxCtContrDqjz.setBhBudgetAmtUp(zxCtContrDqjz.getBhBudgetAmtUp());
           // 上期预计总成本
           dbZxCtContrDqjz.setSgBudgetCostUp(zxCtContrDqjz.getSgBudgetCostUp());
           // isFlagZhb
           dbZxCtContrDqjz.setIsFlagZhb(zxCtContrDqjz.getIsFlagZhb());
           // contractAudit
           dbZxCtContrDqjz.setContractAudit(zxCtContrDqjz.getContractAudit());
           // 共通
           dbZxCtContrDqjz.setModifyUserInfo(userKey, realName);
           flag = zxCtContrDqjzMapper.updateByPrimaryKey(dbZxCtContrDqjz);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtContrDqjz);
        }
    }
    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrDqjz(List<ZxCtContrDqjz> zxCtContrDqjzList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);  
        int flag = 0;
        if (zxCtContrDqjzList != null && zxCtContrDqjzList.size() > 0) {
        	JSONArray jsonArray = new JSONArray();
        	for (ZxCtContrDqjz zxCtContrDqjz : zxCtContrDqjzList) {
        		//删除明细
        		ZxCtContrJzItem zxCtContrJzItem = new ZxCtContrJzItem();
        		zxCtContrJzItem.setMainID(zxCtContrDqjz.getOrgID());
        		List<ZxCtContrJzItem> zxCtContrJzItems = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(zxCtContrJzItem);
        		if (zxCtContrJzItems != null && zxCtContrJzItems.size() > 0) {
        			zxCtContrJzItem.setModifyUserInfo(userKey,realName);
        			zxCtContrJzItemMapper.batchDeleteUpdateZxCtContrJzItem(zxCtContrJzItems,zxCtContrJzItem);
        		}
        	}
        	String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
        	String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
        	ZxCtContrDqjz zxCtContrDqjz = new ZxCtContrDqjz();
        	zxCtContrDqjz.setModifyUserInfo(userKey, realName);
        	flag = zxCtContrDqjzMapper.batchDeleteUpdateZxCtContrDqjz(zxCtContrDqjzList, zxCtContrDqjz);
        }
        // 失败
        if (flag == 0) {
        	return repEntity.errorSave();
        } else {
        	return repEntity.ok("sys.data.delete",zxCtContrDqjzList);
        }
    }

    @Override
	public ResponseEntity getZxCtContrDqjzItemList(ZxCtContrDqjz zxCtContrDqjz) {
		ZxCtContrDqjz dbZxCtContrDqjz = zxCtContrDqjzMapper.selectByPrimaryKey(zxCtContrDqjz.getId());
		if (dbZxCtContrDqjz != null && StrUtil.isNotEmpty(dbZxCtContrDqjz.getId())) {

			ZxCtContrJzItem item = new ZxCtContrJzItem();
			item.setMainID(dbZxCtContrDqjz.getId());
			List<ZxCtContrJzItem> itemList = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(item);
			if(itemList != null && itemList.size() >0) {
				zxCtContrDqjz.setSubType2(itemList.get(0).getSubType2());
				zxCtContrDqjz.setItemBaseList(new ArrayList<>());
				zxCtContrDqjz.setItemList(itemList);
				return repEntity.ok(zxCtContrDqjz);
			}else {
				//如果list是空则证明是首次新增点进去的，这个时候要去【项目初始建造合同清单】里查数据
				
				ZxCtContrJzBase base = new ZxCtContrJzBase();
				base.setOrgID(dbZxCtContrDqjz.getOrgID());
				base.setType("2");
				List<ZxCtContrJzBase> baseList = zxCtContrJzBaseMapper.selectByZxCtContrJzBaseList(base);
				if(baseList != null && baseList.size() >0) {
					ZxCtContrJzItemBase itemBase = new ZxCtContrJzItemBase();
					itemBase.setMainID(baseList.get(0).getId());
					List<ZxCtContrJzItemBase> itemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(itemBase);
					if(itemBaseList != null && itemBaseList.size() >0) {
						zxCtContrDqjz.setSubType2(itemBaseList.get(0).getSubType2());
						zxCtContrDqjz.setItemList(new ArrayList<>());
						zxCtContrDqjz.setItemBaseList(itemBaseList);
						return repEntity.ok(zxCtContrDqjz);
					}
				}
			}
		}
		return repEntity.okList(null, 0);
	}
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
