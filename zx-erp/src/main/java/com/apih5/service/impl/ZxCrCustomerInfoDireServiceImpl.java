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
import com.apih5.mybatis.dao.ZxCrCustomerInfoDireMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerInfoDire;
import com.apih5.service.ZxCrCustomerInfoDireService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxCrCustomerInfoDireService")
public class ZxCrCustomerInfoDireServiceImpl implements ZxCrCustomerInfoDireService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrCustomerInfoDireMapper zxCrCustomerInfoDireMapper;

    @Override
    public ResponseEntity getZxCrCustomerInfoDireListByCondition(ZxCrCustomerInfoDire zxCrCustomerInfoDire) {
        if (zxCrCustomerInfoDire == null) {
            zxCrCustomerInfoDire = new ZxCrCustomerInfoDire();
        }
        // 分页查询
        PageHelper.startPage(zxCrCustomerInfoDire.getPage(),zxCrCustomerInfoDire.getLimit());
        // 获取数据
        List<ZxCrCustomerInfoDire> zxCrCustomerInfoDireList = zxCrCustomerInfoDireMapper.selectByZxCrCustomerInfoDireList(zxCrCustomerInfoDire);
        // 得到分页信息
        PageInfo<ZxCrCustomerInfoDire> p = new PageInfo<>(zxCrCustomerInfoDireList);

        if(CollectionUtil.isEmpty(zxCrCustomerInfoDireList)){
            return repEntity.ok(new ArrayList<>());
        }
        
        return repEntity.okList(zxCrCustomerInfoDireList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrCustomerInfoDireDetail(ZxCrCustomerInfoDire zxCrCustomerInfoDire) {
        if (zxCrCustomerInfoDire == null) {
            zxCrCustomerInfoDire = new ZxCrCustomerInfoDire();
        }
        // 获取数据
        ZxCrCustomerInfoDire dbZxCrCustomerInfoDire = zxCrCustomerInfoDireMapper.selectByPrimaryKey(zxCrCustomerInfoDire.getZxCrCustomerInfoDireId());
        // 数据存在
        if (dbZxCrCustomerInfoDire != null) {
            return repEntity.ok(dbZxCrCustomerInfoDire);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrCustomerInfoDire(ZxCrCustomerInfoDire zxCrCustomerInfoDire) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrCustomerInfoDire.setZxCrCustomerInfoDireId(UuidUtil.generate());
        zxCrCustomerInfoDire.setCreateUserInfo(userKey, realName);
        int flag = zxCrCustomerInfoDireMapper.insert(zxCrCustomerInfoDire);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrCustomerInfoDire);
        }
    }

    @Override
    public ResponseEntity updateZxCrCustomerInfoDire(ZxCrCustomerInfoDire zxCrCustomerInfoDire) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrCustomerInfoDire dbZxCrCustomerInfoDire = zxCrCustomerInfoDireMapper.selectByPrimaryKey(zxCrCustomerInfoDire.getZxCrCustomerInfoDireId());
        if (dbZxCrCustomerInfoDire != null && StrUtil.isNotEmpty(dbZxCrCustomerInfoDire.getZxCrCustomerInfoDireId())) {
           // 关联业务id
           dbZxCrCustomerInfoDire.setBisID(zxCrCustomerInfoDire.getBisID());
           // 单位类别ID
           dbZxCrCustomerInfoDire.setCatID(zxCrCustomerInfoDire.getCatID());
           // 单位工程代码
           dbZxCrCustomerInfoDire.setCatCode(zxCrCustomerInfoDire.getCatCode());
           // 单位工程
           dbZxCrCustomerInfoDire.setCatName(zxCrCustomerInfoDire.getCatName());
           // 分类代码ID
           dbZxCrCustomerInfoDire.setResID(zxCrCustomerInfoDire.getResID());
           // 分类代码
           dbZxCrCustomerInfoDire.setResCode(zxCrCustomerInfoDire.getResCode());
           // 分类名称
           dbZxCrCustomerInfoDire.setResName(zxCrCustomerInfoDire.getResName());
           // 备注
           dbZxCrCustomerInfoDire.setRemarks(zxCrCustomerInfoDire.getRemarks());
           // 排序
           dbZxCrCustomerInfoDire.setSort(zxCrCustomerInfoDire.getSort());
           // 共通
           dbZxCrCustomerInfoDire.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerInfoDireMapper.updateByPrimaryKey(dbZxCrCustomerInfoDire);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrCustomerInfoDire);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrCustomerInfoDire(List<ZxCrCustomerInfoDire> zxCrCustomerInfoDireList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrCustomerInfoDireList != null && zxCrCustomerInfoDireList.size() > 0) {
           ZxCrCustomerInfoDire zxCrCustomerInfoDire = new ZxCrCustomerInfoDire();
           zxCrCustomerInfoDire.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerInfoDireMapper.batchDeleteUpdateZxCrCustomerInfoDire(zxCrCustomerInfoDireList, zxCrCustomerInfoDire);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrCustomerInfoDireList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
