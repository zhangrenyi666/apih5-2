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
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfSpecialEmp.setCompanyId("");
            zxSfSpecialEmp.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfSpecialEmp.setCompanyId(zxSfSpecialEmp.getOrgID());
            zxSfSpecialEmp.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfSpecialEmp.setProjectId(zxSfSpecialEmp.getOrgID());
            zxSfSpecialEmp.setOrgID("");
        }
        // 分页查询
        PageHelper.startPage(zxSfSpecialEmp.getPage(),zxSfSpecialEmp.getLimit());
        // 获取数据
        List<ZxSfSpecialEmp> zxSfSpecialEmpList = zxSfSpecialEmpMapper.selectByZxSfSpecialEmpList(zxSfSpecialEmp);
        // 查询明细
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
        // 查询附件
  		for (ZxSfSpecialEmp zxSfSpecialEmp2 : zxSfSpecialEmpList) {
  			ZxErpFile zxErpFile = new ZxErpFile();
  			zxErpFile.setOtherId(zxSfSpecialEmp2.getZxSfSpecialEmpId());
  			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
  			zxSfSpecialEmp2.setFileList(zxErpFiles);
  		}
        // 得到分页信息
        PageInfo<ZxSfSpecialEmp> p = new PageInfo<>(zxSfSpecialEmpList);

        return repEntity.okList(zxSfSpecialEmpList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfSpecialEmpDetail(ZxSfSpecialEmp zxSfSpecialEmp) {
        if (zxSfSpecialEmp == null) {
            zxSfSpecialEmp = new ZxSfSpecialEmp();
        }
        // 获取数据
        ZxSfSpecialEmp dbZxSfSpecialEmp = zxSfSpecialEmpMapper.selectByPrimaryKey(zxSfSpecialEmp.getZxSfSpecialEmpId());
        // 数据存在
        if (dbZxSfSpecialEmp != null) {
        	// 附件
            ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(zxSfSpecialEmp.getZxSfSpecialEmpId());
        	List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
        	zxSfSpecialEmp.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfSpecialEmp);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
            // 公司只看见自己的
            zxSfSpecialEmp.setCompanyId(zxSfSpecialEmp.getOrgID());
            zxSfSpecialEmp.setCompanyName(zxSfSpecialEmp.getOrgName());
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfSpecialEmp.setProjectId(zxSfSpecialEmp.getOrgID());
            ZxSysProject project = zxSysProjectMapper.getCompanyInfoByProjectId(zxSfSpecialEmp.getOrgID());
            zxSfSpecialEmp.setCompanyId(project.getCompanyId());
            zxSfSpecialEmp.setCompanyName(project.getCompanyName());
        }
        // 添加明细
      	List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList = zxSfSpecialEmp.getZxSfSpecialEmpItemList();
      	if (zxSfSpecialEmpItemList != null && zxSfSpecialEmpItemList.size() > 0) {
      		for (ZxSfSpecialEmpItem zxSfSpecialEmpItem : zxSfSpecialEmpItemList) {
      			zxSfSpecialEmpItem.setSeID(zxSfSpecialEmp.getZxSfSpecialEmpId());
      			zxSfSpecialEmpItem.setZxSfSpecialEmpItemId((UuidUtil.generate()));
      			zxSfSpecialEmpItem.setCreateUserInfo(userKey, realName);
      			zxSfSpecialEmpItemMapper.insert(zxSfSpecialEmpItem);
      		}
      	}
        // 添加附件
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
           // 项目名称
           dbZxSfSpecialEmp.setOrgName(zxSfSpecialEmp.getOrgName());
           // 项目ID
           dbZxSfSpecialEmp.setOrgID(zxSfSpecialEmp.getOrgID());
           // 单位负责人
           dbZxSfSpecialEmp.setManager(zxSfSpecialEmp.getManager());
           // 填报人
           dbZxSfSpecialEmp.setCreator(zxSfSpecialEmp.getCreator());
           // 填报日期
           dbZxSfSpecialEmp.setCreateDate(zxSfSpecialEmp.getCreateDate());
           // 编辑时间
           dbZxSfSpecialEmp.setEditTime(zxSfSpecialEmp.getEditTime());
           // 所属公司ID
           dbZxSfSpecialEmp.setCompanyId(zxSfSpecialEmp.getCompanyId());
           // 所属公司名称
           dbZxSfSpecialEmp.setCompanyName(zxSfSpecialEmp.getCompanyName());
           // 备注
           dbZxSfSpecialEmp.setRemarks(zxSfSpecialEmp.getRemarks());
           // 排序
           dbZxSfSpecialEmp.setSort(zxSfSpecialEmp.getSort());
           // 共通
           dbZxSfSpecialEmp.setModifyUserInfo(userKey, realName);
           flag = zxSfSpecialEmpMapper.updateByPrimaryKey(dbZxSfSpecialEmp);
           // 修改在新增明细数据
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
            // 修改在新增(附件)
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
        // 失败
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
        		// 删除明细
        		ZxSfSpecialEmpItem zxSfSpecialEmpItemSelect = new ZxSfSpecialEmpItem();
                zxSfSpecialEmpItemSelect.setSeID(zxSfSpecialEmp1.getZxSfSpecialEmpId());
     			List<ZxSfSpecialEmpItem> zxSfSpecialEmpItem1 = zxSfSpecialEmpItemMapper.selectByZxSfSpecialEmpItemList(zxSfSpecialEmpItemSelect);
     			if (zxSfSpecialEmpItem1 != null && zxSfSpecialEmpItem1.size() > 0) {
     				zxSfSpecialEmpItemSelect.setModifyUserInfo(userKey, realName);
     				zxSfSpecialEmpItemMapper.batchDeleteUpdateZxSfSpecialEmpItem(zxSfSpecialEmpItem1, zxSfSpecialEmpItemSelect);
     			}
        		// 删除附件
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfSpecialEmpList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
