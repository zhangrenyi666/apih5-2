package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEquipMapper;
import com.apih5.mybatis.dao.ZxEqPurAccessoriesMapper;
import com.apih5.mybatis.dao.ZxEqPurLibMapper;
import com.apih5.mybatis.dao.ZxEqPurRecordMapper;
import com.apih5.mybatis.dao.ZxEqUseEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqPurAccessories;
import com.apih5.mybatis.pojo.ZxEqPurLib;
import com.apih5.mybatis.pojo.ZxEqPurRecord;
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqPurRecordService;
import com.apih5.utils.QRCodeUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqPurRecordService")
public class ZxEqPurRecordServiceImpl implements ZxEqPurRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqPurRecordMapper zxEqPurRecordMapper;

    @Autowired(required = true)
    private ZxEqPurAccessoriesMapper zxEqPurAccessoriesMapper;
    
    @Autowired(required = true)
    private ZxEqPurLibMapper zxEqPurLibMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;
    
    @Autowired(required = true)
    private ZxEqUseEquipMapper zxEqUseEquipMapper;
    
    @ApolloConfig	
  	private Config config;

    @Override
    public ResponseEntity getZxEqPurRecordListByCondition(ZxEqPurRecord zxEqPurRecord) {
        if (zxEqPurRecord == null) {
            zxEqPurRecord = new ZxEqPurRecord();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqPurRecord.setCompanyId("");
            zxEqPurRecord.setUseProjID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqPurRecord.setCompanyId(zxEqPurRecord.getUseProjID());
        	zxEqPurRecord.setUseProjID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqPurRecord.setUseProjID(zxEqPurRecord.getUseProjID());
//        	zxEqPurRecord.setOrgID(zxEqPurRecord.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxEqPurRecord.getPage(),zxEqPurRecord.getLimit());
        // 获取数据
        List<ZxEqPurRecord> zxEqPurRecordList = zxEqPurRecordMapper.selectByZxEqPurRecordList(zxEqPurRecord);
        for (ZxEqPurRecord zxEqPurRecord2 : zxEqPurRecordList) {
        	ZxEqPurAccessories accessories = new ZxEqPurAccessories();
        	accessories.setMasID(zxEqPurRecord2.getId());
        	List<ZxEqPurAccessories> zxEqPurAccessoriesList = zxEqPurAccessoriesMapper.selectByZxEqPurAccessoriesList(accessories);
        	zxEqPurRecord2.setZxEqPurAccessoriesList(zxEqPurAccessoriesList);
			
        	ZxEqPurLib lib = new ZxEqPurLib();
        	lib.setMasID(zxEqPurRecord2.getId());
        	List<ZxEqPurLib> zxEqPurLibList = zxEqPurLibMapper.selectByZxEqPurLibList(lib);
        	zxEqPurRecord2.setZxEqPurLibList(zxEqPurLibList);
        	
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqPurRecord2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqPurRecord2.setFileList(fileList);
		}
        // 得到分页信息
        PageInfo<ZxEqPurRecord> p = new PageInfo<>(zxEqPurRecordList);

        return repEntity.okList(zxEqPurRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqPurRecordDetails(ZxEqPurRecord zxEqPurRecord) {
        if (zxEqPurRecord == null) {
            zxEqPurRecord = new ZxEqPurRecord();
        }
        // 获取数据
        ZxEqPurRecord dbZxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
        // 数据存在
        if (dbZxEqPurRecord != null) {
            return repEntity.ok(dbZxEqPurRecord);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqPurRecord(ZxEqPurRecord zxEqPurRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqPurRecord.setId(UuidUtil.generate());
        zxEqPurRecord.setIsimported("0");//0:未申请入帐 
        zxEqPurRecord.setCreateUserInfo(userKey, realName);
        int flag = zxEqPurRecordMapper.insert(zxEqPurRecord);
        if(zxEqPurRecord.getZxEqPurAccessoriesList() != null && zxEqPurRecord.getZxEqPurAccessoriesList().size() >0){
        	for (ZxEqPurAccessories accessories : zxEqPurRecord.getZxEqPurAccessoriesList()) {
        		accessories.setId(UuidUtil.generate());
        		accessories.setMasID(zxEqPurRecord.getId());
        		accessories.setCreateUserInfo(userKey, realName);
        		flag = zxEqPurAccessoriesMapper.insert(accessories);
			}
        }
        if(zxEqPurRecord.getZxEqPurLibList() != null && zxEqPurRecord.getZxEqPurLibList().size() >0) {	
        	for (ZxEqPurLib lib : zxEqPurRecord.getZxEqPurLibList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setMasID(zxEqPurRecord.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqPurLibMapper.insert(lib);
			}
        }
        if(zxEqPurRecord.getFileList() != null && zxEqPurRecord.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqPurRecord.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqPurRecord.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqPurRecord);
        }
    }

    @Override
    public ResponseEntity updateZxEqPurRecord(ZxEqPurRecord zxEqPurRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqPurRecord dbzxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
        if (dbzxEqPurRecord != null && StrUtil.isNotEmpty(dbzxEqPurRecord.getId())) {
           // 
           dbzxEqPurRecord.setContractID(zxEqPurRecord.getContractID());
           // 
           dbzxEqPurRecord.setSupplieID(zxEqPurRecord.getSupplieID());
           // 
           dbzxEqPurRecord.setSource(zxEqPurRecord.getSource());
           // 购置日期
           dbzxEqPurRecord.setPurDate(zxEqPurRecord.getPurDate());
           // 采购地点
           dbzxEqPurRecord.setPurPlace(zxEqPurRecord.getPurPlace());
           // 使用单位
           dbzxEqPurRecord.setUesProject(zxEqPurRecord.getUesProject());
           // 财务固定资产编号
           dbzxEqPurRecord.setFinanceNo(zxEqPurRecord.getFinanceNo());
           // 设备名称一类
           dbzxEqPurRecord.setEquipno(zxEqPurRecord.getEquipno());
           // 规格
           dbzxEqPurRecord.setSpec(zxEqPurRecord.getSpec());
           // 型号
           dbzxEqPurRecord.setModel(zxEqPurRecord.getModel());
           // 国家厂家
           dbzxEqPurRecord.setFactory(zxEqPurRecord.getFactory());
           // 出厂日期
           dbzxEqPurRecord.setOutfactoryDate(zxEqPurRecord.getOutfactoryDate());
           // 新旧程度
           dbzxEqPurRecord.setOldrate(zxEqPurRecord.getOldrate());
           // 主机厂牌
           dbzxEqPurRecord.setMainFactory(zxEqPurRecord.getMainFactory());
           // 主机型号
           dbzxEqPurRecord.setMainspec(zxEqPurRecord.getMainspec());
           // 
           dbzxEqPurRecord.setMainpower(zxEqPurRecord.getMainpower());
           // 主机系列号
           dbzxEqPurRecord.setMainserial(zxEqPurRecord.getMainserial());
           // 主机出厂日期
           dbzxEqPurRecord.setMainoutfactory(zxEqPurRecord.getMainoutfactory());
           // 副机厂牌
           dbzxEqPurRecord.setViceFactory(zxEqPurRecord.getViceFactory());
           // 副机型号
           dbzxEqPurRecord.setVicespec(zxEqPurRecord.getVicespec());
           // 
           dbzxEqPurRecord.setVicepower(zxEqPurRecord.getVicepower());
           // 副机系列号
           dbzxEqPurRecord.setViceserial(zxEqPurRecord.getViceserial());
           // 副机出厂日期
           dbzxEqPurRecord.setViceoutfactory(zxEqPurRecord.getViceoutfactory());
           // 底盘厂家
           dbzxEqPurRecord.setDownfactory(zxEqPurRecord.getDownfactory());
           // 底盘型式
           dbzxEqPurRecord.setDownspec(zxEqPurRecord.getDownspec());
           // 底盘系列号
           dbzxEqPurRecord.setDownserial(zxEqPurRecord.getDownserial());
           // 底盘出厂日期
           dbzxEqPurRecord.setDownoutfactory(zxEqPurRecord.getDownoutfactory());
           // 原价（不含税）（元）
           dbzxEqPurRecord.setOrginalvalue(zxEqPurRecord.getOrginalvalue());
           // 配套件价值（元）
           dbzxEqPurRecord.setVicevalue(zxEqPurRecord.getVicevalue());
           // 运杂费\购置税（元）
           dbzxEqPurRecord.setTranvalue(zxEqPurRecord.getTranvalue());
           // 完全价值（合计：元）
           dbzxEqPurRecord.setTotalvalue(zxEqPurRecord.getTotalvalue());
           // 牌照号码
           dbzxEqPurRecord.setPassNo(zxEqPurRecord.getPassNo());
           // 
           dbzxEqPurRecord.setIsimported(zxEqPurRecord.getIsimported());
           // 备注
           dbzxEqPurRecord.setRemark(zxEqPurRecord.getRemark());
           // 设备分类
           dbzxEqPurRecord.setResCatalog(zxEqPurRecord.getResCatalog());
           // 设备分类id
           dbzxEqPurRecord.setResCatalogID(zxEqPurRecord.getResCatalogID());
           // 出厂系列号
           dbzxEqPurRecord.setOutFactorySerial(zxEqPurRecord.getOutFactorySerial());
           // 计划批文号
           dbzxEqPurRecord.setPlanNo(zxEqPurRecord.getPlanNo());
           // 验收单号
           dbzxEqPurRecord.setAcceptNO(zxEqPurRecord.getAcceptNO());
           // 自重
           dbzxEqPurRecord.setWeight(zxEqPurRecord.getWeight());
           // 外型尺寸长宽高
           dbzxEqPurRecord.setHeightlong(zxEqPurRecord.getHeightlong());
           // 
           dbzxEqPurRecord.setUseProjID(zxEqPurRecord.getUseProjID());
           // 
           dbzxEqPurRecord.setLocality(zxEqPurRecord.getLocality());
           // 国产进口
           dbzxEqPurRecord.setIsMadeinChina(zxEqPurRecord.getIsMadeinChina());
           // 验收时间
           dbzxEqPurRecord.setAcceptanceDate(zxEqPurRecord.getAcceptanceDate());
           // 
           dbzxEqPurRecord.setIsWorkEquip(zxEqPurRecord.getIsWorkEquip());
           // 主机功率（KW）
           dbzxEqPurRecord.setMainPowerStr(zxEqPurRecord.getMainPowerStr());
           // 付机功率（KW)
           dbzxEqPurRecord.setVicePowerStr(zxEqPurRecord.getVicePowerStr());
           // 总功率（KW)
           dbzxEqPurRecord.setPower(zxEqPurRecord.getPower());
           // 折旧年限
           dbzxEqPurRecord.setDepreciation(zxEqPurRecord.getDepreciation());
           // 设备名称二类
           dbzxEqPurRecord.setEquipnoSecond(zxEqPurRecord.getEquipnoSecond());
           // 所属区域
           dbzxEqPurRecord.setArea(zxEqPurRecord.getArea());
           // 共通
           dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
           flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
           //先删除再新增
           ZxEqPurAccessories delAccessories = new ZxEqPurAccessories();
           delAccessories.setMasID(zxEqPurRecord.getId());
           List<ZxEqPurAccessories> delAccessoriesList = zxEqPurAccessoriesMapper.selectByZxEqPurAccessoriesList(delAccessories);
           if(delAccessoriesList != null &&delAccessoriesList.size() >0) {
        	   delAccessories.setModifyUserInfo(userKey, realName);
        	   zxEqPurAccessoriesMapper.batchDeleteUpdateZxEqPurAccessories(delAccessoriesList, delAccessories);
           }
           if(zxEqPurRecord.getZxEqPurAccessoriesList() != null && zxEqPurRecord.getZxEqPurAccessoriesList().size() >0){
        	   for (ZxEqPurAccessories accessories : zxEqPurRecord.getZxEqPurAccessoriesList()) {
        		   accessories.setId(UuidUtil.generate());
        		   accessories.setMasID(zxEqPurRecord.getId());
        		   accessories.setCreateUserInfo(userKey, realName);
        		   flag = zxEqPurAccessoriesMapper.insert(accessories);
        	   }
           }
           //先删除再新增
           ZxEqPurLib delLib = new ZxEqPurLib();
           delLib.setMasID(zxEqPurRecord.getId());
           List<ZxEqPurLib> delLibList = zxEqPurLibMapper.selectByZxEqPurLibList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqPurLibMapper.batchDeleteUpdateZxEqPurLib(delLibList, delLib);
           }
           if(zxEqPurRecord.getZxEqPurLibList() != null && zxEqPurRecord.getZxEqPurLibList().size() >0) {	
        	   for (ZxEqPurLib lib : zxEqPurRecord.getZxEqPurLibList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMasID(zxEqPurRecord.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqPurLibMapper.insert(lib);
        	   }
           }
           //先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqPurRecord.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqPurRecord.getFileList() != null && zxEqPurRecord.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqPurRecord.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqPurRecord.getId());
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
            return repEntity.ok("sys.data.update",zxEqPurRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqPurRecord(List<ZxEqPurRecord> zxEqPurRecordList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zxEqPurRecordList != null && zxEqPurRecordList.size() > 0) {
    		for (ZxEqPurRecord zxEqPurRecord : zxEqPurRecordList) {
    			ZxEqPurRecord record = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
    			if(record != null && StrUtil.isNotEmpty(record.getId())) {
    				if(StrUtil.equals(record.getIsimported(), "1")) {
    					return repEntity.layerMessage("no", "包含审核中的数据，不能删除，请重新选择！");
    				}
    			}
    		}
    		for (ZxEqPurRecord zxEqPurRecord : zxEqPurRecordList) {
    			ZxEqPurAccessories delAccessories = new ZxEqPurAccessories();
    			delAccessories.setMasID(zxEqPurRecord.getId());
    			List<ZxEqPurAccessories> delAccessoriesList = zxEqPurAccessoriesMapper.selectByZxEqPurAccessoriesList(delAccessories);
    			if(delAccessoriesList != null &&delAccessoriesList.size() >0) {
    				delAccessories.setModifyUserInfo(userKey, realName);
    				zxEqPurAccessoriesMapper.batchDeleteUpdateZxEqPurAccessories(delAccessoriesList, delAccessories);
    			}
    			ZxEqPurLib delLib = new ZxEqPurLib();
    			delLib.setMasID(zxEqPurRecord.getId());
    			List<ZxEqPurLib> delLibList = zxEqPurLibMapper.selectByZxEqPurLibList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqPurLibMapper.batchDeleteUpdateZxEqPurLib(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqPurRecord.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
    		ZxEqPurRecord zxEqPurRecord = new ZxEqPurRecord();
    		zxEqPurRecord.setModifyUserInfo(userKey, realName);
    		flag = zxEqPurRecordMapper.batchDeleteUpdateZxEqPurRecord(zxEqPurRecordList, zxEqPurRecord);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
        else {
            return repEntity.ok("sys.data.delete",zxEqPurRecordList);
        }
    }

  

    @Override
    public ResponseEntity requestNumberZxEqPurRecord(List<ZxEqPurRecord> zxEqPurRecordList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if(zxEqPurRecordList != null && zxEqPurRecordList.size() >0) {
    		for (ZxEqPurRecord record : zxEqPurRecordList) {
    			ZxEqPurRecord dbzxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(record.getId());
    			if(StrUtil.equals(dbzxEqPurRecord.getIsimported(), "1")
    					|| StrUtil.equals(dbzxEqPurRecord.getIsimported(), "2")) {
    				return repEntity.layerMessage("no", "包含已经入账数据，请重新选择!");
    			}
    		}
    		//
    		for (ZxEqPurRecord record : zxEqPurRecordList) {
    			ZxEqPurRecord dbzxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(record.getId());
    			if (dbzxEqPurRecord != null && StrUtil.isNotEmpty(dbzxEqPurRecord.getId())) {
    				//入账状态   
    				dbzxEqPurRecord.setIsimported("1");// 1:申请入帐中 
    				// 共通
    				dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
    				flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
    			}
    		}	
    	}
    	// 失败  
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqPurRecordList);
    	}
    }

	@Override
	public ResponseEntity writeNumberZxEqPurRecord(ZxEqPurRecord zxEqPurRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqPurRecord dbzxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
    	if (dbzxEqPurRecord != null && StrUtil.isNotEmpty(dbzxEqPurRecord.getId())) {
    		//管理编号
    		dbzxEqPurRecord.setEquipManNo(zxEqPurRecord.getEquipManNo());
    		//abc类型
    		dbzxEqPurRecord.setAbcType(zxEqPurRecord.getAbcType());
    		//入账状态
    		dbzxEqPurRecord.setIsimported("2");//2:已入帐 
    		// 共通
    		dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
    		flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
    	
    		//同时新增一条台账数据，附件数据--和二维码数据
    		ZxEqEquip copyZxEqEquip = new ZxEqEquip();
    		BeanUtil.copyProperties(dbzxEqPurRecord, copyZxEqEquip);
    		//id
    		copyZxEqEquip.setId(UuidUtil.generate());
    		// 对应购置记录id
            copyZxEqEquip.setPurRecordID(dbzxEqPurRecord.getId());
            // 机械管理编号
            copyZxEqEquip.setEquipNo(zxEqPurRecord.getEquipManNo());
            // 使用单位id                     记录表中的UseProjID==台账表中的UseOrgID
            copyZxEqEquip.setUseOrgID(dbzxEqPurRecord.getUseProjID());
            // 使用单位name
            copyZxEqEquip.setUseOrgName(dbzxEqPurRecord.getUesProject());
            // 购置日期
            copyZxEqEquip.setPurDate(dbzxEqPurRecord.getPurDate());
            //出厂日期
            copyZxEqEquip.setOutFactoryDate(dbzxEqPurRecord.getOutfactoryDate());
            // 所在地点 === 所在地点(台账-usePlace) --- 购置记录-locality
            copyZxEqEquip.setUsePlace(dbzxEqPurRecord.getLocality());
            // 所属单位id ===所属单位（台账-ownerOrgId）--计提单位（折旧-orgID）购置单位（购置记录-orgID）
            copyZxEqEquip.setOwnerOrgId(dbzxEqPurRecord.getOrgID());
            //所属单位name
            copyZxEqEquip.setOwnerOrgName(dbzxEqPurRecord.getOrgName());
//            // 管理单位
//            dbzxEqEquip.setManageOrgID(dbzxEqPurRecord.getManageOrgID());
            // 设备名称=  设备名称（台账-equipName）= 设备名称一类（购置记录-equipno）
            copyZxEqEquip.setEquipName(dbzxEqPurRecord.getEquipno());
            // 原值==         设备名称-完全价值（合计：元）
            copyZxEqEquip.setOrginalValue(dbzxEqPurRecord.getTotalvalue());
            // 净值
            copyZxEqEquip.setLeftValue(dbzxEqPurRecord.getTotalvalue());
//            // 折旧导入月份
//            copyZxEqEquip.setDepreImportmonth(dbzxEqPurRecord.getDepreImportmonth());
//            // 引进fob价
//            copyZxEqEquip.setFobPrice(dbzxEqPurRecord.getFobPrice());
//            // 引进总价
//            copyZxEqEquip.setFobAmount(dbzxEqPurRecord.getFobAmount());
//            // 引进总折价
//            copyZxEqEquip.setDiscountAmount(dbzxEqPurRecord.getDiscountAmount());
            // 当前状态
//            copyZxEqEquip.setStatus(dbzxEqPurRecord.getStatus());
            // 重点设备标志
//            copyZxEqEquip.setIsimportant(dbzxEqPurRecord.getIsimportant());
//            // 状态发生日期
//            copyZxEqEquip.setChangeDate(dbzxEqPurRecord.getChangeDate());
//            // 已提折旧
//            copyZxEqEquip.setActualDepreAmt(dbzxEqPurRecord.getActualDepreAmt());
            // 确认状态
//            copyZxEqEquip.setCheckStatus(dbzxEqPurRecord.getCheckStatus());
            // 是否销账
//            copyZxEqEquip.setIsDelete(dbzxEqPurRecord.getIsDelete());
            //入账日期
            copyZxEqEquip.setRegdate(new Date());
            //已折旧月份
            copyZxEqEquip.setDepreciationMonth(0);
            // 
//            copyZxEqEquip.setPlancode(dbzxEqPurRecord.getPlancode());
//            // 
//            copyZxEqEquip.setAttachmentID(dbzxEqPurRecord.getAttachmentID());
//            // 是否闲置
//            copyZxEqEquip.setIsXianzhi(dbzxEqPurRecord.getIsXianzhi());
            //生成二维码
        	String qrcodeContent = config.getProperty("ng.web.url", "") + "regulatoryFrameworkMobile/#/regulatoryFrameworkMobile/RegulatoryFrameDetail/" + copyZxEqEquip.getId();
        	copyZxEqEquip.setQrcodeContent(qrcodeContent);
        	String qrcodeName = "";
        	try {
        		qrcodeName = QRCodeUtil.encode(qrcodeContent, HttpUtil.getUploadPath("zx-erp"));
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	copyZxEqEquip.setQrcodeName(qrcodeName);
        	copyZxEqEquip.setQrcodeUrl(HttpUtil.getUploadPathRelative("zx-erp") + qrcodeName);
        	copyZxEqEquip.setQrcodeDownUrl("downloadFile?filePath="+ copyZxEqEquip.getQrcodeUrl() +"&downName=" + qrcodeName);
        	copyZxEqEquip.setIsXianzhi("0");//0:否   1：是
        	copyZxEqEquip.setCreateUserInfo(userKey, realName);
    		flag = zxEqEquipMapper.insert(copyZxEqEquip);
    		
    		//复制一份自有设备
    		ZxEqUseEquip useEquip = new ZxEqUseEquip();
    		BeanUtil.copyProperties(copyZxEqEquip, useEquip);
    		useEquip.setId(UuidUtil.generate());
    		useEquip.setRefEquipID(copyZxEqEquip.getId());
    		useEquip.setOwnerOrgID(copyZxEqEquip.getOwnerOrgId());
    		useEquip.setOwnerOrg(copyZxEqEquip.getOwnerOrgName());
    		useEquip.setUseOrgId(copyZxEqEquip.getUseOrgID());
    		useEquip.setUseOrg(copyZxEqEquip.getUseOrgName());
    		useEquip.setResCatalog(dbzxEqPurRecord.getResCatalog());
    		useEquip.setPowerValue(copyZxEqEquip.getPower());
    		useEquip.setPowerUnit("KW");
    		useEquip.setLocality(copyZxEqEquip.getUsePlace());
    		useEquip.setOrigin(dbzxEqPurRecord.getSource());
    		useEquip.setComID(dbzxEqPurRecord.getCompanyId());
    		useEquip.setComName(dbzxEqPurRecord.getCompanyName());
    		useEquip.setCreateUserInfo(userKey, realName);
    		flag = zxEqUseEquipMapper.insert(useEquip);
    		
    		ZxErpFile file = new ZxErpFile();
    		file.setOtherId(dbzxEqPurRecord.getId());
    		List<ZxErpFile> files = zxErpFileMapper.selectByZxErpFileList(file);
    		if(files != null && files.size() >0) {
    			for (ZxErpFile fileSelect : files) {
    				ZxErpFile copyFile = new ZxErpFile();
    				BeanUtil.copyProperties(fileSelect, copyFile);
    				copyFile.setUid(UuidUtil.generate());
    				copyFile.setOtherId(copyZxEqEquip.getId());
    				copyFile.setCreateUserInfo(userKey, realName);
    				flag = zxErpFileMapper.insert(copyFile);
    				copyFile.setUid(UuidUtil.generate());
    				copyFile.setOtherId(useEquip.getId());
    				flag = zxErpFileMapper.insert(copyFile);
				}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqPurRecord);
    	}
	}

	  @Override
	    public ResponseEntity returnZxEqPurRecord(ZxEqPurRecord zxEqPurRecord) {
	    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	    	String userKey = TokenUtils.getUserKey(request);
	    	String realName = TokenUtils.getRealName(request);
	    	int flag = 0;
	    	ZxEqPurRecord dbzxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
	    	if (dbzxEqPurRecord != null && StrUtil.isNotEmpty(dbzxEqPurRecord.getId())) {
	    		if(StrUtil.equals(dbzxEqPurRecord.getIsimported(), "1")) {
	    			//入账状态 
	    			dbzxEqPurRecord.setIsimported("-1");//-1:已退回
	    			//共通
	    			dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
	    			flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
	    		}else {
	    			return repEntity.layerMessage("no", "申请入帐中的数据才可以退回");
	    		}
	    	}
	    	// 失败
	    	if (flag == 0) {
	    		return repEntity.errorSave();
	    	}
	    	else {
	    		return repEntity.ok("sys.data.update",zxEqPurRecord);
	    	}
	    }
	  
	@Override
	public ResponseEntity reverseAuditZxEqPurRecord(ZxEqPurRecord zxEqPurRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqPurRecord dbzxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
    	if (dbzxEqPurRecord != null && StrUtil.isNotEmpty(dbzxEqPurRecord.getId())) {
    		//入账状态
    		dbzxEqPurRecord.setIsimported("3");//3:已入账反审核
    		// 共通
    		dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
    		flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
    		//同时删除一条台账数据，附件数据--和二维码数据 和自有设备数据
    		ZxEqEquip delEquip = new ZxEqEquip();
    		delEquip.setPurRecordID(dbzxEqPurRecord.getId());
    		List<ZxEqEquip> delEquipList = zxEqEquipMapper.selectByZxEqEquipList(delEquip);
    		if(delEquipList != null && delEquipList.size() >0) {
    			for (ZxEqEquip zxEqEquip : delEquipList) {
    				ZxErpFile delFile = new ZxErpFile();
    				delFile.setOtherId(zxEqEquip.getId());
    				List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    				if(delFileList != null && delFileList.size() >0) {
    					delFile.setModifyUserInfo(userKey, realName);
    					flag = zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    				}
    				ZxEqUseEquip delUseEquip = new ZxEqUseEquip();
    				delUseEquip.setRefEquipID(zxEqEquip.getId());
    				List<ZxEqUseEquip> delUseEquipList = zxEqUseEquipMapper.selectByZxEqUseEquipList(delUseEquip);
    				if(delUseEquipList != null && delUseEquipList.size() >0) {
    					for (ZxEqUseEquip zxEqUseEquip : delUseEquipList) {
    						ZxErpFile delUseFile = new ZxErpFile();
    						delUseFile.setOtherId(zxEqUseEquip.getId());
    						List<ZxErpFile> delUseFileList = zxErpFileMapper.selectByZxErpFileList(delUseFile);
    						if(delUseFileList != null && delUseFileList.size() >0) {
    							delUseFile.setModifyUserInfo(userKey, realName);
    							flag = zxErpFileMapper.batchDeleteUpdateZxErpFile(delUseFileList, delUseFile);
    						}
    					}
    					delUseEquip.setModifyUserInfo(userKey, realName);
    					flag = zxEqUseEquipMapper.batchDeleteUpdateZxEqUseEquip(delUseEquipList, delUseEquip);
    				}
    			}
    			delEquip.setModifyUserInfo(userKey, realName);
    			flag = zxEqEquipMapper.batchDeleteUpdateZxEqEquip(delEquipList, delEquip);
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqPurRecord);
    	}
	}

	@Override
	public ZxEqPurRecord ureportZxEqPurRecordDetails(ZxEqPurRecord zxEqPurRecord) {
		if (zxEqPurRecord == null) {
			zxEqPurRecord = new ZxEqPurRecord();
		}
		// 获取数据
		ZxEqPurRecord dbZxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
		// 数据存在
		return dbZxEqPurRecord;
	}
	
}