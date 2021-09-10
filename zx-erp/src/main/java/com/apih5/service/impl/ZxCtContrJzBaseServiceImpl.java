package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtContrJzBaseMapper;
import com.apih5.mybatis.dao.ZxCtContrJzItemBaseMapper;
import com.apih5.mybatis.pojo.ZxCtContrJzBase;
import com.apih5.mybatis.pojo.ZxCtContrJzItemBase;
import com.apih5.service.ZxCtContrJzBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxCtContrJzBaseService")
public class ZxCtContrJzBaseServiceImpl implements ZxCtContrJzBaseService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrJzBaseMapper zxCtContrJzBaseMapper;

    @Autowired(required = true)
    private ZxCtContrJzItemBaseMapper zxCtContrJzItemBaseMapper;

    @Override
    public ResponseEntity getZxCtContrJzBaseListByCondition(ZxCtContrJzBase zxCtContrJzBase) {
        if (zxCtContrJzBase == null) {
            zxCtContrJzBase = new ZxCtContrJzBase();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtContrJzBase.setComID("");
        	zxCtContrJzBase.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtContrJzBase.setComID(zxCtContrJzBase.getOrgID());
        	zxCtContrJzBase.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtContrJzBase.setOrgID(zxCtContrJzBase.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxCtContrJzBase.getPage(),zxCtContrJzBase.getLimit());
        // 获取数据
        List<ZxCtContrJzBase> zxCtContrJzBaseList = zxCtContrJzBaseMapper.selectByZxCtContrJzBaseList(zxCtContrJzBase);
        for (ZxCtContrJzBase zxCtContrJzBase2 : zxCtContrJzBaseList) {
        	ZxCtContrJzItemBase itemBase = new ZxCtContrJzItemBase();
    		itemBase.setMainID(zxCtContrJzBase2.getId());
    		List<ZxCtContrJzItemBase> zxCtContrJzItemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(itemBase);
    		zxCtContrJzBase2.setZxCtContrJzItemBaseList(zxCtContrJzItemBaseList);
        }
        // 得到分页信息
        PageInfo<ZxCtContrJzBase> p = new PageInfo<>(zxCtContrJzBaseList);

        return repEntity.okList(zxCtContrJzBaseList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrJzBaseDetail(ZxCtContrJzBase zxCtContrJzBase) {
        if (zxCtContrJzBase == null) {
            zxCtContrJzBase = new ZxCtContrJzBase();
        }
        // 获取数据
        ZxCtContrJzBase dbZxCtContrJzBase = zxCtContrJzBaseMapper.selectByPrimaryKey(zxCtContrJzBase.getId());
        // 数据存在
        if (dbZxCtContrJzBase != null) {
            return repEntity.ok(dbZxCtContrJzBase);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrJzBase(ZxCtContrJzBase zxCtContrJzBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //项目的重复校验
        ZxCtContrJzBase base = new ZxCtContrJzBase();
        base.setOrgID(zxCtContrJzBase.getOrgID());
        base.setType(zxCtContrJzBase.getType());
        List<ZxCtContrJzBase> baseList = zxCtContrJzBaseMapper.selectByZxCtContrJzBaseList(base);
        if(baseList != null && baseList.size() >0) {
        	 return repEntity.layerMessage("no", "此项目已经存在过此类基础数据，不能重复添加！");
        }
        //
        zxCtContrJzBase.setId(UuidUtil.generate());
        zxCtContrJzBase.setCreateUserInfo(userKey, realName);
        
        ZxCtContrJzItemBase itemBase = new ZxCtContrJzItemBase();
        itemBase.setMainID("0");
        itemBase.setJzType(zxCtContrJzBase.getType());
        List<ZxCtContrJzItemBase> itemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(itemBase);
        if(itemBaseList != null && itemBaseList.size() >0) {
        	for (ZxCtContrJzItemBase zxCtContrJzItemBase : itemBaseList) {
        		ZxCtContrJzItemBase copyItemBase = new ZxCtContrJzItemBase();
        		BeanUtil.copyProperties(zxCtContrJzItemBase, copyItemBase);
        		copyItemBase.setId(UuidUtil.generate());
        		copyItemBase.setMainID(zxCtContrJzBase.getId());
        		copyItemBase.setCreateUserInfo(userKey, realName);
        		flag = zxCtContrJzItemBaseMapper.insert(copyItemBase);

        	}
        }
        flag = zxCtContrJzBaseMapper.insert(zxCtContrJzBase);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrJzBase);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrJzBase(ZxCtContrJzBase zxCtContrJzBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrJzBase dbZxCtContrJzBase = zxCtContrJzBaseMapper.selectByPrimaryKey(zxCtContrJzBase.getId());
        if (dbZxCtContrJzBase != null && StrUtil.isNotEmpty(dbZxCtContrJzBase.getId())) {
//           // 项目名称id
//           dbZxCtContrJzBase.setOrgID(zxCtContrJzBase.getOrgID());
//           // 项目名称
//           dbZxCtContrJzBase.setOrgName(zxCtContrJzBase.getOrgName());
//           // 类型
//           dbZxCtContrJzBase.setType(zxCtContrJzBase.getType());
           // 备注
           dbZxCtContrJzBase.setRemarks(zxCtContrJzBase.getRemarks());
//           // 清单明细List
//           dbZxCtContrJzBase.setZxCtContrJzItemBaseList(zxCtContrJzBase.getZxCtContrJzItemBaseList());
           // 共通
           dbZxCtContrJzBase.setModifyUserInfo(userKey, realName);
           flag = zxCtContrJzBaseMapper.updateByPrimaryKey(dbZxCtContrJzBase);
        	//先删除
    		ZxCtContrJzItemBase delItemBase = new ZxCtContrJzItemBase();
    		delItemBase.setMainID(zxCtContrJzBase.getId());
    		List<ZxCtContrJzItemBase> delItemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(delItemBase);
    		if(delItemBaseList != null &&delItemBaseList.size() >0) {
    			delItemBase.setModifyUserInfo(userKey, realName);
    			flag = zxCtContrJzItemBaseMapper.batchDeleteUpdateZxCtContrJzItemBase(delItemBaseList, delItemBase);
    		}
    		//再新增
    		 ZxCtContrJzItemBase itemBase = new ZxCtContrJzItemBase();
    	        itemBase.setMainID("0");
    	        itemBase.setJzType(dbZxCtContrJzBase.getType());
    	        List<ZxCtContrJzItemBase> itemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(itemBase);
    		if(itemBaseList != null && itemBaseList.size() >0) {
    			for (ZxCtContrJzItemBase zxCtContrJzItemBase : itemBaseList) {
    				zxCtContrJzItemBase.setId(UuidUtil.generate());
    				zxCtContrJzItemBase.setMainID(dbZxCtContrJzBase.getId());
    				zxCtContrJzItemBase.setCreateUserInfo(userKey, realName);
    				flag = zxCtContrJzItemBaseMapper.insert(zxCtContrJzItemBase);
				}
    		}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtContrJzBase);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrJzBase(List<ZxCtContrJzBase> zxCtContrJzBaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrJzBaseList != null && zxCtContrJzBaseList.size() > 0) {
        	for (ZxCtContrJzBase zxCtContrJzBase : zxCtContrJzBaseList) {
        		ZxCtContrJzItemBase delItemBase = new ZxCtContrJzItemBase();
        		delItemBase.setMainID(zxCtContrJzBase.getId());
        		List<ZxCtContrJzItemBase> delItemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(delItemBase);
        		if(delItemBaseList != null &&delItemBaseList.size() >0) {
        			delItemBase.setModifyUserInfo(userKey, realName);
        			flag = zxCtContrJzItemBaseMapper.batchDeleteUpdateZxCtContrJzItemBase(delItemBaseList, delItemBase);
        		}
        	}
        	ZxCtContrJzBase zxCtContrJzBase = new ZxCtContrJzBase();
           zxCtContrJzBase.setModifyUserInfo(userKey, realName);
           flag = zxCtContrJzBaseMapper.batchDeleteUpdateZxCtContrJzBase(zxCtContrJzBaseList, zxCtContrJzBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtContrJzBaseList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity editZxCtContrJzBaseItem(ZxCtContrJzBase zxCtContrJzBase) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxCtContrJzBase dbZxCtContrJzBase = zxCtContrJzBaseMapper.selectByPrimaryKey(zxCtContrJzBase.getId());
    	if (dbZxCtContrJzBase != null && StrUtil.isNotEmpty(dbZxCtContrJzBase.getId())) {
    		//先删除
    		ZxCtContrJzItemBase delItemBase = new ZxCtContrJzItemBase();
    		delItemBase.setMainID(zxCtContrJzBase.getId());
    		List<ZxCtContrJzItemBase> delItemBaseList = zxCtContrJzItemBaseMapper.selectByZxCtContrJzItemBaseList(delItemBase);
    		if(delItemBaseList != null &&delItemBaseList.size() >0) {
    			delItemBase.setModifyUserInfo(userKey, realName);
    			flag = zxCtContrJzItemBaseMapper.batchDeleteUpdateZxCtContrJzItemBase(delItemBaseList, delItemBase);
    		}
    		//再新增
    		if(zxCtContrJzBase.getZxCtContrJzItemBaseList() != null && zxCtContrJzBase.getZxCtContrJzItemBaseList().size() >0) {
    			for (ZxCtContrJzItemBase zxCtContrJzItemBase : zxCtContrJzBase.getZxCtContrJzItemBaseList()) {
    				zxCtContrJzItemBase.setId(UuidUtil.generate());
    				zxCtContrJzItemBase.setMainID(dbZxCtContrJzBase.getId());
    				zxCtContrJzItemBase.setCreateUserInfo(userKey, realName);
    				flag = zxCtContrJzItemBaseMapper.insert(zxCtContrJzItemBase);
				}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	} else {
    		return repEntity.ok("sys.data.update",zxCtContrJzBase);
    	}
    }
}
