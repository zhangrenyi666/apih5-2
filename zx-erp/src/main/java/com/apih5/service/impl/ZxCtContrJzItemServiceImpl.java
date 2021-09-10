package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtContrCsjzMapper;
import com.apih5.mybatis.dao.ZxCtContrDqjzMapper;
import com.apih5.mybatis.dao.ZxCtContrJzItemMapper;
import com.apih5.mybatis.pojo.ZxCtContrCsjz;
import com.apih5.mybatis.pojo.ZxCtContrDqjz;
import com.apih5.mybatis.pojo.ZxCtContrJzItem;
import com.apih5.service.ZxCtContrJzItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtContrJzItemService")
public class ZxCtContrJzItemServiceImpl implements ZxCtContrJzItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrJzItemMapper zxCtContrJzItemMapper;
    
    @Autowired(required = true)
    private ZxCtContrCsjzMapper zxCtContrCsjzMapper;
    
    @Autowired(required = true)
    private ZxCtContrDqjzMapper zxCtContrDqjzMapper;

    @Override
    public ResponseEntity getZxCtContrJzItemListByCondition(ZxCtContrJzItem zxCtContrJzItem) {
        if (zxCtContrJzItem == null) {
            zxCtContrJzItem = new ZxCtContrJzItem();
        }
        // 分页查询
        PageHelper.startPage(zxCtContrJzItem.getPage(),zxCtContrJzItem.getLimit());
        // 获取数据
        List<ZxCtContrJzItem> zxCtContrJzItemList = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(zxCtContrJzItem);
        // 得到分页信息
        PageInfo<ZxCtContrJzItem> p = new PageInfo<>(zxCtContrJzItemList);

        return repEntity.okList(zxCtContrJzItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContrJzItemDetail(ZxCtContrJzItem zxCtContrJzItem) {
        if (zxCtContrJzItem == null) {
            zxCtContrJzItem = new ZxCtContrJzItem();
        }
        // 获取数据
        ZxCtContrJzItem dbZxCtContrJzItem = zxCtContrJzItemMapper.selectByPrimaryKey(zxCtContrJzItem.getId());
        // 数据存在
        if (dbZxCtContrJzItem != null) {
            return repEntity.ok(dbZxCtContrJzItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrJzItem(ZxCtContrJzItem zxCtContrJzItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag  = 0;
        zxCtContrJzItem.setId(UuidUtil.generate());
        zxCtContrJzItem.setCreateUserInfo(userKey, realName);
		flag = zxCtContrJzItemMapper.insert(zxCtContrJzItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContrJzItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrJzItem(ZxCtContrJzItem zxCtContrJzItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrJzItem dbZxCtContrJzItem = zxCtContrJzItemMapper.selectByPrimaryKey(zxCtContrJzItem.getId());
        if (dbZxCtContrJzItem != null && StrUtil.isNotEmpty(dbZxCtContrJzItem.getId())) {
           // 主表id
           dbZxCtContrJzItem.setMainID(zxCtContrJzItem.getMainID());
           // 建造合同类型
           dbZxCtContrJzItem.setJzType(zxCtContrJzItem.getJzType());
           // 项目
           dbZxCtContrJzItem.setSubType(zxCtContrJzItem.getSubType());
           // 项目内容
           dbZxCtContrJzItem.setSubDetail(zxCtContrJzItem.getSubDetail());
           // 金额
           dbZxCtContrJzItem.setAmt(zxCtContrJzItem.getAmt());
           // 排序
           dbZxCtContrJzItem.setOrderStr(zxCtContrJzItem.getOrderStr());
           // 项目子类
           dbZxCtContrJzItem.setSubType2(zxCtContrJzItem.getSubType2());
           // 项目内容明细
           dbZxCtContrJzItem.setSubDetail2(zxCtContrJzItem.getSubDetail2());
           // 行标识
           dbZxCtContrJzItem.setHangCode(zxCtContrJzItem.getHangCode());
           // 行类型
           dbZxCtContrJzItem.setIsHuizong(zxCtContrJzItem.getIsHuizong());
           // 汇总到哪一行
           dbZxCtContrJzItem.setHuizongCode(zxCtContrJzItem.getHuizongCode());
           // 是否减项
           dbZxCtContrJzItem.setIsReduce(zxCtContrJzItem.getIsReduce());
           // 备注
           dbZxCtContrJzItem.setRemark(zxCtContrJzItem.getRemark());
           // 共通
           dbZxCtContrJzItem.setModifyUserInfo(userKey, realName);
           flag = zxCtContrJzItemMapper.updateByPrimaryKey(dbZxCtContrJzItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtContrJzItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrJzItem(List<ZxCtContrJzItem> zxCtContrJzItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrJzItemList != null && zxCtContrJzItemList.size() > 0) {
           ZxCtContrJzItem zxCtContrJzItem = new ZxCtContrJzItem();
           zxCtContrJzItem.setModifyUserInfo(userKey, realName);
           flag = zxCtContrJzItemMapper.batchDeleteUpdateZxCtContrJzItem(zxCtContrJzItemList, zxCtContrJzItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtContrJzItemList);
        }
    }

	@Override
	public ResponseEntity addZxCtContrJzItemForCs(ZxCtContrJzItem zxCtContrJzItem) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		BigDecimal csBudgetAmt = new BigDecimal("0");
		BigDecimal bhBudgetAmt = new BigDecimal("0");
		BigDecimal bhBudgetCost = new BigDecimal("0");
		BigDecimal sgBudgetCost = new BigDecimal("0");
		int flag  = 0;
		//先删除再新增
		ZxCtContrJzItem delItem = new ZxCtContrJzItem();
		delItem.setMainID(zxCtContrJzItem.getMainID());
		List<ZxCtContrJzItem> delItemList = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(delItem);
		if(delItemList != null && delItemList.size() >0) {
			delItem.setModifyUserInfo(userKey, realName);
			flag = zxCtContrJzItemMapper.batchDeleteUpdateZxCtContrJzItem(delItemList, delItem);
		}
		if(zxCtContrJzItem.getZxCtContrJzItemList() != null && zxCtContrJzItem.getZxCtContrJzItemList().size() >0){ 
			for (ZxCtContrJzItem item : zxCtContrJzItem.getZxCtContrJzItemList()) {
				item.setId(UuidUtil.generate());
				item.setMainID(zxCtContrJzItem.getMainID());
				item.setCreateUserInfo(userKey, realName);
				flag = zxCtContrJzItemMapper.insert(item);

				if(StrUtil.equals(item.getSubDetail(), "初始预计总收入")) {
					csBudgetAmt = item.getAmt();
				}
				if(StrUtil.equals(item.getSubType2(), "标后预算调整确认法") && StrUtil.equals(item.getSubDetail(), "项目标后预算费用总额")) {
					bhBudgetAmt = item.getAmt();
				}
				if(StrUtil.equals(item.getSubType2(), "标后预算调整确认法") && StrUtil.equals(item.getSubDetail(), "初始预计总成本")) {
					bhBudgetCost = item.getAmt();
				}
				if(StrUtil.equals(item.getSubType2(), "施工预算确认法") && StrUtil.equals(item.getSubDetail(), "初始预计总成本")) {
					sgBudgetCost = item.getAmt();
				}
			}
			//更新主表的金额
			ZxCtContrCsjz contrCsjz = zxCtContrCsjzMapper.selectByPrimaryKey(zxCtContrJzItem.getMainID());
			if(contrCsjz != null && StrUtil.isNotEmpty(contrCsjz.getId())) {
				contrCsjz.setCsBudgetAmt(csBudgetAmt);
				contrCsjz.setBhBudgetAmt(bhBudgetAmt);
				contrCsjz.setBhBudgetCost(bhBudgetCost);
				contrCsjz.setSgBudgetCost(sgBudgetCost);
				contrCsjz.setModifyUserInfo(userKey, realName);
				flag = zxCtContrCsjzMapper.updateByPrimaryKey(contrCsjz);
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCtContrJzItem);
		}
	}

	@Override
	public ResponseEntity addZxCtContrJzItemForDq(ZxCtContrJzItem zxCtContrJzItem) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		BigDecimal bhBudgetAmt = new BigDecimal("0");
		BigDecimal sgBudgetCost = new BigDecimal("0");
		
		int flag  = 0;
		//先删除再新增
		ZxCtContrJzItem delItem = new ZxCtContrJzItem();
		delItem.setMainID(zxCtContrJzItem.getMainID());
		List<ZxCtContrJzItem> delItemList = zxCtContrJzItemMapper.selectByZxCtContrJzItemList(delItem);
		if(delItemList != null && delItemList.size() >0) {
			delItem.setModifyUserInfo(userKey, realName);
			flag = zxCtContrJzItemMapper.batchDeleteUpdateZxCtContrJzItem(delItemList, delItem);
		}
		if(zxCtContrJzItem.getZxCtContrJzItemList() != null && zxCtContrJzItem.getZxCtContrJzItemList().size() >0){ 
			for (ZxCtContrJzItem item : zxCtContrJzItem.getZxCtContrJzItemList()) {
				item.setId(UuidUtil.generate());
				item.setMainID(zxCtContrJzItem.getMainID());
				item.setCreateUserInfo(userKey, realName);
				flag = zxCtContrJzItemMapper.insert(item);

				if(StrUtil.equals(item.getSubDetail(), "当前预计总收入")) {
					bhBudgetAmt = item.getAmt();
				}
				if(StrUtil.equals(item.getSubDetail(), "当前预计总成本")) {
					sgBudgetCost = item.getAmt();
				}
				
			}
			//更新主表的金额
			ZxCtContrDqjz contrDqjz = zxCtContrDqjzMapper.selectByPrimaryKey(zxCtContrJzItem.getMainID());
			if(contrDqjz != null && StrUtil.isNotEmpty(contrDqjz.getId())) {
				contrDqjz.setBhBudgetAmt(bhBudgetAmt);
				contrDqjz.setSgBudgetCost(sgBudgetCost);
				contrDqjz.setModifyUserInfo(userKey, realName);
				flag = zxCtContrDqjzMapper.updateByPrimaryKey(contrDqjz);
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCtContrJzItem);
		}
	}

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
