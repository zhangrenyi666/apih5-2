package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkLimitPriceRepMapper;
import com.apih5.mybatis.pojo.ZxSkLimitPriceRep;
import com.apih5.service.ZxSkLimitPriceRepService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkLimitPriceRepService")
public class ZxSkLimitPriceRepServiceImpl implements ZxSkLimitPriceRepService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkLimitPriceRepMapper zxSkLimitPriceRepMapper;

    @Override
    public ResponseEntity getZxSkLimitPriceRepListByCondition(ZxSkLimitPriceRep zxSkLimitPriceRep) {
        if (zxSkLimitPriceRep == null) {
            zxSkLimitPriceRep = new ZxSkLimitPriceRep();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSkLimitPriceRep.setCompanyId("");
        	zxSkLimitPriceRep.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSkLimitPriceRep.setCompanyId(zxSkLimitPriceRep.getProjectId());
        	zxSkLimitPriceRep.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSkLimitPriceRep.setProjectId(zxSkLimitPriceRep.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSkLimitPriceRep.getPage(),zxSkLimitPriceRep.getLimit());
        // 获取数据
        List<ZxSkLimitPriceRep> zxSkLimitPriceRepList = zxSkLimitPriceRepMapper.selectByZxSkLimitPriceRepList(zxSkLimitPriceRep);
        // 得到分页信息
        PageInfo<ZxSkLimitPriceRep> p = new PageInfo<>(zxSkLimitPriceRepList);

        return repEntity.okList(zxSkLimitPriceRepList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkLimitPriceRepDetail(ZxSkLimitPriceRep zxSkLimitPriceRep) {
        if (zxSkLimitPriceRep == null) {
            zxSkLimitPriceRep = new ZxSkLimitPriceRep();
        }
        // 获取数据
        ZxSkLimitPriceRep dbZxSkLimitPriceRep = zxSkLimitPriceRepMapper.selectByPrimaryKey(zxSkLimitPriceRep.getZxSkLimitPriceRepId());
        // 数据存在
        if (dbZxSkLimitPriceRep != null) {
            return repEntity.ok(dbZxSkLimitPriceRep);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkLimitPriceRep(ZxSkLimitPriceRep zxSkLimitPriceRep) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkLimitPriceRep.setZxSkLimitPriceRepId(UuidUtil.generate());
        zxSkLimitPriceRep.setCreateUserInfo(userKey, realName);
        int flag = zxSkLimitPriceRepMapper.insert(zxSkLimitPriceRep);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkLimitPriceRep);
        }
    }

    @Override
    public ResponseEntity updateZxSkLimitPriceRep(ZxSkLimitPriceRep zxSkLimitPriceRep) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkLimitPriceRep dbZxSkLimitPriceRep = zxSkLimitPriceRepMapper.selectByPrimaryKey(zxSkLimitPriceRep.getZxSkLimitPriceRepId());
        if (dbZxSkLimitPriceRep != null && StrUtil.isNotEmpty(dbZxSkLimitPriceRep.getZxSkLimitPriceRepId())) {
           // 期次
           dbZxSkLimitPriceRep.setPeriod(zxSkLimitPriceRep.getPeriod());
           // 项目名称
           dbZxSkLimitPriceRep.setProjectName(zxSkLimitPriceRep.getProjectName());
           // 物资大类
           dbZxSkLimitPriceRep.setResourceName(zxSkLimitPriceRep.getResourceName());
           // 物资名称
           dbZxSkLimitPriceRep.setWorkName(zxSkLimitPriceRep.getWorkName());
           // 规格型号
           dbZxSkLimitPriceRep.setSpec(zxSkLimitPriceRep.getSpec());
           // 计量单位
           dbZxSkLimitPriceRep.setUnit(zxSkLimitPriceRep.getUnit());
           // 所在省份
           dbZxSkLimitPriceRep.setProvince(zxSkLimitPriceRep.getProvince());
           // 限价
           dbZxSkLimitPriceRep.setPrice(zxSkLimitPriceRep.getPrice());
           // 备注
           dbZxSkLimitPriceRep.setRemarks(zxSkLimitPriceRep.getRemarks());
           // 排序
           dbZxSkLimitPriceRep.setSort(zxSkLimitPriceRep.getSort());
           // 共通
           dbZxSkLimitPriceRep.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceRepMapper.updateByPrimaryKey(dbZxSkLimitPriceRep);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkLimitPriceRep);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkLimitPriceRep(List<ZxSkLimitPriceRep> zxSkLimitPriceRepList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkLimitPriceRepList != null && zxSkLimitPriceRepList.size() > 0) {
           ZxSkLimitPriceRep zxSkLimitPriceRep = new ZxSkLimitPriceRep();
           zxSkLimitPriceRep.setModifyUserInfo(userKey, realName);
           flag = zxSkLimitPriceRepMapper.batchDeleteUpdateZxSkLimitPriceRep(zxSkLimitPriceRepList, zxSkLimitPriceRep);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkLimitPriceRepList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxSkLimitPriceRep> ureportZxSkLimitPriceRep(ZxSkLimitPriceRep zxSkLimitPriceRep) {
        if(zxSkLimitPriceRep == null){
            zxSkLimitPriceRep = new ZxSkLimitPriceRep();
        }
        if(StrUtil.isEmpty(zxSkLimitPriceRep.getProjectId())){
            HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
            String userId = TokenUtils.getUserId(request);
            String ext1 = TokenUtils.getExt1(request);
            // 集团全部可见
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                    || StrUtil.equals("admin", userId)) {
                zxSkLimitPriceRep.setCompanyId("");
                zxSkLimitPriceRep.setProjectId("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
                // 公司只看见自己的
                zxSkLimitPriceRep.setCompanyId(zxSkLimitPriceRep.getCompanyId());
                zxSkLimitPriceRep.setProjectId("");
            }else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                    || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
                // 项目通过右上角数据
                if(StrUtil.isEmpty(zxSkLimitPriceRep.getCompanyId())){
                    return new ArrayList<>();
                }else {
                    zxSkLimitPriceRep.setProjectId(zxSkLimitPriceRep.getCompanyId());
                    zxSkLimitPriceRep.setCompanyId("");
                }
            }
        }else {
            zxSkLimitPriceRep.setCompanyId("");
        }
        if(zxSkLimitPriceRep.getPeriodTime() != null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPriceRep.getPeriodTime());
            if(StrUtil.equals(result.substring(4),"12")){
                zxSkLimitPriceRep.setPeriod(result.substring(0,4)+"年下半年");
            }else {
                zxSkLimitPriceRep.setPeriod(result.substring(0,4)+"年上半年");
            }
        }
        return zxSkLimitPriceRepMapper.selectZxSkLimitPriceRep(zxSkLimitPriceRep);
    }
    
    @Override
    public ResponseEntity ureportZxSkLimitPriceRepIdle(ZxSkLimitPriceRep zxSkLimitPriceRep) {
        if(zxSkLimitPriceRep == null){
            zxSkLimitPriceRep = new ZxSkLimitPriceRep();
        }
        if(StrUtil.isEmpty(zxSkLimitPriceRep.getProjectId())){
            HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
            String userId = TokenUtils.getUserId(request);
            String ext1 = TokenUtils.getExt1(request);
            // 集团全部可见
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                    || StrUtil.equals("admin", userId)) {
                zxSkLimitPriceRep.setCompanyId("");
                zxSkLimitPriceRep.setProjectId("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
                // 公司只看见自己的
                zxSkLimitPriceRep.setCompanyId(zxSkLimitPriceRep.getCompanyId());
                zxSkLimitPriceRep.setProjectId("");
            }else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                    || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
                // 项目通过右上角数据
                if(StrUtil.isEmpty(zxSkLimitPriceRep.getCompanyId())){
                    return repEntity.ok(new ArrayList<>());
                }else {
                    zxSkLimitPriceRep.setProjectId(zxSkLimitPriceRep.getCompanyId());
                    zxSkLimitPriceRep.setCompanyId("");
                }
            }
        }else {
            zxSkLimitPriceRep.setCompanyId("");
        }
        if(zxSkLimitPriceRep.getPeriodTime() != null){
            String result = new SimpleDateFormat("yyyyMM").format(zxSkLimitPriceRep.getPeriodTime());
            if(StrUtil.equals(result.substring(4),"12")){
                zxSkLimitPriceRep.setPeriod(result.substring(0,4)+"年下半年");
            }else {
                zxSkLimitPriceRep.setPeriod(result.substring(0,4)+"年上半年");
            }
        }
         // 分页查询
         PageHelper.startPage(zxSkLimitPriceRep.getPage(),zxSkLimitPriceRep.getLimit());
         // 获取数据
         List<ZxSkLimitPriceRep> zxSkLimitPriceRepList = zxSkLimitPriceRepMapper.selectZxSkLimitPriceRep(zxSkLimitPriceRep);
         // 得到分页信息
         PageInfo<ZxSkLimitPriceRep> p = new PageInfo<>(zxSkLimitPriceRepList);

         return repEntity.okList(zxSkLimitPriceRepList, p.getTotal());
    }
}
