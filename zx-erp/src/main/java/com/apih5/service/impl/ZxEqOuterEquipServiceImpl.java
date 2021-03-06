package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqOuterEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqOuterEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqOuterEquipService;
import com.apih5.utils.QRCodeUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqOuterEquipService")
public class ZxEqOuterEquipServiceImpl implements ZxEqOuterEquipService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqOuterEquipMapper zxEqOuterEquipMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @ApolloConfig
	private Config config;
    
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;

    @Override
    public ResponseEntity getZxEqOuterEquipListByCondition(ZxEqOuterEquip zxEqOuterEquip) {
        if (zxEqOuterEquip == null) {
            zxEqOuterEquip = new ZxEqOuterEquip();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqOuterEquip.setComID("");
        	zxEqOuterEquip.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqOuterEquip.setComID(zxEqOuterEquip.getOrgID());
        	zxEqOuterEquip.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqOuterEquip.setOrgID(zxEqOuterEquip.getOrgID());
        }
        
        // ????????????
        PageHelper.startPage(zxEqOuterEquip.getPage(),zxEqOuterEquip.getLimit());
        // ????????????
        List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.selectByZxEqOuterEquipList(zxEqOuterEquip);
        for (ZxEqOuterEquip zxEqOuterEquip2 : zxEqOuterEquipList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqOuterEquip2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqOuterEquip2.setFileList(fileList);
		}
        // ??????????????????
        PageInfo<ZxEqOuterEquip> p = new PageInfo<>(zxEqOuterEquipList);

        return repEntity.okList(zxEqOuterEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqOuterEquipDetails(ZxEqOuterEquip zxEqOuterEquip) {
        if (zxEqOuterEquip == null) {
            zxEqOuterEquip = new ZxEqOuterEquip();
        }
        // ????????????
        ZxEqOuterEquip dbZxEqOuterEquip = zxEqOuterEquipMapper.selectByPrimaryKey(zxEqOuterEquip.getId());
        // ????????????
        if (dbZxEqOuterEquip != null) {
            return repEntity.ok(dbZxEqOuterEquip);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqOuterEquip(ZxEqOuterEquip zxEqOuterEquip) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	zxEqOuterEquip.setId(UuidUtil.generate());
    	//???????????????
    	String qrcodeContent = config.getProperty("ng.web.url", "") + "regulatoryFrameworkMobile/#/regulatoryFrameworkMobile/RegulatoryFrameDetail/" + zxEqOuterEquip.getId();
    	zxEqOuterEquip.setQrcodeContent(qrcodeContent);
    	String qrcodeName = "";
    	try {
    		qrcodeName = QRCodeUtil.encode(qrcodeContent, HttpUtil.getUploadPath("zx-erp"));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	zxEqOuterEquip.setQrcodeName(qrcodeName);
    	zxEqOuterEquip.setQrcodeUrl(HttpUtil.getUploadPathRelative("zx-erp") + qrcodeName);
    	zxEqOuterEquip.setQrcodeDownUrl("downloadFile?filePath="+ zxEqOuterEquip.getQrcodeUrl() +"&downName=" + qrcodeName);
    	zxEqOuterEquip.setCreateUserInfo(userKey, realName);
    	int flag = zxEqOuterEquipMapper.insert(zxEqOuterEquip);
    	if(zxEqOuterEquip.getFileList() != null && zxEqOuterEquip.getFileList().size() >0) {
    		for (ZxErpFile file : zxEqOuterEquip.getFileList()) {
    			file.setUid(UuidUtil.generate());
    			file.setOtherId(zxEqOuterEquip.getId());
    			file.setCreateUserInfo(userKey, realName);
    			flag = zxErpFileMapper.insert(file);
    		}
    	}
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zxEqOuterEquip);
    	}
    }

    @Override
    public ResponseEntity updateZxEqOuterEquip(ZxEqOuterEquip zxEqOuterEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqOuterEquip dbzxEqOuterEquip = zxEqOuterEquipMapper.selectByPrimaryKey(zxEqOuterEquip.getId());
        if (dbzxEqOuterEquip != null && StrUtil.isNotEmpty(dbzxEqOuterEquip.getId())) {
           // ????????????
           dbzxEqOuterEquip.setOrgID(zxEqOuterEquip.getOrgID());
           // ????????????
           dbzxEqOuterEquip.setEquipName(zxEqOuterEquip.getEquipName());
           // ??????
           dbzxEqOuterEquip.setSpec(zxEqOuterEquip.getSpec());
           // ??????
           dbzxEqOuterEquip.setModel(zxEqOuterEquip.getModel());
           // ??????(KW)
           dbzxEqOuterEquip.setPower(zxEqOuterEquip.getPower());
           // ????????????
           dbzxEqOuterEquip.setOutfactory(zxEqOuterEquip.getOutfactory());
           // ????????????
           dbzxEqOuterEquip.setOutfactoryDate(zxEqOuterEquip.getOutfactoryDate());
           // ??????
           dbzxEqOuterEquip.setOrginalValue(zxEqOuterEquip.getOrginalValue());
           // ??????
           dbzxEqOuterEquip.setLeftValue(zxEqOuterEquip.getLeftValue());
           // ????????????
           dbzxEqOuterEquip.setPlace(zxEqOuterEquip.getPlace());
           // ?????????????????????
           dbzxEqOuterEquip.setLeaseLimit(zxEqOuterEquip.getLeaseLimit());
           // ??????????????????/??????
           dbzxEqOuterEquip.setLeaseprice(zxEqOuterEquip.getLeaseprice());
           // ????????????
           dbzxEqOuterEquip.setSupplierID(zxEqOuterEquip.getSupplierID());
           // ????????????
           dbzxEqOuterEquip.setSupplierMaster(zxEqOuterEquip.getSupplierMaster());
           // ????????????
           dbzxEqOuterEquip.setOperator(zxEqOuterEquip.getOperator());
           // ????????????
           dbzxEqOuterEquip.setInDate(zxEqOuterEquip.getInDate());
           // ????????????
           dbzxEqOuterEquip.setOutDate(zxEqOuterEquip.getOutDate());
           // ??????
           dbzxEqOuterEquip.setRemark(zxEqOuterEquip.getRemark());
           // ????????????
           dbzxEqOuterEquip.setState(zxEqOuterEquip.getState());
           // ??????id
           dbzxEqOuterEquip.setContrID(zxEqOuterEquip.getContrID());
           // ????????????id
           dbzxEqOuterEquip.setContrItem(zxEqOuterEquip.getContrItem());
           // ??????
           dbzxEqOuterEquip.setEquipNo(zxEqOuterEquip.getEquipNo());
           // 
           dbzxEqOuterEquip.setContrItemID(zxEqOuterEquip.getContrItemID());
           // ?????????
           dbzxEqOuterEquip.setPassNo(zxEqOuterEquip.getPassNo());
           // ???????????????
           dbzxEqOuterEquip.setStartEndDate(zxEqOuterEquip.getStartEndDate());
           // comid
           dbzxEqOuterEquip.setComID(zxEqOuterEquip.getComID());
           // ????????????
           dbzxEqOuterEquip.setComName(zxEqOuterEquip.getComName());
           // ??????????????????
           dbzxEqOuterEquip.setAcceptance(zxEqOuterEquip.getAcceptance());
           // ??????????????????
           dbzxEqOuterEquip.setIsSepcial(zxEqOuterEquip.getIsSepcial());
           // ????????????????????????
           dbzxEqOuterEquip.setInspReport(zxEqOuterEquip.getInspReport());
           // ???????????????????????????
           dbzxEqOuterEquip.setInspCert(zxEqOuterEquip.getInspCert());
           // ???????????????
           dbzxEqOuterEquip.setOpCert(zxEqOuterEquip.getOpCert());
           // ????????????
           dbzxEqOuterEquip.setIsOut(zxEqOuterEquip.getIsOut());
           // ??????
           dbzxEqOuterEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqOuterEquipMapper.updateByPrimaryKey(dbzxEqOuterEquip);
           //??????????????????
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqOuterEquip.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqOuterEquip.getFileList() != null && zxEqOuterEquip.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqOuterEquip.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqOuterEquip.getId());
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
            return repEntity.ok("sys.data.update",zxEqOuterEquip);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqOuterEquip(List<ZxEqOuterEquip> zxEqOuterEquipList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqOuterEquipList != null && zxEqOuterEquipList.size() > 0) {
        	for (ZxEqOuterEquip zxEqOuterEquip : zxEqOuterEquipList) {
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqOuterEquip.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqOuterEquip zxEqOuterEquip = new ZxEqOuterEquip();
           zxEqOuterEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqOuterEquipMapper.batchDeleteUpdateZxEqOuterEquip(zxEqOuterEquipList, zxEqOuterEquip);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqOuterEquipList);
        }
    }
    
    @Override
	public ResponseEntity getZxEqOuterEquipListForRecord(ZxEqOuterEquip zxEqOuterEquip) {
    	if (zxEqOuterEquip == null) {
    		zxEqOuterEquip = new ZxEqOuterEquip();
    	}
    	String ext1 = "";
    	SysDepartment sysDepartment = sysDepartmentService.getSysDeptProByPrimaryKey(zxEqOuterEquip.getOrgID());
    	if(sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
    		if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "1")) {
    			ext1 = "1";
    		}else if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "2")) {
    			ext1 = "2";
    		}
    	}
    	SysDepartment sysProject =  sysDepartmentService.getSysProjectPrimaryKeyByMapper(zxEqOuterEquip.getOrgID());
    	if(sysProject != null && StrUtil.isNotEmpty(sysProject.getDepartmentId())) {
    		if(StrUtil.equals(sysProject.getDepartmentFlag(), "3")) {
    			ext1 = "3";
    		}else if(StrUtil.equals(sysProject.getDepartmentFlag(), "4")) {
    			ext1 = "4";
    		}
    	}
    	// ??????????????????
    	if(StrUtil.equals("1", ext1)) {
    		zxEqOuterEquip.setComID("");
    		zxEqOuterEquip.setUseOrgID("");
    	} else if(StrUtil.equals("2", ext1)) {
    		// ????????????????????????
    		zxEqOuterEquip.setComID(zxEqOuterEquip.getOrgID());
    		zxEqOuterEquip.setOrgID("");
    	} else if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
    		// ???????????????????????????
    		zxEqOuterEquip.setOrgID(zxEqOuterEquip.getOrgID());//????????????
    	}


    	// ????????????
    	PageHelper.startPage(zxEqOuterEquip.getPage(),zxEqOuterEquip.getLimit());
    	// ????????????
    	List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.selectByZxEqOuterEquipList(zxEqOuterEquip);
    	for (ZxEqOuterEquip zxEqOuterEquip2 : zxEqOuterEquipList) {
    		ZxErpFile file = new ZxErpFile();
    		file.setOtherId(zxEqOuterEquip2.getId());
    		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxEqOuterEquip2.setFileList(fileList);
    	}
    	// ??????????????????
    	PageInfo<ZxEqOuterEquip> p = new PageInfo<>(zxEqOuterEquipList);

    	return repEntity.okList(zxEqOuterEquipList, p.getTotal());
    }
    
    @Override
    public ResponseEntity getZxEqOuterEquipListForCar(ZxEqOuterEquip zxEqOuterEquip) {
        // ????????????
        PageHelper.startPage(zxEqOuterEquip.getPage(),zxEqOuterEquip.getLimit());
        // ????????????
        List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.getZxEqOuterEquipListForCar(zxEqOuterEquip);
        // ??????????????????
        PageInfo<ZxEqOuterEquip> p = new PageInfo<>(zxEqOuterEquipList);

        return repEntity.okList(zxEqOuterEquipList, p.getTotal());
    }

    @Override
    public List<ZxEqOuterEquip> reportZuLinCLForm(ZxEqOuterEquip zxEqOuterEquip) {
        if (zxEqOuterEquip == null) {
            zxEqOuterEquip = new ZxEqOuterEquip();
        }
        // ????????????
        List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.getZxEqOuterEquipListForCar(zxEqOuterEquip);
        return zxEqOuterEquipList;
    }

}