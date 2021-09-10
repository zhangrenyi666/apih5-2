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
import com.apih5.mybatis.dao.ZxEqYyglJbbMapper;
import com.apih5.mybatis.pojo.ZxEqYyglJbb;
import com.apih5.service.ZxEqYyglJbbService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqYyglJbbService")
public class ZxEqYyglJbbServiceImpl implements ZxEqYyglJbbService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqYyglJbbMapper zxEqYyglJbbMapper;

    @Override
    public ResponseEntity getZxEqYyglJbbListByCondition(ZxEqYyglJbb zxEqYyglJbb) {
        if (zxEqYyglJbb == null) {
            zxEqYyglJbb = new ZxEqYyglJbb();
        }
        // 分页查询
        PageHelper.startPage(zxEqYyglJbb.getPage(),zxEqYyglJbb.getLimit());
        // 获取数据
        List<ZxEqYyglJbb> zxEqYyglJbbList = zxEqYyglJbbMapper.selectByZxEqYyglJbbList(zxEqYyglJbb);
        // 得到分页信息
        PageInfo<ZxEqYyglJbb> p = new PageInfo<>(zxEqYyglJbbList);

        return repEntity.okList(zxEqYyglJbbList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqYyglJbbDetail(ZxEqYyglJbb zxEqYyglJbb) {
        if (zxEqYyglJbb == null) {
            zxEqYyglJbb = new ZxEqYyglJbb();
        }
        // 获取数据
        ZxEqYyglJbb dbZxEqYyglJbb = zxEqYyglJbbMapper.selectByPrimaryKey(zxEqYyglJbb.getZxEqYyglJbbId());
        // 数据存在
        if (dbZxEqYyglJbb != null) {
            return repEntity.ok(dbZxEqYyglJbb);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqYyglJbb(ZxEqYyglJbb zxEqYyglJbb) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqYyglJbb.setZxEqYyglJbbId(UuidUtil.generate());
        zxEqYyglJbb.setCreateUserInfo(userKey, realName);
        int flag = zxEqYyglJbbMapper.insert(zxEqYyglJbb);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqYyglJbb);
        }
    }

    @Override
    public ResponseEntity updateZxEqYyglJbb(ZxEqYyglJbb zxEqYyglJbb) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqYyglJbb dbZxEqYyglJbb = zxEqYyglJbbMapper.selectByPrimaryKey(zxEqYyglJbb.getZxEqYyglJbbId());
        if (dbZxEqYyglJbb != null && StrUtil.isNotEmpty(dbZxEqYyglJbb.getZxEqYyglJbbId())) {
           // 单位
           dbZxEqYyglJbb.setOrgName(zxEqYyglJbb.getOrgName());
           // 期次
           dbZxEqYyglJbb.setPeriod(zxEqYyglJbb.getPeriod());
           // 总台数（台）境内
           dbZxEqYyglJbb.setCount1(zxEqYyglJbb.getCount1());
           // 总台数（台）境外
           dbZxEqYyglJbb.setCount2(zxEqYyglJbb.getCount2());
           // 总功率（kw）境内
           dbZxEqYyglJbb.setPower1(zxEqYyglJbb.getPower1());
           // 总功率（kw）境外
           dbZxEqYyglJbb.setPower2(zxEqYyglJbb.getPower2());
           // 固定资产净值（万元）境内
           dbZxEqYyglJbb.setLeftValue1(zxEqYyglJbb.getLeftValue1());
           // 固定资产净值（万元）境外
           dbZxEqYyglJbb.setLeftValue2(zxEqYyglJbb.getLeftValue2());
           // 固定资产原值（万元）境内
           dbZxEqYyglJbb.setOrginalValue1(zxEqYyglJbb.getOrginalValue1());
           // 固定资产原值（万元）境外
           dbZxEqYyglJbb.setOrginalValue2(zxEqYyglJbb.getOrginalValue2());
           // 台头名
           dbZxEqYyglJbb.setReportTitle(zxEqYyglJbb.getReportTitle());
           // 备注
           dbZxEqYyglJbb.setRemarks(zxEqYyglJbb.getRemarks());
           // 排序
           dbZxEqYyglJbb.setSort(zxEqYyglJbb.getSort());
           // 共通
           dbZxEqYyglJbb.setModifyUserInfo(userKey, realName);
           flag = zxEqYyglJbbMapper.updateByPrimaryKey(dbZxEqYyglJbb);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqYyglJbb);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqYyglJbb(List<ZxEqYyglJbb> zxEqYyglJbbList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqYyglJbbList != null && zxEqYyglJbbList.size() > 0) {
           ZxEqYyglJbb zxEqYyglJbb = new ZxEqYyglJbb();
           zxEqYyglJbb.setModifyUserInfo(userKey, realName);
           flag = zxEqYyglJbbMapper.batchDeleteUpdateZxEqYyglJbb(zxEqYyglJbbList, zxEqYyglJbb);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqYyglJbbList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ZxEqYyglJbb ureportZxEqYyglJbb(ZxEqYyglJbb zxEqYyglJbb) {
    	if (zxEqYyglJbb == null) {
            zxEqYyglJbb = new ZxEqYyglJbb();
        }
    	if(StrUtil.isEmpty(zxEqYyglJbb.getPeriod())) {
    		zxEqYyglJbb.setPeriod("");
    	}
    	ZxEqYyglJbb zxEqYyglJbbList = zxEqYyglJbbMapper.selectZxEqYyglJbb(zxEqYyglJbb);
    	if(zxEqYyglJbbList == null) {
    		zxEqYyglJbbList = new ZxEqYyglJbb();
    		zxEqYyglJbbList.setReportTitle(zxEqYyglJbb.getReportTitle());
    		zxEqYyglJbbList.setOrgName(zxEqYyglJbb.getOrgName());
    		zxEqYyglJbbList.setPeriod(zxEqYyglJbb.getPeriod());
    	}
    	return zxEqYyglJbbList;
    }
    
    @Override
    public ResponseEntity ureportZxEqYyglJbbIdle(ZxEqYyglJbb zxEqYyglJbb) {
        if (zxEqYyglJbb == null) {
            zxEqYyglJbb = new ZxEqYyglJbb();
        }
        // 获取数据
        ZxEqYyglJbb zxEqYyglJbbList = zxEqYyglJbbMapper.selectZxEqYyglJbb(zxEqYyglJbb);
        if(zxEqYyglJbbList == null) {
    		zxEqYyglJbbList = new ZxEqYyglJbb();
    		zxEqYyglJbbList.setReportTitle(zxEqYyglJbb.getReportTitle());
    		zxEqYyglJbbList.setOrgName(zxEqYyglJbb.getOrgName());
    		zxEqYyglJbbList.setPeriod(zxEqYyglJbb.getPeriod());
    	}
        return repEntity.ok(zxEqYyglJbbList);
    }
}
