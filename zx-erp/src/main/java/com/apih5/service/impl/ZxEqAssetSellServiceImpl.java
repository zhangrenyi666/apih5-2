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
import com.apih5.mybatis.dao.ZxEqAssetSellItemMapper;
import com.apih5.mybatis.dao.ZxEqAssetSellMapper;
import com.apih5.mybatis.dao.ZxEqEquipMapper;
import com.apih5.mybatis.dao.ZxEqUseEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqAssetSell;
import com.apih5.mybatis.pojo.ZxEqAssetSellItem;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqAssetSellService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqAssetSellService")
public class ZxEqAssetSellServiceImpl implements ZxEqAssetSellService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqAssetSellMapper zxEqAssetSellMapper;
    
    @Autowired(required = true)
    private ZxEqAssetSellItemMapper zxEqAssetSellItemMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;

    @Autowired(required = true)
    private ZxEqUseEquipMapper zxEqUseEquipMapper;

    @Override
    public ResponseEntity getZxEqAssetSellListByCondition(ZxEqAssetSell zxEqAssetSell) {
        if (zxEqAssetSell == null) {
            zxEqAssetSell = new ZxEqAssetSell();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)|| StrUtil.equals("admin", userId)) {
        	zxEqAssetSell.setComID("");
        	zxEqAssetSell.setOutOrgID("");
        	zxEqAssetSell.setSeeFlagForJu("???????????????????????????");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqAssetSell.setComID(zxEqAssetSell.getOutOrgID());
        	zxEqAssetSell.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqAssetSell.setOutOrgID(zxEqAssetSell.getOutOrgID());
        }
        // ????????????
        PageHelper.startPage(zxEqAssetSell.getPage(),zxEqAssetSell.getLimit());
        // ????????????
        List<ZxEqAssetSell> zxEqAssetSellList = zxEqAssetSellMapper.selectByZxEqAssetSellList(zxEqAssetSell);
        for (ZxEqAssetSell zxEqAssetSell2 : zxEqAssetSellList) {
        	ZxEqAssetSellItem item = new ZxEqAssetSellItem();
        	item.setMasID(zxEqAssetSell2.getId());
        	List<ZxEqAssetSellItem> itemList = zxEqAssetSellItemMapper.selectByZxEqAssetSellItemList(item);
        	String equipName = "";
        	if(itemList != null && itemList.size() >0) {
        		for (ZxEqAssetSellItem itemSelect : itemList) {
        			equipName += itemSelect.getEquipName()+",";
				}
        	}
        	if(equipName.indexOf(",")>0) {
        		equipName = equipName.substring(0, equipName.length()-1);
        	}
        	zxEqAssetSell2.setEquipName(equipName);
        	
        	zxEqAssetSell2.setItemList(itemList);
        	
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqAssetSell2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqAssetSell2.setFileList(fileList);
        }
        // ??????????????????
        PageInfo<ZxEqAssetSell> p = new PageInfo<>(zxEqAssetSellList);

        return repEntity.okList(zxEqAssetSellList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqAssetSellDetails(ZxEqAssetSell zxEqAssetSell) {
        if (zxEqAssetSell == null) {
            zxEqAssetSell = new ZxEqAssetSell();
        }
        // ????????????
        ZxEqAssetSell dbZxEqAssetSell = zxEqAssetSellMapper.selectByPrimaryKey(zxEqAssetSell.getId());
        // ????????????
        if (dbZxEqAssetSell != null) {
            return repEntity.ok(dbZxEqAssetSell);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqAssetSell(ZxEqAssetSell zxEqAssetSell) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqAssetSell.setId(UuidUtil.generate());
        zxEqAssetSell.setAuditStatus("0");//?????????????????????
        zxEqAssetSell.setCreateUserInfo(userKey, realName);
        int flag = zxEqAssetSellMapper.insert(zxEqAssetSell);
        if(zxEqAssetSell.getItemList() != null && zxEqAssetSell.getItemList().size() >0) {	
        	for (ZxEqAssetSellItem lib : zxEqAssetSell.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setMasID(zxEqAssetSell.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqAssetSellItemMapper.insert(lib);
			}
        }
        if(zxEqAssetSell.getFileList() != null && zxEqAssetSell.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqAssetSell.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqAssetSell.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqAssetSell);
        }
    }

    @Override
    public ResponseEntity updateZxEqAssetSell(ZxEqAssetSell zxEqAssetSell) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqAssetSell dbzxEqAssetSell = zxEqAssetSellMapper.selectByPrimaryKey(zxEqAssetSell.getId());
        if (dbzxEqAssetSell != null && StrUtil.isNotEmpty(dbzxEqAssetSell.getId())) {
           // ?????????
           dbzxEqAssetSell.setOutOrgID(zxEqAssetSell.getOutOrgID());
           // ??????????????????
           dbzxEqAssetSell.setInnerTran(zxEqAssetSell.getInnerTran());
           // ?????????
           dbzxEqAssetSell.setInOrgID(zxEqAssetSell.getInOrgID());
           // ???????????????
           dbzxEqAssetSell.setInOrgName(zxEqAssetSell.getInOrgName());
           // ????????????
           dbzxEqAssetSell.setReason(zxEqAssetSell.getReason());
           // ????????????
           dbzxEqAssetSell.setBusDate(zxEqAssetSell.getBusDate());
           // ????????????
           dbzxEqAssetSell.setInDate(zxEqAssetSell.getInDate());
           // ?????????????????????????????????
           dbzxEqAssetSell.setOutman2(zxEqAssetSell.getOutman2());
           // ?????????????????????????????????
           dbzxEqAssetSell.setOutman1(zxEqAssetSell.getOutman1());
           // ????????????????????????
           dbzxEqAssetSell.setOutman5(zxEqAssetSell.getOutman5());
           // ?????????????????????????????????
           dbzxEqAssetSell.setOutman3(zxEqAssetSell.getOutman3());
           // ?????????????????????????????????
           dbzxEqAssetSell.setOutman4(zxEqAssetSell.getOutman4());
           // ????????????????????????
           dbzxEqAssetSell.setInman1(zxEqAssetSell.getInman1());
           // ????????????????????????
           dbzxEqAssetSell.setInman2(zxEqAssetSell.getInman2());
           // ????????????????????????
           dbzxEqAssetSell.setInman3(zxEqAssetSell.getInman3());
//           // ????????????
//           dbzxEqAssetSell.setAuditStatus(zxEqAssetSell.getAuditStatus());
           // ??????
           dbzxEqAssetSell.setRemark(zxEqAssetSell.getRemark());
           // ??????id
           dbzxEqAssetSell.setOrderID(zxEqAssetSell.getOrderID());
           // ?????????
           dbzxEqAssetSell.setApprovalNo(zxEqAssetSell.getApprovalNo());
           // ??????
           dbzxEqAssetSell.setModifyUserInfo(userKey, realName);
           flag = zxEqAssetSellMapper.updateByPrimaryKey(dbzxEqAssetSell);
           //??????????????????
           ZxEqAssetSellItem delLib = new ZxEqAssetSellItem();
           delLib.setMasID(zxEqAssetSell.getId());
           List<ZxEqAssetSellItem> delLibList = zxEqAssetSellItemMapper.selectByZxEqAssetSellItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqAssetSellItemMapper.batchDeleteUpdateZxEqAssetSellItem(delLibList, delLib);
           }
           if(zxEqAssetSell.getItemList() != null && zxEqAssetSell.getItemList().size() >0) {	
        	   for (ZxEqAssetSellItem lib : zxEqAssetSell.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMasID(zxEqAssetSell.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqAssetSellItemMapper.insert(lib);
        	   }
           }
           //??????????????????
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqAssetSell.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqAssetSell.getFileList() != null && zxEqAssetSell.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqAssetSell.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqAssetSell.getId());
        		   file.setCreateUserInfo(userKey, realName);
        		   flag = zxErpFileMapper.insert(file);
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqAssetSell);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqAssetSell(List<ZxEqAssetSell> zxEqAssetSellList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqAssetSellList != null && zxEqAssetSellList.size() > 0) {
        	for (ZxEqAssetSell zxEqAssetSell : zxEqAssetSellList) {
    			ZxEqAssetSellItem delLib = new ZxEqAssetSellItem();
    			delLib.setMasID(zxEqAssetSell.getId());
    			List<ZxEqAssetSellItem> delLibList = zxEqAssetSellItemMapper.selectByZxEqAssetSellItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqAssetSellItemMapper.batchDeleteUpdateZxEqAssetSellItem(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqAssetSell.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqAssetSell zxEqAssetSell = new ZxEqAssetSell();
           zxEqAssetSell.setModifyUserInfo(userKey, realName);
           flag = zxEqAssetSellMapper.batchDeleteUpdateZxEqAssetSell(zxEqAssetSellList, zxEqAssetSell);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqAssetSellList);
        }
    }

    @Override
    public ResponseEntity reportZxEqAssetSell(ZxEqAssetSell zxEqAssetSell) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqAssetSell dbzxEqAssetSell = zxEqAssetSellMapper.selectByPrimaryKey(zxEqAssetSell.getId());
    	if (dbzxEqAssetSell != null && StrUtil.isNotEmpty(dbzxEqAssetSell.getId())) {
    		// ????????????
    		dbzxEqAssetSell.setAuditStatus("3");//?????????
    		// ??????
    		dbzxEqAssetSell.setModifyUserInfo(userKey, realName);
    		flag = zxEqAssetSellMapper.updateByPrimaryKey(dbzxEqAssetSell);
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqAssetSell);
    	}
    }

	@Override
	public ResponseEntity auditAgreeZxEqAssetSell(ZxEqAssetSell zxEqAssetSell) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqAssetSell dbzxEqAssetSell = zxEqAssetSellMapper.selectByPrimaryKey(zxEqAssetSell.getId());
    	if (dbzxEqAssetSell != null && StrUtil.isNotEmpty(dbzxEqAssetSell.getId())) {
    		// ????????????
    		dbzxEqAssetSell.setAuditStatus("4");//?????????
    		// ??????
    		dbzxEqAssetSell.setModifyUserInfo(userKey, realName);
    		flag = zxEqAssetSellMapper.updateByPrimaryKey(dbzxEqAssetSell);
    		//????????????000004349???
    		ZxEqAssetSellItem item = new ZxEqAssetSellItem();
			item.setMasID(zxEqAssetSell.getId());
			List<ZxEqAssetSellItem> itemList = zxEqAssetSellItemMapper.selectByZxEqAssetSellItemList(item);
			if(itemList != null && itemList.size() >0) {
				
			}
			for (ZxEqAssetSellItem zxEqAssetSellItem : itemList) {
				ZxEqUseEquip useEquip = new ZxEqUseEquip();
				useEquip.setRefEquipID(zxEqAssetSellItem.getEquipID());
	    	    List<ZxEqUseEquip> useEquipList = zxEqUseEquipMapper.selectByZxEqUseEquipList(useEquip);
				if(useEquipList != null && useEquipList.size() >0) {
					useEquip.setModifyUserInfo(userKey, realName);
					flag = zxEqUseEquipMapper.batchDeleteUpdateZxEqUseEquip(useEquipList, useEquip);
				}
				ZxEqEquip zxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqAssetSellItem.getEquipID());
				if(zxEqEquip != null && StrUtil.isNotEmpty(zxEqEquip.getId())) {
					zxEqEquip.setStatus("??????????????????");
					zxEqEquip.setChangeDate(new Date());
					zxEqEquip.setModifyUserInfo(userKey, realName);
					flag = zxEqEquipMapper.updateByPrimaryKey(zxEqEquip);
				}
			}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqAssetSell);
    	}
	}

	@Override
	public ResponseEntity auditRefuseZxEqAssetSell(ZxEqAssetSell zxEqAssetSell) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqAssetSell dbzxEqAssetSell = zxEqAssetSellMapper.selectByPrimaryKey(zxEqAssetSell.getId());
    	if (dbzxEqAssetSell != null && StrUtil.isNotEmpty(dbzxEqAssetSell.getId())) {
    		// ????????????
    		dbzxEqAssetSell.setAuditStatus("5");//???????????????
    		// ??????
    		dbzxEqAssetSell.setModifyUserInfo(userKey, realName);
    		flag = zxEqAssetSellMapper.updateByPrimaryKey(dbzxEqAssetSell);
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqAssetSell);
    	}
	}
}
