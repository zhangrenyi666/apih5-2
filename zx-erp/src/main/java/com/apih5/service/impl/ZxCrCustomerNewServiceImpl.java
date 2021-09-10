package com.apih5.service.impl;

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
import com.apih5.mybatis.dao.ZxCrCustomerNewMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerNew;
import com.apih5.service.ZxCrCustomerNewService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCrCustomerNewService")
public class ZxCrCustomerNewServiceImpl implements ZxCrCustomerNewService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrCustomerNewMapper zxCrCustomerNewMapper;

    @Override
    public ResponseEntity getZxCrCustomerNewListByCondition(ZxCrCustomerNew zxCrCustomerNew) {
        if (zxCrCustomerNew == null) {
            zxCrCustomerNew = new ZxCrCustomerNew();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCrCustomerNew.setCompanyId("");
        	zxCrCustomerNew.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCrCustomerNew.setCompanyId(zxCrCustomerNew.getProjectId());
        	zxCrCustomerNew.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCrCustomerNew.setProjectId(zxCrCustomerNew.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxCrCustomerNew.getPage(),zxCrCustomerNew.getLimit());
        
       if(StrUtil.isNotEmpty(zxCrCustomerNew.getSearch())) {
    	   zxCrCustomerNew.setCustomerName(zxCrCustomerNew.getSearch());
       }
        
        // 获取数据
        List<ZxCrCustomerNew> zxCrCustomerNewList = zxCrCustomerNewMapper.selectByZxCrCustomerNewList(zxCrCustomerNew);
        for(ZxCrCustomerNew ZxCrCustomerNew : zxCrCustomerNewList) {
        	ZxCrCustomerNew.setType("物资供应商");
        }
        // 得到分页信息
        PageInfo<ZxCrCustomerNew> p = new PageInfo<>(zxCrCustomerNewList);

        return repEntity.okList(zxCrCustomerNewList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrCustomerNewDetail(ZxCrCustomerNew zxCrCustomerNew) {
        if (zxCrCustomerNew == null) {
            zxCrCustomerNew = new ZxCrCustomerNew();
        }
        // 获取数据
        ZxCrCustomerNew dbZxCrCustomerNew = zxCrCustomerNewMapper.selectByPrimaryKey(zxCrCustomerNew.getZxCrCustomerNewId());
        // 数据存在
        if (dbZxCrCustomerNew != null) {
            return repEntity.ok(dbZxCrCustomerNew);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrCustomerNew(ZxCrCustomerNew zxCrCustomerNew) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrCustomerNew.setZxCrCustomerNewId(UuidUtil.generate());
        zxCrCustomerNew.setCreateUserInfo(userKey, realName);
        int flag = zxCrCustomerNewMapper.insert(zxCrCustomerNew);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrCustomerNew);
        }
    }

    @Override
    public ResponseEntity updateZxCrCustomerNew(ZxCrCustomerNew zxCrCustomerNew) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrCustomerNew dbZxCrCustomerNew = zxCrCustomerNewMapper.selectByPrimaryKey(zxCrCustomerNew.getZxCrCustomerNewId());
        if (dbZxCrCustomerNew != null && StrUtil.isNotEmpty(dbZxCrCustomerNew.getZxCrCustomerNewId())) {
           // 供应商名称
           dbZxCrCustomerNew.setCustomerName(zxCrCustomerNew.getCustomerName());
           // 供应商编号
           dbZxCrCustomerNew.setCustomerNo(zxCrCustomerNew.getCustomerNo());
           // 法人
           dbZxCrCustomerNew.setCorparation(zxCrCustomerNew.getCorparation());
           // 组织机构代码证
           dbZxCrCustomerNew.setOrgCertificate(zxCrCustomerNew.getOrgCertificate());
           // 省份
           dbZxCrCustomerNew.setProvince(zxCrCustomerNew.getProvince());
           // 市
           dbZxCrCustomerNew.setDistrict(zxCrCustomerNew.getDistrict());
           // 地址
           dbZxCrCustomerNew.setAddress(zxCrCustomerNew.getAddress());
           // 电话
           dbZxCrCustomerNew.setTelephone(zxCrCustomerNew.getTelephone());
           // 传真
           dbZxCrCustomerNew.setFax(zxCrCustomerNew.getFax());
           // 邮箱
           dbZxCrCustomerNew.setEmail(zxCrCustomerNew.getEmail());
           // 主页
           dbZxCrCustomerNew.setHomePage(zxCrCustomerNew.getHomePage());
           // 经营范围
           dbZxCrCustomerNew.setServices(zxCrCustomerNew.getServices());
           // 更新时间
           dbZxCrCustomerNew.setEditTime(zxCrCustomerNew.getEditTime());
           // 注册日期
           dbZxCrCustomerNew.setRegDate(zxCrCustomerNew.getRegDate());
           // 注册资金
           dbZxCrCustomerNew.setRegMoney(zxCrCustomerNew.getRegMoney());
           // 备注
           dbZxCrCustomerNew.setRemarks(zxCrCustomerNew.getRemarks());
           // 排序
           dbZxCrCustomerNew.setSort(zxCrCustomerNew.getSort());
           // 共通
           dbZxCrCustomerNew.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerNewMapper.updateByPrimaryKey(dbZxCrCustomerNew);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrCustomerNew);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrCustomerNew(List<ZxCrCustomerNew> zxCrCustomerNewList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrCustomerNewList != null && zxCrCustomerNewList.size() > 0) {
           ZxCrCustomerNew zxCrCustomerNew = new ZxCrCustomerNew();
           zxCrCustomerNew.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerNewMapper.batchDeleteUpdateZxCrCustomerNew(zxCrCustomerNewList, zxCrCustomerNew);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrCustomerNewList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
