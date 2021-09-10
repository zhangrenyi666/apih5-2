package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqUseEquipInMapper;
import com.apih5.mybatis.dao.ZxEqUseEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.mybatis.pojo.ZxEqUseEquipIn;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqUseEquipInService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqUseEquipInService")
public class ZxEqUseEquipInServiceImpl implements ZxEqUseEquipInService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqUseEquipInMapper zxEqUseEquipInMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqUseEquipMapper zxEqUseEquipMapper;
   
    @Override
    public ResponseEntity getZxEqUseEquipInListByCondition(ZxEqUseEquipIn zxEqUseEquipIn) {
        if (zxEqUseEquipIn == null) {
            zxEqUseEquipIn = new ZxEqUseEquipIn();
        }
        // 分页查询
        PageHelper.startPage(zxEqUseEquipIn.getPage(),zxEqUseEquipIn.getLimit());
        // 获取数据
        List<ZxEqUseEquipIn> zxEqUseEquipInList = zxEqUseEquipInMapper.selectByZxEqUseEquipInList(zxEqUseEquipIn);
        for (ZxEqUseEquipIn zxEqUseEquipIn2 : zxEqUseEquipInList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqUseEquipIn2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqUseEquipIn2.setFileList(fileList);
		}
        // 得到分页信息
        PageInfo<ZxEqUseEquipIn> p = new PageInfo<>(zxEqUseEquipInList);

        return repEntity.okList(zxEqUseEquipInList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqUseEquipInDetails(ZxEqUseEquipIn zxEqUseEquipIn) {
        if (zxEqUseEquipIn == null) {
            zxEqUseEquipIn = new ZxEqUseEquipIn();
        }
        // 获取数据
        ZxEqUseEquipIn dbZxEqUseEquipIn = zxEqUseEquipInMapper.selectByPrimaryKey(zxEqUseEquipIn.getId());
        // 数据存在
        if (dbZxEqUseEquipIn != null) {
            return repEntity.ok(dbZxEqUseEquipIn);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqUseEquipIn(ZxEqUseEquipIn zxEqUseEquipIn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqUseEquipIn.setId(UuidUtil.generate());
        zxEqUseEquipIn.setCreateUserInfo(userKey, realName);
        int flag = zxEqUseEquipInMapper.insert(zxEqUseEquipIn);
        if(zxEqUseEquipIn.getFileList() != null && zxEqUseEquipIn.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqUseEquipIn.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqUseEquipIn.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        ZxEqUseEquip useEquip = zxEqUseEquipMapper.selectByPrimaryKey(zxEqUseEquipIn.getUseEquipID());
        if(useEquip != null && StrUtil.isNotEmpty(useEquip.getId())) {
        	useEquip.setInDate(zxEqUseEquipIn.getInDate());
        	useEquip.setModifyUserInfo(userKey, realName);
        	flag = zxEqUseEquipMapper.updateByPrimaryKey(useEquip);
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqUseEquipIn);
        }
    }

    @Override
    public ResponseEntity updateZxEqUseEquipIn(ZxEqUseEquipIn zxEqUseEquipIn) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqUseEquipIn dbzxEqUseEquipIn = zxEqUseEquipInMapper.selectByPrimaryKey(zxEqUseEquipIn.getId());
        if (dbzxEqUseEquipIn != null && StrUtil.isNotEmpty(dbzxEqUseEquipIn.getId())) {
           // 
           dbzxEqUseEquipIn.setUseEquipID(zxEqUseEquipIn.getUseEquipID());
           // 所属单位id
           dbzxEqUseEquipIn.setOwnerOrgID(zxEqUseEquipIn.getOwnerOrgID());
           // 
           dbzxEqUseEquipIn.setOwnerOrg(zxEqUseEquipIn.getOwnerOrg());
           // 使用单位
           dbzxEqUseEquipIn.setUseOrg(zxEqUseEquipIn.getUseOrg());
           // 使用单位id
           dbzxEqUseEquipIn.setUseOrgId(zxEqUseEquipIn.getUseOrgId());
           // 所在地点
           dbzxEqUseEquipIn.setLocality(zxEqUseEquipIn.getLocality());
           // 
           dbzxEqUseEquipIn.setEquipMuID(zxEqUseEquipIn.getEquipMuID());
           // 设备CbsID
           dbzxEqUseEquipIn.setEquipCbsID(zxEqUseEquipIn.getEquipCbsID());
           // 资源分类
           dbzxEqUseEquipIn.setResCatalog(zxEqUseEquipIn.getResCatalog());
           // 资源分类id
           dbzxEqUseEquipIn.setResCatalogID(zxEqUseEquipIn.getResCatalogID());
           // 机械管理编号
           dbzxEqUseEquipIn.setEquipNo(zxEqUseEquipIn.getEquipNo());
           // 机械名称
           dbzxEqUseEquipIn.setEquipName(zxEqUseEquipIn.getEquipName());
           // 规格
           dbzxEqUseEquipIn.setSpec(zxEqUseEquipIn.getSpec());
           // 型号
           dbzxEqUseEquipIn.setModel(zxEqUseEquipIn.getModel());
           // 品牌名称
           dbzxEqUseEquipIn.setBrandName(zxEqUseEquipIn.getBrandName());
           // 计量单位
           dbzxEqUseEquipIn.setMeasureUnit(zxEqUseEquipIn.getMeasureUnit());
           // 设备来源
           dbzxEqUseEquipIn.setOrigin(zxEqUseEquipIn.getOrigin());
           // 设备类型
           dbzxEqUseEquipIn.setEquipType(zxEqUseEquipIn.getEquipType());
           // 功率
           dbzxEqUseEquipIn.setPowerValue(zxEqUseEquipIn.getPowerValue());
           // 功率单位
           dbzxEqUseEquipIn.setPowerUnit(zxEqUseEquipIn.getPowerUnit());
           // 机车能力
           dbzxEqUseEquipIn.setMotoAbility(zxEqUseEquipIn.getMotoAbility());
           // 能力单位
           dbzxEqUseEquipIn.setAbilityUnit(zxEqUseEquipIn.getAbilityUnit());
           // 油耗单位
           dbzxEqUseEquipIn.setFuelExpendUnit(zxEqUseEquipIn.getFuelExpendUnit());
           // 单位耗油
           dbzxEqUseEquipIn.setUnitFuelExpend(zxEqUseEquipIn.getUnitFuelExpend());
           // 操作手
           dbzxEqUseEquipIn.setOperater(zxEqUseEquipIn.getOperater());
           // 技术状况
           dbzxEqUseEquipIn.setTechnicalPosition(zxEqUseEquipIn.getTechnicalPosition());
           // 使用状态
           dbzxEqUseEquipIn.setUseStatus(zxEqUseEquipIn.getUseStatus());
           // 进场日期
           dbzxEqUseEquipIn.setInDate(zxEqUseEquipIn.getInDate());
           // 退场日期
           dbzxEqUseEquipIn.setOutDate(zxEqUseEquipIn.getOutDate());
           // 备注
           dbzxEqUseEquipIn.setRemark(zxEqUseEquipIn.getRemark());
           // 
           dbzxEqUseEquipIn.setRefEquipID(zxEqUseEquipIn.getRefEquipID());
           // 
           dbzxEqUseEquipIn.setBizType(zxEqUseEquipIn.getBizType());
           // 验收总体评价
           dbzxEqUseEquipIn.setAcceptance(zxEqUseEquipIn.getAcceptance());
           // 是否特种设备
           dbzxEqUseEquipIn.setIsSepcial(zxEqUseEquipIn.getIsSepcial());
           // 特种设备检验报告
           dbzxEqUseEquipIn.setInspReport(zxEqUseEquipIn.getInspReport());
           // 特种设备使用登记证
           dbzxEqUseEquipIn.setInspCert(zxEqUseEquipIn.getInspCert());
           // 操作人员证
           dbzxEqUseEquipIn.setOpCert(zxEqUseEquipIn.getOpCert());
           // 出厂日期
           dbzxEqUseEquipIn.setMainoutfactory(zxEqUseEquipIn.getMainoutfactory());
           // 厂家
           dbzxEqUseEquipIn.setOutfactory(zxEqUseEquipIn.getOutfactory());
           // 原值
           dbzxEqUseEquipIn.setOrginalValue(zxEqUseEquipIn.getOrginalValue());
           // 入账日期
           dbzxEqUseEquipIn.setRegdate(zxEqUseEquipIn.getRegdate());
           // 计划文批号
           dbzxEqUseEquipIn.setPlanNo(zxEqUseEquipIn.getPlanNo());
           // 共通
           dbzxEqUseEquipIn.setModifyUserInfo(userKey, realName);
           flag = zxEqUseEquipInMapper.updateByPrimaryKey(dbzxEqUseEquipIn);
           //先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqUseEquipIn.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqUseEquipIn.getFileList() != null && zxEqUseEquipIn.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqUseEquipIn.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqUseEquipIn.getId());
        		   file.setCreateUserInfo(userKey, realName);
        		   flag = zxErpFileMapper.insert(file);
        	   }
           }
           
           ZxEqUseEquip useEquip = zxEqUseEquipMapper.selectByPrimaryKey(dbzxEqUseEquipIn.getUseEquipID());
           if(useEquip != null && StrUtil.isNotEmpty(useEquip.getId())) {
           	useEquip.setInDate(zxEqUseEquipIn.getInDate());
           	useEquip.setModifyUserInfo(userKey, realName);
           	flag = zxEqUseEquipMapper.updateByPrimaryKey(useEquip);
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqUseEquipIn);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqUseEquipIn(List<ZxEqUseEquipIn> zxEqUseEquipInList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqUseEquipInList != null && zxEqUseEquipInList.size() > 0) {
        	for (ZxEqUseEquipIn zxEqUseEquipIn : zxEqUseEquipInList) {
        		ZxErpFile delFile = new ZxErpFile();
        		delFile.setOtherId(zxEqUseEquipIn.getId());
        		List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
        		if(delFileList != null && delFileList.size() >0) {
        			delFile.setModifyUserInfo(userKey, realName);
        			zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
        		}
        	}
        	ZxEqUseEquipIn zxEqUseEquipIn = new ZxEqUseEquipIn();
           zxEqUseEquipIn.setModifyUserInfo(userKey, realName);
           flag = zxEqUseEquipInMapper.batchDeleteUpdateZxEqUseEquipIn(zxEqUseEquipInList, zxEqUseEquipIn);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqUseEquipInList);
        }
    }
}
