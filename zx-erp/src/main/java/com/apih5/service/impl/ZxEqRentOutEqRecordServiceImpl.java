package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqRentOutEqRecordItemMapper;
import com.apih5.mybatis.dao.ZxEqRentOutEqRecordMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecord;
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecordItem;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqRentOutEqRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zxEqRentOutEqRecordService")
public class ZxEqRentOutEqRecordServiceImpl implements ZxEqRentOutEqRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqRentOutEqRecordMapper zxEqRentOutEqRecordMapper;
    
    @Autowired(required = true)
    private ZxEqRentOutEqRecordItemMapper zxEqRentOutEqRecordItemMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxEqRentOutEqRecordListByCondition(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        if (zxEqRentOutEqRecord == null) {
            zxEqRentOutEqRecord = new ZxEqRentOutEqRecord();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqRentOutEqRecord.setCompanyId("");
        	zxEqRentOutEqRecord.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqRentOutEqRecord.setCompanyId(zxEqRentOutEqRecord.getOrgID());
        	zxEqRentOutEqRecord.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqRentOutEqRecord.setOrgID(zxEqRentOutEqRecord.getOrgID());
        }
        
        
        // ????????????
        PageHelper.startPage(zxEqRentOutEqRecord.getPage(),zxEqRentOutEqRecord.getLimit());
        // ????????????
        List<ZxEqRentOutEqRecord> zxEqRentOutEqRecordList = zxEqRentOutEqRecordMapper.selectByZxEqRentOutEqRecordList(zxEqRentOutEqRecord);
        for (ZxEqRentOutEqRecord zxEqRentOutEqRecord2 : zxEqRentOutEqRecordList) {
        	ZxEqRentOutEqRecordItem item = new ZxEqRentOutEqRecordItem();
        	item.setBillID(zxEqRentOutEqRecord2.getId());
        	List<ZxEqRentOutEqRecordItem> itemList = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(item);
        	zxEqRentOutEqRecord2.setItemList(itemList);
        	
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqRentOutEqRecord2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqRentOutEqRecord2.setFileList(fileList);
        }
        // ??????????????????
        PageInfo<ZxEqRentOutEqRecord> p = new PageInfo<>(zxEqRentOutEqRecordList);

        return repEntity.okList(zxEqRentOutEqRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqRentOutEqRecordDetails(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        if (zxEqRentOutEqRecord == null) {
            zxEqRentOutEqRecord = new ZxEqRentOutEqRecord();
        }
        // ????????????
        ZxEqRentOutEqRecord dbZxEqRentOutEqRecord = zxEqRentOutEqRecordMapper.selectByPrimaryKey(zxEqRentOutEqRecord.getId());
        // ????????????
        if (dbZxEqRentOutEqRecord != null) {
            return repEntity.ok(dbZxEqRentOutEqRecord);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqRentOutEqRecord.setId(UuidUtil.generate());
        zxEqRentOutEqRecord.setCreateUserInfo(userKey, realName);
        int flag = zxEqRentOutEqRecordMapper.insert(zxEqRentOutEqRecord);
        if(zxEqRentOutEqRecord.getItemList() != null && zxEqRentOutEqRecord.getItemList().size() >0) {	
        	for (ZxEqRentOutEqRecordItem lib : zxEqRentOutEqRecord.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setBillID(zxEqRentOutEqRecord.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqRentOutEqRecordItemMapper.insert(lib);
			}
        }
        if(zxEqRentOutEqRecord.getFileList() != null && zxEqRentOutEqRecord.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqRentOutEqRecord.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqRentOutEqRecord.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqRentOutEqRecord);
        }
    }

    @Override
    public ResponseEntity updateZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqRentOutEqRecord dbzxEqRentOutEqRecord = zxEqRentOutEqRecordMapper.selectByPrimaryKey(zxEqRentOutEqRecord.getId());
        if (dbzxEqRentOutEqRecord != null && StrUtil.isNotEmpty(dbzxEqRentOutEqRecord.getId())) {
           // ????????????
           dbzxEqRentOutEqRecord.setBillNo(zxEqRentOutEqRecord.getBillNo());
           // ????????????id
           dbzxEqRentOutEqRecord.setOrgID(zxEqRentOutEqRecord.getOrgID());
           // ????????????name
           dbzxEqRentOutEqRecord.setOrgName(zxEqRentOutEqRecord.getOrgName());
           // ????????????id
           dbzxEqRentOutEqRecord.setRentOrgID(zxEqRentOutEqRecord.getRentOrgID());
           // ????????????name
           dbzxEqRentOutEqRecord.setRentOrgName(zxEqRentOutEqRecord.getRentOrgName());
           // ????????????
           dbzxEqRentOutEqRecord.setEqName(zxEqRentOutEqRecord.getEqName());
           // ????????????
           dbzxEqRentOutEqRecord.setSpec(zxEqRentOutEqRecord.getSpec());
           // ????????????
           dbzxEqRentOutEqRecord.setBusDate(zxEqRentOutEqRecord.getBusDate());
           // ??????
           dbzxEqRentOutEqRecord.setRemark(zxEqRentOutEqRecord.getRemark());
           // 
           dbzxEqRentOutEqRecord.setOuterEquipID(zxEqRentOutEqRecord.getOuterEquipID());
           // ??????
           dbzxEqRentOutEqRecord.setStatus(zxEqRentOutEqRecord.getStatus());
           // ??????
           dbzxEqRentOutEqRecord.setModifyUserInfo(userKey, realName);
           flag = zxEqRentOutEqRecordMapper.updateByPrimaryKey(dbzxEqRentOutEqRecord);
         //??????????????????
           ZxEqRentOutEqRecordItem delLib = new ZxEqRentOutEqRecordItem();
           delLib.setBillID(zxEqRentOutEqRecord.getId());
           List<ZxEqRentOutEqRecordItem> delLibList = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqRentOutEqRecordItemMapper.batchDeleteUpdateZxEqRentOutEqRecordItem(delLibList, delLib);
           }
           if(zxEqRentOutEqRecord.getItemList() != null && zxEqRentOutEqRecord.getItemList().size() >0) {	
        	   for (ZxEqRentOutEqRecordItem lib : zxEqRentOutEqRecord.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setBillID(zxEqRentOutEqRecord.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqRentOutEqRecordItemMapper.insert(lib);
        	   }
           }
           //??????????????????
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqRentOutEqRecord.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqRentOutEqRecord.getFileList() != null && zxEqRentOutEqRecord.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqRentOutEqRecord.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqRentOutEqRecord.getId());
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
            return repEntity.ok("sys.data.update",zxEqRentOutEqRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqRentOutEqRecord(List<ZxEqRentOutEqRecord> zxEqRentOutEqRecordList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqRentOutEqRecordList != null && zxEqRentOutEqRecordList.size() > 0) {
        	for (ZxEqRentOutEqRecord zxEqRentOutEqRecord : zxEqRentOutEqRecordList) {
    			ZxEqRentOutEqRecordItem delLib = new ZxEqRentOutEqRecordItem();
    			delLib.setBillID(zxEqRentOutEqRecord.getId());
    			List<ZxEqRentOutEqRecordItem> delLibList = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqRentOutEqRecordItemMapper.batchDeleteUpdateZxEqRentOutEqRecordItem(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqRentOutEqRecord.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqRentOutEqRecord zxEqRentOutEqRecord = new ZxEqRentOutEqRecord();
           zxEqRentOutEqRecord.setModifyUserInfo(userKey, realName);
           flag = zxEqRentOutEqRecordMapper.batchDeleteUpdateZxEqRentOutEqRecord(zxEqRentOutEqRecordList, zxEqRentOutEqRecord);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqRentOutEqRecordList);
        }
    }

    @Override
    public ResponseEntity auditZxEqRentOutEqRecord(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqRentOutEqRecord dbzxEqRentOutEqRecord = zxEqRentOutEqRecordMapper.selectByPrimaryKey(zxEqRentOutEqRecord.getId());
    	if (dbzxEqRentOutEqRecord != null && StrUtil.isNotEmpty(dbzxEqRentOutEqRecord.getId())) {
    		// ??????
    		dbzxEqRentOutEqRecord.setStatus(zxEqRentOutEqRecord.getStatus());
    		// ??????
    		dbzxEqRentOutEqRecord.setModifyUserInfo(userKey, realName);
    		flag = zxEqRentOutEqRecordMapper.updateByPrimaryKey(dbzxEqRentOutEqRecord);
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqRentOutEqRecord);
    	}
    }

	@Override
	public ResponseEntity importZxEqRentOutEqRecordList(ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		//????????????,????????????,????????????,????????????,????????????,????????????,??????
		zxEqRentOutEqRecord.setId(UuidUtil.generate());
		zxEqRentOutEqRecord.setBillNo(zxEqRentOutEqRecord.getBillNo());// ????????????
		zxEqRentOutEqRecord.setOrgID(zxEqRentOutEqRecord.getOrgID());// ????????????id
		zxEqRentOutEqRecord.setOrgName(zxEqRentOutEqRecord.getOrgName());// ????????????name
		zxEqRentOutEqRecord.setRentOrgID(zxEqRentOutEqRecord.getRentOrgID());// ????????????id
		zxEqRentOutEqRecord.setRentOrgName(zxEqRentOutEqRecord.getRentOrgName());// ????????????name
		zxEqRentOutEqRecord.setEqName(zxEqRentOutEqRecord.getEqName());// ????????????
		zxEqRentOutEqRecord.setSpec(zxEqRentOutEqRecord.getSpec());// ????????????
		zxEqRentOutEqRecord.setBusDate(zxEqRentOutEqRecord.getBusDate());// ????????????
		zxEqRentOutEqRecord.setRemark(zxEqRentOutEqRecord.getRemark());// ??????
		zxEqRentOutEqRecord.setCreateUserInfo(userKey, realName);
		flag = zxEqRentOutEqRecordMapper.insert(zxEqRentOutEqRecord);
		//???????????????
		String filePath = Apih5Properties.getFilePath() + zxEqRentOutEqRecord.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
			//???????????????????????????????????????
			if(readAll != null && readAll.size() >0) {
				ZxEqRentOutEqRecordItem record = new ZxEqRentOutEqRecordItem();
				record.setBillID(zxEqRentOutEqRecord.getId());
				List<ZxEqRentOutEqRecordItem> debtExcels = zxEqRentOutEqRecordItemMapper.selectByZxEqRentOutEqRecordItemList(record);
				if(debtExcels != null && debtExcels.size() >0) {
					record.setModifyUserInfo(userKey, realName);
					zxEqRentOutEqRecordItemMapper.batchDeleteUpdateZxEqRentOutEqRecordItem(debtExcels, record);
				}
			}
			for(Map<String,Object> map:readAll) {
	    		JSONObject json = new JSONObject(map);
	    		//add
	    		ZxEqRentOutEqRecordItem base = new ZxEqRentOutEqRecordItem();
	    		base.setId(UuidUtil.generate());
	    		base.setBillID(zxEqRentOutEqRecord.getId());
	    		if(StrUtil.isNotEmpty(json.getStr("??????"))){
	    			base.setUseDate(DateUtil.parse(json.getStr("??????")));
				}else{
					base.setUseDate(null);
				}
	    		base.setContent(json.getStr("????????????"));
	    		base.setPlace(json.getStr("????????????"));
	    		base.setUnit(json.getStr("?????????????????????"));
	    		if(StrUtil.isNotEmpty(json.getStr("?????????????????????"))) {
	    			base.setQty(new BigDecimal(json.getStr("?????????????????????")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("?????????"))) {
	    			base.setMile(new BigDecimal(json.getStr("?????????")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("????????????"))) {
	    			base.setActualQty(new BigDecimal(json.getStr("????????????")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("??????????????????"))) {
	    			base.setWaitQty(new BigDecimal(json.getStr("??????????????????")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("??????????????????"))) {
	    			base.setWeatherQty(new BigDecimal(json.getStr("??????????????????")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("??????????????????"))) {
	    			base.setProblemQty(new BigDecimal(json.getStr("??????????????????")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("????????????(???)"))) {
	    			base.setGasQty(new BigDecimal(json.getStr("????????????(???)")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("????????????(???)"))) {
	    			base.setDervQty(new BigDecimal(json.getStr("????????????(???)")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("?????????(??????)"))) {
	    			base.setCoalQty(new BigDecimal(json.getStr("?????????(??????)")));
	    		}
	    		base.setDriverName(json.getStr("????????????"));
	    		base.setCreateUserInfo(userKey, realName);
	    		flag = zxEqRentOutEqRecordItemMapper.insert(base);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();	
		}
		if (flag == 0) {
			return repEntity.errorSave();
		}
		return repEntity.ok("ok");
	}
}
