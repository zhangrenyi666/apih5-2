package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrJzItemBaseMapper;
import com.apih5.mybatis.pojo.ZxCtContrJzItemBase;
import com.apih5.service.ZxCtContrJzItemBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtContrJzItemBaseService")
public class ZxCtContrJzItemBaseServiceImpl implements ZxCtContrJzItemBaseService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrJzItemBaseMapper zxCtContrJzItemBaseMapper;

    @Override
    public ResponseEntity getZxCtContrJzItemBaseListByCondition(ZxCtContrJzItemBase zxCtContrJzItemBase) {
        if (zxCtContrJzItemBase == null) {
            zxCtContrJzItemBase = new ZxCtContrJzItemBase();
        }
        // 分页查询
        PageHelper.startPage(zxCtContrJzItemBase.getPage(),zxCtContrJzItemBase.getLimit());
        // 获取数据
        List<ZxCtContrJzItemBase> zxCtContrJzItemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(zxCtContrJzItemBase);
        // 得到分页信息
        PageInfo<ZxCtContrJzItemBase> p = new PageInfo<>(zxCtContrJzItemBaseList);

        return repEntity.okList(zxCtContrJzItemBaseList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrJzItemBaseDetail(ZxCtContrJzItemBase zxCtContrJzItemBase) {
        if (zxCtContrJzItemBase == null) {
            zxCtContrJzItemBase = new ZxCtContrJzItemBase();
        }
        // 获取数据
        ZxCtContrJzItemBase dbZxCtContrJzItemBase = zxCtContrJzItemBaseMapper.selectByPrimaryKey(zxCtContrJzItemBase.getId());
        // 数据存在
        if (dbZxCtContrJzItemBase != null) {
            return repEntity.ok(dbZxCtContrJzItemBase);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrJzItemBase(ZxCtContrJzItemBase zxCtContrJzItemBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContrJzItemBase.setId(UuidUtil.generate());
        
        if(StrUtil.isNotEmpty(zxCtContrJzItemBase.getMainID())) {
        	
        }else {
        	zxCtContrJzItemBase.setMainID("0");
        }
        zxCtContrJzItemBase.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrJzItemBaseMapper.insert(zxCtContrJzItemBase);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrJzItemBase);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrJzItemBase(ZxCtContrJzItemBase zxCtContrJzItemBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrJzItemBase dbZxCtContrJzItemBase = zxCtContrJzItemBaseMapper.selectByPrimaryKey(zxCtContrJzItemBase.getId());
        if (dbZxCtContrJzItemBase != null && StrUtil.isNotEmpty(dbZxCtContrJzItemBase.getId())) {
           // 项目
           dbZxCtContrJzItemBase.setSubType(zxCtContrJzItemBase.getSubType());
           // 项目内容
           dbZxCtContrJzItemBase.setSubDetail(zxCtContrJzItemBase.getSubDetail());
           // 排序
           dbZxCtContrJzItemBase.setOrderStr(zxCtContrJzItemBase.getOrderStr());
           // 是否初始化数据
           dbZxCtContrJzItemBase.setIsInit(zxCtContrJzItemBase.getIsInit());
           // 项目子类
           dbZxCtContrJzItemBase.setSubType2(zxCtContrJzItemBase.getSubType2());
           // 项目内容明细
           dbZxCtContrJzItemBase.setSubDetail2(zxCtContrJzItemBase.getSubDetail2());
           // 行标识
           dbZxCtContrJzItemBase.setHangCode(zxCtContrJzItemBase.getHangCode());
           // 行类型
           dbZxCtContrJzItemBase.setIsHuizong(zxCtContrJzItemBase.getIsHuizong());
           // 汇总到哪一行
           dbZxCtContrJzItemBase.setHuizongCode(zxCtContrJzItemBase.getHuizongCode());
           // 是否减项
           dbZxCtContrJzItemBase.setIsReduce(zxCtContrJzItemBase.getIsReduce());
           // 备注
           dbZxCtContrJzItemBase.setRemarks(zxCtContrJzItemBase.getRemarks());
           // 共通
           dbZxCtContrJzItemBase.setModifyUserInfo(userKey, realName);
           flag = zxCtContrJzItemBaseMapper.updateByPrimaryKey(dbZxCtContrJzItemBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtContrJzItemBase);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrJzItemBase(List<ZxCtContrJzItemBase> zxCtContrJzItemBaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrJzItemBaseList != null && zxCtContrJzItemBaseList.size() > 0) {
           ZxCtContrJzItemBase zxCtContrJzItemBase = new ZxCtContrJzItemBase();
           zxCtContrJzItemBase.setModifyUserInfo(userKey, realName);
           flag = zxCtContrJzItemBaseMapper.batchDeleteUpdateZxCtContrJzItemBase(zxCtContrJzItemBaseList, zxCtContrJzItemBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtContrJzItemBaseList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
