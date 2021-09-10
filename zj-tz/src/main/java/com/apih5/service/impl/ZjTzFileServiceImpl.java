package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.service.ZjTzFileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzFileService")
public class ZjTzFileServiceImpl implements ZjTzFileService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzFileListByCondition(ZjTzFile zjTzFile) {
        if (zjTzFile == null) {
            zjTzFile = new ZjTzFile();
        }
        // 分页查询
        PageHelper.startPage(zjTzFile.getPage(),zjTzFile.getLimit());
        // 获取数据
        List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFile);
        // 得到分页信息
        PageInfo<ZjTzFile> p = new PageInfo<>(zjTzFileList);

        return repEntity.okList(zjTzFileList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzFileDetails(ZjTzFile zjTzFile) {
        if (zjTzFile == null) {
            zjTzFile = new ZjTzFile();
        }
        // 获取数据
        ZjTzFile dbZjTzFile = zjTzFileMapper.selectByPrimaryKey(zjTzFile.getUid());
        // 数据存在
        if (dbZjTzFile != null) {
            return repEntity.ok(dbZjTzFile);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzFile(ZjTzFile zjTzFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzFile.setUid(UuidUtil.generate());
        zjTzFile.setCreateUserInfo(userKey, realName);
        int flag = zjTzFileMapper.insert(zjTzFile);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzFile);
        }
    }

    @Override
    public ResponseEntity updateZjTzFile(ZjTzFile zjTzFile) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzFile dbzjTzFile = zjTzFileMapper.selectByPrimaryKey(zjTzFile.getUid());
        if (dbzjTzFile != null && StrUtil.isNotEmpty(dbzjTzFile.getUid())) {
           // 其他表主键
           dbzjTzFile.setOtherId(zjTzFile.getOtherId());
           // 其他表文件分类
           dbzjTzFile.setOtherType(zjTzFile.getOtherType());
           // 文件名称
           dbzjTzFile.setName(zjTzFile.getName());
           // 大小
           dbzjTzFile.setSize(zjTzFile.getSize());
           // 文件格式
           dbzjTzFile.setType(zjTzFile.getType());
           // WEB文件地址
           dbzjTzFile.setUrl(zjTzFile.getUrl());
           // WEB缩略图文件地址
           dbzjTzFile.setThumbUrl(zjTzFile.getThumbUrl());
           // 手机文件地址
           dbzjTzFile.setMobileUrl(zjTzFile.getMobileUrl());
           // 手机缩略图文件地址
           dbzjTzFile.setMobileThumbUrl(zjTzFile.getMobileThumbUrl());
           // 相对路径文件地址
           dbzjTzFile.setRelativeUrl(zjTzFile.getRelativeUrl());
           // 相对路径缩略图文件地址
           dbzjTzFile.setRelativeThumbUrl(zjTzFile.getRelativeThumbUrl());
           // 共通
           dbzjTzFile.setModifyUserInfo(userKey, realName);
           flag = zjTzFileMapper.updateByPrimaryKey(dbzjTzFile);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzFile);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzFile(List<ZjTzFile> zjTzFileList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzFileList != null && zjTzFileList.size() > 0) {
           ZjTzFile zjTzFile = new ZjTzFile();
           zjTzFile.setModifyUserInfo(userKey, realName);
           flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(zjTzFileList, zjTzFile);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzFileList);
        }
    }
}
