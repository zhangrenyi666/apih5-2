package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSaProjectPaySettleItemMapper;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettle;
import com.apih5.mybatis.pojo.ZxSaProjectPaySettleItem;
import com.apih5.service.ZxSaProjectPaySettleItemService;
import com.apih5.service.ZxSaProjectPaySettleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSaProjectPaySettleItemService")
public class ZxSaProjectPaySettleItemServiceImpl implements ZxSaProjectPaySettleItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaProjectPaySettleItemMapper zxSaProjectPaySettleItemMapper;
    
    @Autowired(required = true)
    private ZxSaProjectPaySettleService zxSaProjectPaySettleService;

    @Override
    public ResponseEntity getZxSaProjectPaySettleItemListByCondition(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        if (zxSaProjectPaySettleItem == null) {
            zxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
        }
        // 分页查询
        PageHelper.startPage(zxSaProjectPaySettleItem.getPage(),zxSaProjectPaySettleItem.getLimit());
        // 获取数据
        List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList = zxSaProjectPaySettleItemMapper.selectByZxSaProjectPaySettleItemList(zxSaProjectPaySettleItem);
        // 得到分页信息
        PageInfo<ZxSaProjectPaySettleItem> p = new PageInfo<>(zxSaProjectPaySettleItemList);

        return repEntity.okList(zxSaProjectPaySettleItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaProjectPaySettleItemDetail(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        if (zxSaProjectPaySettleItem == null) {
            zxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
        }
        // 获取数据
        ZxSaProjectPaySettleItem dbZxSaProjectPaySettleItem = zxSaProjectPaySettleItemMapper.selectByPrimaryKey(zxSaProjectPaySettleItem.getProjectPaySettleItemId());
        // 数据存在
        if (dbZxSaProjectPaySettleItem != null) {
            return repEntity.ok(dbZxSaProjectPaySettleItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaProjectPaySettleItem(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaProjectPaySettleItem.setProjectPaySettleItemId(UuidUtil.generate());
        zxSaProjectPaySettleItem.setCreateUserInfo(userKey, realName);
        zxSaProjectPaySettleItem.setIsFixed("0");
        
        // check（是否已存在改类型支付项）
        ZxSaProjectPaySettleItem selectItem = new ZxSaProjectPaySettleItem();
    	selectItem.setProjectPaySettleId(zxSaProjectPaySettleItem.getProjectPaySettleId());
    	List<ZxSaProjectPaySettleItem> paySettleItemList = zxSaProjectPaySettleItemMapper.selectByZxSaProjectPaySettleItemList(selectItem);
    	
    	if (paySettleItemList != null && paySettleItemList.size() > 0) {
    		List<ZxSaProjectPaySettleItem> payList = paySettleItemList.stream().filter(settleItem -> StrUtil.equals(settleItem.getPayName(), zxSaProjectPaySettleItem.getPayName())).collect(Collectors.toList());
    		if (payList != null && payList.size() > 0) {
				return repEntity.layerMessage("no", "该合同本期次已存在此名称支付项信息，请修改后重新提交！");
			}
    	}
    	
    	// 处理含税、不含税、税额
    	zxSaProjectPaySettleItem.setThisAmt(CalcUtils.calcMultiply(zxSaProjectPaySettleItem.getQty(), zxSaProjectPaySettleItem.getPrice()));
    	zxSaProjectPaySettleItem.setThisAmtNoTax(CalcUtils.calcMultiply(CalcUtils.calcDivide(zxSaProjectPaySettleItem.getThisAmt(), CalcUtils.calcAdd(new BigDecimal(100), new BigDecimal(zxSaProjectPaySettleItem.getTaxRate()))), new BigDecimal(100)));
    	zxSaProjectPaySettleItem.setThisAmtTax(CalcUtils.calcSubtract(zxSaProjectPaySettleItem.getThisAmt(), zxSaProjectPaySettleItem.getThisAmtNoTax()));
        
        // 主表信息
        ZxSaProjectPaySettle zxSaProjectPaySettle = new ZxSaProjectPaySettle();
    	zxSaProjectPaySettle.setProjectPaySettleId(zxSaProjectPaySettleItem.getProjectPaySettleId());
    	ZxSaProjectPaySettle paySettle = (ZxSaProjectPaySettle) zxSaProjectPaySettleService.getZxSaProjectPaySettleDetail(zxSaProjectPaySettle).getData();
        
    	if (paySettle != null) {
    		zxSaProjectPaySettleItem.setComID(paySettle.getComID());
    		zxSaProjectPaySettleItem.setComName(paySettle.getComName());
    		zxSaProjectPaySettleItem.setPeriod(paySettle.getPeriod());
    		zxSaProjectPaySettleItem.setContractID(paySettle.getContractID());
		}
    	
    	// 查询上期末结算金额
    	ZxSaProjectPaySettleItem upPaySettle = zxSaProjectPaySettleItemMapper.selectUpPaySettle(zxSaProjectPaySettleItem);
    	if (upPaySettle != null) {
    		zxSaProjectPaySettleItem.setUpAmt(upPaySettle.getUpAmt());
		}
    	
        int flag = zxSaProjectPaySettleItemMapper.insert(zxSaProjectPaySettleItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	paySettleItemList = zxSaProjectPaySettleItemMapper.selectByZxSaProjectPaySettleItemList(selectItem);
        	
        	// 修改支付项主表信息
        	paySettle.setProjectPaySettleItemList(paySettleItemList);
        	zxSaProjectPaySettleService.updateZxSaProjectPaySettle(paySettle);
        	return repEntity.ok("sys.data.sava", zxSaProjectPaySettleItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaProjectPaySettleItem(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSaProjectPaySettleItem dbZxSaProjectPaySettleItem = zxSaProjectPaySettleItemMapper.selectByPrimaryKey(zxSaProjectPaySettleItem.getProjectPaySettleItemId());
        if (dbZxSaProjectPaySettleItem != null && StrUtil.isNotEmpty(dbZxSaProjectPaySettleItem.getProjectPaySettleItemId())) {
           // 主表ID
           dbZxSaProjectPaySettleItem.setProjectPaySettleId(zxSaProjectPaySettleItem.getProjectPaySettleId());
           // 合同ID
           dbZxSaProjectPaySettleItem.setContractID(zxSaProjectPaySettleItem.getContractID());
           // 序号
           dbZxSaProjectPaySettleItem.setOrderNum(zxSaProjectPaySettleItem.getOrderNum());
           // 支付项ID
           dbZxSaProjectPaySettleItem.setPayID(zxSaProjectPaySettleItem.getPayID());
           // 编号
           dbZxSaProjectPaySettleItem.setPayNo(zxSaProjectPaySettleItem.getPayNo());
           // 支付项类型
           if (!StrUtil.equals(dbZxSaProjectPaySettleItem.getPayName(), zxSaProjectPaySettleItem.getPayName())) {
        	   	// check（是否已存在改类型支付项）
        	   	ZxSaProjectPaySettleItem selectItem = new ZxSaProjectPaySettleItem();
				selectItem.setProjectPaySettleId(dbZxSaProjectPaySettleItem.getProjectPaySettleId());
				List<ZxSaProjectPaySettleItem> paySettleItemList = zxSaProjectPaySettleItemMapper.selectByZxSaProjectPaySettleItemList(selectItem);
				
				if (paySettleItemList != null && paySettleItemList.size() > 0) {
					List<ZxSaProjectPaySettleItem> payList = paySettleItemList.stream().filter(settleItem -> StrUtil.equals(settleItem.getPayName(), zxSaProjectPaySettleItem.getPayName())).collect(Collectors.toList());
					if (payList != null && payList.size() > 0) {
						return repEntity.layerMessage("no", "该合同本期次已存在此类型支付项信息，请修改后重新提交！");
					}
				}
               dbZxSaProjectPaySettleItem.setPayType(zxSaProjectPaySettleItem.getPayType());
               // 名称
               dbZxSaProjectPaySettleItem.setPayName(zxSaProjectPaySettleItem.getPayName());
           }
           // 单位
           dbZxSaProjectPaySettleItem.setUnit(zxSaProjectPaySettleItem.getUnit());
           // 数量
           dbZxSaProjectPaySettleItem.setQty(zxSaProjectPaySettleItem.getQty());
           // 单价
           dbZxSaProjectPaySettleItem.setPrice(zxSaProjectPaySettleItem.getPrice());
           // 本期结算金额(元)
           dbZxSaProjectPaySettleItem.setThisAmt(zxSaProjectPaySettleItem.getThisAmt());
           // 上期末结算金额(元)
           dbZxSaProjectPaySettleItem.setUpAmt(zxSaProjectPaySettleItem.getUpAmt());
           // 最后编辑时间
           dbZxSaProjectPaySettleItem.setEditTime(zxSaProjectPaySettleItem.getEditTime());
           // 结算期次
           dbZxSaProjectPaySettleItem.setPeriod(zxSaProjectPaySettleItem.getPeriod());
           // 是否已修改
           dbZxSaProjectPaySettleItem.setIsFixed("1");
           // 所属公司ID
           dbZxSaProjectPaySettleItem.setComID(zxSaProjectPaySettleItem.getComID());
           // 所属公司
           dbZxSaProjectPaySettleItem.setComName(zxSaProjectPaySettleItem.getComName());
           // 所属公司排序
           dbZxSaProjectPaySettleItem.setComOrders(zxSaProjectPaySettleItem.getComOrders());
           // 税率(%)
           dbZxSaProjectPaySettleItem.setTaxRate(zxSaProjectPaySettleItem.getTaxRate());
           // 不含税金额
           dbZxSaProjectPaySettleItem.setThisAmtNoTax(zxSaProjectPaySettleItem.getThisAmtNoTax());
           // 税额
           dbZxSaProjectPaySettleItem.setThisAmtTax(zxSaProjectPaySettleItem.getThisAmtTax());
           // 备注
           dbZxSaProjectPaySettleItem.setRemark(zxSaProjectPaySettleItem.getRemark());
           // 排序
           dbZxSaProjectPaySettleItem.setSort(zxSaProjectPaySettleItem.getSort());
           // 共通
           dbZxSaProjectPaySettleItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectPaySettleItemMapper.updateByPrimaryKey(dbZxSaProjectPaySettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改支付项主表信息
        	ZxSaProjectPaySettleItem selectItem = new ZxSaProjectPaySettleItem();
        	selectItem.setProjectPaySettleId(dbZxSaProjectPaySettleItem.getProjectPaySettleId());
        	List<ZxSaProjectPaySettleItem> paySettleItemList = zxSaProjectPaySettleItemMapper.selectByZxSaProjectPaySettleItemList(selectItem);
        	
        	ZxSaProjectPaySettle zxSaProjectPaySettle = new ZxSaProjectPaySettle();
        	zxSaProjectPaySettle.setProjectPaySettleId(dbZxSaProjectPaySettleItem.getProjectPaySettleId());
        	ZxSaProjectPaySettle paySettle = (ZxSaProjectPaySettle) zxSaProjectPaySettleService.getZxSaProjectPaySettleDetail(zxSaProjectPaySettle).getData();
            
        	paySettle.setProjectPaySettleItemList(paySettleItemList);
        	zxSaProjectPaySettleService.updateZxSaProjectPaySettle(paySettle);
            return repEntity.ok("sys.data.update",zxSaProjectPaySettleItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaProjectPaySettleItem(List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaProjectPaySettleItemList != null && zxSaProjectPaySettleItemList.size() > 0) {
           ZxSaProjectPaySettleItem zxSaProjectPaySettleItem = new ZxSaProjectPaySettleItem();
           zxSaProjectPaySettleItem.setModifyUserInfo(userKey, realName);
           flag = zxSaProjectPaySettleItemMapper.batchDeleteUpdateZxSaProjectPaySettleItem(zxSaProjectPaySettleItemList, zxSaProjectPaySettleItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改支付项主表信息
        	ZxSaProjectPaySettleItem selectItem = new ZxSaProjectPaySettleItem();
        	selectItem.setProjectPaySettleId(zxSaProjectPaySettleItemList.get(0).getProjectPaySettleId());
        	List<ZxSaProjectPaySettleItem> paySettleItemList = zxSaProjectPaySettleItemMapper.selectByZxSaProjectPaySettleItemList(selectItem);
        	
        	ZxSaProjectPaySettle zxSaProjectPaySettle = new ZxSaProjectPaySettle();
        	zxSaProjectPaySettle.setProjectPaySettleId(zxSaProjectPaySettleItemList.get(0).getProjectPaySettleId());
        	ZxSaProjectPaySettle paySettle = (ZxSaProjectPaySettle) zxSaProjectPaySettleService.getZxSaProjectPaySettleDetail(zxSaProjectPaySettle).getData();
            
        	paySettle.setProjectPaySettleItemList(paySettleItemList);
        	zxSaProjectPaySettleService.updateZxSaProjectPaySettle(paySettle);
            return repEntity.ok("sys.data.delete",zxSaProjectPaySettleItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity deleteAllZxSaProjectPaySettleItem(ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
		if (zxSaProjectPaySettleItem == null || StrUtil.isEmpty(zxSaProjectPaySettleItem.getProjectPaySettleId())) {
			return repEntity.layerMessage("no", "支付项主键ID不能为空！");
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        zxSaProjectPaySettleItem.setModifyUserInfo(userKey, realName);
        flag = zxSaProjectPaySettleItemMapper.deleteAllZxSaProjectPaySettleItem(zxSaProjectPaySettleItem);
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("删除成功！");
        }
	}
}
