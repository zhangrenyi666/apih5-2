package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEquipMapper;
import com.apih5.mybatis.dao.ZxEqEquipScrapeMapper;
import com.apih5.mybatis.dao.ZxEqUseEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqEquipScrape;
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqEquipScrapeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipScrapeService")
public class ZxEqEquipScrapeServiceImpl implements ZxEqEquipScrapeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipScrapeMapper zxEqEquipScrapeMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;
    
    @Autowired(required = true)
    private ZxEqUseEquipMapper zxEqUseEquipMapper;
    

    @Override
    public ResponseEntity getZxEqEquipScrapeListByCondition(ZxEqEquipScrape zxEqEquipScrape) {
        if (zxEqEquipScrape == null) {
            zxEqEquipScrape = new ZxEqEquipScrape();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqEquipScrape.setComID("");
        	zxEqEquipScrape.setOrgID("");
        	zxEqEquipScrape.setSeeFlagForJu("???????????????????????????");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxEqEquipScrape.setComID(zxEqEquipScrape.getOrgID());
        	zxEqEquipScrape.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxEqEquipScrape.setOrgID(zxEqEquipScrape.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxEqEquipScrape.getPage(),zxEqEquipScrape.getLimit());
        // ????????????
        List<ZxEqEquipScrape> zxEqEquipScrapeList = zxEqEquipScrapeMapper.selectByZxEqEquipScrapeList(zxEqEquipScrape);
        for (ZxEqEquipScrape zxEqEquipScrape2 : zxEqEquipScrapeList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEquipScrape2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEquipScrape2.setFileList(fileList);
        }
        // ??????????????????
        PageInfo<ZxEqEquipScrape> p = new PageInfo<>(zxEqEquipScrapeList);

        return repEntity.okList(zxEqEquipScrapeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipScrapeDetails(ZxEqEquipScrape zxEqEquipScrape) {
        if (zxEqEquipScrape == null) {
            zxEqEquipScrape = new ZxEqEquipScrape();
        }
        // ????????????
        ZxEqEquipScrape dbZxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
        // ????????????
        if (dbZxEqEquipScrape != null) {
            return repEntity.ok(dbZxEqEquipScrape);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(zxEqEquipScrape.getPeriodDate() != null) {
        	zxEqEquipScrape.setPeriod(DateUtil.format(zxEqEquipScrape.getPeriodDate(), "yyyy-MM"));
        }
        zxEqEquipScrape.setId(UuidUtil.generate());
        zxEqEquipScrape.setAuditStatus("0");
        zxEqEquipScrape.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipScrapeMapper.insert(zxEqEquipScrape);
        if(zxEqEquipScrape.getFileList() != null && zxEqEquipScrape.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqEquipScrape.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqEquipScrape.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipScrape);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipScrape dbzxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
        if (dbzxEqEquipScrape != null && StrUtil.isNotEmpty(dbzxEqEquipScrape.getId())) {
           // ??????id
           dbzxEqEquipScrape.setOrgID(zxEqEquipScrape.getOrgID());
           // ????????????
           if(zxEqEquipScrape.getPeriodDate() != null) {
        	   dbzxEqEquipScrape.setPeriodDate(zxEqEquipScrape.getPeriodDate());
        	   dbzxEqEquipScrape.setPeriod(DateUtil.format(zxEqEquipScrape.getPeriodDate(), "yyyy-MM"));
           }
           // ??????id
           dbzxEqEquipScrape.setEquipID(zxEqEquipScrape.getEquipID());
           // ????????????
           dbzxEqEquipScrape.setEquipName(zxEqEquipScrape.getEquipName());
           // ????????????
           dbzxEqEquipScrape.setFinanceNo(zxEqEquipScrape.getFinanceNo());
           // ????????????
           dbzxEqEquipScrape.setSpec(zxEqEquipScrape.getSpec());
           // ????????????
           dbzxEqEquipScrape.setFactory(zxEqEquipScrape.getFactory());
           // ????????????
           dbzxEqEquipScrape.setEnginerno(zxEqEquipScrape.getEnginerno());
           // ???????????????
           dbzxEqEquipScrape.setDownserial(zxEqEquipScrape.getDownserial());
           // ????????????
           dbzxEqEquipScrape.setOutFactoryDate(zxEqEquipScrape.getOutFactoryDate());
           // ??????????????????
           dbzxEqEquipScrape.setRequireYear(zxEqEquipScrape.getRequireYear());
           // ??????????????????
           dbzxEqEquipScrape.setActualYear(zxEqEquipScrape.getActualYear());
           // ????????????
           dbzxEqEquipScrape.setOrginalValue(zxEqEquipScrape.getOrginalValue());
           // ????????????
           dbzxEqEquipScrape.setDeprevalue(zxEqEquipScrape.getDeprevalue());
           // ????????????
           dbzxEqEquipScrape.setLeftvalue(zxEqEquipScrape.getLeftvalue());
           // ????????????
           dbzxEqEquipScrape.setPlace(zxEqEquipScrape.getPlace());
           // ???????????????
           dbzxEqEquipScrape.setPassNo(zxEqEquipScrape.getPassNo());
           // ???????????????????????????
           dbzxEqEquipScrape.setScrapeReason(zxEqEquipScrape.getScrapeReason());
           // ?????????????????????????????????????????????
           dbzxEqEquipScrape.setOption1(zxEqEquipScrape.getOption1());
           // ???????????????????????????
           dbzxEqEquipScrape.setHandleway(zxEqEquipScrape.getHandleway());
           // ???????????????????????????
           dbzxEqEquipScrape.setOption2(zxEqEquipScrape.getOption2());
//           // ????????????
//           dbzxEqEquipScrape.setAuditStatus(zxEqEquipScrape.getAuditStatus());
           // ??????
           dbzxEqEquipScrape.setRemark(zxEqEquipScrape.getRemark());
           // ????????????
           dbzxEqEquipScrape.setEditTime(zxEqEquipScrape.getEditTime());
           // 
           dbzxEqEquipScrape.setApprovalNo(zxEqEquipScrape.getApprovalNo());
           // ????????????
           dbzxEqEquipScrape.setScrapeDate(zxEqEquipScrape.getScrapeDate());
           // 
           dbzxEqEquipScrape.setComID(zxEqEquipScrape.getComID());
           // 
           dbzxEqEquipScrape.setComName(zxEqEquipScrape.getComName());
           // ??????name
           dbzxEqEquipScrape.setOrgName(zxEqEquipScrape.getOrgName());
           // ??????
           dbzxEqEquipScrape.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipScrapeMapper.updateByPrimaryKey(dbzxEqEquipScrape);
           //??????????????????
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqEquipScrape.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqEquipScrape.getFileList() != null && zxEqEquipScrape.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqEquipScrape.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqEquipScrape.getId());
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
            return repEntity.ok("sys.data.update",zxEqEquipScrape);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipScrape(List<ZxEqEquipScrape> zxEqEquipScrapeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipScrapeList != null && zxEqEquipScrapeList.size() > 0) {
        	for (ZxEqEquipScrape zxEqEquipScrape : zxEqEquipScrapeList) {
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqEquipScrape.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqEquipScrape zxEqEquipScrape = new ZxEqEquipScrape();
           zxEqEquipScrape.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipScrapeMapper.batchDeleteUpdateZxEqEquipScrape(zxEqEquipScrapeList, zxEqEquipScrape);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipScrapeList);
        }
    }

    @Override
    public ResponseEntity reportZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquipScrape dbzxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
    	if (dbzxEqEquipScrape != null && StrUtil.isNotEmpty(dbzxEqEquipScrape.getId())) {
    		// ????????????
    		dbzxEqEquipScrape.setAuditStatus("2");//2?????????
    		// ??????
    		dbzxEqEquipScrape.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipScrapeMapper.updateByPrimaryKey(dbzxEqEquipScrape);
//    		//???????????????????????????????????????
//    		ZxEqEquip equip = zxEqEquipMapper.selectByPrimaryKey(dbzxEqEquipScrape.getEquipID());
//    		if(equip != null && StrUtil.isNotEmpty(equip.getId())) {
//    			equip.setDelFlag("1");
//    			equip.setModifyUserInfo(userKey, realName);
//    			flag = zxEqEquipMapper.updateByPrimaryKey(equip);
//    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquipScrape);
    	}
    }

	@Override
	public ZxEqEquipScrape ureportZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
		 if (zxEqEquipScrape == null) {
	            zxEqEquipScrape = new ZxEqEquipScrape();
	        }
	        // ????????????
	        ZxEqEquipScrape dbZxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
	        
	        return dbZxEqEquipScrape;
	}

	@Override
	public ResponseEntity agreeZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquipScrape dbzxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
    	if (dbzxEqEquipScrape != null && StrUtil.isNotEmpty(dbzxEqEquipScrape.getId())) {
    		// ????????????
    		dbzxEqEquipScrape.setAuditStatus("1");//1??????
    		// ??????
    		dbzxEqEquipScrape.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipScrapeMapper.updateByPrimaryKey(dbzxEqEquipScrape);
    		//????????????===????????????status???????????????????????????changeDate??????????????????????????????????????????????????????????????????
    		ZxEqEquip equip = zxEqEquipMapper.selectByPrimaryKey(dbzxEqEquipScrape.getEquipID());
    		if(equip != null && StrUtil.isNotEmpty(equip.getId())) {
    			//update
				equip.setStatus("??????");
				equip.setChangeDate(new Date());
    			equip.setModifyUserInfo(userKey, realName);
    			flag = zxEqEquipMapper.updateByPrimaryKey(equip);
    			//???????????????del
    			ZxEqUseEquip useEquip = new ZxEqUseEquip();
    			useEquip.setRefEquipID(equip.getId());
    			List<ZxEqUseEquip> useEquipList = zxEqUseEquipMapper.selectByZxEqUseEquipList(useEquip);
    			if(useEquipList != null && useEquipList.size() >0) {
    				useEquipList.get(0).setDelFlag("1");
    				useEquipList.get(0).setModifyUserInfo(userKey, realName);
    				flag = zxEqUseEquipMapper.updateByPrimaryKey(useEquipList.get(0));
    				//del???????????????
    				ZxErpFile delFile = new ZxErpFile();
    				delFile.setOtherId(useEquipList.get(0).getId());
    				List<ZxErpFile> delFileList= zxErpFileMapper.selectByZxErpFileList(delFile);
    				if(delFileList != null && delFileList.size() >0) {
    					delFile.setModifyUserInfo(userKey, realName);
    					zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    				}
    			}
    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquipScrape);
    	}
	}

	@Override
	public ResponseEntity refuseZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquipScrape dbzxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
    	if (dbzxEqEquipScrape != null && StrUtil.isNotEmpty(dbzxEqEquipScrape.getId())) {
    		// ????????????
    		dbzxEqEquipScrape.setAuditStatus("3");//3??????
    		// ??????
    		dbzxEqEquipScrape.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipScrapeMapper.updateByPrimaryKey(dbzxEqEquipScrape);
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquipScrape);
    	}
	}
}
