package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfOtherAddFilePressMapper;
import com.apih5.mybatis.pojo.ZxSfOtherAddFilePress;
import com.apih5.service.ZxSfOtherAddFilePressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfOtherAddFilePressService")
public class ZxSfOtherAddFilePressServiceImpl implements ZxSfOtherAddFilePressService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfOtherAddFilePressMapper zxSfOtherAddFilePressMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSfOtherAddFilePressListByCondition(ZxSfOtherAddFilePress zxSfOtherAddFilePress) {
        if (zxSfOtherAddFilePress == null) {
            zxSfOtherAddFilePress = new ZxSfOtherAddFilePress();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfOtherAddFilePress.setCompanyId("");
            zxSfOtherAddFilePress.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfOtherAddFilePress.setCompanyId(zxSfOtherAddFilePress.getOrgId());
            zxSfOtherAddFilePress.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfOtherAddFilePress.setOrgId(zxSfOtherAddFilePress.getOrgId());
        }

        // 分页查询
        PageHelper.startPage(zxSfOtherAddFilePress.getPage(),zxSfOtherAddFilePress.getLimit());
        // 获取数据
        List<ZxSfOtherAddFilePress> zxSfOtherAddFilePressList = zxSfOtherAddFilePressMapper.selectByZxSfOtherAddFilePressList(zxSfOtherAddFilePress);
        for (ZxSfOtherAddFilePress zxSfOtherAddFilePress1 : zxSfOtherAddFilePressList) {
            //查询附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfOtherAddFilePress1.getZxSfOtherAddFilePressId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfOtherAddFilePress1.setZxErpFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfOtherAddFilePress> p = new PageInfo<>(zxSfOtherAddFilePressList);

        return repEntity.okList(zxSfOtherAddFilePressList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfOtherAddFilePressDetail(ZxSfOtherAddFilePress zxSfOtherAddFilePress) {
        if (zxSfOtherAddFilePress == null) {
            zxSfOtherAddFilePress = new ZxSfOtherAddFilePress();
        }
        // 获取数据
        ZxSfOtherAddFilePress dbZxSfOtherAddFilePress = zxSfOtherAddFilePressMapper.selectByPrimaryKey(zxSfOtherAddFilePress.getZxSfOtherAddFilePressId());
        // 数据存在
        if (dbZxSfOtherAddFilePress != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfOtherAddFilePress.getZxSfOtherAddFilePressId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSfOtherAddFilePress.setZxErpFileList(zxErpFiles);
            return repEntity.ok(dbZxSfOtherAddFilePress);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfOtherAddFilePress(ZxSfOtherAddFilePress zxSfOtherAddFilePress) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfOtherAddFilePress.setZxSfOtherAddFilePressId(UuidUtil.generate());
        zxSfOtherAddFilePress.setCreateUserInfo(userKey, realName);
        int flag = zxSfOtherAddFilePressMapper.insert(zxSfOtherAddFilePress);

        //添加附件
        List<ZxErpFile> fileList = zxSfOtherAddFilePress.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfOtherAddFilePress.getZxSfOtherAddFilePressId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfOtherAddFilePress);
        }
    }

    @Override
    public ResponseEntity updateZxSfOtherAddFilePress(ZxSfOtherAddFilePress zxSfOtherAddFilePress) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfOtherAddFilePress dbZxSfOtherAddFilePress = zxSfOtherAddFilePressMapper.selectByPrimaryKey(zxSfOtherAddFilePress.getZxSfOtherAddFilePressId());
        if (dbZxSfOtherAddFilePress != null && StrUtil.isNotEmpty(dbZxSfOtherAddFilePress.getZxSfOtherAddFilePressId())) {
           // 名称
           dbZxSfOtherAddFilePress.setTitle(zxSfOtherAddFilePress.getTitle());
           // 填报人
           dbZxSfOtherAddFilePress.setCreator(zxSfOtherAddFilePress.getCreator());
           // 填报日期
           dbZxSfOtherAddFilePress.setCreateDate(zxSfOtherAddFilePress.getCreateDate());
           // 机构名称
           dbZxSfOtherAddFilePress.setOrgName(zxSfOtherAddFilePress.getOrgName());
           // 备注
           dbZxSfOtherAddFilePress.setNotes(zxSfOtherAddFilePress.getNotes());
           // 共通
           dbZxSfOtherAddFilePress.setModifyUserInfo(userKey, realName);
           flag = zxSfOtherAddFilePressMapper.updateByPrimaryKey(dbZxSfOtherAddFilePress);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfOtherAddFilePress.getZxSfOtherAddFilePressId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //明细list
            List<ZxErpFile> fileList = zxSfOtherAddFilePress.getZxErpFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfOtherAddFilePress.getZxSfOtherAddFilePressId());
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
            return repEntity.ok("sys.data.update",zxSfOtherAddFilePress);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfOtherAddFilePress(List<ZxSfOtherAddFilePress> zxSfOtherAddFilePressList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfOtherAddFilePressList != null && zxSfOtherAddFilePressList.size() > 0) {
            for (ZxSfOtherAddFilePress zxSfOtherAddFilePress : zxSfOtherAddFilePressList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfOtherAddFilePress.getZxSfOtherAddFilePressId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSfOtherAddFilePress zxSfOtherAddFilePress = new ZxSfOtherAddFilePress();
           zxSfOtherAddFilePress.setModifyUserInfo(userKey, realName);
           flag = zxSfOtherAddFilePressMapper.batchDeleteUpdateZxSfOtherAddFilePress(zxSfOtherAddFilePressList, zxSfOtherAddFilePress);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfOtherAddFilePressList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
