package com.apih5.service.impl;

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
import com.apih5.mybatis.dao.ZxEqMoveUseOrgItemMapper;
import com.apih5.mybatis.dao.ZxEqMoveUseOrgMapper;
import com.apih5.mybatis.dao.ZxEqUseEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqMoveUseOrg;
import com.apih5.mybatis.pojo.ZxEqMoveUseOrgItem;
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqMoveUseOrgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqMoveUseOrgService")
public class ZxEqMoveUseOrgServiceImpl implements ZxEqMoveUseOrgService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqMoveUseOrgMapper zxEqMoveUseOrgMapper;
    
    @Autowired(required = true)
    private ZxEqMoveUseOrgItemMapper zxEqMoveUseOrgItemMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;
    
    @Autowired(required = true)
    private ZxEqUseEquipMapper zxEqUseEquipMapper;

    @Override
    public ResponseEntity getZxEqMoveUseOrgListByCondition(ZxEqMoveUseOrg zxEqMoveUseOrg) {
        if (zxEqMoveUseOrg == null) {
            zxEqMoveUseOrg = new ZxEqMoveUseOrg();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
        	zxEqMoveUseOrg.setOutComID("");
        	zxEqMoveUseOrg.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqMoveUseOrg.setSeeFlagForCom(zxEqMoveUseOrg.getOutOrgID());
//        	zxEqMoveUseOrg.setOutComID(zxEqMoveUseOrg.getOutOrgID());
        	zxEqMoveUseOrg.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqMoveUseOrg.setSeeFlagForPro(zxEqMoveUseOrg.getOutOrgID());
        	zxEqMoveUseOrg.setOutOrgID("");
        }
        // ????????????
        PageHelper.startPage(zxEqMoveUseOrg.getPage(),zxEqMoveUseOrg.getLimit());
        // ????????????
        List<ZxEqMoveUseOrg> zxEqMoveUseOrgList = zxEqMoveUseOrgMapper.selectByZxEqMoveUseOrgList(zxEqMoveUseOrg);
        for (ZxEqMoveUseOrg zxEqMoveUseOrg2 : zxEqMoveUseOrgList) {
        	ZxEqMoveUseOrgItem item = new ZxEqMoveUseOrgItem();
        	item.setMoveID(zxEqMoveUseOrg2.getId());
        	List<ZxEqMoveUseOrgItem> itemList = zxEqMoveUseOrgItemMapper.selectByZxEqMoveUseOrgItemList(item);
        	String equipName = "";
        	if(itemList != null && itemList.size() >0) {
        		for (ZxEqMoveUseOrgItem itemSelect : itemList) {
        			equipName += itemSelect.getEquipName()+",";
				}
        	}
        	if(equipName.indexOf(",")>0) {
        		equipName = equipName.substring(0, equipName.length()-1);
        	}
        	zxEqMoveUseOrg2.setEquipName(equipName);
        	zxEqMoveUseOrg2.setItemList(itemList);
        	
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqMoveUseOrg2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqMoveUseOrg2.setFileList(fileList);
        }
        // ??????????????????
        PageInfo<ZxEqMoveUseOrg> p = new PageInfo<>(zxEqMoveUseOrgList);

        return repEntity.okList(zxEqMoveUseOrgList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqMoveUseOrgDetails(ZxEqMoveUseOrg zxEqMoveUseOrg) {
        if (zxEqMoveUseOrg == null) {
            zxEqMoveUseOrg = new ZxEqMoveUseOrg();
        }
        // ????????????
        ZxEqMoveUseOrg dbZxEqMoveUseOrg = zxEqMoveUseOrgMapper.selectByPrimaryKey(zxEqMoveUseOrg.getId());
        // ????????????
        if (dbZxEqMoveUseOrg != null) {
            return repEntity.ok(dbZxEqMoveUseOrg);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        zxEqMoveUseOrg.setId(UuidUtil.generate());
        zxEqMoveUseOrg.setApproveStatus("0");//??????????????????
        zxEqMoveUseOrg.setCreateUserInfo(userKey, realName);
        if(zxEqMoveUseOrg.getItemList() != null && zxEqMoveUseOrg.getItemList().size() >0) {	
        	for (ZxEqMoveUseOrgItem lib : zxEqMoveUseOrg.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setMoveID(zxEqMoveUseOrg.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqMoveUseOrgItemMapper.insert(lib);
			}
        }else {
        	return repEntity.layerMessage("no", "?????????????????????????????????");
        }
        flag = zxEqMoveUseOrgMapper.insert(zxEqMoveUseOrg);

        if(zxEqMoveUseOrg.getFileList() != null && zxEqMoveUseOrg.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqMoveUseOrg.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqMoveUseOrg.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqMoveUseOrg);
        }
    }

    @Override
    public ResponseEntity updateZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqMoveUseOrg dbzxEqMoveUseOrg = zxEqMoveUseOrgMapper.selectByPrimaryKey(zxEqMoveUseOrg.getId());
        if (dbzxEqMoveUseOrg != null && StrUtil.isNotEmpty(dbzxEqMoveUseOrg.getId())) {
           // ?????????id
           dbzxEqMoveUseOrg.setOutOrgID(zxEqMoveUseOrg.getOutOrgID());
           // ?????????id
           dbzxEqMoveUseOrg.setAcceptOrgID(zxEqMoveUseOrg.getAcceptOrgID());
           // ?????????name
           dbzxEqMoveUseOrg.setOutOrgName(zxEqMoveUseOrg.getOutOrgName());
           // ?????????name
           dbzxEqMoveUseOrg.setAcceptOrgName(zxEqMoveUseOrg.getAcceptOrgName());
           // ????????????
           dbzxEqMoveUseOrg.setMovedate(zxEqMoveUseOrg.getMovedate());
           // ????????????
           dbzxEqMoveUseOrg.setDeliveryLocation(zxEqMoveUseOrg.getDeliveryLocation());
           // ????????????
           dbzxEqMoveUseOrg.setMoveExes(zxEqMoveUseOrg.getMoveExes());
           // ??????????????????
           dbzxEqMoveUseOrg.setExesPayOrg(zxEqMoveUseOrg.getExesPayOrg());
           // ???????????????
           dbzxEqMoveUseOrg.setOutOpinion(zxEqMoveUseOrg.getOutOpinion());
           // ??????????????????
           dbzxEqMoveUseOrg.setOutTransactor(zxEqMoveUseOrg.getOutTransactor());
           // ???????????????
           dbzxEqMoveUseOrg.setAcceptOpinion(zxEqMoveUseOrg.getAcceptOpinion());
           // ??????????????????
           dbzxEqMoveUseOrg.setAcceptTransactor(zxEqMoveUseOrg.getAcceptTransactor());
           // ??????????????????
           dbzxEqMoveUseOrg.setAdminOrgOpinion(zxEqMoveUseOrg.getAdminOrgOpinion());
           // ????????????
           dbzxEqMoveUseOrg.setApproveStatus(zxEqMoveUseOrg.getApproveStatus());
           // ??????
           dbzxEqMoveUseOrg.setRemark(zxEqMoveUseOrg.getRemark());
           // ?????????
           dbzxEqMoveUseOrg.setTransferNo(zxEqMoveUseOrg.getTransferNo());
           // ????????????
           dbzxEqMoveUseOrg.setReason(zxEqMoveUseOrg.getReason());
           // ????????????
           dbzxEqMoveUseOrg.setCareinfo(zxEqMoveUseOrg.getCareinfo());
           // ????????????
           dbzxEqMoveUseOrg.setAdminLeader(zxEqMoveUseOrg.getAdminLeader());
           // ????????????
           dbzxEqMoveUseOrg.setEquipAdmin(zxEqMoveUseOrg.getEquipAdmin());
           // ?????????
           dbzxEqMoveUseOrg.setFinance(zxEqMoveUseOrg.getFinance());
           // ?????????
           dbzxEqMoveUseOrg.setLister(zxEqMoveUseOrg.getLister());
           // 
           dbzxEqMoveUseOrg.setSerialNo(zxEqMoveUseOrg.getSerialNo());
           // ???????????????????????????
           dbzxEqMoveUseOrg.setInEquipManageDpt(zxEqMoveUseOrg.getInEquipManageDpt());
           // ???????????????????????????
           dbzxEqMoveUseOrg.setAcceptFinance(zxEqMoveUseOrg.getAcceptFinance());
           // ????????????
           dbzxEqMoveUseOrg.setLocality(zxEqMoveUseOrg.getLocality());
           // comId
           dbzxEqMoveUseOrg.setComID(zxEqMoveUseOrg.getComID());
           // ????????????
           dbzxEqMoveUseOrg.setComName(zxEqMoveUseOrg.getComName());
           // ?????????????????????id
           dbzxEqMoveUseOrg.setOutComID(zxEqMoveUseOrg.getOutComID());
           // ?????????????????????name
           dbzxEqMoveUseOrg.setOutComName(zxEqMoveUseOrg.getOutComName());
           // ?????????????????????id
           dbzxEqMoveUseOrg.setAcceptComID(zxEqMoveUseOrg.getAcceptComID());
           // ?????????????????????name
           dbzxEqMoveUseOrg.setAcceptComName(zxEqMoveUseOrg.getAcceptComName());
           // ??????
           dbzxEqMoveUseOrg.setModifyUserInfo(userKey, realName);
          
           //??????????????????
           ZxEqMoveUseOrgItem delLib = new ZxEqMoveUseOrgItem();
           delLib.setMoveID(zxEqMoveUseOrg.getId());
           List<ZxEqMoveUseOrgItem> delLibList = zxEqMoveUseOrgItemMapper.selectByZxEqMoveUseOrgItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqMoveUseOrgItemMapper.batchDeleteUpdateZxEqMoveUseOrgItem(delLibList, delLib);
           }
           if(zxEqMoveUseOrg.getItemList() != null && zxEqMoveUseOrg.getItemList().size() >0) {	
        	   for (ZxEqMoveUseOrgItem lib : zxEqMoveUseOrg.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMoveID(zxEqMoveUseOrg.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqMoveUseOrgItemMapper.insert(lib);
        	   }
           }else {
        	   return repEntity.layerMessage("no", "?????????????????????????????????");
           }
           flag = zxEqMoveUseOrgMapper.updateByPrimaryKey(dbzxEqMoveUseOrg);
           //??????????????????
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqMoveUseOrg.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqMoveUseOrg.getFileList() != null && zxEqMoveUseOrg.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqMoveUseOrg.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqMoveUseOrg.getId());
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
            return repEntity.ok("sys.data.update",zxEqMoveUseOrg);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqMoveUseOrg(List<ZxEqMoveUseOrg> zxEqMoveUseOrgList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqMoveUseOrgList != null && zxEqMoveUseOrgList.size() > 0) {
        	for (ZxEqMoveUseOrg zxEqMoveUseOrg : zxEqMoveUseOrgList) {
    			ZxEqMoveUseOrgItem delLib = new ZxEqMoveUseOrgItem();
    			delLib.setMoveID(zxEqMoveUseOrg.getId());
    			List<ZxEqMoveUseOrgItem> delLibList = zxEqMoveUseOrgItemMapper.selectByZxEqMoveUseOrgItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqMoveUseOrgItemMapper.batchDeleteUpdateZxEqMoveUseOrgItem(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqMoveUseOrg.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqMoveUseOrg zxEqMoveUseOrg = new ZxEqMoveUseOrg();
           zxEqMoveUseOrg.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveUseOrgMapper.batchDeleteUpdateZxEqMoveUseOrg(zxEqMoveUseOrgList, zxEqMoveUseOrg);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqMoveUseOrgList);
        }
    }

	@Override
	public ResponseEntity outConfirmZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxEqMoveUseOrg dbzxEqMoveUseOrg = zxEqMoveUseOrgMapper.selectByPrimaryKey(zxEqMoveUseOrg.getId());
		if (dbzxEqMoveUseOrg != null && StrUtil.isNotEmpty(dbzxEqMoveUseOrg.getId())) {
			// ????????????
			dbzxEqMoveUseOrg.setApproveStatus("1");//??????????????????
			// ??????
			dbzxEqMoveUseOrg.setModifyUserInfo(userKey, realName);
			flag = zxEqMoveUseOrgMapper.updateByPrimaryKey(dbzxEqMoveUseOrg);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zxEqMoveUseOrg);
		}
	}

	@Override
	public ResponseEntity inConfirmZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg) {
		  HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        int flag = 0;
	        ZxEqMoveUseOrg dbzxEqMoveUseOrg = zxEqMoveUseOrgMapper.selectByPrimaryKey(zxEqMoveUseOrg.getId());
	        if (dbzxEqMoveUseOrg != null && StrUtil.isNotEmpty(dbzxEqMoveUseOrg.getId())) {
	           // ??????????????????
	           dbzxEqMoveUseOrg.setAcceptTransactor(zxEqMoveUseOrg.getAcceptTransactor());
	           // ????????????
	           dbzxEqMoveUseOrg.setApproveStatus("2");//????????????
	           //?????????????????????
	           dbzxEqMoveUseOrg.setAcceptFinance(zxEqMoveUseOrg.getAcceptFinance());
	           // ???????????????????????????
	           dbzxEqMoveUseOrg.setInEquipManageDpt(zxEqMoveUseOrg.getInEquipManageDpt());
	           // ????????????
	           dbzxEqMoveUseOrg.setLocality(zxEqMoveUseOrg.getLocality());
	           // ??????
	           dbzxEqMoveUseOrg.setModifyUserInfo(userKey, realName);
	           flag = zxEqMoveUseOrgMapper.updateByPrimaryKey(dbzxEqMoveUseOrg);
	           //??????????????????
	           ZxErpFile delFile = new ZxErpFile();
	           delFile.setOtherId(zxEqMoveUseOrg.getId());
	           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
	           if(delFileList != null && delFileList.size() >0) {
	        	   delFile.setModifyUserInfo(userKey, realName);
	        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
	           }
	           if(zxEqMoveUseOrg.getFileList() != null && zxEqMoveUseOrg.getFileList().size() >0) {
	        	   for (ZxErpFile file : zxEqMoveUseOrg.getFileList()) {
	        		   file.setUid(UuidUtil.generate());
	        		   file.setOtherId(zxEqMoveUseOrg.getId());
	        		   file.setCreateUserInfo(userKey, realName);
	        		   flag = zxErpFileMapper.insert(file);
	        	   }
	           }
	           //????????????????????????????????????????????????update
	           ZxEqMoveUseOrgItem item = new ZxEqMoveUseOrgItem();
	           item.setMoveID(dbzxEqMoveUseOrg.getId());
	           List<ZxEqMoveUseOrgItem> itemList = zxEqMoveUseOrgItemMapper.selectByZxEqMoveUseOrgItemList(item);
	           if(itemList != null && itemList.size() >0) {
	        	   for (ZxEqMoveUseOrgItem zxEqMoveUseOrgItem : itemList) {
	        		   ZxEqEquip equip = zxEqEquipMapper.selectByPrimaryKey(zxEqMoveUseOrgItem.getEquipID());
	        		   if(equip != null && StrUtil.isNotEmpty(equip.getId())) {
	        			   equip.setUseOrgID(dbzxEqMoveUseOrg.getAcceptOrgID());
	        			   equip.setUseOrgName(dbzxEqMoveUseOrg.getAcceptOrgName());
	        			   equip.setOwnerOrgId(dbzxEqMoveUseOrg.getAcceptComID());
	        			   equip.setOwnerOrgName(dbzxEqMoveUseOrg.getAcceptComName());
	        			   equip.setCompanyId(dbzxEqMoveUseOrg.getAcceptComID());
	        			   equip.setCompanyName(dbzxEqMoveUseOrg.getAcceptComName());
	        			   equip.setModifyUserInfo(userKey, realName);
	        			   flag = zxEqEquipMapper.updateByPrimaryKey(equip);
	        		   }
	        		   //??????????????????????????????????????????????????????update
	        		   ZxEqUseEquip useEquip = new ZxEqUseEquip();
	        		   useEquip.setRefEquipID(zxEqMoveUseOrgItem.getEquipID());
	        		   List<ZxEqUseEquip> useEquipList = zxEqUseEquipMapper.selectByZxEqUseEquipList(useEquip);
	        		   if(useEquipList != null && useEquipList.size() >0) {
	        			   useEquipList.get(0).setUseOrgId(dbzxEqMoveUseOrg.getAcceptOrgID());
	        			   useEquipList.get(0).setUseOrg(dbzxEqMoveUseOrg.getAcceptOrgName());
	        			   useEquipList.get(0).setOwnerOrgID(dbzxEqMoveUseOrg.getAcceptComID());
	        			   useEquipList.get(0).setOwnerOrg(dbzxEqMoveUseOrg.getAcceptComName());
	        			   useEquipList.get(0).setComID(dbzxEqMoveUseOrg.getAcceptComID());
	        			   useEquipList.get(0).setComName(dbzxEqMoveUseOrg.getAcceptComName());
	        			 
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
	            return repEntity.ok("sys.data.update",zxEqMoveUseOrg);
	        }
	}
}
