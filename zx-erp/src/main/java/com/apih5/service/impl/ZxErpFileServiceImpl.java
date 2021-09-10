package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxErpFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxErpFileService")
public class ZxErpFileServiceImpl implements ZxErpFileService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxErpFileListByCondition(ZxErpFile zxErpFile) {
        if (zxErpFile == null) {
            zxErpFile = new ZxErpFile();
        }
        // 分页查询
        PageHelper.startPage(zxErpFile.getPage(),zxErpFile.getLimit());
        // 获取数据
        List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
        // 得到分页信息
        PageInfo<ZxErpFile> p = new PageInfo<>(zxErpFileList);

        return repEntity.okList(zxErpFileList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxErpFileDetails(ZxErpFile zxErpFile) {
        if (zxErpFile == null) {
            zxErpFile = new ZxErpFile();
        }
        // 获取数据
        ZxErpFile dbZxErpFile = zxErpFileMapper.selectByPrimaryKey(zxErpFile.getUid());
        // 数据存在
        if (dbZxErpFile != null) {
            return repEntity.ok(dbZxErpFile);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxErpFile(ZxErpFile zxErpFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxErpFile.setUid(UuidUtil.generate());
        zxErpFile.setCreateUserInfo(userKey, realName);
        int flag = zxErpFileMapper.insert(zxErpFile);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxErpFile);
        }
    }

    @Override
    public ResponseEntity updateZxErpFile(ZxErpFile zxErpFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxErpFile dbzxErpFile = zxErpFileMapper.selectByPrimaryKey(zxErpFile.getUid());
        if (dbzxErpFile != null && StrUtil.isNotEmpty(dbzxErpFile.getUid())) {
           // 其他表主键
           dbzxErpFile.setOtherId(zxErpFile.getOtherId());
           // 其他表文件分类
           dbzxErpFile.setOtherType(zxErpFile.getOtherType());
           // 文件名称
           dbzxErpFile.setName(zxErpFile.getName());
           // 大小
           dbzxErpFile.setSize(zxErpFile.getSize());
           // 文件格式
           dbzxErpFile.setType(zxErpFile.getType());
           // WEB文件地址
           dbzxErpFile.setUrl(zxErpFile.getUrl());
           // WEB缩略图文件地址
           dbzxErpFile.setThumbUrl(zxErpFile.getThumbUrl());
           // 手机文件地址
           dbzxErpFile.setMobileUrl(zxErpFile.getMobileUrl());
           // 手机缩略图文件地址
           dbzxErpFile.setMobileThumbUrl(zxErpFile.getMobileThumbUrl());
           // 相对路径文件地址
           dbzxErpFile.setRelativeUrl(zxErpFile.getRelativeUrl());
           // 相对路径缩略图文件地址
           dbzxErpFile.setRelativeThumbUrl(zxErpFile.getRelativeThumbUrl());
           // 共通
           dbzxErpFile.setModifyUserInfo(userKey, realName);
           flag = zxErpFileMapper.updateByPrimaryKey(dbzxErpFile);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxErpFile);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxErpFile(List<ZxErpFile> zxErpFileList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxErpFileList != null && zxErpFileList.size() > 0) {
           ZxErpFile zxErpFile = new ZxErpFile();
           zxErpFile.setModifyUserInfo(userKey, realName);
           flag = zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFileList, zxErpFile);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxErpFileList);
        }
    }
    
    @Override
    public ResponseEntity deleteAllZxErpFile(ZxErpFile zxErpFile) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zxErpFile != null && StrUtil.isNotEmpty(zxErpFile.getOtherId())) {
    		zxErpFile.setModifyUserInfo(userKey, realName);
    		flag = zxErpFileMapper.deleteAllZxErpFile(zxErpFile);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorDelete();
    	} else {
    		return repEntity.ok("删除成功！");
    	}
    }
}
