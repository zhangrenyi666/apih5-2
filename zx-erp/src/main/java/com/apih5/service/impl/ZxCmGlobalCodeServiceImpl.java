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
import com.apih5.mybatis.dao.ZxCmGlobalCodeMapper;
import com.apih5.mybatis.pojo.ZxCmGlobalCode;
import com.apih5.service.ZxCmGlobalCodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCmGlobalCodeService")
public class ZxCmGlobalCodeServiceImpl implements ZxCmGlobalCodeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCmGlobalCodeMapper zxCmGlobalCodeMapper;

    @Override
    public ResponseEntity getZxCmGlobalCodeListByCondition(ZxCmGlobalCode zxCmGlobalCode) {
        if (zxCmGlobalCode == null) {
            zxCmGlobalCode = new ZxCmGlobalCode();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCmGlobalCode.setCompanyId("");
        	zxCmGlobalCode.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCmGlobalCode.setCompanyId(zxCmGlobalCode.getProjectId());
        	zxCmGlobalCode.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCmGlobalCode.setProjectId(zxCmGlobalCode.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxCmGlobalCode.getPage(),zxCmGlobalCode.getLimit());
        // 学历
        if(zxCmGlobalCode.getCategoryID().equals("categorysafe001003")) {
        	zxCmGlobalCode.setCategoryID(zxCmGlobalCode.getCategoryID());
            // 获取数据
            List<ZxCmGlobalCode> zxCmGlobalCodeList = zxCmGlobalCodeMapper.selectByZxCmGlobalCodeList(zxCmGlobalCode);
            // 得到分页信息
            PageInfo<ZxCmGlobalCode> p = new PageInfo<>(zxCmGlobalCodeList);

            return repEntity.okList(zxCmGlobalCodeList, p.getTotal());
        }
        // 职称
        if(zxCmGlobalCode.getCategoryID().equals("categorysafe001004")) {
        	zxCmGlobalCode.setCategoryID(zxCmGlobalCode.getCategoryID());
            // 获取数据
            List<ZxCmGlobalCode> zxCmGlobalCodeList = zxCmGlobalCodeMapper.selectByZxCmGlobalCodeList(zxCmGlobalCode);
            // 得到分页信息
            PageInfo<ZxCmGlobalCode> p = new PageInfo<>(zxCmGlobalCodeList);

            return repEntity.okList(zxCmGlobalCodeList, p.getTotal());
        }
        
        // 人员类型
        if(zxCmGlobalCode.getCategoryID().equals("categorysafe001008")) {
        	zxCmGlobalCode.setCategoryID(zxCmGlobalCode.getCategoryID());
            // 获取数据
            List<ZxCmGlobalCode> zxCmGlobalCodeList = zxCmGlobalCodeMapper.selectByZxCmGlobalCodeList(zxCmGlobalCode);
            // 得到分页信息
            PageInfo<ZxCmGlobalCode> p = new PageInfo<>(zxCmGlobalCodeList);

            return repEntity.okList(zxCmGlobalCodeList, p.getTotal());
        }
        
        // 特种作业
        if(zxCmGlobalCode.getCategoryID().equals("categorysafe001005")) {
        	zxCmGlobalCode.setCategoryID(zxCmGlobalCode.getCategoryID());
            // 获取数据
            List<ZxCmGlobalCode> zxCmGlobalCodeList = zxCmGlobalCodeMapper.selectByZxCmGlobalCodeList(zxCmGlobalCode);
            // 得到分页信息
            PageInfo<ZxCmGlobalCode> p = new PageInfo<>(zxCmGlobalCodeList);

            return repEntity.okList(zxCmGlobalCodeList, p.getTotal());
        }
        
        // 培训内容
        if(zxCmGlobalCode.getCategoryID().equals("category_safe_educontext")) {
        	zxCmGlobalCode.setCategoryID(zxCmGlobalCode.getCategoryID());
            // 获取数据
            List<ZxCmGlobalCode> zxCmGlobalCodeList = zxCmGlobalCodeMapper.selectByZxCmGlobalCodeList(zxCmGlobalCode);
            // 得到分页信息
            PageInfo<ZxCmGlobalCode> p = new PageInfo<>(zxCmGlobalCodeList);

            return repEntity.okList(zxCmGlobalCodeList, p.getTotal());
        }
        
        // 预算比率
        if(zxCmGlobalCode.getCategoryID().equals("categorysafe001009")) {
        	zxCmGlobalCode.setCategoryID(zxCmGlobalCode.getCategoryID());
            // 获取数据
            List<ZxCmGlobalCode> zxCmGlobalCodeList = zxCmGlobalCodeMapper.selectByZxCmGlobalCodeList(zxCmGlobalCode);
            // 得到分页信息
            PageInfo<ZxCmGlobalCode> p = new PageInfo<>(zxCmGlobalCodeList);

            return repEntity.okList(zxCmGlobalCodeList, p.getTotal());
        }
        
        
        // 获取数据
        List<ZxCmGlobalCode> zxCmGlobalCodeList = zxCmGlobalCodeMapper.selectByZxCmGlobalCodeList(zxCmGlobalCode);
        // 得到分页信息
        PageInfo<ZxCmGlobalCode> p = new PageInfo<>(zxCmGlobalCodeList);

        return repEntity.okList(zxCmGlobalCodeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCmGlobalCodeDetail(ZxCmGlobalCode zxCmGlobalCode) {
        if (zxCmGlobalCode == null) {
            zxCmGlobalCode = new ZxCmGlobalCode();
        }
        // 获取数据
        ZxCmGlobalCode dbZxCmGlobalCode = zxCmGlobalCodeMapper.selectByPrimaryKey(zxCmGlobalCode.getZxCmGlobalCodeId());
        // 数据存在
        if (dbZxCmGlobalCode != null) {
            return repEntity.ok(dbZxCmGlobalCode);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCmGlobalCode(ZxCmGlobalCode zxCmGlobalCode) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCmGlobalCode.setZxCmGlobalCodeId(UuidUtil.generate());
        zxCmGlobalCode.setCreateUserInfo(userKey, realName);
        int flag = zxCmGlobalCodeMapper.insert(zxCmGlobalCode);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCmGlobalCode);
        }
    }

    @Override
    public ResponseEntity updateZxCmGlobalCode(ZxCmGlobalCode zxCmGlobalCode) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCmGlobalCode dbZxCmGlobalCode = zxCmGlobalCodeMapper.selectByPrimaryKey(zxCmGlobalCode.getZxCmGlobalCodeId());
        if (dbZxCmGlobalCode != null && StrUtil.isNotEmpty(dbZxCmGlobalCode.getZxCmGlobalCodeId())) {
           // 分类ID
           dbZxCmGlobalCode.setCategoryID(zxCmGlobalCode.getCategoryID());
           // 代码编号
           dbZxCmGlobalCode.setGlobalID(zxCmGlobalCode.getGlobalID());
           // 代码描述
           dbZxCmGlobalCode.setGlobalDesc(zxCmGlobalCode.getGlobalDesc());
           // 是否启用
           dbZxCmGlobalCode.setEnable(zxCmGlobalCode.getEnable());
           // 是否默认选项
           dbZxCmGlobalCode.setSelected(zxCmGlobalCode.getSelected());
           // 编辑时间
           dbZxCmGlobalCode.setEditTime(zxCmGlobalCode.getEditTime());
           // 比率
           dbZxCmGlobalCode.setPercentage(zxCmGlobalCode.getPercentage());
           // 地区
           dbZxCmGlobalCode.setRegion(zxCmGlobalCode.getRegion());
           // 所属机构（项目ID）
           dbZxCmGlobalCode.setProjectId(zxCmGlobalCode.getProjectId());
           // 项目名称
           dbZxCmGlobalCode.setProjectName(zxCmGlobalCode.getProjectName());
           // 所属公司ID
           dbZxCmGlobalCode.setCompanyId(zxCmGlobalCode.getCompanyId());
           // 所属公司Name
           dbZxCmGlobalCode.setCompanyName(zxCmGlobalCode.getCompanyName());
           // 备注
           dbZxCmGlobalCode.setRemarks(zxCmGlobalCode.getRemarks());
           // 排序
           dbZxCmGlobalCode.setSort(zxCmGlobalCode.getSort());
           // 共通
           dbZxCmGlobalCode.setModifyUserInfo(userKey, realName);
           flag = zxCmGlobalCodeMapper.updateByPrimaryKey(dbZxCmGlobalCode);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCmGlobalCode);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCmGlobalCode(List<ZxCmGlobalCode> zxCmGlobalCodeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCmGlobalCodeList != null && zxCmGlobalCodeList.size() > 0) {
           ZxCmGlobalCode zxCmGlobalCode = new ZxCmGlobalCode();
           zxCmGlobalCode.setModifyUserInfo(userKey, realName);
           flag = zxCmGlobalCodeMapper.batchDeleteUpdateZxCmGlobalCode(zxCmGlobalCodeList, zxCmGlobalCode);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCmGlobalCodeList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
