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
import com.apih5.mybatis.dao.ZxCrCustomerHonorMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerHonor;
import com.apih5.service.ZxCrCustomerHonorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxCrCustomerHonorService")
public class ZxCrCustomerHonorServiceImpl implements ZxCrCustomerHonorService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrCustomerHonorMapper zxCrCustomerHonorMapper;

    @Override
    public ResponseEntity getZxCrCustomerHonorListByCondition(ZxCrCustomerHonor zxCrCustomerHonor) {
        if (zxCrCustomerHonor == null) {
            zxCrCustomerHonor = new ZxCrCustomerHonor();
        }
        // 分页查询
        PageHelper.startPage(zxCrCustomerHonor.getPage(),zxCrCustomerHonor.getLimit());
        
        zxCrCustomerHonor.setBisID(zxCrCustomerHonor.getZxCrCustomerInfoId());
        // 获取数据
        List<ZxCrCustomerHonor> zxCrCustomerHonorList = zxCrCustomerHonorMapper.selectByZxCrCustomerHonorList(zxCrCustomerHonor);
        // 得到分页信息
        PageInfo<ZxCrCustomerHonor> p = new PageInfo<>(zxCrCustomerHonorList);

        if(CollectionUtil.isEmpty(zxCrCustomerHonorList)){
            return repEntity.ok(new ArrayList<>());
        }

        return repEntity.okList(zxCrCustomerHonorList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrCustomerHonorDetail(ZxCrCustomerHonor zxCrCustomerHonor) {
        if (zxCrCustomerHonor == null) {
            zxCrCustomerHonor = new ZxCrCustomerHonor();
        }
        // 获取数据
        ZxCrCustomerHonor dbZxCrCustomerHonor = zxCrCustomerHonorMapper.selectByPrimaryKey(zxCrCustomerHonor.getZxCrCustomerHonorId());
        // 数据存在
        if (dbZxCrCustomerHonor != null) {
            return repEntity.ok(dbZxCrCustomerHonor);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrCustomerHonor(ZxCrCustomerHonor zxCrCustomerHonor) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrCustomerHonor.setZxCrCustomerHonorId(UuidUtil.generate());
        zxCrCustomerHonor.setCreateUserInfo(userKey, realName);
        int flag = zxCrCustomerHonorMapper.insert(zxCrCustomerHonor);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrCustomerHonor);
        }
    }

    @Override
    public ResponseEntity updateZxCrCustomerHonor(ZxCrCustomerHonor zxCrCustomerHonor) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrCustomerHonor dbZxCrCustomerHonor = zxCrCustomerHonorMapper.selectByPrimaryKey(zxCrCustomerHonor.getZxCrCustomerHonorId());
        if (dbZxCrCustomerHonor != null && StrUtil.isNotEmpty(dbZxCrCustomerHonor.getZxCrCustomerHonorId())) {
           // 协作单位id
           dbZxCrCustomerHonor.setBisID(zxCrCustomerHonor.getBisID());
           // 业绩和荣誉
           dbZxCrCustomerHonor.setHonor(zxCrCustomerHonor.getHonor());
           // 附件id
           dbZxCrCustomerHonor.setDatumID(zxCrCustomerHonor.getDatumID());
           // 编辑时间
           dbZxCrCustomerHonor.setEditTime(zxCrCustomerHonor.getEditTime());
           // comID
           dbZxCrCustomerHonor.setComID(zxCrCustomerHonor.getComID());
           // comName
           dbZxCrCustomerHonor.setComName(zxCrCustomerHonor.getComName());
           // comOrders
           dbZxCrCustomerHonor.setComOrders(zxCrCustomerHonor.getComOrders());
           // 序号
           dbZxCrCustomerHonor.setOrderStr(zxCrCustomerHonor.getOrderStr());
           // 编辑日期
           dbZxCrCustomerHonor.setPrepareDate(zxCrCustomerHonor.getPrepareDate());
           // 已建工程项目名称
           dbZxCrCustomerHonor.setProjectName(zxCrCustomerHonor.getProjectName());
           // 合同开始时间
           dbZxCrCustomerHonor.setContrDateS(zxCrCustomerHonor.getContrDateS());
           // 合同结束时间
           dbZxCrCustomerHonor.setContrDateE(zxCrCustomerHonor.getContrDateE());
           // 工程类别
           dbZxCrCustomerHonor.setProjectType(zxCrCustomerHonor.getProjectType());
           // 合同金额
           dbZxCrCustomerHonor.setContrAmount(zxCrCustomerHonor.getContrAmount());
           // 工程数量
           dbZxCrCustomerHonor.setWorkNum(zxCrCustomerHonor.getWorkNum());
           // 单位
           dbZxCrCustomerHonor.setWorkUnit(zxCrCustomerHonor.getWorkUnit());
           // 录入时间
           dbZxCrCustomerHonor.setInputDate(zxCrCustomerHonor.getInputDate());
           // 来源
           dbZxCrCustomerHonor.setDataFrom(zxCrCustomerHonor.getDataFrom());
           // 工程类别ID
           dbZxCrCustomerHonor.setProjectTypeID(zxCrCustomerHonor.getProjectTypeID());
           // 备注
           dbZxCrCustomerHonor.setRemarks(zxCrCustomerHonor.getRemarks());
           // 排序
           dbZxCrCustomerHonor.setSort(zxCrCustomerHonor.getSort());
           // 共通
           dbZxCrCustomerHonor.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerHonorMapper.updateByPrimaryKey(dbZxCrCustomerHonor);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrCustomerHonor);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrCustomerHonor(List<ZxCrCustomerHonor> zxCrCustomerHonorList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrCustomerHonorList != null && zxCrCustomerHonorList.size() > 0) {
           ZxCrCustomerHonor zxCrCustomerHonor = new ZxCrCustomerHonor();
           zxCrCustomerHonor.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerHonorMapper.batchDeleteUpdateZxCrCustomerHonor(zxCrCustomerHonorList, zxCrCustomerHonor);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrCustomerHonorList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
