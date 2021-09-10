package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehFileMapper;
import com.apih5.mybatis.pojo.ZjLzehFile;
import com.apih5.service.ZjLzehFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehFileService")
public class ZjLzehFileServiceImpl implements ZjLzehFileService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehFileMapper zjLzehFileMapper;

    @Override
    public ResponseEntity getZjLzehFileListByCondition(ZjLzehFile zjLzehFile) {
        if (zjLzehFile == null) {
            zjLzehFile = new ZjLzehFile();
        }
        // 分页查询
        PageHelper.startPage(zjLzehFile.getPage(),zjLzehFile.getLimit());
        // 获取数据
        List<ZjLzehFile> zjLzehFileList = zjLzehFileMapper.selectByZjLzehFileList(zjLzehFile);
        // 得到分页信息
        PageInfo<ZjLzehFile> p = new PageInfo<>(zjLzehFileList);

        return repEntity.okList(zjLzehFileList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehFileDetails(ZjLzehFile zjLzehFile) {
        if (zjLzehFile == null) {
            zjLzehFile = new ZjLzehFile();
        }
        // 获取数据
        ZjLzehFile dbZjLzehFile = zjLzehFileMapper.selectByPrimaryKey(zjLzehFile.getUid());
        // 数据存在
        if (dbZjLzehFile != null) {
            return repEntity.ok(dbZjLzehFile);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehFile(ZjLzehFile zjLzehFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehFile.setUid(UuidUtil.generate());
        zjLzehFile.setCreateUserInfo(userKey, realName);
        int flag = zjLzehFileMapper.insert(zjLzehFile);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehFile);
        }
    }

    @Override
    public ResponseEntity updateZjLzehFile(ZjLzehFile zjLzehFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehFile dbzjLzehFile = zjLzehFileMapper.selectByPrimaryKey(zjLzehFile.getUid());
        if (dbzjLzehFile != null && StrUtil.isNotEmpty(dbzjLzehFile.getUid())) {
           // 其他表主键
           dbzjLzehFile.setOtherId(zjLzehFile.getOtherId());
           // 其他表文件分类
           dbzjLzehFile.setOtherType(zjLzehFile.getOtherType());
           // 文件名称
           dbzjLzehFile.setName(zjLzehFile.getName());
           // 大小
           dbzjLzehFile.setSize(zjLzehFile.getSize());
           // 文件格式
           dbzjLzehFile.setType(zjLzehFile.getType());
           // WEB文件地址
           dbzjLzehFile.setUrl(zjLzehFile.getUrl());
           // WEB缩略图文件地址
           dbzjLzehFile.setThumbUrl(zjLzehFile.getThumbUrl());
           // 手机文件地址
           dbzjLzehFile.setMobileUrl(zjLzehFile.getMobileUrl());
           // 手机缩略图文件地址
           dbzjLzehFile.setMobileThumbUrl(zjLzehFile.getMobileThumbUrl());
           // 相对路径文件地址
           dbzjLzehFile.setRelativeUrl(zjLzehFile.getRelativeUrl());
           // 相对路径缩略图文件地址
           dbzjLzehFile.setRelativeThumbUrl(zjLzehFile.getRelativeThumbUrl());
           // 共通
           dbzjLzehFile.setModifyUserInfo(userKey, realName);
           flag = zjLzehFileMapper.updateByPrimaryKey(dbzjLzehFile);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehFile);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehFile(List<ZjLzehFile> zjLzehFileList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehFileList != null && zjLzehFileList.size() > 0) {
           ZjLzehFile zjLzehFile = new ZjLzehFile();
           zjLzehFile.setModifyUserInfo(userKey, realName);
           flag = zjLzehFileMapper.batchDeleteUpdateZjLzehFile(zjLzehFileList, zjLzehFile);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehFileList);
        }
    }
}
