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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqMoveSupervisor.setComID("");
        	zxEqMoveSupervisor.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqMoveSupervisor.setSeeFlagForCom(zxEqMoveSupervisor.getOutOrgID());
//        	zxEqMoveSupervisor.setComID(zxEqMoveSupervisor.getOutOrgID());
        	zxEqMoveSupervisor.setOutOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据===看不到没权限
        	zxEqMoveSupervisor.setOutOrgID(zxEqMoveSupervisor.getOutOrgID());
        }
        
        // 分页查询
        PageHelper.startPage(zxEqMoveSupervisor.getPage(),zxEqMoveSupervisor.getLimit());
        // 获取数据
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
        // 得到分页信息
        PageInfo<ZxEqMoveSupervisor> p = new PageInfo<>(zxEqMoveSupervisorList);

        return repEntity.okList(zxEqMoveSupervisorList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqMoveSupervisorDetails(ZxEqMoveSupervisor zxEqMoveSupervisor) {
        if (zxEqMoveSupervisor == null) {
            zxEqMoveSupervisor = new ZxEqMoveSupervisor();
        }
        // 获取数据
        ZxEqMoveSupervisor dbZxEqMoveSupervisor = zxEqMoveSupervisorMapper.selectByPrimaryKey(zxEqMoveSupervisor.getId());
        // 数据存在
        if (dbZxEqMoveSupervisor != null) {
            return repEntity.ok(dbZxEqMoveSupervisor);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
           // 单据号
           dbzxEqMoveSupervisor.setBillNo(zxEqMoveSupervisor.getBillNo());
           // 调出方
           dbzxEqMoveSupervisor.setOutOrgID(zxEqMoveSupervisor.getOutOrgID());
           dbzxEqMoveSupervisor.setOutOrgName(zxEqMoveSupervisor.getOutOrgName());
           // 接收方
           dbzxEqMoveSupervisor.setInOrgID(zxEqMoveSupervisor.getInOrgID());
           dbzxEqMoveSupervisor.setInOrgName(zxEqMoveSupervisor.getInOrgName());
           // 调拨依据
           dbzxEqMoveSupervisor.setReason(zxEqMoveSupervisor.getReason());
           // 调拨日期
           dbzxEqMoveSupervisor.setBusDate(zxEqMoveSupervisor.getBusDate());
           // 签收日期
           dbzxEqMoveSupervisor.setInDate(zxEqMoveSupervisor.getInDate());
           // 调出单位财务部门负责人
           dbzxEqMoveSupervisor.setOutman1(zxEqMoveSupervisor.getOutman1());
           // 调出单位财务部门经办人
           dbzxEqMoveSupervisor.setOutman2(zxEqMoveSupervisor.getOutman2());
           // 调出单位保管部门
           dbzxEqMoveSupervisor.setOutman5(zxEqMoveSupervisor.getOutman5());
           // 调出单位管理部门负责人
           dbzxEqMoveSupervisor.setOutman3(zxEqMoveSupervisor.getOutman3());
           // 调出单位管理部门经办人
           dbzxEqMoveSupervisor.setOutman4(zxEqMoveSupervisor.getOutman4());
           // 调入单位财会部门
           dbzxEqMoveSupervisor.setInman1(zxEqMoveSupervisor.getInman1());
           // 调入单位管理部门
           dbzxEqMoveSupervisor.setInman2(zxEqMoveSupervisor.getInman2());
           // 调入单位保管部门
           dbzxEqMoveSupervisor.setInman3(zxEqMoveSupervisor.getInman3());
           // 状态
           dbzxEqMoveSupervisor.setAuditStatus(zxEqMoveSupervisor.getAuditStatus());
           // 顺序id
           dbzxEqMoveSupervisor.setOrderID(zxEqMoveSupervisor.getOrderID());
           // 备注
           dbzxEqMoveSupervisor.setRemark(zxEqMoveSupervisor.getRemark());
           // 
           dbzxEqMoveSupervisor.setSerialNo(zxEqMoveSupervisor.getSerialNo());
           // 共通
           dbzxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveSupervisorMapper.updateByPrimaryKey(dbzxEqMoveSupervisor);
           //先删除再新增
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
        // 失败
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
        // 失败
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

    		dbzxEqMoveSupervisor.setAuditStatus("1");//待调入方确认
    		// 共通
    		dbzxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
    		flag = zxEqMoveSupervisorMapper.updateByPrimaryKey(dbzxEqMoveSupervisor);

    	}
    	// 失败
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
			//签收日期
			dbzxEqMoveSupervisor.setInDate(new Date());
			// 调入单位财会部门
			dbzxEqMoveSupervisor.setInman1(zxEqMoveSupervisor.getInman1());
			// 调入单位管理部门
			dbzxEqMoveSupervisor.setInman2(zxEqMoveSupervisor.getInman2());
			// 调入单位保管部门
			dbzxEqMoveSupervisor.setInman3(zxEqMoveSupervisor.getInman3());
			// 审批状态
			dbzxEqMoveSupervisor.setAuditStatus("2");//办理完毕
			// 共通
			dbzxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
			flag = zxEqMoveSupervisorMapper.updateByPrimaryKey(dbzxEqMoveSupervisor);

			//变更对应问题票 4349==将台账中的使用单位和所属单位进行update
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
					//将自有设备中的使用单位和所属单位，公司id进行update
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
		// 失败
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
        	zxEqMoveSupervisor.setAuditStatus("3");//设备编号中
        	flag = zxEqMoveSupervisorMapper.batchRequestZxEqMoveSupervisorNum(zxEqMoveSupervisorList, zxEqMoveSupervisor);
        }
        // 失败
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
           // 状态
           dbzxEqMoveSupervisor.setAuditStatus("4");//待调出方确认
           // 共通
           dbzxEqMoveSupervisor.setModifyUserInfo(userKey, realName);
           flag = zxEqMoveSupervisorMapper.updateByPrimaryKey(dbzxEqMoveSupervisor);
           //先删除再新增
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqMoveSupervisor);
        }
	}
}
