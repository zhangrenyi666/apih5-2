package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjConsumableApplyMapper;
import com.apih5.mybatis.dao.ZjConsumableInventoryMapper;
import com.apih5.mybatis.dao.ZjConsumableSetMapper;
import com.apih5.mybatis.pojo.ZjConsumableApply;
import com.apih5.mybatis.pojo.ZjConsumableInventory;
import com.apih5.mybatis.pojo.ZjConsumableSet;
import com.apih5.service.ZjConsumableSetService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjConsumableSetService")
public class ZjConsumableSetServiceImpl implements ZjConsumableSetService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjConsumableSetMapper zjConsumableSetMapper;
    
    @Autowired(required = true)
    private ZjConsumableInventoryMapper zjConsumableInventoryMapper;
    
    @Autowired(required = true)
    private ZjConsumableApplyMapper zjConsumableApplyMapper;

    @Override
    public ResponseEntity getZjConsumableSetListByCondition(ZjConsumableSet zjConsumableSet) {
        if (zjConsumableSet == null) {
            zjConsumableSet = new ZjConsumableSet();
        }
        // 分页查询
        PageHelper.startPage(zjConsumableSet.getPage(),zjConsumableSet.getLimit());
        // 获取数据
        List<ZjConsumableSet> zjConsumableSetList = zjConsumableSetMapper.selectByZjConsumableSetList(zjConsumableSet);
        if(zjConsumableSetList != null && zjConsumableSetList.size() >0) {
        	for (ZjConsumableSet set : zjConsumableSetList) {
        		ZjConsumableSet brand = new ZjConsumableSet();
        		brand.setCodePid(set.getSetId());
        		brand.setUseState("0");
        		List<ZjConsumableSet> brandList = zjConsumableSetMapper.selectByZjConsumableSetList(brand);
        		if(brandList != null && brandList.size() >0) {
        			for (ZjConsumableSet braSet : brandList) {
        				ZjConsumableSet model = new ZjConsumableSet();
        				model.setUseState("0");
        				model.setCodePid(braSet.getSetId());
                		List<ZjConsumableSet> modelList = zjConsumableSetMapper.selectByZjConsumableSetList(model);
                		if(modelList != null && modelList.size() >0) {
                			for (ZjConsumableSet moSet : modelList) {
                				ZjConsumableSet colour = new ZjConsumableSet();
                				colour.setUseState("0");
                				colour.setCodePid(moSet.getSetId());
                        		List<ZjConsumableSet> colourList = zjConsumableSetMapper.selectByZjConsumableSetList(colour);
                        		moSet.setChildrenList(colourList);
							}
                		}
                		braSet.setChildrenList(modelList);
					}
        		}
        		set.setChildrenList(brandList);
			}
        }
        // 得到分页信息
        PageInfo<ZjConsumableSet> p = new PageInfo<>(zjConsumableSetList);

        return repEntity.okList(zjConsumableSetList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjConsumableSetDetails(ZjConsumableSet zjConsumableSet) {
        if (zjConsumableSet == null) {
            zjConsumableSet = new ZjConsumableSet();
        }
        // 获取数据
        ZjConsumableSet dbZjConsumableSet = zjConsumableSetMapper.selectByPrimaryKey(zjConsumableSet.getSetId());
        // 数据存在
        if (dbZjConsumableSet != null) {
            return repEntity.ok(dbZjConsumableSet);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjConsumableSet(ZjConsumableSet zjConsumableSet) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        
        
        String realName = TokenUtils.getRealName(request);
        //重复性校验
        ZjConsumableSet set = new ZjConsumableSet();
        set.setCategoryEq(zjConsumableSet.getCategory());
        set.setBrandEq(zjConsumableSet.getBrand());
        set.setModelEq(zjConsumableSet.getModel());
        set.setColourEq(zjConsumableSet.getColour());
        List<ZjConsumableSet> setList = zjConsumableSetMapper.selectByZjConsumableSetList(set);
        if(setList != null && setList.size() >0) {
        	 return repEntity.layerMessage("no", "该耗材已经添加，请重新添加！");
        }
        
        
        if(StrUtil.equals(zjConsumableSet.getTypeFlag(), "0") || StrUtil.equals(zjConsumableSet.getTypeFlag(), "1") || StrUtil.equals(zjConsumableSet.getTypeFlag(), "2") ) {
        	zjConsumableSet.setUseState("0");
        }
        zjConsumableSet.setSetId(UuidUtil.generate());
        zjConsumableSet.setCreateUserInfo(userKey, realName);
        int flag = zjConsumableSetMapper.insert(zjConsumableSet);
        
        //1、每增加一种耗材，库存表中相应增加一条数据
        if(StrUtil.equals(zjConsumableSet.getTypeFlag(), "3")) {
        	ZjConsumableInventory addInventory = new ZjConsumableInventory();
        	BeanUtil.copyProperties(zjConsumableSet, addInventory);
        	addInventory.setInventoryId(UuidUtil.generate());
        	addInventory.setNowNum(0);
        	addInventory.setTotalInNum(0);
        	addInventory.setTotalUseNum(0);
        	flag =  zjConsumableInventoryMapper.insert(addInventory);
        }
        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjConsumableSet);
        }
    }

    @Override
    public ResponseEntity updateZjConsumableSet(ZjConsumableSet zjConsumableSet) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjConsumableSet dbzjConsumableSet = zjConsumableSetMapper.selectByPrimaryKey(zjConsumableSet.getSetId());
        if (dbzjConsumableSet != null && StrUtil.isNotEmpty(dbzjConsumableSet.getSetId())) {
           // 使用状态
           dbzjConsumableSet.setUseState(zjConsumableSet.getUseState());
           // 共通
           dbzjConsumableSet.setModifyUserInfo(userKey, realName);
           flag = zjConsumableSetMapper.updateByPrimaryKey(dbzjConsumableSet);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjConsumableSet);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjConsumableSet(List<ZjConsumableSet> zjConsumableSetList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjConsumableSetList != null && zjConsumableSetList.size() > 0) {
        	//删除校验
        	for (ZjConsumableSet zjConsumableSet : zjConsumableSetList) {
        		ZjConsumableApply apply = new ZjConsumableApply();
        		apply.setSetId(zjConsumableSet.getSetId());
        		List<ZjConsumableApply> applyList = zjConsumableApplyMapper.selectByZjConsumableApplyList(apply);
        		if(applyList != null && applyList.size() >0) {
        			return repEntity.layerMessage("no", "包括已经申领的耗材，请重新选择！");
        		}
			}
        	
        	for (ZjConsumableSet zjConsumableSet : zjConsumableSetList) {
        		//同时删除库存表
        		ZjConsumableInventory delInventory = new ZjConsumableInventory();
        		delInventory.setSetId(zjConsumableSet.getSetId());
        		List<ZjConsumableInventory> delInventoryList = zjConsumableInventoryMapper.selectByZjConsumableInventoryList(delInventory);
        		if(delInventoryList != null && delInventoryList.size() >0) {
        			delInventory.setModifyUserInfo(userKey, realName);
        			flag = zjConsumableInventoryMapper.batchDeleteUpdateZjConsumableInventory(delInventoryList, delInventory);
        		}
        	}
        	ZjConsumableSet zjConsumableSet = new ZjConsumableSet();
        	zjConsumableSet.setModifyUserInfo(userKey, realName);
        	flag = zjConsumableSetMapper.batchDeleteUpdateZjConsumableSet(zjConsumableSetList, zjConsumableSet);
        
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorDelete();
        }
        else {
            return repEntity.ok("sys.data.delete",zjConsumableSetList);
        }
    }
    
}