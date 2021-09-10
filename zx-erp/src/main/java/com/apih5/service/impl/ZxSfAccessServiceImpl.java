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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSfAccess.setCompanyId("");
        	zxSfAccess.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSfAccess.setCompanyId(zxSfAccess.getOrgID2());
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSfAccess.setProjectId(zxSfAccess.getOrgID2());
        }
        // 分页查询
        PageHelper.startPage(zxSfAccess.getPage(),zxSfAccess.getLimit());
        // 获取数据
        List<ZxSfAccess> zxSfAccessList = zxSfAccessMapper.selectByZxSfAccessList(zxSfAccess);
        // 查询明细
        for(ZxSfAccess zxSfAccess1 : zxSfAccessList) {
            ZxSfAccessItem zxSfAccessItem = new ZxSfAccessItem();
            zxSfAccessItem.setAccessID(zxSfAccess1.getZxSfAccessId());
            List<ZxSfAccessItem> zxSfAccessItem1 = zxSfAccessItemMapper.selectByZxSfAccessItemList(zxSfAccessItem);
            zxSfAccess1.setZxSfAccessItemList(zxSfAccessItem1);
        }
        // 查询附件
        for (ZxSfAccess zxSfAccess2 : zxSfAccessList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfAccess2.getZxSfAccessId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfAccess2.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfAccess> p = new PageInfo<>(zxSfAccessList);
        return repEntity.okList(zxSfAccessList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfAccessDetail(ZxSfAccess zxSfAccess) {
        if (zxSfAccess == null) {
            zxSfAccess = new ZxSfAccess();
        }
        // 获取数据
        ZxSfAccess dbZxSfAccess = zxSfAccessMapper.selectByPrimaryKey(zxSfAccess.getZxSfAccessId());
        // 数据存在
        if (dbZxSfAccess != null) {
        	// 附件
            ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(zxSfAccess.getZxSfAccessId());
        	List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
        	zxSfAccess.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfAccess);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
        // 添加明细
      	List<ZxSfAccessItem> zxSfAccessItemList = zxSfAccess.getZxSfAccessItemList();
      	if (zxSfAccessItemList != null && zxSfAccessItemList.size() > 0) {
      		for (ZxSfAccessItem zxSfAccessItem : zxSfAccessItemList) {
      			zxSfAccessItem.setAccessID(zxSfAccess.getZxSfAccessId());
      			zxSfAccessItem.setZxSfAccessItemId((UuidUtil.generate()));
      			zxSfAccessItem.setCreateUserInfo(userKey, realName);
      			zxSfAccessItemMapper.insert(zxSfAccessItem);
      		}
      	}
        // 添加附件
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
            // 机构名称
            dbZxSfAccess.setOrgName(zxSfAccess.getOrgName());
            // 机构ID
            dbZxSfAccess.setOrgID(zxSfAccess.getOrgID());
            // 协作队伍名称
            dbZxSfAccess.setCrmName(zxSfAccess.getCrmName());
            // 协作队伍ID
            dbZxSfAccess.setCrmID(zxSfAccess.getCrmID());
            // 营业执照号
            dbZxSfAccess.setCardNo(zxSfAccess.getCardNo());
            // 资质等级
            dbZxSfAccess.setLevs(zxSfAccess.getLevs());
            // 安全许可证
            dbZxSfAccess.setSafeCardNo(zxSfAccess.getSafeCardNo());
            // 合同编号
            dbZxSfAccess.setContractNo(zxSfAccess.getContractNo());
            // 开始时间
            dbZxSfAccess.setInDate(zxSfAccess.getInDate());
            // 结束时间
            dbZxSfAccess.setOutDate(zxSfAccess.getOutDate());
            // 编辑时间
            dbZxSfAccess.setEditTime(zxSfAccess.getEditTime());
            // 级别
            dbZxSfAccess.setLevels(zxSfAccess.getLevels());
            // 备注
            dbZxSfAccess.setRemarks(zxSfAccess.getRemarks());
            // 排序
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
            // 共通
            dbZxSfAccess.setModifyUserInfo(userKey, realName);
            flag = zxSfAccessMapper.updateByPrimaryKey(dbZxSfAccess);
            // 修改在新增明细数据
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
            // 修改在新增(附件)
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
        // 失败
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
        		// 删除明细
        		ZxSfAccessItem zxSfAccessItemSelect = new ZxSfAccessItem();
                zxSfAccessItemSelect.setAccessID(zxSfAccess1.getZxSfAccessId());
     			List<ZxSfAccessItem> zxSfAccessItem1 = zxSfAccessItemMapper.selectByZxSfAccessItemList(zxSfAccessItemSelect);
     			if (zxSfAccessItem1 != null && zxSfAccessItem1.size() > 0) {
     				zxSfAccessItemSelect.setModifyUserInfo(userKey, realName);
     				zxSfAccessItemMapper.batchDeleteUpdateZxSfAccessItem(zxSfAccessItem1, zxSfAccessItemSelect);
     			}
        		// 删除附件
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfAccessList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity updateZxSfAccessOutDate(ZxSfAccess zxSfAccess) {
        Assert.notNull(zxSfAccess.getOutDate(), "必须输入退场时间！");
        if (zxSfAccess.getInDate() != null) {
            if (zxSfAccess.getInDate().after(zxSfAccess.getOutDate())) {
                Assert.notNull(null, "退场时的日期不能早于进场日期！");
            }
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfAccess dbZxSfAccess = zxSfAccessMapper.selectByPrimaryKey(zxSfAccess.getZxSfAccessId());
        if (dbZxSfAccess != null && StrUtil.isNotEmpty(dbZxSfAccess.getZxSfAccessId())) {
            // 共通
            zxSfAccess.setModifyUserInfo(userKey, realName);
            flag = zxSfAccessMapper.updateByPrimaryKey(zxSfAccess);
        }
        // 失败
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
       
            // 结束时间
                dbZxSfAccess.setOutDate(null);
           // 共通
           dbZxSfAccess.setModifyUserInfo(userKey, realName);
           flag = zxSfAccessMapper.updateByPrimaryKey(dbZxSfAccess);
        
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfAccess);
        }
    }
}
