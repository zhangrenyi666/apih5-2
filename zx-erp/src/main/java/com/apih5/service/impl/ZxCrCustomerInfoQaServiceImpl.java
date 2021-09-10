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
import com.apih5.mybatis.dao.ZxCrCustomerInfoQaMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;
import com.apih5.mybatis.pojo.ZxCrCustomerInfoQa;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCrCustomerInfoQaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxCrCustomerInfoQaService")
public class ZxCrCustomerInfoQaServiceImpl implements ZxCrCustomerInfoQaService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrCustomerInfoQaMapper zxCrCustomerInfoQaMapper;

    @Override
    public ResponseEntity getZxCrCustomerInfoQaListByCondition(ZxCrCustomerInfoQa zxCrCustomerInfoQa) {
        if (zxCrCustomerInfoQa == null) {
            zxCrCustomerInfoQa = new ZxCrCustomerInfoQa();
        }
        // 分页查询
        PageHelper.startPage(zxCrCustomerInfoQa.getPage(),zxCrCustomerInfoQa.getLimit());
        
        zxCrCustomerInfoQa.setBisID(zxCrCustomerInfoQa.getZxCrCustomerInfoId());
        // 获取数据
        List<ZxCrCustomerInfoQa> zxCrCustomerInfoQaList = zxCrCustomerInfoQaMapper.selectByZxCrCustomerInfoQaList(zxCrCustomerInfoQa);
        
        if(CollectionUtil.isEmpty(zxCrCustomerInfoQaList)){
            return repEntity.ok(new ArrayList<>());
        }
        
        // 得到分页信息
        PageInfo<ZxCrCustomerInfoQa> p = new PageInfo<>(zxCrCustomerInfoQaList);

        return repEntity.okList(zxCrCustomerInfoQaList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrCustomerInfoQaDetail(ZxCrCustomerInfoQa zxCrCustomerInfoQa) {
        if (zxCrCustomerInfoQa == null) {
            zxCrCustomerInfoQa = new ZxCrCustomerInfoQa();
        }
        // 获取数据
        ZxCrCustomerInfoQa dbZxCrCustomerInfoQa = zxCrCustomerInfoQaMapper.selectByPrimaryKey(zxCrCustomerInfoQa.getID());
        // 数据存在
        if (dbZxCrCustomerInfoQa != null) {
            return repEntity.ok(dbZxCrCustomerInfoQa);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrCustomerInfoQa(ZxCrCustomerInfoQa zxCrCustomerInfoQa) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrCustomerInfoQa.setID(UuidUtil.generate());
        zxCrCustomerInfoQa.setCreateUserInfo(userKey, realName);
        int flag = zxCrCustomerInfoQaMapper.insert(zxCrCustomerInfoQa);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrCustomerInfoQa);
        }
    }

    @Override
    public ResponseEntity updateZxCrCustomerInfoQa(ZxCrCustomerInfoQa zxCrCustomerInfoQa) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrCustomerInfoQa dbZxCrCustomerInfoQa = zxCrCustomerInfoQaMapper.selectByPrimaryKey(zxCrCustomerInfoQa.getID());
        if (dbZxCrCustomerInfoQa != null && StrUtil.isNotEmpty(dbZxCrCustomerInfoQa.getID())) {
           // 协作单位id
           dbZxCrCustomerInfoQa.setBisID(zxCrCustomerInfoQa.getBisID());
           // 附件id
           dbZxCrCustomerInfoQa.setDatumID(zxCrCustomerInfoQa.getDatumID());
           // 供应商编号
           dbZxCrCustomerInfoQa.setCustomerNo(zxCrCustomerInfoQa.getCustomerNo());
           // 资质名称
           dbZxCrCustomerInfoQa.setQaName(zxCrCustomerInfoQa.getQaName());
           // 等级
           dbZxCrCustomerInfoQa.setQuLevel(zxCrCustomerInfoQa.getQuLevel());
           // comID
           dbZxCrCustomerInfoQa.setComID(zxCrCustomerInfoQa.getComID());
           // comName
           dbZxCrCustomerInfoQa.setComName(zxCrCustomerInfoQa.getComName());
           // comOrders
           dbZxCrCustomerInfoQa.setComOrders(zxCrCustomerInfoQa.getComOrders());
           // 序号
           dbZxCrCustomerInfoQa.setOrderStr(zxCrCustomerInfoQa.getOrderStr());
           // 编辑时间
           dbZxCrCustomerInfoQa.setEditTime(zxCrCustomerInfoQa.getEditTime());
           // 资质名称ID
           dbZxCrCustomerInfoQa.setQaID(zxCrCustomerInfoQa.getQaID());
           // 等级ID
           dbZxCrCustomerInfoQa.setQuID(zxCrCustomerInfoQa.getQuID());
           // 共通
           dbZxCrCustomerInfoQa.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerInfoQaMapper.updateByPrimaryKey(dbZxCrCustomerInfoQa);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrCustomerInfoQa);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoQa(List<ZxCrCustomerInfoQa> zxCrCustomerInfoQaList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrCustomerInfoQaList != null && zxCrCustomerInfoQaList.size() > 0) {
           ZxCrCustomerInfoQa zxCrCustomerInfoQa = new ZxCrCustomerInfoQa();
           zxCrCustomerInfoQa.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerInfoQaMapper.batchDeleteUpdateZxCrCustomerInfoQa(zxCrCustomerInfoQaList, zxCrCustomerInfoQa);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrCustomerInfoQaList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓ 
}
