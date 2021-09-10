package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjConsumableApplyBookMapper;
import com.apih5.mybatis.dao.ZjConsumableApplyMapper;
import com.apih5.mybatis.dao.ZjConsumableInventoryMapper;
import com.apih5.mybatis.pojo.ZjConsumableApply;
import com.apih5.mybatis.pojo.ZjConsumableApplyBook;
import com.apih5.mybatis.pojo.ZjConsumableInventory;
import com.apih5.service.ZjConsumableApplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("zjConsumableApplyService")
public class ZjConsumableApplyServiceImpl implements ZjConsumableApplyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjConsumableApplyMapper zjConsumableApplyMapper;
    
    @Autowired(required = true)
    private ZjConsumableApplyBookMapper zjConsumableApplyBookMapper;

    @Autowired(required = true)
    private ZjConsumableInventoryMapper zjConsumableInventoryMapper;
    
    @Autowired(required = true)
	private WeChatEnterpriseService weChatEnterpriseService;
    
    @Override
    public ResponseEntity getZjConsumableApplyListByCondition(ZjConsumableApply zjConsumableApply) {
        if (zjConsumableApply == null) {
            zjConsumableApply = new ZjConsumableApply();
        }
        // 分页查询
        PageHelper.startPage(zjConsumableApply.getPage(),zjConsumableApply.getLimit());
        // 获取数据
        List<ZjConsumableApply> zjConsumableApplyList = zjConsumableApplyMapper.selectByZjConsumableApplyList(zjConsumableApply);
        // 得到分页信息
        PageInfo<ZjConsumableApply> p = new PageInfo<>(zjConsumableApplyList);

        return repEntity.okList(zjConsumableApplyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjConsumableApplyDetails(ZjConsumableApply zjConsumableApply) {
        if (zjConsumableApply == null) {
            zjConsumableApply = new ZjConsumableApply();
        }
        // 获取数据
        ZjConsumableApply dbZjConsumableApply = zjConsumableApplyMapper.selectByPrimaryKey(zjConsumableApply.getApplyId());
        // 数据存在
        if (dbZjConsumableApply != null) {
        	//计算最多可领取数量====用库存表里的总数量-审批通过未申领的数量
        	int leftNum = 0;
        	ZjConsumableInventory inventory = new ZjConsumableInventory();
        	inventory.setSetId(dbZjConsumableApply.getSetId());
        	List<ZjConsumableInventory> inventoryList = zjConsumableInventoryMapper.selectByZjConsumableInventoryList(inventory);
        	if(inventoryList != null && inventoryList.size() >0) {
        		if(inventoryList.get(0).getAppNum() == null) {
        			inventoryList.get(0).setAppNum(0);	
        		}
        		if(inventoryList.get(0).getTotalInNum() == null) {
        			inventoryList.get(0).setTotalInNum(0);	
        		}
        		leftNum = inventoryList.get(0).getTotalInNum()-inventoryList.get(0).getAppNum();
        	}
        	dbZjConsumableApply.setLeftNum(leftNum);
            return repEntity.ok(dbZjConsumableApply);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjConsumableApply(ZjConsumableApply zjConsumableApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjConsumableApply.setApplyId(UuidUtil.generate());
        // 审批状态
        zjConsumableApply.setStatus("0");
        // 领用状态
        zjConsumableApply.setUseStatus("0");
        zjConsumableApply.setCreateUserInfo(userKey, realName);
        int flag = zjConsumableApplyMapper.insert(zjConsumableApply);
        
        //每进行一次申领==PC端耗材申领台账相应生成一条数据
        ZjConsumableApplyBook applyBook = new ZjConsumableApplyBook();
        BeanUtil.copyProperties(zjConsumableApply, applyBook);
        applyBook.setApplyBookId(UuidUtil.generate());
        applyBook.setCategory(zjConsumableApply.getCategoryName());
        applyBook.setBrand(zjConsumableApply.getBrandName());
        applyBook.setModel(zjConsumableApply.getModelName());
        applyBook.setColour(zjConsumableApply.getColourName());
        // 审批时间
        applyBook.setApplyDate(null);
        // 领用时间
        applyBook.setUseDate(null);
        flag = zjConsumableApplyBookMapper.insert(applyBook);
        
        //给审批人发消息
        String url = "http://weixin.fheb.cn:99/consumableMobile/#/consumableMobile/ConsumablesSettingShenPi";
		String msg = "<a href=\"" + url+ "\">" + realName+"发起了耗材领用申领，请点击查看详情" + "</a>";
		String msgAccountId = "zj_qyh_woa_id_0";
		JSONObject sendResult = weChatEnterpriseService.sendWeChatEnterpriseMsgText(msgAccountId, 1, "ygj_chenyy", msg);
	
        
        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjConsumableApply);
        }
    }

    @Override
    public ResponseEntity updateZjConsumableApply(ZjConsumableApply zjConsumableApply) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjConsumableApply dbzjConsumableApply = zjConsumableApplyMapper.selectByPrimaryKey(zjConsumableApply.getApplyId());
        if (dbzjConsumableApply != null && StrUtil.isNotEmpty(dbzjConsumableApply.getApplyId())) {
           // 设置id
           dbzjConsumableApply.setSetId(zjConsumableApply.getSetId());
           // 申请部门
           dbzjConsumableApply.setDeptName(zjConsumableApply.getDeptName());
           // 申请人
           dbzjConsumableApply.setName(zjConsumableApply.getName());
           // 申请时间
           dbzjConsumableApply.setAppDate(zjConsumableApply.getAppDate());
           // 申领数量
           dbzjConsumableApply.setApplyNum(zjConsumableApply.getApplyNum());
           // 当前最多可领用数量
           dbzjConsumableApply.setMostNum(zjConsumableApply.getMostNum());
           // 共通
           dbzjConsumableApply.setModifyUserInfo(userKey, realName);
           flag = zjConsumableApplyMapper.updateByPrimaryKey(dbzjConsumableApply);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjConsumableApply);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjConsumableApply(List<ZjConsumableApply> zjConsumableApplyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjConsumableApplyList != null && zjConsumableApplyList.size() > 0) {
           ZjConsumableApply zjConsumableApply = new ZjConsumableApply();
           zjConsumableApply.setModifyUserInfo(userKey, realName);
           flag = zjConsumableApplyMapper.batchDeleteUpdateZjConsumableApply(zjConsumableApplyList, zjConsumableApply);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjConsumableApplyList);
        }
    }

	@Override
	public ResponseEntity approveZjConsumableApply(ZjConsumableApply zjConsumableApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjConsumableApply dbzjConsumableApply = zjConsumableApplyMapper.selectByPrimaryKey(zjConsumableApply.getApplyId());
        if (dbzjConsumableApply != null && StrUtil.isNotEmpty(dbzjConsumableApply.getApplyId())) {
           // 申领数量
           dbzjConsumableApply.setApplyNum(zjConsumableApply.getApplyNum());
           // 审批状态
           dbzjConsumableApply.setStatus(zjConsumableApply.getStatus());//
           // 共通
           dbzjConsumableApply.setModifyUserInfo(userKey, realName);
           flag = zjConsumableApplyMapper.updateByPrimaryKey(dbzjConsumableApply);
        
           
           ZjConsumableApplyBook applyBook = new ZjConsumableApplyBook();
           applyBook.setApplyId(dbzjConsumableApply.getApplyId());
           List<ZjConsumableApplyBook> applyBookList = zjConsumableApplyBookMapper.selectByZjConsumableApplyBookList(applyBook);
           if(applyBookList != null && applyBookList.size() >0) {
        	   applyBookList.get(0).setStatus(zjConsumableApply.getStatus());
        	   applyBookList.get(0).setApplyDate(new Date());
        	   applyBookList.get(0).setModifyUserInfo(userKey, realName);
        	   flag = zjConsumableApplyBookMapper.updateByPrimaryKey(applyBookList.get(0));
           }
           
           //计算审批通过未领用的数量
           if(StrUtil.equals("1", zjConsumableApply.getStatus())) {
        	   int appNum = 0;
        	   ZjConsumableInventory inventory = new ZjConsumableInventory();
        	   inventory.setSetId(dbzjConsumableApply.getSetId());
        	   List<ZjConsumableInventory> inventoryList = zjConsumableInventoryMapper.selectByZjConsumableInventoryList(inventory);
        	   if(inventoryList != null && inventoryList.size() >0) {
        		   
        		   if(inventoryList.get(0).getAppNum() == null) {
        			   inventoryList.get(0).setAppNum(0);	
        		   }

        		   appNum = inventoryList.get(0).getAppNum() + zjConsumableApply.getApplyNum();
        		   inventoryList.get(0).setAppNum(appNum);
        		   inventoryList.get(0).setModifyUserInfo(userKey, realName);
        		   flag = zjConsumableInventoryMapper.updateByPrimaryKey(inventoryList.get(0));
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjConsumableApply);
        }
	}

	@Override
	public ResponseEntity useZjConsumableApply(ZjConsumableApply zjConsumableApply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjConsumableApply dbzjConsumableApply = zjConsumableApplyMapper.selectByPrimaryKey(zjConsumableApply.getApplyId());
        if (dbzjConsumableApply != null && StrUtil.isNotEmpty(dbzjConsumableApply.getApplyId())) {
        	// 领用状态
        	dbzjConsumableApply.setUseStatus("1");//已领用
        	// 共通
        	dbzjConsumableApply.setModifyUserInfo(userKey, realName);
           flag = zjConsumableApplyMapper.updateByPrimaryKey(dbzjConsumableApply);
       
           //3、领用确认后，“已领用”（领用状态）显示，
           //保存领用确认时间到PC端耗材领用台账，
           //并根据领取数量减相应库存
           ZjConsumableInventory inventory = new ZjConsumableInventory();
           inventory.setSetId(dbzjConsumableApply.getSetId());
           List<ZjConsumableInventory> inventoryList = zjConsumableInventoryMapper.selectByZjConsumableInventoryList(inventory);
           if(inventoryList != null && inventoryList.size() >0) {
        	   inventoryList.get(0).setTotalUseNum(inventoryList.get(0).getTotalUseNum()+dbzjConsumableApply.getApplyNum());
        	   inventoryList.get(0).setNowNum(inventoryList.get(0).getTotalInNum()-inventoryList.get(0).getTotalUseNum());
        	   inventoryList.get(0).setModifyUserInfo(userKey, realName);
        	   flag = zjConsumableInventoryMapper.updateByPrimaryKey(inventoryList.get(0));
           }
           //修改领取数量和时间
           ZjConsumableApplyBook applyBook = new ZjConsumableApplyBook();
           applyBook.setApplyId(dbzjConsumableApply.getApplyId());
           List<ZjConsumableApplyBook> applyBookList = zjConsumableApplyBookMapper.selectByZjConsumableApplyBookList(applyBook);
           if(applyBookList != null && applyBookList.size() >0) {
        	   applyBookList.get(0).setUseStatus(dbzjConsumableApply.getUseStatus());
        	   applyBookList.get(0).setUseDate(new Date());
        	   applyBookList.get(0).setModifyUserInfo(userKey, realName);
        	   flag = zjConsumableApplyBookMapper.updateByPrimaryKey(applyBookList.get(0));
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjConsumableApply);
        }
	}
}
