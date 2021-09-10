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
import com.apih5.mybatis.dao.ZjXmSalaryUserAttachmentMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;
import com.apih5.service.ZjXmSalaryUserAttachmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryUserAttachmentService")
public class ZjXmSalaryUserAttachmentServiceImpl implements ZjXmSalaryUserAttachmentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmSalaryUserAttachmentMapper zjXmSalaryUserAttachmentMapper;

    @Override
    public ResponseEntity getZjXmSalaryUserAttachmentListByCondition(ZjXmSalaryUserAttachment zjXmSalaryUserAttachment) {
        if (zjXmSalaryUserAttachment == null) {
            zjXmSalaryUserAttachment = new ZjXmSalaryUserAttachment();
        }
        // 分页查询
        PageHelper.startPage(zjXmSalaryUserAttachment.getPage(),zjXmSalaryUserAttachment.getLimit());
        // 获取数据
        List<ZjXmSalaryUserAttachment> zjXmSalaryUserAttachmentList = zjXmSalaryUserAttachmentMapper.selectByZjXmSalaryUserAttachmentList(zjXmSalaryUserAttachment);
        // 得到分页信息
        PageInfo<ZjXmSalaryUserAttachment> p = new PageInfo<>(zjXmSalaryUserAttachmentList);

        return repEntity.okList(zjXmSalaryUserAttachmentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmSalaryUserAttachmentDetails(ZjXmSalaryUserAttachment zjXmSalaryUserAttachment) {
        if (zjXmSalaryUserAttachment == null) {
            zjXmSalaryUserAttachment = new ZjXmSalaryUserAttachment();
        }
        // 获取数据
        ZjXmSalaryUserAttachment dbZjXmSalaryUserAttachment = zjXmSalaryUserAttachmentMapper.selectByPrimaryKey(zjXmSalaryUserAttachment.getUid());
        // 数据存在
        if (dbZjXmSalaryUserAttachment != null) {
            return repEntity.ok(dbZjXmSalaryUserAttachment);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmSalaryUserAttachment(ZjXmSalaryUserAttachment zjXmSalaryUserAttachment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmSalaryUserAttachment.setUid(UuidUtil.generate());
        zjXmSalaryUserAttachment.setCreateUserInfo(userKey, realName);
        int flag = zjXmSalaryUserAttachmentMapper.insert(zjXmSalaryUserAttachment);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmSalaryUserAttachment);
        }
    }

    @Override
    public ResponseEntity updateZjXmSalaryUserAttachment(ZjXmSalaryUserAttachment zjXmSalaryUserAttachment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmSalaryUserAttachment dbzjXmSalaryUserAttachment = zjXmSalaryUserAttachmentMapper.selectByPrimaryKey(zjXmSalaryUserAttachment.getUid());
        if (dbzjXmSalaryUserAttachment != null && StrUtil.isNotEmpty(dbzjXmSalaryUserAttachment.getUid())) {
           // 其他表主键
           dbzjXmSalaryUserAttachment.setOtherId(zjXmSalaryUserAttachment.getOtherId());
           // 其他表文件分类
           dbzjXmSalaryUserAttachment.setOtherType(zjXmSalaryUserAttachment.getOtherType());
           // 文件名称
           dbzjXmSalaryUserAttachment.setName(zjXmSalaryUserAttachment.getName());
           // 大小
           dbzjXmSalaryUserAttachment.setSize(zjXmSalaryUserAttachment.getSize());
           // 文件格式
           dbzjXmSalaryUserAttachment.setType(zjXmSalaryUserAttachment.getType());
           // WEB文件地址
           dbzjXmSalaryUserAttachment.setUrl(zjXmSalaryUserAttachment.getUrl());
           // WEB缩略图文件地址
           dbzjXmSalaryUserAttachment.setThumbUrl(zjXmSalaryUserAttachment.getThumbUrl());
           // 手机文件地址
           dbzjXmSalaryUserAttachment.setMobileUrl(zjXmSalaryUserAttachment.getMobileUrl());
           // 手机缩略图文件地址
           dbzjXmSalaryUserAttachment.setMobileThumbUrl(zjXmSalaryUserAttachment.getMobileThumbUrl());
           // 相对路径文件地址
           dbzjXmSalaryUserAttachment.setRelativeUrl(zjXmSalaryUserAttachment.getRelativeUrl());
           // 相对路径缩略图文件地址
           dbzjXmSalaryUserAttachment.setRelativeThumbUrl(zjXmSalaryUserAttachment.getRelativeThumbUrl());
           // 附件类型
           dbzjXmSalaryUserAttachment.setFileType(zjXmSalaryUserAttachment.getFileType());
           // 共通
           dbzjXmSalaryUserAttachment.setModifyUserInfo(userKey, realName);
           flag = zjXmSalaryUserAttachmentMapper.updateByPrimaryKey(dbzjXmSalaryUserAttachment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmSalaryUserAttachment);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmSalaryUserAttachment(List<ZjXmSalaryUserAttachment> zjXmSalaryUserAttachmentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmSalaryUserAttachmentList != null && zjXmSalaryUserAttachmentList.size() > 0) {
           ZjXmSalaryUserAttachment zjXmSalaryUserAttachment = new ZjXmSalaryUserAttachment();
           zjXmSalaryUserAttachment.setModifyUserInfo(userKey, realName);
           flag = zjXmSalaryUserAttachmentMapper.batchDeleteUpdateZjXmSalaryUserAttachment(zjXmSalaryUserAttachmentList, zjXmSalaryUserAttachment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmSalaryUserAttachmentList);
        }
    }
}
