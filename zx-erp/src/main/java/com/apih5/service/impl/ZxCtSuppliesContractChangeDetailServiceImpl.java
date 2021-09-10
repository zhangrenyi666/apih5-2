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
import com.apih5.mybatis.dao.ZxCtSuppliesContractChangeDetailMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContractChangeDetail;
import com.apih5.service.ZxCtSuppliesContractChangeDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContractChangeDetailService")
public class ZxCtSuppliesContractChangeDetailServiceImpl implements ZxCtSuppliesContractChangeDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContractChangeDetailMapper zxCtSuppliesContractChangeDetailMapper;

    @Override
    public ResponseEntity getZxCtSuppliesContractChangeDetailListByCondition(ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail) {
        if (zxCtSuppliesContractChangeDetail == null) {
            zxCtSuppliesContractChangeDetail = new ZxCtSuppliesContractChangeDetail();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContractChangeDetail.getPage(),zxCtSuppliesContractChangeDetail.getLimit());
        // 获取数据
        List<ZxCtSuppliesContractChangeDetail> zxCtSuppliesContractChangeDetailList = zxCtSuppliesContractChangeDetailMapper.selectByZxCtSuppliesContractChangeDetailList(zxCtSuppliesContractChangeDetail);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContractChangeDetail> p = new PageInfo<>(zxCtSuppliesContractChangeDetailList);

        return repEntity.okList(zxCtSuppliesContractChangeDetailList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContractChangeDetailDetail(ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail) {
        if (zxCtSuppliesContractChangeDetail == null) {
            zxCtSuppliesContractChangeDetail = new ZxCtSuppliesContractChangeDetail();
        }
        // 获取数据
        ZxCtSuppliesContractChangeDetail dbZxCtSuppliesContractChangeDetail = zxCtSuppliesContractChangeDetailMapper.selectByPrimaryKey(zxCtSuppliesContractChangeDetail.getZxCtSuppliesContractChangeDetailId());
        // 数据存在
        if (dbZxCtSuppliesContractChangeDetail != null) {
            return repEntity.ok(dbZxCtSuppliesContractChangeDetail);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContractChangeDetail(ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContractChangeDetail.setZxCtSuppliesContractChangeDetailId(UuidUtil.generate());
        zxCtSuppliesContractChangeDetail.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContractChangeDetailMapper.insert(zxCtSuppliesContractChangeDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContractChangeDetail);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContractChangeDetail(ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContractChangeDetail dbZxCtSuppliesContractChangeDetail = zxCtSuppliesContractChangeDetailMapper.selectByPrimaryKey(zxCtSuppliesContractChangeDetail.getZxCtSuppliesContractChangeDetailId());
        if (dbZxCtSuppliesContractChangeDetail != null && StrUtil.isNotEmpty(dbZxCtSuppliesContractChangeDetail.getZxCtSuppliesContractChangeDetailId())) {
           // 申报单价
           dbZxCtSuppliesContractChangeDetail.setApplyPrice(zxCtSuppliesContractChangeDetail.getApplyPrice());
           // 申报新增数量
           dbZxCtSuppliesContractChangeDetail.setApplyAddQty(zxCtSuppliesContractChangeDetail.getApplyAddQty());
           // 申报数量
           dbZxCtSuppliesContractChangeDetail.setApplyQty(zxCtSuppliesContractChangeDetail.getApplyQty());
           // 批复数量
           dbZxCtSuppliesContractChangeDetail.setReplyQty(zxCtSuppliesContractChangeDetail.getReplyQty());
           // 批复单价
           dbZxCtSuppliesContractChangeDetail.setReplyPrice(zxCtSuppliesContractChangeDetail.getReplyPrice());
           // 标的类型
           dbZxCtSuppliesContractChangeDetail.setBidType(zxCtSuppliesContractChangeDetail.getBidType());
           // 变更类型
           dbZxCtSuppliesContractChangeDetail.setAlterType(zxCtSuppliesContractChangeDetail.getAlterType());
           // 变更ID
           dbZxCtSuppliesContractChangeDetail.setAlterID(zxCtSuppliesContractChangeDetail.getAlterID());
           // 备注
           dbZxCtSuppliesContractChangeDetail.setRemarks(zxCtSuppliesContractChangeDetail.getRemarks());
           // 排序
           dbZxCtSuppliesContractChangeDetail.setSort(zxCtSuppliesContractChangeDetail.getSort());
           // 共通
           dbZxCtSuppliesContractChangeDetail.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractChangeDetailMapper.updateByPrimaryKey(dbZxCtSuppliesContractChangeDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContractChangeDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContractChangeDetail(List<ZxCtSuppliesContractChangeDetail> zxCtSuppliesContractChangeDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContractChangeDetailList != null && zxCtSuppliesContractChangeDetailList.size() > 0) {
           ZxCtSuppliesContractChangeDetail zxCtSuppliesContractChangeDetail = new ZxCtSuppliesContractChangeDetail();
           zxCtSuppliesContractChangeDetail.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractChangeDetailMapper.batchDeleteUpdateZxCtSuppliesContractChangeDetail(zxCtSuppliesContractChangeDetailList, zxCtSuppliesContractChangeDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContractChangeDetailList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
