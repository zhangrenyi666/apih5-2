package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxSysProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfOtherAddFileMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfOtherAddFile;
import com.apih5.service.ZxSfOtherAddFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

@Service("zxSfOtherAddFileService")
public class ZxSfOtherAddFileServiceImpl implements ZxSfOtherAddFileService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfOtherAddFileMapper zxSfOtherAddFileMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;
    
    @Override
    public ResponseEntity getZxSfOtherAddFileListByCondition(ZxSfOtherAddFile zxSfOtherAddFile) {
        if (zxSfOtherAddFile == null) {
            zxSfOtherAddFile = new ZxSfOtherAddFile();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSfOtherAddFile.setCompanyId("");
        	zxSfOtherAddFile.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSfOtherAddFile.setCompanyId(zxSfOtherAddFile.getOrgID());
        	zxSfOtherAddFile.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSfOtherAddFile.setProjectId(zxSfOtherAddFile.getOrgID());
        }
        zxSfOtherAddFile.setOrgID("");
        // 分页查询
        PageHelper.startPage(zxSfOtherAddFile.getPage(),zxSfOtherAddFile.getLimit());
        // 获取数据 category 1安全组织机构 2安全工作情况季报 3应急预案查询
        List<ZxSfOtherAddFile> zxSfOtherAddFileList = zxSfOtherAddFileMapper.selectByZxSfOtherAddFileList(zxSfOtherAddFile);
        //查询附件
        for (ZxSfOtherAddFile zxCrCustomerExtAttr1 : zxSfOtherAddFileList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCrCustomerExtAttr1.getZxSfOtherAddFileId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCrCustomerExtAttr1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfOtherAddFile> p = new PageInfo<>(zxSfOtherAddFileList);
        return repEntity.okList(zxSfOtherAddFileList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfOtherAddFileDetail(ZxSfOtherAddFile zxSfOtherAddFile) {
        if (zxSfOtherAddFile == null) {
            zxSfOtherAddFile = new ZxSfOtherAddFile();
        }
        // 获取数据
        ZxSfOtherAddFile dbZxSfOtherAddFile = zxSfOtherAddFileMapper.selectByPrimaryKey(zxSfOtherAddFile.getZxSfOtherAddFileId());
        // 数据存在
        if (dbZxSfOtherAddFile != null) {
        	//附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfOtherAddFile.getZxSfOtherAddFileId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfOtherAddFile.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfOtherAddFile);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfOtherAddFile(ZxSfOtherAddFile zxSfOtherAddFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfOtherAddFile.setZxSfOtherAddFileId(UuidUtil.generate());
        zxSfOtherAddFile.setCreateUserInfo(userKey, realName);
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfOtherAddFile.setCompanyId("");
            zxSfOtherAddFile.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfOtherAddFile.setCompanyId(zxSfOtherAddFile.getOrgID());
            zxSfOtherAddFile.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfOtherAddFile.setProjectId(zxSfOtherAddFile.getOrgID());
            String companyId = zxSysProjectMapper.getCompanyIdByProjectId(zxSfOtherAddFile.getOrgID());
            zxSfOtherAddFile.setCompanyId(companyId);
        }
        if (StringUtils.isEmpty(zxSfOtherAddFile.getCategory())) {
            return repEntity.layerMessage("no", "no category");
        }
        int flag = zxSfOtherAddFileMapper.insert(zxSfOtherAddFile);
        //添加附件
        List<ZxErpFile> fileList = zxSfOtherAddFile.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfOtherAddFile.getZxSfOtherAddFileId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfOtherAddFile);
        }
    }

    @Override
    public ResponseEntity updateZxSfOtherAddFile(ZxSfOtherAddFile zxSfOtherAddFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfOtherAddFile dbZxSfOtherAddFile = zxSfOtherAddFileMapper.selectByPrimaryKey(zxSfOtherAddFile.getZxSfOtherAddFileId());
        if (dbZxSfOtherAddFile != null && StrUtil.isNotEmpty(dbZxSfOtherAddFile.getZxSfOtherAddFileId())) {
            // 填报人
            dbZxSfOtherAddFile.setCreator(zxSfOtherAddFile.getCreator());
            // 填报日期
            dbZxSfOtherAddFile.setCreateDate(zxSfOtherAddFile.getCreateDate());
            // 名称
            dbZxSfOtherAddFile.setTitle(zxSfOtherAddFile.getTitle());
            // 备注
            dbZxSfOtherAddFile.setNotes(zxSfOtherAddFile.getNotes());
            // 备注
            dbZxSfOtherAddFile.setRemarks(zxSfOtherAddFile.getRemarks());
            // 共通
            dbZxSfOtherAddFile.setModifyUserInfo(userKey, realName);
            flag = zxSfOtherAddFileMapper.updateByPrimaryKey(dbZxSfOtherAddFile);
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfOtherAddFile.getZxSfOtherAddFileId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
               zxErpFileSelect.setModifyUserInfo(userKey, realName);
               zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfOtherAddFile.getFileList();
            if(fileList != null && fileList.size()>0) {
               for (ZxErpFile zxErpFile : fileList) {
                   zxErpFile.setOtherId(zxSfOtherAddFile.getZxSfOtherAddFileId());
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
            return repEntity.ok("sys.data.update",zxSfOtherAddFile);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfOtherAddFile(List<ZxSfOtherAddFile> zxSfOtherAddFileList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfOtherAddFileList != null && zxSfOtherAddFileList.size() > 0) {
        	for (ZxSfOtherAddFile zxCrCustomerExtAttr2 : zxSfOtherAddFileList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCrCustomerExtAttr2.getZxSfOtherAddFileId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
        	}
           ZxSfOtherAddFile zxSfOtherAddFile = new ZxSfOtherAddFile();
           zxSfOtherAddFile.setModifyUserInfo(userKey, realName);
           flag = zxSfOtherAddFileMapper.batchDeleteUpdateZxSfOtherAddFile(zxSfOtherAddFileList, zxSfOtherAddFile);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfOtherAddFileList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
