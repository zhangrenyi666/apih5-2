package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCmFilterOrgMapper;
import com.apih5.mybatis.pojo.ZxCmFilterOrg;
import com.apih5.service.ZxCmFilterOrgService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCmFilterOrgService")
public class ZxCmFilterOrgServiceImpl implements ZxCmFilterOrgService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCmFilterOrgMapper zxCmFilterOrgMapper;

    @Override
    public ResponseEntity getZxCmFilterOrgListByCondition(ZxCmFilterOrg zxCmFilterOrg) {
        if (zxCmFilterOrg == null) {
            zxCmFilterOrg = new ZxCmFilterOrg();
        }
        // 分页查询
        PageHelper.startPage(zxCmFilterOrg.getPage(),zxCmFilterOrg.getLimit());
        // 获取数据
        List<ZxCmFilterOrg> zxCmFilterOrgList = zxCmFilterOrgMapper.selectByZxCmFilterOrgList(zxCmFilterOrg);
        // 得到分页信息
        PageInfo<ZxCmFilterOrg> p = new PageInfo<>(zxCmFilterOrgList);

        return repEntity.okList(zxCmFilterOrgList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCmFilterOrgDetail(ZxCmFilterOrg zxCmFilterOrg) {
        if (zxCmFilterOrg == null) {
            zxCmFilterOrg = new ZxCmFilterOrg();
        }
        // 获取数据
        ZxCmFilterOrg dbZxCmFilterOrg = zxCmFilterOrgMapper.selectByPrimaryKey(zxCmFilterOrg.getFilterOrgId());
        // 数据存在
        if (dbZxCmFilterOrg != null) {
            return repEntity.ok(dbZxCmFilterOrg);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCmFilterOrg(ZxCmFilterOrg zxCmFilterOrg) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCmFilterOrg.setFilterOrgId(UuidUtil.generate());
        zxCmFilterOrg.setCreateUserInfo(userKey, realName);
        int flag = zxCmFilterOrgMapper.insert(zxCmFilterOrg);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCmFilterOrg);
        }
    }

    @Override
    public ResponseEntity updateZxCmFilterOrg(ZxCmFilterOrg zxCmFilterOrg) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCmFilterOrg dbZxCmFilterOrg = zxCmFilterOrgMapper.selectByPrimaryKey(zxCmFilterOrg.getFilterOrgId());
        if (dbZxCmFilterOrg != null && StrUtil.isNotEmpty(dbZxCmFilterOrg.getFilterOrgId())) {
           // 项目ID
           dbZxCmFilterOrg.setOrgID(zxCmFilterOrg.getOrgID());
           // 项目名称
           dbZxCmFilterOrg.setOrgName(zxCmFilterOrg.getOrgName());
           // 业务类型
           dbZxCmFilterOrg.setBusinessType(zxCmFilterOrg.getBusinessType());
           // 最后编辑时间
           dbZxCmFilterOrg.setEditTime(zxCmFilterOrg.getEditTime());
           // 备注
           dbZxCmFilterOrg.setRemark(zxCmFilterOrg.getRemark());
           // 排序
           dbZxCmFilterOrg.setSort(zxCmFilterOrg.getSort());
           // 共通
           dbZxCmFilterOrg.setModifyUserInfo(userKey, realName);
           flag = zxCmFilterOrgMapper.updateByPrimaryKey(dbZxCmFilterOrg);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCmFilterOrg);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCmFilterOrg(List<ZxCmFilterOrg> zxCmFilterOrgList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCmFilterOrgList != null && zxCmFilterOrgList.size() > 0) {
           ZxCmFilterOrg zxCmFilterOrg = new ZxCmFilterOrg();
           zxCmFilterOrg.setModifyUserInfo(userKey, realName);
           flag = zxCmFilterOrgMapper.batchDeleteUpdateZxCmFilterOrg(zxCmFilterOrgList, zxCmFilterOrg);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCmFilterOrgList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
