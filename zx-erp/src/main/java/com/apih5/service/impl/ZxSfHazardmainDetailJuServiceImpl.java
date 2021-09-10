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
import com.apih5.mybatis.dao.ZxSfHazardmainDetailJuMapper;
import com.apih5.mybatis.pojo.ZxSfHazardmainDetailJu;
import com.apih5.service.ZxSfHazardmainDetailJuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfHazardmainDetailJuService")
public class ZxSfHazardmainDetailJuServiceImpl implements ZxSfHazardmainDetailJuService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfHazardmainDetailJuMapper zxSfHazardmainDetailJuMapper;

    @Override
    public ResponseEntity getZxSfHazardmainDetailJuListByCondition(ZxSfHazardmainDetailJu zxSfHazardmainDetailJu) {
        if (zxSfHazardmainDetailJu == null) {
            zxSfHazardmainDetailJu = new ZxSfHazardmainDetailJu();
        }
        // 分页查询
        PageHelper.startPage(zxSfHazardmainDetailJu.getPage(),zxSfHazardmainDetailJu.getLimit());
        // 获取数据
        List<ZxSfHazardmainDetailJu> zxSfHazardmainDetailJuList = zxSfHazardmainDetailJuMapper.selectByZxSfHazardmainDetailJuList(zxSfHazardmainDetailJu);
        // 得到分页信息
        PageInfo<ZxSfHazardmainDetailJu> p = new PageInfo<>(zxSfHazardmainDetailJuList);

        return repEntity.okList(zxSfHazardmainDetailJuList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfHazardmainDetailJuDetail(ZxSfHazardmainDetailJu zxSfHazardmainDetailJu) {
        if (zxSfHazardmainDetailJu == null) {
            zxSfHazardmainDetailJu = new ZxSfHazardmainDetailJu();
        }
        // 获取数据
        ZxSfHazardmainDetailJu dbZxSfHazardmainDetailJu = zxSfHazardmainDetailJuMapper.selectByPrimaryKey(zxSfHazardmainDetailJu.getZxSfHazardmainDetailJuId());
        // 数据存在
        if (dbZxSfHazardmainDetailJu != null) {
            return repEntity.ok(dbZxSfHazardmainDetailJu);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfHazardmainDetailJu(ZxSfHazardmainDetailJu zxSfHazardmainDetailJu) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfHazardmainDetailJu.setZxSfHazardmainDetailJuId(UuidUtil.generate());
        zxSfHazardmainDetailJu.setCreateUserInfo(userKey, realName);
        int flag = zxSfHazardmainDetailJuMapper.insert(zxSfHazardmainDetailJu);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfHazardmainDetailJu);
        }
    }

    @Override
    public ResponseEntity updateZxSfHazardmainDetailJu(ZxSfHazardmainDetailJu zxSfHazardmainDetailJu) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfHazardmainDetailJu dbZxSfHazardmainDetailJu = zxSfHazardmainDetailJuMapper.selectByPrimaryKey(zxSfHazardmainDetailJu.getZxSfHazardmainDetailJuId());
        if (dbZxSfHazardmainDetailJu != null && StrUtil.isNotEmpty(dbZxSfHazardmainDetailJu.getZxSfHazardmainDetailJuId())) {
           // 主表id
           dbZxSfHazardmainDetailJu.setMainId(zxSfHazardmainDetailJu.getMainId());
           // 所属机构
           dbZxSfHazardmainDetailJu.setOrgId(zxSfHazardmainDetailJu.getOrgId());
           // 所属机构名称
           dbZxSfHazardmainDetailJu.setOrgName(zxSfHazardmainDetailJu.getOrgName());
           // 编制人
           dbZxSfHazardmainDetailJu.setPreparer(zxSfHazardmainDetailJu.getPreparer());
           // 过程
           dbZxSfHazardmainDetailJu.setProArea(zxSfHazardmainDetailJu.getProArea());
           // 行为(活动)或设备
           dbZxSfHazardmainDetailJu.setDoing(zxSfHazardmainDetailJu.getDoing());
           // 危险因素
           dbZxSfHazardmainDetailJu.setRiskFactors(zxSfHazardmainDetailJu.getRiskFactors());
           // 可能导致的伤害（事故）
           dbZxSfHazardmainDetailJu.setAccident(zxSfHazardmainDetailJu.getAccident());
           // 作业条件危险性评价(L)
           dbZxSfHazardmainDetailJu.setLee(zxSfHazardmainDetailJu.getLee());
           // 作业条件危险性评价(e)
           dbZxSfHazardmainDetailJu.setBee(zxSfHazardmainDetailJu.getBee());
           // 作业条件危险性评价(c)
           dbZxSfHazardmainDetailJu.setCee(zxSfHazardmainDetailJu.getCee());
           // 作业条件危险性评价(d)
           dbZxSfHazardmainDetailJu.setDee(zxSfHazardmainDetailJu.getDee());
           // 风险等级
           dbZxSfHazardmainDetailJu.setRiskLevel(zxSfHazardmainDetailJu.getRiskLevel());
           // 备注
           dbZxSfHazardmainDetailJu.setRemarks(zxSfHazardmainDetailJu.getRemarks());
           // 排序
           dbZxSfHazardmainDetailJu.setSort(zxSfHazardmainDetailJu.getSort());
           // 共通
           dbZxSfHazardmainDetailJu.setModifyUserInfo(userKey, realName);
           flag = zxSfHazardmainDetailJuMapper.updateByPrimaryKey(dbZxSfHazardmainDetailJu);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfHazardmainDetailJu);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfHazardmainDetailJu(List<ZxSfHazardmainDetailJu> zxSfHazardmainDetailJuList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfHazardmainDetailJuList != null && zxSfHazardmainDetailJuList.size() > 0) {
           ZxSfHazardmainDetailJu zxSfHazardmainDetailJu = new ZxSfHazardmainDetailJu();
           zxSfHazardmainDetailJu.setModifyUserInfo(userKey, realName);
           flag = zxSfHazardmainDetailJuMapper.batchDeleteUpdateZxSfHazardmainDetailJu(zxSfHazardmainDetailJuList, zxSfHazardmainDetailJu);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfHazardmainDetailJuList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
