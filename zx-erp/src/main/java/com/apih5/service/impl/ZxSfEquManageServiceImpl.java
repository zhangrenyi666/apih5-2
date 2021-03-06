package com.apih5.service.impl;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxSysProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfEquManageItemMapper;
import com.apih5.mybatis.dao.ZxSfEquManageMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfAccess;
import com.apih5.mybatis.pojo.ZxSfAccessItem;
import com.apih5.mybatis.pojo.ZxSfEquManage;
import com.apih5.mybatis.pojo.ZxSfEquManageItem;
import com.apih5.service.ZxSfEquManageItemService;
import com.apih5.service.ZxSfEquManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

@Service("zxSfEquManageService")
public class ZxSfEquManageServiceImpl implements ZxSfEquManageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfEquManageMapper zxSfEquManageMapper;
    
    @Autowired(required = true)
	private ZxErpFileMapper zxErpFileMapper;
	
    @Autowired(required = true)
    private ZxSfEquManageItemMapper zxSfEquManageItemMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;

	
    @Override
    public ResponseEntity getZxSfEquManageListByCondition(ZxSfEquManage zxSfEquManage) {
        if (zxSfEquManage == null) {
            zxSfEquManage = new ZxSfEquManage();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfEquManage.setCompanyId("");
            zxSfEquManage.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSfEquManage.setCompanyId(zxSfEquManage.getProjectId());
            zxSfEquManage.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            // zxSfEquManage.setProjectId(zxSfEquManage.getOrgID());
        }
        // ????????????
        PageHelper.startPage(zxSfEquManage.getPage(),zxSfEquManage.getLimit());
        // ????????????
        if (zxSfEquManage.getCreateDate() != null) {
            String a = String.valueOf(zxSfEquManage.getCreateDate().getTime()).substring(0, 10);
            Long b = Long.valueOf( a + "000");
            Date creatDate = new Date(b);
            zxSfEquManage.setCreateDate(creatDate);
        }
        List<ZxSfEquManage> zxSfEquManageList = zxSfEquManageMapper.selectByZxSfEquManageList(zxSfEquManage);
        
        // ????????????
    	for(ZxSfEquManage zxSfEquManage1 : zxSfEquManageList) {
    		ZxSfEquManageItem zxSfEquManageItem = new ZxSfEquManageItem();
    		zxSfEquManageItem.setEmID(zxSfEquManage1.getZxSfEquManageId());
    		List<ZxSfEquManageItem> zxSfEquManageItem1 = zxSfEquManageItemMapper.selectByZxSfEquManageItemList(zxSfEquManageItem);
    		zxSfEquManage1.setZxSfEquManageItemList(zxSfEquManageItem1);
            for(ZxSfEquManageItem zxSfEquManageItem3 : zxSfEquManageItem1 ) {
                if(zxSfEquManageItem3.getOutDate() == null ) {
                    zxSfEquManage1.setColorflag("1");
                }
            }
    	}
        // ????????????
  		for (ZxSfEquManage zxSfEquManage2 : zxSfEquManageList) {
  			ZxErpFile zxErpFile = new ZxErpFile();
  			zxErpFile.setOtherId(zxSfEquManage2.getZxSfEquManageId());
  			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
  			zxSfEquManage2.setFileList(zxErpFiles);
  		}
        // ??????????????????
        PageInfo<ZxSfEquManage> p = new PageInfo<>(zxSfEquManageList);

        return repEntity.okList(zxSfEquManageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfEquManageDetail(ZxSfEquManage zxSfEquManage) {
        if (zxSfEquManage == null) {
            zxSfEquManage = new ZxSfEquManage();
        }
        // ????????????
        ZxSfEquManage dbZxSfEquManage = zxSfEquManageMapper.selectByPrimaryKey(zxSfEquManage.getZxSfEquManageId());
        // ????????????
        if (dbZxSfEquManage != null) {
        	// ??????
            ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(zxSfEquManage.getZxSfEquManageId());
        	List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
        	zxSfEquManage.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfEquManage);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSfEquManage(ZxSfEquManage zxSfEquManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfEquManage.setZxSfEquManageId(UuidUtil.generate());
        zxSfEquManage.setCreateUserInfo(userKey, realName);
        String ext1 = TokenUtils.getExt1(request);
        String companyId = zxSysProjectMapper.getCompanyIdByProjectId(zxSfEquManage.getOrgID());
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            zxSfEquManage.setCompanyId(companyId);
            zxSfEquManage.setProjectId(zxSfEquManage.getOrgID());
        } else {
            if(StringUtils.isEmpty(companyId)) {
                zxSfEquManage.setProjectId("");
                zxSfEquManage.setCompanyId(zxSfEquManage.getOrgID());
            } else {
                zxSfEquManage.setProjectId(zxSfEquManage.getOrgID());
                zxSfEquManage.setCompanyId(companyId);
            }
        }
        // ????????????
      	List<ZxSfEquManageItem> zxSfEquManageItemList = zxSfEquManage.getZxSfEquManageItemList();
      	if (zxSfEquManageItemList != null && zxSfEquManageItemList.size() > 0) {
      		for (ZxSfEquManageItem zxSfEquManageItem : zxSfEquManageItemList) {
      			zxSfEquManageItem.setEmID(zxSfEquManage.getZxSfEquManageId());
      			zxSfEquManageItem.setZxSfEquManageItemId((UuidUtil.generate()));
      			zxSfEquManageItem.setCreateUserInfo(userKey, realName);
      			zxSfEquManageItemMapper.insert(zxSfEquManageItem);
      		}
      	}
        // ????????????
        List<ZxErpFile> fileList = zxSfEquManage.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfEquManage.getZxSfEquManageId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        int flag = zxSfEquManageMapper.insert(zxSfEquManage);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfEquManage);
        }
    }

    @Override
    public ResponseEntity updateZxSfEquManage(ZxSfEquManage zxSfEquManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfEquManage dbZxSfEquManage = zxSfEquManageMapper.selectByPrimaryKey(zxSfEquManage.getZxSfEquManageId());
        if (dbZxSfEquManage != null && StrUtil.isNotEmpty(dbZxSfEquManage.getZxSfEquManageId())) {
            String companyId = zxSysProjectMapper.getCompanyIdByProjectId(zxSfEquManage.getOrgID());
            String ext1 = TokenUtils.getExt1(request);
           // ????????????
           dbZxSfEquManage.setOrgName(zxSfEquManage.getOrgName());
           // ??????ID
           dbZxSfEquManage.setOrgID(zxSfEquManage.getOrgID());
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                    || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
                dbZxSfEquManage.setProjectId(zxSfEquManage.getOrgID());
                dbZxSfEquManage.setCompanyId(companyId);
            } else {
                if(StringUtils.isEmpty(companyId)) {
                    dbZxSfEquManage.setCompanyId(zxSfEquManage.getOrgID());
                    dbZxSfEquManage.setProjectId("");
                } else {
                    dbZxSfEquManage.setProjectId(zxSfEquManage.getOrgID());
                    dbZxSfEquManage.setCompanyId(companyId);
                }
            }
           // ???????????????
           dbZxSfEquManage.setEquipAddress(zxSfEquManage.getEquipAddress());
           // ?????????
           dbZxSfEquManage.setRelationMan(zxSfEquManage.getRelationMan());
           // ????????????
           dbZxSfEquManage.setRelationTel(zxSfEquManage.getRelationTel());
           // ????????????????????????
           dbZxSfEquManage.setEquipManager(zxSfEquManage.getEquipManager());
           // ?????????????????????
           dbZxSfEquManage.setDeManager(zxSfEquManage.getDeManager());
           // ?????????
           dbZxSfEquManage.setCreator(zxSfEquManage.getCreator());
           // ????????????
           dbZxSfEquManage.setCreateDate(zxSfEquManage.getCreateDate());
           // ????????????
           dbZxSfEquManage.setEditTime(zxSfEquManage.getEditTime());
           // ??????
           dbZxSfEquManage.setRemarks(zxSfEquManage.getRemarks());
           // ??????
           dbZxSfEquManage.setSort(zxSfEquManage.getSort());
           // ??????
           dbZxSfEquManage.setModifyUserInfo(userKey, realName);
           flag = zxSfEquManageMapper.updateByPrimaryKey(dbZxSfEquManage);
           // ???????????????????????????
           ZxSfEquManageItem zxSfEquManageItemSelect = new ZxSfEquManageItem();
           zxSfEquManageItemSelect.setEmID(zxSfEquManage.getZxSfEquManageId());
			List<ZxSfEquManageItem> zxSfEquManageItem1 = zxSfEquManageItemMapper.selectByZxSfEquManageItemList(zxSfEquManageItemSelect);
			if (zxSfEquManageItem1 != null && zxSfEquManageItem1.size() > 0) {
				zxSfEquManageItemSelect.setModifyUserInfo(userKey, realName);
				zxSfEquManageItemMapper.batchDeleteUpdateZxSfEquManageItem(zxSfEquManageItem1, zxSfEquManageItemSelect);
			}
			List<ZxSfEquManageItem> zxSfEquManageItemList = zxSfEquManage.getZxSfEquManageItemList();
	      	if (zxSfEquManageItemList != null && zxSfEquManageItemList.size() > 0) {
	      		for (ZxSfEquManageItem zxSfEquManageItem : zxSfEquManageItemList) {
	      			zxSfEquManageItem.setEmID(zxSfEquManage.getZxSfEquManageId());
	      			zxSfEquManageItem.setZxSfEquManageItemId((UuidUtil.generate()));
	      			zxSfEquManageItem.setCreateUserInfo(userKey, realName);
	      			zxSfEquManageItemMapper.insert(zxSfEquManageItem);
	      		}
	      	}
            // ???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfEquManage.getZxSfEquManageId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfEquManage.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfEquManage.getZxSfEquManageId());
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
            return repEntity.ok("sys.data.update",zxSfEquManage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfEquManage(List<ZxSfEquManage> zxSfEquManageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfEquManageList != null && zxSfEquManageList.size() > 0) {
        	for(ZxSfEquManage zxSfEquManage1 : zxSfEquManageList) {
        		// ????????????
				ZxErpFile zxErpFile = new ZxErpFile();
				zxErpFile.setOtherId(zxSfEquManage1.getZxSfEquManageId());
				List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
				if (zxErpFiles != null && zxErpFiles.size() > 0) {
					zxErpFile.setModifyUserInfo(userKey, realName);
					zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
				}
				// ???????????????????????????
		        ZxSfEquManageItem zxSfEquManageItemSelect = new ZxSfEquManageItem();
		        zxSfEquManageItemSelect.setEmID(zxSfEquManage1.getZxSfEquManageId());
		        List<ZxSfEquManageItem> zxSfEquManageItem1 = zxSfEquManageItemMapper.selectByZxSfEquManageItemList(zxSfEquManageItemSelect);
				if (zxSfEquManageItem1 != null && zxSfEquManageItem1.size() > 0) {
					zxSfEquManageItemSelect.setModifyUserInfo(userKey, realName);
					zxSfEquManageItemMapper.batchDeleteUpdateZxSfEquManageItem(zxSfEquManageItem1, zxSfEquManageItemSelect);
				}
				 ZxSfEquManage zxSfEquManage = new ZxSfEquManage();
		         zxSfEquManage.setModifyUserInfo(userKey, realName);
		         flag = zxSfEquManageMapper.batchDeleteUpdateZxSfEquManage(zxSfEquManageList, zxSfEquManage);
        	}
          
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfEquManageList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
