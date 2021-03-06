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
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqPurRecord.setCompanyId("");
            zxEqPurRecord.setUseProjID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqPurRecord.setCompanyId(zxEqPurRecord.getUseProjID());
        	zxEqPurRecord.setUseProjID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqPurRecord.setUseProjID(zxEqPurRecord.getUseProjID());
//        	zxEqPurRecord.setOrgID(zxEqPurRecord.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxEqPurRecord.getPage(),zxEqPurRecord.getLimit());
        // ????????????
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
        // ??????????????????
        PageInfo<ZxEqPurRecord> p = new PageInfo<>(zxEqPurRecordList);

        return repEntity.okList(zxEqPurRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqPurRecordDetails(ZxEqPurRecord zxEqPurRecord) {
        if (zxEqPurRecord == null) {
            zxEqPurRecord = new ZxEqPurRecord();
        }
        // ????????????
        ZxEqPurRecord dbZxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
        // ????????????
        if (dbZxEqPurRecord != null) {
            return repEntity.ok(dbZxEqPurRecord);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqPurRecord(ZxEqPurRecord zxEqPurRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqPurRecord.setId(UuidUtil.generate());
        zxEqPurRecord.setIsimported("0");//0:??????????????? 
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
           // ????????????
           dbzxEqPurRecord.setPurDate(zxEqPurRecord.getPurDate());
           // ????????????
           dbzxEqPurRecord.setPurPlace(zxEqPurRecord.getPurPlace());
           // ????????????
           dbzxEqPurRecord.setUesProject(zxEqPurRecord.getUesProject());
           // ????????????????????????
           dbzxEqPurRecord.setFinanceNo(zxEqPurRecord.getFinanceNo());
           // ??????????????????
           dbzxEqPurRecord.setEquipno(zxEqPurRecord.getEquipno());
           // ??????
           dbzxEqPurRecord.setSpec(zxEqPurRecord.getSpec());
           // ??????
           dbzxEqPurRecord.setModel(zxEqPurRecord.getModel());
           // ????????????
           dbzxEqPurRecord.setFactory(zxEqPurRecord.getFactory());
           // ????????????
           dbzxEqPurRecord.setOutfactoryDate(zxEqPurRecord.getOutfactoryDate());
           // ????????????
           dbzxEqPurRecord.setOldrate(zxEqPurRecord.getOldrate());
           // ????????????
           dbzxEqPurRecord.setMainFactory(zxEqPurRecord.getMainFactory());
           // ????????????
           dbzxEqPurRecord.setMainspec(zxEqPurRecord.getMainspec());
           // 
           dbzxEqPurRecord.setMainpower(zxEqPurRecord.getMainpower());
           // ???????????????
           dbzxEqPurRecord.setMainserial(zxEqPurRecord.getMainserial());
           // ??????????????????
           dbzxEqPurRecord.setMainoutfactory(zxEqPurRecord.getMainoutfactory());
           // ????????????
           dbzxEqPurRecord.setViceFactory(zxEqPurRecord.getViceFactory());
           // ????????????
           dbzxEqPurRecord.setVicespec(zxEqPurRecord.getVicespec());
           // 
           dbzxEqPurRecord.setVicepower(zxEqPurRecord.getVicepower());
           // ???????????????
           dbzxEqPurRecord.setViceserial(zxEqPurRecord.getViceserial());
           // ??????????????????
           dbzxEqPurRecord.setViceoutfactory(zxEqPurRecord.getViceoutfactory());
           // ????????????
           dbzxEqPurRecord.setDownfactory(zxEqPurRecord.getDownfactory());
           // ????????????
           dbzxEqPurRecord.setDownspec(zxEqPurRecord.getDownspec());
           // ???????????????
           dbzxEqPurRecord.setDownserial(zxEqPurRecord.getDownserial());
           // ??????????????????
           dbzxEqPurRecord.setDownoutfactory(zxEqPurRecord.getDownoutfactory());
           // ??????????????????????????????
           dbzxEqPurRecord.setOrginalvalue(zxEqPurRecord.getOrginalvalue());
           // ????????????????????????
           dbzxEqPurRecord.setVicevalue(zxEqPurRecord.getVicevalue());
           // ?????????\??????????????????
           dbzxEqPurRecord.setTranvalue(zxEqPurRecord.getTranvalue());
           // ??????????????????????????????
           dbzxEqPurRecord.setTotalvalue(zxEqPurRecord.getTotalvalue());
           // ????????????
           dbzxEqPurRecord.setPassNo(zxEqPurRecord.getPassNo());
           // 
           dbzxEqPurRecord.setIsimported(zxEqPurRecord.getIsimported());
           // ??????
           dbzxEqPurRecord.setRemark(zxEqPurRecord.getRemark());
           // ????????????
           dbzxEqPurRecord.setResCatalog(zxEqPurRecord.getResCatalog());
           // ????????????id
           dbzxEqPurRecord.setResCatalogID(zxEqPurRecord.getResCatalogID());
           // ???????????????
           dbzxEqPurRecord.setOutFactorySerial(zxEqPurRecord.getOutFactorySerial());
           // ???????????????
           dbzxEqPurRecord.setPlanNo(zxEqPurRecord.getPlanNo());
           // ????????????
           dbzxEqPurRecord.setAcceptNO(zxEqPurRecord.getAcceptNO());
           // ??????
           dbzxEqPurRecord.setWeight(zxEqPurRecord.getWeight());
           // ?????????????????????
           dbzxEqPurRecord.setHeightlong(zxEqPurRecord.getHeightlong());
           // 
           dbzxEqPurRecord.setUseProjID(zxEqPurRecord.getUseProjID());
           // 
           dbzxEqPurRecord.setLocality(zxEqPurRecord.getLocality());
           // ????????????
           dbzxEqPurRecord.setIsMadeinChina(zxEqPurRecord.getIsMadeinChina());
           // ????????????
           dbzxEqPurRecord.setAcceptanceDate(zxEqPurRecord.getAcceptanceDate());
           // 
           dbzxEqPurRecord.setIsWorkEquip(zxEqPurRecord.getIsWorkEquip());
           // ???????????????KW???
           dbzxEqPurRecord.setMainPowerStr(zxEqPurRecord.getMainPowerStr());
           // ???????????????KW)
           dbzxEqPurRecord.setVicePowerStr(zxEqPurRecord.getVicePowerStr());
           // ????????????KW)
           dbzxEqPurRecord.setPower(zxEqPurRecord.getPower());
           // ????????????
           dbzxEqPurRecord.setDepreciation(zxEqPurRecord.getDepreciation());
           // ??????????????????
           dbzxEqPurRecord.setEquipnoSecond(zxEqPurRecord.getEquipnoSecond());
           // ????????????
           dbzxEqPurRecord.setArea(zxEqPurRecord.getArea());
           // ??????
           dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
           flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
           //??????????????????
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
           //??????????????????
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
           //??????????????????
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
        // ??????
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
    					return repEntity.layerMessage("no", "????????????????????????????????????????????????????????????");
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
    	// ??????
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
    				return repEntity.layerMessage("no", "??????????????????????????????????????????!");
    			}
    		}
    		//
    		for (ZxEqPurRecord record : zxEqPurRecordList) {
    			ZxEqPurRecord dbzxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(record.getId());
    			if (dbzxEqPurRecord != null && StrUtil.isNotEmpty(dbzxEqPurRecord.getId())) {
    				//????????????   
    				dbzxEqPurRecord.setIsimported("1");// 1:??????????????? 
    				// ??????
    				dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
    				flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
    			}
    		}	
    	}
    	// ??????  
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
    		//????????????
    		dbzxEqPurRecord.setEquipManNo(zxEqPurRecord.getEquipManNo());
    		//abc??????
    		dbzxEqPurRecord.setAbcType(zxEqPurRecord.getAbcType());
    		//????????????
    		dbzxEqPurRecord.setIsimported("2");//2:????????? 
    		// ??????
    		dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
    		flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
    	
    		//?????????????????????????????????????????????--??????????????????
    		ZxEqEquip copyZxEqEquip = new ZxEqEquip();
    		BeanUtil.copyProperties(dbzxEqPurRecord, copyZxEqEquip);
    		//id
    		copyZxEqEquip.setId(UuidUtil.generate());
    		// ??????????????????id
            copyZxEqEquip.setPurRecordID(dbzxEqPurRecord.getId());
            // ??????????????????
            copyZxEqEquip.setEquipNo(zxEqPurRecord.getEquipManNo());
            // ????????????id                     ???????????????UseProjID==???????????????UseOrgID
            copyZxEqEquip.setUseOrgID(dbzxEqPurRecord.getUseProjID());
            // ????????????name
            copyZxEqEquip.setUseOrgName(dbzxEqPurRecord.getUesProject());
            // ????????????
            copyZxEqEquip.setPurDate(dbzxEqPurRecord.getPurDate());
            //????????????
            copyZxEqEquip.setOutFactoryDate(dbzxEqPurRecord.getOutfactoryDate());
            // ???????????? === ????????????(??????-usePlace) --- ????????????-locality
            copyZxEqEquip.setUsePlace(dbzxEqPurRecord.getLocality());
            // ????????????id ===?????????????????????-ownerOrgId???--?????????????????????-orgID??????????????????????????????-orgID???
            copyZxEqEquip.setOwnerOrgId(dbzxEqPurRecord.getOrgID());
            //????????????name
            copyZxEqEquip.setOwnerOrgName(dbzxEqPurRecord.getOrgName());
//            // ????????????
//            dbzxEqEquip.setManageOrgID(dbzxEqPurRecord.getManageOrgID());
            // ????????????=  ?????????????????????-equipName???= ?????????????????????????????????-equipno???
            copyZxEqEquip.setEquipName(dbzxEqPurRecord.getEquipno());
            // ??????==         ????????????-??????????????????????????????
            copyZxEqEquip.setOrginalValue(dbzxEqPurRecord.getTotalvalue());
            // ??????
            copyZxEqEquip.setLeftValue(dbzxEqPurRecord.getTotalvalue());
//            // ??????????????????
//            copyZxEqEquip.setDepreImportmonth(dbzxEqPurRecord.getDepreImportmonth());
//            // ??????fob???
//            copyZxEqEquip.setFobPrice(dbzxEqPurRecord.getFobPrice());
//            // ????????????
//            copyZxEqEquip.setFobAmount(dbzxEqPurRecord.getFobAmount());
//            // ???????????????
//            copyZxEqEquip.setDiscountAmount(dbzxEqPurRecord.getDiscountAmount());
            // ????????????
//            copyZxEqEquip.setStatus(dbzxEqPurRecord.getStatus());
            // ??????????????????
//            copyZxEqEquip.setIsimportant(dbzxEqPurRecord.getIsimportant());
//            // ??????????????????
//            copyZxEqEquip.setChangeDate(dbzxEqPurRecord.getChangeDate());
//            // ????????????
//            copyZxEqEquip.setActualDepreAmt(dbzxEqPurRecord.getActualDepreAmt());
            // ????????????
//            copyZxEqEquip.setCheckStatus(dbzxEqPurRecord.getCheckStatus());
            // ????????????
//            copyZxEqEquip.setIsDelete(dbzxEqPurRecord.getIsDelete());
            //????????????
            copyZxEqEquip.setRegdate(new Date());
            //???????????????
            copyZxEqEquip.setDepreciationMonth(0);
            // 
//            copyZxEqEquip.setPlancode(dbzxEqPurRecord.getPlancode());
//            // 
//            copyZxEqEquip.setAttachmentID(dbzxEqPurRecord.getAttachmentID());
//            // ????????????
//            copyZxEqEquip.setIsXianzhi(dbzxEqPurRecord.getIsXianzhi());
            //???????????????
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
        	copyZxEqEquip.setIsXianzhi("0");//0:???   1??????
        	copyZxEqEquip.setCreateUserInfo(userKey, realName);
    		flag = zxEqEquipMapper.insert(copyZxEqEquip);
    		
    		//????????????????????????
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
    	// ??????
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
	    			//???????????? 
	    			dbzxEqPurRecord.setIsimported("-1");//-1:?????????
	    			//??????
	    			dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
	    			flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
	    		}else {
	    			return repEntity.layerMessage("no", "???????????????????????????????????????");
	    		}
	    	}
	    	// ??????
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
    		//????????????
    		dbzxEqPurRecord.setIsimported("3");//3:??????????????????
    		// ??????
    		dbzxEqPurRecord.setModifyUserInfo(userKey, realName);
    		flag = zxEqPurRecordMapper.updateByPrimaryKey(dbzxEqPurRecord);
    		//?????????????????????????????????????????????--?????????????????? ?????????????????????
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
    	// ??????
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
		// ????????????
		ZxEqPurRecord dbZxEqPurRecord = zxEqPurRecordMapper.selectByPrimaryKey(zxEqPurRecord.getId());
		// ????????????
		return dbZxEqPurRecord;
	}
	
}