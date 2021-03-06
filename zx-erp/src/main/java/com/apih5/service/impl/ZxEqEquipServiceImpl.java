package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxEqCooEquipItemMapper;
import com.apih5.mybatis.dao.ZxEqCooEquipMapper;
import com.apih5.mybatis.dao.ZxEqEquipMapper;
import com.apih5.mybatis.dao.ZxEqOuterEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqCooEquip;
import com.apih5.mybatis.pojo.ZxEqCooEquipItem;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqOuterEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqEquipService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipService")
public class ZxEqEquipServiceImpl implements ZxEqEquipService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqOuterEquipMapper zxEqOuterEquipMapper;

    @Autowired(required = true)
    private ZxEqCooEquipMapper zxEqCooEquipMapper;

    @Autowired(required = true)
    private ZxEqCooEquipItemMapper zxEqCooEquipItemMapper;
    
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;

    @Override
    public ResponseEntity getZxEqEquipListByCondition(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        
        if(StrUtil.equals(zxEqEquip.getUreportFlag(), "comCar")) {//?????????????????? 
        	
        }else if(StrUtil.equals(zxEqEquip.getUreportFlag(), "comCarForMech")) {//????????????????????????
        	
        }else {
        	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
            String userId = TokenUtils.getUserId(request);
            String ext1 = TokenUtils.getExt1(request);
            // ??????????????????
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                    || StrUtil.equals("admin", userId)) {
            	zxEqEquip.setCompanyId("");
            	zxEqEquip.setUseOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
                // ????????????????????????
            	zxEqEquip.setCompanyId(zxEqEquip.getUseOrgID());
            	zxEqEquip.setUseOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                    || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
                // ???????????????????????????
            	zxEqEquip.setUseOrgID(zxEqEquip.getUseOrgID());//????????????
            }	
        }
        
        // ????????????
        PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
        // ????????????
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
        for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEquip2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEquip2.setFileList(fileList);
		}
        // ??????????????????
        PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

        return repEntity.okList(zxEqEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipDetails(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        // ????????????
        ZxEqEquip dbZxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquip.getId());
        // ????????????
        if (dbZxEqEquip != null) {
            return repEntity.ok(dbZxEqEquip);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquip(ZxEqEquip zxEqEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEquip.setId(UuidUtil.generate());
        zxEqEquip.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipMapper.insert(zxEqEquip);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquip);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquip(ZxEqEquip zxEqEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquip dbzxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquip.getId());
        if (dbzxEqEquip != null && StrUtil.isNotEmpty(dbzxEqEquip.getId())) {
           // abc??????
           dbzxEqEquip.setAbcType(zxEqEquip.getAbcType());
           // ????????????
           dbzxEqEquip.setResCatalogID(zxEqEquip.getResCatalogID());
           // ????????????
           dbzxEqEquip.setIsWorkEquip(zxEqEquip.getIsWorkEquip());
           // ????????????
           dbzxEqEquip.setIsMadeinChina(zxEqEquip.getIsMadeinChina());
           // ????????????????????????
           dbzxEqEquip.setFinanceNo(zxEqEquip.getFinanceNo());
           // ????????????
           dbzxEqEquip.setUseOrgID(zxEqEquip.getUseOrgID());
           // ????????????
           dbzxEqEquip.setPurDate(zxEqEquip.getPurDate());
           // ????????????
           dbzxEqEquip.setUsePlace(zxEqEquip.getUsePlace());
           // ????????????
           dbzxEqEquip.setOwnerOrgId(zxEqEquip.getOwnerOrgId());
           // ????????????
           dbzxEqEquip.setManageOrgID(zxEqEquip.getManageOrgID());
           // ????????????
           dbzxEqEquip.setEquipName(zxEqEquip.getEquipName());
           // ??????
           dbzxEqEquip.setSpec(zxEqEquip.getSpec());
           // ??????
           dbzxEqEquip.setModel(zxEqEquip.getModel());
           // ????????????KW???
           dbzxEqEquip.setPower(zxEqEquip.getPower());
           // ??????
           dbzxEqEquip.setSource(zxEqEquip.getSource());
           // ???????????????
           dbzxEqEquip.setViceserial(zxEqEquip.getViceserial());
           // ????????????
           dbzxEqEquip.setDownfactory(zxEqEquip.getDownfactory());
           // ??????????????????
           dbzxEqEquip.setViceoutfactory(zxEqEquip.getViceoutfactory());
           // ???????????????KW???
           dbzxEqEquip.setVicepower(zxEqEquip.getVicepower());
           // ????????????
           dbzxEqEquip.setVicespec(zxEqEquip.getVicespec());
           // ????????????
           dbzxEqEquip.setViceFactory(zxEqEquip.getViceFactory());
           // ???????????????KW???
           dbzxEqEquip.setMainpower(zxEqEquip.getMainpower());
           // ??????????????????
           dbzxEqEquip.setMainoutfactory(zxEqEquip.getMainoutfactory());
           // ???????????????
           dbzxEqEquip.setMainserial(zxEqEquip.getMainserial());
           // ????????????
           dbzxEqEquip.setMainspec(zxEqEquip.getMainspec());
           // ????????????
           dbzxEqEquip.setMainFactory(zxEqEquip.getMainFactory());
           // ????????????
           dbzxEqEquip.setDownspec(zxEqEquip.getDownspec());
           // ???????????????
           dbzxEqEquip.setDownserial(zxEqEquip.getDownserial());
           // ??????????????????
           dbzxEqEquip.setDownoutfactory(zxEqEquip.getDownoutfactory());
           // ?????????????????????
           dbzxEqEquip.setHeightlong(zxEqEquip.getHeightlong());
           // ?????????t)
           dbzxEqEquip.setWeight(zxEqEquip.getWeight());
           // ??????
           dbzxEqEquip.setOrginalValue(zxEqEquip.getOrginalValue());
           // ??????????????????
           dbzxEqEquip.setDepreImportmonth(zxEqEquip.getDepreImportmonth());
           // ??????
           dbzxEqEquip.setLeftValue(zxEqEquip.getLeftValue());
           // ??????fob???
           dbzxEqEquip.setFobPrice(zxEqEquip.getFobPrice());
           // ????????????
           dbzxEqEquip.setFobAmount(zxEqEquip.getFobAmount());
           // ???????????????
           dbzxEqEquip.setDiscountAmount(zxEqEquip.getDiscountAmount());
           // ????????????
           dbzxEqEquip.setAcceptanceDate(zxEqEquip.getAcceptanceDate());
           // ????????????
           dbzxEqEquip.setPassNo(zxEqEquip.getPassNo());
           // ????????????
           dbzxEqEquip.setStatus(zxEqEquip.getStatus());
           // ????????????
           dbzxEqEquip.setOutFactoryDate(zxEqEquip.getOutFactoryDate());
           // ???????????????
           dbzxEqEquip.setOutFactorySerial(zxEqEquip.getOutFactorySerial());
           // ??????????????????
           dbzxEqEquip.setIsimportant(zxEqEquip.getIsimportant());
           // ??????????????????
           dbzxEqEquip.setChangeDate(zxEqEquip.getChangeDate());
           // ??????
           dbzxEqEquip.setRemark(zxEqEquip.getRemark());
           // ????????????
           dbzxEqEquip.setActualDepreAmt(zxEqEquip.getActualDepreAmt());
           // ??????????????????
           dbzxEqEquip.setEquipNo(zxEqEquip.getEquipNo());
           // ????????????
           dbzxEqEquip.setFactory(zxEqEquip.getFactory());
           // ????????????
           dbzxEqEquip.setCheckStatus(zxEqEquip.getCheckStatus());
           // ????????????
           dbzxEqEquip.setIsDelete(zxEqEquip.getIsDelete());
           // ???????????????KW???
           dbzxEqEquip.setMainPowerStr(zxEqEquip.getMainPowerStr());
           // ???????????????KW???
           dbzxEqEquip.setVicePowerStr(zxEqEquip.getVicePowerStr());
           // ????????????
           dbzxEqEquip.setRegdate(zxEqEquip.getRegdate());
           // ???????????????
           dbzxEqEquip.setPlanNo(zxEqEquip.getPlanNo());
           // 
           dbzxEqEquip.setPlancode(zxEqEquip.getPlancode());
           // 
           dbzxEqEquip.setAttachmentID(zxEqEquip.getAttachmentID());
           // ??????
           dbzxEqEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipMapper.updateByPrimaryKey(dbzxEqEquip);
           
           //??????????????????
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqEquip.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqEquip.getFileList() != null && zxEqEquip.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqEquip.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqEquip.getId());
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
            return repEntity.ok("sys.data.update",zxEqEquip);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquip(List<ZxEqEquip> zxEqEquipList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipList != null && zxEqEquipList.size() > 0) {
        	for (ZxEqEquip zxEqEquip : zxEqEquipList) {
        		ZxErpFile delFile = new ZxErpFile();
        		delFile.setOtherId(zxEqEquip.getId());
        		List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
        		if(delFileList != null && delFileList.size() >0) {
        			delFile.setModifyUserInfo(userKey, realName);
        			zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
        		}
        	}
        	ZxEqEquip zxEqEquip = new ZxEqEquip();
        	zxEqEquip.setModifyUserInfo(userKey, realName);
        	flag = zxEqEquipMapper.batchDeleteUpdateZxEqEquip(zxEqEquipList, zxEqEquip);
        }
        // ??????
        if (flag == 0) {
        	return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipList);
        }
    }
    
    @Override
	public ResponseEntity unusedZxEqEquip(ZxEqEquip zxEqEquip) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquip dbzxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquip.getId());
    	if (dbzxEqEquip != null && StrUtil.isNotEmpty(dbzxEqEquip.getId())) {
    		// ????????????
    		dbzxEqEquip.setIsXianzhi("1");
    		// ??????
    		dbzxEqEquip.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipMapper.updateByPrimaryKey(dbzxEqEquip);
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquip);
    	}
	}

    @Override
    public ResponseEntity recoverZxEqEquip(ZxEqEquip zxEqEquip) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquip dbzxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquip.getId());
    	if (dbzxEqEquip != null && StrUtil.isNotEmpty(dbzxEqEquip.getId())) {
    		// ????????????
    		dbzxEqEquip.setIsXianzhi("0");
    		// ??????
    		dbzxEqEquip.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipMapper.updateByPrimaryKey(dbzxEqEquip);
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquip);
    	}
    }

    @Override
    public ResponseEntity getZxEqEquipListForReport(ZxEqEquip zxEqEquip) {
    	if (zxEqEquip == null) {
    		zxEqEquip = new ZxEqEquip();
    	}
    	
//    	if(StrUtil.equals(zxEqEquip.getr, str2)) {
//    		
//    	}
    	// ????????????
    	PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
    	// ????????????
    	List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListForReport(zxEqEquip);
    	// ??????????????????
    	PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

    	return repEntity.okList(zxEqEquipList, p.getTotal());
    }
    
	@Override
	public List<ZxEqEquip> ureportZxEqEquipList(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
			zxEqEquip = new ZxEqEquip();
		}
		if(StrUtil.isNotEmpty(zxEqEquip.getOrgID())) {//????????????
			zxEqEquip.setOwnerOrgId(zxEqEquip.getOrgID());
		}
		if(StrUtil.isNotEmpty(zxEqEquip.getOutOrgID())) {//????????????
			zxEqEquip.setUseOrgID(zxEqEquip.getOutOrgID());
		}
		// ????????????
		List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListForReport(zxEqEquip);
		return zxEqEquipList;
	}
	
	@Override
	public List<ZxEqEquip> ureportZxEqEquipListIdle(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
			zxEqEquip = new ZxEqEquip();
		}
		if(StrUtil.isNotEmpty(zxEqEquip.getOrgID())) {//????????????
			zxEqEquip.setOwnerOrgId(zxEqEquip.getOrgID());
		}
		if(StrUtil.isNotEmpty(zxEqEquip.getOutOrgID())) {//????????????
			zxEqEquip.setUseOrgID(zxEqEquip.getOutOrgID());
		}
		
		// ????????????
		List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListForReportIdle(zxEqEquip);
		for(ZxEqEquip ZxEqEquip1 :zxEqEquipList) {
			if(StrUtil.isNotEmpty(ZxEqEquip1.getSource())) {
				if(ZxEqEquip1.getSource().equals("0")) {
					ZxEqEquip1.setSource("??????");
				}
				
				ZxEqEquip1.setAcceptanceDateStr(DateUtil.formatDateTime(ZxEqEquip1.getAcceptanceDate()));
				ZxEqEquip1.setOutFactoryDateStr(DateUtil.formatDateTime(ZxEqEquip1.getOutFactoryDate()));
				ZxEqEquip1.setDownoutfactoryStr(DateUtil.formatDateTime(ZxEqEquip1.getDownoutfactory()));
				ZxEqEquip1.setMainoutfactoryStr(DateUtil.formatDateTime(ZxEqEquip1.getMainoutfactory()));
				ZxEqEquip1.setViceoutfactoryStr(DateUtil.formatDateTime(ZxEqEquip1.getViceoutfactory()));
				if(ZxEqEquip1.getIsMadeinChina().equals("0")) {
					ZxEqEquip1.setIsMadeinChina("??????");
				}else if(ZxEqEquip1.getIsMadeinChina().equals("1")) {
					ZxEqEquip1.setIsMadeinChina("??????");
				}
			}
			
		}
		
		return zxEqEquipList;
	}

    @Override
    public List<ZxEqEquip> ureportZxEqEquipListVehicle(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        // ????????????
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListVehicleForReport(zxEqEquip);
        return zxEqEquipList;
    }

    @Override
    public List<ZxEqEquip> ureportZxEqEquipListMechanics(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        // ????????????
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListMechanicsForReport(zxEqEquip);
        return zxEqEquipList;
    }

	@Override
	public ResponseEntity getZxEqEquipListForRealFact(ZxEqEquip zxEqEquip) {
		   if (zxEqEquip == null) {
	            zxEqEquip = new ZxEqEquip();
	        }
		   if(StrUtil.equals(zxEqEquip.getSource(), "3")) {//??????????????????
			   List<ZxEqCooEquipItem> zxEqCooEquipItemList = new ArrayList<>();
			   ZxEqCooEquip zxEqCooEquip = new ZxEqCooEquip();
			   zxEqCooEquip.setOrgID(zxEqEquip.getOrgID());
			   List<ZxEqCooEquip> zxEqCooEquipList = zxEqCooEquipMapper.selectByZxEqCooEquipList(zxEqCooEquip);
		        for (ZxEqCooEquip zxEqCooEquip2 : zxEqCooEquipList) {
		        	ZxEqCooEquipItem item = new ZxEqCooEquipItem();
		        	item.setMasID(zxEqCooEquip2.getId());
		        	List<ZxEqCooEquipItem> itemList = zxEqCooEquipItemMapper.selectByZxEqCooEquipItemList(item);
		        	zxEqCooEquipItemList.addAll(itemList);
		        }
		        return repEntity.ok(zxEqCooEquipItemList);
		   }else if(StrUtil.equals(zxEqEquip.getSource(), "2")) {//??????
			   ZxEqOuterEquip zxEqOuterEquip = new ZxEqOuterEquip();
			   zxEqOuterEquip.setOrgID(zxEqEquip.getOrgID());
			   // ????????????
			   PageHelper.startPage(zxEqOuterEquip.getPage(),zxEqOuterEquip.getLimit());
			   // ????????????
			   List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.selectByZxEqOuterEquipList(zxEqOuterEquip);
			   // ??????????????????
			   PageInfo<ZxEqOuterEquip> p = new PageInfo<>(zxEqOuterEquipList);

			   return repEntity.okList(zxEqOuterEquipList, p.getTotal());

		   }else if(StrUtil.equals(zxEqEquip.getSource(), "1")) {//??????
			   if(StrUtil.isNotEmpty(zxEqEquip.getOrgID())) {//????????????
				   zxEqEquip.setUseOrgID(zxEqEquip.getOrgID());
			   }
			   zxEqEquip.setSource("");
			   // ????????????
			   PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
			   // ????????????
			   List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
			   // ??????????????????
			   PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);
			   return repEntity.okList(zxEqEquipList, p.getTotal());
		   }
		   return repEntity.okList(null, 0);
	}

    @Override
    public ResponseEntity ureportZxEqEquipListMechanicsIdle(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }

        // ????????????
        PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
        // ????????????
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListMechanicsForReport(zxEqEquip);
       
        // ??????????????????
        PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

        return repEntity.okList(zxEqEquipList, p.getTotal());
    }
    
    @Override
    public ResponseEntity ureportZxEqEquipListVehicleIdle(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }

        // ????????????
        PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
        // ????????????
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListVehicleForReport(zxEqEquip);
       
        // ??????????????????
        PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

        return repEntity.okList(zxEqEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipListForDepreciation(ZxEqEquip zxEqEquip) {
    	if (zxEqEquip == null) {
    		zxEqEquip = new ZxEqEquip();
    	}
    	String ext1 = "";
    	SysDepartment sysDepartment = sysDepartmentService.getSysDeptProByPrimaryKey(zxEqEquip.getUseOrgID());
    	if(sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
    		if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "1")) {
    			ext1 = "1";
    		}else if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "2")) {
    			ext1 = "2";
    		}
    	}
    	SysDepartment sysProject =  sysDepartmentService.getSysProjectPrimaryKeyByMapper(zxEqEquip.getUseOrgID());
    	if(sysProject != null && StrUtil.isNotEmpty(sysProject.getDepartmentId())) {
    		if(StrUtil.equals(sysProject.getDepartmentFlag(), "3")) {
    			ext1 = "3";
    		}else if(StrUtil.equals(sysProject.getDepartmentFlag(), "4")) {
    			ext1 = "4";
    		}
    	}

    	// ??????????????????
    	if(StrUtil.equals("1", ext1)) {
    		zxEqEquip.setCompanyId("");
    		zxEqEquip.setUseOrgID("");
    	} else if(StrUtil.equals("2", ext1)) {
    		// ????????????????????????
    		zxEqEquip.setCompanyId(zxEqEquip.getUseOrgID());
    		zxEqEquip.setUseOrgID("");
    	} else if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
    		// ???????????????????????????
    		zxEqEquip.setUseOrgID(zxEqEquip.getUseOrgID());//????????????
    	}
    	// ????????????
    	PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
    	// ????????????
    	List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
    	for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
    		ZxErpFile file = new ZxErpFile();
    		file.setOtherId(zxEqEquip2.getId());
    		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxEqEquip2.setFileList(fileList);
    	}
    	// ??????????????????
    	PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

    	return repEntity.okList(zxEqEquipList, p.getTotal());
    }
    
    

	@Override
	public ResponseEntity getZxEqEquipListForDepreciationRemove(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
    		zxEqEquip = new ZxEqEquip();
    	}
    	String ext1 = "";
    	SysDepartment sysDepartment = sysDepartmentService.getSysDeptProByPrimaryKey(zxEqEquip.getUseOrgID());
    	if(sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
    		if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "1")) {
    			ext1 = "1";
    		}else if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "2")) {
    			ext1 = "2";
    		}
    	}
    	SysDepartment sysProject =  sysDepartmentService.getSysProjectPrimaryKeyByMapper(zxEqEquip.getUseOrgID());
    	if(sysProject != null && StrUtil.isNotEmpty(sysProject.getDepartmentId())) {
    		if(StrUtil.equals(sysProject.getDepartmentFlag(), "3")) {
    			ext1 = "3";
    		}else if(StrUtil.equals(sysProject.getDepartmentFlag(), "4")) {
    			ext1 = "4";
    		}
    	}

    	// ??????????????????
    	if(StrUtil.equals("1", ext1)) {
    		zxEqEquip.setCompanyId("");
    		zxEqEquip.setUseOrgID("");
    	} else if(StrUtil.equals("2", ext1)) {
    		// ????????????????????????
    		zxEqEquip.setCompanyId(zxEqEquip.getUseOrgID());
    		zxEqEquip.setUseOrgID("");
    	} else if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
    		// ???????????????????????????
    		zxEqEquip.setUseOrgID(zxEqEquip.getUseOrgID());//????????????
    	}

    	// ????????????
    	PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
    	// ????????????
    	List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
    		for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
    		ZxErpFile file = new ZxErpFile();
    		file.setOtherId(zxEqEquip2.getId());
    		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxEqEquip2.setFileList(fileList);
//    		if(zxEqEquip2.getLeftValue().compareTo(new BigDecimal("0")) <=0) {
//    			zxEqEquipList.remove(zxEqEquip2);
//    			if(zxEqEquipList != null && zxEqEquipList.size() >0) {
//    			}else {
//    				break;
//    			}
//    		}
    	}
    		for (int i = 0; i < zxEqEquipList.size(); i++) {
    			if(zxEqEquipList.get(i).getLeftValue().compareTo(new BigDecimal("0")) <=0) {
    				zxEqEquipList.remove(i);
    				i--;
    			}
    		}
    		
    		for (int i = 0; i < zxEqEquipList.size(); i++) {
    			int leftMonth = Integer.parseInt(zxEqEquipList.get(i).getDepreciation())*12-zxEqEquipList.get(i).getDepreciationMonth();
        		if(leftMonth<=0) {
        			zxEqEquipList.remove(i);
    				i--;
        		}
    		}
    		
    	if(zxEqEquipList != null && zxEqEquipList.size() >0) {
    		for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
        		//??????
        		BigDecimal depreamout = new BigDecimal("0");
        		int leftMonth = Integer.parseInt(zxEqEquip2.getDepreciation())*12-zxEqEquip2.getDepreciationMonth();
        		//????????????=????????????/??????????????????
        		depreamout = CalcUtils.calcDivide(zxEqEquip2.getLeftValue(), new BigDecimal(leftMonth+""));
        		zxEqEquip2.setDepreamout(depreamout);
        		//???????????? = ??????-??????-??????
        		//???????????? = ????????????????????????
        		zxEqEquip2.setFinanceOrginalValue(zxEqEquip2.getActualDepreAmt());
        		zxEqEquip2.setLeftMonth(leftMonth);
			}
    	}
    	// ??????????????????
    	PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

    	return repEntity.okList(zxEqEquipList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxEqEquipListForMoveUseOrg(ZxEqEquip zxEqEquip) { 
		if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        // ????????????
        PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
        // ????????????
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
        for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEquip2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEquip2.setFileList(fileList);
		}
        // ??????????????????
        PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

        return repEntity.okList(zxEqEquipList, p.getTotal());
	}

	@Override
	public ResponseEntity getAddZxEqEquipTotalList(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
			zxEqEquip = new ZxEqEquip();
		}
		// ????????????
		PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
		// ????????????        ??????????????????sql???????????????????????????zxEqEquipMapper.ziYouCLForm
		List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.ureportAddZxEqEquipTotalList(zxEqEquip);
		// ??????????????????
		PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

		return repEntity.okList(zxEqEquipList, p.getTotal());
	}

	@Override
	public List<ZxEqEquip> ureportAddZxEqEquipTotalList(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
			zxEqEquip = new ZxEqEquip();
		}
		// ????????????
		List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.ureportAddZxEqEquipTotalList(zxEqEquip);
		return zxEqEquipList;
	}

	
}
