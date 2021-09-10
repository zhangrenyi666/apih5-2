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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)|| StrUtil.equals("admin", userId)) {
        	zxEqAssetSell.setComID("");
        	zxEqAssetSell.setOutOrgID("");
        	zxEqAssetSell.setSeeFlagForJu("未上报之前局看不到");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqAssetSell.setComID(zxEqAssetSell.getOutOrgID());
        	zxEqAssetSell.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqAssetSell.setOutOrgID(zxEqAssetSell.getOutOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxEqAssetSell.getPage(),zxEqAssetSell.getLimit());
        // 获取数据
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
        // 得到分页信息
        PageInfo<ZxEqAssetSell> p = new PageInfo<>(zxEqAssetSellList);

        return repEntity.okList(zxEqAssetSellList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqAssetSellDetails(ZxEqAssetSell zxEqAssetSell) {
        if (zxEqAssetSell == null) {
            zxEqAssetSell = new ZxEqAssetSell();
        }
        // 获取数据
        ZxEqAssetSell dbZxEqAssetSell = zxEqAssetSellMapper.selectByPrimaryKey(zxEqAssetSell.getId());
        // 数据存在
        if (dbZxEqAssetSell != null) {
            return repEntity.ok(dbZxEqAssetSell);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqAssetSell(ZxEqAssetSell zxEqAssetSell) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqAssetSell.setId(UuidUtil.generate());
        zxEqAssetSell.setAuditStatus("0");//等待转出方确认
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
           // 调出方
           dbzxEqAssetSell.setOutOrgID(zxEqAssetSell.getOutOrgID());
           // 是否内部转让
           dbzxEqAssetSell.setInnerTran(zxEqAssetSell.getInnerTran());
           // 接收方
           dbzxEqAssetSell.setInOrgID(zxEqAssetSell.getInOrgID());
           // 接收方名称
           dbzxEqAssetSell.setInOrgName(zxEqAssetSell.getInOrgName());
           // 转让依据
           dbzxEqAssetSell.setReason(zxEqAssetSell.getReason());
           // 转让日期
           dbzxEqAssetSell.setBusDate(zxEqAssetSell.getBusDate());
           // 签收日期
           dbzxEqAssetSell.setInDate(zxEqAssetSell.getInDate());
           // 调出单位财会部门经办人
           dbzxEqAssetSell.setOutman2(zxEqAssetSell.getOutman2());
           // 调出单位财会部门负责人
           dbzxEqAssetSell.setOutman1(zxEqAssetSell.getOutman1());
           // 调出单位保管部门
           dbzxEqAssetSell.setOutman5(zxEqAssetSell.getOutman5());
           // 调出单位管理部门负责人
           dbzxEqAssetSell.setOutman3(zxEqAssetSell.getOutman3());
           // 调出单位管理部门经办人
           dbzxEqAssetSell.setOutman4(zxEqAssetSell.getOutman4());
           // 调入单位财会部门
           dbzxEqAssetSell.setInman1(zxEqAssetSell.getInman1());
           // 调入单位管理部门
           dbzxEqAssetSell.setInman2(zxEqAssetSell.getInman2());
           // 调入单位保管部门
           dbzxEqAssetSell.setInman3(zxEqAssetSell.getInman3());
//           // 审核状态
//           dbzxEqAssetSell.setAuditStatus(zxEqAssetSell.getAuditStatus());
           // 备注
           dbzxEqAssetSell.setRemark(zxEqAssetSell.getRemark());
           // 顺序id
           dbzxEqAssetSell.setOrderID(zxEqAssetSell.getOrderID());
           // 批文号
           dbzxEqAssetSell.setApprovalNo(zxEqAssetSell.getApprovalNo());
           // 共通
           dbzxEqAssetSell.setModifyUserInfo(userKey, realName);
           flag = zxEqAssetSellMapper.updateByPrimaryKey(dbzxEqAssetSell);
           //先删除再新增
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
           //先删除再新增
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
        // 失败
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
        // 失败
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
    		// 审核状态
    		dbzxEqAssetSell.setAuditStatus("3");//已上报
    		// 共通
    		dbzxEqAssetSell.setModifyUserInfo(userKey, realName);
    		flag = zxEqAssetSellMapper.updateByPrimaryKey(dbzxEqAssetSell);
    	}
    	// 失败
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
    		// 审核状态
    		dbzxEqAssetSell.setAuditStatus("4");//已审核
    		// 共通
    		dbzxEqAssetSell.setModifyUserInfo(userKey, realName);
    		flag = zxEqAssetSellMapper.updateByPrimaryKey(dbzxEqAssetSell);
    		//问题号【000004349】
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
					zxEqEquip.setStatus("转让外部单位");
					zxEqEquip.setChangeDate(new Date());
					zxEqEquip.setModifyUserInfo(userKey, realName);
					flag = zxEqEquipMapper.updateByPrimaryKey(zxEqEquip);
				}
			}
    	}
    	// 失败
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
    		// 审核状态
    		dbzxEqAssetSell.setAuditStatus("5");//审核不通过
    		// 共通
    		dbzxEqAssetSell.setModifyUserInfo(userKey, realName);
    		flag = zxEqAssetSellMapper.updateByPrimaryKey(dbzxEqAssetSell);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqAssetSell);
    	}
	}
}
