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
import com.apih5.mybatis.dao.ZxEqAbcMachineryMapper;
import com.apih5.mybatis.pojo.ZxEqAbcMachinery;
import com.apih5.service.ZxEqAbcMachineryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqAbcMachineryService")
public class ZxEqAbcMachineryServiceImpl implements ZxEqAbcMachineryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqAbcMachineryMapper zxEqAbcMachineryMapper;

    @Override
    public ResponseEntity getZxEqAbcMachineryListByCondition(ZxEqAbcMachinery zxEqAbcMachinery) {
        if (zxEqAbcMachinery == null) {
            zxEqAbcMachinery = new ZxEqAbcMachinery();
        }
        // 分页查询
        PageHelper.startPage(zxEqAbcMachinery.getPage(),zxEqAbcMachinery.getLimit());
        // 获取数据
        List<ZxEqAbcMachinery> zxEqAbcMachineryList = zxEqAbcMachineryMapper.selectByZxEqAbcMachineryList(zxEqAbcMachinery);
        // 得到分页信息
        PageInfo<ZxEqAbcMachinery> p = new PageInfo<>(zxEqAbcMachineryList);

        return repEntity.okList(zxEqAbcMachineryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqAbcMachineryDetail(ZxEqAbcMachinery zxEqAbcMachinery) {
        if (zxEqAbcMachinery == null) {
            zxEqAbcMachinery = new ZxEqAbcMachinery();
        }
        // 获取数据
        ZxEqAbcMachinery dbZxEqAbcMachinery = zxEqAbcMachineryMapper.selectByPrimaryKey(zxEqAbcMachinery.getZxEqAbcMachineryId());
        // 数据存在
        if (dbZxEqAbcMachinery != null) {
            return repEntity.ok(dbZxEqAbcMachinery);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqAbcMachinery(ZxEqAbcMachinery zxEqAbcMachinery) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqAbcMachinery.setZxEqAbcMachineryId(UuidUtil.generate());
        zxEqAbcMachinery.setCreateUserInfo(userKey, realName);
        int flag = zxEqAbcMachineryMapper.insert(zxEqAbcMachinery);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxEqAbcMachinery);
        }
    }

    @Override
    public ResponseEntity updateZxEqAbcMachinery(ZxEqAbcMachinery zxEqAbcMachinery) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqAbcMachinery dbZxEqAbcMachinery = zxEqAbcMachineryMapper.selectByPrimaryKey(zxEqAbcMachinery.getZxEqAbcMachineryId());
        if (dbZxEqAbcMachinery != null && StrUtil.isNotEmpty(dbZxEqAbcMachinery.getZxEqAbcMachineryId())) {
           // 单位名称
           dbZxEqAbcMachinery.setOrgname(zxEqAbcMachinery.getOrgname());
           // 期末机械台数合计
           dbZxEqAbcMachinery.setNumsum(zxEqAbcMachinery.getNumsum());
           // 期末机械台数A类
           dbZxEqAbcMachinery.setNum1(zxEqAbcMachinery.getNum1());
           // 期末机械台数B类
           dbZxEqAbcMachinery.setNum2(zxEqAbcMachinery.getNum2());
           // 期末机械台数C类
           dbZxEqAbcMachinery.setNum3(zxEqAbcMachinery.getNum3());
           // 期末机械台数D类
           dbZxEqAbcMachinery.setNum4(zxEqAbcMachinery.getNum4());
           // 期末原值（万元）合计
           dbZxEqAbcMachinery.setYamtsum(zxEqAbcMachinery.getYamtsum());
           // 期末原值（万元）A类
           dbZxEqAbcMachinery.setYamt1(zxEqAbcMachinery.getYamt1());
           // 期末原值（万元）B类
           dbZxEqAbcMachinery.setYamt2(zxEqAbcMachinery.getYamt2());
           // 期末原值（万元）C类
           dbZxEqAbcMachinery.setYamt3(zxEqAbcMachinery.getYamt3());
           // 期末原值（万元）D类
           dbZxEqAbcMachinery.setYamt4(zxEqAbcMachinery.getYamt4());
           // 期末净值（万元）合计
           dbZxEqAbcMachinery.setJamtsum(zxEqAbcMachinery.getJamtsum());
           // 期末净值（万元）A类
           dbZxEqAbcMachinery.setJamt1(zxEqAbcMachinery.getJamt1());
           // 期末净值（万元）B类
           dbZxEqAbcMachinery.setJamt2(zxEqAbcMachinery.getJamt2());
           // 期末净值（万元）C类
           dbZxEqAbcMachinery.setJamt3(zxEqAbcMachinery.getJamt3());
           // 期末净值（万元）D类
           dbZxEqAbcMachinery.setJamt4(zxEqAbcMachinery.getJamt4());
           // 备注
           dbZxEqAbcMachinery.setRemarks(zxEqAbcMachinery.getRemarks());
           // 排序
           dbZxEqAbcMachinery.setSort(zxEqAbcMachinery.getSort());
           // 共通
           dbZxEqAbcMachinery.setModifyUserInfo(userKey, realName);
           flag = zxEqAbcMachineryMapper.updateByPrimaryKey(dbZxEqAbcMachinery);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxEqAbcMachinery);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqAbcMachinery(List<ZxEqAbcMachinery> zxEqAbcMachineryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqAbcMachineryList != null && zxEqAbcMachineryList.size() > 0) {
           ZxEqAbcMachinery zxEqAbcMachinery = new ZxEqAbcMachinery();
           zxEqAbcMachinery.setModifyUserInfo(userKey, realName);
           flag = zxEqAbcMachineryMapper.batchDeleteUpdateZxEqAbcMachinery(zxEqAbcMachineryList, zxEqAbcMachinery);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxEqAbcMachineryList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxEqAbcMachinery> ureportZxEqAbcMachinery(ZxEqAbcMachinery zxEqAbcMachinery) {
    	if(zxEqAbcMachinery==null) {
    		zxEqAbcMachinery = new ZxEqAbcMachinery();
    	}
    	List<ZxEqAbcMachinery> zxEqAbcMachineryList = zxEqAbcMachineryMapper.selectZxEqAbcMachinery(zxEqAbcMachinery);
    	return zxEqAbcMachineryList;
    }
    
    @Override
    public ResponseEntity ureportZxEqAbcMachineryIdle(ZxEqAbcMachinery zxEqAbcMachinery) {
        if (zxEqAbcMachinery == null) {
            zxEqAbcMachinery = new ZxEqAbcMachinery();
        }
       
        // 获取数据
        List<ZxEqAbcMachinery> zxEqAbcMachineryList = zxEqAbcMachineryMapper.selectZxEqAbcMachinery(zxEqAbcMachinery);

        return repEntity.okList(zxEqAbcMachineryList, zxEqAbcMachineryList.size());
    }
}
