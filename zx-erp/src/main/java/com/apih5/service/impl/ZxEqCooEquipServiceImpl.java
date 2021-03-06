package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxEqCooEquipItemMapper;
import com.apih5.mybatis.dao.ZxEqCooEquipMapper;
import com.apih5.mybatis.pojo.ZxEqCooEquip;
import com.apih5.mybatis.pojo.ZxEqCooEquipItem;
import com.apih5.service.ZxEqCooEquipService;
import com.apih5.utils.QRCodeUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zxEqCooEquipService")
public class ZxEqCooEquipServiceImpl implements ZxEqCooEquipService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqCooEquipMapper zxEqCooEquipMapper;
    
    @Autowired(required = true)
    private ZxEqCooEquipItemMapper zxEqCooEquipItemMapper;
    
    @ApolloConfig	
  	private Config config;

    @Override
    public ResponseEntity getZxEqCooEquipListByCondition(ZxEqCooEquip zxEqCooEquip) {
        if (zxEqCooEquip == null) {
            zxEqCooEquip = new ZxEqCooEquip();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqCooEquip.setComID("");
        	zxEqCooEquip.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqCooEquip.setComID(zxEqCooEquip.getOrgID());
        	zxEqCooEquip.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqCooEquip.setOrgID(zxEqCooEquip.getOrgID());
        }
        
        // ????????????
        PageHelper.startPage(zxEqCooEquip.getPage(),zxEqCooEquip.getLimit());
        // ????????????
        List<ZxEqCooEquip> zxEqCooEquipList = zxEqCooEquipMapper.selectByZxEqCooEquipList(zxEqCooEquip);
        for (ZxEqCooEquip zxEqCooEquip2 : zxEqCooEquipList) {
        	ZxEqCooEquipItem item = new ZxEqCooEquipItem();
        	item.setMasID(zxEqCooEquip2.getId());
        	List<ZxEqCooEquipItem> itemList = zxEqCooEquipItemMapper.selectByZxEqCooEquipItemList(item);
        	zxEqCooEquip2.setItemList(itemList);
        }
        // ??????????????????
        PageInfo<ZxEqCooEquip> p = new PageInfo<>(zxEqCooEquipList);

        return repEntity.okList(zxEqCooEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqCooEquipDetails(ZxEqCooEquip zxEqCooEquip) {
        if (zxEqCooEquip == null) {
            zxEqCooEquip = new ZxEqCooEquip();
        }
        // ????????????
        ZxEqCooEquip dbZxEqCooEquip = zxEqCooEquipMapper.selectByPrimaryKey(zxEqCooEquip.getId());
        // ????????????
        if (dbZxEqCooEquip != null) {
            return repEntity.ok(dbZxEqCooEquip);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqCooEquip(ZxEqCooEquip zxEqCooEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqCooEquip.setId(UuidUtil.generate());
      //???????????????
    	String qrcodeContent = config.getProperty("ng.web.url", "") + "regulatoryFrameworkMobile/#/regulatoryFrameworkMobile/RegulatoryFrameDetail/" + zxEqCooEquip.getId();
        zxEqCooEquip.setQrcodeContent(qrcodeContent);
    	String qrcodeName = "";
    	try {
    		qrcodeName = QRCodeUtil.encode(qrcodeContent, HttpUtil.getUploadPath("zx-erp"));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	zxEqCooEquip.setQrcodeName(qrcodeName);
    	zxEqCooEquip.setQrcodeUrl(HttpUtil.getUploadPathRelative("zx-erp") + qrcodeName);
    	zxEqCooEquip.setQrcodeDownUrl("downloadFile?filePath="+ zxEqCooEquip.getQrcodeUrl() +"&downName=" + qrcodeName);
    	
        zxEqCooEquip.setCreateUserInfo(userKey, realName);
        int flag = zxEqCooEquipMapper.insert(zxEqCooEquip);
        if(zxEqCooEquip.getItemList() != null && zxEqCooEquip.getItemList().size() >0) {	
        	for (ZxEqCooEquipItem lib : zxEqCooEquip.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setMasID(zxEqCooEquip.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqCooEquipItemMapper.insert(lib);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqCooEquip);
        }
    }

    @Override
    public ResponseEntity updateZxEqCooEquip(ZxEqCooEquip zxEqCooEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqCooEquip dbzxEqCooEquip = zxEqCooEquipMapper.selectByPrimaryKey(zxEqCooEquip.getId());
        if (dbzxEqCooEquip != null && StrUtil.isNotEmpty(dbzxEqCooEquip.getId())) {
           // ????????????id
           dbzxEqCooEquip.setOrgID(zxEqCooEquip.getOrgID());
           // ????????????id
           dbzxEqCooEquip.setSubUnitID(zxEqCooEquip.getSubUnitID());
           // ?????????
           dbzxEqCooEquip.setEditMan(zxEqCooEquip.getEditMan());
           // ??????
           dbzxEqCooEquip.setRemark(zxEqCooEquip.getRemark());
           // ????????????
           dbzxEqCooEquip.setState(zxEqCooEquip.getState());
           // ??????id
           dbzxEqCooEquip.setContractID(zxEqCooEquip.getContractID());
           //????????????
           dbzxEqCooEquip.setContractName(zxEqCooEquip.getContractName());
           // comid
           dbzxEqCooEquip.setComID(zxEqCooEquip.getComID());
           // ????????????
           dbzxEqCooEquip.setComName(zxEqCooEquip.getComName());
           // ??????
           dbzxEqCooEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqCooEquipMapper.updateByPrimaryKey(dbzxEqCooEquip);
         //??????????????????
           ZxEqCooEquipItem delLib = new ZxEqCooEquipItem();
           delLib.setMasID(zxEqCooEquip.getId());
           List<ZxEqCooEquipItem> delLibList = zxEqCooEquipItemMapper.selectByZxEqCooEquipItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqCooEquipItemMapper.batchDeleteUpdateZxEqCooEquipItem(delLibList, delLib);
           }
           if(zxEqCooEquip.getItemList() != null && zxEqCooEquip.getItemList().size() >0) {	
        	   for (ZxEqCooEquipItem lib : zxEqCooEquip.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMasID(zxEqCooEquip.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqCooEquipItemMapper.insert(lib);
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqCooEquip);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqCooEquip(List<ZxEqCooEquip> zxEqCooEquipList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqCooEquipList != null && zxEqCooEquipList.size() > 0) {
        	for (ZxEqCooEquip zxEqCooEquip : zxEqCooEquipList) {
    			ZxEqCooEquipItem delLib = new ZxEqCooEquipItem();
    			delLib.setMasID(zxEqCooEquip.getId());
    			List<ZxEqCooEquipItem> delLibList = zxEqCooEquipItemMapper.selectByZxEqCooEquipItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqCooEquipItemMapper.batchDeleteUpdateZxEqCooEquipItem(delLibList, delLib);
    			}
    		}
        	ZxEqCooEquip zxEqCooEquip = new ZxEqCooEquip();
           zxEqCooEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqCooEquipMapper.batchDeleteUpdateZxEqCooEquip(zxEqCooEquipList, zxEqCooEquip);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqCooEquipList);
        }
    }

	@Override
	public ResponseEntity importZxEqCooEquipList(ZxEqCooEquip zxEqCooEquip) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		//????????????,????????????,????????????,????????????
		zxEqCooEquip.setId(UuidUtil.generate());
		zxEqCooEquip.setOrgID(zxEqCooEquip.getOrgID());// ????????????id
		zxEqCooEquip.setContractNo(zxEqCooEquip.getContractNo());//????????????
		zxEqCooEquip.setContractName(zxEqCooEquip.getContractName());//????????????
		zxEqCooEquip.setSubUnitID(zxEqCooEquip.getSubUnitID());// ????????????id
		zxEqCooEquip.setCreateUserInfo(userKey, realName);
		flag = zxEqCooEquipMapper.insert(zxEqCooEquip);
		//???????????????
		String filePath = Apih5Properties.getFilePath() + zxEqCooEquip.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
			//???????????????????????????????????????
			if(readAll != null && readAll.size() >0) {
				ZxEqCooEquipItem record = new ZxEqCooEquipItem();
				record.setMasID(zxEqCooEquip.getId());
				List<ZxEqCooEquipItem> debtExcels = zxEqCooEquipItemMapper.selectByZxEqCooEquipItemList(record);
				if(debtExcels != null && debtExcels.size() >0) {
					record.setModifyUserInfo(userKey, realName);
					zxEqCooEquipItemMapper.batchDeleteUpdateZxEqCooEquipItem(debtExcels, record);
				}
			}
			for(Map<String,Object> map:readAll) {
	    		JSONObject json = new JSONObject(map);
	    		//add
	    		ZxEqCooEquipItem base = new ZxEqCooEquipItem();
	    		base.setId(UuidUtil.generate());
	    		base.setMasID(zxEqCooEquip.getId());
	    		base.setEquipName(json.getStr("????????????"));
	    		base.setSpec(json.getStr("??????"));
	    		base.setModel(json.getStr("??????"));
	    		base.setPower(json.getStr("??????(kw)"));
	    		base.setOutfactory(json.getStr("????????????"));
	    		if(StrUtil.isNotEmpty(json.getStr("????????????"))){
	    			base.setOutfactoryDate(DateUtil.parse(json.getStr("????????????")));
				}else{
					base.setOutfactoryDate(null);
				}
	    		if(StrUtil.isNotEmpty(json.getStr("??????"))) {
	    			base.setOrginalValue(new BigDecimal(json.getStr("??????")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("??????"))) {
	    			base.setLeftValue(new BigDecimal(json.getStr("??????")));
	    		}
	    		base.setOldrate(json.getStr("????????????"));
	    		if(StrUtil.isNotEmpty(json.getStr("??????????????????"))){
	    			base.setBeginDate(DateUtil.parse(json.getStr("??????????????????")));
				}else{
					base.setBeginDate(null);
				}
	    		if(StrUtil.isNotEmpty(json.getStr("??????????????????"))){
	    			base.setBeginDate(DateUtil.parse(json.getStr("??????????????????")));
				}else{
					base.setEndDate(null);
				}
	    		base.setRemark(json.getStr("??????"));
	    		base.setCreateUserInfo(userKey, realName);
	    		flag = zxEqCooEquipItemMapper.insert(base);
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
