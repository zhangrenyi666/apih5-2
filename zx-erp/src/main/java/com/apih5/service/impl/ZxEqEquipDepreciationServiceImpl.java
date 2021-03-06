package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEquipDepreciationItemMapper;
import com.apih5.mybatis.dao.ZxEqEquipDepreciationMapper;
import com.apih5.mybatis.dao.ZxEqEquipMapper;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqEquipDepreciation;
import com.apih5.mybatis.pojo.ZxEqEquipDepreciationItem;
import com.apih5.service.ZxEqEquipDepreciationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipDepreciationService")
public class ZxEqEquipDepreciationServiceImpl implements ZxEqEquipDepreciationService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipDepreciationMapper zxEqEquipDepreciationMapper;

    @Autowired(required = true)
    private ZxEqEquipDepreciationItemMapper zxEqEquipDepreciationItemMapper;
    
    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;
    
    
    @Override
    public ResponseEntity getZxEqEquipDepreciationListByCondition(ZxEqEquipDepreciation zxEqEquipDepreciation) {
        if (zxEqEquipDepreciation == null) {
            zxEqEquipDepreciation = new ZxEqEquipDepreciation();
        }
        String depreperiod = "";
        
        if(zxEqEquipDepreciation.getDepreperiodDate() != null) {
        	depreperiod = DateUtil.format(zxEqEquipDepreciation.getDepreperiodDate(), "yyyyMM");
        	zxEqEquipDepreciation.setDepreperiod(depreperiod);
        	zxEqEquipDepreciation.setDepreperiodDate(null);
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqEquipDepreciation.setComID("");
        	zxEqEquipDepreciation.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqEquipDepreciation.setComID(zxEqEquipDepreciation.getOrgID());
        	zxEqEquipDepreciation.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqEquipDepreciation.setOrgID(zxEqEquipDepreciation.getOrgID());
        }
        
        // ????????????
        PageHelper.startPage(zxEqEquipDepreciation.getPage(),zxEqEquipDepreciation.getLimit());
        // ????????????
        List<ZxEqEquipDepreciation> zxEqEquipDepreciationList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(zxEqEquipDepreciation);
        for (ZxEqEquipDepreciation zxEqEquipDepreciation2 : zxEqEquipDepreciationList) {
        	ZxEqEquipDepreciationItem item = new ZxEqEquipDepreciationItem();
        	item.setMasID(zxEqEquipDepreciation2.getId());
        	List<ZxEqEquipDepreciationItem> itemList = zxEqEquipDepreciationItemMapper.selectByZxEqEquipDepreciationItemList(item);
        	zxEqEquipDepreciation2.setItemList(itemList);
        	//??????orginalValue  ????????????depreamout  ???????????? leftValue
        	BigDecimal orginalValue = new BigDecimal("0");
        	BigDecimal depreamout = new BigDecimal("0");
        	BigDecimal leftValue = new BigDecimal("0");
        	if(itemList != null && itemList.size() >0) {
        		for (ZxEqEquipDepreciationItem itemSelect : itemList) {
					if(itemSelect.getOrginalValue() != null) {
						orginalValue = CalcUtils.calcAdd(orginalValue, itemSelect.getOrginalValue());
					}
					if(itemSelect.getDepreamout() != null) {
						depreamout = CalcUtils.calcAdd(depreamout, itemSelect.getDepreamout());
					}
					if(itemSelect.getLeftValue() != null) {
						leftValue = CalcUtils.calcAdd(leftValue, itemSelect.getLeftValue());
					}
				}
        	}
        	zxEqEquipDepreciation2.setOrginalValue(orginalValue);
        	zxEqEquipDepreciation2.setDepreamout(depreamout);
        	zxEqEquipDepreciation2.setLeftValue(leftValue);
        }
        // ??????????????????
        PageInfo<ZxEqEquipDepreciation> p = new PageInfo<>(zxEqEquipDepreciationList);

        return repEntity.okList(zxEqEquipDepreciationList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipDepreciationDetails(ZxEqEquipDepreciation zxEqEquipDepreciation) {
        if (zxEqEquipDepreciation == null) {
            zxEqEquipDepreciation = new ZxEqEquipDepreciation();
        }
        // ????????????
        ZxEqEquipDepreciation dbZxEqEquipDepreciation = zxEqEquipDepreciationMapper.selectByPrimaryKey(zxEqEquipDepreciation.getId());
        // ????????????
        if (dbZxEqEquipDepreciation != null) {
            return repEntity.ok(dbZxEqEquipDepreciation);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipDepreciation(ZxEqEquipDepreciation zxEqEquipDepreciation) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String depreperiod = ""; 
        //??????
        if(zxEqEquipDepreciation.getDepreperiodDate() != null) {
        	depreperiod = DateUtil.format(zxEqEquipDepreciation.getDepreperiodDate(), "yyyyMM");
        }else {
        	return repEntity.layerMessage("no", "??????????????????");
        }
        // ??????????????????4432???
        ZxEqEquipDepreciation checkDepreciation = new ZxEqEquipDepreciation();
        checkDepreciation.setOrgID(zxEqEquipDepreciation.getOrgID());
        int y = DateUtil.year(zxEqEquipDepreciation.getDepreperiodDate());
        int m = DateUtil.month(zxEqEquipDepreciation.getDepreperiodDate());
        List<ZxEqEquipDepreciation> checkDepreciationList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(checkDepreciation);
        if(checkDepreciationList != null && checkDepreciationList.size() >0) {
        	if(m == 0) {
        		y = y-1;
        		m = 12;
        		checkDepreciation.setDepreperiod(y+""+m);
        	}else {
        		String auto = String.format("%02d", m); 
        		checkDepreciation.setDepreperiod(y+""+auto);
        	}
        	List<ZxEqEquipDepreciation> checkDepList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(checkDepreciation);
        	if(checkDepList != null && checkDepList.size() >0) {
        		checkDepreciation.setDepreperiod(depreperiod);
        		List<ZxEqEquipDepreciation> checkList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(checkDepreciation);
        		if(checkList != null && checkList.size() >0) {
        			return repEntity.layerMessage("no", "???????????????????????????????????????????????????");
        		}
        	}else {
        		return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????????????????????????????");
        	}
        }
        ZxEqEquipDepreciation depreciationSelect = new ZxEqEquipDepreciation();
        depreciationSelect.setOrgID(zxEqEquipDepreciation.getOrgID());
        depreciationSelect.setAuditStatus("?????????");
        List<ZxEqEquipDepreciation> depreciationSelectList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(depreciationSelect);
        if(depreciationSelectList != null && depreciationSelectList.size() >0) {
        	return repEntity.layerMessage("no", "??????????????????????????????????????????");
        }
        
     
        
        //add
        zxEqEquipDepreciation.setId(UuidUtil.generate());
        zxEqEquipDepreciation.setDepreperiod(depreperiod);	
        zxEqEquipDepreciation.setAuditStatus("?????????");
        zxEqEquipDepreciation.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipDepreciationMapper.insert(zxEqEquipDepreciation);
        if(zxEqEquipDepreciation.getItemList() != null && zxEqEquipDepreciation.getItemList().size() >0) {	
        	for (ZxEqEquipDepreciationItem lib : zxEqEquipDepreciation.getItemList()) {
        		lib.setId(UuidUtil.generate());
    			lib.setMasID(zxEqEquipDepreciation.getId());
    			lib.setOrgID(zxEqEquipDepreciation.getOrgID());
    			lib.setAuditStatus(zxEqEquipDepreciation.getAuditStatus());
    			lib.setCreateUserInfo(userKey, realName);
        		
        		ZxEqEquipDepreciationItem item = new ZxEqEquipDepreciationItem();
        		item.setOrgID(zxEqEquipDepreciation.getOrgID());
        		item.setEquipID(lib.getEquipID());
        		item.setAuditStatus("?????????");
        		List<ZxEqEquipDepreciationItem> itemList = zxEqEquipDepreciationItemMapper.selectByZxEqEquipDepreciationItemList(item);
        		if(itemList != null && itemList.size() >0) {
        			BigDecimal depreamoutTotal = new BigDecimal("0");
        			for (ZxEqEquipDepreciationItem depreciationItem : itemList) {
        				depreamoutTotal = CalcUtils.calcAdd(depreamoutTotal, depreciationItem.getDepreamout());
					}
        			lib.setFinanceOrginalValue(depreamoutTotal);
        		}else {
        			lib.setFinanceOrginalValue(new BigDecimal("0"));
        		}
        		flag = zxEqEquipDepreciationItemMapper.insert(lib);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipDepreciation);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipDepreciation(ZxEqEquipDepreciation zxEqEquipDepreciation) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipDepreciation dbzxEqEquipDepreciation = zxEqEquipDepreciationMapper.selectByPrimaryKey(zxEqEquipDepreciation.getId());
        if (dbzxEqEquipDepreciation != null && StrUtil.isNotEmpty(dbzxEqEquipDepreciation.getId())) {
        	  ZxEqEquipDepreciation depreciationSelect = new ZxEqEquipDepreciation();
              depreciationSelect.setOrgID(zxEqEquipDepreciation.getOrgID());
              depreciationSelect.setIdFlag(dbzxEqEquipDepreciation.getId());
              depreciationSelect.setAuditStatus("?????????");
              List<ZxEqEquipDepreciation> depreciationSelectList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(depreciationSelect);
              if(depreciationSelectList != null && depreciationSelectList.size() >0) {
              	return repEntity.layerMessage("no", "??????????????????????????????????????????");
              }
        	
        	String depreperiod = ""; 
             //??????
             if(zxEqEquipDepreciation.getDepreperiodDate() != null) {
             	depreperiod = DateUtil.format(zxEqEquipDepreciation.getDepreperiodDate(), "yyyyMM");
             }else {
             	return repEntity.layerMessage("no", "??????????????????");
             }
        	// ??????????????????4432???
            ZxEqEquipDepreciation checkDepreciation = new ZxEqEquipDepreciation();
            checkDepreciation.setOrgID(zxEqEquipDepreciation.getOrgID());
            int y = DateUtil.year(zxEqEquipDepreciation.getDepreperiodDate());
            int m = DateUtil.month(zxEqEquipDepreciation.getDepreperiodDate());
            List<ZxEqEquipDepreciation> checkDepreciationList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(checkDepreciation);
            if(checkDepreciationList != null && checkDepreciationList.size() >0) {
            	if(m == 0) {
            		y = y-1;
            		m = 12;
            		checkDepreciation.setDepreperiod(y+""+m);
            	}else {
            		String auto = String.format("%02d", m); 
            		checkDepreciation.setDepreperiod(y+""+auto);
            	}
            	checkDepreciation.setAuditStatus("?????????");
            	List<ZxEqEquipDepreciation> checkDepList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(checkDepreciation);
            	if(checkDepList != null && checkDepList.size() >0) {
            		checkDepreciation.setDepreperiod(depreperiod);
            		checkDepreciation.setIdFlag(dbzxEqEquipDepreciation.getId());
            		List<ZxEqEquipDepreciation> checkList = zxEqEquipDepreciationMapper.selectByZxEqEquipDepreciationList(checkDepreciation);
            		if(checkList != null && checkList.size() >0) {
            			return repEntity.layerMessage("no", "???????????????????????????????????????????????????");
            		}
            	}else {
            		return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????????????????????????????");
            	}
            }
          
            
            
            
            
            
            
        	// ????????????
           dbzxEqEquipDepreciation.setOrgID(zxEqEquipDepreciation.getOrgID());
           // ????????????
           dbzxEqEquipDepreciation.setDepreDate(zxEqEquipDepreciation.getDepreDate());
           // ????????????
           if(zxEqEquipDepreciation.getDepreperiodDate() != null) {
        	   dbzxEqEquipDepreciation.setDepreperiodDate(zxEqEquipDepreciation.getDepreperiodDate());
        	   dbzxEqEquipDepreciation.setDepreperiod(DateUtil.format(zxEqEquipDepreciation.getDepreperiodDate(), "yyyyMM"));	
           }
           // ????????????
           dbzxEqEquipDepreciation.setAuditStatus(zxEqEquipDepreciation.getAuditStatus());
           // ??????
           dbzxEqEquipDepreciation.setRemark(zxEqEquipDepreciation.getRemark());
           // ??????
           dbzxEqEquipDepreciation.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipDepreciationMapper.updateByPrimaryKey(dbzxEqEquipDepreciation);
           //??????????????????
           ZxEqEquipDepreciationItem delLib = new ZxEqEquipDepreciationItem();
           delLib.setMasID(zxEqEquipDepreciation.getId());
           List<ZxEqEquipDepreciationItem> delLibList = zxEqEquipDepreciationItemMapper.selectByZxEqEquipDepreciationItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqEquipDepreciationItemMapper.batchDeleteUpdateZxEqEquipDepreciationItem(delLibList, delLib);
           }
           if(zxEqEquipDepreciation.getItemList() != null && zxEqEquipDepreciation.getItemList().size() >0) {	
        	   for (ZxEqEquipDepreciationItem lib : zxEqEquipDepreciation.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMasID(zxEqEquipDepreciation.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqEquipDepreciationItemMapper.insert(lib);
        		 
//        		   ZxEqEquip eqEquip = zxEqEquipMapper.selectByPrimaryKey(lib.getEquipID());
//        		   if(eqEquip != null && StrUtil.isNotEmpty(eqEquip.getId())) {
//        			   eqEquip.setLeftValue(lib.getLeftValue());
//        			   eqEquip.setModifyUserInfo(userKey, realName);
//        			   flag = zxEqEquipMapper.updateByPrimaryKey(eqEquip);
//        		   }
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEquipDepreciation);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipDepreciation(List<ZxEqEquipDepreciation> zxEqEquipDepreciationList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipDepreciationList != null && zxEqEquipDepreciationList.size() > 0) {
        	for (ZxEqEquipDepreciation zxEqEquipDepreciation : zxEqEquipDepreciationList) {
    			ZxEqEquipDepreciationItem delLib = new ZxEqEquipDepreciationItem();
    			delLib.setMasID(zxEqEquipDepreciation.getId());
    			List<ZxEqEquipDepreciationItem> delLibList = zxEqEquipDepreciationItemMapper.selectByZxEqEquipDepreciationItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqEquipDepreciationItemMapper.batchDeleteUpdateZxEqEquipDepreciationItem(delLibList, delLib);
    			}
    		}
        	ZxEqEquipDepreciation zxEqEquipDepreciation = new ZxEqEquipDepreciation();
           zxEqEquipDepreciation.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipDepreciationMapper.batchDeleteUpdateZxEqEquipDepreciation(zxEqEquipDepreciationList, zxEqEquipDepreciation);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipDepreciationList);
        }
    }

	@Override
	public ResponseEntity auditZxEqEquipDepreciation(ZxEqEquipDepreciation zxEqEquipDepreciation) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipDepreciation dbzxEqEquipDepreciation = zxEqEquipDepreciationMapper.selectByPrimaryKey(zxEqEquipDepreciation.getId());
        if (dbzxEqEquipDepreciation != null && StrUtil.isNotEmpty(dbzxEqEquipDepreciation.getId())) {
           // ????????????
           dbzxEqEquipDepreciation.setAuditStatus("?????????");
           // ??????
           dbzxEqEquipDepreciation.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipDepreciationMapper.updateByPrimaryKey(dbzxEqEquipDepreciation);
        
           ZxEqEquipDepreciationItem item = new ZxEqEquipDepreciationItem();
           item.setMasID(dbzxEqEquipDepreciation.getId());
           List<ZxEqEquipDepreciationItem> itemList = zxEqEquipDepreciationItemMapper.selectByZxEqEquipDepreciationItemList(item);
           if(itemList != null && itemList.size() >0) {
        	   for (ZxEqEquipDepreciationItem zxEqEquipDepreciationItem : itemList) {
        		   zxEqEquipDepreciationItem.setAuditStatus(dbzxEqEquipDepreciation.getAuditStatus());
        		   zxEqEquipDepreciationItem.setModifyUserInfo(userKey, realName);
        		   flag = zxEqEquipDepreciationItemMapper.updateByPrimaryKey(zxEqEquipDepreciationItem);

        		   //zxEqEquipMapper
        		   ZxEqEquip eqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquipDepreciationItem.getEquipID());
        		   int depreciationMonth = eqEquip.getDepreciationMonth();
        		   if(eqEquip != null && StrUtil.isNotEmpty(eqEquip.getId())) {
        			   depreciationMonth++;
        			   eqEquip.setDepreciationMonth(depreciationMonth);	
        			   eqEquip.setLeftValue(zxEqEquipDepreciationItem.getLeftValue());
        			   eqEquip.setActualDepreAmt(CalcUtils.calcAdd(eqEquip.getActualDepreAmt(), zxEqEquipDepreciationItem.getDepreamout()));
        			   eqEquip.setModifyUserInfo(userKey, realName);
        			   flag = zxEqEquipMapper.updateByPrimaryKey(eqEquip);
        		   }
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEquipDepreciation);
        }
	}
}
