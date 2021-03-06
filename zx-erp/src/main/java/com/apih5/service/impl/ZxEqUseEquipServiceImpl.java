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
import com.apih5.mybatis.dao.ZxEqEWorkItemMapper;
import com.apih5.mybatis.dao.ZxEqEWorkMapper;
import com.apih5.mybatis.dao.ZxEqUseEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqEWork;
import com.apih5.mybatis.pojo.ZxEqEWorkItem;
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqUseEquipService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqUseEquipService")
public class ZxEqUseEquipServiceImpl implements ZxEqUseEquipService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqUseEquipMapper zxEqUseEquipMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqEWorkMapper zxEqEWorkMapper;
    
    @Autowired(required = true)
    private ZxEqEWorkItemMapper zxEqEWorkItemMapper;
    @Override
    public ResponseEntity getZxEqUseEquipListByCondition(ZxEqUseEquip zxEqUseEquip) {
        if (zxEqUseEquip == null) {
            zxEqUseEquip = new ZxEqUseEquip();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqUseEquip.setComID("");
        	zxEqUseEquip.setUseOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqUseEquip.setComID(zxEqUseEquip.getUseOrgId());
        	zxEqUseEquip.setUseOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqUseEquip.setUseOrgId(zxEqUseEquip.getUseOrgId());
        }
        
        
        
        // ????????????
        PageHelper.startPage(zxEqUseEquip.getPage(),zxEqUseEquip.getLimit());
        // ????????????
        List<ZxEqUseEquip> zxEqUseEquipList = zxEqUseEquipMapper.selectByZxEqUseEquipList(zxEqUseEquip);
        for (ZxEqUseEquip zxEqUseEquip2 : zxEqUseEquipList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqUseEquip2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqUseEquip2.setFileList(fileList);
		}
        // ??????????????????
        PageInfo<ZxEqUseEquip> p = new PageInfo<>(zxEqUseEquipList);

        return repEntity.okList(zxEqUseEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqUseEquipDetails(ZxEqUseEquip zxEqUseEquip) {
        if (zxEqUseEquip == null) {
            zxEqUseEquip = new ZxEqUseEquip();
        }
        // ????????????
        ZxEqUseEquip dbZxEqUseEquip = zxEqUseEquipMapper.selectByPrimaryKey(zxEqUseEquip.getId());
        // ????????????
        if (dbZxEqUseEquip != null) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(dbZxEqUseEquip.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	dbZxEqUseEquip.setFileList(fileList);
        	
        	  BigDecimal gasoline = new BigDecimal("0");
              BigDecimal diesel = new BigDecimal("0");
              BigDecimal totalMiles = new BigDecimal("0");
              BigDecimal unitFuelExpend = new BigDecimal("0");
        	
              ZxEqEWork eqEWork = new ZxEqEWork();
              eqEWork.setEquipID(dbZxEqUseEquip.getRefEquipID());
        	List<ZxEqEWork> eqEWorkList = zxEqEWorkMapper.selectByZxEqEWorkList(eqEWork);
        	if(eqEWorkList != null && eqEWorkList.size() >0) {
        		for (ZxEqEWork zxEqEWork : eqEWorkList) {
        			ZxEqEWorkItem eqEWorkItem = new ZxEqEWorkItem();
        			eqEWorkItem.setMainID(zxEqEWork.getId());
        			List<ZxEqEWorkItem> eqEWorkItemList = zxEqEWorkItemMapper.selectByZxEqEWorkItemList(eqEWorkItem);
        			if(eqEWorkItemList != null && eqEWorkItemList.size() >0) {
        				for (ZxEqEWorkItem lib : eqEWorkItemList) {
        					gasoline = CalcUtils.calcAdd(gasoline, lib.getGasoline());
        	        		diesel = CalcUtils.calcAdd(diesel, lib.getDiesel());
        	        		totalMiles = CalcUtils.calcAdd(totalMiles, lib.getRunDay());
						}
        			}
				}
        	}
        	unitFuelExpend = CalcUtils.calcDivide(CalcUtils.calcAdd(gasoline, diesel), totalMiles,2);
        	dbZxEqUseEquip.setUnitFuelExpend(unitFuelExpend);
        	return repEntity.ok(dbZxEqUseEquip);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqUseEquip(ZxEqUseEquip zxEqUseEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqUseEquip.setId(UuidUtil.generate());
        zxEqUseEquip.setCreateUserInfo(userKey, realName);
        int flag = zxEqUseEquipMapper.insert(zxEqUseEquip);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqUseEquip);
        }
    }

    @Override
    public ResponseEntity updateZxEqUseEquip(ZxEqUseEquip zxEqUseEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqUseEquip dbzxEqUseEquip = zxEqUseEquipMapper.selectByPrimaryKey(zxEqUseEquip.getId());
        if (dbzxEqUseEquip != null && StrUtil.isNotEmpty(dbzxEqUseEquip.getId())) {
           // ????????????id
           dbzxEqUseEquip.setOwnerOrgID(zxEqUseEquip.getOwnerOrgID());
           // 
           dbzxEqUseEquip.setEquipMuID(zxEqUseEquip.getEquipMuID());
           // ??????CbsId
           dbzxEqUseEquip.setEquipCbsID(zxEqUseEquip.getEquipCbsID());
           // ????????????
           dbzxEqUseEquip.setOwnerOrg(zxEqUseEquip.getOwnerOrg());
           // ????????????id
           dbzxEqUseEquip.setUseOrgId(zxEqUseEquip.getUseOrgId());
           // ????????????
           dbzxEqUseEquip.setUseOrg(zxEqUseEquip.getUseOrg());
           // ????????????
           dbzxEqUseEquip.setLocality(zxEqUseEquip.getLocality());
           // ????????????
           dbzxEqUseEquip.setResCatalog(zxEqUseEquip.getResCatalog());
           // ????????????id
           dbzxEqUseEquip.setResCatalogID(zxEqUseEquip.getResCatalogID());
           // ??????id
           dbzxEqUseEquip.setRefEquipID(zxEqUseEquip.getRefEquipID());
           // ??????????????????
           dbzxEqUseEquip.setEquipNo(zxEqUseEquip.getEquipNo());
           // ????????????
           dbzxEqUseEquip.setEquipName(zxEqUseEquip.getEquipName());
           // ??????
           dbzxEqUseEquip.setSpec(zxEqUseEquip.getSpec());
           // ??????
           dbzxEqUseEquip.setModel(zxEqUseEquip.getModel());
           // ????????????
           dbzxEqUseEquip.setBrandName(zxEqUseEquip.getBrandName());
           // ????????????
           dbzxEqUseEquip.setMeasureUnit(zxEqUseEquip.getMeasureUnit());
           // ????????????
           dbzxEqUseEquip.setOrigin(zxEqUseEquip.getOrigin());
           // ????????????
           dbzxEqUseEquip.setEquipType(zxEqUseEquip.getEquipType());
           // ??????
           dbzxEqUseEquip.setPowerValue(zxEqUseEquip.getPowerValue());
           // ????????????
           dbzxEqUseEquip.setPowerUnit(zxEqUseEquip.getPowerUnit());
           // ????????????
           dbzxEqUseEquip.setMotoAbility(zxEqUseEquip.getMotoAbility());
           // ????????????
           dbzxEqUseEquip.setAbilityUnit(zxEqUseEquip.getAbilityUnit());
           // ????????????
           dbzxEqUseEquip.setFuelExpendUnit(zxEqUseEquip.getFuelExpendUnit());
           // ????????????
           dbzxEqUseEquip.setUnitFuelExpend(zxEqUseEquip.getUnitFuelExpend());
           // ?????????
           dbzxEqUseEquip.setOperater(zxEqUseEquip.getOperater());
           // ????????????
           dbzxEqUseEquip.setTechnicalPosition(zxEqUseEquip.getTechnicalPosition());
           // ????????????
           dbzxEqUseEquip.setUseStatus(zxEqUseEquip.getUseStatus());
           // ????????????
           dbzxEqUseEquip.setInDate(zxEqUseEquip.getInDate());
           // ????????????
           dbzxEqUseEquip.setOutDate(zxEqUseEquip.getOutDate());
           // ??????
           dbzxEqUseEquip.setRemark(zxEqUseEquip.getRemark());
           // ???????????????
           dbzxEqUseEquip.setPlanNo(zxEqUseEquip.getPlanNo());
           // ????????????
           dbzxEqUseEquip.setRegdate(zxEqUseEquip.getRegdate());
           // ??????
           dbzxEqUseEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqUseEquipMapper.updateByPrimaryKey(dbzxEqUseEquip);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqUseEquip);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqUseEquip(List<ZxEqUseEquip> zxEqUseEquipList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqUseEquipList != null && zxEqUseEquipList.size() > 0) {
           ZxEqUseEquip zxEqUseEquip = new ZxEqUseEquip();
           zxEqUseEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqUseEquipMapper.batchDeleteUpdateZxEqUseEquip(zxEqUseEquipList, zxEqUseEquip);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqUseEquipList);
        }
    }
}
