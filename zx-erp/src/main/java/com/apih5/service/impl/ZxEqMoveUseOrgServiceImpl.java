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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1) || StrUtil.equals("admin", userId)) {
        	zxEqMoveUseOrg.setOutComID("");
        	zxEqMoveUseOrg.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqMoveUseOrg.setSeeFlagForCom(zxEqMoveUseOrg.getOutOrgID());
//        	zxEqMoveUseOrg.setOutComID(zxEqMoveUseOrg.getOutOrgID());
        	zxEqMoveUseOrg.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqMoveUseOrg.setSeeFlagForPro(zxEqMoveUseOrg.getOutOrgID());
        	zxEqMoveUseOrg.setOutOrgID("");
        }
        // 分页查询
        PageHelper.startPage(zxEqMoveUseOrg.getPage(),zxEqMoveUseOrg.getLimit());
        // 获取数据
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
        // 得到分页信息
        PageInfo<ZxEqMoveUseOrg> p = new PageInfo<>(zxEqMoveUseOrgList);

        return repEntity.okList(zxEqMoveUseOrgList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqMoveUseOrgDetails(ZxEqMoveUseOrg zxEqMoveUseOrg) {
        if (zxEqMoveUseOrg == null) {
            zxEqMoveUseOrg = new ZxEqMoveUseOrg();
        }
        // 获取数据
        ZxEqMoveUseOrg dbZxEqMoveUseOrg = zxEqMoveUseOrgMapper.selectByPrimaryKey(zxEqMoveUseOrg.getId());
        // 数据存在
        if (dbZxEqMoveUseOrg != null) {
            return repEntity.ok(dbZxEqMoveUseOrg);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqMoveUseOrg(ZxEqMoveUseOrg zxEqMoveUseOrg) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        zxEqMoveUseOrg.setId(UuidUtil.generate());
        zxEqMoveUseOrg.setApproveStatus("0");//待调出方确认
        zxEqMoveUseOrg.setCreateUserInfo(userKey, realName);
        if(zxEqMoveUseOrg.getItemList() != null && zxEqMoveUseOrg.getItemList().size() >0) {	
        	for (ZxEqMoveUseOrgItem lib : zxEqMoveUseOrg.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setMoveID(zxEqMoveUseOrg.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqMoveUseOrgItemMapper.insert(lib);
			}
        }else {
        	return repEntity.layerMessage("no", "该项目下设备不能为空！");
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
           // 调出方id
           dbzxEqMoveUseOrg.setOutOrgID(zxEqMoveUseOrg.getOutOrgID());
           // 接收方id
           dbzxEqMoveUseOrg.setAcceptOrgID(zxEqMoveUseOrg.getAcceptOrgID());
           // 调出方name
           dbzxEqMoveUseOrg.setOutOrgName(zxEqMoveUseOrg.getOutOrgName());
           // 接收方name
           dbzxEqMoveUseOrg.setAcceptOrgName(zxEqMoveUseOrg.getAcceptOrgName());
           // 调令日期
           dbzxEqMoveUseOrg.setMovedate(zxEqMoveUseOrg.getMovedate());
           // 交付地点
           dbzxEqMoveUseOrg.setDeliveryLocation(zxEqMoveUseOrg.getDeliveryLocation());
           // 转场费用
           dbzxEqMoveUseOrg.setMoveExes(zxEqMoveUseOrg.getMoveExes());
           // 费用承担单位
           dbzxEqMoveUseOrg.setExesPayOrg(zxEqMoveUseOrg.getExesPayOrg());
           // 调出方意见
           dbzxEqMoveUseOrg.setOutOpinion(zxEqMoveUseOrg.getOutOpinion());
           // 调出方经手人
           dbzxEqMoveUseOrg.setOutTransactor(zxEqMoveUseOrg.getOutTransactor());
           // 接收方意见
           dbzxEqMoveUseOrg.setAcceptOpinion(zxEqMoveUseOrg.getAcceptOpinion());
           // 接收方负责人
           dbzxEqMoveUseOrg.setAcceptTransactor(zxEqMoveUseOrg.getAcceptTransactor());
           // 主管单位意见
           dbzxEqMoveUseOrg.setAdminOrgOpinion(zxEqMoveUseOrg.getAdminOrgOpinion());
           // 审批状态
           dbzxEqMoveUseOrg.setApproveStatus(zxEqMoveUseOrg.getApproveStatus());
           // 备注
           dbzxEqMoveUseOrg.setRemark(zxEqMoveUseOrg.getRemark());
           // 调令号
           dbzxEqMoveUseOrg.setTransferNo(zxEqMoveUseOrg.getTransferNo());
           // 调拨依据
           dbzxEqMoveUseOrg.setReason(zxEqMoveUseOrg.getReason());
           // 注意事项
           dbzxEqMoveUseOrg.setCareinfo(zxEqMoveUseOrg.getCareinfo());
           // 主管领导
           dbzxEqMoveUseOrg.setAdminLeader(zxEqMoveUseOrg.getAdminLeader());
           // 机械主管
           dbzxEqMoveUseOrg.setEquipAdmin(zxEqMoveUseOrg.getEquipAdmin());
           // 财务科
           dbzxEqMoveUseOrg.setFinance(zxEqMoveUseOrg.getFinance());
           // 制表人
           dbzxEqMoveUseOrg.setLister(zxEqMoveUseOrg.getLister());
           // 
           dbzxEqMoveUseOrg.setSerialNo(zxEqMoveUseOrg.getSerialNo());
           // 调入方设备管理部门
           dbzxEqMoveUseOrg.setInEquipManageDpt(zxEqMoveUseOrg.getInEquipManageDpt());
           // 调入方财务管理部门
           dbzxEqMoveUseOrg.setAcceptFinance(zxEqMoveUseOrg.getAcceptFinance());
           // 所在地点
           dbzxEqMoveUseOrg.setLocality(zxEqMoveUseOrg.getLocality());
           // comId
           dbzxEqMoveUseOrg.setComID(zxEqMoveUseOrg.getComID());
           // 公司名称
           dbzxEqMoveUseOrg.setComName(zxEqMoveUseOrg.getComName());
           // 调出方所属公司id
           dbzxEqMoveUseOrg.setOutComID(zxEqMoveUseOrg.getOutComID());
           // 调出方所属公司name
           dbzxEqMoveUseOrg.setOutComName(zxEqMoveUseOrg.getOutComName());
           // 接收方所属公司id
           dbzxEqMoveUseOrg.setAcceptComID(zxEqMoveUseOrg.getAcceptComID());
           // 接收方所属公司name
           dbzxEqMoveUseOrg.setAcceptComName(zxEqMoveUseOrg.getAcceptComName());
           // 共通
           dbzxEqMoveUseOrg.setModifyUserInfo(userKey, realName);
          
           //先删除再新增
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
        	   return repEntity.layerMessage("no", "该项目下设备不能为空！");
           }
           flag = zxEqMoveUseOrgMapper.updateByPrimaryKey(dbzxEqMoveUseOrg);
           //先删除再新增
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
        // 失败
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
        // 失败
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
			// 审批状态
			dbzxEqMoveUseOrg.setApproveStatus("1");//待调入方确认
			// 共通
			dbzxEqMoveUseOrg.setModifyUserInfo(userKey, realName);
			flag = zxEqMoveUseOrgMapper.updateByPrimaryKey(dbzxEqMoveUseOrg);
		}
		// 失败
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
	           // 接收方负责人
	           dbzxEqMoveUseOrg.setAcceptTransactor(zxEqMoveUseOrg.getAcceptTransactor());
	           // 审批状态
	           dbzxEqMoveUseOrg.setApproveStatus("2");//办理完毕
	           //调入方财务部门
	           dbzxEqMoveUseOrg.setAcceptFinance(zxEqMoveUseOrg.getAcceptFinance());
	           // 调入方设备管理部门
	           dbzxEqMoveUseOrg.setInEquipManageDpt(zxEqMoveUseOrg.getInEquipManageDpt());
	           // 所在地点
	           dbzxEqMoveUseOrg.setLocality(zxEqMoveUseOrg.getLocality());
	           // 共通
	           dbzxEqMoveUseOrg.setModifyUserInfo(userKey, realName);
	           flag = zxEqMoveUseOrgMapper.updateByPrimaryKey(dbzxEqMoveUseOrg);
	           //先删除再新增
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
	           //将台账中的使用单位和所属单位进行update
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
	        		   //将自有设备中的使用单位和所属单位进行update
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
	        // 失败
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	            return repEntity.ok("sys.data.update",zxEqMoveUseOrg);
	        }
	}
}
