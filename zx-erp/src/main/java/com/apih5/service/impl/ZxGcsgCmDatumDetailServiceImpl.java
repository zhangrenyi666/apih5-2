package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxGcsgCmDatumDetailMapper;
import com.apih5.mybatis.pojo.ZxGcsgCmDatumDetail;
import com.apih5.service.ZxGcsgCmDatumDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxGcsgCmDatumDetailService")
public class ZxGcsgCmDatumDetailServiceImpl implements ZxGcsgCmDatumDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxGcsgCmDatumDetailMapper zxGcsgCmDatumDetailMapper;

    @Override
    public ResponseEntity getZxGcsgCmDatumDetailListByCondition(ZxGcsgCmDatumDetail zxGcsgCmDatumDetail) {
        if (zxGcsgCmDatumDetail == null) {
            zxGcsgCmDatumDetail = new ZxGcsgCmDatumDetail();
        }
        // 分页查询
        PageHelper.startPage(zxGcsgCmDatumDetail.getPage(),zxGcsgCmDatumDetail.getLimit());
        // 获取数据
        List<ZxGcsgCmDatumDetail> zxGcsgCmDatumDetailList = zxGcsgCmDatumDetailMapper.selectByZxGcsgCmDatumDetailList(zxGcsgCmDatumDetail);
        // 得到分页信息
        PageInfo<ZxGcsgCmDatumDetail> p = new PageInfo<>(zxGcsgCmDatumDetailList);

        return repEntity.okList(zxGcsgCmDatumDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxGcsgCmDatumDetailDetail(ZxGcsgCmDatumDetail zxGcsgCmDatumDetail) {
        if (zxGcsgCmDatumDetail == null) {
            zxGcsgCmDatumDetail = new ZxGcsgCmDatumDetail();
        }
        // 获取数据
        ZxGcsgCmDatumDetail dbZxGcsgCmDatumDetail = zxGcsgCmDatumDetailMapper.selectByPrimaryKey(zxGcsgCmDatumDetail.getCmDatumDetailId());
        // 数据存在
        if (dbZxGcsgCmDatumDetail != null) {
            return repEntity.ok(dbZxGcsgCmDatumDetail);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxGcsgCmDatumDetail(ZxGcsgCmDatumDetail zxGcsgCmDatumDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxGcsgCmDatumDetail.setCmDatumDetailId(UuidUtil.generate());
        zxGcsgCmDatumDetail.setCreateUserInfo(userKey, realName);
        int flag = zxGcsgCmDatumDetailMapper.insert(zxGcsgCmDatumDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxGcsgCmDatumDetail);
        }
    }

    @Override
    public ResponseEntity updateZxGcsgCmDatumDetail(ZxGcsgCmDatumDetail zxGcsgCmDatumDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxGcsgCmDatumDetail dbZxGcsgCmDatumDetail = zxGcsgCmDatumDetailMapper.selectByPrimaryKey(zxGcsgCmDatumDetail.getCmDatumDetailId());
        if (dbZxGcsgCmDatumDetail != null && StrUtil.isNotEmpty(dbZxGcsgCmDatumDetail.getCmDatumDetailId())) {
           // 主表id
           dbZxGcsgCmDatumDetail.setMasterID(zxGcsgCmDatumDetail.getMasterID());
           // 子系统
           dbZxGcsgCmDatumDetail.setSubSystem(zxGcsgCmDatumDetail.getSubSystem());
           // 模块名
           dbZxGcsgCmDatumDetail.setModuleName(zxGcsgCmDatumDetail.getModuleName());
           // saveDateStr
           dbZxGcsgCmDatumDetail.setSaveDateStr(zxGcsgCmDatumDetail.getSaveDateStr());
           // 附件大小
           dbZxGcsgCmDatumDetail.setFileSize(zxGcsgCmDatumDetail.getFileSize());
           // 附件名
           dbZxGcsgCmDatumDetail.setFileName(zxGcsgCmDatumDetail.getFileName());
           // 是否删除
           dbZxGcsgCmDatumDetail.setDeleted(zxGcsgCmDatumDetail.getDeleted());
           // 最后编辑时间
           dbZxGcsgCmDatumDetail.setEditTime(zxGcsgCmDatumDetail.getEditTime());
           // 请求地址
           dbZxGcsgCmDatumDetail.setUrl(zxGcsgCmDatumDetail.getUrl());
           // 标识
           dbZxGcsgCmDatumDetail.setIdentifyStr(zxGcsgCmDatumDetail.getIdentifyStr());
           // 分公司ID
           dbZxGcsgCmDatumDetail.setComID(zxGcsgCmDatumDetail.getComID());
           // 分公司名称
           dbZxGcsgCmDatumDetail.setComName(zxGcsgCmDatumDetail.getComName());
           // 备注
           dbZxGcsgCmDatumDetail.setRemarks(zxGcsgCmDatumDetail.getRemarks());
           // 排序
           dbZxGcsgCmDatumDetail.setSort(zxGcsgCmDatumDetail.getSort());
           // 共通
           dbZxGcsgCmDatumDetail.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCmDatumDetailMapper.updateByPrimaryKey(dbZxGcsgCmDatumDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxGcsgCmDatumDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxGcsgCmDatumDetail(List<ZxGcsgCmDatumDetail> zxGcsgCmDatumDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxGcsgCmDatumDetailList != null && zxGcsgCmDatumDetailList.size() > 0) {
           ZxGcsgCmDatumDetail zxGcsgCmDatumDetail = new ZxGcsgCmDatumDetail();
           zxGcsgCmDatumDetail.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCmDatumDetailMapper.batchDeleteUpdateZxGcsgCmDatumDetail(zxGcsgCmDatumDetailList, zxGcsgCmDatumDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxGcsgCmDatumDetailList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
