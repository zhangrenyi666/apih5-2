package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjConsumableInventoryMapper;
import com.apih5.mybatis.dao.ZjConsumablePutMapper;
import com.apih5.mybatis.pojo.ZjConsumableInventory;
import com.apih5.mybatis.pojo.ZjConsumablePut;
import com.apih5.service.ZjConsumablePutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjConsumablePutService")
public class ZjConsumablePutServiceImpl implements ZjConsumablePutService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjConsumablePutMapper zjConsumablePutMapper;
    
    @Autowired(required = true)
    private ZjConsumableInventoryMapper zjConsumableInventoryMapper;

    @Override
    public ResponseEntity getZjConsumablePutListByCondition(ZjConsumablePut zjConsumablePut) {
    	if (zjConsumablePut == null) {
    		zjConsumablePut = new ZjConsumablePut();
    	}
    	if(zjConsumablePut.getPutTimeSearch() != null && zjConsumablePut.getPutTimeSearch().size() >1) {
    		zjConsumablePut.setStartTime(zjConsumablePut.getPutTimeSearch().get(0));
    		zjConsumablePut.setEndTime(zjConsumablePut.getPutTimeSearch().get(1));
    	}
    	// 分页查询
        PageHelper.startPage(zjConsumablePut.getPage(),zjConsumablePut.getLimit());
        // 获取数据
        List<ZjConsumablePut> zjConsumablePutList = zjConsumablePutMapper.selectByZjConsumablePutList(zjConsumablePut);
        // 得到分页信息
        PageInfo<ZjConsumablePut> p = new PageInfo<>(zjConsumablePutList);

        return repEntity.okList(zjConsumablePutList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjConsumablePutDetails(ZjConsumablePut zjConsumablePut) {
        if (zjConsumablePut == null) {
            zjConsumablePut = new ZjConsumablePut();
        }
        // 获取数据
        ZjConsumablePut dbZjConsumablePut = zjConsumablePutMapper.selectByPrimaryKey(zjConsumablePut.getPutId());
        // 数据存在
        if (dbZjConsumablePut != null) {
            return repEntity.ok(dbZjConsumablePut);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjConsumablePut(ZjConsumablePut zjConsumablePut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjConsumablePut.setPutId(UuidUtil.generate());
        zjConsumablePut.setCreateUserInfo(userKey, realName);
        int flag = zjConsumablePutMapper.insert(zjConsumablePut);
        
        //每增加一条入库数据，库存表中总入库量相应增加
        ZjConsumableInventory inventory = new ZjConsumableInventory();
        inventory.setSetId(zjConsumablePut.getSetId());
        List<ZjConsumableInventory> inventoryList = zjConsumableInventoryMapper.selectByZjConsumableInventoryList(inventory);
        int nowNum = 0;
        int totalInNum = 0;
        if(inventoryList != null && inventoryList.size() >0) {
        	nowNum = inventoryList.get(0).getNowNum()+ zjConsumablePut.getThisAddNum();
        	totalInNum = inventoryList.get(0).getTotalInNum()+ zjConsumablePut.getThisAddNum();
        	inventoryList.get(0).setNowNum(nowNum);
        	inventoryList.get(0).setTotalInNum(totalInNum);
        	inventoryList.get(0).setModifyUserInfo(userKey, realName);
        	zjConsumableInventoryMapper.updateByPrimaryKey(inventoryList.get(0));
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjConsumablePut);
        }
    }

    @Override
    public ResponseEntity updateZjConsumablePut(ZjConsumablePut zjConsumablePut) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjConsumablePut dbzjConsumablePut = zjConsumablePutMapper.selectByPrimaryKey(zjConsumablePut.getPutId());
        if (dbzjConsumablePut != null && StrUtil.isNotEmpty(dbzjConsumablePut.getPutId())) {
        	int thisAddNumOld = dbzjConsumablePut.getThisAddNum();
        	// 本次增加数量
        	dbzjConsumablePut.setThisAddNum(zjConsumablePut.getThisAddNum());
        	// 入库时间
        	dbzjConsumablePut.setPutTime(zjConsumablePut.getPutTime());
        	// 备注
        	dbzjConsumablePut.setRemark(zjConsumablePut.getRemark());
        	// 共通
        	dbzjConsumablePut.setModifyUserInfo(userKey, realName);
        	flag = zjConsumablePutMapper.updateByPrimaryKey(dbzjConsumablePut);
        	//每修改一条入库数据，库存表中总入库量相应修改
        	ZjConsumableInventory inventory = new ZjConsumableInventory();
        	inventory.setSetId(dbzjConsumablePut.getSetId());
        	List<ZjConsumableInventory> inventoryList = zjConsumableInventoryMapper.selectByZjConsumableInventoryList(inventory);
        	int nowNum = 0;
        	int totalInNum = 0;
        	if(inventoryList != null && inventoryList.size() >0) {
        		nowNum = inventoryList.get(0).getNowNum()+ dbzjConsumablePut.getThisAddNum()-thisAddNumOld;
        		totalInNum = inventoryList.get(0).getTotalInNum()+ dbzjConsumablePut.getThisAddNum()-thisAddNumOld;
        		inventoryList.get(0).setNowNum(nowNum);
        		inventoryList.get(0).setTotalInNum(totalInNum);
        		inventoryList.get(0).setModifyUserInfo(userKey, realName);
        		zjConsumableInventoryMapper.updateByPrimaryKey(inventoryList.get(0));
        	}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjConsumablePut);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjConsumablePut(List<ZjConsumablePut> zjConsumablePutList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjConsumablePutList != null && zjConsumablePutList.size() > 0) {
        	for (ZjConsumablePut zjConsumablePut : zjConsumablePutList) {
        		//每修改一条入库数据，库存表中总入库量相应修改
            	ZjConsumableInventory inventory = new ZjConsumableInventory();
            	inventory.setSetId(zjConsumablePut.getSetId());
            	List<ZjConsumableInventory> inventoryList = zjConsumableInventoryMapper.selectByZjConsumableInventoryList(inventory);
            	int nowNum = 0;
            	int totalInNum = 0;
            	if(inventoryList != null && inventoryList.size() >0) {
            		nowNum = inventoryList.get(0).getNowNum()- zjConsumablePut.getThisAddNum();
            		totalInNum = inventoryList.get(0).getTotalInNum()- zjConsumablePut.getThisAddNum();
            		inventoryList.get(0).setNowNum(nowNum);
            		inventoryList.get(0).setTotalInNum(totalInNum);
            		inventoryList.get(0).setModifyUserInfo(userKey, realName);
            		flag = zjConsumableInventoryMapper.updateByPrimaryKey(inventoryList.get(0));
            	}	
			}
        	ZjConsumablePut zjConsumablePut = new ZjConsumablePut();
        	zjConsumablePut.setModifyUserInfo(userKey, realName);
        	flag = zjConsumablePutMapper.batchDeleteUpdateZjConsumablePut(zjConsumablePutList, zjConsumablePut);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorDelete();
        }
        else {
            return repEntity.ok("sys.data.delete",zjConsumablePutList);
        }
    }
    
}