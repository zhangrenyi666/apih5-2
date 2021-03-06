package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxSysProjectMapper;
import com.apih5.mybatis.pojo.ZxSysProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfSpecialEmpItemMapper;
import com.apih5.mybatis.dao.ZxSfSpecialEmpMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfSpecialEmp;
import com.apih5.mybatis.pojo.ZxSfSpecialEmpItem;
import com.apih5.service.ZxSfSpecialEmpService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxSfSpecialEmpService")
public class ZxSfSpecialEmpServiceImpl implements ZxSfSpecialEmpService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfSpecialEmpMapper zxSfSpecialEmpMapper;
    
    @Autowired(required = true)
	private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxSfSpecialEmpItemMapper zxSfSpecialEmpItemMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;

    @Override
    public ResponseEntity getZxSfSpecialEmpListByCondition(ZxSfSpecialEmp zxSfSpecialEmp) {
        if (zxSfSpecialEmp == null) {
            zxSfSpecialEmp = new ZxSfSpecialEmp();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfSpecialEmp.setCompanyId("");
            zxSfSpecialEmp.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSfSpecialEmp.setCompanyId(zxSfSpecialEmp.getOrgID());
            zxSfSpecialEmp.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSfSpecialEmp.setProjectId(zxSfSpecialEmp.getOrgID());
            zxSfSpecialEmp.setOrgID("");
        }
        // ????????????
        PageHelper.startPage(zxSfSpecialEmp.getPage(),zxSfSpecialEmp.getLimit());
        // ????????????
        List<ZxSfSpecialEmp> zxSfSpecialEmpList = zxSfSpecialEmpMapper.selectByZxSfSpecialEmpList(zxSfSpecialEmp);
        // ????????????
    	for(ZxSfSpecialEmp zxSfSpecialEmp1 : zxSfSpecialEmpList) {
    		ZxSfSpecialEmpItem zxSfSpecialEmpItem = new ZxSfSpecialEmpItem();
    		zxSfSpecialEmpItem.setSeID(zxSfSpecialEmp1.getZxSfSpecialEmpId());
    		List<ZxSfSpecialEmpItem> zxSfSpecialEmpItem1 = zxSfSpecialEmpItemMapper.selectByZxSfSpecialEmpItemList(zxSfSpecialEmpItem);
            Date now = new Date();
            Optional<ZxSfSpecialEmpItem> optional = zxSfSpecialEmpItem1.stream().filter(s -> s.getCheckDate() != null && s.getCheckDate().before(now)).findAny();
            zxSfSpecialEmp1.setCheckDateMark("0");
            if (optional.isPresent()) {
                zxSfSpecialEmp1.setCheckDateMark("1");
            }
            zxSfSpecialEmp1.setZxSfSpecialEmpItemList(zxSfSpecialEmpItem1);
    	}
        // ????????????
  		for (ZxSfSpecialEmp zxSfSpecialEmp2 : zxSfSpecialEmpList) {
  			ZxErpFile zxErpFile = new ZxErpFile();
  			zxErpFile.setOtherId(zxSfSpecialEmp2.getZxSfSpecialEmpId());
  			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
  			zxSfSpecialEmp2.setFileList(zxErpFiles);
  		}
        // ??????????????????
        PageInfo<ZxSfSpecialEmp> p = new PageInfo<>(zxSfSpecialEmpList);

        return repEntity.okList(zxSfSpecialEmpList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfSpecialEmpDetail(ZxSfSpecialEmp zxSfSpecialEmp) {
        if (zxSfSpecialEmp == null) {
            zxSfSpecialEmp = new ZxSfSpecialEmp();
        }
        // ????????????
        ZxSfSpecialEmp dbZxSfSpecialEmp = zxSfSpecialEmpMapper.selectByPrimaryKey(zxSfSpecialEmp.getZxSfSpecialEmpId());
        // ????????????
        if (dbZxSfSpecialEmp != null) {
        	// ??????
            ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(zxSfSpecialEmp.getZxSfSpecialEmpId());
        	List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
        	zxSfSpecialEmp.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfSpecialEmp);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSfSpecialEmp(ZxSfSpecialEmp zxSfSpecialEmp) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfSpecialEmp.setZxSfSpecialEmpId(UuidUtil.generate());
        zxSfSpecialEmp.setCreateUserInfo(userKey, realName);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSfSpecialEmp.setCompanyId(zxSfSpecialEmp.getOrgID());
            zxSfSpecialEmp.setCompanyName(zxSfSpecialEmp.getOrgName());
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSfSpecialEmp.setProjectId(zxSfSpecialEmp.getOrgID());
            ZxSysProject project = zxSysProjectMapper.getCompanyInfoByProjectId(zxSfSpecialEmp.getOrgID());
            zxSfSpecialEmp.setCompanyId(project.getCompanyId());
            zxSfSpecialEmp.setCompanyName(project.getCompanyName());
        }
        // ????????????
      	List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList = zxSfSpecialEmp.getZxSfSpecialEmpItemList();
      	if (zxSfSpecialEmpItemList != null && zxSfSpecialEmpItemList.size() > 0) {
      		for (ZxSfSpecialEmpItem zxSfSpecialEmpItem : zxSfSpecialEmpItemList) {
      			zxSfSpecialEmpItem.setSeID(zxSfSpecialEmp.getZxSfSpecialEmpId());
      			zxSfSpecialEmpItem.setZxSfSpecialEmpItemId((UuidUtil.generate()));
      			zxSfSpecialEmpItem.setCreateUserInfo(userKey, realName);
      			zxSfSpecialEmpItemMapper.insert(zxSfSpecialEmpItem);
      		}
      	}
        // ????????????
        List<ZxErpFile> fileList = zxSfSpecialEmp.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfSpecialEmp.getZxSfSpecialEmpId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        int flag = zxSfSpecialEmpMapper.insert(zxSfSpecialEmp);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfSpecialEmp);
        }
    }

    @Override
    public ResponseEntity updateZxSfSpecialEmp(ZxSfSpecialEmp zxSfSpecialEmp) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfSpecialEmp dbZxSfSpecialEmp = zxSfSpecialEmpMapper.selectByPrimaryKey(zxSfSpecialEmp.getZxSfSpecialEmpId());
        if (dbZxSfSpecialEmp != null && StrUtil.isNotEmpty(dbZxSfSpecialEmp.getZxSfSpecialEmpId())) {
           // ????????????
           dbZxSfSpecialEmp.setOrgName(zxSfSpecialEmp.getOrgName());
           // ??????ID
           dbZxSfSpecialEmp.setOrgID(zxSfSpecialEmp.getOrgID());
           // ???????????????
           dbZxSfSpecialEmp.setManager(zxSfSpecialEmp.getManager());
           // ?????????
           dbZxSfSpecialEmp.setCreator(zxSfSpecialEmp.getCreator());
           // ????????????
           dbZxSfSpecialEmp.setCreateDate(zxSfSpecialEmp.getCreateDate());
           // ????????????
           dbZxSfSpecialEmp.setEditTime(zxSfSpecialEmp.getEditTime());
           // ????????????ID
           dbZxSfSpecialEmp.setCompanyId(zxSfSpecialEmp.getCompanyId());
           // ??????????????????
           dbZxSfSpecialEmp.setCompanyName(zxSfSpecialEmp.getCompanyName());
           // ??????
           dbZxSfSpecialEmp.setRemarks(zxSfSpecialEmp.getRemarks());
           // ??????
           dbZxSfSpecialEmp.setSort(zxSfSpecialEmp.getSort());
           // ??????
           dbZxSfSpecialEmp.setModifyUserInfo(userKey, realName);
           flag = zxSfSpecialEmpMapper.updateByPrimaryKey(dbZxSfSpecialEmp);
           // ???????????????????????????
//           ZxSfSpecialEmpItem zxSfSpecialEmpItemSelect = new ZxSfSpecialEmpItem();
//           zxSfSpecialEmpItemSelect.setSeID(zxSfSpecialEmp.getZxSfSpecialEmpId());
//			List<ZxSfSpecialEmpItem> zxSfSpecialEmpItem1 = zxSfSpecialEmpItemMapper.selectByZxSfSpecialEmpItemList(zxSfSpecialEmpItemSelect);
//			if (zxSfSpecialEmpItem1 != null && zxSfSpecialEmpItem1.size() > 0) {
//				zxSfSpecialEmpItemSelect.setModifyUserInfo(userKey, realName);
//				zxSfSpecialEmpItemMapper.batchDeleteUpdateZxSfSpecialEmpItem(zxSfSpecialEmpItem1, zxSfSpecialEmpItemSelect);
//			}
//			List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList = zxSfSpecialEmp.getZxSfSpecialEmpItemList();
//	      	if (zxSfSpecialEmpItemList != null && zxSfSpecialEmpItemList.size() > 0) {
//	      		for (ZxSfSpecialEmpItem zxSfSpecialEmpItem : zxSfSpecialEmpItemList) {
//	      			zxSfSpecialEmpItem.setSeID(zxSfSpecialEmp.getZxSfSpecialEmpId());
//	      			zxSfSpecialEmpItem.setZxSfSpecialEmpItemId((UuidUtil.generate()));
//	      			zxSfSpecialEmpItem.setCreateUserInfo(userKey, realName);
//	      			zxSfSpecialEmpItemMapper.insert(zxSfSpecialEmpItem);
//	      		}
//	      	}
            // ???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfSpecialEmp.getZxSfSpecialEmpId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfSpecialEmp.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfSpecialEmp.getZxSfSpecialEmpId());
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
            return repEntity.ok("sys.data.update",zxSfSpecialEmp);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfSpecialEmp(List<ZxSfSpecialEmp> zxSfSpecialEmpList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfSpecialEmpList != null && zxSfSpecialEmpList.size() > 0) {
        	for(ZxSfSpecialEmp zxSfSpecialEmp1 : zxSfSpecialEmpList) {
        		// ????????????
        		ZxSfSpecialEmpItem zxSfSpecialEmpItemSelect = new ZxSfSpecialEmpItem();
                zxSfSpecialEmpItemSelect.setSeID(zxSfSpecialEmp1.getZxSfSpecialEmpId());
     			List<ZxSfSpecialEmpItem> zxSfSpecialEmpItem1 = zxSfSpecialEmpItemMapper.selectByZxSfSpecialEmpItemList(zxSfSpecialEmpItemSelect);
     			if (zxSfSpecialEmpItem1 != null && zxSfSpecialEmpItem1.size() > 0) {
     				zxSfSpecialEmpItemSelect.setModifyUserInfo(userKey, realName);
     				zxSfSpecialEmpItemMapper.batchDeleteUpdateZxSfSpecialEmpItem(zxSfSpecialEmpItem1, zxSfSpecialEmpItemSelect);
     			}
        		// ????????????
				ZxErpFile zxErpFile = new ZxErpFile();
				zxErpFile.setOtherId(zxSfSpecialEmp1.getZxSfSpecialEmpId());
				List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
				if (zxErpFiles != null && zxErpFiles.size() > 0) {
					zxErpFile.setModifyUserInfo(userKey, realName);
					zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
				}
        		ZxSfSpecialEmp zxSfSpecialEmp = new ZxSfSpecialEmp();
                zxSfSpecialEmp.setModifyUserInfo(userKey, realName);
                flag = zxSfSpecialEmpMapper.batchDeleteUpdateZxSfSpecialEmp(zxSfSpecialEmpList, zxSfSpecialEmp);
        	}
           
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfSpecialEmpList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
