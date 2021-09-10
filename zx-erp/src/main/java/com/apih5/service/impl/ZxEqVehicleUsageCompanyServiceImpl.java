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
import com.apih5.mybatis.dao.ZxEqVehicleUsageCompanyMapper;
import com.apih5.mybatis.pojo.ZxEqVehicleUsageCompany;
import com.apih5.service.ZxEqVehicleUsageCompanyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqVehicleUsageCompanyService")
public class ZxEqVehicleUsageCompanyServiceImpl implements ZxEqVehicleUsageCompanyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqVehicleUsageCompanyMapper zxEqVehicleUsageCompanyMapper;

    @Override
    public ResponseEntity getZxEqVehicleUsageCompanyListByCondition(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        if (zxEqVehicleUsageCompany == null) {
            zxEqVehicleUsageCompany = new ZxEqVehicleUsageCompany();
        }
        // 分页查询
        PageHelper.startPage(zxEqVehicleUsageCompany.getPage(),zxEqVehicleUsageCompany.getLimit());
        // 获取数据
        List<ZxEqVehicleUsageCompany> zxEqVehicleUsageCompanyList = zxEqVehicleUsageCompanyMapper.selectByZxEqVehicleUsageCompanyList(zxEqVehicleUsageCompany);
        // 得到分页信息
        PageInfo<ZxEqVehicleUsageCompany> p = new PageInfo<>(zxEqVehicleUsageCompanyList);

        return repEntity.okList(zxEqVehicleUsageCompanyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqVehicleUsageCompanyDetail(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        if (zxEqVehicleUsageCompany == null) {
            zxEqVehicleUsageCompany = new ZxEqVehicleUsageCompany();
        }
        // 获取数据
        ZxEqVehicleUsageCompany dbZxEqVehicleUsageCompany = zxEqVehicleUsageCompanyMapper.selectByPrimaryKey(zxEqVehicleUsageCompany.getZxEqVehicleUsageCompanyId());
        // 数据存在
        if (dbZxEqVehicleUsageCompany != null) {
            return repEntity.ok(dbZxEqVehicleUsageCompany);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqVehicleUsageCompany(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqVehicleUsageCompany.setZxEqVehicleUsageCompanyId(UuidUtil.generate());
        zxEqVehicleUsageCompany.setCreateUserInfo(userKey, realName);
        int flag = zxEqVehicleUsageCompanyMapper.insert(zxEqVehicleUsageCompany);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqVehicleUsageCompany);
        }
    }

    @Override
    public ResponseEntity updateZxEqVehicleUsageCompany(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqVehicleUsageCompany dbZxEqVehicleUsageCompany = zxEqVehicleUsageCompanyMapper.selectByPrimaryKey(zxEqVehicleUsageCompany.getZxEqVehicleUsageCompanyId());
        if (dbZxEqVehicleUsageCompany != null && StrUtil.isNotEmpty(dbZxEqVehicleUsageCompany.getZxEqVehicleUsageCompanyId())) {
           // 分类
           dbZxEqVehicleUsageCompany.setCatName(zxEqVehicleUsageCompany.getCatName());
           // 管理编号
           dbZxEqVehicleUsageCompany.setEquipNo(zxEqVehicleUsageCompany.getEquipNo());
           // 机械名称
           dbZxEqVehicleUsageCompany.setEquipName(zxEqVehicleUsageCompany.getEquipName());
           // 技术规格
           dbZxEqVehicleUsageCompany.setTechrequire(zxEqVehicleUsageCompany.getTechrequire());
           // 日历台日
           dbZxEqVehicleUsageCompany.setCalendarNumDay(zxEqVehicleUsageCompany.getCalendarNumDay());
           // 完好台日
           dbZxEqVehicleUsageCompany.setWellDays(zxEqVehicleUsageCompany.getWellDays());
           // 完好率
           dbZxEqVehicleUsageCompany.setWellPercen(zxEqVehicleUsageCompany.getWellPercen());
           // 运转台日
           dbZxEqVehicleUsageCompany.setWorkDays(zxEqVehicleUsageCompany.getWorkDays());
           // 运转台时
           dbZxEqVehicleUsageCompany.setActualQty(zxEqVehicleUsageCompany.getActualQty());
           // 利用率
           dbZxEqVehicleUsageCompany.setRunDayPercen(zxEqVehicleUsageCompany.getRunDayPercen());
           // 行驶里程
           dbZxEqVehicleUsageCompany.setUsefulMileage(zxEqVehicleUsageCompany.getUsefulMileage());
           // 油耗情况汽油
           dbZxEqVehicleUsageCompany.setOilamount(zxEqVehicleUsageCompany.getOilamount());
           // 油耗情况柴油
           dbZxEqVehicleUsageCompany.setCaioidamout(zxEqVehicleUsageCompany.getCaioidamout());
           // 电消耗
           dbZxEqVehicleUsageCompany.setConsumption(zxEqVehicleUsageCompany.getConsumption());
           // 备注
           dbZxEqVehicleUsageCompany.setRemarks(zxEqVehicleUsageCompany.getRemarks());
           // 排序
           dbZxEqVehicleUsageCompany.setSort(zxEqVehicleUsageCompany.getSort());
           // 共通
           dbZxEqVehicleUsageCompany.setModifyUserInfo(userKey, realName);
           flag = zxEqVehicleUsageCompanyMapper.updateByPrimaryKey(dbZxEqVehicleUsageCompany);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqVehicleUsageCompany);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqVehicleUsageCompany(List<ZxEqVehicleUsageCompany> zxEqVehicleUsageCompanyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqVehicleUsageCompanyList != null && zxEqVehicleUsageCompanyList.size() > 0) {
           ZxEqVehicleUsageCompany zxEqVehicleUsageCompany = new ZxEqVehicleUsageCompany();
           zxEqVehicleUsageCompany.setModifyUserInfo(userKey, realName);
           flag = zxEqVehicleUsageCompanyMapper.batchDeleteUpdateZxEqVehicleUsageCompany(zxEqVehicleUsageCompanyList, zxEqVehicleUsageCompany);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqVehicleUsageCompanyList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public List<ZxEqVehicleUsageCompany> ureportZxEqVehicleUsageCompany(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
    	if (zxEqVehicleUsageCompany == null) {
            zxEqVehicleUsageCompany = new ZxEqVehicleUsageCompany();
        }
    	List<ZxEqVehicleUsageCompany> zxEqVehicleUsageCompanyList = zxEqVehicleUsageCompanyMapper.selectZxEqVehicleUsageCompany(zxEqVehicleUsageCompany);
    	
    	return zxEqVehicleUsageCompanyList;
    }
    
    @Override
    public ResponseEntity ureportZxEqVehicleUsageCompanyIdle(ZxEqVehicleUsageCompany zxEqVehicleUsageCompany) {
        if (zxEqVehicleUsageCompany == null) {
            zxEqVehicleUsageCompany = new ZxEqVehicleUsageCompany();
        }
        // 分页查询
        PageHelper.startPage(zxEqVehicleUsageCompany.getPage(),zxEqVehicleUsageCompany.getLimit());
        // 获取数据
        List<ZxEqVehicleUsageCompany> zxEqVehicleUsageCompanyList = zxEqVehicleUsageCompanyMapper.selectZxEqVehicleUsageCompany(zxEqVehicleUsageCompany);
        // 得到分页信息
        PageInfo<ZxEqVehicleUsageCompany> p = new PageInfo<>(zxEqVehicleUsageCompanyList);

        return repEntity.okList(zxEqVehicleUsageCompanyList, p.getTotal());
    }

}
