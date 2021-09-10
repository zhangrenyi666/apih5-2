package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtContrDetailAttrMapper;
import com.apih5.mybatis.pojo.ZxCtContrDetailAttr;
import com.apih5.service.ZxCtContrDetailAttrService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtContrDetailAttrService")
public class ZxCtContrDetailAttrServiceImpl implements ZxCtContrDetailAttrService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrDetailAttrMapper zxCtContrDetailAttrMapper;
    
	@Override
    public ResponseEntity getZxCtContrDetailAttrListByCondition(ZxCtContrDetailAttr zxCtContrDetailAttr) {
        if (zxCtContrDetailAttr == null) {
            zxCtContrDetailAttr = new ZxCtContrDetailAttr();
        }
        // 分页查询
        PageHelper.startPage(zxCtContrDetailAttr.getPage(),zxCtContrDetailAttr.getLimit());
        // 获取数据
        List<ZxCtContrDetailAttr> zxCtContrDetailAttrList = zxCtContrDetailAttrMapper.selectByZxCtContrDetailAttrList(zxCtContrDetailAttr);
        // 得到分页信息
        PageInfo<ZxCtContrDetailAttr> p = new PageInfo<>(zxCtContrDetailAttrList);

        return repEntity.okList(zxCtContrDetailAttrList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrDetailAttrDetail(ZxCtContrDetailAttr zxCtContrDetailAttr) {
        if (zxCtContrDetailAttr == null) {
            zxCtContrDetailAttr = new ZxCtContrDetailAttr();
        }
        // 获取数据
        ZxCtContrDetailAttr dbZxCtContrDetailAttr = zxCtContrDetailAttrMapper.selectByPrimaryKey(zxCtContrDetailAttr.getId());
        // 数据存在
        if (dbZxCtContrDetailAttr != null) {
            return repEntity.ok(dbZxCtContrDetailAttr);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrDetailAttr(ZxCtContrDetailAttr zxCtContrDetailAttr) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContrDetailAttr.setId(UuidUtil.generate());
        zxCtContrDetailAttr.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrDetailAttrMapper.insert(zxCtContrDetailAttr);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrDetailAttr);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrDetailAttr(ZxCtContrDetailAttr zxCtContrDetailAttr) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrDetailAttr dbZxCtContrDetailAttr = zxCtContrDetailAttrMapper.selectByPrimaryKey(zxCtContrDetailAttr.getId());
        if (dbZxCtContrDetailAttr != null && StrUtil.isNotEmpty(dbZxCtContrDetailAttr.getId())) {
           // 合同条款ID
           dbZxCtContrDetailAttr.setContrDetailID(zxCtContrDetailAttr.getContrDetailID());
           // 保证金
           dbZxCtContrDetailAttr.setPledgeMoney(zxCtContrDetailAttr.getPledgeMoney());
           // 类型
           dbZxCtContrDetailAttr.setType(zxCtContrDetailAttr.getType());
           // 金额
           dbZxCtContrDetailAttr.setMoney(zxCtContrDetailAttr.getMoney());
           // 返还条件
           dbZxCtContrDetailAttr.setBackCondition(zxCtContrDetailAttr.getBackCondition());
           // 期限
           dbZxCtContrDetailAttr.setTimeLimit(zxCtContrDetailAttr.getTimeLimit());
           // 备注
           dbZxCtContrDetailAttr.setRemarks(zxCtContrDetailAttr.getRemarks());
           // 排序
           dbZxCtContrDetailAttr.setSort(zxCtContrDetailAttr.getSort());
           // 共通
           dbZxCtContrDetailAttr.setModifyUserInfo(userKey, realName);
           flag = zxCtContrDetailAttrMapper.updateByPrimaryKey(dbZxCtContrDetailAttr);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtContrDetailAttr);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrDetailAttr(List<ZxCtContrDetailAttr> zxCtContrDetailAttrList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrDetailAttrList != null && zxCtContrDetailAttrList.size() > 0) {
           ZxCtContrDetailAttr zxCtContrDetailAttr = new ZxCtContrDetailAttr();
           zxCtContrDetailAttr.setModifyUserInfo(userKey, realName);
           flag = zxCtContrDetailAttrMapper.batchDeleteUpdateZxCtContrDetailAttr(zxCtContrDetailAttrList, zxCtContrDetailAttr);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtContrDetailAttrList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity deleteAllZxCtContrDetailAttr(ZxCtContrDetailAttr zxCtContrDetailAttr) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrDetailAttr != null && StrUtil.isNotEmpty(zxCtContrDetailAttr.getContrDetailID())) {
           zxCtContrDetailAttr.setModifyUserInfo(userKey, realName);
           flag = zxCtContrDetailAttrMapper.deleteAllZxCtContrDetailAttr(zxCtContrDetailAttr);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorDelete();
        } else {
            return repEntity.ok("删除成功！");
        }
    }
    
}
