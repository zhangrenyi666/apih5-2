package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrCustomerInfoDatumMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerInfoDatum;
import com.apih5.service.ZxCrCustomerInfoDatumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxCrCustomerInfoDatumService")
public class ZxCrCustomerInfoDatumServiceImpl implements ZxCrCustomerInfoDatumService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrCustomerInfoDatumMapper zxCrCustomerInfoDatumMapper;

    @Override
    public ResponseEntity getZxCrCustomerInfoDatumListByCondition(ZxCrCustomerInfoDatum zxCrCustomerInfoDatum) {
        if (zxCrCustomerInfoDatum == null) {
            zxCrCustomerInfoDatum = new ZxCrCustomerInfoDatum();
        }
        // 分页查询
        PageHelper.startPage(zxCrCustomerInfoDatum.getPage(),zxCrCustomerInfoDatum.getLimit());
        
        zxCrCustomerInfoDatum.setBisID(zxCrCustomerInfoDatum.getZxCrCustomerInfoId());
        
        // 获取数据
        List<ZxCrCustomerInfoDatum> zxCrCustomerInfoDatumList = zxCrCustomerInfoDatumMapper.selectByZxCrCustomerInfoDatumList(zxCrCustomerInfoDatum);

        if(CollectionUtil.isEmpty(zxCrCustomerInfoDatumList)){
            return repEntity.ok(new ArrayList<>());
        }
        
        // 得到分页信息
        PageInfo<ZxCrCustomerInfoDatum> p = new PageInfo<>(zxCrCustomerInfoDatumList);

        return repEntity.okList(zxCrCustomerInfoDatumList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrCustomerInfoDatumDetail(ZxCrCustomerInfoDatum zxCrCustomerInfoDatum) {
        if (zxCrCustomerInfoDatum == null) {
            zxCrCustomerInfoDatum = new ZxCrCustomerInfoDatum();
        }
        // 获取数据
        ZxCrCustomerInfoDatum dbZxCrCustomerInfoDatum = zxCrCustomerInfoDatumMapper.selectByPrimaryKey(zxCrCustomerInfoDatum.getZxCrCustomerInfoDatumId());
        // 数据存在
        if (dbZxCrCustomerInfoDatum != null) {
            return repEntity.ok(dbZxCrCustomerInfoDatum);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrCustomerInfoDatum(ZxCrCustomerInfoDatum zxCrCustomerInfoDatum) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrCustomerInfoDatum.setZxCrCustomerInfoDatumId(UuidUtil.generate());
        zxCrCustomerInfoDatum.setCreateUserInfo(userKey, realName);
        int flag = zxCrCustomerInfoDatumMapper.insert(zxCrCustomerInfoDatum);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrCustomerInfoDatum);
        }
    }

    @Override
    public ResponseEntity updateZxCrCustomerInfoDatum(ZxCrCustomerInfoDatum zxCrCustomerInfoDatum) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrCustomerInfoDatum dbZxCrCustomerInfoDatum = zxCrCustomerInfoDatumMapper.selectByPrimaryKey(zxCrCustomerInfoDatum.getZxCrCustomerInfoDatumId());
        if (dbZxCrCustomerInfoDatum != null && StrUtil.isNotEmpty(dbZxCrCustomerInfoDatum.getZxCrCustomerInfoDatumId())) {
           // 关联业务id
           dbZxCrCustomerInfoDatum.setBisID(zxCrCustomerInfoDatum.getBisID());
           // 名称
           dbZxCrCustomerInfoDatum.setDatumName(zxCrCustomerInfoDatum.getDatumName());
           // 编号
           dbZxCrCustomerInfoDatum.setDatumNo(zxCrCustomerInfoDatum.getDatumNo());
           // 附件id
           dbZxCrCustomerInfoDatum.setDatumID(zxCrCustomerInfoDatum.getDatumID());
           // 编辑时间
           dbZxCrCustomerInfoDatum.setEditTime(zxCrCustomerInfoDatum.getEditTime());
           // comID
           dbZxCrCustomerInfoDatum.setComID(zxCrCustomerInfoDatum.getComID());
           // comName
           dbZxCrCustomerInfoDatum.setComName(zxCrCustomerInfoDatum.getComName());
           // comOrders
           dbZxCrCustomerInfoDatum.setComOrders(zxCrCustomerInfoDatum.getComOrders());
           // 排序
           dbZxCrCustomerInfoDatum.setOrderStr(zxCrCustomerInfoDatum.getOrderStr());
           // 备注
           dbZxCrCustomerInfoDatum.setRemarks(zxCrCustomerInfoDatum.getRemarks());
           // 排序
           dbZxCrCustomerInfoDatum.setSort(zxCrCustomerInfoDatum.getSort());
           // 共通
           dbZxCrCustomerInfoDatum.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerInfoDatumMapper.updateByPrimaryKey(dbZxCrCustomerInfoDatum);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrCustomerInfoDatum);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoDatum(List<ZxCrCustomerInfoDatum> zxCrCustomerInfoDatumList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrCustomerInfoDatumList != null && zxCrCustomerInfoDatumList.size() > 0) {
           ZxCrCustomerInfoDatum zxCrCustomerInfoDatum = new ZxCrCustomerInfoDatum();
           zxCrCustomerInfoDatum.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerInfoDatumMapper.batchDeleteUpdateZxCrCustomerInfoDatum(zxCrCustomerInfoDatumList, zxCrCustomerInfoDatum);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrCustomerInfoDatumList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
