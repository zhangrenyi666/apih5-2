package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxSysProjectMapper;
import com.apih5.mybatis.pojo.ZxSysProject;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfAccessItemMapper;
import com.apih5.mybatis.dao.ZxSfAccessMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfAccess;
import com.apih5.mybatis.pojo.ZxSfAccessItem;
import com.apih5.service.ZxSfAccessService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

@Service("zxSfAccessService")
public class ZxSfAccessServiceImpl implements ZxSfAccessService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfAccessMapper zxSfAccessMapper;
    
    @Autowired(required = true)
	private ZxErpFileMapper zxErpFileMapper;
	
	@Autowired(required = true)
	private ZxSfAccessItemMapper zxSfAccessItemMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;

    @Override
    public ResponseEntity getZxSfAccessListByCondition(ZxSfAccess zxSfAccess) {
        if (zxSfAccess == null) {
            zxSfAccess = new ZxSfAccess();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSfAccess.setCompanyId("");
        	zxSfAccess.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
        	zxSfAccess.setCompanyId(zxSfAccess.getOrgID2());
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
        	zxSfAccess.setProjectId(zxSfAccess.getOrgID2());
        }
        // ????????????
        PageHelper.startPage(zxSfAccess.getPage(),zxSfAccess.getLimit());
        // ????????????
        List<ZxSfAccess> zxSfAccessList = zxSfAccessMapper.selectByZxSfAccessList(zxSfAccess);
        // ????????????
        for(ZxSfAccess zxSfAccess1 : zxSfAccessList) {
            ZxSfAccessItem zxSfAccessItem = new ZxSfAccessItem();
            zxSfAccessItem.setAccessID(zxSfAccess1.getZxSfAccessId());
            List<ZxSfAccessItem> zxSfAccessItem1 = zxSfAccessItemMapper.selectByZxSfAccessItemList(zxSfAccessItem);
            zxSfAccess1.setZxSfAccessItemList(zxSfAccessItem1);
        }
        // ????????????
        for (ZxSfAccess zxSfAccess2 : zxSfAccessList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfAccess2.getZxSfAccessId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfAccess2.setFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxSfAccess> p = new PageInfo<>(zxSfAccessList);
        return repEntity.okList(zxSfAccessList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfAccessDetail(ZxSfAccess zxSfAccess) {
        if (zxSfAccess == null) {
            zxSfAccess = new ZxSfAccess();
        }
        // ????????????
        ZxSfAccess dbZxSfAccess = zxSfAccessMapper.selectByPrimaryKey(zxSfAccess.getZxSfAccessId());
        // ????????????
        if (dbZxSfAccess != null) {
        	// ??????
            ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(zxSfAccess.getZxSfAccessId());
        	List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
        	zxSfAccess.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfAccess);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSfAccess(ZxSfAccess zxSfAccess) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfAccess.setZxSfAccessId(UuidUtil.generate());
        zxSfAccess.setCreateUserInfo(userKey, realName);
        ZxSysProject project = zxSysProjectMapper.getCompanyInfoByProjectId(zxSfAccess.getOrgID());
        if (project != null) {
            zxSfAccess.setProjectId(zxSfAccess.getOrgID());
            zxSfAccess.setCompanyId(project.getCompanyId());
            zxSfAccess.setCompanyName(project.getCompanyName());
        } else {
            zxSfAccess.setCompanyId(zxSfAccess.getOrgID());
            zxSfAccess.setCompanyName(zxSfAccess.getOrgName());
        }
        // ????????????
      	List<ZxSfAccessItem> zxSfAccessItemList = zxSfAccess.getZxSfAccessItemList();
      	if (zxSfAccessItemList != null && zxSfAccessItemList.size() > 0) {
      		for (ZxSfAccessItem zxSfAccessItem : zxSfAccessItemList) {
      			zxSfAccessItem.setAccessID(zxSfAccess.getZxSfAccessId());
      			zxSfAccessItem.setZxSfAccessItemId((UuidUtil.generate()));
      			zxSfAccessItem.setCreateUserInfo(userKey, realName);
      			zxSfAccessItemMapper.insert(zxSfAccessItem);
      		}
      	}
        // ????????????
 		List<ZxErpFile> fileList = zxSfAccess.getFileList();
 		if (fileList != null && fileList.size() > 0) {
 			for (ZxErpFile zxErpFile : fileList) {
 				zxErpFile.setOtherId(zxSfAccess.getZxSfAccessId());
 				zxErpFile.setUid((UuidUtil.generate()));
 				zxErpFile.setCreateUserInfo(userKey, realName);
 				zxErpFileMapper.insert(zxErpFile);
 			}
 		}
        int flag = zxSfAccessMapper.insert(zxSfAccess);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfAccess);
        }
    }

    @Override
    public ResponseEntity updateZxSfAccess(ZxSfAccess zxSfAccess) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfAccess dbZxSfAccess = zxSfAccessMapper.selectByPrimaryKey(zxSfAccess.getZxSfAccessId());
        if (dbZxSfAccess != null && StrUtil.isNotEmpty(dbZxSfAccess.getZxSfAccessId())) {
            // ????????????
            dbZxSfAccess.setOrgName(zxSfAccess.getOrgName());
            // ??????ID
            dbZxSfAccess.setOrgID(zxSfAccess.getOrgID());
            // ??????????????????
            dbZxSfAccess.setCrmName(zxSfAccess.getCrmName());
            // ????????????ID
            dbZxSfAccess.setCrmID(zxSfAccess.getCrmID());
            // ???????????????
            dbZxSfAccess.setCardNo(zxSfAccess.getCardNo());
            // ????????????
            dbZxSfAccess.setLevs(zxSfAccess.getLevs());
            // ???????????????
            dbZxSfAccess.setSafeCardNo(zxSfAccess.getSafeCardNo());
            // ????????????
            dbZxSfAccess.setContractNo(zxSfAccess.getContractNo());
            // ????????????
            dbZxSfAccess.setInDate(zxSfAccess.getInDate());
            // ????????????
            dbZxSfAccess.setOutDate(zxSfAccess.getOutDate());
            // ????????????
            dbZxSfAccess.setEditTime(zxSfAccess.getEditTime());
            // ??????
            dbZxSfAccess.setLevels(zxSfAccess.getLevels());
            // ??????
            dbZxSfAccess.setRemarks(zxSfAccess.getRemarks());
            // ??????
            dbZxSfAccess.setSort(zxSfAccess.getSort());

            ZxSysProject project = zxSysProjectMapper.getCompanyInfoByProjectId(zxSfAccess.getOrgID());
            if (project != null) {
                dbZxSfAccess.setCompanyId(project.getCompanyId());
                dbZxSfAccess.setCompanyName(project.getCompanyName());
                dbZxSfAccess.setProjectId(zxSfAccess.getOrgID());
            } else {
                dbZxSfAccess.setCompanyId(zxSfAccess.getOrgID());
                dbZxSfAccess.setCompanyName(zxSfAccess.getOrgName());
            }
            // ??????
            dbZxSfAccess.setModifyUserInfo(userKey, realName);
            flag = zxSfAccessMapper.updateByPrimaryKey(dbZxSfAccess);
            // ???????????????????????????
            ZxSfAccessItem zxSfAccessItemSelect = new ZxSfAccessItem();
            zxSfAccessItemSelect.setAccessID(zxSfAccess.getZxSfAccessId());
            List<ZxSfAccessItem> zxSfAccessItem1 = zxSfAccessItemMapper.selectByZxSfAccessItemList(zxSfAccessItemSelect);
            if (zxSfAccessItem1 != null && zxSfAccessItem1.size() > 0) {
                zxSfAccessItemSelect.setModifyUserInfo(userKey, realName);
                zxSfAccessItemMapper.batchDeleteUpdateZxSfAccessItem(zxSfAccessItem1, zxSfAccessItemSelect);
            }
	        List<ZxSfAccessItem> zxSfAccessItemList = zxSfAccess.getZxSfAccessItemList();
	        if(zxSfAccessItemList !=null && zxSfAccessItemList.size()>0) {
	        	for(ZxSfAccessItem zxSfAccessItem : zxSfAccessItemList) {
	        		zxSfAccessItem.setAccessID(zxSfAccess.getZxSfAccessId());
	        		zxSfAccessItem.setZxSfAccessItemId((UuidUtil.generate()));
	        		zxSfAccessItem.setCreateUserInfo(userKey, realName);
	        		zxSfAccessItemMapper.insert(zxSfAccessItem);
	        	}
	        }
            // ???????????????(??????)
			ZxErpFile zxErpFileSelect = new ZxErpFile();
			zxErpFileSelect.setOtherId(zxSfAccess.getZxSfAccessId());
			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
			if (zxErpFiles != null && zxErpFiles.size() > 0) {
				zxErpFileSelect.setModifyUserInfo(userKey, realName);
				zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
			}
			List<ZxErpFile> fileList = zxSfAccess.getFileList();
			if (fileList != null && fileList.size() > 0) {
				for (ZxErpFile zxErpFile : fileList) {
					zxErpFile.setOtherId(zxSfAccess.getZxSfAccessId());
					zxErpFile.setUid((UuidUtil.generate()));
					zxErpFile.setCreateUserInfo(userKey, realName);
					zxErpFileMapper.insert(zxErpFile);
				}
			}
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfAccess);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfAccess(List<ZxSfAccess> zxSfAccessList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfAccessList != null && zxSfAccessList.size() > 0) {
        	for(ZxSfAccess zxSfAccess1 : zxSfAccessList) {
        		// ????????????
        		ZxSfAccessItem zxSfAccessItemSelect = new ZxSfAccessItem();
                zxSfAccessItemSelect.setAccessID(zxSfAccess1.getZxSfAccessId());
     			List<ZxSfAccessItem> zxSfAccessItem1 = zxSfAccessItemMapper.selectByZxSfAccessItemList(zxSfAccessItemSelect);
     			if (zxSfAccessItem1 != null && zxSfAccessItem1.size() > 0) {
     				zxSfAccessItemSelect.setModifyUserInfo(userKey, realName);
     				zxSfAccessItemMapper.batchDeleteUpdateZxSfAccessItem(zxSfAccessItem1, zxSfAccessItemSelect);
     			}
        		// ????????????
				ZxErpFile zxErpFile = new ZxErpFile();
				zxErpFile.setOtherId(zxSfAccess1.getZxSfAccessId());
				List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
				if (zxErpFiles != null && zxErpFiles.size() > 0) {
					zxErpFile.setModifyUserInfo(userKey, realName);
					zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
				}
        	}
        	ZxSfAccess zxSfAccess = new ZxSfAccess();
            zxSfAccess.setModifyUserInfo(userKey, realName);
            flag = zxSfAccessMapper.batchDeleteUpdateZxSfAccess(zxSfAccessList, zxSfAccess);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfAccessList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    
    @Override
    public ResponseEntity updateZxSfAccessOutDate(ZxSfAccess zxSfAccess) {
        Assert.notNull(zxSfAccess.getOutDate(), "???????????????????????????");
        if (zxSfAccess.getInDate() != null) {
            if (zxSfAccess.getInDate().after(zxSfAccess.getOutDate())) {
                Assert.notNull(null, "?????????????????????????????????????????????");
            }
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfAccess dbZxSfAccess = zxSfAccessMapper.selectByPrimaryKey(zxSfAccess.getZxSfAccessId());
        if (dbZxSfAccess != null && StrUtil.isNotEmpty(dbZxSfAccess.getZxSfAccessId())) {
            // ??????
            zxSfAccess.setModifyUserInfo(userKey, realName);
            flag = zxSfAccessMapper.updateByPrimaryKey(zxSfAccess);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        } else {
            return repEntity.ok("sys.data.update",zxSfAccess);
        }
    }
    
    @Override
    public ResponseEntity updateZxSfAccessOutDateReturn(ZxSfAccess zxSfAccess) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfAccess dbZxSfAccess = zxSfAccessMapper.selectByPrimaryKey(zxSfAccess.getZxSfAccessId());
       
            // ????????????
                dbZxSfAccess.setOutDate(null);
           // ??????
           dbZxSfAccess.setModifyUserInfo(userKey, realName);
           flag = zxSfAccessMapper.updateByPrimaryKey(dbZxSfAccess);
        
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfAccess);
        }
    }
}
