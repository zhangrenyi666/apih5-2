package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaProjectSetItemMapper;
import com.apih5.mybatis.dao.ZxSaProjectSetMapper;
import com.apih5.mybatis.pojo.ZxSaProjectSet;
import com.apih5.mybatis.pojo.ZxSaProjectSetItem;
import com.apih5.service.ZxSaProjectSetService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSaProjectSetService")
public class ZxSaProjectSetServiceImpl implements ZxSaProjectSetService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectSetMapper zxSaProjectSetMapper;

    @Autowired(required = true)
    private ZxSaProjectSetItemMapper zxSaProjectSetItemMapper;
    
    @Override
    public ResponseEntity getZxSaProjectSetListByCondition(ZxSaProjectSet zxSaProjectSet) {
        if (zxSaProjectSet == null) {
            zxSaProjectSet = new ZxSaProjectSet();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSaProjectSet.setComID("");
        	zxSaProjectSet.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSaProjectSet.setComID(zxSaProjectSet.getOrgID());
        	zxSaProjectSet.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSaProjectSet.setOrgID(zxSaProjectSet.getOrgID());//所属单位
        }
        
        // 分页查询
        PageHelper.startPage(zxSaProjectSet.getPage(),zxSaProjectSet.getLimit());
        // 获取数据
        List<ZxSaProjectSet> zxSaProjectSetList = zxSaProjectSetMapper.selectByZxSaProjectSetList(zxSaProjectSet);
        for (ZxSaProjectSet zxSaProjectSet2 : zxSaProjectSetList) {
        	 ZxSaProjectSetItem item = new ZxSaProjectSetItem();
             item.setMainID(zxSaProjectSet2.getId());
             List<ZxSaProjectSetItem> itemList = zxSaProjectSetItemMapper.selectByZxSaProjectSetItemList(item);
             zxSaProjectSet2.setItemList(itemList);
		}
        // 得到分页信息
        PageInfo<ZxSaProjectSet> p = new PageInfo<>(zxSaProjectSetList);

        return repEntity.okList(zxSaProjectSetList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaProjectSetDetails(ZxSaProjectSet zxSaProjectSet) {
        if (zxSaProjectSet == null) {
            zxSaProjectSet = new ZxSaProjectSet();
        }
        // 获取数据
        ZxSaProjectSet dbZxSaProjectSet = zxSaProjectSetMapper.selectByPrimaryKey(zxSaProjectSet.getId());
        // 数据存在
        if (dbZxSaProjectSet != null) {
            return repEntity.ok(dbZxSaProjectSet);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxSaProjectSet(ZxSaProjectSet zxSaProjectSet) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //重复项目校验
        ZxSaProjectSet zxSaProjectSetSelect = new ZxSaProjectSet();
        zxSaProjectSetSelect.setContrType(zxSaProjectSet.getContrType());
        zxSaProjectSetSelect.setOrgID(zxSaProjectSet.getOrgID());
        List<ZxSaProjectSet> zxSaProjectSetList = zxSaProjectSetMapper.selectByZxSaProjectSetList(zxSaProjectSetSelect);
        if(zxSaProjectSetList != null && zxSaProjectSetList.size() >0) {
        	return repEntity.layerMessage("no", "该项目已存在，不用重复添加！");	
        }
        zxSaProjectSet.setId(UuidUtil.generate());
        zxSaProjectSet.setCreateUserInfo(userKey, realName);
        int flag = zxSaProjectSetMapper.insert(zxSaProjectSet);
        if(zxSaProjectSet.getItemList() != null && zxSaProjectSet.getItemList().size() >0) {
        	for (ZxSaProjectSetItem item : zxSaProjectSet.getItemList()) {
        		item.setId(UuidUtil.generate());
        		item.setMainID(zxSaProjectSet.getId());
        		item.setContrType(zxSaProjectSet.getContrType());
        		item.setOrgID(zxSaProjectSet.getOrgID());
        		item.setCreateUserInfo(userKey, realName);
        		flag = zxSaProjectSetItemMapper.insert(item);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxSaProjectSet);
        }
    }

    @Override
    public ResponseEntity updateZxSaProjectSet(ZxSaProjectSet zxSaProjectSet) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaProjectSet dbzxSaProjectSet = zxSaProjectSetMapper.selectByPrimaryKey(zxSaProjectSet.getId());
        if (dbzxSaProjectSet != null && StrUtil.isNotEmpty(dbzxSaProjectSet.getId())) {
//           // 类别
//           dbzxSaProjectSet.setContrType(zxSaProjectSet.getContrType());
//           // 项目id
//           dbzxSaProjectSet.setOrgID(zxSaProjectSet.getOrgID());
//           // 项目名称
//           dbzxSaProjectSet.setOrgName(zxSaProjectSet.getOrgName());
//           // 
//           dbzxSaProjectSet.setComID(zxSaProjectSet.getComID());
//           // 
//           dbzxSaProjectSet.setComName(zxSaProjectSet.getComName());
//           // 
//           dbzxSaProjectSet.setComOrders(zxSaProjectSet.getComOrders());
           // 共通
           dbzxSaProjectSet.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectSetMapper.updateByPrimaryKey(dbzxSaProjectSet);
           //先删除再新增
           ZxSaProjectSetItem delItem = new ZxSaProjectSetItem();
           delItem.setMainID(dbzxSaProjectSet.getId());
           delItem.setContrTypeFlag1(zxSaProjectSet.getContrTypeFlag1());
           delItem.setContrTypeFlag2(zxSaProjectSet.getContrTypeFlag2());
           delItem.setContrTypeFlag3(zxSaProjectSet.getContrTypeFlag3());
           delItem.setContrTypeFlag4(zxSaProjectSet.getContrTypeFlag4());
           List<ZxSaProjectSetItem> delItemList = zxSaProjectSetItemMapper.selectByZxSaProjectSetItemList(delItem);
           if(delItemList != null &&delItemList.size() >0) {
        	   flag = zxSaProjectSetItemMapper.batchDeleteUpdateZxSaProjectSetItem(delItemList, delItem);
           }
           if(zxSaProjectSet.getItemList() != null && zxSaProjectSet.getItemList().size() >0) {
        	   for (ZxSaProjectSetItem item : zxSaProjectSet.getItemList()) {
        		   item.setId(UuidUtil.generate());
        		   item.setMainID(zxSaProjectSet.getId());
        		   item.setContrType(zxSaProjectSet.getContrType());
        		   item.setOrgID(dbzxSaProjectSet.getOrgID());
        		   item.setCreateUserInfo(userKey, realName);
        		   flag = zxSaProjectSetItemMapper.insert(item);
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSaProjectSet);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectSet(List<ZxSaProjectSet> zxSaProjectSetList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectSetList != null && zxSaProjectSetList.size() > 0) {
        	//删除
        	for (ZxSaProjectSet zxSaProjectSet : zxSaProjectSetList) {
        		ZxSaProjectSetItem delItemTotal = new ZxSaProjectSetItem();
        		delItemTotal.setMainID(zxSaProjectSet.getId());
        		List<ZxSaProjectSetItem> delItemTotalList = zxSaProjectSetItemMapper.selectByZxSaProjectSetItemList(delItemTotal);
        		if(delItemTotalList != null && delItemTotalList.size() >0) {
        			ZxSaProjectSetItem delItem = new ZxSaProjectSetItem();
        			delItem.setMainID(zxSaProjectSet.getId());
        			delItem.setContrTypeFlag1(zxSaProjectSet.getContrTypeFlag1());
        			delItem.setContrTypeFlag2(zxSaProjectSet.getContrTypeFlag2());
        			delItem.setContrTypeFlag3(zxSaProjectSet.getContrTypeFlag3());
        			delItem.setContrTypeFlag4(zxSaProjectSet.getContrTypeFlag4());
        			List<ZxSaProjectSetItem> delItemList = zxSaProjectSetItemMapper.selectByZxSaProjectSetItemList(delItem);
        			if(delItemList != null &&delItemList.size() >0) {
        				if(delItemTotalList.size()  != delItemList.size()) {
        					 return repEntity.layerMessage("no", "包括被结算引用的数据。不能删除，请重新选择！");
        				}
        			}
        		}
        	}
        	
        	
         	for (ZxSaProjectSet zxSaProjectSet : zxSaProjectSetList) {
        		ZxSaProjectSetItem delItem = new ZxSaProjectSetItem();
        		 delItem.setMainID(zxSaProjectSet.getId());
        		 delItem.setContrTypeFlag1(zxSaProjectSet.getContrTypeFlag1());
                 delItem.setContrTypeFlag2(zxSaProjectSet.getContrTypeFlag2());
                 delItem.setContrTypeFlag3(zxSaProjectSet.getContrTypeFlag3());
                 delItem.setContrTypeFlag4(zxSaProjectSet.getContrTypeFlag4());
        		List<ZxSaProjectSetItem> delItemList = zxSaProjectSetItemMapper.selectByZxSaProjectSetItemList(delItem);
        		if(delItemList != null &&delItemList.size() >0) {
        			flag = zxSaProjectSetItemMapper.batchDeleteUpdateZxSaProjectSetItem(delItemList, delItem);
        		}
        	}
        	
        	ZxSaProjectSet zxSaProjectSet = new ZxSaProjectSet();
           zxSaProjectSet.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectSetMapper.batchDeleteUpdateZxSaProjectSet(zxSaProjectSetList, zxSaProjectSet);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxSaProjectSetList);
        }
    }
}
