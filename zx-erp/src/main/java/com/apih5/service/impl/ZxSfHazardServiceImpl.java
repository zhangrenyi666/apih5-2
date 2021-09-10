package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfHazardMapper;
import com.apih5.mybatis.pojo.ZxSfHazard;
import com.apih5.service.ZxSfHazardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfHazardService")
public class ZxSfHazardServiceImpl implements ZxSfHazardService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfHazardMapper zxSfHazardMapper;

    @Override
    public ResponseEntity getZxSfHazardListByCondition(ZxSfHazard zxSfHazard) {
        if (zxSfHazard == null) {
            zxSfHazard = new ZxSfHazard();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfHazard.setCompanyId("");
            zxSfHazard.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfHazard.setCompanyId(zxSfHazard.getOrgID());
            zxSfHazard.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfHazard.setOrgID(zxSfHazard.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSfHazard.getPage(),zxSfHazard.getLimit());
        // 获取数据
        List<ZxSfHazard> zxSfHazardList = zxSfHazardMapper.selectByZxSfHazardList(zxSfHazard);
        // 得到分页信息
        PageInfo<ZxSfHazard> p = new PageInfo<>(zxSfHazardList);

        return repEntity.okList(zxSfHazardList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfHazardDetail(ZxSfHazard zxSfHazard) {
        if (zxSfHazard == null) {
            zxSfHazard = new ZxSfHazard();
        }
        // 获取数据
        ZxSfHazard dbZxSfHazard = zxSfHazardMapper.selectByPrimaryKey(zxSfHazard.getZxSfHazardId());
        // 数据存在
        if (dbZxSfHazard != null) {
            return repEntity.ok(dbZxSfHazard);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfHazard(ZxSfHazard zxSfHazard) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfHazard.setZxSfHazardId(UuidUtil.generate());
        zxSfHazard.setCreateUserInfo(userKey, realName);
        int flag = zxSfHazardMapper.insert(zxSfHazard);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfHazard);
        }
    }

    @Override
    public ResponseEntity updateZxSfHazard(ZxSfHazard zxSfHazard) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfHazard dbZxSfHazard = zxSfHazardMapper.selectByPrimaryKey(zxSfHazard.getZxSfHazardId());
        if (dbZxSfHazard != null && StrUtil.isNotEmpty(dbZxSfHazard.getZxSfHazardId())) {
           // 机构名称
           dbZxSfHazard.setOrgName(zxSfHazard.getOrgName());
           // 机构ID
           dbZxSfHazard.setOrgID(zxSfHazard.getOrgID());
           // 过程(区域)
           dbZxSfHazard.setProArea(zxSfHazard.getProArea());
           // 行为(活动)或设备
           dbZxSfHazard.setDoing(zxSfHazard.getDoing());
           // 危险因素
           dbZxSfHazard.setRiskFactors(zxSfHazard.getRiskFactors());
           // 可能导致的伤害（事故）
           dbZxSfHazard.setAccident(zxSfHazard.getAccident());
           // 作业条件危险性评价(L)
           dbZxSfHazard.setLee(zxSfHazard.getLee());
           // 作业条件危险性评价(e)
           dbZxSfHazard.setBee(zxSfHazard.getBee());
           // 作业条件危险性评价(c)
           dbZxSfHazard.setCee(zxSfHazard.getCee());
           // 作业条件危险性评价(d)
           dbZxSfHazard.setDee(zxSfHazard.getDee());
           // 风险等级
           dbZxSfHazard.setRiskLevel(zxSfHazard.getRiskLevel());
           // 预防措施
           dbZxSfHazard.setSafeguard(zxSfHazard.getSafeguard());
           // 主表ID
           dbZxSfHazard.setMainID(zxSfHazard.getMainID());
           // 编辑时间
           dbZxSfHazard.setEditTime(zxSfHazard.getEditTime());
           // 备注
           dbZxSfHazard.setRemarks(zxSfHazard.getRemarks());
           // 排序
           dbZxSfHazard.setSort(zxSfHazard.getSort());
           // 共通
           dbZxSfHazard.setModifyUserInfo(userKey, realName);
           flag = zxSfHazardMapper.updateByPrimaryKey(dbZxSfHazard);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfHazard);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfHazard(List<ZxSfHazard> zxSfHazardList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfHazardList != null && zxSfHazardList.size() > 0) {
           ZxSfHazard zxSfHazard = new ZxSfHazard();
           zxSfHazard.setModifyUserInfo(userKey, realName);
           flag = zxSfHazardMapper.batchDeleteUpdateZxSfHazard(zxSfHazardList, zxSfHazard);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfHazardList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
