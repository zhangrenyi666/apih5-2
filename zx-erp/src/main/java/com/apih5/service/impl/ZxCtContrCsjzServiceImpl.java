package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrCsjzMapper;
import com.apih5.mybatis.dao.ZxCtContrJzBaseMapper;
import com.apih5.mybatis.dao.ZxCtContrJzItemBaseMapper;
import com.apih5.mybatis.dao.ZxCtContrJzItemMapper;
import com.apih5.mybatis.pojo.ZxCtContrCsjz;
import com.apih5.mybatis.pojo.ZxCtContrJzBase;
import com.apih5.mybatis.pojo.ZxCtContrJzItem;
import com.apih5.mybatis.pojo.ZxCtContrJzItemBase;
import com.apih5.service.ZxCtContrCsjzService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxCtContrCsjzService")
public class ZxCtContrCsjzServiceImpl implements ZxCtContrCsjzService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrCsjzMapper zxCtContrCsjzMapper;
    
    @Autowired(required = true)
    private ZxCtContrJzItemMapper zxCtContrJzItemMapper;
    
    @Autowired(required = true)
    private ZxCtContrJzBaseMapper zxCtContrJzBaseMapper;
    
    @Autowired(required = true)
    private ZxCtContrJzItemBaseMapper zxCtContrJzItemBaseMapper;

    @Override
    public ResponseEntity getZxCtContrCsjzListByCondition(ZxCtContrCsjz zxCtContrCsjz) {
        if (zxCtContrCsjz == null) {
            zxCtContrCsjz = new ZxCtContrCsjz();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtContrCsjz.setComID("");
        	zxCtContrCsjz.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxCtContrCsjz.setComID(zxCtContrCsjz.getOrgID());
        	zxCtContrCsjz.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxCtContrCsjz.setOrgID(zxCtContrCsjz.getOrgID());
        }
        
        // ????????????
        PageHelper.startPage(zxCtContrCsjz.getPage(),zxCtContrCsjz.getLimit());
        // ????????????
        List<ZxCtContrCsjz> zxCtContrCsjzList = zxCtContrCsjzMapper.selectByZxCtContrCsjzList(zxCtContrCsjz);
        // ??????????????????
        PageInfo<ZxCtContrCsjz> p = new PageInfo<>(zxCtContrCsjzList);

        return repEntity.okList(zxCtContrCsjzList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrCsjzDetail(ZxCtContrCsjz zxCtContrCsjz) {
    	if (zxCtContrCsjz == null) {
    		zxCtContrCsjz = new ZxCtContrCsjz();
    	}
    	ZxCtContrCsjz dbZxCtContrCsjz = new ZxCtContrCsjz();
    	if(StrUtil.isNotEmpty(zxCtContrCsjz.getWorkId())) {
    		ZxCtContrCsjz contrCsjz = new ZxCtContrCsjz();
    		contrCsjz.setWorkId(zxCtContrCsjz.getWorkId());
    		List<ZxCtContrCsjz> csList = zxCtContrCsjzMapper.selectByZxCtContrCsjzList(contrCsjz);
    		if(csList != null && csList.size() >0) {
    			dbZxCtContrCsjz = csList.get(0);
    		}
    	}else if(StrUtil.isNotEmpty(zxCtContrCsjz.getId())) {
    		// ????????????
    		dbZxCtContrCsjz = zxCtContrCsjzMapper.selectByPrimaryKey(zxCtContrCsjz.getId());
    	}
    	// ????????????
    	if (dbZxCtContrCsjz != null) {
    		return repEntity.ok(dbZxCtContrCsjz);
    	} else {
    		return repEntity.layerMessage("no", "????????????");
    	}
    }

    @Override
    public ResponseEntity saveZxCtContrCsjz(ZxCtContrCsjz zxCtContrCsjz) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        BigDecimal csBudgetAmt = new BigDecimal("0");
        BigDecimal bhBudgetAmt = new BigDecimal("0");
        BigDecimal bhBudgetCost = new BigDecimal("0");
        BigDecimal sgBudgetCost = new BigDecimal("0");
        //????????????
        ZxCtContrCsjz contrCsjz = new ZxCtContrCsjz();
        contrCsjz.setOrgID(zxCtContrCsjz.getOrgID());
        List<ZxCtContrCsjz> contrCsjzList = zxCtContrCsjzMapper.selectByZxCtContrCsjzList(contrCsjz);
        if(contrCsjzList != null && contrCsjzList.size() >0) {
        	 return repEntity.layerMessage("no", "????????????????????????????????????????????????");
        }
        //add
        zxCtContrCsjz.setId(UuidUtil.generate());
        zxCtContrCsjz.setApih5FlowStatus("-1");
        zxCtContrCsjz.setCsBudgetAmt(csBudgetAmt);
        zxCtContrCsjz.setBhBudgetAmt(bhBudgetAmt);
        zxCtContrCsjz.setBhBudgetCost(bhBudgetCost);
        zxCtContrCsjz.setSgBudgetCost(sgBudgetCost);
        zxCtContrCsjz.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrCsjzMapper.insert(zxCtContrCsjz);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrCsjz);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrCsjz(ZxCtContrCsjz zxCtContrCsjz) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrCsjz dbZxCtContrCsjz = zxCtContrCsjzMapper.selectByPrimaryKey(zxCtContrCsjz.getId());
        if (dbZxCtContrCsjz != null && StrUtil.isNotEmpty(dbZxCtContrCsjz.getId())) {
           // ????????????
           dbZxCtContrCsjz.setBuildTime(zxCtContrCsjz.getBuildTime());
           // ?????????id
           dbZxCtContrCsjz.setReporterID(zxCtContrCsjz.getReporterID());
           // ?????????
           dbZxCtContrCsjz.setReporter(zxCtContrCsjz.getReporter());
           // ????????????id
           dbZxCtContrCsjz.setInstProcessId(zxCtContrCsjz.getInstProcessId());
           // work_id
           dbZxCtContrCsjz.setWorkId(zxCtContrCsjz.getWorkId());
           if(StrUtil.isNotEmpty(zxCtContrCsjz.getApih5FlowStatus())) {
        	   // ????????????
        	   dbZxCtContrCsjz.setApih5FlowStatus(zxCtContrCsjz.getApih5FlowStatus());
           }
           //???????????????
			if(StrUtil.equals("opinionField1", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField1(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField1()));
			}
			//
			if(StrUtil.equals("opinionField2", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField2(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField2()));
			}
			//
			if(StrUtil.equals("opinionField3", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField3(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField3()));
			}
			//
			if(StrUtil.equals("opinionField4", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField4(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField4()));
			}
			//
			if(StrUtil.equals("opinionField5", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField5(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField5()));
			}
			//
			if(StrUtil.equals("opinionField6", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField6(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField6()));
			}
			//
			if(StrUtil.equals("opinionField7", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField7(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField7()));
			}
			//
			if(StrUtil.equals("opinionField8", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField8(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField8()));
			}
			//
			if(StrUtil.equals("opinionField9", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField9(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField9()));
			}
			//
			if(StrUtil.equals("opinionField10", zxCtContrCsjz.getOpinionField(), true)){
				dbZxCtContrCsjz.setOpinionField10(zxCtContrCsjz.getOpinionContent(realName, dbZxCtContrCsjz.getOpinionField10()));
			}
           // ??????
           dbZxCtContrCsjz.setItem(zxCtContrCsjz.getItem());
           // isReport
           dbZxCtContrCsjz.setIsReport(zxCtContrCsjz.getIsReport());
           // isFlag
           dbZxCtContrCsjz.setIsFlag(zxCtContrCsjz.getIsFlag());
           // auditSys
           dbZxCtContrCsjz.setAuditSys(zxCtContrCsjz.getAuditSys());
           // auditWorkitemID
           dbZxCtContrCsjz.setAuditWorkitemID(zxCtContrCsjz.getAuditWorkitemID());
           // ??????
           dbZxCtContrCsjz.setRemarks(zxCtContrCsjz.getRemarks());
           // ??????
           dbZxCtContrCsjz.setModifyUserInfo(userKey, realName);
           flag = zxCtContrCsjzMapper.updateByPrimaryKey(dbZxCtContrCsjz);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtContrCsjz);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrCsjz(List<ZxCtContrCsjz> zxCtContrCsjzList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        if (zxCtContrCsjzList != null && zxCtContrCsjzList.size() > 0) {
        	//??????
    		JSONArray jsonArray = new JSONArray();
        	for (ZxCtContrCsjz zxCtContrCsjz : zxCtContrCsjzList) {
        		jsonArray.add(zxCtContrCsjz.getWorkId());
        		ZxCtContrJzItem delItem = new ZxCtContrJzItem();
        		delItem.setMainID(zxCtContrCsjz.getId());
        		List<ZxCtContrJzItem> delItemList = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(delItem);
        		if(delItemList != null && delItemList.size() >0) {
        			flag = zxCtContrJzItemMapper.batchDeleteUpdateZxCtContrJzItem(delItemList, delItem);
        		}
        	}
        	String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
        	String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
        	ZxCtContrCsjz zxCtContrCsjz = new ZxCtContrCsjz();
        	zxCtContrCsjz.setModifyUserInfo(userKey, realName);
        	flag = zxCtContrCsjzMapper.batchDeleteUpdateZxCtContrCsjz(zxCtContrCsjzList, zxCtContrCsjz);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtContrCsjzList);
        }
    }
    

	@Override
	public ResponseEntity getZxCtContrCsjzItemList(ZxCtContrCsjz zxCtContrCsjz) {
		ZxCtContrCsjz dbZxCtContrCsjz = zxCtContrCsjzMapper.selectByPrimaryKey(zxCtContrCsjz.getId());
		if (dbZxCtContrCsjz != null && StrUtil.isNotEmpty(dbZxCtContrCsjz.getId())) {
			ZxCtContrJzItem item = new ZxCtContrJzItem();
			item.setMainID(dbZxCtContrCsjz.getId());
			List<ZxCtContrJzItem> itemList = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(item);
			if(itemList != null && itemList.size() >0) {
				zxCtContrCsjz.setSubType2(itemList.get(0).getSubType2());
				zxCtContrCsjz.setItemBaseList(new ArrayList<>());
				zxCtContrCsjz.setItemList(itemList);
				return repEntity.ok(zxCtContrCsjz);
			}else {
				//??????list???????????????????????????????????????????????????????????????????????????????????????????????????????????????
				ZxCtContrJzBase base = new ZxCtContrJzBase();
				base.setOrgID(dbZxCtContrCsjz.getOrgID());
				base.setType("1");
				List<ZxCtContrJzBase> baseList = zxCtContrJzBaseMapper.selectByZxCtContrJzBaseList(base);
				if(baseList != null && baseList.size() >0) {
					ZxCtContrJzItemBase itemBase = new ZxCtContrJzItemBase();
					itemBase.setMainID(baseList.get(0).getId());
					List<ZxCtContrJzItemBase> itemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(itemBase);
					if(itemBaseList != null && itemBaseList.size() >0) {
						zxCtContrCsjz.setSubType2(itemBaseList.get(0).getSubType2());
						zxCtContrCsjz.setItemList(new ArrayList<>());
						zxCtContrCsjz.setItemBaseList(itemBaseList);
						// ??????????????????
						return repEntity.ok(zxCtContrCsjz);
					}
				}
			}
		}
		return repEntity.okList(null, 0);
	}
    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
	@Override
	public ResponseEntity getZxCtContrCsjzListApih5FlowStatus2ForDq(ZxCtContrCsjz zxCtContrCsjz) {
		if (zxCtContrCsjz == null) {
			zxCtContrCsjz = new ZxCtContrCsjz();
		}
		zxCtContrCsjz.setApih5FlowStatus("2");
		// ????????????
		PageHelper.startPage(zxCtContrCsjz.getPage(),zxCtContrCsjz.getLimit());
		// ????????????
		List<ZxCtContrCsjz> zxCtContrCsjzList = zxCtContrCsjzMapper.selectByZxCtContrCsjzList(zxCtContrCsjz);
		// ??????????????????
		PageInfo<ZxCtContrCsjz> p = new PageInfo<>(zxCtContrCsjzList);

		return repEntity.okList(zxCtContrCsjzList, p.getTotal());
	}
}
