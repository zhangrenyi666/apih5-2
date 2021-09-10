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
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfHazardMainMapper;
import com.apih5.mybatis.dao.ZxSfHazardMapper;
import com.apih5.mybatis.dao.ZxSfHazardRoomAttMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfHazard;
import com.apih5.mybatis.pojo.ZxSfHazardMain;
import com.apih5.mybatis.pojo.ZxSfHazardRoomAtt;
import com.apih5.service.ZxSfHazardMainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfHazardMainService")
public class ZxSfHazardMainServiceImpl implements ZxSfHazardMainService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfHazardMainMapper zxSfHazardMainMapper;
    
    @Autowired(required = true)
	private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxSfHazardMapper zxSfHazardMapper;
    
    @Autowired(required = true)
    private ZxSfHazardRoomAttMapper zxSfHazardRoomAttMapper;

    @Override
    public ResponseEntity getZxSfHazardMainListByCondition(ZxSfHazardMain zxSfHazardMain) {
        if (zxSfHazardMain == null) {
            zxSfHazardMain = new ZxSfHazardMain();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSfHazardMain.setCompanyId("");
        	zxSfHazardMain.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSfHazardMain.setCompanyId(zxSfHazardMain.getOrgID2());
        	// zxSfHazardMain.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSfHazardMain.setProjectId(zxSfHazardMain.getOrgID2());
        }
        // 分页查询
        PageHelper.startPage(zxSfHazardMain.getPage(),zxSfHazardMain.getLimit());
        // 获取数据
        List<ZxSfHazardMain> zxSfHazardMainList = zxSfHazardMainMapper.selectByZxSfHazardMainList(zxSfHazardMain);
        // 查询明细
  		for (ZxSfHazardMain zxSfHazardMain1 : zxSfHazardMainList) {
  			ZxSfHazard zxSfHazard = new ZxSfHazard();
  			zxSfHazard.setMainID(zxSfHazardMain1.getZxSfHazardMainId());
  			List<ZxSfHazard> zxSfHazardList = zxSfHazardMapper.selectByZxSfHazardList(zxSfHazard);
  			zxSfHazardMain1.setZxSfHazardList(zxSfHazardList);
  		}
        // 查询附件
  		for (ZxSfHazardMain zxSfHazardMain2 : zxSfHazardMainList) {
  			ZxErpFile zxErpFile = new ZxErpFile();
  			zxErpFile.setOtherId(zxSfHazardMain2.getZxSfHazardMainId());
  			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
  			zxSfHazardMain2.setFileList(zxErpFiles);
  		}
        // 得到分页信息
        PageInfo<ZxSfHazardMain> p = new PageInfo<>(zxSfHazardMainList);
        return repEntity.okList(zxSfHazardMainList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfHazardMainDetail(ZxSfHazardMain zxSfHazardMain) {
        if (zxSfHazardMain == null) {
            zxSfHazardMain = new ZxSfHazardMain();
        }
        // 获取数据
        ZxSfHazardMain dbZxSfHazardMain = zxSfHazardMainMapper.selectByPrimaryKey(zxSfHazardMain.getZxSfHazardMainId());
        // 数据存在
        if (dbZxSfHazardMain != null) {
            return repEntity.ok(dbZxSfHazardMain);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfHazardMain(ZxSfHazardMain zxSfHazardMain) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfHazardMain.setZxSfHazardMainId(UuidUtil.generate());
        zxSfHazardMain.setCreateUserInfo(userKey, realName);
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfHazardMain.setCompanyId("");
            zxSfHazardMain.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            zxSfHazardMain.setProjectId(zxSfHazardMain.getOrgID());
        }
        int flag = zxSfHazardMainMapper.insert(zxSfHazardMain);
        // 添加明细
  		List<ZxSfHazard> zxSfHazardList = zxSfHazardMain.getZxSfHazardList();
  		if (zxSfHazardList != null && zxSfHazardList.size() > 0) {
  			for (ZxSfHazard zxSfHazard : zxSfHazardList) {
  				zxSfHazard.setMainID(zxSfHazardMain.getZxSfHazardMainId());
  				zxSfHazard.setZxSfHazardId((UuidUtil.generate()));
  				zxSfHazard.setCreateUserInfo(userKey, realName);
  				zxSfHazardMapper.insert(zxSfHazard);
  			}
  		}
        // 添加附件
        List<ZxErpFile> fileList = zxSfHazardMain.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfHazardMain.getZxSfHazardMainId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfHazardMain);
        }
    }

    @Override
    public ResponseEntity updateZxSfHazardMain(ZxSfHazardMain zxSfHazardMain) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfHazardMain dbZxSfHazardMain = zxSfHazardMainMapper.selectByPrimaryKey(zxSfHazardMain.getZxSfHazardMainId());
        if (dbZxSfHazardMain != null && StrUtil.isNotEmpty(dbZxSfHazardMain.getZxSfHazardMainId())) {
           // 机构ID
           dbZxSfHazardMain.setOrgID(zxSfHazardMain.getOrgID());
           // 机构名称
           dbZxSfHazardMain.setOrgName(zxSfHazardMain.getOrgName());
           // 编制人
           dbZxSfHazardMain.setCreator(zxSfHazardMain.getCreator());
           // 类型
           dbZxSfHazardMain.setType(zxSfHazardMain.getType());
           // 所属公司ID
           dbZxSfHazardMain.setCompanyId(zxSfHazardMain.getCompanyId());
           // 所属公司名称
           dbZxSfHazardMain.setCompanyName(zxSfHazardMain.getCompanyName());
           // 备注
           dbZxSfHazardMain.setRemarks(zxSfHazardMain.getRemarks());
           // 排序
           dbZxSfHazardMain.setSort(zxSfHazardMain.getSort());
           // 共通
           dbZxSfHazardMain.setModifyUserInfo(userKey, realName);
           flag = zxSfHazardMainMapper.updateByPrimaryKey(dbZxSfHazardMain);
            // 修改在新增(明细)
           ZxSfHazard zxSfHazardSelect = new ZxSfHazard();
           zxSfHazardSelect.setMainID(zxSfHazardMain.getZxSfHazardMainId());
			List<ZxSfHazard> zxSfHazard = zxSfHazardMapper.selectByZxSfHazardList(zxSfHazardSelect);
			if (zxSfHazard != null && zxSfHazard.size() > 0) {
				zxSfHazardSelect.setModifyUserInfo(userKey, realName);
				zxSfHazardMapper.batchDeleteUpdateZxSfHazard(zxSfHazard, zxSfHazardSelect);
			}
			List<ZxSfHazard> zxSfHazardList = zxSfHazardMain.getZxSfHazardList();
	  		if (zxSfHazardList != null && zxSfHazardList.size() > 0) {
	  			for (ZxSfHazard zxSfHazard1 : zxSfHazardList) {
	  				zxSfHazard1.setMainID(zxSfHazardMain.getZxSfHazardMainId());
	  				zxSfHazard1.setZxSfHazardId((UuidUtil.generate()));
	  				zxSfHazard1.setCreateUserInfo(userKey, realName);
	  				zxSfHazardMapper.insert(zxSfHazard1);
	  			}
	  		}
           
            // 修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfHazardMain.getZxSfHazardMainId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfHazardMain.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfHazardMain.getZxSfHazardMainId());
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
            return repEntity.ok("sys.data.update",zxSfHazardMain);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfHazardMain(List<ZxSfHazardMain> zxSfHazardMainList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfHazardMainList != null && zxSfHazardMainList.size() > 0) {
        	for(ZxSfHazardMain zxSfHazardMain1 : zxSfHazardMainList) {
        		ZxSfHazard zxSfHazardSelect = new ZxSfHazard();
                zxSfHazardSelect.setMainID(zxSfHazardMain1.getZxSfHazardMainId());
     			List<ZxSfHazard> zxSfHazard = zxSfHazardMapper.selectByZxSfHazardList(zxSfHazardSelect);
     			if (zxSfHazard != null && zxSfHazard.size() > 0) {
     				zxSfHazardSelect.setModifyUserInfo(userKey, realName);
     				zxSfHazardMapper.batchDeleteUpdateZxSfHazard(zxSfHazard, zxSfHazardSelect);
     			}
        		
        		ZxErpFile zxErpFileSelect = new ZxErpFile();
    			zxErpFileSelect.setOtherId(zxSfHazardMain1.getZxSfHazardMainId());
    			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
    			if (zxErpFiles != null && zxErpFiles.size() > 0) {
    				zxErpFileSelect.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
    			}
        	}
        	ZxSfHazardMain zxSfHazardMain = new ZxSfHazardMain();
            zxSfHazardMain.setModifyUserInfo(userKey, realName);
            flag = zxSfHazardMainMapper.batchDeleteUpdateZxSfHazardMain(zxSfHazardMainList, zxSfHazardMain);
           
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfHazardMainList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
