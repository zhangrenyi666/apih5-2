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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqUseEquip.setComID("");
        	zxEqUseEquip.setUseOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqUseEquip.setComID(zxEqUseEquip.getUseOrgId());
        	zxEqUseEquip.setUseOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqUseEquip.setUseOrgId(zxEqUseEquip.getUseOrgId());
        }
        
        
        
        // 分页查询
        PageHelper.startPage(zxEqUseEquip.getPage(),zxEqUseEquip.getLimit());
        // 获取数据
        List<ZxEqUseEquip> zxEqUseEquipList = zxEqUseEquipMapper.selectByZxEqUseEquipList(zxEqUseEquip);
        for (ZxEqUseEquip zxEqUseEquip2 : zxEqUseEquipList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqUseEquip2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqUseEquip2.setFileList(fileList);
		}
        // 得到分页信息
        PageInfo<ZxEqUseEquip> p = new PageInfo<>(zxEqUseEquipList);

        return repEntity.okList(zxEqUseEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqUseEquipDetails(ZxEqUseEquip zxEqUseEquip) {
        if (zxEqUseEquip == null) {
            zxEqUseEquip = new ZxEqUseEquip();
        }
        // 获取数据
        ZxEqUseEquip dbZxEqUseEquip = zxEqUseEquipMapper.selectByPrimaryKey(zxEqUseEquip.getId());
        // 数据存在
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
            return repEntity.layerMessage("no", "无数据！");
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
           // 所属单位id
           dbzxEqUseEquip.setOwnerOrgID(zxEqUseEquip.getOwnerOrgID());
           // 
           dbzxEqUseEquip.setEquipMuID(zxEqUseEquip.getEquipMuID());
           // 设备CbsId
           dbzxEqUseEquip.setEquipCbsID(zxEqUseEquip.getEquipCbsID());
           // 所属单位
           dbzxEqUseEquip.setOwnerOrg(zxEqUseEquip.getOwnerOrg());
           // 使用单位id
           dbzxEqUseEquip.setUseOrgId(zxEqUseEquip.getUseOrgId());
           // 使用单位
           dbzxEqUseEquip.setUseOrg(zxEqUseEquip.getUseOrg());
           // 所在地点
           dbzxEqUseEquip.setLocality(zxEqUseEquip.getLocality());
           // 资源分类
           dbzxEqUseEquip.setResCatalog(zxEqUseEquip.getResCatalog());
           // 资源分类id
           dbzxEqUseEquip.setResCatalogID(zxEqUseEquip.getResCatalogID());
           // 设备id
           dbzxEqUseEquip.setRefEquipID(zxEqUseEquip.getRefEquipID());
           // 机械管理编号
           dbzxEqUseEquip.setEquipNo(zxEqUseEquip.getEquipNo());
           // 机械名称
           dbzxEqUseEquip.setEquipName(zxEqUseEquip.getEquipName());
           // 规格
           dbzxEqUseEquip.setSpec(zxEqUseEquip.getSpec());
           // 型号
           dbzxEqUseEquip.setModel(zxEqUseEquip.getModel());
           // 品牌名称
           dbzxEqUseEquip.setBrandName(zxEqUseEquip.getBrandName());
           // 计量单位
           dbzxEqUseEquip.setMeasureUnit(zxEqUseEquip.getMeasureUnit());
           // 设备来源
           dbzxEqUseEquip.setOrigin(zxEqUseEquip.getOrigin());
           // 设备类型
           dbzxEqUseEquip.setEquipType(zxEqUseEquip.getEquipType());
           // 功率
           dbzxEqUseEquip.setPowerValue(zxEqUseEquip.getPowerValue());
           // 功率单位
           dbzxEqUseEquip.setPowerUnit(zxEqUseEquip.getPowerUnit());
           // 机车能力
           dbzxEqUseEquip.setMotoAbility(zxEqUseEquip.getMotoAbility());
           // 能力单位
           dbzxEqUseEquip.setAbilityUnit(zxEqUseEquip.getAbilityUnit());
           // 耗油单位
           dbzxEqUseEquip.setFuelExpendUnit(zxEqUseEquip.getFuelExpendUnit());
           // 单位耗油
           dbzxEqUseEquip.setUnitFuelExpend(zxEqUseEquip.getUnitFuelExpend());
           // 操作手
           dbzxEqUseEquip.setOperater(zxEqUseEquip.getOperater());
           // 技术状况
           dbzxEqUseEquip.setTechnicalPosition(zxEqUseEquip.getTechnicalPosition());
           // 使用状态
           dbzxEqUseEquip.setUseStatus(zxEqUseEquip.getUseStatus());
           // 进场日期
           dbzxEqUseEquip.setInDate(zxEqUseEquip.getInDate());
           // 退场日期
           dbzxEqUseEquip.setOutDate(zxEqUseEquip.getOutDate());
           // 备注
           dbzxEqUseEquip.setRemark(zxEqUseEquip.getRemark());
           // 计划批文号
           dbzxEqUseEquip.setPlanNo(zxEqUseEquip.getPlanNo());
           // 入账日期
           dbzxEqUseEquip.setRegdate(zxEqUseEquip.getRegdate());
           // 共通
           dbzxEqUseEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqUseEquipMapper.updateByPrimaryKey(dbzxEqUseEquip);
        }
        // 失败
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqUseEquipList);
        }
    }
}
