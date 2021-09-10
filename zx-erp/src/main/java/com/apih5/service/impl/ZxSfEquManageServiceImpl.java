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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfEquManage.setCompanyId("");
            zxSfEquManage.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfEquManage.setCompanyId(zxSfEquManage.getProjectId());
            zxSfEquManage.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            // zxSfEquManage.setProjectId(zxSfEquManage.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSfEquManage.getPage(),zxSfEquManage.getLimit());
        // 获取数据
        if (zxSfEquManage.getCreateDate() != null) {
            String a = String.valueOf(zxSfEquManage.getCreateDate().getTime()).substring(0, 10);
            Long b = Long.valueOf( a + "000");
            Date creatDate = new Date(b);
            zxSfEquManage.setCreateDate(creatDate);
        }
        List<ZxSfEquManage> zxSfEquManageList = zxSfEquManageMapper.selectByZxSfEquManageList(zxSfEquManage);
        
        // 查询明细
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
        // 查询附件
  		for (ZxSfEquManage zxSfEquManage2 : zxSfEquManageList) {
  			ZxErpFile zxErpFile = new ZxErpFile();
  			zxErpFile.setOtherId(zxSfEquManage2.getZxSfEquManageId());
  			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
  			zxSfEquManage2.setFileList(zxErpFiles);
  		}
        // 得到分页信息
        PageInfo<ZxSfEquManage> p = new PageInfo<>(zxSfEquManageList);

        return repEntity.okList(zxSfEquManageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfEquManageDetail(ZxSfEquManage zxSfEquManage) {
        if (zxSfEquManage == null) {
            zxSfEquManage = new ZxSfEquManage();
        }
        // 获取数据
        ZxSfEquManage dbZxSfEquManage = zxSfEquManageMapper.selectByPrimaryKey(zxSfEquManage.getZxSfEquManageId());
        // 数据存在
        if (dbZxSfEquManage != null) {
        	// 附件
            ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(zxSfEquManage.getZxSfEquManageId());
        	List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
        	zxSfEquManage.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfEquManage);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
        // 添加明细
      	List<ZxSfEquManageItem> zxSfEquManageItemList = zxSfEquManage.getZxSfEquManageItemList();
      	if (zxSfEquManageItemList != null && zxSfEquManageItemList.size() > 0) {
      		for (ZxSfEquManageItem zxSfEquManageItem : zxSfEquManageItemList) {
      			zxSfEquManageItem.setEmID(zxSfEquManage.getZxSfEquManageId());
      			zxSfEquManageItem.setZxSfEquManageItemId((UuidUtil.generate()));
      			zxSfEquManageItem.setCreateUserInfo(userKey, realName);
      			zxSfEquManageItemMapper.insert(zxSfEquManageItem);
      		}
      	}
        // 添加附件
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
           // 机构名称
           dbZxSfEquManage.setOrgName(zxSfEquManage.getOrgName());
           // 机构ID
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
           // 设备所在地
           dbZxSfEquManage.setEquipAddress(zxSfEquManage.getEquipAddress());
           // 联系人
           dbZxSfEquManage.setRelationMan(zxSfEquManage.getRelationMan());
           // 联系电话
           dbZxSfEquManage.setRelationTel(zxSfEquManage.getRelationTel());
           // 设备安全主管领导
           dbZxSfEquManage.setEquipManager(zxSfEquManage.getEquipManager());
           // 主管部门负责人
           dbZxSfEquManage.setDeManager(zxSfEquManage.getDeManager());
           // 填报人
           dbZxSfEquManage.setCreator(zxSfEquManage.getCreator());
           // 填报日期
           dbZxSfEquManage.setCreateDate(zxSfEquManage.getCreateDate());
           // 编辑日期
           dbZxSfEquManage.setEditTime(zxSfEquManage.getEditTime());
           // 备注
           dbZxSfEquManage.setRemarks(zxSfEquManage.getRemarks());
           // 排序
           dbZxSfEquManage.setSort(zxSfEquManage.getSort());
           // 共通
           dbZxSfEquManage.setModifyUserInfo(userKey, realName);
           flag = zxSfEquManageMapper.updateByPrimaryKey(dbZxSfEquManage);
           // 修改在新增明细数据
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
            // 修改在新增(附件)
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
        // 失败
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
        		// 删除附件
				ZxErpFile zxErpFile = new ZxErpFile();
				zxErpFile.setOtherId(zxSfEquManage1.getZxSfEquManageId());
				List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
				if (zxErpFiles != null && zxErpFiles.size() > 0) {
					zxErpFile.setModifyUserInfo(userKey, realName);
					zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
				}
				// 修改在新增明细数据
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfEquManageList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
