package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxGcsgCommonAttachmentMapper;
import com.apih5.mybatis.pojo.ZxGcsgCommonAttachment;
import com.apih5.service.ZxGcsgCommonAttachmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxGcsgCommonAttachmentService")
public class ZxGcsgCommonAttachmentServiceImpl implements ZxGcsgCommonAttachmentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxGcsgCommonAttachmentMapper zxGcsgCommonAttachmentMapper;

    @Override
    public ResponseEntity getZxGcsgCommonAttachmentListByCondition(ZxGcsgCommonAttachment zxGcsgCommonAttachment) {
        if (zxGcsgCommonAttachment == null) {
            zxGcsgCommonAttachment = new ZxGcsgCommonAttachment();
        }
        // 分页查询
        PageHelper.startPage(zxGcsgCommonAttachment.getPage(),zxGcsgCommonAttachment.getLimit());
        // 获取数据
        List<ZxGcsgCommonAttachment> zxGcsgCommonAttachmentList = zxGcsgCommonAttachmentMapper.selectByZxGcsgCommonAttachmentList(zxGcsgCommonAttachment);
        // 得到分页信息
        PageInfo<ZxGcsgCommonAttachment> p = new PageInfo<>(zxGcsgCommonAttachmentList);

        return repEntity.okList(zxGcsgCommonAttachmentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxGcsgCommonAttachmentDetail(ZxGcsgCommonAttachment zxGcsgCommonAttachment) {
        if (zxGcsgCommonAttachment == null) {
            zxGcsgCommonAttachment = new ZxGcsgCommonAttachment();
        }
        // 获取数据
        ZxGcsgCommonAttachment dbZxGcsgCommonAttachment = zxGcsgCommonAttachmentMapper.selectByPrimaryKey(zxGcsgCommonAttachment.getUid());
        // 数据存在
        if (dbZxGcsgCommonAttachment != null) {
            return repEntity.ok(dbZxGcsgCommonAttachment);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxGcsgCommonAttachment(ZxGcsgCommonAttachment zxGcsgCommonAttachment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxGcsgCommonAttachment.setUid(UuidUtil.generate());
        zxGcsgCommonAttachment.setCreateUserInfo(userKey, realName);
        int flag = zxGcsgCommonAttachmentMapper.insert(zxGcsgCommonAttachment);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxGcsgCommonAttachment);
        }
    }

    @Override
    public ResponseEntity updateZxGcsgCommonAttachment(ZxGcsgCommonAttachment zxGcsgCommonAttachment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxGcsgCommonAttachment dbZxGcsgCommonAttachment = zxGcsgCommonAttachmentMapper.selectByPrimaryKey(zxGcsgCommonAttachment.getUid());
        if (dbZxGcsgCommonAttachment != null && StrUtil.isNotEmpty(dbZxGcsgCommonAttachment.getUid())) {
           // 其他表主键
           dbZxGcsgCommonAttachment.setOtherId(zxGcsgCommonAttachment.getOtherId());
           // 业务表类型
           dbZxGcsgCommonAttachment.setOtherType(zxGcsgCommonAttachment.getOtherType());
           // 文件名称
           dbZxGcsgCommonAttachment.setName(zxGcsgCommonAttachment.getName());
           // 大小
           dbZxGcsgCommonAttachment.setSize(zxGcsgCommonAttachment.getSize());
           // 文件格式
           dbZxGcsgCommonAttachment.setType(zxGcsgCommonAttachment.getType());
           // WEB文件地址
           dbZxGcsgCommonAttachment.setUrl(zxGcsgCommonAttachment.getUrl());
           // WEB缩略图文件地址
           dbZxGcsgCommonAttachment.setThumbUrl(zxGcsgCommonAttachment.getThumbUrl());
           // 手机文件地址
           dbZxGcsgCommonAttachment.setMobileUrl(zxGcsgCommonAttachment.getMobileUrl());
           // 手机缩略图文件地址
           dbZxGcsgCommonAttachment.setMobileThumbUrl(zxGcsgCommonAttachment.getMobileThumbUrl());
           // 相对路径文件地址
           dbZxGcsgCommonAttachment.setRelativeUrl(zxGcsgCommonAttachment.getRelativeUrl());
           // 相对路径缩略图文件地址
           dbZxGcsgCommonAttachment.setRelativeThumbUrl(zxGcsgCommonAttachment.getRelativeThumbUrl());
           // 附件分类
           dbZxGcsgCommonAttachment.setFileType(zxGcsgCommonAttachment.getFileType());
           // 备注
           dbZxGcsgCommonAttachment.setRemarks(zxGcsgCommonAttachment.getRemarks());
           // 排序
           dbZxGcsgCommonAttachment.setSort(zxGcsgCommonAttachment.getSort());
           // 共通
           dbZxGcsgCommonAttachment.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCommonAttachmentMapper.updateByPrimaryKey(dbZxGcsgCommonAttachment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxGcsgCommonAttachment);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxGcsgCommonAttachment(List<ZxGcsgCommonAttachment> zxGcsgCommonAttachmentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxGcsgCommonAttachmentList != null && zxGcsgCommonAttachmentList.size() > 0) {
           ZxGcsgCommonAttachment zxGcsgCommonAttachment = new ZxGcsgCommonAttachment();
           zxGcsgCommonAttachment.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCommonAttachmentMapper.batchDeleteUpdateZxGcsgCommonAttachment(zxGcsgCommonAttachmentList, zxGcsgCommonAttachment);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxGcsgCommonAttachmentList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
