package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEquipMapper;
import com.apih5.mybatis.dao.ZxEqMoveSupervisorItemMapper;
import com.apih5.mybatis.dao.ZxEqMoveSupervisorMapper;
import com.apih5.mybatis.dao.ZxEqUseEquipMapper;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqMoveSupervisor;
import com.apih5.mybatis.pojo.ZxEqMoveSupervisorItem;
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.service.ZxEqMoveSupervisorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqMoveSupervisorService")
public class ZxEqMoveSupervisorServiceImpl implements ZxEqMoveSupervisorService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqMoveSupervisorMapper zxEqMoveSupervisorMapper;
    
    @Autowired(required = true)
    private ZxEqMoveSupervisorItemMapper zxEqMoveSupervisorItemMapper;
    
    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;
    
    @Autowired(required = true)
    private ZxEqUseEquipMapper zxEqUseEquipMapper;
    
    @Override
    public ResponseEntity getZxEqMoveSupervisorListByCondition(ZxEqMoveSupervisor zxEqMoveSupervisor) {
        if (zxEqMoveSupervisor == null) {
            zxEqMoveSupervisor = new ZxEqMoveSupervisor();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqMoveSupervisor.setComID("");
        	zxEqMoveSupervisor.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqMoveSupervisor.setSeeFlagForCom(zxEqMoveSupervisor.getOutOrgID());
//        	zxEqMoveSupervisor.setComID(zxEqMoveSupervisor.getOutOrgID());
        	zxEqMoveSupervisor.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????===??????????????????
        	zxEqMoveSupervisor.setOutOrgID(zxEqMoveSupervisor.getOutOrgID());
        }
        
        // ????????????
        PageHelper.startPage(zxEqMoveSupervisor.getPage(),zxEqMoveSupervisor.getLimit());
        // ????????????
        List<ZxEqMoveSupervisor> zxEqMoveSupervisorList = zxEqMoveSupervisorMapper.selectByZxEqMoveSupervisorList(zxEqMoveSupervisor);
        for (ZxEqMoveSupervisor zxEqMoveSupervisor2 : zxEqMoveSupervisorList) {
        	ZxEqMoveSupervisorItem item = new ZxEqMoveSupervisorItem();
        	item.setMasID(zxEqMoveSupervisor2.getId());
        	List<ZxEqMoveSupervisorItem> itemList = zxEqMoveSupervisorItemMapper.selectByZxEqMoveSupervisorItemList(item);
        	String equipName = "";
        	if(itemList != null && itemList.size() >0) {
        		for (ZxEqMoveSupervisorItem itemSelect : itemList) {
        			equipName += itemSelect.getEquipName()+",";
				}
        	}
        	if(equipName.indexOf(",")>0) {
        		equipName = equipName.substring(0, equipName.length()-1);
        	}
        	zxEqMoveSupervisor2.setEquipName(equipName);
        	
        	
        	zxEqMoveSupervisor2.setItemList(itemList);
        }
        // ??????????????????
        PageInfo<ZxEqMoveSupervisor> p = new PageInfo<>(zxEqMoveSupervisorList);

        return repEntity.okList(zxEqMoveSupervisorList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqMoveSupervisorDetails(ZxEqMoveSupervisor zxEqMoveSupervisor) {
        if (zxEqMoveSupervisor == null) {
            zxEqMoveSupervisor = new ZxEqMoveSupervisor();
        }
        // ????????????
        ZxEqMoveSupervisor dbZxEqMoveSupervisor = zxEqMoveSupervisorMapper.selectByPrimaryKey(zxEqMoveSupervisor.getId());
        // ????????????
        if (dbZxEqMoveSupervisor != null) {
            return repEntity.ok(dbZxEqMoveSupervisor);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqMoveSupervisor(ZxEqMoveSupervisor zxEqMoveSupervisor) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqMoveSupervisor.setId(UuidUtil.generate());
        zxEqMoveSupervisor.setAuditStatus("0");
        zxEqMoveSupervisor.setCreateUserInfo(userKey, realName);
        int flag = zxEqMoveSupervisorMapper.insert(zxEqMoveSupervisor);
        if(zxEqMoveSupervisor.getItemList() != null && zxEqMoveSupervisor.getItemList().size() >0) {	
        	for (ZxEqMoveSupervisorItem lib : zxEqMoveSupervisor.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setMasID(zxEqMoveSupervisor.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqMoveSupervisorItemMapper.insert(lib);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqMoveSupervisor);
        }
    }

    @Override
    public ResponseEntity updateZxEqMoveSupervisor(ZxEqMoveSupervisor zxEqMoveSupervisor) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqMoveSupervisor dbzxEqMoveSupervisor = zxEqMoveSupervisorMapper.selectByPrimaryKey(zxEqMoveSupervisor.getId());
        if (dbzxEqMoveSupervisor != null && StrUtil.isNotEmpty(dbzxEqMoveSupervisor.getId())) {
           // ?????????
           dbzxEqMoveSupervisor.setBillNo(zxEqMoveSupervisor.getBillNo());
           // ?????????
           dbzxEqMoveSupervisor.setOutOrgID(zxEqMoveSupervisor.getOutOrgID());
           dbzxEqMoveSupervisor.setOutOrgName(zxEqMoveSupervisor.getOutOrgName());
           // ?????????
           dbzxEqMoveSupervisor.setInOrgID(zxEqMoveSupervisor.getInOrgID());
           dbzxEqMoveSupervisor.setInOrgName(zxEqMoveSupervisor.getInOrgName());
           // ????????????
           dbzxEqMoveSupervisor.setReason(zxEqMoveSupervisor.getReason());
           // ????????????
           dbzxEqMoveSupervisor.setBusDate(zxEqMoveSupervisor.getBusDate());
           // ????????????
           dbzxEqMoveSupervisor.setInDate(zxEqMoveSupervisor.getInDate());
           // ?????????????????????????????????
           dbzxEqMoveSupervisor.setOutman1(zxEqMoveSupervisor.getOutman1());
           // ?????????????????????????????????
           dbzxEqMoveSupervisor.setOutman2(zxEqMoveSupervisor.getOutman2());
           // ????????????????????????
           dbzxEqMoveSupervisor.setOutman5(zxEqMoveSupervisor.getOutman5());
           // ?????????????????????????????????
           dbzxEqMoveSupervisor.setOutman3(zxEqMoveSupervisor.getOutman3());
           // ?????????????????????????????????
           dbzxEqMoveSupervisor.setOutman4(zxEqMoveSupervisor.getOutman4());
           // ????????????????????????
           dbzxEqMoveSupervisor.setInman1(zxEqMoveSupervisor.getInman1());
           // ????????????????????????
           dbzxEqMoveSupervisor.setInman2(zxEqMoveSupervisor.getInman2());
           // ????????????????????????
           dbzxEqMoveSupervisor.setInman3(zxEqMoveSupervisor.getInman3());
           // ??????
           dbzxEqMoveSupervisor.setAuditStatus(zxEqMoveSupervisor.getAuditStatus());
           // ??????id
           dbzxEqMoveSupervisor.setOrderID(zxEqMoveSupervisor.getOrderID());
           // ??????
           dbzxEqMoveSupervisor.setRemark(zxEqMoveSupervisor.getRemark());
           // 
           dbzxEqMoveSupervisor.setSerialNo(zxEqMoveSupervisor.getSerialNo());
           // ??????
           dbzxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveSupervisorMapper.updateByPrimaryKey(dbzxEqMoveSupervisor);
           //??????????????????
           ZxEqMoveSupervisorItem delLib = new ZxEqMoveSupervisorItem();
           delLib.setMasID(zxEqMoveSupervisor.getId());
           List<ZxEqMoveSupervisorItem> delLibList = zxEqMoveSupervisorItemMapper.selectByZxEqMoveSupervisorItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqMoveSupervisorItemMapper.batchDeleteUpdateZxEqMoveSupervisorItem(delLibList, delLib);
           }
           if(zxEqMoveSupervisor.getItemList() != null && zxEqMoveSupervisor.getItemList().size() >0) {	
        	   for (ZxEqMoveSupervisorItem lib : zxEqMoveSupervisor.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMasID(zxEqMoveSupervisor.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqMoveSupervisorItemMapper.insert(lib);
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqMoveSupervisor);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqMoveSupervisor(List<ZxEqMoveSupervisor> zxEqMoveSupervisorList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqMoveSupervisorList != null && zxEqMoveSupervisorList.size() > 0) {
        	for (ZxEqMoveSupervisor zxEqMoveSupervisor : zxEqMoveSupervisorList) {
    			ZxEqMoveSupervisorItem delLib = new ZxEqMoveSupervisorItem();
    			delLib.setMasID(zxEqMoveSupervisor.getId());
    			List<ZxEqMoveSupervisorItem> delLibList = zxEqMoveSupervisorItemMapper.selectByZxEqMoveSupervisorItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqMoveSupervisorItemMapper.batchDeleteUpdateZxEqMoveSupervisorItem(delLibList, delLib);
    			}
    		}
        	ZxEqMoveSupervisor zxEqMoveSupervisor = new ZxEqMoveSupervisor();
           zxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveSupervisorMapper.batchDeleteUpdateZxEqMoveSupervisor(zxEqMoveSupervisorList, zxEqMoveSupervisor);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqMoveSupervisorList);
        }
    }

    @Override
    public ResponseEntity outConfirmZxEqMoveSupervisor(ZxEqMoveSupervisor zxEqMoveSupervisor) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqMoveSupervisor dbzxEqMoveSupervisor = zxEqMoveSupervisorMapper.selectByPrimaryKey(zxEqMoveSupervisor.getId());
    	if (dbzxEqMoveSupervisor != null && StrUtil.isNotEmpty(dbzxEqMoveSupervisor.getId())) {

    		dbzxEqMoveSupervisor.setAuditStatus("1");//??????????????????
    		// ??????
    		dbzxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
    		flag = zxEqMoveSupervisorMapper.updateByPrimaryKey(dbzxEqMoveSupervisor);

    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqMoveSupervisor);
    	}
    }

	@Override
	public ResponseEntity inConfirmZxEqMoveSupervisor(ZxEqMoveSupervisor zxEqMoveSupervisor) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxEqMoveSupervisor dbzxEqMoveSupervisor = zxEqMoveSupervisorMapper.selectByPrimaryKey(zxEqMoveSupervisor.getId());
		if (dbzxEqMoveSupervisor != null && StrUtil.isNotEmpty(dbzxEqMoveSupervisor.getId())) {
			//????????????
			dbzxEqMoveSupervisor.setInDate(new Date());
			// ????????????????????????
			dbzxEqMoveSupervisor.setInman1(zxEqMoveSupervisor.getInman1());
			// ????????????????????????
			dbzxEqMoveSupervisor.setInman2(zxEqMoveSupervisor.getInman2());
			// ????????????????????????
			dbzxEqMoveSupervisor.setInman3(zxEqMoveSupervisor.getInman3());
			// ????????????
			dbzxEqMoveSupervisor.setAuditStatus("2");//????????????
			// ??????
			dbzxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
			flag = zxEqMoveSupervisorMapper.updateByPrimaryKey(dbzxEqMoveSupervisor);

			//????????????????????? 4349==????????????????????????????????????????????????update
			ZxEqMoveSupervisorItem item = new ZxEqMoveSupervisorItem();
			item.setMasID(dbzxEqMoveSupervisor.getId());
			List<ZxEqMoveSupervisorItem> itemList = zxEqMoveSupervisorItemMapper.selectByZxEqMoveSupervisorItemList(item);
			if(itemList != null && itemList.size() >0) {
				for (ZxEqMoveSupervisorItem zxEqMoveUseOrgItem : itemList) {
					ZxEqEquip equip = zxEqEquipMapper.selectByPrimaryKey(zxEqMoveUseOrgItem.getEquipID());
					if(equip != null && StrUtil.isNotEmpty(equip.getId())) {
						equip.setUseOrgID(zxEqMoveUseOrgItem.getUseOrgId());
						equip.setUseOrgName(zxEqMoveUseOrgItem.getUseOrgName());
						equip.setOwnerOrgId(dbzxEqMoveSupervisor.getInOrgID());
						equip.setOwnerOrgName(dbzxEqMoveSupervisor.getInOrgName());
						equip.setCompanyId(dbzxEqMoveSupervisor.getInOrgID());
						equip.setCompanyName(dbzxEqMoveSupervisor.getInOrgName());
						equip.setEquipNo(zxEqMoveUseOrgItem.getNewManageNo());
						equip.setModifyUserInfo(userKey, realName);
						flag = zxEqEquipMapper.updateByPrimaryKey(equip);
					}
					//?????????????????????????????????????????????????????????id??????update
					ZxEqUseEquip useEquip = new ZxEqUseEquip();
					useEquip.setRefEquipID(zxEqMoveUseOrgItem.getEquipID());
					List<ZxEqUseEquip> useEquipList = zxEqUseEquipMapper.selectByZxEqUseEquipList(useEquip);
					if(useEquipList != null && useEquipList.size() >0) {
						useEquipList.get(0).setUseOrgId(zxEqMoveUseOrgItem.getUseOrgId());
						useEquipList.get(0).setUseOrg(zxEqMoveUseOrgItem.getUseOrgName());
						useEquipList.get(0).setOwnerOrgID(dbzxEqMoveSupervisor.getInOrgID());
						useEquipList.get(0).setOwnerOrg(dbzxEqMoveSupervisor.getInOrgName());
						useEquipList.get(0).setComID(dbzxEqMoveSupervisor.getInOrgID());
						useEquipList.get(0).setComName(dbzxEqMoveSupervisor.getInOrgName());
						useEquipList.get(0).setModifyUserInfo(userKey, realName);
						flag = zxEqUseEquipMapper.updateByPrimaryKey(useEquipList.get(0));
					}
				}
			}

		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zxEqMoveSupervisor);
		}
	}

	@Override
	public ResponseEntity batchRequestZxEqMoveSupervisorNum(List<ZxEqMoveSupervisor> zxEqMoveSupervisorList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqMoveSupervisorList != null && zxEqMoveSupervisorList.size() > 0) {
        	ZxEqMoveSupervisor zxEqMoveSupervisor = new ZxEqMoveSupervisor();
        	zxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
        	zxEqMoveSupervisor.setAuditStatus("3");//???????????????
        	flag = zxEqMoveSupervisorMapper.batchRequestZxEqMoveSupervisorNum(zxEqMoveSupervisorList, zxEqMoveSupervisor);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqMoveSupervisorList);
        }
	}

	@Override
	public ResponseEntity writeZxEqMoveSupervisorNum(ZxEqMoveSupervisor zxEqMoveSupervisor) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqMoveSupervisor dbzxEqMoveSupervisor = zxEqMoveSupervisorMapper.selectByPrimaryKey(zxEqMoveSupervisor.getId());
        if (dbzxEqMoveSupervisor != null && StrUtil.isNotEmpty(dbzxEqMoveSupervisor.getId())) {
           // ??????
           dbzxEqMoveSupervisor.setAuditStatus("4");//??????????????????
           // ??????
           dbzxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveSupervisorMapper.updateByPrimaryKey(dbzxEqMoveSupervisor);
           //??????????????????
           ZxEqMoveSupervisorItem delLib = new ZxEqMoveSupervisorItem();
           delLib.setMasID(zxEqMoveSupervisor.getId());
           List<ZxEqMoveSupervisorItem> delLibList = zxEqMoveSupervisorItemMapper.selectByZxEqMoveSupervisorItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqMoveSupervisorItemMapper.batchDeleteUpdateZxEqMoveSupervisorItem(delLibList, delLib);
           }
           if(zxEqMoveSupervisor.getItemList() != null && zxEqMoveSupervisor.getItemList().size() >0) {	
        	   for (ZxEqMoveSupervisorItem lib : zxEqMoveSupervisor.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMasID(zxEqMoveSupervisor.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqMoveSupervisorItemMapper.insert(lib);
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqMoveSupervisor);
        }
	}
}
