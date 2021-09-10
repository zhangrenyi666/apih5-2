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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqCooEquip.setComID("");
        	zxEqCooEquip.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqCooEquip.setComID(zxEqCooEquip.getOrgID());
        	zxEqCooEquip.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqCooEquip.setOrgID(zxEqCooEquip.getOrgID());
        }
        
        // 分页查询
        PageHelper.startPage(zxEqCooEquip.getPage(),zxEqCooEquip.getLimit());
        // 获取数据
        List<ZxEqCooEquip> zxEqCooEquipList = zxEqCooEquipMapper.selectByZxEqCooEquipList(zxEqCooEquip);
        for (ZxEqCooEquip zxEqCooEquip2 : zxEqCooEquipList) {
        	ZxEqCooEquipItem item = new ZxEqCooEquipItem();
        	item.setMasID(zxEqCooEquip2.getId());
        	List<ZxEqCooEquipItem> itemList = zxEqCooEquipItemMapper.selectByZxEqCooEquipItemList(item);
        	zxEqCooEquip2.setItemList(itemList);
        }
        // 得到分页信息
        PageInfo<ZxEqCooEquip> p = new PageInfo<>(zxEqCooEquipList);

        return repEntity.okList(zxEqCooEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqCooEquipDetails(ZxEqCooEquip zxEqCooEquip) {
        if (zxEqCooEquip == null) {
            zxEqCooEquip = new ZxEqCooEquip();
        }
        // 获取数据
        ZxEqCooEquip dbZxEqCooEquip = zxEqCooEquipMapper.selectByPrimaryKey(zxEqCooEquip.getId());
        // 数据存在
        if (dbZxEqCooEquip != null) {
            return repEntity.ok(dbZxEqCooEquip);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqCooEquip(ZxEqCooEquip zxEqCooEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqCooEquip.setId(UuidUtil.generate());
      //生成二维码
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
           // 编制单位id
           dbzxEqCooEquip.setOrgID(zxEqCooEquip.getOrgID());
           // 劳务队伍id
           dbzxEqCooEquip.setSubUnitID(zxEqCooEquip.getSubUnitID());
           // 编制人
           dbzxEqCooEquip.setEditMan(zxEqCooEquip.getEditMan());
           // 备注
           dbzxEqCooEquip.setRemark(zxEqCooEquip.getRemark());
           // 审核状态
           dbzxEqCooEquip.setState(zxEqCooEquip.getState());
           // 合同id
           dbzxEqCooEquip.setContractID(zxEqCooEquip.getContractID());
           //合同名称
           dbzxEqCooEquip.setContractName(zxEqCooEquip.getContractName());
           // comid
           dbzxEqCooEquip.setComID(zxEqCooEquip.getComID());
           // 公司名称
           dbzxEqCooEquip.setComName(zxEqCooEquip.getComName());
           // 共通
           dbzxEqCooEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqCooEquipMapper.updateByPrimaryKey(dbzxEqCooEquip);
         //先删除再新增
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
        // 失败
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
        // 失败
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
		//编制单位,合同编号,合同名称,劳务队伍
		zxEqCooEquip.setId(UuidUtil.generate());
		zxEqCooEquip.setOrgID(zxEqCooEquip.getOrgID());// 编制单位id
		zxEqCooEquip.setContractNo(zxEqCooEquip.getContractNo());//合同编号
		zxEqCooEquip.setContractName(zxEqCooEquip.getContractName());//合同名称
		zxEqCooEquip.setSubUnitID(zxEqCooEquip.getSubUnitID());// 劳务队伍id
		zxEqCooEquip.setCreateUserInfo(userKey, realName);
		flag = zxEqCooEquipMapper.insert(zxEqCooEquip);
		//新增子明细
		String filePath = Apih5Properties.getFilePath() + zxEqCooEquip.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
			//重复导入删除上次的导入记录
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
	    		base.setEquipName(json.getStr("设备名称"));
	    		base.setSpec(json.getStr("型号"));
	    		base.setModel(json.getStr("规格"));
	    		base.setPower(json.getStr("功率(kw)"));
	    		base.setOutfactory(json.getStr("生产厂家"));
	    		if(StrUtil.isNotEmpty(json.getStr("出产日期"))){
	    			base.setOutfactoryDate(DateUtil.parse(json.getStr("出产日期")));
				}else{
					base.setOutfactoryDate(null);
				}
	    		if(StrUtil.isNotEmpty(json.getStr("原值"))) {
	    			base.setOrginalValue(new BigDecimal(json.getStr("原值")));
	    		}
	    		if(StrUtil.isNotEmpty(json.getStr("净值"))) {
	    			base.setLeftValue(new BigDecimal(json.getStr("净值")));
	    		}
	    		base.setOldrate(json.getStr("新旧程度"));
	    		if(StrUtil.isNotEmpty(json.getStr("使用开始时间"))){
	    			base.setBeginDate(DateUtil.parse(json.getStr("使用开始时间")));
				}else{
					base.setBeginDate(null);
				}
	    		if(StrUtil.isNotEmpty(json.getStr("使用结束时间"))){
	    			base.setBeginDate(DateUtil.parse(json.getStr("使用结束时间")));
				}else{
					base.setEndDate(null);
				}
	    		base.setRemark(json.getStr("备注"));
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
